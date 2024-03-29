package nc.ui.dm.so.deal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.BillStatus;
import nc.ui.pub.bill.IBillModelRowStateChangeEventListener;
import nc.ui.pub.bill.IBillRelaSortListener2;
import nc.ui.wl.pub.FilterNullBody;
import nc.ui.zmpub.pub.tool.SingleVOChangeDataUiTool;
import nc.vo.dm.so.deal.SoDeHeaderVo;
import nc.vo.dm.so.deal.SoDealBillVO;
import nc.vo.dm.so.deal.SoDealVO;
import nc.vo.ic.pub.StockInvOnHandVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.query.ConditionVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.trade.voutils.VOUtil;
import nc.vo.wdsnew.pub.AvailNumBO;
import nc.vo.wdsnew.pub.BillStockBO1;
import nc.vo.wl.pub.VOTool;
import nc.vo.wl.pub.WdsWlPubConst;
import nc.vo.wl.pub.WdsWlPubTool;
import nc.vo.zmpub.pub.tool.ZmPubTool;

public class SoDealEventHandler implements BillEditListener,IBillRelaSortListener2{	
	private SoDealClientUI ui = null;
	private SoDealQryDlg m_qrypanel = null;
	
	public SoDealVO[] curBodys = null;
	//数据缓存
	private SoDealVO[] m_billdatas = null;
	private String whereSql = null;
	
	private BillStockBO1 stock=null;
	
	public BillStockBO1 getStock(){
		if(stock ==null){
			stock =new BillStockBO1();
		}
		return stock ;
	}
	  private AvailNumBO  abo=null;
	    public AvailNumBO getAbo(){
	    	
	    	if(abo==null){
	    		abo=new AvailNumBO();
	    	}
	    	return abo;
	    }
	
//	private List<SoDealVO> lseldata = new ArrayList<SoDealVO>();
	
	UFBoolean getOrderType(){
		if(getQryDlg().m_rbclose.isSelected()){
			return UFBoolean.TRUE;
		}else{
			return UFBoolean.FALSE;
		}
	}
	
	public SoDealEventHandler(SoDealClientUI parent){
		super();
		ui = parent;
		getDataPane().addSortRelaObjectListener2(this);
		
	}
	private BillModel getBodyDataPane(){
		return ui.getPanel().getBodyBillModel();
	}

	private BillModel getDataPane(){
		return ui.getPanel().getHeadBillModel();
	}

	public void onButtonClicked(String btnTag){
		if(btnTag.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_DEAL)){
			try {
				onDeal();
			} catch (BusinessException e) {
				e.printStackTrace();
				showErrorMessage(e.getMessage());
			}
		}else if(btnTag.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_SELNO)){
			onNoSel();
		}else if(btnTag.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_SELALL)){
			onAllSel();
		}else if(btnTag.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_QRY)){
			onQuery();
		}else if (btnTag
				.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_XNDEAL)) {
//			onXNDeal();
		}else if(btnTag.equalsIgnoreCase("关闭")){
			try {
				onClose();
			} catch (Exception e) {
				e.printStackTrace();
				showErrorMessage(e.getMessage());
			}
		}else if(btnTag.equalsIgnoreCase("打开")){
			try {
				onOpen();
			} catch (Exception e) {
				e.printStackTrace();
				showErrorMessage(e.getMessage());
			}
		}
	}
	
	public SoDealVO[] getDataBuffer(){
		return m_billdatas;
	}
	/**
	 * 
	 * @作者：lyf:根据表头单据号，加载表体
	 * @说明：完达山物流项目 
	 * @时间：2011-11-10下午08:10:37
	 * @param key
	 * @return
	 */
	protected SoDealVO[] getSelectBufferData(String key ){
		if(key == null || "".equalsIgnoreCase(key)){
			return null;
		}
		
		if(m_billdatas == null || m_billdatas.length ==0){
			return null;
		}
		ArrayList<SoDealVO> list = new ArrayList<SoDealVO>();
		for(SoDealVO dealvo:m_billdatas)
		{
			String vreceiptcode = dealvo.getVreceiptcode();
			if(key.equalsIgnoreCase(vreceiptcode)){
				list.add(dealvo);
			}
		}
		curBodys = list.toArray( new SoDealVO[0]);
		return curBodys;
 	}
	public void onNoSel(){
		int rowcount = getDataPane().getRowCount();
		if(rowcount <= 0)
			return;
		for (int i = 0; i < ui.getPanel().getParentListPanel().getTable().getRowCount(); i++) {
			ui.getPanel().getParentListPanel().getTableModel().setRowState(i, BillModel.UNSTATE);
			ui.headRowChange(i);
			BillModel model = ui.getPanel().getBodyBillModel();
			IBillModelRowStateChangeEventListener l = model.getRowStateChangeEventListener();
			model.removeRowStateChangeEventListener();
			ui.getPanel().getChildListPanel().cancelSelectAllTableRow();
			model.addRowStateChangeEventListener(l);
			ui.getPanel().updateUI();
		}
	}
	

	/**
	 * 
	 * @作者：全选
	 * @说明：完达山物流项目 
	 * @时间：2011-11-11上午11:09:55
	 */
	public void onAllSel(){
		if(getDataBuffer() == null||getDataBuffer().length == 0)
			return;
		for (int i = 0; i < ui.getPanel().getParentListPanel().getTable().getRowCount(); i++) {
			ui.getPanel().getParentListPanel().getTableModel().setRowState(i, BillModel.SELECTED);
			ui.headRowChange(i);
			BillModel model = ui.getPanel().getBodyBillModel();
			IBillModelRowStateChangeEventListener l = model.getRowStateChangeEventListener();
			model.removeRowStateChangeEventListener();
			ui.getPanel().getChildListPanel().selectAllTableRow();
			model.addRowStateChangeEventListener(l);
			ui.getPanel().updateUI();
		}
		
	}
	private SoDealQryDlg getQryDlg(){
		if(m_qrypanel == null){
			m_qrypanel = new SoDealQryDlg();
			m_qrypanel.setTempletID(ui.cl.getCorp(), WdsWlPubConst.DM_SO_DEAL_NODECODE, ui.cl.getUser(), null);
			m_qrypanel.hideUnitButton();
		}
		return m_qrypanel;
	}
	
	private void clearData(){
		m_billdatas = null;
		getDataPane().clearBodyData();
		getBodyDataPane().clearBodyData();
	}
	/**
	 * 
	 * @作者：lyf
	 * @说明：完达山物流项目 
	 * 查询动作相应方法
	 * @时间：2011-3-25上午09:49:04
	 */
	public void onQuery(){		
		/**
		 * 满足什么条件的计划呢？人员和仓库已经绑定了   登陆人只能查询他的权限仓库  总仓的人可以安排分仓的
		 * 校验登录人是否为总仓库的人 如果是可以安排任何仓库的  转分仓  计划 
		 * 如果是分仓的人 只能 安排  本分仓内部的  发运计划
		 */	
		if(PuPubVO.getString_TrimZeroLenAsNull(ui.getWhid()) == null){
			showWarnMessage("当前登录人未绑定仓库");
			return ;
		}
		getQryDlg().showModal();
		if(!getQryDlg().isCloseOK())
			return;
		whereSql = getSQL();

		try{			
			onRefresh();
			
		}catch(Exception e){
			e.printStackTrace();
			showErrorMessage(WdsWlPubTool.getString_NullAsTrimZeroLen(e.getMessage()));
			return;
		}		
		
	}
	/**
	 * 设置先存量信息
	 * @作者：mlr
	 * @说明：完达山物流项目 
	 * @时间：2012-7-2下午01:31:22
	 * @param billdatas
	 * @throws Exception 
	 */
	private void setStock(SoDealVO[] billdatas) throws Exception {
		if(billdatas==null || billdatas.length==0)
			return ;
		String pk_ssdate=(String) ZmPubTool.execFomularClient("pk_ssdate->getColValue2(tb_stockstate,ss_pk,ss_state,ss_state,pk_corp,pk_corp)", 
				new String[]{"ss_state","pk_corp"},
                new String[]{"合格",ClientEnvironment.getInstance().getCorporation().getPrimaryKey()});
	
		
		for(int i=0;i<billdatas.length;i++){
			billdatas[i].setVdef1(pk_ssdate);
			if(billdatas[i].getCbodywarehouseid()==null ||billdatas[i].getCbodywarehouseid().length()==0){
			billdatas[i].setCbodywarehouseid(ui.getWhid());
			}
			billdatas[i].setPk_corp(ui.getCl().getCorp());
		}
		//构造现存量查询条件
		StockInvOnHandVO[] vos=(StockInvOnHandVO[]) SingleVOChangeDataUiTool.runChangeVOAry(billdatas, StockInvOnHandVO.class, "nc.ui.wds.self.changedir.CHGWDS4TOACCOUNTNUM");
		if(vos==null || vos.length==0)
			return;
		//获得现存量
		StockInvOnHandVO[] nvos=(StockInvOnHandVO[]) getStock().queryStockCombinForClient(vos);
		if(nvos==null || nvos.length==0)
			return ;
		for(int i=0;i<billdatas.length;i++){
			if(nvos[i]!=null){		
				UFDouble  uf1=nvos[i].getWhs_stocktonnage();//库存主数量
				billdatas[i].setNstorenumout(uf1);
			}
		}
	}
	private void setAvailNum(SoDealVO[] billdatas) throws Exception {		
		if(billdatas==null || billdatas.length==0)
			return ;
		String pk_ssdate=(String) ZmPubTool.execFomularClient("pk_ssdate->getColValue2(tb_stockstate,ss_pk,ss_state,ss_state,pk_corp,pk_corp)", 
				new String[]{"ss_state","pk_corp"},
                new String[]{"合格",ClientEnvironment.getInstance().getCorporation().getPrimaryKey()});
	
		for(int i=0;i<billdatas.length;i++){
			billdatas[i].setVdef1(pk_ssdate);
		}
		//构造现存量查询条件
		StockInvOnHandVO[] vos=(StockInvOnHandVO[]) SingleVOChangeDataUiTool.runChangeVOAry(billdatas, StockInvOnHandVO.class, "nc.ui.wds.self.changedir.CHGWDS4TOACCOUNTNUM");
		if(vos==null || vos.length==0)
			return;
		StockInvOnHandVO[] nvos=(StockInvOnHandVO[]) getAbo().getAvailNumForClient(vos);
		if(nvos==null || nvos.length==0)
			return ;
		for(int i=0;i<billdatas.length;i++){
			if(nvos[i]!=null){		
				UFDouble  uf1=nvos[i].getWhs_stocktonnage();//可用主数量
				UFDouble uf2=nvos[i].getWhs_stockpieces();//可用辅数量
				billdatas[i].setNdrqarrstorenumout(uf2);
				billdatas[i].setNdrqstorenumout(uf1);
			}
		}
	}

	private void onRefresh() throws Exception{
		SoDealVO[] billdatas = null;
		clearData();
		boolean iserrorhint = false;
		if(PuPubVO.getString_TrimZeroLenAsNull(whereSql)!=null){

			try{
				billdatas = SoDealHealper.doQuery(whereSql,ui.getWhid(),querStorepk,getOrderType());
			   // querStorepk=null;
			}catch(Exception e){
				e.printStackTrace();
				showErrorMessage(WdsWlPubTool.getString_NullAsTrimZeroLen(e.getMessage()));
				return;
			}		
			if(billdatas == null||billdatas.length == 0){
//				clearData();
				showHintMessage("操作完成：没有满足条件的数据");
				return;
			}

			try {
				setStock(billdatas);
				setAvailNum(billdatas);
			} catch (Exception e) {
				e.printStackTrace();
				ui.showHintMessage("设置库存量异常");
				iserrorhint = true;
			}

			setData(billdatas);
			
//			//设置默认第一行选中
//			if(billdatas!=null&& billdatas.length>0){
//	          ui.getPanel().getHeadBillModel().setRowState(0, BillModel.SELECTED);		
//	          ui.getPanel().getChildListPanel().selectAllTableRow();
//			}

		}
		if(!iserrorhint)
			showHintMessage("操作完成");
	}
	
	/**
	 * 
	 * @作者：lyf:查询完成设置界面数据
	 * @说明：完达山物流项目 
	 * @时间：2011-11-10下午11:57:53
	 * @param billdatas
	 */
	void setData(SoDealVO[] billdatas ){
		CircularlyAccessibleValueObject[][]  voss = SplitBillVOs.getSplitVOs(billdatas, SoDeHeaderVo.split_fields);
		if(voss == null || voss.length ==0)
			return ;
		int len = voss.length;
		SoDealBillVO[] billvos1 = new SoDealBillVO[len];
		SoDealBillVO tmpbill = null;
		SoDeHeaderVo tmpHead = null;
		SoDealVO[] vos = null;
		for(int i=0;i<len;i++){
			vos = (SoDealVO[])voss[i];
			tmpHead = new SoDeHeaderVo();
			tmpHead.setCcustomerid(vos[0].getCcustomerid());
			tmpHead.setDbilldate((UFDate)VOTool.max(vos, "dbilldate"));//应取 最小订单日期
			tmpHead.setBisspecial(UFBoolean.FALSE);
			tmpHead.setCsalecorpid(vos[0].getCsalecorpid());
			tmpHead.setVreceiptcode(vos[0].getVreceiptcode());
			tmpHead.setCbiztype(vos[0].getCbiztype());
			tmpHead.setCemployeeid(vos[0].getCemployeeid());
			tmpHead.setCdeptid(vos[0].getCdeptid());
			
			tmpHead.setPk_defdoc12(vos[0].getPk_defdoc12());
			tmpHead.setVdef12(vos[0].getVdef12());
		

			String pk_stordoc=vos[0].getCbodywarehouseid();
			if(pk_stordoc==null || pk_stordoc.length()==0)
				pk_stordoc=ui.getWhid();
			tmpHead.setCbodywarehouseid(pk_stordoc);
			tmpHead.setStatus(VOStatus.NEW);
			tmpbill = new SoDealBillVO();
			tmpbill.setParentVO(tmpHead);
			tmpbill.setChildrenVO(vos);
			billvos1[i] = tmpbill;
		}
		SoDealBillVO[] billvos  =sort(billvos1, billdatas);
		//处理查询出的计划  缓存  界面
		getDataPane().setBodyDataVO(WdsWlPubTool.getParentVOFromAggBillVo(billvos, SoDeHeaderVo.class));
		getDataPane().execLoadFormula();
		getBodyDataPane().setBodyDataVO(billvos[0].getChildrenVO());
		getBodyDataPane().execLoadFormula();
		 setDataBuffer(billdatas);		
		showHintMessage("查询完成");
		ui.updateButtonStatus(WdsWlPubConst.DM_PLANDEAL_BTNTAG_DEAL,true);
	}
	private  String querStorepk=null;
	/**
	 * 
	 * @作者：lyf
	 * @说明：完达山物流项目 
	 * 获得 对 发运计划的查询条件
	 * wds_sendplanin发运计划主表
	 * wds_sendplanin_b 发运计划子表
	 * @时间：2011-3-25上午09:47:50
	 * @return
	 * @throws Exception
	 */
	private String getSQL(){
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" h.pk_corp='"+ui.cl.getCorp());
		whereSql.append("' and (coalesce(b.nnumber,0) -  coalesce(b."+WdsWlPubConst.DM_SO_DEALNUM_FIELD_NAME+",0)) > 0 ");
		String where = null;
	
		ConditionVO[] vos= getQryDlg().getConditionVO();
		
		if(vos==null || vos.length==0){
			where=getQryDlg().getWhereSQL();
		}else{
			List<ConditionVO> list=new ArrayList<ConditionVO>();
			//过滤掉出发货站查询条件
			for(int i=0;i<vos.length;i++){
				if(vos[i].getFieldCode().equals("pk_out")){
					querStorepk=vos[i].getValue();
				}else{
					list.add(vos[i]);
				}
			}		
			where=getQryDlg().getWhereSQL(list.toArray(new ConditionVO[0]));
		}
		
		if(PuPubVO.getString_TrimZeroLenAsNull(where)!=null){
			whereSql.append(" and "+where);
		}
		whereSql.append(" and h.fstatus in('"+BillStatus.AUDIT+"','"+BillStatus.FINISH+"') and isnull(h.dr,0)=0");//审核通过的
		/**
		 * 
		 * bifreceiptfinish              CHAR(1)             是否发货结束   NULL                
           bifinventoryfinish            CHAR(1)             是否出库结束     
		 * 
		 * 在 销售扩展子表上 存在表体的行状态   没有进行过滤 如果后续需要  应扩展对  以上发货结束的控制 
		 * 
		 */
		return whereSql.toString();
	}
	
	private void setDataBuffer(SoDealVO[] billdatas){
		this.m_billdatas = billdatas;
	}
	
	private AggregatedValueObject[] getSelectVos(){
		AggregatedValueObject[] selectVos = ui.getPanel().getMultiSelectedVOs(SoDealBillVO.class.getName(), SoDeHeaderVo.class.getName(), SoDealVO.class.getName());
		AggregatedValueObject[] newVos = (AggregatedValueObject[])VOUtil.filter(selectVos, new FilterNullBody());
		return newVos;
	}
	/**
	 * 
	 * @throws BusinessException 
	 * @作者：zhf
	 * @说明：完达山物流项目 
	 * 发运计划  安排按钮处理方法
	 * @时间：2011-3-25下午02:59:20
	 */
	public void onDeal() throws BusinessException{
		/**
		 * 数据校验
		 * 过滤掉本次安排数量为0的行  
		 * 本次安排数量不能大于 计划数量-累计安排数量
		 * 将满足的数据传入后台   数据转换   保存    
		 * 
		 * 分单规则：计划号  发货站 收货站  存货   单据日期   安排日期
		 * 
		 */
//		AggregatedValueObject[] selectVos = ui.getPanel().getMultiSelectedVOs(SoDealBillVO.class.getName(), SoDeHeaderVo.class.getName(), SoDealVO.class.getName());
		WdsWlPubTool.stopEditing(getDataPane());
		WdsWlPubTool.stopEditing(getBodyDataPane());
		
		AggregatedValueObject[] newVos = getSelectVos();
		if(newVos == null || newVos.length == 0){
			showWarnMessage("未选中数据");
			return;
		}
		//表头仓库 可以编辑 ，更新表体仓库
		for(int i=0;i<newVos.length;i++){
			Object cbodywarehouseid =newVos[i].getParentVO().getAttributeValue("cbodywarehouseid");
			CircularlyAccessibleValueObject[] bodys = newVos[i].getChildrenVO();
			UFBoolean biszt = PuPubVO.getUFBoolean_NullAs(newVos[i].getParentVO().getAttributeValue("bdericttrans"), UFBoolean.FALSE);
			if(bodys != null){
				for(CircularlyAccessibleValueObject body:bodys){
					body.setAttributeValue("cbodywarehouseid", cbodywarehouseid);
					body.setAttributeValue("bdericttrans", biszt);
				}
			}
		}
		//校验只有相同的客户可以合单
		CircularlyAccessibleValueObject[][]  splitVos = SplitBillVOs.getSplitVOs(WdsWlPubTool.getParentVOFromAggBillVo(newVos, SoDeHeaderVo.class), new String[]{"ccustomerid"});
		if(splitVos !=null && splitVos.length>1){
			showErrorMessage("只能安排相同的客户合单");
			return ;
		}
		List<SuperVO> ldata = WdsWlPubTool.filterVOsZeroNum(WdsWlPubTool.getBodysVOFromAggBillVo(newVos, SoDealVO.class),"nnum");
		if(ldata == null||ldata.size() == 0){
			showErrorMessage("选中数据没有安排");
			return;
		}
		//安排  安排前   数据校验:赠品单不能被拆分
		checkIsGiftSpilt((SoDealBillVO[])newVos);
		try{
			for(SuperVO vo:ldata){
				((SoDealVO)vo).validataOnDeal();
			}
			if(!valute(ldata)){
				ui.showErrorMessage("可用量不够");
				return;
			}
			SoDealHealper.doDeal(ldata, ui);
			onRefresh();
		}catch(Exception e){
			e.printStackTrace();
			if(e instanceof ValidationException){
				showErrorMessage(e.getMessage());
				return;
			}
			showErrorMessage(WdsWlPubTool.getString_NullAsTrimZeroLen(e.getMessage()));
			return;
		}
	}
	/**
	 * 校验可用量是否够用
	 * 
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2012-7-27上午10:40:44
	 * @param ldata
	 */
	private boolean valute(List<SuperVO> ldata) {
		if(ldata==null || ldata.size()==0){
			return true;
		}
		
		//liuys add 循环遍历表体vo,将相同存货的表体安排数量相加,最终用于比对该存货的现存量
		Map<String,UFDouble> map = new HashMap<String, UFDouble>();
		for(int i=0;i<ldata.size();i++){
			SuperVO vo=ldata.get(i);
			if(vo == null)
				continue;
			String cinventoryid = PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue("cinventoryid"));
			UFDouble nassnum = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("nassnum"));//安排数量
			if(map.containsKey(cinventoryid)){
				UFDouble value = PuPubVO.getUFDouble_NullAsZero(map.get(cinventoryid));
				map.put(cinventoryid, nassnum.add(value));
			}else{
				map.put(cinventoryid,nassnum);
			}
		}
		
		for(int i=0;i<ldata.size();i++){
			SuperVO vo=ldata.get(i);
			//安排量
			if(vo == null)
				continue;
			String cinventoryid = PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue("cinventoryid"));
			UFDouble uf1=map.get(cinventoryid);
			//可用量
			UFDouble uf2=PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("ndrqarrstorenumout"));
			if((uf2.sub(uf1)).doubleValue()<0){
				return false;
			}else{
				continue;
			}
		}
		return true;
	}

	/**
	 * 
	 * @作者：校验，赠品单是否被拆分
	 * @说明：完达山物流项目 
	 * @时间：2011-12-1下午02:55:46
	 * @param dealBills
	 * @throws BusinessException 
	 */
	private void checkIsGiftSpilt(SoDealBillVO[] dealBills) throws BusinessException{		
		for(SoDealBillVO dealBill:dealBills){
			SoDealVO[] bodys = dealBill.getBodyVos();
			if(bodys == null || bodys.length ==0){
				throw  new BusinessException("表体不能为空");
			}
			//将表体数据按照订单分单
			CircularlyAccessibleValueObject[][] vos = SplitBillVOs.getSplitVOs(bodys, new String[]{"csaleid"});
			if(vos == null || vos.length ==0){
				return;
			}
			//判断赠品是否拆单
			for(int i=0;i<vos.length;i++){
				SoDealVO[] splitBodys =(SoDealVO[])vos[i];
				String csaleid = splitBodys[0].getCsaleid();
				if(csaleid == null || "".equalsIgnoreCase(csaleid)){
					continue;
				}
				int count =0;
				boolean fisgift = false;
				for(SoDealVO dealvo:getDataBuffer()){//跟缓存中的数据比较
					String csaleid2 = dealvo.getCsaleid();
					boolean  blargessflag = PuPubVO.getUFBoolean_NullAs(dealvo.getBlargessflag(), UFBoolean.FALSE).booleanValue();
					if(csaleid.equalsIgnoreCase(csaleid2)){
						count= count+1;
						if(blargessflag){
							fisgift = blargessflag;
						}
					}
				}
				if(fisgift && (splitBodys.length-count)<0){//如果是赠品单，则必须整单安排，不能拆单据
					throw new BusinessException("赠品必须整单安排，不能拆单安排");
				}
			}
		}

	}
	 /**
	 * 按订单日期由小到大排序
	 * 
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2012-7-31下午01:51:19
	 * @param billvos
	 * @param m_billdatas
	 */
	private SoDealBillVO[] sort(SoDealBillVO[] billvos, SoDealVO[] m_billdatas) {
		
		SoDeHeaderVo[]  hvos=(SoDeHeaderVo[]) WdsWlPubTool.getParentVOFromAggBillVo(billvos, SoDeHeaderVo.class);
		if(hvos==null|| hvos.length==0)
			return null;
		VOUtil.ascSort(hvos, new String[]{"dbilldate"});
		SoDealBillVO[]  bills=new SoDealBillVO[billvos.length];
		for(int i=0;i<bills.length;i++){
			SoDeHeaderVo hvo=hvos[i];
			List<SoDealVO> bodys=new ArrayList<SoDealVO>();
			for(int k=0;k<m_billdatas.length;k++){
				boolean isq=true;
			   for(int j=0;j< SoDeHeaderVo.split_fields.length;j++){
			      if(!hvo.getAttributeValue(SoDeHeaderVo.split_fields[j]).equals(m_billdatas[k].getAttributeValue(SoDeHeaderVo.split_fields[j]))){
			    	  isq=false;
			    	  break;
			      }
			   }
			   if(isq){
				   bodys.add(m_billdatas[k]);
			   }
			}
			bills[i]=new SoDealBillVO();
			bills[i].setParentVO(hvos[i]);
			bills[i].setChildrenVO(bodys.toArray(new SoDealVO[0]));
		}		
		return bills;
	}
	public void bodyRowChange(BillEditEvent e) {		
	}
	
	private void showErrorMessage(String msg){
		ui.showErrorMessage(msg);
	}
	private void showWarnMessage(String msg){
		ui.showWarningMessage(msg);
	}
	private void showHintMessage(String msg){
		ui.showHintMessage(msg);
	}
	//发运计划安排1.点击表头不能排序2013.12.19
	public Object[] getRelaSortObjectArray() {
		// TODO Auto-generated method stub
//		return getDataBuffer();
		return null;
	}

	public void afterEdit(BillEditEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void doCloseOrOpen(UFBoolean bclose) throws Exception {
		AggregatedValueObject[] newVos = getSelectVos();
		if(newVos == null || newVos.length == 0){
			showWarnMessage("未选中数据");
			return;
		}
		SoDealBillVO[] orders = (SoDealBillVO[])newVos;
		SoDealVO[] bodys = null;
		List<String> lhid = new ArrayList<String>();
		String key = null;
		for(SoDealBillVO order:orders){
			bodys = order.getBodyVos();
			for(SoDealVO body:bodys){
				key = PuPubVO.getString_TrimZeroLenAsNull(body.getCsaleid());
				if(key == null)
					continue;
				if(lhid.contains(key))
					continue;
				lhid.add(key);				
			}
		}

		if(lhid.size()<=0)
			return;

		//		远程调用
		SoDealHealper.doCloseOrOpen(lhid.toArray(new String[0]), ui, bclose);

		//		刷新界面数据
		onRefresh();

	}
	
	private void onClose()throws Exception{
		doCloseOrOpen(UFBoolean.TRUE);
	}
    private void onOpen()throws Exception{
    	doCloseOrOpen(UFBoolean.FALSE);
    }
}

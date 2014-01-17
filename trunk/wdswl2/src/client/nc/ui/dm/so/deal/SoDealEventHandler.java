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
	//���ݻ���
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
		}else if(btnTag.equalsIgnoreCase("�ر�")){
			try {
				onClose();
			} catch (Exception e) {
				e.printStackTrace();
				showErrorMessage(e.getMessage());
			}
		}else if(btnTag.equalsIgnoreCase("��")){
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
	 * @���ߣ�lyf:���ݱ�ͷ���ݺţ����ر���
	 * @˵�������ɽ������Ŀ 
	 * @ʱ�䣺2011-11-10����08:10:37
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
	 * @���ߣ�ȫѡ
	 * @˵�������ɽ������Ŀ 
	 * @ʱ�䣺2011-11-11����11:09:55
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
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ 
	 * ��ѯ������Ӧ����
	 * @ʱ�䣺2011-3-25����09:49:04
	 */
	public void onQuery(){		
		/**
		 * ����ʲô�����ļƻ��أ���Ա�Ͳֿ��Ѿ�����   ��½��ֻ�ܲ�ѯ����Ȩ�޲ֿ�  �ֵܲ��˿��԰��ŷֲֵ�
		 * У���¼���Ƿ�Ϊ�ֿܲ���� ����ǿ��԰����κβֿ��  ת�ֲ�  �ƻ� 
		 * ����Ƿֲֵ��� ֻ�� ����  ���ֲ��ڲ���  ���˼ƻ�
		 */	
		if(PuPubVO.getString_TrimZeroLenAsNull(ui.getWhid()) == null){
			showWarnMessage("��ǰ��¼��δ�󶨲ֿ�");
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
	 * �����ȴ�����Ϣ
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ 
	 * @ʱ�䣺2012-7-2����01:31:22
	 * @param billdatas
	 * @throws Exception 
	 */
	private void setStock(SoDealVO[] billdatas) throws Exception {
		if(billdatas==null || billdatas.length==0)
			return ;
		String pk_ssdate=(String) ZmPubTool.execFomularClient("pk_ssdate->getColValue2(tb_stockstate,ss_pk,ss_state,ss_state,pk_corp,pk_corp)", 
				new String[]{"ss_state","pk_corp"},
                new String[]{"�ϸ�",ClientEnvironment.getInstance().getCorporation().getPrimaryKey()});
	
		
		for(int i=0;i<billdatas.length;i++){
			billdatas[i].setVdef1(pk_ssdate);
			if(billdatas[i].getCbodywarehouseid()==null ||billdatas[i].getCbodywarehouseid().length()==0){
			billdatas[i].setCbodywarehouseid(ui.getWhid());
			}
			billdatas[i].setPk_corp(ui.getCl().getCorp());
		}
		//�����ִ�����ѯ����
		StockInvOnHandVO[] vos=(StockInvOnHandVO[]) SingleVOChangeDataUiTool.runChangeVOAry(billdatas, StockInvOnHandVO.class, "nc.ui.wds.self.changedir.CHGWDS4TOACCOUNTNUM");
		if(vos==null || vos.length==0)
			return;
		//����ִ���
		StockInvOnHandVO[] nvos=(StockInvOnHandVO[]) getStock().queryStockCombinForClient(vos);
		if(nvos==null || nvos.length==0)
			return ;
		for(int i=0;i<billdatas.length;i++){
			if(nvos[i]!=null){		
				UFDouble  uf1=nvos[i].getWhs_stocktonnage();//���������
				billdatas[i].setNstorenumout(uf1);
			}
		}
	}
	private void setAvailNum(SoDealVO[] billdatas) throws Exception {		
		if(billdatas==null || billdatas.length==0)
			return ;
		String pk_ssdate=(String) ZmPubTool.execFomularClient("pk_ssdate->getColValue2(tb_stockstate,ss_pk,ss_state,ss_state,pk_corp,pk_corp)", 
				new String[]{"ss_state","pk_corp"},
                new String[]{"�ϸ�",ClientEnvironment.getInstance().getCorporation().getPrimaryKey()});
	
		for(int i=0;i<billdatas.length;i++){
			billdatas[i].setVdef1(pk_ssdate);
		}
		//�����ִ�����ѯ����
		StockInvOnHandVO[] vos=(StockInvOnHandVO[]) SingleVOChangeDataUiTool.runChangeVOAry(billdatas, StockInvOnHandVO.class, "nc.ui.wds.self.changedir.CHGWDS4TOACCOUNTNUM");
		if(vos==null || vos.length==0)
			return;
		StockInvOnHandVO[] nvos=(StockInvOnHandVO[]) getAbo().getAvailNumForClient(vos);
		if(nvos==null || nvos.length==0)
			return ;
		for(int i=0;i<billdatas.length;i++){
			if(nvos[i]!=null){		
				UFDouble  uf1=nvos[i].getWhs_stocktonnage();//����������
				UFDouble uf2=nvos[i].getWhs_stockpieces();//���ø�����
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
				showHintMessage("������ɣ�û����������������");
				return;
			}

			try {
				setStock(billdatas);
				setAvailNum(billdatas);
			} catch (Exception e) {
				e.printStackTrace();
				ui.showHintMessage("���ÿ�����쳣");
				iserrorhint = true;
			}

			setData(billdatas);
			
//			//����Ĭ�ϵ�һ��ѡ��
//			if(billdatas!=null&& billdatas.length>0){
//	          ui.getPanel().getHeadBillModel().setRowState(0, BillModel.SELECTED);		
//	          ui.getPanel().getChildListPanel().selectAllTableRow();
//			}

		}
		if(!iserrorhint)
			showHintMessage("�������");
	}
	
	/**
	 * 
	 * @���ߣ�lyf:��ѯ������ý�������
	 * @˵�������ɽ������Ŀ 
	 * @ʱ�䣺2011-11-10����11:57:53
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
			tmpHead.setDbilldate((UFDate)VOTool.max(vos, "dbilldate"));//Ӧȡ ��С��������
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
		//�����ѯ���ļƻ�  ����  ����
		getDataPane().setBodyDataVO(WdsWlPubTool.getParentVOFromAggBillVo(billvos, SoDeHeaderVo.class));
		getDataPane().execLoadFormula();
		getBodyDataPane().setBodyDataVO(billvos[0].getChildrenVO());
		getBodyDataPane().execLoadFormula();
		 setDataBuffer(billdatas);		
		showHintMessage("��ѯ���");
		ui.updateButtonStatus(WdsWlPubConst.DM_PLANDEAL_BTNTAG_DEAL,true);
	}
	private  String querStorepk=null;
	/**
	 * 
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ 
	 * ��� �� ���˼ƻ��Ĳ�ѯ����
	 * wds_sendplanin���˼ƻ�����
	 * wds_sendplanin_b ���˼ƻ��ӱ�
	 * @ʱ�䣺2011-3-25����09:47:50
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
			//���˵�������վ��ѯ����
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
		whereSql.append(" and h.fstatus in('"+BillStatus.AUDIT+"','"+BillStatus.FINISH+"') and isnull(h.dr,0)=0");//���ͨ����
		/**
		 * 
		 * bifreceiptfinish              CHAR(1)             �Ƿ񷢻�����   NULL                
           bifinventoryfinish            CHAR(1)             �Ƿ�������     
		 * 
		 * �� ������չ�ӱ��� ���ڱ������״̬   û�н��й��� ���������Ҫ  Ӧ��չ��  ���Ϸ��������Ŀ��� 
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
	 * @���ߣ�zhf
	 * @˵�������ɽ������Ŀ 
	 * ���˼ƻ�  ���Ű�ť������
	 * @ʱ�䣺2011-3-25����02:59:20
	 */
	public void onDeal() throws BusinessException{
		/**
		 * ����У��
		 * ���˵����ΰ�������Ϊ0����  
		 * ���ΰ����������ܴ��� �ƻ�����-�ۼư�������
		 * ����������ݴ����̨   ����ת��   ����    
		 * 
		 * �ֵ����򣺼ƻ���  ����վ �ջ�վ  ���   ��������   ��������
		 * 
		 */
//		AggregatedValueObject[] selectVos = ui.getPanel().getMultiSelectedVOs(SoDealBillVO.class.getName(), SoDeHeaderVo.class.getName(), SoDealVO.class.getName());
		WdsWlPubTool.stopEditing(getDataPane());
		WdsWlPubTool.stopEditing(getBodyDataPane());
		
		AggregatedValueObject[] newVos = getSelectVos();
		if(newVos == null || newVos.length == 0){
			showWarnMessage("δѡ������");
			return;
		}
		//��ͷ�ֿ� ���Ա༭ �����±���ֿ�
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
		//У��ֻ����ͬ�Ŀͻ����Ժϵ�
		CircularlyAccessibleValueObject[][]  splitVos = SplitBillVOs.getSplitVOs(WdsWlPubTool.getParentVOFromAggBillVo(newVos, SoDeHeaderVo.class), new String[]{"ccustomerid"});
		if(splitVos !=null && splitVos.length>1){
			showErrorMessage("ֻ�ܰ�����ͬ�Ŀͻ��ϵ�");
			return ;
		}
		List<SuperVO> ldata = WdsWlPubTool.filterVOsZeroNum(WdsWlPubTool.getBodysVOFromAggBillVo(newVos, SoDealVO.class),"nnum");
		if(ldata == null||ldata.size() == 0){
			showErrorMessage("ѡ������û�а���");
			return;
		}
		//����  ����ǰ   ����У��:��Ʒ�����ܱ����
		checkIsGiftSpilt((SoDealBillVO[])newVos);
		try{
			for(SuperVO vo:ldata){
				((SoDealVO)vo).validataOnDeal();
			}
			if(!valute(ldata)){
				ui.showErrorMessage("����������");
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
	 * У��������Ƿ���
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-27����10:40:44
	 * @param ldata
	 */
	private boolean valute(List<SuperVO> ldata) {
		if(ldata==null || ldata.size()==0){
			return true;
		}
		
		//liuys add ѭ����������vo,����ͬ����ı��尲���������,�������ڱȶԸô�����ִ���
		Map<String,UFDouble> map = new HashMap<String, UFDouble>();
		for(int i=0;i<ldata.size();i++){
			SuperVO vo=ldata.get(i);
			if(vo == null)
				continue;
			String cinventoryid = PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue("cinventoryid"));
			UFDouble nassnum = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("nassnum"));//��������
			if(map.containsKey(cinventoryid)){
				UFDouble value = PuPubVO.getUFDouble_NullAsZero(map.get(cinventoryid));
				map.put(cinventoryid, nassnum.add(value));
			}else{
				map.put(cinventoryid,nassnum);
			}
		}
		
		for(int i=0;i<ldata.size();i++){
			SuperVO vo=ldata.get(i);
			//������
			if(vo == null)
				continue;
			String cinventoryid = PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue("cinventoryid"));
			UFDouble uf1=map.get(cinventoryid);
			//������
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
	 * @���ߣ�У�飬��Ʒ���Ƿ񱻲��
	 * @˵�������ɽ������Ŀ 
	 * @ʱ�䣺2011-12-1����02:55:46
	 * @param dealBills
	 * @throws BusinessException 
	 */
	private void checkIsGiftSpilt(SoDealBillVO[] dealBills) throws BusinessException{		
		for(SoDealBillVO dealBill:dealBills){
			SoDealVO[] bodys = dealBill.getBodyVos();
			if(bodys == null || bodys.length ==0){
				throw  new BusinessException("���岻��Ϊ��");
			}
			//���������ݰ��ն����ֵ�
			CircularlyAccessibleValueObject[][] vos = SplitBillVOs.getSplitVOs(bodys, new String[]{"csaleid"});
			if(vos == null || vos.length ==0){
				return;
			}
			//�ж���Ʒ�Ƿ��
			for(int i=0;i<vos.length;i++){
				SoDealVO[] splitBodys =(SoDealVO[])vos[i];
				String csaleid = splitBodys[0].getCsaleid();
				if(csaleid == null || "".equalsIgnoreCase(csaleid)){
					continue;
				}
				int count =0;
				boolean fisgift = false;
				for(SoDealVO dealvo:getDataBuffer()){//�������е����ݱȽ�
					String csaleid2 = dealvo.getCsaleid();
					boolean  blargessflag = PuPubVO.getUFBoolean_NullAs(dealvo.getBlargessflag(), UFBoolean.FALSE).booleanValue();
					if(csaleid.equalsIgnoreCase(csaleid2)){
						count= count+1;
						if(blargessflag){
							fisgift = blargessflag;
						}
					}
				}
				if(fisgift && (splitBodys.length-count)<0){//�������Ʒ����������������ţ����ܲ𵥾�
					throw new BusinessException("��Ʒ�����������ţ����ܲ𵥰���");
				}
			}
		}

	}
	 /**
	 * ������������С��������
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-31����01:51:19
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
	//���˼ƻ�����1.�����ͷ��������2013.12.19
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
			showWarnMessage("δѡ������");
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

		//		Զ�̵���
		SoDealHealper.doCloseOrOpen(lhid.toArray(new String[0]), ui, bclose);

		//		ˢ�½�������
		onRefresh();

	}
	
	private void onClose()throws Exception{
		doCloseOrOpen(UFBoolean.TRUE);
	}
    private void onOpen()throws Exception{
    	doCloseOrOpen(UFBoolean.FALSE);
    }
}

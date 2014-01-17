package nc.ui.dm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.ic.pub.bill.AfterEditCtrl;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pub.bill.BillModel;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.wl.pub.LoginInforHelper;
import nc.ui.zmpub.pub.tool.SingleVOChangeDataUiTool;
import nc.uif.pub.exception.UifException;
import nc.vo.deal.dataset.PlanSetVO;
import nc.vo.dm.PlanDealVO;
import nc.vo.ic.pub.StockInvOnHandVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.wdsnew.pub.AvailNumBO;
import nc.vo.wdsnew.pub.BillStockBO1;
import nc.vo.wl.pub.WdsWlPubConst;
import nc.vo.wl.pub.WdsWlPubTool;
import nc.vo.zmpub.pub.tool.ZmPubTool;

public class PlanDealEventHandler {

	private PlanDealClientUI ui = null;
	private PlanDealQryDlg m_qrypanel = null;
	// ���ݻ���
	// private PlanDealVO[] m_billdatas = null;
	// private List<PlanDealVO> lseldata = new ArrayList<PlanDealVO>();
	private String whereSql = null;
	public PlanDealEventHandler(PlanDealClientUI parent) {
		super();
		ui = parent;
		// getDataPane().addSortRelaObjectListener2(this);
	}

	private AvailNumBO abo = null;
	
	public AvailNumBO getAbo() {

		if (abo == null) {
			abo = new AvailNumBO();
		}
		return abo;
	}

	private BillStockBO1 stock = null;

	public BillStockBO1 getStock() {
		if (stock == null) {
			stock = new BillStockBO1();
		}
		return stock;
	}

	private BillModel getDataPane() {
		return ui.getPanel().getBodyBillModel();
	}

	public void onButtonClicked(String btnTag) {
		if (btnTag.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_DEAL)) {
			onDeal();
		} else if (btnTag
				.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_SELNO)) {
			onNoSel();
		} else if (btnTag
				.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_SELALL)) {
			onAllSel();
		} else if (btnTag
				.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_QRY)) {
			onQuery();
		} else if (btnTag
				.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_XNDEAL)) {
			onXNDeal();
		}
	}

	// private PlanDealVO[] getDataBuffer() {
	// return m_billdatas;
	// }

	public void onNoSel() {
		int rowcount = getDataPane().getRowCount();
		if (rowcount <= 0)
			return;
		// for (int i = 0; i <
		// ui.getPanel().getChildListPanel().getTable().getRowCount(); i++) {
		// ui.getPanel().getParentListPanel().getTableModel().setRowState(i,
		// BillModel.UNSTATE);
		// ui.headRowChange(i);
		// BillModel model = ui.getPanel().getBodyBillModel();
		// IBillModelRowStateChangeEventListener l =
		// model.getRowStateChangeEventListener();
		// model.removeRowStateChangeEventListener();
		ui.getPanel().getChildListPanel().cancelSelectAllTableRow();
		// model.addRowStateChangeEventListener(l);
		ui.getPanel().updateUI();
		// }
	}

	// private void clearCache() {
	// lseldata.clear();
	// // tsInfor.clear();
	// }

	public void onAllSel() {
		// for (int i = 0; i <
		// ui.getPanel().getParentListPanel().getTable().getRowCount(); i++) {
		// ui.getPanel().getParentListPanel().getTableModel().setRowState(i,
		// BillModel.SELECTED);
		// ui.headRowChange(i);
		// BillModel model = ui.getPanel().getBodyBillModel();
		// IBillModelRowStateChangeEventListener l =
		// model.getRowStateChangeEventListener();
		// model.removeRowStateChangeEventListener();
		ui.getPanel().getChildListPanel().selectAllTableRow();
		// model.addRowStateChangeEventListener(l);
		ui.getPanel().updateUI();
		// }
	}

	private PlanDealQryDlg getQryDlg() {
		if (m_qrypanel == null) {
			m_qrypanel = new PlanDealQryDlg(ui, null, ui.cl.getCorp(),
					WdsWlPubConst.DM_PLAN_DEAL_NODECODE, ui.cl.getUser(), null);
			// m_qrypanel.hideUnitButton();
			// m_qrypanel.hideNormal();
			// m_qrypanel.setConditionEditable("h.pk_corp",true);
			// m_qrypanel.setValueRef("h.pk_corp", new UIRefPane("��˾Ŀ¼"));
			// m_qrypanel.changeValueRef("h.pk_corp", new UIRefPane("��˾Ŀ¼"));
		}
		return m_qrypanel;
	}

	private void clearData() {
		// m_billdatas = null;
		// lseldata.clear();
		getDataPane().clearBodyData();
		tsInfor.clear();
	}

	/**
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ ��ѯ������Ӧ����
	 * @ʱ�䣺2011-3-25����09:49:04
	 */
	public void onQuery() {
		/**
		 * ����ʲô�����ļƻ��أ���Ա�Ͳֿ��Ѿ����� ��½��ֻ�ܲ�ѯ����Ȩ�޲ֿ� �ֵܲ��˿��԰��ŷֲֵ� У���¼���Ƿ�Ϊ�ֿܲ����
		 * ����ǿ��԰����κβֿ�� ת�ֲ� �ƻ� ����Ƿֲֵ��� ֻ�� ���� ���ֲ��ڲ��� ���˼ƻ�
		 * 
		 */
		getQryDlg().showModal();
		if (!getQryDlg().isCloseOK())
			return;
		PlanDealVO[] billdatas = null;
		try {
			whereSql = getSQL();
			billdatas = PlanDealHealper.doQuery(whereSql);
		} catch (Exception e) {
			e.printStackTrace();
			showErrorMessage(WdsWlPubTool.getString_NullAsTrimZeroLen(e
					.getMessage()));
			return;
		}
		try {
			setStock(billdatas);
			setAvailNum(billdatas);
			setInNum(billdatas);
		} catch (Exception e) {
			e.printStackTrace();
			showErrorMessage("���ÿ����ʧ��");
			return;
		}
		/**
		 * ���û���
		 */
		String pk_corp =ClientEnvironment.getInstance().getCorporation().pk_corp;
		setDataToUI(billdatas);
		
	}

	private void setAvailNum(PlanDealVO[] billdatas) throws Exception {
		if (billdatas == null || billdatas.length == 0)
			return;
		String pk_ssdate=(String) ZmPubTool.execFomularClient("pk_ssdate->getColValue2(tb_stockstate,ss_pk,ss_state,ss_state,pk_corp,pk_corp)", 
				new String[]{"ss_state","pk_corp"},
                new String[]{"�ϸ�",ClientEnvironment.getInstance().getCorporation().getPrimaryKey()});
	
		for (int i = 0; i < billdatas.length; i++) {
			billdatas[i].setVdef1(pk_ssdate);
		}
		// �����ִ�����ѯ����
		StockInvOnHandVO[] vos = (StockInvOnHandVO[]) SingleVOChangeDataUiTool
				.runChangeVOAry(billdatas, StockInvOnHandVO.class,
						"nc.ui.wds.self.changedir.CHGWDS2TOACCOUNTNUM");
		if (vos == null || vos.length == 0)
			return;
		
		StockInvOnHandVO[] nvos = (StockInvOnHandVO[]) getAbo()
				.getAvailNumForClient(vos);
		
		if (nvos == null || nvos.length == 0)
			return;
		for (int i = 0; i < billdatas.length; i++) {
			if (nvos[i] != null) {
				UFDouble uf1 = nvos[i].getWhs_stocktonnage();// ����������
				UFDouble uf2 = nvos[i].getWhs_stockpieces();// ���ø�����
				billdatas[i].setNdrqarrstorenumout(uf1);
				billdatas[i].setNdrqstorenumout(uf2);
			}
		}
	}
	/**
	 * �޸���:����
	 * �޸�ԭ�򣺵õ��ջ��ֿ������
	 * �޸�ʱ�䣺2013/11/29
	 * @param billdatas
	 * @throws Exception 
	 */
	private void setInNum(PlanDealVO[] billdatas) throws Exception
	{
		for(int i=0;i<billdatas.length;i++)
		{
			String str1=billdatas[i].getPk_inwhouse();
			String str2=billdatas[i].getPk_outwhouse();
			billdatas[i].setAttributeValue("pk_outwhouse", str1);
			billdatas[i].setAttributeValue("pk_inwhouse", str2);
		}
		if (billdatas == null || billdatas.length == 0)
			return;
		String pk_ssdate=(String) ZmPubTool.execFomularClient("pk_ssdate->getColValue2(tb_stockstate,ss_pk,ss_state,ss_state,pk_corp,pk_corp)", 
				new String[]{"ss_state","pk_corp"},
                new String[]{"�ϸ�",ClientEnvironment.getInstance().getCorporation().getPrimaryKey()});
	
		for (int i = 0; i < billdatas.length; i++) {
			billdatas[i].setVdef1(pk_ssdate);
		}
		// �����ִ�����ѯ����
		StockInvOnHandVO[] vos = (StockInvOnHandVO[]) SingleVOChangeDataUiTool
				.runChangeVOAry(billdatas, StockInvOnHandVO.class,
						"nc.ui.wds.self.changedir.CHGWDS2TOACCOUNTNUM");
		if (vos == null || vos.length == 0)
			return;
		StockInvOnHandVO[] nvos = (StockInvOnHandVO[]) getAbo()
				.getAvailNumForClient(vos);
		if (nvos == null || nvos.length == 0)
			return;
		for (int i = 0; i < billdatas.length; i++) {
			if (nvos[i] != null) {
				UFDouble uf1 = nvos[i].getWhs_stocktonnage();// ����������
				UFDouble uf2 = nvos[i].getWhs_stockpieces();// ���ø�����
				billdatas[i].setNstorenumin(uf1);
				billdatas[i].setNdrqstorenumin(uf2);
			}
		}
		for(int i=0;i<billdatas.length;i++)
		{
			String str1=billdatas[i].getPk_inwhouse();
			String str2=billdatas[i].getPk_outwhouse();
			billdatas[i].setAttributeValue("pk_outwhouse", str1);
			billdatas[i].setAttributeValue("pk_inwhouse", str2);
		}
	}

	/**
	 * ���ÿ�� ��
	 * 
	 * @���ߣ�zhf
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-3����10:36:27
	 * @param billdatas
	 * @throws Exception
	 */
	private void setStock(PlanDealVO[] billdatas) throws Exception {
		if (billdatas == null || billdatas.length == 0)
			return;
		/**
		 * ע�⣺���ù�ʽ
		 */
		String pk_ssdate=(String) ZmPubTool.execFomularClient("pk_ssdate->getColValue2(tb_stockstate,ss_pk,ss_state,ss_state,pk_corp,pk_corp)", 
				new String[]{"ss_state","pk_corp"},
                new String[]{"�ϸ�",ClientEnvironment.getInstance().getCorporation().getPrimaryKey()});
	
		for (int i = 0; i < billdatas.length; i++) {
			billdatas[i].setVdef1(pk_ssdate);
		}
		// �����ִ�����ѯ����
		StockInvOnHandVO[] vos = (StockInvOnHandVO[]) SingleVOChangeDataUiTool
				.runChangeVOAry(billdatas, StockInvOnHandVO.class,
						"nc.ui.wds.self.changedir.CHGWDS2TOACCOUNTNUM");
		if (vos == null || vos.length == 0)
			return;
		// ����ִ���
		StockInvOnHandVO[] nvos = (StockInvOnHandVO[]) getStock()
				.queryStockCombinForClient(vos);
		
		if (nvos == null || nvos.length == 0)
			return;
		for (int i = 0; i < billdatas.length; i++) {
			if (nvos[i] != null) {
				UFDouble uf1 = nvos[i].getWhs_stocktonnage();// ���������
				billdatas[i].setNstorenumout(uf1);
			}
		}
		//���úϸ�״̬��     ָ���ڼ���ڵ�  �����
		setDateStock(billdatas,vos);
		
	}

   /**
    * ���úϸ�״̬��ָ���ڼ���ڵ�  �����
 * @param billdatas 
    * @���ߣ�mlr
    * @˵�������ɽ������Ŀ 
    * @ʱ�䣺2012-9-21����05:22:47
    * @param nvos
 * @throws Exception 
    */
	private void setDateStock(PlanDealVO[] billdatas, StockInvOnHandVO[] vos1) throws Exception {
		
		PlanSetVO[] vos = null;
		try {
			vos = (PlanSetVO[]) HYPubBO_Client.queryByCondition(PlanSetVO.class, 
					" pk_corp = '"+ui.getEviment().getCorporation().getPrimaryKey()+"'and isnull(dr,0)=0 ");
		} catch (UifException e) {
			e.printStackTrace();
		}
		if(vos==null || vos.length==0)
			ui.showErrorMessage("�����˼ƻ����� �ϸ�״̬�ڼ����� �ڵ� �� û������ ��ѯ�ڼ�");
		
		//���õ�һ���ڼ��
		
	    UFDate  logdate=   ui.getEviment().getDate();//�õ���ǰ��¼��ҵ������		
		Integer start1=vos[0].getStart1();
		UFDate st1=logdate.getDateBefore(start1);		
		Integer ends1=vos[0].getEnds1();
		UFDate ed1=logdate.getDateBefore(ends1);
	    //�����ѯ�ִ�����whereSql����
		String whereSql=" creadate >= '"+ed1.toString()+"' and creadate <='"+st1.toString()+"'";
		// ����ִ���
		StockInvOnHandVO[] nvos = (StockInvOnHandVO[]) getStock()
				.queryStockCombinForClient(vos1,whereSql);
		if (nvos == null || nvos.length == 0)
			return;
		for (int i = 0; i < billdatas.length; i++) {
			if (nvos[i] != null) {
				UFDouble uf1 = nvos[i].getWhs_stocktonnage();// ���������
			    billdatas[i].setNum1(uf1);
			    UFDouble  uf2=nvos[i].getWhs_stockpieces();//��渨����
			    billdatas[i].setBnum1(uf2);
			}
		}
		
		//���õڶ����ڼ��
		
		
		
		Integer start2=vos[0].getStart2();
		UFDate st2=logdate.getDateBefore(start2);		

		Integer ends2=vos[0].getEnds2();
		UFDate ed2=logdate.getDateBefore(ends2);
   
	    //�����ѯ�ִ�����whereSql����
		String whereSql1=" creadate >= '"+ed2.toString()+"' and creadate <='"+st2.toString()+"'";
		// ����ִ���
		StockInvOnHandVO[] nvos1 = (StockInvOnHandVO[]) getStock()
				.queryStockCombinForClient(vos1,whereSql1);
		if (nvos1 == null || nvos1.length == 0)
			return;
		for (int i = 0; i < billdatas.length; i++) {
			if (nvos[i] != null) {
				UFDouble uf1 = nvos1[i].getWhs_stocktonnage();// ���������
			    billdatas[i].setNum2(uf1);
			    UFDouble  uf2=nvos1[i].getWhs_stockpieces();//��渨����
			    billdatas[i].setBnum2(uf2);
			}
		}
		
		
		
	}

	Map<String, UFDateTime> tsInfor = new HashMap<String, UFDateTime>();

	/**
	 * 
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ :�������ݵ�����ͻ���
	 * @ʱ�䣺2011-11-14����07:41:04
	 * @param billdatas
	 */
	public void setDataToUI(PlanDealVO[] billdatas) {
		clearData();
		if (billdatas == null || billdatas.length == 0) {
			showHintMessage("��ѯ��ɣ�û����������������");
			return;
		}
		// ����ʱ���
		// Map<String, UFDateTime> tsInfor = new HashMap<String, UFDateTime>();
		for (PlanDealVO data : billdatas) {
			tsInfor.put(data.getPrimaryKey(), data.getTs());
		}
		// �����ѯ���ļƻ� ���� ����
		getDataPane().setBodyDataVO(billdatas);
		getDataPane().execLoadFormula();
		// billdatas = (PlanDealVO[]) getDataPane().getBodyValueVOs(
		// PlanDealVO.class.getName());
		// for(PlanDealVO data:billdatas){
		// data.setTs(tsInfor.get(data.getPrimaryKey()));
		// }
		// setDataBuffer(billdatas);
		showHintMessage("��ѯ���");
	}

	/**
	 * 
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ ��ˢ��
	 * @ʱ�䣺2011-11-14����07:32:22
	 */
	public void onRefresh() {
		clearData();
		PlanDealVO[] billdatas = null;
		try {
			billdatas = PlanDealHealper.doQuery(whereSql);
		} catch (Exception e) {
			e.printStackTrace();
			showErrorMessage(WdsWlPubTool.getString_NullAsTrimZeroLen(e
					.getMessage()));
			return;
		}
		setDataToUI(billdatas);
	}

	/**
	 * 
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ ��� �� ���˼ƻ��Ĳ�ѯ���� wds_sendplanin���˼ƻ����� wds_sendplanin_b ���˼ƻ��ӱ�
	 * @ʱ�䣺2011-3-25����09:47:50
	 * @return
	 * @throws Exception
	 */
	private String getSQL() throws Exception {
		StringBuffer whereSql = new StringBuffer();
		String where = getQryDlg().getWhereSQL();
		if (where != null && !"".equals(where)) {
			whereSql.append(where + " and");
		}
		whereSql.append("  nvl(wds_sendplanin.dr,0)=0");
		whereSql.append(" and nvl(wds_sendplanin_b.dr,0)=0 ");
		whereSql
				.append(" and wds_sendplanin.pk_corp='" + ui.cl.getCorp() + "'");
		whereSql.append(" and wds_sendplanin.vbillstatus=1 ");
		whereSql.append(" and wds_sendplanin.iplantype=0 ");
		whereSql
				.append(" and (coalesce(wds_sendplanin_b.nplannum,0) -  coalesce(wds_sendplanin_b.ndealnum,0)) > 0");
		// zhf ׷�� ֧�ֹ��˹ر� �ƻ�
		whereSql
				.append(" and coalesce(wds_sendplanin_b.reserve14,'N')='N' and coalesce(wds_sendplanin.reserve14,'N') = 'N' ");
		String cwhid = getLoginInforHelper().getLogInfor(
				ui.m_ce.getUser().getPrimaryKey()).getWhid();
		if (!WdsWlPubTool.isZc(cwhid)) {
			whereSql.append(" and wds_sendplanin.pk_outwhouse = '" + cwhid
					+ "' ");
		}
		return whereSql.toString();
	}

	private LoginInforHelper helper = null;

	public LoginInforHelper getLoginInforHelper() {
		if (helper == null) {
			helper = new LoginInforHelper();
		}
		return helper;
	}

	// private void setDataBuffer(PlanDealVO[] billdatas) {
	// this.m_billdatas = billdatas;
	// }

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ���˼ƻ� ���ⰲ�Ű�ť������
	 * @ʱ�䣺2011-3-25����02:59:20
	 */
	public void onXNDeal() {
		// if (lseldata == null || lseldata.size() == 0) {
		// showWarnMessage("��ѡ��Ҫ���������");
		// return;
		// }
		// XnApDLG tdpDlg = new XnApDLG(WdsWlPubConst.XNAP,
		// ui.getEviment().getUser()
		// .getPrimaryKey(), ui.getEviment().getCorporation()
		// .getPk_corp(), ui, lseldata);
		// if(tdpDlg.showModal()== UIDialog.ID_OK){}
		// nc.ui.pub.print.IDataSource dataSource = new DealDataSource(
		// ui.getBillListPanel(), WdsWlPubConst.DM_PLAN_DEAL_NODECODE);
		// nc.ui.pub.print.PrintEntry print = new
		// nc.ui.pub.print.PrintEntry(null,
		// dataSource);
		// print.setTemplateID(ui.getEviment().getCorporation().getPk_corp(),WdsWlPubConst.DM_PLAN_DEAL_NODECODE,ui.getEviment().getUser().getPrimaryKey(),
		// null, null);
		// if (print.selectTemplate() == 1)
		// print.preview();

	}

	/**
	 * 
	 * @���ߣ�zhf
	 * @˵�������ɽ������Ŀ ���˼ƻ� ���Ű�ť������
	 * @ʱ�䣺2011-3-25����02:59:20
	 */
	public void onDeal() {
		// ���� ����ǰ ����У��
		/**
		 * ����У�� �����ֿⲻ��Ϊ�� ����ֿⲻ��Ϊ�� �����ֿⲻ����ͬ ���˵����ΰ�������Ϊ0���� ���ΰ����������ܴ��� �ƻ�����-�ۼư�������
		 * ����������ݴ����̨ ����ת�� ����
		 * 
		 * �ֵ����򣺼ƻ��� ����վ �ջ�վ ��� �������� ��������
		 * 
		 */
		WdsWlPubTool.stopEditing(getDataPane());
		if (getDataPane().getRowCount() <= 0) {
			showWarnMessage("��ѡ��Ҫ���������");
			return;
		}

		int[] rows = ui.getPanel().getBodyTable().getSelectedRows();
		if (rows == null || rows.length == 0) {
			showErrorMessage("��ѡ��Ҫ���������");
			return;
		}

		PlanDealVO[] vos = (PlanDealVO[]) getDataPane().getBodySelectedVOs(
				PlanDealVO.class.getName());
		if (vos == null || vos.length == 0) {
			showErrorMessage("���ݻ�ȡʧ��,�����²���");
			return;
		}

		List<SuperVO> ldata = WdsWlPubTool.filterVOsZeroNum(Arrays.asList(vos),
				"nnum");
		if (ldata == null || ldata.size() == 0) {
			showErrorMessage("ѡ������û�а���");
			return;
		}
		try {
			PlanDealVO tmp = null;
			for (SuperVO vo : ldata) {
				tmp = (PlanDealVO) vo;
				tmp.validataOnDeal();
				tmp.setTs(tsInfor.get(tmp.getPrimaryKey()));
			}
			if(!valute(ldata)){
				ui.showErrorMessage("����������");
				return;
			}
			PlanDealHealper.doDeal(ldata, ui);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof ValidationException) {
				showErrorMessage(e.getMessage());
				return;
			}
			showErrorMessage(WdsWlPubTool.getString_NullAsTrimZeroLen(e
					.getMessage()));
			return;
		}
		ui.showHintMessage("�����Ѿ����...");
		onRefresh();
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
		for(int i=0;i<ldata.size();i++){
			SuperVO vo=ldata.get(i);
			//������
			UFDouble uf1=PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("nassnum"));
			//������
			UFDouble uf2=PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("ndrqstorenumout"));
			if((uf2.sub(uf1)).doubleValue()<0){
				return false;
			}else{
				return true;
			}
		}
		return true;
	}

	// public void afterEdit(BillEditEvent e) {
	// // TODO Auto-generated method stub
	// int row = e.getRow();
	// String key = e.getKey();
	// if (row < 0)
	// return;
	// if (key.equalsIgnoreCase("bsel")) {
	// UFBoolean bsel = PuPubVO.getUFBoolean_NullAs(getDataPane()
	// .getValueAt(row, "bsel"), UFBoolean.FALSE);
	// if (bsel.booleanValue()) {
	// lseldata.add(getDataBuffer()[row]);
	// } else {
	// lseldata.remove(getDataBuffer()[row]);
	// }
	// } else if ("nnum".equalsIgnoreCase(key)) {
	// UFDouble hsl = PuPubVO.getUFDouble_NullAsZero(getDataBuffer()[row]
	// .getHsl());
	// UFDouble num = e.getValue() == null ? new UFDouble(0)
	// : new UFDouble(e.getValue().toString());
	// if(hsl != null && hsl.doubleValue()>0){
	// getDataBuffer()[row].setNassnum(num.div(hsl));
	// ui.getPanel().getHeadBillModel().setValueAt(num.div(hsl), row,
	// "nassnum");
	// }
	// } else if ("nassnum".equalsIgnoreCase(key)) {
	// UFDouble assnum = e.getValue() == null ? new UFDouble(0)
	// : new UFDouble(e.getValue().toString());
	// UFDouble hsl = PuPubVO.getUFDouble_NullAsZero(getDataBuffer()[row]
	// .getHsl());
	// getDataBuffer()[row].setNnum(hsl.multiply(assnum));
	// getDataBuffer()[row].setNassnum(assnum);
	// ui.getPanel().getHeadBillModel().setValueAt(hsl.multiply(assnum), row,
	// "nnum");
	// }
	//
	// }

	private void showErrorMessage(String msg) {
		ui.showErrorMessage(msg);
	}

	private void showWarnMessage(String msg) {
		ui.showWarningMessage(msg);
	}

	private void showHintMessage(String msg) {
		ui.showHintMessage(msg);
	}

	// public Object[] getRelaSortObjectArray() {
	// // TODO Auto-generated method stub
	// return getDataBuffer();
	// }
	//
	// public void bodyRowChange(BillEditEvent e) {
	// // TODO Auto-generated method stub
	//
	// }

}

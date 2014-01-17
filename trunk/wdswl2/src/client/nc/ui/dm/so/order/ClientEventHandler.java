package nc.ui.dm.so.order;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UITable;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.wl.pub.BeforeSaveValudate;
import nc.ui.wl.pub.LoginInforHelper;
import nc.ui.wl.pub.WdsPubEnventHandler;
import nc.vo.dm.so.order.SoorderBVO;
import nc.vo.dm.so.order.SoorderVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.wl.pub.ButtonCommon;
import nc.vo.wl.pub.WdsWlPubConst;
import nc.vo.wl.pub.WdsWlPubTool;

public class ClientEventHandler extends WdsPubEnventHandler {

	public ClientUIQueryDlg queryDialog = null;
	private LoginInforHelper helper = null;
//	nc.ui.zmpub.pub.bill.FlowManageEventHandler lt=null;
//	public nc.ui.zmpub.pub.bill.FlowManageEventHandler getETH(){
//		if(lt==null){
//			lt=new nc.ui.zmpub.pub.bill.FlowManageEventHandler(this.getBillManageUI(),this.getUIController());
//		}
//		return lt;
//	}
	public LoginInforHelper getLoginInforHelper() {
		if (helper == null) {
			helper = new LoginInforHelper();
		}
		return helper;
	}
	/**
	 * ��ѯ֧����ԴȨ�޹���
	 * for add mlr
	 */
//	protected void onBoQuery() throws Exception {
//		getETH().onBoQuery("pk_invbasdoc", "pk_invmandoc");
//	}
	public ClientEventHandler(ClientUI clientUI, IControllerBase control) {
		super(clientUI, control);
	}

	protected UIDialog createQueryUI() {
		return new ClientUIQueryDlg(getBillUI(), null, _getCorp()
				.getPrimaryKey(), getBillUI()._getModuleCode(), _getOperator(),
				getBillUI().getBusinessType(), getBillUI().getNodeKey());
	}

	@Override
	protected String getHeadCondition() {
		StringBuffer strWhere = new StringBuffer();
		boolean isStock = true;
		String pk_stordoc = WdsWlPubConst.WDS_WL_ZC;
		strWhere.append(" wds_soorder.pk_corp = '"
				+ _getCorp().getPrimaryKey() + "' ");
		try {
			pk_stordoc = getLoginInforHelper().getCwhid(_getOperator());
			isStock = WdsWlPubTool.isZc(pk_stordoc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// �Ƿ����ܲ�
		if (!isStock) {
			strWhere.append(" and wds_soorder.pk_outwhouse='" + pk_stordoc
					+ "'");
		}
		return strWhere.toString();

	}
/**
 * �޸��ˣ�����
 */
	@Override
	public void onButton(ButtonObject bo) {

		// ����� ���������޸ĵĴ���
		Object o = getBillCardPanelWrapper().getBillCardPanel().getHeadItem(
				"fisended").getValueObject();
		ButtonObject parentBtn = bo.getParent();
		if (parentBtn != null) {
			int intParentBtn = Integer.parseInt(parentBtn.getTag());
			if (IBillButton.Action == intParentBtn) {
				if (o != null && ((String) o).equalsIgnoreCase("true")) {
					getBillUI().showErrorMessage("�õ����Ѷ��᲻�������");
					return;

				}
			}

		} else {
			Integer intbtn = Integer.valueOf(bo.getTag());
			if (IBillButton.Edit == intbtn || IBillButton.Del == intbtn
					|| IBillButton.Delete == intbtn

					|| ButtonCommon.TRAN_COL == intbtn) {
				if (o != null && ((String) o).equalsIgnoreCase("true")) {
					getBillUI().showErrorMessage("�õ����Ѷ��᲻�������");
					return;

				}
			}

		}
		super.onButton(bo);
	}

	@Override
	protected void onBoSave() throws Exception {
		beforeSaveCheck();
		super.onBoSave();
	}

	/**
	 * 
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ ���屣��ǰУ��
	 * @ʱ�䣺2011-3-23����09:05:20
	 * @throws Exception
	 */
	protected void beforeSaveCheck() throws Exception {
		// ����ǿ�У��
		if (getBillUI().getVOFromUI() != null) {
			CircularlyAccessibleValueObject[] bodys = getBillUI().getVOFromUI()
					.getChildrenVO();
			if (bodys == null || bodys.length == 0) {
				throw new BusinessException("���岻����Ϊ��");
			} else {
				for (CircularlyAccessibleValueObject body : bodys) {
					((SoorderBVO) body).validate();
				}
			}
		}
		;
	}

	@Override
	protected void onBoLineAdd() throws Exception {
		// TODO Auto-generated method stub
		super.onBoLineAdd();
	}

	@Override
	protected void onBoElse(int intBtn) throws Exception {
		if (intBtn == ButtonCommon.TRAN_COL) {
			onBoCol();
		}
		if (intBtn == ButtonCommon.LOCK) {
			onBoLock();
		}
		if (intBtn == ButtonCommon.UNLOCK) {
			onBoUnlock();
		}
		if(intBtn == ButtonCommon.btnopen){
			onBoOpen();
		}
		if(intBtn == ButtonCommon.btnclose){
			onBoClose();
		}
		super.onBoElse(intBtn);
	}
	
	public void onBoClose() throws Exception {
		AggregatedValueObject billVo = getBufferData().getCurrentVO();
		if(billVo == null){
			getBillUI().showWarningMessage("��ѡ��Ҫ����������");
		}
		SuperVO head = (SuperVO) billVo.getParentVO();
		UFBoolean fisclose = PuPubVO.getUFBoolean_NullAs(head.getAttributeValue(WdsWlPubConst.sendorder_close), UFBoolean.FALSE);
		if(fisclose == UFBoolean.TRUE ){
			return ;
		}
		head.setAttributeValue(WdsWlPubConst.sendorder_close, UFBoolean.TRUE);	
		HYPubBO_Client.update(head);
		onBoRefresh();
		getBillUI().showHintMessage("�����ѹر�");
	}

	public void onBoOpen() throws Exception  {
		AggregatedValueObject billVo = getBufferData().getCurrentVO();
		if(billVo == null){
			getBillUI().showWarningMessage("��ѡ��Ҫ����������");
		}
		SuperVO head = (SuperVO) billVo.getParentVO();
		UFBoolean fisclose = PuPubVO.getUFBoolean_NullAs(head.getAttributeValue(WdsWlPubConst.sendorder_close), UFBoolean.FALSE);
		if(fisclose == UFBoolean.FALSE ){
			return ;
		}
		head.setAttributeValue(WdsWlPubConst.sendorder_close, UFBoolean.FALSE);	
		HYPubBO_Client.update(head);
		onBoRefresh();
		getBillUI().showHintMessage("�����Ѵ�");
	}

	@Override
	public void onBoAudit() throws Exception {
		if (getBufferData().getCurrentVO() == null) {
			getBillUI().showWarningMessage("����ѡ��һ������");
			return;
		}
		SoorderVO head = (SoorderVO) getBufferData().getCurrentVO()
				.getParentVO();
		UFBoolean fisended = PuPubVO.getUFBoolean_NullAs(head.getFisended(),
				UFBoolean.FALSE);
		if (fisended == UFBoolean.FALSE) {
			getBillUI().showWarningMessage("������δ����");
			return;
		}

		super.onBoAudit();
	}

	@Override
	public void onBoCancelAudit() throws Exception {
		if (getBufferData().getCurrentVO() == null) {
			getBillUI().showWarningMessage("����ѡ��һ������");
			return;
		}
		//add by yf 201207-26 �ѹرն�����������
		AggregatedValueObject billVo = getBufferData().getCurrentVO();
		SuperVO head = (SuperVO) billVo.getParentVO();
		UFBoolean fisclose = PuPubVO.getUFBoolean_NullAs(head.getAttributeValue(WdsWlPubConst.sendorder_close), UFBoolean.FALSE);
		if(fisclose.booleanValue() ){
			getBillUI().showWarningMessage("�����ѹرգ���������");
			return ;
		}
		super.onBoCancelAudit();
	}

	/**
	 * liuys add ֧��������ӡ
	 */
	@Override
	protected void onBoPrint() throws Exception {
		// ������б���棬ʹ��ListPanelPRTS����Դ
		if (getBillManageUI().isListPanelSelected()) {
			nc.ui.pub.print.IDataSource dataSource = new MyListDateSource(
					getBillUI()._getModuleCode(), ((BillManageUI) getBillUI())
							.getBillListPanel());
			int[] rows = getBillManageUI().getBillListPanel().getHeadTable()
					.getSelectedRows();
			if(rows == null || rows.length <=0){
				return ;
			}
			int rowstart = getBillManageUI().getBillListPanel().getHeadTable()
					.getSelectedRow();
			nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(
					null, dataSource);
			print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(),
					getBillUI()._getModuleCode(), getBillUI()._getOperator(),
					getBillUI().getBusinessType(), getBillUI().getNodeKey());
			UITable bt = getBillManageUI().getBillListPanel().getHeadTable();
			int len = rows.length;
			if (print.selectTemplate() == 1)
				for (int i = 0; i < len; i++) {
					bt.getSelectionModel().setSelectionInterval(rowstart + i,
							rowstart + i);
					print.print(true, false);
					Integer iprintcount = PuPubVO.getInteger_NullAs(
							getBufferData().getCurrentVO().getParentVO()
									.getAttributeValue("iprintcount"), 0);
					iprintcount = iprintcount + 1;
					getBufferData().getCurrentVO().getParentVO()
							.setAttributeValue("iprintcount", iprintcount);
					HYPubBO_Client.update((SuperVO) getBufferData()
							.getCurrentVO().getParentVO());
					onBoRefresh();
				}

		} else {
			// ��������Դ��֧��ͼƬ
			nc.ui.pub.print.IDataSource dataSource = new MyCardDateSource(
					getBillUI()._getModuleCode(), getBillCardPanelWrapper()
							.getBillCardPanel());
			nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(
					null, dataSource);
			print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(),
					getBillUI()._getModuleCode(), getBillUI()._getOperator(),
					getBillUI().getBusinessType(), getBillUI().getNodeKey());
			if (print.selectTemplate() == 1)
				print.preview();
			// ��������Դ��֧��ͼƬ
			Integer iprintcount = PuPubVO.getInteger_NullAs(getBufferData()
					.getCurrentVO().getParentVO().getAttributeValue(
							"iprintcount"), 0);
			iprintcount = iprintcount + 1;
			getBufferData().getCurrentVO().getParentVO().setAttributeValue(
					"iprintcount", iprintcount);
			HYPubBO_Client.update((SuperVO) getBufferData().getCurrentVO()
					.getParentVO());
			onBoRefresh();
		}
	}

	/**
	 * 
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ ����
	 * @ʱ�䣺2011-6-10����10:05:26
	 * @throws Exception
	 */
	private void onBoLock() throws Exception {
		AggregatedValueObject billVo = getBufferData().getCurrentVO();
		if (billVo == null) {
			getBillUI().showWarningMessage("��ѡ��Ҫ����������");
		}
		BeforeSaveValudate.checkNotAllNull(billVo, "noutnum", "ʵ������");
		SoorderVO head = (SoorderVO) billVo.getParentVO();
		UFBoolean fisended = PuPubVO.getUFBoolean_NullAs(head.getFisended(),
				UFBoolean.FALSE);
		if (fisended.booleanValue()) {
			return;
		}
		head.setFisended(UFBoolean.TRUE);
		head.setItransstatus(new Integer(1));
		HYPubBO_Client.update(head);
		onBoRefresh();
	}

	/**
	 * 
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ �ⶳ
	 * @ʱ�䣺2011-6-10����10:05:39
	 * @throws Exception
	 */
	private void onBoUnlock() throws Exception {
		AggregatedValueObject billVo = getBufferData().getCurrentVO();
		if (billVo == null) {
			getBillUI().showWarningMessage("��ѡ��Ҫ����������");
		}
		SoorderVO head = (SoorderVO) billVo.getParentVO();
		UFBoolean fisended = PuPubVO.getUFBoolean_NullAs(head.getFisended(),
				UFBoolean.FALSE);
		if (!fisended.booleanValue()) {
			return;
		}
		head.setFisended(UFBoolean.FALSE);
		HYPubBO_Client.update(head);
		onBoRefresh();
	}

	/**
	 * �˷Ѽ���
	 * @throws Exception 
	 */
	public void onBoCol() throws Exception {
		if (getBufferData().isVOBufferEmpty()) {
			getBillUI().showHintMessage("������");
			return;
		}
		AggregatedValueObject billvo = getBufferData().getCurrentVO();
		if (billvo == null)
			return;
		if (billvo.getChildrenVO() == null
				|| billvo.getChildrenVO().length == 0) {
			getBillUI().showHintMessage("��������Ϊ��");
			return;
		}
		UFDouble noutnum = PuPubVO.getUFDouble_NullAsZero(null);
		for(CircularlyAccessibleValueObject body:billvo.getChildrenVO()){
			UFDouble noutnum1 = PuPubVO.getUFDouble_NullAsZero(body.getAttributeValue("noutnum"));
			noutnum  = noutnum.add(noutnum1);
		}
		if(noutnum.doubleValue() ==0){
			getBillUI().showWarningMessage("�޳�������");
			return;
		}
		SoorderVO head = (SoorderVO) getBufferData().getCurrentVO()
				.getParentVO();
		if (PuPubVO.getInteger_NullAs(head.getIcoltype(), 0) == 3)// �ֶ������˷�
			return;
		try {
			billvo = TranColHelper.col(getBillUI(), billvo, _getDate(),
					_getOperator());
			if (getBillManageUI().isListPanelSelected()) {
				onBoReturn();
			}

		} catch (Exception e) {
			e.printStackTrace();
			getBillUI().showErrorMessage(
					WdsWlPubTool.getString_NullAsTrimZeroLen(e.getMessage()));
			return;
		}

//		getBillCardPanelWrapper().getBillCardPanel().getBillData()
//				.setBillValueVO(billvo);
//		getBillCardPanelWrapper().getBillCardPanel().getBillModel()
//				.execLoadFormula();
//		getBufferData().setCurrentVO(billvo);
		onBoRefresh();
	}

}
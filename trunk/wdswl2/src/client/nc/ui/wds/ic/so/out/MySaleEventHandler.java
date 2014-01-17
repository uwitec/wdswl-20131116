package nc.ui.wds.ic.so.out;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.trade.bill.BillListPanelWrapper;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.wds.ic.pub.OutPubClientUI;
import nc.ui.wds.ic.pub.OutPubEventHandler;
import nc.ui.wds.w8004040204.ssButtun.ISsButtun;
import nc.ui.wl.pub.LoginInforHelper;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.uif.pub.exception.UifException;
import nc.vo.dm.so.order.SoorderVO;
import nc.vo.ic.other.out.TbOutgeneralBVO;
import nc.vo.ic.other.out.TbOutgeneralHVO;
import nc.vo.ic.pub.ScaleKey;
import nc.vo.ic.pub.ScaleValue;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.wl.pub.WdsWlPubConst;

/**
 * 0 ����Ա 1 ��Ϣ�� 2 ���˿� 3���� �û�����0---------���Խ��г��� �û�����1---------Ϊ����ǩ�ֵ��û�
 * �û�����2---------�޸Ĳ��� �û�����3---------����Ȩ��
 */
public class MySaleEventHandler extends OutPubEventHandler {
	private LoginInforHelper login = null;
	protected nc.ui.pub.print.PrintEntry m_print = null;
	protected ScaleValue m_ScaleValue = new ScaleValue();
	protected ScaleKey m_ScaleKey = new ScaleKey();

	public MySaleEventHandler(OutPubClientUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	@Override
	protected UIDialog createQueryUI() {
		return new MyQueryDIG(getBillUI(), null, _getCorp().getPk_corp(),
				getBillUI().getModuleCode(), _getOperator(), null);
	}

	@Override
	protected String getHeadCondition() {
		return " pk_corp = '" + _getCorp().getPrimaryKey()
				+ "' and isnull(dr,0) = 0 and vbilltype = '"
				+ WdsWlPubConst.BILLTYPE_SALE_OUT + "' ";
	}

	private LoginInforHelper getLoginInfoHelper() {
		if (login == null) {
			login = new LoginInforHelper();
		}
		return login;
	}

	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn);
		switch (intBtn) {
		case ISsButtun.tpzd:// ����ָ��(�ֶ����)
			valudateWhereYeqian();
			// liuys ��ע�͵�У�� //��� ���ΨһУ��
			// BeforeSaveValudate.beforeSaveBodyUnique(getBillCardPanelWrapper().getBillCardPanel().getBillTable(),
			// getBillCardPanelWrapper().getBillCardPanel().getBillModel(),
			// new String[]{"ccunhuobianma","batchcode"},
			// new String[]{"�������","���κ�"});
			// int ii = ontpzd();
			// if(ii != UIDialog.ID_CANCEL)
			// onBatchCodeChange();
			break;
		case ISsButtun.zdqh:// �Զ�ȡ��
			valudateWhereYeqian();
			// ��� ���ΨһУ��
			// BeforeSaveValudate.beforeSaveBodyUnique(getBillCardPanelWrapper().getBillCardPanel().getBillTable(),
			// getBillCardPanelWrapper().getBillCardPanel().getBillModel(),
			// new String[]{"ccunhuobianma","batchcode"},
			// new String[]{"�������","���κ�"});
			onzdqh();
			// onBatchCodeChange();
			break;
		case ISsButtun.ckmx:// �鿴��ϸ
			onckmx();
			break;
		case ISsButtun.Qxqz:// ȡ��ǩ��
			onQxqz();
			break;
		case ISsButtun.Qzqr:// ǩ��ȷ��
			onQzqr();
			break;
		case nc.ui.wds.w80020206.buttun0206.ISsButtun.RefSoOrder:// ���������˵�
			((MyClientUI) getBillUI()).setRefBillType(WdsWlPubConst.WDS5);
			onBillRef();

			break;
		case nc.ui.wds.w80020206.buttun0206.ISsButtun.RefRedSoOrder:// ���������˵�
			((MyClientUI) getBillUI()).setRefBillType("30");
			onBillRef();
			break;
		}
	}

	/**
	 * �����Ӧ��ҳǩ �����ڳ����ӱ�ҳǩ
	 * 
	 * @���ߣ�zhf
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2011-9-22����02:56:45
	 */
	private void valudateWhereYeqian() throws Exception {
		String tablecode = getBillCardPanelWrapper().getBillCardPanel()
				.getBodyPanel().getTableCode();
		if (!"tb_outgeneral_b".equalsIgnoreCase(tablecode)) {
			throw new Exception("��ѡ�������ҳǩ");
		}
	}

	/**
	 * ���պ�����Ĭ��ֵ
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2011-9-22����04:10:24
	 */
	@Override
	protected void setRefData(AggregatedValueObject[] vos) throws Exception {
		super.setRefData(vos);
		setInitByWhid(new String[] { "srl_pk", "pk_cargdoc" });
		// ��� ���յĳ���ֿ�Ϊ�� ����Ĭ�ϲֿ�Ϊ��ǰ����Ա�ֿ�
		setInitWarehouse("srl_pk");
		setPk_cargdoc();// ���û�λ
	}

	/**
	 * ���û�λ��Ϣ
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2011-9-22����02:17:50
	 */
	private void setPk_cargdoc() throws Exception {
		String pk_cargdoc = PuPubVO
				.getString_TrimZeroLenAsNull(getBillCardPanelWrapper()
						.getBillCardPanel().getHeadItem("pk_cargdoc")
						.getValueObject());
		if (pk_cargdoc == null || pk_cargdoc.length() == 0) {
			throw new Exception("��ѡ���ͷ��λ");
		}
		int count = getBillCardPanelWrapper().getBillCardPanel().getRowCount();
		if (count == 0) {
			return;
		}
		for (int i = 0; i < count; i++) {
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
					pk_cargdoc, i, "cspaceid");
		}
	}

	@Override
	public void onBillRef() throws Exception {
		super.onBillRef();
		// �����Ա��ֵ
		getBillCardPanelWrapper().getBillCardPanel()
				.getHeadItem("cwhsmanagerid").setValue(_getOperator());
		// ������ҵ�����ڸ�ֵ
		MyClientUI ui = (MyClientUI) getBillUI();
		// int
		// rowCounts=getBillCardPanelWrapper().getBillCardPanel().getBillTable(ui.getTableCodes()[0]).getRowCount();
		int rowCounts = getBillCardPanelWrapper().getBillCardPanel()
				.getBillTable(ui.getTableCodes()[0]).getRowCount();
		for (int i = 0; i < rowCounts; i++) {
			getBillCardPanelWrapper().getBillCardPanel()
					.getBillModel(ui.getTableCodes()[0])
					.setValueAt(_getDate(), i, "dbizdate");
			getBillCardPanelWrapper().getBillCardPanel()
					.getBillModel(ui.getTableCodes()[0])
					.setValueAt(getPk_cargDoc(), i, "cspaceid");
		}
		getBillUI().updateUI();
	}

	@Override
	protected void onBoSave() throws Exception {
		// ����ǩ���� С�� ʵ��������У��
		if (getBillUI().getVOFromUI().getChildrenVO() != null) {
			TbOutgeneralBVO[] tbs = (TbOutgeneralBVO[]) getBillUI()
					.getVOFromUI().getChildrenVO();
			for (int i = 0; i < tbs.length; i++) {
				UFDouble u1 = PuPubVO.getUFDouble_NullAsZero(tbs[i]
						.getNoutassistnum());
				UFDouble u2 = PuPubVO.getUFDouble_NullAsZero(tbs[i]
						.getNtagnum());
				if (u1.sub(u2).doubleValue() < 0) {
					throw new BusinessException("��ǩ����   ���ܴ���ʵ������");
				}
			}
			//2014-01-07 ���� ����ʱ���ӱȶ��˵��� ��д����begin
			if (tbs[0].getGeneral_b_pk() == null) {//�жϱ����Ƿ���pk�����û�д�������������
				LongTimeTask lTask = new LongTimeTask();
				int size = tbs.length;
				String[] pks = new String[size];
				for (int i = 0; i < tbs.length; i++) {
					// ȡ���ϲ���ԴID
					pks[i] = tbs[i].getCsourcebillbid();//ȡ�ñ��������յ���Դpk��wds_soorder_b���˵�����ȥ��ѯ��Ӧ������pk
				}
				Map<String, List<TbOutgeneralBVO>> returndata = (Map<String, List<TbOutgeneralBVO>>) lTask
						.callRemoteService("wds", "nc.bo.other.out.CheckNumDMO",
								"doQuery", new Class[] { String[].class },
								new Object[] { pks }, 2);
				if (returndata != null) {
					for (int i = 0; i < returndata.size(); i++) {
						TbOutgeneralBVO item = tbs[i];
						String key = item.getCsourcebillbid();
						List<TbOutgeneralBVO> l = returndata.get(key);
						UFDouble djsl = item.getNoutassistnum();
						UFDouble kzsl = l.get(0).getNshouldoutnum();
						if (djsl.compareTo(kzsl) > 0) {
							throw new Exception(l.get(0).getCinvbasid()
									+ "���ο�������Ϊ" + kzsl + "����¼������Ϊ" + djsl);
						}
					}
				}
			}
		}
		//2014-01-07 ���� ���ӱȶ��˵��� ��д����end
		BillItem[] bte2 = getBillCardPanelWrapper().getBillCardPanel()
				.getBillModel().getBodyItems();
		if (bte2 == null || bte2.length == 0) {

		}
		super.onBoSave();
	}

	// ȡ��ǩ��
	protected void onQxqz() throws Exception {
		try {
			if (getBufferData().getCurrentVO() != null) {
				getBillManageUI().showHintMessage("����ִ��ȡ��ǩ��...");
				int retu = getBillManageUI().showOkCancelMessage(
						"ȡ��ǩ�ֻ�ɾ������װж�Ѻ��㵥���Ƿ�ȷ��ȡ��ǩ��?");
				if (retu != UIDialog.ID_OK) {
					return;
				}
				AggregatedValueObject aObject = getBufferData()
						.getCurrentVOClone();
				TbOutgeneralHVO generalh = (TbOutgeneralHVO) aObject
						.getParentVO();
				generalh.setVbillstatus(IBillStatus.FREE);// ����״̬
				generalh.setCregister(null);// ǩ��������
				generalh.setTaccounttime(null);// ǩ��ʱ��
				generalh.setQianzidate(null);// ǩ������
				ArrayList<String> list = new ArrayList<String>();
				list.add(_getDate().toString());
				list.add(_getOperator());// ��¼��
				// �����ű�
				PfUtilClient.processAction(getBillManageUI(), "CANELSIGN",
						getBillManageUI().getUIControl().getBillType(),
						_getDate().toString(), aObject, list);
				// ���»���
				onBoRefresh();
			}
		} catch (Exception e) {
			Logger.error(e);
			getBillManageUI().showErrorMessage("ȡ��ǩ��ʧ��:" + e.getMessage());
		}
	}

	/**
	 * �����б�����
	 */
	protected void updateListVo(int[] rows) throws java.lang.Exception {
		CircularlyAccessibleValueObject vo = null;
		if (rows != null && rows.length >= 0) {
			for (int i : rows) {
				vo = getBufferData().getVOByRowNo(i).getParentVO();
				getBillListPanelWrapper().updateListVo(vo, i);
			}
		}
	}

	protected BillListPanelWrapper getBillListPanelWrapper() {
		return getBillManageUI().getBillListWrapper();
	}

	// ǩ��ȷ��
	protected void onQzqr() throws Exception {
		try {
			if (getBufferData().getCurrentVO() != null) {
				getBillManageUI().showHintMessage("����ִ��ǩ��...");
				int retu = getBillManageUI().showOkCancelMessage("ȷ��ǩ��?");
				if (retu != 1) {
					return;
				}
				AggregatedValueObject aObject = getBufferData()
						.getCurrentVOClone();
				TbOutgeneralHVO generalh = (TbOutgeneralHVO) aObject
						.getParentVO();
				generalh.setVbillstatus(IBillStatus.CHECKPASS);// ����״̬
				generalh.setCregister(_getOperator());// ǩ��������
				generalh.setTaccounttime(getBillUI()._getServerTime()
						.toString());// ǩ��ʱ��
				generalh.setQianzidate(_getDate());// ǩ������
				ArrayList<String> list = new ArrayList<String>();
				list.add(_getDate().toString());
				list.add(_getOperator());// ��¼��
				// �����ű�
				PfUtilClient.processAction(getBillManageUI(), "SIGN",
						getBillManageUI().getUIControl().getBillType(),
						_getDate().toString(), aObject, list);
				// ���»���
				onBoRefresh();
			}
		} catch (Exception e) {
			Logger.error(e);
			getBillManageUI().showErrorMessage("ǩ��ʧ��:" + e.getMessage());
		}
	}

	// @Override
	// protected void onBoPrint() throws Exception {
	// // TODO Auto-generated method stub
	// super.onBoPrint();
	// Integer iprintcount
	// =PuPubVO.getInteger_NullAs(getBillCardPanelWrapper().getBillCardPanel().getTailItem("iprintcount").getValueObject(),
	// 0) ;
	// iprintcount=++iprintcount;
	// getBillCardPanelWrapper().getBillCardPanel().getTailItem("iprintcount").setValue(iprintcount);
	// getBufferData().getCurrentVO().getParentVO().setAttributeValue("iprintcount",
	// iprintcount);
	// HYPubBO_Client.update((SuperVO)getBufferData().getCurrentVO().getParentVO());
	// }
	protected void onBoEdit() throws Exception {
		valuteOrder();
		super.onBoEdit();
		// zhf add
		// setHeadPartEdit(false);
		// getButtonManager().getButton(IBillButton.Line).setEnabled(false);
		// getBillUI().updateButtons();
		// setInitByWhid(new String[]{"srl_pk","pk_cargdoc"});
	}

	private void valuteOrder() throws Exception {
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		SuperVO[] vos = (SuperVO[]) vo.getChildrenVO();
		if (vos == null || vos.length == 0)
			return;
		SuperVO bvo = (SuperVO) vos[0];
		String csid = PuPubVO.getString_TrimZeroLenAsNull(bvo
				.getAttributeValue("csourcebillhid"));
		// ��ѯ�˵������Ƿ��Ѿ�����
		SuperVO[] ovs = HYPubBO_Client.queryByCondition(SoorderVO.class,
				" isnull(dr,0)=0 and pk_soorder='" + csid + "'");
		if (ovs == null || ovs.length == 0)
			return;
		SoorderVO head = (SoorderVO) ovs[0];
		UFBoolean isdj = PuPubVO.getUFBoolean_NullAs(head.getFisended(),
				new UFBoolean(false));
		if (isdj.booleanValue() == true) {
			throw new Exception("�����˵��Ѿ����� ,��������");
		}

	}

	@Override
	// add xjx
	protected void onBoPrint() throws Exception {
		// ��������б���棬ʹ��ListPanelPRTS����Դ
		if (getBillManageUI().isListPanelSelected()) {
			nc.ui.pub.print.IDataSource dataSource = new MyListSoDateSource(
					getBillUI()._getModuleCode(),
					((BillManageUI) getBillUI()).getBillListPanel());
			nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(
					null, dataSource);
			print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(),
					getBillUI()._getModuleCode(), getBillUI()._getOperator(),
					getBillUI().getBusinessType(), getBillUI().getNodeKey());
			if (print.selectTemplate() == 1)
				print.preview();
		} else {
			final nc.ui.pub.print.IDataSource dataSource = new MySoDateSource(
					getBillUI()._getModuleCode(), getBillCardPanelWrapper()
							.getBillCardPanel());
			final nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(
					null, dataSource);
			print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(),
					getBillUI()._getModuleCode(), getBillUI()._getOperator(),
					getBillUI().getBusinessType(), getBillUI().getNodeKey());
			if (print.selectTemplate() == 1)
				print.preview();
			// ��������Դ��֧�����̴�ӡ
			// super.onBoPrint();
		}
		Integer iprintcount = PuPubVO.getInteger_NullAs(getBufferData()
				.getCurrentVO().getParentVO().getAttributeValue("cdt_pk"), 0);
		iprintcount = iprintcount + 1;
		getBufferData().getCurrentVO().getParentVO()
				.setAttributeValue("iprintcount", iprintcount);
		try {
			HYPubBO_Client.update((SuperVO) getBufferData().getCurrentVO()
					.getParentVO());
			onBoRefresh();
		} catch (final UifException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}
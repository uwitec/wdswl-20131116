package nc.ui.zmpub.pub.bill;

import java.sql.SQLException;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.BillCardPanelWrapper;
import nc.ui.trade.bill.BusiTypeChangeEvent;
import nc.ui.trade.bill.IBillBusiListener;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.bill.RefBillTypeChangeEvent;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.pub.VOTreeNode;
import nc.ui.trade.treecard.BillTreeCardUI;
import nc.ui.trade.treecard.ITreeCardController;
import nc.ui.trade.treecard.TreeCardEventHandler;
import nc.vo.pf.pub.BusitypeVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.trade.button.ButtonVO;

/**
 * �������������
 * 
 * @author mlr
 */
public abstract class SingleTreeEventHandler extends TreeCardEventHandler {
	private static final String staticACTION = "BOACTION";
	private static final String staticASS = "BOASS";
	private IBillBusiListener m_bbl = null;

	public void onTreeSelected(VOTreeNode selectnode) {
		try {
			if ((getUITreeCardController() instanceof ISingleController)) {
				onQueryBodyData(selectnode);
			}
		} catch (BusinessException ex) {
			getBillUI().showErrorMessage(ex.getMessage());
			ex.printStackTrace();
		} catch (Exception e) {
			getBillUI().showErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * ��ť�����¼�
	 */
	public void onButton(ButtonObject bo) {
		if (getUITreeCardController().isTableTree()) {
			ButtonObject parentBtn = bo.getParent();
			if (parentBtn != null) {
				int intBtn = Integer.parseInt(parentBtn.getTag());
			}
		}
		if (getBillUI().getBillOperate() == IBillOperate.OP_ADD
				|| getBillUI().getBillOperate() == IBillOperate.OP_EDIT) {
			if (getBillCardPanelWrapper() != null)
				getBillCardPanelWrapper().getBillCardPanel().stopEditing();

		}
		try {
			ButtonObject parentBtn = bo.getParent();

			if (parentBtn != null && Integer.parseInt(parentBtn.getTag()) < 100) {
				int intParentBtn = Integer.parseInt(parentBtn.getTag());
				complexOnButton(intParentBtn, bo);
			} else {
				if (bo.getTag() == null)
					System.out.println("������ť��������TAG,TAG>100������.....");
				int intBtn = Integer.parseInt(bo.getTag());
				if (intBtn > 100)// ��Ŵ���100��Ϊ���Զ��尴ť
					onBoElse(intBtn);
				else
					// ������Ϊ��Ԥ�ð�ť
					simpleOnButton(intBtn, bo);
			}
		} catch (BusinessException ex) {
			onBusinessException(ex);
		} catch (SQLException ex) {
			getBillUI().showErrorMessage(ex.getMessage());
		} catch (Exception e) {
			getBillUI().showErrorMessage(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * ���ఴť���¼����� �������ڣ�(2004-2-25 21:04:27)
	 * 
	 * @param intBtn
	 *            int
	 * @exception java.lang.Exception
	 *                �쳣˵����
	 */
	private void simpleOnButton(int intBtn, ButtonObject bo)
			throws java.lang.Exception {
		buttonActionBefore(getBillUI(), intBtn);
		switch (intBtn) {
		case IBillButton.Add: {
			if (!getBillUI().isBusinessType().booleanValue()) {
				getBillUI().showHintMessage(
						nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"uifactory", "UPPuifactory-000061")/*
																	 * @res
																	 * "��ʼ�������ӵ��ݣ���ȴ�......"
																	 */);
				onBoAdd(bo);
				// ����ִ�к���
				buttonActionAfter(getBillUI(), intBtn);
			}
			break;
		}
		case IBillButton.Edit: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000067")/*
												 * @res "��ʼ���б༭���ݣ���ȴ�......"
												 */);
			onBoEdit();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Del: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000068")/*
												 * @res "��ʼ�������ϵ��ݣ���ȴ�......"
												 */);
			onBoDel();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000069")/* @res "�������ϲ�������" */
			);
			break;
		}
		case IBillButton.Delete: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000070")/*
												 * @res "��ʼ���е���ɾ������ȴ�......"
												 */);
			onBoDelete();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000071")/* @res "����ɾ�����" */
			);
			break;
		}
		case IBillButton.Query: {
			getBillUI().showHintMessage(bo.getName());

			if (super.getUIController() instanceof ISingleController) {
				ISingleController strl = (ISingleController) super
						.getUIController();
				if (strl.isSingleDetail())
					onBoBodyQuery();
				else
					onBoQuery();
			} else
				onBoQuery();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);

			break;
		}
		case IBillButton.Save: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000072")/*
												 * @res "��ʼ���е��ݱ��棬��ȴ�......"
												 */);
			onBoSave();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000073")/* @res "���ݱ����������" */
			);
			break;
		}
		case IBillButton.Cancel: {
			onBoCancel();
			// �����ʾ״̬����ʾ��Ϣ
			getBillUI().showHintMessage("");
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Print: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000074")/*
												 * @res "��ʼ���д�ӡ���ݣ���ȴ�......"
												 */);
			onBoPrint();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000075")/* @res "���ݴ�ӡ��������" */
			);
			break;
		}
		case IBillButton.DirectPrint: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000074")/*
												 * @res "��ʼ���д�ӡ���ݣ���ȴ�......"
												 */);
			onBoDirectPrint();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000075")/* @res "���ݴ�ӡ��������" */
			);
			break;
		}

		case IBillButton.Return: {
			onBoReturn();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Card: {
			onBoCard();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Refresh: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000076")/*
												 * @res "��ʼ����ˢ�µ��ݣ���ȴ�......"
												 */);
			onBoRefresh();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000077")/* @res "����ˢ�²�������" */
			);

			break;
		}
		case IBillButton.Refbill: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000078")/*
												 * @res "��ʼ���в��յ��ݣ���ȴ�......"
												 */);
			onBillRef();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000079")/* @res "���ݲ��ղ�������" */
			);
			break;
		}
		case IBillButton.Copy: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000080")/*
												 * @res "��ʼ�������ݸ��ƣ���ȴ�......"
												 */);
			onBoCopy();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000081")/* @res "���ݸ��Ʋ�������" */
			);
			break;
		}
		case IBillButton.Audit: {
			// ��Ϊ�������ʾ�����������Թ淶������ɾ������
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res "��ʼִ�в���,��ȴ�..."
			// */);
			onBoAudit();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res "ִ�����. "
			// */
			// );
			break;
		}
		case IBillButton.CancelAudit: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res ��ʼִ�в���, ��ȴ�...
			// */
			// );
			onBoCancelAudit();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res ִ�����.
			// */
			// );
			break;
		}
		case IBillButton.Commit: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res ��ʼִ�в���, ��ȴ�...
			// */
			// );
			onBoCommit();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res ִ�����.
			// */
			// );
			break;
		}
		case IBillButton.SelAll: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res ��ʼִ�в���, ��ȴ�...
			// */
			// );

			onBoSelAll();

			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res ִ�����.
			// */
			// );
			break;
		}
		case IBillButton.SelNone: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res ��ʼִ�в���, ��ȴ�...
			// */
			// );
			onBoSelNone();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res ִ�����.
			// */
			// );
			break;
		}
		case IBillButton.ImportBill: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res ��ʼִ�в���, ��ȴ�...
			// */
			// );
			onBoImport();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res ִ�����.
			// */
			// );
			break;
		}
		case IBillButton.ExportBill: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res ��ʼִ�в���, ��ȴ�...
			// */
			// );
			onBoExport();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res ִ�����.
			// */
			// );
			break;
		}
		case IBillButton.ApproveInfo: {
			onBoApproveInfo();
			break;
		}
		default: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res ��ʼִ�в���, ��ȴ�...
			// */
			// );

			onBoActionElse(bo);

			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res ִ�����.
			// */
			// );
			break;
		}
		}

	}

	/**
	 * ��ťm_boBusiType���ʱִ�еĶ���,���б�Ҫ���븲��.
	 */
	private final void onBoBusiType(ButtonObject bo) throws Exception {
		// ִ��ǰ����
		busiTypeBefore(getBillUI(), bo);
		bo.setSelected(true);
		// ����ҵ������
		BusitypeVO vo = (BusitypeVO) bo.getData();
		// �������Ӱ�ť
		getBusiDelegator()
				.retAddBtn(getButtonManager().getButton(IBillButton.Add),
						_getCorp().getPrimaryKey(),
						getUIController().getBillType(), bo);
		// //����ִ�а�ť(�뵥��״̬�޹أ�
		getBusiDelegator().retElseBtn(
				getButtonManager().getButton(IBillButton.Action),
				getUIController().getBillType(), staticACTION);

		// getButtonManager().setActionButtonVO(
		// getBillUI().isSaveAndCommitTogether());

		String oldtype = getBillUI().getBusinessType();
		String newtype = vo.getPrimaryKey();
		String oldcode = getBillUI().getBusicode();
		String newcode = vo.getBusicode();

		// ҵ����������
		getBillUI().setBusinessType(newtype);
		// ҵ�����ʹ���
		getBillUI().setBusicode(newcode);

		// ����ˢ��UI
		getBillUI().initUI();
		// ���UI����
		getBillUI().getBufferData().clear();
		getBillUI().getBufferData().setCurrentRow(-1);

		getBillUI().updateButtonUI();

		if (m_bbl != null) {
			BusiTypeChangeEvent e = new BusiTypeChangeEvent(this, oldtype,
					newtype, oldcode, newcode);
			m_bbl.busiTypeChange(e);
		}
	}

	/**
	 * ���ఴť���¼����� �������ڣ�(2004-2-25 21:04:27)
	 * 
	 * @param intBtn
	 *            int
	 * @exception java.lang.Exception
	 *                �쳣˵����
	 */
	private void complexOnButton(int intBtn, ButtonObject bo)
			throws java.lang.Exception {
		switch (intBtn) {
		case IBillButton.Busitype: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000060")/*
												 * @res "��ʼѡ��ҵ�����ͣ���ȴ�......"
												 */);
			onBoBusiType(bo);
			break;
		}
		case IBillButton.Add: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000061")/*
												 * @res "��ʼ�������ӵ��ݣ���ȴ�......"
												 */);
			onBoBusiTypeAdd(bo, null);
			break;
		}
		case IBillButton.Action: {
			onBoAction(bo);
			break;
		}
		case IBillButton.Ass: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000062")/*
												 * @res "��ʼ�Ե��ݵĸ������в�������ȴ�......"
												 */);
			onBoAss(bo);
			break;
		}
		case IBillButton.Line: {
			onBoLine(bo);
			break;
		}
		case IBillButton.File: {
			onBoFile(bo);
		}
		case IBillButton.Brow: {
			onBoBrow(bo);
			break;
		}
		case IBillButton.NodeKey: {
			onBoNodekey(bo);
			break;
		}
		case IBillButton.Refbill: {
			ButtonVO btnVo = (ButtonVO) bo.getData();
			onBoElse(btnVo.getBtnNo());
			break;
		}
		default: {
			if (bo.getData() != null && bo.getData() instanceof ButtonVO) {
				ButtonVO btnVo = (ButtonVO) bo.getData();
				onBoElse(btnVo.getBtnNo());
			}
			break;
		}
		}
	}

	/**
	 * ��ťm_boNodekey���ʱִ�еĶ���,���б�Ҫ���븲��.
	 */
	private final void onBoNodekey(ButtonObject bo) throws Exception {
		bo.setSelected(true);
		// ����NodeKey
		getBillUI().setNodeKey(bo.getTag());

		// ����ˢ��UI
		getBillUI().initUI();
		// ��ղ�ѯģ��
		setQueryUI(null);
		// ���UI����
		getBillUI().getBufferData().clear();
		getBillUI().getBufferData().setCurrentRow(-1);

		getBillUI().updateButtonUI();
	}

	/**
	 * ����������� �������ڣ�(2004-1-7 8:57:02)
	 * 
	 * @exception java.lang.Exception
	 *                �쳣˵����
	 */
	private void onBoBrow(ButtonObject bo) throws java.lang.Exception {
		int intBtn = Integer.parseInt(bo.getTag());
		// ����ִ��ǰ����
		buttonActionBefore(getBillUI(), intBtn);
		switch (intBtn) {
		case IBillButton.First: {
			getBufferData().first();
			break;
		}
		case IBillButton.Prev: {
			getBufferData().prev();
			break;
		}
		case IBillButton.Next: {
			getBufferData().next();
			break;
		}
		case IBillButton.Last: {
			getBufferData().last();
			break;
		}
		}
		// ����ִ�к���
		buttonActionAfter(getBillUI(), intBtn);
		getBillUI().showHintMessage(
				nc.ui.ml.NCLangRes.getInstance()
						.getStrByID(
								"uifactory",
								"UPPuifactory-000503",
								null,
								new String[] { nc.vo.format.Format
										.indexFormat(getBufferData()
												.getCurrentRow() + 1) })/*
																		 * @res
																		 * "ת����:"
																		 * +
																		 * getBufferData
																		 * ().
																		 * getCurrentRow
																		 * () +
																		 * "ҳ���)"
																		 */
		);

	}

	/**
	 * �в������� �������ڣ�(2004-1-7 8:57:02)
	 * 
	 * @exception java.lang.Exception
	 *                �쳣˵����
	 */
	private void onBoLine(ButtonObject bo) throws java.lang.Exception {
		int intBtn = -1;// Integer.parseInt(bo.getTag());

		if (bo.getData() != null && bo.getData() instanceof ButtonVO) {
			ButtonVO btnVo = (ButtonVO) bo.getData();
			intBtn = btnVo.getBtnNo();
		} else {
			intBtn = Integer.parseInt(bo.getTag());
		}

		// ����ִ��ǰ����
		buttonActionBefore(getBillUI(), intBtn);

		getBillUI().showHintMessage(bo.getName());

		switch (intBtn) {
		case IBillButton.AddLine: {
			onBoLineAdd();
			break;
		}
		case IBillButton.DelLine: {
			onBoLineDel();
			break;
		}
		case IBillButton.CopyLine: {
			onBoLineCopy();
			break;
		}
		case IBillButton.InsLine: {
			onBoLineIns();
			break;
		}
		case IBillButton.PasteLine: {
			onBoLinePaste();
			break;
		}
		default:
			onBoElse(intBtn);
			break;
		}

		// ����ִ�к���
		buttonActionAfter(getBillUI(), intBtn);
	}

	/**
	 * ��ťm_boAction���ʱִ�еĶ���,���б�Ҫ���븲��. ����ִ�ж�������
	 */
	private final void onBoAss(ButtonObject bo) throws Exception {
		beforeOnBoAss(bo);
		AggregatedValueObject modelVo = getBufferData().getCurrentVO();
		Object ret = getBusinessAction().processAction(bo.getTag(), modelVo,
				getUIController().getBillType(),
				getBillUI()._getDate().toString(), getBillUI().getUserObject());
		if (ret != null && ret instanceof AggregatedValueObject) {
			AggregatedValueObject vo = (AggregatedValueObject) ret;
			// ����״̬
			modelVo.getParentVO().setAttributeValue(
					getBillField().getField_BillStatus(),
					vo.getParentVO().getAttributeValue(
							getBillField().getField_BillStatus()));
			// ����ʱ���
			modelVo.getParentVO().setAttributeValue("ts",
					vo.getParentVO().getAttributeValue("ts"));

			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}
		afterOnBoAss(bo);
	}

	/**
	 * ��ťm_boAction���ʱִ�еĶ���,���б�Ҫ���븲��. ����ִ�ж�������
	 */
	private final void onBoAction(ButtonObject bo) throws Exception {
		// getBillUI().showHintMessage(
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
		// "UPPuifactory-000179")/*
		// * @res "��ʼִ�в���,��ȴ�..."
		// */);
		ButtonVO btnVo = (ButtonVO) bo.getData();
		if (btnVo == null)
			return;
		switch (btnVo.getBtnNo()) {
		case IBillButton.Audit: {
			onBoAudit();
			break;
		}
		case IBillButton.CancelAudit: {
			onBoCancelAudit();
			break;
		}
		case IBillButton.Commit: {
			onBoCommit();
			break;
		}
		case IBillButton.Del: {
			onBoDel();
			break;
		}
		default: {
			onBoActionElse(bo);
			break;
		}
		}

		// getBillUI().showHintMessage(
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
		// "UPPuifactory-000180")/*
		// * @res "ִ�����."
		// */
		// );
	}

	/**
	 * ��ťm_boAdd���ʱִ�еĶ���,���б�Ҫ���븲��.
	 * 
	 * @param bo
	 *            ��Դ���ݵ�ƽ̨���ɰ�ť
	 * @param sourceBillId
	 *            �ο���Դ����Id
	 */
	private final void onBoBusiTypeAdd(ButtonObject bo, String sourceBillId)
			throws Exception {
		getBusiDelegator().childButtonClicked(bo, _getCorp().getPrimaryKey(),
				getBillUI()._getModuleCode(), _getOperator(),
				getUIController().getBillType(), getBillUI(),
				getBillUI().getUserObject(), sourceBillId);
		if (nc.ui.pub.pf.PfUtilClient.makeFlag) {
			// ���õ���״̬
			getBillUI().setCardUIState();
			// ����
			getBillUI().setBillOperate(IBillOperate.OP_ADD);
		} else {
			if (PfUtilClient.isCloseOK()) {
				if (m_bbl != null) {
					String tmpString = bo.getTag();
					int findIndex = tmpString.indexOf(":");
					String newtype = tmpString.substring(0, findIndex);
					RefBillTypeChangeEvent e = new RefBillTypeChangeEvent(this,
							null, newtype);
					m_bbl.refBillTypeChange(e);
				}
				if (isDataChange())
					setRefData(PfUtilClient.getRetVos());
				else
					setRefData(PfUtilClient.getRetOldVos());
			}
		}
	}

	/**
	 * ��ȡ�������ݵ���������
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-11-2����03:11:17
	 * @return
	 */
	public abstract String getSelectTreeIdName();

	/**
	 * �����в���
	 */
	@Override
	protected void onBoLineAdd() throws Exception {
		if (getBillTreeCardUI().getBillTreeSelectNode() != null) {
			if (getBillTreeCardUI().getBillTreeSelectNode().getChildCount() > 0) {
				throw new Exception("��ѡ�о�������ڵ�   ����ѡ�����ڵ�ĸ���");
			}
			super.onBoLineAdd();
			String pk_father = getBillTreeCardUI().getBillTreeSelectNode()
					.getData().getPrimaryKey();

			int selectRow = getBillCardPanelWrapper().getBillCardPanel()
					.getBillTable().getSelectedRow();
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
					pk_father, selectRow, getSelectTreeIdName());// ��ֵ��������
		} else {
			throw new Exception("��ѡ�����ڵ�");
		}
	}

	/**
	 * ��ѯ��������
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-11-2����01:38:28
	 * @param selectnode
	 * @throws Exception
	 */
	public void onQueryBodyData(VOTreeNode selectnode) throws Exception {
		Class voClass = Class.forName(getUIController().getBillVoName()[1]);

		SuperVO vo = (SuperVO) voClass.newInstance();

		String strWhere = "(isnull(dr,0)=0)";

		if (vo.getParentPKFieldName() != null)
			strWhere = "(" + strWhere + ") and " + vo.getParentPKFieldName()
					+ "='" + selectnode.getData().getPrimaryKey() + "'";

		SuperVO[] queryVos = getBusiDelegator().queryHeadAllData(voClass,
				getUIController().getBillType(), strWhere);

		if (queryVos != null && queryVos.length != 0) {
			AggregatedValueObject aVo = (AggregatedValueObject) Class.forName(
					getUIController().getBillVoName()[0]).newInstance();
			aVo.setParentVO(null);
			aVo.setChildrenVO(queryVos);
			getBufferData().addVOToBuffer(aVo);

			int num = getBufferData().getVOBufferSize();
			if (num == -1)
				num = 0;
			else
				num = num - 1;
			getBillTreeCardUI().getTreeToBuffer().put(selectnode.getNodeID(),
					num + "");

			getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
		} else {
			getBillUI().setBillOperate(IBillOperate.OP_INIT);
		}
	}

	/**
	 * CardButtonController ������ע�⡣
	 * 
	 * @param billUI
	 *            nc.ui.trade.pub.BillCardUI
	 */
	public SingleTreeEventHandler(BillTreeCardUI billUI, ICardController control) {
		super(billUI, control);
	}

	/**
	 * ����ģ���װ��ķ������� �������ڣ�(2004-1-6 22:29:36)
	 * 
	 * @return nc.ui.pub.bill.BillCardPanel
	 */
	protected BillCardPanelWrapper getBillCardPanelWrapper() {
		return getBillTreeCardUI().getBillCardWrapper();
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2004-1-9 8:37:34)
	 * 
	 * @return nc.ui.trade.pub.BillCardUI
	 */
	protected BillTreeCardUI getBillTreeCardUI() {
		return (BillTreeCardUI) getBillUI();
	}

	/**
	 * Bill��Ӧ�Ļ�������,CARD,LIST�������ش˷����� �������ڣ�(2004-1-7 8:44:06)
	 * 
	 * @return nc.ui.trade.buffer.BillUIBuffer
	 */
	protected nc.ui.trade.buffer.BillUIBuffer getBufferData() {
		return getBillTreeCardUI().getBufferData();
	}

	/**
	 * ��ÿ�Ƭ�������� �������ڣ�(2004-1-7 11:42:27)
	 * 
	 * @return nc.ui.trade.controller.ICardController
	 */
	protected ITreeCardController getUITreeCardController() {
		return (ITreeCardController) getUIController();
	}

	/**
	 * �Ƿ���������ڵ㡣 �������ڣ�(2004-02-06 13:12:42)
	 * 
	 * @return boolean
	 */
	public boolean isAllowAddNode(nc.ui.trade.pub.TableTreeNode node) {
		return true;
	}

	/**
	 * �Ƿ����ɾ���ڵ㡣 �������ڣ�(2004-02-06 13:12:42)
	 * 
	 * @return boolean
	 */
	public boolean isAllowDelNode(nc.ui.trade.pub.TableTreeNode node) {
		return true;
	}

	/**
	 * �������ӵĴ��� �������ڣ�(2002-12-23 12:43:15)
	 */
	public void onBoAdd(ButtonObject bo) throws Exception {
		if (isAllowAddNode(getBillTreeCardUI().getBillTreeSelectNode()))
			super.onBoAdd(bo);
	}

	/**
	 * ��ťm_boRefresh���ʱִ�еĶ���,���б�Ҫ���븲��.
	 */
	protected void onBoRefresh() throws Exception {

		getBillTreeCardUI().clearTreeSelect();

		getBillTreeCardUI().createBillTree(
				getBillTreeCardUI().getCreateTreeData());

		getBillTreeCardUI().afterInit();

		getBillTreeCardUI().setBillOperate(
				nc.ui.trade.base.IBillOperate.OP_INIT);
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2004-1-9 8:37:34)
	 * 
	 * @return nc.ui.trade.pub.BillCardUI
	 */
	protected void onBoSave() throws Exception {
		super.onBoSave();
	}

}

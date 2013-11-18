package nc.ui.zmpub.pub.bill;

import nc.bs.logging.Logger;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillListData;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.linkoperate.ILinkQuery;
import nc.ui.pub.linkoperate.ILinkQueryData;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.BillCardPanelWrapper;
import nc.ui.trade.bill.BillListPanelWrapper;
import nc.ui.trade.bill.BillTemplateWrapper;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.zmpub.pub.freeitem.FreeItemRefPane;
import nc.ui.zmpub.pub.freeitem.InvAttrCellRenderer;
import nc.ui.zmpub.pub.tool.DefSetTool;
import nc.vo.bd.def.DefVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.bill.BillRendererVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;
import nc.vo.zmpub.pub.freeitem.FreeVO;
import nc.vo.zmpub.pub.freeitem.VInvVO;

/**
 * <֧���Զ�����> BillManageUI <����ͷ����������> <֧���������>
 * 
 * @author zhf
 * 
 */
public abstract class DefBillManageUI extends BillManageUI implements
		ILinkQuery {

	public DefBillManageUI() {
		super();
	}

	public DefBillManageUI(Boolean useBillSource) {
		super(useBillSource);
	}

	public DefBillManageUI(String pk_corp, String pk_billType,
			String pk_busitype, String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}

	private DefVO[] m_defBody = null;

	private DefVO[] m_defHead = null;

	private Class bodyVOClass;

	private Class bodyVO1Class;

	private Class aggBillVOClass;

	// ���������
	protected static FreeItemRefPane ivjFreeItemRefPane = null;

	@Override
	public void afterEdit(BillEditEvent e) {
		String sItemKey = e.getKey();
		if (sItemKey.startsWith("vdef")) {
			// �Զ�����༭���¼�
			afterVuserDefEdit(e);
		} else if (e.getPos() == IBillItem.BODY && "vfree0".equals(sItemKey)) {
			// �����������
			afterFreeItemEdit(e);
		} else {
			super.afterEdit(e);
		}
	}

	@Override
	public boolean beforeEdit(BillEditEvent e) {
		String sItemKey = e.getKey();
		// �����������
		if (e.getPos() == IBillItem.BODY && "vfree0".equals(sItemKey)) {
			beforeFreeItemEdit(e);
		}
		return super.beforeEdit(e);
	}

	@Override
	protected BillListPanelWrapper createBillListPanelWrapper()
			throws Exception {
		BillListPanelWrapper list = super.createBillListPanelWrapper();
		BillListPanel billist = list.getBillListPanel();
		BillListData bd = billist.getBillListData();
		if (bd != null) {
			// �޸��Զ�����
			bd = changeBillListDataByUserDef(getDefHeadVO(), getDefBodyVO(), bd);
			billist.setListData(bd);
		}
		return list;
	}

	@Override
	protected BillCardPanelWrapper createBillCardPanelWrapper()
			throws Exception {
		BillCardPanelWrapper card = super.createBillCardPanelWrapper();
		BillCardPanel cardPanel = card.getBillCardPanel();
		BillData billdate = cardPanel.getBillData();
		if (billdate != null) {
			// �޸��Զ�����
			billdate = changeBillDataByUserDef(getDefHeadVO(), getDefBodyVO(),
					billdate);
			cardPanel.setBillData(billdate);
		}
		return card;
	}

	/**
	 * �Զ������(��Ƭ״̬)
	 * 
	 * @param defHead
	 * @param defBody
	 * @param oldBillData
	 * @return
	 */
	protected BillData changeBillDataByUserDef(DefVO[] defHead,
			DefVO[] defBody, BillData oldBillData) {
		try {
			// �����Զ��������
			if (defHead != null) {
				oldBillData.updateItemByDef(defHead, "vdef", true);
				for (int i = 1; i <= 20; i++) {
					nc.ui.pub.bill.BillItem item = oldBillData
							.getHeadItem("vdef" + i);
					if (item != null) {
						((nc.ui.pub.beans.UIRefPane) item.getComponent())
								.setAutoCheck(true);
					}
				}
			}
			// ����
			if ((defBody != null)) {
				oldBillData.updateItemByDef(defBody, "vdef", false);
				for (int i = 1; i <= 20; i++) {
					nc.ui.pub.bill.BillItem item = oldBillData
							.getBodyItem("vdef" + i);
					if (item != null) {
						((nc.ui.pub.beans.UIRefPane) item.getComponent())
								.setAutoCheck(true);
					}
					//
					if (item != null && item.getComponent() != null)
						((nc.ui.pub.beans.UIRefPane) item.getComponent())
								.setEditable(item.isEdit());
				}
			}
		} catch (Exception e) {
			Logger.error(e.getMessage());
		}
		return oldBillData;
	}

	/**
	 * �Զ������(�б�״̬)
	 * 
	 * @param defHead
	 * @param defBody
	 * @param oldBillData
	 * @return
	 */
	protected BillListData changeBillListDataByUserDef(DefVO[] defHead,
			DefVO[] defBody, BillListData oldBillData) {
		try {
			if (defHead != null) // ��ͷ
				oldBillData.updateItemByDef(defHead, "vdef", true);
			if (defBody != null) // ����
				oldBillData.updateItemByDef(defBody, "vdef", false);
			return oldBillData;
		} catch (Exception e) {
			Logger.error(e.getMessage());
		}
		return oldBillData;
	}

	/**
	 * ��ȡ��ͷ�Զ�����VO
	 * 
	 * @return
	 */
	public DefVO[] getDefHeadVO() {
		if (m_defHead == null) {
			try {
				m_defHead = DefSetTool.getDefHead(getCorpPrimaryKey(),
						getUIControl().getBillType());
			} catch (Exception e) {
				Logger.error(e.getMessage());
			}
		}
		return m_defHead;
	}

	/**
	 * ��ȡ�����Զ�����VO
	 * 
	 * @return
	 */
	public DefVO[] getDefBodyVO() {
		if (m_defBody == null) {
			try {
				m_defBody = DefSetTool.getDefBody(getCorpPrimaryKey(),
						getUIControl().getBillType());
			} catch (Exception e) {
				Logger.error(e.getMessage());
			}
		}
		return m_defBody;
	}

	protected void afterVuserDefEdit(BillEditEvent e) {
		int pos = e.getPos();
		String sItemKey = e.getKey();
		int row = e.getRow();
		if (pos == 0) {// ��ͷ
			String sVdefPkKey = "pk_defdoc"
					+ sItemKey.substring("vdef".length());
			DefSetTool.afterEditHead(getBillCardPanel().getBillData(),
					sItemKey, sVdefPkKey);
		} else if (pos == 1) {// ����
			String sVdefPkKey = "pk_defdoc"
					+ sItemKey.substring("vdef".length());
			DefSetTool.afterEditBody(getBillCardPanel().getBillModel(), row,
					sItemKey, sVdefPkKey);
		}
	}

	/**
	 * �������
	 */
	@SuppressWarnings("restriction")
	protected void afterFreeItemEdit(BillEditEvent e) {
		try {
			FreeItemRefPane ref = (FreeItemRefPane) getBillCardPanel()
					.getBodyItem("vfree0").getComponent();
			FreeVO voFree = ref.getFreeVO();
			// ���������������
			for (int i = 0; i <= 10; i++) {
				String fieldname = "vfree" + i;
				Object o = voFree.getAttributeValue(fieldname);
				getBillCardPanel().setBodyValueAt(o, e.getRow(), fieldname);
			}
		} catch (Exception e1) {
			Logger.error(e);
		}
		// ��������ɫ
		InvAttrCellRenderer ficr = new InvAttrCellRenderer();
		ficr.setFreeItemRenderer(getBillCardPanel());
	}

	/**
	 * ����༭�¼�������ѡ�����
	 */
	public void afterInventoryMutiEdit(BillEditEvent e) {
		int editrow = e.getRow();
		String key = e.getKey();
		UIRefPane invRef = (UIRefPane) getBillCardPanel().getBodyItem(key)
				.getComponent();
		// ���Ϊ�գ���մ���������
		String[] refPks = invRef.getRefPKs();
		if (refPks == null || refPks.length == 0) {
			// afterInvEditClear(editrow);
			return;
		}
		if (e.getOldValue() != null && e.getValue() != null) {
			// ���ԭ������ؼ�¼
			// afterInvEditClear(editrow);
		}

		// ������ȡ�����Ϣ,ͨ����̨��ѯ�����ش����Ϣ
		VInvVO[] invvos = null;

		boolean bisCalculate = getBillCardPanel().getBillModel()
				.isNeedCalculate();
		getBillCardPanel().getBillModel().setNeedCalculate(false);

		// �����¿���
		if (refPks.length > 1) {
			if (editrow == getBillCardPanel().getRowCount() - 1) {
				addNullLine(e.getRow(), refPks.length - 1);
			} else {
				insertNullLine(e.getRow(), refPks.length - 1);
			}
		}
		// ��������ֵ
		for (int i = 0; i < refPks.length; i++) {
			// ���ô�������Ϣ
			// setBodyValueByInvVO(invvos[i], e.getRow() + i);
		}
		// ��ɫ��
		InvAttrCellRenderer ficr = new InvAttrCellRenderer();
		ficr.setFreeItemRenderer(getBillCardPanel());
		// ����ϼ���
		getBillCardPanel().getBillModel().setNeedCalculate(bisCalculate);
		if (bisCalculate) {
			getBillCardPanel().getBillModel().reCalcurateAll();
		}
	}

	/**
	 * ���ӿ���
	 */
	public void addNullLine(int istartrow, int count) {
		if (count > 0) {
			for (int i = 1; i <= count; i++) {
				getBillCardPanel().addLine();
			}
		}
	}

	/**
	 * �������
	 */
	public void insertNullLine(int istartrow, int count) {
		if (count > 0) {
			for (int i = 1; i <= count; i++) {
				getBillCardPanel().insertLine();
			}
		}
	}

	/**
	 * ������༭ǰ�¼�
	 */
	protected void beforeFreeItemEdit(BillEditEvent e) {
		// ��ô��VO
		try {
			int row = e.getRow();
			VInvVO voInv = (VInvVO) getBillCardPanel().getBodyValueAt(row,
					"invvo");
			getBillCardPanel().getBillModel().addRowAttributeObject(row,
					e.getKey(), null);
			if (voInv != null) {
				getFreeItemRefPane().setFreeItemParam(voInv);
			}
		} catch (Exception ex) {
			Logger.info("����������ʧ��!");
		}
	}

	// /**
	// * ����༭�󣬱����д�ȡ�����Ϣ
	// */
	// public void setBodyFreeItemValue(String tablecode ,int row){
	// if (row < 0)
	// return;
	// if(tablecode == null || "".equals(tablecode)){
	// tablecode = getBillCardPanel().getCurrentBodyTableCode();
	// }
	// Object o =
	// getBillCardPanel().getBillModel(tablecode).getRowAttributeObject(row,"BILLCARD");
	// VInvo voInv = null;
	// if(voInv != null)
	// voInv = (VInvo)o;
	// else
	// voInv =
	// //���ݴ����ѯ---�����Ϣ
	// getBillCardPanel().getBillModel(tablecode).addRowAttributeObject(row,"BILLCARD",voInv);
	// }
	/**
	 * ��ȡ���������
	 * 
	 * @return
	 */
	protected FreeItemRefPane getFreeItemRefPane() {
		if (ivjFreeItemRefPane == null) {
			try {
				ivjFreeItemRefPane = new FreeItemRefPane();
				ivjFreeItemRefPane.setName("FreeItemRefPane");
				ivjFreeItemRefPane.setLocation(209, 4);
			} catch (java.lang.Throwable ivjExc) {
				ivjExc.printStackTrace();
			}
		}
		return ivjFreeItemRefPane;
	}

	/**
	 * ���鶯��
	 */
	public void doQueryAction(ILinkQueryData querydata) {
		try {
			if (querydata == null)
				return;
			String id = querydata.getBillID();
			if (id == null || "".equals(id))
				return;
			SuperVO headvo = (SuperVO) getBodyB1Class().newInstance();
			// ��ѯ��������
			SuperVO[] queryVos = getBusiDelegator().queryHeadAllData(
					getBodyB1Class(),
					getUIControl().getBillType(),
					" " + headvo.getTableName() + "."
							+ getUIControl().getPkField() + " = '" + id + "' ");
			// ��ѯ�ӱ�����
			if (queryVos != null && queryVos.length > 0) {
				setCurrentPanel(BillTemplateWrapper.CARDPANEL);
				AggregatedValueObject aggvo = (AggregatedValueObject) getAggVOClass()
						.newInstance();
				aggvo.setParentVO(queryVos[0]);
				getBusiDelegator().setChildData(aggvo, getBodyVOClass(),
						getUIControl().getBillType(),
						queryVos[0].getPrimaryKey(), null);
				getBufferData().addVOToBuffer(aggvo);
				getBufferData().setCurrentRow(getBufferData().getCurrentRow());
			}
			//
			ButtonObject[] btns = getButtons();
			for (ButtonObject btn : btns) {
				// if (("" + (IBillButton.Card)).equals(btn.getTag()) || ("" +
				// (IBillButton.Return)).equals(btn.getTag())) {
				// btn.setEnabled(true);
				// btn.setVisible(true);
				// } else {
				btn.setEnabled(false);
				btn.setVisible(false);
				// }
			}
			updateButtons();
		} catch (Exception e) {
			Logger.error(e);
		}
		//
	}

	private Class getBodyVOClass() throws Exception {
		if (bodyVOClass == null)
			bodyVOClass = Class.forName(getUIControl().getBillVoName()[2]);
		return bodyVOClass;
	}

	private Class getAggVOClass() throws Exception {
		if (aggBillVOClass == null)
			aggBillVOClass = Class.forName(getUIControl().getBillVoName()[0]);
		return aggBillVOClass;
	}

	private Class getBodyB1Class() throws Exception {
		if (bodyVO1Class == null)
			bodyVO1Class = Class.forName(getUIControl().getBillVoName()[1]);
		return bodyVO1Class;
	}

	protected void initSelfData() {// ��������ʾ��
		BillRendererVO cellRendererVo = new BillRendererVO();
		cellRendererVo.setShowZeroLikeNull(false);
		cellRendererVo.setShowRed(true);
		getBillCardPanel().setBodyShowFlags(cellRendererVo);
		// for add mlr
		ButtonObject btnobj = getButtonManager().getButton(IBillButton.Edit);
		if (btnobj != null) {
			if (btnobj.getData() != null) {
				nc.vo.trade.button.ButtonVO btn = (ButtonVO) btnobj.getData();
				btn.setBusinessStatus(new int[] { IBillStatus.FREE });
			}
		}

		btnobj = getButtonManager().getButton(IBillButton.Line);
		if (btnobj != null) {
			btnobj.removeChildButton(getButtonManager().getButton(
					IBillButton.CopyLine));
			btnobj.removeChildButton(getButtonManager().getButton(
					IBillButton.PasteLine));
			btnobj.removeChildButton(getButtonManager().getButton(
					IBillButton.InsLine));
			// btnobj.removeChildButton(getButtonManager().getButton(IBillButton.PasteLinetoTail));
		}
		getBillCardPanel().setBodyMenuShow(false);
	}

	// ֧������
	public abstract boolean isLinkQueryEnable();

	public int getLinkButtonNo() {
		return ZmpubBtnConst.LINKQUERY;
	}

	protected void initPrivateButton() {
		// ��ӡ����
		ButtonVO btnvo6 = new ButtonVO();
		btnvo6.setBtnNo(ZmpubBtnConst.ASSPRINT);
		btnvo6.setBtnName("��ӡ����");
		btnvo6.setBtnChinaName("��ӡ����");
		btnvo6.setBtnCode(null);// code�������Ϊ��
		btnvo6.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT });
		btnvo6.setChildAry(new int[] { IBillButton.Print,
				IBillButton.DirectPrint });
		addPrivateButton(btnvo6);

		if (!isLinkQueryEnable())
			return;
		ButtonVO btnvo = new ButtonVO();
		btnvo.setBtnNo(getLinkButtonNo());
		btnvo.setBtnName("����");
		btnvo.setBtnChinaName("����");
		btnvo.setBtnCode(null);// code�������Ϊ��
		btnvo.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT,
				IBillOperate.OP_NO_ADDANDEDIT });
		addPrivateButton(btnvo);

		// ������ѯ
		ButtonVO btnvo5 = new ButtonVO();
		btnvo5.setBtnNo(ZmpubBtnConst.ASSQUERY);
		btnvo5.setBtnName("������ѯ");
		btnvo5.setBtnChinaName("������ѯ");
		btnvo5.setBtnCode(null);// code�������Ϊ��
		btnvo5.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT });
		btnvo5.setChildAry(new int[] { ZmpubBtnConst.LINKQUERY,
				IBillButton.ApproveInfo });
		addPrivateButton(btnvo5);

		super.initPrivateButton();
	}

	abstract public String getBillType();

	protected String getBillNo() throws java.lang.Exception {
		return HYPubBO_Client.getBillNo(getBillType(), _getCorp()
				.getPrimaryKey(), null, null);
	}

	@Override
	public void setDefaultData() throws Exception {
		// TODO Auto-generated method stub
		getBillCardPanel().setHeadItem("pk_corp", _getCorp().getPk_corp());// ��˾
		getBillCardPanel().setHeadItem("vbillstatus", IBillStatus.FREE);// ����״̬
		getBillCardPanel().setHeadItem("dbilldate", _getDate());// ��������
		getBillCardPanel().setTailItem("dmakedate", _getDate());// �Ƶ�����
		getBillCardPanel().setTailItem("voperatorid", _getOperator());// �Ƶ���
		getBillCardPanel().setHeadItem("fisself", UFBoolean.TRUE);// ������Դ
		getBillCardPanel().setHeadItem("fisclose", UFBoolean.FALSE);// ������Դ
		getBillCardPanel().setHeadItem("pk_billtype", getBillType());// ������Դ
	}
}

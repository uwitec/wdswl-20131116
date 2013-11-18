package nc.ui.zmpub.pub.report;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.print.IPrintTemplateQry;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ToftPanel;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIMenuItem;
import nc.ui.pub.beans.UISplitPane;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.beans.table.ColumnGroup;
import nc.ui.pub.beans.table.GroupableTableHeader;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.bill.BillScrollPane.BillTable;
import nc.ui.pub.report.ReportBaseClass;
import nc.ui.pub.report.ReportItem;
import nc.ui.report.base.IButtonActionAndState;
import nc.ui.report.base.QueryAction;
import nc.ui.trade.report.controller.IReportCtl;
import nc.ui.trade.report.controller.ReportCtl;
import nc.ui.trade.report.group.GroupTableModel;
import nc.ui.trade.report.query.QueryDLG;
import nc.ui.zmpub.pub.report.buttonaction.ButtonAssets;
import nc.ui.zmpub.pub.report.buttonaction.IReportButton;
import nc.ui.zmpub.pub.report.buttonaction.LevelSubTotalAction;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.report.ReportModelVO;
import nc.vo.pub.report.SubtotalContext;
import nc.vo.pub.rs.MemoryResultSet;
import nc.vo.pub.rs.MemoryResultSetMetaData;
import nc.vo.trade.report.IReportModelSelectType;
import nc.vo.trade.report.TableField;
import nc.vo.zmpub.pub.report.IPeneExtendInfo;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.report.SubtotalVO;
import nc.vo.zmpub.pub.report.Toolkit;
import nc.vo.zmpub.pub.report.VOConvert;

/**
 * 
 * @author��������
 */
abstract public class ReportBaseUI extends ToftPanel implements ISender,
		IAdjustCombinVOTool, IPene2NodeInfo {

	private int listenerNum;

	private QueryDLG m_qryDlg = null;

	private ReportBaseClass m_report = null;

	private SubtotalVO m_voSubtotal = null;

	private CircularlyAccessibleValueObject[] m_vosTotal = null;

	private CombinVOTool m_combinTool = null;

	private IMsgProxy m_msgProxy = null;

	private boolean m_bCrossTable = false;

	public ButtonObject[] m_buttons = null;

	public int[] m_buttonArray = null;

	public Boolean unlockSortListener = false;// �����Ƿ����������

	// -----------------------------------------------------------------------------------���by
	// guanyj------------------------
	// ��������Ӱ�ť�Լ���Ӧ�¼�������
	private ButtonAssets button_action_map = new ButtonAssets(this);

	// ���ݵ�ReportModelVO,��Ϊ����ģ����ܻ�ı���������������Լ�������������
	private nc.vo.pub.report.ReportModelVO[] copyOfReportModelVOs = null;

	private LevelSubTotalAction subtotalAction = (LevelSubTotalAction) button_action_map
			.get(ButtonAssets.m_boLevelSubTotal);

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ �ϼư�ť�¼�������
	 * @ʱ�䣺2011-7-12����02:44:33
	 * @return
	 */
	public LevelSubTotalAction getSubtotalAction() {
		return subtotalAction;
	}

	private boolean needGroup = false;

	private IReportCtl m_uiCtl;

	// ����key�б�
	private ArrayList<String> groupKeys = new ArrayList<String>();

	// �����б������ָ���
	private UISplitPane veriSplitPane = null;

	// ������ʾTable
	private UITable groupTable = null;

	// ����hash��
	private HashMap groupMap = new HashMap();

	private CircularlyAccessibleValueObject[] allBodyDataVO = null;

	private ArrayList reportModelColumnGroups = new ArrayList();

	// �ָ����ţ�
	protected static final char columnSystemDelimiter = '_';

	// ������������
	private Vector dataVec = null;

	// �����������˼�����
	private int num = 0;

	private int n = 0;

	// -----------------------------------------------------------------------------------------------------------------------------

	public final int HEAD = 0;

	public final int BODY = 1;

	public final int TAIL = 2;

	// ���Ҿ���
	private String m_digitBase = null;

	private Vector<String> vecNotMnyFlds = null;

	/**
	 * ReportBaseUI ������ע�⡣
	 */
	public ReportBaseUI() {
		super();
		init();
	}

	public nc.ui.pub.ClientEnvironment _getCE() {
		return nc.ui.pub.ClientEnvironment.getInstance();
	}

	public String _getCorpID() {
		return _getCE().getCorporation().getPk_corp();
	}

	public UFDate _getCurrDate() {
		return _getCE().getDate();
	}

	public String _getModelCode() {
		// return _getCE().getModuleCode();
		return getFuncRegisterVO().getFunCode();
	}

	public String _getUserID() {
		return _getCE().getUser().getPrimaryKey();
	}

	/**
	 * ��̬���ӱ����С�
	 */
	public void addNewItems(ReportItem[] items) {
		getReportBase().addBodyItem(items);
	}

	public void adjustCombinedVO(nc.vo.pub.CircularlyAccessibleValueObject vo,
			Object oMsg) {
		if (!(vo instanceof ReportBaseVO))
			return;
		ReportBaseVO voBase = (ReportBaseVO) vo;
		if (!(oMsg instanceof SubtotalVO))
			return;
		if (!(voBase.isCombined() || voBase.isSubtotal() || voBase.isTotal()))
			return;
		SubtotalVO voSubtotal = (SubtotalVO) oMsg;
		// ��������ֵ��,�����е������ֶ���Ϊ�ա�
		String[] valueflds = voSubtotal.getValueFlds();
		String[] groupflds = voSubtotal.getGroupFlds();
		String[] names = voBase.getAttributeNames();
		for (int i = 0; i < names.length; i++) {
			if (Toolkit.binarySearch(valueflds, names[i]) < 0
					&& (groupflds == null || Toolkit.binarySearch(groupflds,
							names[i]) < 0))
				voBase.setAttributeValue(names[i], null);
		}
		// дС�ƺϼơ�
		if (groupflds == null) {
			voBase.setAttributeValue(voSubtotal.getTotalDescOnFld(), "�����ϼơ���");
		} else {
			String[] onFlds = voSubtotal.getSubDescOnFlds();
			String onfld = onFlds[voSubtotal.getGroupFlds().length - 1];
			voBase.setAttributeValue(onfld, "����С�ơ���");
		}
	}

	/* �ԲμӺϲ���VO���д��� */
	public void adjustDetailVO(nc.vo.pub.CircularlyAccessibleValueObject vo) {
	}

	/**
	 * �����า�ǣ��ṩ������������������ո� ���ߣ�Ѧ��ƽ �������ڣ�2005-1-12 9:26:22
	 * 
	 * @return
	 */
	protected String createCodeRule() {
		return null;
	}

	public void doCross(String[] strRowKeys, String[] strColKeys,
			String[] strValueKeys) {
		if (getBuffedTotalVO() == null || getBuffedTotalVO().length == 0)
			return;
		try {
			getReportBase()
					.drawCrossTable(strRowKeys, strColKeys, strValueKeys);
		} catch (Exception e) {
			e.printStackTrace();
		}
		updateUI();
		setCrossTableFlag(true);
	}

	public void doSubTotal() throws Exception {
		if (m_voSubtotal == null)
			throw new Exception("С�ƺϼ�����Ҫ����SubtotalVO,���Ե���setSubtotalVO()������");
		m_voSubtotal.validate();
		CircularlyAccessibleValueObject[] vos = getVOFromUI();
		if (vos == null || vos.length == 0)
			m_vosTotal = null;
		else {
			// ���շ����кϲ����ݡ�
			String[] groupflds = m_voSubtotal.getGroupFlds();
			int iGroupDeep = m_voSubtotal.getGroupDepth();
			String[] curGroupFlds = null;
			SubtotalVO vobak = (SubtotalVO) m_voSubtotal.clone();
			if (groupflds != null) {
				for (int i = 0; i < groupflds.length
						&& (iGroupDeep == -1 || i < iGroupDeep); i++) {
					curGroupFlds = new String[groupflds.length - i];
					System.arraycopy(groupflds, 0, curGroupFlds, 0,
							groupflds.length - i);
					vobak.setGroupFlds(curGroupFlds);
					vos = getCombinVOTool().combinVO(vos, vobak);
				}
			}
			vobak.setGroupFlds(null);
			CircularlyAccessibleValueObject voTotal = getCombinVOTool()
					.combinVO(vos, vobak)[0];
			// ȡֵ��
			m_vosTotal = (voTotal == null ? null : ((ReportBaseVO) voTotal)
					.getAllVOs(vobak.getOrderbyFlds(), vobak.isDesc()));
		}
		// ����UI.
		getReportBase().setBodyDataVO(m_vosTotal, false);
		updateUI();
	}

	// public void doSubTotal() throws Exception {
	// if (m_voSubtotal == null)
	// throw new Exception("С�ƺϼ�����Ҫ����SubtotalVO,���Ե���setSubtotalVO()������");
	// m_voSubtotal.validate();
	// CircularlyAccessibleValueObject[] vos = getVOFromUI();
	// if (vos == null || vos.length == 0)
	// m_vosTotal = null;
	// else {
	// String[] groupflds = m_voSubtotal.getGroupFlds();
	// int iGroupDeep = m_voSubtotal.getGroupDepth();
	// String[] curGroupFlds = null;
	// SubtotalVO vobak = (SubtotalVO) m_voSubtotal.clone();
	// if (groupflds != null) {
	// for (int i = 0; i < groupflds.length
	// && (iGroupDeep == -1 || i < iGroupDeep); i++) {
	// curGroupFlds = new String[groupflds.length - i];
	// System.arraycopy(groupflds, 0, curGroupFlds, 0,
	// groupflds.length - i);
	// vobak.setGroupFlds(curGroupFlds);
	// vos = getCombinVOTool().combinVO(vos, vobak);
	// }
	// }
	// vobak.setGroupFlds(null);
	// CircularlyAccessibleValueObject voTotal = getCombinVOTool()
	// .combinVO(vos, vobak)[0];
	// // ȡֵ��
	// m_vosTotal = (voTotal == null ? null : vos);//�޸ģ�������������
	// }
	// // ����UI.
	// getReportBase().setBodyDataVO(m_vosTotal, false);
	// //��������ֶβ�Ϊ�� �����С�ƺϼ�
	// if(m_voSubtotal.getTotalDescOnFld() != null &&
	// m_voSubtotal.getTotalDescOnFld().length() != 0){
	// String[] gfs = new String[]{m_voSubtotal.getTotalDescOnFld()};
	// String[] tfs =
	// subtotalAction.totalTableFieldToString(subtotalAction.getSubTotalCandidateTotalFields());
	// subtotalAction.onSubtotal(gfs, tfs);
	// //����С�ƺϼƵ�״̬�ֶ�
	// subtotalAction.setSubTotalCurrentUsingGroupFields(subtotalAction.groupStringToTableField(gfs));
	// subtotalAction.setSubTotalCurrentUsingTotalFields(subtotalAction.totalStringToTableField(tfs));
	// }
	// m_vosTotal = getVOFromUI();//--------------
	// getReportBase().setBodyDataVO(getVOFromUI(), false);
	// updateUI();
	// }

	public CircularlyAccessibleValueObject[] getBuffedTotalVO() {
		return m_vosTotal;
	}

	// public nc.ui.pub.ButtonObject[] getButtons() {
	// if (m_buttons == null) {
	// if (getPene2NodeInfo() == null)
	// m_buttons = new ButtonObject[] { m_boQry, m_boPrint };
	// /**
	// * xkf �������ӿ�ʵ�������� ����Ӹð�ť
	// */
	// else
	// m_buttons = new ButtonObject[] { m_boQry, m_boPrint, m_boPenerate };
	// }
	//
	// return m_buttons;
	// }

	public CombinVOTool getCombinVOTool() {
		if (m_combinTool == null) {
			m_combinTool = new CombinVOTool(this);
		}
		return m_combinTool;
	}

	public ReportBaseVO getHeadVOByQryDlg(
			nc.ui.trade.report.query.QueryDLG qrydlg) {
		qrydlg = (qrydlg == null ? getQueryDlg() : qrydlg);
		nc.vo.pub.query.ConditionVO[] vos = qrydlg.getConditionVO();
		ReportBaseVO vo = new ReportBaseVO();
		for (int i = 0; vos != null && i < vos.length; i++) {
			if (vos[i].getValue() != null) {
				String fldcode = vos[i].getFieldCode();
				if (fldcode.indexOf(".") > -1) {
					fldcode = "h_"
							+ fldcode.substring(fldcode.indexOf(".") + 1);
				}
				vo.setAttributeValue(fldcode, vos[i].getValue());
			}
		}
		return vo;
	}

	public ReportBaseVO getHeadVOFromUI() throws Exception {
		CircularlyAccessibleValueObject vo = getReportBase().getHeadDataVO();
		if (vo == null)
			return null;
		String[] attrnames = vo.getAttributeNames();
		if (attrnames == null || attrnames.length == 0)
			return null;
		ReportBaseVO voBase = new ReportBaseVO();
		for (int i = 0; i < attrnames.length; i++)
			voBase.setAttributeValue(attrnames[i], vo
					.getAttributeValue(attrnames[i]));
		return voBase;
	}

	/**
	 * ���ܣ�����ѡ������������Ҫ������ı�as a part of from caluse.
	 */
	public String getJoinTableByQueryCon() {
		String s = getQueryDlg().getTableJoinClauseForQuery();
		return s == null ? "" : s;
	}

	/**
	 * ���ܣ�����ѡ������������Ҫ������ı�as a part of from caluse.
	 */
	public String getJoinTableByQueryCon(nc.ui.trade.report.query.QueryDLG dlg) {
		String s = dlg.getTableJoinClauseForQuery();
		return s == null ? "" : s;
	}

	public IMsgProxy getMsgProxy() {
		return m_msgProxy;
	}

	/**
	 * �������� �������ڣ�(01-7-19 17:59:47)
	 */
	public ReportItem getNewItem(String key, String name, int dataType,
			int width) {
		ReportItem item = new ReportItem();
		item.setKey(key);
		item.setName(name);
		item.setDataType(dataType);
		item.setWidth(width);
		item.setEdit(true);
		return item;
	}

	public QueryDLG getQueryDlg() {
		if (m_qryDlg == null) {
			m_qryDlg = new QueryDLG(this);
			m_qryDlg.setTempletID(_getCorpID(), _getModelCode(), _getUserID(),
					null);
			m_qryDlg.setNormalShow(false);
		}
		return m_qryDlg;
	}

	public ReportBaseClass getReportBase() {
		if (m_report == null) {
			try {
				m_report = new ReportBaseClass();
				m_report.setName("ReportBase");
				m_report.setTempletID(_getCorpID(), _getModelCode(),
						_getUserID(), null);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return m_report;
	}

	public void setReportBase(ReportBaseClass m_report) {
		this.m_report = m_report;
	}

	public ReportBaseVO getSelectedRowVO() {
		int iSelRow = -1;
		iSelRow = getReportBase().getBillTable().getSelectedRow();
		if (iSelRow == -1) {
			MessageDialog.showHintDlg(this, "��ʾ", "��ѡ��һ�к�������ϸ!");
			return null;
		}
		if (getBuffedTotalVO() == null)
			return null;
		ReportBaseVO voBase = (ReportBaseVO) getBuffedTotalVO()[iSelRow];
		if (voBase.isCombined() || voBase.isSubtotal() || voBase.isTotal()) {
			MessageDialog.showHintDlg(this, "��ʾ", "С�ƺϼ��в���������ϸ!");
			return null;
		}
		return voBase;
	}

	public ReportBaseVO getSelectedVO() {
		int iSelRow = -1;
		iSelRow = getReportBase().getBillTable().getSelectedRow();
		if (iSelRow == -1) {
			MessageDialog.showHintDlg(this, "��ʾ", "��ѡ��һ���Խ��д�͸!");
			return null;
		}
		if (getBuffedTotalVO() == null)
			return null;
		ReportBaseVO voBase = new ReportBaseVO();
		if (getCurrentVO()[iSelRow] instanceof SuperVO) {
			voBase = (ReportBaseVO) VOConvert
					.superVOTocircularlyVO((SuperVO) getCurrentVO()[iSelRow]);
		} else {
			voBase = (ReportBaseVO) getCurrentVO()[iSelRow];
		}
		if (voBase.isCombined() || voBase.isSubtotal() || voBase.isTotal()
				|| checkIsTotalLine(voBase)) {
			// MessageDialog.showHintDlg(this, "��ʾ", "С�ƺϼ��в�֧�ִ�͸!");
			return null;
		}
		if (voBase.getStringValue("nrowclass") != null
				&& Integer.parseInt(voBase.getStringValue("nrowclass")) == 0) {
			return null;
		}
		return voBase;
	}

	private Boolean checkIsTotalLine(ReportBaseVO vo) {
		for (int i = 0; i < vo.getAttributeNames().length; i++) {
			if (vo.getAttributeValue(vo.getAttributeNames()[i])
					.equals("����С�ơ���")
					|| vo.getAttributeValue(vo.getAttributeNames()[i]).equals(
							"�����ϼơ���")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ����ʵ�ָ÷���������ҵ�����ı��⡣
	 * 
	 * @version (00-6-6 13:33:25)
	 * 
	 * @return java.lang.String
	 */
	public String getTitle() {
		return null;
	}

	/**
	 * �����������δ��̬�ĸ�UI������ʱ������ȷ����
	 */
	public CircularlyAccessibleValueObject[] getVOFromUI() throws Exception {
		ReportItem[] items = getReportBase().getBody_Items();
		int rows = getReportBase().getRowCount();
		ReportBaseVO[] result = new ReportBaseVO[rows];
		for (int row = 0; row < rows; row++) {
			result[row] = new ReportBaseVO();
			for (int i = 0; i < items.length; i++) {
				result[row].setAttributeValue(items[i].getKey(),
						getReportBase().getBodyValueAt(row, items[i].getKey()));

			}
		}

		return result;
	}

	public void init() {
		add(getReportBase());
		reSetModel();
		setUIAfterLoadTemplate();

		getReportBase().setBodyMenu(new UIMenuItem[0]);

		// ////////////////////////////by guanyj
		initPrivateButton();
		setButtons(getAllBtnAry());
		updateAllButtons();
		backupReportModelVOs();

		if (getUIControl().getGroupKeys() != null)
			needGroup = true;

		initColumnGroups();

		// ��ʾ�к�
		getReportBase().setShowNO(true);
		// ��ʾǧ��λ
		getReportBase().setShowThMark(true);
		// add by saf/08-4-16/�����������۽��ȣ�Ĭ��8λ
		setDefaultPriceAndQuantityDecimalDigits();
		setDefaultDecimalDigits();
	}

	// //////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * ���ݱ���ģ�����ݣ���Ϊ��ĳЩ�����п��ܱ��ı�
	 */
	protected void backupReportModelVOs() {
		ArrayList<ReportModelVO> al = new ArrayList<ReportModelVO>();
		for (int i = 0; i < getReportBase().getAllBodyVOs().length; i++) {
			// ����ֻ���� select_type!=δ��ѡ���VO
			if (!getReportBase().getAllBodyVOs()[i].getSelectType().equals(
					IReportModelSelectType.UNSELECTED))
				al.add((ReportModelVO) getReportBase().getAllBodyVOs()[i]
						.clone());

		}
		copyOfReportModelVOs = (nc.vo.pub.report.ReportModelVO[]) al
				.toArray(new nc.vo.pub.report.ReportModelVO[0]);

	}

	// /////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * ��ӷ���
	 * 
	 */
	protected void initColumnGroups() {
		nc.vo.pub.report.ReportModelVO[] vos = getModelVOs();// getReportBase().getAllBodyVOs();
		HashMap tmpHash = new HashMap();

		for (int i = 0; i < vos.length; i++) {
			int index = vos[i].getColumnSystem().indexOf(columnSystemDelimiter);
			String key;
			ArrayList al;
			if (index != -1) {
				key = vos[i].getColumnSystem().substring(0, index);
			} else {
				key = vos[i].getColumnSystem();
			}
			if (tmpHash.get(key) == null) {
				al = new ArrayList();
				tmpHash.put(key, al);
			} else {
				al = (ArrayList) tmpHash.get(key);
			}
			al.add(vos[i].getColumnCode());

		}
		reportModelColumnGroups.addAll(tmpHash.values());
	}

	public int[] getReportButtonAry() {
		m_buttonArray = new int[] { IReportButton.QueryBtn,
		// IReportButton.ColumnFilterBtn, IReportButton.CrossBtn,
				// IReportButton.FilterBtn, IReportButton.SortBtn,
				// IReportButton.SubTotalBtn,
				IReportButton.onboRefresh, IReportButton.PrintBtn, };
		return m_buttonArray;
	}

	/**
	 * 
	 * date:2007-8-22 ����01:17:30
	 * 
	 * @author:saf void
	 */
	protected void initPrivateButton() {
	}

	protected final void addPrivateButton(String name, String hint,
			Integer btnno) {
		ButtonObject bo = new ButtonObject(name, hint, 1);
		bo.setTag(btnno.toString());
		button_action_map.getButtonMap().put(btnno, bo);
	}

	/**
	 * �˷�������Ԥ����ť���Զ��尴ť
	 * 
	 */
	private ButtonObject[] getAllBtnAry() {
		if (button_action_map.getVisibleButtonsByOrder().size() == 0)
			return null;
		ButtonObject[] buttonAry = (ButtonObject[]) button_action_map
				.getVisibleButtonsByOrder().toArray(new ButtonObject[0]);

		return buttonAry;
	}

	public boolean isCrossTable() {
		return m_bCrossTable;
	}

	/**
	 * ����ʵ�ָ÷�������Ӧ��ť�¼���
	 * 
	 */
	public void onButtonClicked(nc.ui.pub.ButtonObject bo) {
		// if (bo == m_boQry)
		// onQuery();
		// if (bo == m_boPrint) {
		// onPrint();
		// }
		// if (bo == m_boPenerate) {
		// onPenerate();
		// }
		// ----------------------------Ӧ��button_action_map-----------------------

		if (button_action_map.get(bo) != null) {
			IButtonActionAndState action = (IButtonActionAndState) button_action_map
					.get(bo);
			// �ж��Ƿ��ǲ�ѯ��ť������ ����ѯ�����������ݱ�����
			if (action instanceof QueryAction) {
				num++;
			}

			try {
				action.execute();
				// modify by chenliang at 07/09/23
				updateBodyDigits();
			} catch (Exception e) {
				showErrorMessage("����" + e.getMessage());
			}
		} else {
			try {
				onBoElse(Integer.parseInt(bo.getTag()));
			} catch (Exception e) {
				showErrorMessage("����" + e.getMessage());
			}
		}

		updateAllButtons();
	}

	// --------------------------------------------------------down--------------------------by
	// guanyj///////////////////////

	/**
     * 
     */
	protected void onBoElse(Integer intBtn) throws Exception {
	}

	/**
	 * �������а�ť״̬----------------------------------------------------------------
	 * -------
	 */
	protected void updateAllButtons() {
		boolean hasData = false;
		BillModel bm = m_report.getBillModel();
		if (bm != null
				&& (bm.getDataVector() == null || bm.getDataVector().size() == 0)) {
			hasData = false;
		} else {
			hasData = true;
		}
		setAllButtonState(hasData);
		updateButtons();

	}

	/**
	 * ��������button״̬��������IButtonActionAndState�ӿڵ�isButtonAvailable
	 * 
	 * @author guanyj
	 * @param hasData
	 */
	private void setAllButtonState(boolean hasData) {
		Iterator it = button_action_map.keySet().iterator();
		while (it.hasNext()) {
			ButtonObject obj = (ButtonObject) it.next();
			IButtonActionAndState state = (IButtonActionAndState) button_action_map
					.get(obj);
			int result = state.isButtonAvailable();
			switch (result) {
			case 0:
				obj.setEnabled(false);
				break;
			case 1:
				obj.setEnabled(true);
				break;
			case IButtonActionAndState.ENABLE_ALWAYS:
				obj.setEnabled(true);
				break;
			case IButtonActionAndState.ENABLE_WHEN_HAS_DATA:
				if (hasData) {
					obj.setEnabled(true);
					break;
				} else
					obj.setEnabled(false);
				break;
			}

		}
	}

	/**
	 * ��ģ���ӡ����ť���ñ�������
	 * 
	 */
	public void onPrint() throws Exception {
		Object template = null;
		try {
			// template =
			// nc.ui.pub.print.PrintBO_Client.getTempletByModule(_getModelCode());
			// xkf ���� v5
			IPrintTemplateQry tempservice = (IPrintTemplateQry) NCLocator
					.getInstance().lookup(IPrintTemplateQry.class.getName());
			template = tempservice.getTempletByModule(_getModelCode());
		} catch (Exception e) {
		}
		if (template == null) {
			nc.ui.pub.print.PrintDirectEntry print = PMPrintManager
					.getDirectPrinter(getReportBase(), getTitle());
			print.preview();
		} else {
			nc.ui.trade.pub.CardPanelPRTS ds = new nc.ui.trade.pub.CardPanelPRTS(
					getTitle(), getReportBase());
			nc.ui.pub.print.PrintEntry printer = new nc.ui.pub.print.PrintEntry(
					this);
			printer.setDataSource(ds);
			printer.setTemplateID(getCorpPrimaryKey(), _getModelCode(),
					_getUserID(), null, null);
			// @@@@ ��������ӡ�޷�ѡ���ӡģ�� by xep 2006-06-08
			if (printer.selectTemplate() < 0)
				return;
			printer.preview();
		}

	}

	abstract public void onQuery();

	abstract public ReportBaseVO[] getReportVO(String wheresql)
			throws BusinessException;

	public void registerMsgProxy(IMsgProxy mp) {
		m_msgProxy = mp;
	}

	public void reloadReport() {
		this.removeAll();
		m_report = null;
		add(getReportBase());
		getReportBase().getBodyPanel().getTable().removeSortListener();
		setUIAfterLoadTemplate();
		m_bCrossTable = false;
	}

	/**
	 * ������ʾ��
	 */
	protected void reSetModel() {
		getReportBase().setBillModel(getReportBase().getBillModel());
		if (getUnlockSortListener() != null
				&& getUnlockSortListener().booleanValue() == false) {
			getReportBase().getBodyPanel().getTable().removeSortListener();
		}
		getReportBase().getBodyPanel().getTable().setSelectionMode(
				javax.swing.ListSelectionModel.SINGLE_SELECTION);
		getReportBase().getBodyPanel().getTable().setRowSelectionAllowed(true);
		getReportBase().getBodyPanel().getTable().setColumnSelectionAllowed(
				false);
		getReportBase().getBodyPanel().getTable().setCellSelectionEnabled(true);
		getReportBase().setShowThMark(true);
	}

	public void setBodyVO(CircularlyAccessibleValueObject[] vos) {
		if (isCrossTable())
			reloadReport();
		getReportBase().setBodyDataVO(vos, true);
		m_vosTotal = vos;
		// saf/07/08/21am/add/�������˫����Ԫ��ִ��������ص���
		if (listenerNum < 1) {
			initListener();
			listenerNum += 1;
		}
	}

	public void setBodyVO(CircularlyAccessibleValueObject[] vos,
			boolean isLoadFormula) {

		// �ڸ�ֵǰ�����ý���ľ��� ,��֤���Ȳ���ʧadd by wenming 2008-3-25
		setDecimalDigits();
		if (isCrossTable())
			reloadReport();
		getReportBase().setBodyDataVO(vos, isLoadFormula);
		m_vosTotal = vos;
		// saf/07/08/21am/add/�������˫����Ԫ��ִ��������ص���
		if (listenerNum < 1) {
			initListener();
			listenerNum += 1;
		}
	}

	/**
	 * ���ܣ��������˫����Ԫ��ִ��������ص��� date:2007-8-21 ����09:32:30
	 * 
	 * @author:saf void
	 */
	// public void initListener() {
	// // ����Table������
	// Component com = getReportBase().getBillTable();
	// com.addMouseListener(new MouseAdapter() {
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	// */
	// public void mouseClicked(MouseEvent e) {
	// if (e.getSource() instanceof BillTable && e.getClickCount() == 2) {
	// onPenerate();
	// }
	// }
	// });
	// }
	public void initListener() {
		// ����Table������
		Component com = getReportBase().getBillTable();
		MouseListener[] mosListener = (MouseListener[]) com
				.getListeners(MouseListener.class);
		com.addMouseListener(new CustMouseListener(mosListener));
	}

	/**
	 * �ڲ��࣬����table����¼����� ���ߣ�Ѧ��ƽ �������ڣ�2007-7-27 17:09:28
	 */
	private class CustMouseListener extends MouseAdapter {
		MouseListener[] listeners = null;

		public CustMouseListener(MouseListener[] list) {
			super();
			this.listeners = list;
		}

		/*
		 * @see
		 * java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent e) {
			for (int i = 0; listeners != null && i < listeners.length; i++) {
				listeners[i].mouseClicked(e);
			}
			if (e.getSource() instanceof BillTable && e.getClickCount() == 2) {
				onPenerate();
			}
		}
	}

	private void specailPenerate() {

	}

	/**
	 * �����г��ȡ�
	 */
	public void setColWidth(String colName, int iwidth) {
		if (colName == null)
			return;
		for (int i = 0; i < getReportBase().getBodyShowItems().length; i++) {
			if (getReportBase().getBodyShowItems()[i].getKey().equals(
					colName.trim()))
				getReportBase().getBodyShowItems()[i].setWidth(iwidth);
		}
	}

	public void setCrossTableFlag(boolean b) {
		m_bCrossTable = b;
	}

	public void setGroupColumn(String[] columnIdentifiers, String groupName) {
		UITable table = getReportBase().getBillTable();
		TableColumnModel cm = table.getColumnModel();
		ColumnGroup g = new ColumnGroup(groupName);
		for (int i = 0; i < cm.getColumnCount(); i++) {
			if (Toolkit.binarySearch(columnIdentifiers, cm.getColumn(i)
					.getIdentifier()) > -1)
				g.add(cm.getColumn(i));
		}
		GroupableTableHeader header = (GroupableTableHeader) table
				.getTableHeader();
		header.addColumnGroup(g);
	}

	public void setHeadDataByQryDlg() {
		getReportBase().setHeadDataVO(getHeadVOByQryDlg(null));
		getReportBase().execHeadLoadFormulas();

	}

	public void setHeadDataBYQryDlg(nc.ui.trade.report.query.QueryDLG qryDlg) {
		getReportBase().setHeadDataVO(getHeadVOByQryDlg(qryDlg));
		getReportBase().execHeadLoadFormulas();

	}

	/*
	 * �ϼ�vo����ϸ vo����һ��UI.
	 */
	public void setReportVOsVsDetailVOs(String[] joinFlds, String[] detailFlds,
			String detailKeyFlds) {

		try {
			CircularlyAccessibleValueObject[] vos = getVOFromUI();
			if (vos == null || vos.length == 0)
				return;
			m_vosTotal = Toolkit.combinReportVOsVsDetailVOs(vos, joinFlds,
					detailFlds, detailKeyFlds);
			getReportBase().setBodyDataVO(m_vosTotal, false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * �ϼ�vo����ϸ vo����һ��UI.
	 */
	public void setReportVOsVsDetailVOs(
			CircularlyAccessibleValueObject[] totalvos,
			CircularlyAccessibleValueObject[] detailvos, String[] joinFlds,
			String[] detailFlds, String detailKeyFlds) {
		setBodyVO(Toolkit.addTwoVos(totalvos, detailvos));
		setReportVOsVsDetailVOs(joinFlds, detailFlds, detailKeyFlds);
	}

	/**
	 * ������ʾ��
	 * 
	 * @parameters:cols the cols that should be hide or showed,
	 * @ipos,0-head 1-body,2-tail.
	 * @bshow,true show
	 */
	public void setShowCol(String[] cols, int ipos, boolean bShow) {
		nc.ui.pub.bill.BillItem[] items = null;
		switch (ipos) {
		case HEAD:
			items = getReportBase().getHead_Items();
			break;
		case BODY:
			items = getReportBase().getBodyItems();
			break;
		case TAIL:
			items = getReportBase().getTailItems();
			break;
		}
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; j < cols.length; j++) {
				if (items[i].getKey().equalsIgnoreCase(cols[j])) {
					if (ipos != BODY && !bShow) {
						// ReportITem��һ�� bug.�ڴ����⴦��
						getReportBase().getHeaderPanel().remove(
								items[i].getComponent());
						getReportBase().getHeaderPanel().remove(
								items[i].getCaptionLabel());

					}
					items[i].setShow(bShow);
				}
			}
		}
		reSetModel();
	}

	protected void setSubtotalVO(SubtotalVO vo) {
		m_voSubtotal = vo;
	}

	protected SubtotalVO getSubtotalVO() {
		return m_voSubtotal;
	}

	/**
	 * ���ر�����������ö�UI���б�Ҫ�ĵ�����
	 */
	abstract public void setUIAfterLoadTemplate();

	/**
	 * ��͸�ڵ���Ϣ�ӿ�ʵ�֣�����Ҫ��͸��ѯ�ģ���Ҫ���ظ÷�����
	 */
	public String getPene2NodeInfo() {
		return null;
	}

	/**
	 * ����ʱ��Ҫ�Զ�����Ϣ ʱoverwrite�˷��� Ĭ�Ϸ��ؿգ���ʾ������������ʵ����ο�IPeneExtendInfo�ӿ�
	 * 
	 * @return
	 */
	public IPeneExtendInfo getExtendInfo() {
		return null;
	}

	public void onPenerate() {

	}

	private String[] getExtendInfoFromPeneInfo(IPeneExtendInfo extendInfo,
			String itemkey) {
		// TODO Auto-generated method stub
		if (extendInfo == null) {
			return null;
		}
		// if (exPeneInfoHash == null) {
		HashMap<String, String[]> exPeneInfoHash = new HashMap<String, String[]>();
		String[][] cloumkeys = extendInfo.getColumKeys();
		String[] exWhereStrs = extendInfo.getExtendsWhereStrings();
		String[] exNodecode = extendInfo.getNodeCodes();
		for (int i = 0; i < exWhereStrs.length; i++) {
			String[] arrayCols = cloumkeys[i];
			for (int j = 0; j < arrayCols.length; j++) {
				String columkey = arrayCols[j];
				if (!exPeneInfoHash.containsKey(columkey)) {
					exPeneInfoHash.put(columkey, new String[] { exWhereStrs[i],
							exNodecode[i] });
				}
			}
		}
		// }
		return (String[]) exPeneInfoHash.get(itemkey);
	}

	/**
	 * �Խ���vo����
	 */
	public void onSort(String[] fields, int[] asc) {
		CircularlyAccessibleValueObject[] vos = null;
		if ((vos = getCurrentVO()) == null)
			return;
		getReportBase().getReportSortUtil().multiSort(vos, fields, asc);
		setBodyDataVO(vos, false);
	}

	/**
	 * ���ص�ǰVO������ӷ�������Ҳ�������ֱ�Ӵӽ����ϵõ����ӽ����ϵõ�VO�ȽϺ�ʱ�����ǣ�������ݺ�
	 * VO˳���������أ�����ֱ��getVOFromUI����ΪһЩ��������������û���ڷ���������ֳ���
	 * 
	 * @return
	 */
	public CircularlyAccessibleValueObject[] getCurrentVO() {
		CircularlyAccessibleValueObject[] cvos = null;
		if ((cvos = getCurrentVOFromGroupMap()) == null) {
			try {
				cvos = getVOFromUI();
			} catch (Exception e) {
				showErrorMessage("����" + e.getMessage());
			}
		}
		return cvos;
	}

	private CircularlyAccessibleValueObject[] getCurrentVOFromGroupMap() {
		if (groupKeys.size() == 0)
			return null;
		int selectedRow = 0;
		if (getGroupTable().getSelectedRow() != -1)
			selectedRow = getGroupTable().getSelectedRow();
		String[] keys = getValuesFromGroupTable(selectedRow);
		String key = "";
		for (int i = 0; i < keys.length; i++) {
			key += keys[i];
			if (i != keys.length - 1)
				key += ":";
		}
		return (groupMap.get(key) == null ? null
				: (CircularlyAccessibleValueObject[]) ((ArrayList) groupMap
						.get(key))
						.toArray(new CircularlyAccessibleValueObject[0]));
	}

	/**
	 * 
	 * @return
	 */
	protected UITable getGroupTable() {
		if (groupTable == null) {
			groupTable = new UITable(new DefaultTableModel());
			groupTable.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					int row = groupTable.getSelectedRow();
					if (row == -1)
						return;
					int count = groupTable.getModel().getColumnCount();
					StringBuffer key = new StringBuffer();
					for (int i = 0; i < count; i++) {
						key
								.append(groupTable.getModel()
										.getValueAt(row, i) == null ? ""
										: groupTable.getModel().getValueAt(row,
												i).toString());
						if (i != count - 1)
							key.append(":");
					}
					ArrayList tmpVO = (ArrayList) groupMap.get(key.toString());
					if (tmpVO != null && tmpVO.size() > 0) {
						setBodyDataVO(
								(CircularlyAccessibleValueObject[]) tmpVO
										.toArray(new CircularlyAccessibleValueObject[0]),
								false);
					}
					setHeadItems(convertVOKeysToModelKeys((String[]) groupKeys
							.toArray(new String[0])),
							getValuesFromGroupTable(row));
				}
			});
		}
		return groupTable;
	}

	/**
	 * ���ñ�ͷ����
	 * 
	 * @param key
	 * @param value
	 */
	private void setHeadItems(String[] keys, Object[] values) {
		if (keys == null || values == null || keys.length == 0
				|| values.length == 0)
			return;
		if (keys.length != values.length) {
			System.out.println("����ֵ����Ŀ��ƥ��");
			return;
		}
		for (int i = 0; i < keys.length; i++) {
			getReportBase().setHeadItem(keys[i], values[i]);
		}
	}

	/**
	 * �ӷ���table�л�ȡָ��������
	 * 
	 * @param row
	 * @return
	 */
	private String[] getValuesFromGroupTable(int row) {
		UITable table = getGroupTable();
		int count = table.getColumnCount();
		String[] str = new String[count];
		for (int i = 0; i < str.length; i++) {
			str[i] = (String) table.getModel().getValueAt(row, i);
		}
		return str;
	}

	/**
	 * @param bodyDataVO
	 *            Ҫ���õ� bodyDataVO��
	 * @param isLoadFormula
	 *            �Ƿ�ִ�м��ع�ʽ
	 */
	protected void setBodyDataVO(CircularlyAccessibleValueObject[] dataVO,
			boolean isLoadFormula) {
		getReportBase().setBodyDataVO(dataVO, isLoadFormula);
		if (needGroup) {
			needGroup = false;
			onGroup(getUIControl().getGroupKeys());
		}
		updateAllButtons();
		// saf/07/08/21am/add/�������˫����Ԫ��ִ��������ص���
		if (listenerNum < 1) {
			initListener();
			listenerNum += 1;
		}
	}

	/**
	 * ����
	 * 
	 * @param context
	 * @throws Exception
	 * @throws Exception
	 */
	protected void onGroup(String[] keys) {
		String[] colNames = getColumnNamesByKeys(convertVOKeysToModelKeys(keys));
		try {
			onGroup(keys, colNames);
		} catch (Exception e) {
			showErrorMessage("����" + e.getMessage());
		}
	}

	/**
	 * ͨ��keys���col��ʾ��
	 * 
	 * @param keys
	 *            ������ModelKey������'_'�ָ���key
	 * @return
	 */
	protected String[] getColumnNamesByKeys(String[] keys) {
		if (keys == null || keys.length == 0)
			return null;
		ReportModelVO[] fields = getModelVOs();
		// ��ʹ�ù̶����飬��ֹ����Ԫ��ΪNULL������������ж�
		ArrayList list = new ArrayList();
		if (fields != null && fields.length != 0) {
			for (int i = 0; i < keys.length; i++) {
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getColumnCode().equals(keys[i]))
						list.add(fields[j].getColumnUser());
				}
			}
		}
		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * ����
	 * 
	 * @param keys
	 * @param names
	 * @throws Exception
	 */
	protected void onGroup(String[] keys, String[] names) throws Exception {
		String[] convertGroupKeys = convertVOKeysToModelKeys((String[]) groupKeys
				.toArray(new String[0]));
		// ��ʾ�ϴ����ص���
		getReportBase().showHiddenColumn(convertGroupKeys);
		// �����ϴ���ӵı�ͷ
		removeHeadItems(convertGroupKeys);
		// ��������¼
		groupKeys.clear();
		// ��Ϊȡ������
		if (keys == null || names == null || keys.length == 0
				|| names.length == 0 || keys.length != names.length) {
			setVeriSplitEnabled(false);
			// ���û�ԭ����
			setBodyDataVO(allBodyDataVO, false);
			return;
		}

		groupKeys.addAll(Arrays.asList(keys));
		// �����ָ���
		setVeriSplitEnabled(true);
		// �ӽ����ȡVO
		if (allBodyDataVO == null || allBodyDataVO.length == 0)
			allBodyDataVO = getVOFromUI();
		// ��շ���hash
		groupMap.clear();
		// ������key hash��key����ʽ�� a:b:c::��ֵ��Ϊ��" ",��Ӱ��ʹ��
		for (int i = 0; i < allBodyDataVO.length; i++) {
			StringBuffer key = new StringBuffer();
			for (int j = 0; j < keys.length; j++) {
				key
						.append(allBodyDataVO[i].getAttributeValue(keys[j]) == null ? " "
								: allBodyDataVO[i].getAttributeValue(keys[j])
										.toString());
				if (j != keys.length - 1)
					key.append(":");
			}
			addVoToHashmap(key.toString(), allBodyDataVO[i]);
		}
		String[] convertedKeys = convertVOKeysToModelKeys(keys);
		extractItemsToHead(convertedKeys, names);
		getReportBase().hideColumn(convertedKeys);
		// �����model
		GroupTableModel model = new GroupTableModel();
		model.addColumns(names);
		model.addRows(groupMap.keySet());
		getGroupTable().setModel(model);
		// ��ʾ��һ������
		ArrayList tmpVO = (ArrayList) groupMap.get(groupMap.keySet().iterator()
				.next());
		if (tmpVO != null && tmpVO.size() > 0) {
			setBodyDataVO((CircularlyAccessibleValueObject[]) tmpVO
					.toArray(new CircularlyAccessibleValueObject[0]), false);
			setHeadItems(convertedKeys, getValuesFromGroupTable(0));
		}
	}

	/**
	 * ��keys����Ӧ��item��ȡ����ͷ
	 * 
	 * @param keys
	 *            ����
	 * @param colNames
	 *            ������
	 */
	private void extractItemsToHead(String[] keys, String[] colNames) {
		String[] convertKeys = convertVOKeysToModelKeys(keys);
		ReportItem[] items = new ReportItem[keys.length];
		for (int i = 0; i < convertKeys.length; i++) {
			items[i] = new ReportItem();
			items[i].setKey(convertKeys[i]);
			items[i].setShow(true);
			items[i].setName(colNames[i]);
		}
		getReportBase().addHeadItem(items);
	}

	/**
	 * ��VO��ӵ�HashMap��
	 * 
	 * @param key
	 * @param vo
	 */
	private void addVoToHashmap(String key, CircularlyAccessibleValueObject vo) {
		ArrayList list = null;
		if ((list = (ArrayList) groupMap.get(key)) == null) {
			list = new ArrayList();
			list.add(vo);
			groupMap.put(key, list);
		} else
			list.add(vo);
	}

	/**
	 * @param veriSplitPane
	 */
	private void setVeriSplitEnabled(boolean enabled) {
		if (enabled) {
			veriSplitPane.setDividerLocation(200);
			veriSplitPane.setEnabled(true);
			veriSplitPane.setDividerSize(8);
			veriSplitPane.setOneTouchExpandable(true);
			getReportBase().setShowNO(true);
		} else {
			veriSplitPane.setDividerLocation(0);
			// veriSplitPane.setEnabled(false);
			veriSplitPane.setDividerSize(0);
			veriSplitPane.setOneTouchExpandable(false);
			getReportBase().setShowNO(false);
		}
	}

	/**
	 * @param strings
	 */
	private void removeHeadItems(String[] strs) {
		ReportItem[] headItems = getReportBase().getHead_Items();
		ArrayList list = new ArrayList();
		if (headItems != null && headItems.length > 0) {
			for (int i = 0; i < headItems.length; i++) {
				if (contains(strs, headItems[i].getKey()))
					continue;
				list.add(headItems[i]);
			}
		}
		getReportBase().setHead_Items(
				(ReportItem[]) list.toArray(new ReportItem[0]));
	}

	/**
	 * 
	 * @param source
	 * @param element
	 * @return
	 */
	public boolean contains(String[] source, String element) {
		if (source != null) {
			for (int i = 0; i < source.length; i++) {
				if (source[i].equals(element))
					return true;
			}
		}
		return false;
	}

	/**
	 * ���ص�ǰUI��Ӧ�Ŀ�����ʵ����
	 * 
	 * @return nc.ui.tm.pub.ControlBase
	 */
	public IReportCtl getUIControl() {
		if (m_uiCtl == null)
			m_uiCtl = createIReportCtl();
		return m_uiCtl;
	}

	/**
	 * Ĭ�Ϸ��ش����ݿ��в�ѯ���ı��������Ϣ��������Լ����ƣ�ֻ�����ش˷������ɡ�
	 * 
	 * @return nc.ui.trade.report.controller.IReportCtl
	 */
	protected IReportCtl createIReportCtl() {
		return new ReportCtl();
	}

	/**
	 * ��ӡԤ��
	 * 
	 * @throws Exception
	 */
	public void onPrintPreview() throws Exception {
		getReportBase().previewData();
	}

	/**
	 * ֱ�Ӵ�ӡ
	 */
	public void onPrintTemplet() {
		getReportBase().printData();
	}

	public CircularlyAccessibleValueObject[] getBodyDataVO() {
		return getReportBase().getBodyDataVO();
	}

	/**
	 * �������� �ܹ���ɻָ����� �������ǴӲ�ѯ���������ݼ��й���
	 * 
	 * @author guanyj
	 */
	public void onFilter(String strFomula) throws Exception {
		if (num > n) {
			n = num;
			this.dataVec = getReportBase().getBillModel().getDataVector();
		}
		if (strFomula == null || strFomula.trim().equals("")) {
			getReportBase().getBillModel().setDataVector(this.dataVec);
			MemoryResultSetMetaData mrsmd = getReportBase()
					.getReportGeneralUtil().createMeteData();
			getReportBase().getReportInfoCtrl().setMrs(
					getReportBase().getReportGeneralUtil().vector2Mrs(
							this.dataVec, mrsmd));
			return;
		}

		MemoryResultSetMetaData mrsmd = getReportBase().getReportGeneralUtil()
				.createMeteData();
		// �����ڴ�����
		Vector dataVec = getReportBase().getBillModel().getDataVector();
		if (getReportBase().getReportInfoCtrl().getMrs() == null)
			getReportBase().getReportInfoCtrl().setMrs(
					getReportBase().getReportGeneralUtil().vector2Mrs(dataVec,
							mrsmd));
		MemoryResultSet Memrs = getReportBase().getReportGeneralUtil()
				.vector2Mrs(this.dataVec, mrsmd);

		// ת���ڴ����� �Ӳ�ѯ�����Ľ�����й���
		MemoryResultSet mrs = getReportBase()
				.changeMrs_filter(Memrs, strFomula);
		// ��תΪ����
		dataVec = getReportBase().getReportGeneralUtil().mrs2Vector(mrs);
		// �����������
		getReportBase().getBillModel().setDataVector(dataVec);

		// ��dataVec������ת���س�mrs��������
		getReportBase().getReportInfoCtrl().setMrs(
				getReportBase().getReportGeneralUtil().vector2Mrs(dataVec,
						mrsmd));
		// else
		// getReportBase().filter(strFomula);
	}

	/**
	 * �����������ݡ�
	 * 
	 * @return
	 * @throws Exception
	 */
	public CircularlyAccessibleValueObject[] getAllBodyDataVO() {
		if (allBodyDataVO == null) {
			try {
				allBodyDataVO = getVOFromUI();
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
			}
		}
		return allBodyDataVO;
	}

	/**
	 * ������ʾ�������ݣ��������⣬����˳���Լ�������ʾ����
	 * 
	 * @param title
	 *            ����ʾ����
	 * @param fieldNames
	 *            ����keyֵ�����ᰴ������˳����ʾ
	 * @param showNames
	 *            ��Ӧkey����ʾ���ƣ���Ϊ�գ��ռ���Ϊ��������ʾ���ơ������key�ĳ��Ȳ�һ�£�Ҳ��ΪΪ��
	 * @throws Exception
	 */
	public void onColumnFilter(String title, String[] fieldNames,
			String[] showNames, boolean isAdjustOrder) throws Exception {
		CircularlyAccessibleValueObject[] vos = getBodyDataVO();
		getReportBase().hideColumn(getAllColumnCodes());

		// ����ģ���б���
		getReportBase().setReportTitle(title);
		// ������ʾ����
		setTitleText(title);
		getReportBase().showHiddenColumn(fieldNames);
		if (isAdjustOrder)
			setColumnOrder(fieldNames);
		if (showNames != null && showNames.length == fieldNames.length)
			setColumnName(fieldNames, showNames);
		// setBodyDataVO(vos, false);//edit guanyj ������Ŀ���ñ������ݲ����£� ����Ҫ������ʾ��ʽ
		setBodyDataVO(vos, true);
	}

	/**
	 * �õ����������еı���
	 * 
	 * @return java.lang.String[]
	 */
	protected String[] getAllColumnCodes() {
		nc.vo.pub.report.ReportModelVO[] vos = getReportBase().getAllBodyVOs();
		String[] names = new String[vos.length];
		for (int i = 0; i < vos.length; i++) {
			names[i] = vos[i].getColumnCode();
		}
		return names;
	}

	/**
	 * ����������ñ�����е�˳��
	 * 
	 */
	protected void setColumnOrder(String[] column_codes) {
		ReportItem[] items = getReportBase().getBody_Items();

		ArrayList al = new ArrayList();
		HashMap tmpHas = new HashMap();
		for (int i = 0; i < items.length; i++) {
			tmpHas.put(items[i].getKey(), items[i]);
		}

		for (int i = 0; i < column_codes.length; i++) {
			if (tmpHas.containsKey(column_codes[i])) {
				al.add(tmpHas.get(column_codes[i]));
				tmpHas.remove(column_codes[i]);
			}

		}
		al.addAll(tmpHas.values());

		ReportItem[] newitems = (ReportItem[]) al.toArray(new ReportItem[0]);
		getReportBase().setBody_Items(newitems);

	}

	/**
	 * ���Ľ���ģ�����ʾ����
	 * 
	 * @param fieldNames
	 * @param showNames
	 */
	private void setColumnName(String[] fieldNames, String[] showNames) {
		ReportItem[] items = getReportBase().getBody_Items();
		HashMap tmpHas = new HashMap();
		for (int i = 0; i < items.length; i++) {
			tmpHas.put(items[i].getKey(), items[i]);
		}

		for (int i = 0; i < fieldNames.length; i++) {
			if (tmpHas.containsKey(fieldNames[i])) {
				ReportItem tmpItem = (ReportItem) tmpHas.get(fieldNames[i]);
				if (!tmpItem.getName().equals(showNames[i]))
					tmpItem.setName(showNames[i]);
			}

		}
		getReportBase().setBody_Items(items);
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ���÷���ϼƵ�Ĭ��ֵ ���Զ����з���ϼ�
	 * @ʱ�䣺2011-7-12����03:10:29
	 * @param strings
	 *            Ĭ�Ϸ����ֶ�
	 * @param combinFields
	 *            ��Ҫ�ϼ��ֶ�
	 * @throws Exception
	 */
	protected void setDefSubtotal(String[] strings, String[] combinFields)
			throws Exception {
		// ���÷���ϼ�Ĭ��ֵ
		getSubtotalAction().setIssub(true);
		getSubtotalAction().setIssum(true);
		if (strings == null || strings.length == 0) {
			throw new BusinessException("�����ֶβ�����Ϊ��");
		}
		if (combinFields == null || combinFields.length == 0) {
			throw new BusinessException("�ϼ��ֶβ�����Ϊ��");
		}
		TableField[] fs = TableField.create(strings);
		TableField[] fs1 = TableField.create(combinFields);
		getSubtotalAction().setSubTotalCurrentUsingGroupFields(fs);
		getSubtotalAction().setSubTotalCurrentUsingTotalFields(fs1);
		this.setBodyDataForSub();// �����������еĺϼ� С����ȥ
		getSubtotalAction().executeSubTotal();
	}

	/**
	 * �ӽ�����ʾ���ֶ���ѡ����ݡ�type����Ӧ���͵��ֶ�
	 * 
	 * @param type
	 * @return TableField[]
	 */
	public TableField[] getVisibleFieldsByDataType(Integer type) {
		TableField[] visibleFields = getVisibleFields();
		ArrayList<TableField> al = new ArrayList<TableField>();
		nc.vo.pub.report.ReportModelVO[] vos = getModelVOs();// getReportBase().getAllBodyVOs();
		for (int i = 0; i < vos.length; i++) {
			if (vos[i].getDataType().equals(type)) {
				TableField f = createTableFieldFromReportModelVO(vos[i]);
				if (Arrays.asList(visibleFields).contains(f)) {
					al.add(f);
				}
			}
		}
		return (TableField[]) al.toArray(new TableField[0]);
	}

	/**
	 * �õ�����ģ�������ص���(select_type == 1,����û�ڽ�������ʾ���С� select_type==2
	 * ���б���Ϊ�����������С��ų�����)
	 * 
	 * @return nc.vo.tm.report.TableField[]
	 */
	public TableField[] getInvisibleFields() {
		nc.vo.pub.report.ReportModelVO[] vos = getModelVOs();// getReportBase().getAllBodyVOs();
		ArrayList invisible = new ArrayList();
		for (int i = 0; i < vos.length; i++) {
			if (vos[i].getSelectType().intValue() == IReportModelSelectType.VISIBLE
					.intValue()) {
				try {
					getReportBase().getBillTable().getColumn(
							vos[i].getColumnUser());

				}
				// �׳��쳣��˵���������Ѿ�����
				catch (Exception e) {
					invisible.add(createTableFieldFromReportModelVO(vos[i]));
				}

			}
		}

		TableField[] invisibleFields = (TableField[]) invisible
				.toArray(new TableField[0]);

		return invisibleFields;
	}

	/**
	 * ��ý�������ʾ�������ֶ�
	 * 
	 * @return TableField[]
	 */
	public TableField[] getVisibleFields() {
		nc.vo.pub.report.ReportModelVO[] vos = getModelVOs();// getReportBase().getAllBodyVOs();
		ArrayList<TableField> visible = new ArrayList<TableField>();
		for (int i = 0; i < vos.length; i++) {
			if (vos[i].getSelectType().intValue() == IReportModelSelectType.VISIBLE
					.intValue()) {
				try {
					getReportBase().getBillTable().getColumn(
							vos[i].getColumnUser());
					visible.add(createTableFieldFromReportModelVO(vos[i]));
				}
				// �ô��쳣��Ӧ���׳�
				catch (Exception e) {
					// showErrorMessage("����" + e.getMessage());
				}
			}
		}

		TableField[] visibleFields = (TableField[]) visible
				.toArray(new TableField[0]);

		return visibleFields;
	}

	/**
	 * 
	 * @param context
	 *            С�ƺϼ�������
	 * @throws Exception
	 */
	public void onSubTotal(SubtotalContext context) throws Exception {
		getReportBase().setSubtotalContext(context);
		getReportBase().subtotal();
	}

	/**
	 * ����ģ������VO��Ŀǰ���ص��Ǳ���������VO����ģ�����ṩ����Ӧ���ܺ���ֱ�ӵ���ģ�������Ӧ����
	 * 
	 * @return ģ��modelVO
	 */
	public ReportModelVO[] getModelVOs() {
		return copyOfReportModelVOs; // getReportBase().getAllBodyVOs();
	}

	protected TableField createTableFieldFromReportModelVO(
			nc.vo.pub.report.ReportModelVO vo) {
		return new TableField(convertReportModelFieldNameToVOFieldName(vo
				.getColumnCode()), vo.getColumnUser(), vo.getDataType()
				.intValue() == 1
				|| vo.getDataType().intValue() == 2);
	}

	/**
	 * ����'_'�ָ���ReportModel��fieldNameת��Ϊ��'.'�ָ���VOFieldName
	 * 
	 * @return java.lang.String
	 * @param reportFieldName
	 *            java.lang.String
	 */
	protected String convertReportModelFieldNameToVOFieldName(
			String reportFieldName) {
		if (reportFieldName.indexOf('_') == -1)
			return reportFieldName;
		else
			return reportFieldName.substring(0, reportFieldName.indexOf('_'))
					+ "."
					+ reportFieldName.substring(
							reportFieldName.indexOf('_') + 1, reportFieldName
									.length());
	}

	/**
	 * 
	 * @param keys
	 * @return
	 */
	public String[] convertVOKeysToModelKeys(String[] keys) {
		if (keys == null || keys.length == 0)
			return null;
		String[] convertedKeys = new String[keys.length];
		for (int i = 0; i < keys.length; i++) {
			convertedKeys[i] = convertVOFieldNameToReportModelFieldName(keys[i]);
		}
		return convertedKeys;
	}

	/**
	 * ����'.'�ָ���VOFieldNameת��Ϊ��'_'�ָ���ReportModel��fieldName
	 * 
	 * @return java.lang.String
	 * @param voFieldName
	 *            java.lang.String
	 */
	public String convertVOFieldNameToReportModelFieldName(String voFieldName) {
		if (voFieldName.indexOf('.') == -1)
			return voFieldName;
		else
			return voFieldName.substring(0, voFieldName.indexOf('.'))
					+ "_"
					+ voFieldName.substring(voFieldName.indexOf('.') + 1,
							voFieldName.length());
	}

	/**
	 * ������
	 */
	public void onCross(String[] rows, String[] cols, String[] values)
			throws Exception {
		getReportBase().drawCrossTable(rows, cols, values);
	}

	public void setBodyDataForSub() {
		int num = 0;
		boolean isAdd = false;

		CircularlyAccessibleValueObject[] tmpVOs = getReportBase()
				.getBodyDataVO();
		CircularlyAccessibleValueObject[] tmpBodyVOs = new CircularlyAccessibleValueObject[tmpVOs.length];

		for (int i = 0; i < tmpVOs.length; i++) {

			String[] names = tmpVOs[i].getAttributeNames();
			for (int j = 0; j < names.length; j++) {

				if (tmpVOs[i].getAttributeValue(names[j]) == "�����ϼơ���"
						|| tmpVOs[i].getAttributeValue(names[j]) == "����С�ơ���") {
					num++;
					isAdd = true;
					continue;
				}

			}
			if (isAdd) {
				isAdd = false;
				continue;
			}
			tmpBodyVOs[i - num] = tmpVOs[i];
		}
		CircularlyAccessibleValueObject[] bodyVOs = new CircularlyAccessibleValueObject[tmpVOs.length
				- num];

		for (int i = 0; i < bodyVOs.length; i++) {
			bodyVOs[i] = tmpBodyVOs[i];
		}
		getReportBase().setBodyDataVO(bodyVOs);
	}

	/**
	 * ��ȡ����Ľ���ֶ�KEY
	 * 
	 * created by chenliang at 2007-9-23 ����10:05:45
	 * 
	 * @return
	 */
	private String[] getMnyFlds() {
		BillModel bm = getReportBase().getBillModel();
		BillItem[] items = bm.getBodyItems();
		if (items == null || items.length == 0)
			return null;
		List<String> mnyFldList = new ArrayList<String>();
		for (int i = 0; i < items.length; i++) {
			int idatatype = items[i].getDataType();
			if (idatatype == IBillItem.DECIMAL) {
				String mnyFld = items[i].getKey();
				mnyFldList.add(mnyFld);
			}
		}
		if (mnyFldList.size() == 0)
			return null;
		return (String[]) mnyFldList.toArray(new String[0]);
	}

	/**
	 * ���ý���ֶε���ʾ����
	 */
	protected void setDefaultDecimalDigits() {
		BillModel bm = getReportBase().getBillModel();
		BillItem[] items = bm.getBodyItems();
		if (items == null || items.length == 0)
			return;
		for (int i = 0; i < items.length; i++) {
			int idatatype = items[i].getDataType();
			if (idatatype == IBillItem.DECIMAL) {
				items[i].setDecimalDigits(new Integer(8).intValue());
			}
		}

	}

	/**
	 * ���ý���ֶε���ʾ����
	 */
	protected void setDecimalDigits() {
		BillModel bm = getReportBase().getBillModel();
		BillItem[] items = bm.getBodyItems();
		if (items == null || items.length == 0)
			return;
		for (int i = 0; i < items.length; i++) {
			int idatatype = items[i].getDataType();
			if (idatatype == IBillItem.DECIMAL && isMnyItem(items[i].getKey())) {

				// ///////////////////------------------------------------------zpmע������������Ĭ�Ͼ���
				// Ϊ 8-------------??????????????????????????????????
				items[i].setDecimalDigits(4);
			}
		}

	}

	protected boolean isMnyItem(String key) {
		// TODO Auto-generated method stub
		if (vecNotMnyFlds == null) {
			vecNotMnyFlds = new Vector<String>();
			if (getPriceShowNum() != null && getPriceShowNum().length > 0)
				for (int i = 0; i < getPriceShowNum().length; i++)
					vecNotMnyFlds.add(getPriceShowNum()[i]);
			if (getQuantityShowNum() != null && getQuantityShowNum().length > 0)
				for (int i = 0; i < getQuantityShowNum().length; i++)
					vecNotMnyFlds.add(getQuantityShowNum()[i]);
			if (getOtherItemDigitShowNum() != null
					&& getOtherItemDigitShowNum().length == 2) {
				for (int i = 0; i < getOtherItemDigitShowNum()[0].length; i++)
					vecNotMnyFlds.add(getOtherItemDigitShowNum()[0][i]);
			}
		}
		return !vecNotMnyFlds.contains(key);
	}

	/**
	 * ˢ�±����ʹ���ȱ仯��Ч
	 * 
	 * created by chenliang at 2007-9-23 ����09:52:31
	 */
	protected void updateBodyDigits() {
		setDecimalDigits();
		// ���õ��ۺ������ľ��� ,add by wenming 2008-1-21
		setPriceAndQuantityDecimalDigits();
		setOtherDecimalDigtis();
		BillModel bm = getReportBase().getBillModel();
		int rowCount = bm.getRowCount();
		if (rowCount <= 0)
			return;

		String[] mnyFlds = getMnyFlds();
		if (mnyFlds == null || mnyFlds.length == 0)
			return;
		for (int row = 0; row < rowCount; row++) {
			for (int j = 0; j < mnyFlds.length; j++) {
				bm.setValueAt(bm.getValueAt(row, mnyFlds[j]), row, mnyFlds[j]);
			}
		}

	}

	/**
	 * 2008-04-26���������ֶξ���
	 */
	protected void setOtherDecimalDigtis() {
		// ��Ҫ���þ��ȵ��ֶ�
		if (getOtherItemDigitShowNum() != null
				&& getOtherItemDigitShowNum().length == 2) {
			String[] bodyotheritems = getOtherItemDigitShowNum()[0];
			// ��Ҫ���õľ���
			String[] bodyotheritemsdigi = getOtherItemDigitShowNum()[1];

			// ������������
			if (bodyotheritems != null && bodyotheritems.length > 0
					&& bodyotheritems.length == bodyotheritemsdigi.length) {

				for (int j = 0; j < bodyotheritems.length; j++) {
					if (!isNull(bodyotheritems[j])) {
						int digitint = isNull(bodyotheritemsdigi[j]) ? 2
								: new Integer(bodyotheritemsdigi[j]);
						String attrName = bodyotheritems[j];
						BillItem item = getReportBase().getBillModel()
								.getItemByKey(attrName);
						if (item != null)
							item.setDecimalDigits(digitint);
					}

				}
			}
		}
	}

	// ������Ҫ�O�õ������ֶεľ��Ȍ������M 0�S���ֶΣ�1�S�Ǿ���
	protected String[][] getOtherItemDigitShowNum() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @see nc.ui.trade.card.BillCardUI#getCardItemShowNum() ���ߣ�wenming
	 * ���õ��۾���һά���� �������е���,��Ҫ�����Լ��ľ���,��д�˷��� �������ڣ�2008-1-21 11:16:58 @return
	 */
	public String[] getPriceShowNum() {

		return null;
	}

	/*
	 * @see nc.ui.trade.card.BillCardUI#getCardItemShowNum() ���ߣ�wenming ������������
	 * ������������,��Ҫ�����Լ��ľ���,��д�˷��� ���ߣ����� �������ڣ�2008-1-21 ����09:40:47 @return
	 */
	public String[] getQuantityShowNum() {

		return null;
	}

	/**
	 * 
	 * ���ߣ����� �������ڣ�2008-1-21 ����03:03:04
	 */
	protected void setDefaultPriceAndQuantityDecimalDigits() {
		// ��������
		String[] bodyunitpriceitems = getPriceShowNum();
		String[] bodyquantityitems = getQuantityShowNum();

		// ���õ��۾���
		if (bodyunitpriceitems != null && bodyunitpriceitems.length > 0) {

			for (int j = 0; j < bodyunitpriceitems.length; j++) {
				if (!isNull(bodyunitpriceitems[j])) {
					String attrName = bodyunitpriceitems[j];
					getReportBase().getBillModel().getItemByKey(attrName)
							.setDecimalDigits(new Integer(8).intValue());
				}

			}
		}
		// ������������
		if (bodyquantityitems != null && bodyquantityitems.length > 0) {
			for (int j = 0; j < bodyquantityitems.length; j++) {
				if (!isNull(bodyquantityitems[j])) {
					String attrQuantityName = bodyquantityitems[j];
					getReportBase().getBillModel().getItemByKey(
							attrQuantityName).setDecimalDigits(
							new Integer(8).intValue());
				}
			}
		}
	}

	/**
	 * 
	 * ���õ��ۡ����� ����
	 */
	protected void setPriceAndQuantityDecimalDigits() {
	}

	/**
	 * ���ַ�����Ϊ�ա�
	 */
	protected boolean isNull(Object o) {
		if (o == null || o.toString() == null
				|| o.toString().trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ˵����
	 * 
	 * @Version��
	 * @DateTime 2008-2-22 ����11:05:37
	 * @Authors��ssd
	 * @return unlockSortListener
	 */
	public Boolean getUnlockSortListener() {
		return unlockSortListener;
	}

	/**
	 * ˵����
	 * 
	 * @Version��
	 * @DateTime 2008-2-22 ����11:05:38
	 * @Authors��ssd
	 * @param unlockSortListener
	 */
	public void setUnlockSortListener(Boolean unlockSortListener) {
		this.unlockSortListener = unlockSortListener;
	}

	/**
	 * ��͸�ڵ���Ϣ�ӿ�ʵ�֣�����Ҫ��͸����ͬĿ��ʱ����Ҫ���ظ÷����� ���ߣ�zhourj
	 */
	public String getPenetrateObj() {
		return null;
	}

}
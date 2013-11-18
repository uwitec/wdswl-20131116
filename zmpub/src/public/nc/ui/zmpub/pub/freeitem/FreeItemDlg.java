package nc.ui.zmpub.pub.freeitem;

import java.util.ArrayList;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelListener;

import nc.bs.logging.Logger;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITextField;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.BillScrollPane;
import nc.vo.zmpub.pub.freeitem.DefdefHeaderVO;
import nc.vo.zmpub.pub.freeitem.IUiSizeDef;

/**
 * �˴���������˵���� �������ڣ�(2000-5-8 ���� 5:13)
 * 
 * @author��������
 */
public class FreeItemDlg extends UIDialog implements ListSelectionListener,
		javax.swing.event.TableModelListener, TableColumnModelListener,
		BillEditListener2 {
	private BillScrollPane ivjBillScrollPane1 = null;
	private javax.swing.JPanel ivjUIDialogContentPane = null;
	private UIButton ivjExitButton = null;
	private UIButton ivjOKButton = null;
	private java.lang.String m_sInventoryName = "01 ���� ��ʽ ��������";
	private int m_iFreeItemNo = 2;
	private ArrayList m_alFreeItemName = new ArrayList();
	private ArrayList m_alFreeItemNameStatus = new ArrayList();
	private ArrayList m_alFreeItemValue = new ArrayList();
	private String[][] m_sReturnFreeItem;
	private BillModel ivjBillModel1 = null;
	private final java.lang.Object[] m_oNames = { "����������", "������ֵ" }; // �����ֶ���
	private BillItem[] m_biBodyItems = new BillItem[m_oNames.length];
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	private UILabel ivjInventoryNameLabel = null;
	private UITextField ivjInventoryNameTField = null;
	private UIRefPane m_urpUIRefPane = null;
	private FreeItemCellEditor[] m_bceBillCellEditor = new FreeItemCellEditor[10];

	class IvjEventHandler implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == FreeItemDlg.this.getOKButton())
				connEtoC2(e);
			if (e.getSource() == FreeItemDlg.this.getExitButton())
				connEtoC3(e);
		};
	};

	// private boolean OKButtonEnabled = false;//ȷ��OKButton�Ƿ����
	/**
	 * FreeItemDlg ������ע�⡣
	 */
	public FreeItemDlg() {
		super();
		initialize();
	}

	/**
	 * FreeItemDlg ������ע�⡣
	 * 
	 * @param parent
	 *            java.awt.Container
	 */
	public FreeItemDlg(java.awt.Container parent) {
		super(parent);
		initialize();
	}

	/**
	 * FreeItemDlg ������ע�⡣
	 * 
	 * @param parent
	 *            java.awt.Container
	 * @param title
	 *            java.lang.String
	 */
	public FreeItemDlg(java.awt.Container parent, String title) {
		super(parent, title);
		initialize();
	}

	/**
	 * FreeItemDlg ������ע�⡣
	 * 
	 * @param owner
	 *            java.awt.Frame
	 */
	public FreeItemDlg(java.awt.Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * FreeItemDlg ������ע�⡣
	 * 
	 * @param owner
	 *            java.awt.Frame
	 * @param title
	 *            java.lang.String
	 */
	public FreeItemDlg(java.awt.Frame owner, String title) {
		super(owner, title);
		initialize();
	}

	/**
	 * �༭ǰ���� �������ڣ�(2001-3-23 2:02:27)
	 * 
	 * @param e
	 *            ufbill.BillEditEvent
	 */
	public boolean beforeEdit(nc.ui.pub.bill.BillEditEvent e) {
		int rownow = e.getRow();
		int colnow = getBillScrollPane1().getTable().getSelectedColumn();

		switch (colnow) {
		case 0:
			break;
		case 1:
			Object oValue = getBillScrollPane1().getTableModel().getValueAt(
					rownow, 1);
			getBillScrollPane1().getTable().getColumnModel().getColumn(1)
					.setCellEditor(m_bceBillCellEditor[rownow]);
			if (getBillScrollPane1().getTableModel().getBodyItems()[1]
					.getComponent() instanceof UIRefPane) {
				((UIRefPane) getBillScrollPane1().getTableModel()
						.getBodyItems()[1].getComponent())
						.setValue(oValue == null ? null : oValue.toString());
				((UIRefPane) getBillScrollPane1().getTableModel()
						.getBodyItems()[1].getComponent())
						.setText(oValue == null ? null : oValue.toString());
			}
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-23 ���� 2:21) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	protected void checkOKButton() {
		// for (int i= 0; i < getBillModel1().getRowCount(); i++) {
		for (int i = 0; i < getBillScrollPane1().getTableModel().getRowCount(); i++) {
			if ((null == getBillScrollPane1().getTableModel().getValueAt(i, 1))
					|| (getBillScrollPane1().getTableModel().getValueAt(i, 1)
							.toString().trim().equals(""))) {
				// getOKButton().setEnabled(false);
				return;
			}
			// getOKButton().setEnabled(true);
		}
	}

	/**
	 * �\n�����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-10 ���� 9:21) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public void closeCancel() {
		setResult(ID_CANCEL);
		close();
	}

	/**
	 * �\n�����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-10 ���� 9:22) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public void closeOK() {
		m_sReturnFreeItem = null;
		m_sReturnFreeItem = new String[m_iFreeItemNo][3];

		// getBillScrollPane1().getTableModel().fireTableRowsUpdated(
		// 0,
		// getBillScrollPane1().getTableModel().getRowCount() - 1);
		if (getBillScrollPane1().getTable().isEditing()) {
			getBillScrollPane1().getTable().editingStopped(null);
		}

		// boolean bAllDataOKorNotFill = true;
		Object oOldValue = null;
		Object oNewValue = null;
		for (int i = 0; i < getBillScrollPane1().getTableModel().getRowCount(); i++) {
			oNewValue = null;
			oNewValue = getBillScrollPane1().getTableModel().getValueAt(i, 1);
			if (i > 0) {
				if ((null == oOldValue || oOldValue.toString().trim().length() == 0)
						&& (null != oNewValue && oNewValue.toString().trim()
								.length() > 0)) {
					return;
				}
				if ((null != oOldValue && oOldValue.toString().trim().length() > 0)
						&& (null == oNewValue || oNewValue.toString().trim()
								.length() == 0)) {
					return;
				}
			}
			oOldValue = oNewValue;
		}

		for (int i = 0; i < m_iFreeItemNo; i++) {
			m_sReturnFreeItem[i][0] = getBillScrollPane1().getTableModel()
					.getValueAt(i, 0).toString();
			if (null == getBillScrollPane1().getTableModel().getValueAt(i, 1)) {
				m_sReturnFreeItem[i][1] = null;
				m_sReturnFreeItem[i][2] = null;
			} else {
				if (m_bceBillCellEditor[i].getComponent().getClass() == UIRefPane.class) {
					// m_sReturnFreeItem[i][1]=
					// ((UIRefPane)
					// m_bceBillCellEditor[i].getComponent()).getRefPK().toString();
					// m_sReturnFreeItem[i][2]=
					// ((UIRefPane)
					// m_bceBillCellEditor[i].getComponent()).getRefName().toString();
					m_sReturnFreeItem[i][1] = getBillScrollPane1()
							.getTableModel().getValueAt(i, 1).toString();
					m_sReturnFreeItem[i][2] = getBillScrollPane1()
							.getTableModel().getValueAt(i, 1).toString();
				} else {
					m_sReturnFreeItem[i][1] = getBillScrollPane1()
							.getTableModel().getValueAt(i, 1).toString();
					m_sReturnFreeItem[i][2] = getBillScrollPane1()
							.getTableModel().getValueAt(i, 1).toString();
				}
			}
		}

		// getOKButton().setEnabled(false);
		// this.hide();
		setResult(ID_OK);
		close();
	}

	/** Tells listeners that a column was added to the model. */
	public void columnAdded(javax.swing.event.TableColumnModelEvent e) {
	}

	/** Tells listeners that a column was moved due to a margin change. */
	public void columnMarginChanged(javax.swing.event.ChangeEvent e) {
	}

	/** Tells listeners that a column was repositioned. */
	public void columnMoved(javax.swing.event.TableColumnModelEvent e) {
	}

	/** Tells listeners that a column was removed from the model. */
	public void columnRemoved(javax.swing.event.TableColumnModelEvent e) {
	}

	/**
	 * ����������������� Ŀ�ģ��༭���������ʱfocusʼ���ڵڶ����л��� �޸��ߣ��۱� �޸�ʱ�䣺2005-01-20
	 * 
	 * Tells listeners that the selection model of the TableColumnModel changed.
	 */
	public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e) {
		int rownow = getBillScrollPane1().getTable().getSelectedRow();
		int colnow = getBillScrollPane1().getTable().getSelectedColumn();

		switch (colnow) {
		case 0:
			// focusʼ�ղ����ڵ�һ�С�
			getBillScrollPane1().getTable().setRowSelectionInterval(rownow,
					rownow);
			getBillScrollPane1().getTable().setColumnSelectionInterval(1, 1);
			break;
		case 1:
			int rowCount = getBillScrollPane1().getTable().getRowCount();
			if (rownow == rowCount - 1) {
				getBillScrollPane1().getTable().transferFocus();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * connEtoC2: (OKButton.action.actionPerformed(java.awt.event.ActionEvent)
	 * --> FreeItemDlg.oKButton_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
	 * 
	 * @param arg1
	 *            java.awt.event.ActionEvent
	 */
	/* ���棺�˷������������ɡ� */
	private void connEtoC2(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.onOK(arg1);
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoC3: (ExitButton.action.actionPerformed(java.awt.event.ActionEvent)
	 * -->
	 * FreeItemDlg.exitButton_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
	 * 
	 * @param arg1
	 *            java.awt.event.ActionEvent
	 */
	/* ���棺�˷������������ɡ� */
	private void connEtoC3(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.onCancel(arg1);
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connPtoP1SetTarget: (BillModel1.this <--> BillScrollPane1.tableModel)
	 */
	/* ���棺�˷������������ɡ� */
	private void connPtoP1SetTarget() {
		/* ��Դ����Ŀ�� */
		try {
			getBillScrollPane1().setTableModel(getBillModel1());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * ���� BillModel1 ����ֵ��
	 * 
	 * @return nc.ui.pub.bill.BillModel
	 */
	/* ���棺�˷������������ɡ� */
	private nc.ui.pub.bill.BillModel getBillModel1() {
		if (ivjBillModel1 == null) {
			try {
				ivjBillModel1 = new nc.ui.pub.bill.BillModel();
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjBillModel1;
	}

	/**
	 * ���� BillScrollPane1 ����ֵ��
	 * 
	 * @return nc.ui.pub.bill.BillScrollPane
	 */
	/* ���棺�˷������������ɡ� */
	private nc.ui.pub.bill.BillScrollPane getBillScrollPane1() {
		if (ivjBillScrollPane1 == null) {
			try {
				ivjBillScrollPane1 = new nc.ui.pub.bill.BillScrollPane();
				ivjBillScrollPane1.setAutoAddLine(false);
				ivjBillScrollPane1.setName("BillScrollPane1");
				ivjBillScrollPane1.setBounds(27, 45, 208, 206);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjBillScrollPane1;
	}

	/**
	 * ���� ExitButton ����ֵ��
	 * 
	 * @return nc.ui.pub.beans.UIButton
	 */
	/* ���棺�˷������������ɡ� */
	private nc.ui.pub.beans.UIButton getExitButton() {
		if (ivjExitButton == null) {
			try {
				ivjExitButton = new nc.ui.pub.beans.UIButton();
				ivjExitButton.setName("ExitButton");
				ivjExitButton.setText(nc.ui.ml.NCLangRes.getInstance()
						.getStrByID("common", "UC001-0000008")/* @res "ȡ��" */);
				// ivjExitButton.setLocation(165, 255);
				ivjExitButton.setBounds(this.getWidth()
						- IUiSizeDef.BTN_X_CANCEL, 255, IUiSizeDef.BTN_WIDTH,
						IUiSizeDef.BTN_HEIGHT);

				// user code begin {1}
				ivjExitButton.addKeyListener(new KeyCancelListener());
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjExitButton;
	}

	/**
	 * ���� CabNameLabel ����ֵ��
	 * 
	 * @return nc.ui.pub.beans.UILabel
	 */
	/* ���棺�˷������������ɡ� */
	private nc.ui.pub.beans.UILabel getInventoryNameLabel() {
		if (ivjInventoryNameLabel == null) {
			try {
				ivjInventoryNameLabel = new nc.ui.pub.beans.UILabel();
				ivjInventoryNameLabel.setName("InventoryNameLabel");
				ivjInventoryNameLabel.setText(nc.ui.ml.NCLangRes.getInstance()
						.getStrByID("common", "UC000-0001439")/* @res "���" */);
				ivjInventoryNameLabel.setBounds(27, 12, 30, 22);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjInventoryNameLabel;
	}

	/**
	 * ���� CabNameTField ����ֵ��
	 * 
	 * @return nc.ui.pub.beans.UITextField
	 */
	/* ���棺�˷������������ɡ� */
	private nc.ui.pub.beans.UITextField getInventoryNameTField() {
		if (ivjInventoryNameTField == null) {
			try {
				ivjInventoryNameTField = new nc.ui.pub.beans.UITextField();
				ivjInventoryNameTField.setName("InventoryNameTField");
				ivjInventoryNameTField.setBounds(53, 12, 182, 20);
				ivjInventoryNameTField.setEditable(false);
				ivjInventoryNameTField.setMaxLength(100);
				// user code begin {1}
				ivjInventoryNameTField.setEnabled(false);
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjInventoryNameTField;
	}

	/**
	 * ���� OKButton ����ֵ��
	 * 
	 * @return nc.ui.pub.beans.UIButton
	 */
	/* ���棺�˷������������ɡ� */
	private nc.ui.pub.beans.UIButton getOKButton() {
		if (ivjOKButton == null) {
			try {
				ivjOKButton = new nc.ui.pub.beans.UIButton();
				ivjOKButton.setName("OKButton");
				ivjOKButton.setText(nc.ui.ml.NCLangRes.getInstance()
						.getStrByID("common", "UC001-0000044")/* @res "ȷ��" */);
				// ivjOKButton.setLocation(89, 255);
				ivjOKButton.setBounds(this.getWidth() - IUiSizeDef.BTN_X_OK,
						255, IUiSizeDef.BTN_WIDTH, IUiSizeDef.BTN_HEIGHT);

				// user code begin {1}
				ivjOKButton.addKeyListener(new KeyOKListener());
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjOKButton;
	}

	/**
	 * �\n�����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2000-5-9 ���� 2:19) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String[][]
	 */
	public String[][] getReturnFreeItem() {
		return m_sReturnFreeItem;
	}

	/**
	 * ���� UIDialogContentPane ����ֵ��
	 * 
	 * @return javax.swing.JPanel
	 */
	/* ���棺�˷������������ɡ� */
	private javax.swing.JPanel getUIDialogContentPane() {
		if (ivjUIDialogContentPane == null) {
			try {
				ivjUIDialogContentPane = new javax.swing.JPanel();
				ivjUIDialogContentPane.setName("UIDialogContentPane");
				ivjUIDialogContentPane.setLayout(null);
				getUIDialogContentPane().add(getBillScrollPane1(),
						getBillScrollPane1().getName());
				getUIDialogContentPane().add(getOKButton(),
						getOKButton().getName());
				getUIDialogContentPane().add(getExitButton(),
						getExitButton().getName());
				getUIDialogContentPane().add(getInventoryNameLabel(),
						getInventoryNameLabel().getName());
				getUIDialogContentPane().add(getInventoryNameTField(),
						getInventoryNameTField().getName());
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjUIDialogContentPane;
	}

	/**
	 * ÿ�������׳��쳣ʱ������
	 * 
	 * @param exception
	 *            java.lang.Throwable
	 */
	private void handleException(java.lang.Throwable exception) {

		/* ��ȥ���и��е�ע�ͣ��Խ�δ��׽�����쳣��ӡ�� stdout�� */
		Logger.error("--------- δ��׽�����쳣 ---------", exception);
	}

	/**
	 * ��ʼ������
	 * 
	 * @exception java.lang.Exception
	 *                �쳣˵����
	 */
	/* ���棺�˷������������ɡ� */
	private void initConnections() throws java.lang.Exception {
		// user code begin {1}
		// user code end
		getOKButton().addActionListener(ivjEventHandler);
		getExitButton().addActionListener(ivjEventHandler);
		connPtoP1SetTarget();
	}

	/**
	 * ��ʼ���ࡣ
	 */
	/* ���棺�˷������������ɡ� */
	private void initialize() {
		try {
			// user code begin {1}
			int lengthOfInput;
			if (getInventoryNameTField().getMaxLength() > m_sInventoryName
					.length()) {
				lengthOfInput = m_sInventoryName.length();
			} else {
				lengthOfInput = getInventoryNameTField().getMaxLength();
			}
			// lengthOfInput--;
			// getInventoryNameTField().setText(m_sInventoryName);
			getInventoryNameTField().setText(
					m_sInventoryName.substring(0, lengthOfInput));

			for (int i = 0; i < m_oNames.length; i++) {
				m_biBodyItems[i] = new BillItem();
				m_biBodyItems[i].setName(m_oNames[i].toString());
				m_biBodyItems[i].setKey(m_oNames[i].toString());
				m_biBodyItems[i].setWidth(100);
				m_biBodyItems[i].setEnabled(true);
				m_biBodyItems[i].setEdit(true);
			}
			m_biBodyItems[0].setDataType(BillItem.STRING);
			m_biBodyItems[0].setEdit(false);
			m_biBodyItems[0].setEnabled(false);
			m_biBodyItems[1].setDataType(BillItem.STRING);
			// m_biBodyItems[1].setDataType(BillItem.UFREF);
			// m_biBodyItems[1].setRefType("������");

			getBillModel1().setBodyItems(m_biBodyItems);
			// getBillScrollPane1().getTable().getSelectionModel().addListSelectionListener(
			// this);
			// default test code below
			m_alFreeItemName = new ArrayList();
			ArrayList alTemp = new ArrayList();
			alTemp.add(new Integer(ConsultType.InherentConsult));
			alTemp.add("���ŵ���");
			m_alFreeItemName.add(alTemp);
			alTemp = new ArrayList();
			alTemp.add(new Integer(ConsultType.InherentConsult));
			alTemp.add("��Ա����");
			m_alFreeItemName.add(alTemp);

			// test code end

			// user code end
			setName("FreeItemDlg");
			// setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
			setResizable(false);
			setSize(267, 321);
			setTitle("���������");
			setContentPane(getUIDialogContentPane());
			initConnections();
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
		// user code begin {2}
		this.setFreeItemName(m_alFreeItemName);

		getBillScrollPane1().addEditListener2(this);
		getBillScrollPane1().getTable().getColumnModel()
				.addColumnModelListener(this);
		getBillScrollPane1().getTable().addKeyListener(new IvjKeyListener());

		// getBillScrollPane1().getTable().getSelectionModel().addListSelectionListener(this);
		// getBillScrollPane1().getTableModel().addTableModelListener(this);

		// user code end
	}

	/**
	 * Comment
	 */
	public void onCancel(java.awt.event.ActionEvent actionEvent) {
		closeCancel();
		// this.hide();
		return;
	}

	/**
	 * Comment
	 */
	public void onOK(java.awt.event.ActionEvent actionEvent) {
		closeOK();
		return;
	}

	/**
	 * ���� �����������,�����ǲ�������
	 * 
	 * 
	 * 
	 * �������ڣ�(2000-5-8 ���� 6:05)
	 * 
	 * @param newCabName
	 *            java.lang.String
	 */
	public void setFreeItemName(ArrayList alFreeItemDef) {
		try {
			if (null == alFreeItemDef || alFreeItemDef.size() == 0)
				return;
			m_iFreeItemNo = alFreeItemDef.size();

			getBillScrollPane1().getTableModel().removeTableModelListener(this);
			m_alFreeItemNameStatus = null;
			m_alFreeItemNameStatus = alFreeItemDef;
			// �õ����������Ʊ�
			m_alFreeItemName = null;
			m_alFreeItemName = new ArrayList();
			ArrayList itemLabelName = new ArrayList();
			for (int i = 0; i < m_iFreeItemNo; i++) {
				if (null == alFreeItemDef.get(i)) {
					m_alFreeItemName.add(null);
					continue;
				}
				m_alFreeItemName.add(((ArrayList) alFreeItemDef.get(i)).get(
						IFreeItemParamSeqDef.DEF_REF_NAME).toString().trim());
				// ������ ���������Label��ʾ 2009-05-06
				if (((ArrayList) alFreeItemDef.get(i)).get(
						IFreeItemParamSeqDef.RET_TYPE).toString().trim()
						.equals("1")) {
					itemLabelName.add(((ArrayList) alFreeItemDef.get(i)).get(
							IFreeItemParamSeqDef.ITEM_NAME).toString().trim());
				} else {
					itemLabelName.add(((ArrayList) alFreeItemDef.get(i)).get(
							IFreeItemParamSeqDef.DEF_REF_NAME).toString()
							.trim());
				}

			}

			// ����
			int totalRow = getBillScrollPane1().getTable().getRowCount();
			for (int i = totalRow; i > 0; i--) {
				getBillScrollPane1().getTableModel().removeRow(i - 1);
			}
			int iRefType = ConsultType.NoConsult;// ��������

			// �������ƺͲ���
			for (int i = 0; i < m_iFreeItemNo; i++) {
				if ((null == m_alFreeItemName.get(i))
						|| (m_alFreeItemName.get(i).toString().trim().length() == 0)) {
					continue;
				}
				getBillScrollPane1().getTableModel().addLine();
				// ��һ�������ơ�
				getBillScrollPane1().getTableModel().setValueAt(
						itemLabelName.get(i).toString().trim(), i, 0);

				iRefType = Integer.parseInt(((ArrayList) alFreeItemDef.get(i))
						.get(IFreeItemParamSeqDef.RET_TYPE).toString().trim());
				if (iRefType == ConsultType.NoConsult) {
					Logger.error("NoConsult");
					m_urpUIRefPane = null;
					m_urpUIRefPane = new UIRefPane();
					m_urpUIRefPane.setButtonVisible(false);
					DefdefHeaderVO dhvo = (DefdefHeaderVO) ((ArrayList) alFreeItemDef
							.get(i)).get(IFreeItemParamSeqDef.DEF_REF_PK);
					String sTypeName = dhvo.getType().trim();
					if (sTypeName.equals("����") || sTypeName.equals("����")) {
						m_urpUIRefPane.setButtonVisible(true);
						m_urpUIRefPane.setRefNodeName("����");
						m_urpUIRefPane.setReturnCode(false);
						m_urpUIRefPane.setAutoCheck(true);
					} else if (sTypeName.equals("����")) {
						m_urpUIRefPane.setButtonVisible(false);
						m_urpUIRefPane.setTextType("TextDbl");// FreeItemCellEditor���õ���������á����ж����͡�
						m_urpUIRefPane.setMaxLength(dhvo.getLengthnum()
								.intValue());
						m_urpUIRefPane
								.setNumPoint(dhvo.getDigitnum() == null ? 0
										: dhvo.getDigitnum().intValue());
					}
					// if (sTypeName.equals("ͳ��")) {
					// m_urpUIRefPane.setButtonVisible(false);
					// m_urpUIRefPane.setRefNodeName(m_alFreeItemName.get(i).toString().trim());
					// m_urpUIRefPane.setReturnCode(false);
					// m_urpUIRefPane.setEditable(true);
					// m_urpUIRefPane.setMaxLength(dhvo.getLengthnum().intValue());
					// //m_urpUIRefPane.setNumPoint(dhvo.getDigitnum() == null ?
					// 0 : dhvo.getDigitnum().intValue());
					// }
					else {
						m_urpUIRefPane.setButtonVisible(false);
						m_urpUIRefPane.setMaxLength(dhvo.getLengthnum()
								.intValue());
					}
					m_urpUIRefPane.setEditable(true);
					m_bceBillCellEditor[i] = null;
					m_bceBillCellEditor[i] = new FreeItemCellEditor(
							m_urpUIRefPane);
				} else if (iRefType == ConsultType.InherentConsult) {
					Logger.error("InherentConsult");
					m_urpUIRefPane = null;
					m_urpUIRefPane = new UIRefPane();
					m_urpUIRefPane.setRefNodeName(m_alFreeItemName.get(i)
							.toString().trim());
					m_urpUIRefPane.setReturnCode(false);
					m_urpUIRefPane.setEditable(true);
					m_bceBillCellEditor[i] = null;
					m_bceBillCellEditor[i] = new FreeItemCellEditor(
							m_urpUIRefPane);
				} else if (iRefType == ConsultType.UserDefConsult) {
					Logger.error("UserDefConsult");

					m_urpUIRefPane = null;
					m_urpUIRefPane = new UIRefPane();
					m_urpUIRefPane.setRefNodeName("�Զ������");
					// 2005-01-17 ��ѧ�� ���˾�
					nc.ui.bd.def.DefaultDefdocRefModel modelUserdef = new nc.ui.bd.def.DefaultDefdocRefModel();
					modelUserdef.setPkdefdef(((ArrayList) alFreeItemDef.get(i))
							.get(IFreeItemParamSeqDef.DEF_REF_PK_LIST)
							.toString().trim()); // ע���� pk_defdoclist������
													// pk_defdef
					m_urpUIRefPane.setRefModel(modelUserdef);
					m_urpUIRefPane.setReturnCode(false);
					m_urpUIRefPane.setAutoCheck(true);
					m_urpUIRefPane.setMaxLength(Integer
							.parseInt(((ArrayList) alFreeItemDef.get(i)).get(
									IFreeItemParamSeqDef.DEF_LEN).toString()
									.trim()));
					m_urpUIRefPane.setEditable(true);

					m_bceBillCellEditor[i] = null;
					m_bceBillCellEditor[i] = new FreeItemCellEditor(
							m_urpUIRefPane);
				} else {
					Logger.error("ERROR:Consult Type  is invalid." + iRefType);
				}
			}
			getBillScrollPane1().getTable().getColumnModel().getColumn(1)
					.setCellEditor(m_bceBillCellEditor[0]);

			getBillScrollPane1().getTableModel().addTableModelListener(this);
			getBillScrollPane1().getTable().setColumnSelectionInterval(1, 1);
			getBillScrollPane1().getTable().setRowSelectionInterval(0, 0);
			// getBillScrollPane1().getTable().getSelectionModel().setSelectionInterval(0,
			// 1);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}

	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2000-5-8 ���� 6:05)
	 * 
	 * @param newCabName
	 *            java.lang.String
	 */
	public void setFreeItemValue(ArrayList newalFreeItemValue) {
		try {
			if (null == newalFreeItemValue)
				return;
			int iFreeItemNotemp = newalFreeItemValue.size();
			if (iFreeItemNotemp == 0) {
				return;
			}
			getBillScrollPane1().getTableModel().removeTableModelListener(this);
			m_alFreeItemValue = null;
			m_alFreeItemValue = newalFreeItemValue;
			int totalRow = getBillScrollPane1().getTable().getRowCount();
			if (iFreeItemNotemp > totalRow)
				iFreeItemNotemp = totalRow;

			for (int i = 0; i < iFreeItemNotemp; i++) {
				if ((null == newalFreeItemValue.get(i))
						|| (null == m_bceBillCellEditor[i])) {
					continue;
				}
				if (m_bceBillCellEditor[i].getComponent().getClass() == UIRefPane.class) {
					((UIRefPane) m_bceBillCellEditor[i].getComponent())
							.setText(newalFreeItemValue.get(i).toString()
									.trim());
					((UIRefPane) m_bceBillCellEditor[i].getComponent())
							.setValue(newalFreeItemValue.get(i).toString()
									.trim());
					// �޸��ˣ������� �޸�ʱ�䣺2009-7-13 ����01:14:44
					// �޸�ԭ������������洢�������ƣ�������Ҫ������ֵ��ʱ��������ƥ������]
					if (((UIRefPane) m_bceBillCellEditor[i].getComponent())
							.getRefModel() != null)
						((UIRefPane) m_bceBillCellEditor[i].getComponent())
								.getRefModel().matchData(
										((UIRefPane) m_bceBillCellEditor[i]
												.getComponent()).getRefModel()
												.getRefNameField(),
										newalFreeItemValue.get(i).toString()
												.trim());

					getBillScrollPane1().getTableModel().setValueAt(
							newalFreeItemValue.get(i).toString().trim(), i, 1);
				} else {
					getBillScrollPane1().getTableModel().setValueAt(
							newalFreeItemValue.get(i).toString().trim(), i, 1);
				}
			}
			getBillScrollPane1().getTableModel().addTableModelListener(this);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}

	}

	/**
	 * ���� �˴����뷽��˵���� �������ڣ�(2000-5-8 ���� 6:05)
	 * 
	 * @param newCabName
	 *            java.lang.String
	 */
	public void setInventoryName(String newCode, String newInventoryName,
			String newSpec, String newType) {
		try {
			int lengthOfInput;
			if (null == newCode)
				newCode = "";
			if (null == newInventoryName)
				newInventoryName = "";
			if (null == newSpec)
				newSpec = "";
			if (null == newType)
				newType = "";
			m_sInventoryName = newCode + " " + newInventoryName + " " + newSpec
					+ " " + newType;
			if (m_sInventoryName == "")
				m_iFreeItemNo = 0;
			if (getInventoryNameTField().getMaxLength() > m_sInventoryName
					.length()) {
				lengthOfInput = m_sInventoryName.length();
			} else {
				lengthOfInput = getInventoryNameTField().getMaxLength();
			}

			getInventoryNameTField().setText(
					m_sInventoryName.substring(0, lengthOfInput));
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	}

	/**
	 * This fine grain notification tells listeners the exact range of cells,
	 * rows, or columns that changed.
	 */
	public void tableChanged(javax.swing.event.TableModelEvent e) {
		int colnow, rownow;
		colnow = e.getColumn();
		rownow = e.getFirstRow();
		getBillScrollPane1().getTableModel().removeTableModelListener(this);

		if (Integer.parseInt(((ArrayList) m_alFreeItemNameStatus.get(rownow))
				.get(0).toString().trim()) == ConsultType.NoConsult) {
			// do nothing
		} else if (Integer.parseInt(((ArrayList) m_alFreeItemNameStatus
				.get(rownow)).get(0).toString().trim()) == ConsultType.UserDefConsult) {
			getBillScrollPane1().getTableModel().setValueAt(
					((UIRefPane) m_bceBillCellEditor[rownow].getComponent())
							.getRefName(), rownow, 1);
		}

		getBillScrollPane1().getTableModel().addTableModelListener(this);
	}

	/**
	 * Called whenever the value of the selection changes.
	 * 
	 * @param e
	 *            the event that characterizes the change.
	 */
	public void valueChanged(javax.swing.event.ListSelectionEvent e) {
		int colnow, rownow;
		int thisnumber;
		try {
			getBillScrollPane1().getTableModel().removeTableModelListener(this);
			rownow = getBillScrollPane1().getTable().getEditingRow();
			colnow = getBillScrollPane1().getTable().getEditingColumn();
			// colnow= getBillScrollPane1().getTable().getSelectedColumn();
			// rownow= getBillScrollPane1().getTable().getSelectedRow();
			switch (colnow) {
			case 0:
				break;
			case 1:
				// String sValue=
				// getBillScrollPane1().getTable().getValueAt(rownow,
				// colnow).toString();
				Object oValue = getBillScrollPane1().getTableModel()
						.getValueAt(rownow, 1);
				getBillScrollPane1().getTable().getColumnModel().getColumn(1)
						.setCellEditor(m_bceBillCellEditor[rownow]);
				if (getBillScrollPane1().getTableModel().getBodyItems()[1]
						.getComponent() instanceof UIRefPane) {
					((UIRefPane) getBillScrollPane1().getTableModel()
							.getBodyItems()[1].getComponent())
							.setValue(oValue == null ? null : oValue.toString());
					// setValueAt(oValue, rownow, 1);
				}
				// getBillScrollPane1().getTableModel().setValueAt(sValue,
				// rownow, 1);
				// if (Integer
				// .parseInt(
				// ((ArrayList)
				// m_alFreeItemNameStatus.get(rownow)).get(0).toString().trim())
				// == ConsultType.NoConsult) {
				// //do nothing
				// } else if (
				// Integer.parseInt(
				// ((ArrayList)
				// m_alFreeItemNameStatus.get(rownow)).get(0).toString().trim())
				// == ConsultType.UserDefConsult) {
				// ((UIRefPane)
				// m_bceBillCellEditor[rownow].getComponent()).setName(sValue);
				// getBillScrollPane1().getTableModel().setValueAt(
				// ((UIRefPane)
				// m_bceBillCellEditor[rownow].getComponent()).getRefName(),
				// rownow,
				// 1);
				// }
				break;
			default:
				break;
			}
			// for (int i= 0; i < getBillScrollPane1().getTable().getRowCount();
			// i++) {
			// getBillScrollPane1().getTableModel().setValueAt(
			// ((UIRefPane) m_bceBillCellEditor[i].getComponent()).getRefName(),
			// i,
			// 1);
			// }
			// checkOKButton();
			getBillScrollPane1().getTableModel().addTableModelListener(this);
			return;
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	}

	class IvjKeyListener extends java.awt.event.KeyAdapter {
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
				FreeItemDlg.this.closeCancel();
			}
		}

	}

	class KeyCancelListener extends java.awt.event.KeyAdapter {
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
				FreeItemDlg.this.closeCancel();
			}
		}

	}

	class KeyOKListener extends java.awt.event.KeyAdapter {
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
				FreeItemDlg.this.closeOK();
				return;
			}
		}

	}

	/**
	 * ����ڵ� - ��������ΪӦ�ó�������ʱ���������������
	 * 
	 * @param args
	 *            java.lang.String[]
	 */
	public static void main(java.lang.String[] args) {
		try {
			// String text="  89113456589.";
			// if(text.equals("."))
			// text="";
			// else if(text.endsWith(".")){
			// text=text.substring(0,text.length()-1);
			// }
			// Logger.error(text);

			nc.vo.bd.CorpVO voCorp = new nc.vo.bd.CorpVO();
			voCorp.setPrimaryKey("CPK");
			ClientEnvironment.getInstance().setCorporation(voCorp);
			nc.vo.sm.UserVO voUser = new nc.vo.sm.UserVO();
			voUser.setPrimaryKey("UPK");
			ClientEnvironment.getInstance().setUser(voUser);

			FreeItemDlg aFreeItemDlg;
			aFreeItemDlg = new FreeItemDlg();
			aFreeItemDlg.setModal(true);
			aFreeItemDlg.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					System.exit(0);
				};
			});
			// for test code below
			ArrayList alFreeItemName = new ArrayList();
			ArrayList alTemp = new ArrayList();
			alTemp.add(new Integer(ConsultType.InherentConsult));
			alTemp.add("���ŵ���");
			alFreeItemName.add(alTemp);
			alTemp = new ArrayList();
			alTemp.add(new Integer(ConsultType.InherentConsult));
			alTemp.add("��Ա����");
			alFreeItemName.add(alTemp);

			alTemp = new ArrayList();
			alTemp.add(new Integer(ConsultType.NoConsult));
			alTemp.add("PKPK");
			DefdefHeaderVO voDef = new DefdefHeaderVO();
			voDef.setType("����");
			voDef.setDigitnum(new Integer(3));
			voDef.setLengthnum(new Integer(6));
			alTemp.add(voDef);
			alFreeItemName.add(alTemp);

			alTemp = new ArrayList();
			alTemp.add(new Integer(ConsultType.NoConsult));
			alTemp.add("PKPK");
			DefdefHeaderVO voDef2 = new DefdefHeaderVO();
			voDef2.setType("����");
			voDef2.setDigitnum(new Integer(0));
			voDef2.setLengthnum(new Integer(6));
			alTemp.add(voDef2);
			alFreeItemName.add(alTemp);
			aFreeItemDlg.setFreeItemName(alFreeItemName);

			alTemp = new ArrayList();
			alTemp.add(new Integer(ConsultType.NoConsult));
			alTemp.add("PKPK");
			DefdefHeaderVO voDef3 = new DefdefHeaderVO();
			voDef3.setType("����");
			alTemp.add(voDef3);
			alFreeItemName.add(alTemp);
			aFreeItemDlg.setFreeItemName(alFreeItemName);
			// test code end

			// aFreeItemDlg.show();
			java.awt.Insets insets = aFreeItemDlg.getInsets();
			aFreeItemDlg.setSize(aFreeItemDlg.getWidth() + insets.left
					+ insets.right, aFreeItemDlg.getHeight() + insets.top
					+ insets.bottom);
			aFreeItemDlg.setVisible(true);
		} catch (Throwable exception) {
			Logger.error("nc.ui.pub.beans.UIDialog �� main() �з����쳣", exception);
		}
	}

}
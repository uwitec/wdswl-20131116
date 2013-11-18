package nc.vo.zmpub.pub.report2;
import java.awt.Dimension;

import javax.swing.JPanel;

import nc.bs.logging.Logger;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UIRadioButton;
import nc.ui.pub.querymodel.RotateCrossSetDlg;
import nc.ui.pub.querymodel.UIUtil;
import nc.ui.zmpub.pub.report.buttonaction2.IReportDealListener;
import nc.ui.zmpub.pub.report.buttonaction2.RotateCrossPanel;
import nc.vo.iuforeport.businessquery.SelectFldVO;
import nc.vo.pub.querymodel.RotateCrossVO;
/**
 * ��ת�������ÿ�
 * 
 * �������ڣ�(2003-11-28 9:38:11)
 * @author���쿡��  modify mlr �޸���ת���� ������г����bug
 */
public class CrossDLG extends UIDialog implements IReportDealListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1463011405178652008L;
	private RotateCrossVO m_rc = null;
	private UIButton ivjBnCancel = null;
	private UIButton ivjBnOK = null;
	private UIPanel ivjPnSouth = null;
	//private FlowLayout ivjPnSouthFlowLayout = null;
	private JPanel ivjUIDialogContentPane = null;
	private UIPanel ivjPnNorth = null;
	private RotateCrossPanel ivjPnRotateCross = null;
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	
	
	private UIComboBox cb = null;//���������
	
	public UIComboBox getComboBox(){
		if(cb == null){
			cb = new UIComboBox();
			cb.setBounds(30, 65, 100, 25);	
		}
		return cb;
	}
	
	
	private UIRadioButton m_istotal = null;
	private UIRadioButton getIsTotal(){
		if(m_istotal == null){
			m_istotal = new UIRadioButton();
			m_istotal.setBounds(20, 65, 16, 16);
		}
		return m_istotal;
	}

	class IvjEventHandler implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == CrossDLG.this.getBnOK())
				connEtoC1(e);
			if (e.getSource() == CrossDLG.this.getBnCancel())
				connEtoC2(e);
		};
	};

/**
 * RotateCrossSetDlg ������ע�⡣
 * @deprecated
 */
public CrossDLG() {
	super();
	initialize();
}
/**
 * RotateCrossSetDlg ������ע�⡣
 * @param parent java.awt.Container
 */
public CrossDLG(java.awt.Container parent) {
	super(parent);
	initialize();
	
}

public boolean getIsTotal1(){
	  return getIsTotal().isSelected();
	}
/**
 * ȡ��
 */
public void bnCancel_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	closeCancel();
}
/**
 * ȷ��
 */
public void bnOK_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	String strErr = getPnRotateCross().checkCrossinfo();
	if (strErr == null)
		m_rc = getPnRotateCross().getRotateCross();
	else
		if (strErr.equals(""))
			m_rc = null;
		else {
			MessageDialog.showWarningDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("10241201","UPP10241201-000099")/*@res "��ѯ����"*/, strErr);
			return;
		}
	closeOK();
}
/**
 * connEtoC1:  (BnOK.action.actionPerformed(java.awt.event.ActionEvent) --> RotateCrossSetDlg.bnOK_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* ���棺�˷������������ɡ� */
private void connEtoC1(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.bnOK_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC2:  (BnCancel.action.actionPerformed(java.awt.event.ActionEvent) --> RotateCrossSetDlg.bnCancel_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* ���棺�˷������������ɡ� */
private void connEtoC2(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.bnCancel_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * ���� BnCancel ����ֵ��
 * @return nc.ui.pub.beans.UIButton
 */
/* ���棺�˷������������ɡ� */
private nc.ui.pub.beans.UIButton getBnCancel() {
	if (ivjBnCancel == null) {
		try {
			ivjBnCancel = new nc.ui.pub.beans.UIButton();
			ivjBnCancel.setName("BnCancel");
			ivjBnCancel.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("10241201","UPP10241201-000000")/*@res "ȡ��"*/);
			// user code begin {1}
			ivjBnCancel.setPreferredSize(new Dimension(90, 22));
			UIUtil.autoSizeComp(ivjBnCancel, ivjBnCancel.getText());
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjBnCancel;
}
/**
 * ���� BnOK ����ֵ��
 * @return nc.ui.pub.beans.UIButton
 */
/* ���棺�˷������������ɡ� */
private nc.ui.pub.beans.UIButton getBnOK() {
	if (ivjBnOK == null) {
		try {
			ivjBnOK = new nc.ui.pub.beans.UIButton();
			ivjBnOK.setName("BnOK");
			ivjBnOK.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("10241201","UPP10241201-000001")/*@res "ȷ��"*/);
			// user code begin {1}
			ivjBnOK.setPreferredSize(new Dimension(90, 22));
			UIUtil.autoSizeComp(ivjBnOK, ivjBnOK.getText());
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjBnOK;
}
/**
 * ���� PnNorth ����ֵ��
 * @return nc.ui.pub.beans.UIPanel
 */
/* ���棺�˷������������ɡ� */
private nc.ui.pub.beans.UIPanel getPnNorth() {
	if (ivjPnNorth == null) {
		try {
			ivjPnNorth = new nc.ui.pub.beans.UIPanel();
			ivjPnNorth.setName("PnNorth");
			ivjPnNorth.setPreferredSize(new java.awt.Dimension(10, 4));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPnNorth;
}
/**
 * ���� PnRotateCross ����ֵ��
 * @return nc.ui.iuforeport.businessquery.RotateCrossPanel
 */
/* ���棺�˷������������ɡ� */
private RotateCrossPanel getPnRotateCross() {
	if (ivjPnRotateCross == null) {
		try {
			ivjPnRotateCross = new RotateCrossPanel();
			ivjPnRotateCross.setName("PnRotateCross");
			ivjPnRotateCross.addReportDealListener(this);
//			ivjPnRotateCross.addReportDealListener
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPnRotateCross;
}
/**
 * ���� PnSouth ����ֵ��
 * @return nc.ui.pub.beans.UIPanel
 */
/* ���棺�˷������������ɡ� */
private nc.ui.pub.beans.UIPanel getPnSouth() {
	if (ivjPnSouth == null) {
		try {
			ivjPnSouth = new nc.ui.pub.beans.UIPanel();
			ivjPnSouth.setName("PnSouth");
			ivjPnSouth.setLayout(getPnSouthFlowLayout());
			
			UILabel label1 = new UILabel("�Ƿ����ϼ�");
			label1.setBounds(30, 65, 100, 25);
			getPnSouth().add(label1);			
					
			getPnSouth().add(getIsTotal());
			getPnSouth().add(getComboBox());
			getPnSouth().add(getBnOK(), getBnOK().getName());
			getPnSouth().add(getBnCancel(), getBnCancel().getName());
			
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPnSouth;
}
/**
 * ���� PnSouthFlowLayout ����ֵ��
 * @return java.awt.FlowLayout
 */
/* ���棺�˷������������ɡ� */
private java.awt.FlowLayout getPnSouthFlowLayout() {
	java.awt.FlowLayout ivjPnSouthFlowLayout = null;
	try {
		/* �������� */
		ivjPnSouthFlowLayout = new java.awt.FlowLayout();
		ivjPnSouthFlowLayout.setVgap(8);
		ivjPnSouthFlowLayout.setHgap(20);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	};
	return ivjPnSouthFlowLayout;
}
/**
 * �����ת����VO
 * �������ڣ�(2003-11-25 10:44:05)
 * @param rcs nc.vo.pub.querymodel.RotateCrossVO[]
 */
public RotateCrossVO getRotateCross() {
	return m_rc;
}
/**
 * ���� UIDialogContentPane ����ֵ��
 * @return javax.swing.JPanel
 */
/* ���棺�˷������������ɡ� */
private javax.swing.JPanel getUIDialogContentPane() {
	if (ivjUIDialogContentPane == null) {
		try {
			ivjUIDialogContentPane = new javax.swing.JPanel();
			ivjUIDialogContentPane.setName("UIDialogContentPane");
			ivjUIDialogContentPane.setLayout(new java.awt.BorderLayout());
			getUIDialogContentPane().add(getPnSouth(), "South");
			getUIDialogContentPane().add(getPnRotateCross(), "Center");
			getUIDialogContentPane().add(getPnNorth(), "North");
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
 * @param exception java.lang.Throwable
 * @i18n upp08110600001=--------- δ��׽�����쳣 ---------
 */
private void handleException(java.lang.Throwable exception) {

	/* ��ȥ���и��е�ע�ͣ��Խ�δ��׽�����쳣��ӡ�� stdout�� */
	Logger.error("--------- δ��׽�����쳣 ---------", exception);
}
/**
 * ��ʼ������
 * @exception java.lang.Exception �쳣˵����
 */
/* ���棺�˷������������ɡ� */
private void initConnections() throws java.lang.Exception {
	// user code begin {1}
	// user code end
	getBnOK().addActionListener(ivjEventHandler);
	getBnCancel().addActionListener(ivjEventHandler);
}
/**
 * ��ʼ���ࡣ
 */
/* ���棺�˷������������ɡ� */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("RotateCrossSetDlg");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setSize(750, 480);
        this.setResizable(true);
		setTitle(nc.ui.ml.NCLangRes.getInstance().getStrByID("10241201","UPP10241201-001291")/*@res "��ת��������"*/);
		setContentPane(getUIDialogContentPane());
		initConnections();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	// user code end
}
/**
 * ����ڵ� - ��������ΪӦ�ó�������ʱ���������������
 * @param args java.lang.String[]
 * @i18n upp08110600002=nc.ui.pub.beans.UIDialog �� main() �з����쳣
 */
public static void main(java.lang.String[] args) {
	try {
		RotateCrossSetDlg aRotateCrossSetDlg;
		aRotateCrossSetDlg = new RotateCrossSetDlg();
		aRotateCrossSetDlg.setModal(true);
		aRotateCrossSetDlg.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		aRotateCrossSetDlg.setVisible(true);
		java.awt.Insets insets = aRotateCrossSetDlg.getInsets();
		aRotateCrossSetDlg.setSize(aRotateCrossSetDlg.getWidth() + insets.left + insets.right, aRotateCrossSetDlg.getHeight() + insets.top + insets.bottom);
		aRotateCrossSetDlg.setVisible(true);
	} catch (Throwable exception) {
		Logger.error("nc.ui.pub.beans.UIDialog �� main() �з����쳣", exception);
	}
}
/**
 * ������ת����VO
 * �������ڣ�(2003-11-25 10:44:05)
 * @param rcs nc.vo.pub.querymodel.RotateCrossVO[]
 */
public void setRotateCross(RotateCrossVO rc, SelectFldVO[] sfs) {
	getPnRotateCross().setRotateCross(rc, sfs);
	initSetComBox(rc.getStrCols());
}
private void initSetComBox(String[] strs) {
	String[] rts= strs;
	if(rts==null || rts.length==0)
		return;
	for(int i=1;i<rts.length+1;i++){
		getComboBox().addItem("��"+i+"��");
	}
}
public void deal(boolean isadd,int counts) {
	// TODO Auto-generated method stub
	if(isadd){
		int cots=getComboBox().getItemCount();
		for(int i=1;i<counts+1;i++){		
		getComboBox().addItem("��"+(cots+i)+"��");
		}
	}else{
		for(int i=0;i<counts;i++){
		getComboBox().removeItemAt(getComboBox().getItemCount()-1);
		}
		//getComboBox().remove();
	}
	
}
}  
package nc.ui.zmpub.pub.excel;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIFileChooser;
import nc.ui.pub.beans.UITextField;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.ui.zmpub.pub.tool.SingleVOChangeDataUiTool;
import nc.vo.pfxx.pub.Filter;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDate;
import nc.vo.scm.pub.SCMEnv;
import nc.vo.zmpub.excel.UpLoadFileVO;
import nc.vo.zmpub.pub.report.ReportBaseVO;

/**
 * Excel�ļ��ϴ�������
 * 
 * @author mlr
 * 
 */
public class UpLoadCtrlTool {

	AbstractBillUI billUI = null;
	private UITextField ivjtfDirectory = null;
	public UpLoadCtrlTool m_HgUploadCtrl;
	/** �ļ�ѡȡ�� */
	private UIFileChooser m_filechooser = null;
	private int state = 0;
	/** ��ǰѡ���·�� */
	private String m_currentPath = null;
	// ��ǰ�ļ�
	private File m_fFilePath = null;
	/** ��ǰĿ¼��XLS�ļ����� */
	private File[] m_allcurrfiles = null;

	private Hashtable m_ht = null;

	private Vector excelTOBill = null;

	/**
	 * UpLoadCtrl ������ע�⡣
	 */
	public UpLoadCtrlTool() {
		super();
	}

	public UpLoadCtrlTool(AbstractBillUI parentUI) {
		billUI = parentUI;
	}

	/**
	 * �����ļ�f���ļ�VO�� �������ڣ�(2010-12-23 20:53:13)
	 */
	public UpLoadFileVO createFileVOs(File f, int iSeq) {
		UpLoadFileVO vo = new UpLoadFileVO();
		int pos = f.toString().lastIndexOf("\\");
		String fname = null;
		String sFilePath = null;
		String[] s = null;
		if (pos > 0 && pos < f.toString().length() - 1)
			fname = f.toString().substring(pos + 1);

		sFilePath = f.toString();
//		s = getBillCode(sFilePath);
		vo.setSequence((new Integer(iSeq)).toString()); // ���к�
		vo.setSelect(new nc.vo.pub.lang.UFBoolean(false)); // �Ƿ�ѡ�У�Ĭ��Ϊ��ѡ
		vo.setFileName(fname); // �ļ�����
//		vo.setBillCode(s[0]); // ���ݺţ����ļ���һ����¼
		vo
		.setFileDate((new nc.vo.pub.lang.UFDate(f.lastModified()))
				.toString());// �ļ�����
//		vo.setFileStatus(s[1]); // �ļ�״̬
		return vo;
	}

//	/**
//	 * ����ʵ�ָ÷���������ҵ�����ı��⡣
//	 * 
//	 * @version (2010-12-23 20:53:13)
//	 * 
//	 * @return java.lang.String
//	 */
//	public String[] getBillCode(String sFilePath) {
//		// ���ļ���һ����¼�ĵ��ݺ�
//		String sBillCode = null;
//		// ���ļ�״̬
//		String sStatus = null;
//		// ����
//		String[] sReturn = new String[2];
//
//		try {
//			ExcelReadCtrl excelReadCtrl = getCa(sFilePath, true);
//			if (excelReadCtrl == null) {
//				throw new Exception("û��ע��ExcelReadCtrlʵ����");
//			}
//			nc.vo.pp.ask.ExcelFileVO vo = excelReadCtrl.getVOAtLine(1);
//			sBillCode = vo.getBillCode();
//			sStatus = excelReadCtrl.getExcelFileFlag();
//			sReturn[0] = sBillCode;
//			sReturn[1] = sStatus;
//		} catch (Exception e) {
//			SCMEnv.out(e.getMessage());
//			PuTool.outException(e);
//		}
//		return sReturn;
//	}

	/**
	 * liuys ���ܣ������ļ����������ƻ�ר�ã� ������ ���أ� ���⣺ ���ڣ�(2010-12-23 20:53:13)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.util.ArrayList
	 * @param pk_corp
	 *            java.lang.String
	 * @param sFilePath
	 *            java.lang.String[]
	 * @param sDir
	 *            java.lang.String
	 * @param user
	 *            java.lang.String
	 * @param logDate
	 *            java.lang.String
	 * @exception java.lang.Exception
	 *                �쳣˵����
	 */
	public ReportBaseVO[] UpLoadFiles(String pk_corp, String[] sFilePath,
			String sDir, String user, String logDate)
	throws java.lang.Exception {
		return UpLoadFiles(pk_corp, sFilePath, sDir, false, user, logDate,
				false);
	}

	/**
	 * liuys
	 * 
	 * ���ܣ��������ļ���ѯ�۵��ã� ������ ���أ� ���⣺ ���ڣ�(2010-12-23 20:53:13) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.util.ArrayList
	 * @param pk_corp
	 *            java.lang.String
	 * @param sFilePath
	 *            java.lang.String[]
	 * @param sDir
	 *            java.lang.String
	 * @param bOnlyFullBarcode
	 *            boolean
	 * @param m_user
	 *            java.lang.String
	 * @param m_logDate
	 *            java.lang.String
	 * @param bAutoSend
	 *            boolean
	 * @exception java.lang.Exception
	 *                �쳣˵����
	 */
	public ReportBaseVO[] UpLoadFiles(String pk_corp, String[] sFilePath,
			String sDir, boolean bOnlyFullBarcode, String m_user,
			String m_logDate, boolean bAutoSend) throws java.lang.Exception {

		ReportBaseVO[] vos = null;
		ExcelReadCtrl erc = null;

		if (pk_corp == null || pk_corp.length() <= 0 || sFilePath == null
				|| sFilePath.length <= 0)
			return null;

		String sFldPath = null;
		for (int i = 0; i < sFilePath.length; i++) {
			// ÿ��ֻ�ϴ�һ����֤��ȷ���ļ����Բ��ܴ����ļ�Ӱ��
			try {
				sFldPath = sDir + "\\" + sFilePath[i];
				erc = getCa(sFldPath, true);

				vos = erc.getAskVOsFromExcel();
			} catch (Exception e) {
				SCMEnv.out(e);
			}
		}
		return vos;
	}

	
	private ExcelReadCtrl ca = null;

	public ExcelReadCtrl getCa(String spath, boolean flag) throws Exception {
		if (ca == null) {
			String cl = getExcelReadCtrlClass();
			if (cl == null || cl.length() == 0)
				throw new Exception("û��ע��ExcelReadCtrlʵ����ȫ·��");
			Class cls = Class.forName(cl);
			Constructor ctor = cls.getConstructor(new Class[] { String.class,
					boolean.class });

			this.ca = (ExcelReadCtrl) ctor.newInstance(new Object[] { spath,
					flag });
		}

		return ca;
	}

	private String cls = null;

	public String getExcelReadCtrlClass() {
		return cls;
	}

	public void setExcelReadCtrlClass(String cls) {
		this.cls = cls;
	}

	/**
	 * liuys Excel���뵽���� �������ڣ�(2010-12-23 9:51:50)
	 */
	public ReportBaseVO[] onExcelBill(AbstractBillUI ui) {
		// ����ѡ�е��ϴ����ļ�
		String[] sFileNames = null;
		// ���ļ�
		doShowDir(ui);
		// ���ݰ�ť�����ж�
		ReportBaseVO[] rvos = null;
		if (state == javax.swing.JFileChooser.CANCEL_OPTION) {
			ui.showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"40040701", "UPP40040701-000074")/* @res "�ϴ�������ȡ����" */);
			return null;
		} else {
			// ��ȡѡ�е��ϴ��ļ�
			sFileNames = getSelectedFiles();
			if (sFileNames == null || sFileNames.length <= 0) {
				ui.showWarningMessage(nc.ui.ml.NCLangRes.getInstance()
						.getStrByID("40040701", "UPP40040701-000075")/*
						 * @res
						 * "��ѡ��Ҫ�ϴ��ĵ����ļ���"
						 */);
				//				if (!((BillManageUI) ui).isListPanelSelected()) {
				//					// // ��ʾ���ݡ�����ť״̬
				//					// getBufferData().clear();
				//					// getBufferData().updateView();
				//					// }
				//					// return;
				//				}
			}

			try {

				// ��ȡ�ϴ��ļ���·��
				String sPath = gettfDirectory().getText().trim();
				// �����ϴ�
				rvos = this.UpLoadFiles(getCorpId(), sFileNames, sPath,
						getUserId(), getLogDate());
				String isSuccess = "success";				

				//				�����ݽ��д���
				dealData(rvos);
				
				isUpLoadFileSuccessNew(isSuccess, ui);

				return rvos;
				
				

			} catch (Exception e) {
				MessageDialog.showErrorDlg(ui, nc.ui.ml.NCLangRes.getInstance()
						.getStrByID("SCMCOMMON", "UPPSCMCommon-000132")/*
						 * @res
						 * "����"
						 */, e
						 .getMessage());

			}
		}
		return rvos;
	}

	/**
	 * @���ܣ��жϵ����Ƿ��ϴ��ɹ�
	 * @���ߣ�liuys �������ڣ�(2004-12-8 15:48:14)
	 * @param:alUploadFile--�����ϴ����ļ� alUploadFailFile--�����ϴ�ʧ�ܵ��ļ�
	 *                              askBillVOs--�����ϴ��ɹ����ļ� sPath--�ϴ��ļ���·��
	 *                              erc---EXCEL�ļ��ӿ�
	 * @return:
	 * @exception:
	 * @see;
	 * @since:
	 * 
	 * @param newM_strState
	 *            java.lang.String
	 */
	private void isUpLoadFileSuccessNew(String isSuccess, AbstractBillUI ui) {
		// �ж��ļ��Ƿ��ϴ��ɹ�
		if (isSuccess != null && isSuccess.length() > 0
				&& "success".equals(isSuccess)) {
			MessageDialog
			.showWarningDlg(ui, "��ʾ", nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40040701", "UPP40040701-000070")/*
					 * @res
					 * "�����ļ��ϴ��ɹ���"
					 */);

		} else if ((isSuccess != null && isSuccess.length() > 0 && "false"
				.equals(isSuccess))
				|| isSuccess == null) {
			MessageDialog.showErrorDlg(ui,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
					"UPPSCMCommon-000132")/* @res "����" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40040701",
					"UPP40040701-000073")/* @res "�����ļ��ϴ�ʧ��!" */);
		}
	}

	/**
	 * �õ�ѡ�е��ļ�
	 * 
	 * @return java.lang.String[]
	 */
	private String[] getSelectedFiles() {
		String[] ss = null;
		java.util.Vector v = new java.util.Vector();
		// String sPath = null;
		String sFilePath = null;
		UpLoadFileVO temp = null;
		if (null != excelTOBill && excelTOBill.size() > 0) {
			for (int i = 0; i < excelTOBill.size(); i++) {
				temp = new UpLoadFileVO();
				temp = (UpLoadFileVO) excelTOBill.get(i);
				if (temp != null && temp.getFileName() != null
						&& temp.getFileName().trim().length() > 0) {
					sFilePath = temp.getFileName().trim();
					v.add(sFilePath);
				}
			}
		} else {
			return null;
		}
		ss = new String[v.size()];
		v.copyInto(ss);
		return ss;
	}

	/**
	 * @���ܣ���ȡ��˾ID
	 */
	private String getCorpId() {
		String corpid = null;
		if (corpid == null || "".equals(corpid.trim())) {
			corpid = nc.ui.pub.ClientEnvironment.getInstance().getCorporation()
			.getPrimaryKey();
		}
		return corpid;
	}

	/**
	 * @���ܣ���ȡ��½����
	 */
	private String getLogDate() {
		UFDate logDate = nc.ui.pub.ClientEnvironment.getInstance().getDate();
		return logDate.toString();
	}

//	/**
//	 * @���ܣ���ȡClientEnvironment
//	 */
//	private ClientEnvironment getcl() {
//		return ClientEnvironment.getInstance();
//	}

	/**
	 * @���ܣ���ȡ��½��ID
	 */
	public String getUserId() {
		String userid = nc.ui.pub.ClientEnvironment.getInstance().getUser()
		.getPrimaryKey();
		return userid;
	}

	/**
	 * liuys ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2010-12-23 20:53:13) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return nc.ui.pub.beans.UITextField
	 */
	private nc.ui.pub.beans.UITextField gettfDirectory() {
		if (ivjtfDirectory == null) {
			try {
				ivjtfDirectory = new nc.ui.pub.beans.UITextField();
				ivjtfDirectory.setName("tfDirectory");
				ivjtfDirectory.setBounds(92, 9, 634, 22);
				ivjtfDirectory.setMaxLength(500);
			} catch (java.lang.Throwable ivjExc) {
			}
		}
		return ivjtfDirectory;
	}

	/**
	 * ��ʾѡ���ļ�Ŀ¼
	 * 
	 */
	private void doShowDir(AbstractBillUI ui) {
		state = getFileChooser().showOpenDialog(ui);
		File f = getFileChooser().getSelectedFile();
		if (f != null && state == UIFileChooser.APPROVE_OPTION) {
			{
				// ��¼��ǰ�ļ���Ŀ¼
				m_currentPath = f.toString();
				// ���õ�ǰ�ļ�Ŀ¼
				m_fFilePath = f;

				if (m_fFilePath.isFile()) {
					int pos = f.toString().lastIndexOf("\\");
					String sFilePath = null;
					// String[] s=null;
					if (pos > 0 && pos < f.toString().length() - 1)
						sFilePath = f.toString().substring(0, pos);
					gettfDirectory().setText(sFilePath);
				} else {
					gettfDirectory().setText(f.toString());
				}
				// ������ǰĿ¼�����е��ļ�
				readAllFileList(ui);
			}
		}
	}

	/**
	 * ��ȡĿ¼�������ļ�
	 * 
	 * @return boolean
	 */
	private boolean readAllFileList(AbstractBillUI ui) {
		// select directory or file
		if (m_fFilePath.isDirectory()) {
			m_allcurrfiles = m_fFilePath.listFiles(new Filter("xls"));
		} else if (m_fFilePath.isFile()) {
			String fileName = m_currentPath;
			File f = new File(fileName);

			m_allcurrfiles = new File[1];
			m_allcurrfiles[0] = f;
		}
		if (m_allcurrfiles == null)
			return false;
		// �Ѷ�����XLS�ļ����뵽�����
		UpLoadFileVO vo = null;
		int k = 0;
		// UpLoadFileVO[] vos = null;
		excelTOBill = new Vector();
		if (m_ht == null || m_ht.size() == 0) { // �ǹ���ʱ
			for (int i = 0; i < m_allcurrfiles.length; i++) {
				if (m_fFilePath.isDirectory() && !m_allcurrfiles[i].exists()) {
					continue;
				}
				vo = this.createFileVOs(m_allcurrfiles[i], i + 1);
				excelTOBill.add(vo);
			}
		} else { // �������
			String sfiletype = null;
			String sfilename = null;
			String sfiledate = null;

			if (m_ht.containsKey("filetype")) {
				sfiletype = m_ht.get("filetype").toString();
			}
			if (m_ht.containsKey("filename")) {
				sfilename = m_ht.get("filename").toString();
			}
			if (m_ht.containsKey("filedate")) {
				sfiledate = m_ht.get("filedate").toString();
			}

			for (int i = 0; i < m_allcurrfiles.length; i++) {
				if (!m_allcurrfiles[i].exists()) {
					continue;
				}
				vo = this.createFileVOs(m_allcurrfiles[i], k + 1);
				String sType = null;
				String sName = null;
				String sDate = null;
				sType = vo.getFileStatus();
				sName = vo.getFileName();
				sDate = vo.getFileDate();
				// �����ļ�����
				if (sfiletype != null && sfiletype.length() > 0) {
					if (!sfiletype.equals(sType)) {
						continue;
					}
				}
				// �����ļ�����
				if (sfilename != null && sfilename.length() > 0) {
					if (sName.indexOf(sfilename) == -1) {
						continue;
					}
				}
				// �����ļ�����
				if (sfiledate != null && sfiledate.length() > 0) {
					if (sDate.indexOf(sfiletype) == -1) {
						continue;
					}
				}
				excelTOBill.add(vo);
			}
		}
		return true;
	}

	/**
	 * ����ѡ�е��ļ�����
	 * 
	 * @return javax.swing.JFileChooser
	 */
	private JFileChooser getFileChooser() {
		if (m_filechooser == null) {
			m_filechooser = new UIFileChooser();
			// ��ȥ��ǰ���ļ�������
			m_filechooser.removeChoosableFileFilter(m_filechooser
					.getFileFilter());
			// ����ļ�ѡ�������
			m_filechooser.addChoosableFileFilter(new ExcelFileFilter("xls"));// Excel�ļ�
			// ��ʾ��ѡȡ�İ����ļ���Ŀ¼
			m_filechooser
			.setFileSelectionMode(m_filechooser.FILES_AND_DIRECTORIES);
		}
		return m_filechooser;
	}

	/*******************************************************************************
	 * �ļ��򿪿�Ĺ�����
	 */
	public class ExcelFileFilter extends FileFilter {
		String ext;

		public ExcelFileFilter(String ext) {
			this.ext = ext;
		}

		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			}
			String fileName = file.getName();
			int index = fileName.lastIndexOf(".");
			if (index > 0 && index < fileName.length() - 1) {
				String extension = fileName.substring(index + 1).toLowerCase();
				if (extension.equals(ext)) {
					return true;
				}
			}
			return false;
		}

		public String getDescription() {
			if ("xls".equals(ext)) {
				return "Excel���ͣ�*.xls)";
			}
			return "";
		}
	}
	
	/**
	 * ���ú�̨�����������
	 */
	public void dealData(ReportBaseVO[] rvos) throws Exception {
		if (rvos == null || rvos.length == 0)
			return;

		ExcelReadCtrl ctrl = getCa(null, true);
		// ת��������vo
		if (ctrl.isSingle()){// �������ݴ���
			CircularlyAccessibleValueObject[] vos = SingleVOChangeDataUiTool
			.runChangeVOAry(rvos, ctrl.getSingleVOClass(),
					ctrl.getSingleChangeClassName());
			// ת��̨����
			Class[] ParameterTypes = new Class[] { CircularlyAccessibleValueObject[].class };
			Object[] ParameterValues = new Object[] { vos };

			LongTimeTask.calllongTimeService("zmpub", billUI,
					"���ڴ���...", 2, ctrl.getDealBOClassName(), null, "dealSingleImportDatas", ParameterTypes,
					ParameterValues);
		} else {// �������ݴ��� �� ���б�ͷ���� �ĵ��� ��Ҫע�ᵥ������ vo����
			// ֱ��ת��̨����
			Class[] ParameterTypes = new Class[] { ReportBaseVO[].class,
					String.class, String.class };
			Object[] ParameterValues = new Object[] { rvos, ctrl.getBillType(),
					ctrl.getTmpBillType() };
			//			LongTimeTask.callRemoteService(WdsWlPubConst.WDS_WL_MODULENAME,
			//					getDealBOClassName(), "dealBillImportDatas",
			//					ParameterTypes, ParameterValues, 2);

			LongTimeTask.calllongTimeService("wds", billUI,
					"���ڴ���...", 2, ctrl.getDealBOClassName(), null, "dealBillImportDatas", ParameterTypes,
					ParameterValues);
		}
	}
}

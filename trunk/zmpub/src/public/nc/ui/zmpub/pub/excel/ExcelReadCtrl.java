package nc.ui.zmpub.pub.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JOptionPane;

import nc.ui.pp.pub.ExcelColumnInfo;
import nc.ui.pp.pub.IExcelFileFlag;
import nc.ui.pu.pub.PuTool;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.SCMEnv;
import nc.vo.zmpub.pub.report.ReportBaseVO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Excel�ļ���ȡ��
 * 
 * @author mlr
 */
@SuppressWarnings("restriction")
public abstract class ExcelReadCtrl {
	private HSSFWorkbook wb = null;// Excel�ļ�����
	private HSSFSheet[] sheet = null;// sheet����,����ҳ��

	// ��ǰ�ļ�����
	private String m_fileName;

	/**
	 * ExcelReadCtrl ������ע�⡣
	 */
	public ExcelReadCtrl() {
		super();
	}

	/**
	 * ExcelReadCtrl ������ע�⡣
	 * 
	 * @param sFileName
	 *            java.lang.String
	 * @param flag
	 *            boolean
	 * @exception java.lang.Exception
	 *                �쳣˵����
	 */
	public ExcelReadCtrl(String sFileName, boolean flag) throws Exception {
		super();
		if (sFileName == null)
			return;
		m_fileName = sFileName;

		FileInputStream fs = new FileInputStream(m_fileName);
		wb = new HSSFWorkbook(fs);

		int sheetSize = wb.getNumberOfSheets();
		if (sheetSize == 0) {
			JOptionPane
			.showMessageDialog(null, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40040701", "UPP40040701-000193")/*
					 * @res
					 * "Excel�ļ�Ϊ�գ�"
					 */);
			return;
		}
		sheet = new HSSFSheet[sheetSize];

		for (int i = 0; i < sheetSize; i++) {
			sheet[i] = wb.getSheetAt(i);
		}

		fs.close();
	}

//	/**
//	 * ?user> ���ܣ��ӵ�һ��sheetָ����Excel��ȡ��VO ������ ���أ� ���⣺ ���ڣ�(2004-8-25 12:18:30)
//	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
//	 * 
//	 * @return nc.vo.ic.pub.barcodeoffline.ExcelFileVO
//	 * @param line
//	 *            int
//	 */
//	public ExcelFileVO getVOAtLine(int indexRow) {
//		ExcelFileVO vo = new nc.vo.pp.ask.ExcelFileVO();
//		HSSFRow row = sheet[0].getRow(indexRow);
//		setRowToVO(row, vo);
//		return vo;
//	}

	/**
	 * ���� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2004-10-15 9:22:40) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param len
	 *            int
	 * @param lenAll
	 *            int
	 */
	private void clearRestLines(int len, int lenAll) {
		if (len >= lenAll)
			return;

		for (int i = len; i <= lenAll; i++) {
			HSSFRow row = sheet[0].getRow(i);
			if (row == null)
				continue;

			for (short j = 0; j < ExcelColumnInfo.saAskVOName.length; j++) {
				HSSFCell cell = row.getCell(j);
				if (cell == null)
					continue;
				cell.setCellValue("");

			}

		}

	}

	/**
	 * Author������ ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2004-10-16 13:01:30) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param len
	 *            int
	 * @param lenAll
	 *            int
	 */
	private void clearVendorRestLines(int len, int lenAll) {
		if (len >= lenAll)
			return;

		for (int i = len; i <= lenAll; i++) {
			HSSFRow row = sheet[0].getRow(i);
			if (row == null)
				continue;

			for (short j = 0; j < ExcelColumnInfo.saVendorVOName.length; j++) {
				HSSFCell cell = row.getCell(j);
				if (cell == null)
					continue;
				cell.setCellValue("");

			}

		}

	}
	
	protected int beginrow = 1;//Ĭ�Ͽ�ʼ��

	/**
	 * Author mlr ���ܣ���õ����ת��ΪReporBaseVO[]��ʽ��EXCEL�ļ���ѯ�۵��ã� ������ ���أ� ���⣺
	 * ���ڣ�(2004-10-15 14:43:44) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return nc.vo.pp.ask.ExcelFileVO[]
	 * @throws Exception
	 */
	public ReportBaseVO[] getAskVOsFromExcel() throws Exception {
		ReportBaseVO[] voaReturn = null;
		Vector vcSheet = getDataRowVector(0);

		if (vcSheet == null)
			return null;
		if (vcSheet.size() < 1)
			return null;

		int len = vcSheet.size();
		voaReturn = new ReportBaseVO[len-1];//����������ͷ

		ReportBaseVO voTemp = null;
		HSSFRow rowTemp = null;
		for (int i = beginrow; i < len; i++) {
			voTemp = new ReportBaseVO();
			rowTemp = (HSSFRow) vcSheet.get(i);
			setRowToVO(rowTemp, voTemp);
			// //����Excel�ļ��ı�־λ
			// voTemp.setExcelFlag(fileFlag);
			voaReturn[i-beginrow] = voTemp;
		}
		if (voaReturn.length > 0) {
			return voaReturn;
		}
		return null;

	}

	/**
	 * Author������ ���ܣ�������������� ������ ���أ� ���⣺ ���ڣ�(2004-10-15 14:12:16)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.util.Vector
	 * @param sheetID   excel��ҳ��
	 *            int
	 */
	private java.util.Vector getDataRowVector(int sheetID) {
		java.util.Iterator iter = sheet[sheetID].rowIterator();
		java.util.Vector vcRow = new java.util.Vector();

		while (iter.hasNext()) {
			HSSFRow row = (HSSFRow) iter.next();
			if (row == null || row.getCell((short) 1) == null) {
				continue;
			}
			vcRow.add(row);
		}
		return vcRow;
	}

	/**
	 * ���� ���ܣ����EXCEL�ļ�״̬��־��ѯ�۵��ã� ������ ���أ� ���⣺ ���ڣ�(2004-10-15 9:10:46)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String
	 */
	public String getExcelFileFlag() {

		HSSFRow row = sheet[0].getRow(0);
		if (row == null) {
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("40040701",
			"UPP40040701-000194")/* @res "�ļ���ʽ����" */;
		}
		int iColumn = ExcelColumnInfo.saAskCaption.length;
		HSSFCell cell = row.getCell((short) iColumn);
		if (cell == null || cell.getCellType() != HSSFCell.CELL_TYPE_STRING) {
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("40040701",
			"UPP40040701-000195")/* @res "�հ�״̬λ" */;
		}
		String value = cell.getStringCellValue().trim();
		if (!value.equals(IExcelFileFlag.F_EDITED)
				&& !value.equals(IExcelFileFlag.F_EDITING)
				&& !value.equals(IExcelFileFlag.F_NEW)
				&& !value.equals(IExcelFileFlag.F_UPLOAD)
				&& !value.equals(IExcelFileFlag.F_UPLOADFAILED))
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("40040701",
			"UPP40040701-000196")/* @res "״̬λ����" */;

		return value;

	}

	/**
	 * ���� ���ܣ�����в����ˣ�ѯ�۵��ã� ������ ���أ� ���⣺ ���ڣ�(2004-10-15 9:12:17)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String
	 */
	public String getStatusWho() {
		HSSFRow row = sheet[0].getRow(0);
		if (row == null) {
			return IExcelFileFlag.F_NO_BODY;
		}
		int iColumn = ExcelColumnInfo.saAskCaption.length + 1;
		HSSFCell cell = row.getCell((short) iColumn);
		if (cell == null || cell.getCellType() != HSSFCell.CELL_TYPE_STRING) {
			return IExcelFileFlag.F_NO_BODY;
		}
		String value = cell.getStringCellValue();

		if (value == null || value.trim().equals(""))
			return IExcelFileFlag.F_NO_BODY;

		return value.trim();
	}

	/**
	 * Author������ ���ܣ����EXCEL�ļ�״̬��־�����۵��ã� ������ ���أ� ���⣺ ���ڣ�(2004-10-16 13:05:02)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String
	 */
	public String getVendorExcelFileFlag() {

		HSSFRow row = sheet[0].getRow(0);
		if (row == null) {
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("40040701",
			"UPP40040701-000194")/* @res "�ļ���ʽ����" */;
		}
		int iColumn = ExcelColumnInfo.saVendorCaption.length;
		HSSFCell cell = row.getCell((short) iColumn);
		if (cell == null || cell.getCellType() != HSSFCell.CELL_TYPE_STRING) {
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("40040701",
			"UPP40040701-000195")/* @res "�հ�״̬λ" */;
		}
		String value = cell.getStringCellValue().trim();
		if (!value.equals(IExcelFileFlag.F_EDITED)
				&& !value.equals(IExcelFileFlag.F_EDITING)
				&& !value.equals(IExcelFileFlag.F_NEW)
				&& !value.equals(IExcelFileFlag.F_UPLOAD)
				&& !value.equals(IExcelFileFlag.F_UPLOADFAILED))
			return nc.ui.ml.NCLangRes.getInstance().getStrByID("40040701",
			"UPP40040701-000196")/* @res "״̬λ����" */;

		return value;

	}

	/**
	 * Author������ ���ܣ�����в����ˣ����۵��ã� ������ ���أ� ���⣺ ���ڣ�(2004-10-16 16:50:16)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String
	 */
	public String getVendorStatusWho() {
		HSSFRow row = sheet[0].getRow(0);
		if (row == null) {
			return IExcelFileFlag.F_NO_BODY;
		}
		int iColumn = ExcelColumnInfo.saVendorCaption.length + 1;
		HSSFCell cell = row.getCell((short) iColumn);
		if (cell == null || cell.getCellType() != HSSFCell.CELL_TYPE_STRING) {
			return IExcelFileFlag.F_NO_BODY;
		}
		String value = cell.getStringCellValue();

		if (value == null || value.trim().equals(""))
			return IExcelFileFlag.F_NO_BODY;

		return value.trim();
	}

	

	/**
	 * ���� ���ܣ�����EXCEL�ļ�״̬��־��ѯ�۵��ã� ������ ���أ� ���⣺ ���ڣ�(2004-10-15 9:16:51)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String
	 * @param strFlag
	 *            java.lang.String
	 */
	public String setExcelFileFlag(String strFlag) {
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(m_fileName);

			HSSFRow row = sheet[0].getRow(0);
			if (row == null) {
				return "error";
			}
			int iColumn = nc.ui.pp.pub.ExcelColumnInfo.saAskCaption.length;
			HSSFCell cell = row.getCell((short) iColumn);

			// ��һ�ε���ʱ���ļ���ʾ��ΪNEW
			if (cell == null) {
				cell = row.createCell((short) iColumn);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(IExcelFileFlag.F_NEW);
			}
			// String value = cell.getStringCellValue().trim();
			// if (!value.equals(IExcelFileFlag.F_EDITED)
			// && !value.equals(IExcelFileFlag.F_EDITING)
			// && !value.equals(IExcelFileFlag.F_NEW)
			// && !value.equals(IExcelFileFlag.F_UPLOAD))
			// return "error";

			cell.setCellValue(strFlag);

			wb.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			SCMEnv.out(e.getMessage());
			PuTool.outException(e);
			JOptionPane
			.showMessageDialog(null, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40040701", "UPP40040701-000211")/*
					 * @res
					 * "��д�ļ��쳣��"
					 */);
		}
		return "setValue success";
	}

	/**
	 * 	ע���ӦExcel�ļ����ֶ��б�   ����ģʽ  ishead  ����Ϊ  false
	 * @return
	 */
	public  String[] getFieldNames(boolean ishead){
		if(ishead)
			return getHeadFieldNames();
		else
			return getBodyFieldNames();
	}
	
	/**
	 * ���ر�ͷ�ֶ�����
	 * @return
	 */
	public abstract String[] getHeadFieldNames();
	/**
	 * ���ر����ֶ�����
	 * @return
	 */
	public abstract String[] getBodyFieldNames();
	
	/**
	 * �Ƿ��ͷ
	 * @param headFlagCell
	 * @return
	 */
	protected boolean isHead(HSSFCell headFlagCell){
		if(isSingle())//����ǵ����ֱ�ӷ���false
			return false;
		if(headFlagCell == null)
			return false;
		int type = headFlagCell.getCellType();
		if(type != HSSFCell.CELL_TYPE_STRING)
			return false;
		String value = PuPubVO.getString_TrimZeroLenAsNull(headFlagCell.getStringCellValue());
		if(value == null)
			return false;
		if(value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("TRUE"))
			return true;
		return false;
	}

	/**
	 * author mlr ���ܣ������ϴ�����ÿ�е�ֵ��ѯ�۵��ã� ���ڣ�(2004-10-15 9:17:14)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @throws Exception
	 */
	private void setRowToVO(HSSFRow row, ReportBaseVO vo) throws Exception {
		if (row == null || vo == null)
			return;
		SimpleDateFormat formatter = null;
		String dateToString = null;
		String[] fieldNames = getFieldNames(isHead(row.getCell((short)0)));
		if (fieldNames == null || fieldNames.length == 0)
			throw new Exception("û��ע���ӦExcel�ļ����ֶ��б�");
		for (short i = 0; i < fieldNames.length; i++) {
			HSSFCell cellTemp = row.getCell(i);
			// �м䲻���п���
			if (cellTemp == null) {
				continue;
			}
			int type = cellTemp.getCellType();
			Object value = null;

			switch (type) {
			case HSSFCell.CELL_TYPE_STRING:
				value = PuPubVO.getString_TrimZeroLenAsNull(cellTemp
						.getStringCellValue());
				if (value == null) {
					continue;
				}
				value = value.toString().trim();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				double db = cellTemp.getNumericCellValue();
				// ����������� 2008.1.15 modify by donggq
				String strFormat = // java.text.NumberFormat.getNumberInstance(java.util.Locale.CHINA).format(db);
					new UFDouble(db).toString();
				java.util.StringTokenizer strToken = new java.util.StringTokenizer(
						strFormat, ",");
				StringBuffer sbBuffer = new StringBuffer();
				while (strToken.hasMoreTokens()) {
					String strT = strToken.nextToken();
					sbBuffer.append(strT);

				}
				if (sbBuffer.toString().trim().length() > 0) {
					value = new UFDouble(sbBuffer.toString().trim());
				}
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
			case HSSFCell.CELL_TYPE_BLANK:
			case HSSFCell.CELL_TYPE_ERROR:
			case HSSFCell.CELL_TYPE_FORMULA:
			default:
				break;
			}
			vo.setAttributeValue(fieldNames[i], value);
		}
	}



	/**
	 * Author������ ���ܣ�����EXCEL�ļ�״̬��־�����۵��ã� ������ ���أ� ���⣺ ���ڣ�(2004-10-16 13:05:44)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String
	 * @param strFlag
	 *            java.lang.String
	 */
	public String setVendorExcelFileFlag(String strFlag) {
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(m_fileName);

			HSSFRow row = sheet[0].getRow(0);
			if (row == null) {
				return "error";
			}
			int iColumn = nc.ui.pp.pub.ExcelColumnInfo.saVendorCaption.length;
			HSSFCell cell = row.getCell((short) iColumn);

			// ��һ�ε���ʱ���ļ���ʾ��ΪNEW
			if (cell == null) {
				cell = row.createCell((short) iColumn);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(IExcelFileFlag.F_NEW);
			}
			// String value = cell.getStringCellValue().trim();
			// if (!value.equals(IExcelFileFlag.F_EDITED)
			// && !value.equals(IExcelFileFlag.F_EDITING)
			// && !value.equals(IExcelFileFlag.F_NEW)
			// && !value.equals(IExcelFileFlag.F_UPLOAD))
			// return "error";

			cell.setCellValue(strFlag);

			wb.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			SCMEnv.out(e.getMessage());
			PuTool.outException(e);
			JOptionPane
			.showMessageDialog(null, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40040701", "UPP40040701-000198")/*
					 * @res
					 * "setExcelFileFlag��д�ļ��쳣��"
					 */);
		}
		return "setValue success";
	}


	/**
	 * ���� ���ܣ��ѵ��������õ�EXCEL�ļ���Ӧ�����У�ѯ�۵��ã� ������ ���أ� ���⣺ ���ڣ�(2004-10-15 9:17:30)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param voFile
	 *            nc.vo.pp.ask.ExcelFileVO
	 * @param line
	 *            int
	 */
	public void setVOAtLine(nc.vo.pp.ask.ExcelFileVO voFile, int line) {
		try {
			if (line <= 0)
				return;
			if (voFile == null)
				return;

			HSSFRow row = sheet[0].getRow(line);

			if (row == null)
				row = sheet[0].createRow((short) line);
			// Style the cell with borders all around
			HSSFCellStyle style = wb.createCellStyle();
			style.setBorderBottom(HSSFCellStyle.BORDER_DASHED);
			style.setBottomBorderColor(HSSFColor.BLACK.index);
			style.setBorderLeft(HSSFCellStyle.BORDER_DASHED);
			style.setLeftBorderColor(HSSFColor.GREEN.index);
			style.setBorderRight(HSSFCellStyle.BORDER_DASHED);
			style.setRightBorderColor(HSSFColor.BLUE.index);
			style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM_DASHED);
			style.setTopBorderColor(HSSFColor.BLACK.index);
			HSSFCellStyle styleForHidden = wb.createCellStyle();
			styleForHidden.setHidden(true);
			styleForHidden.setLocked(true);
			UFDate valueOfDate = null;
			UFDouble valueOfDouble = null;
			for (short i = 0; i < nc.ui.pp.pub.ExcelColumnInfo.saAskVOName.length; i++) {
				HSSFCell cell = row.getCell(i);
				if (cell == null)
					cell = row.createCell(i);
				// ���֮ǰ�ļ�Ϊ���֣�������������쳣��������Ҫ����
				// cell.setCellType(HSSFCell.CELL_TYPE_STRING);

				String strCellValue = (String) voFile
				.getAttributeValue(nc.ui.pp.pub.ExcelColumnInfo.saAskVOName[i]);
				if ((i == 7 || i == 8 || i == 9 || i == 10 || i == 12)
						&& (strCellValue != null && strCellValue.toString()
								.trim().length() > 0)) {
					valueOfDouble = new UFDouble(strCellValue);
				} else if ((i == 11 || i == 13 || i == 14)
						&& (strCellValue != null && strCellValue.toString()
								.trim().length() > 0)) {
					valueOfDate = new UFDate(strCellValue);
				}

				if (i == 7 || i == 8 || i == 9 || i == 10 || i == 12) {
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else {
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
				if (strCellValue == null
						|| (strCellValue != null && strCellValue.toString()
								.trim().length() == 0)) {
					cell.setCellValue("");
				} else if (i == 7 || i == 8 || i == 9 || i == 10 || i == 12) {
					if (valueOfDouble != null
							&& valueOfDouble.toString().trim().length() > 0) {
						cell.setCellValue(valueOfDouble.doubleValue());
					} else {
						cell.setCellValue(strCellValue);
					}
				}
				// else if(i == 11 || i == 13 || i ==14 ){
				// if(valueOfDate != null &&
				// valueOfDate.toString().trim().length() > 0){
				// cell.setCellValue(valueOfDate.toDate());
				// }else{
				// cell.setCellValue(strCellValue);
				// }
				// }
				else {
					cell.setCellValue(strCellValue);
				}

				if (i < 15) {
					cell.setCellStyle(style);
				} else {
					cell.setCellStyle(styleForHidden);
				}
			}
		} catch (Exception e) {
			SCMEnv.out("filetoExcel err :" + e.getMessage());
			PuTool.outException(e);
		}
	}

	/**
	 * ���� ���ܣ��ѵ��������õ�EXCEL�ļ���Ӧ�����У�ѯ�۵��ã� ������ ���أ� ���⣺ ���ڣ�(2004-10-15 9:17:30)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param voFile
	 *            nc.vo.pp.ask.ExcelFileVO
	 * @param line
	 *            int
	 */
	public void setVOAtLineForHead(nc.vo.pp.ask.ExcelFileVO voFile, int line) {
		try {
			if (line <= 0)
				return;
			if (voFile == null)
				return;

			HSSFRow row = null;

			int j = 1;
			short createLine = 0;
			for (short i = 0; i < nc.ui.pp.pub.ExcelColumnInfo.saAskVONameForHead.length; i++) {
				if (i < 3) {
					row = sheet[0].getRow(2);
					if (row == null)
						row = sheet[0].createRow((short) 2);
				} else {
					row = sheet[0].getRow(3);
					if (row == null)
						row = sheet[0].createRow((short) 3);
				}
				if (i == 0 || i == 3) {
					j = 1;
				}
				if (i == 1 || i == 4) {
					j = 8;
				}
				if (i == 2 || i == 5) {
					j = 14;
				}
				createLine = new Integer(j).shortValue();
				HSSFCell cell = row.getCell(createLine);
				if (cell == null)
					cell = row.createCell(createLine);
				// ���֮ǰ�ļ�Ϊ���֣�������������쳣��������Ҫ����
				// cell.setCellType(HSSFCell.CELL_TYPE_STRING);

				String strCellValue = (String) voFile
				.getAttributeValue(nc.ui.pp.pub.ExcelColumnInfo.saAskVONameForHead[i]);
				if (strCellValue == null)
					cell.setCellValue("");
				else
					cell.setCellValue(strCellValue);

			}
			j = 0;
			createLine = 0;
		} catch (Exception e) {
			SCMEnv.out(e.getMessage());
			SCMEnv.out("filetoExcel err :" + e.getMessage());
			PuTool.outException(e);
		}
	}



	/**
	 * ����Ҫ���ݱ����ѯid���ֶ�
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-11-4����05:59:23
	 * @return
	 */
//	public abstract String[] getQueryIds();

	/**
	 * ����Ҫ���ݱ����ѯid���ֶ� ������� ������ getQueryIds()һһ��Ӧ
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-11-4����05:59:23
	 * @return
	 */
//	public abstract String[] getQueryTables();

	/**
	 * ����Ҫ��ѯ�Ŀ�� ��Ӧ��id ������ getQueryTables()һһ��Ӧ
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-11-4����05:59:23
	 * @return
	 */
//	public abstract String[] getQuerySelectIDs();

	/**
	 * ����Ҫ��ѯ�Ŀ�� ��Ӧ�ı�������� ������ getQueryTables()һһ��Ӧ
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-11-4����05:59:23
	 * @return
	 */
//	public abstract String[] getQueryCodeNames();

	/**
	 * �Ƿ��ѯ��˾ ������ getQueryTables()һһ��Ӧ
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-11-4����05:59:23
	 * @return
	 */
//	public abstract boolean[] getIsQueCorps();

	/**
	 * �������ݽ����󹹽�����
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-11-5����10:06:33
	 * @return
	 */
//	public abstract String getReturnVO();

	/**
	 * �Ƿ������ ������getQueryTables һһ��Ӧ
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-11-5����01:42:59
	 * @return
	 */
//	public abstract boolean[] getIsMutitables();

	/**
	 * ��ȡ��ֵ�������б�
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-11-5����02:15:47
	 * @return
	 */
//	public abstract String[] getSetValueIds();

	/**
	 * �Ƿ���  ���� ��ͷ����ṹ  true������  false������ģʽ
	 * @return
	 */
	public  abstract boolean isSingle();

	/**
	 * ��������
	 * @return
	 */
	public  abstract String getBillType();

	/**
	 * excel ���ⵥ������
	 * @return
	 */
	public String getTmpBillType(){
		return "EXCEL";
	}

	/**
	 * ��������ת��ʱ��Ҫ�ṩ��voȫ·��
	 * @return
	 */
	protected abstract Class getSingleVOClass();

	/**
	 * ��õ��� ���ݽ�����   ��reportbasevo ת���� ҵ������
	 * @return
	 */
	protected abstract String getSingleChangeClassName();
	/**
	 * ��̨���������  ����̳� nc.bs.zmpub.pub.excel.AbstractExcetBO
	 * @return
	 */
	protected abstract String getDealBOClassName();

}

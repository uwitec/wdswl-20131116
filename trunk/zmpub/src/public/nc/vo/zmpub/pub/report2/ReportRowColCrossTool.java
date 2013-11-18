package nc.vo.zmpub.pub.report2;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.table.TableColumnModel;

import nc.ui.pub.beans.UITable;
import nc.ui.pub.beans.table.GroupableTableHeader;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.report.ReportGeneralUtil;
import nc.ui.pub.report.ReportItem;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.pub.cquery.FldgroupVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.rs.MemoryResultSet;
import nc.vo.pub.rs.MemoryResultSetMetaData;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.zmpub.pub.report.ReportBaseVO;

/**
 * �����о���Ҫ�õ� �� ��ѯ������ĳһ�е����� �� ĳ���е����� չ�� ������ �γɶ�ά�Ķ�̬�� ʲô��һά�� ��ν��һά����� ���ǹ̶������
 * ���ϵ������Ƕ�̬�仯�� Ҳ���� ������ ���� �������
 * 
 * �������� ��Ӧ�ö�֪��
 * 
 * ��ôʲô�Ƕ�ά���� ��ν�Ķ�ά����� �� �� �ж��Ƕ�̬�仯��
 * 
 * Ҳ���� �Ὣ��ѯ������ ĳһ�е����� ��̬չ�� �ŵ�������
 * 
 * @author mlr 2011-12-11 ----- 2011-12-16
 */

/**
 *��һ��items  Ҫ�ڸ�items�� 4  8   6  11 .......����λ�ô� ��һ��item ��μ��أ�
 *
 *���� ��һ��int���� ��� ����  Ϊ indexs
 *
 * ��һ�����������  Ҫ��ӵ�item Ϊ  newits
 * 
 * Ȼ�� �� items ����ŵ�һ��������ȥ Ϊ oldits
 * 
 * ѭ�����£�               4 9 8 14
 * 
 * for(int i=0;i<newits.length;i++){
 *    indexs[i]=indexs[i]+i;
 * }
 * 
 * for(int i=0;i<indexs.length;i++){
 *    oldits.add(indexs[i],newits[i]);
 * }
 * 
 * 
 * 
 * ��ι���һ����
 * 
 * ���������±�:
 *  
 *  id  parentid  name code leaval(Ϊ�ڼ���)
 *  
 * ��װ��voΪ  id parentid  name code leaval  list(list���ڴ������) 
 * 
 * 
 * ��ô ��� ����һ���� ���յõ�һ��vo��
 * 
 * ������ô����
 * 
 * ���� ����ѯ�����ݷ�װ���� ���� һ������ �����еĶ���Ϊ vo  vo�е�listû��ֵ
 * 
 * ���ȸ��ݼ��η���  �õ��������� 
 * ���������ݷŵ� map ������ keyΪ����
 * 
 * Ȼ��ѭ������:
 * �ӵڶ�����ʼ ���ܵ�һ��  ������ ���� �ڶ���.......
 * for(int i=map.size()-1-1;i>=0;i--){
 *    //�õ���һ��������
 *    list xl=map.get(i-1);
 *    //�õ������ε�����
 *    list sl=map.get(i);
 *    for(int i=0;i<sl.size();i++){
 *      for(int j=0;j<xl.size();j++){
 *         if(sl.get(i).getid().equal(xl.get(j).getpareantid())){
 *            sl.get(i).addChildren(xl.get(j));
 *         }
 *      }
 *    }
 * }
 *  map.get(0);//�������յĹ�����
 */
public class ReportRowColCrossTool {
	private static ReportItem[] m_crsResultValItems = null;
	// ������ά��ĸ����б�ͷ�ı�ͷ����
	private static FldgroupVO[] fldgroupVOs = null;
	// ���˳ߴ�
	private static int fielterNum = 0;
	// ������չ���ó�ʼλ��
	private static int itemstart = 0;
	// ���˼���
	private static int size = 0;
	// ������ͷ�е�ά������
	// ���������ݽ�����ɺ�
	// ���ø�ά�� ���� ���϶��ͷ��
	/**
	 * �������ݽ��� ������ά��Ĺ�����
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-15����11:20:17
	 * @param strCrsRows
	 *            Ҫ�������
	 * @param strCrsCols
	 *            Ҫ�������
	 * @param strCrsVals
	 *            Ҫ�����ֵ
	 * @throws Exception
	 */
	public static void onCross(ReportBaseUI ui, String[] strCrsRows,
			String[] strCrsCols, String[] strCrsVals) throws Exception {
		// ������ ������ Ҫ���� һ���ϲ���ά��
		// �������е� �����ֶε�ά�� ���� �����е����ݺϲ�����
		if (strCrsRows == null || strCrsRows.length == 0)
			throw new Exception("�����в���Ϊ��");
		if (strCrsCols == null || strCrsCols.length == 0)
			throw new Exception("�����в���Ϊ��");
		if (strCrsVals == null || strCrsVals.length == 0)
			throw new Exception("����ֵ����Ϊ��");
		// ���������� ����ά��
		StringBuffer comRows = new StringBuffer();
		for (int i = 0; i < strCrsRows.length; i++) {
			comRows.append(strCrsRows[i]);
			if (i != strCrsRows.length - 1) {
				comRows.append(" ");
			}
		}
		StringBuffer comCols = new StringBuffer();
		String[] cols = new String[strCrsCols.length + 1];
		for (int i = 0; i < strCrsCols.length; i++) {
			comCols.append(strCrsCols[i]);
			cols[i] = strCrsCols[i];
			if (i != strCrsCols.length - 1) {
				comCols.append(" ");
			}
		}
		cols[cols.length - 1] = "&type";
		fielterNum = strCrsVals.length;// ����ά��
		size = strCrsCols.length;
		itemstart = strCrsRows.length;// ��¼���������ɵĳ�ʼλ��
		String[] strows = new String[] { comRows.toString() };
		drawCrossTable(ui, strows,
				new String[] { comCols.toString(), "&type" }, strCrsVals);
	}
	
	
	/**
	 * �������ݽ��� ������ά��Ĺ�����
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-15����11:20:17
	 * @param strCrsRows
	 *            Ҫ�������
	 * @param strCrsCols
	 *            Ҫ�������
	 * @param strCrsVals
	 *            Ҫ�����ֵ
	 * @throws Exception
	 */
	public static void onCross(ReportBaseUI ui, String[] strCrsRows,
			String[] strCrsCols, String[] strCrsVals,int leavel) throws Exception {
		// ������ ������ Ҫ���� һ���ϲ���ά��
		// �������е� �����ֶε�ά�� ���� �����е����ݺϲ�����
		if (strCrsRows == null || strCrsRows.length == 0)
			throw new Exception("�����в���Ϊ��");
		if (strCrsCols == null || strCrsCols.length == 0)
			throw new Exception("�����в���Ϊ��");
		if (strCrsVals == null || strCrsVals.length == 0)
			throw new Exception("����ֵ����Ϊ��");
		// ���������� ����ά��
		StringBuffer comRows = new StringBuffer();
		for (int i = 0; i < strCrsRows.length; i++) {
			comRows.append(strCrsRows[i]);
			if (i != strCrsRows.length - 1) {
				comRows.append(" ");
			}
		}
		StringBuffer comCols = new StringBuffer();
		String[] cols = new String[strCrsCols.length + 1];
		for (int i = 0; i < strCrsCols.length; i++) {
			comCols.append(strCrsCols[i]);
			cols[i] = strCrsCols[i];
			if (i != strCrsCols.length - 1) {
				comCols.append(" ");
			}
		}
		cols[cols.length - 1] = "&type";
		fielterNum = strCrsVals.length;// ����ά��
		size = strCrsCols.length;
		itemstart = strCrsRows.length;// ��¼���������ɵĳ�ʼλ��
		String[] strows = new String[] { comRows.toString() };
		drawCrossTable(ui, strows,
				new String[] { comCols.toString(), "&type" }, strCrsVals,leavel);
	}


	private static void drawCrossTable(ReportBaseUI ui, String[] strCrsRows,
			String[] strCrsCols, String[] strCrsVals, int leavel) throws Exception {

		// �����ڴ�����Ԫ����
		MemoryResultSetMetaData mrsmd = getReportGeneralUtil(ui)
				.createMeteData();
		// �����ڴ�����
		Vector dataVec = ui.getReportBase().getBillModel().getDataVector();
		MemoryResultSet mrsOrg = getReportGeneralUtil(ui).vector2Mrs(dataVec,
				mrsmd);
		ZmCrossTable ct = new ZmCrossTable();
		ct.setStrSp("_");
		ct.setMrsOrg(mrsOrg);
		ct.setCrsRows(strCrsRows);
		ct.setCrsCols(strCrsCols);
		ct.setCrsVals(strCrsVals);
		ReportItem[] risOrg = ui.getReportBase().getBody_Items();
		String[] strMrsOrgDisNames = new String[risOrg.length];
		for (int i = 0; i < risOrg.length; i++)
			strMrsOrgDisNames[i] = risOrg[i].getName();
		ct.setMrsOrgColDisNames(strMrsOrgDisNames);
		MemoryResultSet mrsCrs = ct.getCrossTableMrs();
		ReportItem[] risNew = getCrossBody_Items(ui, strCrsRows.length, mrsCrs);
		FldgroupVO[] fgvos = ct.getCrossTableFldGrpVOs();
		if (fgvos != null)
			// ui.getReportBase().setFieldGroup(fgvos);
			ui.getReportBase().setBody_Items(risNew);
		Vector vecBodyDataVec = getReportGeneralUtil(ui).mrs2Vector(mrsCrs);
		ui.getReportBase().getBillModel().setDataVector(vecBodyDataVec);
		//���Ӻ���ϼ�
		// onTotal(ui,strCrsRows.length-1,vecBodyDataVec);
		

			try {
				createItem2(risNew, ui, false, leavel, vecBodyDataVec);
				ui.getReportBase().getBillModel().setDataVector(vecBodyDataVec);

				// ui.getReportBase().getBillModel().execLoadFormula();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		ui.getReportBase().getBillModel().updateValue();
		// ui.getReportBase().getBillModel().getBillModelData();
		ReportBaseVO[] vos = new ReportBaseVO[ui.getReportBase().getBillModel()
				.getRowCount()];
		for (int i = 0; i < vos.length; i++) {
			vos[i] = new ReportBaseVO();
		}

		ui.getReportBase().getBillModel().getBodyValueVOs(vos);

		ui.getBodyDataVO();
		ui.setBodyDataVO(vos, true);
		
//		// onTotal(ui,5);
//		createItem(fgvos, ui, false);
//		ui.getBodyDataVO();
		// ui.getReportBase().getBillModel().execLoadFormula();		
	}


	/**
	 * �� �����溯�����봫�뽻���У������У�������ֵ����
	 * 
	 * @param rows
	 *            java.lang.String[]
	 * @param columns
	 *            java.lang.String[]
	 */
	protected static void drawCrossTable(ReportBaseUI ui, String[] strCrsRows,
			String[] strCrsCols, String[] strCrsVals) throws Exception {
		// �����ڴ�����Ԫ����
		MemoryResultSetMetaData mrsmd = getReportGeneralUtil(ui)
				.createMeteData();
		// �����ڴ�����
		Vector dataVec = ui.getReportBase().getBillModel().getDataVector();
		MemoryResultSet mrsOrg = getReportGeneralUtil(ui).vector2Mrs(dataVec,
				mrsmd);
		ZmCrossTable ct = new ZmCrossTable();
		ct.setStrSp("_");
		ct.setMrsOrg(mrsOrg);
		ct.setCrsRows(strCrsRows);
		ct.setCrsCols(strCrsCols);
		ct.setCrsVals(strCrsVals);
		ReportItem[] risOrg = ui.getReportBase().getBody_Items();
		String[] strMrsOrgDisNames = new String[risOrg.length];
		for (int i = 0; i < risOrg.length; i++)
			strMrsOrgDisNames[i] = risOrg[i].getName();
		ct.setMrsOrgColDisNames(strMrsOrgDisNames);
		MemoryResultSet mrsCrs = ct.getCrossTableMrs();
		ReportItem[] risNew = getCrossBody_Items(ui, strCrsRows.length, mrsCrs);
		FldgroupVO[] fgvos = ct.getCrossTableFldGrpVOs();
		if (fgvos != null)
			// ui.getReportBase().setFieldGroup(fgvos);
			ui.getReportBase().setBody_Items(risNew);
		Vector vecBodyDataVec = getReportGeneralUtil(ui).mrs2Vector(mrsCrs);
		ui.getReportBase().getBillModel().setDataVector(vecBodyDataVec);
		// onTotal(ui,5);
		createItem(fgvos, ui, false);
		ui.getBodyDataVO();
		// ui.getReportBase().getBillModel().execLoadFormula();
	}

	/**	 
	   ��֧�ֺϼ� ���������ĸ��϶��ͷ ��Ҫ���� �������ж��е������ ���� ���϶��ͷ �÷��� ��Ҫ���� ���ݽṹ�е� �ݹ�����ĵķ�ʽ ������
	 * ���ͷ���� �Ƚ����Կ��� �� ά�� ��ѯ���浱�� �й��������б�ͷ �ķ��� ���� �Ǹ����������ĸ����б�ͷ ������ڶ�������е������ �ᵼ��
	 * �����ĸ����б�ͷ�����ݳ��� ���������ĸ����б�ͷ��item ����ȫ����� ���� �� д�˱��������ڹ��������б�ͷ����
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-16����03:54:16
	 * @param fgvos
	 * @param ui
	 */
	private static void createItem(FldgroupVO[] fgvos, ReportBaseUI ui,
			boolean istotal) {
		if (fgvos == null || fgvos.length == 0)
			return;
		UITable cardTable = ui.getReportBase().getBillTable();
		GroupableTableHeader cardHeader = (GroupableTableHeader) cardTable
				.getTableHeader();
		TableColumnModel cardTcm = cardTable.getColumnModel();
		// ��¼
		List<ZmColumnGroup> list = new ArrayList<ZmColumnGroup>();
		int start = itemstart;
		int cossize = fielterNum;
		int size = 0;// ��¼���ͷ�ĸ���
		FldgroupVO[] fgvos1 = filter(fgvos, cossize - 1);

		if (istotal == false) {
			for (int i = 0; i < fgvos1.length; i++) {
				String[] cbs = fgvos1[i].getGroupname().split("��");
				size = cbs.length;
				String colName = "null";
				String name = "null";
				if (size != 0) {
					colName = cbs[cbs.length - 1];
					for (int k = 0; k < cbs.length - 1; k++) {
						name = name + cbs[k];
					}
				}
				ZmColumnGroup cp = new ZmColumnGroup(colName);
				cp.setParentName(name);
				cp.setName(name + colName);
				for (int j = start; j < start + fielterNum; j++) {
					cp.add(cardTcm.getColumn(j));
				}
				start = start + fielterNum;
				list.add(cp);
			}
		} else {
			for (int i = 0; i < fgvos1.length; i++) {
				String[] cbs = fgvos1[i].getGroupname().split("��");
				size = cbs.length;
				String colName = cbs[cbs.length - 1];
				String name = "";
				for (int k = 0; k < cbs.length - 1; k++) {
					name = name + cbs[k];
				}
				ZmColumnGroup cp = new ZmColumnGroup(colName);
				cp.setParentName(name);
				cp.setName(name + colName);
				for (int j = start; j < start + fielterNum; j++) {
					String st = ui.getReportBase().getBillModel()
							.getColumnName(j);
					if ("�ϼ�".equals(st)) {
						continue;
					}
					cp.add(cardTcm.getColumn(j));
				}
				start = start + fielterNum;
				list.add(cp);
			}
		}
		Map<Integer, List<ZmColumnGroup>> map = new HashMap<Integer, List<ZmColumnGroup>>();
		map.put(size - 2, list);
		// ��ʼ�ݹ����� ���������б�ͷ
		for (int x = size - 2; x >= 0; x--) {
			// /////////////////
			List<ZmColumnGroup> list1 = map.get(x);
			List<ZmColumnGroup> flist = new ArrayList<ZmColumnGroup>();
			for (int i = 0; i < fgvos1.length; i++) {
				String[] cbs = fgvos1[i].getGroupname().split("��");
				String name = "null";
				String sname = "null";
				if (cbs.length != 0) {
					name = cbs[x];
				}
				for (int k = 0; k < x; k++) {
					sname = sname + cbs[k];
				}
				if (!isExist(sname + name, flist)) {
					ZmColumnGroup cp = new ZmColumnGroup(name);
					cp.setParentName(sname);
					cp.setName(sname + name);
					flist.add(cp);
				}
			}
			for (int i = 0; i < flist.size(); i++) {
				String name = flist.get(i).getName();
				for (int j = 0; j < list1.size(); j++) {
					if (name.equals(list1.get(j).getParentName())) {
						flist.get(i).add(list1.get(j));
					}
				}
			}
			map.put(x - 1, flist);
		}
		List<ZmColumnGroup> flist1 = new ArrayList<ZmColumnGroup>();
		flist1 = map.get(-1);
		for (int i = 0; i < flist1.size(); i++) {
			cardHeader.addColumnGroup(flist1.get(i));
		}
		ui.getReportBase().getBillModel().updateValue();
	}

	private static boolean isExist(String name, List<ZmColumnGroup> flist) {
		if (name == null) {
			return false;
		}
		if (flist == null || flist.size() == 0)
			return false;
		boolean isExist = false;
		for (int i = 0; i < flist.size(); i++) {
			if (flist.get(i).getName().equals(name)) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}
	/**
	 * ���ߴ��������
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-16����09:17:45
	 * @param fgvos
	 * @param cossize
	 * @return
	 */
	private static FldgroupVO[] filter(FldgroupVO[] fgvos, int cossize) {
		if (fgvos == null || fgvos.length == 0)
			return null;
		if (cossize == 0)
			cossize = 1;
		List<FldgroupVO> list = new ArrayList<FldgroupVO>();
		for (int i = 0; i < fgvos.length; i = i + cossize) {
			list.add(fgvos[i]);
		}
		return list.toArray(new FldgroupVO[0]);
	}

	protected static ReportGeneralUtil getReportGeneralUtil(ReportBaseUI ui) {
		return ui.getReportBase().getReportGeneralUtil();
	}

	/**
	 * ͨ������� ��ȡ����item ��������:(2002-11-5 17:34:05)
	 * @author mlr ���Բ�ѯ���� ����ѯ���� �д��� �������Ѿ�����
	 * @return nc.ui.pub.report.ReportItem[]
	 */
	protected static ReportItem[] getCrossBody_Items(int lenRows,
			MemoryResultSet mrsCrs, ReportItem[] items) throws Exception {
		MemoryResultSetMetaData mrsmd = mrsCrs.getMetaData0();
		ReportItem[] riCrs = new ReportItem[mrsmd.getColumnCount()];
		Vector vecResultValueItem = new Vector();
		for (int i = 0; i < mrsmd.getColumnCount(); i++) {
			ReportItem ri = new ReportItem();
			String strKey = mrsmd.getColumnName(i + 1);
			if (i < lenRows) {
				ri.setKey(strKey);
			} else {
				StringTokenizer st = new StringTokenizer(strKey, "_");
				StringBuffer sb = new StringBuffer();
				while (st.hasMoreElements()) {
					String letter = StringUtil.getPYIndexStr(st.nextElement()
							.toString(), true);
					sb.append(letter);
					sb.append("_");
				}
				ri.setKey(sb.toString());
				ri.setNote(strKey);
				vecResultValueItem.add(ri);
			}
			ri.setDataType(sqlType2itemType(mrsmd.getColumnType(i + 1)));
			ReportItem riOrg = getBody_Item(strKey, items);
			if (riOrg != null) {
				ri.setName(riOrg.getName());
				ri.setDataType(riOrg.getDataType());
			} else if (strKey.equals("&type")) {
				ri.setName("");
			} else if (strKey.equals("value")) {
				ri.setName("");
			} else {
				int index = strKey.lastIndexOf("_");
				ri.setName(strKey.substring(index + 1));
				ri.setDataType(IBillItem.DECIMAL);
			}
			ri.setWidth(80);
			riCrs[i] = ri;
		}
		m_crsResultValItems = (ReportItem[]) vecResultValueItem
				.toArray(new ReportItem[0]);
		return riCrs;
	}

	/**
	 * �˴����뷽��˵��. ��������:(2002-12-19 10:32:10)
	 * @return int
	 * @param isqlType
	 */
	public static int sqlType2itemType(int isqlType) {
		int iitemType = BillItem.STRING;
		switch (isqlType) {
		case Types.INTEGER: {
			iitemType = BillItem.INTEGER;
			break;
		}
		case Types.DECIMAL: {
			iitemType = BillItem.DECIMAL;
			break;
		}
		case Types.DOUBLE: {
			iitemType = BillItem.DECIMAL;
			break;
		}
		case Types.DATE: {
			iitemType = BillItem.DATE;
			break;
		}
		case Types.TIME: {
			iitemType = BillItem.TIME;
			break;
		}
		}
		return iitemType;
	}

	/**
	 * �˴����뷽��˵��.
	 * 
	 * @return nc.ui.pub.report.ReportItem
	 * @param key
	 */
	public static ReportItem getBody_Item(String key, ReportItem[] items) {
		BillItem[] bis = items;
		if (bis == null)
			return null;
		else {
			ReportItem[] riAlls = new ReportItem[bis.length];
			for (int i = 0; i < bis.length; i++)
				riAlls[i] = (ReportItem) bis[i];
			int index = -1;
			for (int i = 0; i < riAlls.length; i++)
				if (riAlls[i].getKey().equals(key)) {
					index = i;
					break;
				}
			if (index == -1)
				return null;
			else
				return riAlls[index];
		}
	}

	/**
	 * ͨ������� ��ȡ����item ��������:(2002-11-5 17:34:05)
	 * @author mlr ���Բ�ѯ���� ����ѯ���� �д��� �������Ѿ�����
	 * @return nc.ui.pub.report.ReportItem[]
	 */
	protected static ReportItem[] getCrossBody_Items(ReportBaseUI ui,
			int lenRows, MemoryResultSet mrsCrs) throws Exception {
		MemoryResultSetMetaData mrsmd = mrsCrs.getMetaData0();
		ReportItem[] riCrs = new ReportItem[mrsmd.getColumnCount()];
		Vector vecResultValueItem = new Vector();
		for (int i = 0; i < mrsmd.getColumnCount(); i++) {
			ReportItem ri = new ReportItem();
			String strKey = mrsmd.getColumnName(i + 1);
			if (i < lenRows) {
				ri.setKey(strKey);
			} else {
				StringTokenizer st = new StringTokenizer(strKey, "_");
				StringBuffer sb = new StringBuffer();
				while (st.hasMoreElements()) {
					String letter = StringUtil.getPYIndexStr(st.nextElement()
							.toString(), true);
					sb.append(letter);
					sb.append("_");
				}
				ri.setKey(sb.toString());
				ri.setNote(strKey);
				vecResultValueItem.add(ri);
			}

			ri.setDataType(getReportGeneralUtil(ui).sqlType2itemType(
					mrsmd.getColumnType(i + 1)));
			ReportItem riOrg = ui.getReportBase().getBody_Item(strKey);
			if (riOrg != null) {
				ri.setName(riOrg.getName());
				ri.setDataType(riOrg.getDataType());
			} else if (strKey.equals("&type")) {
				ri.setName("");
			} else if (strKey.equals("value")) {
				ri.setName("");
			} else {
				int index = strKey.lastIndexOf("_");
				ri.setName(strKey.substring(index + 1));
				ri.setDataType(BillItem.DECIMAL);
			}
			ri.setWidth(80);
			riCrs[i] = ri;
		}
		m_crsResultValItems = (ReportItem[]) vecResultValueItem
				.toArray(new ReportItem[0]);
		return riCrs;
	}

	private class Item {
		private String code = null;
	}

	public static void onCross(ReportBaseUI ui, MemoryResultSetMetaData mrsmd,
			Vector vc, ReportItem[] risOrg, String[] strCrsRows,
			String[] strCrsCols, String[] strCrsVals, boolean istotal,
			int leaval) throws Exception {
		// ������ ������ Ҫ���� һ���ϲ���ά��
		// �������е� �����ֶε�ά�� ���� �����е����ݺϲ�����
		if (strCrsRows == null || strCrsRows.length == 0)
			throw new Exception("�����в���Ϊ��");
		if (strCrsCols == null || strCrsCols.length == 0)
			throw new Exception("�����в���Ϊ��");
		if (strCrsVals == null || strCrsVals.length == 0)
			throw new Exception("����ֵ����Ϊ��");
		// ���������� ����ά��
		StringBuffer comRows = new StringBuffer();
		for (int i = 0; i < strCrsRows.length; i++) {
			comRows.append(strCrsRows[i]);
			if (i != strCrsRows.length - 1) {
				comRows.append(" ");
			}
		}
		StringBuffer comCols = new StringBuffer();
		String[] cols = new String[strCrsCols.length + 1];
		for (int i = 0; i < strCrsCols.length; i++) {
			comCols.append(strCrsCols[i]);
			cols[i] = strCrsCols[i];
			if (i != strCrsCols.length - 1) {
				comCols.append(" ");
			}
		}
		cols[cols.length - 1] = "&type";
		fielterNum = strCrsVals.length;// ����ά��
		itemstart = strCrsRows.length;// ��¼���������ɵĳ�ʼλ��
		size = strCrsCols.length;
		String[] strows = new String[] { comRows.toString() };
		drawCrossTable(ui, mrsmd, vc, risOrg, strows, new String[] {
				comCols.toString(), "&type" }, strCrsVals, istotal, leaval);
	}

	/**
	 * ����������ת��Ϊ�ڴ�����
	 * @return nc.vo.pub.cquery.QryResultVO[]
	 * @param headerdata
	 */
	public static MemoryResultSet vector2Mrs(Vector vecData,
			MemoryResultSetMetaData mrsmd) {
		ArrayList al = new ArrayList();
		for (int i = 0; i < vecData.size(); i++) {
			Vector vecRow = (Vector) vecData.elementAt(i);
			ArrayList alRow = new ArrayList();
			for (int j = 0; j < vecRow.size(); j++)
				alRow.add(vecRow.elementAt(j));
			al.add(alRow);
		}
		MemoryResultSet mrs = new MemoryResultSet(al, mrsmd);
		return mrs;
	}

	/**
	 * ���ڴ�����ת��Ϊ��������
	 * @return nc.vo.pub.cquery.QryResultVO[]
	 * @param headerdata
	 */
	public static Vector mrs2Vector(MemoryResultSet rs, ReportItem[] items) {
		Vector v = new Vector();
		Object[] objFlds = null;
		Object objTemp = null;
		try {
			MemoryResultSetMetaData rsmd = rs.getMetaData0();
			int iColCount = rsmd.getColumnCount();
			// ���ÿ�е���������(��Ҫ��Ϊ��������������ͨ��getObject()��������,������Ҫ���ַ��͵��ֶ�ʹ��getstring()����)
			int[] iColTypes = new int[iColCount];
			for (int i = 0; i < iColCount; i++)
				iColTypes[i] = rsmd.getColumnType(i + 1);
			while (rs.next()) {
				objFlds = new Object[iColCount]; // ��Ҫ
				for (int i = 1; i <= iColCount; i++) {
					// Ϊ������С�ƺϼ����⴦��,������С�ƺϼƵĽ��ΪArrayList
					objTemp = rs.getObject(i);
					if (objTemp != null && objTemp instanceof ArrayList) {
						ArrayList valueList = (ArrayList) objTemp;
						BillItem bis = items[i - 1];
						// ��ʽ��
						if (bis.getDataType() == BillItem.DECIMAL) {
							int digits = bis.getDecimalDigits();
							if (digits < 0)
								digits = -digits;
							for (int vi = 0, vn = valueList.size(); vi < vn; vi++) {
								Object o = valueList.get(vi);
								if (o instanceof BigDecimal) {
									valueList.set(vi, ((BigDecimal) o)
											.setScale(digits,
													BigDecimal.ROUND_HALF_UP));
								}
							}
						}
						objFlds[i - 1] = valueList;
						continue;
					}

					if (iColTypes[i - 1] == Types.CHAR
							|| iColTypes[i - 1] == Types.VARCHAR) {
						objTemp = rs.getString(i); // ��ͬ���ݿ��е��ַ��������ֶ��Ƿ����������ʽ?
						String strColName = rsmd.getColumnName(i);
						ReportItem riCol = getBody_Item(strColName, items);

						if (riCol != null
								&& riCol.getDataType() == BillItem.BOOLEAN)
							objFlds[i - 1] = (objTemp == null) ? new UFBoolean(
									'N') : new UFBoolean(objTemp.toString()
									.trim());
						else
							objFlds[i - 1] = (objTemp == null) ? "" : objTemp
									.toString().trim();
					} else {
						objTemp = rs.getObject(i); // ���ֱ����getString,���ڶ����������ֶ�ʱ�����
						if (iColTypes[i - 1] == Types.DECIMAL
								|| iColTypes[i - 1] == Types.DOUBLE) {
							if (objTemp == null)
								objFlds[i - 1] = null;
							else {
								String strColName = rsmd.getColumnName(i);
								ReportItem riCol = getBody_Item(strColName,
										items);
								;
								if (riCol != null) {
									int iprecision = riCol.getDecimalDigits();
									objFlds[i - 1] = new UFDouble(objTemp
											.toString(), iprecision);
								} else
									objFlds[i - 1] = new UFDouble(objTemp
											.toString());
							}
						} else if (iColTypes[i - 1] == Types.INTEGER) {
							Integer iobj = null;
							if (objTemp == null)
								objFlds[i - 1] = null;
							else {
								String str = objTemp.toString();
								int indexpoint = str.indexOf(".");
								if (indexpoint != -1)
									iobj = new Integer(str.substring(0,
											indexpoint));
								else
									iobj = new Integer(str);
								objFlds[i - 1] = iobj;
							}
						} else
							objFlds[i - 1] = (objTemp == null) ? "" : objTemp;
					}
				}
				v.addElement(objFlds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ���ñ�����Ϣ
		Vector vecData = new Vector();
		for (int i = 0; i < v.size(); i++) {
			objFlds = (Object[]) v.elementAt(i);
			Vector vecTemp = new Vector();
			for (int j = 0; j < objFlds.length; j++)
				vecTemp.addElement(objFlds[j]);
			vecData.addElement(vecTemp);
		}
		return vecData;
	}

	/**
	 * �� �����溯�����봫�뽻���У������У�������ֵ����
	 * @param leaval
	 * @param istotal
	 * @param rows
	 * @param columns
	 */
	protected static void drawCrossTable(ReportBaseUI ui,
			MemoryResultSetMetaData mrsmd, Vector vc, ReportItem[] risOrg,
			String[] strCrsRows, String[] strCrsCols, String[] strCrsVals,
			boolean istotal, int leaval) throws Exception {
		// �����ڴ�����Ԫ����
		// MemoryResultSetMetaData mrsmd = getReportGeneralUtil(ui)
		// .createMeteData();
		// �����ڴ�����
		Vector dataVec = vc;
		MemoryResultSet mrsOrg = vector2Mrs(dataVec, mrsmd);
		ZmCrossTable ct = new ZmCrossTable();
		ct.setStrSp("_");
		ct.setMrsOrg(mrsOrg);
		ct.setCrsRows(strCrsRows);
		ct.setCrsCols(strCrsCols);
		ct.setCrsVals(strCrsVals);
		// ReportItem[] risOrg = ui.getReportBase().getBody_Items();
		String[] strMrsOrgDisNames = new String[risOrg.length];
		for (int i = 0; i < risOrg.length; i++)
			strMrsOrgDisNames[i] = risOrg[i].getName();
		ct.setMrsOrgColDisNames(strMrsOrgDisNames);
		MemoryResultSet mrsCrs = ct.getCrossTableMrs();
		ReportItem[] risNew = getCrossBody_Items(strCrsRows.length, mrsCrs,
				risOrg);
		FldgroupVO[] fgvos = ct.getCrossTableFldGrpVOs();
		if (fgvos != null)
			// ui.getReportBase().setFieldGroup(fgvos);
			// ui.getReportBase().getbo
			ui.getReportBase().setBody_Items(risNew);
		Vector vecBodyDataVec = mrs2Vector(mrsCrs, risOrg);
		// onTotal(ui,strCrsRows.length-1,vecBodyDataVec);
		if (istotal) {

			try {
				createItem2(risNew, ui, false, leaval, vecBodyDataVec);
				ui.getReportBase().getBillModel().setDataVector(vecBodyDataVec);

				// ui.getReportBase().getBillModel().execLoadFormula();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			createItem(fgvos, ui, istotal);
			ui.getReportBase().getBillModel().setDataVector(vecBodyDataVec);

		}
		ui.getReportBase().getBillModel().updateValue();
		// ui.getReportBase().getBillModel().getBillModelData();
		ReportBaseVO[] vos = new ReportBaseVO[ui.getReportBase().getBillModel()
				.getRowCount()];
		for (int i = 0; i < vos.length; i++) {
			vos[i] = new ReportBaseVO();
		}

		ui.getReportBase().getBillModel().getBodyValueVOs(vos);

		ui.getBodyDataVO();
		ui.setBodyDataVO(vos, true);
		// ui.getReportBase().getBillModel().execLoadFormula();

	}
	/**
	 * ֧�ֺ���ϼƵĶ�̬�д���
	 * 
	 * ֧�ֺϼ� ���������ĸ��϶��ͷ ��Ҫ���� �������ж��е������ ���� ���϶��ͷ �÷��� ��Ҫ���� ���ݽṹ�е� �ݹ�����ĵķ�ʽ ������
	 * ���ͷ���� �Ƚ����Կ��� �� ά�� ��ѯ���浱�� �й��������б�ͷ �ķ��� ���� �Ǹ����������ĸ����б�ͷ ������ڶ�������е������ �ᵼ��
	 * �����ĸ����б�ͷ�����ݳ��� ���������ĸ����б�ͷ��item ����ȫ����� ���� �� д�˱��������ڹ��������б�ͷ����
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-16����03:54:16
	 * @param fgvos
	 * @param ui
	 */
	private static void createItem2(ReportItem[] items, ReportBaseUI ui,
			boolean istotal, int totalleavl, Vector<Vector> vc) {
		if (items == null || items.length == 0)
			return;
		// ���ݺϼƵļ��� ��������ĺϼ��� items �����¹���vc����
		//vc��BillModel��һ�����ݽṹ  BillModel��Item �� Vector 
		//Item ��UI�ؼ�   ��Vector�ǿؼ���Ӧ������
		List<ReportItem> its = createTotalItem(items, totalleavl, ui, vc);
		// �õ����������ù��� �ù������ڹ��������б�ͷ
		TableColumnModel ct = getTableColumnModel(ui);
		items = its.toArray(new ReportItem[0]);
		// ������̬�����б�ͷ
		List<ZmColumnGroup> list = new ArrayList<ZmColumnGroup>();
		//������һ���ĸ����б�ͷ �����ĸ����б�ͷ���ݷŵ� list��
		createItem1(list, items, ct, totalleavl);
		//���ڴ�� �������εĸ����б�ͷ
		Map<Integer, List<ZmColumnGroup>> map = new HashMap<Integer, List<ZmColumnGroup>>();
		//����һ���ĸ����б�ͷ�ŵ�map��
		map.put(size - 2, list);
		// �ݹ����� ���� 2��---��� һ���ĸ����б�ͷ
		createItem12(map, items, ui, totalleavl);
	}

	private static void createItem12(Map<Integer, List<ZmColumnGroup>> map,
			ReportItem[] items, ReportBaseUI ui, int totalleavl) {
		// ��ʼ�ݹ����� ���������б�ͷ
		for (int x = size - 2; x >= 0; x--) {
			List<ZmColumnGroup> list1 = map.get(x);
			List<ZmColumnGroup> flist = new ArrayList<ZmColumnGroup>();
			TableColumnModel ct = getTableColumnModel(ui);
			for (int i = itemstart; i < items.length; i++) {
				if (items[i].getNote() == null
						|| items[i].getNote().length() == 0)
					continue;
				String[] cbs = items[i].getNote().split("��");
				String name = "";
				String sname = "";
				if (cbs.length != 0) {
					name = cbs[x];
				}
				for (int k = 0; k < x; k++) {
					sname = sname + cbs[k];
				}
				//�鿴��ǰ�����Ƿ� �Ѿ��ﵽ�ϼ��еĸ����б�ͷ����Ӽ���
				if (x == size - totalleavl) {
					//����ﵽ�� �ϼ��еĸ����еļ��� ��ô��Ҫ�����ϼ��еĸ���
					if (ct.getColumn(i).getHeaderValue().equals("�ϼ�")) {
						ZmColumnGroup cp = new ZmColumnGroup(name);
						cp.setParentName(sname);
						cp.setName(sname + name);
						flist.add(cp);
						//��ԭʼ�ϼ�item����ӵ������ж�����
						cp.add(ct.getColumn(i));
					}
				} else {
					//���û�дﵽ�ϼƵļ��� �ǾͰ��������ĸ����д�����ʽ
					//����������
					if (!isExist(sname + name, flist)) {
						ZmColumnGroup cp = new ZmColumnGroup(name);
						cp.setParentName(sname);
						cp.setName(sname + name);
						flist.add(cp);
					}
				}
			}
			//�������¼��εĸ����еĹ���
			//�� �������ĸ�����  Ҫ�����ǵ��¼������� ��ӽ��� �������νṹ
			//flistΪ�ϼ�������
			//list1Ϊ�¼������� ParentName���Ӧ���ϼ������е� nameһ��
			//����ͨ�����ֹ�ϵ �Ϳ��Թ��� ���¼�֮��Ĺ�ϵ �����Թ���������
			for (int i = 0; i < flist.size(); i++) {
				String name = flist.get(i).getName();
				for (int j = 0; j < list1.size(); j++) {
					if (name.equals(list1.get(j).getParentName())) {
						ZmColumnGroup zm = list1.get(j);
						flist.get(i).add(list1.get(j));
					}
				}
			}
			map.put(x - 1, flist);
		}
		//�����������ĸ�����
		//��Ϊ���ǹ�������һ�����νṹ ����ֻ��ȡ������ĸ����м��Ͼ�����
		List<ZmColumnGroup> flist1 = new ArrayList<ZmColumnGroup>();
		flist1 = map.get(-1);//ȡ����ĸ�����
		//��ø�����ui���ù���
		UITable cardTable = ui.getReportBase().getBillTable();
		GroupableTableHeader cardHeader = (GroupableTableHeader) cardTable
				.getTableHeader();
		//������ĸ�������ӵ�ui�� 
		for (int i = 0; i < flist1.size(); i++) {
			cardHeader.addColumnGroup(flist1.get(i));
		}

	}

	private static TableColumnModel getTableColumnModel(ReportBaseUI ui) {
		UITable cardTable = ui.getReportBase().getBillTable();
		GroupableTableHeader cardHeader = (GroupableTableHeader) cardTable
				.getTableHeader();
		TableColumnModel cardTcm = cardTable.getColumnModel();
		return cardTcm;
	}

	/**
	 * �÷�������д���� UI�ؼ� ��Item
	 * ΪʲôҪ��д����Item�أ�
	 * ����Ϊ Ҫ��ԭ��item�Ļ���֮�� ��Ӻ���ϼ���  
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-1-5����07:11:53
	 * @param items
	 * @param ui
	 * @param vc
	 */
	private static List<ReportItem> createTotalItem(ReportItem[] items,
			int totalleavl, ReportBaseUI ui, Vector<Vector> vc) {
		int size = ReportRowColCrossTool.size;
		List<ReportItem> its = new ArrayList<ReportItem>();//
		//�����ݽ���������� item ��װ��һ��List������
		for (int i = 0; i < items.length; i++) {
			its.add(items[i]);
		}
		//��¼��ӺϼƵ�λ��
		List<String> totals = new ArrayList<String>();// �ϼ�
		for (int i = itemstart; i < items.length; i = i + fielterNum) {
			if (items[i].getNote() == null || items[i].getNote().length() == 0)
				continue;
			String[] cbs = items[i].getNote().split("��");
			size = cbs.length;
			String colName = "";
			String name = "";
			if (size != 0) {
				//���ָ�����ε�����
				colName = cbs[cbs.length - totalleavl];
				for (int k = 0; k < cbs.length - totalleavl; k++) {
					name = name + cbs[k];
				}
			}
			//����Ӧ���ε����� ��ӵ�totals
			if (!isExist1(totals, name + colName)) {
				totals.add(name + colName);
			}
		}
		//���ݽ�������Ķ�̬�еĵ�һ��������λ��
		int st = itemstart;
		try {
			//��ʼ��  its ��������Ӻϼ�����
			int totalstart = itemstart;
			for (String key : totals) {
				boolean isEqual = true;
				for (int i = st; i < its.size(); i++) {
					String[] cbs = its.get(i).getNote().split("��");
					String colName = "";
					String name = "";
					if (size != 0) {
						colName = cbs[cbs.length - totalleavl];
						for (int k = 0; k < cbs.length - totalleavl; k++) {
							name = name + cbs[k];
						}
					}
					if (key.equals(name + colName)) {
						isEqual = true;
					} else {
						isEqual = false;
					}
					if (isEqual == false && !its.get(i).getName().equals("�ϼ�")) {

						ReportItem item = ReportPubTool.getItem("num" + i,
								"�ϼ�", IBillItem.DECIMAL, i, 80);
						item.setNote(its.get(i - 1).getNote());
						its.add(i, item);//׷��һ���ϼ���
						createData(i, vc);//׷��һ���ϼ�ֵ ������Ϊ0						
						//����׷����item����ϼ�ֵ
						//totalstartΪ����ϼ�ֵ�ĳ�ʼ����
						//i-1Ϊ����ϼ�ֵ�Ľ�������
						//������ ����λ�� totalstart ��ʼ  ��i-1Ϊֹ��������� 
						//�ӵ�����λ��Ϊi��
						//�����úϼ��� ���ö�Ӧֵ
						caltotal(its, totalstart, i, vc);
						totalstart = i + 1;
						st = i + 1;
						break;
					}
				}
			}
			// ������һ�еĺϼ�
			if (its.size() > 0) {
				ReportItem item = ReportPubTool.getItem("num" + its.size(),
						"�ϼ�", IBillItem.DECIMAL, its.size(), 80);
				item.setNote(its.get(its.size() - 1).getNote());
				its.add(its.size(), item);
				createData(its.size() - 1, vc);
				caltotal(its, totalstart, its.size() - 1, vc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
        //��ΪBillModel��item�Ѿ������˱仯 ����Ҫ��д����BillModel
		ui.getReportBase().setBody_Items(its.toArray(new ReportItem[0]));
		return its;
	}

	/**
	 * ����ϼ�ֵ 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-1-6����07:49:49
	 * @param its
	 * @param vc
	 * @param totalstart
	 * @param i
	 */
	private static void caltotal(List<ReportItem> its, int startindex,
			int endindex, Vector<Vector> vc) {
		if (vc == null || vc.size() == 0)
			return;
		for (int j = 0; j < vc.size(); j++) {
			UFDouble uf = new UFDouble(0.0);
			Vector vt = vc.get(j);
			for (int i = startindex; i < endindex; i++) {
				uf = uf.add(PuPubVO.getUFDouble_NullAsZero(vt.get(i)));
			}
			vt.set(endindex, uf);
		}
	}

	/**
	 * ���ڴ����ĺϼƹ�ʽ��ִ��ʱ ��������ת���쳣
	 * ���� ���Ҳ����ô˷���
	 * �����ϼƹ�ʽ
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-1-6����12:47:23
	 * @param its
	 * @param itemstart2
	 * @param i
	 */
	private static void createFormular(List<ReportItem> its, int itemstart2,
			int index) {
		String formu = " num" + (index) + "-> ";
		for (int i = itemstart2; i < index; i++) {
			formu = formu + its.get(i).getKey() + "+";
		}
		its.get(index).setLoadFormula(
				new String[] { formu.substring(0, formu.length() - 1) });
	}

	/**
	 * ָ�����������һ���ϼ�ֵ ��ʼֵ Ϊ 0
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-1-6����12:13:47
	 * @param i
	 * @param vc
	 */
	private static void createData(int index, Vector<Vector> vc) {
		if (vc == null || vc.size() == 0)
			return;
		for (int i = 0; i < vc.size(); i++) {
			vc.get(i).add(index, new UFDouble(0.0));
		}
	}

	/**
	 *  ������һ���ĸ�����
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-1-5����07:10:02
	 * @param list
	 * @param items
	 * @param cardTcm
	 */
	private static void createItem1(List<ZmColumnGroup> list,
			ReportItem[] items, TableColumnModel cardTcm, int leaval) {
		//�����б�ͷ���ж��ټ�
		int size = ReportRowColCrossTool.size;
		//���ݽ�������Ķ�̬�е� ��һ��������λ��  ����ʼ���������б�ͷ�ĳ�ʼλ��
		int start = itemstart;
		for (int i = itemstart; i < items.length; i++) {
			if (items[i].getNote() == null || items[i].getNote().length() == 0)
				continue;

			String[] cbs = items[i].getNote().split("��");
			size = cbs.length;
			String colName = "";
			String name = "";
			if (size != 0) {
				colName = cbs[cbs.length - 1];
				for (int k = 0; k < cbs.length - 1; k++) {
					name = name + cbs[k];
				}
			}
			//����һ���µĸ����б�ͷ�Ķ���
			ZmColumnGroup cp = new ZmColumnGroup(colName.split("_")[0]);
			//���øö�����һ��������
			cp.setParentName(name);
			//���øö��������
			cp.setName(name + colName);
			boolean istotal = false;
			for (int j = start; j < start + fielterNum; j++) {
				cp.add(cardTcm.getColumn(j));
				if (cardTcm.getColumn(j).getHeaderValue().equals("�ϼ�")) {
					istotal = true;
				}
			}
			start = start + fielterNum;
			//ֻ�Բ��Ǻϼ��е�item ����һ�������� 
			//���� ����ϼ��е���Ӽ����ǵ�һ���Ļ� 
			//��ô ��ȻҪ���ϼ��� ������һ���ĸ�����
			if (istotal == false || leaval == 1)
				list.add(cp);
		}
	}

	private static boolean isExist1(List<String> totals, String string) {
		boolean isE = false;
		for (int i = 0; i < totals.size(); i++) {
			if (string.equals(totals.get(i))) {
				isE = true;
			} else {
				isE = false;
			}
		}
		return isE;
	}

}

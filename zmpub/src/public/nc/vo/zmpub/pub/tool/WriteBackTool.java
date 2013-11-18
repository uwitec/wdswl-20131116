package nc.vo.zmpub.pub.tool;

import java.util.ArrayList;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ArrayProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;

/**
 * ���ڻ�д�Ĺ�����
 * 
 * @author mlr
 * @modify zhf ֧�� ��λ�д
 * @date 2011-9-13
 */
public class WriteBackTool {
	private static BaseDAO dao = null;
	protected static String vsourcebillrowid = "vsourcebillrowid";// Ĭ����Դ��id
	// �������Ĭ����Ҫ����
	protected static String vsourcebilltype = "vsourcebilltype";
	protected static String vsourcebillid = "vsourcebillid";

	protected static BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	// private static Map<String,WriteBackVO> addmap= new
	// HashMap<String,WriteBackVO>();//���������д������
	protected static List<WriteBackVO> ladd = new ArrayList<WriteBackVO>();// ���������д������
	protected static List<WriteBackVO> ledit = new ArrayList<WriteBackVO>();// ����޸Ļ�д������
	protected static List<WriteBackVO> ldel = new ArrayList<WriteBackVO>();// ���ɾ����д������

	public static void setVsourcebillrowid(String id) {
		vsourcebillrowid = id;
	}

	public static void setVsourcebillid(String id) {
		vsourcebillid = id;
	}

	public static void setVsourcebilltype(String type) {
		vsourcebilltype = type;
	}

	/**
	 * ��д�������ڷ���
	 * 
	 * @throws Exception
	 * @param vos
	 *            ��д�����vo����
	 * @param soutablename
	 *            ��Դ����
	 * @param soutableidname
	 *            ��Դ����������
	 * @param fieldnames
	 *            Ҫ��д���ֶ�
	 * @param backfieldnames
	 *            ��Դ���д��Ӧ�ֶ�
	 */
	public static void writeBack(SuperVO[] vos, String soutablename,
			String soutableidname, String[] fieldnames, String[] backfieldnames)
			throws Exception {
		if (vos == null || vos.length == 0)
			return;
		clearMap1();
		try {
			splitSetMap(vos, soutablename, soutableidname, fieldnames,
					backfieldnames);
			// �������ݵĻ�д
			writeBackSou();
		} catch (Exception e) {
			clearMap();
			throw new Exception(e.getMessage());
		} finally {
			clearMap();
		}
	}

	/**
	 * ��д�������ڷ���
	 * 
	 * @throws Exception
	 * @param vos
	 *            ��д�����vo����
	 * @param soutablename
	 *            ��Դ����
	 * @param soutableidname
	 *            ��Դ����������
	 * @param fieldnames
	 *            Ҫ��д���ֶ�
	 * @param backfieldnames
	 *            ��Դ���д��Ӧ�ֶ�
	 * @param checkfieldnames
	 *            ��Դ����ƻ�д�ֶε��ֶ� �ͻ�д�ֶζ�Ӧ
	 */
	public static void writeBack(SuperVO[] vos, String soutablename,
			String soutableidname, String[] fieldnames,
			String[] backfieldnames, String[] checkfieldnames) throws Exception {
		clearMap1();
		// ���ֻ�д��vo����
		splitSetMap(vos, soutablename, soutableidname, fieldnames,
				backfieldnames);
		// �������ݵĻ�д
		writeBackSou();
		check(vos, soutablename, soutableidname, backfieldnames,
				checkfieldnames);
		clearMap();
	}

	private static void clearMap() {
		ladd.clear();
		ledit.clear();
		ldel.clear();
		setVsourcebillrowid("vsourcebillrowid");// ����ΪĬ��ֵ
		setVsourcebillid("vsourcebillid");
		setVsourcebilltype("vsourcebilltype");
	}

	private static void clearMap1() {
		ladd.clear();
		ledit.clear();
		ldel.clear();
	}

	/**
	 * �������ݵĻ�д
	 * 
	 * @throws Exception
	 */
	private static void writeBackSou() throws Exception {
		writeBackAdd();// ��д��������
		writeBackEdit();// ��д�޸�����
		writeBackDete();// ��дɾ������
	}

	/**
	 * ��ɾ�����ݵĻ�д
	 * 
	 * @throws Exception
	 */
	private static void writeBackDete() throws Exception {
		if (ldel == null || ldel.size() == 0)
			return;
		for (WriteBackVO vo : ldel) {
			// WriteBackVO vo=delemap.get(key);
			UFDouble[] oldnums = getOldNums(vo);// ������
			for (int i = 0; i < oldnums.length; i++) {
				oldnums[i] = new UFDouble(0).sub(oldnums[i]);
			}
			vo.setNums(oldnums);
		}
		writeBackToSource(ldel);
	}

	/**
	 * ������ݿ��оɵĲ�ѯ����
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	private static UFDouble[] getOldNums(WriteBackVO vo) throws Exception {
		String sql = vo.getQueryOldSql();
		if (sql == null) {
			return null;
		}
		Object o = getDao().executeQuery(sql, new ArrayProcessor());
		if (o == null) {
			throw new Exception(" Ҫ��д�������Ѿ������� ,���ִ���");
		}
		Object[] obs = (Object[]) o;
		if (obs == null || obs.length == 0)
			throw new Exception(" Ҫ��д�������Ѿ������� ,���ִ���");

		UFDouble[] oldnums = new UFDouble[obs.length];
		for (int i = 0; i < obs.length; i++) {
			oldnums[i] = PuPubVO.getUFDouble_NullAsZero(obs[i]);
		}
		return oldnums;
	}

	/**
	 * ���޸����ݵĻ�д
	 * 
	 * @throws DAOException
	 */
	private static void writeBackEdit() throws Exception {
		if (ledit == null || ledit.size() == 0) {
			return;
		}
		for (WriteBackVO vo : ledit) {
			// WriteBackVO vo=editmap.get(key);
			UFDouble[] oldnums = getOldNums(vo);// ������
			if (oldnums == null) {
				continue;
			}
			UFDouble[] newnums = vo.getNums();// ������
			if (newnums == null || newnums.length == 0) {
				throw new Exception(" Ҫ��д�������Ѿ������� ,���ִ���");
			}
			if (oldnums == null || oldnums.length == 0) {
				throw new Exception(" Ҫ��д�������Ѿ������� ,���ִ���");
			}
			UFDouble[] editnums = new UFDouble[newnums.length];// ������ Ҫ��д������
			for (int i = 0; i < oldnums.length; i++) {
				editnums[i] = newnums[i].sub(oldnums[i]);
			}
			vo.setNums(editnums);// ��������Ļ�д���� �����дvo
		}
		writeBackToSource(ledit);// �����ݻ�д����Դ,�������ݿ����

	}

	/**
	 * ���л�д����,�������ݿ�
	 * 
	 * @param map
	 * @throws Exception
	 */
	private static void writeBackToSource(List<WriteBackVO> map)
			throws Exception {
		if (map == null || map.size() == 0) {
			return;
		}
		for (WriteBackVO vo : map) {
			String sql = vo.getWriteBackSql();
			if (sql == null) {
				continue;
			}
			getDao().executeUpdate(sql);
		}
	}

	/**
	 * ���������ݵĻ�д
	 * 
	 * @throws Exception
	 */
	private static void writeBackAdd() throws Exception {
		writeBackToSource(ladd);
	}

	/**
	 * �÷������� ������ �޸� ɾ�� �Ļ�д���ֳ���������װ�ɱ���������Ҫ����������
	 * 
	 * @param vos
	 * @param soutablename
	 * @param soutableid
	 * @param backfieldnames
	 * @param backnums
	 * @throws Exception
	 */
	protected static void splitSetMap(SuperVO[] vos, String soutablename,
			String soutableidname, String[] fieldnames, String[] backfieldnames)
			throws Exception {
		if (vos == null || vos.length == 0) {
			return;
		}
		if (soutablename == null || soutablename.length() == 0) {
			return;
		}
		if (soutableidname == null || soutableidname.length() == 0) {
			return;
		}
		if (backfieldnames == null || backfieldnames.length == 0) {
			return;
		}
		if (backfieldnames.length != fieldnames.length) {
			throw new Exception("��д���ݲ��Ϸ�");
		}
		for (SuperVO vo : vos) {
			// �������Ϊ�� ��Ϊ��������
			if (vo.getStatus() == VOStatus.NEW || vo.getPrimaryKey() == null
					|| vo.getStatus() == VOStatus.UNCHANGED) {
				setValue(vo, soutablename, soutableidname, fieldnames,
						backfieldnames);
				// addmap.put(PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue(vsourcebillrowid)),backvo);
				ladd.add(backvo);
			} else if (vo.getStatus() == VOStatus.UPDATED) {
				setValue(vo, soutablename, soutableidname, fieldnames,
						backfieldnames);
				// editmap.put(PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue(vsourcebillrowid)),backvo);
				// editmap.put(PuPubVO.getString_TrimZeroLenAsNull(vo.getPrimaryKey()),backvo);
				ledit.add(backvo);
			} else {
				setValue(vo, soutablename, soutableidname, fieldnames,
						backfieldnames);
				// delemap.put(PuPubVO.getString_TrimZeroLenAsNull(vo.getPrimaryKey()),backvo);
				ldel.add(backvo);
			}
		}
	}

	private static WriteBackVO backvo = null;

	/**
	 * ����д��vo��ֵ
	 * 
	 * @param vo
	 * @param soutablename
	 *            ��Դ����
	 * @param soutableidname
	 *            ��Դ��id����
	 * @param fieldnames
	 *            Ҫ��д�ı���ֶ�����
	 * @param backfieldnames
	 *            Ҫ��д����Դ���ֶ�������
	 */
	protected static void setValue(SuperVO vo, String soutablename,
			String soutableidname, String[] fieldnames, String[] backfieldnames) {
		backvo = new WriteBackVO();
		backvo.setIdname(soutableidname);
		backvo.setIdvalue(PuPubVO.getString_TrimZeroLenAsNull(vo
				.getAttributeValue(vsourcebillrowid)));
		UFDouble[] backnums = new UFDouble[fieldnames.length];
		for (int i = 0; i < fieldnames.length; i++) {
			backnums[i] = PuPubVO.getUFDouble_NullAsZero(vo
					.getAttributeValue(fieldnames[i]));
		}
		backvo.setNums(backnums);
		backvo.setNumsnames1(fieldnames);
		backvo.setSourcetablename(soutablename);
		backvo.setVsourcebillid(PuPubVO.getString_TrimZeroLenAsNull(vo
				.getAttributeValue(vsourcebillid)));
		backvo.setVsourcebillrowid(PuPubVO.getString_TrimZeroLenAsNull(vo
				.getAttributeValue(vsourcebillrowid)));
		backvo.setVsourcebilltype(PuPubVO.getString_TrimZeroLenAsNull(vo
				.getAttributeValue(vsourcebilltype)));
		backvo.setNumsnames(backfieldnames);
		backvo.setTablename(PuPubVO.getString_TrimZeroLenAsNull(vo
				.getTableName()));
		backvo.setId(PuPubVO.getString_TrimZeroLenAsNull(vo.getPrimaryKey()));
		backvo.setIdname1(PuPubVO.getString_TrimZeroLenAsNull(vo
				.getPKFieldName()));
	}

	/**
	 * ��д�������У�� zhf
	 * 
	 * @throws Exception
	 * @param checkfieldnames
	 *            ��Դ�� �����ֶ� ���� �� ������� ��д �������� checkfieldnamesΪ�������� ʵ�ʳ�������
	 * @param backfieldnames
	 *            ��Դ��� �ۼ����� ���磺 ����,backfieldnamesΪ�������� �ۼ��������
	 */
	public static void check(SuperVO[] vos, String soutablename,
			String soutableidname, String[] backfieldnames,
			String[] checkfieldnames) throws Exception {
		if (vos == null || vos.length == 0)
			return;
		if (backfieldnames.length != checkfieldnames.length)
			throw new BusinessException("����������Ȳ�һ��");

		List<String> lsouid = new ArrayList<String>();
		String tmps = null;
		for (SuperVO vo : vos) {
			tmps = PuPubVO.getString_TrimZeroLenAsNull(vo
					.getAttributeValue(vsourcebillrowid));
			if (tmps == null || lsouid.contains(tmps))
				continue;
			lsouid.add(tmps);
		}

		if (lsouid.size() == 0)
			return;

		// У���ۼƻ�д�� ���ܴ��� ������
		StringBuffer str = new StringBuffer();
		str.append(" select count(0) from ");
		str.append(soutablename);
		str.append(" where isnull(dr,0)= 0 ");
		str.append(" and " + soutableidname + " in "
				+ getSubSql(lsouid.toArray(new String[0])));
		str.append(" and (");
		for (int i = 0; i < backfieldnames.length; i++) {
			if (i > 0)
				str.append(" or ");
			str.append("  abs(coalesce(" + checkfieldnames[i]
					+ ",0.0))-abs(coalesce(" + backfieldnames[i] + ",0.0))<0 ");
		}
		str.append(" )");
		int ivalue = PuPubVO.getInteger_NullAs(getDao().executeQuery(
				str.toString(), new ColumnProcessor()), 0);
		if (ivalue > 0)
			throw new BusinessException("������Դ������������");

		// У���ۼ�������Ϊ����
		str.setLength(0);
		str.append(" select count(0) from ");
		str.append(soutablename);
		str.append(" where isnull(dr,0)= 0 ");
		str.append(" and " + soutableidname + " in "
				+ getSubSql(lsouid.toArray(new String[0])));
		str.append(" and (");
		for (int i = 0; i < backfieldnames.length; i++) {
			if (i > 0)
				str.append(" or ");
			str.append(" coalesce(" + backfieldnames[i] + ",0.0)<0 ");
		}
		str.append(" )");

		ivalue = PuPubVO.getInteger_NullAs(getDao().executeQuery(
				str.toString(), new ColumnProcessor()), 0);
		if (ivalue > 0)
			throw new BusinessException("���ݻ�д�쳣");
	}

	public static String getSubSql(String[] saID) {
		String sID = null;
		StringBuffer sbSql = new StringBuffer("(");
		for (int i = 0; i < saID.length; i++) {
			if (i > 0) {
				sbSql.append(",");
			}
			sbSql.append("'");
			sID = saID[i];
			if (sID == null) {
				sID = "";
			}
			sbSql.append(sID);
			sbSql.append("'");
		}
		sbSql = sbSql.append(")");
		return sbSql.toString();
	}
}

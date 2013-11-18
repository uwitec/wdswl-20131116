package nc.bs.zmpub.pub.tool.stock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import nc.bs.logging.Logger;
import nc.bs.pub.SystemException;
import nc.bs.zmpub.pub.report.ReportDMO;
import nc.ui.scm.util.ObjectUtils;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.tool.CombinVO;

/**
 * ��������ѯ��������
 * 
 * @author mlr
 */
public abstract class AvailNumBoTool extends BillStockBO {
	private static final long serialVersionUID = 5669705676536970653L;
	private ReportDMO dmo = null;

	public ReportDMO getDMO() throws SystemException, NamingException {
		if (dmo == null) {
			dmo = new ReportDMO();
		}
		return dmo;
	}

	// �����������ò��� start
	@Override
	public Map<String, String> getTypetoChangeClass() throws Exception {
		return null;
	}

	@Override
	public Map<String, UFBoolean[]> getTypetosetnum() throws Exception {
		return null;
	}

	// �����������ò��� end

	/**
	 * �õ���������->�ִ����Ķ�Ӧ�ֶ� �� ���� �е��ֶ� �� �ִ��� �е��ֶζ�Ӧ��ϵ
	 */
	public abstract Map<String, String[]> getTypetoAccountFields()
			throws Exception;

	/**
	 * �õ��������� ->������ѯ���
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-26����10:18:34
	 * 
	 */
	public abstract Map<String, String> getTypetoQuerySql() throws Exception;

	/**
	 * 
	 * �õ������� �������ִ���vo��װ�Ĳ�ѯ���� �˷����ͻ��˵���
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-26����10:13:19
	 * 
	 */
	public SuperVO[] getAvailNumForClient(SuperVO[] vos) throws Exception {
		// �õ��ִ���
		SuperVO[] nvos = queryStockCombinForClient(vos);
		if (nvos == null || nvos.length == 0)
			return null;
		// �õ�����ռ����
		ReportBaseVO[] dvos = getAvailNumForClient1(vos);
		if (dvos == null || dvos.length == 0)
			return nvos;
		// �õ�������
		SuperVO[] zvos = getNums(nvos, dvos);
		return zvos;
	}

	/**
	 * 
	 * �õ������� �������ִ���vo��װ�Ĳ�ѯ���� �˷�����̨����
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-26����10:13:19
	 * 
	 */
	public SuperVO[] getAvailNumDatas(SuperVO[] vos) throws Exception {
		// �õ��ִ���
		SuperVO[] nvos = super.queryStockCombin(vos);
		if (nvos == null || nvos.length == 0)
			return null;
		// �õ�����ռ����
		ReportBaseVO[] dvos = getAvailNum1(vos);
		if (dvos == null || dvos.length == 0)
			return nvos;
		// �õ�������
		SuperVO[] zvos = getNums(nvos, dvos);
		return zvos;
	}
    /**
     * ����ռ����
     * @���ߣ�mlr
     * @˵�������ɽ������Ŀ 
     * @ʱ�䣺2012-7-30����11:24:51
     *
     */
	protected ReportBaseVO[] getAvailNum1(SuperVO[] voss) throws Exception {
		SuperVO[] vos=(SuperVO[]) ObjectUtils.serializableClone(voss);
		// �õ�������ѯ�Ĺ�������
		Map<String, List<String>> whereSqls = getWhereSqls(vos);
		// �õ���ӹ���������Ķ�����ѯsql
		Map<String, List<String>> sqls = getSqls(whereSqls);
		// �õ�����ռ����
		ReportBaseVO[] dvos = getOrderNums(vos, sqls);
		return dvos;
	}

	protected ReportBaseVO[] getAvailNumForClient1(SuperVO[] vos)
			throws Exception {
		// �õ�������ѯ�Ĺ�������
		Map<String, List<String>> whereSqls = getWhereSqls(vos);
		// �õ���ӹ���������Ķ�����ѯsql
		Map<String, List<String>> sqls = getSqls(whereSqls);
		// �õ�����ռ����
		ReportBaseVO[] dvos = getOrderNumsForClient(vos, sqls);
		return dvos;
	}

	/**
	 * �õ������� ������=�ִ���->Ԥ�Ʒ�����
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-26����01:08:07
	 * 
	 */
	protected SuperVO[] getNums(SuperVO[] nvos, ReportBaseVO[] dvos)
			throws Exception {
		if (nvos == null || nvos.length == 0)
			return null;
		if (dvos == null || dvos.length == 0)
			return nvos;
		if (nvos.length != dvos.length)
			return nvos;
		for (int i = 0; i < nvos.length; i++) {
			SuperVO nvo = nvos[i];
			ReportBaseVO dvo = dvos[i];
			if (nvo == null)
				continue;
			if (dvo == null)
				continue;
			String[] fields = getChangeNums();
			if (fields == null || fields.length == 0)
				throw new Exception("û��ע���ִ����仯�ֶ�");
			for (int j = 0; j < fields.length; j++) {
				// �õ� �ִ���
				UFDouble nuf = PuPubVO.getUFDouble_NullAsZero(nvo
						.getAttributeValue(fields[j]));
				// �õ� Ԥ�Ƴ�����
				UFDouble duf = PuPubVO.getUFDouble_NullAsZero(dvo
						.getAttributeValue(fields[j]));
				// ���ÿ�����
				nvo.setAttributeValue(fields[j], nuf.sub(duf));
			}
		}
		return nvos;
	}

	/**
	 * �õ������� Ԥ�Ƴ����� for��̨��ѯ
	 * 
	 * @throws Exception
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-26����10:20:59
	 * 
	 */
	public ReportBaseVO[] getAvailNum(SuperVO[] vos) throws Exception {
		// �õ�������ѯ�Ĺ�������
		Map<String, List<String>> whereSqls = getWhereSqls(vos);
		// �õ���ӹ���������Ķ�����ѯsql
		Map<String, List<String>> sqls = getSqls(whereSqls);
		// �õ�����ռ����
		ReportBaseVO[] dvos = getOrderNums(vos, sqls);
		return dvos;
	}

	public ReportBaseVO[] getOrderNumsForClient(SuperVO[] vos,
			Map<String, List<String>> sqls) throws Exception {

		ReportBaseVO[] nvos = null;
		try {
			Class[] ParameterTypes = new Class[] { SuperVO[].class, Map.class };

			Object[] ParameterValues = new Object[] { vos, sqls };
			Object o = LongTimeTask.calllongTimeService("zmpub", null,
					"���ڲ�ѯ...", 1, getThisClassName(), null, "getOrderNums",
					ParameterTypes, ParameterValues);
			if (o != null) {
				nvos = (ReportBaseVO[]) o;
			}
		} catch (Exception e) {
			Logger.error(e);
			throw new Exception(e.getMessage());

		}
		return nvos;
	}

	/**
	 * �õ�����ռ����
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-26����10:29:40
	 * 
	 */
	public ReportBaseVO[] getOrderNums(SuperVO[] vos,
			Map<String, List<String>> sqls) throws Exception {
		// ��ѯ�����Ŀ�����
		ReportBaseVO[] nvos = querySqls(sqls);
		// �õ��� ��ѯά�Ⱥϲ��� �Ŀ�����
		ReportBaseVO[] zvos = getCombinVos(vos, nvos);
		return zvos;
	}

	/**
	 * ����ѯά�Ⱥϲ�����������
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-26����12:07:40
	 * 
	 */
	protected ReportBaseVO[] getCombinVos(SuperVO[] vos, ReportBaseVO[] nvos)
			throws Exception {
		ArrayList<ReportBaseVO> nlist = new ArrayList<ReportBaseVO>();
		if (vos == null || vos.length == 0)
			return null;
		if (nvos == null || nvos.length == 0)
			return null;
		for (int i = 0; i < vos.length; i++) {
			Map<String, String> conds = getConminFields1(vos[i]);
			String[] conds1 = getConminFields(vos[i]);
			if (conds == null || conds.size() == 0) {
				nlist.add(null);
			} else {
				// ��ø�ά���µĶ���������
				ReportBaseVO[] vos1 = getVos(conds, nvos);
				ReportBaseVO[] vos3=null;
				if(vos1!=null && vos1.length!=0)
					vos3=(ReportBaseVO[]) ObjectUtils.serializableClone(vos1);
				ReportBaseVO[] coms = (ReportBaseVO[]) CombinVO.combinData(
						vos3, conds1, getChangeNums(), ReportBaseVO.class);
				if (coms == null || coms.length == 0) {
					nlist.add(null);
				} else {
					nlist.add(coms[0]);
				}

			}

		}
		if (nlist == null || nlist.size() == 0)
			return null;
		return nlist.toArray((ReportBaseVO[]) java.lang.reflect.Array
				.newInstance(ReportBaseVO.class, 0));
	}

	/**
	 * ������ݺϲ�ά��
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-2����12:52:49
	 * 
	 */
	public Map<String, String> getConminFields1(SuperVO vo) throws Exception {

		Map<String, String> map = new HashMap<String, String>();// ��� �ϲ��ֶεļ�ֵ��
		String[] fields = getDef_Fields();
		if (fields == null || fields.length == 0)
			throw new Exception("û��ע���ִ���ά��");
		if (vo == null)
			return null;
		for (int i = 0; i < fields.length; i++) {
			String value = PuPubVO.getString_TrimZeroLenAsNull(vo
					.getAttributeValue(fields[i]));
			if (value != null) {
				map.put(fields[i], value);
			}
		}
		if (map == null || map.size() == 0)
			return null;

		return map;
	}

	/**
	 * �������ά�� ��������
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-26����12:49:53
	 * 
	 */
	private ReportBaseVO[] getVos(Map<String, String> conds, ReportBaseVO[] nvos) {
		if (conds == null || conds.size() == 0)
			return nvos;
		if (nvos == null || nvos.length == 0)
			return null;
		List<ReportBaseVO> list = new ArrayList<ReportBaseVO>();
		for (int i = 0; i < nvos.length; i++) {
			boolean bo = true;
			for (String key : conds.keySet()) {
				String qvalue = PuPubVO.getString_TrimZeroLenAsNull(conds
						.get(key));
				String nvalue = PuPubVO.getString_TrimZeroLenAsNull(nvos[i]
						.getAttributeValue(key));
				if (qvalue == null || nvalue == null) {
					bo = false;
					break;
				}
				if (!qvalue.equals(nvalue)) {
					bo = false;
					break;
				}
			}
			if (bo == true) {
				list.add(nvos[i]);
			}
		}
		return list.toArray(new ReportBaseVO[0]);
	}

	/**
	 * ��ѯ�����Ŀ�����
	 * 
	 * @throws NamingException
	 * @throws SQLException
	 * @throws SystemException
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-26����12:07:28
	 * 
	 */
	private ReportBaseVO[] querySqls(Map<String, List<String>> sqls)
			throws SystemException, SQLException, NamingException {
		List<String> list = new ArrayList<String>();

		if (sqls == null || sqls.size() == 0)
			return null;
		for (String key : sqls.keySet()) {
			List<String> li = sqls.get(key);
			list.addAll(li);
		}
		if (list == null || list.size() == 0)
			return null;
		List<ReportBaseVO[]> vos = getDMO().queryVOBySql(
				list.toArray(new String[0]));
		if (vos == null || vos.size() == 0)
			return null;
		List<ReportBaseVO> listvo = new ArrayList<ReportBaseVO>();
		for (int i = 0; i < vos.size(); i++) {
			ReportBaseVO[] rvos = vos.get(i);
			if (rvos == null || rvos.length == 0)
				continue;
			for (int j = 0; j < rvos.length; j++) {
				listvo.add(rvos[j]);
			}
		}
		return listvo.toArray(new ReportBaseVO[0]);
	}

	/**
	 * �õ���ӹ���������� ������ѯsql
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-26����10:25:59
	 * 
	 */
	public Map<String, List<String>> getSqls(Map<String, List<String>> whereSqls)
			throws Exception {
		Map<String, String> sqls = getTypetoQuerySql();
		if (sqls == null || sqls.size() == 0)
			throw new Exception("û��ע�ᵥ������->������ѯsql");
		Map<String, List<String>> map = new HashMap<String, List<String>>();

		if (whereSqls == null || whereSqls.size() == 0)
			return null;
		for (String type : whereSqls.keySet()) {
			List<String> wsqls = whereSqls.get(type);
			List<String> sqlzs = new ArrayList<String>();
			String sql = sqls.get(type);
			if (sql == null || sql.length() == 0)
				throw new Exception("��������[" + type + "] û��ע�ᶩ����ѯ���");
			if (wsqls == null || wsqls.size() == 0) {
				List<String> list = new ArrayList<String>();
				list.add(sql);
				map.put(type, list);
				continue;
			}
			for (int i = 0; i < wsqls.size(); i++) {
				String zsql = sql + " and " + wsqls.get(i);
				sqlzs.add(zsql);
			}
			map.put(type, sqlzs);
		}
		return map;
	}

	/**
	 * �õ�������ѯ�Ĺ�������
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-26����10:25:32
	 * 
	 */
	public Map<String, List<String>> getWhereSqls(SuperVO[] vos1)
			throws Exception {
	
		if (vos1 == null || vos1.length == 0)
			return null;
		SuperVO[] vos=(SuperVO[]) CombinVO.combinData(vos1, getDef_Fields(),getChangeNums() , vos1[0].getClass());
		// �������� --->�ִ��� ��Ӧ�ֶ�
		Map<String, String[]> map = getTypetoAccountFields();
		// �������� --->����sql
		Map<String, List<String>> mapn = new HashMap<String, List<String>>();
		if (map == null || map.size() == 0)
			throw new Exception("û��ע�ᵥ�����͵��ִ����Ķ�Ӧ�ֶ�");
		for (String type : map.keySet()) {
			// ��� ��������
			List<String> list = new ArrayList<String>();
			String[] fields = map.get(type);
			// �õ��ִ���ά���ֶ�
			String[] acfs = getDef_Fields();
			if (acfs == null || acfs.length == 0)
				throw new Exception("û��ע���ִ�����Сά���ֶ�");
			if (fields.length != acfs.length)
				throw new Exception("��������[" + type + "] ���ִ�����Ӧ�ֶ� ���Ȳ�һ��");
			for (int i = 0; i < vos.length; i++) {
				String whereSql = null;
				SuperVO vo = vos[i];
				for (int j = 0; j < acfs.length; j++) {
					if (PuPubVO.getString_TrimZeroLenAsNull(vos[i]
							.getAttributeValue(acfs[j])) == null) {
						continue;
					}
					if (fields[j] == null || fields[j].length() == 0)
						continue;
					if (whereSql == null) {
						whereSql = fields[j] + " = '"
								+ vo.getAttributeValue(acfs[j]) + "'";
					} else {
						whereSql = whereSql + " and " + fields[j] + " = '"
								+ vo.getAttributeValue(acfs[j]) + "'";
					}
				}
				list.add(whereSql);
			}
			mapn.put(type, list);
		}
		return mapn;
	}
}

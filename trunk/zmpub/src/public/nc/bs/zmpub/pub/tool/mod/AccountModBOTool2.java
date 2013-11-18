package nc.bs.zmpub.pub.tool.mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import nc.bs.dao.BaseDAO;
import nc.bs.pub.SystemException;
import nc.bs.zmpub.pub.report.ReportDMO;
import nc.bs.zmpub.pub.tool.SingleVOChangeDataBsTool;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.zmpub.pub.report.ReportBaseVO;

/**
 * �ִ����޸�������
 * 
 * @author mlr ���ڼƻ�̨��BOû�а�����ʵ�� ���� ���ñ仯�����㷨 �� ��Сά�Ⱥϲ� �Լ� ���¼ƻ������㷨 �ڱ���ʵ��
 *         �Ƕ�AccountModBOTool�ĸĽ� ʵ���˶����ݵĶ��η�װ ��������ע��
 */
public abstract class AccountModBOTool2 {
	private ReportDMO dmo = null;

	public ReportDMO getDMO() throws SystemException, NamingException {
		if (dmo == null) {
			dmo = new ReportDMO();
		}
		return dmo;
	}

	private BaseDAO dao = null;

	public BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	public AccountModBOTool2() {
		init();
	}

	protected void init() {
	}

	/**
	 * 
	 * �÷���Ŀ���ǻ�ȡ Ҫ�޸���ҵ�񵥾ݵ� �������ݵĻ����޸���Ϣ String ---�������� BillData---ע��ĵ�����Ϣ
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-11-11����03:31:05
	 * @return
	 * @throws Exception
	 */
	public abstract Map<String, BillData> getBillDataMap() throws Exception;

	/**
	 * �÷����������ڻ�ȡ ���ݹ�����Ϣ
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-11-11����03:33:15
	 * @return
	 * @throws Exception
	 */
	public abstract PubBillData getPubBillData() throws Exception;

	/**
	 * �÷��������ڻ�ȡ�ִ�ע����Ϣ
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-11-11����03:34:40
	 * @return
	 * @throws Exception
	 */
	public abstract AccountData getAccountData() throws Exception;

	/**
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-11-11����03:33:56
	 * @param vos
	 * @param isNumCirl
	 * @throws Exception
	 */
	public void setAccountNum(SuperVO[] vos, UFBoolean[] isNumCirl)
			throws Exception {

		AccountData account = getAccountData();
		if (account == null) {
			throw new Exception("û��ע���ִ�����Ϣ");
		}
		if (account.getSetNumFields() == null
				|| account.getSetNumFields().length == 0)
			throw new Exception("û�����ִ���ע����  ע��仯�ֶ���Ϣ");
		if (vos == null || vos.length == 0)
			return;
		for (int j = 0; j < vos.length; j++) {
			String[] fields = account.getSetNumFields();
			for (int i = 0; i < fields.length; i++) {
				if (isNumCirl[i] == null) {
					vos[j].setAttributeValue(fields[i], new UFDouble(0));
				} else if (isNumCirl[i].booleanValue() == true) {
					UFDouble num = PuPubVO.getUFDouble_NullAsZero(vos[j]
							.getAttributeValue(fields[i]));
					vos[j].setAttributeValue(fields[i], num.multiply(-1));
				}
			}
		}
	}

	/**
	 * ��ѯ���е���Ҫ�޸��� �ִ�����vo
	 * 
	 * @return
	 */
	public ReportBaseVO[] queryModVO() throws Exception {
		PubBillData pb = getPubBillData();
		if (pb == null) {
			throw new Exception("û��ע�ᵥ�ݹ�����Ϣ");
		}
		String sql = pb.getQuerySql();
		String[] sql11 = pb.getQuerySql1();
		ReportBaseVO[] mvos = null;
		List<ReportBaseVO> lists = new ArrayList<ReportBaseVO>();
		if (sql != null && sql.length() != 0) {
			mvos = getDMO().queryVOBySql(sql);
		} else {
			if (sql11 == null || sql11.length == 0)
				throw new Exception("û���ڵ��ݹ�����Ϣ����ע��ҵ�񵥾ݲ�ѯ���");
			List<ReportBaseVO[]> list = getDMO().queryVOBySql(sql11);
			if (list == null || list.size() == 0)
				return null;
			for (int i = 0; i < list.size(); i++) {
				mvos = list.get(i);
				if (mvos != null && mvos.length != 0)
					lists.addAll(Arrays.asList(mvos));
			}
		}
		if (lists != null && lists.size() != 0)
			return lists.toArray(new ReportBaseVO[0]);
		return mvos;
	}

	public Map<String, ReportBaseVO[]> getTypetomodvo(ReportBaseVO[] vos)
			throws Exception {
		PubBillData pb = getPubBillData();
		if (pb == null) {
			throw new Exception("û��ע�ᵥ�ݹ�����Ϣ");
		}
		String billtype = pb.getBillTypeName();
		if (billtype == null || billtype.length() == 0)
			throw new Exception(" û��ע�ᵥ������");
		if (vos == null || vos.length == 0) {
			return null;
		}
		ReportBaseVO[][] voss = (ReportBaseVO[][]) SplitBillVOs.getSplitVOs(
				vos, new String[] { billtype });
		if (voss == null || voss.length == 0)
			return null;
		Map<String, ReportBaseVO[]> map = new HashMap<String, ReportBaseVO[]>();
		for (int i = 0; i < voss.length; i++) {
			ReportBaseVO[] vs = voss[i];
			String btype = PuPubVO.getString_TrimZeroLenAsNull(vs[0]
					.getAttributeValue(billtype));
			if (btype == null || btype == null) {
				continue;
			}
			setAccountModVOBYStatus(map, btype, vs);
		}
		return map;
	}

	/**
	 * ����typetostatus �������� ״̬��Ӧmap �����ִ����޸�vo
	 * 
	 * @param map
	 * @param btype
	 * @param vs
	 * @throws Exception
	 */
	public void setAccountModVOBYStatus(Map<String, ReportBaseVO[]> map,
			String btype, ReportBaseVO[] vs) throws Exception {
		Map<String, BillData> bd = getBillDataMap();
		if (bd == null || bd.size() == 0)
			throw new Exception("û��ע��ҵ�񵥾���Ϣ");

		BillData ac = bd.get(btype);
		if (ac == null) {
			throw new Exception("�� " + btype + "���͵ĵ���û��ע�ᵥ����Ϣ");
		}
		boolean[] isQuerys = ac.getBillStatus();
		if (isQuerys == null || isQuerys.length == 0) {
			return;
		}
		if (isQuerys.length != 2) {
			throw new Exception("��ȡ�������Ͷ�Ӧ �ĵ���״̬����");
		}
		if (isQuerys[0] == true && isQuerys[1] == true) {
			if (vs != null && vs.length != 0)
				map.put(btype, vs);
		}
		if (isQuerys[0] == true && isQuerys[1] == false) {
			if (vs == null || vs.length == 0)
				return;
			ReportBaseVO[] nvs = getModvosByStatus(IBillStatus.FREE, vs);
			if (nvs != null && nvs.length != 0)
				map.put(btype, nvs);
		}
		if (isQuerys[0] == false && isQuerys[1] == true) {
			if (vs == null || vs.length == 0)
				return;
			ReportBaseVO[] nvs = getModvosByStatus(IBillStatus.CHECKPASS, vs);
			if (nvs != null && nvs.length != 0)
				map.put(btype, nvs);
		}
	}

	/**
	 * ���ݵ���״̬���� vs
	 * 
	 * @param checkpass
	 * @param vs
	 * @throws Exception
	 */
	public ReportBaseVO[] getModvosByStatus(int billStatus, ReportBaseVO[] vs)
			throws Exception {
		PubBillData pb = getPubBillData();
		if (pb == null) {
			throw new Exception("û��ע�ᵥ�ݹ�����Ϣ");
		}
		String status = pb.getBillTypeStatusName();
		if (status == null || status.length() == 0)
			throw new Exception(" û��ע�ᵥ��״̬����");
		if (vs == null || vs.length == 0)
			return null;
		List<ReportBaseVO> list = new ArrayList<ReportBaseVO>();
		for (int i = 0; i < vs.length; i++) {
			Integer stus = PuPubVO.getInteger_NullAs(vs[i]
					.getAttributeValue(status), -1);
			if (stus == billStatus) {
				list.add(vs[i]);
			}
		}
		if (list == null || list.size() == 0)
			return null;
		return list.toArray(new ReportBaseVO[0]);
	}

	// getTypetoChangeClass
	/**
	 * ��� �ִ���vo
	 */
	public Map<String, SuperVO[]> getNumVO(Map<String, ReportBaseVO[]> map)
			throws Exception {
		if (map == null || map.size() == 0)
			return null;
		AccountData account = getAccountData();
		if (account == null) {
			throw new Exception("û��ע���ִ�����Ϣ");
		}
		Map<String, SuperVO[]> nummap = new HashMap<String, SuperVO[]>();// ����ִ���vo
		String className = account.getNumClass();
		Class cl = Class.forName(className);
		if (className == null || className.length() == 0)
			throw new Exception("û��ע���ִ�����Ӧ��ȫ��");
		ReportBaseVO[] rvos = null;
		SuperVO[] svos = null;
		String changClass = null;// ������

		Map<String, BillData> bd = getBillDataMap();
		if (bd == null || bd.size() == 0)
			throw new Exception("û��ע��ҵ�񵥾���Ϣ");

		for (String key : map.keySet()) {
			rvos = map.get(key);
			if (rvos == null || rvos.length == 0)
				continue;
			if (bd.get(key) == null)
				throw new Exception("�� " + key + " ��������û��ע��");
			changClass = bd.get(key).getChangeClass();
			if (changClass == null || changClass.length() == 0)
				throw new Exception(" �������ͱ���Ϊ :" + key + "��û��ע�ύ����");
			svos = (SuperVO[]) SingleVOChangeDataBsTool.runChangeVOAry(rvos,
					cl, changClass);
			if (svos != null && svos.length != 0)
				nummap.put(key, svos);
		}
		return nummap;
	}

	/**
	 * �����ִ������ݱ仯��
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void setAccountNumChange(Map<String, SuperVO[]> map)
			throws Exception {
		if (map == null || map.size() == 0)
			return;
		AccountData account = getAccountData();
		if (account == null) {
			throw new Exception("û��ע���ִ�����Ϣ");
		}
		String[] fields = account.getSetNumFields();
		Map<String, BillData> bd = getBillDataMap();
		if (bd == null || bd.size() == 0)
			throw new Exception("û��ע��ҵ�񵥾���Ϣ");

		if (fields == null || fields.length == 0)
			throw new Exception(" û��ע���ִ����仯���ֶ�");
		SuperVO[] vos = null;
		for (String key : map.keySet()) {
			if (bd.get(key) == null)
				throw new Exception("�� " + key + " ��������û��ע��");
			UFBoolean[] ufs = bd.get(key).getIsChangeNum();
			if (ufs == null || ufs.length == 0 || ufs.length != fields.length)
				throw new Exception("��������Ϊ" + key
						+ " ע��ı仯������Ϊ�� ��  ע��Ĺ�������ͱ仯���ֶ����鳤�Ȳ�һ��");
			vos = map.get(key);
			if (vos == null || vos.length == 0)
				continue;
			setAccountNum(vos, ufs);
		}
	}

	/**
	 * ���������ʽ���ִ���vo
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public SuperVO[] getAccountNum(Map<String, SuperVO[]> map) throws Exception {
		if (map == null || map.size() == 0)
			return null;
		List<SuperVO> list = new ArrayList<SuperVO>();
		SuperVO[] svos = null;
		for (String key : map.keySet()) {
			svos = map.get(key);
			if (svos != null && svos.length != 0)
				list.addAll(Arrays.asList(svos));
		}
		AccountData account = getAccountData();
		if (account == null) {
			throw new Exception("û��ע���ִ�����Ϣ");
		}
		if (list != null && list.size() != 0) {
			String className = account.getNumClass();
			if (className == null || className.length() == 0)
				throw new Exception("û��ע���ִ���ʵ����");
			Class cl = Class.forName(className);
			return list.toArray((SuperVO[]) java.lang.reflect.Array
					.newInstance(cl, 0));
		}
		return null;
	}

	/**
	 * ����Сά�Ƚ������ݺϲ�
	 */
	/**
	 * @author zhf ��̨��ͳ��ά�Ⱥϲ�����
	 * @param accounts
	 * @return
	 * @throws Exception
	 */
	public SuperVO[] combinAccounts(SuperVO[] accounts) throws Exception {
		if (accounts == null || accounts.length == 0)
			return null;
		AccountData account = getAccountData();
		if (account == null) {
			throw new Exception("û��ע���ִ�����Ϣ");
		}
		String[] num_condition_fields = account.getUnpk();
		if (num_condition_fields == null || num_condition_fields.length == 0)
			throw new Exception(" û��ע���ִ�����Сά���ֶ�");

		CircularlyAccessibleValueObject[][] os = SplitBillVOs.getSplitVOs(
				accounts, num_condition_fields);
		int len = os.length;
		String className = account.getNumClass();
		if (className == null || className.length() == 0)
			throw new Exception("û��ע���ִ���ʵ����");
		if (account.getSetNumFields() == null
				|| account.getSetNumFields().length == 0)
			throw new Exception(" û�����ִ���ע������ע���ִ����仯�ֶ�");
		Class cl = Class.forName(className);
		SuperVO[] newAccouts = (SuperVO[]) java.lang.reflect.Array.newInstance(
				cl, len);
		SuperVO[] datas = null;
		SuperVO tmp = null;
		for (int i = 0; i < len; i++) {
			datas = (SuperVO[]) os[i];
			tmp = datas[0];
			for (int j = 0; j < datas.length; j++) {
				if (j == 0)
					continue;
				for (String num : account.getSetNumFields()) {
					tmp.setAttributeValue(num, PuPubVO.getUFDouble_NullAsZero(
							tmp.getAttributeValue(num)).add(
							PuPubVO.getUFDouble_NullAsZero(datas[j]
									.getAttributeValue(num))));
				}
			}
			newAccouts[i] = tmp;
		}
		return newAccouts;
	}

	/**
	 * ����ִ���
	 * 
	 * @param sql
	 * @throws Exception
	 */
	public void clearNum(String sql) throws Exception {
		getDao().executeUpdate(sql);
	}

	public void setNumAccoutSpecialDeal(Map<String, SuperVO[]> nmap)
			throws Exception {

	}

	public void setNumAccountModSpecialDeal(Map<String, ReportBaseVO[]> map)
			throws Exception {

	}

	/**
	 * ���˱���仯��Ϊ�������
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-10-19����05:33:57
	 * @param vos
	 * @return
	 * @throws Exception
	 */
	public SuperVO[] filterNUll(SuperVO[] vos) throws Exception {
		List<SuperVO> list = new ArrayList<SuperVO>();
		if (vos == null || vos.length == 0) {
			return null;
		}
		AccountData account = getAccountData();
		if (account == null) {
			throw new Exception("û��ע���ִ�����Ϣ");
		}
		String[] fnames = account.getSetNumFields();
		if (fnames == null || fnames.length == 0)
			throw new Exception(" û��ע���ִ����仯�ֶ�");
		for (int i = 0; i < vos.length; i++) {
			boolean isNull = true;
			for (int j = 0; j < fnames.length; j++) {
				UFDouble num = PuPubVO.getUFDouble_NullAsZero(vos[i]
						.getAttributeValue(fnames[j]));
				if (!num.equals(new UFDouble(0))) {
					isNull = false;
				}
			}
			if (isNull == false)
				list.add(vos[i]);
		}
		if (list.size() == 0) {
			return null;
		}
		String className = account.getNumClass();
		if (className == null || className.length() == 0)
			throw new Exception("û��ע���ִ���ʵ����");
		Class cl = Class.forName(className);
		return list.toArray((SuperVO[]) java.lang.reflect.Array.newInstance(cl,
				0));
	}

	/**
	 * ����ѯ���� ��ѯ���е���Ҫ�޸��� �ִ�����vo
	 * 
	 * @return
	 */
	public ReportBaseVO[] queryModVO(String whereSql) throws Exception {
		PubBillData pb = getPubBillData();
		if (pb == null) {
			throw new Exception("û��ע�ᵥ�ݹ�����Ϣ");
		}
		String sql = pb.getQuerySql(whereSql);
		String[] sql11 = pb.getQuerySql1(whereSql);
		ReportBaseVO[] mvos = null;
		List<ReportBaseVO> lists = new ArrayList<ReportBaseVO>();
		if (sql != null && sql.length() != 0) {
			mvos = getDMO().queryVOBySql(sql);
		} else {
			if (sql11 == null || sql11.length == 0)
				throw new Exception("û���ڵ��ݹ�����Ϣ����ע��ҵ�񵥾ݲ�ѯ���");
			List<ReportBaseVO[]> list = getDMO().queryVOBySql(sql11);
			if (list == null || list.size() == 0)
				return null;
			for (int i = 0; i < list.size(); i++) {
				mvos = list.get(i);
				if (mvos != null && mvos.length != 0)
					lists.addAll(Arrays.asList(mvos));
			}
		}
		if (lists != null && lists.size() != 0)
			return lists.toArray(new ReportBaseVO[0]);
		return mvos;
	}

	/**
	 * �ִ����޸������ ��whereSql����
	 * 
	 * @throws Exception
	 */
	public void accountMOD(String whereSql) throws Exception {
		clearNum(getClearSql(whereSql));// ����ִ���
		ReportBaseVO[] rvos = queryModVO(whereSql);// ��ѯ���� �޸�����
		Map<String, ReportBaseVO[]> map = getTypetomodvo(rvos);// �õ���������
																// ӳ�����Ҫ�޸��� ����
		setNumAccountModSpecialDeal(map);
		Map<String, SuperVO[]> nmap = getNumVO(map);// ���ݽ��� ����ִ���vo
		setAccountNumChange(nmap);// �������ݱ仯��
		SuperVO[] vos = getAccountNum(nmap);// �õ��ִ���vo����
		SuperVO[] combinvos = combinAccounts(vos);// ���ִ�����Сά�Ƚ������ݺϲ�
		SuperVO[] zvos = filterNUll(combinvos);// ���˵��仯��Ϊ�������
		if (zvos == null || zvos.length == 0)
			return;
		getDao().insertVOArray(zvos);// �����ִ���
	}

	public abstract String getClearSql(String whereSql) throws Exception;

	/**
	 *�������� ��ѯ���еĵ��� ���ת����ĵ�̨������
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-10-25����10:17:02
	 * @param whereSql
	 * @return
	 * @throws Exception
	 */
	public SuperVO[] getAccountNum(String whereSql) throws Exception {
		ReportBaseVO[] rvos = queryModVO(whereSql);// ��ѯ���� �޸�����
		Map<String, ReportBaseVO[]> map = getTypetomodvo(rvos);// �õ���������
																// ӳ�����Ҫ�޸��� ����
		setNumAccountModSpecialDeal(map);
		Map<String, SuperVO[]> nmap = getNumVO(map);// ���ݽ��� ����ִ���vo
		setAccountNumChange(nmap);// �������ݱ仯��
		SuperVO[] vos = getAccountNum(nmap);// �õ��ִ���vo����
		SuperVO[] combinvos = combinAccounts(vos);// ���ִ�����Сά�Ƚ������ݺϲ�
		SuperVO[] zvos = filterNUll(combinvos);// ���˵��仯��Ϊ�������
		return zvos;
	}
}

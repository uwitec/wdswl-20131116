package nc.bs.zmpub.pub.tool.mod;

import java.util.Collection;
import java.util.Iterator;

import nc.bd.accperiod.AccountCalendar;
import nc.bs.zmpub.pub.tool.SingleVOChangeDataBsTool;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.bd.period2.AccperiodmonthVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.zmpub.pub.tool.CombinVO;

/**
 * ̨���޸������� �Ƕ� AccountModBOTool2����չ ֧�ִ� �½�����ȡ�ѽ���µ��ִ������� +�޸�û�н��Ĵ�ҵ�񵥾ݻ��ܵ����� �޸�Ŀ��:
 * ����ִ��� �޸���Ч�� ��Ϊͨ��ע�����������ע��
 * 
 * @author mlr
 */
public abstract class AccountModBOTool4 extends AccountModBOTool2 {
	// ���󷽷�����չ
	/**
	 * ��ȡ���ע����Ϣ
	 */
	public abstract AccountBalanceData getAccountBalanceData() throws Exception;

	// ҵ���߼�ʵ�巽������չ ��ʼ for add mlr
	/**
	 * @author mlr
	 * @˵�������׸ڿ�ҵ���޸���̨�� �ִ����޸������ ֧�ִӰ��½�����ȡ����̨���޸�
	 * @param whereSql1
	 *            ���ݲ�ѯwhereSql ��Ӧҵ�񵥾� ҵ�񵥾ݱ���֧��һ��wheresql ���ҵ�񵥾ݶ�Ӧ��ͬ�ı� ��֧��
	 *            �˷�����Ҫ�Ľ�
	 * @param whereSql2
	 *            ����ѯwhereSql ��Ӧ����
	 * @param whereSql3
	 *            ���̨�����ݵ� whereSql ��Ӧ�ִ�����
	 * @param whereSql4
	 *            �����ڳ�����whereSql ��Ӧ���� һ��� whereSql2 һ�� ���� ������ڳ����ǲ�ͬ��
	 * @return
	 * @throws Exception
	 */
	public void modifyAccounts(String whereSql1, String whereSql2,
			String whereSql3, String whereSql4, String pk_corp)
			throws Exception {
		AccountBalanceData abd = getAccountBalanceData();
		if (abd == null)
			throw new Exception("û��ע������Ϣ");
		AccountData ad = getAccountData();
		if (ad == null)
			throw new Exception("û��ע���ִ�����Ϣ");

		clearNum(getClearSql(whereSql3));// ����ִ���
		AccperiodmonthVO am = getLastAccountMonth(pk_corp);// ��ȡ���һ��������
		SuperVO[] preparas = null;
		if (am != null) {// ��ȡ�����
			preparas = getAccountsForOneMonth(am.getPk_accperiodmonth(),
					whereSql2);
		}
		if (am == null) {
			// û�н����� �����ڳ����
			preparas = abd.loadPeridData(whereSql4);
			preparas = combinAccounts(preparas);

		}
		PubBillData pb = getPubBillData();
		if (pb == null)
			throw new Exception(" û��ע�ᵥ�ݹ�����Ϣ");
		StringBuffer whereBuffer = new StringBuffer();
		if (pb.getBillDateName() == null || pb.getBillDateName().length() == 0)
			throw new Exception("û��ע��ҵ�񵥾�����");
		if (am != null) {
			whereBuffer.append(" h." + pb.getBillDateName() + "  > '"
					+ am.getEnddate() + "' ");
		}
		if (whereBuffer.length() != 0) {
			if (PuPubVO.getString_TrimZeroLenAsNull(whereSql1) != null) {
				whereBuffer.append(" and " + whereSql1);
			}
		} else {
			if (PuPubVO.getString_TrimZeroLenAsNull(whereSql1) != null) {
				whereBuffer.append(whereSql1);
			}
		}
		SuperVO[] paras = null;
		paras = this.getAccountNum(whereBuffer.toString());
		if (paras != null && paras.length > 0) {// ����û��ҵ������ ���Ϊ��
			for (SuperVO para : paras) {
				if (PuPubVO.getUFDouble_NullAsZero(
						para.getAttributeValue("nnum")).doubleValue() < 0)
					throw new BusinessException("�����쳣,���������ڸ����");
				if (PuPubVO.getUFDouble_NullAsZero(
						para.getAttributeValue("nallnum")).doubleValue() < 0)
					throw new BusinessException("�����쳣,�ڼ������ڸ����");
			}
		}

		String classn = abd.getChangeClass();// ��ȡ��� -->�ִ��������ݽ�����
		if (classn == null || classn.length() == 0)
			throw new Exception(" û��ע���������ִ����Ľ�����");
		String clssnum = ad.getNumClass();// ��ȡ�ִ��� ����ȫ·��
		if (clssnum == null || clssnum.length() == 0)
			throw new Exception(" û��ע���ִ�����ȫ·��");
		Class cl = Class.forName(clssnum);
		SuperVO[] newpreparas1 = null;
		if (preparas != null && preparas.length != 0)
			newpreparas1 = (SuperVO[]) SingleVOChangeDataBsTool.runChangeVOAry(
					preparas, cl, classn);// ���ݽ�������ִ���vo
		SuperVO[] newpreparas = combinAccounts(newpreparas1);// ����Сά�Ƚ������ݺϲ�
		SuperVO[] newparas = combinAccounts(paras);// ����Сά�Ƚ������ݺϲ�
		String[] strs = ad.getSetNumFields();// ��ñ仯�������ֶ�
		int[] types = ad.getSetNumFieldsType();// ��ñ仯����������
		if (strs == null || strs.length == 0)
			throw new Exception("û��ע��仯���ֶ�");
		if (types == null || types.length == 0)
			throw new Exception("û��ע��仯���ֶε�����");
		if (ad.getUnpk() == null || ad.getUnpk().length == 0)
			throw new Exception("û�����ִ�����Ϣ��ע���ִ�����Сά��");
		// �ϲ���� ���ڳ���� ׷�ӵ����½��
		SuperVO[] newalls = (SuperVO[]) CombinVO.combinVoByFields(newparas,
				newpreparas, ad.getUnpk(), types, strs);
		// �����޸���̨������
		if (newalls == null || newalls.length == 0)
			return;
		getDao().insertVOArray(newalls);
	}

	/**
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ������ϵͳ���Ľ����� ���ϵͳ�����ڽ����� ����null 2011-10-25����03:27:29
	 * @return
	 * @throws Exception
	 */
	public AccperiodmonthVO getLastAccountMonth(String pk_corp)
			throws Exception {
		AccountBalanceData abd = getAccountBalanceData();
		if (abd == null)
			throw new Exception("û��ע������Ϣ");
		String sql = abd.getLastAccountMonthQuerySql(pk_corp);
		if (sql == null || sql.length() == 0)
			throw new Exception(" û��ע���ѯ�������� ��Ӧ�Ļ���µĲ�ѯ���");
		String cmonthid = PuPubVO.getString_TrimZeroLenAsNull(getDao()
				.executeQuery(sql, new ColumnProcessor()));
		if (cmonthid == null)
			return null;
		return AccountCalendar.getInstanceByAccperiodMonth(cmonthid)
				.getMonthVO();
	}

	/**
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�����½���ȡָ���µ��½����� 2011-10-22����04:00:12
	 * @param cmonthid
	 *  appWhereSql������Χ
	 * @return
	 * @throws Exception
	 */
	public SuperVO[] getAccountsForOneMonth(String cmonthid, String appWhereSql)
			throws Exception {
		AccountBalanceData abd = getAccountBalanceData();
		if (abd == null)
			throw new Exception("û��ע������Ϣ");
		String moclass = abd.getMonthAccountClass();
		String monid = abd.getAcMonID();
		if (moclass == null || moclass.length() == 0)
			throw new Exception("û��ע�������");
		Class moc = Class.forName(moclass);
		if (monid == null || monid.length() == 0)
			throw new Exception("û��ע������Ż��������������");
		if (PuPubVO.getString_TrimZeroLenAsNull(cmonthid) == null)
			throw new BusinessException("�������[�·�]Ϊ��");
		String whereSql = null;
		if (PuPubVO.getString_TrimZeroLenAsNull(appWhereSql) != null)
			whereSql = "isnull(dr,0) = 0 and " + monid + "= '" + cmonthid + "'"
					+ " and " + appWhereSql;
		else
			whereSql = "isnull(dr,0) = 0 and " + monid + " = '" + cmonthid
					+ "'";
		Collection<SuperVO> c = (Collection<SuperVO>) getDao()
				.retrieveByClause(moc, whereSql);
		if (c == null || c.size() == 0)
			return null;
		SuperVO[] months = new SuperVO[c.size()];
		Iterator<SuperVO> it = c.iterator();
		int index = 0;
		while (it.hasNext()) {
			months[index] = it.next();
			index++;
		}
		return months;
	}
	// ҵ���߼�ʵ�巽������չ ���� for add mlr
}

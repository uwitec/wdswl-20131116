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
 * ̨���޸������� �Ƕ� AccountModBOTool����չ ֧�ִ� �½�����ȡ�ѽ���µ��ִ������� �޸�û�н���ҵ������ �޸�Ŀ��: ����ִ���
 * �޸���Ч��
 * 
 * @author mlr
 */
public abstract class AccountModBOTool3 extends AccountModBOTool {
	// ������չ��ʼ ���󷽷�����չ for add mlr
	/**
	 * ��ȡ�½� ������ȫ·��
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-10-31����10:53:54
	 * @return
	 * @throws Exception
	 */
	public abstract String getMonthAccountClass() throws Exception;

	/**
	 * ע���½� ���� �����id���ֶ�����
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-10-31����11:03:55
	 * @return
	 * @throws Exception
	 */
	public abstract String getAcMonID() throws Exception;

	/**
	 * �׸ڿ�ҵ�����ز�ѯ���Ľ����� ����������� id
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-10-31����11:11:15
	 * @return
	 * @throws Exception
	 */
	public abstract String getLastAccountMonthQuerySql(String corp)
			throws Exception;

	/**
	 * ���Ӱ���ִ����� ��Ҫ�������õ��ֶε��������� ע��Ľӿ�Ϊ IUFTypes
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-10-31����12:26:04
	 * @return
	 * @throws Exception
	 */
	public abstract int[] getSetNumFieldsType() throws Exception;

	/**
	 * ��ȡ��������ע���ֶε�����
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-10-31����11:17:44
	 * @return
	 * @throws Exception
	 */
	public abstract String getBillDateName() throws Exception;

	/**
	 * ��ȡ�½��--->�ִ����ĵ�������
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-10-31����02:05:26
	 * @return
	 * @throws Exception
	 */
	public abstract String getChangeClass() throws Exception;

	/**
	 * �����ڳ����� ��ʲô����¼����ڳ������أ�
	 * 
	 * �·ݽ���ʱ ���һ�·� �Ѿ����� һ�·ݵĽ��˱��� �϶��Ѿ��������ڳ�����
	 * 
	 * ���һ�·��Ѿ����˵�����½���̨���޸� �Ͳ�������ڳ������� ��Ϊ ����ʱ �� �Ǵӽ�����ȡ�ѽ��˵����� ת�����ִ��� ����
	 * ���һ������������Ĵ�ҵ�� �����ϻ�����������
	 * 
	 * ���һ�·�δ���� ��ô�ڳ����� ���޸���ʱ�� �ͼ��ز����ִ��������Ա����ֶ�����
	 * 
	 * 
	 * @author mlr
	 * @param whereSql4
	 * @˵�������׸ڿ�ҵ�� 2011-11-1����12:47:10
	 * @return
	 * @throws Exception
	 */
	public abstract SuperVO[] loadPeridData(String whereSql4) throws Exception;

	// ������չ��ʼ ���󷽷�����չ for add mlr

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
	 *            �����ڳ�����whereSql ��Ӧ���� һ��� whereSql2 һ�� ���� ������ڳ���һ��
	 * @return
	 * @throws Exception
	 */
	public void modifyAccounts(String whereSql1, String whereSql2,
			String whereSql3, String whereSql4, String pk_corp)
			throws Exception {
		clearNum(getClearSql(whereSql3));// ����ִ���
		AccperiodmonthVO am = getLastAccountMonth(pk_corp);// ��ȡ���һ��������
		SuperVO[] preparas = null;
		if (am != null) {// ��ȡ�����
			preparas = getAccountsForOneMonth(am.getPk_accperiodmonth(),
					whereSql2);
		}
		if (am == null) {
			// û�н����� �����ڳ����
			preparas = loadPeridData(whereSql4);
			preparas = combinAccounts(preparas);

		}
		StringBuffer whereBuffer = new StringBuffer();
		if (getBillDateName() == null || getBillDateName().length() == 0)
			throw new Exception("û��ע�ᵥ������");
		if (am != null) {
			whereBuffer.append(" h." + getBillDateName() + "  > '"
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
		String classn = getChangeClass();// ��ȡ��� -->�ִ��������ݽ�����
		if (classn == null || classn.length() == 0)
			throw new Exception(" û��ע���������ִ����Ľ�����");
		String clssnum = getNumClass();// ��ȡ�ִ��� ����ȫ·��
		if (clssnum == null || clssnum.length() == 0)
			throw new Exception(" û��ע���ִ�����ȫ·��");
		Class cl = Class.forName(clssnum);
		SuperVO[] newpreparas1 = null;
		if (preparas != null && preparas.length != 0)
			newpreparas1 = (SuperVO[]) SingleVOChangeDataBsTool.runChangeVOAry(
					preparas, cl, classn);// ���ݽ�������ִ���vo
		SuperVO[] newpreparas = combinAccounts(newpreparas1);// ����Сά�Ƚ������ݺϲ�
		SuperVO[] newparas = combinAccounts(paras);// ����Сά�Ƚ������ݺϲ�
		String[] strs = getSetNumFields();// ��ñ仯�������ֶ�
		int[] types = getSetNumFieldsType();// ��ñ仯����������
		if (strs == null || strs.length == 0)
			throw new Exception("û��ע��仯���ֶ�");
		if (types == null || types.length == 0)
			throw new Exception("û��ע��仯���ֶε�����");
		// �ϲ���� ���ڳ���� ׷�ӵ����½��
		SuperVO[] newalls = (SuperVO[]) CombinVO.combinVoByFields(newparas,
				newpreparas, getUnpk(), types, strs);
		// �����޸���̨������
		if (newalls == null || newalls.length == 0)
			return;
		if (newalls != null && newalls.length > 0) {// ����û��ҵ������ ���Ϊ��
			for (SuperVO para : newalls) {
				if (PuPubVO.getUFDouble_NullAsZero(
						para.getAttributeValue("nnum")).doubleValue() < 0)
					throw new BusinessException("�����쳣,���������ڸ����");
				if (PuPubVO.getUFDouble_NullAsZero(
						para.getAttributeValue("nallnum")).doubleValue() < 0)
					throw new BusinessException("�����쳣,�ڼ������ڸ����");
			}
		}
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
		String sql = getLastAccountMonthQuerySql(pk_corp);
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
	 *            appWhereSql������Χ
	 * @return
	 * @throws Exception
	 */
	public SuperVO[] getAccountsForOneMonth(String cmonthid, String appWhereSql)
			throws Exception {

		String moclass = getMonthAccountClass();
		String monid = getAcMonID();

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
	// ҵ���߼�ʵ�巽������չ ��ʼ for add mlr
}

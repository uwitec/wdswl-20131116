package nc.bs.zmpub.pub.tool.mod;

/**
 * ҵ�񵥾ݹ�����Ϣע����
 * 
 * @author mlr
 */
public abstract class PubBillData {
	/**
	 * ����ĳ��ά�Ȳ�ҵ�񵥾�
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String getQuerySql(String whereSql) throws Exception;

	/**
	 * ����ĳ��ά�Ȳ�ѯҵ�񵥾� ��ҵ�񵥾� ��wheresql �����ֶ�һ�� �����һ�µĻ� �򹤾����޷�֧�ְ�ĳ��ά�Ƚ��������޸�
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String[] getQuerySql1(String whereSql) throws Exception;

	/**
	 * ��ò�ѯ���
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String getQuerySql() throws Exception;

	/**
	 * ��ò�ѯ���
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String[] getQuerySql1() throws Exception;

	/**
	 * ��õ��������ֶ����� ��ҵ�񵥾ݱ��뱣��ͳһ
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String getBillTypeName() throws Exception;

	/**
	 * ��õ���״̬�ֶ����� ��ҵ�񵥾ݱ��뱣��ͳһ
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String getBillTypeStatusName() throws Exception;

	/**
	 * ��ȡ��������ע���ֶε����� ��ҵ�񵥾ݱ��뱣��ͳһ
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-10-31����11:17:44
	 * @return
	 * @throws Exception
	 */
	public abstract String getBillDateName() throws Exception;

}

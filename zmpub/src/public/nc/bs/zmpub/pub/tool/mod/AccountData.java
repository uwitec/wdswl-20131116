package nc.bs.zmpub.pub.tool.mod;

/**
 * �ִ�����Ϣע����
 * 
 * @author mlr
 */
public abstract class AccountData {
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
	 * ����ĳ��ά������ִ���
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String getClearSql(String whereSql) throws Exception;

	/**
	 * ���Ӱ���ִ����� ��Ҫ�������õ��ֶ���������
	 * 
	 * @return Map<String,boolean[]>
	 * @throws Exception
	 */
	public abstract String[] getSetNumFields() throws Exception;

	/**
	 * ����ִ�������ȫ·��
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String getNumClass() throws Exception;

	/**
	 * ����ִ�����Сά������
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String[] getUnpk() throws Exception;
}

package nc.bs.zmpub.pub.tool.mod;

import nc.vo.pub.SuperVO;

/**
 * �����Ϣע����
 * 
 * @author mlr
 */
public abstract class AccountBalanceData {
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
}

package nc.vo.zmpub.pub.report;

/**
 * ��͸��ص���չ��Ϣ��������Ҫ��ʵ�ָýӿ�
 * 
 * @author xkf
 * 
 */

public interface IPeneExtendInfo {

	/**
	 * �����Ҫ�������õ�������itemkey����Թ����з���commoncolumn������Ҫ�������Ҫ����ǰ̨�����жϴ򿪲�ͬ�ڵ� �����������
	 * ��ǰ̨��������Щ�����޷��ں�̨��ʼ����
	 * 
	 * @return
	 */
	public String[][] getColumKeys();

	/**
	 * ����з��� ���ض�Ӧ����չ������������Ҫ��getColumKeys()��ͬ����˳���Ӧ
	 * 
	 * @return
	 */
	public String[] getExtendsWhereStrings();

	/**
	 * ��������з��� ��Ӧ�Ľڵ�ţ�������Ҫ��getColumKeys()��ͬ����˳���Ӧ
	 * 
	 * @return
	 */
	public String[] getNodeCodes();
}

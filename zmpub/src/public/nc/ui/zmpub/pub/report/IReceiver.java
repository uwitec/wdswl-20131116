package nc.ui.zmpub.pub.report;

/**
 * �˴���������˵���� �������ڣ�(2004-8-19 9:14:23)
 * 
 * @author��������
 */
public interface IReceiver {
	/**
	 *�յ�sender����Ϣ��Ĵ���
	 * 
	 * @parameter:msgProxy,��Ϣ����
	 *@parameter:sender,��Ϣ��Դ��
	 *@parameter:msg,��Ϣ���ݡ�
	 *@return:���ظ�sender����Ϣ��
	 */
	public Object receiverMsg(IMsgProxy msgProxy, ISender sender, Object msg)
			throws Exception;
}

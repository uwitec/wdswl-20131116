package nc.ui.zmpub.pub.report;

/**
 * ��Ϣ����ӿڡ� �������ڣ�(2004-8-19 9:23:13)
 * 
 * @author��������
 */
public interface IMsgProxy {

	public Object sendMsg(ISender sender, String receiverClassName, Object msg)
			throws Exception;

	public Object sendMsg(ISender sender, IReceiver receiver, Object msg)
			throws Exception;
}

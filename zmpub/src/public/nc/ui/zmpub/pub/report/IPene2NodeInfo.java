/**
 * 
 */
package nc.ui.zmpub.pub.report;

import nc.vo.zmpub.pub.report.IPeneExtendInfo;

/**
 * <p>
 * ����͸ҵ�񵥾���Ϣ�ӿ�
 * 
 * @author xkf
 * @date 2007-6-21 ����06:58:13
 * @version V5.0
 * @��Ҫ����ʹ�ã� <ul>
 *          <li><b>���ʹ�ø��ࣺ</b></li> <li><b>�Ƿ��̰߳�ȫ��</b></li> <li><b>������Ҫ��</b>
 *          </li> <li><b>ʹ��Լ����</b></li> <li><b>������</b></li>
 *          </ul>
 *          </p>
 *          <p>
 * @��֪��BUG�� <ul>
 *          <li></li>
 *          </ul>
 *          </p>
 *          <p>
 * @�޸���ʷ��
 */
public interface IPene2NodeInfo {

	/**
	 * @return ��ǰ����ڵ��
	 */
	public String getPene2NodeInfo();

	/**
	 * ��ö��⴩͸��Ϣ
	 * 
	 * @return
	 */
	public IPeneExtendInfo getExtendInfo();
}

/**
 * 
 */
package nc.vo.zmpub.pub.report2;

import nc.ui.pub.linkoperate.ILinkQueryData;

/**
 * <p>
 * @author xkf
 * @date 2007-6-21 ����08:40:49
 * @version V5.0
 * @��Ҫ����ʹ�ã�
 *  <ul>
 * 		<li><b>���ʹ�ø��ࣺ</b></li>
 *      <li><b>�Ƿ��̰߳�ȫ��</b></li>
 * 		<li><b>������Ҫ��</b></li>
 * 		<li><b>ʹ��Լ����</b></li>
 * 		<li><b>������</b></li>
 * </ul>
 * </p>
 * <p>
 * @��֪��BUG��
 * 	<ul>
 * 		<li></li>
 *  </ul>
 * </p>
 * <p>
 * @�޸���ʷ��
 */
public class PMLinkQueryData implements ILinkQueryData {

	/**
	 * 
	 */
	public PMLinkQueryData() {
		// TODO �Զ����ɹ��캯�����
	}

	private String billID = null;
	private String billType = null;
	private String pkOrg = null;
	private Object userObject = null;
	public String getBillID() {
		// TODO �Զ����ɷ������
		return billID;
	}

	public String getBillType() {
		// TODO �Զ����ɷ������
		return billType;
	}

	public String getPkOrg() {
		// TODO �Զ����ɷ������
		return pkOrg;
	}

	public Object getUserObject() {
		// TODO �Զ����ɷ������
		return userObject;
	}

	public void setBillID(String billID) {
		this.billID = billID;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public void setPkOrg(String pkOrg) {
		this.pkOrg = pkOrg;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

}

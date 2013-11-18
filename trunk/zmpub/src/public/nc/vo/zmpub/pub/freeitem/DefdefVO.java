package nc.vo.zmpub.pub.freeitem;

import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;

/**
 * Defdef�ľۺ�VO�ࡣ
 * 
 * �������ڣ�(2001-11-8)
 * 
 * @author��������
 */
public class DefdefVO extends AggregatedValueObject {

	private DefdefHeaderVO header = null;
	private DefdefItemVO[] items = null;

	// ʱ�����ʾ��������δʹ�ã�
	long currentTimestamp; // ��ǰʱ���
	long initialTimestamp; // �����ݿ����ʱ��õ�ʱ���

	/**
	 * DefdefVO ������ע�⡣
	 */
	public DefdefVO() {
		super();
	}

	/**
	 * <p>
	 * ����ӱ��VO���顣
	 * <p>
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.CircularlyAccessibleValueObject[]
	 */
	public CircularlyAccessibleValueObject[] getChildrenVO() {

		return items;
	}

	/**
	 * <p>
	 * ���ĸ���VO��
	 * <p>
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.CircularlyAccessibleValueObject
	 */
	public CircularlyAccessibleValueObject getParentVO() {

		return header;
	}

	/**
	 * <p>
	 * �����ӱ��VO���顣
	 * <p>
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param children
	 *            nc.vo.pub.CircularlyAccessibleValueObject[]
	 */
	public void setChildrenVO(CircularlyAccessibleValueObject[] children) {

		items = (DefdefItemVO[]) children;
	}

	/**
	 * <p>
	 * ����ĸ���VO��
	 * <p>
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param parent
	 *            nc.vo.pub.CircularlyAccessibleValueObject
	 */
	public void setParentVO(CircularlyAccessibleValueObject parent) {

		header = (DefdefHeaderVO) parent;
	}
}
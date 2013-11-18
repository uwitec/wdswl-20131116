package nc.vo.zmpub.pub.report;

import java.util.HashMap;

import nc.vo.pub.SuperVO;

/**
 * ����������ʹ�õ�SuperVO ���ߣ�Ѧ��ƽ �������ڣ�2006-4-20 13:27:25
 */
public class ReportTreeGatherVO extends SuperVO {

	private HashMap map = new HashMap();

	/**
	 * TempTreeGatherVO ������ע��
	 * 
	 */
	public ReportTreeGatherVO() {
		super();
	}

	/**
	 * @see java.lang.Object#clone() ���ߣ�Ѧ��ƽ �������ڣ�2006-4-20 17:07:38
	 * @return
	 */
	public Object clone() {

		try {
			SuperVO vo = (SuperVO) getClass().newInstance();
			String[] fieldNames = getAttributeNames();
			if (fieldNames != null) {
				for (int i = 0; i < fieldNames.length; i++) {
					vo.setAttributeValue(fieldNames[i],
							getAttributeValue(fieldNames[i]));
				}
			}
			return vo;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @see nc.vo.pub.CircularlyAccessibleValueObject#getAttributeNames() ���ߣ�Ѧ��ƽ
	 *      �������ڣ�2006-4-20 13:11:06
	 * @return
	 */
	public String[] getAttributeNames() {
		return (String[]) map.keySet().toArray(new String[0]);
	}

	/**
	 * @see nc.vo.pub.CircularlyAccessibleValueObject#getAttributeValue(java.lang.String)
	 *      ���ߣ�Ѧ��ƽ �������ڣ�2006-4-20 13:11:06
	 * @param attributeName
	 * @return
	 */
	public Object getAttributeValue(String attributeName) {
		return map.get(attributeName);
	}

	/**
	 * @see nc.vo.pub.SuperVO#getParentPKFieldName() ���ߣ�Ѧ��ƽ �������ڣ�2006-4-20
	 *      13:09:53
	 * @return
	 */
	public String getParentPKFieldName() {
		return null;
	}

	/**
	 * @see nc.vo.pub.SuperVO#getPKFieldName() ���ߣ�Ѧ��ƽ �������ڣ�2006-4-20 13:09:53
	 * @return
	 */
	public String getPKFieldName() {
		return null;
	}

	/**
	 * @see nc.vo.pub.SuperVO#getTableName() ���ߣ�Ѧ��ƽ �������ڣ�2006-4-20 13:09:53
	 * @return
	 */
	public String getTableName() {
		return null;
	}

	/**
	 * @see nc.vo.pub.CircularlyAccessibleValueObject#setAttributeValue(java.lang.String,
	 *      java.lang.Object) ���ߣ�Ѧ��ƽ �������ڣ�2006-4-20 13:12:25
	 * @param attributeName
	 * @param value
	 */
	public void setAttributeValue(String attributeName, Object value) {
		map.put(attributeName, value);
	}
}

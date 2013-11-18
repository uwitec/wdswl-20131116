package nc.vo.zmpub.pub.report;

import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;

import nc.vo.pub.CircularlyAccessibleValueObject;

/**
 * ������
 * 
 * @author��������
 */
public class Toolkit {

	/**
	 * Toolkit ������ע�⡣
	 */
	private Toolkit() {
		super();
	}

	/**
	 * ��鴫��Ĳ����Ƿ�Ϊ�ա�
	 * 
	 * @return boolean ��������ֵΪnull������true��
	 *         ���value������ΪString������value.length()Ϊ0������true��
	 *         ���value������ΪObject[]������value.lengthΪ0������true��
	 *         ���value������ΪCollection������value.size()Ϊ0������true��
	 *         ���value������ΪDictionary������value.size()Ϊ0������true�� ���򷵻�false��
	 * @param value
	 *            �����ֵ��
	 */
	public static boolean isEmpty(Object value) {
		if (value == null)
			return true;
		if ((value instanceof String)
				&& (((String) value).trim().length() <= 0))
			return true;
		if ((value instanceof Object[]) && (((Object[]) value).length <= 0))
			return true;
		if ((value instanceof Collection) && ((Collection) value).size() <= 0)
			return true;
		if ((value instanceof Dictionary) && ((Dictionary) value).size() <= 0)
			return true;
		return false;
	}

	public static String[] addTwoStringArray(String[] s1, String[] s2) {
		if (s2 == null || s2.length == 0)
			return s1;
		if (s1 == null)
			s1 = new String[0];
		String[] s = new String[s1.length + s2.length];
		System.arraycopy(s1, 0, s, 0, s1.length);
		System.arraycopy(s2, 0, s, s1.length, s2.length);
		return s;
	}

	public static CircularlyAccessibleValueObject[] addTwoVos(
			CircularlyAccessibleValueObject[] vos1,
			CircularlyAccessibleValueObject[] vos2) {
		if (vos2 == null || vos2.length == 0)
			return vos1;
		if (vos1 == null)
			vos1 = new CircularlyAccessibleValueObject[0];
		CircularlyAccessibleValueObject[] vos = new CircularlyAccessibleValueObject[vos1.length
				+ vos2.length];
		System.arraycopy(vos1, 0, vos, 0, vos1.length);
		System.arraycopy(vos2, 0, vos, vos1.length, vos2.length);
		return vos;
	}

	/*
	 * ���ܣ������ݶ���Ĳ��ҡ�liujianbo 2004/4/8+
	 */
	public static int binarySearch(Object[] ary, Object o) {
		if (isEmpty(ary))
			return -1;
		Arrays.sort(ary);
		return Arrays.binarySearch(ary, o);
	}

	/**
	 *���ϼ�vo����ϸvo ���ںϳ�һ��VO��
	 * 
	 * @parameter:vos:total vos and detailvos have been added.; @
	 *                      joinFlds,detailvos and totalvos join fields. @
	 *                      detailFlds,detailvos fields names array. @
	 *                      detailKey,the fields that can sign a detail vo.
	 * @return :combined vos
	 */
	public static CircularlyAccessibleValueObject[] combinReportVOsVsDetailVOs(
			CircularlyAccessibleValueObject[] vos, String[] joinFlds,
			String[] detailFlds, String detailKey) throws Exception {
		if (vos == null || vos.length == 0)
			return null;
		if (detailFlds == null || detailFlds.length == 0)
			throw new Exception("������ϸ�ֶ����鲻��Ϊ�ա�");
		if (joinFlds == null || joinFlds.length == 0)
			throw new Exception("����Join�ֶ����鲻��Ϊ�ա�");
		if (detailKey == null)
			throw new Exception("������ϸ�ؼ����в���Ϊ�ա�");
		// ��join�У�����ϸ�Ĺؼ����н��н�������������֤�ϼ������ϱߡ�
		SimpleSortVOTool sortTool = new SimpleSortVOTool();
		sortTool.sortVO(vos, addTwoStringArray(joinFlds,
				new String[] { detailKey }), true);
		// ���ö�ջ��
		java.util.Stack st = new java.util.Stack();
		for (int i = 0; i < vos.length; i++) { // �����ջΪ����ֱ�ӷ����ջ��
			if (st.isEmpty()) {
				st.push(vos[i]);
				continue;
			} // ȡջ���Ķ��󡣲��õ�����ϸ��
			CircularlyAccessibleValueObject vo = (CircularlyAccessibleValueObject) st
					.pop();
			Object oTop = vo.getAttributeValue(detailKey);
			Object oCur = vos[i].getAttributeValue(detailKey);
			// ��ǰ��ϸVOΪ��������ջ��
			if (oCur == null) {
				st.push(vo);
				st.push(vos[i]);
				continue;
			} // ���󶼲�Ϊ����Ϊ��ϸ��������ϸ����Ϊ�գ������ջ��
			if (oTop != null && oCur != null) {
				st.push(vo);
				String[] attrnames = vos[i].getAttributeNames();
				for (int j = 0; j < attrnames.length; j++) {
					if (Toolkit.binarySearch(detailFlds, attrnames[j]) < 0)
						vos[i].setAttributeValue(attrnames[j], null);
				}
				st.push(vos[i]);
				continue;
			} // ջ��������ϸΪ�գ���ǰ������ϸ��Ϊ�ս��кϲ���
			if (oTop == null && oCur != null) {
				for (int j = 0; j < detailFlds.length; j++) {
					vo.setAttributeValue(detailFlds[j], vos[i]
							.getAttributeValue(detailFlds[j]));
				}
				st.push(vo);
				continue;
			}
		} // ջת�����顣
		CircularlyAccessibleValueObject[] vosResult = new CircularlyAccessibleValueObject[st
				.size()];
		st.copyInto(vosResult);
		return vosResult;
	}

	/**
	 *���ϼ�vo����ϸvo ���ںϳ�һ��VO��
	 * 
	 * @parameter:vosTotal,totalvos;vosDetial,detail vos; @ joinFlds,detailvos
	 *                                               and totalvos join fields. @
	 *                                               detailFlds,detailvos fields
	 *                                               names array. @
	 *                                               detailKey,the fields that
	 *                                               can sign a detail vo.
	 * @return :combined vos
	 */
	public static CircularlyAccessibleValueObject[] combinReportVOsVsDetailVOs(
			CircularlyAccessibleValueObject[] vosTotal,
			CircularlyAccessibleValueObject[] vosDetail, String[] joinFlds,
			String[] detailFlds, String detailKey) throws Exception {
		// ����vo�������һ�����顣
		CircularlyAccessibleValueObject[] vos = addTwoVos(vosTotal, vosDetail);
		return combinReportVOsVsDetailVOs(vos, joinFlds, detailFlds, detailKey);
	}

	/**
	 * ����ָ���������������ɲ���SQL��䡣
	 * 
	 * @author: ������ 2004/3/24+
	 * @param arsKey
	 *            ���ݡ�
	 * @return String
	 * 
	 */
	public static String getWherePartByKeys(String fld, String[] arsKey) {
		final int MAX = 500;
		if (isEmpty(arsKey))
			return " 1 = 1 ";
		if (arsKey.length == 1)
			return fld + "='" + arsKey[0] + "'";
		if (arsKey.length <= MAX) {
			String sTmp = fld + " in (";
			for (int i = 0; i < arsKey.length; i++) {
				if (i == arsKey.length - 1) {
					sTmp += "'" + arsKey[i] + "')";
					break;
				}
				sTmp += "'" + arsKey[i] + "',";
			}
			return sTmp;
		}
		int ipos = 0;
		int itimes = arsKey.length / MAX;
		int mode = arsKey.length % MAX;
		String where = null;
		for (int i = 0; i < itimes; i++) {
			if (where == null)
				where = " ( " + fld + " in ( ";
			else
				where += " or " + fld + " in (";
			for (int j = 0; j < MAX; j++) {
				if (j == MAX - 1) {
					where += "'" + arsKey[ipos + j] + "')";
					break;
				}
				where += "'" + arsKey[ipos + j] + "',";
			}
			ipos += MAX;
		}
		if (mode == 0)
			where += " )";
		else {
			where += " or " + fld + " in (";
			for (int k = 0; k < mode; k++) {
				if (k == mode - 1) {
					where += "'" + arsKey[ipos + k] + "'))";
					break;
				}
				where += "'" + arsKey[ipos + k] + "',";
			}
		}
		return where;
	}

	/*
	 * ȥ��List��Ϊ�յĶ���������2004/4/09+
	 */
	public static void packList(java.util.List list) {
		if (list.isEmpty())
			return;
		for (int i = 0; i < list.size(); i++) {
			if (isEmpty(list.get(i))) {
				list.remove(i);
				i--;
			}
		}
	}
}
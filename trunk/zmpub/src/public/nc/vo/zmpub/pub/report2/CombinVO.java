package nc.vo.zmpub.pub.report2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

import nc.ui.scm.util.ObjectUtils;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.zmpub.pub.report.IUFTypes;
import nc.vo.zmpub.pub.report.ReportBaseVO;

public class CombinVO {
	/**
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ �ж����������Ƿ����
	 * @ʱ�䣺2011-7-12����10:34:05
	 * @param o1
	 *            Ҫ�ȽϵĶ���
	 * @param o2
	 *            Ҫ�ȽϵĶ���
	 */
	private static boolean isEqual(Object o1, Object o2) {
		if (isEmpty(o1) && !isEmpty(o2)) {
			return false;
		}
		if (!isEmpty(o1) && isEmpty(o2)) {
			return false;
		}
		if (!isEmpty(o1) && !isEmpty(o2)) {
			if (!o1.equals(o2)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ����ĳ��ά��(����) �����������������ֶζ�Ӧֵ��ͬ�ĺϲ� ���� ��ֵ�ֶ�������������� �ж���Ҫ��͵��ֶ� �����������
	 * 
	 *             ʹ�ñ�������ǰ�������� ����vo���鰴ά������ֻ�ܲ鵽һ������������vo
	 * @ʱ�䣺2011-7-11����09:12:25
	 * @param vos
	 *            Ҫ�ϲ��ı���vos
	 * @param vos1
	 *            Ҫ�ϲ��ı���vos1
	 * @param voCombinConds
	 *            �����ֶ�����
	 * @param types
	 *            ��ֵ����
	 * @param combinFields
	 *            ��ֵ�ֶ�
	 * @return
	 */
	public static ReportBaseVO[] combinVoByFields(ReportBaseVO[] vos,
			ReportBaseVO[] vos1, String[] voCombinConds, int[] types,
			String[] combinFields) {
		// ��¼ vos1���Ѿ����ϲ�����vo
		List<ReportBaseVO> list = new ArrayList<ReportBaseVO>();
		if (isEmpty(vos)) {
			if (!isEmpty(vos1)) {
				return vos1;
			}
		}
		if (isEmpty(vos1)) {
			if (!isEmpty(vos)) {
				return vos;
			}
		}
		if (isEmpty(vos) && isEmpty(vos1)) {
			return null;
		}
		int size = vos.length;
		int size1 = vos1.length;
		// �� vos�е�ÿ��vo ����������vos1
		// ������������ vos1�е�vo ��Ӧ��ֵ �ӵ�vos��vo��
		for (int i = 0; i < size; i++) {
			ReportBaseVO avo = vos[i];
			for (int j = 0; j < size1; j++) {
				ReportBaseVO bvo = vos1[j];
				boolean isEqual = true;
				for (int k = 0; k < voCombinConds.length; k++) {
					Object o1 = avo.getAttributeValue(voCombinConds[k]);
					Object o2 = bvo.getAttributeValue(voCombinConds[k]);
					if (!isEqual(o1, o2)) {
						isEqual = false;
						break;
					}
				}
				if (isEqual) {
					list.add(bvo);
					int csize = combinFields.length;
					for (int n = 0; n < csize; n++) {
						Object resultobj = avo
								.getAttributeValue(combinFields[n]);
						Object tmpobj = bvo.getAttributeValue(combinFields[n]);
						switch (types[n]) {
						case IUFTypes.INT:
							int iresult = (resultobj == null ? 0
									: ((Integer) resultobj).intValue());
							int itmp = (tmpobj == null ? 0 : ((Integer) tmpobj)
									.intValue());
							avo.setAttributeValue(combinFields[n], new Integer(
									iresult + itmp));
							continue;
						case IUFTypes.LONG:
							long lgtmp = (tmpobj == null ? 0 : ((Long) tmpobj)
									.longValue());
							long lgresult = (resultobj == null ? 0
									: ((Long) resultobj).longValue());
							if (tmpobj != null)
								avo.setAttributeValue(combinFields[n],
										new Long(lgresult + lgtmp));
							continue;
						case IUFTypes.UFD:
							UFDouble ufdtmp = (tmpobj == null ? new UFDouble(
									"0") : (UFDouble) tmpobj);
							UFDouble ufdResult = (resultobj == null ? new UFDouble(
									"0")
									: (UFDouble) resultobj);
							avo.setAttributeValue(combinFields[n], ufdResult
									.add(ufdtmp));
							continue;
						case IUFTypes.STR:
							String strtmp = (tmpobj == null ? "" : tmpobj
									.toString());
							String strresult = (resultobj == null ? ""
									: resultobj.toString());
							avo.setAttributeValue(combinFields[n], strtmp
									+ strresult);
							continue;
						}
					}
					break;
				}
			}
		}
		// ��¼vos1��û�б�vosƥ���ϵ�vo ���ж��κϲ�
		// ���� vos1 �� list
		// ����ĳ��ά������ ��vos1�з��������� �� list�в������������ҳ���
		// ������أ�
		// ����ѭ�� ÿ����vos1�е�һ��vo ȥlist�а��������ҷ��������� vo
		// ����з���������vo
		// �ͶϿ� list ѭ�� ������һ��ѭ��
		// ��ν������������� vo��vos1���ҳ���
		// ��ѭ���� list�����һ��Ԫ�� ��û�з���������Ԫ��ʱ, ��vos�еĶ�Ӧvoȡ��������
		List<ReportBaseVO> list1 = new ArrayList<ReportBaseVO>();// ��¼vos1��û�б�ƥ���vo
		int csize = vos1.length;
		for (int i = 0; i < csize; i++) {
			ReportBaseVO avo = vos1[i];
			int csize1 = list.size();
			for (int j = 0; j < csize1; j++) {
				boolean isEqual = true;
				ReportBaseVO bvo = list.get(j);
				for (int k = 0; k < voCombinConds.length; k++) {
					Object o1 = avo.getAttributeValue(voCombinConds[k]);
					Object o2 = bvo.getAttributeValue(voCombinConds[k]);
					if (!isEqual(o1, o2)) {
						isEqual = false;
						break;
					}
				}
				if (isEqual) {
					break;
				}
				if (j == csize1 - 1) {
					list1.add(vos1[i]);
				}
			}
		}
		// ���list ����Ϊ0 ˵��vos ��vos1 û��һ��ƥ���ϵ�
		if (list.size() == 0) {
			for (int i = 0; i < vos1.length; i++) {
				list1.add(vos1[i]);
			}
		}
		// ��û��ƥ���ϵ�vo�ϲ���
		if (list1.size() > 0) {
			for (int i = 0; i < vos.length; i++) {
				list1.add(vos[i]);
			}
			return list1.toArray(new ReportBaseVO[0]);
		} else {
			return vos;
		}
	}

	/**
	 * �� vos1 �е����� ׷�ӵ� vos ��
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-25����09:55:11
	 * @param vos
	 * @param vos1
	 */
	public static void add(ReportBaseVO vos, ReportBaseVO vos1, String xid) {
		if (vos == null) {
			return;
		}
		if (vos1 == null) {
			return;
		}
		String[] fields = vos1.getAttributeNames();
		if (fields == null || fields.length == 0)
			return;
		for (int i = 0; i < fields.length; i++) {
			if (xid == null || xid.length() == 0) {
				vos.setAttributeValue(fields[i], vos1
						.getAttributeValue(fields[i]));
			} else {
				vos.setAttributeValue(fields[i] + xid, vos1
						.getAttributeValue(fields[i]));
			}
		}

	}

	/**
	 * �����������е����ݺϲ���һ��
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-28����09:33:15
	 * @param vos
	 * @param vos1
	 * @return
	 * @throws Exception
	 */
	public static ReportBaseVO[] comin(ReportBaseVO[] vos, ReportBaseVO[] vos1)
			throws Exception {
		if (isEmpty(vos)) {
			if (!isEmpty(vos1)) {
				return vos1;
			}
		}
		if (isEmpty(vos1)) {
			if (!isEmpty(vos)) {
				return vos;
			}
		}
		if (isEmpty(vos) && isEmpty(vos1)) {
			return null;
		}
		List<ReportBaseVO> list = new ArrayList<ReportBaseVO>();
		for (int i = 0; i < vos.length; i++) {
			list.add(vos[i]);
		}
		for (int j = 0; j < vos1.length; j++) {
			list.add(vos1[j]);
		}
		return list.toArray(new ReportBaseVO[0]);
	}

	/**
	 * ����׷�� ��ĳ������ά�� ���������������� ƴ�ӵ�һ�� ���������� ��ά��������ѯ �������ݱ����� Ψһ�� Ҫ�뱣֤����Ψһ�Ļ�
	 * �����ȵ���combinVoByFields��������ά�Ⱥϲ�\ ��vos2������׷�ӵ�vos1��
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-25����09:44:00
	 * @param vos1
	 * @param vos2
	 * @param conds
	 * @return
	 * @throws Exception
	 */
	public static ReportBaseVO[] addByContion1(ReportBaseVO[] vos,
			ReportBaseVO[] vos1, String[] voCombinConds, String xid)
			throws Exception {
		if (isEmpty(vos)) {
			if (!isEmpty(vos1)) {
				return vos1;
			}
		}
		if (isEmpty(vos1)) {
			if (!isEmpty(vos)) {
				return vos;
			}
		}
		if (isEmpty(vos) && isEmpty(vos1)) {
			return null;
		}
		// ��¼ vos1���Ѿ����ϲ�����vo
		List<ReportBaseVO> list = new ArrayList<ReportBaseVO>();

		int size = vos.length;
		int size1 = vos1.length;
		// �� vos�е�ÿ��vo ����������vos1
		// ������������ vos1 �ӵ� vo��
		for (int i = 0; i < size; i++) {
			ReportBaseVO avo = vos[i];
			for (int j = 0; j < size1; j++) {
				ReportBaseVO bvo = vos1[j];
				boolean isEqual = true;
				for (int k = 0; k < voCombinConds.length; k++) {
					Object o1 = avo.getAttributeValue(voCombinConds[k]);
					Object o2 = bvo.getAttributeValue(voCombinConds[k]);
					if (!isEqual(o1, o2)) {
						isEqual = false;
						break;
					}
				}
				if (isEqual) {
					add(avo, bvo, xid);
				}
			}
		}
		return vos;
	}

	/**
	 * ����׷�� ��ĳ������ά�� ���������������� ƴ�ӵ�һ�� ���������� ��ά��������ѯ �������ݱ����� Ψһ�� Ҫ�뱣֤����Ψһ�Ļ�
	 * �����ȵ���combinVoByFields��������ά�Ⱥϲ�
	 * 
	 * �Ƕ� addByContion1�ĸĽ� �Ὣ vos1�а�ά�Ȳ�ѯ���� ����vos�а�ͬ����ά�Ȳ�ѯȴ�鲻����
	 * ����vos������һ��vo��vos1�еĸ�����׷�ӵ�vos��
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-25����09:44:00
	 * @param vos1
	 *            ������
	 * @param vos2
	 *            ������
	 * @param conds
	 *            ׷������
	 * @param xid
	 *            ׷�ӱ�ʶ ��vos1�е�����׷�ӵ�vos��ʱ �Ὣ vos1���������� ��Ϊ ������+xid ��� xidΪ�� ��׷�ӱ�ʾ
	 * @return
	 * @throws Exception
	 */
	public static ReportBaseVO[] addByContion2(ReportBaseVO[] vos,
			ReportBaseVO[] vos1, String[] voCombinConds, String xid)
			throws Exception {
		if (isEmpty(vos)) {
			if (!isEmpty(vos1)) {
				return vos1;
			}
		}
		if (isEmpty(vos1)) {
			if (!isEmpty(vos)) {
				return vos;
			}
		}
		if (isEmpty(vos) && isEmpty(vos1)) {
			return null;
		}
		// ��¼ vos1���Ѿ����ϲ�����vo
		List<ReportBaseVO> list = new ArrayList<ReportBaseVO>();

		int size = vos.length;
		int size1 = vos1.length;
		// �� vos�е�ÿ��vo ����������vos1
		// ������������ vos1 �ӵ� vo��
		for (int i = 0; i < size; i++) {
			ReportBaseVO avo = vos[i];
			for (int j = 0; j < size1; j++) {
				ReportBaseVO bvo = vos1[j];
				boolean isEqual = true;
				for (int k = 0; k < voCombinConds.length; k++) {
					Object o1 = avo.getAttributeValue(voCombinConds[k]);
					Object o2 = bvo.getAttributeValue(voCombinConds[k]);
					if (!isEqual(o1, o2)) {
						isEqual = false;
						break;
					}
				}
				if (isEqual) {
					add(avo, bvo, xid);
					list.add(bvo);
				}
			}
		}

		// ��¼vos1��û�б�vosƥ���ϵ�vo ���ж��κϲ�
		// ���� vos1 �� list
		// ����ĳ��ά������ ��vos1�з��������� �� list�в������������ҳ���
		// ������أ�
		// ����ѭ�� ÿ����vos1�е�һ��vo ȥlist�а��������ҷ��������� vo
		// ����з���������vo
		// �ͶϿ� list ѭ�� ������һ��ѭ��
		// ��ν������������� vo��vos1���ҳ���
		// ��ѭ���� list�����һ��Ԫ�� ��û�з���������Ԫ��ʱ, ��vos�еĶ�Ӧvoȡ��������
		List<CircularlyAccessibleValueObject> list1 = new ArrayList<CircularlyAccessibleValueObject>();// ��¼vos1��û�б�ƥ���vo
		int csize = vos1.length;
		for (int i = 0; i < csize; i++) {
			CircularlyAccessibleValueObject avo = vos1[i];
			int csize1 = list.size();
			for (int j = 0; j < csize1; j++) {
				boolean isEqual = true;
				CircularlyAccessibleValueObject bvo = list.get(j);
				for (int k = 0; k < voCombinConds.length; k++) {
					Object o1 = avo.getAttributeValue(voCombinConds[k]);
					Object o2 = bvo.getAttributeValue(voCombinConds[k]);
					if (!isEqual(o1, o2)) {
						isEqual = false;
						break;
					}
				}
				if (isEqual) {
					break;
				}
				if (j == csize1 - 1) {
					list1.add(vos1[i]);
				}
			}
		}
		// ���list ����Ϊ0 ˵��vos ��vos1 û��һ��ƥ���ϵ�
		if (list.size() == 0) {
			for (int i = 0; i < vos1.length; i++) {
				list1.add(vos1[i]);
			}
		}
		// ��û��ƥ���ϵ�vo�ϲ���
		if (list1.size() > 0) {
			for (int i = 0; i < vos.length; i++) {
				list1.add(vos[i]);
			}
			return (ReportBaseVO[]) list1
					.toArray((CircularlyAccessibleValueObject[]) java.lang.reflect.Array
							.newInstance(vos[0].getClass(), 0));
		} else {
			return vos;
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ �жϴ���Ķ����Ƿ�Ϊ��
	 * @ʱ�䣺2011-7-5����09:02:51
	 * @param value
	 * @return
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

	/**
	 * ����Сά�Ƚ������ݺϲ�
	 */
	/**
	 * @author mlr ��̨��ͳ��ά�Ⱥϲ�����
	 * @param accounts
	 * @return
	 * @throws Exception
	 */
	public static ReportBaseVO[] combinVoByFields(ReportBaseVO[] accounts,
			String[] num_condition_fields, String[] combinFields)
			throws Exception {
		if (accounts == null || accounts.length == 0)
			return null;
		if (num_condition_fields == null || num_condition_fields.length == 0)
			throw new Exception(" �ϲ������ֶ�Ϊ��");

		CircularlyAccessibleValueObject[][] os = SplitBillVOs.getSplitVOs(
				accounts, num_condition_fields);
		int len = os.length;
		ReportBaseVO[] newAccouts = new ReportBaseVO[os.length];
		ReportBaseVO[] datas = null;
		ReportBaseVO tmp = null;
		for (int i = 0; i < len; i++) {
			datas = (ReportBaseVO[]) os[i];
			tmp = datas[0];
			for (int j = 0; j < datas.length; j++) {
				if (j == 0)
					continue;
				for (String num : combinFields) {
					tmp.setAttributeValue(num, PuPubVO.getUFDouble_NullAsZero(
							tmp.getAttributeValue(num)).add(
							PuPubVO.getUFDouble_NullAsZero(datas[j]
									.getAttributeValue(num))));
				}
			}
			newAccouts[i] = tmp;
		}
		return newAccouts;
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵���� ����ĳ��ά��(����) �������������ֶζ�Ӧֵ��ͬ�ĺϲ�
	 * 
	 * @ʱ�䣺2011-7-11����09:12:25
	 * @param vos
	 * @param voCombinConds
	 *            �����ֶ�����
	 * @param types
	 *            ��ֵ����
	 * @param combinFields
	 *            ����ֶ�
	 * @return
	 */
	public static CircularlyAccessibleValueObject[] combinVoByFields(
			CircularlyAccessibleValueObject[] vos, String[] voCombinConds,
			int[] types, String[] combinFields) {

		if (vos == null || vos.length == 0) {
			return vos;
		}
		CircularlyAccessibleValueObject[][] voss = SplitBillVOs.getSplitVOs(
				vos, voCombinConds);
		// new ��ͷ��voΪ������װ��������vo
		CircularlyAccessibleValueObject[] newVos = (CircularlyAccessibleValueObject[]) java.lang.reflect.Array
				.newInstance(vos[0].getClass(), voss.length);
		int size = voss.length;
		for (int i = 0; i < size; i++) {
			CircularlyAccessibleValueObject newVo = null;
			int size1 = voss[i].length;
			for (int j = 0; j < size1; j++) {
				CircularlyAccessibleValueObject oldVo = (CircularlyAccessibleValueObject) voss[i][j];
				if (newVo == null) {
					try {
						newVo = (CircularlyAccessibleValueObject) ObjectUtils
								.serializableClone(oldVo);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					int csize = combinFields.length;
					for (int n = 0; n < csize; n++) {
						Object resultobj = newVo
								.getAttributeValue(combinFields[n]);
						Object tmpobj = oldVo
								.getAttributeValue(combinFields[n]);
						switch (types[n]) {
						case IUFTypes.INT:
							int iresult = (resultobj == null ? 0
									: ((Integer) resultobj).intValue());
							int itmp = (tmpobj == null ? 0 : ((Integer) tmpobj)
									.intValue());
							newVo.setAttributeValue(combinFields[n],
									new Integer(iresult + itmp));
							continue;
						case IUFTypes.LONG:
							long lgtmp = (tmpobj == null ? 0 : ((Long) tmpobj)
									.longValue());
							long lgresult = (resultobj == null ? 0
									: ((Long) resultobj).longValue());
							if (tmpobj != null)
								newVo.setAttributeValue(combinFields[n],
										new Long(lgresult + lgtmp));
							continue;
						case IUFTypes.UFD:
							UFDouble ufdtmp = (tmpobj == null ? new UFDouble(
									"0") : (UFDouble) tmpobj);
							UFDouble ufdResult = (resultobj == null ? new UFDouble(
									"0")
									: (UFDouble) resultobj);
							newVo.setAttributeValue(combinFields[n], ufdResult
									.add(ufdtmp));
							continue;
						case IUFTypes.STR:
							String strtmp = (tmpobj == null ? "" : tmpobj
									.toString());
							String strresult = (resultobj == null ? ""
									: resultobj.toString());
							newVo.setAttributeValue(combinFields[n], strtmp
									+ strresult);
							continue;
						}
					}
				}
			}
			newVos[i] = newVo;
		}
		return newVos;
	}

	/**
	 * 
	 * @author zhf
	 * @˵������������ҵ��vo�ϲ� 2011-9-26����02:57:12
	 * @param vos
	 * @param voCombinConds
	 *            �ϲ�ά��
	 * @param combinFields
	 *            ����ֶ� ˵������Щ�ֶξ���֧��UFDouble
	 * @return
	 * @throws BusinessException
	 */
	public static CircularlyAccessibleValueObject[] combinData(
			CircularlyAccessibleValueObject[] vos, String[] voCombinConds,
			String[] combinFields, Class classname) {
		if (vos == null || vos.length == 0 || voCombinConds == null
				|| voCombinConds.length == 0)
			return null;
		java.util.Map<String, CircularlyAccessibleValueObject> dataMap = new HashMap<String, CircularlyAccessibleValueObject>();
		StringBuffer key = new StringBuffer();
		String skey = null;
		CircularlyAccessibleValueObject tmpvo = null;
		for (CircularlyAccessibleValueObject vo : vos) {
			key.setLength(0);
			for (String name : voCombinConds) {
				key.append(vo.getAttributeValue(name));
			}
			skey = key.toString();
			if (dataMap.containsKey(skey)) {
				tmpvo = dataMap.get(skey);
				for (String name : combinFields) {
					vo.setAttributeValue(name, PuPubVO.getUFDouble_NullAsZero(
							vo.getAttributeValue(name)).add(
							PuPubVO.getUFDouble_NullAsZero(tmpvo
									.getAttributeValue(name))));
				}
			}

			dataMap.put(skey, vo);
		}
		if (dataMap.size() == 0)
			return null;
		CircularlyAccessibleValueObject[] tarVos = (CircularlyAccessibleValueObject[]) java.lang.reflect.Array
				.newInstance(classname, dataMap.size());
		dataMap.values().toArray(tarVos);
		return tarVos;
	}

}

package nc.bs.zmpub.pub.check;

import java.util.Collection;
import java.util.Dictionary;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * ��̨�ֶ�Ψһ��У�� author:mlr
 * */
public class BsUniqueCheck {
	private static BaseDAO dao;

	private static BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ У��ĳ���ֶε�Ψһ��
	 * @ʱ�䣺2011-7-6����12:36:25
	 * @param vos
	 *            У���vo����
	 * @param checkField
	 *            У����ֶ�
	 * @param errorMessage
	 *            ������ʾ��Ϣ
	 * @throws Exception
	 */
	public static void FieldUniqueCheck(SuperVO[] vos, String checkField,
			String errorMessage) throws Exception {
		if (isEmpty(vos)) {
			throw new BusinessException("�����VO���鲻��Ϊ��");
		}
		for (int i = 0; i < vos.length; i++) {
			BsUniqueCheck.FieldUniqueCheck(vos[i], checkField, null,
					errorMessage);
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ��ĳ�������£� У��ĳ���ֶ���ϵ�Ψһ�� ���� �� condition
	 * @ʱ�䣺2011-7-6����12:36:25
	 * @param vos
	 *            У���vo����
	 * @param checkField
	 *            У����ֶ�
	 * @param errorMessage
	 *            ������ʾ��Ϣ
	 * @param condition
	 *            У������
	 * @throws Exception
	 */
	public static void FieldUniqueChecks(SuperVO[] vos, String[] checkFields,
			String condition, String errorMessage) throws Exception {
		if (isEmpty(vos)) {
			throw new BusinessException("�����VO���鲻��Ϊ��");
		}
		for (int i = 0; i < vos.length; i++) {
			FieldUniqueChecks(vos[i], checkFields, condition, errorMessage);
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ��ĳ�������£� У��ĳ���ֶ���ϵ�Ψһ�� ���� �� condition
	 * @ʱ�䣺2011-7-6����12:36:25
	 * @param vos
	 *            У���vo����
	 * @param checkField
	 *            У����ֶ�
	 * @param errorMessage
	 *            ������ʾ��Ϣ
	 * @param condition
	 *            У������
	 * @throws Exception
	 */
	public static void FieldUniqueChecks1(AggregatedValueObject billvo,
			String[] checkFields, String headcontion, String bodycontion,
			String errorMessage) throws Exception {
		if (isEmpty(billvo)) {
			throw new BusinessException("����ľۺ�vo����Ϊ��");
		}
		FieldUniqueChecks(billvo, checkFields, headcontion, bodycontion,
				errorMessage);

	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ��ĳ�������� У���������ֶε�Ψһ��
	 * @ʱ�䣺2011-7-5����08:49:35
	 * @param vo
	 *            У��vo
	 * @param checkFields
	 *            У���ֶ����������
	 * @param errorMessage
	 *            ���ش�����ʾ��Ϣ
	 * @param headconditon
	 *            ��ͷ��������
	 * @param bodyconditon
	 *            �����������
	 * @throws Exception
	 */
	public static void FieldUniqueChecks(AggregatedValueObject billvo,
			String[] checkFields, String headconditon, String bodyconditon,
			String errorMessage) throws Exception {
		if (isEmpty(billvo)) {
			return;
		}
		if (isEmpty(billvo.getParentVO())) {
			return;
		}
		if (isEmpty(billvo.getChildrenVO())) {
			return;
		}
		if (isEmpty(checkFields)) {
			throw new BusinessException("����Ψһ�Եĵ��ֶ����ֲ���Ϊ��");
		}
		if (isEmpty(errorMessage)) {
			throw new BusinessException("������ʾ��Ϣ����Ϊ��");
		}
		SuperVO headVo = (SuperVO) billvo.getParentVO();
		SuperVO[] bodyVos = (SuperVO[]) billvo.getChildrenVO();
		int size = bodyVos.length;
		for (int j = 0; j < size; j++) {
			// �ж����޸ĺ�ı��滹��������ı���
			if (isEmpty(bodyVos[j].getPrimaryKey())) {
				queryByCheckFields(headVo, bodyVos[j], checkFields,
						headconditon, bodyconditon, errorMessage);
			} else {
				List list = queryByPrimaryKey(bodyVos[j]);
				// �ж��޸ĺ�ļ�¼���Ƿ�ı���ֵ���������ݿ��еļ�¼��ui�еĵ�ǰ��¼���бȽϣ�
				SuperVO vo1 = (SuperVO) list.get(0);
				// �ж��Ƿ��޸���ҪУ���ֶε�ֵ
				boolean ismodrec = false;
				// У���Ƿ��޸���ҪУ����ֶε�ֵ
				for (int i = 0; i < checkFields.length; i++) {
					if (isEmpty(bodyVos[j].getAttributeValue(checkFields[i]))
							&& isEmpty(vo1.getAttributeValue(checkFields[i]))) {
						continue;
					}
					if (isEmpty(bodyVos[j].getAttributeValue(checkFields[i]))
							|| isEmpty(vo1.getAttributeValue(checkFields[i]))) {
						ismodrec = true;
						break;
					}
					if (!bodyVos[j].getAttributeValue(checkFields[i]).equals(
							vo1.getAttributeValue(checkFields[i]))) {
						ismodrec = true;
						break;
					}
				}
				if (ismodrec) {
					queryByCheckFields(headVo, bodyVos[j], checkFields,
							headconditon, bodyconditon, errorMessage);
				}
			}
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ У�� ��������ֶε�Ψһ��
	 * @ʱ�䣺2011-7-5����08:49:35
	 * @param vo
	 *            У��vo
	 * @param checkFields
	 *            У���ֶ����������
	 * @param headconditon
	 *            ��ͷ����
	 * @param bodyconditon
	 *            ��������
	 * @param errorMessage
	 *            ���ش�����ʾ��Ϣ
	 * @throws Exception
	 */
	private static void queryByCheckFields(SuperVO headVo, SuperVO bodyVo,
			String[] checkFields, String headconditon, String bodyconditon,
			String errorMessage) throws Exception {
		if (isEmpty(headVo))
			return;
		if (isEmpty(bodyVo))
			return;
		StringBuffer cond = new StringBuffer();
		for (int i = 0; i < checkFields.length; i++) {
			String sign = " = ";
			if (isEmpty(bodyVo.getAttributeValue(checkFields[i]))) {
				if (!(bodyVo.getAttributeValue(checkFields[i]) instanceof String)
						&& !"".equalsIgnoreCase((String) bodyVo
								.getAttributeValue(checkFields[i]))) {
					sign = " is ";
				}
			}
			if (isChar(bodyVo.getAttributeValue(checkFields[i]))
					&& sign.equalsIgnoreCase(" = ")) {
				cond.append(" " + checkFields[i] + sign + "'"
						+ bodyVo.getAttributeValue(checkFields[i]) + "'"
						+ " and");
			} else {
				cond.append(" " + checkFields[i] + sign
						+ bodyVo.getAttributeValue(checkFields[i]) + " and");
			}
		}
		int length = cond.toString().length();
		String condition = cond.toString().substring(0, length - 4)
				+ " and  isNull(" + bodyVo.getEntityName() + ".dr,0)=0";
		List list = (List) getDao().retrieveByClause(bodyVo.getClass(),
				condition);
		String sql = " select count(*) from " + bodyVo.getEntityName()
				+ " join " + headVo.getEntityName() + " on "
				+ bodyVo.getEntityName() + "." + bodyVo.getParentPKFieldName()
				+ "=" + headVo.getEntityName() + "." + headVo.getPKFieldName()
				+ " where " + condition + headconditon + bodyconditon;
		Object o = getDao().executeQuery(sql, new ColumnProcessor());
		if (o == null) {
			return;
		}
		Integer num = (Integer) o;
		if (num.intValue() == 0) {
			return;
		} else {
			throw new BusinessException(errorMessage);
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ��ĳ�������� У�����ݿ���ĳ���ֶε�Ψһ�� ���� ����condition
	 * @ʱ�䣺2011-7-5����08:46:12
	 * @param vo
	 *            У���vo
	 * @param checkField
	 *            У����ֶε�����
	 * @param errorMessage
	 *            ������ʾ��Ϣ
	 * @param condition
	 *            ��������
	 * @throws Exception
	 */
	public static void FieldUniqueCheck(SuperVO vo, String checkField,
			String condition, String errorMessage) throws Exception {
		if (isEmpty(vo)) {
			return;
		}
		if (isEmpty(checkField)) {
			throw new BusinessException("����Ψһ�Եĵ��ֶ����ֲ���Ϊ�ڿ�");
		}
		if (isEmpty(errorMessage)) {
			throw new BusinessException("������ʾ��Ϣ����Ϊ�ڿ�");
		}
		// �ж����޸ĺ�ı��滹��������ı���
		if (isEmpty(vo.getPrimaryKey())) {
			queryByCheckField(vo, checkField, condition, errorMessage);
		} else {
			List list = queryByPrimaryKey(vo);
			// �ж��޸ĺ�ļ�¼���Ƿ�ı���ֵ���������ݿ��еļ�¼��ui�еĵ�ǰ��¼���бȽϣ�
			SuperVO vo1 = (SuperVO) list.get(0);
			if (isEmpty(vo.getAttributeValue(checkField))
					&& isEmpty(vo1.getAttributeValue(checkField))) {
				return;
			}
			if (!isEmpty(vo.getAttributeValue(checkField))
					&& !isEmpty(vo1.getAttributeValue(checkField))) {
				if (vo.getAttributeValue(checkField).equals(
						vo1.getAttributeValue(checkField))) {
					return;
				} else {
					queryByCheckField(vo, checkField, condition, errorMessage);
				}
			}
			if (isEmpty(vo.getAttributeValue(checkField))
					|| isEmpty(vo.getAttributeValue(checkField))) {
				queryByCheckField(vo, checkField, condition, errorMessage);
			}
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ��ĳ�������� У��ĳ���ֶε����ݿ�Ψһ��
	 * @ʱ�䣺2011-7-6����09:35:24
	 * @param vo
	 * @param checkField
	 * @param condition
	 * @param errorMessage
	 */
	private static void queryByCheckField(SuperVO vo, String checkField,
			String condition1, String errorMessage) throws Exception {
		String sign = " = ";
		if (isEmpty(vo.getAttributeValue(checkField))) {
			if (!(vo.getAttributeValue(checkField) instanceof String)
					&& !"".equalsIgnoreCase((String) vo
							.getAttributeValue(checkField))) {
				sign = " is ";
			}
		}
		String value = "";
		if (isChar(value) && sign.equalsIgnoreCase(" = ")) {
			value = "'" + vo.getAttributeValue(checkField) + "'";
		} else {
			value = vo.getAttributeValue(checkField) + "";
		}

		String condition = checkField + sign + value + " and  isNull("
				+ vo.getEntityName() + ".dr,0)=0 " + condition1;
		List list = (List) getDao().retrieveByClause(vo.getClass(), condition);
		if (list == null || list.size() == 0) {
			return;
		} else {
			throw new BusinessException(errorMessage);
		}

	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ����ֶε����佻���Ψһ��У�� ��У��ĳ��ά���£�����Сֵ �� ���ֵ ֮��ֻ���� һ����¼
	 * 
	 * @ʱ�䣺2011-7-6����09:50:30
	 * @param vo
	 * @param checkFields
	 *            У����ֶ�����
	 * @param minField
	 *            ��Сֵ
	 * @param maxField
	 *            ���ֵ
	 * @param errorMessage
	 *            ������ʾ��Ϣ
	 * @throws Exception
	 */
	public static void FieldUniqueCheckInment(SuperVO vo, String[] checkFields,
			String minField, String maxField, String errorMessage)
			throws Exception {
		if (isEmpty(vo)) {
			return;
		}
		if (isEmpty(checkFields)) {
			throw new BusinessException("У����ֶβ���Ϊ��");
		}
		if (isEmpty(minField) || isEmpty(maxField)) {
			throw new BusinessException("������Сֵ�����ֵ������Ϊ��");
		}
		if (isEmpty(vo.getAttributeValue(minField))
				|| isEmpty(vo.getAttributeValue(maxField))) {
			throw new BusinessException("�����vo ��Сֵ �� ���ֵ����Ϊ��");
		}
		if (isEmpty(vo.getPrimaryKey())) {
			// ������ı���
			queryByCheckFieldsInMent(vo, checkFields, minField, maxField,
					errorMessage);
		} else {
			// �޸ĺ󱣴�
			List list = queryByPrimaryKey(vo);
			SuperVO svo = (SuperVO) list.get(0);
			boolean ismodrec = false;
			// У���Ƿ��޸���ҪУ����ֶε�ֵ
			for (int i = 0; i < checkFields.length; i++) {
				if (isEmpty(vo.getAttributeValue(checkFields[i]))
						&& isEmpty(vo.getAttributeValue(checkFields[i]))) {
					continue;
				}
				if (isEmpty(vo.getAttributeValue(checkFields[i]))
						|| isEmpty(vo.getAttributeValue(checkFields[i]))) {
					ismodrec = true;
					break;
				}
				if (!vo.getAttributeValue(checkFields[i]).equals(
						svo.getAttributeValue(checkFields[i]))) {
					ismodrec = true;
					break;
				}
			}
			if (ismodrec) {
				queryByCheckFieldsInMent(vo, checkFields, minField, maxField,
						errorMessage);
			} else {
				queryByCheckFieldsInMent1(vo, svo, checkFields, minField,
						maxField, errorMessage);
			}
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ �÷����������޸ı���ʱ��
	 * 
	 *             У���ֶμ� checkFields ���ֵ��û�����޸� ���� ��������Сֵ(minField)
	 *             ���������ֵ(maxField) ���������޸�
	 * 
	 *             ����������ø÷���У��
	 * @ʱ�䣺2011-7-6����12:19:38
	 * @param vo
	 *            Ҫ�����vo
	 * @param svo
	 *            �������������ݿ��в����vo
	 * @param checkFields
	 *            У����ֶ�
	 * @param minField
	 *            ������Сֵ
	 * @param maxField
	 *            �������ֵ
	 * @param errorMessage
	 *            ������ʾ��Ϣ
	 * @throws Exception
	 */
	private static void queryByCheckFieldsInMent1(SuperVO vo, SuperVO svo,
			String[] checkFields, String minField, String maxField,
			String errorMessage) throws Exception {
		Object voMin = vo.getAttributeValue(minField);
		Object voMax = vo.getAttributeValue(maxField);
		Object svoMin = svo.getAttributeValue(minField);
		Object svoMax = svo.getAttributeValue(maxField);
		if (isEmpty(voMin) || isEmpty(voMax)) {
			throw new BusinessException("�����vo��Сֵ �� ���ֵ����Ϊ��");
		}
		if (isEmpty(svoMin) || isEmpty(svoMax)) {
			return;
		}
		if (voMin.equals(svoMin) && voMax.equals(svoMax)) {
			return;
		} else {
			List listo = queryByCheckFields(vo, checkFields);
			if (isEmpty(listo)) {
				throw new BusinessException("�ü�¼�Ѿ���ɾ��,��ˢ�½���");
			}
			int size = listo.size();
			for (int i = 0; i < size; i++) {
				SuperVO ivo = (SuperVO) listo.get(i);
				if (ivo.getPrimaryKey().equalsIgnoreCase(vo.getPrimaryKey())) {
					listo.remove(i);
					break;
				}
			}
			size = listo.size();
			for (int i = 0; i < size; i++) {
				SuperVO ssvo = (SuperVO) listo.get(i);
				if (isEmpty(ssvo.getAttributeValue(minField))
						|| isEmpty(ssvo.getAttributeValue(maxField))) {
					continue;
				}
				Object ssvoMin = ssvo.getAttributeValue(minField);
				Object ssvoMax = ssvo.getAttributeValue(maxField);
				if (compareInto(voMax, ssvoMin) == -1
						|| compareInto(voMin, ssvoMax) == 1) {
					continue;
				} else {
					throw new BusinessException(errorMessage);
				}
			}
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ����ֶε����佻���Ψһ��У�� ��У��ĳ��ά���£�����Сֵ �� ���ֵ ֮��ֻ���� һ����¼ ���ڵ��ݵ��������� ��
	 *             �޸ı���ʱΨһУ���ֶ�(checkFields)�Ѿ����޸� ��ʱ��
	 * @ʱ�䣺2011-7-6����10:58:47
	 * @param vo
	 *            Ҫ�����vo
	 * @param checkFields
	 *            У����ֶ�
	 * @param minField
	 *            ������Сֵ
	 * @param maxField
	 *            �������ֵ
	 * @param errorMessage
	 *            ������ʾ��Ϣ
	 * @throws Exception
	 */
	private static void queryByCheckFieldsInMent(SuperVO vo,
			String[] checkFields, String minField, String maxField,
			String errorMessage) throws Exception {
		List list = queryByCheckFields(vo, checkFields);
		if (isEmpty(list)) {
			return;
		}
		int size = list.size();
		for (int i = 0; i < size; i++) {
			SuperVO svo = (SuperVO) list.get(i);
			if (isEmpty(svo.getAttributeValue(minField))
					|| isEmpty(svo.getAttributeValue(maxField))) {
				continue;
			}
			Object voMin = vo.getAttributeValue(minField);
			Object voMax = vo.getAttributeValue(maxField);
			Object svoMin = svo.getAttributeValue(minField);
			Object svoMax = svo.getAttributeValue(maxField);
			if (compareInto(voMax, svoMin) == -1
					|| compareInto(voMin, svoMax) == 1) {
				continue;
			} else {
				throw new BusinessException(errorMessage);
			}
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ �Ƚ���������֮��Ĵ�С ��� o1>o2 ���� 1 ��� o1=o2 ���� 0 ��� o1<o2 ���� -1 ���
	 *             �Ƿ����ݲ��ܱȽϷ��� -2
	 * @ʱ�䣺2011-7-6����10:21:21
	 * @param o1
	 * @param o2
	 */
	private static int compareInto(Object o1, Object o2) {
		if (isEmpty(o1) || isEmpty(o2)) {
			return -2;
		}
		if (o1 instanceof Integer && o2 instanceof Integer) {
			Integer i1 = (Integer) o1;
			Integer i2 = (Integer) o2;
			return i1.compareTo(i2);
		}
		if (o1 instanceof UFDouble && o2 instanceof UFDouble) {
			UFDouble i1 = (UFDouble) o1;
			UFDouble i2 = (UFDouble) o2;
			return i1.compareTo(i2);
		}
		if (o1 instanceof String && o2 instanceof String) {
			String i1 = (String) o1;
			String i2 = (String) o2;
			return i1.compareTo(i2);
		}
		if (o1 instanceof UFDate && o2 instanceof UFDate) {
			UFDate i1 = (UFDate) o1;
			UFDate i2 = (UFDate) o2;
			if (i1.before(i2)) {
				return -1;
			} else if (i1.after(i2)) {
				return 1;
			} else {
				return 0;
			}
		}
		return -2;
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ У��ĳ���ֶε�Ψһ��
	 * @ʱ�䣺2011-7-6����12:38:18
	 * @param vo
	 * @param checkField
	 * @param errorMessage
	 * @throws Exception
	 */
	private static void queryByCheckField(SuperVO vo, String checkField,
			String errorMessage) throws Exception {
		String sign = " = ";
		if (isEmpty(vo.getAttributeValue(checkField))) {
			if (!(vo.getAttributeValue(checkField) instanceof String)
					&& !"".equalsIgnoreCase((String) vo
							.getAttributeValue(checkField))) {
				sign = " is ";
			}
		}
		String value = "";
		if (isChar(value) && sign.equalsIgnoreCase(" = ")) {
			value = "'" + vo.getAttributeValue(checkField) + "'";
		} else {
			value = vo.getAttributeValue(checkField) + "";
		}

		String condition = checkField + sign + value + " and  isNull("
				+ vo.getEntityName() + ".dr,0)=0";
		List list = (List) getDao().retrieveByClause(vo.getClass(), condition);
		if (list == null || list.size() == 0) {
			return;
		} else {
			throw new BusinessException(errorMessage);
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ����������ѯһ��vo
	 * @ʱ�䣺2011-7-6����12:40:17
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	private static List queryByPrimaryKey(SuperVO vo) throws Exception {

		String condition = vo.getPKFieldName() + " ='" + vo.getPrimaryKey()
				+ "' and  isNull(" + vo.getEntityName() + ".dr,0)=0";
		List list = (List) getDao().retrieveByClause(vo.getClass(), condition);
		if (list == null || list.size() <= 0) {
			throw new BusinessException("�ü�¼�Ѿ���ɾ����������ˢ�½���");
		}
		return list;
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ У�鼸���ֶ���ϵ�Ψһ��
	 * @ʱ�䣺2011-7-6����12:41:00
	 * @param vos
	 *            У���vo����
	 * @param checkFields
	 *            У����ֶ�����
	 * @param errorMessage
	 *            ������ʾ��Ϣ
	 * @throws Exception
	 */
	public static void FieldUniqueCheck(SuperVO[] vos, String[] checkFields,
			String errorMessage) throws Exception {
		if (isEmpty(vos)) {
			throw new BusinessException("�����VO���鲻��Ϊ��");
		}
		for (int i = 0; i < vos.length; i++) {
			FieldUniqueCheck(vos[i], checkFields, errorMessage);
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ У������ֶε�Ψһ��
	 * @ʱ�䣺2011-7-5����08:49:35
	 * @param vo
	 *            У��vo
	 * @param checkFields
	 *            У���ֶ����������
	 * @param errorMessage
	 *            ���ش�����ʾ��Ϣ
	 * @throws Exception
	 */
	public static void FieldUniqueCheck(SuperVO vo, String[] checkFields,
			String errorMessage) throws Exception {
		if (isEmpty(vo)) {
			return;
		}
		if (isEmpty(checkFields)) {
			throw new BusinessException("����Ψһ�Եĵ��ֶ����ֲ���Ϊ��");
		}
		if (isEmpty(errorMessage)) {
			throw new BusinessException("������ʾ��Ϣ����Ϊ��");
		}
		// �ж����޸ĺ�ı��滹��������ı���
		if (isEmpty(vo.getPrimaryKey())) {
			queryByCheckFields(vo, checkFields, errorMessage);
		} else {
			List list = queryByPrimaryKey(vo);
			// �ж��޸ĺ�ļ�¼���Ƿ�ı���ֵ���������ݿ��еļ�¼��ui�еĵ�ǰ��¼���бȽϣ�
			SuperVO vo1 = (SuperVO) list.get(0);
			// �ж��Ƿ��޸���ҪУ���ֶε�ֵ
			boolean ismodrec = false;
			// У���Ƿ��޸���ҪУ����ֶε�ֵ
			for (int i = 0; i < checkFields.length; i++) {
				if (isEmpty(vo.getAttributeValue(checkFields[i]))
						&& isEmpty(vo.getAttributeValue(checkFields[i]))) {
					continue;
				}
				if (isEmpty(vo.getAttributeValue(checkFields[i]))
						|| isEmpty(vo.getAttributeValue(checkFields[i]))) {
					ismodrec = true;
					break;
				}
				if (!vo.getAttributeValue(checkFields[i]).equals(
						vo1.getAttributeValue(checkFields[i]))) {
					ismodrec = true;
					break;
				}
			}
			if (ismodrec) {
				queryByCheckFields(vo, checkFields, errorMessage);
			}
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ��ĳ���������� У������ֶε�Ψһ�� ��������conditon
	 * @ʱ�䣺2011-7-5����08:49:35
	 * @param vo
	 *            У��vo
	 * @param checkFields
	 *            У���ֶ����������
	 * @param errorMessage
	 *            ���ش�����ʾ��Ϣ
	 * @param conditon
	 *            ��������
	 * @throws Exception
	 */
	public static void FieldUniqueChecks(SuperVO vo, String[] checkFields,
			String conditon, String errorMessage) throws Exception {
		if (isEmpty(vo)) {
			return;
		}
		if (isEmpty(checkFields)) {
			throw new BusinessException("����Ψһ�Եĵ��ֶ����ֲ���Ϊ��");
		}
		if (isEmpty(errorMessage)) {
			throw new BusinessException("������ʾ��Ϣ����Ϊ��");
		}
		// �ж����޸ĺ�ı��滹��������ı���
		if (isEmpty(vo.getPrimaryKey())) {
			queryByCheckFields(vo, checkFields, conditon, errorMessage);
		} else {
			List list = queryByPrimaryKey(vo);
			// �ж��޸ĺ�ļ�¼���Ƿ�ı���ֵ���������ݿ��еļ�¼��ui�еĵ�ǰ��¼���бȽϣ�
			SuperVO vo1 = (SuperVO) list.get(0);
			// �ж��Ƿ��޸���ҪУ���ֶε�ֵ
			boolean ismodrec = false;
			// У���Ƿ��޸���ҪУ����ֶε�ֵ
			for (int i = 0; i < checkFields.length; i++) {
				if (isEmpty(vo.getAttributeValue(checkFields[i]))
						&& isEmpty(vo.getAttributeValue(checkFields[i]))) {
					continue;
				}
				if (isEmpty(vo.getAttributeValue(checkFields[i]))
						|| isEmpty(vo.getAttributeValue(checkFields[i]))) {
					ismodrec = true;
					break;
				}
				if (!vo.getAttributeValue(checkFields[i]).equals(
						vo1.getAttributeValue(checkFields[i]))) {
					ismodrec = true;
					break;
				}
			}
			if (ismodrec) {
				queryByCheckFields(vo, checkFields, conditon, errorMessage);
			}
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ��ĳ���������� У������ֶε�Ψһ�� ��������conditon
	 * @ʱ�䣺2011-7-5����08:49:35
	 * @param vo
	 *            У��vo
	 * @param checkFields
	 *            У���ֶ����������
	 * @param errorMessage
	 *            ���ش�����ʾ��Ϣ
	 * @param conditon
	 *            ��������
	 * @throws Exception
	 */
	private static void queryByCheckFields(SuperVO vo, String[] checkFields,
			String conditon, String errorMessage) throws Exception {
		StringBuffer cond = new StringBuffer();
		for (int i = 0; i < checkFields.length; i++) {
			String sign = " = ";
			if (isEmpty(vo.getAttributeValue(checkFields[i]))) {
				if (!(vo.getAttributeValue(checkFields[i]) instanceof String)
						&& !"".equalsIgnoreCase((String) vo
								.getAttributeValue(checkFields[i]))) {
					sign = " is ";
				}
			}
			if (isChar(vo.getAttributeValue(checkFields[i]))
					&& sign.equalsIgnoreCase(" = ")) {
				cond.append(" " + checkFields[i] + sign + "'"
						+ vo.getAttributeValue(checkFields[i]) + "'" + " and");
			} else {
				cond.append(" " + checkFields[i] + sign
						+ vo.getAttributeValue(checkFields[i]) + " and");
			}
		}
		int length = cond.toString().length();
		String condition = cond.toString().substring(0, length - 4)
				+ " and  isNull(" + vo.getEntityName() + ".dr,0)=0 " + conditon;
		List list = (List) getDao().retrieveByClause(vo.getClass(), condition);
		if (list == null || list.size() == 0) {
			return;
		} else {
			throw new BusinessException(errorMessage);
		}

	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ У������ֶε�Ψһ��
	 * @ʱ�䣺2011-7-5����08:49:35
	 * @param vo
	 *            У��vo
	 * @param checkFields
	 *            У���ֶ����������
	 * @param errorMessage
	 *            ���ش�����ʾ��Ϣ
	 * @throws Exception
	 */
	private static void queryByCheckFields(SuperVO vo, String[] checkFields,
			String errorMessage) throws Exception {
		StringBuffer cond = new StringBuffer();
		for (int i = 0; i < checkFields.length; i++) {
			String sign = " = ";
			if (isEmpty(vo.getAttributeValue(checkFields[i]))) {
				if (!(vo.getAttributeValue(checkFields[i]) instanceof String)
						&& !"".equalsIgnoreCase((String) vo
								.getAttributeValue(checkFields[i]))) {
					sign = " is ";
				}
			}
			if (isChar(vo.getAttributeValue(checkFields[i]))
					&& sign.equalsIgnoreCase(" = ")) {
				cond.append(" " + checkFields[i] + sign + "'"
						+ vo.getAttributeValue(checkFields[i]) + "'" + " and");
			} else {
				cond.append(" " + checkFields[i] + sign
						+ vo.getAttributeValue(checkFields[i]) + " and");
			}
		}
		int length = cond.toString().length();
		String condition = cond.toString().substring(0, length - 4)
				+ " and  isNull(" + vo.getEntityName() + ".dr,0)=0";
		List list = (List) getDao().retrieveByClause(vo.getClass(), condition);
		if (list == null || list.size() == 0) {
			return;
		} else {
			throw new BusinessException(errorMessage);
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ���� У���ֶ� �����ݿ��в�ѯ���������� vo����
	 * @ʱ�䣺2011-7-6����12:43:34
	 * @param vo
	 *            Ҫ��ѯ��vo
	 * @param checkFields
	 *            У����ֶ�����
	 * @return
	 * @throws Exception
	 */
	private static List queryByCheckFields(SuperVO vo, String[] checkFields)
			throws Exception {
		StringBuffer cond = new StringBuffer();
		for (int i = 0; i < checkFields.length; i++) {
			String sign = " = ";
			if (isEmpty(vo.getAttributeValue(checkFields[i]))) {
				if (!(vo.getAttributeValue(checkFields[i]) instanceof String)
						&& !"".equalsIgnoreCase((String) vo
								.getAttributeValue(checkFields[i]))) {
					sign = " is ";
				}
			}
			if (isChar(vo.getAttributeValue(checkFields[i]))
					&& sign.equalsIgnoreCase(" = ")) {
				cond.append(" " + checkFields[i] + sign + "'"
						+ vo.getAttributeValue(checkFields[i]) + "'" + " and");
			} else {
				cond.append(" " + checkFields[i] + sign
						+ vo.getAttributeValue(checkFields[i]) + " and");
			}
		}
		int length = cond.toString().length();
		String condition = cond.toString().substring(0, length - 4)
				+ " and  isNull(" + vo.getEntityName() + ".dr,0)=0";
		List list = (List) getDao().retrieveByClause(vo.getClass(), condition);
		return list;
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ �жϴ���Ķ����Ƿ����ַ���
	 * @ʱ�䣺2011-7-5����08:51:48
	 * @param value
	 * @return
	 */
	private static boolean isChar(Object value) {
		if (value == null) {
			return false;
		}
		if (value instanceof String || value instanceof UFDate) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ �жϴ���Ķ����Ƿ�Ϊ��(String)
	 * @ʱ�䣺2011-7-5����08:53:00
	 * @param o
	 * @return
	 */
	private static boolean isnull(Object o) {
		if (o == null || o.toString().trim().equals(""))
			return true;
		return false;
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ �жϴ���Ķ����Ƿ�Ϊ��
	 * @ʱ�䣺2011-7-6����10:31:42
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

}

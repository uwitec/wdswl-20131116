package nc.ui.zmpub.pub.check;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;

import nc.ui.pub.beans.UITable;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.NullFieldException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;

/**
 * author:mlr ����Ϊ����ǰ�� ǰ̨У����
 * 
 * */
public class BeforeSaveValudate {
	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ����ļ����ֶι���Ψһ�ԣ�ÿһ�еļ�¼�ļ����ֶι�����Ψһ�Լ�¼���������бȽ� ������Ψһ��У��
	 * @ʱ�䣺2011-7-5����08:58:49
	 * @param table
	 * @param model
	 * @param fields
	 *            У����ֶ���������
	 * @param displays
	 *            У����ֶε���ʾ����
	 * @throws Exception
	 */
	public static void beforeSaveBodyUnique(UITable table, BillModel model,
			String[] fields, String[] displays) throws Exception {
		int num = table.getRowCount();
		if (fields == null || fields.length == 0) {
			return;
		}
		if (num > 0) {
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < num; i++) {
				String key = "";
				for (String str : fields) {
					Object o1 = model.getValueAt(i, str);
					key = key + "," + String.valueOf(o1);
				}
				if (list.contains(key)) {
					String dis = "";
					for (int j = 0; j < displays.length; j++) {
						dis = dis + "[ " + displays[j] + " ]";
					}
					throw new BusinessException("��[" + (i + 1) + "]�б����ֶ� "
							+ dis + " �����ظ�!");
				} else {
					list.add(key);
				}
			}
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
	public static void beforeSaveBodyUniqueInmet(SuperVO[] vos,
			String[] fields, String minField, String maxField,
			String[] displays, String minDisplay, String maxDisplay)
			throws Exception {
		if (isEmpty(vos)) {
			return;
		}
		SuperVO[][] voss = (SuperVO[][]) SplitBillVOs.getSplitVOs(vos, fields);
		if (isEmpty(voss)) {
			return;
		}
		if (isEmpty(fields)) {
			throw new Exception("У����ֶβ�����Ϊ��");
		}
		if (isNULL(minField) || isNULL(maxField)) {
			throw new Exception("У��������Сֵ �� ���ֵ������Ϊ��");
		}
		if (isEmpty(displays)) {
			throw new Exception("У�������ʾ�ֶβ�����Ϊ��");
		}
		// ������ʾ�ֶ�
		String dis = "";
		for (int j = 0; j < displays.length; j++) {
			dis = dis + "[ " + displays[j] + " ]";
		}
		int size = voss.length;
		for (int i = 0; i < size; i++) {
			int size1 = voss[i].length;
			for (int j = 0; j < size1; j++) {
				SuperVO vo = voss[i][j];
				for (int k = j + 1; k < size1; k++) {
					Object voMin = vo.getAttributeValue(minField);
					Object voMax = vo.getAttributeValue(maxField);
					Object svoMin = voss[i][k].getAttributeValue(minField);
					Object svoMax = voss[i][k].getAttributeValue(maxField);
					if (compareInto(voMax, svoMin) == -1
							|| compareInto(voMin, svoMax) == 1) {
						continue;
					} else {
						throw new BusinessException("�����ֶ�  " + dis + "��ͬ�������  "
								+ "[" + minDisplay + "] �� [" + maxDisplay
								+ "] ������ڽ���");
					}
				}
			}
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ У��vo������ĳ���ֶ�����ֵ�ĺ� ��Ϊ�ջ��� ֻУ����� ָ����У���ֶηֱ�����е�ֵ�ĺ� ��Ϊ�ջ���
	 * @ʱ�䣺2011-7-8����09:14:08
	 * @param vo
	 * @param checkFields
	 *            У����ֶ�����
	 * @param displays
	 *            �ֶ����� ��ʾ����
	 * @throws BusinessException
	 */
	public static void checkNotAllNulls(AggregatedValueObject vo,
			String[] checkFields, String[] displays) throws BusinessException {
		if (checkFields == null || checkFields.length == 0) {
			throw new BusinessException("У���ֶβ�����Ϊ��");
		}
		int size = checkFields.length;
		for (int i = 0; i < size; i++) {
			checkNotAllNull(vo, checkFields[i], displays[i]);
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ У��vo������ĳ���ֶ�����ֵ�ĺ� ��Ϊ�ջ��� ֻУ����� ָ����У���ֶηֱ�����е�ֵ�ĺ� ��Ϊ�ջ���
	 * @ʱ�䣺2011-7-8����09:14:08
	 * @param vo
	 * @param checkFields
	 *            У����ֶ�����
	 * @param display
	 *            �ֶ�������ʾ����
	 * @throws BusinessException
	 */
	private static void checkNotAllNull(AggregatedValueObject vo,
			String checkField, String display) throws BusinessException {
		if (vo.getChildrenVO() == null || vo.getChildrenVO().length == 0) {
			return;
		}
		SuperVO[] vos = (SuperVO[]) vo.getChildrenVO();
		UFDouble znum = new UFDouble("0.0");
		int size = vos.length;
		for (int i = 0; i < size; i++) {
			UFDouble num = PuPubVO.getUFDouble_NullAsZero(vos[i]
					.getAttributeValue(checkField));
			znum = znum.add(num);
		}
		if (znum.doubleValue() <= 0) {
			throw new BusinessException("[" + display + "]������Ϊ��");
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ �ж϶����Ƿ�ΪInteger����
	 * @ʱ�䣺2011-7-8����09:23:26
	 * @param o
	 * @return
	 */
	private static boolean isInteger(Object o) {
		if (o == null) {
			return true;
		}
		if (o instanceof Integer) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ �ж϶����Ƿ�ΪUFDouble����
	 * @ʱ�䣺2011-7-8����09:23:26
	 * @param o
	 * @return
	 */
	private static boolean isUFDouble(Object o) {
		if (o == null) {
			return true;
		}
		if (o instanceof UFDouble) {
			return true;
		}
		return false;
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
			return i1.compareTo(i2);
		}
		return -2;
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ���岻����Ϊ�յ�У��
	 * @ʱ�䣺2011-7-5����09:00:23
	 * @param table
	 * @throws Exception
	 */
	public static void BodyNotNULL(UITable table) throws Exception {
		if (table.getRowCount() <= 0) {
			throw new Exception("���岻����Ϊ��");
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ����ĳ���ֶε�Ψһ��У��
	 * @ʱ�䣺2011-7-5����09:00:23
	 * @param table
	 * @throws Exception
	 */
	public static void FieldBodyUnique(UITable table, BillModel model,
			String checkField, String displayName) throws Exception {
		int num = table.getRowCount();
		if (checkField == null || "".equalsIgnoreCase(checkField)) {
			return;
		}
		if (num > 0) {
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < num; i++) {
				String key = "";
				Object o1 = model.getValueAt(i, checkField);
				key = String.valueOf(o1);
				if (list.contains(key)) {
					throw new BusinessException("�����[" + (i + 1) + "]���ֶ�"
							+ "[ " + displayName + " ]" + "�����ظ�!");
				} else {
					list.add(key);
				}
			}
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ����ĳ���ֶε�Ψһ��У��
	 * @ʱ�䣺2011-7-5����09:00:23
	 * @param table
	 * @throws Exception
	 */
	public static void FieldBodyUnique(int counts, BillModel model,
			String checkField, String displayName) throws Exception {
		int num = counts;
		if (checkField == null || "".equalsIgnoreCase(checkField)) {
			return;
		}
		if (num > 0) {
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < num; i++) {
				String key = "";
				Object o1 = model.getValueAt(i, checkField);
				key = String.valueOf(o1);
				if (list.contains(key)) {
					throw new BusinessException("�����[" + (i + 1) + "]���ֶ�"
							+ "[ " + displayName + " ]" + "�����ظ�!");
				} else {
					list.add(key);
				}
			}
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
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ �жϳ���Ķ����Ƿ�Ϊ��
	 * @ʱ�䣺2011-7-5����09:03:51
	 * @param o
	 * @return
	 */
	public static boolean isNULL(Object o) {
		if (o == null || o.toString().trim().equals(""))
			return true;
		return false;
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ������У��
	 * @ʱ�䣺2011-6-7����04:24:33
	 * @throws ValidationException
	 */
	public static void dataNotNullValidate(BillCardPanel panel)
			throws ValidationException {
		StringBuffer message = null;
		BillItem[] headtailitems = panel.getBillData().getHeadTailItems();
		if (headtailitems != null) {
			for (int i = 0; i < headtailitems.length; i++) {
				if (headtailitems[i].isNull())
					if (isNULL(headtailitems[i].getValueObject())
							&& headtailitems[i].isShow()) {
						if (message == null)
							message = new StringBuffer();
						message.append("[");
						message.append(headtailitems[i].getName());
						message.append("]");
						message.append(",");
					}
			}
		}
		if (message != null) {
			message.deleteCharAt(message.length() - 1);
			throw new NullFieldException(message.toString());
		}
		// ���Ӷ��ӱ��ѭ��
		String[] tableCodes = panel.getBillData().getTableCodes(BillData.BODY);
		if (tableCodes != null) {
			for (int t = 0; t < tableCodes.length; t++) {
				String tablecode = tableCodes[t];
				for (int i = 0; i < panel.getBillModel(tablecode).getRowCount(); i++) {
					StringBuffer rowmessage = new StringBuffer();

					rowmessage.append(" ");
					if (tableCodes.length > 1) {
						rowmessage.append(panel.getBillData().getTableName(
								BillData.BODY, tablecode));
						rowmessage.append("(");
						// "ҳǩ"
						rowmessage.append(nc.ui.ml.NCLangRes.getInstance()
								.getStrByID("_Bill", "UPP_Bill-000003"));
						rowmessage.append(") ");
					}
					rowmessage.append(i + 1);
					rowmessage.append("(");
					// "��"
					rowmessage.append(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("_Bill", "UPP_Bill-000002"));
					rowmessage.append(") ");

					StringBuffer errormessage = null;
					BillItem[] items = panel.getBillData()
							.getBodyItemsForTable(tablecode);
					for (int j = 0; j < items.length; j++) {
						BillItem item = items[j];
						if (item.isShow() && item.isNull()) {// �����Ƭ��ʾ������Ϊ�գ��ŷǿ�У��
							Object aValue = panel.getBillModel(tablecode)
									.getValueAt(i, item.getKey());
							if (isNULL(aValue)) {
								errormessage = new StringBuffer();
								errormessage.append("[");
								errormessage.append(item.getName());
								errormessage.append("]");
								errormessage.append(",");
							}
						}
					}
					if (errormessage != null) {

						errormessage.deleteCharAt(errormessage.length() - 1);
						rowmessage.append(errormessage);
						if (message == null)
							message = new StringBuffer(rowmessage);
						else
							message.append(rowmessage);
						break;
					}
				}
				if (message != null)
					break;
			}
		}
		if (message != null) {
			throw new NullFieldException(message.toString());
		}

	}

}

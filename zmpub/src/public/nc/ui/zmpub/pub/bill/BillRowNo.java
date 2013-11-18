package nc.ui.zmpub.pub.bill;

import java.util.HashMap;

import nc.ui.pub.bill.BillCardPanel;
import nc.vo.pub.lang.UFDouble;

/**
 * ���ݱ����Զ������кŵĹ�����
 * 
 * @author
 */
public class BillRowNo {
	// �к���ʼֵ
	private static int m_rowNoStart = 10;
	// �кŵ���󳤶�
	public static final int MAX_LENGTH = 20;
	// С�����λ����
	public static final int DIGIT_POWER = 8;
	// ��
	public static final UFDouble ZERO_UFDOUBLE = new UFDouble(0.0);
	// ���������Сֵ
	public static final UFDouble MIN_VALUE = new UFDouble(0.00000001);
	// ��ʱʹ�����·�ʽ���� ����
	public static final int STEP_VALUE = 10;

	public static final int START_VALUE = 10;

	public static String getCorrectString(UFDouble dOrgValue) {

		String sOrgValue = dOrgValue.toString();
		if (sOrgValue.indexOf(".") < 0) {
			return sOrgValue;
		}

		// ȡ���µ����������UFDouble
		UFDouble dNewValue = dOrgValue.setScale(-8, UFDouble.ROUND_HALF_UP);
		// ������ȷ��ֵ
		dNewValue = dNewValue.compareTo(MIN_VALUE) <= 0 ? MIN_VALUE : dNewValue;

		// ԭֵ
		String sNew = dNewValue.toString();
		int iLen = sNew.length();

		// ȥ������0
		int iDotIndex = sNew.indexOf(".");
		if (iDotIndex > 0) {
			for (int i = iLen - 1; i >= iDotIndex; i--) {
				if ('0' == sNew.charAt(i)) {
					sNew = sNew.substring(0, i);
				} else if ('.' == sNew.charAt(i)) {
					sNew = sNew.substring(0, i);
					break;
				} else {
					break;
				}
			}
		}

		return sNew;
	}

	/**
	 * BillRowNo ������ע�⡣
	 */
	private BillRowNo() {
		super();
	}

	public static void pasteLineRowNo(BillCardPanel pnlCard, String sBillType,
			String sRowNOKey, int iPasteCount) {

		// ����ƽ���к�
		insertLineRowNos(pnlCard, sBillType, sRowNOKey, pnlCard.getBillTable()
				.getSelectedRow(), iPasteCount);

	}

	/**
	 * ��һ�к��Զ���������е��кš�
	 * <p>
	 * <strong>����ģ�飺SCM����ģ�顣</strong>
	 * <p>
	 * <strong>����޸��ˣ���ӡ��</strong>
	 * <p>
	 * <strong>����޸���ǰ��2006-7-4</strong>
	 * <p>
	 * <strong>����������</strong>
	 * <p>
	 * 
	 * @param BillCardPanel
	 *            pnlCard ����ģ��
	 * @param String
	 *            sBillType ��������
	 * @param String
	 *            sRowNOKey �к�ITEMKEY
	 * @return void
	 * @throws ��
	 * @since
	 * @see
	 */
	public static void addLineRowNo(BillCardPanel pnlList, String sBillType,
			String sRowNOKey) {

		addLineRowNos(pnlList, sBillType, sRowNOKey, 1);

	}

	/**
	 * �������N��ʱ���Զ��������������е��кš�
	 * <p>
	 * <strong>����ģ�飺SCM����ģ�顣</strong>
	 * <p>
	 * <strong>����޸��ˣ���ӡ��</strong>
	 * <p>
	 * <strong>����޸���ǰ��2006-7-4</strong>
	 * <p>
	 * <strong>����������</strong>
	 * <p>
	 * 
	 * @param BillCardPanel
	 *            pnlCard ����ģ��
	 * @param String
	 *            sBillType ��������
	 * @param String
	 *            sRowNOKey �к�ITEMKEY
	 * @param int iSetCount ����������������5->9����ֵΪ4
	 * @return void
	 * @throws ��
	 * @since
	 * @see
	 */
	public static void addLineRowNos(BillCardPanel pnlCard, String sBillType,
			String sRowNOKey, int iSetCount) {

		int iTotalCount = pnlCard.getBillTable().getRowCount();

		int[] iaGivenRow = new int[iSetCount];
		int iPreRow = iTotalCount - iSetCount - 1;
		for (int i = 0; i < iSetCount; i++) {
			iaGivenRow[i] = iPreRow + i + 1;
		}
		addLineRowNos(pnlCard, sBillType, sRowNOKey, iPreRow, iaGivenRow);

	}

	/**
	 * ���к��Զ���������е��кš���ʹ����ָ��ȡ����һ�кŵ��С�����Щ�������кš�
	 * <p>
	 * <strong>����ģ�飺SCM����ģ�顣</strong>
	 * <p>
	 * <strong>����޸��ˣ���ӡ��</strong>
	 * <p>
	 * <strong>����޸���ǰ��2006-7-4</strong>
	 * <p>
	 * <strong>����������</strong>
	 * <p>
	 * 
	 * @param BillCardPanel
	 *            pnlCard ����ģ��
	 * @param String
	 *            sBillType ��������
	 * @param String
	 *            sRowNOKey �к�ITEMKEY
	 * @param int iGivenPreRow ʹ����ָ����ȡ��һ���кŵ���
	 * @param int[] iaGivenRow ʹ����ָ�����������кŵ���
	 * @return void
	 * @throws ��
	 * @since V5.0
	 * @see
	 */
	public static void addLineRowNos(BillCardPanel pnlList, String sBillType,
			String sRowNOKey, int iGivenPreRow, int[] iaGivenSetRow) {

		// ������ȷ���ж�
		if (pnlList == null || sBillType == null || sRowNOKey == null
				|| pnlList.getBodyItem(sRowNOKey) == null
				|| iaGivenSetRow == null) {
			System.err.print("�кŹ������������������ȷ�����飡");
			return;
		}

		// �ȶ������õ����кŽ�����գ�����õ�������к��Ǵ����
		int iSetLen = iaGivenSetRow.length;
		for (int i = 0; i < iSetLen; i++) {
			pnlList.getBillModel()
					.setValueAt(null, iaGivenSetRow[i], sRowNOKey);
		}

		// int iPreviousRow = iGivenPreRow ;
		UFDouble dPreviousRowNO = getRowNoUFDoubleMax(pnlList, sRowNOKey);
		for (int i = 0; i < iSetLen; i++) {
			// �����кţ����һ���кţ�����
			UFDouble dCurRowNO = dPreviousRowNO.add(m_rowNoStart);
			// ����ֵ
			pnlList.getBillModel().setValueAt(getCorrectString(dCurRowNO),
					iaGivenSetRow[i], sRowNOKey);

			dPreviousRowNO = dCurRowNO;
		}
	}

	/**
	 * ��������:�õ������к�
	 * 
	 * @param: BillCardPanel pnlCard ����ģ��
	 * @param: String sRowNoKey �к�ITEMKEY
	 * @return:��ǰ���������к�
	 * @serialData: 2006-10-18
	 */
	private static UFDouble getRowNoUFDoubleMax(BillCardPanel pnlCard,
			String sRowNOKey) {

		// ����ֻ�д���
		int nRow = pnlCard.getBillTable().getRowCount();
		if (nRow == 1) {
			return ZERO_UFDOUBLE;
		}

		UFDouble dMaxValue = ZERO_UFDOUBLE;
		UFDouble dEveryValue = null;
		for (int i = 0; i < nRow; i++) {
			dEveryValue = getRowNoUFDoubleAt(pnlCard, sRowNOKey, i);
			if (dMaxValue.compareTo(dEveryValue) < 0) {
				dMaxValue = dEveryValue;
			}
		}

		return dMaxValue;
	}

	/**
	 * ���ߣ���ӡ�� ���ܣ��õ�ĳ�е��кš������ؿ� �����������0.0�� 1�������ǰ���ڵ�һ��ǰ; ������ BillCardPanel pnlCard
	 * ��ǰ����ģ�� int iRow �� String sRowNoKey �к�ITEMKEY ���أ��� ���⣺�� ���ڣ�(2003-4-8
	 * 11:39:21) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	private static UFDouble getRowNoUFDoubleAt(BillCardPanel pnlCard,
			String sRowNoKey, int iRow) {

		if (iRow < 0) {
			return ZERO_UFDOUBLE;
		}

		Object oValue = pnlCard.getBillModel().getValueAt(iRow, sRowNoKey);
		return getUFDouble(oValue);
	}

	public static UFDouble getUFDouble(Object oValue) {

		if (oValue == null || oValue.toString().trim().equals("")) {
			return ZERO_UFDOUBLE;
		} else if (oValue instanceof UFDouble) {
			return (UFDouble) oValue;
		} else {
			return new UFDouble(oValue.toString().trim());
		}
	}

	private static UFDouble getRowNoUFDoubleLessThan(BillCardPanel pnlCard,
			String sRowNOKey, int iRow) {

		// ����ֻ�д���
		int nRow = pnlCard.getBillTable().getRowCount();
		if (nRow == 1) {
			return ZERO_UFDOUBLE;
		}

		// ���Ƚ��к�
		UFDouble dKnownRowNo = getRowNoUFDoubleAt(pnlCard, sRowNOKey, iRow);
		// ��¼���ñȵ�ǰ�к�С���к�
		UFDouble dLessFinal = ZERO_UFDOUBLE;

		UFDouble dTemp = null;

		for (int i = 0; i < nRow; i++) {
			dTemp = getRowNoUFDoubleAt(pnlCard, sRowNOKey, i);
			if (dTemp.compareTo(dKnownRowNo) < 0
					&& dTemp.compareTo(dLessFinal) > 0) {
				dLessFinal = dTemp;
			}
		}

		if (dLessFinal.compareTo(dKnownRowNo) == 0) {
			dLessFinal = ZERO_UFDOUBLE;
		}

		// ��������к�
		return dLessFinal;
	}

	public static void insertLineRowNos(BillCardPanel pnlCard,
			String sBillType, String sRowNOKey, int iEndRow, int iSetCount) {

		int iPreviousRow = iEndRow - 1 - iSetCount;
		int[] iaGivenSetRow = new int[iSetCount];
		for (int i = 0; i < iSetCount; i++) {
			iaGivenSetRow[i] = iPreviousRow + 1 + i;
		}

		insertLineRowNos(pnlCard, sBillType, sRowNOKey, iEndRow, iaGivenSetRow);

	}

	public static void insertLineRowNos(BillCardPanel pnlCard,
			String sBillType, String sRowNOKey, int iNextRow,
			int[] iaGivenSetRow) {

		// ������ȷ���ж�
		if (pnlCard == null || sBillType == null || sRowNOKey == null
				|| pnlCard.getBodyItem(sRowNOKey) == null
				|| iaGivenSetRow == null) {
			System.err.print("�кŹ������������������ȷ�����飡");
			return;
		}

		// �õ���ʼ�С��������к�
		// ��ʼ�кţ���ճ�����к���С���У������кţ�ճ����
		UFDouble dPreviousRowNO = getRowNoUFDoubleLessThan(pnlCard, sRowNOKey,
				iNextRow);
		UFDouble dNextRowNO = getRowNoUFDoubleAt(pnlCard, sRowNOKey, iNextRow);

		// �������������кŵ�����
		int iSetLen = iaGivenSetRow.length;
		UFDouble[] uaRowNo = new UFDouble[iSetLen];

		// ������ĩ�к���ȵ����
		if (dPreviousRowNO.compareTo(dNextRowNO) == 0) {
			for (int i = 0; i < iSetLen; i++) {
				uaRowNo[i] = dPreviousRowNO;
			}
		} else {
			// ȡ��ĩ��֮���С��λ��
			// int iDgt = Math.min(
			// getPower(dPreviousRowNO),getPower(dNextRowNO) );
			// ����,��ȡ��Сλ��
			UFDouble dStep = (dNextRowNO.sub(dPreviousRowNO)).div(iSetLen + 1);
			for (int i = 0; i < iSetLen; i++) {
				uaRowNo[i] = dPreviousRowNO.add(dStep.multiply(i + 1.));
			}

			// ����Ԫ�رȽϣ�������ظ����������λ
			int iStepDgt = DIGIT_POWER;
			UFDouble Zero = new UFDouble(0.0);

			UFDouble[] uaRowNoDgt = new UFDouble[iSetLen];
			for (int i = 0; i < iSetLen; i++) {
				uaRowNoDgt[i] = uaRowNo[i];
			}
			while (true) {
				// ����ʱ���鸳ֵ
				HashMap hmapRowNo = new HashMap();
				for (int i = 0; i < iSetLen; i++) {
					// ������Χ
					if ((uaRowNoDgt[i].compareTo(dPreviousRowNO) <= 0 && uaRowNoDgt[i]
							.compareTo(dNextRowNO) <= 0)
							|| (uaRowNoDgt[i].compareTo(dPreviousRowNO) >= 0 && uaRowNoDgt[i]
									.compareTo(dNextRowNO) >= 0)
							|| uaRowNoDgt[i].compareTo(Zero) == 0) {
						break;
					}
					hmapRowNo.put(getCorrectString(uaRowNoDgt[i]), null);
				}
				hmapRowNo.put(getCorrectString(dPreviousRowNO), null);
				hmapRowNo.put(getCorrectString(dNextRowNO), null);
				if (hmapRowNo.size() != iSetLen + 2) {
					break;
				} else {
					for (int i = 0; i < iSetLen; i++) {
						uaRowNo[i] = uaRowNoDgt[i];
					}
					if (iStepDgt <= 0) {
						break;
					}
				}

				// ���²����Ԫ����λ
				--iStepDgt;
				for (int i = 0; i < iSetLen; i++) {
					uaRowNoDgt[i] = uaRowNoDgt[i].setScale(iStepDgt,
							UFDouble.ROUND_DOWN);
				}
			}
		}
		// ����������к�
		for (int i = 0; i < iSetLen; i++) {
			// ����ֵ
			pnlCard.getBillModel().setValueAt(getCorrectString(uaRowNo[i]),
					iaGivenSetRow[i], sRowNOKey);
		}

	}
}
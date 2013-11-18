package nc.vo.zmpub.pub.report;

import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;

public class VOConvert {
	/**
	 * CircularlyAccessibleValueObject����תΪSuperVO���� ����������ǰ���� ���ߣ�chengjun
	 * �������ڣ�2006-4-20 13:43:05
	 * 
	 * @param cirVOs
	 * @return
	 */
	public static SuperVO[] circularlyVOToSuperVO(
			CircularlyAccessibleValueObject[] cirVOs) {

		if (cirVOs == null && cirVOs.length == 0)
			return null;

		String[] cirAttrNames = cirVOs[0].getAttributeNames();
		ReportTreeGatherVO[] supVOs = new ReportTreeGatherVO[cirVOs.length];
		for (int i = 0; i < cirVOs.length; i++) {
			supVOs[i] = new ReportTreeGatherVO();
			for (int j = 0; j < cirAttrNames.length; j++) {
				supVOs[i].setAttributeValue(cirAttrNames[j], cirVOs[i]
						.getAttributeValue(cirAttrNames[j]));
			}
		}

		return supVOs;
	}

	/**
	 * SuperVOתΪCircularlyAccessibleValueObject ���ߣ�chengjun �������ڣ�2006-4-20
	 * 13:43:08
	 * 
	 * @param supVO
	 * @return
	 */
	public static CircularlyAccessibleValueObject superVOTocircularlyVO(
			SuperVO supVO) {

		if (supVO == null)
			return null;

		String[] supAttrNames = supVO.getAttributeNames();
		ReportBaseVO cirVO = new ReportBaseVO();
		for (int j = 0; j < supAttrNames.length; j++) {
			cirVO.setAttributeValue(supAttrNames[j], supVO
					.getAttributeValue(supAttrNames[j]));
		}

		return cirVO;
	}

	/**
	 * SuperVOCircularlyVOConvertTool ������ע��
	 * 
	 */
	public VOConvert() {
		super();
	}
}

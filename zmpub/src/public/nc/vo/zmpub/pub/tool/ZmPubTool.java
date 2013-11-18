package nc.vo.zmpub.pub.tool;

import nc.vo.pub.BusinessException;

/**
 * �ù��������ȴӻ�����ȡ��,���������û�вŲ�ѯ���ݿ� ��������˲�ѯЧ��
 * 
 * @author zhf
 */
public class ZmPubTool {
	public static final Integer INTEGER_ZERO_VALUE = new Integer(0); // ������
	private static nc.bs.pub.formulaparse.FormulaParse fp = new nc.bs.pub.formulaparse.FormulaParse();
	/**
	 * 
	 * @author zhf
	 * @˵�������׸ڿ�ҵ����Ϊnull���ַ�������Ϊ������ 2010-11-22����02:51:02
	 * @param value
	 * @return
	 */
	public static String getString_NullAsTrimZeroLen(Object value) {
		if (value == null) {
			return "";
		}
		return value.toString().trim();
	}
	/**
	 * ���ں�̨
	 * 
	 * @param fomular
	 *            ִ�еĹ�ʽ
	 *            ���ѯ�ֿ����Ĺ�ʽ��storcode->getColValue(bd_strodoc,storcode,pk_stordoc
	 *            ,storid)
	 * @param names
	 *            ����ֵ������ �磺new String[]{"storid"}
	 * @param values
	 *            ����ֵ ��:new String[]{"0001AE10000000018ES9"}
	 * @return
	 * @throws BusinessException
	 */
	public static final Object execFomular(String fomular, String[] names,
			String[] values) throws BusinessException {
		fp.setExpress(fomular);
		if (names.length != values.length) {
			throw new BusinessException("��������쳣");
		}
		int index = 0;
		for (String name : names) {
			fp.addVariable(name, values[index]);
			index++;
		}
		return fp.getValue();
	}

	private static nc.ui.pub.formulaparse.FormulaParse fpClient = new nc.ui.pub.formulaparse.FormulaParse();

	/**
	 * ����ǰ̨
	 * 
	 * @param fomular
	 *            ִ�еĹ�ʽ
	 *            ���ѯ�ֿ����Ĺ�ʽ��storcode->getColValue(bd_strodoc,storcode,pk_stordoc
	 *            ,storid)
	 * @param names
	 *            ����ֵ������ �磺new String[]{"storid"}
	 * @param values
	 *            ����ֵ ��:new String[]{"0001AE10000000018ES9"}
	 * @return
	 * @throws BusinessException
	 */
	public static final Object execFomularClient(String fomular,
			String[] names, String[] values) throws BusinessException {
		fpClient.setExpress(fomular);
		if (names.length != values.length) {
			throw new BusinessException("��������쳣");
		}
		int index = 0;
		for (String name : names) {
			fpClient.addVariable(name, values[index]);
			index++;
		}
		return fpClient.getValue();
	}
}

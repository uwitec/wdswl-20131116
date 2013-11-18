package nc.vo.zmpub.pub.report;

/**
 * �˴���������˵���� �������ڣ�(2004-8-18 8:45:57)
 * 
 * @author��������
 */
public class SubtotalVO extends nc.vo.pub.ValueObject {
	private String[] m_groupFlds = null; // �����С�
	private String[] m_valueFlds = null; // ��ֵ�С�
	private int[] m_valueTypes = null; // ��ֵ���������͡�
	private boolean m_bGroupFldCanNull = false; // �����е������Ƿ����Ϊ�ա�
	private boolean[] m_bAsLeafRs = null; // �����кϲ����Ƿ���Ϊĩ���ڵ��¼��
	private String[] m_subDescOnFlds = null; // С������������
	private String m_totalDescOnFld = null; // �ϼ�������
	private String[] m_orderbyFlds = null; // �����С�
	private boolean m_bDesc = true; // ������
	private int m_iGroupDepth = -1;// �������ȡ�

	/**
	 * SubtotalVO ������ע�⡣
	 */
	public SubtotalVO() {
		super();
	}

	/**
	 * ������ֵ�������ʾ���ơ�
	 * 
	 * �������ڣ�(2001-2-15 14:18:08)
	 * 
	 * @return java.lang.String ������ֵ�������ʾ���ơ�
	 */
	public String getEntityName() {
		return null;
	}

	public int getGroupDepth() {
		return m_iGroupDepth;
	}

	public String[] getGroupFlds() {
		return m_groupFlds;
	}

	public String[] getOrderbyFlds() {
		return m_orderbyFlds;
	}

	public String[] getSubDescOnFlds() {
		return m_subDescOnFlds;
	}

	public String getTotalDescOnFld() {
		return m_totalDescOnFld;
	}

	public String[] getValueFlds() {
		return m_valueFlds;
	}

	public int[] getValueFldTypes() {
		return m_valueTypes;
	}

	public boolean[] isAsLeafRs() {
		return m_bAsLeafRs;
	}

	public boolean isDesc() {
		return m_bDesc;
	}

	public boolean isGroupFldCanNUll() {
		return m_bGroupFldCanNull;
	}

	public void setAsLeafRs(boolean[] b) {
		m_bAsLeafRs = b;
	}

	public void setGroupDepth(int iDepth) {
		m_iGroupDepth = iDepth;
	}

	public void setGroupFldCanNUll(boolean b) {
		m_bGroupFldCanNull = b;
	}

	public void setGroupFlds(String[] flds) {
		m_groupFlds = flds;
	}

	public void setOrderbyFlds(String[] flds) {
		m_orderbyFlds = flds;
	}

	public void setSortDesc(boolean b) {
		m_bDesc = b;
	}

	public void setSubDescOnFlds(String[] flds) {
		m_subDescOnFlds = flds;
	}

	public void setTotalDescOnFld(String fld) {
		m_totalDescOnFld = fld;
	}

	public void setValueFlds(String[] flds) {
		m_valueFlds = flds;
	}

	public void setValueFldTypes(int[] itype) {
		m_valueTypes = itype;
	}

	/**
	 * ��֤���������֮��������߼���ȷ�ԡ�
	 * 
	 * �������ڣ�(2001-2-15 11:47:35)
	 * 
	 * @exception nc.vo.pub.ValidationException
	 *                �����֤ʧ�ܣ��׳� ValidationException���Դ�����н��͡�
	 */
	public void validate() throws nc.vo.pub.ValidationException {
		if (getValueFldTypes() == null)
			throw new nc.vo.pub.ValidationException(
					"����С�ƺϼ�ʱ��SubtotalVO����ֵ�е����Ͷ��岻��Ϊ��!");
		if (getValueFlds() == null)
			throw new nc.vo.pub.ValidationException(
					"����С�ƺϼ�ʱ��SubtotalVO��ֵ�еĶ��岻��Ϊ��!");
		// if (isAsLeafRs() == null)
		// throw new
		// nc.vo.pub.ValidationException("����С�ƺϼ�ʱ��SubtotalVO�����з���ϲ����Ƿ�Ϊԭʼ�ж��岻��Ϊ��!");
		if (getValueFlds().length != getValueFldTypes().length)
			throw new nc.vo.pub.ValidationException(
					"����С�ƺϼ�ʱ��SubtotalVO����ֵ�е����Ͷ��岻ȫ!");
		// if (getGroupFlds() != null && getGroupFlds().length !=
		// isAsLeafRs().length)
		// throw new
		// nc.vo.pub.ValidationException("����С�ƺϼ�ʱ��SubtotalVO�����з���ϲ����Ƿ�Ϊԭʼ�ж��岻ȫ!");
		// if (getGroupFlds() != null && getGroupFlds().length !=
		// getSubDescOnFlds().length)
		// throw new
		// nc.vo.pub.ValidationException("����С�ƺϼ�ʱ��SubtotalVO�����з���ϲ���С�ƺϼ�д����һ�ж��岻ȫ!");
	}
}

package nc.vo.zmpub.pub.report;

/**
 * �������ڣ�(2004-1-15 14:01:18)
 * 
 * @author��������
 */
public class SumInfoVO {
	private String[] m_strFields = null;
	private int[] m_iTypes = null;
	private String m_strSubTotalFld = null;
	private String m_strTotalFld = null;

	/**
	 * SumInfoVO ������ע�⡣
	 */
	public SumInfoVO() {
		super();
	}

	public String[] getFields() {
		return m_strFields;
	}

	public String getSubtotalFld() {
		return m_strSubTotalFld;
	}

	public String getTotalFld() {
		return m_strTotalFld;
	}

	public int[] getTypes() {
		return m_iTypes;
	}

	public void setFields(String[] strFlds) {
		m_strFields = strFlds;
	}

	public void setSubtotalFld(String strFld) {
		m_strSubTotalFld = strFld;
	}

	public void setTotalFld(String strFld) {
		m_strTotalFld = strFld;
	}

	public void setTypes(int[] iTypes) {
		m_iTypes = iTypes;
	}
}

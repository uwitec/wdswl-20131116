package nc.vo.zmpub.pub.report;

/**
 * �������ڣ�(2004-5-22 8:58:05)
 * 
 * @author�������� ��ѯ����VO.
 */
public class QueryVO extends nc.vo.pub.ValueObject {

	// ѡ���ֶ�.
	private String[] selectflds = null;
	// from clause.
	private String fromclause = null;
	// ��ѯ����.
	private String whereString = null;
	// �����ֶ�.
	private String[] groupflds = null;
	// �����ֶ�.
	private String[] orderFlds = null;
	// Ϊ�˷�����չ�����Ӹ��ӵ��ֶΡ�
	private java.util.Hashtable m_hMsg = new java.util.Hashtable();

	/**
	 * QueryVO ������ע�⡣
	 */
	public QueryVO() {
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

	public String getFromClause() {
		return fromclause;
	}

	public String[] getGroupFlds() {
		return groupflds;
	}

	public Object getMsg(String key) {
		return m_hMsg.get(key);
	}

	public String[] getOrderFlds() {
		return orderFlds;
	}

	public String[] getSelectFlds() {
		return selectflds;
	}

	public String getWhereClause() {
		return whereString;
	}

	public void setFromClause(String from) {
		fromclause = from;
	}

	public void setGourpFlds(String[] flds) {
		groupflds = flds;
	}

	public void setMsg(String key, Object o) {
		if (o != null)
			m_hMsg.put(key, o);
	}

	public void setOrderFlds(String[] orderfld) {
		orderFlds = orderfld;
	}

	public void setSelectFlds(String[] flds) {
		selectflds = flds;
	}

	public void setWhereString(String where) {
		whereString = where;
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
	}
}

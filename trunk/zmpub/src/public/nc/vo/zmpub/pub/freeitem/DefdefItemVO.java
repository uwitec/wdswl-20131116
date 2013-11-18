package nc.vo.zmpub.pub.freeitem;

import java.util.ArrayList;

import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.FieldObject;
import nc.vo.pub.IntegerField;
import nc.vo.pub.NullFieldException;
import nc.vo.pub.StringField;
import nc.vo.pub.UFDateTimeField;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFDateTime;

/**
 * �˴���������˵����
 * 
 * �������ڣ�(2001-11-8)
 * 
 * @author��������
 */
public class DefdefItemVO extends CircularlyAccessibleValueObject {

	public String m_pk_defdoc;

	public String m_pk_defdoc1;
	public String m_doccode;
	public String m_docname;
	public UFDateTime m_ts;
	public Integer m_dr;

	/**
	 * �����������Ե�FieldObjects����Ҫ����ϵͳ�����У� ҵ������в����õ������FieldObjects��
	 */
	private static StringField m_pk_defdocField;
	private static StringField m_pk_defdefField;
	private static StringField m_pk_defdoc1Field;
	private static StringField m_doccodeField;
	private static StringField m_docnameField;
	private static UFDateTimeField m_tsField;
	private static IntegerField m_drField;

	/**
	 * ʹ�������ֶν��г�ʼ���Ĺ����ӡ�
	 * 
	 * �������ڣ�(2001-11-8)
	 */
	public DefdefItemVO() {

	}

	/**
	 * ʹ���������г�ʼ���Ĺ����ӡ�
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param ??fieldNameForMethod?? ����ֵ
	 */
	public DefdefItemVO(String newPk_defdoc) {

		// Ϊ�����ֶθ�ֵ:
		m_pk_defdoc = newPk_defdoc;
	}

	/**
	 * ����Object�ķ���,��¡���VO����
	 * 
	 * �������ڣ�(2001-11-8)
	 */
	public Object clone() {

		// ���ƻ������ݲ������µ�VO����
		Object o = null;
		try {
			o = super.clone();
		} catch (Exception e) {
		}
		DefdefItemVO defdoc = (DefdefItemVO) o;

		// �������渴�Ʊ�VO������������ԣ�

		return defdoc;
	}

	/**
	 * ������ֵ�������ʾ���ơ�
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return java.lang.String ������ֵ�������ʾ���ơ�
	 */
	public String getEntityName() {

		return "Defdoc";
	}

	/**
	 * ���ض����ʶ������Ψһ��λ����
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return String
	 */
	public String getPrimaryKey() {

		return m_pk_defdoc;
	}

	/**
	 * ���ö����ʶ������Ψһ��λ����
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param m_pk_defdoc
	 *            String
	 */
	public void setPrimaryKey(String newPk_defdoc) {

		m_pk_defdoc = newPk_defdoc;
	}

	/**
	 * ����m_pk_defdoc��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return String
	 */
	public String getPk_defdoc() {
		return m_pk_defdoc;
	}

	/**
	 * ����m_pk_defdoc1��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return String
	 */
	public String getPk_defdoc1() {
		return m_pk_defdoc1;
	}

	/**
	 * ����m_doccode��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return String
	 */
	public String getDoccode() {
		return m_doccode;
	}

	/**
	 * ����m_docname��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return String
	 */
	public String getDocname() {
		return m_docname;
	}

	/**
	 * ����m_ts��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return UFDateTime
	 */
	public UFDateTime getTs() {
		return m_ts;
	}

	/**
	 * ����m_dr��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return Integer
	 */
	public Integer getDr() {
		return m_dr;
	}

	/**
	 * ����m_pk_defdoc��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_pk_defdoc
	 *            String
	 */
	public void setPk_defdoc(String newPk_defdoc) {

		m_pk_defdoc = newPk_defdoc;
	}

	/**
	 * ����m_pk_defdoc1��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_pk_defdoc1
	 *            String
	 */
	public void setPk_defdoc1(String newPk_defdoc1) {

		m_pk_defdoc1 = newPk_defdoc1;
	}

	/**
	 * ����m_doccode��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_doccode
	 *            String
	 */
	public void setDoccode(String newDoccode) {

		m_doccode = newDoccode;
	}

	/**
	 * ����m_docname��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_docname
	 *            String
	 */
	public void setDocname(String newDocname) {

		m_docname = newDocname;
	}

	/**
	 * ����m_ts��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_ts
	 *            UFDateTime
	 */
	public void setTs(UFDateTime newTs) {

		m_ts = newTs;
	}

	/**
	 * ����m_dr��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_dr
	 *            Integer
	 */
	public void setDr(Integer newDr) {

		m_dr = newDr;
	}

	/**
	 * ��֤���������֮��������߼���ȷ�ԡ�
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @exception nc.vo.pub.ValidationException
	 *                �����֤ʧ�ܣ��׳� ValidationException���Դ�����н��͡�
	 */
	public void validate() throws ValidationException {

		ArrayList errFields = new ArrayList(); // errFields record those null
												// fields that cannot be null.
		// ����Ƿ�Ϊ������յ��ֶθ��˿�ֵ���������Ҫ�޸��������ʾ��Ϣ��
		if (m_pk_defdoc == null) {
			errFields.add(new String("m_pk_defdoc"));
		}
		if (pk_defdoclist == null) {
			errFields.add(new String("pk_defdoclist"));
		}
		if (m_doccode == null) {
			errFields.add(new String("m_doccode"));
		}
		if (m_docname == null) {
			errFields.add(new String("m_docname"));
		}
		// construct the exception message:
		StringBuffer message = new StringBuffer();
		message.append("�����ֶβ���Ϊ�գ�");
		if (errFields.size() > 0) {
			String[] temp = (String[]) errFields.toArray(new String[0]);
			message.append(temp[0]);
			for (int i = 1; i < temp.length; i++) {
				message.append("��");
				message.append(temp[i]);
			}
			// throw the exception:
			throw new NullFieldException(message.toString());
		}
	}

	/**
	 * <p>
	 * ��Ҫ��һ��ѭ���з��ʵ����Ե��������顣
	 * <p>
	 * �������ڣ�(??Date??)
	 * 
	 * @return java.lang.String[]
	 */
	public java.lang.String[] getAttributeNames() {

		return new String[] { "docsystype", "pk_defdoc1", "doccode", "docname",
				"ts", "dr", "pk_defdoclist" };
	}

	/**
	 * <p>
	 * ����һ�����������ַ��������Ե�ֵ��
	 * <p>
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param key
	 *            java.lang.String
	 */
	public Object getAttributeValue(String attributeName) {

		if (attributeName.equals("pk_defdoc")) {
			return m_pk_defdoc;
		} else if (attributeName.equals("pk_defdoclist")) {
			return pk_defdoclist;
		} else if (attributeName.equals("pk_defdoc1")) {
			return m_pk_defdoc1;
		} else if (attributeName.equals("doccode")) {
			return m_doccode;
		} else if (attributeName.equals("docname")) {
			return m_docname;
		} else if (attributeName.equals("ts")) {
			return m_ts;
		} else if (attributeName.equals("dr")) {
			return m_dr;
		} else if (attributeName.equals("docsystype")) {
			return m_docsystype;
		}
		return null;
	}

	/**
	 * <p>
	 * �Բ���name���͵���������ֵ��
	 * <p>
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param key
	 *            java.lang.String
	 */
	public void setAttributeValue(String name, Object value) {

		try {
			if (name.equals("pk_defdoc")) {
				m_pk_defdoc = (String) value;
			} else if (name.equals("pk_defdoclist")) {
				pk_defdoclist = (String) value;
			} else if (name.equals("pk_defdoc1")) {
				m_pk_defdoc1 = (String) value;
			} else if (name.equals("doccode")) {
				m_doccode = (String) value;
			} else if (name.equals("docname")) {
				m_docname = (String) value;
			} else if (name.equals("ts")) {
				m_ts = switchObjToUFDateTime(value);
			} else if (name.equals("dr")) {
				m_dr = switchObjToInteger(value);
			} else if (name.equals("docsystype")) {
				m_docsystype = switchObjToInteger(value);
			}
		} catch (ClassCastException e) {
			throw new ClassCastException("setAttributeValue������Ϊ " + name
					+ " ��ֵʱ����ת�����󣡣�ֵ��" + value + "��");
		}
	}

	public UFDateTime switchObjToUFDateTime(Object param) {
		UFDateTime ufdatetime = null;
		if (param != null) {
			if (param instanceof UFDateTime)
				ufdatetime = (UFDateTime) param;
			else {
				String sTrimValue = param.toString().trim();
				if (sTrimValue.length() > 0) {
					try {
						ufdatetime = new UFDateTime(sTrimValue, false);
					} catch (Exception e) {
					}
				}
			}
		}
		return ufdatetime;
	}

	public Integer switchObjToInteger(Object param) {
		Integer integer = null;
		if (param != null) {
			if (param instanceof Integer)
				integer = (Integer) param;
			else {
				String sTrimValue = param.toString().trim();
				if (sTrimValue.length() > 0) {
					try {
						integer = new Integer(sTrimValue);
					} catch (Exception e) {
					}
				}
			}
		}
		return integer;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getPk_defdocField() {

		if (m_pk_defdocField == null) {
			try {
				m_pk_defdocField = new StringField();
				// ���Ե�����
				m_pk_defdocField.setName("pk_defdoc");
				// ���Ե�����
				m_pk_defdocField.setLabel("null");
				// ����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_pk_defdocField;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getPk_defdefField() {

		if (m_pk_defdefField == null) {
			try {
				m_pk_defdefField = new StringField();
				// ���Ե�����
				m_pk_defdefField.setName("pk_defdef");
				// ���Ե�����
				m_pk_defdefField.setLabel("null");
				// ����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_pk_defdefField;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getPk_defdoc1Field() {

		if (m_pk_defdoc1Field == null) {
			try {
				m_pk_defdoc1Field = new StringField();
				// ���Ե�����
				m_pk_defdoc1Field.setName("pk_defdoc1");
				// ���Ե�����
				m_pk_defdoc1Field.setLabel("null");
				// ����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_pk_defdoc1Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getDoccodeField() {

		if (m_doccodeField == null) {
			try {
				m_doccodeField = new StringField();
				// ���Ե�����
				m_doccodeField.setName("doccode");
				// ���Ե�����
				m_doccodeField.setLabel("null");
				// ����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_doccodeField;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getDocnameField() {

		if (m_docnameField == null) {
			try {
				m_docnameField = new StringField();
				// ���Ե�����
				m_docnameField.setName("docname");
				// ���Ե�����
				m_docnameField.setLabel("null");
				// ����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_docnameField;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static UFDateTimeField getTsField() {

		if (m_tsField == null) {
			try {
				m_tsField = new UFDateTimeField();
				// ���Ե�����
				m_tsField.setName("ts");
				// ���Ե�����
				m_tsField.setLabel("null");
				// ����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_tsField;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static IntegerField getDrField() {

		if (m_drField == null) {
			try {
				m_drField = new IntegerField();
				// ���Ե�����
				m_drField.setName("dr");
				// ���Ե�����
				m_drField.setLabel("null");
				// ����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_drField;
	}

	/**
	 * �������ValueObject�������FieldObject����ļ��ϡ�
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.FieldObject[]
	 */
	public FieldObject[] getFields() {

		FieldObject[] fields = { getPk_defdocField(), getPk_defdefField(),
				getPk_defdoc1Field(), getDoccodeField(), getDocnameField(),
				getTsField(), getDrField() };

		return fields;
	}

	public Integer m_docsystype;
	public String pk_defdoclist;

	/**
	 * ����m_dr��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return Integer
	 */
	public Integer getDocsystype() {
		return m_docsystype;
	}

	/**
	 * ����m_pk_defdef��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return String
	 */
	public String getPk_defdoclist() {
		return pk_defdoclist;
	}

	/**
	 * ����m_pk_defdef��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_pk_defdef
	 *            String
	 */
	public void setDocsystype(Integer newPk_defdef) {

		m_docsystype = newPk_defdef;
	}

	/**
	 * ����m_pk_defdef��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_pk_defdef
	 *            String
	 */
	public void setPk_defdoclist(String newPk_defdef) {

		pk_defdoclist = newPk_defdef;
	}
}
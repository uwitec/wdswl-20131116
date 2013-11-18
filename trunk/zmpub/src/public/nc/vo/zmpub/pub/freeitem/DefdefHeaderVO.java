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
public class DefdefHeaderVO extends CircularlyAccessibleValueObject {

	public String m_pk_defdef;
	public String m_defcode;
	public String m_defname;

	public String m_type;
	public Integer m_lengthnum;
	public Integer m_digitnum;
	public UFDateTime m_ts;
	public Integer m_dr;

	/**
	 * �����������Ե�FieldObjects����Ҫ����ϵͳ�����У� ҵ������в����õ������FieldObjects��
	 */
	private static StringField m_pk_defdefField;
	private static StringField m_defcodeField;
	private static StringField m_defnameField;

	private static StringField m_typeField;
	private static IntegerField m_lengthnumField;
	private static IntegerField m_digitnumField;
	private static UFDateTimeField m_tsField;
	private static IntegerField m_drField;

	/**
	 * ʹ�������ֶν��г�ʼ���Ĺ����ӡ�
	 * 
	 * �������ڣ�(2001-11-8)
	 */
	public DefdefHeaderVO() {

	}

	/**
	 * ʹ���������г�ʼ���Ĺ����ӡ�
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param ??fieldNameForMethod?? ����ֵ
	 */
	public DefdefHeaderVO(String newPk_defdef) {

		// Ϊ�����ֶθ�ֵ:
		m_pk_defdef = newPk_defdef;
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
		DefdefHeaderVO defdef = (DefdefHeaderVO) o;

		// �������渴�Ʊ�VO������������ԣ�

		return defdef;
	}

	/**
	 * ������ֵ�������ʾ���ơ�
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return java.lang.String ������ֵ�������ʾ���ơ�
	 */
	public String getEntityName() {

		return "Defdef";
	}

	/**
	 * ���ض����ʶ������Ψһ��λ����
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return String
	 */
	public String getPrimaryKey() {

		return m_pk_defdef;
	}

	/**
	 * ���ö����ʶ������Ψһ��λ����
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param m_pk_defdef
	 *            String
	 */
	public void setPrimaryKey(String newPk_defdef) {

		m_pk_defdef = newPk_defdef;
	}

	/**
	 * ����m_pk_defdef��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return String
	 */
	public String getPk_defdef() {
		return m_pk_defdef;
	}

	/**
	 * ����m_defcode��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return String
	 */
	public String getDefcode() {
		return m_defcode;
	}

	/**
	 * ����m_defname��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return String
	 */
	public String getDefname() {
		return m_defname;
	}

	/**
	 * ����m_type��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return String
	 */
	public String getType() {
		return m_type;
	}

	/**
	 * ����m_lengthnum��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return Integer
	 */
	public Integer getLengthnum() {
		return m_lengthnum;
	}

	/**
	 * ����m_digitnum��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return Integer
	 */
	public Integer getDigitnum() {
		return m_digitnum;
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
	 * ����m_pk_defdef��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_pk_defdef
	 *            String
	 */
	public void setPk_defdef(String newPk_defdef) {

		m_pk_defdef = newPk_defdef;
	}

	/**
	 * ����m_defcode��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_defcode
	 *            String
	 */
	public void setDefcode(String newDefcode) {

		m_defcode = newDefcode;
	}

	/**
	 * ����m_defname��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_defname
	 *            String
	 */
	public void setDefname(String newDefname) {

		m_defname = newDefname;
	}

	/**
	 * ����m_type��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_type
	 *            String
	 */
	public void setType(String newType) {

		m_type = newType;
	}

	/**
	 * ����m_lengthnum��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_lengthnum
	 *            Integer
	 */
	public void setLengthnum(Integer newLengthnum) {

		m_lengthnum = newLengthnum;
	}

	/**
	 * ����m_digitnum��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_digitnum
	 *            Integer
	 */
	public void setDigitnum(Integer newDigitnum) {

		m_digitnum = newDigitnum;
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
		if (m_pk_defdef == null) {
			errFields.add(new String("m_pk_defdef"));
		}
		if (m_defcode == null) {
			errFields.add(new String("m_defcode"));
		}
		if (m_defname == null) {
			errFields.add(new String("m_defname"));
		}
		if (pk_defdoclist == null) {
			errFields.add(new String("pk_defdoclist"));
		}
		if (m_type == null) {
			errFields.add(new String("m_type"));
		}
		if (m_lengthnum == null) {
			errFields.add(new String("m_lengthnum"));
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

		return new String[] { "defcode", "defname", "pk_defdoclist", "type",
				"lengthnum", "digitnum", "ts", "dr" };
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

		if (attributeName.equals("pk_defdef")) {
			return m_pk_defdef;
		} else if (attributeName.equals("defcode")) {
			return m_defcode;
		} else if (attributeName.equals("defname")) {
			return m_defname;
		} else if (attributeName.equals("pk_defdoclist")) {
			return pk_defdoclist;
		} else if (attributeName.equals("type")) {
			return m_type;
		} else if (attributeName.equals("lengthnum")) {
			return m_lengthnum;
		} else if (attributeName.equals("digitnum")) {
			return m_digitnum;
		} else if (attributeName.equals("ts")) {
			return m_ts;
		} else if (attributeName.equals("dr")) {
			return m_dr;
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
			if (name.equals("pk_defdef")) {
				m_pk_defdef = (String) value;
			} else if (name.equals("defcode")) {
				m_defcode = (String) value;
			} else if (name.equals("defname")) {
				m_defname = (String) value;
			} else if (name.equals("pk_defdoclist")) {
				pk_defdoclist = (String) value;
			} else if (name.equals("type")) {
				m_type = (String) value;
			} else if (name.equals("lengthnum")) {
				m_lengthnum = switchObjToInteger(value);
			} else if (name.equals("digitnum")) {
				m_digitnum = switchObjToInteger(value);
			} else if (name.equals("ts")) {
				m_ts = switchObjToUFDateTime(value);
			} else if (name.equals("dr")) {
				m_dr = switchObjToInteger(value);
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
	public static StringField getDefcodeField() {

		if (m_defcodeField == null) {
			try {
				m_defcodeField = new StringField();
				// ���Ե�����
				m_defcodeField.setName("defcode");
				// ���Ե�����
				m_defcodeField.setLabel("null");
				// ����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_defcodeField;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getDefnameField() {

		if (m_defnameField == null) {
			try {
				m_defnameField = new StringField();
				// ���Ե�����
				m_defnameField.setName("defname");
				// ���Ե�����
				m_defnameField.setLabel("null");
				// ����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_defnameField;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getTypeField() {

		if (m_typeField == null) {
			try {
				m_typeField = new StringField();
				// ���Ե�����
				m_typeField.setName("type");
				// ���Ե�����
				m_typeField.setLabel("null");
				// ����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_typeField;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static IntegerField getLengthnumField() {

		if (m_lengthnumField == null) {
			try {
				m_lengthnumField = new IntegerField();
				// ���Ե�����
				m_lengthnumField.setName("lengthnum");
				// ���Ե�����
				m_lengthnumField.setLabel("null");
				// ����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_lengthnumField;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static IntegerField getDigitnumField() {

		if (m_digitnumField == null) {
			try {
				m_digitnumField = new IntegerField();
				// ���Ե�����
				m_digitnumField.setName("digitnum");
				// ���Ե�����
				m_digitnumField.setLabel("null");
				// ����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_digitnumField;
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

		FieldObject[] fields = { getPk_defdefField(), getDefcodeField(),
				getDefnameField(), getPk_defdoclistField(), getTypeField(),
				getLengthnumField(), getDigitnumField(), getTsField(),
				getDrField() };

		return fields;
	}

	public String pk_defdoclist;
	private static StringField pk_defdoclistField;

	/**
	 * ����m_docislevflag��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return UFBoolean
	 */
	public String getPk_defdoclist() {
		return pk_defdoclist;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getPk_defdoclistField() {

		if (pk_defdoclistField == null) {
			try {
				pk_defdoclistField = new StringField();
				// ���Ե�����
				pk_defdoclistField.setName("docislevflag");
				// ���Ե�����
				pk_defdoclistField.setLabel("null");
				// ����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return pk_defdoclistField;
	}

	/**
	 * ����m_docislevflag��setter������
	 * 
	 * �������ڣ�(2001-11-8)
	 * 
	 * @param newM_docislevflag
	 *            UFBoolean
	 */
	public void setPk_defdoclist(String newDocislevflag) {

		pk_defdoclist = newDocislevflag;
	}
}
/*
 * �������� 2005-1-24
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package nc.vo.zmpub.excel;

import nc.vo.pub.lang.UFBoolean;

/**
 * @author zhouxiao
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class UpLoadFileVO extends nc.vo.pub.CircularlyAccessibleValueObject {
	private String m_sSequence;//���к�
	private UFBoolean m_bSelect;//ѡ����
	private String m_sFileName;//�ļ�����
	private String m_sBillCode;//���ݺ�
	private String m_sFileDate;//�ļ�����
	private String m_sFileStatus;//�ļ�״̬
/**
 * UpLoadVO ������ע�⡣
 */
public UpLoadFileVO() {
	super();
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(01-3-20 17:26:03)
 * @return java.lang.String[]
 */
public java.lang.String[] getAttributeNames() {
	return null;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(01-3-20 17:24:29)
 * @param key java.lang.String
 */
public Object getAttributeValue(String attributeName) {
	if (attributeName.equals("sequence")) {
		return m_sSequence;
	} else if (attributeName.equals("select")) {
		return m_bSelect;
	} else if (attributeName.equals("filename")) {
		return m_sFileName;
	} else if (attributeName.equals("billcode")) {
		return m_sBillCode;
	}else if (attributeName.equals("filedate")) {
		return m_sFileDate;
	}else if (attributeName.equals("filestatus")) {
		return m_sFileStatus;
	}
	return null;
}
/**
 * ?user>
 * ���ܣ�
 * ������
 * ���أ�
 * ���⣺
 * ���ڣ�(2004-9-1 10:00:49)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * @return java.lang.String
 */
public java.lang.String getBillCode() {
	return m_sBillCode;
}
/**
 * ������ֵ�������ʾ���ơ�
 * 
 * �������ڣ�(2001-2-15 14:18:08)
 * @return java.lang.String ������ֵ�������ʾ���ơ�
 */
public String getEntityName() {
	return null;
}
/**
 * ?user>
 * ���ܣ�
 * ������
 * ���أ�
 * ���⣺
 * ���ڣ�(2004-9-1 10:00:49)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * @return java.lang.String
 */
public java.lang.String getFileDate() {
	return m_sFileDate;
}
/**
 * ?user>
 * ���ܣ�
 * ������
 * ���أ�
 * ���⣺
 * ���ڣ�(2004-9-1 10:00:49)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * @return java.lang.String
 */
public java.lang.String getFileName() {
	return m_sFileName;
}
/**
 * ?user>
 * ���ܣ�
 * ������
 * ���أ�
 * ���⣺
 * ���ڣ�(2004-9-1 10:00:49)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * @return java.lang.String
 */
public java.lang.String getFileStatus() {
	return m_sFileStatus;
}
/**
 * ?user>
 * ���ܣ�
 * ������
 * ���أ�
 * ���⣺
 * ���ڣ�(2004-9-1 10:00:49)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * @return nc.vo.pub.lang.UFBoolean
 */
public nc.vo.pub.lang.UFBoolean getSelect() {
	return m_bSelect;
}
/**
 * ?user>
 * ���ܣ�
 * ������
 * ���أ�
 * ���⣺
 * ���ڣ�(2004-9-1 10:00:49)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * @return java.lang.String
 */
public java.lang.String getSequence() {
	return m_sSequence;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(01-3-20 17:24:29)
 * @param key java.lang.String
 */
public void setAttributeValue(String name, Object value) {
	if (name.equals("sequence")) {
		m_sSequence = (String) value;
	} else if (name.equals("select")) {
		m_bSelect = (UFBoolean) value;
	} else if (name.equals("filename")) {
		m_sFileName = (String) value;
	} else if (name.equals("billcode")) {
		m_sBillCode = (String) value;
	}
	else if (name.equals("filedate")) {
		m_sFileDate = (String) value;
	}
	else if (name.equals("filestatus")) {
		m_sFileStatus = (String) value;
	}
}
/**
 * ?user>
 * ���ܣ�
 * ������
 * ���أ�
 * ���⣺
 * ���ڣ�(2004-9-1 10:00:49)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * @param newBillCode java.lang.String
 */
public void setBillCode(java.lang.String newBillCode) {
	m_sBillCode = newBillCode;
}
/**
 * ?user>
 * ���ܣ�
 * ������
 * ���أ�
 * ���⣺
 * ���ڣ�(2004-9-1 10:00:49)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * @param newFileDate java.lang.String
 */
public void setFileDate(java.lang.String newFileDate) {
	m_sFileDate = newFileDate;
}
/**
 * ?user>
 * ���ܣ�
 * ������
 * ���أ�
 * ���⣺
 * ���ڣ�(2004-9-1 10:00:49)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * @param newFileName java.lang.String
 */
public void setFileName(java.lang.String newFileName) {
	m_sFileName = newFileName;
}
/**
 * ?user>
 * ���ܣ�
 * ������
 * ���أ�
 * ���⣺
 * ���ڣ�(2004-9-1 10:00:49)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * @param newFileStatus java.lang.String
 */
public void setFileStatus(java.lang.String newFileStatus) {
	m_sFileStatus = newFileStatus;
}
/**
 * ?user>
 * ���ܣ�
 * ������
 * ���أ�
 * ���⣺
 * ���ڣ�(2004-9-1 10:00:49)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * @param newSelect nc.vo.pub.lang.UFBoolean
 */
public void setSelect(nc.vo.pub.lang.UFBoolean newSelect) {
	m_bSelect = newSelect;
}
/**
 * ?user>
 * ���ܣ�
 * ������
 * ���أ�
 * ���⣺
 * ���ڣ�(2004-9-1 10:00:49)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * @param newSequence java.lang.String
 */
public void setSequence(java.lang.String newSequence) {
	m_sSequence = newSequence;
}
/**
 * ��֤���������֮��������߼���ȷ�ԡ�
 * 
 * �������ڣ�(2001-2-15 11:47:35)
 * @exception nc.vo.pub.ValidationException �����֤ʧ�ܣ��׳�
 *     ValidationException���Դ�����н��͡�
 */
public void validate() throws nc.vo.pub.ValidationException {}
}

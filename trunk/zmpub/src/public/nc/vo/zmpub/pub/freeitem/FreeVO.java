package nc.vo.zmpub.pub.freeitem;

import java.util.ArrayList;

import nc.bs.logging.Logger;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.FieldObject;
import nc.vo.pub.NullFieldException;
import nc.vo.pub.StringField;
import nc.vo.pub.ValidationException;

/**
 * �˴���������˵����
 * 
 * �������ڣ�(2001-6-20)
 * 
 * @author��wangnj
 */
public class FreeVO extends CircularlyAccessibleValueObject {

	// �����������ID
	public String m_cinventoryid = null;

	public String m_vfree0 = null;

	public String m_vfree1 = null;

	public String m_vfree2 = null;

	public String m_vfree3 = null;

	public String m_vfree4 = null;

	public String m_vfree5 = null;

	public String m_vfree6 = null;

	public String m_vfree7 = null;

	public String m_vfree8 = null;

	public String m_vfree9 = null;

	public String m_vfree10 = null;

	public String m_vfreevalue1 = null;

	public String m_vfreevalue2 = null;

	public String m_vfreevalue3 = null;

	public String m_vfreevalue4 = null;

	public String m_vfreevalue5 = null;

	public String m_vfreevalue6 = null;

	public String m_vfreevalue7 = null;

	public String m_vfreevalue8 = null;

	public String m_vfreevalue9 = null;

	public String m_vfreevalue10 = null;

	public String m_vfreeid1 = null;

	public String m_vfreeid2 = null;

	public String m_vfreeid3 = null;

	public String m_vfreeid4 = null;

	public String m_vfreeid5 = null;

	public String m_vfreeid6 = null;

	public String m_vfreeid7 = null;

	public String m_vfreeid8 = null;

	public String m_vfreeid9 = null;

	public String m_vfreeid10 = null;

	public String m_vfreename1 = null;

	public String m_vfreename2 = null;

	public String m_vfreename3 = null;

	public String m_vfreename4 = null;

	public String m_vfreename5 = null;

	public String m_vfreename6 = null;

	public String m_vfreename7 = null;

	public String m_vfreename8 = null;

	public String m_vfreename9 = null;

	public String m_vfreename10 = null;

	/**
	 * �����������Ե�FieldObjects����Ҫ����ϵͳ�����У� ҵ������в����õ������FieldObjects��
	 */
	private static StringField m_cinventoryidField = null;

	private static StringField m_vfree0Field = null;

	private static StringField m_vfree1Field = null;

	private static StringField m_vfree2Field = null;

	private static StringField m_vfree3Field = null;

	private static StringField m_vfree4Field = null;

	private static StringField m_vfree5Field = null;

	private static StringField m_vfree6Field = null;

	private static StringField m_vfree7Field = null;

	private static StringField m_vfree8Field = null;

	private static StringField m_vfree9Field = null;

	private static StringField m_vfree10Field = null;

	private static StringField m_vfreevalue1Field = null;

	private static StringField m_vfreevalue2Field = null;

	private static StringField m_vfreevalue3Field = null;

	private static StringField m_vfreevalue4Field = null;

	private static StringField m_vfreevalue5Field = null;

	private static StringField m_vfreevalue6Field = null;

	private static StringField m_vfreevalue7Field = null;

	private static StringField m_vfreevalue8Field = null;

	private static StringField m_vfreevalue9Field = null;

	private static StringField m_vfreevalue10Field = null;

	private static StringField m_vfreeid1Field = null;

	private static StringField m_vfreeid2Field = null;

	private static StringField m_vfreeid3Field = null;

	private static StringField m_vfreeid4Field = null;

	private static StringField m_vfreeid5Field = null;

	private static StringField m_vfreeid6Field = null;

	private static StringField m_vfreeid7Field = null;

	private static StringField m_vfreeid8Field = null;

	private static StringField m_vfreeid9Field = null;

	private static StringField m_vfreeid10Field = null;

	private static StringField m_vfreename1Field = null;

	private static StringField m_vfreename2Field = null;

	private static StringField m_vfreename3Field = null;

	private static StringField m_vfreename4Field = null;

	private static StringField m_vfreename5Field = null;

	private static StringField m_vfreename6Field = null;

	private static StringField m_vfreename7Field = null;

	private static StringField m_vfreename8Field = null;

	private static StringField m_vfreename9Field = null;

	private static StringField m_vfreename10Field = null;

	/**
	 * ʹ�������ֶν��г�ʼ���Ĺ����ӡ�
	 * 
	 * �������ڣ�(2001-6-20)
	 */
	public FreeVO() {

	}

	/**
	 * ʹ���������г�ʼ���Ĺ����ӡ�
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param ??fieldNameForMethod?? ����ֵ
	 */
	public FreeVO(String newCinventoryid) {

		// Ϊ�����ֶθ�ֵ:
		m_cinventoryid = newCinventoryid;

	}

	/**
	 * ����Object�ķ���,��¡���VO����
	 * 
	 * �������ڣ�(2001-6-20)
	 */
	public Object clone() {

		// ���ƻ������ݲ������µ�VO����
		Object o = null;
		try {
			o = super.clone();
		} catch (Exception e) {
		}
		FreeVO free = (FreeVO) o;

		// �������渴�Ʊ�VO������������ԣ�

		return free;
	}

	/**
	 * 
	 * ���䶨�� �������ڣ�(2001-6-20)
	 */
	public void setDefine(FreeVO afree) {
		if (afree == null)
			return;
		if (m_cinventoryid != null
				&& !m_cinventoryid.equals(afree.getCinventoryid()))
			return;
		String vfreeid = "vfreeid", vfreeidkey = null;
		String vfreename = "vfreename", vfreenamekey = null;

		for (int i = 1; i <= 10; i++) {
			vfreeidkey = vfreeid + i;
			vfreenamekey = vfreename + i;
			setAttributeValue(vfreeidkey, afree.getAttributeValue(vfreeidkey));
			setAttributeValue(vfreenamekey, afree
					.getAttributeValue(vfreenamekey));
		}

	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		boolean b = false;
		if ((obj != null) && (obj instanceof FreeVO)) {
			// �ȱȽϴ��id
			FreeVO freevo = (FreeVO) obj;
			String cinventoryid = freevo.getCinventoryid();
			if (m_cinventoryid == null && cinventoryid == null)
				b = true;
			else if (m_cinventoryid == null && cinventoryid != null
					&& cinventoryid.trim().length() > 0)
				return false;
			else if (cinventoryid == null && m_cinventoryid != null
					&& m_cinventoryid.trim().length() > 0)
				return false;
			else if (cinventoryid.trim().equals(m_cinventoryid.trim()))
				b = true;
			else
				return false;

			// �Ƚ�free0
			if (getWholeFreeItem().equals((freevo.getWholeFreeItem())))
				b = true;
		}
		return b;
	}

	/**
	 * ������ֵ�������ʾ���ơ�
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return java.lang.String ������ֵ�������ʾ���ơ�
	 */
	public String getEntityName() {

		return "Free";
	}

	/**
	 * ���ض����ʶ������Ψһ��λ����
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getPrimaryKey() {

		return m_cinventoryid;
	}

	/**
	 * ���ö����ʶ������Ψһ��λ����
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param m_cinventoryid
	 *            String
	 */
	public void setPrimaryKey(String newCinventoryid) {

		m_cinventoryid = newCinventoryid;
	}

	/**
	 * ����m_cinventoryid��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getCinventoryid() {
		return m_cinventoryid;
	}

	/**
	 * ����m_vfree0��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfree0() {
		return getWholeFreeItem();
	}

	/**
	 * ����m_vfree1��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfree1() {
		return m_vfree1;
	}

	/**
	 * ����m_vfree2��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfree2() {
		return m_vfree2;
	}

	/**
	 * ����m_vfree3��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfree3() {
		return m_vfree3;
	}

	/**
	 * ����m_vfree4��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfree4() {
		return m_vfree4;
	}

	/**
	 * ����m_vfree5��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfree5() {
		return m_vfree5;
	}

	/**
	 * ����m_vfree6��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfree6() {
		return m_vfree6;
	}

	/**
	 * ����m_vfree7��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfree7() {
		return m_vfree7;
	}

	/**
	 * ����m_vfree8��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfree8() {
		return m_vfree8;
	}

	/**
	 * ����m_vfree9��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfree9() {
		return m_vfree9;
	}

	/**
	 * ����m_vfree10��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfree10() {
		return m_vfree10;
	}

	/**
	 * ����m_vfreevalue1��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreevalue1() {
		return m_vfreevalue1;
	}

	/**
	 * ����m_vfreevalue2��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreevalue2() {
		return m_vfreevalue2;
	}

	/**
	 * ����m_vfreevalue3��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreevalue3() {
		return m_vfreevalue3;
	}

	/**
	 * ����m_vfreevalue4��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreevalue4() {
		return m_vfreevalue4;
	}

	/**
	 * ����m_vfreevalue5��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreevalue5() {
		return m_vfreevalue5;
	}

	/**
	 * ����m_vfreevalue6��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreevalue6() {
		return m_vfreevalue6;
	}

	/**
	 * ����m_vfreevalue7��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreevalue7() {
		return m_vfreevalue7;
	}

	/**
	 * ����m_vfreevalue8��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreevalue8() {
		return m_vfreevalue8;
	}

	/**
	 * ����m_vfreevalue9��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreevalue9() {
		return m_vfreevalue9;
	}

	/**
	 * ����m_vfreevalue10��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreevalue10() {
		return m_vfreevalue10;
	}

	/**
	 * ����m_vfreeid1��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreeid1() {
		return m_vfreeid1;
	}

	/**
	 * ����m_vfreeid2��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreeid2() {
		return m_vfreeid2;
	}

	/**
	 * ����m_vfreeid3��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreeid3() {
		return m_vfreeid3;
	}

	/**
	 * ����m_vfreeid4��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreeid4() {
		return m_vfreeid4;
	}

	/**
	 * ����m_vfreeid5��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreeid5() {
		return m_vfreeid5;
	}

	/**
	 * ����m_vfreeid6��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreeid6() {
		return m_vfreeid6;
	}

	/**
	 * ����m_vfreeid7��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreeid7() {
		return m_vfreeid7;
	}

	/**
	 * ����m_vfreeid8��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreeid8() {
		return m_vfreeid8;
	}

	/**
	 * ����m_vfreeid9��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreeid9() {
		return m_vfreeid9;
	}

	/**
	 * ����m_vfreeid10��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreeid10() {
		return m_vfreeid10;
	}

	/**
	 * ����m_vfreename1��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreename1() {
		return m_vfreename1;
	}

	/**
	 * ����m_vfreename2��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreename2() {
		return m_vfreename2;
	}

	/**
	 * ����m_vfreename3��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreename3() {
		return m_vfreename3;
	}

	/**
	 * ����m_vfreename4��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreename4() {
		return m_vfreename4;
	}

	/**
	 * ����m_vfreename5��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreename5() {
		return m_vfreename5;
	}

	/**
	 * ����m_vfreename6��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreename6() {
		return m_vfreename6;
	}

	/**
	 * ����m_vfreename7��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreename7() {
		return m_vfreename7;
	}

	/**
	 * ����m_vfreename8��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreename8() {
		return m_vfreename8;
	}

	/**
	 * ����m_vfreename9��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreename9() {
		return m_vfreename9;
	}

	/**
	 * ����m_vfreename10��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return String
	 */
	public String getVfreename10() {
		return m_vfreename10;
	}

	/**
	 * ����m_cinventoryid��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_cinventoryid
	 *            String
	 */
	public void setCinventoryid(String newCinventoryid) {

		m_cinventoryid = newCinventoryid;
	}

	/**
	 * ����m_vfree0��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfree0
	 *            String
	 */
	public void setVfree0(String newVfree0) {

		m_vfree0 = newVfree0;
	}

	/**
	 * ����m_vfree1��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfree1
	 *            String
	 */
	public void setVfree1(String newVfree1) {

		m_vfree1 = newVfree1;
		m_vfree0 = null;
	}

	/**
	 * ����m_vfree2��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfree2
	 *            String
	 */
	public void setVfree2(String newVfree2) {

		m_vfree2 = newVfree2;
		m_vfree0 = null;
	}

	/**
	 * ����m_vfree3��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfree3
	 *            String
	 */
	public void setVfree3(String newVfree3) {

		m_vfree3 = newVfree3;
		m_vfree0 = null;
	}

	/**
	 * ����m_vfree4��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfree4
	 *            String
	 */
	public void setVfree4(String newVfree4) {

		m_vfree4 = newVfree4;
		m_vfree0 = null;
	}

	/**
	 * ����m_vfree5��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfree5
	 *            String
	 */
	public void setVfree5(String newVfree5) {

		m_vfree5 = newVfree5;
		m_vfree0 = null;
	}

	/**
	 * ����m_vfree6��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfree6
	 *            String
	 */
	public void setVfree6(String newVfree6) {

		m_vfree6 = newVfree6;
		m_vfree0 = null;
	}

	/**
	 * ����m_vfree7��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfree7
	 *            String
	 */
	public void setVfree7(String newVfree7) {

		m_vfree7 = newVfree7;
		m_vfree0 = null;
	}

	/**
	 * ����m_vfree8��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfree8
	 *            String
	 */
	public void setVfree8(String newVfree8) {

		m_vfree8 = newVfree8;
		m_vfree0 = null;
	}

	/**
	 * ����m_vfree9��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfree9
	 *            String
	 */
	public void setVfree9(String newVfree9) {

		m_vfree9 = newVfree9;
		m_vfree0 = null;
	}

	/**
	 * ����m_vfree10��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfree10
	 *            String
	 */
	public void setVfree10(String newVfree10) {

		m_vfree10 = newVfree10;
		m_vfree0 = null;
	}

	/**
	 * ����m_vfreevalue1��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreevalue1
	 *            String
	 */
	public void setVfreevalue1(String newVfreevalue1) {

		m_vfreevalue1 = newVfreevalue1;
	}

	/**
	 * ����m_vfreevalue2��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreevalue2
	 *            String
	 */
	public void setVfreevalue2(String newVfreevalue2) {

		m_vfreevalue2 = newVfreevalue2;
	}

	/**
	 * ����m_vfreevalue3��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreevalue3
	 *            String
	 */
	public void setVfreevalue3(String newVfreevalue3) {

		m_vfreevalue3 = newVfreevalue3;
	}

	/**
	 * ����m_vfreevalue4��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreevalue4
	 *            String
	 */
	public void setVfreevalue4(String newVfreevalue4) {

		m_vfreevalue4 = newVfreevalue4;
	}

	/**
	 * ����m_vfreevalue5��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreevalue5
	 *            String
	 */
	public void setVfreevalue5(String newVfreevalue5) {

		m_vfreevalue5 = newVfreevalue5;
	}

	/**
	 * ����m_vfreevalue6��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreevalue6
	 *            String
	 */
	public void setVfreevalue6(String newVfreevalue6) {

		m_vfreevalue6 = newVfreevalue6;
	}

	/**
	 * ����m_vfreevalue7��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreevalue7
	 *            String
	 */
	public void setVfreevalue7(String newVfreevalue7) {

		m_vfreevalue7 = newVfreevalue7;
	}

	/**
	 * ����m_vfreevalue8��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreevalue8
	 *            String
	 */
	public void setVfreevalue8(String newVfreevalue8) {

		m_vfreevalue8 = newVfreevalue8;
	}

	/**
	 * ����m_vfreevalue9��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreevalue9
	 *            String
	 */
	public void setVfreevalue9(String newVfreevalue9) {

		m_vfreevalue9 = newVfreevalue9;
	}

	/**
	 * ����m_vfreevalue10��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreevalue10
	 *            String
	 */
	public void setVfreevalue10(String newVfreevalue10) {

		m_vfreevalue10 = newVfreevalue10;
	}

	/**
	 * ����m_vfreeid1��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreeid1
	 *            String
	 */
	public void setVfreeid1(String newVfreeid1) {

		m_vfreeid1 = newVfreeid1;
	}

	/**
	 * ����m_vfreeid2��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreeid2
	 *            String
	 */
	public void setVfreeid2(String newVfreeid2) {

		m_vfreeid2 = newVfreeid2;
	}

	/**
	 * ����m_vfreeid3��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreeid3
	 *            String
	 */
	public void setVfreeid3(String newVfreeid3) {

		m_vfreeid3 = newVfreeid3;
	}

	/**
	 * ����m_vfreeid4��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreeid4
	 *            String
	 */
	public void setVfreeid4(String newVfreeid4) {

		m_vfreeid4 = newVfreeid4;
	}

	/**
	 * ����m_vfreeid5��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreeid5
	 *            String
	 */
	public void setVfreeid5(String newVfreeid5) {

		m_vfreeid5 = newVfreeid5;
	}

	/**
	 * ����m_vfreeid6��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreeid6
	 *            String
	 */
	public void setVfreeid6(String newVfreeid6) {

		m_vfreeid6 = newVfreeid6;
	}

	/**
	 * ����m_vfreeid7��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreeid7
	 *            String
	 */
	public void setVfreeid7(String newVfreeid7) {

		m_vfreeid7 = newVfreeid7;
	}

	/**
	 * ����m_vfreeid8��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreeid8
	 *            String
	 */
	public void setVfreeid8(String newVfreeid8) {

		m_vfreeid8 = newVfreeid8;
	}

	/**
	 * ����m_vfreeid9��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreeid9
	 *            String
	 */
	public void setVfreeid9(String newVfreeid9) {

		m_vfreeid9 = newVfreeid9;
	}

	/**
	 * ����m_vfreeid10��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreeid10
	 *            String
	 */
	public void setVfreeid10(String newVfreeid10) {

		m_vfreeid10 = newVfreeid10;
	}

	/**
	 * ����m_vfreename1��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreename1
	 *            String
	 */
	public void setVfreename1(String newVfreename1) {

		m_vfreename1 = newVfreename1;
	}

	/**
	 * ����m_vfreename2��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreename2
	 *            String
	 */
	public void setVfreename2(String newVfreename2) {

		m_vfreename2 = newVfreename2;
	}

	/**
	 * ����m_vfreename3��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreename3
	 *            String
	 */
	public void setVfreename3(String newVfreename3) {

		m_vfreename3 = newVfreename3;
	}

	/**
	 * ����m_vfreename4��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreename4
	 *            String
	 */
	public void setVfreename4(String newVfreename4) {

		m_vfreename4 = newVfreename4;
	}

	/**
	 * ����m_vfreename5��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreename5
	 *            String
	 */
	public void setVfreename5(String newVfreename5) {

		m_vfreename5 = newVfreename5;
	}

	/**
	 * ����m_vfreename6��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreename6
	 *            String
	 */
	public void setVfreename6(String newVfreename6) {

		m_vfreename6 = newVfreename6;
	}

	/**
	 * ����m_vfreename7��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreename7
	 *            String
	 */
	public void setVfreename7(String newVfreename7) {

		m_vfreename7 = newVfreename7;
	}

	/**
	 * ����m_vfreename8��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreename8
	 *            String
	 */
	public void setVfreename8(String newVfreename8) {

		m_vfreename8 = newVfreename8;
	}

	/**
	 * ����m_vfreename9��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreename9
	 *            String
	 */
	public void setVfreename9(String newVfreename9) {

		m_vfreename9 = newVfreename9;
	}

	/**
	 * ����m_vfreename10��setter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param newM_vfreename10
	 *            String
	 */
	public void setVfreename10(String newVfreename10) {

		m_vfreename10 = newVfreename10;
	}

	/**
	 * ��֤���������֮��������߼���ȷ�ԡ�
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @exception nc.vo.pub.ValidationException
	 *                �����֤ʧ�ܣ��׳� ValidationException���Դ�����н��͡�
	 */
	public void validate() throws ValidationException {

		ArrayList errFields = new ArrayList(); // errFields record those null
		// fields
		// that cannot be null.
		// ����Ƿ�Ϊ�������յ��ֶθ��˿�ֵ���������Ҫ�޸��������ʾ��Ϣ��
		if (m_cinventoryid == null) {
			errFields.add(new String("m_cinventoryid"));
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

		return new String[] { "vfree0", "vfree1", "vfree2", "vfree3", "vfree4",
				"vfree5", "vfree6", "vfree7", "vfree8", "vfree9", "vfree10",
				"vfreevalue1", "vfreevalue2", "vfreevalue3", "vfreevalue4",
				"vfreevalue5", "vfreevalue6", "vfreevalue7", "vfreevalue8",
				"vfreevalue9", "vfreevalue10", "vfreeid1", "vfreeid2",
				"vfreeid3", "vfreeid4", "vfreeid5", "vfreeid6", "vfreeid7",
				"vfreeid8", "vfreeid9", "vfreeid10", "vfreename1",
				"vfreename2", "vfreename3", "vfreename4", "vfreename5",
				"vfreename6", "vfreename7", "vfreename8", "vfreename9",
				"vfreename10", "pk_invbasdoc", "cinventoryid" };
	}

	/**
	 * <p>
	 * ����һ�����������ַ��������Ե�ֵ��
	 * <p>
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param key
	 *            java.lang.String
	 */
	public Object getAttributeValue(String attributeName) {

		if (attributeName.equals("cinventoryid")) {
			return m_cinventoryid;
		} else if (attributeName.equals("pk_invbasdoc")) {
			return m_Pk_invbasdoc;
		} else if (attributeName.equals("vfree0")) {
			return getWholeFreeItem();
		} else if (attributeName.equals("vfree1")) {
			return m_vfree1;
		} else if (attributeName.equals("vfree2")) {
			return m_vfree2;
		} else if (attributeName.equals("vfree3")) {
			return m_vfree3;
		} else if (attributeName.equals("vfree4")) {
			return m_vfree4;
		} else if (attributeName.equals("vfree5")) {
			return m_vfree5;
		} else if (attributeName.equals("vfree6")) {
			return m_vfree6;
		} else if (attributeName.equals("vfree7")) {
			return m_vfree7;
		} else if (attributeName.equals("vfree8")) {
			return m_vfree8;
		} else if (attributeName.equals("vfree9")) {
			return m_vfree9;
		} else if (attributeName.equals("vfree10")) {
			return m_vfree10;
		} else if (attributeName.equals("vfreevalue1")) {
			return m_vfreevalue1;
		} else if (attributeName.equals("vfreevalue2")) {
			return m_vfreevalue2;
		} else if (attributeName.equals("vfreevalue3")) {
			return m_vfreevalue3;
		} else if (attributeName.equals("vfreevalue4")) {
			return m_vfreevalue4;
		} else if (attributeName.equals("vfreevalue5")) {
			return m_vfreevalue5;
		} else if (attributeName.equals("vfreevalue6")) {
			return m_vfreevalue6;
		} else if (attributeName.equals("vfreevalue7")) {
			return m_vfreevalue7;
		} else if (attributeName.equals("vfreevalue8")) {
			return m_vfreevalue8;
		} else if (attributeName.equals("vfreevalue9")) {
			return m_vfreevalue9;
		} else if (attributeName.equals("vfreevalue10")) {
			return m_vfreevalue10;
		} else if (attributeName.equals("vfreeid1")) {
			return m_vfreeid1;
		} else if (attributeName.equals("vfreeid2")) {
			return m_vfreeid2;
		} else if (attributeName.equals("vfreeid3")) {
			return m_vfreeid3;
		} else if (attributeName.equals("vfreeid4")) {
			return m_vfreeid4;
		} else if (attributeName.equals("vfreeid5")) {
			return m_vfreeid5;
		} else if (attributeName.equals("vfreeid6")) {
			return m_vfreeid6;
		} else if (attributeName.equals("vfreeid7")) {
			return m_vfreeid7;
		} else if (attributeName.equals("vfreeid8")) {
			return m_vfreeid8;
		} else if (attributeName.equals("vfreeid9")) {
			return m_vfreeid9;
		} else if (attributeName.equals("vfreeid10")) {
			return m_vfreeid10;
		} else if (attributeName.equals("vfreename1")) {
			return m_vfreename1;
		} else if (attributeName.equals("vfreename2")) {
			return m_vfreename2;
		} else if (attributeName.equals("vfreename3")) {
			return m_vfreename3;
		} else if (attributeName.equals("vfreename4")) {
			return m_vfreename4;
		} else if (attributeName.equals("vfreename5")) {
			return m_vfreename5;
		} else if (attributeName.equals("vfreename6")) {
			return m_vfreename6;
		} else if (attributeName.equals("vfreename7")) {
			return m_vfreename7;
		} else if (attributeName.equals("vfreename8")) {
			return m_vfreename8;
		} else if (attributeName.equals("vfreename9")) {
			return m_vfreename9;
		} else if (attributeName.equals("vfreename10")) {
			return m_vfreename10;
		}
		return null;
	}

	/**
	 * <p>
	 * �Բ���name���͵���������ֵ��
	 * <p>
	 * �������ڣ�(2001-6-20)
	 * 
	 * @param key
	 *            java.lang.String
	 */
	public void setAttributeValue(String name, Object value) {
		String sTrimedValue = null; // trimed value to String-type
		// fields.default
		// is null.
		if (value != null) {
			sTrimedValue = value.toString().trim();
			if (sTrimedValue.length() == 0)
				sTrimedValue = null;
		}

		try {
			if (name.equals("cinventoryid")) {
				m_cinventoryid = sTrimedValue;
			} else if (name.equals("pk_invbasdoc")) {
				m_Pk_invbasdoc = sTrimedValue;
			} else if (name.equals("vfree0")) {
				m_vfree0 = sTrimedValue;
			} else if (name.equals("vfree1")) {
				m_vfree1 = sTrimedValue;
				m_vfree0 = null;
			} else if (name.equals("vfree2")) {
				m_vfree2 = sTrimedValue;
				m_vfree0 = null;
			} else if (name.equals("vfree3")) {
				m_vfree3 = sTrimedValue;
				m_vfree0 = null;
			} else if (name.equals("vfree4")) {
				m_vfree4 = sTrimedValue;
				m_vfree0 = null;
			} else if (name.equals("vfree5")) {
				m_vfree5 = sTrimedValue;
				m_vfree0 = null;
			} else if (name.equals("vfree6")) {
				m_vfree6 = sTrimedValue;
				m_vfree0 = null;
			} else if (name.equals("vfree7")) {
				m_vfree7 = sTrimedValue;
				m_vfree0 = null;
			} else if (name.equals("vfree8")) {
				m_vfree8 = sTrimedValue;
				m_vfree0 = null;
			} else if (name.equals("vfree9")) {
				m_vfree9 = sTrimedValue;
				m_vfree0 = null;
			} else if (name.equals("vfree10")) {
				m_vfree10 = sTrimedValue;
				m_vfree0 = null;
			} else if (name.equals("vfreevalue1")) {
				m_vfreevalue1 = sTrimedValue;
			} else if (name.equals("vfreevalue2")) {
				m_vfreevalue2 = sTrimedValue;
			} else if (name.equals("vfreevalue3")) {
				m_vfreevalue3 = sTrimedValue;
			} else if (name.equals("vfreevalue4")) {
				m_vfreevalue4 = sTrimedValue;
			} else if (name.equals("vfreevalue5")) {
				m_vfreevalue5 = sTrimedValue;
			} else if (name.equals("vfreevalue6")) {
				m_vfreevalue6 = sTrimedValue;
			} else if (name.equals("vfreevalue7")) {
				m_vfreevalue7 = sTrimedValue;
			} else if (name.equals("vfreevalue8")) {
				m_vfreevalue8 = sTrimedValue;
			} else if (name.equals("vfreevalue9")) {
				m_vfreevalue9 = sTrimedValue;
			} else if (name.equals("vfreevalue10")) {
				m_vfreevalue10 = sTrimedValue;
			} else if (name.equals("vfreeid1")) {
				m_vfreeid1 = sTrimedValue;
			} else if (name.equals("vfreeid2")) {
				m_vfreeid2 = sTrimedValue;
			} else if (name.equals("vfreeid3")) {
				m_vfreeid3 = sTrimedValue;
			} else if (name.equals("vfreeid4")) {
				m_vfreeid4 = sTrimedValue;
			} else if (name.equals("vfreeid5")) {
				m_vfreeid5 = sTrimedValue;
			} else if (name.equals("vfreeid6")) {
				m_vfreeid6 = sTrimedValue;
			} else if (name.equals("vfreeid7")) {
				m_vfreeid7 = sTrimedValue;
			} else if (name.equals("vfreeid8")) {
				m_vfreeid8 = sTrimedValue;
			} else if (name.equals("vfreeid9")) {
				m_vfreeid9 = sTrimedValue;
			} else if (name.equals("vfreeid10")) {
				m_vfreeid10 = sTrimedValue;
			} else if (name.equals("vfreename1")) {
				m_vfreename1 = sTrimedValue;
			} else if (name.equals("vfreename2")) {
				m_vfreename2 = sTrimedValue;
			} else if (name.equals("vfreename3")) {
				m_vfreename3 = sTrimedValue;
			} else if (name.equals("vfreename4")) {
				m_vfreename4 = sTrimedValue;
			} else if (name.equals("vfreename5")) {
				m_vfreename5 = sTrimedValue;
			} else if (name.equals("vfreename6")) {
				m_vfreename6 = sTrimedValue;
			} else if (name.equals("vfreename7")) {
				m_vfreename7 = sTrimedValue;
			} else if (name.equals("vfreename8")) {
				m_vfreename8 = sTrimedValue;
			} else if (name.equals("vfreename9")) {
				m_vfreename9 = sTrimedValue;
			} else if (name.equals("vfreename10")) {
				m_vfreename10 = sTrimedValue;
			}
		} catch (ClassCastException e) {
			throw new ClassCastException("setAttributeValue������Ϊ " + name
					+ " ��ֵʱ����ת������");
		}
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getCinventoryidField() {

		if (m_cinventoryidField == null) {
			try {
				m_cinventoryidField = new StringField();
				// ���Ե�����
				m_cinventoryidField.setName("cinventoryid");
				// ���Ե�����
				m_cinventoryidField.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_cinventoryidField;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfree0Field() {

		if (m_vfree0Field == null) {
			try {
				m_vfree0Field = new StringField();
				// ���Ե�����
				m_vfree0Field.setName("vfree0");
				// ���Ե�����
				m_vfree0Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfree0Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfree1Field() {

		if (m_vfree1Field == null) {
			try {
				m_vfree1Field = new StringField();
				// ���Ե�����
				m_vfree1Field.setName("vfree1");
				// ���Ե�����
				m_vfree1Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfree1Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfree2Field() {

		if (m_vfree2Field == null) {
			try {
				m_vfree2Field = new StringField();
				// ���Ե�����
				m_vfree2Field.setName("vfree2");
				// ���Ե�����
				m_vfree2Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfree2Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfree3Field() {

		if (m_vfree3Field == null) {
			try {
				m_vfree3Field = new StringField();
				// ���Ե�����
				m_vfree3Field.setName("vfree3");
				// ���Ե�����
				m_vfree3Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfree3Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfree4Field() {

		if (m_vfree4Field == null) {
			try {
				m_vfree4Field = new StringField();
				// ���Ե�����
				m_vfree4Field.setName("vfree4");
				// ���Ե�����
				m_vfree4Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfree4Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfree5Field() {

		if (m_vfree5Field == null) {
			try {
				m_vfree5Field = new StringField();
				// ���Ե�����
				m_vfree5Field.setName("vfree5");
				// ���Ե�����
				m_vfree5Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfree5Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfree6Field() {

		if (m_vfree6Field == null) {
			try {
				m_vfree6Field = new StringField();
				// ���Ե�����
				m_vfree6Field.setName("vfree6");
				// ���Ե�����
				m_vfree6Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfree6Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfree7Field() {

		if (m_vfree7Field == null) {
			try {
				m_vfree7Field = new StringField();
				// ���Ե�����
				m_vfree7Field.setName("vfree7");
				// ���Ե�����
				m_vfree7Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfree7Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfree8Field() {

		if (m_vfree8Field == null) {
			try {
				m_vfree8Field = new StringField();
				// ���Ե�����
				m_vfree8Field.setName("vfree8");
				// ���Ե�����
				m_vfree8Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfree8Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfree9Field() {

		if (m_vfree9Field == null) {
			try {
				m_vfree9Field = new StringField();
				// ���Ե�����
				m_vfree9Field.setName("vfree9");
				// ���Ե�����
				m_vfree9Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfree9Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfree10Field() {

		if (m_vfree10Field == null) {
			try {
				m_vfree10Field = new StringField();
				// ���Ե�����
				m_vfree10Field.setName("vfree10");
				// ���Ե�����
				m_vfree10Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfree10Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreevalue1Field() {

		if (m_vfreevalue1Field == null) {
			try {
				m_vfreevalue1Field = new StringField();
				// ���Ե�����
				m_vfreevalue1Field.setName("vfreevalue1");
				// ���Ե�����
				m_vfreevalue1Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreevalue1Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreevalue2Field() {

		if (m_vfreevalue2Field == null) {
			try {
				m_vfreevalue2Field = new StringField();
				// ���Ե�����
				m_vfreevalue2Field.setName("vfreevalue2");
				// ���Ե�����
				m_vfreevalue2Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreevalue2Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreevalue3Field() {

		if (m_vfreevalue3Field == null) {
			try {
				m_vfreevalue3Field = new StringField();
				// ���Ե�����
				m_vfreevalue3Field.setName("vfreevalue3");
				// ���Ե�����
				m_vfreevalue3Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreevalue3Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreevalue4Field() {

		if (m_vfreevalue4Field == null) {
			try {
				m_vfreevalue4Field = new StringField();
				// ���Ե�����
				m_vfreevalue4Field.setName("vfreevalue4");
				// ���Ե�����
				m_vfreevalue4Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreevalue4Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreevalue5Field() {

		if (m_vfreevalue5Field == null) {
			try {
				m_vfreevalue5Field = new StringField();
				// ���Ե�����
				m_vfreevalue5Field.setName("vfreevalue5");
				// ���Ե�����
				m_vfreevalue5Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreevalue5Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreevalue6Field() {

		if (m_vfreevalue6Field == null) {
			try {
				m_vfreevalue6Field = new StringField();
				// ���Ե�����
				m_vfreevalue6Field.setName("vfreevalue6");
				// ���Ե�����
				m_vfreevalue6Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreevalue6Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreevalue7Field() {

		if (m_vfreevalue7Field == null) {
			try {
				m_vfreevalue7Field = new StringField();
				// ���Ե�����
				m_vfreevalue7Field.setName("vfreevalue7");
				// ���Ե�����
				m_vfreevalue7Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreevalue7Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreevalue8Field() {

		if (m_vfreevalue8Field == null) {
			try {
				m_vfreevalue8Field = new StringField();
				// ���Ե�����
				m_vfreevalue8Field.setName("vfreevalue8");
				// ���Ե�����
				m_vfreevalue8Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreevalue8Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreevalue9Field() {

		if (m_vfreevalue9Field == null) {
			try {
				m_vfreevalue9Field = new StringField();
				// ���Ե�����
				m_vfreevalue9Field.setName("vfreevalue9");
				// ���Ե�����
				m_vfreevalue9Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreevalue9Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreevalue10Field() {

		if (m_vfreevalue10Field == null) {
			try {
				m_vfreevalue10Field = new StringField();
				// ���Ե�����
				m_vfreevalue10Field.setName("vfreevalue10");
				// ���Ե�����
				m_vfreevalue10Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreevalue10Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreeid1Field() {

		if (m_vfreeid1Field == null) {
			try {
				m_vfreeid1Field = new StringField();
				// ���Ե�����
				m_vfreeid1Field.setName("vfreeid1");
				// ���Ե�����
				m_vfreeid1Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreeid1Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreeid2Field() {

		if (m_vfreeid2Field == null) {
			try {
				m_vfreeid2Field = new StringField();
				// ���Ե�����
				m_vfreeid2Field.setName("vfreeid2");
				// ���Ե�����
				m_vfreeid2Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreeid2Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreeid3Field() {

		if (m_vfreeid3Field == null) {
			try {
				m_vfreeid3Field = new StringField();
				// ���Ե�����
				m_vfreeid3Field.setName("vfreeid3");
				// ���Ե�����
				m_vfreeid3Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreeid3Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreeid4Field() {

		if (m_vfreeid4Field == null) {
			try {
				m_vfreeid4Field = new StringField();
				// ���Ե�����
				m_vfreeid4Field.setName("vfreeid4");
				// ���Ե�����
				m_vfreeid4Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreeid4Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreeid5Field() {

		if (m_vfreeid5Field == null) {
			try {
				m_vfreeid5Field = new StringField();
				// ���Ե�����
				m_vfreeid5Field.setName("vfreeid5");
				// ���Ե�����
				m_vfreeid5Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreeid5Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreeid6Field() {

		if (m_vfreeid6Field == null) {
			try {
				m_vfreeid6Field = new StringField();
				// ���Ե�����
				m_vfreeid6Field.setName("vfreeid6");
				// ���Ե�����
				m_vfreeid6Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreeid6Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreeid7Field() {

		if (m_vfreeid7Field == null) {
			try {
				m_vfreeid7Field = new StringField();
				// ���Ե�����
				m_vfreeid7Field.setName("vfreeid7");
				// ���Ե�����
				m_vfreeid7Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreeid7Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreeid8Field() {

		if (m_vfreeid8Field == null) {
			try {
				m_vfreeid8Field = new StringField();
				// ���Ե�����
				m_vfreeid8Field.setName("vfreeid8");
				// ���Ե�����
				m_vfreeid8Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreeid8Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreeid9Field() {

		if (m_vfreeid9Field == null) {
			try {
				m_vfreeid9Field = new StringField();
				// ���Ե�����
				m_vfreeid9Field.setName("vfreeid9");
				// ���Ե�����
				m_vfreeid9Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreeid9Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreeid10Field() {

		if (m_vfreeid10Field == null) {
			try {
				m_vfreeid10Field = new StringField();
				// ���Ե�����
				m_vfreeid10Field.setName("vfreeid10");
				// ���Ե�����
				m_vfreeid10Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreeid10Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreename1Field() {

		if (m_vfreename1Field == null) {
			try {
				m_vfreename1Field = new StringField();
				// ���Ե�����
				m_vfreename1Field.setName("vfreename1");
				// ���Ե�����
				m_vfreename1Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreename1Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreename2Field() {

		if (m_vfreename2Field == null) {
			try {
				m_vfreename2Field = new StringField();
				// ���Ե�����
				m_vfreename2Field.setName("vfreename2");
				// ���Ե�����
				m_vfreename2Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreename2Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreename3Field() {

		if (m_vfreename3Field == null) {
			try {
				m_vfreename3Field = new StringField();
				// ���Ե�����
				m_vfreename3Field.setName("vfreename3");
				// ���Ե�����
				m_vfreename3Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreename3Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreename4Field() {

		if (m_vfreename4Field == null) {
			try {
				m_vfreename4Field = new StringField();
				// ���Ե�����
				m_vfreename4Field.setName("vfreename4");
				// ���Ե�����
				m_vfreename4Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreename4Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreename5Field() {

		if (m_vfreename5Field == null) {
			try {
				m_vfreename5Field = new StringField();
				// ���Ե�����
				m_vfreename5Field.setName("vfreename5");
				// ���Ե�����
				m_vfreename5Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreename5Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreename6Field() {

		if (m_vfreename6Field == null) {
			try {
				m_vfreename6Field = new StringField();
				// ���Ե�����
				m_vfreename6Field.setName("vfreename6");
				// ���Ե�����
				m_vfreename6Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreename6Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreename7Field() {

		if (m_vfreename7Field == null) {
			try {
				m_vfreename7Field = new StringField();
				// ���Ե�����
				m_vfreename7Field.setName("vfreename7");
				// ���Ե�����
				m_vfreename7Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreename7Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreename8Field() {

		if (m_vfreename8Field == null) {
			try {
				m_vfreename8Field = new StringField();
				// ���Ե�����
				m_vfreename8Field.setName("vfreename8");
				// ���Ե�����
				m_vfreename8Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreename8Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreename9Field() {

		if (m_vfreename9Field == null) {
			try {
				m_vfreename9Field = new StringField();
				// ���Ե�����
				m_vfreename9Field.setName("vfreename9");
				// ���Ե�����
				m_vfreename9Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreename9Field;
	}

	/**
	 * FieldObject��Getter������
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject
	 */
	public static StringField getVfreename10Field() {

		if (m_vfreename10Field == null) {
			try {
				m_vfreename10Field = new StringField();
				// ���Ե�����
				m_vfreename10Field.setName("vfreename10");
				// ���Ե�����
				m_vfreename10Field.setLabel("null");
				// �����ӶԱ����Ե�����������

			} catch (Throwable exception) {
				handleException(exception);
			}
		}
		return m_vfreename10Field;
	}

	/**
	 * �������ValueObject�������FieldObject����ļ��ϡ�
	 * 
	 * �������ڣ�(2001-6-20)
	 * 
	 * @return nc.vo.pub.FieldObject[]
	 */
	public FieldObject[] getFields() {

		FieldObject[] fields = { getCinventoryidField(), getVfree0Field(),
				getVfree1Field(), getVfree2Field(), getVfree3Field(),
				getVfree4Field(), getVfree5Field(), getVfree6Field(),
				getVfree7Field(), getVfree8Field(), getVfree9Field(),
				getVfree10Field(), getVfreevalue1Field(),
				getVfreevalue2Field(), getVfreevalue3Field(),
				getVfreevalue4Field(), getVfreevalue5Field(),
				getVfreevalue6Field(), getVfreevalue7Field(),
				getVfreevalue8Field(), getVfreevalue9Field(),
				getVfreevalue10Field(), getVfreeid1Field(), getVfreeid2Field(),
				getVfreeid3Field(), getVfreeid4Field(), getVfreeid5Field(),
				getVfreeid6Field(), getVfreeid7Field(), getVfreeid8Field(),
				getVfreeid9Field(), getVfreeid10Field(), getVfreename1Field(),
				getVfreename2Field(), getVfreename3Field(),
				getVfreename4Field(), getVfreename5Field(),
				getVfreename6Field(), getVfreename7Field(),
				getVfreename8Field(), getVfreename9Field(),
				getVfreename10Field() };

		return fields;
	}

	public static final String FI_NULL = ":-)=";

	public static final int FREE_ITEM_NUM = 10;// ����������

	public java.lang.String m_Pk_invbasdoc;

	/**
	 * ����IC ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2004-7-6 14:33:37) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPk_invbasdoc() {
		return m_Pk_invbasdoc;
	}

	/**
	 * �����ߣ������� ���ܣ��õ�������VO ������ ���أ� ���⣺ ���ڣ�(2001-5-24 ���� 9:48) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param firstRow
	 *            int
	 */
	public String getWholeFreeItemOld() {
		// ����vfree0Ϊ��vfree1-10�õ�������
		if (m_vfree0 != null && m_vfree0.trim().length() > 0)
			return m_vfree0;
		String sTempString = "";
		// ��������
		int iAppendix = 1;
		for (int i = 1; i <= FREE_ITEM_NUM; i++) {
			if (((null == getAttributeValue("vfreename" + i)) || (getAttributeValue(
					"vfreename" + i).toString().length() == 0))
					|| ((null == getAttributeValue("vfree" + i)) || (getAttributeValue(
							"vfree" + i).toString().length() == 0))) {
				continue;
			}
			if (i < 10)
				iAppendix = i;
			else
				iAppendix = 'a' + (i - 10);
			sTempString = sTempString + "["
					// + iAppendix
					+ getAttributeValue("vfreename" + i) + ":"
					+ getAttributeValue("vfree" + i) + "]";
		}

		m_vfree0 = sTempString;
		return sTempString;
	}

	/**
	 * �����ߣ������� ���ܣ��õ�������VO ������ ���أ� ���⣺ ���ڣ�(2001-5-24 ���� 9:48) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param firstRow
	 *            int
	 */
	public String getWholeFreeItem() {
		// ����vfree0Ϊ��vfree1-10�õ�������
		if (m_vfree0 != null && m_vfree0.trim().length() > 0)
			return m_vfree0;

		StringBuffer sbFullValue = new StringBuffer();
		// ---- && ------ name �� value ����ʱ����ʾ֮��
		// -------------------------
		if (m_vfree1 != null && m_vfree1.trim().length() > 0
				&& m_vfreename1 != null && m_vfreename1.trim().length() > 0) {
			sbFullValue.append("[").append(m_vfreename1).append(":").append(
					m_vfree1).append("]");
		}
		// -------------------------
		if (m_vfree2 != null && m_vfree2.trim().length() > 0
				&& m_vfreename2 != null && m_vfreename2.trim().length() > 0) {
			sbFullValue.append("[").append(m_vfreename2).append(":").append(
					m_vfree2).append("]");
		}
		// -------------------------
		if (m_vfree3 != null && m_vfree3.trim().length() > 0
				&& m_vfreename3 != null && m_vfreename3.trim().length() > 0) {
			sbFullValue.append("[").append(m_vfreename3).append(":").append(
					m_vfree3).append("]");
		}
		// -------------------------
		if (m_vfree4 != null && m_vfree4.trim().length() > 0
				&& m_vfreename4 != null && m_vfreename4.trim().length() > 0) {
			sbFullValue.append("[").append(m_vfreename4).append(":").append(
					m_vfree4).append("]");
		}
		// -------------------------
		if (m_vfree5 != null && m_vfree5.trim().length() > 0
				&& m_vfreename5 != null && m_vfreename5.trim().length() > 0) {
			sbFullValue.append("[").append(m_vfreename5).append(":").append(
					m_vfree5).append("]");
		}
		m_vfree0 = sbFullValue.toString().trim();
		// ʵ����Ŀǰֻʹ����5����������Ժ����6-10���Ȳ���������
		return m_vfree0;
	}

	/**
	 * �����ߣ������� ���ܣ�ֻ�õ�������ֵ��ƴ�ӡ�
	 * 
	 * ע�������VO�еĴ��㷨һ��
	 * 
	 * ������ ���أ�������ֵ��ƴ�ӡ� ���⣺ ���ڣ�(2001-5-24 ���� 9:48) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param firstRow
	 *            int
	 */
	public String getWholeFreeItemValue() {
		// ����vfree0Ϊ��vfree1-10�õ�������
		StringBuffer sbWholeValue = new StringBuffer();

		// ����ǿշ�������ֵ(��ֹ�������ֵ��"null"),
		// �Ա���ֵһһ��Ӧ������Ƚϳ���
		if (m_vfree1 != null && m_vfree1.trim().length() > 0)
			sbWholeValue.append(m_vfree1);
		else
			sbWholeValue.append(FI_NULL);
		if (m_vfree2 != null && m_vfree2.trim().length() > 0)
			sbWholeValue.append(m_vfree2);
		else
			sbWholeValue.append(FI_NULL);
		if (m_vfree3 != null && m_vfree3.trim().length() > 0)
			sbWholeValue.append(m_vfree3);
		else
			sbWholeValue.append(FI_NULL);
		if (m_vfree4 != null && m_vfree4.trim().length() > 0)
			sbWholeValue.append(m_vfree4);
		else
			sbWholeValue.append(FI_NULL);
		if (m_vfree5 != null && m_vfree5.trim().length() > 0)
			sbWholeValue.append(m_vfree5);
		else
			sbWholeValue.append(FI_NULL);
		if (m_vfree6 != null && m_vfree6.trim().length() > 0)
			sbWholeValue.append(m_vfree6);
		else
			sbWholeValue.append(FI_NULL);
		if (m_vfree7 != null && m_vfree7.trim().length() > 0)
			sbWholeValue.append(m_vfree7);
		else
			sbWholeValue.append(FI_NULL);
		if (m_vfree8 != null && m_vfree8.trim().length() > 0)
			sbWholeValue.append(m_vfree8);
		else
			sbWholeValue.append(FI_NULL);
		if (m_vfree9 != null && m_vfree9.trim().length() > 0)
			sbWholeValue.append(m_vfree9);
		else
			sbWholeValue.append(FI_NULL);
		if (m_vfree10 != null && m_vfree10.trim().length() > 0)
			sbWholeValue.append(m_vfree10);
		else
			sbWholeValue.append(FI_NULL);

		return sbWholeValue.toString().trim();
	}

	/**
	 * �����ߣ������� ���ܣ�ֻ�õ�������ֵ��ƴ�ӡ�
	 * 
	 * ע�������VO�еĴ��㷨һ��
	 * 
	 * ������ ���أ�������ֵ��ƴ�ӡ� ���⣺ ���ڣ�(2001-5-24 ���� 9:48) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param firstRow
	 *            int
	 */
	public String getWholeFreeItemValueOld() {
		// ����vfree0Ϊ��vfree1-10�õ�������
		StringBuffer sbTempString = new StringBuffer();

		Object oTempValue = null;
		for (int i = 1; i <= FREE_ITEM_NUM; i++) {
			oTempValue = getAttributeValue("vfree" + i);
			if (oTempValue != null) // blank is also value.
				sbTempString.append(oTempValue.toString());
			else
				// ����ǿշ�������ֵ(��ֹ�������ֵ��"null"),
				// �Ա���ֵһһ��Ӧ������Ƚϳ���
				sbTempString.append(FI_NULL);
		}

		return sbTempString.toString();
	}

	/**
	 * �����ߣ����˾� ���ܣ��õ��Ƿ���vfree1-10���� ������ ���أ� ��valueֵ: true ����: true
	 * 
	 * ���⣺ ���ڣ�(2003-1-23 ���� 9:48) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param firstRow
	 *            int
	 */
	public boolean hasFreeItem() {
		if (m_vfreeid1 != null && m_vfreeid1.trim().length() > 0)
			return true;
		else if (m_vfreeid2 != null && m_vfreeid2.trim().length() > 0)
			return true;
		else if (m_vfreeid3 != null && m_vfreeid3.trim().length() > 0)
			return true;
		else if (m_vfreeid4 != null && m_vfreeid4.trim().length() > 0)
			return true;
		else if (m_vfreeid5 != null && m_vfreeid5.trim().length() > 0)
			return true;
		else if (m_vfreeid6 != null && m_vfreeid6.trim().length() > 0)
			return true;
		else if (m_vfreeid7 != null && m_vfreeid7.trim().length() > 0)
			return true;
		else if (m_vfreeid8 != null && m_vfreeid8.trim().length() > 0)
			return true;
		else if (m_vfreeid9 != null && m_vfreeid9.trim().length() > 0)
			return true;
		else if (m_vfreeid10 != null && m_vfreeid10.trim().length() > 0)
			return true;
		else
			// ���ǿգ��Ǿͷ���false
			return false;
	}

	/**
	 * �����ߣ����˾� ���ܣ��õ��Ƿ���vfree1-10���� ������ ���أ� ��valueֵ: false ����: true
	 * 
	 * ���⣺ ���ڣ�(2003-1-23 ���� 9:48) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param firstRow
	 *            int
	 */
	public boolean isNullValue() {
		if (m_vfree1 != null && m_vfree1.trim().length() > 0)
			return false;
		else if (m_vfree2 != null && m_vfree2.trim().length() > 0)
			return false;
		else if (m_vfree3 != null && m_vfree3.trim().length() > 0)
			return false;
		else if (m_vfree4 != null && m_vfree4.trim().length() > 0)
			return false;
		else if (m_vfree5 != null && m_vfree5.trim().length() > 0)
			return false;
		else if (m_vfree6 != null && m_vfree6.trim().length() > 0)
			return false;
		else if (m_vfree7 != null && m_vfree7.trim().length() > 0)
			return false;
		else if (m_vfree8 != null && m_vfree8.trim().length() > 0)
			return false;
		else if (m_vfree9 != null && m_vfree9.trim().length() > 0)
			return false;
		else if (m_vfree10 != null && m_vfree10.trim().length() > 0)
			return false;
		else
			// ���ǿգ��Ǿͷ���true
			return true;
	}

	/**
	 * ����IC ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2004-7-6 14:33:37) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param newPk_invbasdoc
	 *            java.lang.String
	 */
	public void setPk_invbasdoc(java.lang.String newPk_invbasdoc) {
		m_Pk_invbasdoc = newPk_invbasdoc;
	}

	/**
	 * for test
	 * 
	 */
	public static void main(String[] a) {
		int loop = 100000;

		FreeVO vo = new FreeVO();

		long t1, t2;
		// ------------------- NULL ------------------------

		for (int i = 0; i < loop; i++) {
			vo.setVfree0(null);
			vo.getWholeFreeItemOld();
		}

		for (int i = 0; i < loop; i++) {
			vo.setVfree0(null);
			vo.getWholeFreeItem();
		}

		vo.setVfree0(null);
		String s1 = vo.getWholeFreeItemOld();// ��ȷ����֤
		vo.setVfree0(null);
		String s2 = vo.getWholeFreeItem();
		if (!s1.equals(s2))
			Logger.error("ERROR NULL-1->" + s1 + "!=" + s2);
		vo.setVfree0(null);
		s1 = vo.getWholeFreeItemValueOld();
		vo.setVfree0(null);
		s2 = vo.getWholeFreeItemValue();
		if (!s1.equals(s2))
			Logger.error("ERROR NULL-2->" + s1 + "!=" + s2);
		// -------------------- FULL ---------------------
		vo.setVfree1("aaa1");
		vo.setVfreename1("name1");
		vo.setVfree2("aaa2");
		vo.setVfreename2("name2");
		vo.setVfree3("aaa3");
		vo.setVfreename3("name3");
		vo.setVfree4("aaa4");
		vo.setVfreename4("name4");
		vo.setVfree5("aaa5");
		vo.setVfreename5("name5");

		for (int i = 0; i < loop; i++) {
			vo.setVfree0(null);
			vo.getWholeFreeItemOld();
		}

		for (int i = 0; i < loop; i++) {
			vo.setVfree0(null);
			vo.getWholeFreeItem();
		}

		vo.setVfree0(null);
		s1 = vo.getWholeFreeItemOld();
		vo.setVfree0(null);
		s2 = vo.getWholeFreeItem();
		if (!s1.equals(s2))
			Logger.error("ERROR FULL-1->" + s1 + "!=" + s2);

		vo.setVfree0(null);
		s1 = vo.getWholeFreeItemValueOld();
		vo.setVfree0(null);
		s2 = vo.getWholeFreeItemValue();
		if (!s1.equals(s2))
			Logger.error("ERROR FULL-2->" + s1 + "!=" + s2);
		Logger.info("vo check is OK!");
		// =============================== HALF 1=================
		vo.setVfree1(null);
		vo.setVfreename1("name1");
		vo.setVfree2(null);
		vo.setVfreename2("name2");
		vo.setVfree3(null);
		vo.setVfreename3("name3");
		vo.setVfree4("aaa4");
		vo.setVfreename4("name4");
		vo.setVfree5(null);
		vo.setVfreename5("name5");

		for (int i = 0; i < loop; i++) {
			vo.setVfree0(null);
			vo.getWholeFreeItemOld();
		}

		for (int i = 0; i < loop; i++) {
			vo.setVfree0(null);
			vo.getWholeFreeItem();
		}

		vo.setVfree0(null);
		s1 = vo.getWholeFreeItemOld();
		vo.setVfree0(null);
		s2 = vo.getWholeFreeItem();
		if (!s1.equals(s2))
			Logger.error("ERROR HALF 1-1->" + s1 + "!=" + s2);

		vo.setVfree0(null);
		s1 = vo.getWholeFreeItemValueOld();
		vo.setVfree0(null);
		s2 = vo.getWholeFreeItemValue();
		if (!s1.equals(s2))
			Logger.error("ERROR HALF 1-2->" + s1 + "!=" + s2);
		Logger.info("vo check is OK!");
		// =============================== HALF 2=================
		vo.setVfree1("aaa1");
		vo.setVfreename1(null);
		vo.setVfree2("aaa2");
		vo.setVfreename2(null);
		vo.setVfree3("aaa3");
		vo.setVfreename3(null);
		vo.setVfree4("aaa4");
		vo.setVfreename4(null);
		vo.setVfree5("aaa5");
		vo.setVfreename5(null);

		for (int i = 0; i < loop; i++) {
			vo.setVfree0(null);
			vo.getWholeFreeItemOld();
		}

		for (int i = 0; i < loop; i++) {
			vo.setVfree0(null);
			vo.getWholeFreeItem();
		}

		vo.setVfree0(null);
		s1 = vo.getWholeFreeItemOld();
		vo.setVfree0(null);
		s2 = vo.getWholeFreeItem();
		if (!s1.equals(s2))
			Logger.error("ERROR HALF 2-1->" + s1 + "!=" + s2);

		vo.setVfree0(null);
		s1 = vo.getWholeFreeItemValueOld();
		vo.setVfree0(null);
		s2 = vo.getWholeFreeItemValue();
		if (!s1.equals(s2))
			Logger.error("ERROR HALF 2-2->" + s1 + "!=" + s2);
		Logger.info("vo check is OK!");

	}
}
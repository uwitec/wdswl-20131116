package nc.vo.zmpub.pub.report2;
import nc.ui.bd.ref.AbstractRefModel;
/**
 * ���������ļ�����
 * @author mlr
 */
public class ConfigRefModel extends AbstractRefModel {
	private int m_DefaultFieldCount= 4;
	private String[] m_aryFieldCode= { "detatil","nodecode", };
	private String[] m_aryFieldName= { "�����ļ�������Ϣ","���ܽڵ�"  };
	private String m_sPkFieldCode= "pk_config";
	private String m_sRefTitle= "���������ļ�ѡ����տ�";
	private String m_sTableName= "zm_config";
	private String sqlWherePart = "  isnull(dr,0)=0 ";
	/**
	 * RouteRefModel ������ע�⡣
	 */
	public ConfigRefModel() {
		super();
	}

	/**
	 * getDefaultFieldCount ����ע�⡣
	 */
	public int getDefaultFieldCount() {
		return m_DefaultFieldCount;
	}
	/**
	 * ��ʾ�ֶ��б�
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldCode() {
		return m_aryFieldCode;
	}
	/**
	 * ��ʾ�ֶ�������
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldName() {
		return m_aryFieldName;
	}
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2001-9-6 10:56:48)
	 */
	public String[] getHiddenFieldCode() {
		return new String[] { m_sPkFieldCode };
	}
	/**
	 * �����ֶ���
	 * @return java.lang.String
	 */
	public String getPkFieldCode() {
		return m_sPkFieldCode;
	}
	/**
	 * ���ձ���
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getRefTitle() {
		return m_sRefTitle;
	}
	/**
	 * �������ݿ�������ͼ��
	 * �������ڣ�(01-4-4 0:57:23)
	 * @return java.lang.String
	 */
	public String getTableName() {
		return m_sTableName;
	}
	public String getWherePart() {
		return sqlWherePart;
	}
	public boolean isCacheEnabled() {
		return false;
	}
}

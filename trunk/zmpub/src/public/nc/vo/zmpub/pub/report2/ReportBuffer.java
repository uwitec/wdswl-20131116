package nc.vo.zmpub.pub.report2;
import java.io.Serializable;

import nc.vo.pub.lang.UFBoolean;
/**
 * ����������� ���� ������水ť �Զ��������ݿ�
 * @author mlr
 * 
 */
public class ReportBuffer implements Serializable{
	private static final long serialVersionUID = 144801743456602307L;
	private String pk_config=null;//����
	private String detatil=null;//������Ϣ
	private String nodecode = null;// ���ܽڵ��
	// ��������
	private String[] strRows = null;// ������
	private String[] strCols = null;// ������
	private String[] strVals = null;// ����ֵ
	private UFBoolean istotal=null;//
	public UFBoolean getIstotal() {
		return istotal;
	}

	public String getDetatil() {
		return detatil;
	}

	public void setDetatil(String detatil) {
		this.detatil = detatil;
	}

	public void setIstotal(UFBoolean istotal) {
		this.istotal = istotal;
	}

	private Integer  lel=null;//���ܼ���
	// �ϼ�����
	private UFBoolean issub = null;// �Ƿ�С��
	private UFBoolean issum = null;// �Ƿ�ϼ�
	private String[] totfields = null;// �ϼ�γ��
	private String[] totfieldsNames = null;// �ϼ�γ������
    
	
	
	public Integer getLel() {
		return lel;
	}

	public void setLel(Integer lel) {
		this.lel = lel;
	}

	public String getPk_config() {
		return pk_config;
	}

	public void setPk_config(String pk_config) {
		this.pk_config = pk_config;
	}

	public String getNodecode() {
		return nodecode;
	}

	public void setNodecode(String nodecode) {
		this.nodecode = nodecode;
	}

	public String[] getStrRows() {
		return strRows;
	}

	public void setStrRows(String[] strRows) {
		this.strRows = strRows;
	}

	public String[] getStrCols() {
		return strCols;
	}

	public void setStrCols(String[] strCols) {
		this.strCols = strCols;
	}

	public String[] getStrVals() {
		return strVals;
	}

	public void setStrVals(String[] strVals) {
		this.strVals = strVals;
	}

	public UFBoolean getIssub() {
		return issub;
	}

	public void setIssub(UFBoolean issub) {
		this.issub = issub;
	}

	public UFBoolean getIssum() {
		return issum;
	}

	public void setIssum(UFBoolean issum) {
		this.issum = issum;
	}

	public String[] getTotfields() {
		return totfields;
	}

	public void setTotfields(String[] totfields) {
		this.totfields = totfields;
	}

	public String[] getTotfieldsNames() {
		return totfieldsNames;
	}

	public void setTotfieldsNames(String[] totfieldsNames) {
		this.totfieldsNames = totfieldsNames;
	}

}

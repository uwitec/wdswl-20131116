package nc.vo.zmpub.pub.report;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;

/**
 * ��������vo
 * 
 * @author mlr
 */
public class ReportBufferVO extends SuperVO {

//	 create table zm_config(
//			   pk_config char(20),
//			   nodecode char(50),
//			   strRows varchar(1000),
//			   strCols varchar(1000),
//			   strVals varchar(1000),
//			   lel varchar(1000),
//			   istotal char(1),
//			   issub char(1),
//			   issum char(1),
//			   totfields varchar(1000),
//			   totfieldsNames varchar(1000),
//			  detatil varchar(1000),
//			    ts                  CHAR(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
//			    dr                  NUMBER(10) default 0
//
//			   )
	private String detatil=null;//������Ϣ
	
	private String pk_config;// ����

	private String nodecode = null;// ���ܽڵ��

	// ��������
	private String strRows = null;// ������ ��&��Ϊ�ָ���
	private String strCols = null;// ������ ��&��Ϊ�ָ���
	private String strVals = null;// ����ֵ ��&��Ϊ�ָ���
	private UFBoolean istotal = null;//
	private Integer lel = null;// ���ܼ���

	// �ϼ�����
	private UFBoolean issub = null;// �Ƿ�С��
	private UFBoolean issum = null;// �Ƿ�ϼ�
	private String totfields = null;// �ϼ�γ�� ��&��Ϊ�ָ���
	private String totfieldsNames = null;// �ϼ�γ������ ��&��Ϊ�ָ���

	private UFDateTime ts;
	private Integer dr;

	public UFBoolean getIstotal() {
		return istotal;
	}

	public void setIstotal(UFBoolean istotal) {
		this.istotal = istotal;
	}

	public UFDateTime getTs() {
		return ts;
	}

	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

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

	public String getStrRows() {
		return strRows;
	}

	public void setStrRows(String strRows) {
		this.strRows = strRows;
	}

	public String getStrCols() {
		return strCols;
	}

	public void setStrCols(String strCols) {
		this.strCols = strCols;
	}

	public String getStrVals() {
		return strVals;
	}

	public void setStrVals(String strVals) {
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

	public String getTotfields() {
		return totfields;
	}

	public void setTotfields(String totfields) {
		this.totfields = totfields;
	}

	public String getTotfieldsNames() {
		return totfieldsNames;
	}

	public void setTotfieldsNames(String totfieldsNames) {
		this.totfieldsNames = totfieldsNames;
	}

	@Override
	public String getPKFieldName() {

		return "pk_config";
	}

	@Override
	public String getParentPKFieldName() {

		return null;
	}

	@Override
	public String getTableName() {

		return "zm_config";
	}

	@Override
	public String getPrimaryKey() {

		return pk_config;
	}

	public String getDetatil() {
		return detatil;
	}

	public void setDetatil(String detatil) {
		this.detatil = detatil;
	}

}

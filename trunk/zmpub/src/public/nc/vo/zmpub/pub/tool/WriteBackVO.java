package nc.vo.zmpub.pub.tool;

import java.io.Serializable;

import nc.vo.pub.lang.UFDouble;

/**
 * ���ڷ�װ��д���ݵ�VO
 * 
 * @author mlr
 * @date 2011-9-13
 */
public class WriteBackVO implements Serializable {

	private static final long serialVersionUID = -8671328730444062598L;
	private String vsourcebillrowid;// ��Դ������id
	private String vsourcebilltype;// ��Դ��������
	private String vsourcebillid;// ��Դ����id
	private UFDouble[] nums;// Ҫ��д������
	private String[] numsnames;// Ҫ��д����Դ������������
	private String[] numsnames1;// ����ֶ�����
	private String idname;// Ҫ��д�ı����������
	private String idvalue;// Ҫ��д�ı������ֵ
	private String sourcetablename;// Ҫ��д�ı������
	private String tablename;// ����
	private String id;// ������
	private String idname1;// ����������
	// ��д��sql���
	private StringBuffer writeBackSql = new StringBuffer();
	// ��ѯ �ɵ����� ��sql
	private StringBuffer queryOldSql = new StringBuffer();

	public WriteBackVO(String vsourcebillrowid, String vsourcebilltype,
			String vsourcebillid, UFDouble[] nums, String idname,
			String idvalue, String sourcetablename, String tablename) {
		super();
		this.vsourcebillrowid = vsourcebillrowid;
		this.vsourcebilltype = vsourcebilltype;
		this.vsourcebillid = vsourcebillid;
		this.nums = nums;
		this.idname = idname;
		this.idvalue = idvalue;
		this.sourcetablename = sourcetablename;
		this.tablename = tablename;
	}

	public WriteBackVO() {
		super();
	}

	public String getWriteBackSql() {
		writeBackSql.setLength(0);
		writeBackSql.append(" update " + sourcetablename);
		writeBackSql.append(" set ");
		for (int i = 0; i < numsnames.length; i++) {
			writeBackSql.append(numsnames[i] + "=coalesce(" + numsnames[i]
					+ ",0.0)+ " + nums[i] + " ");
			if (i < numsnames.length - 1) {
				writeBackSql.append(" ,");
			}
		}
		writeBackSql.append(" where  ");
		writeBackSql.append(" isnull(dr,0)=0 ");
		writeBackSql.append(" and " + idname + "='" + idvalue + "'");
		return writeBackSql.toString();
	}

	public String getQueryOldSql() {
		queryOldSql.setLength(0);
		queryOldSql.append(" select ");
		for (int i = 0; i < numsnames1.length; i++) {

			queryOldSql.append(numsnames1[i]);
			if (i < numsnames1.length - 1) {
				queryOldSql.append(" ,");
			}
		}
		queryOldSql.append(" from " + tablename + " ");
		queryOldSql.append(" where ");
		queryOldSql.append(idname1 + "='" + id + "'");
		return queryOldSql.toString();
	}

	public String[] getNumsnames1() {
		return numsnames1;
	}

	public void setNumsnames1(String[] numsnames1) {
		this.numsnames1 = numsnames1;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdname1() {
		return idname1;
	}

	public void setIdname1(String idname1) {
		this.idname1 = idname1;
	}

	public String[] getNumsnames() {
		return numsnames;
	}

	public void setNumsnames(String[] numsnames) {
		this.numsnames = numsnames;
	}

	public String getIdname() {
		return idname;
	}

	public void setIdname(String idname) {
		this.idname = idname;
	}

	public String getIdvalue() {
		return idvalue;
	}

	public void setIdvalue(String idvalue) {
		this.idvalue = idvalue;
	}

	public String getVsourcebillrowid() {
		return vsourcebillrowid;
	}

	public void setVsourcebillrowid(String vsourcebillrowid) {
		this.vsourcebillrowid = vsourcebillrowid;
	}

	public String getVsourcebilltype() {
		return vsourcebilltype;
	}

	public void setVsourcebilltype(String vsourcebilltype) {
		this.vsourcebilltype = vsourcebilltype;
	}

	public String getVsourcebillid() {
		return vsourcebillid;
	}

	public void setVsourcebillid(String vsourcebillid) {
		this.vsourcebillid = vsourcebillid;
	}

	public UFDouble[] getNums() {
		return nums;
	}

	public void setNums(UFDouble[] nums) {
		this.nums = nums;
	}

	public String getSourcetablename() {
		return sourcetablename;
	}

	public void setSourcetablename(String sourcetablename) {
		this.sourcetablename = sourcetablename;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
}

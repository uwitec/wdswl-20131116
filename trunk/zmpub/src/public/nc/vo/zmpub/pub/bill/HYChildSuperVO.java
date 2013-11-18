package nc.vo.zmpub.pub.bill;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

/**
 * ��׼�����ӱ�vo
 * 
 * @author zhf
 */
public abstract class HYChildSuperVO extends SuperVO {
	/**
	 * ��ҵsupervo 10���Զ����� 5��pk_defdoc 3���ַ���Ԥ���� 5������Ԥ���� 2������Ԥ����
	 */

	public String crowno;// �к�
	public Integer irowstatus;// ��״̬
	public Integer idatasource;// 0 Ϊ���� 1Ϊ�ϱ�
	public Integer ieditcount;// �޶�����

	public Integer getIdatasource() {
		return idatasource;
	}

	public void setIdatasource(Integer idatasource) {
		this.idatasource = idatasource;
	}

	public Integer getIeditcount() {
		return ieditcount;
	}

	public void setIeditcount(Integer ieditcount) {
		this.ieditcount = ieditcount;
	}

	// �������� ���⼸���ֶ� ����������
	// ����Ҹ� ui������֧�ֵ�
	// ������ �����޸� ���׵������ݲ���������� ui����ֻ��vlastbillid ����Դ��
	// �����¼���ε�id���ֶβ���vlastbillid
	// ��ô�ᵼ�� ���������ݵļ���ʧ�� ���׵��²���������ݴ���
	// ��¼��Դ �� Դͷ���ݵ��ֶ� ��ʼ
	public String vlastbilltype;// ��Դ�������� ��¼���� ���κ��ĵ����õ�
	public String vlastbillid;// ��Դ����ID
	public String vlastbillrowid;// ��Դ����RowID
	public String vsourcebilltype;// Դͷ��������
	public String vsourcebillid;// Դͷ����ID
	public String vsourcebillrowid;// Դͷ����RowID
	// ��¼��Դ �� Դͷ���ݵ��ֶ� ����
	public String vmemo;// ��ע
	public String vdef1;
	public String vdef2;
	public String vdef3;
	public String vdef4;
	public String vdef5;
	public String vdef6;
	public String vdef7;
	public String vdef8;
	public String vdef9;
	public String vdef10;

	public String pk_defdoc1;
	public String pk_defdoc2;
	public String pk_defdoc3;
	public String pk_defdoc4;
	public String pk_defdoc5;

	public String vreserve1;
	public String vreserve2;
	public String vreserve3;

	public UFDouble nreserve1;
	public UFDouble nreserve2;
	public UFDouble nreserve3;
	public UFDouble nreserve4;
	public UFDouble nreserve5;

	public UFBoolean ureserve1;
	public UFBoolean ureserve2;
	public UFBoolean ureserve3;

	public UFDateTime ts;

	public Integer dr;

	public static String vlastbilltype1 = "vlastbilltype";// ��Դ�������� ��¼����
															// ���κ��ĵ����õ�
	public static String vlastbillid1 = "vlastbillid";// ��Դ����ID
	public static String vlastbillrowid1 = "vlastbillrowid";// ��Դ����RowID
	public static String vsourcebilltype1 = "vsourcebilltype";// Դͷ��������
	public static String vsourcebillid1 = "vsourcebillid";// Դͷ����ID
	public static String vsourcebillrowid1 = "vsourcebillrowid";// Դͷ����RowID

	public String getVmemo() {
		return vmemo;
	}

	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public UFDateTime getTs() {
		return ts;
	}

	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	public String getVdef1() {
		return vdef1;
	}

	public void setVdef1(String vdef1) {
		this.vdef1 = vdef1;
	}

	public String getVdef2() {
		return vdef2;
	}

	public void setVdef2(String vdef2) {
		this.vdef2 = vdef2;
	}

	public String getVdef3() {
		return vdef3;
	}

	public void setVdef3(String vdef3) {
		this.vdef3 = vdef3;
	}

	public String getVdef4() {
		return vdef4;
	}

	public void setVdef4(String vdef4) {
		this.vdef4 = vdef4;
	}

	public String getVdef5() {
		return vdef5;
	}

	public void setVdef5(String vdef5) {
		this.vdef5 = vdef5;
	}

	public String getVdef6() {
		return vdef6;
	}

	public void setVdef6(String vdef6) {
		this.vdef6 = vdef6;
	}

	public String getVdef7() {
		return vdef7;
	}

	public void setVdef7(String vdef7) {
		this.vdef7 = vdef7;
	}

	public String getVdef8() {
		return vdef8;
	}

	public void setVdef8(String vdef8) {
		this.vdef8 = vdef8;
	}

	public String getVdef9() {
		return vdef9;
	}

	public void setVdef9(String vdef9) {
		this.vdef9 = vdef9;
	}

	public String getVdef10() {
		return vdef10;
	}

	public void setVdef10(String vdef10) {
		this.vdef10 = vdef10;
	}

	public String getPk_defdoc1() {
		return pk_defdoc1;
	}

	public void setPk_defdoc1(String pk_defdoc1) {
		this.pk_defdoc1 = pk_defdoc1;
	}

	public String getPk_defdoc2() {
		return pk_defdoc2;
	}

	public void setPk_defdoc2(String pk_defdoc2) {
		this.pk_defdoc2 = pk_defdoc2;
	}

	public String getPk_defdoc3() {
		return pk_defdoc3;
	}

	public void setPk_defdoc3(String pk_defdoc3) {
		this.pk_defdoc3 = pk_defdoc3;
	}

	public String getPk_defdoc4() {
		return pk_defdoc4;
	}

	public void setPk_defdoc4(String pk_defdoc4) {
		this.pk_defdoc4 = pk_defdoc4;
	}

	public String getPk_defdoc5() {
		return pk_defdoc5;
	}

	public void setPk_defdoc5(String pk_defdoc5) {
		this.pk_defdoc5 = pk_defdoc5;
	}

	public String getVreserve1() {
		return vreserve1;
	}

	public void setVreserve1(String vreserve1) {
		this.vreserve1 = vreserve1;
	}

	public String getVreserve2() {
		return vreserve2;
	}

	public void setVreserve2(String vreserve2) {
		this.vreserve2 = vreserve2;
	}

	public String getVreserve3() {
		return vreserve3;
	}

	public void setVreserve3(String vreserve3) {
		this.vreserve3 = vreserve3;
	}

	public String getCrowno() {
		return crowno;
	}

	public void setCrowno(String crowno) {
		this.crowno = crowno;
	}

	public Integer getIrowstatus() {
		return irowstatus;
	}

	public void setIrowstatus(Integer irowstatus) {
		this.irowstatus = irowstatus;
	}

	public UFDouble getNreserve4() {
		return nreserve4;
	}

	public void setNreserve4(UFDouble nreserve4) {
		this.nreserve4 = nreserve4;
	}

	public UFDouble getNreserve5() {
		return nreserve5;
	}

	public void setNreserve5(UFDouble nreserve5) {
		this.nreserve5 = nreserve5;
	}

	public UFDouble getNreserve1() {
		return nreserve1;
	}

	public void setNreserve1(UFDouble nreserve1) {
		this.nreserve1 = nreserve1;
	}

	public UFDouble getNreserve2() {
		return nreserve2;
	}

	public void setNreserve2(UFDouble nreserve2) {
		this.nreserve2 = nreserve2;
	}

	public UFDouble getNreserve3() {
		return nreserve3;
	}

	public void setNreserve3(UFDouble nreserve3) {
		this.nreserve3 = nreserve3;
	}

	public UFBoolean getUreserve1() {
		return ureserve1;
	}

	public void setUreserve1(UFBoolean ureserve1) {
		this.ureserve1 = ureserve1;
	}

	public UFBoolean getUreserve2() {
		return ureserve2;
	}

	public void setUreserve2(UFBoolean ureserve2) {
		this.ureserve2 = ureserve2;
	}

	public UFBoolean getUreserve3() {
		return ureserve3;
	}

	public void setUreserve3(UFBoolean ureserve3) {
		this.ureserve3 = ureserve3;
	}

	public String getVlastbilltype() {
		return vlastbilltype;
	}

	public void setVlastbilltype(String vlastbilltype) {
		this.vlastbilltype = vlastbilltype;
	}

	public String getVlastbillid() {
		return vlastbillid;
	}

	public void setVlastbillid(String vlastbillid) {
		this.vlastbillid = vlastbillid;
	}

	public String getVlastbillrowid() {
		return vlastbillrowid;
	}

	public void setVlastbillrowid(String vlastbillrowid) {
		this.vlastbillrowid = vlastbillrowid;
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

	public String getVsourcebillrowid() {
		return vsourcebillrowid;
	}

	public void setVsourcebillrowid(String vsourcebillrowid) {
		this.vsourcebillrowid = vsourcebillrowid;
	}

}

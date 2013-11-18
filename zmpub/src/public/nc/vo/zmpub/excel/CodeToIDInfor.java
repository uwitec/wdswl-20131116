package nc.vo.zmpub.excel;

import nc.vo.pub.ValidationException;
import nc.vo.pub.ValueObject;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;

public class CodeToIDInfor extends ValueObject {
	
//	��֪����������   ��������������Ϣ �����ݽṹ�޷���װ  �û��Զ���ʵ�ְ�
	
	private String idname;//����   ��ת������������ڵ�ID�ֶ�����
	private String tablename;//����  ��ת�������漰�ĵ�����
	private String codename;//����   �������ڱ����ֶ�����
	private String thiscodename;//��ǰ���ڱ����ֶ�����    ����
	private String codevalue;//------------�����ڸ�ֵ
	
	private String corpname;//����	 ��˾�ֶ�����   �漰�Ļ��������ڵĹ�˾�ֶε�����
	private String corpvalue;//------------�����ڸ�ֵ   ��˾ֵ
	public UFBoolean isBasic = UFBoolean.FALSE;//�Ƿ��Ʒ��������  //����
	public UFBoolean isCorp = UFBoolean.FALSE;//�����Ƿ�˾��  //����
	public UFBoolean isCorpField = UFBoolean.FALSE;//��˾�ֶ�  ��˾����ת��Ϊ��˾ID  ��������ת��   //����
	public UFBoolean isDefTran = UFBoolean.FALSE;//�Ƿ��Զ���  ת��
	public UFBoolean isCache = UFBoolean.TRUE;//�Ƿ񻺴�  ����
	public UFBoolean isSave = UFBoolean.TRUE;//�Ƿ񱣴���ֶε�ֵ  �粻������ ����ֵ��vo  һ�����ڹ�˾ �������û����
	private String defTranClassName = null;//�Զ������ת����  �̳� ideftran �ӿ�
	
	

	public String getThiscodename() {
		return thiscodename;
	}

	public void setThiscodename(String thiscodename) {
		this.thiscodename = thiscodename;
	}

	public String getDefTranClassName() {
		return defTranClassName;
	}

	public void setDefTranClassName(String defTranClassName) {
		this.defTranClassName = defTranClassName;
	}

	public String getSelectSql(){
		if(PuPubVO.getString_TrimZeroLenAsNull(codevalue)==null)
			return null;
		StringBuffer str = new StringBuffer();
		str.append("select ");
		str.append(idname);
		str.append(" from ");
		str.append(tablename);
		str.append(" where ");
		str.append(codename);
		str.append(" = '"+codevalue+"' ");
		if(isCorp.booleanValue()){
			str.append(" and ");
			str.append(corpname);
			str.append(" = '"+corpvalue+"' ");
		}
		str.append(" and nvl(dr,0)=0 ");
		return str.toString();
	}
	
	public UFBoolean getIsCorp() {
		return isCorp;
	}

	public void setIsCorp(UFBoolean isCorp) {
		this.isCorp = isCorp;
	}

	public String getFomular(){
		StringBuffer str = new StringBuffer();
		str.append(idname);
		if(isCorp.booleanValue()){
			str.append("->getcolvalue2(");
			str.append(tablename);
			str.append(","+idname);
			str.append(",");
			str.append(codename);
			str.append(",");
			str.append(thiscodename);
			
			str.append(",");
			str.append(corpname);
			str.append(",");
			str.append(corpname);
			
			str.append(")");
			return str.toString();
		}
		str.append("->getColValue(");
		str.append(tablename);
		str.append(","+idname);
		str.append(",");
		str.append(codename);
		str.append(",");
		str.append(thiscodename);
		str.append(")");
		return str.toString();
	}

	public String getIdname() {
		return idname;
	}

	public void setIdname(String idname) {
		this.idname = idname;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getCodename() {
		return codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}

	public String getCorpname() {
		return corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

	public String getCodevalue() {
		return codevalue;
	}

	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}

	public String getCorpvalue() {
		return corpvalue;
	}

	public void setCorpvalue(String corpvalue) {
		this.corpvalue = corpvalue;
	}

	public UFBoolean getIsBasic() {
		return isBasic;
	}

	public void setIsBasic(UFBoolean isBasic) {
		this.isBasic = isBasic;
	}

	@Override
	public String getEntityName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate() throws ValidationException {
		// TODO Auto-generated method stub

	}

}

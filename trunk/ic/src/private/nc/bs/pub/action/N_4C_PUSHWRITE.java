package nc.bs.pub.action;


import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.uap.pf.PFBusinessException;
/**
 * [�������۳���]->��Ӧ��[���۳���]
 * @author Administrator
 *
 */
public class N_4C_PUSHWRITE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_4C_SAVEBASE ������ע�⡣
 */
public N_4C_PUSHWRITE() {
  super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
  super.m_tmpVo=vo;
  //####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
//*************��ƽ̨ȡ���ɸö����������ڲ�����������ȡ����Ҫ�����VO��***********
Object inCurObject=getVo();
Object inPreObject=getUserObj();
StringBuffer sErr=new StringBuffer();
Object retObj=null;
Object retObjPk=null;
//1,���ȼ�鴫����������Ƿ�Ϸ����Ƿ�Ϊ�ա�
if(!(inCurObject instanceof  nc.vo.ic.pub.bill.GeneralBillVO)) throw new nc.vo.pub.BusinessException("Remote Call",new nc.vo.pub.BusinessException("������ϣ������Ŀ�����۳������Ͳ�ƥ��"));
if(inCurObject == null)  throw new nc.vo.pub.BusinessException("Remote Call",new nc.vo.pub.BusinessException("������ϣ������Ŀ�����۳���û������"));
//2,���ݺϷ���������ת��Ϊ������۳��⡣
nc.vo.ic.pub.bill.GeneralBillVO inCurVO=null;
nc.vo.ic.pub.bill.GeneralBillVO inPreVO=null;
if(inCurObject !=null)
 inCurVO=(nc.vo.ic.pub.bill.GeneralBillVO)inCurObject;
if(inPreObject !=null) inPreVO=(nc.vo.ic.pub.bill.GeneralBillVO)inPreObject;
inCurObject=null;
inPreObject=null;
//��ȡƽ̨����Ĳ���
setParameter("INCURVO",inCurVO);
setParameter("INPREVO",inPreVO);
//################################################
//Ϊ�޸�ҵ��Ӧ�ձ���ĸ���
//��д�����Ͽͻ���ҵ��Ӧ�ա�ע������������ô˷����Ļ���Ӧע�͵�SIGN&CANCELSIGN����ͬ����
//������ظ���д��������ݴ���
if(inCurVO!=null)
 setParameter("INCURVOARAP",(nc.vo.ic.pub.bill.GeneralBillVO)inCurVO.clone());
if(inPreVO!=null)
  setParameter("INPREVOARAP",(nc.vo.ic.pub.bill.GeneralBillVO)inPreVO.clone());
//################################################
//����˵��:����Ƿ���ʡ�<������>
//Ŀǰ�Ǹ��ݵ��ݱ���ҵ�����ڼ�顣������ݵ�¼���ڼ�飬�뽫checkAccountStatus��ΪcheckAccountStatus1
runClass("nc.bs.ic.ic2a3.AccountctrlDMO","checkAccountStatus","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//###########################################################################
String sBillCode=null;
nc.vo.scm.pub.IBillCode ibc=(nc.vo.scm.pub.IBillCode)inCurVO;
setParameter("IBC",ibc);
ArrayList alRet=new ArrayList();
try{
//�÷���<��������>
//����˵��:����浥��ʱ���
runClass("nc.bs.ic.pub.check.CheckDMO","checkTimeStamp","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//����˵��:���vmi�ֿ�ʱ������Ĺ�Ӧ�̱���
if (! ( "4S".equals (inCurVO.getHeaderVO ().getCbilltypecode ()))) {
runClass( "nc.bs.ic.pub.check.CheckInvVendorDMO", "checkVmiVendorInput", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
}
//�÷���<��������>
//����˵��:����Ƿ��е��ݺţ����û�У�ϵͳ�Զ�������
sBillCode=(String)runClass("nc.bs.ic.pub.check.CheckDMO","setBillCode","&IBC:nc.vo.scm.pub.IBillCode",vo,m_keyHas,m_methodReturnHas);//�÷���<��������>
//#################
//У�����������ʹ������ �÷�����������
String sBarcodeCheckErr=(String)runClass("nc.bs.ic.pub.check.CheckBarcodeDMO","checkBarcodeAbsent","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
if (sBarcodeCheckErr!=null )
sErr.append(sBarcodeCheckErr);
//#################
//У�������˻����������Ƿ�����ڶ�Ӧ�ͻ����۳��ⵥ������  �÷�����������
sBarcodeCheckErr=(String)runClass("nc.bs.ic.pub.check.CheckBarcodeDMO","checkSaleConsist","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
if (sBarcodeCheckErr!=null )
sErr.append(sBarcodeCheckErr);
//#################
//###########################################################################
//zhx add ���۳��ⵥ�Ƿ��Ѿ�����ǩ�յļ�飬to  check whether can modify  20021113
Object oUpdated  =null;
oUpdated =runClass("nc.bs.ic.pub.ictodm.IC2DMInterface","isBillUpdated","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
if(oUpdated== null||!((UFBoolean)oUpdated).booleanValue()){
  throw new nc.vo.pub.BusinessException("Remote Call",new nc.vo.pub.BusinessException("���󣺿�����۳����Ѿ�����ǩ�գ������޸�! "));
}
 //����˵��:�ֿ�����Դ���ֵ�������Ĳֿ��Ƿ���ͬһ�ɱ���
runClass("nc.bs.ic.pub.check.CheckDMO","isSameCostOrg","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas); 
  
//###########################################################################
//����˵��:��������������кŽ���
setParameter("userID",inCurVO.getParentVO().getAttributeValue("coperatoridnow"));
setParameter("sDate",getUserDate().toString().substring(0,10));
//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
//����˵��:���ⵥ�����������������кŽ���
runClass("nc.bs.ic.pub.freeze.FreezeDMO","unLockInv","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&userID:STRING,&sDate:STRING",vo,m_keyHas,m_methodReturnHas);
//#################################################
//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
//����˵��:�����������ϼ����� 2004-06-24 yangbo˵ȡ��
//runClass( "nc.bs.so.pub.CheckApproveDMO", "isOutAppRequst", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//##################################################
//�÷���<��������>
//����˵��:����Ƿ������˼����
runClass( "nc.bs.ic.pub.check.CheckDMO", "isPicked", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//#############################################################     
//�÷���<��������>
//����˵��:��������ε�ʧЧ�����Ƿ�һ��
runClass("nc.bs.ic.pub.check.CheckDMO","checkInvalidateDate","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//���ת����ҵ�����Ͳ�������ļ��
Object oIsSpecialBizType=runClass("nc.bs.ic.pub.check.CheckDMO","checkIsSpecifyBusiType","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//����true:�ǽ��ת����ҵ������
if(oIsSpecialBizType!=null && (new UFBoolean(oIsSpecialBizType.toString())).booleanValue()==false){
 //�÷���<��������>
 //����˵��:����������Ƿ�������
 runClass("nc.bs.ic.pub.check.CheckBusiDMO","checkRelativeBill","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
}
//�÷���<��������>
//����˵��:����浥�ݺ��Ƿ��ظ�
runClass("nc.bs.ic.pub.check.CheckDMO","checkBillCodeFore","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//##################################################
//�����ݵ��ݸ������Ϳ�浥λ֮���ת�������û������ҵ����ʵʩ��Աע�͵���
runClass("nc.bs.ic.pub.bill.DesassemblyBO","setMeasRateVO","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//���ݱ���ǰ����ͳһ����
runClass("nc.bs.ic.pub.BillActionBase","beforeSave","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//����˵��:���ݱ���
retObjPk=runClass("nc.bs.ic.ic211.GeneralHBO","saveBill","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//�÷���<������>
//����˵��:��������
Object s2=runClass("nc.bs.ic.pub.bill.GeneralBillBO","makeBothToZeroOnly","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
if(s2!=null)
 sErr.append((String)s2);
//�÷���<������>
//����˵��:����
runClass("nc.bs.ic.ic211.GeneralHBO","processOutBillMny","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//������䣬���û������ҵ����ʵʩ��Աע�͵���
runClass("nc.bs.ic.pub.bill.DesassemblyBO","exeDesassembly","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//#########################################################################
//����Ӧ��Ӧ��
//����Ӧ�գ�������##################################zpm ע��#####################################################################################################
//runClass("nc.bs.ic.ic211.GeneralHDMO","renovateARWhenSave","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//#######################################################################################################################################
//��д�ۼƳ�������
runClass("nc.bs.ic.pub.RewriteDMO","reWriteCorNum","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
if(oIsSpecialBizType!=null && (new UFBoolean(oIsSpecialBizType.toString())).booleanValue()==false){
//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
if(inCurVO.isHaveSourceBill()){
//����˵��:��浥�ݱ���ʱ�޸Ŀ�����
        runClass("nc.bs.ic.pub.bill.ICATP","modifyATP","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
}
//�÷���<��������>
//����˵��:�����������������һ��,���θ����,��������
runClass("nc.bs.ic.pub.check.CheckDMO","checkDBL_New","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<������>
//����˵��:��鹩Ӧ�̹����������
//runClass("nc.bs.ic.pub.check.CheckInvVendorDMO","checkInvQtyNewVendor_New","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<��������>
//����˵��:����������Ƿ������⸺���
runClass("nc.bs.ic.pub.check.CheckDMO","checkInOutTrace","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<������>
//����˵��:����λ�����Ƿ񳬳�
//runClass("nc.bs.ic.pub.check.CheckDMO","checkCargoVolumeOut","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
}
//�÷���<��������>
//����˵��:������Ƿ��Ѿ����䵽�����֯
runClass("nc.bs.ic.pub.check.CheckDMO","checkCalBodyInv_New","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
if(oIsSpecialBizType!=null && (new UFBoolean(oIsSpecialBizType.toString())).booleanValue()==false){
//�÷���<��������>
//����˵��:������̶���λ
runClass("nc.bs.ic.pub.check.CheckDMO","checkFixSpace","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<��������>
//����˵��:������������
runClass("nc.bs.ic.pub.check.CheckDMO","checkPlaceAlone","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<������>
//����˵��:�����߿�桢��Ϳ�桢��ȫ��桢�ٶ�����
Object s1=runClass("nc.bs.ic.pub.check.CheckDMO","checkParam_new","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
if(s1!=null)
 sErr.append((String)s1);
} 
//������ú����ڵ���ʾ
if (inCurVO.m_sErr!=null)
sErr.append(inCurVO.m_sErr);
//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
//����˵������д���ֵ������ⵥ�ۼ��˻�����
runClass("nc.bs.ic.pub.RewriteDMO","rewriteOutRetNumFor4C4Y","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//����˵�������۳��ⵥ��д���˵��������� 20021113
runClass("nc.bs.ic.pub.RewriteDMO","reWriteDMNew","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//����˵��:���۳��ⵥ����ʱ��д���۵��ݳ�������
runClass("nc.bs.ic.pub.RewriteDMO","reWriteSaleNewBatch","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//##################################################
//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
//����˵��:��������״̬
//runClass("nc.bs.so.pub.DataControlDMO","autoSetInventoryFinish","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�������ǰ�����������Ƿ�ƥ��
if(retObjPk != null && !(retObjPk instanceof ArrayList))  throw new nc.vo.pub.BusinessException("Remote Call",new nc.vo.pub.BusinessException("���󣺱��涯���ķ���ֵ���ʹ���"));
//##################################################
//############################
//����ҵ����־���÷�����������
setParameter("ERR",sErr.toString());
setParameter("FUN","���棨������");
runClass("nc.bs.ic.pub.check.CheckDMO","insertBusinesslog","&INCURVO:nc.vo.pub.AggregatedValueObject,&ERR:String,&FUN:String",vo,m_keyHas,m_methodReturnHas);
//############################
if(sErr.toString().trim().length()==0)
  alRet.add(null);
else
  alRet.add(sErr.toString());
alRet.add(retObjPk);
if(inCurVO.isHaveSourceBill()){
//����˵��:��������ʱ���,������ 
        runClass("nc.bs.ic.pub.bill.ICATP","checkAtpInstantly","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
}
//���С�͵���VO��ǰ̨���� 
nc.vo.ic.pub.smallbill.SMGeneralBillVO smbillvo = inCurVO.getSmallBillVO();
alRet.add(smbillvo);
//���ݱ������ͳһ����
runClass("nc.bs.ic.pub.BillActionBase","afterSave","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
}catch(Exception e){
//############################
//����ҵ����־���÷�����������
setParameter("EXC",e.getMessage());
setParameter("FUN","����(����)");
runClass("nc.bs.ic.pub.check.CheckBO","insertBusinessExceptionlog","&INCURVO:nc.vo.pub.AggregatedValueObject,&EXC:String,&FUN:String",vo,m_keyHas,m_methodReturnHas);
//############################
if(sBillCode!=null){
  if(e instanceof nc.vo.pub.BusinessException){
   if(((nc.vo.pub.BusinessException)e).getCause()== null ||(((nc.vo.pub.BusinessException)e).getCause()!= null && !(((nc.vo.pub.BusinessException)e).getCause() instanceof nc.vo.ic.pub.exp.BillCodeNotUnique)))
               runClass("nc.bs.ic.pub.check.CheckDMO","returnBillCode","&IBC:nc.vo.scm.pub.IBillCode",vo,m_keyHas,m_methodReturnHas);
 }else{
    if(!(e instanceof nc.vo.ic.pub.exp.BillCodeNotUnique))
      runClass("nc.bs.ic.pub.check.CheckDMO","returnBillCode","&IBC:nc.vo.scm.pub.IBillCode",vo,m_keyHas,m_methodReturnHas);
 }
}
         if (e instanceof nc.vo.pub.BusinessException)
      throw (nc.vo.pub.BusinessException) e;
    else
      throw new nc.vo.pub.BusinessException("Remote Call", e);
}
return alRet;  
//************************************************************************
} catch (Exception ex) {
  if (ex instanceof BusinessException)
    throw (BusinessException) ex;
  else 
    throw new PFBusinessException(ex.getMessage(), ex);
}
}
/*
* ��ע��ƽ̨��дԭʼ�ű�
*/
public String getCodeRemark(){
  return "  //####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####\n//*************��ƽ̨ȡ���ɸö����������ڲ�����������ȡ����Ҫ�����VO��***********\nObject inCurObject=getVo();\nObject inPreObject=getUserObj();\nStringBuffer sErr=new StringBuffer();\nObject retObj=null;\nObject retObjPk=null;\n//1,���ȼ�鴫����������Ƿ�Ϸ����Ƿ�Ϊ�ա�\nif(!(inCurObject instanceof  nc.vo.ic.pub.bill.GeneralBillVO)) throw new nc.vo.pub.BusinessException(\"Remote Call\",new nc.vo.pub.BusinessException(\"������ϣ������Ŀ�����۳������Ͳ�ƥ��\"));\nif(inCurObject == null)  throw new nc.vo.pub.BusinessException(\"Remote Call\",new nc.vo.pub.BusinessException(\"������ϣ������Ŀ�����۳���û������\"));\n//2,���ݺϷ���������ת��Ϊ������۳��⡣\nnc.vo.ic.pub.bill.GeneralBillVO inCurVO=null;\nnc.vo.ic.pub.bill.GeneralBillVO inPreVO=null;\nif(inCurObject !=null)\n inCurVO=(nc.vo.ic.pub.bill.GeneralBillVO)inCurObject;\nif(inPreObject !=null) inPreVO=(nc.vo.ic.pub.bill.GeneralBillVO)inPreObject;\ninCurObject=null;\ninPreObject=null;\n//��ȡƽ̨����Ĳ���\nsetParameter(\"INCURVO\",inCurVO);\nsetParameter(\"INPREVO\",inPreVO);\n//################################################\n//Ϊ�޸�ҵ��Ӧ�ձ���ĸ���\n//��д�����Ͽͻ���ҵ��Ӧ�ա�ע������������ô˷����Ļ���Ӧע�͵�SIGN&CANCELSIGN����ͬ����\n//������ظ���д��������ݴ���\nif(inCurVO!=null)\n setParameter(\"INCURVOARAP\",(nc.vo.ic.pub.bill.GeneralBillVO)inCurVO.clone());\nif(inPreVO!=null)\n  setParameter(\"INPREVOARAP\",(nc.vo.ic.pub.bill.GeneralBillVO)inPreVO.clone());\n//################################################\n//����˵��:����Ƿ���ʡ�<������>\n//Ŀǰ�Ǹ��ݵ��ݱ���ҵ�����ڼ�顣������ݵ�¼���ڼ�飬�뽫checkAccountStatus��ΪcheckAccountStatus1\nrunClassCom@\"nc.bs.ic.ic2a3.AccountctrlDMO\",\"checkAccountStatus\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//###########################################################################\nString sBillCode=null;\nnc.vo.scm.pub.IBillCode ibc=(nc.vo.scm.pub.IBillCode)inCurVO;\nsetParameter(\"IBC\",ibc);\nArrayList alRet=new ArrayList();\ntry{\n//�÷���<��������>\n//����˵��:����浥��ʱ���\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkTimeStamp\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//����˵��:���vmi�ֿ�ʱ������Ĺ�Ӧ�̱���\nif (! ( \"4S\".equals (inCurVO.getHeaderVO ().getCbilltypecode ()))) {\nrunClassCom@ \"nc.bs.ic.pub.check.CheckInvVendorDMO\", \"checkVmiVendorInput\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n}\n//�÷���<��������>\n//����˵��:����Ƿ��е��ݺţ����û�У�ϵͳ�Զ�������\nsBillCode=(String)runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"setBillCode\",\"&IBC:nc.vo.scm.pub.IBillCode\"@;//�÷���<��������>\n//#################\n//У�����������ʹ������ �÷�����������\nString sBarcodeCheckErr=(String)runClassCom@\"nc.bs.ic.pub.check.CheckBarcodeDMO\",\"checkBarcodeAbsent\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\nif (sBarcodeCheckErr!=null )\nsErr.append(sBarcodeCheckErr);\n//#################\n//У�������˻����������Ƿ�����ڶ�Ӧ�ͻ����۳��ⵥ������  �÷�����������\nsBarcodeCheckErr=(String)runClassCom@\"nc.bs.ic.pub.check.CheckBarcodeDMO\",\"checkSaleConsist\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\nif (sBarcodeCheckErr!=null )\nsErr.append(sBarcodeCheckErr);\n//#################\n//###########################################################################\n//zhx add ���۳��ⵥ�Ƿ��Ѿ�����ǩ�յļ�飬to  check whether can modify  20021113\nObject oUpdated  =null;\noUpdated =runClassCom@\"nc.bs.ic.pub.ictodm.IC2DMInterface\",\"isBillUpdated\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\nif(oUpdated== null||!((UFBoolean)oUpdated).booleanValue()){\n  throw new nc.vo.pub.BusinessException(\"Remote Call\",new nc.vo.pub.BusinessException(\"���󣺿�����۳����Ѿ�����ǩ�գ������޸�! \"));\n}\n //����˵��:�ֿ�����Դ���ֵ�������Ĳֿ��Ƿ���ͬһ�ɱ���\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"isSameCostOrg\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@; \n  \n//###########################################################################\n//����˵��:��������������кŽ���\nsetParameter(\"userID\",inCurVO.getParentVO().getAttributeValue(\"coperatoridnow\"));\nsetParameter(\"sDate\",getUserDate().toString().substring(0,10));\n//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\n//����˵��:���ⵥ�����������������кŽ���\nrunClassCom@\"nc.bs.ic.pub.freeze.FreezeDMO\",\"unLockInv\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&userID:STRING,&sDate:STRING\"@;\n//#################################################\n//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\n//����˵��:�����������ϼ����� 2004-06-24 yangbo˵ȡ��\n//runClassCom@ \"nc.bs.so.pub.CheckApproveDMO\", \"isOutAppRequst\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//##################################################\n//�÷���<��������>\n//����˵��:����Ƿ������˼����\nrunClassCom@ \"nc.bs.ic.pub.check.CheckDMO\", \"isPicked\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//#############################################################     \n//�÷���<��������>\n//����˵��:��������ε�ʧЧ�����Ƿ�һ��\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkInvalidateDate\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//���ת����ҵ�����Ͳ�������ļ��\nObject oIsSpecialBizType=runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkIsSpecifyBusiType\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//����true:�ǽ��ת����ҵ������\nif(oIsSpecialBizType!=null && (new UFBoolean(oIsSpecialBizType.toString())).booleanValue()==false){\n //�÷���<��������>\n //����˵��:����������Ƿ�������\n runClassCom@\"nc.bs.ic.pub.check.CheckBusiDMO\",\"checkRelativeBill\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n}\n//�÷���<��������>\n//����˵��:����浥�ݺ��Ƿ��ظ�\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkBillCodeFore\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//##################################################\n//�����ݵ��ݸ������Ϳ�浥λ֮���ת�������û������ҵ����ʵʩ��Աע�͵���\nrunClassCom@\"nc.bs.ic.pub.bill.DesassemblyBO\",\"setMeasRateVO\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n//���ݱ���ǰ����ͳһ����\nrunClasscom@\"nc.bs.ic.pub.BillActionBase\",\"beforeSave\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;" +
      "\n//����˵��:���ݱ���\nretObjPk=runClassCom@\"nc.bs.ic.ic211.GeneralHBO\",\"saveBill\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//�÷���<������>\n//����˵��:��������\nObject s2=runClassCom@\"nc.bs.ic.pub.bill.GeneralBillBO\",\"makeBothToZeroOnly\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\nif(s2!=null)\n sErr.append((String)s2);\n//�÷���<������>\n//����˵��:����\nrunClassCom@\"nc.bs.ic.ic211.GeneralHBO\",\"processOutBillMny\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//������䣬���û������ҵ����ʵʩ��Աע�͵���\nrunClassCom@\"nc.bs.ic.pub.bill.DesassemblyBO\",\"exeDesassembly\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n//#########################################################################\n//����Ӧ��Ӧ��\n//����Ӧ�գ�������\nrunClassCom@\"nc.bs.ic.ic211.GeneralHDMO\",\"renovateARWhenSave\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//��д�ۼƳ�������\nrunClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"reWriteCorNum\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\nif(oIsSpecialBizType!=null && (new UFBoolean(oIsSpecialBizType.toString())).booleanValue()==false){\n//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\nif(inCurVO.isHaveSourceBill()){\n//����˵��:��浥�ݱ���ʱ�޸Ŀ�����\n        runClassCom@\"nc.bs.ic.pub.bill.ICATP\",\"modifyATP\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n}\n//�÷���<��������>\n//����˵��:�����������������һ��,���θ����,��������\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkDBL_New\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<������>\n//����˵��:��鹩Ӧ�̹����������\n//runClassCom@\"nc.bs.ic.pub.check.CheckInvVendorDMO\",\"checkInvQtyNewVendor_New\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<��������>\n//����˵��:����������Ƿ������⸺���\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkInOutTrace\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<������>\n//����˵��:����λ�����Ƿ񳬳�\n//runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkCargoVolumeOut\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n}\n//�÷���<��������>\n//����˵��:������Ƿ��Ѿ����䵽�����֯\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkCalBodyInv_New\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\nif(oIsSpecialBizType!=null && (new UFBoolean(oIsSpecialBizType.toString())).booleanValue()==false){\n//�÷���<��������>\n//����˵��:������̶���λ\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkFixSpace\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<��������>\n//����˵��:������������\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkPlaceAlone\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<������>\n//����˵��:�����߿�桢��Ϳ�桢��ȫ��桢�ٶ�����\nObject s1=runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkParam_new\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\nif(s1!=null)\n sErr.append((String)s1);\n} \n//������ú����ڵ���ʾ\nif (inCurVO.m_sErr!=null)\nsErr.append(inCurVO.m_sErr);\n//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\n//����˵������д���ֵ������ⵥ�ۼ��˻�����\nrunClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"rewriteOutRetNumFor4C4Y\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//����˵�������۳��ⵥ��д���˵��������� 20021113\nrunClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"reWriteDMNew\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//����˵��:���۳��ⵥ����ʱ��д���۵��ݳ�������\nrunClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"reWriteSaleNewBatch\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//##################################################\n//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\n//����˵��:��������״̬\n//runClassCom@\"nc.bs.so.pub.DataControlDMO\",\"autoSetInventoryFinish\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�������ǰ�����������Ƿ�ƥ��\nif(retObjPk != null && !(retObjPk instanceof ArrayList))  throw new nc.vo.pub.BusinessException(\"Remote Call\",new nc.vo.pub.BusinessException(\"���󣺱��涯���ķ���ֵ���ʹ���\"));\n//##################################################\n//############################\n//����ҵ����־���÷�����������\nsetParameter(\"ERR\",sErr.toString());\nsetParameter(\"FUN\",\"���棨������\");\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"insertBusinesslog\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&ERR:String,&FUN:String\"@;\n//############################\nif(sErr.toString().trim().length()==0)\n  alRet.add(null);\nelse\n  alRet.add(sErr.toString());\nalRet.add(retObjPk);\nif(inCurVO.isHaveSourceBill()){\n//����˵��:��������ʱ���,������ \n        runClassCom@\"nc.bs.ic.pub.bill.ICATP\",\"checkAtpInstantly\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n}\n//���С�͵���VO��ǰ̨���� \nnc.vo.ic.pub.smallbill.SMGeneralBillVO smbillvo = inCurVO.getSmallBillVO();\nalRet.add(smbillvo);\n//���ݱ������ͳһ����\nrunClasscom@\"nc.bs.ic.pub.BillActionBase\",\"afterSave\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n}catch(Exception e){\n//############################\n//����ҵ����־���÷�����������\nsetParameter(\"EXC\",e.getMessage());\nsetParameter(\"FUN\",\"����(����)\");\nrunClassCom@\"nc.bs.ic.pub.check.CheckBO\",\"insertBusinessExceptionlog\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&EXC:String,&FUN:String\"@;\n//############################\nif(sBillCode!=null){\n  if(e instanceof nc.vo.pub.BusinessException){\n   if(((nc.vo.pub.BusinessException)e).getCause()== null ||(((nc.vo.pub.BusinessException)e).getCause()!= null && !(((nc.vo.pub.BusinessException)e).getCause() instanceof nc.vo.ic.pub.exp.BillCodeNotUnique)))\n               runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"returnBillCode\",\"&IBC:nc.vo.scm.pub.IBillCode\"@;\n }else{\n    if(!(e instanceof nc.vo.ic.pub.exp.BillCodeNotUnique))\n      runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"returnBillCode\",\"&IBC:nc.vo.scm.pub.IBillCode\"@;\n }\n}\n         if (e instanceof nc.vo.pub.BusinessException)\n      throw (nc.vo.pub.BusinessException) e;\n    else\n      throw new nc.vo.pub.BusinessException(\"Remote Call\", e);\n}\nreturn alRet;  \n//************************************************************************\n";}
/*
* ��ע�����ýű�������HAS
*/
private void setParameter(String key,Object val)  {
  if (m_keyHas==null){
    m_keyHas=new Hashtable();
  }
  if (val!=null)  {
    m_keyHas.put(key,val);
  }
}
}

package nc.bs.pub.action;


import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.uap.pf.PFBusinessException;
/**
 * [�������۳���]->[��Ӧ�����۳���]ɾ�� 
 */
public class N_4C_CANELDELETE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_4C_DELETE ������ע�⡣
 */
public N_4C_CANELDELETE() {
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
  Object inCurObject  =getVos ();
 Object retObj  =null;
 StringBuffer sErr  =new StringBuffer ();
  //1,���ȼ�鴫����������Ƿ�Ϸ����Ƿ�Ϊ�ա�
  if (! (inCurObject instanceof nc.vo.ic.pub.bill.GeneralBillVO[])) throw new nc.vo.pub.BusinessException ( "Remote Call",new nc.vo.pub.BusinessException ( "������ϣ������Ŀ�����۳������Ͳ�ƥ��"));
 if (inCurObject  == null) throw new nc.vo.pub.BusinessException ( "Remote Call",new nc.vo.pub.BusinessException ( "������ϣ������Ŀ�����۳���û������"));
  //2,���ݺϷ���������ת��Ϊ������۳��⡣
  nc.vo.ic.pub.bill.GeneralBillVO inCurVO  =null;
 nc.vo.ic.pub.bill.GeneralBillVO[] inCurVOs  = (nc.vo.ic.pub.bill.GeneralBillVO[])inCurObject;
 inCurObject  =null;
 for (int i=0;i<inCurVOs.length;i  ++) {
   inCurVO  =inCurVOs[i];
    if(inCurVO!=null&&inCurVO.getHeaderVO()!=null)
      inCurVO.getHeaderVO().setStatus(nc.vo.pub.VOStatus.DELETED);
    //��ȡƽ̨����Ĳ���
   setParameter ( "INCURVO",inCurVO);
    //###################################################
   //Ϊ�޸�ҵ��Ӧ�ձ���ĸ���
    //��д�����Ͽͻ���ҵ��Ӧ�ա�ע������������ô˷����Ļ���Ӧע�͵�SIGN&CANCELSIGN����ͬ����
   //������ظ���д��������ݴ���
   if(inCurVO!=null)
     setParameter("INCURVOARAP",(nc.vo.ic.pub.bill.GeneralBillVO)inCurVO.clone());
   //###################################################
   Object alLockedPK  =null;
   try {
     //####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
      //����˵��:����Ƿ���ʡ�<������>
     //Ŀǰ�Ǹ��ݵ��ݱ���ҵ�����ڼ�顣������ݵ�¼���ڼ�飬�뽫checkAccountStatus��ΪcheckAccountStatus1
      //runClass("nc.bs.ic.ic2a3.AccountctrlDMO","checkAccountStatus","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
     //#############################################################
     //����˵��:������ⵥ�ݼ�ҵ����
      alLockedPK  =runClass( "nc.bs.ic.pub.bill.ICLockBO", "lockBill", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
      //##################################################
      //�÷���<��������>
     //zhx add ������۳��ⵥ�Ƿ���ǩ�� ��to check whether can modify 
     Object oUpdated =runClass("nc.bs.ic.pub.ictodm.IC2DMInterface","isBillUpdated","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
      if(oUpdated== null||!((UFBoolean)oUpdated).booleanValue()){
         throw new nc.vo.pub.BusinessException("Remote Call",new nc.vo.pub.BusinessException("���󣺿�����������Ѿ�����ǩ�գ�����ɾ��! "));
     }
     //����˵��:����Ƿ������˼����
     runClass( "nc.bs.ic.pub.check.CheckDMO", "isPicked", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
      //�÷���<��������>
     //����˵��:����浥��ʱ���
      runClass( "nc.bs.ic.pub.check.CheckDMO", "checkTimeStamp", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
      //����˵��:����������Ƿ�������
     runClass("nc.bs.ic.pub.check.CheckBusiDMO","checkRelativeSourceBill","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
     //#######################################################################################################################################################################################################################################################################
     //����Ӧ�գ������� --------------------zpm ע���������࣬�Ҳ���ʵ����
//                                                                                        runClass("nc.bs.ic.ic211.GeneralHDMO","renovateARWhenDelete","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//       ###################################################################################################################                                                                                     //#################################
     //�÷���<������>
      //����˵��:��鹩Ӧ�̹����������
     //setParameter("INPREVO",null);
     //runClass("nc.bs.ic.pub.check.CheckInvVendorDMO","checkInvQtyNegativeNewVendor","&INPREVO:nc.vo.pub.AggregatedValueObject,&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//���ݱ���ǰ����ͳһ����
runClass("nc.bs.ic.pub.BillActionBase","beforeSave","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
     //����˵��:����ɾ��
     runClass( "nc.bs.ic.ic211.GeneralHBO", "deleteBill", "&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
      //####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
      if(inCurVO.isHaveSourceBill()){
       //����˵��:��浥�ݱ���ʱ�޸Ŀ�����
       runClass( "nc.bs.ic.pub.bill.ICATP", "modifyATPWhenDeleteBill", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
     }
     //####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
      //����˵��:���۳��ⵥ��д���۵��ݳ�������
      setParameter ( "RWINCURVO",null);
     setParameter ( "RWINPREVO",inCurVO);
    
      //ע�����˳����Ϊ��ɾ�����������Խ���ǰ��VO��Ϊoldvo����
                                                                                        //����˵������д���ֵ������ⵥ�ۼ��˻�����
                                                                                        runClass("nc.bs.ic.pub.RewriteDMO","rewriteOutRetNumFor4C4Y","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
      //ɾ�����۳��ⵥ��ʱ����д���˵�
     runClass( "nc.bs.ic.pub.RewriteDMO", "reWriteDMNew", "&RWINCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&RWINPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
     //����˵��:���۳��ⵥ����ʱ��д���۵��ݳ�������
     runClass("nc.bs.ic.pub.RewriteDMO","reWriteSaleNewBatch","&RWINCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&RWINPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
     //##################################################
      //�������ǰ�����������Ƿ�ƥ��
     //if (retObj != null && ! (retObj instanceof ArrayList)) throw new nc.vo.pub.BusinessException ("Remote Call",new nc.vo.pub.BusinessException ("����ɾ�������ķ���ֵ���ʹ���"));
      //####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
      //����˵��:������������״̬������
      //runClass( "nc.bs.so.pub.DataControlDMO", "autoSetInventoryCancelFinish", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
      //##################################################
      //##################################################
      //�����ݵ��ݸ������Ϳ�浥λ֮���ת�������û������ҵ����ʵʩ��Աע�͵���
      runClass("nc.bs.ic.pub.bill.DesassemblyBO","setMeasRateVO","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
      //������䣬���û������ҵ����ʵʩ��Աע�͵���
     runClass("nc.bs.ic.pub.bill.DesassemblyBO","exeDesassembly","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
     //����˵������д�ۼƳ�������
     setParameter("CURVO",null);
     setParameter("PREVO",inCurVO);
      runClass("nc.bs.ic.pub.RewriteDMO","reWriteCorNum","&CURVO:nc.vo.ic.pub.bill.GeneralBillVO,&PREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
     //����˵��:ɾ���������
     setParameter("hid",inCurVO.getHeaderVO().getPrimaryKey());
      runClass( "nc.bs.ic.ic2a1.PickBillDMO", "deleteItemsByOuthid", "&hid:String",vo,m_keyHas,m_methodReturnHas);
//�÷���<��������>
//����˵��:�����������������һ��,���θ����,��������
runClass("nc.bs.ic.pub.check.CheckDMO","checkDBL_New","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<��������>
//����˵��:����������Ƿ������⸺���
runClass("nc.bs.ic.pub.check.CheckDMO","checkInOutTrace","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<������>
//����˵��:����λ�����Ƿ񳬳�
//runClass("nc.bs.ic.pub.check.CheckDMO","checkCargoVolumeOut","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//�÷���<������>
//����˵��:�����߿�桢��Ϳ�桢��ȫ��桢�ٶ�����
Object s1=runClass("nc.bs.ic.pub.check.CheckDMO","checkParam_new","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
if(s1!=null)
 sErr.append((String)s1);
//##################################################
//************************�����۶������������**************************************************
//String sActionName  =nc.vo.scm.recordtime.RecordType.SAVEOUT; 
//setParameter ( "sAction",sActionName);
//**************************************************************************************************
//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
//����˵��:ȡ������ĵ��ݶ���ִ��ʱ��: �˷���wnj���践��retObj
//runClass( "nc.bs.scm.recordtime.RecordTimeImpl", "unrecord", "&sAction:STRING,&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//##################################################
///�÷���<��������>
//����˵��:����ɾ��ʱ�˻ص��ݺ�
nc.vo.scm.pub.IBillCode ibc=(nc.vo.scm.pub.IBillCode)inCurVO;
setParameter("IBC",ibc);
runClass("nc.bs.ic.pub.check.CheckDMO","returnBillCodeWhenDelete","&IBC:nc.vo.scm.pub.IBillCode",vo,m_keyHas,m_methodReturnHas);
//���ݱ������ͳһ����
runClass("nc.bs.ic.pub.BillActionBase","afterSave","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//###################################################
   }catch(Exception e){
//############################
//����ҵ����־���÷�����������
setParameter("EXC",e.getMessage());
setParameter("FUN","ɾ��");
runClass("nc.bs.ic.pub.check.CheckBO","insertBusinessExceptionlog","&INCURVO:nc.vo.pub.AggregatedValueObject,&EXC:String,&FUN:String",vo,m_keyHas,m_methodReturnHas);
//###########################
      if (e instanceof nc.vo.pub.BusinessException)
      throw (nc.vo.pub.BusinessException) e;
    else
      throw new nc.vo.pub.BusinessException("Remote Call", e);
}   
  }
if(inCurVO.isHaveSourceBill()){
//����˵��:��������ʱ���,������
setParameter("INPREVOATP",null);
runClass("nc.bs.ic.pub.bill.ICATP","checkAtpInstantly","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVOATP:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
}
//############################
//����ҵ����־���÷�����������
setParameter("INCURVOS",inCurVOs);
setParameter("ERR",sErr.toString());
setParameter("FUN","ɾ��");
runClass("nc.bs.ic.pub.check.CheckDMO","insertBusinesslog","&INCURVOS:nc.vo.ic.pub.bill.GeneralBillVO[],&ERR:String,&FUN:String",vo,m_keyHas,m_methodReturnHas);
//############################
 inCurVO  =null;
 ArrayList alRet  =new ArrayList ();
 if (sErr.toString ().trim ().length ()  ==0)
  alRet.add (null);
 else
  alRet.add (sErr.toString ());
 //alRet.add (retObj);
 return retObj;
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
  return "  //####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####\n  //*************��ƽ̨ȡ���ɸö����������ڲ�����������ȡ����Ҫ�����VO��***********\n  Object inCurObject  =getVos ();\n Object retObj  =null;\n StringBuffer sErr  =new StringBuffer ();\n  //1,���ȼ�鴫����������Ƿ�Ϸ����Ƿ�Ϊ�ա�\n  if (! (inCurObject instanceof nc.vo.ic.pub.bill.GeneralBillVO[])) throw new nc.vo.pub.BusinessException ( \"Remote Call\",new nc.vo.pub.BusinessException ( \"������ϣ������Ŀ�����۳������Ͳ�ƥ��\"));\n if (inCurObject  == null) throw new nc.vo.pub.BusinessException ( \"Remote Call\",new nc.vo.pub.BusinessException ( \"������ϣ������Ŀ�����۳���û������\"));\n  //2,���ݺϷ���������ת��Ϊ������۳��⡣\n  nc.vo.ic.pub.bill.GeneralBillVO inCurVO  =null;\n nc.vo.ic.pub.bill.GeneralBillVO[] inCurVOs  = (nc.vo.ic.pub.bill.GeneralBillVO[])inCurObject;\n inCurObject  =null;\n for (int i=0;i<inCurVOs.length;i  ++) {\n   inCurVO  =inCurVOs[i];\n    if(inCurVO!=null&&inCurVO.getHeaderVO()!=null)\n      inCurVO.getHeaderVO().setStatus(nc.vo.pub.VOStatus.DELETED);\n    //��ȡƽ̨����Ĳ���\n   setParameter ( \"INCURVO\",inCurVO);\n    //###################################################\n   //Ϊ�޸�ҵ��Ӧ�ձ���ĸ���\n    //��д�����Ͽͻ���ҵ��Ӧ�ա�ע������������ô˷����Ļ���Ӧע�͵�SIGN&CANCELSIGN����ͬ����\n   //������ظ���д��������ݴ���\n   if(inCurVO!=null)\n     setParameter(\"INCURVOARAP\",(nc.vo.ic.pub.bill.GeneralBillVO)inCurVO.clone());\n   //###################################################\n   Object alLockedPK  =null;\n   try {\n     //####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\n      //����˵��:����Ƿ���ʡ�<������>\n     //Ŀǰ�Ǹ��ݵ��ݱ���ҵ�����ڼ�顣������ݵ�¼���ڼ�飬�뽫checkAccountStatus��ΪcheckAccountStatus1\n      //runClassCom@\"nc.bs.ic.ic2a3.AccountctrlDMO\",\"checkAccountStatus\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n     //#############################################################\n     //����˵��:������ⵥ�ݼ�ҵ����\n      alLockedPK  =runClassCom@ \"nc.bs.ic.pub.bill.ICLockBO\", \"lockBill\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n      //##################################################\n      //�÷���<��������>\n     //zhx add ������۳��ⵥ�Ƿ���ǩ�� ��to check whether can modify \n     Object oUpdated =runClassCom@\"nc.bs.ic.pub.ictodm.IC2DMInterface\",\"isBillUpdated\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n      if(oUpdated== null||!((UFBoolean)oUpdated).booleanValue()){\n         throw new nc.vo.pub.BusinessException(\"Remote Call\",new nc.vo.pub.BusinessException(\"���󣺿�����������Ѿ�����ǩ�գ�����ɾ��! \"));\n     }\n     //����˵��:����Ƿ������˼����\n     runClassCom@ \"nc.bs.ic.pub.check.CheckDMO\", \"isPicked\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n      //�÷���<��������>\n     //����˵��:����浥��ʱ���\n      runClassCom@ \"nc.bs.ic.pub.check.CheckDMO\", \"checkTimeStamp\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n      //����˵��:����������Ƿ�������\n     runClassCom@\"nc.bs.ic.pub.check.CheckBusiDMO\",\"checkRelativeSourceBill\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n                                                                                        //����Ӧ�գ�������\n                                                                                        runClassCom@\"nc.bs.ic.ic211.GeneralHDMO\",\"renovateARWhenDelete\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n                                                                                        //#################################\n     //�÷���<������>\n      //����˵��:��鹩Ӧ�̹����������\n     //setParameter(\"INPREVO\",null);\n     //runClassCom@\"nc.bs.ic.pub.check.CheckInvVendorDMO\",\"checkInvQtyNegativeNewVendor\",\"&INPREVO:nc.vo.pub.AggregatedValueObject,&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//���ݱ���ǰ����ͳһ����\nrunClasscom@\"nc.bs.ic.pub.BillActionBase\",\"beforeSave\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n     //����˵��:����ɾ��\n     runClassCom@ \"nc.bs.ic.ic211.GeneralHBO\", \"deleteBill\", \"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n      //####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\n      if(inCurVO.isHaveSourceBill()){\n       //����˵��:��浥�ݱ���ʱ�޸Ŀ�����\n       runClassCom@ \"nc.bs.ic.pub.bill.ICATP\", \"modifyATPWhenDeleteBill\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n     }\n     //####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\n      //����˵��:���۳��ⵥ��д���۵��ݳ�������\n      setParameter ( \"RWINCURVO\",null);\n     setParameter ( \"RWINPREVO\",inCurVO);\n    \n      //ע�����˳����Ϊ��ɾ�����������Խ���ǰ��VO��Ϊoldvo����\n                                                                                        //����˵������д���ֵ������ⵥ�ۼ��˻�����\n                                                                                        runClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"rewriteOutRetNumFor4C4Y\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n      //ɾ�����۳��ⵥ��ʱ����д���˵�\n     runClassCom@ \"nc.bs.ic.pub.RewriteDMO\", \"reWriteDMNew\", \"&RWINCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&RWINPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n     //����˵��:���۳��ⵥ����ʱ��д���۵��ݳ�������\n     runClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"reWriteSaleNewBatch\",\"&RWINCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&RWINPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;" +
      "\n     //##################################################\n      //�������ǰ�����������Ƿ�ƥ��\n     //if (retObj != null && ! (retObj instanceof ArrayList)) throw new nc.vo.pub.BusinessException (\"Remote Call\",new nc.vo.pub.BusinessException (\"����ɾ�������ķ���ֵ���ʹ���\"));\n      //####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\n      //����˵��:������������״̬������\n      //runClassCom@ \"nc.bs.so.pub.DataControlDMO\", \"autoSetInventoryCancelFinish\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n      //##################################################\n      //##################################################\n      //�����ݵ��ݸ������Ϳ�浥λ֮���ת�������û������ҵ����ʵʩ��Աע�͵���\n      runClassCom@\"nc.bs.ic.pub.bill.DesassemblyBO\",\"setMeasRateVO\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n      //������䣬���û������ҵ����ʵʩ��Աע�͵���\n     runClassCom@\"nc.bs.ic.pub.bill.DesassemblyBO\",\"exeDesassembly\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n     //����˵������д�ۼƳ�������\n     setParameter(\"CURVO\",null);\n     setParameter(\"PREVO\",inCurVO);\n      runClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"reWriteCorNum\",\"&CURVO:nc.vo.ic.pub.bill.GeneralBillVO,&PREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n     //����˵��:ɾ���������\n     setParameter(\"hid\",inCurVO.getHeaderVO().getPrimaryKey());\n      runClassCom@ \"nc.bs.ic.ic2a1.PickBillDMO\", \"deleteItemsByOuthid\", \"&hid:String\"@;\n//�÷���<��������>\n//����˵��:�����������������һ��,���θ����,��������\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkDBL_New\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<��������>\n//����˵��:����������Ƿ������⸺���\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkInOutTrace\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<������>\n//����˵��:����λ�����Ƿ񳬳�\n//runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkCargoVolumeOut\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//�÷���<������>\n//����˵��:�����߿�桢��Ϳ�桢��ȫ��桢�ٶ�����\nObject s1=runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkParam_new\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\nif(s1!=null)\n sErr.append((String)s1);\n//##################################################\n//************************�����۶������������**************************************************\n//String sActionName  =nc.vo.scm.recordtime.RecordType.SAVEOUT; \n//setParameter ( \"sAction\",sActionName);\n//**************************************************************************************************\n//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\n//����˵��:ȡ������ĵ��ݶ���ִ��ʱ��: �˷���wnj���践��retObj\n//runClassCom@ \"nc.bs.scm.recordtime.RecordTimeImpl\", \"unrecord\", \"&sAction:STRING,&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//##################################################\n///�÷���<��������>\n//����˵��:����ɾ��ʱ�˻ص��ݺ�\nnc.vo.scm.pub.IBillCode ibc=(nc.vo.scm.pub.IBillCode)inCurVO;\nsetParameter(\"IBC\",ibc);\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"returnBillCodeWhenDelete\",\"&IBC:nc.vo.scm.pub.IBillCode\"@;\n//���ݱ������ͳһ����\nrunClasscom@\"nc.bs.ic.pub.BillActionBase\",\"afterSave\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//###################################################\n   }catch(Exception e){\n//############################\n//����ҵ����־���÷�����������\nsetParameter(\"EXC\",e.getMessage());\nsetParameter(\"FUN\",\"ɾ��\");\nrunClassCom@\"nc.bs.ic.pub.check.CheckBO\",\"insertBusinessExceptionlog\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&EXC:String,&FUN:String\"@;\n//###########################\n      if (e instanceof nc.vo.pub.BusinessException)\n      throw (nc.vo.pub.BusinessException) e;\n    else\n      throw new nc.vo.pub.BusinessException(\"Remote Call\", e);\n}   \n  }\nif(inCurVO.isHaveSourceBill()){\n//����˵��:��������ʱ���,������\nsetParameter(\"INPREVOATP\",null);\nrunClassCom@\"nc.bs.ic.pub.bill.ICATP\",\"checkAtpInstantly\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVOATP:nc.vo.pub.AggregatedValueObject\"@;\n}\n//############################\n//����ҵ����־���÷�����������\nsetParameter(\"INCURVOS\",inCurVOs);\nsetParameter(\"ERR\",sErr.toString());\nsetParameter(\"FUN\",\"ɾ��\");\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"insertBusinesslog\",\"&INCURVOS:nc.vo.ic.pub.bill.GeneralBillVO[],&ERR:String,&FUN:String\"@;\n//############################\n inCurVO  =null;\n ArrayList alRet  =new ArrayList ();\n if (sErr.toString ().trim ().length ()  ==0)\n  alRet.add (null);\n else\n  alRet.add (sErr.toString ());\n //alRet.add (retObj);\n return retObj;\n  //************************************************************************\n";}
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

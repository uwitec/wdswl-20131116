package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.wl.pub.WdsWlPubConst;
/**
 *  ���˼ƻ�¼��
 * @author Administrator
 *
 */
public class N_WDS1_WRITE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;

public N_WDS1_WRITE() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	try {
			super.m_tmpVo = vo;
			Object retObj = null;
			setParameter("currentVo", vo.m_preValueVo);
			/**begin-------������¼ƻ�����У�鵱ǰ����ֿ��ڵ�ǰ���Ƿ��Ѿ����¼ƻ�---------begin */
			Object iplantype =vo.m_preValueVo.getParentVO().getAttributeValue("iplantype");
			//modify by yf 2014-02-12 ���˼ƻ�����У�����������ʶ��ά�ȣ������Ƶ�����ƻ� begin
//			UFBoolean fisxn = PuPubVO.getUFBoolean_NullAs(vo.m_preValueVo.getParentVO().getAttributeValue(WdsWlPubConst.dmplan_xn), UFBoolean.FALSE);//add by yf 2012-07-26 ���������ƻ���У���¼ƻ�Ψһ��
			if(iplantype !=null && 0==(Integer)iplantype){
//				setParameter("InWhouse", vo.m_preValueVo.getParentVO().getAttributeValue("pk_inwhouse"));
//				setParameter("OutWhouse", vo.m_preValueVo.getParentVO().getAttributeValue("pk_outwhouse"));
//				setParameter("Pk", vo.m_preValueVo.getParentVO().getAttributeValue("pk_sendplanin"));
//				setParameter("Date", vo.m_currentDate);//SPF ADD
//				setParameter("Reser", vo.m_preValueVo.getParentVO().getAttributeValue("reserve15"));
//				if(!fisxn.booleanValue()){
//					runClass("nc.bs.wl.plan.PlanCheckinBO", "beforeCheck","&OutWhouse:String,&InWhouse:String,&Pk:String,&Date:String,&Reser:UFBoolean", vo, m_keyHas,	m_methodReturnHas);
//				}
				runClass("nc.bs.wl.plan.PlanCheckinBO", "beforeCheck","&currentVo:nc.vo.pub.AggregatedValueObject", vo, m_keyHas,	m_methodReturnHas);
			}
			//modify by yf 2014-02-12 ���˼ƻ�����У�����������ʶ��ά�ȣ������Ƶ�����ƻ� end
			//����ǰ��У�� ׷�Ӽƻ�����ʱ,���û���¼ƻ�,׷�Ӽƻ���������  ׷�Ӽƻ�û�д����������� 
			if(iplantype!=null && 1==(Integer)iplantype){
				runClass("nc.bs.wl.plan.PlanCheckinBO","checkForBplan","&currentVo:nc.vo.pub.AggregatedValueObject",vo, m_keyHas,m_methodReturnHas);		
			}		
			/**begin-------������¼ƻ�����У�鵱ǰ����ֿ��ڵ�ǰ���Ƿ��Ѿ����¼ƻ�---------end */
			
			/**begin-------�����׷�Ӽƻ�����У�鵱ǰ����ֿ��ڵ�ǰ���Ƿ��Ѿ�׷�Ӽƻ�---------begin */
//			Object iplantype1 =vo.m_preValueVo.getParentVO().getAttributeValue("iplantype");
//			if(iplantype !=null && 1==(Integer)iplantype){
//				setParameter("InWhouse", vo.m_preValueVo.getParentVO().getAttributeValue("pk_inwhouse"));
//				setParameter("Pk", vo.m_preValueVo.getParentVO().getAttributeValue("pk_sendplanin"));
//				runClass("nc.bs.wl.plan.PlanCheckinBO", "beforeCheck1","&InWhouse:String,&Pk:String", vo, m_keyHas,	m_methodReturnHas);
//			}
			/**begin-------�����׷�Ӽƻ�����У�鵱ǰ����ֿ��ڵ�ǰ���Ƿ��Ѿ�׷�Ӽƻ�---------end */
			
			
			
			retObj = runClass("nc.bs.trade.comsave.BillSave", "saveBill","nc.vo.pub.AggregatedValueObject:01", vo, m_keyHas,	m_methodReturnHas);
			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
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
	return "	try {\n			super.m_tmpVo = vo;\n			Object retObj = null;\n			// ���漴�ύ\n			retObj = runClass(\"nc.bs.gt.pub.HYBillSave\", \"saveBill\",\"nc.vo.pub.AggregatedValueObject:01\", vo, m_keyHas,	m_methodReturnHas);\n			return retObj;\n		} catch (Exception ex) {\n			if (ex instanceof BusinessException)\n				throw (BusinessException) ex;\n			else\n				throw new PFBusinessException(ex.getMessage(), ex);\n		}\n";}
/*
* ��ע�����ýű�������HAS
*/
private void setParameter(String key,Object val)	{
	if (m_keyHas==null){
		m_keyHas=new Hashtable();
	}
	if (val!=null)	{
		m_keyHas.put(key,val);
	}
}
}

package nc.bs.pf.changedir;

import nc.vo.pf.change.UserDefineFunction;
import nc.vo.pub.lang.UFDate;
import nc.vo.wl.pub.WdsWlPubConst;
/**
 * �������ۼƻ�  ����ʱ ���� ���۷��˵� ����ת��  
 *  ע��  �ô����˼ƻ��ı���ʹ�õ��� sodealvo ��ͷʹ�� saleorderhvo  �ƻ����ŵ�vo  zhf
 *
 * �������ڣ�(2004-11-18)
 * @author��ƽ̨�ű�����
 */
public class CHGWDS4TOWDS5 extends nc.bs.pf.change.VOConversion {
/**
 * CHG20TO21 ������ע�⡣
 */
public CHGWDS4TOWDS5() {
	super();
}
/**
* ��ú������ȫ¼�����ơ�
* @return java.lang.String[]
*/
public String getAfterClassName() {
	return "nc.bs.wds2.conversion.AfterWDSChg";
}
/**
* �����һ���������ȫ¼�����ơ�
* @return java.lang.String[]
*/
public String getOtherClassName() {
	return null;
}
/**
* ����ֶζ�Ӧ��
* @return java.lang.String[]
*/

public String[] getField() {
	return new String[] {
			
			"H_pk_corp->SYSCORP",//��˾
			"H_voperatorid->SYSOPERATOR",//����Ա
			//
			"H_pk_cumandoc->H_ccustomerid",//�ջ��ͻ�
			"H_pk_deptdoc->H_cdeptid",//����
			"H_vemployeeid->H_cemployeeid",//ҵ��Ա
			"H_pk_busitype->H_cbiztype",//ҵ������(���۶�����ͷ)
			"H_csalecorpid->H_csalecorpid",//������֯(���۶�����ͷ)
			"H_ccalbodyid->B_cadvisecalbodyid",//�����֯(���۶�������)(���鷢�������֯)
			"H_creceiptcustomerid->B_creceiptcorpid",//�ջ���λ(���۶�������)
			"H_vinaddress->B_vreceiveaddress",//�ջ���ַ(���۶�������)
			
			"H_reserve5->H_pk_defdoc12",//��������
			"H_vdef5->H_vdef12",//���۵���
		
			
			"B_csourcebillhid->B_csaleid",
			"B_csourcebillbid->B_corder_bid",
			"B_vsourcebillcode->B_vreceiptcode",
			"B_csourcetype->H_creceipttype",//��������
			"B_vsourcerowno->B_crowno",
			"B_cfirstbillhid->B_csaleid",
			"B_cfirstbillbid->B_corder_bid",
			"B_vfirstbillcode->B_vreceiptcode",
			"B_vfirstrowno->B_crowno",
			
			"B_pk_invmandoc->B_cinventoryid",
			"B_pk_invbasdoc->B_cinvbasdocid",
			"B_vdef1->B_vdef1",//���״̬
			"B_uint->B_cunitid",//��������λ
			"B_assunit->B_cpackunitid",//��������λ			
			"B_picicode->B_cbatchid",//����
			"B_fisgift->B_blargessflag",//�Ƿ���Ʒ
			"B_bisdate->B_disdate",//�Ƿ������
			"B_nassarrangnum->B_nassnum",//���Ÿ�����
			"B_narrangnmu->B_nnum",//��������
		//	"B_isxnap->B_isxnap",//�Ƿ����ⰲ�� liuys add
			"B_isxnap->B_pk_defdoc11",//�Ƿ����ⰲ�� 
			// 2013-12-21 ����  ���ۼƻ������������˵���ע�� begin
			"B_vmemo->B_vnote",//��ע
			// 2013-12-21 ����  ���ۼƻ������������˵���ע�� end
//			zhf  add
			"H_reserve16->H_bdericttrans"//�Ƿ�����  ���۰���ʱ����  ���۶���  �Ƿ�ֱ�� �ֶ� ��Ϊ�Ƿ�����
		};
}
/**
* ��ù�ʽ��
* @return java.lang.String[]
*/
public String[] getFormulas() {
	if(m_strDate == null){
		new UFDate(System.currentTimeMillis());
		super.setSysDate(new UFDate(System.currentTimeMillis()).toString());
	}
	return new String[] {
			//add by yf 2014-03-17  Ԥ���ֶ�7(RESERVE7) ����ʾ �����˵�����ʱ�� begin
			"H_reserve7->tostring(datetime())",
			//add by yf 2014-03-17 Ԥ���ֶ�7(RESERVE7) ����ʾ �����˵�����ʱ�� end
			"H_pk_outwhouse->B_cbodywarehouseid",
			"H_pk_billtype->\""+WdsWlPubConst.WDS5+"\"",
			"H_vbillstatus->int(8)",
			"H_itranstype->int(0)",//���䷽ʽ
		    "B_cfirsttype->\""+30+"\"",
		    "H_dmakedate->\""+m_strDate+"\"",
		    "H_dbilldate->\""+m_strDate+"\"",
		    "H_dbegindate->\""+m_strDate+"\"",
		    "H_pk_cubasdoc->getColValue(bd_cumandoc,pk_cubasdoc,pk_cumandoc,H_ccustomerid)",
			"B_csourcetype->\""+30+"\"",
			"B_nhgrate->getColValue(bd_measdoc,scalefactor,pk_measdoc,B_assunit)",//������[����λ]
	};
}
/**
* �����û��Զ��庯����
*/
public UserDefineFunction[] getUserDefineFunction() {
	return null;
}
}

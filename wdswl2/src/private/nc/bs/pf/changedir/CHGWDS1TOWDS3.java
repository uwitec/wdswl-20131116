package nc.bs.pf.changedir;

import nc.vo.pf.change.UserDefineFunction;
import nc.vo.pub.lang.UFDate;
import nc.vo.wl.pub.WdsWlPubConst;
/**
 * ���ڷ��˼ƻ�  ����ʱ ���� ���˶��� ����ת��  
 *  ע��  �ô����˼ƻ��ı���ʹ�õ��� plandealvo  �ƻ����ŵ�vo  zhf
 *
 * �������ڣ�(2004-11-18)
 * @author��ƽ̨�ű�����
 */
public class CHGWDS1TOWDS3 extends nc.bs.pf.change.VOConversion {
/**
 * CHG20TO21 ������ע�⡣
 */
public CHGWDS1TOWDS3() {
	super();
}
/**
* ��ú������ȫ¼�����ơ�
* @return java.lang.String[]
*/
public String getAfterClassName() {
	return "nc.bs.wds2.conversion.AfterCHGWDS1TOWDS3";
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
			"H_pk_outwhouse->H_pk_outwhouse",
			"H_pk_inwhouse->H_pk_inwhouse",
			"H_reserve2->H_reserve2",
			"H_reserve1->H_reserve1", 
			"H_pk_fdsyzc_h->H_reserve1", 
			"H_reserve3->H_reserve3",
			"H_reserve4->H_reserve4",
			"H_reserve5->H_reserve5",
			"H_reserve6->H_reserve6",
			"H_reserve7->H_reserve7",
			"H_reserve8->H_reserve8",
			"H_reserve9->H_reserve9",
			"H_reserve10->H_reserve10",
			"H_reserve11->H_reserve11",
			"H_reserve12->H_reserve12",
			"H_reserve13->H_reserve13",
			"H_reserve14->H_reserve14",
			"H_reserve15->H_reserve15", //�Ƿ�Ƿ��
//			"H_reserve16->H_reserve16",
			//�Զ�����
			"H_vdef4->H_vdef4",
			"H_vdef3->H_vdef3",
			"H_vdef2->H_vdef2",
			"H_vdef1->H_vdef1",
			"H_vdef5->H_vdef5",
			"H_vdef6->H_vdef6",
			"H_vdef7->H_vdef7",
			"H_vdef8->H_vdef8",
			"H_vdef9->H_vdef9",
			"H_vdef10->H_vdef10",
			"H_vdef11->H_vdef11",
			"H_vdef12->H_vdef12",
			"H_vdef13->H_vdef13",
			"H_vdef14->H_vdef14",
			"H_vdef15->H_vdef15",
			"H_vdef16->H_vdef16",
			"H_vdef17->H_vdef17",
			"H_vdef18->H_vdef18",
			"H_vdef19->H_vdef19",
			"H_vdef20->H_vdef20",
			
			"B_csourcebillhid->B_pk_sendplanin",
			"B_csourcebillbid->B_pk_sendplanin_b",
			"B_vsourcebillcode->B_vbillno",
			"B_csourcetype->H_pk_billtype",
			
			"B_cfirstbillhid->B_pk_sendplanin",
			"B_cfirstbillbid->B_pk_sendplanin_b",
			"B_vfirstbillcode->B_vbillno",
			
			"B_pk_invmandoc->B_pk_invmandoc",
			"B_pk_invbasdoc->B_pk_invbasdoc",
			"B_unit->B_unit",//�� ������λ
			"B_assunit->B_assunit",//��������λ
			"B_nassplannum->B_nassplannum",//�ƻ����Ÿ�����
			"B_nplannum->B_nplannum",//�ƻ���������
			"B_ndealnum->B_nnum",//���ΰ�������
			"B_nassdealnum->B_nassnum",//���ΰ��Ÿ�����
			"B_nhsl->B_hsl",//������
			"B_bisdate->B_bisdate",//�Ƿ������
			"B_reserve2->B_reserve2",
			"B_reserve1->B_reserve1", 
			"B_pk_fdsyzc_h->B_reserve1", 
			"B_reserve3->B_reserve3",
			"B_reserve4->B_reserve4",
			"B_reserve5->B_reserve5",
			"B_reserve6->B_reserve6",
			"B_reserve7->B_reserve7",
			"B_reserve8->B_reserve8",
			"B_reserve9->B_reserve9",
			"B_reserve10->B_reserve10",
			"B_reserve11->B_reserve11",
			"B_reserve12->B_reserve12",
			"B_reserve13->B_reserve13",
			"B_reserve14->B_reserve14",
			"B_reserve15->B_reserve15",//�Ƿ�Ƿ��
			"B_reserve16->B_reserve16",//----add by yf 2012-07-26���˶������崫�뷢�˼ƻ��Ƿ�����
			
		
			//�Զ�����
			"B_vdef4->B_vdef4",
			"B_vdef3->B_vdef3",
			"B_vdef2->B_vdef2",
			"B_vdef1->B_vdef1",//���״̬
			"B_vdef5->B_vdef5",
			"B_vdef6->B_vdef6",
			"B_vdef7->B_vdef7",
			"B_vdef8->B_vdef8",
			"B_vdef9->B_vdef9",
			"B_vdef10->B_vdef10",
			"B_vdef11->B_vdef11",
			"B_vdef12->B_vdef12",
			"B_vdef13->B_vdef13",
			"B_vdef14->B_vdef14",
			"B_vdef15->B_vdef15",
			"B_vdef16->B_vdef16",
			"B_vdef17->B_vdef17",
			"B_vdef18->B_vdef18",
			"B_vdef19->B_vdef19",
			"B_vdef20->B_vdef20",
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
			"H_pk_corp->\""+m_strCorp+"\"",
			"H_voperatorid->\""+m_strOperator+"\"",
			"H_pk_billtype->\""+WdsWlPubConst.WDS3+"\"",
			"H_vbillstatus->int(8)",
		    "B_csourcetype->\""+WdsWlPubConst.WDS1+"\"",
		    "H_fisbigglour->\"N\"",
		    "H_dmakedate->\""+m_strDate+"\"",
		    "H_dbilldate->\""+m_strDate+"\"",
		    "H_dbegindate->\""+m_strDate+"\"",
		    "H_itransstatus->\""+0+"\""
	};
}
/**
* �����û��Զ��庯����
*/
public UserDefineFunction[] getUserDefineFunction() {
	return null;
}
}

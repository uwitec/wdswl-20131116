package nc.bs.pf.changedir;

import nc.vo.pf.change.UserDefineFunction;
import nc.vo.wl.pub.WdsWlPubConst;
/**
 * �������۳���->��Ӧ�����۳���
 * @author zpm
 *
 */
public class CHGWDS8TO4C  extends nc.bs.pf.change.VOConversion {

	public CHGWDS8TO4C() {
		super();
	}


	public String getAfterClassName() {
		return "nc.bs.ic.pub.pfconv.HardLockChgVO";
	}

	public String getOtherClassName() {
		return "nc.ui.ic.pub.pfconv.HardLockChgVO";
	}

	public String[] getField() {
		return new String[] {
//				"H_vbillcode",//���ݺ�--------ϵͳ�жϣ���������ڣ����Զ�����
//				"B_crowno",//�к�
//				"coperatorid", //����Ա
//				"H_dbilldate->SYSDATE",//��������			
				
				
				

				"H_pk_defdoc12->H_pk_defdoc5",//��������
				"H_vuserdef12->H_vuserdef7",//���۵���
				"H_cwarehouseid->H_srl_pk",//�ֿ�
//				"H_pk_calbody->H_pk_calbody",//�����֯
				"H_cbiztype->H_cbiztype",//ҵ������
				"H_cdispatcherid->H_cdispatcherid",//�շ����
				"H_cwhsmanagerid->H_cwhsmanagerid",//���Ա
				"H_cdptid->H_cdptid",//����
				"H_cbizid->H_cbizid",//ҵ��Ա
				"H_ccustomerid->H_ccustomerid",//�ͻ�
				"H_vdiliveraddress->H_vdiliveraddress",//�ջ���ַ[���˵�ַ]
				"H_pk_corp->H_pk_corp",//��˾
				"H_coperatorid->H_coperatorid",//�Ƶ���
				
				"B_creceieveid->H_creceiptcustomerid",//�ջ���λ����id
//				"B_pk_cubasdocrev",//�ջ���λ����ID
				
				"B_cinvbasid->B_cinvbasid",//�����������ID   
				"B_cinventoryid->B_cinventoryid",//�������ID  	
				"B_pk_measdoc->B_unitid",//����λ
				"B_castunitid->B_castunitid",//����λ
				"B_hsl->B_hsl",//������ 
				"B_scrq->B_cshengchanriqi",//��������-----------------------------zpm
				"B_dvalidate->B_cshixiaoriqi",//ʧЧ����------------------------------zpm
				"B_nshouldoutassistnum->B_nshouldoutassistnum",//Ӧ��������
				"B_nshouldoutnum->B_nshouldoutnum",//Ӧ������ 
				"B_noutassistnum->B_noutassistnum",//ʵ��������
				"B_noutnum->B_noutnum",//ʵ������
				"B_nprice->B_nprice",//����
				"B_nmny->B_nmny",//���
				"B_vbatchcode->B_vbatchcode",//���κ�	------------------------------zpm
				
				"B_flargess->B_flargess",//�Ƿ���Ʒ
				"B_cspaceid->B_cspaceid",//��λID
				 
				"B_csourcebillhid->B_cfirstbillhid",//�������۳��� Դͷ���ݱ�ͷID[���� ����]  
				"B_csourcebillbid->B_cfirstbillbid",//�������۳��� Դͷ���ݱ���ID  [���� ����]  
				"B_vsourcebillcode->B_vfirstbillcode",//�������۳��� Դͷ���ݺ�[���� ����]   
				"B_csourcetype->B_cfirsttype",//�������۳��� Դͷ�������ͱ���[���� ����]   
				"B_"+WdsWlPubConst.csourcehid_wds+"->B_general_pk",//Lyf:ERP����ⵥ����¼����ϵͳ��Դ��������,�Ա������ĵ����ܹ����鵽ERP����
				"B_"+WdsWlPubConst.csourcebid_wds+"->B_general_b_pk",//Lyf:ERP����ⵥ����¼����ϵͳ��Դ��������,�Ա������ĵ����ܹ����鵽ERP����
				///---------------------------�ù�Ӧ�� ���۳����Դͷ��¼ �������۳���ID
				"B_cfirstbillhid->B_cfirstbillhid",//-- 
				"B_cfirstbillbid->B_cfirstbillbid",//--
				"B_vfirstbillcode->B_vfirstbillcode",//
				"B_cfirsttype->B_cfirsttype",//
				
				"H_freplenishflag->H_freplenishflag",//�Ƿ��˻�
				"H_boutretflag->H_boutretflag",//�Ƿ��˻�
				"B_dbizdate->B_dbizdate"//��������->ҵ������-------------------------zpm
		};
	}

	public String[] getFormulas() {
		return new String[] {
				//add by yf 20140106 ������η�Ʊ������ʾ������ʾ������ƺͱ�������� begin
				//cinventorycode,invname
				"B_invname->getColValue(bd_invbasdoc, invname,pk_invbasdoc,B_cinvbasid)",
				"B_cinventorycode->getColValue(bd_invbasdoc, invcode,pk_invbasdoc,B_cinvbasid)",
				//add by yf 20140106 ������η�Ʊ������ʾ������ʾ������ƺͱ�������� end
				//add by yf 20140116 �������erp���۳��ⵥ������ҵ���Զ�����vdef10,vdef11  begin
				//vdef10,vdef11
				"B_vuserdef10->getColValue(so_saleexecute, vdef10,csale_bid,B_cfirstbillbid)",
				"B_vuserdef11->getColValue(so_saleexecute, vdef11,csale_bid,B_cfirstbillbid)",
				//add by yf 20140116 �������erp���۳��ⵥ������ҵ���Զ�����vdef10,vdef11 end
				"H_cbilltypecode->\"4C\"",
				"H_pk_calbody->getColValue(bd_stordoc, pk_calbody,pk_stordoc,H_srl_pk)",//�����֯
				"H_cothercorpid->getColValue(bd_stordoc, pk_corp,pk_stordoc,H_srl_pk)",//��⹫˾
				"H_cothercalbodyid->getColValue(bd_stordoc, pk_calbody,pk_stordoc,H_srl_pk)",//�������֯ 
				"B_cquotecurrency->getColValue(so_saleorder_b,ccurrencytypeid,corder_bid,B_cfirstbillbid)",

		};
	}
	/**
	* �����û��Զ��庯����
	*/
	public UserDefineFunction[] getUserDefineFunction() {
		return null;
	}
}

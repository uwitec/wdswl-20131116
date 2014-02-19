package nc.bs.pf.changedir;

import nc.vo.pf.change.UserDefineFunction;
import nc.vo.wl.pub.WdsWlPubConst;
/**
 * ������������->��Ӧ����������
 * @author zpm
 *
 */
public class CHGWDS6TO4I  extends nc.bs.pf.change.VOConversion {

	public CHGWDS6TO4I() {
		super();
	}


	public String getAfterClassName() {
		return null;
	}

	public String getOtherClassName() {
	  return null;
	}

	public String[] getField() {
		return new String[] {
//				"H_vbillcode",//���ݺ�--------ϵͳ�жϣ���������ڣ����Զ�����
//				"B_crowno",//�к�
//				"coperatorid", //����Ա
//				"H_dbilldate->SYSDATE",//��������
				
				"H_cotherwhid->H_srl_pkr",//���ֿ�
				"H_cwarehouseid->H_srl_pk",//�ֿ�
//				"H_pk_calbody->H_pk_calbody",//�����֯
				"H_cbiztype->H_cbiztype",//ҵ������
				//modify by yf 2014-02-17 �ش�erp���Ա�����š��շ���� begin
//				"H_cdispatcherid->H_cdispatcherid",//�շ����
//				"H_cwhsmanagerid->H_cwhsmanagerid",//���Ա
//				"H_cdptid->H_cdptid",//����
				"H_cdispatcherid->H_vuserdef8",//�շ����
				"H_cwhsmanagerid->H_cbizid",//���Ա
				"H_cdptid->H_cdptid",//����
				//modify by yf 2014-02-17 �ش�erp���Ա�����š��շ���� end
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
				 
				"B_csourcebillhid->B_general_pk",//[��Ӧ���ֶ� [��������]-------����Ϊ�գ�ʵ�ʱ��� ������������ֵ]
				"B_csourcebillbid->B_general_b_pk",//[��Ӧ���ֶ� [��������]-------����Ϊ�գ�ʵ�ʱ��� ������������ֵ]
				"B_vsourcebillcode->H_vbillno",//[��Ӧ���ֶ� [��������]-------����Ϊ�գ�ʵ�ʱ��� ������������ֵ]
				"B_csourcetype->H_vbilltype",//[��Ӧ���ֶ� [��������]-------����Ϊ�գ�ʵ�ʱ��� ������������ֵ]
				
				"B_cfirstbillhid->B_cfirstbillhid",// [��Ӧ���ֶ� [��������]-------Ϊ�ջ��߷��˼ƻ�����]
				"B_cfirstbillbid->B_cfirstbillbid",//  [��Ӧ���ֶ� [��������]-------Ϊ�ջ��߷��˼ƻ���������] 
				"B_vfirstbillcode->B_vfirstbillcode",//[��Ӧ���ֶ� [��������]-------Ϊ�ջ��߷��˼ƻ�����]
				"B_cfirsttype->B_cfirsttype",//[��Ӧ���ֶ� [��������]-------Ϊ�ջ��߷��˼ƻ�����]				
				"B_dbizdate->B_dbizdate",//��������->ҵ������-------------------------zpm
				"B_"+WdsWlPubConst.csourcehid_wds+"->B_general_pk",//Lyf:ERP����ⵥ����¼����ϵͳ��Դ��������,�Ա������ĵ����ܹ����鵽ERP����
				"B_"+WdsWlPubConst.csourcebid_wds+"->B_general_b_pk",//Lyf:ERP����ⵥ����¼����ϵͳ��Դ��������,�Ա������ĵ����ܹ����鵽ERP����
				//nc.itf.pd.pd4010.IBomOperate����
				//nplannedprice�ƻ����ۣ����û�мƻ����ۣ���ӵ��ݵļƻ���Ӧ��ȡ����Ӧ�ĳɱ����Ϳ����֯��ת����������
				//�����������⣬�ƻ�����ҲΪ��
		};
	}
	

	public String[] getFormulas() {
		
		return new String[] {
				"H_cbilltypecode->\"4I\"",
				"H_pk_calbody->getColValue(bd_stordoc, pk_calbody,pk_stordoc,H_srl_pk)",//�����֯
				"H_cothercorpid->getColValue(bd_stordoc, pk_corp,pk_stordoc,H_srl_pk)",//��⹫˾
				"H_cothercalbodyid->getColValue(bd_stordoc, pk_calbody,pk_stordoc,H_srl_pk)",//�������֯ 
//
		};
	}
	/**
	* �����û��Զ��庯����
	*/
	public UserDefineFunction[] getUserDefineFunction() {
		return null;
	}
}

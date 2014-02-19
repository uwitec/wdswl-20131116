package nc.bs.pf.changedir;

import nc.vo.pf.change.UserDefineFunction;
import nc.vo.wl.pub.WdsWlPubConst;

/**
 * �����������->��Ӧ���������
 * @author zpm
 *
 */
public class CHGWDS7TO4A extends nc.bs.pf.change.VOConversion {
	/**
	 * CHG4ETO4C ������ע�⡣
	 */
	public CHGWDS7TO4A() {
		super();
	}
	/**
	* ��ú������ȫ¼�����ơ�
	* @return java.lang.String[]
	*/
	public String getAfterClassName() {
		return null;
	}
	/**
	* �����һ���������ȫ¼�����ơ�
	* @return java.lang.String[]
	*/
	public String getOtherClassName() {
	  return "nc.ui.ic.pub.pfconv.ChgAft4KNLMR24IA";
	}

	public String[] getField() {
		return (
				new String[] {
						"H_cbiztype->H_geh_cbiztype",//ҵ������
						"H_cdispatcherid->H_geh_cdispatcherid",//�շ����
						"H_cwhsmanagerid->H_geh_cwhsmanagerid",//���Ա
						"H_cdptid->H_geh_cdptid",//����
						"H_cbizid->H_geh_cbizid",//ҵ��Ա
//						"H_ccustomerid->",//�ͻ�
//						"H_vdiliveraddress->",//�ջ���ַ[���˵�ַ]
						"H_pk_corp->H_pk_corp",//��˾
						"H_coperatorid->H_coperatorid",//�Ƶ���
						
//						"B_creceieveid->",//�ջ���λ����id
						//modify by yf 2014-02-17 �ش�erp���Ա�����š��շ���� begin
//						"H_cdispatcherid->H_cdispatcherid",//�շ����
//						"H_cwhsmanagerid->H_cwhsmanagerid",//���Ա
//						"H_cdptid->H_cdptid",//����
						"H_cdispatcherid->H_geh_customize8",//�շ����
						"H_cwhsmanagerid->H_geh_cbizid",//���Ա
						//modify by yf 2014-02-17 �ش�erp���Ա�����š��շ���� end
						
						"B_cinvbasid->B_geb_cinvbasid",//�����������ID   
						"B_cinventoryid->B_geb_cinventoryid",//�������ID  	
						"B_pk_measdoc->B_pk_measdoc",//����λ
						"B_castunitid->B_castunitid",//����λ
						"B_hsl->B_geb_hsl",//������ 
						"B_scrq->B_geb_proddate",//��������-----------------------------zpm
						"B_dvalidate->B_geb_dvalidate",//ʧЧ����------------------------------zpm
						
						
						"B_nshouldinnum->B_geb_snum",//Ӧ������
						"B_nneedinassistnum->B_geb_bsnum",//Ӧ�븨����  
						"B_ninnum->B_geb_anum",//ʵ������
						"B_ninassistnum->B_geb_banum",//ʵ�븨����   
						
						
						"B_nprice->B_geb_nprice",//����
						"B_nmny->B_geb_nmny",//���
						"B_vbatchcode->B_geb_vbatchcode",//���κ�	
						"B_flargess->B_geb_flargess",//�Ƿ���Ʒ
						"B_cspaceid->B_geb_space",//��λID

						"B_csourcebillhid->B_geh_pk",//[����  �������ֶ�]
						"B_csourcebillbid->B_geb_pk",//[����  ��Ӧ�� �������ֶ�]
						"B_vsourcebillcode->H_geh_billcode",//[���� ��Ӧ�� �������ֶ�]
						"B_csourcetype->H_geh_billtype",//[����  ��Ӧ�� �������ֶ�]
						"B_"+WdsWlPubConst.csourcehid_wds+"->B_geh_pk",//Lyf:ERP����ⵥ����¼����ϵͳ��Դ��������,�Ա������ĵ����ܹ����鵽ERP����
						"B_"+WdsWlPubConst.csourcebid_wds+"->B_geb_pk",//Lyf:ERP����ⵥ����¼����ϵͳ��Դ��������,�Ա������ĵ����ܹ����鵽ERP����
						"B_cfirstbillhid->B_cfirstbillhid",// [��Ӧ���ֶ� [�������]-------Ϊ�ջ��߷��˼ƻ�����]
						"B_cfirstbillbid->B_cfirstbillbid",//  [��Ӧ���ֶ� [�������]-------Ϊ�ջ��߷��˼ƻ���������] 
						"B_vfirstbillcode->B_vfirstbillcode",//[��Ӧ���ֶ� [�������]-------Ϊ�ջ��߷��˼ƻ�����]
						"B_cfirsttype->B_cfirsttype",//[��Ӧ���ֶ� [�������]-------Ϊ�ջ��߷��˼ƻ�����]
						"B_dbizdate->B_geb_dbizdate",//�������--ҵ������
						
		
//						"H_coperatoridnow->SYSOPERATOR",
//						"H_coperatorid->SYSOPERATOR",

						"H_cotherwhid->H_geh_cotherwhid",//����ֿ�
						"H_cwarehouseid->H_geh_cwarehouseid"//���ֿ�
//						"B_nplannedmny->B_jhje",//�ƻ����
//						"B_nplannedprice->B_jhdj",//�ƻ�����
					
				});
	}     

		public String[] getFormulas()
		{ 
			return (new String[] {
					"H_cbilltypecode->\"4A\"", 
					"H_pk_calbody->getColValue(bd_stordoc, pk_calbody,pk_stordoc,H_geh_cotherwhid)",
					"H_cothercorpid->getColValue(bd_stordoc, pk_corp,pk_stordoc,H_geh_cwarehouseid)",//��⹫˾
					"H_cothercalbodyid->getColValue(bd_stordoc, pk_calbody,pk_stordoc,H_geh_cwarehouseid)", 
//					"B_csourcetype->\"WDS7\"",					
				});
		}
	/**
	* �����û��Զ��庯����
	*/
	public UserDefineFunction[] getUserDefineFunction() {
		return null;
	}
}

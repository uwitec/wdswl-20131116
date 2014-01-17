package nc.bs.pf.changedir;

import nc.vo.pf.change.UserDefineFunction;
import nc.vo.wl.pub.WdsWlPubConst;

/**
 * ���ص������->��Ӧ���������
 * @author zpm
 *
 */
public class CHGWDS9TO4E extends nc.bs.pf.change.VOConversion {

	public CHGWDS9TO4E() {
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
						"H_pk_corp->H_pk_corp",//�빫˾
						"H_pk_calbody->H_geh_calbody",//������֯
						"H_cwarehouseid->H_geh_cwarehouseid",//���ֿ�
						
						"H_cothercorpid->H_geh_cothercorpid",//����˾
						"H_cothercalbodyid->H_geh_cothercalbodyid",//�������֯
						"H_cotherwhid->H_geh_cotherwhid",//����ֿ�
						//liuys add ������˾,�����֯,�ֿ⸳ֵ
						"H_coutcorpid->H_geh_cothercorpid",
						"H_coutcalbodyid->H_geh_cothercalbodyid",
						
						"H_fallocflag->H_geh_fallocflag",//�������ͱ�־
						
						"H_cbiztype->H_geh_cbiztype",//ҵ������
						"H_cdispatcherid->H_geh_cdispatcherid",//�շ����
						"H_cwhsmanagerid->H_geh_cwhsmanagerid",//���Ա
						"H_cdptid->H_geh_cdptid",//����
						"H_cbizid->H_geh_cbizid",//ҵ��Ա
//						"H_ccustomerid->",//�ͻ�
//						"H_vdiliveraddress->",//�ջ���ַ[���˵�ַ]
						//2013-12-21 ���� �ش�ʱ�����Ƶ��� begin
						"H_coperatorid->H_coperatorid",//�Ƶ���
						//2013-12-21 ���� �ش�ʱ�����Ƶ��� end
//						"B_creceieveid->",//�ջ���λ����id
						
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
					//	"B_vbatchcode->B_geb_vbatchcode",//���κ�	
						"B_vbatchcode->B_geb_backvbatchcode",//ԭ���κŻ�д
						
						"B_flargess->B_geb_flargess",//�Ƿ���Ʒ
						"B_cspaceid->B_geb_space",//��λID

						"B_csourcebillhid->B_gylbillhid",//[���� ��Ӧ�� �������ⵥ]
						"B_csourcebillbid->B_gylbillbid",//[���� ��Ӧ�� �������ⵥ]
						"B_vsourcebillcode->B_gylbillcode",//[���� ��Ӧ�� �������ⵥ]
						"B_csourcetype->B_gylbilltype",//[���� ��Ӧ�� �������ⵥ]
//						"B_"+WdsWlPubConst.csourcehid_wds+"->B_geb_pk",//Lyf:ERP����ⵥ����¼����ϵͳ��Դ��������,�Ա������ĵ����ܹ����鵽ERP����
						"B_"+WdsWlPubConst.csourcebid_wds+"->B_geb_pk",//Lyf:ERP����ⵥ����¼����ϵͳ��Դ��������,�Ա������ĵ����ܹ����鵽ERP����
						"B_cfirstbillhid->B_cfirstbillhid",// [����  ���� ������ⵥ�ֶ�]��
						"B_cfirstbillbid->B_cfirstbillbid",//   [����  ���� ������ⵥ�ֶ�]
						"B_vfirstbillcode->B_vfirstbillcode",//[����  ���� ������ⵥ�ֶ�]
						"B_cfirsttype->B_cfirsttype",//[����  ���� ������ⵥ�ֶ�]
						
						"B_dbizdate->B_geb_dbizdate",//�������--ҵ������
						
		
						"H_coperatoridnow->SYSOPERATOR",
						"H_coperatorid->SYSOPERATOR",
						
//						"B_nplannedmny->B_jhje",//�ƻ����
//						"B_nplannedprice->B_jhdj",//�ƻ�����
					
				});
	}     

		public String[] getFormulas()
		{ 
			return (new String[] {
					"H_cbilltypecode->\"4E\"", 
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

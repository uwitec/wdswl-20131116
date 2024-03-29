package nc.bs.pf.changedir;

import nc.vo.pf.change.UserDefineFunction;
import nc.vo.wl.pub.WdsWlPubConst;
/**
 * 本地其它出库->供应链其它出库
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
//				"H_vbillcode",//单据号--------系统判断，如果不存在，则自动生成
//				"B_crowno",//行号
//				"coperatorid", //操作员
//				"H_dbilldate->SYSDATE",//单据日期
				
				"H_cotherwhid->H_srl_pkr",//入库仓库
				"H_cwarehouseid->H_srl_pk",//仓库
//				"H_pk_calbody->H_pk_calbody",//库存组织
				"H_cbiztype->H_cbiztype",//业务类型
				//modify by yf 2014-02-17 回传erp库管员、部门、收发类别 begin
//				"H_cdispatcherid->H_cdispatcherid",//收发类别
//				"H_cwhsmanagerid->H_cwhsmanagerid",//库管员
//				"H_cdptid->H_cdptid",//部门
				"H_cdispatcherid->H_vuserdef8",//收发类别
				"H_cwhsmanagerid->H_cbizid",//库管员
				"H_cdptid->H_cdptid",//部门
				//modify by yf 2014-02-17 回传erp库管员、部门、收发类别 end
				"H_cbizid->H_cbizid",//业务员
				"H_ccustomerid->H_ccustomerid",//客户
				"H_vdiliveraddress->H_vdiliveraddress",//收货地址[发运地址]
				"H_pk_corp->H_pk_corp",//公司
				"H_coperatorid->H_coperatorid",//制单人
				
				"B_creceieveid->H_creceiptcustomerid",//收货单位管理id
//				"B_pk_cubasdocrev",//收货单位基本ID
				
				"B_cinvbasid->B_cinvbasid",//存货基本档案ID   
				"B_cinventoryid->B_cinventoryid",//存货管理ID  	
				"B_pk_measdoc->B_unitid",//主单位
				"B_castunitid->B_castunitid",//辅单位
				"B_hsl->B_hsl",//换算率 
				"B_scrq->B_cshengchanriqi",//生产日期-----------------------------zpm
				"B_dvalidate->B_cshixiaoriqi",//失效日期------------------------------zpm
				"B_nshouldoutassistnum->B_nshouldoutassistnum",//应发辅数量
				"B_nshouldoutnum->B_nshouldoutnum",//应发数量 
				"B_noutassistnum->B_noutassistnum",//实发辅数量
				"B_noutnum->B_noutnum",//实发数量
				"B_nprice->B_nprice",//单价
				"B_nmny->B_nmny",//金额
				"B_vbatchcode->B_vbatchcode",//批次号	------------------------------zpm
				
				"B_flargess->B_flargess",//是否赠品
				"B_cspaceid->B_cspaceid",//货位ID
				 
				"B_csourcebillhid->B_general_pk",//[供应链字段 [其他出库]-------理论为空，实际保存 本地其他出库值]
				"B_csourcebillbid->B_general_b_pk",//[供应链字段 [其他出库]-------理论为空，实际保存 本地其他出库值]
				"B_vsourcebillcode->H_vbillno",//[供应链字段 [其他出库]-------理论为空，实际保存 本地其他出库值]
				"B_csourcetype->H_vbilltype",//[供应链字段 [其他出库]-------理论为空，实际保存 本地其他出库值]
				
				"B_cfirstbillhid->B_cfirstbillhid",// [供应链字段 [其他出库]-------为空或者发运计划主键]
				"B_cfirstbillbid->B_cfirstbillbid",//  [供应链字段 [其他出库]-------为空或者发运计划表体主键] 
				"B_vfirstbillcode->B_vfirstbillcode",//[供应链字段 [其他出库]-------为空或者发运计划编码]
				"B_cfirsttype->B_cfirsttype",//[供应链字段 [其他出库]-------为空或者发运计划编码]				
				"B_dbizdate->B_dbizdate",//出库日期->业务日期-------------------------zpm
				"B_"+WdsWlPubConst.csourcehid_wds+"->B_general_pk",//Lyf:ERP出入库单，记录物流系统来源单据主键,以便物流的单据能够联查到ERP单据
				"B_"+WdsWlPubConst.csourcebid_wds+"->B_general_b_pk",//Lyf:ERP出入库单，记录物流系统来源单据主键,以便物流的单据能够联查到ERP单据
				//nc.itf.pd.pd4010.IBomOperate报错
				//nplannedprice计划单价，如果没有计划单价，则从单据的计划价应该取自相应的成本型型库存组织，转向生产制造
				//正常其它出库，计划单价也为空
		};
	}
	

	public String[] getFormulas() {
		
		return new String[] {
				//add by yf 20140221 解决下游发票报错提示，不显示存货名称和编码的问题 begin
				//cinventorycode,invname
				"B_invname->getColValue(bd_invbasdoc, invname,pk_invbasdoc,B_cinvbasid)",
				"B_cinventorycode->getColValue(bd_invbasdoc, invcode,pk_invbasdoc,B_cinvbasid)",
				//add by yf 20140221解决下游发票报错提示，不显示存货名称和编码的问题 end
				//add by yf 20140224 物流其他出库单据收发类别为样品的，计算全月平均单单价后，报错 begin
				"B_vuserdef6->getColValue(bd_invmandoc,refsaleprice, pk_invmandoc,B_cinventoryid )",
				//add by yf 20140224 物流其他出库单据收发类别为样品的，计算全月平均单单价后，报错 end
				"H_cbilltypecode->\"4I\"",
				"H_pk_calbody->getColValue(bd_stordoc, pk_calbody,pk_stordoc,H_srl_pk)",//库存组织
				"H_cothercorpid->getColValue(bd_stordoc, pk_corp,pk_stordoc,H_srl_pk)",//入库公司
				"H_cothercalbodyid->getColValue(bd_stordoc, pk_calbody,pk_stordoc,H_srl_pk)",//入库库存组织 
//
		};
	}
	/**
	* 返回用户自定义函数。
	*/
	public UserDefineFunction[] getUserDefineFunction() {
		return null;
	}
}

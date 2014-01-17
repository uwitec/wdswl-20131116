package nc.ui.wds.ic.so.out;
import java.awt.Container;
import java.util.HashSet;
import java.util.Set;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.wdsnew.pub.MBillSourceDLG;
import nc.ui.wdsnew.pub.PowerGetTool;
import nc.ui.wl.pub.LoginInforHelper;
import nc.vo.dm.so.order.SoorderBVO;
import nc.vo.dm.so.order.SoorderVO;
import nc.vo.pub.AggregatedValueObject;
/**
 * 销售出库参照销售运单(WDS5)对话框
 * 按 操作员 绑定货位 过滤存货
 * @author mlr
 */
public class RefBillSourceDlg extends MBillSourceDLG{
	
	private static final long serialVersionUID = 1L;
	//获得权限过滤的sql
	String sql=null;
	private LoginInforHelper lo=new LoginInforHelper();
	
	private String getPowerSql(){
		String sql=null;
		if (sql == null || sql.length() == 0)
			try {
				sql = PowerGetTool.queryClassPowerSql(ClientEnvironment
						.getInstance().getUser().getPrimaryKey());
			} catch (Exception e) {
				this.getClientUI().showErrorMessage(e.getMessage());		
				e.printStackTrace();
			}
		return sql;
	}
	public RefBillSourceDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType, businessType,
				templateId, currentBillType, parent);
		init();
	}
	public RefBillSourceDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			String nodeKey, Object userObj, Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType, businessType,
				templateId, currentBillType, nodeKey, userObj, parent);
		init();
	}

	private void init() {
		setSpiltFields(new String[]{"pk_outwhouse"});
		setSpiltFields1(new String []{"isxnap"});
	}
	public String getHeadCondition() {
		String sql=null;	
		try {
			sql= "  coalesce(wds_soorder_b.narrangnmu,0)-coalesce(wds_soorder_b.noutnum,0)>0 " +//安排数量-出库数量>0
				   " and wds_soorder_b.pk_invmandoc in ("+getPowerSql()+")"+
			       " and wds_soorder.pk_outwhouse ='"+lo.getCwhid(ClientEnvironment.getInstance().getUser().getPrimaryKey())+"'";//过滤当前操作员绑定的仓库
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sql;
	}	
	public String getBodyCondition(){
		return " isnull(wds_soorder_b.dr,0)=0 and coalesce(wds_soorder_b.narrangnmu,0)-coalesce(wds_soorder_b.noutnum,0)>0"+//安排数量-出库数量>0
			" and pk_invmandoc in ("+getPowerSql()+")";
	}

	protected boolean isHeadCanMultiSelect() {
		return false;
	}

	@Override
	public String getPk_invbasdocName() {
		
		return "pk_invbasdoc";
	}

	@Override
	public String getPk_invmandocName() {
		
		return "pk_invmandoc";
	}

	@Override
	public IControllerBase getUIController() {		
		return new nc.ui.dm.so.order.ClientController();
	}
	
	//modify by yf 2014-01-16 销售出库单参照销售运单，如果表体来源不同业务类型，不能参照 begin
	@Override
	public void onOk() {
		if (getbillListPanel().getHeadBillModel().getRowCount() > 0) {
			AggregatedValueObject[] selectedBillVOs = getbillListPanel().getMultiSelectedVOs(m_billVo,
					m_billHeadVo, m_billBodyVo);
			retBillVo = selectedBillVOs.length > 0 ? selectedBillVOs[0] : null;
			retBillVos = selectedBillVOs;
			retBillVos=spilt(selectedBillVOs);
		}
		if(!checksoorder()){
			return;
		}
		this.closeOK();
	}
	
	private boolean checksoorder() {
//		SoorderVO hvo = (SoorderVO) retBillVo.getParentVO();
		SoorderBVO[] bvos = (SoorderBVO[]) retBillVo.getChildrenVO();
		
		//业务类型不符
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < bvos.length; i++) {
			if(!set.contains(bvos[i].getReserve5())){
				set.add(bvos[i].getReserve5());
			}
		}
		if(set.size() > 1){
			MessageDialog.showErrorDlg(this, "错误", "订单业务类型不一致");
			return false;
		}
		
		return true;
	}
	//modify by yf 2014-01-16 销售出库单参照销售运单，如果表体来源不同业务类型，不能参照 end
}

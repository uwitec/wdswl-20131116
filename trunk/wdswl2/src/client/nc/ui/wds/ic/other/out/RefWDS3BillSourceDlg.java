package nc.ui.wds.ic.other.out;
import java.awt.Container;
import java.util.HashSet;
import java.util.Set;

import nc.bs.logging.Logger;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.wdsnew.pub.MBillSourceDLG;
import nc.ui.wdsnew.pub.PowerGetTool;
import nc.ui.wl.pub.LoginInforHelper;
import nc.vo.dm.order.SendorderBVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.wl.pub.WdsWlPubConst;
/**
 * @author mlr
 *�������� ���� ���˶�����WDS3��
 */
public class RefWDS3BillSourceDlg  extends MBillSourceDLG {
	private static final long serialVersionUID = 1L;
	private LoginInforHelper lo=new LoginInforHelper();
	//���Ȩ�޹��˵�sql
	String sql=null;
	
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
	
	public RefWDS3BillSourceDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType, businessType,
				templateId, currentBillType, parent);
		init();
	}
	public RefWDS3BillSourceDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			String nodeKey, Object userObj, Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType, businessType,
				templateId, currentBillType, nodeKey, userObj, parent);
		init();
	}
	String pk_store=null;
	public void init(){
		try{
			setSpiltFields(new String[]{"pk_outwhouse"});
			setSpiltFields1(new String []{"reserve16"});
			pk_store=lo.getCwhid(ClientEnvironment.getInstance().getUser().getPrimaryKey());
		}catch(Exception e){
			Logger.error(e);
		}
	}
	
	
	@Override
	public String getTitle() {
		return "���շ��˶���";
	}
	@Override
	public String getHeadCondition() {
		
		return "  coalesce(wds_sendorder_b.ndealnum,0)-coalesce(wds_sendorder_b.noutnum,0)>0 " +//��������-��������>0
		" and wds_sendorder_b.pk_invmandoc in ("+getPowerSql()+")"+
		" and wds_sendorder.pk_outwhouse ='"+pk_store+"'" +//���˵�ǰ����Ա�󶨵Ĳֿ�
	    " and wds_sendorder.pk_billtype='"+WdsWlPubConst.WDS3+"'";
	}
	
	
	@Override
	public String getBodyCondition() {
		return " isnull(wds_sendorder_b.dr,0)=0 and coalesce(wds_sendorder_b.ndealnum,0)-coalesce(wds_sendorder_b.noutnum,0)>0"+//��������-��������>0
		" and pk_invmandoc in ("+getPowerSql()+")";
	}
	
	@Override
	protected boolean isHeadCanMultiSelect() {
		return false;
	}
	@Override
	protected boolean isBodyCanSelected() {
		return true;
	}
	
	@Override
	public boolean getIsBusinessType() {
		return false;
	}

	@Override
	public IControllerBase getUIController() {
		return new nc.ui.dm.order.ClientController();
	}
	@Override
	public String getPk_invbasdocName() {
		
		return "pk_invbasdoc";
	}

	@Override
	public String getPk_invmandocName() {
		
		return "pk_invmandoc";
	}

	//modify by yf 2014-02-11 ����ƻ�����ͨ�ƻ���Ҫ�ֵ�����  begin
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
		SendorderBVO[] bvos = (SendorderBVO[]) retBillVo.getChildrenVO();
		
		//ҵ�����Ͳ���
		Set<UFBoolean> set = new HashSet<UFBoolean>();
		for (int i = 0; i < bvos.length; i++) {
			if(!set.contains(bvos[i].getReserve16())){
				set.add(bvos[i].getReserve16());
			}
		}
		if(set.size() > 1){
			MessageDialog.showErrorDlg(this, "����", "����ҵ�����Ͳ�һ��");
			return false;
		}
		
		return true;
	}
	//modify by yf 2014-02-11 ����ƻ�����ͨ�ƻ���Ҫ�ֵ����� end
	
}

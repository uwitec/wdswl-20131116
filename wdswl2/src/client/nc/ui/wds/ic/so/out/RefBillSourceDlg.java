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
 * ���۳�����������˵�(WDS5)�Ի���
 * �� ����Ա �󶨻�λ ���˴��
 * @author mlr
 */
public class RefBillSourceDlg extends MBillSourceDLG{
	
	private static final long serialVersionUID = 1L;
	//���Ȩ�޹��˵�sql
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
			sql= "  coalesce(wds_soorder_b.narrangnmu,0)-coalesce(wds_soorder_b.noutnum,0)>0 " +//��������-��������>0
				   " and wds_soorder_b.pk_invmandoc in ("+getPowerSql()+")"+
			       " and wds_soorder.pk_outwhouse ='"+lo.getCwhid(ClientEnvironment.getInstance().getUser().getPrimaryKey())+"'";//���˵�ǰ����Ա�󶨵Ĳֿ�
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sql;
	}	
	public String getBodyCondition(){
		return " isnull(wds_soorder_b.dr,0)=0 and coalesce(wds_soorder_b.narrangnmu,0)-coalesce(wds_soorder_b.noutnum,0)>0"+//��������-��������>0
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
	
	//modify by yf 2014-01-16 ���۳��ⵥ���������˵������������Դ��ͬҵ�����ͣ����ܲ��� begin
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
		
		//ҵ�����Ͳ���
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < bvos.length; i++) {
			if(!set.contains(bvos[i].getReserve5())){
				set.add(bvos[i].getReserve5());
			}
		}
		if(set.size() > 1){
			MessageDialog.showErrorDlg(this, "����", "����ҵ�����Ͳ�һ��");
			return false;
		}
		
		return true;
	}
	//modify by yf 2014-01-16 ���۳��ⵥ���������˵������������Դ��ͬҵ�����ͣ����ܲ��� end
}

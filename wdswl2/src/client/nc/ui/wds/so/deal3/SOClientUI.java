package nc.ui.wds.so.deal3;

import javax.swing.ListSelectionModel;

import nc.ui.ml.NCLangRes;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ToftPanel;
import nc.ui.so.so001.order.SaleOrderAdminUI;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.wl.pub.LongTimeTask;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.so.so001.SaleOrderVO;
import nc.vo.so.so001.SaleorderHVO;
import nc.vo.wl.pub.WdsWlPubConst;

public class SOClientUI extends SaleOrderAdminUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8942470137638469586L;

	ButtonObject boXuni;
	ButtonObject boUnXuni;
	ButtonObject[] buttonarray = null;

	UFBoolean xuni = UFBoolean.TRUE;
	UFBoolean unxuni = UFBoolean.FALSE;

	@Override
	public String getModuleCode() {
		return "40060301";
	}
	@Override
	protected void initialize() {
		super.initialize();
		getBillListPanel().getHeadTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
	}
	@Override
	public ButtonObject[] getBillButtons() {
		if (buttonarray == null) {
			buttonarray = new ButtonObject[] { getBoQuery(), getBoXuni(),
					getBoUnXuni(), getBoCard(), getBoReturn(), getBoBrowse() };
		}
		return buttonarray;
	}

	protected ButtonObject getBoXuni() {
		if (boXuni == null) {
			boXuni = new ButtonObject("虚拟", "虚拟", 0, "虚拟");
		}
		return boXuni;
	}

	protected ButtonObject getBoUnXuni() {
		if (boUnXuni == null) {
			boUnXuni = new ButtonObject("取消虚拟", "取消虚拟", 0, "取消虚拟");
		}
		return boUnXuni;
	}

	@Override
	public void onButtonClicked(ButtonObject bo) {
		// 未选中行处理
		if (strShowState.equals("列表") /*-=notranslate=-*/
				&& getBillListPanel().getHeadTable().getSelectedRowCount() <= 0
				&& bo.getParent() != boBusiType && bo.getParent() != boBrowse
				&& bo != boDocument && bo != boListDeselectAll
				&& bo != boListSelectAll && bo != boCard && bo != boQuery
				&& bo.getParent() != boAdd && bo.getParent() != boBusiType) {
			showWarningMessage(NCLangRes.getInstance().getStrByID(
					"40060301", "UPP40060301-000281")/* @res "请选择一条单据" */);
			return;
		}
		super.onButtonClicked(bo);
		if (bo == boXuni) {
			try {
				onBoXuni();
				onRefresh();
			} catch (Exception e) {
				showErrorMessage(e.getMessage());
				return;
			}
		}
		if (bo == boUnXuni) {
			try {
				onBoUnXuni();
				onRefresh();
			} catch (Exception e) {
				showErrorMessage(e.getMessage());
				return;
			}
		}
	}

	private void onBoXuni() throws Exception {
		//
		SaleOrderVO saleorder = (SaleOrderVO) getVO(false);
		//
		if (saleorder != null) {
			SaleorderHVO hvo = saleorder.getHeadVO();
			doCloseOrOpen(new String[] { hvo.getPrimaryKey() }, this, xuni);
		}
	}

	private void onBoUnXuni() throws Exception {
		//
		SaleOrderVO saleorder = (SaleOrderVO) getVO(false);
		//
		if (saleorder != null) {
			SaleorderHVO hvo = saleorder.getHeadVO();
			doCloseOrOpen(new String[] { hvo.getPrimaryKey() }, this, unxuni);
		}

	}

	private static String bo = "nc.bs.wds.so.deal3.SoDeal3BO";

	public static void doCloseOrOpen(String[] ids, ToftPanel tp,
			UFBoolean isXuni) throws Exception {
		if (ids == null || ids.length == 0)
			return;
		Class[] ParameterTypes = new Class[] { String[].class, UFBoolean.class };
		Object[] ParameterValues = new Object[] { ids, isXuni };
		// LongTimeTask.calllongTimeService(WdsWlPubConst.WDS_WL_MODULENAME, tp,
		// "正在处理...", 2, bo, null, "doCloseOrOpen", ParameterTypes,
		// ParameterValues);
		LongTimeTask.callRemoteService("wds", bo, "doCloseOrOpen",
				ParameterTypes, ParameterValues, 2);
	}

}

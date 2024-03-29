package nc.ui.wds.ic.so.out;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.trade.bill.BillListPanelWrapper;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.wds.ic.pub.OutPubClientUI;
import nc.ui.wds.ic.pub.OutPubEventHandler;
import nc.ui.wds.w8004040204.ssButtun.ISsButtun;
import nc.ui.wl.pub.LoginInforHelper;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.uif.pub.exception.UifException;
import nc.vo.dm.so.order.SoorderVO;
import nc.vo.ic.other.out.TbOutgeneralBVO;
import nc.vo.ic.other.out.TbOutgeneralHVO;
import nc.vo.ic.pub.ScaleKey;
import nc.vo.ic.pub.ScaleValue;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.wl.pub.WdsWlPubConst;

/**
 * 0 保管员 1 信息科 2 发运科 3内勤 用户类型0---------可以进行出库 用户类型1---------为可以签字的用户
 * 用户类型2---------修改操作 用户类型3---------所有权限
 */
public class MySaleEventHandler extends OutPubEventHandler {
	private LoginInforHelper login = null;
	protected nc.ui.pub.print.PrintEntry m_print = null;
	protected ScaleValue m_ScaleValue = new ScaleValue();
	protected ScaleKey m_ScaleKey = new ScaleKey();

	public MySaleEventHandler(OutPubClientUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	@Override
	protected UIDialog createQueryUI() {
		return new MyQueryDIG(getBillUI(), null, _getCorp().getPk_corp(),
				getBillUI().getModuleCode(), _getOperator(), null);
	}

	@Override
	protected String getHeadCondition() {
		return " pk_corp = '" + _getCorp().getPrimaryKey()
				+ "' and isnull(dr,0) = 0 and vbilltype = '"
				+ WdsWlPubConst.BILLTYPE_SALE_OUT + "' ";
	}

	private LoginInforHelper getLoginInfoHelper() {
		if (login == null) {
			login = new LoginInforHelper();
		}
		return login;
	}

	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn);
		switch (intBtn) {
		case ISsButtun.tpzd:// 托盘指定(手动拣货)
			valudateWhereYeqian();
			// liuys 先注释掉校验 //拣货 存货唯一校验
			// BeforeSaveValudate.beforeSaveBodyUnique(getBillCardPanelWrapper().getBillCardPanel().getBillTable(),
			// getBillCardPanelWrapper().getBillCardPanel().getBillModel(),
			// new String[]{"ccunhuobianma","batchcode"},
			// new String[]{"存货编码","批次号"});
			// int ii = ontpzd();
			// if(ii != UIDialog.ID_CANCEL)
			// onBatchCodeChange();
			break;
		case ISsButtun.zdqh:// 自动取货
			valudateWhereYeqian();
			// 拣货 存货唯一校验
			// BeforeSaveValudate.beforeSaveBodyUnique(getBillCardPanelWrapper().getBillCardPanel().getBillTable(),
			// getBillCardPanelWrapper().getBillCardPanel().getBillModel(),
			// new String[]{"ccunhuobianma","batchcode"},
			// new String[]{"存货编码","批次号"});
			onzdqh();
			// onBatchCodeChange();
			break;
		case ISsButtun.ckmx:// 查看明细
			onckmx();
			break;
		case ISsButtun.Qxqz:// 取消签字
			onQxqz();
			break;
		case ISsButtun.Qzqr:// 签字确认
			onQzqr();
			break;
		case nc.ui.wds.w80020206.buttun0206.ISsButtun.RefSoOrder:// 参照销售运单
			((MyClientUI) getBillUI()).setRefBillType(WdsWlPubConst.WDS5);
			onBillRef();

			break;
		case nc.ui.wds.w80020206.buttun0206.ISsButtun.RefRedSoOrder:// 参照销售运单
			((MyClientUI) getBillUI()).setRefBillType("30");
			onBillRef();
			break;
		}
	}

	/**
	 * 拣货对应的页签 必须在出库子表页签
	 * 
	 * @作者：zhf
	 * @说明：完达山物流项目
	 * @时间：2011-9-22下午02:56:45
	 */
	private void valudateWhereYeqian() throws Exception {
		String tablecode = getBillCardPanelWrapper().getBillCardPanel()
				.getBodyPanel().getTableCode();
		if (!"tb_outgeneral_b".equalsIgnoreCase(tablecode)) {
			throw new Exception("请选择表体存货页签");
		}
	}

	/**
	 * 参照后设置默认值
	 * 
	 * @throws Exception
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2011-9-22下午04:10:24
	 */
	@Override
	protected void setRefData(AggregatedValueObject[] vos) throws Exception {
		super.setRefData(vos);
		setInitByWhid(new String[] { "srl_pk", "pk_cargdoc" });
		// 如果 参照的出库仓库为空 设置默认仓库为当前保管员仓库
		setInitWarehouse("srl_pk");
		setPk_cargdoc();// 设置货位
	}

	/**
	 * 设置货位信息
	 * 
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2011-9-22下午02:17:50
	 */
	private void setPk_cargdoc() throws Exception {
		String pk_cargdoc = PuPubVO
				.getString_TrimZeroLenAsNull(getBillCardPanelWrapper()
						.getBillCardPanel().getHeadItem("pk_cargdoc")
						.getValueObject());
		if (pk_cargdoc == null || pk_cargdoc.length() == 0) {
			throw new Exception("请选择表头货位");
		}
		int count = getBillCardPanelWrapper().getBillCardPanel().getRowCount();
		if (count == 0) {
			return;
		}
		for (int i = 0; i < count; i++) {
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
					pk_cargdoc, i, "cspaceid");
		}
	}

	@Override
	public void onBillRef() throws Exception {
		super.onBillRef();
		// 库管理员赋值
		getBillCardPanelWrapper().getBillCardPanel()
				.getHeadItem("cwhsmanagerid").setValue(_getOperator());
		// 给表体业务日期赋值
		MyClientUI ui = (MyClientUI) getBillUI();
		// int
		// rowCounts=getBillCardPanelWrapper().getBillCardPanel().getBillTable(ui.getTableCodes()[0]).getRowCount();
		int rowCounts = getBillCardPanelWrapper().getBillCardPanel()
				.getBillTable(ui.getTableCodes()[0]).getRowCount();
		for (int i = 0; i < rowCounts; i++) {
			getBillCardPanelWrapper().getBillCardPanel()
					.getBillModel(ui.getTableCodes()[0])
					.setValueAt(_getDate(), i, "dbizdate");
			getBillCardPanelWrapper().getBillCardPanel()
					.getBillModel(ui.getTableCodes()[0])
					.setValueAt(getPk_cargDoc(), i, "cspaceid");
		}
		getBillUI().updateUI();
	}

	@Override
	protected void onBoSave() throws Exception {
		// 对贴签数量 小于 实入数量的校验
		if (getBillUI().getVOFromUI().getChildrenVO() != null) {
			TbOutgeneralBVO[] tbs = (TbOutgeneralBVO[]) getBillUI()
					.getVOFromUI().getChildrenVO();
			for (int i = 0; i < tbs.length; i++) {
				UFDouble u1 = PuPubVO.getUFDouble_NullAsZero(tbs[i]
						.getNoutassistnum());
				UFDouble u2 = PuPubVO.getUFDouble_NullAsZero(tbs[i]
						.getNtagnum());
				if (u1.sub(u2).doubleValue() < 0) {
					throw new BusinessException("贴签数量   不能大于实入数量");
				}
			}
			//2014-01-07 宇少 保存时增加比对运单上 回写数量begin
			if (tbs[0].getGeneral_b_pk() == null) {//判断表体是否有pk，如果没有代表是新增保存
				LongTimeTask lTask = new LongTimeTask();
				int size = tbs.length;
				String[] pks = new String[size];
				for (int i = 0; i < tbs.length; i++) {
					// 取得上层资源ID
					pks[i] = tbs[i].getCsourcebillbid();//取得表体所参照的资源pk在wds_soorder_b（运单表）里去查询对应的主键pk
				}
				Map<String, List<TbOutgeneralBVO>> returndata = (Map<String, List<TbOutgeneralBVO>>) lTask
						.callRemoteService("wds", "nc.bo.other.out.CheckNumDMO",
								"doQuery", new Class[] { String[].class },
								new Object[] { pks }, 2);
				if (returndata != null) {
					for (int i = 0; i < returndata.size(); i++) {
						TbOutgeneralBVO item = tbs[i];
						String key = item.getCsourcebillbid();
						List<TbOutgeneralBVO> l = returndata.get(key);
						UFDouble djsl = item.getNoutassistnum();
						UFDouble kzsl = l.get(0).getNshouldoutnum();
						if (djsl.compareTo(kzsl) > 0) {
							throw new Exception(l.get(0).getCinvbasid()
									+ "本次可做数量为" + kzsl + "单据录入数量为" + djsl);
						}
					}
				}
			}
		}
		//2014-01-07 宇少 增加比对运单上 回写数量end
		BillItem[] bte2 = getBillCardPanelWrapper().getBillCardPanel()
				.getBillModel().getBodyItems();
		if (bte2 == null || bte2.length == 0) {

		}
		super.onBoSave();
	}

	// 取消签字
	protected void onQxqz() throws Exception {
		try {
			if (getBufferData().getCurrentVO() != null) {
				getBillManageUI().showHintMessage("正在执行取消签字...");
				int retu = getBillManageUI().showOkCancelMessage(
						"取消签字会删除下游装卸费核算单，是否确认取消签字?");
				if (retu != UIDialog.ID_OK) {
					return;
				}
				AggregatedValueObject aObject = getBufferData()
						.getCurrentVOClone();
				TbOutgeneralHVO generalh = (TbOutgeneralHVO) aObject
						.getParentVO();
				generalh.setVbillstatus(IBillStatus.FREE);// 自由状态
				generalh.setCregister(null);// 签字人主键
				generalh.setTaccounttime(null);// 签字时间
				generalh.setQianzidate(null);// 签字日期
				ArrayList<String> list = new ArrayList<String>();
				list.add(_getDate().toString());
				list.add(_getOperator());// 登录人
				// 动作脚本
				PfUtilClient.processAction(getBillManageUI(), "CANELSIGN",
						getBillManageUI().getUIControl().getBillType(),
						_getDate().toString(), aObject, list);
				// 更新缓存
				onBoRefresh();
			}
		} catch (Exception e) {
			Logger.error(e);
			getBillManageUI().showErrorMessage("取消签字失败:" + e.getMessage());
		}
	}

	/**
	 * 更新列表数据
	 */
	protected void updateListVo(int[] rows) throws java.lang.Exception {
		CircularlyAccessibleValueObject vo = null;
		if (rows != null && rows.length >= 0) {
			for (int i : rows) {
				vo = getBufferData().getVOByRowNo(i).getParentVO();
				getBillListPanelWrapper().updateListVo(vo, i);
			}
		}
	}

	protected BillListPanelWrapper getBillListPanelWrapper() {
		return getBillManageUI().getBillListWrapper();
	}

	// 签字确认
	protected void onQzqr() throws Exception {
		try {
			if (getBufferData().getCurrentVO() != null) {
				getBillManageUI().showHintMessage("正在执行签字...");
				int retu = getBillManageUI().showOkCancelMessage("确认签字?");
				if (retu != 1) {
					return;
				}
				AggregatedValueObject aObject = getBufferData()
						.getCurrentVOClone();
				TbOutgeneralHVO generalh = (TbOutgeneralHVO) aObject
						.getParentVO();
				generalh.setVbillstatus(IBillStatus.CHECKPASS);// 审批状态
				generalh.setCregister(_getOperator());// 签字人主键
				generalh.setTaccounttime(getBillUI()._getServerTime()
						.toString());// 签字时间
				generalh.setQianzidate(_getDate());// 签字日期
				ArrayList<String> list = new ArrayList<String>();
				list.add(_getDate().toString());
				list.add(_getOperator());// 登录人
				// 动作脚本
				PfUtilClient.processAction(getBillManageUI(), "SIGN",
						getBillManageUI().getUIControl().getBillType(),
						_getDate().toString(), aObject, list);
				// 更新缓存
				onBoRefresh();
			}
		} catch (Exception e) {
			Logger.error(e);
			getBillManageUI().showErrorMessage("签字失败:" + e.getMessage());
		}
	}

	// @Override
	// protected void onBoPrint() throws Exception {
	// // TODO Auto-generated method stub
	// super.onBoPrint();
	// Integer iprintcount
	// =PuPubVO.getInteger_NullAs(getBillCardPanelWrapper().getBillCardPanel().getTailItem("iprintcount").getValueObject(),
	// 0) ;
	// iprintcount=++iprintcount;
	// getBillCardPanelWrapper().getBillCardPanel().getTailItem("iprintcount").setValue(iprintcount);
	// getBufferData().getCurrentVO().getParentVO().setAttributeValue("iprintcount",
	// iprintcount);
	// HYPubBO_Client.update((SuperVO)getBufferData().getCurrentVO().getParentVO());
	// }
	protected void onBoEdit() throws Exception {
		valuteOrder();
		super.onBoEdit();
		// zhf add
		// setHeadPartEdit(false);
		// getButtonManager().getButton(IBillButton.Line).setEnabled(false);
		// getBillUI().updateButtons();
		// setInitByWhid(new String[]{"srl_pk","pk_cargdoc"});
	}

	private void valuteOrder() throws Exception {
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		SuperVO[] vos = (SuperVO[]) vo.getChildrenVO();
		if (vos == null || vos.length == 0)
			return;
		SuperVO bvo = (SuperVO) vos[0];
		String csid = PuPubVO.getString_TrimZeroLenAsNull(bvo
				.getAttributeValue("csourcebillhid"));
		// 查询运单单看是否已经冻结
		SuperVO[] ovs = HYPubBO_Client.queryByCondition(SoorderVO.class,
				" isnull(dr,0)=0 and pk_soorder='" + csid + "'");
		if (ovs == null || ovs.length == 0)
			return;
		SoorderVO head = (SoorderVO) ovs[0];
		UFBoolean isdj = PuPubVO.getUFBoolean_NullAs(head.getFisended(),
				new UFBoolean(false));
		if (isdj.booleanValue() == true) {
			throw new Exception("上游运单已经冻结 ,不能作废");
		}

	}

	@Override
	// add xjx
	protected void onBoPrint() throws Exception {
		// 　如果是列表界面，使用ListPanelPRTS数据源
		if (getBillManageUI().isListPanelSelected()) {
			nc.ui.pub.print.IDataSource dataSource = new MyListSoDateSource(
					getBillUI()._getModuleCode(),
					((BillManageUI) getBillUI()).getBillListPanel());
			nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(
					null, dataSource);
			print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(),
					getBillUI()._getModuleCode(), getBillUI()._getOperator(),
					getBillUI().getBusinessType(), getBillUI().getNodeKey());
			if (print.selectTemplate() == 1)
				print.preview();
		} else {
			final nc.ui.pub.print.IDataSource dataSource = new MySoDateSource(
					getBillUI()._getModuleCode(), getBillCardPanelWrapper()
							.getBillCardPanel());
			final nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(
					null, dataSource);
			print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(),
					getBillUI()._getModuleCode(), getBillUI()._getOperator(),
					getBillUI().getBusinessType(), getBillUI().getNodeKey());
			if (print.selectTemplate() == 1)
				print.preview();
			// 更改数据源，支持托盘打印
			// super.onBoPrint();
		}
		Integer iprintcount = PuPubVO.getInteger_NullAs(getBufferData()
				.getCurrentVO().getParentVO().getAttributeValue("cdt_pk"), 0);
		iprintcount = iprintcount + 1;
		getBufferData().getCurrentVO().getParentVO()
				.setAttributeValue("iprintcount", iprintcount);
		try {
			HYPubBO_Client.update((SuperVO) getBufferData().getCurrentVO()
					.getParentVO());
			onBoRefresh();
		} catch (final UifException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}
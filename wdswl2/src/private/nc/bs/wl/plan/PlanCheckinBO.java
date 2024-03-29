package nc.bs.wl.plan;

import java.util.ArrayList;
import java.util.List;

import nc.bd.accperiod.AccountCalendar;
import nc.bd.accperiod.InvalidAccperiodExcetion;
import nc.bs.dao.BaseDAO;
import nc.bs.wl.pub.WdsPubResulSetProcesser;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.dm.SendplaninB2VO;
import nc.vo.dm.SendplaninBVO;
import nc.vo.dm.SendplaninVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.wl.pub.VOTool;
import nc.vo.wl.pub.WdsWlPubConst;

/**
 * @作者：lyf
 * @说明：完达山物流项目 发运计划录入（WDS1）后台类
 */
public class PlanCheckinBO {
	// 计划主数量
	private String planNum = "nplannum";
	// 计划辅数量
	private String planBnum = "nassplannum";
	// 入库仓库
	private String pk_inwhouse = "pk_inwhouse";
	// 出库仓库
	private String pk_outwhouse = "pk_outwhouse";
	BaseDAO dao = null;

	private BaseDAO getBaseDAO() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	/**
	 * 
	 * @作者：lyf
	 * @说明：完达山物流项目 保存前校验 当前登录人的管理的仓库在当前会计月是否已经有月计划
	 * @时间：2011-3-23下午09:14:56
	 * @param pk_inwhouse
	 *            =调入仓库p主键，pk=主表主键
	 */
	public void beforeCheck(String pk_outwhouse, String pk_inwhouse, String pk,
			String date, UFBoolean reserve15) throws BusinessException {
		AccountCalendar calendar = AccountCalendar.getInstance();
		calendar.setDate(new UFDate(date));// spf add
		UFDate beginDate = calendar.getMonthVO().getBegindate();
		UFDate endDate = calendar.getMonthVO().getEnddate();
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) from wds_sendplanin ");
		sql.append(" where wds_sendplanin.iplantype=0 and dmakedate between '");
		sql.append(beginDate + "' and '" + endDate);
		sql.append("' and pk_inwhouse ='" + pk_inwhouse + "' ");
		sql.append("  and pk_outwhouse='" + pk_outwhouse + "'");
		// sql.append("  and isnull(reserve15,'N')='"+reserve15+"'");//是否欠发
		sql.append(" and isnull(dr,0)=0");

		int i = PuPubVO.getInteger_NullAs(getBaseDAO().executeQuery(
				sql.toString(), WdsPubResulSetProcesser.COLUMNPROCESSOR), 0);
		if (pk == null || "".equalsIgnoreCase(pk)) {
			if (i > 0) {
				throw new BusinessException("该调入仓库，当前会计月已经有月计划,只可以做追加计划");
			}
		} else {
			if (i > 1) {
				throw new BusinessException("该调入仓库，当前会计月已经有月计划,只可以做追加计划");
			}
		}

	}

	/*
	 * add by yf 2014-02-11 增加虚拟维度，保存发运计划时，普通计划和虚拟计划分开校验 改void
	 * nc.bs.wl.plan.PlanCheckinBO.beforeCheck(String pk_outwhouse, String
	 * pk_inwhouse, String pk, String date, UFBoolean reserve15) throws
	 * BusinessException vo参数
	 */
	public void beforeCheck(AggregatedValueObject vo) throws BusinessException {
		// -----
		SendplaninVO hend = (SendplaninVO) vo.getParentVO();// 主表vo
		UFDate date = hend.getDmakedate();// 制单日期
		// 入库仓库主键
		String pk_in = PuPubVO.getString_TrimZeroLenAsNull(hend
				.getAttributeValue(pk_inwhouse));
		// 出库仓库主键
		String pk_out = PuPubVO.getString_TrimZeroLenAsNull(hend
				.getAttributeValue(pk_outwhouse));
		String pk = hend.getPk_sendplanin();// 计划pk
		UFBoolean reserve15 = hend.getReserve15();//
		UFBoolean fisxn = PuPubVO.getUFBoolean_NullAs(hend
				.getAttributeValue(WdsWlPubConst.dmplan_xn), UFBoolean.FALSE);// 是否虚拟
		// -----

		AccountCalendar calendar = AccountCalendar.getInstance();
		calendar.setDate(date);
		UFDate beginDate = calendar.getMonthVO().getBegindate();
		UFDate endDate = calendar.getMonthVO().getEnddate();
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) from wds_sendplanin ");
		sql.append(" where wds_sendplanin.iplantype=0 and dmakedate between '");
		sql.append(beginDate + "' and '" + endDate);
		sql.append("' and pk_inwhouse ='" + pk_in + "' ");
		sql.append("  and pk_outwhouse='" + pk_out + "' ");
		// sql.append("  and isnull(reserve15,'N')='"+reserve15+"'");//是否欠发
		// 是否虚拟
		sql.append("  and isnull(" + WdsWlPubConst.dmplan_xn + ",'N')='"
				+ fisxn + "'");// 是否虚拟
		sql.append(" and isnull(dr,0)=0");

		int i = PuPubVO.getInteger_NullAs(getBaseDAO().executeQuery(
				sql.toString(), WdsPubResulSetProcesser.COLUMNPROCESSOR), 0);
		if (pk == null || "".equalsIgnoreCase(pk)) {
			if (i > 0) {
				throw new BusinessException("该调入仓库，当前会计月已经有月计划,只可以做追加计划");
			}
		} else {
			if (i > 1) {
				throw new BusinessException("该调入仓库，当前会计月已经有月计划,只可以做追加计划");
			}
		}

	}

	/**
	 * 
	 * @作者：lyf
	 * @说明：完达山物流项目 弃审前校验 是否已经安排生产下游发运订单
	 * @时间：2011-3-27上午09:44:46
	 * @param 要弃审的
	 *            发运计划单据
	 * @throws BusinessException
	 */
	public void beforeUnApprove(AggregatedValueObject obj)
			throws BusinessException {
		if (obj == null) {
			return;
		}
		SendplaninVO parent = (SendplaninVO) obj.getParentVO();
		String pk_sendplanin = parent.getPk_sendplanin();

		StringBuffer sql = new StringBuffer();
		sql.append(" select count(0) ");
		sql.append(" from wds_sendorder ");
		sql.append(" join wds_sendorder_b ");
		sql
				.append(" on wds_sendorder.pk_sendorder= wds_sendorder_b.pk_sendorder");
		sql
				.append(" where isnull(wds_sendorder.dr,0)=0 and isnull(wds_sendorder_b.dr,0)=0 ");
		sql.append(" and wds_sendorder_b.csourcebillhid ='" + pk_sendplanin
				+ "'");
		int i = PuPubVO.getInteger_NullAs(getBaseDAO().executeQuery(
				sql.toString(), WdsPubResulSetProcesser.COLUMNPROCESSOR), 0);
		if (i > 0) {
			throw new BusinessException("已有下游发运订单，请先删除发运订单再做此操作");
		}

		// 月计划审批 查看追加计划是否有审批的 如果有则 不允许弃审
		Object iplantype = parent.getAttributeValue("iplantype");
		if (iplantype != null && 0 == (Integer) iplantype) {
			String pk_inwhouse = parent.getPk_inwhouse();
			if (pk_inwhouse == null) {
				pk_inwhouse = "";
			}
			String pk_outwhouse = parent.getPk_outwhouse();
			if (pk_outwhouse == null) {
				pk_outwhouse = "";
			}
			// modify by yf 2014-02-11 增加虚拟维度 begin
			UFBoolean fisxn = PuPubVO.getUFBoolean_NullAs(parent
					.getAttributeValue(WdsWlPubConst.dmplan_xn), UFBoolean.FALSE);// 是否虚拟
			// modify by yf 2014-02-11 增加虚拟维度 begin
			// modify by yf 2013-11-26 以制单日期，确定计划会计期间 begin
			// AccountCalendar calendar = AccountCalendar.getInstance();
			AccountCalendar calendar = getCalendar(parent.getDmakedate());
			// modify by yf 2013-11-26 以制单日期，确定计划会计期间 end
			UFDate beginDate = calendar.getMonthVO().getBegindate();
			UFDate endDate = calendar.getMonthVO().getEnddate();
			StringBuffer sql1 = new StringBuffer();
			sql1.append(" select pk_sendplanin  from");
			sql1.append(" wds_sendplanin ");
			sql1.append(" where vbillstatus=1 ");// 单据状态=审批通过
			sql1.append(" and iplantype =1");// 追加月计划
			sql1.append(" and isnull(dr,0)=0");
			sql1.append(" and dmakedate between '");
			sql1.append(beginDate + "' and '" + endDate);
			sql1.append("' and pk_inwhouse ='" + pk_inwhouse + "'");
			sql1.append(" and pk_outwhouse ='" + pk_outwhouse + "'");
			// modify by yf 2014-02-11 增加虚拟维度 begin
			sql1.append("  and isnull(" + WdsWlPubConst.dmplan_xn + ",'N')='"
					+ fisxn + "'");
			// modify by yf 2014-02-11 增加虚拟维度 end
			List<SendplaninBVO> mods = new ArrayList<SendplaninBVO>();
			Object o = getBaseDAO().executeQuery(sql1.toString(),
					new ColumnProcessor());
			if (o == null || "".equalsIgnoreCase((String) o)) {

			} else {
				throw new BusinessException("已经审批过的追加月计划");
			}
		}
	}

	// 追加计划校验
	public void beforeCheck1(String pk_inwhouse, String pk)
			throws BusinessException {
		AccountCalendar calendar = AccountCalendar.getInstance();
		UFDate beginDate = calendar.getMonthVO().getBegindate();
		UFDate endDate = calendar.getMonthVO().getEnddate();
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) from wds_sendplanin ");
		sql.append(" where dmakedate between '");
		sql.append(beginDate + "' and '" + endDate);
		sql.append("' and pk_inwhouse ='" + pk_inwhouse + "'");
		sql.append(" and wds_sendplanin.iplantype=0 ");
		// sql.append("  and isnull(reserve15,'N')='"+reserve15+"'");//是否欠发
		sql.append(" and isnull(dr,0)=0");
		int i = PuPubVO.getInteger_NullAs(getBaseDAO().executeQuery(
				sql.toString(), WdsPubResulSetProcesser.COLUMNPROCESSOR), 0);
		if (i <= 0) {
			throw new BusinessException("该调入仓库，当前会计月没有月计划");
		}
	}

	// 将追加计划合并到月计划
	public void planStats(AggregatedValueObject obj2) throws BusinessException {
		// 将传来的对象克隆一份
		AggregatedValueObject obj = VOTool.aggregateVOClone(obj2);
		SendplaninVO parent = (SendplaninVO) obj.getParentVO();
		String pk_inwhouse = parent.getPk_inwhouse();
		if (pk_inwhouse == null) {
			pk_inwhouse = "";
		}
		String pk_outwhouse = parent.getPk_outwhouse();
		if (pk_outwhouse == null) {
			pk_outwhouse = "";
		}
		// modify by yf 2014-02-11 增加虚拟维度 begin
		UFBoolean fisxn = PuPubVO.getUFBoolean_NullAs(parent
				.getAttributeValue(WdsWlPubConst.dmplan_xn), UFBoolean.FALSE);// 是否虚拟
		// modify by yf 2014-02-11 增加虚拟维度 begin
		SendplaninBVO[] childs = (SendplaninBVO[]) obj.getChildrenVO();
		SendplaninVO hend = (SendplaninVO) obj.getParentVO();
		// UFBoolean reserve15= PuPubVO.getUFBoolean_NullAs(hend.getReserve15(),
		// UFBoolean.FALSE);//表头是否欠发
		// modify by yf 2013-12-03 以制单日期，确定计划会计期间 begin
		// AccountCalendar calendar = AccountCalendar.getInstance();
		AccountCalendar calendar = getCalendar(parent.getDmakedate());
		// modify by yf 2013-12-03 以制单日期，确定计划会计期间 end
		UFDate beginDate = calendar.getMonthVO().getBegindate();
		UFDate endDate = calendar.getMonthVO().getEnddate();
		// 1.查询月计划:如果月计划还没有审批，追加计划不能审批
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_sendplanin  from");
		sql.append(" wds_sendplanin ");
		sql.append(" where vbillstatus=1 ");// 单据状态=审批通过
		sql.append(" and iplantype =0");// 月计划
		sql.append(" and isnull(dr,0)=0");
		// if(reserve15.booleanValue()==false){
		// sql.append("  and isnull(reserve15,'N')='N'");//不是欠发
		// }else{
		// sql.append("  and isnull(reserve15,'N')='Y'");//是欠发
		// }
		sql.append(" and dmakedate between '");
		sql.append(beginDate + "' and '" + endDate);
		sql.append("' and pk_inwhouse ='" + pk_inwhouse + "'");
		sql.append(" and pk_outwhouse ='" + pk_outwhouse + "'");
		// modify by yf 2014-02-11 增加虚拟维度 begin
		sql.append("  and isnull(" + WdsWlPubConst.dmplan_xn + ",'N')='"
				+ fisxn + "'");
		// modify by yf 2014-02-11 增加虚拟维度 end
		List<SendplaninBVO> adds = new ArrayList<SendplaninBVO>();
		List<SendplaninBVO> mods = new ArrayList<SendplaninBVO>();
		Object o = getBaseDAO().executeQuery(sql.toString(),
				new ColumnProcessor());
		if (o == null || "".equalsIgnoreCase((String) o)) {
			throw new BusinessException("未找到已经审批过的月计划 ");
		}
		// 1.1查询月计划的表体明细
		String cond = " pk_sendplanin='" + o + "' and isnull(dr,0)=0";
		List<SendplaninBVO> list = (List<SendplaninBVO>) getBaseDAO()
				.retrieveByClause(SendplaninBVO.class, cond);
		// 2.将追加计划存货追加到月计划上：
		// 大日期存货追加;新增存货增加;相同的非大日期存货更新月计划安排数量;
		boolean isExist = false;// 是否更新月计划
		UFBoolean bisdate = null; // 是否大日期 月计划
		UFBoolean bisdate1 = null; // 是否大日期 追加计划
		ArrayList<SendplaninB2VO> b2List_Update = new ArrayList<SendplaninB2VO>();
		ArrayList<SendplaninB2VO> b2List_Add = new ArrayList<SendplaninB2VO>();
		for (int i = 0; i < childs.length; i++) {
			isExist = false;
			UFDouble nplanNum = PuPubVO.getUFDouble_NullAsZero(childs[i]
					.getNplannum());
			if (nplanNum.doubleValue() <= 0) {// 本次有安排数量的，才更新月计划
				continue;
			}
			// 获得需要更新到月计划的表体存货
			bisdate = PuPubVO.getUFBoolean_NullAs(childs[i].getBisdate(),
					UFBoolean.FALSE);
			if (!bisdate.booleanValue()) {
				for (int j = 0; j < list.size(); j++) {
					bisdate1 = PuPubVO.getUFBoolean_NullAs(list.get(j)
							.getBisdate(), UFBoolean.FALSE);
					if (!bisdate1.booleanValue()) {// 判断是否大日期
						if (childs[i].getPk_invmandoc().equalsIgnoreCase(
								list.get(j).getPk_invmandoc())) {// 存货名称相同
							// 记录来源明细信息
							SendplaninB2VO b2vo = new SendplaninB2VO();
							b2vo.setCsourcebillhid(parent.getPrimaryKey());
							b2vo.setCsourcebillbid(childs[i].getPrimaryKey());
							b2vo.setCsourcetype(parent.getPk_billtype());
							b2vo.setVsourcebillcode(parent.getVbillno());
							b2vo.setSorce_ndealnum(childs[i].getNplannum());
							b2vo.setSorce_nassdealnum(childs[i]
									.getNassplannum());
							b2vo.setPk_sendplanin(list.get(j)
									.getPk_sendplanin());
							b2vo
									.setPk_sendplanin_b(list.get(j)
											.getPrimaryKey());
							b2List_Update.add(b2vo);
							// 更新月计划安排数量
							list.get(j).setNplannum(
									PuPubVO.getUFDouble_NullAsZero(
											list.get(j).getNplannum()).add(
											nplanNum));
							list
									.get(j)
									.setNassplannum(
											PuPubVO
													.getUFDouble_NullAsZero(
															list
																	.get(j)
																	.getNassplannum())
													.add(
															PuPubVO
																	.getUFDouble_NullAsZero(childs[i]
																			.getNassplannum())));
							mods.add(list.get(j));
							isExist = true;
							break;
						}
					}
				}
			}
			// 获得需要追加到月计划的表体存货
			if (!isExist) {
				// 记录来源明细信息
				SendplaninB2VO b2vo = new SendplaninB2VO();
				b2vo.setCsourcebillhid(parent.getPrimaryKey());
				b2vo.setCsourcebillbid(childs[i].getPrimaryKey());
				b2vo.setCsourcetype(parent.getPk_billtype());
				b2vo.setVsourcebillcode(parent.getVbillno());
				b2vo.setSorce_ndealnum(childs[i].getNplannum());
				b2vo.setSorce_nassdealnum(childs[i].getNassplannum());
				b2vo.setPk_sendplanin(o.toString());
				b2vo.setPk_sendplanin_b(null);
				b2List_Add.add(b2vo);
				//
				childs[i].setPk_sendplanin((String) o);
				childs[i].setPk_sendplanin_b(null);
				adds.add(childs[i]);
			}
		}
		// 更新数据库
		for (int i = 0; i < adds.size(); i++) {
			String pk_sendplanin_b = getBaseDAO().insertVOWithPK(adds.get(i));
			b2List_Add.get(i).setPk_sendplanin_b(pk_sendplanin_b);
		}
		if (mods.size() > 0) {
			getBaseDAO().updateVOList(mods);
		}
		// 更新来源信息
		getBaseDAO().insertVOList(b2List_Add);
		getBaseDAO().insertVOList(b2List_Update);

	}

	/**
	 * 
	 * @作者：mlr
	 * @说明：完达山物流项目 检查发运计划的计划数量不允许都为空
	 * @时间：2011-7-8下午07:18:43
	 * @param vo
	 * @throws BusinessException
	 */
	public void checkNotAllNull(AggregatedValueObject vo)
			throws BusinessException {
		if (vo.getChildrenVO() == null || vo.getChildrenVO().length == 0) {
			return;
		}
		SuperVO[] vos = (SuperVO[]) vo.getChildrenVO();
		UFDouble znum = new UFDouble("0.0");
		UFDouble bznum = new UFDouble("0.0");
		int size = vos.length;
		for (int i = 0; i < size; i++) {
			UFDouble num = PuPubVO.getUFDouble_NullAsZero(vos[i]
					.getAttributeValue(planNum));
			UFDouble bnum = PuPubVO.getUFDouble_NullAsZero(vos[i]
					.getAttributeValue(planBnum));
			znum = znum.add(num);
			bznum = bznum.add(bnum);
		}
		if (znum.doubleValue() <= 0 || bznum.doubleValue() <= 0) {
			throw new BusinessException("发运计划[月计划]的计划数量不允许都为空");
		}
	}

	/**
	 * 
	 * @作者：mlr
	 * @说明：完达山物流项目 检查发运计划追加计划 如果计划数量都为空 不允许保存 如果没有月计划 追加计划不允许保存
	 * @时间：2011-7-8下午07:18:43
	 * @param vo
	 * @throws BusinessException
	 */
	public void checkForBplan(AggregatedValueObject vo)
			throws BusinessException {
		// 保存追加计划时 计划数量不允许为空
		checkNotAllNull(vo);
		// 校验是否存在月计划,如果不存在月计划不允许保存
		SuperVO hvo = (SuperVO) vo.getParentVO();
		SendplaninVO hend = (SendplaninVO) vo.getParentVO();
		UFBoolean reserve15 = PuPubVO.getUFBoolean_NullAs(hend.getReserve15(),
				UFBoolean.FALSE);// 表头是否欠发
		// modify by yf 2014-02-11 增加虚拟维度 begin
		UFBoolean fisxn = PuPubVO.getUFBoolean_NullAs(hend
				.getAttributeValue(WdsWlPubConst.dmplan_xn), UFBoolean.FALSE);// 是否虚拟
		// modify by yf 2014-02-11 增加虚拟维度 begin
		// modify by yf 2013-12-03 以制单日期，确定计划会计期间 begin
		// AccountCalendar calendar = AccountCalendar.getInstance();
		AccountCalendar calendar = getCalendar(hend.getDmakedate());
		// modify by yf 2013-12-03 以制单日期，确定计划会计期间 end
		UFDate beginDate = calendar.getMonthVO().getBegindate();
		UFDate endDate = calendar.getMonthVO().getEnddate();
		// 入库仓库主键
		String pk_in = PuPubVO.getString_TrimZeroLenAsNull(hvo
				.getAttributeValue(pk_inwhouse));
		// 出库仓库主键
		String pk_out = PuPubVO.getString_TrimZeroLenAsNull(hvo
				.getAttributeValue(pk_outwhouse));
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_sendplanin  from");
		sql.append(" wds_sendplanin where ");
		sql.append(" isnull(wds_sendplanin.dr,0)=0");
		if (reserve15.booleanValue() == false) {
			sql.append("  and isnull(reserve15,'N')='N'");// 不是欠发
		} else {
			sql.append("  and isnull(reserve15,'N')='Y'");// 是欠发
		}
		sql.append(" and dmakedate between '");
		sql.append(beginDate + "' and '" + endDate);
		sql.append("' and pk_inwhouse ='" + pk_in + "'");
		sql.append("  and pk_outwhouse='" + pk_out + "'");
		sql.append("  and wds_sendplanin.iplantype=0 ");
		// modify by yf 2014-02-11 增加虚拟维度 begin
		sql.append("  and isnull(" + WdsWlPubConst.dmplan_xn + ",'N')='"
				+ fisxn + "'");
		// modify by yf 2014-02-11 增加虚拟维度 end
		String pk = PuPubVO.getString_TrimZeroLenAsNull(getBaseDAO()
				.executeQuery(sql.toString(),
						WdsPubResulSetProcesser.COLUMNPROCESSOR));
		if (pk == null || pk.trim().length() == 0) {
			throw new BusinessException(" 该月还没有月计划或是欠发月计划   不能添加追加计划");
		}
	}

	/**
	 * 
	 * @作者：lyf
	 * @说明：完达山物流项目 : 将追加计划从月计划总拆出来
	 * @时间：2011-11-30下午01:15:25
	 * @param obj2
	 * @throws BusinessException
	 */
	public void unplanStats(AggregatedValueObject obj2)
			throws BusinessException {
		// 将传来的对象克隆一份
		AggregatedValueObject obj = VOTool.aggregateVOClone(obj2);

		SendplaninVO parent = (SendplaninVO) obj.getParentVO();
		// UFBoolean reserve15=
		// PuPubVO.getUFBoolean_NullAs(parent.getReserve15(),
		// UFBoolean.FALSE);//表头是否欠发

		String pk_inwhouse = parent.getPk_inwhouse();
		if (pk_inwhouse == null) {
			pk_inwhouse = "";
		}
		String pk_outwhouse = parent.getPk_outwhouse();
		if (pk_outwhouse == null) {
			pk_outwhouse = "";
		}
		// modify by yf 2014-02-11 增加虚拟维度 begin
		UFBoolean fisxn = PuPubVO.getUFBoolean_NullAs(parent
				.getAttributeValue(WdsWlPubConst.dmplan_xn), UFBoolean.FALSE);// 是否虚拟
		// modify by yf 2014-02-11 增加虚拟维度 begin
		SendplaninBVO[] childs = (SendplaninBVO[]) obj.getChildrenVO();
		// modify by yf 2013-12-03 以制单日期，确定计划会计期间 begin
		// AccountCalendar calendar = AccountCalendar.getInstance();
		AccountCalendar calendar = getCalendar(parent.getDmakedate());
		// modify by yf 2013-12-03 以制单日期，确定计划会计期间 end
		UFDate beginDate = calendar.getMonthVO().getBegindate();
		UFDate endDate = calendar.getMonthVO().getEnddate();
		// 1.查询月计划:如果月计划没有处于审批态，则当前不允许弃审
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_sendplanin  from");
		sql.append(" wds_sendplanin ");
		sql.append(" where vbillstatus=1 ");// 单据状态=审批通过
		sql.append(" and iplantype =0");// 月计划
		sql.append(" and isnull(dr,0)=0");
		// if(reserve15.booleanValue()==false){
		// sql.append("  and isnull(reserve15,'N')='N'");//不是欠发
		// }else{
		// sql.append("  and isnull(reserve15,'N')='Y'");//是欠发
		// }
		sql.append(" and dmakedate between '");
		sql.append(beginDate + "' and '" + endDate);
		sql.append("' and pk_inwhouse ='" + pk_inwhouse + "'");
		sql.append(" and pk_outwhouse ='" + pk_outwhouse + "'");
		// modify by yf 2014-02-11 增加虚拟维度 begin
		sql.append("  and isnull(" + WdsWlPubConst.dmplan_xn + ",'N')='"
				+ fisxn + "'");
		// modify by yf 2014-02-11 增加虚拟维度 end
		List<SendplaninBVO> mods = new ArrayList<SendplaninBVO>();
		Object o = getBaseDAO().executeQuery(sql.toString(),
				new ColumnProcessor());
		if (o == null || "".equalsIgnoreCase((String) o)) {
			throw new BusinessException("未找到已经审批过的月计划");
		}
		// 2.查询月计划表体数据：子表1和记录来源明细记录的子表2
		String cond = " pk_sendplanin='" + o + "' and isnull(dr,0)=0";
		List<SendplaninBVO> list = (List<SendplaninBVO>) getBaseDAO()
				.retrieveByClause(SendplaninBVO.class, cond);
		List<SendplaninB2VO> list_csoure = (List<SendplaninB2VO>) getBaseDAO()
				.retrieveByClause(SendplaninB2VO.class, cond);
		for (int i = 0; i < childs.length; i++) {
			// 2.1查找追加计划明细对应 月计划表体明细
			String pk_sendplanin_b_source = childs[i].getPrimaryKey();
			String pk_sendplanin_b = null;
			for (int j = 0; j < list_csoure.size(); j++) {
				String csourcebillbid = list_csoure.get(j).getCsourcebillbid();
				if (pk_sendplanin_b_source.equalsIgnoreCase(csourcebillbid)) {
					pk_sendplanin_b = list_csoure.get(j).getPk_sendplanin_b();
				}
			}
			// if(pk_sendplanin_b == null ||
			// "".equalsIgnoreCase(pk_sendplanin_b)){
			// throw new BusinessException("查询数据异常：未查询到追加计划明细，对应的月计划明细");
			// }
			// 2.2 将追加计划数量从月计划总减去，并用减去后的计划数量和安排数量比较，判断是否已经安排
			if (pk_sendplanin_b != null && pk_sendplanin_b.length() > 0) {
				for (int j = 0; j < list.size(); j++) {
					if (pk_sendplanin_b.equalsIgnoreCase(list.get(j)
							.getPrimaryKey())) {
						list
								.get(j)
								.setNplannum(
										PuPubVO
												.getUFDouble_NullAsZero(
														list.get(j)
																.getNplannum())
												.sub(
														PuPubVO
																.getUFDouble_NullAsZero(childs[i]
																		.getNplannum())));
						list
								.get(j)
								.setNassplannum(
										PuPubVO
												.getUFDouble_NullAsZero(
														list
																.get(j)
																.getNassplannum())
												.sub(
														PuPubVO
																.getUFDouble_NullAsZero(childs[i]
																		.getNassplannum())));
						UFDouble ndealNum = PuPubVO.getUFDouble_NullAsZero(list
								.get(j).getNdealnum());
						UFDouble nplannum = PuPubVO.getUFDouble_NullAsZero(list
								.get(j).getNplannum());
						if (nplannum.sub(ndealNum).doubleValue() < 0) {
							throw new BusinessException(
									"不能弃审，月计划已经安排了该追加计划中的单品");
						}
						mods.add(list.get(j));
						break;
					}
				}
			}
		}
		if (mods.size() > 0) {
			getBaseDAO().updateVOList(mods);
		}

	}

	/**
	 * 根据日期返回日期所在会计期间日历
	 * 
	 * @yf
	 * @param date
	 * @return
	 * @throws InvalidAccperiodExcetion
	 */
	private AccountCalendar getCalendar(UFDate date)
			throws InvalidAccperiodExcetion {
		AccountCalendar calendar = AccountCalendar.getInstance();
		calendar.setDate(date);
		return calendar;
	}
}

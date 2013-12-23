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

/**
 * @���ߣ�lyf
 * @˵�������ɽ������Ŀ ���˼ƻ�¼�루WDS1����̨��
 */
public class PlanCheckinBO {
	// �ƻ�������
	private String planNum = "nplannum";
	// �ƻ�������
	private String planBnum = "nassplannum";
	// ���ֿ�
	private String pk_inwhouse = "pk_inwhouse";
	// ����ֿ�
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
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ ����ǰУ�� ��ǰ��¼�˵Ĺ���Ĳֿ��ڵ�ǰ������Ƿ��Ѿ����¼ƻ�
	 * @ʱ�䣺2011-3-23����09:14:56
	 * @param pk_inwhouse
	 *            =����ֿ�p������pk=��������
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
		// sql.append("  and isnull(reserve15,'N')='"+reserve15+"'");//�Ƿ�Ƿ��
		sql.append(" and isnull(dr,0)=0");

		int i = PuPubVO.getInteger_NullAs(getBaseDAO().executeQuery(
				sql.toString(), WdsPubResulSetProcesser.COLUMNPROCESSOR), 0);
		if (pk == null || "".equalsIgnoreCase(pk)) {
			if (i > 0) {
				throw new BusinessException("�õ���ֿ⣬��ǰ������Ѿ����¼ƻ�,ֻ������׷�Ӽƻ�");
			}
		} else {
			if (i > 1) {
				throw new BusinessException("�õ���ֿ⣬��ǰ������Ѿ����¼ƻ�,ֻ������׷�Ӽƻ�");
			}
		}

	}

	/**
	 * 
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ ����ǰУ�� �Ƿ��Ѿ������������η��˶���
	 * @ʱ�䣺2011-3-27����09:44:46
	 * @param Ҫ�����
	 *            ���˼ƻ�����
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
			throw new BusinessException("�������η��˶���������ɾ�����˶��������˲���");
		}

		// �¼ƻ����� �鿴׷�Ӽƻ��Ƿ��������� ������� ����������
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
			// modify by yf 2013-11-26 ���Ƶ����ڣ�ȷ���ƻ�����ڼ� begin
//			AccountCalendar calendar = AccountCalendar.getInstance();
			AccountCalendar calendar = getCalendar(parent.getDmakedate());
			// modify by yf 2013-11-26 ���Ƶ����ڣ�ȷ���ƻ�����ڼ� end
			UFDate beginDate = calendar.getMonthVO().getBegindate();
			UFDate endDate = calendar.getMonthVO().getEnddate();
			StringBuffer sql1 = new StringBuffer();
			sql1.append(" select pk_sendplanin  from");
			sql1.append(" wds_sendplanin ");
			sql1.append(" where vbillstatus=1 ");// ����״̬=����ͨ��
			sql1.append(" and iplantype =1");// ׷���¼ƻ�
			sql1.append(" and isnull(dr,0)=0");
			sql1.append(" and dmakedate between '");
			sql1.append(beginDate + "' and '" + endDate);
			sql1.append("' and pk_inwhouse ='" + pk_inwhouse + "'");
			sql1.append(" and pk_outwhouse ='" + pk_outwhouse + "'");
			List<SendplaninBVO> mods = new ArrayList<SendplaninBVO>();
			Object o = getBaseDAO().executeQuery(sql1.toString(),
					new ColumnProcessor());
			if (o == null || "".equalsIgnoreCase((String) o)) {

			} else {
				throw new BusinessException("�Ѿ���������׷���¼ƻ�");
			}
		}
	}

	// ׷�Ӽƻ�У��
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
		// sql.append("  and isnull(reserve15,'N')='"+reserve15+"'");//�Ƿ�Ƿ��
		sql.append(" and isnull(dr,0)=0");
		int i = PuPubVO.getInteger_NullAs(getBaseDAO().executeQuery(
				sql.toString(), WdsPubResulSetProcesser.COLUMNPROCESSOR), 0);
		if (i <= 0) {
			throw new BusinessException("�õ���ֿ⣬��ǰ�����û���¼ƻ�");
		}
	}

	// ��׷�Ӽƻ��ϲ����¼ƻ�
	public void planStats(AggregatedValueObject obj2) throws BusinessException {
		// �������Ķ����¡һ��
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
		SendplaninBVO[] childs = (SendplaninBVO[]) obj.getChildrenVO();
		SendplaninVO hend = (SendplaninVO) obj.getParentVO();
		// UFBoolean reserve15= PuPubVO.getUFBoolean_NullAs(hend.getReserve15(),
		// UFBoolean.FALSE);//��ͷ�Ƿ�Ƿ��
		// modify by yf 2013-12-03 ���Ƶ����ڣ�ȷ���ƻ�����ڼ� begin
//		AccountCalendar calendar = AccountCalendar.getInstance();
		AccountCalendar calendar = getCalendar(parent.getDmakedate());
		// modify by yf 2013-12-03 ���Ƶ����ڣ�ȷ���ƻ�����ڼ� end
		UFDate beginDate = calendar.getMonthVO().getBegindate();
		UFDate endDate = calendar.getMonthVO().getEnddate();
		// 1.��ѯ�¼ƻ�:����¼ƻ���û��������׷�Ӽƻ���������
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_sendplanin  from");
		sql.append(" wds_sendplanin ");
		sql.append(" where vbillstatus=1 ");// ����״̬=����ͨ��
		sql.append(" and iplantype =0");// �¼ƻ�
		sql.append(" and isnull(dr,0)=0");
		// if(reserve15.booleanValue()==false){
		// sql.append("  and isnull(reserve15,'N')='N'");//����Ƿ��
		// }else{
		// sql.append("  and isnull(reserve15,'N')='Y'");//��Ƿ��
		// }
		sql.append(" and dmakedate between '");
		sql.append(beginDate + "' and '" + endDate);
		sql.append("' and pk_inwhouse ='" + pk_inwhouse + "'");
		sql.append(" and pk_outwhouse ='" + pk_outwhouse + "'");
		List<SendplaninBVO> adds = new ArrayList<SendplaninBVO>();
		List<SendplaninBVO> mods = new ArrayList<SendplaninBVO>();
		Object o = getBaseDAO().executeQuery(sql.toString(),
				new ColumnProcessor());
		if (o == null || "".equalsIgnoreCase((String) o)) {
			throw new BusinessException("δ�ҵ��Ѿ����������¼ƻ� ");
		}
		// 1.1��ѯ�¼ƻ��ı�����ϸ
		String cond = " pk_sendplanin='" + o + "' and isnull(dr,0)=0";
		List<SendplaninBVO> list = (List<SendplaninBVO>) getBaseDAO()
				.retrieveByClause(SendplaninBVO.class, cond);
		// 2.��׷�Ӽƻ����׷�ӵ��¼ƻ��ϣ�
		// �����ڴ��׷��;�����������;��ͬ�ķǴ����ڴ�������¼ƻ���������;
		boolean isExist = false;// �Ƿ�����¼ƻ�
		UFBoolean bisdate = null; // �Ƿ������ �¼ƻ�
		UFBoolean bisdate1 = null; // �Ƿ������ ׷�Ӽƻ�
		ArrayList<SendplaninB2VO> b2List_Update = new ArrayList<SendplaninB2VO>();
		ArrayList<SendplaninB2VO> b2List_Add = new ArrayList<SendplaninB2VO>();
		for (int i = 0; i < childs.length; i++) {
			isExist = false;
			UFDouble nplanNum = PuPubVO.getUFDouble_NullAsZero(childs[i]
					.getNplannum());
			if (nplanNum.doubleValue() <= 0) {// �����а��������ģ��Ÿ����¼ƻ�
				continue;
			}
			// �����Ҫ���µ��¼ƻ��ı�����
			bisdate = PuPubVO.getUFBoolean_NullAs(childs[i].getBisdate(),
					UFBoolean.FALSE);
			if (!bisdate.booleanValue()) {
				for (int j = 0; j < list.size(); j++) {
					bisdate1 = PuPubVO.getUFBoolean_NullAs(list.get(j)
							.getBisdate(), UFBoolean.FALSE);
					if (!bisdate1.booleanValue()) {// �ж��Ƿ������
						if (childs[i].getPk_invmandoc().equalsIgnoreCase(
								list.get(j).getPk_invmandoc())) {// ���������ͬ
							// ��¼��Դ��ϸ��Ϣ
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
							// �����¼ƻ���������
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
			// �����Ҫ׷�ӵ��¼ƻ��ı�����
			if (!isExist) {
				// ��¼��Դ��ϸ��Ϣ
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
		// �������ݿ�
		for (int i = 0; i < adds.size(); i++) {
			String pk_sendplanin_b = getBaseDAO().insertVOWithPK(adds.get(i));
			b2List_Add.get(i).setPk_sendplanin_b(pk_sendplanin_b);
		}
		if (mods.size() > 0) {
			getBaseDAO().updateVOList(mods);
		}
		// ������Դ��Ϣ
		getBaseDAO().insertVOList(b2List_Add);
		getBaseDAO().insertVOList(b2List_Update);

	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ��鷢�˼ƻ��ļƻ�����������Ϊ��
	 * @ʱ�䣺2011-7-8����07:18:43
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
			throw new BusinessException("���˼ƻ�[�¼ƻ�]�ļƻ�����������Ϊ��");
		}
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ ��鷢�˼ƻ�׷�Ӽƻ� ����ƻ�������Ϊ�� �������� ���û���¼ƻ� ׷�Ӽƻ���������
	 * @ʱ�䣺2011-7-8����07:18:43
	 * @param vo
	 * @throws BusinessException
	 */
	public void checkForBplan(AggregatedValueObject vo)
			throws BusinessException {
		// ����׷�Ӽƻ�ʱ �ƻ�����������Ϊ��
		checkNotAllNull(vo);
		// У���Ƿ�����¼ƻ�,����������¼ƻ���������
		SuperVO hvo = (SuperVO) vo.getParentVO();
		SendplaninVO hend = (SendplaninVO) vo.getParentVO();
		UFBoolean reserve15 = PuPubVO.getUFBoolean_NullAs(hend.getReserve15(),
				UFBoolean.FALSE);// ��ͷ�Ƿ�Ƿ��

		// modify by yf 2013-12-03 ���Ƶ����ڣ�ȷ���ƻ�����ڼ� begin
//		AccountCalendar calendar = AccountCalendar.getInstance();
		AccountCalendar calendar = getCalendar(hend.getDmakedate());
		// modify by yf 2013-12-03 ���Ƶ����ڣ�ȷ���ƻ�����ڼ� end
		UFDate beginDate = calendar.getMonthVO().getBegindate();
		UFDate endDate = calendar.getMonthVO().getEnddate();
		// ���ֿ�����
		String pk_in = PuPubVO.getString_TrimZeroLenAsNull(hvo
				.getAttributeValue(pk_inwhouse));
		// ����ֿ�����
		String pk_out = PuPubVO.getString_TrimZeroLenAsNull(hvo
				.getAttributeValue(pk_outwhouse));
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_sendplanin  from");
		sql.append(" wds_sendplanin where ");
		sql.append(" isnull(wds_sendplanin.dr,0)=0");
		if (reserve15.booleanValue() == false) {
			sql.append("  and isnull(reserve15,'N')='N'");// ����Ƿ��
		} else {
			sql.append("  and isnull(reserve15,'N')='Y'");// ��Ƿ��
		}
		sql.append(" and dmakedate between '");
		sql.append(beginDate + "' and '" + endDate);
		sql.append("' and pk_inwhouse ='" + pk_in + "'");
		sql.append("  and pk_outwhouse='" + pk_out + "'");
		sql.append("  and wds_sendplanin.iplantype=0 ");
		String pk = PuPubVO.getString_TrimZeroLenAsNull(getBaseDAO()
				.executeQuery(sql.toString(),
						WdsPubResulSetProcesser.COLUMNPROCESSOR));
		if (pk == null || pk.trim().length() == 0) {
			throw new BusinessException(" ���»�û���¼ƻ�����Ƿ���¼ƻ�   �������׷�Ӽƻ�");
		}
	}

	/**
	 * 
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ : ��׷�Ӽƻ����¼ƻ��ܲ����
	 * @ʱ�䣺2011-11-30����01:15:25
	 * @param obj2
	 * @throws BusinessException
	 */
	public void unplanStats(AggregatedValueObject obj2)
			throws BusinessException {
		// �������Ķ����¡һ��
		AggregatedValueObject obj = VOTool.aggregateVOClone(obj2);

		SendplaninVO parent = (SendplaninVO) obj.getParentVO();
		// UFBoolean reserve15=
		// PuPubVO.getUFBoolean_NullAs(parent.getReserve15(),
		// UFBoolean.FALSE);//��ͷ�Ƿ�Ƿ��

		String pk_inwhouse = parent.getPk_inwhouse();
		if (pk_inwhouse == null) {
			pk_inwhouse = "";
		}
		String pk_outwhouse = parent.getPk_outwhouse();
		if (pk_outwhouse == null) {
			pk_outwhouse = "";
		}
		SendplaninBVO[] childs = (SendplaninBVO[]) obj.getChildrenVO();
		// modify by yf 2013-12-03 ���Ƶ����ڣ�ȷ���ƻ�����ڼ� begin
//		AccountCalendar calendar = AccountCalendar.getInstance();
		AccountCalendar calendar = getCalendar(parent.getDmakedate());
		// modify by yf 2013-12-03 ���Ƶ����ڣ�ȷ���ƻ�����ڼ� end
		UFDate beginDate = calendar.getMonthVO().getBegindate();
		UFDate endDate = calendar.getMonthVO().getEnddate();
		// 1.��ѯ�¼ƻ�:����¼ƻ�û�д�������̬����ǰ����������
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_sendplanin  from");
		sql.append(" wds_sendplanin ");
		sql.append(" where vbillstatus=1 ");// ����״̬=����ͨ��
		sql.append(" and iplantype =0");// �¼ƻ�
		sql.append(" and isnull(dr,0)=0");
		// if(reserve15.booleanValue()==false){
		// sql.append("  and isnull(reserve15,'N')='N'");//����Ƿ��
		// }else{
		// sql.append("  and isnull(reserve15,'N')='Y'");//��Ƿ��
		// }
		sql.append(" and dmakedate between '");
		sql.append(beginDate + "' and '" + endDate);
		sql.append("' and pk_inwhouse ='" + pk_inwhouse + "'");
		sql.append(" and pk_outwhouse ='" + pk_outwhouse + "'");
		List<SendplaninBVO> mods = new ArrayList<SendplaninBVO>();
		Object o = getBaseDAO().executeQuery(sql.toString(),
				new ColumnProcessor());
		if (o == null || "".equalsIgnoreCase((String) o)) {
			throw new BusinessException("δ�ҵ��Ѿ����������¼ƻ�");
		}
		// 2.��ѯ�¼ƻ��������ݣ��ӱ�1�ͼ�¼��Դ��ϸ��¼���ӱ�2
		String cond = " pk_sendplanin='" + o + "' and isnull(dr,0)=0";
		List<SendplaninBVO> list = (List<SendplaninBVO>) getBaseDAO()
				.retrieveByClause(SendplaninBVO.class, cond);
		List<SendplaninB2VO> list_csoure = (List<SendplaninB2VO>) getBaseDAO()
				.retrieveByClause(SendplaninB2VO.class, cond);
		for (int i = 0; i < childs.length; i++) {
			// 2.1����׷�Ӽƻ���ϸ��Ӧ �¼ƻ�������ϸ
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
			// throw new BusinessException("��ѯ�����쳣��δ��ѯ��׷�Ӽƻ���ϸ����Ӧ���¼ƻ���ϸ");
			// }
			// 2.2 ��׷�Ӽƻ��������¼ƻ��ܼ�ȥ�����ü�ȥ��ļƻ������Ͱ��������Ƚϣ��ж��Ƿ��Ѿ�����
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
									"���������¼ƻ��Ѿ������˸�׷�Ӽƻ��еĵ�Ʒ");
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
	 * �������ڷ����������ڻ���ڼ�����
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

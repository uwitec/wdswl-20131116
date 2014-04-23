package nc.bs.dm.so;

import java.util.ArrayList;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.pub.pf.PfUtilBO;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.wl.pub.WdsPubResulSetProcesser;
import nc.itf.scm.cenpur.service.TempTableUtil;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.dm.so.deal.SoDeHeaderVo;
import nc.vo.dm.so.deal.SoDealBillVO;
import nc.vo.dm.so.deal.SoDealVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.wl.pub.Wds2WlPubConst;
import nc.vo.wl.pub.WdsWlPubConst;
import nc.vo.wl.pub.WdsWlPubTool;

public class SoDealBO {
	
	private BaseDAO m_dao = null;
	private BaseDAO getDao() {
		if (m_dao == null) {
			m_dao = new BaseDAO();
		}
		return m_dao;
	}

	/**
	 * 
	 * @���ߣ�zhf
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2011-3-29����02:08:02
	 * @param whereSql
	 * @return
	 * @throws Exception
	 */
	public SoDealVO[] doQuery(String whereSql,String pk_storedoc,String queryStore,UFBoolean isclose) throws Exception {
		SoDealVO[] datas = null;
		// ʵ�ֲ�ѯ���۶������߼�
		StringBuffer sql = new StringBuffer();
		sql.append("select  distinct ");
		String[] names = SoDealVO.m_headNames;

		for (String name : names) {
			sql.append(name + ", ");
		}
		names = SoDealVO.m_bodyNames;
		for (String name : names) {
			sql.append(name + ", ");
		}
		sql.append(" 'aaa' ");
		sql.append(" from so_sale h  " );
		sql.append(" inner join so_saleorder_b b on h.csaleid = b.csaleid");
		sql.append(" where ");
		sql.append("  isnull(h.dr,0)=0  and isnull(b.dr,0)=0  ");
		
//		------------------------------------------------zhf add ֧�ֲ�ѯ�������ѹرյĶ���zhf
		
		if(isclose.booleanValue()){
			sql.append(" and coalesce(h.bisclose,'N') = 'Y' ");
		}
		else{
			//modify by yf 2014-04-01 ���۰���ѡ��δ�رն�����ѯ����Ȼ�ܲ�ѯ������δ�رն���������Ҫ�󲻲���κ�δ�رն��� begin
			sql.append(" and coalesce(h.bisclose,'N') = 'N' ");
//			sql.append(" and ( coalesce(h.bisclose,'N') = 'N' " +
//					"and coalesce(h."+Wds2WlPubConst.so_virtual+",'"+Wds2WlPubConst.so_virtual_value_no+"') = '"+Wds2WlPubConst.so_virtual_value_no+"' ");
//			sql.append(" or h."+Wds2WlPubConst.so_virtual+" = '"+Wds2WlPubConst.so_virtual_value_yes+"' )");
			//modify by yf 2014-04-01 ���۰���ѡ��δ�رն�����ѯ����Ȼ�ܲ�ѯ������δ�رն���������Ҫ�󲻲���κ�δ�رն��� end
		}
//		------------------------------------------------

		
		
		
		
		
		if (whereSql != null && whereSql.length() > 0) {
			sql.append(" and " + whereSql);
		}
		sql.append(" and h.creceiptcustomerid in(");
		sql.append(" select distinct tb_storcubasdoc.pk_cumandoc ");
		sql.append(" from wds_storecust_h ");
		sql.append(" join tb_storcubasdoc ");
		sql.append(" on wds_storecust_h.pk_wds_storecust_h = tb_storcubasdoc.pk_wds_storecust_h ");
		sql.append("  where isnull(wds_storecust_h.dr,0)=0");
		sql.append("  and isnull(tb_storcubasdoc.dr,0)=0");
		if(!WdsWlPubTool.isZc(pk_storedoc)){
		sql.append("  and wds_storecust_h.pk_stordoc ='"+pk_storedoc+"'");
		}
		if(queryStore!=null && queryStore.length()>0){
		sql.append("  and wds_storecust_h.pk_stordoc ='"+queryStore+"'");	
		}
		sql.append(" )");
		Object o = getDao().executeQuery(sql.toString(),
				new BeanListProcessor(SoDealVO.class));
		if (o == null)
			return null;
		ArrayList<SoDealVO> list = (ArrayList<SoDealVO>) o;
		datas = list.toArray(new SoDealVO[0]);
		setStock(datas);
		return datas;
	}
	String sql=
	" select  wds_storecust_h.pk_stordoc "+
	" from wds_storecust_h "+
	" join tb_storcubasdoc "+
	" on wds_storecust_h.pk_wds_storecust_h = tb_storcubasdoc.pk_wds_storecust_h "+
	"  where isnull(wds_storecust_h.dr,0)=0"+
	"  and isnull(tb_storcubasdoc.dr,0)=0";
	/**
	 * ���÷����ֿ�
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ 
	 * @ʱ�䣺2012-7-31����12:35:56
	 * @param datas
	 * @throws DAOException 
	 */
	private void setStock(SoDealVO[] datas)  {
		if(datas==null || datas.length==0)
			return ;
		for(int i=0;i<datas.length;i++){
			//'0001B11000000000W46O'
			String pk_cub=datas[i].getCreceiptcustomerid();
			String sql1="";
			sql1=sql+" and tb_storcubasdoc.pk_cumandoc = '"+pk_cub+"'";
			String pk_storc=null;
			try {
				pk_storc = PuPubVO.getString_TrimZeroLenAsNull(getDao().executeQuery(sql1,new ColumnProcessor()));
			} catch (DAOException e) {
				e.printStackTrace();
			}
			datas[i].setCbodywarehouseid(pk_storc);
		}		
	}

	/**
	 * 
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2011-3-25����03:58:14
	 * @param ldataoon
	 * @param infor
	 *            :��¼�ˣ���¼��˾����¼����
	 * @throws Exception
	 */
	public void doDeal(List<SoDealVO> ldata, List<String> infor)
			throws Exception {
		if (ldata == null || ldata.size() == 0)
			return;
		
		//��  ����վ    �ͻ�  �Ƿ�����  �ֵ�
		CircularlyAccessibleValueObject[][] datas = SplitBillVOs.getSplitVOs(
				(CircularlyAccessibleValueObject[]) (ldata
						.toArray(new SoDealVO[0])),
				WdsWlPubConst.SO_PLAN_DEAL_SPLIT_FIELDS);
		if (datas == null || datas.length == 0)
			return;
		int len = datas.length;
		SoDealVO[] tmpVOs = null;
		//���� ���ۼƻ����ŵľۺ�vo   ���� ���ۼƻ�����--->�����˵��� ���ݽ��� 
		SoDealBillVO[] planBillVos = new SoDealBillVO[len];
		for (int i = 0; i < len; i++) {
			tmpVOs = (SoDealVO[]) datas[i];
			planBillVos[i] = new SoDealBillVO();
			planBillVos[i].setParentVO(getPlanHead(tmpVOs[0]));
			planBillVos[i].setChildrenVO(tmpVOs);
		}
		//�������ݽ��������������˵�
		PfParameterVO paraVo = new PfParameterVO();
		paraVo.m_operator = infor.get(0);
		paraVo.m_coId = infor.get(1);
		paraVo.m_currentDate = infor.get(2);
		AggregatedValueObject[] orderVos = (AggregatedValueObject[]) PfUtilTools
				.runChangeDataAry(WdsWlPubConst.WDS4, WdsWlPubConst.WDS5,
						planBillVos, paraVo);
		
		
		//���������˵�����ű������������˵�
		if (orderVos == null || orderVos.length == 0) {
			return;
		}	
		PfUtilBO pfbo = new PfUtilBO();
		for (AggregatedValueObject bill : orderVos) {
			pfbo.processAction(WdsWlPubConst.DM_PLAN_TO_ORDER_SAVE,
					WdsWlPubConst.WDS5, infor.get(2), null, bill, null);
		}
	}

	private SoDeHeaderVo getPlanHead(SoDealVO dealVo) {
		if (dealVo == null)
			return null;
		SoDeHeaderVo head = new SoDeHeaderVo();
		String[] names = head.getAttributeNames();
		for (String name : names) {
			head.setAttributeValue(name, dealVo.getAttributeValue(name));
		}
		return head;
	}
	//	
	
//	zhf add ---------------------------2012 1 4
	public void doCloseOrOpen(String[] orderbids,UFBoolean isclose) throws BusinessException{
		if(orderbids == null || orderbids.length == 0)
			return;
		TempTableUtil ut = new TempTableUtil();
		String sflag = isclose.booleanValue()?"N":"Y";//---------------
		String sql = "select csaleid from so_sale where isnull(dr,0) = 0 " +
				" and coalesce(bisclose,'N') = '"+sflag+"'" +
				" and csaleid in "+ut.getSubSql(orderbids);
		List ldata = (List)getDao().executeQuery(sql, WdsPubResulSetProcesser.COLUMNLISTROCESSOR);
		if(ldata == null || ldata.size() == 0)
			return;
		sflag = isclose.booleanValue()?"Y":"N";//-------------------
		sql = "update so_sale set bisclose = '"+sflag+"' where csaleid in "+ut.getSubSql((ArrayList)ldata);
		getDao().executeUpdate(sql);
	}

}

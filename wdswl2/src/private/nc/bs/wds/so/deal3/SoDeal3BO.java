package nc.bs.wds.so.deal3;

import java.util.ArrayList;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.wl.pub.WdsPubResulSetProcesser;
import nc.itf.scm.cenpur.service.TempTableUtil;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

public class SoDeal3BO {

	private BaseDAO m_dao = null;

	private BaseDAO getDao() {
		if (m_dao == null) {
			m_dao = new BaseDAO();
		}
		return m_dao;
	}
	
	public SoDeal3BO() {
		
		super();
	}

	public void doCloseOrOpen(String[] orderbids, UFBoolean isXuni)
			throws BusinessException {
		if (orderbids == null || orderbids.length == 0)
			return;
		TempTableUtil ut = new TempTableUtil();
		String pk_defdoc11 = !isXuni.booleanValue() ? "0001S3100000000OK5HM"
				: "0001S3100000000OK5HN";// 虚拟，查询非虚拟的订单;取消虚拟，查询虚拟订单
		String sql = "select csaleid from so_sale where isnull(dr,0) = 0 "
				+ " and coalesce(pk_defdoc11,'0001S3100000000OK5HN') = '"
				+ pk_defdoc11 + "'" + " and csaleid in "
				+ ut.getSubSql(orderbids);
		List ldata = (List) getDao().executeQuery(sql,
				WdsPubResulSetProcesser.COLUMNLISTROCESSOR);
		if (ldata == null || ldata.size() == 0)
			return;
		pk_defdoc11 = isXuni.booleanValue() ? "0001S3100000000OK5HM"
				: "0001S3100000000OK5HN";// 是，否
		String vdef11 = isXuni.booleanValue() ? "是" : "否";
		sql = "update so_sale set pk_defdoc11 = '" + pk_defdoc11 + "' , vdef11 = '"
				+ vdef11 + "' where csaleid in "
				+ ut.getSubSql((ArrayList) ldata);
		getDao().executeUpdate(sql);
	}

}

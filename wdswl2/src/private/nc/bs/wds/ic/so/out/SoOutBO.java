package nc.bs.wds.ic.so.out;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.naming.NamingException;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.bs.pub.SuperDMO;
import nc.bs.wds.load.pub.PushSaveWDSF;
import nc.bs.wds.pub.report.ReportDMO;
import nc.bs.wl.pub.WdsPubResulSetProcesser;
import nc.itf.uap.pf.IPFBusiAction;
import nc.vo.ic.other.out.TbOutgeneralHVO;
import nc.vo.ic.pub.bill.GeneralBillItemVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.ic.pub.smallbill.SMGeneralBillVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFDate;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.zmpub.pub.report.ReportBaseVO;

/**
 * ���۳���(WDS8)
 * 
 * @author Administrator
 */
public class SoOutBO {
	private String s_billtype = "4C";
	private PushSaveWDSF puf = null;

	public PushSaveWDSF getPuf() {
		if (puf == null) {
			puf = new PushSaveWDSF();
		}
		return puf;
	}

	private SuperDMO dmo = new SuperDMO();

	public void updateHVO(TbOutgeneralHVO hvo) throws BusinessException {
		if (hvo == null) {
			return;
		}
		if (hvo.getPrimaryKey() != null)
			hvo.setStatus(VOStatus.UPDATED);
		else
			hvo.setStatus(VOStatus.NEW);
		dmo.update(hvo);
	}

	public void pushSign4C(String date, AggregatedValueObject billvo)
			throws Exception {
		// ���۳���ǩ��
		if (billvo != null && billvo instanceof GeneralBillVO) {
			GeneralBillVO billVO = (GeneralBillVO) billvo;
			IPFBusiAction bsBusiAction = (IPFBusiAction) NCLocator
					.getInstance().lookup(IPFBusiAction.class.getName());
			//modify by yf 2014-01-02 ǩ�����ڣ���������Խ��ˣ�����ȡ����1�� begin
			String pk_corp = (String) billvo.getParentVO().getAttributeValue(
					"pk_corp");
			date = getSignDate(pk_corp, date);
			//modify by yf 2014-01-02 ǩ�����ڣ���������Խ��ˣ�����ȡ����1�� end
			ArrayList retList = (ArrayList) bsBusiAction.processAction(
					"PUSHWRITE", s_billtype, date, null, billVO, null, null);
			SMGeneralBillVO smbillvo = (SMGeneralBillVO) retList.get(2);
			billVO.setSmallBillVO(smbillvo);
			// ǩ�ּ�� <->[ǩ�����ںͱ���ҵ������]
			// ��ǰ������<->[ҵ�������������ǰ������Ա]
			// �ջ�λ��� bb1��
			bsBusiAction.processAction("SIGN", s_billtype, date, null, billVO,
					null, null);
			// ��ʽ�����γ�װж�Ѻ��㵥
			// getPuf().pushSaveWDSF(smbillvo, coperator, date,
			// LoadAccountBS.LOADFEE);
		}
	}

	public void canelPushSign4C(String date, AggregatedValueObject[] billvo)
			throws Exception {
		// ȡ�����۳���ǩ��
		if (billvo != null && billvo[0] != null
				&& billvo[0] instanceof GeneralBillVO) {
			IPFBusiAction bsBusiAction = (IPFBusiAction) NCLocator
					.getInstance().lookup(IPFBusiAction.class.getName());
			for (int i = 0; i < billvo.length; i++) {
				ArrayList retList = (ArrayList) bsBusiAction.processAction(
						"CANCELSIGN", s_billtype, date, null, billvo[i], null,
						null);
				if (retList.get(0) != null && (Boolean) retList.get(0)) {// ȡ��ǩ�ֳɹ�
					// "CANELDELETE"
					bsBusiAction.processAction("DELETE", s_billtype, date,
							null, billvo[i], null, null);
				}
			}
		}
	}

	// ֧�����̴�ӡ
	public ReportBaseVO[] getCorpTP(String general) throws DAOException,
			SQLException, IOException, NamingException {
		if (general == null || "".equalsIgnoreCase(general)) {
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select tb_outgeneral_t.*,bd_cargdoc_tray.*  ");// ����ⵥ�� ��
		sql.append(" from tb_outgeneral_t ");
		sql.append(" join bd_cargdoc_tray ");// ����������Ϣ
		sql.append(" on tb_outgeneral_t.cdt_pk = bd_cargdoc_tray.cdt_pk");
		sql.append(" where  general_b_pk='" + general + "'");
		sql
				.append(" and isnull(tb_outgeneral_t.dr,0)=0  and isnull(bd_cargdoc_tray.dr,0)=0 ");
		ReportBaseVO[] vos = new ReportDMO().queryVOBySql(sql.toString());
		if (vos == null || vos.length == 0) {
			return null;
		}
		return vos;
	}
	//modify by yf 2014-01-02 ǩ�����ڣ���������Խ��ˣ�����ȡ����1�� begin
	BaseDAO dao = null;

	BaseDAO getBaseDAO() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}
	private String getSignDate(String corp, String date) throws BusinessException {
		Integer dates = getDefaultDay(corp);
		int jzday = dates.intValue();
		// �����ǰ��С�ڵ��ڽ����ڣ���ERP�ĳ���ⵥ������Ϊ��ǰ�ڣ�
		// �����ǰ�ڴ��ڽ����ڣ���EPR����Ϊ��һ����1��
		UFDate dbilldate = new UFDate(date);
		int dqday = Integer.parseInt(dbilldate.getStrDay());
		if (dqday > jzday) {
			dbilldate = NextMonth(dbilldate);
		}
		return dbilldate.toString();
	}
	// ��ǰ�µ���һ����һ�� ����
	public UFDate NextMonth(UFDate dbilldate) {
		// TODO Auto-generated method stu
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		calendar.setTimeInMillis(dbilldate.getMillis());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		return new UFDate(calendar.getTime());

	}

	/**
	 * 
	 * @���ߣ�lyf ��ѯ������Ĭ��ֵ
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2011-12-20����10:23:43
	 * @throws BusinessException
	 */
	public Integer getDefaultDay(String corp) throws BusinessException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select datavale+1 from wds_periodsetting_h ");
		sql.append(" where isnull(dr,0) =0 ");
		sql.append(" and pk_corp='" + corp + "'");
		Object value = getBaseDAO().executeQuery(sql.toString(),
				WdsPubResulSetProcesser.COLUMNPROCESSOR);
		return PuPubVO.getInteger_NullAs(value, 20);
	}
	//modify by yf 2014-01-02 ǩ�����ڣ���������Խ��ˣ�����ȡ����1�� end
}
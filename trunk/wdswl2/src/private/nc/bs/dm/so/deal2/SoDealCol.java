package nc.bs.dm.so.deal2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import nc.bs.dao.BaseDAO;
import nc.bs.logging.Logger;
import nc.bs.pub.pf.PfUtilBO;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.wds.ic.stock.StockInvOnHandBO;
import nc.bs.wl.pub.WdsPubResulSetProcesser;
import nc.bs.zmpub.pub.tool.SingleVOChangeDataBsTool;
import nc.bs.zmpub.pub.tool.stock.AvailNumBoTool;
import nc.vo.dm.so.deal2.SoDealBillVO;
import nc.vo.dm.so.deal2.SoDealHeaderVo;
import nc.vo.dm.so.deal2.SoDealVO;
import nc.vo.dm.so.deal2.StoreInvNumVO;
import nc.vo.ic.pub.StockInvOnHandVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.wdsnew.pub.AvailNumDea2BO;
import nc.vo.wl.pub.WdsWlPubConst;
import nc.vo.wl.pub.WdsWlPubTool;
/**
 * @author zhf ���ۼƻ������㷨�� 
 * ���� �����ŵ����� ���а��Ŵ��� ������Ҫ�ֹ����ŵ�����
 * 
 */
public class SoDealCol {

	private SoDealBillVO[] bills = null;

	private List lpara = null;

	public SoDealCol() {
		super();
	}

	public SoDealCol(SoDealBillVO[] bills, List lpara) {
		super();
		this.bills = bills;
		this.lpara = lpara;
	}

	public void setData(SoDealBillVO[] bills, List lpara) {
		this.bills = bills;
		this.lpara = lpara;
	}

	private BaseDAO dao = null;

	private BaseDAO getDao() {
		if (dao == null)
			dao = new BaseDAO();
		return dao;
	}
	private StockInvOnHandBO stockbo = null;
	private StockInvOnHandBO getStockBO() {
		if (stockbo == null) {
			stockbo = new StockInvOnHandBO(getDao());
		}
		return stockbo;
	}

	private Map<String, UFDouble> custMinNumInfor = null;

	public void clearCustMinNumInfor() {
		if (custMinNumInfor != null)
			custMinNumInfor.clear();
	}

	/**
	 * 
	 * @���ߣ�zhf
	 * @˵�� ���ɽ������Ŀ ͨ���ֲֿ��̰󶨻�ȡ �ֲֿ��̵���С����������
	 * @ʱ�䣺2011-7-7����04:09:28
	 * @param ccustid
	 * @param pk_store
	 * @return
	 * @throws BusinessException
	 */
	public UFDouble getMinSendNumForCust(String ccustid, String pk_store)
			throws BusinessException {

		if (PuPubVO.getString_TrimZeroLenAsNull(ccustid) == null)
			return null;
		String key = WdsWlPubTool.getString_NullAsTrimZeroLen(pk_store)
				+ WdsWlPubTool.getString_NullAsTrimZeroLen(ccustid);
		if (custMinNumInfor == null) {
			custMinNumInfor = new HashMap<String, UFDouble>();
		}
		if (!custMinNumInfor.containsKey(key)) {
			String sql = " select ndef1 from tb_storcubasdoc where isnull(dr,0)=0 and "
					+ " pk_cumandoc = '"
					+ ccustid
					+ "' and pk_stordoc = '"
					+ pk_store + "'";// (select pk_stordoc from bd_stordoc
										// where " +
			// "isnull(dr,0)=0 and )";
			custMinNumInfor.put(key, PuPubVO
					.getUFDouble_NullAsZero(getDao().executeQuery(sql,
							WdsPubResulSetProcesser.COLUMNPROCESSOR)));
		}
		return custMinNumInfor.get(key);
	}

	/**
	 * 
	 * @���ߣ�zhf
	 * @˵������ѯ ��������
	 * @ʱ�䣺2011-11-8����07:59:53
	 * @param invNumInfor
	 * @throws BusinessException
	 */
//	private void initInvNumInfor(Map<String, StoreInvNumVO> invNumInfor)
//			throws BusinessException {
//		// invNumInfor <key,StoreInvNumVO> =
//		// <�����������id��StoreInvNumVO��������ǰ����������������Ҫ���ŵ�����,�Ѿ����ŵ��˵�ռ������>
//		Set<String> invs = new HashSet<String>();// ���ΰ��ŵ����д��id
//		SoDealHeaderVo head = null;
//		SoDealVO[] bodys = null;
//		StoreInvNumVO tmpNumVO = null;
//		// 1.��ȡ�����
//		UFDouble[] stocknums = null;
//		String pk_corp = SQLHelper.getCorpPk();
//		Logger.info("��ȡ��浱ǰ����...");
//		String key = null;
//		for (SoDealBillVO bill : bills) {
//			head = bill.getHeader();
//			bodys = bill.getBodyVos();
//			if (bodys == null || bodys.length == 0)
//				continue;
//			for (SoDealVO body : bodys) {
//				key = WdsWlPubTool.getString_NullAsTrimZeroLen(body
//						.getCinvbasdocid())+WdsWlPubTool.getString_NullAsTrimZeroLen(body.getVdef1());
//				invs.add(body.getCinvbasdocid());
//				if (invNumInfor.containsKey(key)) {
//					tmpNumVO = invNumInfor.get(key);
//				} else {
//					tmpNumVO = new StoreInvNumVO();
//					tmpNumVO.setPk_corp(pk_corp);
//					tmpNumVO.setCstoreid(head.getCbodywarehouseid());
//					tmpNumVO.setCinvbasid(body.getCinvbasdocid());
//					tmpNumVO.setCinvmanid(body.getCinventoryid());
//					//���ݲֿ�+��� ��� �ϸ�ʹ���״̬���ִ���
////					String strWhere= " ss_pk in('"+WdsWlPubConst.WDS_STORSTATE_PK_hg+"','"+WdsWlPubConst.WDS_STORSTATE_PK_dj+"')";
//					String strWhere= " ss_pk in('"+WdsWlPubConst.WDS_STORSTATE_PK_hg+"')";
//					stocknums = getStockBO().getInvStockNum(pk_corp,
//							tmpNumVO.getCstoreid(), null,
//							tmpNumVO.getCinvbasid(), null, null,strWhere);
//					if (stocknums == null || stocknums.length == 0)
//						continue;
//					tmpNumVO.setNstocknum(stocknums[0]);
//					tmpNumVO.setNstockassnum(stocknums[1]);
//				}
//				tmpNumVO.setNplannum(PuPubVO.getUFDouble_NullAsZero(
//						tmpNumVO.getNplannum()).add(
//						PuPubVO.getUFDouble_NullAsZero(body.getNnum())));
//				tmpNumVO.setNplanassnum(PuPubVO.getUFDouble_NullAsZero(
//						tmpNumVO.getNplanassnum()).add(
//						PuPubVO.getUFDouble_NullAsZero(body.getNassnum())));
//				invNumInfor.put(key, tmpNumVO);
//			}
//		}
//		if (invNumInfor.size() == 0) {
//			Logger.info("���δ����Ŵ������Ϊ�գ��޷����ţ��˳�");
//		}
//		// 2.��ȡ�˵�ռ����,���ݴ���ִ����ʹ������������������Ϊ���ࣺ�����źͲ�������
//		Logger.info("��ȡ����Ѱ���δ������...");
//		Map<String, UFDouble[]> invNumInfor2 = getStockBO().getNdealNumInfor(
//				pk_corp, head.getCbodywarehouseid(),
//				invs.toArray(new String[0]), new TempTableUtil());
//		if (invNumInfor2 == null || invNumInfor2.size() == 0) {
//			Logger.info("���ΰ��ŵĴ���������Ѱ���δ������");
//			if (invNumInfor2 == null)
//				invNumInfor2 = new HashMap<String, UFDouble[]>();
//		}
//		Logger.info("���δ����Ŵ�����״����");
//		for (String key2 : invNumInfor.keySet()) {
//			tmpNumVO = invNumInfor.get(key2);
//			stocknums = invNumInfor2.get(key2);
//			if (tmpNumVO == null)
//				continue;
//			// �Ѱ����˵�ռ����
//			tmpNumVO.setNdealnum(stocknums == null ? WdsWlPubTool.DOUBLE_ZERO
//					: stocknums[0]);
//			tmpNumVO
//					.setNdealassnum(stocknums == null ? WdsWlPubTool.DOUBLE_ZERO
//							: stocknums[1]);
//			// ��ǰ������=�����-�Ѿ����ŵ��˵�ռ����
//			tmpNumVO.setNnum(tmpNumVO.getNstocknum()
//					.sub(tmpNumVO.getNdealnum()));
//			tmpNumVO.setNassnum(tmpNumVO.getNstockassnum().sub(
//					tmpNumVO.getNdealassnum()));
//			// ��������� > ���ΰ����� �����Ϊ�ɰ���
//			if (tmpNumVO.getNassnum().doubleValue() > tmpNumVO.getNplanassnum()
//					.doubleValue())
//				tmpNumVO.setBisok(UFBoolean.TRUE);
//			else
//				tmpNumVO.setBisok(UFBoolean.FALSE);
//			Logger.info(" ���"
//					+ WdsWlPubTool.getInvCodeByInvid(tmpNumVO.getCinvbasid())
//					+ " ��ǰ������" + tmpNumVO.getNstockassnum() + " �Ѱ���δ��������"
//					+ tmpNumVO.getNdealassnum() + " ���ο�������"
//					+ tmpNumVO.getNassnum() + " ���δ�����������"
//					+ tmpNumVO.getNplanassnum());
//		}
//	}
	
	private AvailNumBoTool numBO = null;
	private AvailNumBoTool getNumBO(){
		if(numBO == null){
			numBO = new AvailNumDea2BO();
		}
		return numBO;
	}
	
	/**
	 * 
	 * @���ߣ�zhf
	 * @˵������ѯ ��������
	 * @ʱ�䣺2011-11-8����07:59:53
	 * @param invNumInfor
	 * @throws BusinessException
	 */
	private void initInvNumInfor2(Map<String, StoreInvNumVO> invNumInfor)
			throws Exception {
		// invNumInfor <key,StoreInvNumVO> =
		// <�����������id��StoreInvNumVO��������ǰ����������������Ҫ���ŵ�����,�Ѿ����ŵ��˵�ռ������>

		// ���ݲ�����װ ��ѯ������ ����������ת�� У��������Ƿ�����
//		Set<String> invs = new HashSet<String>();// ���ΰ��ŵ����д��id

		SoDealVO[] bodys = null;
		Logger.info("��ȡ��浱ǰ����...");
		String key = null;

		List<SoDealVO> lpara = new ArrayList<SoDealVO>();

		for (SoDealBillVO bill : bills) {
			bodys = bill.getBodyVos();
			if (bodys == null || bodys.length == 0)
				continue;
			lpara.addAll(Arrays.asList(bodys));
		}

		StockInvOnHandVO[] paras = (StockInvOnHandVO[]) SingleVOChangeDataBsTool
				.runChangeVOAry(lpara.toArray(new SoDealVO[0]),
						StockInvOnHandVO.class,
						"nc.ui.wds.self.changedir.CHGDEAL2TOACCOUNTNUM");

		// ���ô����ӿڲ�ѯ����
		StoreInvNumVO[] nums = (StoreInvNumVO[]) getNumBO().getAvailNumDatas(
				paras);

		if (nums == null || nums.length == 0)
			Logger.info("���ΰ��Ż�Ʒ��������Ϊ�գ��޷�����");

		StoreInvNumVO tmp = null;
		for (StoreInvNumVO num : nums) {
			key = WdsWlPubTool.getString_NullAsTrimZeroLen(num.getCinvbasid())
					+ WdsWlPubTool.getString_NullAsTrimZeroLen(num.getSs_pk());

			if (invNumInfor.containsKey(key)) {
				tmp = invNumInfor.get(key);
				num.setNplannum(tmp.getNplannum().add(num.getNplannum()));
				num.setNplanassnum(tmp.getNplanassnum().add(
						num.getNplanassnum()));
			}
			invNumInfor.put(key, num);
		}

		for (StoreInvNumVO num : invNumInfor.values()) {
			num.setBisok(UFBoolean.TRUE);
			//modify by yf 2014-03-20 ���۰���2 �������㹻������ʾ���������� begin
//			if (num.getNnum().sub(num.getNplannum()).doubleValue() <= 0.0) {
//				num.setBisok(UFBoolean.FALSE);
//			}
			if (num.getNnum().sub(num.getNplannum()).doubleValue() < 0.0) {
				num.setBisok(UFBoolean.FALSE);
			}
			//modify by yf 2014-03-20 ���۰���2 �������㹻������ʾ���������� end
			Logger.info(" ���"
					+ WdsWlPubTool.getInvCodeByInvid(num.getCinvbasid())
					+ " ��ǰ������" + num.getNstockassnum() + " �Ѱ���δ��������"
					+ num.getNdealassnum() + " ���ο�������" + num.getNassnum()
					+ " ���δ�����������" + num.getNplanassnum());
		}
	}

	/**
	 * 
	 * @���ߣ�zhf
	 * @˵�������ɽ������Ŀ �㷨������ 
	 * 1�����ΰ��ŵĸ���Ʒ�ֵĿ�����
	 *  2����Ʒ�ֻ��ܸ���Ʒ�ֵ������� 
	 *  3����Ʒ�ַ�Ϊ���ࣺ1������������* 2������������ 
	 *  4�����ͻ���Ϊ���ࣺ1����ֱ�Ӱ����� 2�����ڴ��������Ʒ�ֵĿͻ��� 
	 *  5����ֱ�Ӱ��ŵĿͻ�	ֱ�Ӱ��������˵� 
	 *  6������Ŀͻ����ݿͻ���С���������˿�����������������ȿͻ�����С��������С �����ÿͻ�
	 *  7����ʣ��Ŀͻ����ݷ������ �ȴ� �û��ֹ�����
	 * @ʱ�䣺2011-7-7����04:58:32 ���ز������ŵĿͻ� �� �������ŵ�Ʒ�ֿ��״̬
	 */
	public Object col() throws Exception {
		if (bills == null || bills.length == 0)
			return null;
		// 1.��ÿ��������������Ϣ 
		// invNumInfor�����װ<������Ҫ���ŵĴ������id+���״̬id��StoreInvNumVO(�����ǰ��������Ѿ����ŵ��˵��������˵�+���˶�����ռ��������������=�����-ռ������)>
		Map<String, StoreInvNumVO> invNumInfor = new HashMap<String, StoreInvNumVO>();
		String key = null;
		initInvNumInfor2(invNumInfor);
		if (invNumInfor.size() == 0)
			throw new BusinessException("���д���ĵ�ǰ�������Ϊ��,�޷��Զ�����");
		// 2.���ݿ��������˿ͻ������ͻ���Ϊ���ο���ֱ�Ӱ��ŵĿͻ�������������Ҫ�ֹ����ŵĿͻ�
		Logger.info("������С���������˿�������ƫ�͵Ĵ��");
		List<SoDealVO> ldeal = null;
		List<SoDealVO> lnodeal = null;
		List<SoDealBillVO> lcust = new ArrayList<SoDealBillVO>();// ��Ϊ���������㣬����ֱ�Ӱ��ŷ����Ŀͻ���Ϣ
		List<String> reasons = new ArrayList<String>();// �ͻ����β��ܰ��ŵ�ԭ��,����ǰ̨����ʾ��
		List<String> reasons2 = new ArrayList<String>();//����ֱ�Ӱ��ŵĿͻ�
		for (SoDealBillVO bill : bills) {
			SoDealVO[] bodys = bill.getBodyVos();
			if (bodys == null || bodys.length == 0)
				continue;
			boolean pass = false;//�Ƿ������ÿͻ�
			boolean isGift = false;//�ж��Ƿ���Ʒ������Ʒ��������𵥡�����Ʒ��������������������������㣬ֱ�Ӱ���;�п����������㣬����;
			boolean isdeal = true;//Ĭ�� �ÿͻ����� ��������������
			for (SoDealVO body : bodys) {
				key = WdsWlPubTool.getString_NullAsTrimZeroLen(body.getCinvbasdocid())+WdsWlPubTool.getString_NullAsTrimZeroLen(body.getVdef1());
				StoreInvNumVO tmpNumVO = invNumInfor.get(key);
				boolean blargessflag = PuPubVO.getUFBoolean_NullAs(body.getBlargessflag(), UFBoolean.FALSE).booleanValue();
				if(blargessflag){
					isGift = true;
				}
				// 2.1���ڴ���Ŀ�����<=0�� �漰�ô���Ŀͻ� �����ɰ��� ���� �ÿͻ�ֱ�Ӷ���
				if(tmpNumVO == null){
					String num = "0";
					String reason = "���"+ WdsWlPubTool.getInvCodeByInvid(body.getCinvbasdocid())
					+ "������Ϊ:"+num+"�ͻ�["
					+ WdsWlPubTool.getCustNameByid(bill.getHeader()
							.getCcustomerid()) + "]�����޷�����;\n";
					Logger.info(reason);
					reasons.add(reason);
					pass = true;
					continue;
				}
				double nnum = PuPubVO.getUFDouble_NullAsZero(tmpNumVO.getNnum()).doubleValue();//������������
				double nassnum = PuPubVO.getUFDouble_NullAsZero(tmpNumVO.getNassnum()).doubleValue();//������������
				//2.2���ڴ���Ŀ�����<=0�� �漰�ô���Ŀͻ� �����ɰ��� ���� �ÿͻ�ֱ�Ӷ���
				if (nnum<=0) {
					String reason = "���"+ WdsWlPubTool.getInvCodeByInvid(body.getCinvbasdocid())
					+ "������:������="+nnum+",������="+nassnum+"\n �ͻ�["
					+ WdsWlPubTool.getCustNameByid(bill.getHeader()
							.getCcustomerid()) + "]�����޷�����;\n";
					Logger.info(reason);
					reasons.add(reason);
					pass = true;
					continue;
				}
				// 2.3��������� ���ڸÿͻ�����С������ ���� ���ÿͻ� 
				double nminSendNum = getMinSendNumForCust(bill.getHeader().getCcustomerid(), bill.getHeader().getCbodywarehouseid()).doubleValue();	
				if (nassnum -nassnum<0) {
					String reason = "���"
						+ WdsWlPubTool.getInvCodeByInvid(body
								.getCinvbasdocid())
						+ "������:������="+nnum+",������="+nassnum+"\n ���ڿͻ�����С������"+nminSendNum+",�ͻ�["
						+ WdsWlPubTool.getCustNameByid(bill.getHeader()
								.getCcustomerid()) + "]�����޷�����;\n";
					Logger.info(reason);
					reasons.add(reason);
					pass = true;
					continue;
				}
				//2.4�ȽϿ������뱾�ΰ����������ж��Ƿ����ֱ�Ӱ���(��������� > ���ΰ�������,bisok = true)
				// ��ʹ�ÿͻ���������һ�������������㣬Ҳ���Ϊ����ֱ�Ӱ���
				boolean bisok = PuPubVO.getUFBoolean_NullAs(invNumInfor.get(key).getBisok(),UFBoolean.FALSE).booleanValue();
				if (!bisok) {
					isdeal = false;
					continue;
				}
			}
			//3. ���� �� 4�� ���жϣ��Կͻ���3��������а��ţ�
			//pass=true ֱ�����������β����а���;
			//isdeal=true ����ֱ�Ӱ��������˵�������ldeal,һ��ֱ�Ӱ��������˵�;
			//isdeal = false ����ֱ�Ӱ��ţ�����lnodeal �������ݴ�������ǰ̨�����ֶ�����
			if (pass)
				continue;
			if (isdeal) {
				if (ldeal == null) {
					ldeal = new ArrayList<SoDealVO>();
				}
				ldeal.addAll(Arrays.asList(bodys));
				reasons2.add(WdsWlPubTool.getCustNameByid(bill.getHeader()
						.getCcustomerid()));
				Logger.info("##�ͻ�["
						+ WdsWlPubTool.getCustNameByid(bill.getHeader()
								.getCcustomerid()) + "]���ο�ֱ�Ӱ���");
			} else {
				if (lnodeal == null) {
					lnodeal = new ArrayList<SoDealVO>();
				}
				if(isGift){
					String reason = "�ͻ�["+ WdsWlPubTool.getCustNameByid(bill.getHeader()
								.getCcustomerid()) + "������Ʒ�������ֿ���������Ĵ�������ܰ��ţ��ɽ���Ʒ���ȵ�������";
					Logger.info(reason);
					reasons.add(reason);
				}else{
					lnodeal.addAll(Arrays.asList(bodys));
					lcust.add(bill);
				}
				
			}
		}
		// 4.�����˺���԰��ŵ������ݽ��а���
		// 4.1 ������ֱ�Ӱ��ŵ����ݣ�ֱ�Ӱ��������˵�����isauto���Ϊtrue
		UFBoolean isauto = UFBoolean.FALSE;// ���ΰ��Ŵ����ŵ������У��Ƿ������������Զ����ţ��������Զ�����
		if (ldeal == null || ldeal.size() == 0) {
			Logger.info("���ΰ���δ���ڿ�ֱ�Ӱ��ŵĿͻ�");
		} else {// ֱ�Ӱ���
			doDeal(ldeal, lpara);
			
//			���ųɹ���   ����������
			StoreInvNumVO tmpnum = null;
			for(SoDealVO vo:ldeal){
				key = WdsWlPubTool.getString_NullAsTrimZeroLen(vo.getCinvbasdocid())
				+WdsWlPubTool.getString_NullAsTrimZeroLen(vo.getVdef1());
				tmpnum = invNumInfor.get(key);
			    tmpnum.setNnum(tmpnum.getNnum().sub(vo.getNnum()));
			    tmpnum.setNassnum(tmpnum.getNassnum().sub(vo.getNassnum()));
			}
			
			isauto = UFBoolean.TRUE;
			Logger.info("ϵͳֱ�Ӱ��ųɹ�");
		}
		//4.2 ����Ҫ�ֶ����ŵ����ݣ����з�װ������ǰ̨����
		//????��������Զ����ţ��Զ�����֮����ǰ����������Ѿ���׼ȷ���Ƿ�Ӧ�����¼��㣿��
		if (lnodeal != null && lnodeal.size() > 0) {
			Collection<StoreInvNumVO> c = invNumInfor.values();
			Iterator<StoreInvNumVO> it = c.iterator();
			StoreInvNumVO tmp = null;
			List<StoreInvNumVO> ltmp = new ArrayList<StoreInvNumVO>();
			while (it.hasNext()) {
				tmp = it.next();
				for (SoDealVO deal : lnodeal) {
					if ((deal.getCinvbasdocid()+deal.getVdef1()).equalsIgnoreCase(
							(tmp.getCinvbasid()+tmp.getSs_pk()))) {
						tmp.getLdeal().add(deal);
					}
				}
				if (tmp.getLdeal().size() > 0)
					ltmp.add(tmp);
			}
			if (lcust.size() > 0)
				Logger.info("����" + lcust.size() + "���ͻ����ڿ�治����Ҫ�ֹ����а���");
			// UFDateTime time2 = new UFDateTime(System.currentTimeMillis());
			Logger.info("���ΰ��Ŵ������,���ؽ����ֹ�����");
			Logger.info("#####################################################");
			return new Object[] { isauto, lcust, ltmp,reasons,reasons2};
		} else {
			Logger.info("���ΰ���δ������Ҫ�û��ֹ����ŵ�����");
			Logger.info("���ΰ��Ŵ������");
			Logger.info("#####################################################");
		}
		return new Object[] { isauto, null, null ,reasons,reasons2};
	}
	
	
	/**
	 * 
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ:ִ�а��ţ����������˵�
	 * @ʱ�䣺2011-3-25����03:58:14
	 * @param ldata
	 * @param infor
	 *            :��¼�ˣ���¼��˾����¼����
	 * @throws Exception
	 */
	public void doDeal(List<SoDealVO> ldata, List<String> infor)
			throws Exception {
		if (ldata == null || ldata.size() == 0)
			return;
//		/**
//		 * ���ţ����ɷ��˵� ���˼ƻ��������ɷ��˶���
//		 * 
//		 * �ƻ����� �ƻ��к� ���ϲ��ƻ��� �ƻ��Ͷ���Ϊ1�Զ��ϵ �ֵ����� ����վ �ջ�վ��ͬ �����Ǽƻ�����
//		 * */
//		// ��д�ƻ��ۼư�������
//		// ���ۼƻ�����vo---�����۶���
//		Map<String, UFDouble> map = new HashMap<String, UFDouble>();
//		for (int i = 0; i < ldata.size(); i++) {
//			String key = ldata.get(i).getCorder_bid();
//			UFDouble num = PuPubVO.getUFDouble_NullAsZero(ldata.get(i)
//					.getNnum());
//			if (map.containsKey(key)) {
//				UFDouble oldValue = PuPubVO
//						.getUFDouble_NullAsZero(map.get(key));
//				map.put(key, oldValue.add(num));
//			}
//			map.put(key, num);
//		}
//		reWriteDealNumForPlan(map);
		// �� �ƻ��� ����վ �ͻ� �ֵ�
		CircularlyAccessibleValueObject[][] datas = SplitBillVOs.getSplitVOs(
				(CircularlyAccessibleValueObject[]) (ldata
						.toArray(new SoDealVO[0])),
				WdsWlPubConst.SO_PLAN_DEAL_SPLIT_FIELDS);
		if (datas == null || datas.length == 0)
			return;
		int len = datas.length;
		SoDealVO[] tmpVOs = null;
		SoDealBillVO[] planBillVos = new SoDealBillVO[len];
		for (int i = 0; i < len; i++) {
			tmpVOs = (SoDealVO[]) datas[i];
			planBillVos[i] = new SoDealBillVO();
			planBillVos[i].setParentVO(getPlanHead(tmpVOs[0]));
			planBillVos[i].setChildrenVO(tmpVOs);
		}
		// // ���۶���--�������˵�
		PfParameterVO paraVo = new PfParameterVO();
		paraVo.m_operator = infor.get(0);
		paraVo.m_coId = infor.get(1);
		paraVo.m_currentDate = infor.get(2);
		// // ������ ���� ���� ������
		AggregatedValueObject[] orderVos = (AggregatedValueObject[]) PfUtilTools
				.runChangeDataAry(WdsWlPubConst.WDS4, WdsWlPubConst.WDS5,
						planBillVos, paraVo);
		// // �ֵ�---�����涩��
		if (orderVos == null || orderVos.length == 0) {
			return;
		}
		PfUtilBO pfbo = new PfUtilBO();
		for (AggregatedValueObject bill : orderVos) {
			pfbo.processAction(WdsWlPubConst.DM_PLAN_TO_ORDER_SAVE,
					WdsWlPubConst.WDS5, infor.get(2), null, bill, null);
		}
	}
	
	/**
	 * 
	 * @���ߣ�lyf
	 * @˵�������ɽ������Ŀ �����ΰ�����������д�����۶��������ۼƷ�������
	 * @ʱ�䣺2011-3-25����04:44:08
	 * @param dealnumInfor
	 * @throws BusinessException
	 */
	private void reWriteDealNumForPlan(Map<String, UFDouble> map)
			throws BusinessException {

		if (map == null || map.size() == 0)
			return;
		for (Entry<String, UFDouble> entry : map.entrySet()) {
			String sql = "update so_saleorder_b set "
					+ WdsWlPubConst.DM_SO_DEALNUM_FIELD_NAME
					+ " = coalesce("
					+ WdsWlPubConst.DM_SO_DEALNUM_FIELD_NAME
					+ ",0)+"
					+ PuPubVO.getUFDouble_NullAsZero(entry.getValue())
							.doubleValue() + " where corder_bid='"
					+ entry.getKey() + "'";
			if (getDao().executeUpdate(sql) == 0) {
				throw new BusinessException("�����쳣���÷��˼ƻ������ѱ�ɾ���������²�ѯ����");
			}
			;

			// ���ƻ�������nplannum�����ۼư�������(ndealnum)�Ƚ�

			// ����ۼư����������ڼƻ��������׳��쳣

			String sql1 = "select count(0) from so_saleorder_b where corder_bid='"
					+ entry.getKey()
					+ "'and (coalesce(nnumber,0)-coalesce("
					+ WdsWlPubConst.DM_SO_DEALNUM_FIELD_NAME + ",0))>=0";
			Object o = getDao().executeQuery(sql1,
					WdsPubResulSetProcesser.COLUMNPROCESSOR);
			if (o == null) {
				throw new BusinessException("���ƻ�������");
			}
		}
	}
	
	private SoDealHeaderVo getPlanHead(SoDealVO dealVo) {
		if (dealVo == null)
			return null;
		SoDealHeaderVo head = new SoDealHeaderVo();
		String[] names = head.getAttributeNames();
		for (String name : names) {
			head.setAttributeValue(name, dealVo.getAttributeValue(name));
		}
		return head;
	}

}

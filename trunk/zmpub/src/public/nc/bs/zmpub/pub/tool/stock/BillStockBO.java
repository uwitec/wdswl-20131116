package nc.bs.zmpub.pub.tool.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nc.bs.logging.Logger;
import nc.bs.zmpub.pub.tool.SingleVOChangeDataBsTool;
import nc.jdbc.framework.util.SQLHelper;
import nc.ui.scm.util.ObjectUtils;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.zmpub.pub.tool.CombinVO;

/**
 * �Ƕ��ִ������������չ ��� ҵ�񵥾ݸ����ִ����Ĳ���
 * 
 * @author mlr
 */
public abstract class BillStockBO extends StockBO {
	/**
	 * ��ü̳��������
	 */
	public abstract String getThisClassName();

	/**
	 * 
	 */
	private static final long serialVersionUID = 3404341684522482080L;

	// ���󷽷�����չ��ʼ
	/**
	 * �������� �� ���ݽ����� ��Ӧmap
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract Map<String, String> getTypetoChangeClass() throws Exception;

	/**
	 * ��ø����������� ��Ӧ�� �ִ������ù���
	 * 
	 * @return Map<String,boolean[]>
	 * @throws Exception
	 */

	public abstract Map<String, UFBoolean[]> getTypetosetnum() throws Exception;

	// ���󷽷�����չ����
	/**
	 * ҵ�񵥾ݸ����ִ��� ��������ҵ�񵥾ݵ�����
	 * 
	 * @param vos
	 *            ҵ�񵥾�����
	 * @param pk_billtype
	 *            �����ִ�����������
	 * @throws Exception
	 */

	public void updateStockByBill(SuperVO[] vos, String pk_billtype)
			throws Exception {
		Map<String, String> map = getTypetoChangeClass();
		if (map == null || map.size() == 0)
			throw new Exception("û��ע�ᵥ������->�ִ��������ݽ�����");
		String className = map.get(pk_billtype);
		if (className == null || className.length() == 0)
			throw new Exception(" ��������Ϊ:" + pk_billtype + " û��ע�ύ����");
		String changeClName = getClassName();
		if (changeClName == null || changeClName.length() == 0)
			throw new Exception("û��ע���ִ���ʵ�����ȫ·��");
		Class cl = Class.forName(changeClName);
		SuperVO[] numvos = SingleVOChangeDataBsTool.runChangeVOAry(vos, cl,
				className);
		if (numvos == null || numvos.length == 0) {
			return;
		}
		setAccountNumChange(numvos, pk_billtype);
		updateStock(numvos);
	}

	/**
	 * ҵ�񵥾ݸ����ִ��� ��������ҵ�񵥾ݵ�����
	 * 
	 * @param vos
	 *            ҵ�񵥾�����
	 * @param pk_billtype
	 *            �����ִ�����������
	 * @throws Exception
	 */
	public void updateStockByBill(AggregatedValueObject vos1, String pk_billtype)
			throws Exception {
		AggregatedValueObject vos=(AggregatedValueObject) ObjectUtils.serializableClone(vos1);
		
		Map<String, String> map = getTypetoChangeClass();
		if (map == null || map.size() == 0)
			throw new Exception("û��ע�ᵥ������->�ִ��������ݽ�����");
		String className = map.get(pk_billtype);
		if (className == null || className.length() == 0)
			throw new Exception(" ��������Ϊ:" + pk_billtype + " û��ע�ύ����");
		String changeClName = getClassName();
		if (changeClName == null || changeClName.length() == 0)
			throw new Exception("û��ע���ִ���ʵ�����ȫ·��");
		// �������޸Ĳ� �޸������� �������޸Ĳ� ɾ������
		dealMod(vos, pk_billtype);
		Class cl = Class.forName(changeClName);
		// ���˵�ɾ����
		AggregatedValueObject billvo = filterDel(vos);
		SuperVO[] numvos = SingleVOChangeDataBsTool.runChangeVOAry(billvo, cl,
				className);
		if (numvos == null || numvos.length == 0) {
			return;
		}
		setAccountNumChange(numvos, pk_billtype);
		updateStock(numvos);
	}

	/**
	 * ���˵�ɾ����
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-6-28����05:20:24
	 * 
	 */
	private AggregatedValueObject filterDel(AggregatedValueObject vos)
			throws Exception {
		if (vos == null || vos.getParentVO() == null
				|| vos.getChildrenVO() == null
				|| vos.getChildrenVO().length == 0)
			return vos;
		AggregatedValueObject billvo = (AggregatedValueObject) ObjectUtils
				.serializableClone(vos);
		SuperVO[] bvos = (SuperVO[]) billvo.getChildrenVO();
		List<SuperVO> list = new ArrayList<SuperVO>();
		for (int i = 0; i < bvos.length; i++) {
			if (bvos[i].getStatus() != VOStatus.DELETED) {
				list.add(bvos[i]);
			}
		}
		billvo.setChildrenVO(list.toArray((SuperVO[]) java.lang.reflect.Array
				.newInstance(bvos[0].getClass(), 0)));
		return billvo;
	}

	/**
	 * �������޸Ĳ��� �޸������� �������޸Ĳ��� ɾ������
	 * 
	 * @param pk_billtype
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-6-28����12:39:13
	 * 
	 */
	private void dealMod(AggregatedValueObject vos, String pk_billtype)
			throws Exception {
		if (vos.getParentVO() == null) {
			throw new Exception("���ݱ�ͷΪ��");
		}
		if (vos.getChildrenVO() == null || vos.getChildrenVO().length == 0)
			return;
		SuperVO[] bodys = (SuperVO[]) ObjectUtils.serializableClone(vos
				.getChildrenVO());
		// ����޸� ɾ����vo
		List<SuperVO> list = new ArrayList<SuperVO>();
		for (int i = 0; i < bodys.length; i++) {
			//modify by zhw  �������Ϊ��  Ϊ����״̬
			if(bodys[i].getPrimaryKey() != null){
				if (bodys[i].getStatus() == VOStatus.UPDATED
						|| bodys[i].getStatus() == VOStatus.DELETED) {
					list.add(bodys[i]);
				}
			}
		}
		if (list == null || list.size() == 0)
			return;
		// ����޸�ɾ���ļ�¼�� ���ݿ��Ӧ��¼ vo
		SuperVO[] ovos = (SuperVO[]) java.lang.reflect.Array.newInstance(list
				.get(0).getClass(), list.size());
		for (int i = 0; i < list.size(); i++) {
			List li = (List) getDao().retrieveByClause(
					list.get(0).getClass(),
					list.get(0).getPKFieldName() + " = '"
							+ list.get(i).getPrimaryKey() + "'");
			if (li != null && li.size() != 0)
				ovos[i] = (SuperVO) li.get(0);
		}
		Map<String, String> map = getTypetoChangeClass();
		String changeClName = getClassName();
		Class cl = Class.forName(changeClName);
		String className = map.get(pk_billtype);
		SuperVO headVo = (SuperVO) ObjectUtils.serializableClone(vos
				.getParentVO());
		SuperVO[] bodyVos = (SuperVO[]) ObjectUtils.serializableClone(ovos);
		AggregatedValueObject billvo = new HYBillVO();
		billvo.setParentVO(headVo);
		billvo.setChildrenVO(bodyVos);
		SuperVO[] numvos = SingleVOChangeDataBsTool.runChangeVOAry(billvo, cl,
				className);
		if (getChangeNums() == null || getChangeNums().length == 0)
			throw new Exception("û��ע���ִ����仯�ֶ�");
		for (int i = 0; i < numvos.length; i++) {
			for (int j = 0; j < getChangeNums().length; j++) {
				UFDouble uf = PuPubVO.getUFDouble_NullAsZero(numvos[i]
						.getAttributeValue(getChangeNums()[j]));
				numvos[i].setAttributeValue(getChangeNums()[j], new UFDouble(0)
						.sub(uf));
			}
		}
		setAccountNumChange(numvos, pk_billtype);
		updateStock(numvos);
	}

	/**
	 * ���ݴ�����ִ���vo ȡ��ά�� ��ѯ�ִ��� ArrayList<SuperVO[]> ���ÿ����ѯά�Ȳ�ѯ�������ִ���
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-2����12:25:52
	 * 
	 */
	public ArrayList<SuperVO[]> queryStockDetail(SuperVO[] vos)
			throws Exception {
		ArrayList<SuperVO[]> list = new ArrayList<SuperVO[]>();
		if (vos == null || vos.length == 0)
			return null;

		for (int i = 0; i < vos.length; i++) {
			String whereSql = getWheresql(vos[i]);
			if (whereSql == null || whereSql.length() == 0) {
				list.add(null);
			} else {
				list.add(queryStock(whereSql));
			}
		}
		return list;
	}
	/**
	 * ���ݴ�����ִ���vo ȡ��ά�� ��ѯ�ִ��� ArrayList<SuperVO[]> ���ÿ����ѯά�Ȳ�ѯ�������ִ���
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-2����12:25:52
	 * 
	 */
	public ArrayList<SuperVO[]> queryStockDetail1(SuperVO[] vos,String whereSql1)
			throws Exception {
		ArrayList<SuperVO[]> list = new ArrayList<SuperVO[]>();
		if (vos == null || vos.length == 0)
			return null;

		for (int i = 0; i < vos.length; i++) {
			String whereSql = getWheresql(vos[i]);		
			if (whereSql == null || whereSql.length() == 0) {
				list.add(null);
			} else {
				if(whereSql!=null && whereSql.length()>0 ){
					whereSql=whereSql+" and "+whereSql1;
				}
				list.add(queryStock(whereSql));
			}
		}
		return list;
	}

	public String getWheresql(SuperVO vos) throws Exception {
		if (vos == null)
			return null;
		String[] fields = getDef_Fields();
		if (fields == null || fields.length == 0)
			throw new Exception("û��ע���ִ���ά��");
		String wsql = null;
		for (int i = 0; i < fields.length; i++) {
			String value = PuPubVO.getString_TrimZeroLenAsNull(vos
					.getAttributeValue(fields[i]));
			if (value != null) {
				if (wsql == null) {
					wsql = fields[i] + " = '" + value + "'";
				} else {
					wsql = wsql + " and " + fields[i] + " = '" + value + "'";
				}
			}
		}
		if (wsql == null) {
			return null;
		} else {
			wsql = wsql + " and isnull(dr,0)=0 and pk_corp='"
					+ SQLHelper.getCorpPk() + "'";
		}
		return wsql;
	}

	/**
	 * ���ݴ�����ִ���vo ȡ��ά�� ��ѯ�ִ��� SuperVO[] ���ÿ����ѯά�Ȳ�ѯ�������ִ���(����ѯά�Ⱥϲ���)
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-2����12:27:29
	 * 
	 */
	public SuperVO[] queryStockCombin(SuperVO[] vos) throws Exception {
		ArrayList<SuperVO[]> list = queryStockDetail(vos);
		if (vos == null || vos.length == 0)
			return null;
		if (list == null || list.size() == 0)
			return null;
		for (int i = 0; i < vos.length; i++) {
			SuperVO[] vss = list.get(i);
			String[] conds = getConminFields(vos[i]);
			if (conds == null || conds.length == 0) {
				continue;
			} else {
				SuperVO[] coms = (SuperVO[]) CombinVO.combinData(vss, conds,
						getChangeNums(), vos[0].getClass());
				String[] filelds = getChangeNums();
				if (filelds == null || filelds.length == 0) {
					continue;
				}
				if(coms==null || coms.length==0)
					continue;
				for (int k = 0; k < filelds.length; k++) {
					vos[i].setAttributeValue(filelds[k], coms[0]
							.getAttributeValue(filelds[k]));
				}
			}
		}
		return vos;
	}
	
	/**
	 * ���ݴ�����ִ���vo ȡ��ά�� ��ѯ�ִ��� SuperVO[] ���ÿ����ѯά�Ȳ�ѯ�������ִ���(����ѯά�Ⱥϲ���)
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-2����12:27:29
	 * 
	 */
	public SuperVO[] queryStockCombin1(SuperVO[] vos,String whereSql) throws Exception {
		ArrayList<SuperVO[]> list = queryStockDetail1(vos,whereSql);
		if (vos == null || vos.length == 0)
			return null;
		if (list == null || list.size() == 0)
			return null;
		for (int i = 0; i < vos.length; i++) {
			SuperVO[] vss = list.get(i);
			String[] conds = getConminFields(vos[i]);
			if (conds == null || conds.length == 0) {
				continue;
			} else {
				SuperVO[] coms = (SuperVO[]) CombinVO.combinData(vss, conds,
						getChangeNums(), vos[0].getClass());
				String[] filelds = getChangeNums();
				if (filelds == null || filelds.length == 0) {
					continue;
				}
				if(coms==null || coms.length==0)
					continue;
				for (int k = 0; k < filelds.length; k++) {
					vos[i].setAttributeValue(filelds[k], coms[0]
							.getAttributeValue(filelds[k]));
				}
			}
		}
		return vos;
	}

	/**
	 * ������ݺϲ�ά��
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-2����12:52:49
	 * 
	 */
	public String[] getConminFields(SuperVO vo) throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		String[] fields = getDef_Fields();
		if (fields == null || fields.length == 0)
			throw new Exception("û��ע���ִ���ά��");
		if (vo == null)
			return null;
		for (int i = 0; i < fields.length; i++) {
			String value = PuPubVO.getString_TrimZeroLenAsNull(vo
					.getAttributeValue(fields[i]));
			if (value != null) {
				list.add(fields[i]);
			}
		}
		if (list == null || list.size() == 0)
			return null;

		return list.toArray(new String[0]);
	}

	/**
	 * �����ִ������ݱ仯��
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void setAccountNumChange(SuperVO[] vos1, String pk_billtype)
			throws Exception {
		if (vos1 == null || vos1.length == 0)
			return;
		Map<String, UFBoolean[]> nmap = getTypetosetnum();
		if (nmap == null || nmap.size() == 0)
			throw new Exception(" û��ע�ᵥ������ ��Ӧ  �ִ����仯����");
		String[] fields = getChangeNums();
		if (fields == null || fields.length == 0)
			throw new Exception(" û��ע���ִ����仯���ֶ�");
		UFBoolean[] ufs = nmap.get(pk_billtype);
		if (ufs == null || ufs.length == 0 || ufs.length != fields.length)
			throw new Exception("��������Ϊ" + pk_billtype
					+ " ע��ı仯������Ϊ�� ��  ע��Ĺ�������ͱ仯���ֶ����鳤�Ȳ�һ��");
		setAccountNum(vos1, ufs);
	}

	/**
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�����󼶵�������ת��Ϊ̨�˲��� 2011-9-14����03:24:36
	 * @param bill
	 *            �󼶵���
	 * @return
	 * @throws Exception
	 */
	public void setAccountNum(SuperVO[] vos, UFBoolean[] isNumCirl)
			throws Exception {
		if (vos == null || vos.length == 0)
			return;
		for (int j = 0; j < vos.length; j++) {
			String[] fields = getChangeNums();
			for (int i = 0; i < fields.length; i++) {
				if (isNumCirl[i] == null) {
					vos[j].setAttributeValue(fields[i], new UFDouble(0.0));
				} else if (isNumCirl[i].booleanValue() == true) {
					UFDouble num = PuPubVO.getUFDouble_NullAsZero(vos[j]
							.getAttributeValue(fields[i]));
					vos[j].setAttributeValue(fields[i], num.multiply(-1));
				}
			}
		}
	}

	/**
	 * ���ݴ�����ִ���vo ȡ��ά�� ��ѯ�ִ��� ArrayList<SuperVO[]> ���ÿ����ѯά�Ȳ�ѯ�������ִ���
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-2����12:25:52
	 * 
	 */
	public ArrayList<SuperVO[]> queryStockDetailForClient(SuperVO[] vos)
			throws Exception {
		ArrayList<SuperVO[]> list = null;
		try {
			Class[] ParameterTypes = new Class[] { SuperVO[].class };
			Object[] ParameterValues = new Object[] { vos };
			Object o = LongTimeTask.calllongTimeService("zmpub", null,
					"���ڲ�ѯ...", 1, getThisClassName(), null, "queryStockDetail",
					ParameterTypes, ParameterValues);
			if (o != null) {
				list = (ArrayList<SuperVO[]>) o;
			}
		} catch (Exception e) {
			Logger.error(e);
			throw new Exception(e.getMessage());

		}
		return list;
	}

	/**
	 * ���ݴ�����ִ���vo ȡ��ά�� ��ѯ�ִ��� SuperVO[] ���ÿ����ѯά�Ȳ�ѯ�������ִ���(����ѯά�Ⱥϲ���)
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-2����12:27:29
	 * 
	 */
	public SuperVO[] queryStockCombinForClient(SuperVO[] vos) throws Exception {
		SuperVO[] nvos = null;
		try {
			Class[] ParameterTypes = new Class[] { SuperVO[].class };
			Object[] ParameterValues = new Object[] { vos };
			Object o = LongTimeTask.calllongTimeService("zmpub", null,
					"���ڲ�ѯ...", 1, getThisClassName(), null, "queryStockCombin",
					ParameterTypes, ParameterValues);
			if (o != null) {
				nvos = (SuperVO[]) o;
			}
		} catch (Exception e) {
			Logger.error(e);
			throw new Exception(e.getMessage());

		}
		return nvos;
	}
	/**
	 * ���ݴ�����ִ���vo ȡ��ά�� ��ѯ�ִ��� SuperVO[] ���ÿ����ѯά�Ȳ�ѯ�������ִ���(����ѯά�Ⱥϲ���)
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-2����12:27:29
	 * 
	 */
	public SuperVO[] queryStockCombinForClient(SuperVO[] vos,String whereSql) throws Exception {
		SuperVO[] nvos = null;
		try {
			Class[] ParameterTypes = new Class[] { SuperVO[].class,String.class };
			Object[] ParameterValues = new Object[] { vos,whereSql};
			Object o = LongTimeTask.calllongTimeService("zmpub", null,
					"���ڲ�ѯ...", 1, getThisClassName(), null, "queryStockCombin1",
					ParameterTypes, ParameterValues);
			if (o != null) {
				nvos = (SuperVO[]) o;
			}
		} catch (Exception e) {
			Logger.error(e);
			throw new Exception(e.getMessage());

		}
		return nvos;
	}

	/**
	 * ͨ��where������ѯ�ִ���
	 * 
	 * @param whereSql
	 * @return
	 */
	public SuperVO[] queryStockForClient(String whereSql) throws Exception {
		SuperVO[] nvos = null;
		try {
			Class[] ParameterTypes = new Class[] { String.class };
			Object[] ParameterValues = new Object[] { whereSql };
			Object o = LongTimeTask.calllongTimeService("zmpub", null,
					"���ڲ�ѯ...", 1, getThisClassName(), null, "queryStock",
					ParameterTypes, ParameterValues);
			if (o != null) {
				nvos = (SuperVO[]) o;
			}
		} catch (Exception e) {
			Logger.error(e);
			throw new Exception(e.getMessage());

		}
		return nvos;
	}

}

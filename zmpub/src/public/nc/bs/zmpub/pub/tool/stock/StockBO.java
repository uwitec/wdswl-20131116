package nc.bs.zmpub.pub.tool.stock;

import java.io.Serializable;
import java.util.Collection;

import nc.bs.dao.BaseDAO;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.zmpub.pub.tool.CombinVO;

/**
 * ����̨�� ����ִ����Ĺ��������� ʲô���ִ����� �ִ������� ���״̬��ʱʱ�仯������ ��չ�� ����ⵥ �ִ����϶�Ҫ�� �����ⵥ �ִ����϶�Ҫ��
 * 
 * �����ü�¼�����ǰ������ı� �ͽ����ִ�����
 * 
 * �������������� �����ִ����仯�ģ� ʲôʱ�����أ� ������������ҪӰ�쵽�ִ�����ʱ�� �Ϳ����ñ������� �������ִ���
 * 
 * Ҳ�����ñ�������������Сά�Ȳ�ѯ�ִ���
 * 
 * @author zhf mlr �����ִ��� ����������
 */
public abstract class StockBO implements Serializable {

	private static final long serialVersionUID = -3849756175968282000L;

	public StockBO() {
		super();
	}

	public StockBO(BaseDAO dao) {
		super();
		this.dao = dao;
	}

	/**
	 * �����Ӧ��ʵ�����¹��� 1�������ִ��������������£� 2����ȡ�ִ���̬ 3������������ 4������������ 5�������ڼ��� 6������������
	 */
	private BaseDAO dao = null;

	public BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	/**
	 * ����ִ�����Сά�ȶ����ֶ�
	 * 
	 * @return
	 */
	public abstract String[] getDef_Fields();

	/**
	 * ���Ӱ���ִ����仯���ֶ�����(��֧��UFDouble����)
	 * 
	 * @return
	 */
	public abstract String[] getChangeNums();

	/**
	 * ��ȡ�ִ������ȫ·��
	 * 
	 * @return
	 */
	public abstract String getClassName();

	/**
	 * �����ִ���
	 * 
	 * @param accounts
	 * @throws Exception
	 */
	public void updateStock(SuperVO[] accounts1) throws Exception {
		if (getDef_Fields() == null || getDef_Fields().length == 0) {
			throw new Exception("û��ע���ִ�����Сά������");
		}
		if (getChangeNums() == null || getChangeNums().length == 0) {
			throw new Exception("û��ע��Ӱ���ִ����仯������");
		}
		if (getClassName() == null || getClassName().length() == 0) {
			throw new Exception("û��ע���ִ���ʵ�����ȫ·��");
		}
		Class cl = Class.forName(getClassName());
		// ���Ȱ� ��С��ά�Ƚ������ݺϲ�
		SuperVO[] accounts = (SuperVO[]) CombinVO.combinData(accounts1,
				getDef_Fields(), getChangeNums(), cl);
		if (accounts == null || accounts.length == 0)
			return;
		checkBeforeUpdate(accounts);
		int size = accounts.length;
		String whereSql = null;
		for (int i = 0; i < size; i++) {
			whereSql = getWhereSql(accounts[i]);
			whereSql = whereSql + " and isnull(dr,0)=0 ";
			SuperVO[] vos = queryStock(whereSql);
			// ���ݿ���û�и�ά�ȵ����� ���²���һ��
			if (vos == null || vos.length == 0) {
				getDao().insertVO(accounts[i]);
			} else if (vos.length > 1) {
				throw new Exception("�����쳣 ����Сά�Ȼ�ȡ�ִ������� ��������һ��");
			}
			// �������Сά�Ȳ�ѯ��һ���Ļ� �ͽ��µ����ݼӵ��ɵ�������ִ�� ���²���
			else if (vos.length == 1) {
				SuperVO oldvo = vos[0];// �����ݿ��в�ѯ����������
				SuperVO newvo = accounts[i];
				String[] changeNums = getChangeNums();
				for (int j = 0; j < changeNums.length; j++) {
					UFDouble old = PuPubVO.getUFDouble_NullAsZero(oldvo
							.getAttributeValue(changeNums[j]));
					UFDouble newo = PuPubVO.getUFDouble_NullAsZero(newvo
							.getAttributeValue(changeNums[j]));
					oldvo.setAttributeValue(changeNums[j], old.add(newo));
				}
				getDao().updateVO(oldvo);
			}
		}
		// �ִ�����������֮��,У���ִ����Ƿ����ָ�ֵ
		// ������ָ�ֵ �������ݻع�
		check(accounts);
	}

	/**
	 * �ִ�������ǰУ�� У���ִ�����Сά�Ȳ���Ϊ��
	 * 
	 * @throws Exception
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-7-13����07:43:21
	 */
	private void checkBeforeUpdate(SuperVO[] accounts) throws Exception {
		String[] fields = getDef_Fields();
		if (fields == null || fields.length == 0)
			throw new Exception("û��ע���ִ�����Сά���ֶ�");
		for (int i = 0; i < accounts.length; i++) {
			SuperVO vo = accounts[i];
			for (int j = 0; j < fields.length; j++) {
				System.out.println("======="+vo
						.getAttributeValue(fields[j]).toString());
				String value = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue(fields[j]));
				if (value == null) {
					throw new Exception("�ִ�������ʱ ������Сά��Ϊ�յ��ֶ�");
				}
			}
		}

	}

	public String getWhereSql(SuperVO superVO) throws Exception {
		String whereSql = "";
		String[] fields = getDef_Fields();
		if (fields == null || fields.length == 0)
			throw new Exception("û��ע���ִ�����Сά���ֶ�");
		for (int i = 0; i < fields.length; i++) {
			whereSql = whereSql + " " + fields[i] + " = '"
					+ superVO.getAttributeValue(fields[i]) + "'";
			if (i != fields.length - 1) {
				whereSql = whereSql + " and ";
			}
		}
		return whereSql;
	}

	/**
	 * ͨ��where������ѯ�ִ���
	 * 
	 * @param whereSql
	 * @return
	 */
	public SuperVO[] queryStock(String whereSql) throws Exception {
		String clname = getClassName();
		if (clname == null || clname.length() == 0)
			throw new Exception("û��ע���ִ���ʵ����ȫ·��");
		Class cl = Class.forName(clname);
		Collection list = getDao().retrieveByClause(cl, whereSql);
		if (list == null || list.size() == 0)
			return null;
		SuperVO[] vos = (SuperVO[]) list
				.toArray((SuperVO[]) java.lang.reflect.Array.newInstance(cl,
						list.size()));
		return vos;

	}

	/**
	 * У���ִ������ݲ���Ϊ��ֵ
	 * 
	 * @param whereSql
	 * @return
	 * @throws Exception
	 */
	public void check(SuperVO[] vos) throws Exception {
		if (vos == null || vos.length == 0)
			return;
		for (int i = 0; i < vos.length; i++) {
			String whereSql = getWhereSql(vos[i]);
			SuperVO[] ols = queryStock(whereSql);
			if (ols == null || ols.length == 0)
				return;
			for (int j = 0; j < ols.length; j++) {
				String[] fields = getChangeNums();
				if (fields == null || fields.length == 0) {
					throw new Exception("û��ע���ִ����仯�ֶ�");
				}
				for (int k = 0; k < fields.length; k++) {
					UFDouble uf = PuPubVO.getUFDouble_NullAsZero(ols[j]
							.getAttributeValue(fields[k]));
					if (uf.compareTo(new UFDouble(0.0)) < 0) {
						throw new Exception("�ִ������ָ�ֵ");
					}
				}
			}
		}
	}

}

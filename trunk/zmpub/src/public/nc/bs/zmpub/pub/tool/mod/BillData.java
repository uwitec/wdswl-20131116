package nc.bs.zmpub.pub.tool.mod;

import nc.vo.pub.lang.UFBoolean;

/**
 * ҵ�񵥾���Ϣע����
 * 
 * @author mlr
 */
public abstract class BillData {
	/**
	 * ע��仯����
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-10-31����09:25:17
	 * @return
	 * @throws Exception
	 */
	public abstract UFBoolean[] getIsChangeNum() throws Exception;

	/**
	 * ע����Щ����״̬�ĵ��ݿ��Բ�ѯ Ŀǰֻ֧�ֱ���̬������̬ 0---Ϊ����̬ 1---Ϊ����̬
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-10-31����09:26:24
	 * @return
	 * @throws Exception
	 */
	public abstract boolean[] getBillStatus() throws Exception;

	/**
	 * ע�ᵥ��->�ִ����ĵ�������
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-10-31����09:29:04
	 * @return
	 * @throws Exception
	 */
	public abstract String getChangeClass() throws Exception;
}

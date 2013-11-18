package nc.vo.zmpub.pub.report2;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.trade.report.query.QueryDLG;
import nc.vo.pub.query.ConditionVO;
import nc.vo.trade.field.BillField;

/**
 * ������ѯ�Ի���
 */
public class HYPubQueryDlg extends QueryDLG {

	public HYPubQueryDlg() {
		super();
	}

	public HYPubQueryDlg(java.awt.Container parent) {
		super(parent);
	}

	public HYPubQueryDlg(java.awt.Container parent, String title) {
		super(parent, title);
	}

	public HYPubQueryDlg(java.awt.Frame parent) {
		super(parent);
	}

	public HYPubQueryDlg(java.awt.Frame parent, String title) {
		super(parent, title);
	}

	public HYPubQueryDlg(boolean isFixedSet) {
		super(isFixedSet);
	}

	/**
	 * �ڲ�ѯģ���г��ֵ������������������
	 * @return
	 * twh (2006-6-14 ����10:58:55)<br>
	 */
	protected String getHeadTable() {
		return null;
	}

	/**
	 * ����״̬����
	 * @return
	 * twh (2006-6-14 ����11:01:28)<br>
	 */
	protected String getBillStatusFieldName() {
		return BillField.getInstance().getField_BillStatus();
	}

	/**
	 * �����ConditionVO[]��ϳɵ�where�Ӿ�
	 */
	public String getWhereSQL(ConditionVO[] conditions) {
		return super.getWhereSQL(conditions);
	}

	protected String getPkCorp() {
		return ClientEnvironment.getInstance().getCorporation().getPk_corp();
	}

	/**
	 * ����fieldcode����ȡֵ���Ĳ��ջ�̶�ѡ�����(������ѡ��Ľ��).
	 */
	public Object getValueRefObjectByFieldCode(String fieldcode) {
		Object o = super.getValueRefObjectByFieldCode(fieldcode);
		if (o == null) {
			return new UIRefPane();
		}
		return o;
	}
}

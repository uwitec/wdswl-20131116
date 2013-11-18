package nc.ui.zmpub.pub.tool;

import java.util.HashMap;
import java.util.List;

import nc.bs.logging.Logger;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.vo.bd.def.DefVO;

/**
 * ��ȡ�Զ��������
 * 
 * @author Administrator
 * 
 */
public class DefSetTool {

	private static HashMap<String, DefVO[]> m_hmDefHead = new HashMap<String, DefVO[]>();

	private static HashMap<String, DefVO[]> m_hmDefBody = new HashMap<String, DefVO[]>();

	public static DefVO[] getDefHead(String pk_corp, String cbilltypecode) {
		if (pk_corp == null) {
			return null;
		}
		if (!m_hmDefHead.containsKey(pk_corp + cbilltypecode)) {
			Object[] objs = getDefVOBatch(pk_corp, cbilltypecode);
			if (objs == null || objs.length == 0) {
				return null;
			}
			if (objs[1] != null) {
				m_hmDefBody.put(pk_corp + cbilltypecode, (DefVO[]) objs[1]);
			}
			if (objs[0] == null) {
				return null;
			}
			m_hmDefHead.put(pk_corp + cbilltypecode, (DefVO[]) objs[0]);
		}
		return (DefVO[]) m_hmDefHead.get(pk_corp + cbilltypecode);
	}

	public static DefVO[] getDefBody(String pk_corp, String cbilltypecode) {
		if (pk_corp == null) {
			return null;
		}
		if (!m_hmDefBody.containsKey(pk_corp + cbilltypecode)) {
			Object[] objs = getDefVOBatch(pk_corp, cbilltypecode);
			if (objs == null) {
				return null;
			}
			if (objs[0] != null) {
				m_hmDefHead.put(pk_corp + cbilltypecode, (DefVO[]) objs[0]);
			}
			if (objs[1] == null) {
				return null;
			}
			m_hmDefBody.put(pk_corp + cbilltypecode, (DefVO[]) objs[1]);
		}
		return (DefVO[]) m_hmDefBody.get(pk_corp + cbilltypecode);
	}

	/**
	 * ���ܣ�����ִ���Զ�����VO��ȡ����
	 */
	private static Object[] getDefVOBatch(String pk_corp, String cbilltypecode) {
		Object[] objs = null;
		try {
			String objheadcode = "MD_" + cbilltypecode + "_head";
			String objbodycode = "MD_" + cbilltypecode + "_body";
			if (objheadcode == null && objbodycode == null)
				return null;
			List<DefVO[]> l = null;
			// List<DefVO[]> l =
			// DefquoteQueryUtil.getInstance().queryDefusedVOByCodes(new
			// String[]{objheadcode,objbodycode}, pk_corp);
			if (l != null)
				return l.toArray();
		} catch (Exception e) {
			Logger.error("����ִ���Զ�����VO��ȡ�����쳣!��ϸ��Ϣ���£�");
		}
		return objs;
	}

	/**
	 * ����༭���¼�
	 * 
	 * @param billModel
	 * @param iRow
	 * @param sVdefValueKey
	 * @param sVdefPkKey
	 */
	public static void afterEditBody(BillModel billModel, int iRow,
			String sVdefValueKey, String sVdefPkKey) {

		// ������ȷ���ж�
		if (billModel == null || sVdefPkKey == null || sVdefValueKey == null
				|| billModel.getItemByKey(sVdefPkKey) == null
				|| billModel.getItemByKey(sVdefValueKey) == null) {
			return;
		}

		BillItem item = billModel.getItemByKey(sVdefValueKey);
		// ���������ж�
		if (item.getDataType() == BillItem.USERDEF
				|| item.getDataType() == BillItem.UFREF) {
			UIRefPane refpane = (UIRefPane) item.getComponent();
			String sPk_defdoc = getString_Trim0LenAsNull(refpane.getRefPK());
			if (sPk_defdoc == null
					&& (refpane.getUITextField().getText() != null && refpane
							.getUITextField().getText().trim().length() > 0)) {
				refpane.setValue("");
				billModel.setValueAt(null, iRow, sVdefValueKey);
				return;
			}
			billModel.setValueAt(sPk_defdoc, iRow, sVdefPkKey);
		} else {
			billModel.setValueAt(null, iRow, sVdefPkKey);
		}

	}

	public static String getString_Trim0LenAsNull(Object value) {
		if (value == null || value.toString().trim().length() == 0) {
			return null;
		}
		return value.toString();
	}

	/**
	 * ��ͷ�༭���¼�
	 * 
	 * @param billModel
	 * @param iRow
	 * @param sVdefValueKey
	 * @param sVdefPkKey
	 */
	public static void afterEditHead(BillData bdata, String sVdefValueKey,
			String sVdefPkKey) {

		// ������ȷ���ж�
		if (bdata == null || sVdefPkKey == null || sVdefValueKey == null
				|| bdata.getHeadItem(sVdefPkKey) == null
				|| bdata.getHeadItem(sVdefValueKey) == null) {
			return;
		}

		BillItem item = bdata.getHeadItem(sVdefValueKey);
		// ���������ж�
		if (item.getDataType() == BillItem.USERDEF
				|| item.getDataType() == BillItem.UFREF) {
			UIRefPane refpane = (UIRefPane) item.getComponent();
			String sPk_defdoc = getString_Trim0LenAsNull(refpane.getRefPK());
			if (sPk_defdoc == null
					&& (refpane.getUITextField().getText() != null && refpane
							.getUITextField().getText().trim().length() > 0))
				return;
			bdata.setHeadItem(sVdefPkKey, sPk_defdoc);
		} else {
			bdata.setHeadItem(sVdefPkKey, null);
		}
	}
}

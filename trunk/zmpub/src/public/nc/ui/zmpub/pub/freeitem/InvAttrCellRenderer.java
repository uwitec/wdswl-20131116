package nc.ui.zmpub.pub.freeitem;

import java.awt.Color;
import java.awt.Component;

import nc.bs.logging.Logger;
import nc.ui.pub.bill.BillModel;
import nc.vo.pub.AggregatedValueObject;

/**
 * ���˾�--��ɫ��
 * 
 * @author Administrator
 * 
 */
public class InvAttrCellRenderer extends
		javax.swing.table.DefaultTableCellRenderer {

	// ԭ���ĵ�Ԫ��������ɫ
	private final Color INIT_COLOR = Color.blue;
	// ��ʾ�������ɫ
	private final Color FOREGROUND_COLOR = Color.red;// new Color(128,128,255);
	// ��������Itemkey
	private final String FREE_ITEM = "vfree0";

	javax.swing.table.DefaultTableCellRenderer m_defRender = null;

	// ��������������Ե�ͬ������VO
	private AggregatedValueObject m_voBill = null;

	// ������BILL CARD PANEL,����VALUE IS NULL CHECK
	private nc.ui.pub.bill.BillCardPanel m_bcp = null;

	// ��������ֵ��
	private int m_iFreeItemCol = -1;

	/**
	 * RowNOCellRenderer ������ע�⡣
	 */
	public InvAttrCellRenderer() {
		super();
	}

	/*
	 * Returns the table cell renderer.
	 */
	public Component getTableCellRendererComponent(javax.swing.JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {

		Component comp = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		// ���ڱ仯�ķ�Χ
		if (table.getModel() instanceof BillModel) {
			// set the foreground color.
			setForeground(FOREGROUND_COLOR);
			// setBackground(m_newColor);//new Color(128,128,255));
			this.setText("��¼��");
		} else {
			setForeground(INIT_COLOR);
		}
		this.repaint();
		return comp;

	}

	/**
	 * �����ˣ����˾�
	 * 
	 * ���ܣ�������������ɫ��
	 * 
	 */
	public void setFreeItemRenderer(nc.ui.pub.bill.BillCardPanel bcp) {
		if (bcp == null || bcp.getBillTable() == null) {
			Logger.info("CELLRENDER:set renderer param null.");
			return;
		} else
			this.m_bcp = bcp;
		m_iFreeItemCol = bcp.getBodyColByKey(FREE_ITEM);
		nc.ui.pub.bill.BillItem bi = bcp.getBodyItem(FREE_ITEM);
		if (m_iFreeItemCol < 0 || bi == null || bi.getName() == null
				|| !bi.isShow()) {
			Logger.info("CELLRENDER:cannot find free0.");
			return;
		}
		String sTitle = bi.getName();
		nc.ui.pub.beans.UITable table = bcp.getBillTable();
		// ���������Ŀ��ģ���в����ڣ����벶���쳣������
		if (sTitle == null || sTitle.trim().length() <= 0)
			return;
		try {
			javax.swing.table.TableColumn tal = table.getColumn(sTitle);
			if (tal == null) {
				Logger.info("CELLRENDER:cannot find " + sTitle);
				return;
			}
			tal.setCellRenderer(this);
		} catch (Exception e) {
			Logger.error(e);
			return;
		}
	}
}
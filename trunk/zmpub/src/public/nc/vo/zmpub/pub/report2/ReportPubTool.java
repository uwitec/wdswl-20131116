package nc.vo.zmpub.pub.report2;
import nc.ui.pub.report.ReportItem;
public class ReportPubTool {
/**
 * ����һ���������Ԫ��
 * �������ڣ�(01-7-19 17:59:47)
 */
public static ReportItem getItem(
	String key,
	String name,
	int dataType,
	int showOrder,
	int width) {
	ReportItem item = new ReportItem();
	item.setKey(key);
	item.setName(name);
	item.setDataType(dataType);
	item.setShowOrder(showOrder);
	item.setWidth(width);
	item.setEdit(false);
	return item;
}
}
package nc.vo.zmpub.pub.report2;
import nc.ui.pub.beans.table.ColumnGroup;
/**
 * �ܹ���¼�����  ������ϱ�ͷ
 * @author mlr
 */
public class ZmColumnGroup extends ColumnGroup{
    private String parentName=null;//��¼���������
    private String name=null;//�Լ�������
	public ZmColumnGroup(String text) {
		super(text);
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

package nc.vo.zmpub.pub.report;

/**
 * �������ڣ�(2004-1-15 14:47:48)
 * 
 * @author��������
 */
public interface IReportVO {
	public java.util.Vector getDetailvo();

	public boolean isCombined();

	public boolean isSubtotal();

	public boolean isTotal();

	public void setCombined(boolean b);

	public void setDetailVO(java.util.Vector v);

	public void setSubtotal(boolean b);

	public void setTotal(boolean b);
}

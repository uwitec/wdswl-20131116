package nc.vo.zmpub.pub.report2;
/**
 * ���ں���ϼƵ�vo
 * @author mlr
 *
 */
public class TotalVO{
	   private Integer start=null;
	   private Integer end=null;
	   private ZmColumnGroup zm;
	public  TotalVO(){
		super();
	} 
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	public ZmColumnGroup getZm() {
		return zm;
	}
	public void setZm(ZmColumnGroup zm) {
		this.zm = zm;
	}
	   
	   
}
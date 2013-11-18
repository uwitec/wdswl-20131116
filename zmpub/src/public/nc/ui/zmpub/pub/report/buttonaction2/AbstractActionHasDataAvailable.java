package nc.ui.zmpub.pub.report.buttonaction2;
/**
 * ����ʵ����isButtonAvailable,ʹ��ť�������ݵ�ʱ����Ч ����nc.ui.pm.pub.report.ReportBaseUI
 * @author guanyj1
 * 2007.8.10
 */
import nc.ui.report.base.IButtonActionAndState;
import nc.vo.zmpub.pub.report2.ReportBaseUI;

public abstract class AbstractActionHasDataAvailable implements IButtonActionAndState {
	protected ReportBaseUI reportBaseUI = null;

	public AbstractActionHasDataAvailable() {
	}
	public AbstractActionHasDataAvailable(ReportBaseUI reportBaseUI){
		this.reportBaseUI = reportBaseUI;
	}
	
	/**
	 * ����������ʱ��ť��Ч
	 */
	public int isButtonAvailable() {
		 return ENABLE_WHEN_HAS_DATA;
	}

	/**
	 * 
	 * @return ReportBaseUI
	 */
	public ReportBaseUI getReportBaseUI(){
		return reportBaseUI;
	}
	public void setReportBaseUI(ReportBaseUI reportBaseUI){
		this.reportBaseUI = reportBaseUI;
	}
}

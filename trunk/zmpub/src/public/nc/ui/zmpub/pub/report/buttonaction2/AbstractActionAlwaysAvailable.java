package nc.ui.zmpub.pub.report.buttonaction2;
/**
 * ����ʵ����isButtonAvailable,ʹ��ťʼ����Ч
 * @author guanyj
 */
import nc.ui.report.base.IButtonActionAndState;
import nc.vo.zmpub.pub.report2.ReportBaseUI;

public abstract class AbstractActionAlwaysAvailable implements
		IButtonActionAndState {

	protected ReportBaseUI reportBaseUI = null;
	public AbstractActionAlwaysAvailable() {
	}
	public AbstractActionAlwaysAvailable(ReportBaseUI reportBaseUI) {
		this.reportBaseUI = reportBaseUI;
	}
	
	/**
	 * ��ť������Ч
	 */
	public int isButtonAvailable()
	{
		return ENABLE_ALWAYS;
	}
	public ReportBaseUI getReportBaseUI(){
		return reportBaseUI;
	}

	public void setReportBaseUI(ReportBaseUI reportBaseUI){
		this.reportBaseUI = reportBaseUI;
	}
}

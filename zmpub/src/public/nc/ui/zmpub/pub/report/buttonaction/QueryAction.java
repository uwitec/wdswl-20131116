package nc.ui.zmpub.pub.report.buttonaction;

import nc.ui.zmpub.pub.report.ReportBaseUI;

/**
 * ��ѯ �¼�
 * 
 * @author guanyj1
 * 
 */
public class QueryAction extends AbstractActionAlwaysAvailable {

	public QueryAction() {
	}

	/**
	 * @param reportBaseUI
	 */
	public QueryAction(ReportBaseUI reportBaseUI) {
		super(reportBaseUI);
	}

	public void execute() throws Exception {
		getReportBaseUI().onQuery();
		// TODO i18n
		getReportBaseUI().showHintMessage(
				nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report",
						"UPPuifactory_report-000031")/* @res "��ѯ�ɹ�" */);
	}
}

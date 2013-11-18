package nc.ui.zmpub.pub.report.buttonaction;

/**
 * ��������
 * @author guanyj1
 */
import nc.ui.pub.beans.UIDialog;
import nc.ui.trade.report.filter.DataSetFilterDlg;
import nc.ui.zmpub.pub.report.ReportBaseUI;

public class FilterAction extends AbstractActionHasDataAvailable {
	DataSetFilterDlg m_filterDlg = null;

	public FilterAction() {
		super();
	}

	public FilterAction(ReportBaseUI reportBaseUI) {
		super(reportBaseUI);
	}

	public void execute() throws Exception {
		if (getFilterDLG().showModal() == UIDialog.ID_OK) {
			String strFomula = getFilterDLG().getFomula();
			getReportBaseUI().onFilter(strFomula);
			// TODO i18n
			getReportBaseUI()
					.showHintMessage(
							nc.ui.ml.NCLangRes.getInstance().getStrByID(
									"uifactory_report",
									"UPPuifactory_report-000017")/* @res "���˳ɹ�" */);
		}
	}

	private DataSetFilterDlg getFilterDLG() {
		if (m_filterDlg == null) {
			m_filterDlg = new DataSetFilterDlg(getReportBaseUI(),
					getReportBaseUI().getVisibleFields(), getReportBaseUI()
							.getAllBodyDataVO());
		}
		return m_filterDlg;
	}
}

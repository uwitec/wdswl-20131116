package nc.ui.zmpub.pub.report.buttonaction;

/**
 * �ּ�С��  �¼�
 * @author guanyj1
 * @date:2007��8��9��
 */
import java.util.ArrayList;
import java.util.Arrays;

import nc.bs.logging.Logger;
import nc.ui.report.base.ISubTotalConf;
import nc.ui.trade.report.subtotal.SubTotalConfDLG;
import nc.ui.zmpub.pub.report.ReportBaseUI;
import nc.vo.pub.BusinessException;
import nc.vo.pub.report.SubtotalContext;
import nc.vo.trade.report.IReportModelDataTypes;
import nc.vo.trade.report.TableField;

public class LevelSubTotalAction extends AbstractActionHasDataAvailable
		implements ISubTotalConf {

	// С�ƺϼƷ����ֶ�
	private TableField[] subTotalCurrentUsingGroupFields = new TableField[0];

	// С�ƺϼƻ����ֶ�
	private TableField[] SubTotalCurrentUsingTotalFields = new TableField[0];
	// add by ycy 2009-09-07 ��ѡ���Ƿ�С�� �Ƿ�ϼ� ֮ǰ����ѡ��û��Ч��
	private boolean issub = false;

	private boolean issum = true;

	public LevelSubTotalAction(ReportBaseUI reportBaseUI) {
		super(reportBaseUI);
	}

	public LevelSubTotalAction() {
		super();
	}

	/**
	 * ����С�ƺϼƴ��� ���[ȷ��]����executeSubTotal()
	 */
	public void execute() throws Exception {
		SubTotalConfDLG dlg = new SubTotalConfDLG(getReportBaseUI(), this);
		if (dlg.showModal() == nc.ui.pub.beans.UIDialog.ID_OK) {
			issub = dlg.isSub();
			issum = dlg.isSum();
			setSubTotalCurrentUsingGroupFields(dlg.getGroupFields());
			setSubTotalCurrentUsingTotalFields(dlg.getTotalFields());
			getReportBaseUI().setBodyDataForSub();// �����������еĺϼ� С����ȥ��

			executeSubTotal();

			getReportBaseUI()
					.showHintMessage(
							nc.ui.ml.NCLangRes.getInstance().getStrByID(
									"uifactory_report",
									"UPPuifactory_report-000022")/*
																 * @res "С�ƺϼƳɹ�"
																 */);
		}
	}

	/**
	 * �ж����� С�ƺϼ� 2007-8-23 ����03:36:35 guanyj1
	 */
	public void executeSubTotal() throws Exception {
		// ����Ϊ�յ����
		if (getReportBaseUI().getBodyDataVO() == null
				|| getReportBaseUI().getBodyDataVO().length == 0) {
			Logger.debug("������û�����ݣ�С�ƺϼƲ���ȡ��");
			return;
		}
		// δѡ����� �ϼ� �ֶε����
		if (getSubTotalCurrentUsingGroupFields() == null
				|| getSubTotalCurrentUsingGroupFields().length == 0
				|| getSubTotalCurrentUsingTotalFields() == null
				|| getSubTotalCurrentUsingTotalFields().length == 0)
			throw new BusinessException(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("uifactory_report",
							"UPPuifactory_report-000019")/*
														 * @res
														 * "�����ֶκͺϼ��ֶ�������Ҫ��ѡһ����ܽ���С�ƺϼƲ�����"
														 */);

		// ������� �� ��ʼС�ƺϼ�
		if (getSubTotalCurrentUsingGroupFields() != null
				&& getSubTotalCurrentUsingGroupFields().length != 0
				&& getSubTotalCurrentUsingTotalFields() != null
				&& getSubTotalCurrentUsingTotalFields().length != 0) {
			String[] gfs = new String[getSubTotalCurrentUsingGroupFields().length];
			for (int i = 0; i < gfs.length; i++) {
				gfs[i] = getSubTotalCurrentUsingGroupFields()[i].getFieldName();
				gfs[i] = gfs[i].replace('.', '_');
			}
			String[] tfs = new String[getSubTotalCurrentUsingTotalFields().length];
			for (int i = 0; i < tfs.length; i++) {
				tfs[i] = getSubTotalCurrentUsingTotalFields()[i].getFieldName();
				tfs[i] = tfs[i].replace('.', '_');
			}

			onSubtotal(gfs, tfs);

		}
	}

	public boolean isIssub() {
		return issub;
	}

	public void setIssub(boolean issub) {
		this.issub = issub;
	}

	public boolean isIssum() {
		return issum;
	}

	public void setIssum(boolean issum) {
		this.issum = issum;
	}

	/**
	 * ����С�ƺϼ�
	 * 
	 * @param gfs
	 * @param tfs
	 * @throws Exception
	 *             2007-8-23 ����04:15:33 guanyj1
	 */
	public void onSubtotal(String[] gfs, String[] tfs) throws Exception {
		SubtotalContext context = new SubtotalContext();
		context.setGrpKeys(gfs);
		context.setSubtotalCols(tfs);
		context.setTotalNameColKeys(gfs[0]);
		context.setIsSubtotal(issub);
		context.setIsSumtotal(issum);
		context.setSubtotalName("����С�ơ���");
		context.setSumtotalName("�����ϼơ���");
		getReportBaseUI().onSubTotal(context);
	}

	/**
	 * ��TableField[]ת����String[]
	 * 
	 * @param table
	 * @return 2007-8-24 ����02:55:01 guanyj1
	 */
	public String[] totalTableFieldToString(TableField[] table) {
		String[] tfs = new String[table.length];
		for (int i = 0; i < tfs.length; i++) {
			tfs[i] = table[i].getFieldName();
			tfs[i] = tfs[i].replace('.', '_');
		}
		return tfs;
	}

	/**
	 * �������ֶ�ת��ΪTableField[]
	 * 
	 * @param gfs
	 * @return 2007-8-24 ����09:00:09 guanyj1
	 */
	public TableField[] groupStringToTableField(String[] gfs) {
		TableField[] all = getSubTotalCandidateGroupFields();
		TableField[] temp = new TableField[gfs.length];

		for (int i = 0; i < gfs.length; i++) {
			for (int j = 0; j < all.length; j++) {
				if (gfs[i].equals(all[j].getFieldName().replace('.', '_'))) {
					temp[i] = all[j];
					continue;
				}
			}
		}

		return temp;
	}

	/**
	 * �������ֶ�ת��ΪTableField[]
	 * 
	 * @param tfs
	 * @return 2007-8-24 ����09:04:02 guanyj1
	 */
	public TableField[] totalStringToTableField(String[] tfs) {
		TableField[] all = getSubTotalCandidateTotalFields();
		TableField[] temp = new TableField[tfs.length];

		for (int i = 0; i < tfs.length; i++) {
			for (int j = 0; j < all.length; j++) {
				if (tfs[i].equals(all[j].getFieldName().replace('.', '_'))) {
					temp[i] = all[j];
					continue;
				}
			}
		}

		return temp;
	}

	/**
	 * @param newSubTotalCurrentUsingGroupFields
	 *            nc.vo.tm.report.TableField[]
	 */
	public void setSubTotalCurrentUsingGroupFields(
			TableField[] newSubTotalCurrentUsingGroupFields) {
		subTotalCurrentUsingGroupFields = newSubTotalCurrentUsingGroupFields;
	}

	/**
	 * 
	 * @param newSubTotalCurrentUsingTotalFields
	 *            nc.vo.tm.report.TableField[]
	 */
	public void setSubTotalCurrentUsingTotalFields(
			TableField[] newSubTotalCurrentUsingTotalFields) {
		SubTotalCurrentUsingTotalFields = newSubTotalCurrentUsingTotalFields;
	}

	/**
	 * 
	 * @return nc.vo.tm.report.TableField[]
	 */
	public TableField[] getSubTotalCurrentUsingGroupFields() {
		return subTotalCurrentUsingGroupFields;
	}

	/**
	 * 
	 * @return nc.vo.tm.report.TableField[]
	 */
	public TableField[] getSubTotalCurrentUsingTotalFields() {
		return SubTotalCurrentUsingTotalFields;
	}

	/**
	 * ���ؿ�ѡ��С�ƺϼƷ����ֶΡ� ����������ʾ�������ַ����ֶ���Ϊ��ѡ��С�ƺϼƷ����ֶ�
	 * 
	 * @return nc.vo.tm.report.TableField[]
	 */
	public TableField[] getSubTotalCandidateGroupFields() {
		return getReportBaseUI().getVisibleFieldsByDataType(
				IReportModelDataTypes.STRING);
	}

	/**
	 * ���ؿ�ѡ��С�ƺϼƻ����ֶΡ� ����������ʾ��������ֵ��(�������������)�ֶ���Ϊ��ѡ��С�ƺϼƻ����ֶΡ�
	 * 
	 * @return nc.vo.tm.report.TotalField[]
	 */
	public TableField[] getSubTotalCandidateTotalFields() {
		TableField[] f = getReportBaseUI().getVisibleFieldsByDataType(
				IReportModelDataTypes.FLOAT);
		TableField[] i = getReportBaseUI().getVisibleFieldsByDataType(
				IReportModelDataTypes.INTEGER);
		ArrayList<TableField> al = new ArrayList<TableField>();
		al.addAll(Arrays.asList(f));
		al.addAll(Arrays.asList(i));
		return (TableField[]) al.toArray(new TableField[0]);
	}
}

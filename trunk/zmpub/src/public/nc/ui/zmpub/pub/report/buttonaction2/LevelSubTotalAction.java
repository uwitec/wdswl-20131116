package nc.ui.zmpub.pub.report.buttonaction2;

/**
 * �ּ�С��  �¼�
 * @author guanyj1
 * @date:2007��8��9��
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import nc.bs.logging.Logger;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.report.base.ISubTotalConf;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.report.SubtotalContext;
import nc.vo.trade.report.IReportModelDataTypes;
import nc.vo.trade.report.TableField;
import nc.vo.zmpub.pub.report2.ReportBaseUI;
import nc.vo.zmpub.pub.report2.ReportBuffer;
import nc.vo.zmpub.pub.report2.ZmReportBaseUI2;
import nc.vo.zmpub.pub.report2.ZmSubTotalConfDLG;

public class LevelSubTotalAction extends AbstractActionHasDataAvailable
		implements ISubTotalConf {

	// С�ƺϼƷ����ֶ�
	private TableField[] subTotalCurrentUsingGroupFields = new TableField[0];

	// С�ƺϼƻ����ֶ�
	private TableField[] SubTotalCurrentUsingTotalFields = new TableField[0];
	// add by ycy 2009-09-07 ��ѡ���Ƿ�С�� �Ƿ�ϼ� ֮ǰ����ѡ��û��Ч��
	private boolean issub = false;

	private boolean issum = true;

	private boolean isshow = true;

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
		subTotalCurrentUsingGroupFields = new TableField[0];
		SubTotalCurrentUsingTotalFields = new TableField[0];
		ZmSubTotalConfDLG dlg = new ZmSubTotalConfDLG(getReportBaseUI(), this);
		if (dlg.showModal() == nc.ui.pub.beans.UIDialog.ID_OK) {
			issub = dlg.isSub();
			issum = dlg.isSum();
			isshow = dlg.isLevelCompute();
			setSubTotalCurrentUsingGroupFields(dlg.getGroupFields());
			setSubTotalCurrentUsingTotalFields(dlg.getTotalFields());
			getReportBaseUI().setBodyDataForSub();// �����������еĺϼ� С����ȥ��

			executeSubTotal();
			ZmReportBaseUI2 ui = (ZmReportBaseUI2) getReportBaseUI();
			ReportBuffer buff = ui.getBuff();
			buff.setIssub(new UFBoolean(issub));
			buff.setIssum(new UFBoolean(issum));
			TableField[] fls = dlg.getGroupFields();
			String[] tts = null;
			String[] ttsname = null;
			if (fls != null && fls.length != 0) {
				tts = new String[fls.length];
				ttsname = new String[fls.length];
				for (int i = 0; i < fls.length; i++) {
					tts[i] = fls[i].getFieldName();
					ttsname[i] = fls[i].getFieldShowName();
				}
			}
			buff.setTotfields(tts);
			buff.setTotfieldsNames(ttsname);

			// buff.seti

			dealShow();
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
	 * �Զ��������ݺϼ�
	 */
	public void atuoexecute2() throws Exception {
		subTotalCurrentUsingGroupFields = new TableField[0];
		SubTotalCurrentUsingTotalFields = new TableField[0];
		ZmSubTotalConfDLG dlg = new ZmSubTotalConfDLG(getReportBaseUI(), this);
		issub = false;
		issum = true;
		isshow = dlg.isLevelCompute();
		setSubTotalCurrentUsingGroupFields(dlg.getGroupFields2());
		setSubTotalCurrentUsingTotalFields(dlg.getTotalFields2());
		// getReportBaseUI().setBodyDataForSub();// �����������еĺϼ� С����ȥ��

		executeSubTotal();
		dealShow();
		getReportBaseUI().showHintMessage(
				nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report",
						"UPPuifactory_report-000022")/*
													 * @res "С�ƺϼƳɹ�"
													 */);
	}

	/**
	 * �Զ��������ݺϼ�
	 */
	public void atuoexecute2(boolean issub, boolean issum, String[] totfields,
			String[] totfieldsNames) throws Exception {
		subTotalCurrentUsingGroupFields = new TableField[0];
		SubTotalCurrentUsingTotalFields = new TableField[0];
		ZmSubTotalConfDLG dlg = new ZmSubTotalConfDLG(getReportBaseUI(), this);
		this.issub = issub;
		this.issum = issum;
		isshow = dlg.isLevelCompute();
		if (totfields == null || totfields.length == 0) {
			setSubTotalCurrentUsingGroupFields(dlg.getGroupFields2());
		} else {
			setSubTotalCurrentUsingGroupFields(getTableFields(totfields,
					totfieldsNames));
		}
		setSubTotalCurrentUsingTotalFields(dlg.getTotalFields2());
		// getReportBaseUI().setBodyDataForSub();// �����������еĺϼ� С����ȥ��

		executeSubTotal();
		dealShow();
		getReportBaseUI().showHintMessage(
				nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report",
						"UPPuifactory_report-000022")/*
													 * @res "С�ƺϼƳɹ�"
													 */);
	}

	/**
	 * �Զ��������ݺϼ�
	 */
	public void atuoexecute3(boolean issub, boolean issum, String[] totfields,
			String[] totfieldsNames, String[] sumfields, String[] sumfieldsNames)
			throws Exception {
		subTotalCurrentUsingGroupFields = new TableField[0];
		SubTotalCurrentUsingTotalFields = new TableField[0];
		ZmSubTotalConfDLG dlg = new ZmSubTotalConfDLG(getReportBaseUI(), this);
		this.issub = issub;
		this.issum = issum;
		isshow = dlg.isLevelCompute();
		if (totfields == null || totfields.length == 0) {
			setSubTotalCurrentUsingGroupFields(dlg.getGroupFields2());
		} else {
			setSubTotalCurrentUsingGroupFields(getTableFields(totfields,
					totfieldsNames));
		}
		if (sumfields == null || sumfields.length == 0) {
			setSubTotalCurrentUsingTotalFields(dlg.getTotalFields2());
			// getReportBaseUI().setBodyDataForSub();// �����������еĺϼ� С����ȥ��
		} else {
			// dlg.getTotalFields2();
			setSubTotalCurrentUsingTotalFields(getTableFields(sumfields,
					sumfieldsNames));
		}
		executeSubTotal();
		dealShow();
		getReportBaseUI().showHintMessage(
				nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report",
						"UPPuifactory_report-000022")/*
													 * @res "С�ƺϼƳɹ�"
													 */);
	}

	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2011-12-29����06:05:52
	 * @param totfields
	 * @param totfieldsNames
	 * @return
	 */
	public TableField[] getTableFields(String[] totfields,
			String[] totfieldsNames) {
		if (totfields == null || totfields.length == 0)
			return null;
		if (totfieldsNames == null || totfieldsNames.length == 0)
			return null;
		if (totfields.length != totfieldsNames.length) {
			return null;
		}
		TableField[] tbs = new TableField[totfields.length];
		for (int i = 0; i < tbs.length; i++) {
			tbs[i] = new TableField(totfields[i].trim(), totfieldsNames[i]
					.trim(), false);
		}
		return tbs;
	}

	/**
	 * �����Ƿ�ֻ��ʾԭʼ��
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-22����09:49:06
	 */
	private void dealShow() {
		Vector vecs = new Vector();
		String name = subTotalCurrentUsingGroupFields[0].getFieldName();

		BillModel mb = getReportBaseUI().getReportBase().getBillModel();
		int index = mb.getBodyColByKey(name);
		if (isshow == false) {
			Vector vc = getReportBaseUI().getReportBase().getBillModel()
					.getDataVector();
			for (int i = 0; i < vc.size(); i++) {
				Vector v = (Vector) vc.get(i);
				String va = (String) v.get(index);
				if (va.equals("����С�ơ���")) {
					Vector n = (Vector) vc.get(i - 1);
					String sf = create(subTotalCurrentUsingGroupFields, n);
					v.set(index, sf);
					vecs.add(v);
				}

			}
			getReportBaseUI().getReportBase().getBillModel()
					.setDataVector(vecs);
			getReportBaseUI().getReportBase().getBillModel().updateValue();

		}
	}

	private String create(TableField[] subTotalCurrentUsingGroupFields2,
			Vector n) {
		String name = "";
		BillModel mb = getReportBaseUI().getReportBase().getBillModel();
		for (int i = 0; i < subTotalCurrentUsingGroupFields2.length; i++) {
			String fname = subTotalCurrentUsingGroupFields2[i].getFieldName();
			if (fname == null || fname.length() == 0)
				continue;
			if (".".equalsIgnoreCase(fname.substring(fname.length() - 1))) {
				fname = fname.substring(0, fname.length() - 1) + "_";
			}
			int index = mb.getBodyColByKey(fname);
			if (index < 0)
				continue;
			name = name + "%" + n.get(index);
		}
		return name;
	}

	public void getBodyValueVOs(CircularlyAccessibleValueObject[] bodyVOs) {
		BillModel mb = getReportBaseUI().getReportBase().getBillModel();
		for (int i = 0; i < bodyVOs.length; i++) {
			for (int j = 0; j < mb.getBodyItems().length; j++) {
				BillItem item = mb.getBodyItems()[j];
				Object aValue = mb.getValueAt(i, j);
				aValue = item.converType(aValue);
				bodyVOs[i].setAttributeValue(item.getKey(), aValue);
			}
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
		TableField[] j = getReportBaseUI().getVisibleFields();

		ArrayList<TableField> al = new ArrayList<TableField>();
		al.addAll(Arrays.asList(f));
		al.addAll(Arrays.asList(i));
		return (TableField[]) al.toArray(new TableField[0]);
	}
}

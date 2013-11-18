package nc.ui.zmpub.pub.report.buttonaction;

/**
 * ���뼶С�ƺϼ��¼�
 * @author guanyj1
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.report.CodeLevelSubtotalTool;
import nc.ui.pub.report.ReportBaseClass;
import nc.ui.trade.report.levelsubtotal.CodeLevelSubtotalDLG;
import nc.ui.trade.report.levelsubtotal.CodeLevelSubtotalDescription;
import nc.ui.zmpub.pub.report.ReportBaseUI;
import nc.vo.pub.report.CodeLevelResultSetContext;
import nc.vo.pub.rs.MemoryResultSet;
import nc.vo.pub.rs.MemoryResultSetMetaData;
import nc.vo.trade.report.IReportModelDataTypes;
import nc.vo.trade.report.TableField;

public class CodeLevelSubtotalAction extends AbstractActionHasDataAvailable {

	private static final String TOTAL_SIGN = "�����ϼơ���";

	public CodeLevelSubtotalAction(ReportBaseUI reportBaseUI) {
		super(reportBaseUI);
	}

	public CodeLevelSubtotalAction() {
		super();
	}

	public void execute() throws Exception {
		CodeLevelSubtotalDLG dlg = new CodeLevelSubtotalDLG(getReportBaseUI(),
				getAllCols(), getSumCols(), null);
		if (dlg.showModal() == UIDialog.ID_OK) {
			getReportBaseUI().setBodyDataForSub();
			CodeLevelSubtotalDescription desc = dlg.getResultDesc();
			CodeLevelResultSetContext clc = new CodeLevelResultSetContext();
			clc.setCols(make2Wei(getReportBaseUI().convertVOKeysToModelKeys(
					desc.getSumCols())));
			if (desc.getGroupCol() != null)
				clc.setGrpItem(getReportBaseUI()
						.convertVOFieldNameToReportModelFieldName(
								desc.getGroupCol()));
			if (desc.getFilterFormula() != null
					&& !desc.getFilterFormula().equals(""))
				clc.setFilter(desc.getFilterFormula());
			clc.setLevelCol(getReportBaseUI()
					.convertVOFieldNameToReportModelFieldName(
							desc.getLevelCol()));
			clc.setLevelMode(desc.getLevelMode());
			clc.setTopLevel(desc.getBeginLevel());
			clc.setBottomLevel(desc.getEndLevel());
			clc.setIsLevelDown(desc.isSumColUp());
			System.out.println(clc.getGrpItem());
			System.out.println(clc.getLevelCol());
			for (int i = 0; i < desc.getSumCols().length; i++)
				System.out.println(clc.getCols()[i]);
			codeLevelSubtotal(clc);
		}

	}

	private void codeLevelSubtotal(CodeLevelResultSetContext clc) {
		// �����ڴ�����Ԫ����
		MemoryResultSetMetaData mrsmd = getBaseClass().getReportGeneralUtil()
				.createMeteData();
		// �����ڴ�����
		Vector dataVec = getBaseClass().getBillModel().getDataVector();
		getBaseClass().getReportInfoCtrl().setMrs(
				getBaseClass().getReportGeneralUtil()
						.vector2Mrs(dataVec, mrsmd));

		try {
			CodeLevelSubtotalTool clst = new CodeLevelSubtotalTool();
			MemoryResultSet mrsTemp = clst.calcLevelSubtotal(getBaseClass()
					.getReportInfoCtrl().getMrs(), clc);
			dataVec = getBaseClass().getReportGeneralUtil().mrs2Vector(mrsTemp);
			// ȥ�������С�&&��
			int iLen = ((Vector) dataVec.get(0)).size();
			for (int i = 0; i < dataVec.size(); i++)
				((Vector) dataVec.get(i)).remove(iLen - 1);
			Vector vecToMod = null;
			if (clc.isLevelDown())
				vecToMod = (Vector) dataVec.get(dataVec.size() - 1);
			else
				vecToMod = (Vector) dataVec.get(0);
			for (int i = 0; i < iLen - 1; i++) {

				if (vecToMod.get(i).equals("@@")) {
					vecToMod.set(i, TOTAL_SIGN);
					break;
				}
			}
			// �����������
			getBaseClass().getBillModel().setDataVector(dataVec);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private ReportBaseClass getBaseClass() {
		return getReportBaseUI().getReportBase();
	}

	private String[][] make2Wei(String[] cols) {
		String[][] result = new String[cols.length][2];
		for (int i = 0; i < cols.length; i++) {
			result[i][0] = cols[i];
			result[i][1] = cols[i];
		}
		return result;
	}

	private TableField[] getAllCols() {
		return getReportBaseUI().getVisibleFields();
	}

	private TableField[] getSumCols() {
		TableField[] f = getReportBaseUI().getVisibleFieldsByDataType(
				IReportModelDataTypes.FLOAT);
		TableField[] i = getReportBaseUI().getVisibleFieldsByDataType(
				IReportModelDataTypes.INTEGER);
		ArrayList al = new ArrayList();
		al.addAll(Arrays.asList(f));
		al.addAll(Arrays.asList(i));
		return (TableField[]) al.toArray(new TableField[0]);
	}

}

/*
 * �������� 2005-9-2
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package nc.vo.zmpub.pub.report2;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import nc.ui.pub.beans.UICheckBox;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.dialog.StandardUIDialog;
import nc.ui.report.base.ISubTotalConf;
import nc.ui.trade.component.ListToListPanel;
import nc.vo.trade.report.TableField;

/**
 * @author dengjt modify mlr֧���Ƿ���ʾԭʼ�� �������� 2005-9-2
 */
public class ZmSubTotalConfDLG extends StandardUIDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7376433796357942322L;
	private ISubTotalConf istc = null;
	private ListToListPanel groupPanel = null;
	private ListToListPanel totalPanel = null;
	private UIPanel settingPane = null;
	private UICheckBox sumChooser = null;
	private UICheckBox subChooser = null;
	private UICheckBox levelChooser = null;

	public ZmSubTotalConfDLG(java.awt.Container parent, ISubTotalConf istc) {
		super(parent);
		this.istc = istc;
		initLayout();
	}

	/**
	 *
	 */
	private void initLayout() {
		setSize(650, 650);

		setErrorMsgSize(new Dimension(680, 500));

		setResizable(true);

		this.themePanel
				.setTheme(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"uifactory_report", "UPPuifactory_report-000156")/*
																		 * @res
																		 * "С�ƺϼ�����"
																		 */);
		this.themePanel
				.setDetailTheme(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"uifactory_report", "UPPuifactory_report-000157")/*
																		 * @res
																		 * "ѡ������ֶκ�ҪС�Ƶ��ֶ�,�Լ�����С�ƺϼ�ѡ��"
																		 */);

		Container contentPane = this.editorPanel;

		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(getGroupPanel());
		contentPane.add(getSettingPane());
		contentPane.add(getTotalPanel());
		// zjb+
		setTitle(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"uifactory_report", "UPPuifactory_report-000007")/* @res "�ּ�С��" */);
	}

	/**
	 * @return ���� groupPanel��
	 */
	private ListToListPanel getGroupPanel() {
		if (groupPanel == null) {
			TableField[] all = istc.getSubTotalCandidateGroupFields();
			TableField[] using = istc.getSubTotalCurrentUsingGroupFields();
			java.util.ArrayList al = new java.util.ArrayList();
			for (int i = 0; i < all.length; i++) {
				if (!java.util.Arrays.asList(using).contains(all[i]))
					al.add(all[i]);
			}
			TableField[] nousing = (TableField[]) al.toArray(new TableField[0]);
			groupPanel = new ListToListPanel(
					nc.ui.ml.NCLangRes.getInstance().getStrByID(
							"uifactory_report", "UPPuifactory_report-000158")/*
																			 * @res
																			 * "��ѡ�����ֶ�"
																			 */,
					nousing,
					nc.ui.ml.NCLangRes.getInstance().getStrByID(
							"uifactory_report", "UPPuifactory_report-000159")/*
																			 * @res
																			 * "��ѡ�����ֶ�"
																			 */,
					using);

		}
		return groupPanel;
	}

	/**
	 * @return ���� totalPanel��
	 */
	private ListToListPanel getTotalPanel() {
		if (totalPanel == null) {
			TableField[] all = istc.getSubTotalCandidateTotalFields();
			TableField[] using = istc.getSubTotalCurrentUsingTotalFields();
			java.util.ArrayList al = new java.util.ArrayList();
			for (int i = 0; i < all.length; i++) {
				if (!java.util.Arrays.asList(using).contains(all[i]))
					al.add(all[i]);
			}
			TableField[] nousing = (TableField[]) al.toArray(new TableField[0]);
			totalPanel = new ListToListPanel(
					nc.ui.ml.NCLangRes.getInstance().getStrByID(
							"uifactory_report", "UPPuifactory_report-000160")/*
																			 * @res
																			 * "��ѡ�����ֶ�"
																			 */,
					nousing,
					nc.ui.ml.NCLangRes.getInstance().getStrByID(
							"uifactory_report", "UPPuifactory_report-000161")/*
																			 * @res
																			 * "��ѡ�����ֶ�"
																			 */,
					using, null, true);
		}
		return totalPanel;
	}

	/**
	 * @return ���� settingPane��
	 */
	private UIPanel getSettingPane() {
		if (settingPane == null) {
			settingPane = new UIPanel();
			settingPane
					.setBorder(BorderFactory
							.createTitledBorder(nc.ui.ml.NCLangRes
									.getInstance().getStrByID(
											"uifactory_report",
											"UPPuifactory_report-000162")/*
																		 * @res
																		 * "С�ƺϼ�ѡ��"
																		 */));
			settingPane.add(getSubChooser());
			settingPane.add(getSumChooser());
			settingPane.add(getLevelChooser());
		}
		return settingPane;
	}

	/**
	 * @return ���� subChooser��
	 */
	protected UICheckBox getSubChooser() {
		if (subChooser == null) {
			subChooser = new UICheckBox();
			subChooser.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"uifactory_report", "UPPuifactory_report-000142")/*
																	 * @res
																	 * "����С��"
																	 */);
			subChooser.setSelected(true);
		}
		return subChooser;
	}

	/**
	 * @return ���� sumChooser��
	 */
	private UICheckBox getSumChooser() {
		if (sumChooser == null) {
			sumChooser = new UICheckBox();
			sumChooser.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"uifactory_report", "UPPuifactory_report-000145")/*
																	 * @res
																	 * "����ϼ�"
																	 */);
			sumChooser.setSelected(true);
		}
		return sumChooser;
	}

	/**
	 * @return ���� levelChooser��
	 */
	private UICheckBox getLevelChooser() {
		if (levelChooser == null) {
			levelChooser = new UICheckBox();
			levelChooser.setText("��ʾԭʼ��");
			levelChooser.setSelected(true);
		}
		return levelChooser;
	}

	/**
	 * �÷����õ�SQL�����Group���ֶ� ���������е�˳����Group��˳��
	 */
	public TableField[] getGroupFields() {
		TableField[] fs = new TableField[getGroupPanel().getRightData().length];
		for (int i = 0; i < fs.length; i++) {
			fs[i] = (TableField) getGroupPanel().getRightData()[i];
		}
		return fs;
	}

	/**
	 * �÷����õ����еĻ����ֶΣ� ��һ����˵������ֵ���͵��ֶΣ� �Լ���Ҫ����Щ�ֶν��еĲ�����sum or avg
	 * 
	 * @return nc.ui.report.base.TotalField[]
	 */
	public TableField[] getTotalFields() {
		TableField[] fs = new TableField[getTotalPanel().getRightData().length];
		for (int i = 0; i < fs.length; i++) {
			fs[i] = (TableField) getTotalPanel().getRightData()[i];
		}
		return fs;
	}

	/**
	 * �÷����õ����еĻ����ֶΣ� ��һ����˵������ֵ���͵��ֶΣ� �Լ���Ҫ����Щ�ֶν��еĲ�����sum or avg
	 * 
	 * @return nc.ui.report.base.TotalField[]
	 */
	public TableField[] getTotalFields2() {
		TableField[] fs = new TableField[getTotalPanel().getLeftData().length];
		for (int i = 0; i < fs.length; i++) {
			fs[i] = (TableField) getTotalPanel().getLeftData()[i];
		}
		return fs;
	}

	/**
	 * �÷����õ�SQL�����Group���ֶ� ���������е�˳����Group��˳��
	 */
	public TableField[] getGroupFields2() {
		TableField[] fs = new TableField[getGroupPanel().getLeftData().length];
		for (int i = 0; i < fs.length; i++) {
			fs[i] = (TableField) getGroupPanel().getLeftData()[i];
		}
		return fs;
	}

	public boolean isSum() {
		return getSumChooser().isSelected();
	}

	public boolean isSub() {
		return getSubChooser().isSelected();
	}

	public boolean isLevelCompute() {
		return getLevelChooser().isSelected();
	}

	@Override
	protected void complete() throws Exception {
	}
}
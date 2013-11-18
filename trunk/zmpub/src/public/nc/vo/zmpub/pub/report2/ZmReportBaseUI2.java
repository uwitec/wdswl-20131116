package nc.vo.zmpub.pub.report2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.zmpub.pub.report.buttonaction2.CaPuBtnConst;
import nc.ui.zmpub.pub.report.buttonaction2.CrossAction;
import nc.ui.zmpub.pub.report.buttonaction2.IReportButton;
import nc.ui.zmpub.pub.report.buttonaction2.LevelSubTotalAction;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.zmpub.pub.report.ReportBaseVO;

/**
 * ֧�ַ����ѯ�ı������ ֧�����ݽ��� �ּ��ε�С�� �ϼ� ���� ���� ˢ�� �Լ���ӡ ֧�ֲ�ѯ��̬�� �� ��ʼ����̬��
 * 
 * @author mlr
 * @mod mlr ���ӱ������û��� ֧�ֽ����εı������� ���б��� �´β�ѯʱ �Զ���ȡ���� ���б�����������
 * 
 */
public class ZmReportBaseUI2 extends JxReportBaseUI {
	// for add mlr ���ӱ������ñ��水ť
	private ReportBuffer buff = null;// �������û���
	

	public ReportBuffer getBuff() {
		if (buff == null) {
			buff = queryBuffer(this._getModelCode());
		}
		if (buff == null) {
			buff = new ReportBuffer();
			buff.setNodecode(_getModelCode());
		}
		return buff;
	}

	/**
	 * ���ݽڵ�Ŵ����ݿ��в�ѯ����
	 * 
	 * @param modelCode
	 * @return
	 */
	public ReportBuffer queryBuffer(String modelCode) {
		ReportBuffer buffer = null;
		try {
			Class[] ParameterTypes = new Class[] { String.class };
			Object[] ParameterValues = new Object[] { modelCode };
			Object o = LongTimeTask.calllongTimeService("zmpub", this,
					"���ڲ�ѯ...", 1, "nc.bs.zmpub.pub.report.BufferDMO", null,
					"queryByNodeId", ParameterTypes, ParameterValues);
			if (o != null) {
				buffer = (ReportBuffer) o;
			}
		} catch (Exception e) {
			Logger.error(e);
			MessageDialog.showErrorDlg(this, "����", e.getMessage());
		}
		return buffer;
	}

	public int[] getReportButtonAry() {
		m_buttonArray = new int[] { IReportButton.QueryBtn,
				IReportButton.LevelSubTotalBtn, IReportButton.CrossBtn,
				IReportButton.PrintBtn, CaPuBtnConst.onboRefresh,
				CaPuBtnConst.save, };
		return m_buttonArray;
	}

	/**
	 * ���������ø��µ����ݿ�
	 * 
	 * @param modelCode
	 * @return
	 */
	public ReportBuffer updateBuffer(ReportBuffer buff) {
		ReportBuffer buffer = null;
		try {
			Class[] ParameterTypes = new Class[] { ReportBuffer.class };
			Object[] ParameterValues = new Object[] { buff };
			Object o = LongTimeTask.calllongTimeService("zmpub", this,
					"���ڲ�ѯ...", 1, "nc.bs.zmpub.pub.report.BufferDMO", null,
					"updateBuffer", ParameterTypes, ParameterValues);
			if (o != null) {
				buffer = (ReportBuffer) o;
			}
			this.showHintMessage("���ñ���ɹ�");
		} catch (Exception e) {
			Logger.error(e);
			MessageDialog.showErrorDlg(this, "����", e.getMessage());
		}
		return buffer;
	}

	public void setBuff(ReportBuffer buff) {
		this.buff = buff;
	}

	public ZmReportBaseUI2() {
		super();
		// getBuff();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2436203500270302801L;

	private String[] sqls = null;

	@Override
	public Map getNewItems() throws Exception {
		return null;
	}

	@Override
	public String getQuerySQL() throws Exception {
		return super.getQueryConditon();
	}

	protected void initPrivateButton() {

		addPrivateButton("��������", "��������", CaPuBtnConst.save);
		super.initPrivateButton();
	}

	/**
     * 
     */
	protected void onBoElse(Integer intBtn) throws Exception {
		switch (intBtn) {
		case CaPuBtnConst.save: {
			onBoSave();
			break;
		}
		}
		super.onBoElse(intBtn);
	}

	/**
	 * ���������ð�ť
	 */
	public void onBoSave() {
		if (getBuff() == null) {
			return;
		} else {
			updateBuffer(getBuff());
		}
	}

	@Override
	public void initReportUI() {

	}

	@Override
	public void onQuery() {
		getQueryDlg().showModal();
		if (getQueryDlg().getResult() == UIDialog.ID_OK) {
			try {
				// ��ձ�������
				clearBody();
				setDynamicColumn1();
				// �õ���ѯ���
				setColumn();

				List<ReportBaseVO[]> list = getReportVO(getSqls());
				sqls = getSqls();
				ReportBaseVO[] vos = null;
				vos = dealBeforeSetUI(list);
				if (vos == null || vos.length == 0)
					return;
				if (vos != null) {
					super.updateBodyDigits();
					setBodyVO(vos);
					updateVOFromModel();// ���¼��س�ʼ����ʽ
					dealQueryAfter();// ��ѯ��ĺ������� һ������ Ĭ�����ݽ���֮��Ĳ���
				}
			} catch (BusinessException e) {
				e.printStackTrace();
				this.showErrorMessage(e.getMessage());

			} catch (Exception e) {
				e.printStackTrace();
				this.showErrorMessage(e.getMessage());

			}
		}
	}

	/**
	 * ���ղ�ѯ�����sql
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-22����10:41:05
	 * @return
	 */
	public String[] getSqls() throws Exception {
		return null;

	}

	public void onboRefresh() throws Exception {
		// ��ձ�������
		clearBody();
		// ���ö�̬��
		setDynamicColumn1();
		// �õ���ѯ���
		ReportBaseVO[] vos = null;
		// ���û����кϲ�
		setColumn();
		// ����vo
		List<ReportBaseVO[]> list = getReportVO(getSqls());
		sqls = getSqls();
		ReportBaseVO[] vos1 = null;
		vos1 = dealBeforeSetUI(list);
		if (vos1 == null || vos1.length == 0)
			return;
		if (vos1 != null) {
			super.updateBodyDigits();
			setBodyVO(vos1);
			updateVOFromModel();// ���¼��س�ʼ����ʽ
			dealQueryAfter();// ��ѯ��ĺ������� һ������ Ĭ�����ݽ���֮��Ĳ���
		}
		this.showHintMessage("ˢ�²�������");
	}

	/**
	 * ���õ�ui����֮ǰ ��������ѯ�������
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-22����10:42:36
	 * @param list
	 * @return
	 */
	public ReportBaseVO[] dealBeforeSetUI(List<ReportBaseVO[]> list)
			throws Exception {
		if (list == null || list.size() == 0) {
			return null;
		}
		List<ReportBaseVO> zlist = new ArrayList<ReportBaseVO>();
		for (int i = 0; i < list.size(); i++) {
			ReportBaseVO[] vos = list.get(i);
			if (vos == null || vos.length == 0)
				continue;
			for (int j = 0; j < vos.length; j++) {
				zlist.add(vos[j]);
			}

		}
		return zlist.toArray(new ReportBaseVO[0]);
	}

	/**
	 * ��ѯ��� ���õ�ui����֮�� ��������
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-22����10:42:36
	 * @param list
	 * @return
	 */
	public void dealQueryAfter() throws Exception {
		ReportBuffer buff = getBuff();
		if (buff == null) {
			return;
		}
		String[] rows = buff.getStrRows();
		String[] cols = buff.getStrCols();
		String[] vals = buff.getStrVals();
		UFBoolean istotal = buff.getIstotal();
		Integer lel = buff.getLel();
		/**
		 * ִ�����ݽ���
		 */
		if (rows != null && cols != null && vals != null && rows.length != 0
				&& cols.length != 0 && vals.length != 0) {
			nc.ui.pub.ButtonObject bo = (ButtonObject) getButton_action_map()
					.getButtonMap().get(IReportButton.CrossBtn);
			CrossAction action = (CrossAction) getButton_action_map().get(bo);
			if (istotal == null)
				istotal = UFBoolean.FALSE;
			action.execute2(rows, cols, vals, istotal, lel);

		}
		/**
		 * ִ�кϼ�
		 */
		UFBoolean issub = buff.getIssub();
		UFBoolean issum = buff.getIssum();
		String[] totalfields = buff.getTotfields();
		String[] totalfieldsName = buff.getTotfieldsNames();
		if (totalfields != null && totalfieldsName != null
				&& totalfields.length != 0 && totalfieldsName.length != 0) {
			nc.ui.pub.ButtonObject bo = (ButtonObject) getButton_action_map()
					.getButtonMap().get(IReportButton.LevelSubTotalBtn);
			LevelSubTotalAction action = (LevelSubTotalAction) getButton_action_map()
					.get(bo);
			if (istotal == null)
				istotal = UFBoolean.FALSE;
			action.atuoexecute2(PuPubVO.getUFBoolean_NullAs(issub,
					UFBoolean.FALSE).booleanValue(),
					PuPubVO.getUFBoolean_NullAs(issum, UFBoolean.FALSE)
							.booleanValue(), totalfields, totalfieldsName);
		}
		return;
	}

	/**
	 * �����ѯ
	 */
	public List<ReportBaseVO[]> getReportVO(String[] sqls)
			throws BusinessException {
		List<ReportBaseVO[]> reportVOs = null;
		try {
			Class[] ParameterTypes = new Class[] { String[].class };
			Object[] ParameterValues = new Object[] { sqls };
			Object o = LongTimeTask.calllongTimeService("zmpub", this,
					"���ڲ�ѯ...", 1, "nc.bs.zmpub.pub.report.ReportDMO", null,
					"queryVOBySql", ParameterTypes, ParameterValues);
			if (o != null) {
				reportVOs = (List<ReportBaseVO[]>) o;
			}
		} catch (Exception e) {
			Logger.error(e);
			MessageDialog.showErrorDlg(this, "����", e.getMessage());
		}
		return reportVOs;
	}

}

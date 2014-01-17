package nc.ui.dm;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.ToftPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.wl.pub.LoginInforHelper;
import nc.vo.ic.pub.StockInvOnHandVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.session.ClientLink;
import nc.vo.wl.pub.WdsWlPubConst;

/**
 * ���˼ƻ�����
 * @author mlr
 */
public class PlanDealClientUI extends ToftPanel implements BillEditListener,
		BillEditListener2 {
	private static final long serialVersionUID = 1L;
	// ���尴ť
	private ButtonObject m_btnQry = new ButtonObject(
			WdsWlPubConst.DM_PLANDEAL_BTNTAG_QRY,
			WdsWlPubConst.DM_PLANDEAL_BTNTAG_QRY, 2,
			WdsWlPubConst.DM_PLANDEAL_BTNTAG_QRY);
	private ButtonObject m_btnDeal = new ButtonObject(
			WdsWlPubConst.DM_PLANDEAL_BTNTAG_DEAL,
			WdsWlPubConst.DM_PLANDEAL_BTNTAG_DEAL, 2,
			WdsWlPubConst.DM_PLANDEAL_BTNTAG_DEAL);
	private ButtonObject m_btnSelAll = new ButtonObject(
			WdsWlPubConst.DM_PLANDEAL_BTNTAG_SELALL,
			WdsWlPubConst.DM_PLANDEAL_BTNTAG_SELALL, 2,
			WdsWlPubConst.DM_PLANDEAL_BTNTAG_SELALL);
	private ButtonObject m_btnSelno = new ButtonObject(
			WdsWlPubConst.DM_PLANDEAL_BTNTAG_SELNO,
			WdsWlPubConst.DM_PLANDEAL_BTNTAG_SELNO, 2,
			WdsWlPubConst.DM_PLANDEAL_BTNTAG_SELNO);
	protected ClientEnvironment m_ce = null;
	protected ClientLink cl = null;
	private PlanDealEventHandler m_handler = null;
	// �������ģ��
	private BillListPanel m_panel = null;
	// ��ť�¼�����

	private LoginInforHelper helper = null;

	public LoginInforHelper getLoginInforHelper() {
		if (helper == null) {
			
			helper = new LoginInforHelper();
		}
		return helper;
	}

	public PlanDealClientUI() {
		super();
		m_ce = ClientEnvironment.getInstance();
		cl = new ClientLink(m_ce);
		init();
	}

	protected BillListPanel getPanel() {
		if (m_panel == null) {
			m_panel = new BillListPanel();
			m_panel.loadTemplet(WdsWlPubConst.DM_PLAN_DEAL_BILLTYPE, null, m_ce
					.getUser().getPrimaryKey(), m_ce.getCorporation()
					.getPrimaryKey());
			m_panel.setEnabled(true);
			m_panel.getParentListPanel().setTotalRowShow(true);
			m_panel.setMultiSelect(true);
			//add  by zhw ���Ӻϼ���
			m_panel.getChildListPanel().setTatolRowShow(true);
			m_panel.getBodyTable().removeSortListener();
		}
		return m_panel;
	}

	public ClientEnvironment getEviment() {
		return m_ce;
	}

	private void init() {
		setLayout(new java.awt.CardLayout());
		add(getPanel(), "a");

		createEventHandler();

		setButton();

		initListener();
	}

	private void initListener() {
		// ����༭ǰ�����
		getPanel().addBodyEditListener(this);
		getPanel().getBodyScrollPane("wds_sendplanin_b").addEditListener2(this);
	}

	private void setButton() {
		ButtonObject[] m_objs = new ButtonObject[] { m_btnSelAll, m_btnSelno,
				m_btnQry, m_btnDeal };
		this.setButtons(m_objs);
	}

	private void createEventHandler() {
		m_handler = new PlanDealEventHandler(this);
	}
	@Override
	public String getTitle() {
		return null;
	}
	@Override
	public void onButtonClicked(ButtonObject btn) {
		m_handler.onButtonClicked(btn.getCode());
	}

	public void updateButtonStatus(String btnTag, boolean flag) {
		if (btnTag.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_DEAL)) {
			m_btnDeal.setEnabled(flag);
		} else if (btnTag
				.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_SELNO)) {
			m_btnSelno.setEnabled(flag);
		} else if (btnTag
				.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_SELALL)) {
			m_btnSelAll.setEnabled(flag);
		} else if (btnTag
				.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_QRY)) {
			m_btnQry.setEnabled(flag);
		} else if (btnTag
				.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_XNDEAL)) {
			// m_btnXnDeal.setEnabled(flag);
		}
		updateButtons();
	}

	public void afterEdit(BillEditEvent e) {

		String key = e.getKey();// ���������븺��
		String value = PuPubVO.getString_TrimZeroLenAsNull(e.getValue());
		int row = e.getRow();
		if ("nassnum".equalsIgnoreCase(key)) {
			UFDouble num = PuPubVO.getUFDouble_NullAsZero(getPanel()
					.getBodyBillModel().getValueAt(row, "nassnum"));
			if (num.doubleValue() < 0) {
				showWarningMessage("�������Ÿ���");
				getPanel().getBodyBillModel().setValueAt(e.getOldValue(),
						e.getRow(), key);
				return;
			}
			if(num.doubleValue() >0){
		        getPanel().getBodyTable().addRowSelectionInterval(row, row);
			}else{
		        getPanel().getBodyTable().removeRowSelectionInterval(row, row);

			}
//			// ���Ÿ����� �༭�� ���� for add mlr
//			UFDouble oldvalue = e.getOldValue() == null ? new UFDouble(0)
//					: (UFDouble) e.getOldValue();
//			if (num == null || num.doubleValue() == 0
//					|| num.doubleValue() > oldvalue.doubleValue()) {
//				MessageDialog.showHintDlg(getPanel(), "����",
//						"�������ֵ����,�����֮ǰ��ֵҪС!");
//				getPanel().getBodyBillModel().setValueAt(oldvalue, row,
//						"nassnum");
//				getPanel().getBodyBillModel().execEditFormulasByKey(row,
//						"nassnum");
//				return;
//			}
//			String tablecode = getPanel().getChildListPanel().getTableCode();
//			getPanel().getBodyScrollPane(tablecode).copyLine();
//			getPanel().getBodyScrollPane(tablecode).pasteLine();
//			getPanel().getBodyBillModel().setValueAt(oldvalue.sub(num), row,
//					"nassnum");
//			getPanel().getBodyBillModel().execEditFormulasByKey(row, "nassnum");
		}
		if ("ss_state".equalsIgnoreCase(key)) {
			if (value == null) {
				getPanel().getBodyBillModel().setValueAt(null, row,
						"nstorenumout");// ���������
				getPanel().getBodyBillModel().setValueAt(null, row,
						"anstorenumout");// ��渨����

			}
			String pk_corp = ClientEnvironment.getInstance().getCorporation()
					.getPrimaryKey();
			String pk_strodoc = null;
			try {
				pk_strodoc = PuPubVO
						.getString_TrimZeroLenAsNull(getLoginInforHelper()
								.getLogInfor(m_ce.getUser().getPrimaryKey())
								.getWhid());
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			String pk_invmandoc = PuPubVO
					.getString_TrimZeroLenAsNull(getPanel().getBodyBillModel()
							.getValueAt(row, "pk_invmandoc"));
			String pk_invbasdoc = PuPubVO
					.getString_TrimZeroLenAsNull(getPanel().getBodyBillModel()
							.getValueAt(row, "pk_invbasdoc"));
			String pk_ss = PuPubVO.getString_TrimZeroLenAsNull(getPanel()
					.getBodyBillModel().getValueAt(row, "vdef1"));
			if (pk_corp == null || pk_strodoc == null || pk_invmandoc == null
					|| pk_invbasdoc == null || pk_ss == null) {
				return;
			}
			StockInvOnHandVO vo = new StockInvOnHandVO();
			vo.setPk_corp(pk_corp);
			vo.setPk_customize1(pk_strodoc);
			vo.setPk_invmandoc(pk_invmandoc);
			vo.setPk_invbasdoc(pk_invbasdoc);
			vo.setSs_pk(pk_ss);
			StockInvOnHandVO[] vos = null;// �ִ���
			StockInvOnHandVO[] vos1 = null;// ������
			try {
				vos = (StockInvOnHandVO[]) m_handler.getStock()
						.queryStockCombinForClient(
								new StockInvOnHandVO[] { vo });
				vos1 = (StockInvOnHandVO[]) m_handler.getAbo()
						.getAvailNumForClient(
								new StockInvOnHandVO[] { vo });
			} catch (Exception e1) {
				getPanel().getBodyBillModel().setValueAt(null, row,
						"nstorenumout");// ���������
				getPanel().getBodyBillModel().setValueAt(null, row,
						"anstorenumout");// ��渨����
				getPanel().getBodyBillModel().setValueAt(null, row,
						"ndrqarrstorenumout");// ����������
				getPanel().getBodyBillModel().setValueAt(null, row,
						"ndrqstorenumout");// ���ø�����
				e1.printStackTrace();
				showErrorMessage("��ȡ�ִ���ʧ��");
			}
			if (vos == null || vos.length == 0 || vos[0] == null) {
				getPanel().getBodyBillModel().setValueAt(null, row,
						"nstorenumout");// ���������
				getPanel().getBodyBillModel().setValueAt(null, row,
						"anstorenumout");// ��渨����
				getPanel().getBodyBillModel().setValueAt(null, row,
						"ndrqarrstorenumout");// ����������
				getPanel().getBodyBillModel().setValueAt(null, row,
						"ndrqstorenumout");// ���ø�����
				return;
			}
			getPanel().getBodyBillModel().setValueAt(
					vos[0].getWhs_stocktonnage(), row, "nstorenumout");// ���������
			getPanel().getBodyBillModel().setValueAt(
					vos[0].getWhs_stockpieces(), row, "anstorenumout");// ��渨����
			if (vos1 == null || vos1.length == 0 || vos1[0] == null) {
				getPanel().getBodyBillModel().setValueAt(null, row,
						"ndrqarrstorenumout");// ����������
				getPanel().getBodyBillModel().setValueAt(null, row,
						"ndrqstorenumout");// ���ø�����
				return;
			} else {
				//ndrqarrstorenumout
				//ndrqstorenumout
				getPanel().getBodyBillModel().setValueAt(
						vos1[0].getWhs_stocktonnage(), row, "ndrqarrstorenumout");// ����������
				getPanel().getBodyBillModel()
						.setValueAt(vos1[0].getWhs_stockpieces(), row,
								"ndrqstorenumout");// ���ø�����
			}
			
			
			//�޸��ˣ����� �޸�ʱ�䣺2013.12.5 �޸�ԭ�� �����ջ�վ�������
			setInNum(key,value,row);
			
		}

		// m_handler.afterEdit(e);
	}
	/**
	 * 2013.12.5 �����ջ�վ������� �޸��ˣ�����
	 */
	private void setInNum(String key,String value,int row)
	{
		//�ٵ��ջ�վ�����Ϊnullʱ �ı�״̬�� ֱ������Ϊnull
		if (value == null) {
			getPanel().getBodyBillModel().setValueAt(null, row,
					"nstorenumin");// �ջ�վ���������
			getPanel().getBodyBillModel().setValueAt(null, row,
					"anstorenumin");// �ջ�վ��渨����
		}
		//��
		String pk_corp = ClientEnvironment.getInstance().getCorporation()
				.getPrimaryKey();
		String pk_strodoc = null;
		pk_strodoc = PuPubVO
			.getString_TrimZeroLenAsNull(getPanel().getBodyBillModel()
					.getValueAt(row, "pk_inwhouse"));

		String pk_invmandoc = PuPubVO
				.getString_TrimZeroLenAsNull(getPanel().getBodyBillModel()
						.getValueAt(row, "pk_invmandoc"));
		String pk_invbasdoc = PuPubVO
				.getString_TrimZeroLenAsNull(getPanel().getBodyBillModel()
						.getValueAt(row, "pk_invbasdoc"));
		String pk_ss = PuPubVO.getString_TrimZeroLenAsNull(getPanel()
				.getBodyBillModel().getValueAt(row, "vdef1"));
		//�����ջ�վ������
		StockInvOnHandVO vo = new StockInvOnHandVO();
		vo.setPk_corp(pk_corp);
		vo.setPk_customize1(pk_strodoc);
		vo.setPk_invmandoc(pk_invmandoc);
		vo.setPk_invbasdoc(pk_invbasdoc);
		vo.setSs_pk(pk_ss);
		
		StockInvOnHandVO[] vos = null;// �ջ�վ��������
		try {
			vos = (StockInvOnHandVO[]) m_handler.getAbo()
			.getAvailNumForClient(
					new StockInvOnHandVO[] { vo });
		} catch (Exception e) {
			getPanel().getBodyBillModel().setValueAt(null, row,
			"nstorenumin");// �ջ�վ������������
			getPanel().getBodyBillModel().setValueAt(null, row,
			"anstorenumin");// �ջ�վ�����ø�����
			e.printStackTrace();
			showErrorMessage("��ȡ�ջ�վ��������ʧ��");
		}
		
		if (vos == null || vos.length == 0 || vos[0] == null) {
			getPanel().getBodyBillModel().setValueAt(null, row,
					"nstorenumin");// �ջ�վ������������
			getPanel().getBodyBillModel().setValueAt(null, row,
					"anstorenumin");// �ջ�վ�����ø�����
			return;
		}
		getPanel().getBodyBillModel().setValueAt(
				vos[0].getWhs_stocktonnage(), row, "nstorenumin");//�ջ�վ������������
		getPanel().getBodyBillModel().setValueAt(
				vos[0].getWhs_stockpieces(), row, "anstorenumin");//�ջ�վ�����ø�����
	}
	
	
	
	public void bodyRowChange(BillEditEvent e) {

	}

	public String getHslFieldName() {
		return "hsl";
	};// ��ȡ�������ֶ���

	public String getAssNumFieldName() {
		return "nassnum";
	};// ��ȡ����¥�ֶ���

	public boolean beforeEdit(BillEditEvent e) {
		if (PuPubVO.getString_TrimZeroLenAsNull(getHslFieldName()) != null
				&& PuPubVO.getString_TrimZeroLenAsNull(getAssNumFieldName()) != null) {
			if (e.getKey().equalsIgnoreCase(getAssNumFieldName())) {// ��������
																	// ������Ϊ0
																	// ���������ɱ༭
				UFDouble hsl = PuPubVO.getUFDouble_NullAsZero(getPanel()
						.getBodyBillModel().getValueAt(e.getRow(),
								getHslFieldName()));
				if (hsl.equals(WdsWlPubConst.ufdouble_zero)) {
					return false;
				}
			}
		}
		return true;
	}
}

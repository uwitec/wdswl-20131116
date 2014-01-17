package nc.ui.dm.so.deal2;

import javax.swing.JComponent;

import nc.bs.logging.Logger;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.ToftPanel;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.IBillModelRowStateChangeEventListener;
import nc.ui.pub.bill.RowStateChangeEvent;
import nc.ui.wl.pub.LoginInforHelper;
import nc.vo.ic.pub.StockInvOnHandVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.session.ClientLink;
import nc.vo.wl.pub.LoginInforVO;
import nc.vo.wl.pub.WdsWlPubConst;

/**
 * ���ۼƻ�����2
 * 
 * @author Administrator
 * 
 */
public class SoDealClientUI extends ToftPanel implements BillEditListener,BillEditListener2 {

	/**
	 * 
	 */
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
	//	private ButtonObject m_btnXnDeal = new ButtonObject(
	//			WdsWlPubConst.DM_PLANDEAL_BTNTAG_XNDEAL,
	//			WdsWlPubConst.DM_PLANDEAL_BTNTAG_XNDEAL, 2,
	//			WdsWlPubConst.DM_PLANDEAL_BTNTAG_XNDEAL);

	protected ClientEnvironment m_ce = null;
	protected ClientLink cl = null;
	private SoDealEventHandler m_handler = null;
	private String cwhid;//��ǰ��¼�ͻ������ֿ�
	public String getWhid(){
		String cc=null;
		try {
			cc= new LoginInforHelper().getLogInfor(m_ce.getUser().getPrimaryKey()).getWhid();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cc;
	}
	// �������ģ��
	private BillListPanel m_panel = null;
	// ��ť�¼�����
	private LoginInforHelper helper = null;
	public LoginInforHelper getLoginInforHelper() {
		helper=null;
		if (helper == null) {
			helper = new LoginInforHelper();
		}
		return helper;
	}

	public SoDealClientUI() {
		super();
		m_ce = ClientEnvironment.getInstance();
		cl = new ClientLink(m_ce);
		init();
	}

	protected BillListPanel getPanel() {
		if (m_panel == null) {
			m_panel = new BillListPanel();
			m_panel.loadTemplet(WdsWlPubConst.WDS4_2, null, m_ce.getUser()
					.getPrimaryKey(), m_ce.getCorporation().getPrimaryKey());
			m_panel.setEnabled(true);
//			m_panel.getParentListPanel().setTotalRowShow(true);
			m_panel.getChildListPanel().setTotalRowShow(true);
			m_panel.setMultiSelect(true);
//			m_panel.getHeadTable().removeSortListener();
		}
		return m_panel;
	}

	private void init() {
		setLayout(new java.awt.CardLayout());
		add(getPanel(), "a");
		createEventHandler();
		setButton();
		initListener();
		try {
			cwhid  = new LoginInforHelper().getLogInfor(m_ce.getUser().getPrimaryKey()).getWhid();
		} catch (Exception e) {
			e.printStackTrace();
			cwhid = null;
		}
	}

	private void initListener() {
		//��ͷ����
		getPanel().addEditListener(this);
		getPanel().getHeadBillModel().addRowStateChangeEventListener(new HeadRowStateListener());
		//�������
		BodyEditListener bodyEditListener = new BodyEditListener(); 
		getPanel().getChildListPanel().addEditListener(bodyEditListener);
		getPanel().getChildListPanel().addEditListener2(bodyEditListener);

	}
	/**
	 * lyf:����༭����
	 * @author
	 *
	 */
	private class BodyEditListener  implements BillEditListener,BillEditListener2{
		public void afterEdit(BillEditEvent e) {
			String key = e.getKey();
			Object value=e.getValue();
			int row =e.getRow();
			 LoginInforHelper log = new LoginInforHelper();
				if ("nassnum".equalsIgnoreCase(key)) {
					UFDouble num = PuPubVO.getUFDouble_NullAsZero(getPanel()
							.getBodyBillModel().getValueAt(row, "nassnum"));
					if (num.doubleValue() < 0) {
						showWarningMessage("�������Ÿ���");
						getPanel().getBodyBillModel().setValueAt(e.getOldValue(),
								e.getRow(), key);
						return;
					}
					// ���Ÿ����� �༭�� ���� for add mlr
					UFDouble oldvalue = e.getOldValue() == null ? new UFDouble(0)
							: (UFDouble) e.getOldValue();
					if (num == null || num.doubleValue() == 0
							|| num.doubleValue() > oldvalue.doubleValue()) {
						MessageDialog.showHintDlg(getPanel(), "����",
								"�������ֵ����,�����֮ǰ��ֵҪС!");
						getPanel().getBodyBillModel().setValueAt(oldvalue, row,
								"nassnum");
						getPanel().getBodyBillModel().execEditFormulasByKey(row,
								"nassnum");
						return;
					}
					String tablecode = getPanel().getChildListPanel()
							.getTableCode();
					getPanel().getBodyScrollPane(tablecode).copyLine();
					getPanel().getBodyScrollPane(tablecode).pasteLine();
					getPanel().getBodyBillModel().setValueAt(oldvalue.sub(num),
							row, "nassnum");
					getPanel().getBodyBillModel().execEditFormulasByKey(row,
							"nassnum");
				}
		
			if ("ss_state".equalsIgnoreCase(key)) {
				if (value == null) {
					getPanel().getBodyBillModel().setValueAt(null, row,
							"nstorenumout");// ���������
					getPanel().getBodyBillModel().setValueAt(null, row,
							"anstorenumout");// ��渨����

				}
				String pk_corp = ClientEnvironment.getInstance()
						.getCorporation().getPrimaryKey();
				String pk_strodoc = null;
				try {
					pk_strodoc = PuPubVO.getString_TrimZeroLenAsNull(log
							.getLogInfor(m_ce.getUser().getPrimaryKey())
							.getWhid());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				String pk_invmandoc = PuPubVO
						.getString_TrimZeroLenAsNull(getPanel()
								.getBodyBillModel().getValueAt(row,
										"cinventoryid"));
				String pk_invbasdoc = PuPubVO
						.getString_TrimZeroLenAsNull(getPanel()
								.getBodyBillModel().getValueAt(row,
										"cinvbasdocid"));
				String pk_ss = PuPubVO.getString_TrimZeroLenAsNull(getPanel()
						.getBodyBillModel().getValueAt(row, "vdef1"));
				if (pk_corp == null || pk_strodoc == null
						|| pk_invmandoc == null || pk_invbasdoc == null
						|| pk_ss == null) {
					return;
				}
				StockInvOnHandVO vo = new StockInvOnHandVO();
				vo.setPk_corp(pk_corp);
				vo.setPk_customize1(pk_strodoc);
				vo.setPk_invmandoc(pk_invmandoc);
				vo.setPk_invbasdoc(pk_invbasdoc);
				vo.setSs_pk(pk_ss);
				StockInvOnHandVO[] vos = null;
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
							"ndrqstorenumout");// ����������
					getPanel().getBodyBillModel().setValueAt(null, row,
							"ndrqarrstorenumout");// ���ø�����
					e1.printStackTrace();
					showErrorMessage("��ȡ�ִ���ʧ��");
				}
				if (vos == null || vos.length == 0 || vos[0] == null) {
					getPanel().getBodyBillModel().setValueAt(null, row,
							"nstorenumout");// ���������
					getPanel().getBodyBillModel().setValueAt(null, row,
							"anstorenumout");// ��渨����
					getPanel().getBodyBillModel().setValueAt(null, row,
							"ndrqstorenumout");// ����������
					getPanel().getBodyBillModel().setValueAt(null, row,
							"ndrqarrstorenumout");// ���ø�����
					return;
				}

				getPanel().getBodyBillModel().setValueAt(
						vos[0].getWhs_stocktonnage(), row, "nstorenumout");// ���������
				getPanel().getBodyBillModel().setValueAt(
						vos[0].getWhs_stockpieces(), row, "anstorenumout");// ��渨����
				if (vos1 == null || vos1.length == 0 || vos1[0] == null) {
					getPanel().getBodyBillModel().setValueAt(null, row,
							"ndrqstorenumout");// ����������
					getPanel().getBodyBillModel().setValueAt(null, row,
							"ndrqarrstorenumout");// ���ø�����
					return;
				} else {
					getPanel().getBodyBillModel().setValueAt(
							vos1[0].getWhs_stocktonnage(), row,
							"ndrqstorenumout");// ����������
					getPanel().getBodyBillModel().setValueAt(
							vos1[0].getWhs_stockpieces(), row,
							"ndrqarrstorenumout");// ���ø�����
				}
			}
		}

//		public void bodyRowChange(BillEditEvent e) {
//			int row = e.getOldRow();
//			if(row <0){
//				return ;
//			}
//			int state = getPanel().getBodyBillModel().getRowState(row);
//			String csaleid = PuPubVO.getString_TrimZeroLenAsNull(getPanel().getBodyBillModel().getValueAt(row, "csaleid"));
//			if(isGift(csaleid)){
//				updateGiftState(csaleid,state);
//				updateUI();
//			}
//		}
		/**
		 * 
		 * @���ߣ�������Ʒ��״̬
		 * @˵�������ɽ������Ŀ 
		 * @ʱ�䣺2011-11-24����10:31:16
		 * @param csaleid
		 */
		public void updateGiftState(String csaleid,int state){

			if(csaleid == null || "".equalsIgnoreCase(csaleid)){
				return ;
			}
			int count = getPanel().getBodyBillModel().getRowCount();
			for(int row =0;row<count;row++){
				String csourcebillhid = PuPubVO.getString_TrimZeroLenAsNull(getPanel().getBodyBillModel().getValueAt(row, "csaleid"));
				if(csaleid.equalsIgnoreCase(csourcebillhid)){
					getPanel().getBodyBillModel().setRowState(row, state);
				}
			}
			return ;
		
		}
		/**
		 * 
		 * @���ߣ�lyf:�ж��Ƿ���Ʒ��
		 * @˵�������ɽ������Ŀ 
		 * @ʱ�䣺2011-11-17����09:41:46
		 * @return
		 */
		public boolean isGift(String csaleid){
			if(csaleid == null || "".equalsIgnoreCase(csaleid)){
				return false;
			}
			boolean isGift = false;
			int count = getPanel().getBodyBillModel().getRowCount();
			for(int row =0;row<count;row++){
				String csourcebillhid = PuPubVO.getString_TrimZeroLenAsNull(getPanel().getBodyBillModel().getValueAt(row, "csaleid"));
				if(csaleid.equalsIgnoreCase(csourcebillhid)){
					Object value = getPanel().getBodyBillModel().getValueAt(row, "blargessflag");
					isGift = PuPubVO.getUFBoolean_NullAs(value, UFBoolean.FALSE).booleanValue();
					if(isGift){
						return isGift;
					}
				}
			}
			return isGift;
		}
		public boolean beforeEdit(BillEditEvent e) {
			return true;
		}

		public void bodyRowChange(BillEditEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	public void headRowChange(int iNewRow) {
		if (!getPanel().setBodyModelData(iNewRow)) {
			//1.���������������
			loadBodyData(iNewRow);
			//2.���ݵ�ģ����
			getPanel().setBodyModelDataCopy(iNewRow);
		}
		getPanel().repaint();
	}
	/**
	 * ���ۼƻ�����2
	 * @param row
	 */
	private void loadBodyData(int row){
		getPanel().getBodyBillModel().clearBodyData();
		String key = (String) getPanel().getHeadBillModel().getValueAt(row,"csaleid");
//		getPanel().getBodyBillModel().setBodyDataVO(m_handler.getDataBuffer()[row].getBodyVos());
		//�޸��ˣ�����
		//�޸����ڣ�2013.12.26
		getPanel().getBodyBillModel().setBodyDataVO(m_handler.getSelectBufferData(key));// ���ñ���
		getPanel().getBodyBillModel().execLoadFormula();
	}


	private class HeadRowStateListener implements IBillModelRowStateChangeEventListener {
		public void valueChanged(RowStateChangeEvent e) {
			if (e.getRow() != getPanel().getHeadTable().getSelectedRow()) {
				headRowChange(e.getRow());
			}

			BillModel model = getPanel().getBodyBillModel();
			IBillModelRowStateChangeEventListener l = model.getRowStateChangeEventListener();
			model.removeRowStateChangeEventListener();
			if (e.isSelectState()) {
				getPanel().getChildListPanel().selectAllTableRow();
			} else {
				getPanel().getChildListPanel().cancelSelectAllTableRow();
			}
			model.addRowStateChangeEventListener(l);
			getPanel().updateUI();
		}

	}

	private void setButton() {
		ButtonObject[] m_objs = new ButtonObject[] { 
				m_btnSelAll, m_btnSelno,
				m_btnQry, m_btnDeal};
		this.setButtons(m_objs);
	}

	private void createEventHandler() {

		m_handler = new SoDealEventHandler(this);

	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onButtonClicked(ButtonObject btn) {
		// TODO Auto-generated method stub

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
		}else if(btnTag
				.equalsIgnoreCase(WdsWlPubConst.DM_PLANDEAL_BTNTAG_XNDEAL)){
			//			m_btnXnDeal.setEnabled(flag);
		}
		updateButtons();
	}

	public boolean beforeEdit(BillEditEvent e) {
		String key = e.getKey();
		int row  = e.getRow();
		if(e.getPos() == BillItem.HEAD){
			if ("warehousename".equalsIgnoreCase(key)) {
				try {
					LoginInforVO login = getLoginInforHelper().getLogInfor(
							m_ce.getUser().getPrimaryKey());
					if (login.getBistp() == null) {
						return false;
					}
					// ����Ȩ�޵Ĺ��ˣ�ֻ�о�������Ȩ�޵ı���Ա�����ܱ༭����վ
					if (login.getBistp().booleanValue() == true) {
						getPanel().getHeadItem("warehousename").setEnabled(true);
						// ����ֱ�������� �Ĳֿ�
						JComponent c = getPanel().getHeadItem("warehousename")
						.getComponent();
						if (c instanceof UIRefPane) {
							UIRefPane ref = (UIRefPane) c;
							ref.getRefModel().addWherePart(
							" and def1 = '1' and isnull(dr,0) = 0");
						}
						return true;
					} else {
						getPanel().getHeadItem("warehousename").setEnabled(false);
						return false;
					}
				} catch (Exception e1) {
					Logger.error(e1);
				}
			}
		}else{
			if("nassnum".equalsIgnoreCase(key) || "nnum".equalsIgnoreCase(key)){//������Ʒ�����Ա����
				Object value = getPanel().getBodyBillModel().getValueAt(row, "blargessflag");
				if(PuPubVO.getUFBoolean_NullAs(value, UFBoolean.FALSE).booleanValue()){
					return false;
				}
			}else if(e.getKey().equalsIgnoreCase("nassnum")){
				UFDouble hsl = PuPubVO.getUFDouble_NullAsZero(getPanel().getBodyBillModel().getValueAt(e.getRow(), "hsl"));
				if(hsl.equals(WdsWlPubConst.ufdouble_zero)){
					return false;
				}
			}
		}
		
		return true;
	}

	public ClientLink getCl() {
		return cl;
	}

	public void setCl(ClientLink cl) {
		this.cl = cl;
	}

	public void afterEdit(BillEditEvent e) {
		String key = e.getKey();
		
	}

	public void bodyRowChange(BillEditEvent e) {
		// TODO Auto-generated method stub
		if(e.getRow()<0)
			return;
		headRowChange(e.getRow());
		getPanel().getBodyBillModel().reCalcurateAll();	
	}


	
}

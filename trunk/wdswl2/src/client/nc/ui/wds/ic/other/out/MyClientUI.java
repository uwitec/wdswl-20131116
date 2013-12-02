package nc.ui.wds.ic.other.out;
import javax.swing.JComponent;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardBeforeEditListener;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bsdelegate.BusinessDelegator;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.ButtonVOFactory;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.wds.ic.pub.OutPubClientUI;
import nc.ui.wds.w80020206.buttun0206.QxqzBtn;
import nc.ui.wds.w80020206.buttun0206.QzqrBtn;
import nc.ui.wds.w8004040204.ssButtun.fzgnBtn;
import nc.ui.wds.w8004040204.ssButtun.zdqhBtn;
import nc.ui.wds.w80060206.buttun0206.ISsButtun;
import nc.vo.ic.other.out.MyBillVO;
import nc.vo.ic.other.out.TbOutgeneralHVO;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.wl.pub.WdsWlPubConst;
/**
 * ��������
 */
public class MyClientUI extends OutPubClientUI implements
		BillCardBeforeEditListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 

	private String curRefBilltype = null;


	protected ManageEventHandler createEventHandler() {
		return new OtherOutEventHandler(this, getUIControl());
	}

	public MyClientUI() {
		super();
	}

	public MyClientUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}

	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
	}

	protected String getBillNo() throws java.lang.Exception {
//		return HYPubBO_Client.getBillNo(WdsWlPubConst.BILLTYPE_OTHER_OUT,
//				_getOperator(), null, null);
		//�޸��ˣ������޸�����:201311:23
		return HYPubBO_Client.getBillNo(WdsWlPubConst.BILLTYPE_OTHER_OUT,
				getBillCardPanel().getCorp(), null, null);
		
	}
	public void updateSpecialButton(){
		MyBillVO agg = (MyBillVO)getBufferData().getCurrentVO();
		TbOutgeneralHVO head  = (TbOutgeneralHVO)agg.getParentVO();
		getButtonManager().getButton(IBillButton.Print).setEnabled(true);
		if(head.getPrimaryKey() == null || "".equals(head.getPrimaryKey())){
		    getButtonManager().getButton(IBillButton.Add).setEnabled(false);		
		}else{
			getButtonManager().getButton(IBillButton.Add).setEnabled(true);

		}
		super.updateSpecialButton();
	}
	/**
	 * ������ύ�Ƿ�һ����ɡ� ���һ����ɷ���true �������ڣ�(2004-2-27 11:32:56)
	 * 
	 * @return boolean
	 */
	public boolean isSaveAndCommitTogether() {
		return true;
	}

	protected void setHeadSpecialData(CircularlyAccessibleValueObject vo,
			int intRow) throws Exception {
	}

	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
	}

	protected void initSelfData() {
		super.initSelfData();
		// ��ȥ�в������ఴť
		ButtonObject btnobj = getButtonManager().getButton(IBillButton.Line);
		if (btnobj != null) {
			btnobj.removeChildButton(getButtonManager().getButton(
					IBillButton.CopyLine));
			btnobj.removeChildButton(getButtonManager().getButton(
					IBillButton.PasteLine));
			btnobj.removeChildButton(getButtonManager().getButton(
					IBillButton.InsLine));
		}
		getBillCardPanel().setBillBeforeEditListenerHeadTail(this);
	
	}

	@Override
	public boolean beforeEdit(BillEditEvent e) {
		String key = e.getKey();		
		int row = e.getRow();
		if (e.getPos() == BillItem.BODY) {
			if("ntagnum".equalsIgnoreCase(key)){
				UFBoolean fistag = PuPubVO.getUFBoolean_NullAs(getBillCardPanel().getBodyValueAt(row, "fistag"), UFBoolean.FALSE);
				if(fistag.booleanValue()){
					return true;
				}
				return false;
			}
			String csourcetype = PuPubVO
				.getString_TrimZeroLenAsNull(getBillCardPanel()
						.getBodyValueAt(row, "csourcetype"));
			
			
			//�ڰ�����Ϣ��   ͨ����ͷ�Ĳֿ�԰�����в��չ���
			if("teamcode".equals(key)){//����
				//�ֿ�id
				Object a = getBillCardPanel().getHeadItem("srl_pk").getValueObject();
				if(a==null){
					showWarningMessage("��ѡ��ֿ�");
					return false;
				}
				UIRefPane panel = (UIRefPane) this.getBillCardPanel().getBodyItem("teamcode").getComponent();
				if (null != a && !"".equals(a)) {
					//�޸Ĳ��� ���� �������� ָ���ֿ�id
					panel.getRefModel().addWherePart(" and wds_teamdoc_h.vdef1 = '"+a+"' ");
				}
			}
			
			
			//����ǲ��չ����Ĵ�������Ա༭ ����������Ƶ��ݿ��Ա༭
			if ("ccunhuobianma".equalsIgnoreCase(key)) {
				if(getBillOperate() == IBillOperate.OP_REFADD)
					return false;
				if (csourcetype != null) {
					return false;
				} else {
					String pk_cargdoc = (String) getBillCardPanel().getHeadItem("pk_cargdoc").getValueObject();
					if (null == pk_cargdoc || "".equalsIgnoreCase(pk_cargdoc)) {
						showWarningMessage("ǰѡ������λ");
						return false;
					}
					JComponent c = getBillCardPanel().getBodyItem("ccunhuobianma").getComponent();
					if (c instanceof UIRefPane) {
						UIRefPane ref = (UIRefPane) c;
						ref.getRefModel().addWherePart("  and wds_cargdoc1.pk_cargdoc='"+pk_cargdoc+"' ");
					}
					return true;
				}
			}else if("nshouldoutnum".equalsIgnoreCase(key)){
//				zhf add
				if(getBillOperate() == IBillOperate.OP_REFADD)
					return false;
				if(csourcetype != null){

					return false;
				} else {
					return true;
				}
			}else if("nshouldoutassistnum".equalsIgnoreCase(key)){
//				zhf add
				if(getBillOperate() == IBillOperate.OP_REFADD)
					return false;
				if (csourcetype != null) {
				return false;
				} else {
					return true;
				}
			}
			
		}
		return super.beforeEdit(e);
	}

	public void setDefaultData() throws Exception {
		// ��ǰ��˾ ��ǰ�����֯ ��ǰ�ֿ� ��ǰ��λ
		getBillCardPanel().setHeadItem("pk_corp", _getCorp().getPrimaryKey());
		getBillCardPanel().setHeadItem("pk_calbody",
				WdsWlPubConst.DEFAULT_CALBODY);
		if (getBillOperate() != IBillOperate.OP_REFADD) {
			try {
				getBillCardPanel().setHeadItem("srl_pk",
						getLoginInforHelper().getCwhid(_getOperator()));
				getBillCardPanel().setHeadItem(
						"pk_cargdoc",
						getLoginInforHelper().getSpaceByLogUserForStore(
								_getOperator()));
				// getBillCardPanel().setHeadItem("",
				// LoginInforHelper.getLogInfor(userid));
			} catch (Exception e) {
				e.printStackTrace();// zhf �쳣������
			}
		}
		// �Ƶ��� �Ƶ�����
		getBillCardPanel().setTailItem("tmaketime", _getServerTime());
		getBillCardPanel().setHeadItem("dbilldate", _getDate());
		getBillCardPanel().setTailItem("coperatorid", _getOperator());
		getBillCardPanel().setHeadItem("vbilltype",
				WdsWlPubConst.BILLTYPE_OTHER_OUT);
		//getBillCardPanel().setHeadItem("is_yundan", UFBoolean.TRUE);
		// ����״̬
		getBillCardPanel().getHeadTailItem("vbillstatus").setValue(
				IBillStatus.FREE); // ����״̬
		try {
			getBillCardPanel().setHeadItem("vbillcode", getBillNo());
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

	public void setDefaultDataOnRef() throws Exception {
		// ��ǰ��˾ ��ǰ�����֯ ��ǰ�ֿ� ��ǰ��λ
		getBillCardPanel().setHeadItem("pk_corp", _getCorp().getPrimaryKey());
		getBillCardPanel().setHeadItem("pk_calbody",
				WdsWlPubConst.DEFAULT_CALBODY);
		try {
			// getBillCardPanel().setHeadItem("srl_pk",
			// LoginInforHelper.getCwhid(_getOperator()));
			getBillCardPanel().setHeadItem(
					"pk_cargdoc",
					getLoginInforHelper().getSpaceByLogUserForStore(
							_getOperator()));
			// // getBillCardPanel().setHeadItem("",
			// LoginInforHelper.getLogInfor(userid));
		} catch (Exception e) {
			e.printStackTrace();// zhf �쳣������
		}
		// �Ƶ��� �Ƶ�����
		
		getBillCardPanel().setTailItem("tmaketime", _getServerTime());
		getBillCardPanel().setHeadItem("dbilldate", _getDate());
		getBillCardPanel().setTailItem("coperatorid", _getOperator());
		getBillCardPanel().setHeadItem("vbilltype",
				WdsWlPubConst.BILLTYPE_OTHER_OUT);
		//getBillCardPanel().setHeadItem("is_yundan", UFBoolean.TRUE);
		setBillNo();
		// getBillCardPanel().setHeadItem("pwb_fbillflag",2);
		// getBillCardPanel().setHeadItem("vbillcode",
		// HYPubBO_Client.getBillNo(WdsWlPubConst.BILLTYPE_OTHER_OUT,
		// _getOperator(), null, null));
	}

	
	
	@Override
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
		String key=e.getKey();
		if(e.getPos() == BillItem.HEAD){
			if("srl_pk".equalsIgnoreCase(key)){
			   //�ֿ� Ϊ�� �� ��λ��ֹ�༭����֮ ��λ�ɱ༭
			   boolean isEditable = true;
			   if(PuPubVO.getString_TrimZeroLenAsNull(e.getValue()) == null){
				   isEditable = false;
			   }
			   getBillCardPanel().getHeadItem("pk_cargdoc").setEnabled(isEditable);
			   getBillCardPanel().getHeadItem("pk_cargdoc").setValue(null);
			// add by  zhw
			   String[] tablecodes = new String[] {"tb_outgeneral_b", "tb_outgeneral_b2"};
	              clearTable(tablecodes);
			   
			 }else if("pk_cargdoc".equalsIgnoreCase(key)){
				// add by  zhw
				 String[] tablecodes = new String[] {"tb_outgeneral_b", "tb_outgeneral_b2"};
	              clearTable(tablecodes);
			 }
			if("cdptid".equalsIgnoreCase(key)){
				getBillCardPanel().getHeadItem("cbizid").setValue(null);	
			}else if(key.equalsIgnoreCase("cbizid")){		
				getBillCardPanel().execHeadTailLoadFormulas();
			}
		}else{
			if("fistag".equalsIgnoreCase(key)){//����ѡ��ǩ��ʱ����ǩ�������
				int row = e.getRow();
				Object o = e.getValue();
				if( UFBoolean.FALSE.equals(o) ){
					getBillCardPanel().setBodyValueAt(null,row, "ntagnum");
				}
			}
		}
	}

	 /**
     * @˵�� ���ݱ��� tableCode,���ҳǩ����
     * @ʱ�� 2010-9-14����02:06:02
     * @param tableCodes
     */
    protected void clearTable(String[] tableCodes) {
        if (tableCodes != null && tableCodes.length > 0) {
            for (int i = 0; i < tableCodes.length; i++) {
                if(getBillCardPanel().getBillModel(tableCodes[i])==null)
                    return;
                int count = getBillCardPanel().getBillModel(tableCodes[i])
                        .getRowCount();
                int[] array = new int[count];
                for (int j = 0; j < count; j++) {
                    array[j] = j;
                }
                getBillCardPanel().getBillData().getBillModel(tableCodes[i])
                        .delLine(array);
            }
        }
    }
	@Override
	protected void initEventListener() {
		
		super.initEventListener();
		getBillCardPanel().setBillBeforeEditListenerHeadTail(this);

	}
	/**
	 * ��ͷ�ı༭ǰ�¼�
	 */	
	public boolean beforeEdit(BillItemEvent e) {
		String key = e.getItem().getKey();
		if (e.getItem().getPos() == BillItem.HEAD) {
			// �ֿ���ˣ�ֻ��������ϵͳ��
			// srl_pkr Ϊ���ֿ�
			if ("srl_pk".equalsIgnoreCase(key)) {
				JComponent c = getBillCardPanel().getHeadItem("srl_pk")
						.getComponent();
				if (c instanceof UIRefPane) {
					UIRefPane ref = (UIRefPane) c;
					ref.getRefModel().addWherePart(
							" and def1 = '1' and isnull(dr,0) = 0");
				}
			}
			//���������Ĳֿ�
			if ("srl_pkr".equalsIgnoreCase(key)) {
				JComponent c = getBillCardPanel().getHeadItem("srl_pkr")
						.getComponent();
				if (c instanceof UIRefPane) {
					UIRefPane ref = (UIRefPane) c;
					ref.getRefModel().addWherePart(
							" and def1 = '1' and isnull(dr,0) = 0");
				}
			}
			//���ݲֿ���˻�λ
			if("pk_cargdoc".equals(key)){//�����λ
				//�ֿ�id
				Object a = getBillCardPanel().getHeadItem("srl_pk").getValueObject();
				if(a==null){
					showWarningMessage("��ѡ��ֿ�");
					return false;
				}
				UIRefPane panel = (UIRefPane) this.getBillCardPanel().getHeadItem("pk_cargdoc").getComponent();
				if (null != a && !"".equals(a)) {
					//�޸Ĳ��� ���� �������� ָ���ֿ�id
					panel.getRefModel().addWherePart(" and bd_stordoc.pk_stordoc = '"+a+"' ");
				}
			}
			// �Ե�ǰ��λ�µĹ���Ա���й���
			if ("cwhsmanagerid".equalsIgnoreCase(key)) {
				String pk_cargdoc = (String) getBillCardPanel().getHeadItem(
						"pk_cargdoc").getValueObject();
				if (null == pk_cargdoc || "".equalsIgnoreCase(pk_cargdoc)) {
					showWarningMessage("ǰѡ������λ");
					return false;
				}
				JComponent c = getBillCardPanel().getHeadItem
				("cwhsmanagerid").getComponent();
				if (c instanceof UIRefPane) {
					UIRefPane ref = (UIRefPane) c;
					ref.getRefModel().addWherePart(
							"  and tb_stockstaff.pk_cargdoc='" + pk_cargdoc + "' ");
				}
				return true;
			}
		}
		
		

	
		return true;
	}

	protected AbstractManageController createController() {
		return new OtherOutClientUICtrl();
	}

	/**
	 * ������ݲ���ƽ̨ʱ��UI����Ҫ���ش˷��������ز���ƽ̨��ҵ�������
	 * 
	 * @return BusinessDelegator ����ƽ̨��ҵ�������
	 */
	protected BusinessDelegator createBusinessDelegator() {
		return new OtherOutDelegator();
	}

	/**
	 * ע���Զ��尴ť
	 */
	protected void initPrivateButton() {

		super.initPrivateButton();
		// zjBtn customizeButton1 = new zjBtn();
		// addPrivateButton(customizeButton1.getButtonVO());
		// zkBtn customizeButton2 = new zkBtn();
		// addPrivateButton(customizeButton2.getButtonVO());
		// cgqyBtn customizeButton3 = new cgqyBtn();
		// addPrivateButton(customizeButton3.getButtonVO());
		// zzdjBtn customizeButton4 = new zzdjBtn();
		// addPrivateButton(customizeButton4.getButtonVO());
		fzgnBtn customizeButton5 = new fzgnBtn();
		addPrivateButton(customizeButton5.getButtonVO());
//		tpzdBtn customizeButton6 = new tpzdBtn();
//		addPrivateButton(customizeButton6.getButtonVO());
		zdqhBtn customizeButton7 = new zdqhBtn();
		addPrivateButton(customizeButton7.getButtonVO());
//		ckmxBtn customizeButton8 = new ckmxBtn();
//		addPrivateButton(customizeButton8.getButtonVO());

		QxqzBtn customizeButton9 = new QxqzBtn();
		addPrivateButton(customizeButton9.getButtonVO());
		QzqrBtn customizeButton10 = new QzqrBtn();
		addPrivateButton(customizeButton10.getButtonVO());

		getButtonManager().getButtonAry(
				new int[] { ISsButtun.Qzqr, ISsButtun.Qxqz });// ȡ��ǩ��,ǩ��ȷ��

		ButtonVO soOrder = new ButtonVO();
		soOrder.setBtnNo(nc.ui.wds.w80020206.buttun0206.ISsButtun.RefSendOrder);
		soOrder.setBtnCode(null);
		soOrder.setBtnName("���˶���");
		soOrder.setBtnChinaName("���˶���");
		addPrivateButton(soOrder);
		ButtonVO redSoorder = new ButtonVO();
		redSoorder.setBtnNo(nc.ui.wds.w80020206.buttun0206.ISsButtun.RefWDSC);
		redSoorder.setBtnCode(null);
		redSoorder.setBtnName("�ɹ�ȡ��");
		redSoorder.setBtnChinaName("�ɹ�ȡ��");
		addPrivateButton(redSoorder);
		
		ButtonVO btransfer = new ButtonVO();
		btransfer.setBtnNo(nc.ui.wds.w80020206.buttun0206.ISsButtun.RefHWTZ);
		btransfer.setBtnCode(null);
		btransfer.setBtnName("��λ������");
		btransfer.setBtnChinaName("��λ������");
		addPrivateButton(btransfer);
		
		ButtonVO tsyd = new ButtonVO();
		tsyd.setBtnNo(nc.ui.wds.w80020206.buttun0206.ISsButtun.tsyd);
		tsyd.setBtnCode(null);
		tsyd.setBtnName("�����˵�");
		tsyd.setBtnChinaName("�����˵�");
		addPrivateButton(tsyd);
		
		ButtonVO refbill = ButtonVOFactory.getInstance().build(
				IBillButton.Refbill);
		refbill.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT,
				IBillOperate.OP_INIT });
		refbill.setChildAry(new int[] { soOrder.getBtnNo(),
				redSoorder.getBtnNo(),btransfer.getBtnNo(),tsyd.getBtnNo() });
		addPrivateButton(refbill);
	}


	@Override
	public String getRefBillType() {		
		return curRefBilltype;
	}

	public void setRefBillType(String curRefBilltype) {
		this.curRefBilltype = curRefBilltype;
	}	
}

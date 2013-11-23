package nc.ui.wds.ic.other.in;

import javax.swing.JComponent;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITabbedPane;
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
import nc.ui.wds.ic.pub.MutiInPubClientUI;
import nc.ui.wds.w80020206.buttun0206.QxqzBtn;
import nc.ui.wds.w80020206.buttun0206.QzqrBtn;
import nc.ui.wds.w8004040214.buttun0214.ZdrkBtn;
import nc.ui.wds.w80060206.buttun0206.ISsButtun;
import nc.vo.ic.pub.PubVO;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.field.IBillField;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.wl.pub.WdsWlPubConst;

/**
 *  �������
 */
public class MyClientUI extends MutiInPubClientUI  implements  BillCardBeforeEditListener{
	private static final long serialVersionUID = 1L;
	private String curRefBilltype = null;
	protected ManageEventHandler createEventHandler() {
		return new OtherInEventHandler(this, getUIControl());
	}
	
	public MyClientUI() {
		super();
	}

	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
	}

	protected void setHeadSpecialData(CircularlyAccessibleValueObject vo,
			int intRow) throws Exception {
	}
	
	protected BusinessDelegator createBusinessDelegator() {
		return new OtherInDelegator();
	}


	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
	}

	
	@Override
	protected void initEventListener() {
		
		super.initEventListener();
		getBillCardPanel().setBillBeforeEditListenerHeadTail(this);
	}
	public MyClientUI(String pk_corp, String pk_billType,String pk_busitype, String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
	}

	protected void initSelfData() {
		//��ȥ�в������ఴť
		ButtonObject btnobj = getButtonManager().getButton(IBillButton.Line);
		if (btnobj != null) {
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.CopyLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.PasteLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.InsLine));
		}
		
		// ����ҳǩ�л�����
		UITabbedPane m_CardUITabbedPane = getBillCardPanel().getBodyTabbedPane();
		m_CardUITabbedPane.addChangeListener(this);
		getBillCardPanel().setBillBeforeEditListenerHeadTail(this);
	
	}


	@Override
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
		String key=e.getKey();
		if("geh_cdptid".equalsIgnoreCase(key)){
		getBillCardPanel().getHeadItem("geh_cbizid").setValue(null);	
		}else if(key.equalsIgnoreCase("geh_cbizid")){		
			getBillCardPanel().execHeadTailLoadFormulas();
		}
		
		
	}
	public void afterUpdate() {
		if (!getBufferData().isVOBufferEmpty()) {
			int row = getBufferData().getCurrentRow();
			if (row < 0) {
				return;
			}
			Object o = getBufferData().getCurrentVO().getParentVO()
					.getAttributeValue(getBillField().getField_BillStatus());
			if (o.equals(IBillStatus.FREE)) {// ����
				getButtonManager().getButton(ISsButtun.Qxqz).setEnabled(false);
				getButtonManager().getButton(ISsButtun.Qzqr).setEnabled(true);
			} else {// ǩ��
				getButtonManager().getButton(ISsButtun.Qzqr).setEnabled(false);
				getButtonManager().getButton(ISsButtun.Qxqz).setEnabled(true);
			}
			updateButtons();
		}
	}
	public void setDefaultData() throws Exception {
		//��ǰ��˾ ��ǰ�����֯  ��ǰ�ֿ�  ��ǰ��λ
		getBillCardPanel().setHeadItem("geh_corp", _getCorp().getPk_corp());//��⹫˾
		getBillCardPanel().setHeadItem("pk_corp",_getCorp().getPk_corp());//��ǰ��¼��˾
		getBillCardPanel().setHeadItem("geh_calbody", WdsWlPubConst.DEFAULT_CALBODY);
		if(getBillOperate() != IBillOperate.OP_REFADD){
			try{		
				getBillCardPanel().setHeadItem("geh_cwarehouseid", getLoginInforHelper().getCwhid(_getOperator()));
				getBillCardPanel().setHeadItem("pk_cargdoc", getLoginInforHelper().getSpaceByLogUserForStore(_getOperator()));
			}catch(Exception e){
				showHintMessage("��ǰ��¼��û�а󶨵��ֿ�");
			}
		
		}
		getBillCardPanel().setHeadItem("geh_billtype",WdsWlPubConst.BILLTYPE_OTHER_IN);
		getBillCardPanel().setHeadItem("geh_cbilltypecode",WdsWlPubConst.BILLTYPE_OTHER_IN);//��ⵥ������
		getBillCardPanel().setHeadItem("pwb_fbillflag",IBillStatus.FREE);//����״̬
		getBillCardPanel().setHeadItem("geh_billcode", getBillNo());
		//�Ƶ���  �Ƶ�����  
		getBillCardPanel().setTailItem("copetadate",_getDate());
		getBillCardPanel().setTailItem("coperatorid",_getOperator());
		getBillCardPanel().setTailItem("tmaketime",_getServerTime());
		getBillCardPanel().setHeadItem("geh_dbilldate",_getDate());	
		}
	@Override
	/**
	 * �޸����ڣ�2013/11/20
	 * �޸��ˣ�����
	 * �޸�ԭ��
	 */
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(WdsWlPubConst.BILLTYPE_OTHER_IN,getBillCardPanel().getCorp(),null, null);
	}
	@Override
	protected IBillField createBillField() {
		return new BillField();
	}
	

	protected AbstractManageController createController() {
		return new OtherInClientUICtrl();
	}

	
	
//	@Override
//	public boolean beforeEdit(BillEditEvent e) {
//		int row  = e.getRow();
//		String key=e.getKey();
//		
//		  String csourcetype = PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel()
//					.getBodyValueAt(row, "csourcetype"));
//	     //����ǲ��չ����Ĳ����Ա༭ ����������Ƶ��ݿ��Ա༭
//		if ("invcode".equalsIgnoreCase(key)) {
//			if(getBillOperate() == IBillOperate.OP_EDIT)//zhf add 20110624  �޸�ʱ ������벻���޸�
//				return false;
//			if (csourcetype != null) {
//				return false;
//			} else {
//				String pk_cargdoc=(String) getBillCardPanel().getHeadItem("pk_cargdoc").getValueObject();
//				if(null==pk_cargdoc || "".equalsIgnoreCase(pk_cargdoc)){
//					showWarningMessage("ǰѡ������λ");
//					return false;
//				}			
//				JComponent c =getBillCardPanel().getBodyItem("invcode").getComponent();
//				if( c instanceof UIRefPane){
//					UIRefPane ref = (UIRefPane)c;
//					ref.getRefModel().addWherePart("  and tb_spacegoods.pk_cargdoc='"+pk_cargdoc+"' ");
//				}
//				return true;
//			}
//		}
//		if("geb_snum".equalsIgnoreCase(key)){
//			if(getBillOperate() == IBillOperate.OP_EDIT)//zhf add 20110624  �޸�ʱ ������벻���޸�
//				return false;
//			if (csourcetype != null) {
//				return false;
//			} else {
//				return true;
//			}		
//		}if("geb_bsnum".equalsIgnoreCase(key)){
//			if(getBillOperate() == IBillOperate.OP_EDIT)//zhf add 20110624  �޸�ʱ ������벻���޸�
//				return false;
//			if (csourcetype != null) {
//				return false;
//			} else {
//				return true;
//			}		
//		}
//			
////		//���˵�ǰ��λ�µĴ��
////		if("invcode".equalsIgnoreCase(key)){
////			
////			return true;	
////						
////		}
//	
//
//		return super.beforeEdit(e);
//	}
	/**
	 * ע���Զ��尴ť
	 */
	protected void initPrivateButton() {
		super.initPrivateButton();
//		FzgnBtn customizeButton1=new FzgnBtn();
//		addPrivateButton(customizeButton1.getButtonVO());
//		ZdtpBtn customizeButton2=new ZdtpBtn();
//		addPrivateButton(customizeButton2.getButtonVO());
//		CkmxBtn customizeButton3=new CkmxBtn();
//		addPrivateButton(customizeButton3.getButtonVO());
		ZdrkBtn customizeButton4=new ZdrkBtn();
		addPrivateButton(customizeButton4.getButtonVO());
		QzqrBtn customizeButton9=new QzqrBtn();
		addPrivateButton(customizeButton9.getButtonVO());
		QxqzBtn customizeButton10=new QxqzBtn();
		addPrivateButton(customizeButton10.getButtonVO());
		//��ϵͳע��Ĳ��հ�ť�ĸ���
		ButtonVO ref4i = new ButtonVO();
		ref4i.setBtnNo(nc.ui.wds.w80020206.buttun0206.ISsButtun.Ref4I);
		ref4i.setBtnCode(null);
		ref4i.setBtnName("��Ӧ����������");
		ref4i.setBtnChinaName("��Ӧ����������");
		
		addPrivateButton(ref4i);
		
		//��Ӳ���   �������� ��ť    for add mlr
		ButtonVO refwds6 = new ButtonVO();
		refwds6.setBtnNo(nc.ui.wds.w80020206.buttun0206.ISsButtun.refwds6);
		refwds6.setBtnCode(null);
		refwds6.setBtnName("��������");
		refwds6.setBtnChinaName("��������");
		addPrivateButton(refwds6);
		//��Ӳ���   �������� ��ť    for add mlr
		ButtonVO refwds = new ButtonVO();
		refwds.setBtnNo(nc.ui.wds.w80020206.buttun0206.ISsButtun.refwds);
		refwds.setBtnCode(null);
		refwds.setBtnName("�����˵�");
		refwds.setBtnChinaName("�����˵�");
		addPrivateButton(refwds);
		
		ButtonVO refbill =ButtonVOFactory.getInstance().build(IBillButton.Refbill);
		refbill.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT, IBillOperate.OP_INIT });
		refbill.setChildAry(new int[]{refwds6.getBtnNo(),refwds.getBtnNo()});		
		addPrivateButton(refbill);
	}
	@Override
	public String getRefBillType() {
		return curRefBilltype;
	}
	protected void setRefBillType(String curRefBilltype) {
		this.curRefBilltype = curRefBilltype;
	}
	public boolean beforeEdit(BillItemEvent e) {
		String key=e.getItem().getKey();
		//�����ֿ���ˣ�����ֻ���������Ĳֿ�
		if("geh_cwarehouseid".equalsIgnoreCase(key)){
			JComponent c =getBillCardPanel().getHeadItem("geh_cwarehouseid").getComponent();
			if( c instanceof UIRefPane){
				UIRefPane ref = (UIRefPane)c;
				ref.getRefModel().addWherePart("  and def1 = '1' and isnull(dr,0) = 0");
			}
			return true;
		}
		//�Գ���ֿ���ˣ�����ֻ���������Ĳֿ�
		if("geh_cotherwhid".equalsIgnoreCase(key)){
			JComponent c =getBillCardPanel().getHeadItem("geh_cotherwhid").getComponent();
			if( c instanceof UIRefPane){
				UIRefPane ref = (UIRefPane)c;
				ref.getRefModel().addWherePart("  and def1 = '1' and isnull(dr,0) = 0");
			}
			return true;
		}
		//������λ���ˣ�����ֻ���ڶ�Ӧ�����ֿ�����Ļ�λ
		if("pk_cargdoc".equalsIgnoreCase(key)){
			String pk_store=(String) getBillCardPanel().getHeadItem("geh_cwarehouseid").getValueObject();
			if(null==pk_store || "".equalsIgnoreCase(pk_store)){
				showWarningMessage("��ѡ�����ֿ�");
				return false;
			}			
			JComponent c =getBillCardPanel().getHeadItem("pk_cargdoc").getComponent();
			if( c instanceof UIRefPane){
				UIRefPane ref = (UIRefPane)c;
				ref.getRefModel().addWherePart("  and bd_cargdoc.pk_stordoc='"+pk_store+"' and isnull(bd_cargdoc.dr,0) = 0");
			}
			return true;			
		}
		//�Ե�ǰ��λ�µĹ���Ա���й���
		if("geh_cwhsmanagerid".equalsIgnoreCase(key)){
			String pk_cargdoc=(String) getBillCardPanel().getHeadItem("pk_cargdoc").getValueObject();
			if(null==pk_cargdoc || "".equalsIgnoreCase(pk_cargdoc)){
				showWarningMessage("ǰѡ������λ");
				return false;
			}			
			JComponent c =getBillCardPanel().getHeadItem("geh_cwhsmanagerid").getComponent();
			if( c instanceof UIRefPane){
				UIRefPane ref = (UIRefPane)c;
				ref.getRefModel().addWherePart("  and tb_stockstaff.pk_cargdoc='"+pk_cargdoc+"' ");
			}
			return true;		
		}
		return true;
	}
	@Override
	public boolean beforeEdit(BillEditEvent e) {
		String key = e.getKey();				
		if (e.getPos() == BillItem.BODY) {	
			//�ڰ�����Ϣ��   ͨ����ͷ�Ĳֿ�԰�����в��չ���
			if("teamcode".equals(key)){//����
				//�ֿ�id
				Object a = getBillCardPanel().getHeadItem("geh_cwarehouseid").getValueObject();
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
			//liuys add Ӧ�������Լ�Ӧ�ո�����������״̬���ܱ༭
			if("geb_snum".equals(key)||"geb_bsnum".equals(key)){
				Object obj = getBillCardPanel().getBillModel().getValueAt(e.getRow(), "csourcebillhid");
				if(PuPubVO.getString_TrimZeroLenAsNull(obj)==null){
					return true;
				}else
					return false;
				//��ʱ,ǰ̨��sql
			}
		}
		super.beforeEdit(e);
		return true;
	}
	@Override
	public Object getUserObject() {
		return new GetCheck();
	}	
}
package nc.ui.wds.ic.other.out;

import java.util.ArrayList;
import java.util.List;

import nc.bs.logging.Logger;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.wds.ic.pub.OutPubClientUI;
import nc.ui.wds.ic.pub.OutPubEventHandler;
import nc.ui.wds.w8004040204.ssButtun.ISsButtun;
import nc.uif.pub.exception.UifException;
import nc.vo.dm.order.SendorderVO;
import nc.vo.ic.other.out.TbOutgeneralBVO;
import nc.vo.ic.other.out.TbOutgeneralHVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.wl.pub.BillRowNo;
import nc.vo.wl.pub.WdsWlPubConst;
import nc.vo.wl.pub.WdsWlPubTool;
/**
 * ��������
 * @author author
 * @version tempProject version
 */
public class OtherOutEventHandler extends OutPubEventHandler {

	private String vbillno = getBillUI().getBillField().getField_BillNo(); // ���ݺ��ֶ�
	
	private EventHandlerTools tools;
	
	public OtherOutEventHandler(OutPubClientUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	
	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn);
		switch (intBtn) {
			case ISsButtun.zzdj:
				onzzdj(null);
				break;
			case ISsButtun.tpzd:
				valudateWhereYeqian();
				//��� ���ΨһУ��
//				BeforeSaveValudate.beforeSaveBodyUnique(getBillCardPanelWrapper().getBillCardPanel().getBillTable(),
//						getBillCardPanelWrapper().getBillCardPanel().getBillModel(),
//						new String[]{"ccunhuobianma","batchcode"},
//						new String[]{"�������","���κ�"});
			///	ontpzd();
				
				break;
			case ISsButtun.zdqh:
				valudateWhereYeqian();
				onzdqh();
				
				break;
			case ISsButtun.ckmx:
			//	onckmx();
				break;
			case nc.ui.wds.w80060206.buttun0206.ISsButtun.Qxqz:
				onQxqz();
				break;
			case nc.ui.wds.w80060206.buttun0206.ISsButtun.Qzqr:
				onQzqr();
				break;
			case nc.ui.wds.w80020206.buttun0206.ISsButtun.RefSendOrder:
				((MyClientUI)getBillUI()).setRefBillType(WdsWlPubConst.WDS3);
				onBillRef();				
				break;
			case nc.ui.wds.w80020206.buttun0206.ISsButtun.RefWDSC:
				((MyClientUI)getBillUI()).setRefBillType(WdsWlPubConst.WDSC);
				onBillRef();
				
				setInitByWhid(new String[]{"srl_pk","pk_cargdoc"});
				setInitWarehouse("srl_pk");
				break;
			case nc.ui.wds.w80020206.buttun0206.ISsButtun.RefHWTZ:
				((MyClientUI)getBillUI()).setRefBillType(WdsWlPubConst.HWTZ);
				onBillRef();	
				getButtonManager().getButton(ISsButtun.zdqh).setEnabled(false);
				getBillCardPanelWrapper().getBillCardPanel().getBodyItem("ss_state").setEdit(false);
				break;
			case nc.ui.wds.w80020206.buttun0206.ISsButtun.tsyd:
				((MyClientUI)getBillUI()).setRefBillType(WdsWlPubConst.WDSS);
				onBillRef();	
				//getButtonManager().getButton(ISsButtun.zdqh).setEnabled(false);
				getBillCardPanelWrapper().getBillCardPanel().getBodyItem("ss_state").setEdit(false);
				break;	
			}
	}
	
	/**
	 * �����Ӧ��ҳǩ
	 * �����ڳ����ӱ�ҳǩ
	 * @���ߣ�zhf
	 * @˵�������ɽ������Ŀ 
	 * @ʱ�䣺2011-9-22����02:56:45
	 */
    private void valudateWhereYeqian()throws Exception{
	   String tablecode=getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().getTableCode();
	   if(!"tb_outgeneral_b".equalsIgnoreCase(tablecode)){
		 throw new Exception("��ѡ�������ҳǩ");   
	   }
	}
	@Override
	protected void setRefData(AggregatedValueObject[] vos) throws Exception {
		// TODO Auto-generated method stub
		super.setRefData(vos);
		//���� �ֿ�ͻ�λ���Ƿ�ɱ༭���ֿܲ��ԣ��ֲֲ�����
		setInitByWhid(new String[]{"srl_pk","pk_cargdoc"});
		//���ò��ճ����г���ֿ�Ϊ�գ���ֵĬ�ϲֿ�Ϊ��ǰ����Ա�ֿ�
		setInitWarehouse("srl_pk");
		//���õ��ݺ�
		getBillCardPanelWrapper().getBillCardPanel().setHeadItem("vbillno", ((MyClientUI)getBillUI()).getBillNo());
	 //	getBillCardPanelWrapper().getBillCardPanel().setHeadItem("is_yundan", null);
	}
	@Override
	protected UIDialog createQueryUI() {
		// TODO Auto-generated method stub
		return new MyQueryDIG(
				getBillUI(), null, 				
				_getCorp().getPk_corp(), getBillUI().getModuleCode()				
				, getBillUI()._getOperator(), null		
		);
	}
	@Override
	protected String getHeadCondition() {
		return " pk_corp = '"+_getCorp().getPrimaryKey()+"' and isnull(dr,0) = 0 and vbilltype = '"+WdsWlPubConst.BILLTYPE_OTHER_OUT+"' ";
	}

	protected void onBoLineAdd() throws Exception {
		super.onBoLineAdd();
	    BillRowNo.addLineRowNo(getBillCardPanelWrapper().getBillCardPanel(),WdsWlPubConst.BILLTYPE_OTHER_OUT, "crowno");
	    
	}
	
	// ǩ��ȷ��
	protected void onQzqr() throws Exception {
		try{
			if (getBufferData().getCurrentVO()!=null){
				getBillManageUI().showHintMessage("ִ��ǩ��...");
				int retu = getBillManageUI().showOkCancelMessage("ȷ��ǩ��?");
				if (retu != 1) {
					return;
				}
				AggregatedValueObject aObject  = getBufferData().getCurrentVOClone();
				TbOutgeneralHVO generalh = (TbOutgeneralHVO)aObject.getParentVO();
				generalh.setVbillstatus(IBillStatus.CHECKPASS);// ����״̬
				generalh.setCregister(_getOperator());// ǩ��������
				generalh.setTaccounttime(getBillUI()._getServerTime().toString());// ǩ��ʱ��
				generalh.setQianzidate(_getDate());// ǩ������
				ArrayList<String> list = new ArrayList<String>();
				list.add(_getDate().toString());
				list.add(_getOperator());//��¼��
				//�����ű�
				PfUtilClient.processAction(getBillManageUI(), "SIGN", getBillManageUI().getUIControl().getBillType(), _getDate().toString(), aObject,list );
				//���»���
				onBoRefresh();
			}
		}catch(Exception e){
			Logger.error(e);
			getBillManageUI().showErrorMessage("ǩ��ʧ��:"+e.getMessage());
		}
	}
	
	
	
	
	
	@Override
	protected void onBoSave() throws Exception {
		//����ǩ����    С��    ʵ��������У��
		if( getBillUI().getVOFromUI().getChildrenVO()!=null){
			TbOutgeneralBVO[] tbs=(TbOutgeneralBVO[]) getBillUI().getVOFromUI().getChildrenVO();
			for(int i=0;i<tbs.length;i++){
				UFDouble u1=PuPubVO.getUFDouble_NullAsZero(tbs[i].getNoutassistnum());
				UFDouble u2=PuPubVO.getUFDouble_NullAsZero(tbs[i].getNtagnum());
				if(u1.sub(u2).doubleValue()<0){
					throw new BusinessException("��ǩ����   ���ܴ��� ʵ������");
				}
			}			
		}
		if(getBillCardPanelWrapper().getBillVOFromUI().getChildrenVO().length==0)
		{
			throw new BusinessException("���岻��Ϊ��");
		}
		super.onBoSave();
	}

	// ȡ��ǩ��
	protected void onQxqz() throws Exception {
		try{
			if (getBufferData().getCurrentVO()!=null){
				getBillManageUI().showHintMessage("����ִ��ȡ��ǩ��...");
				int retu = getBillManageUI().showOkCancelMessage("ȡ��ǩ�ֻ�ɾ������װж�Ѻ��㵥���Ƿ�ȷ��ȡ��ǩ��?");
				if (retu != UIDialog.ID_OK) {
					return;
				}
				AggregatedValueObject aObject  = getBufferData().getCurrentVOClone();
				TbOutgeneralHVO generalh = (TbOutgeneralHVO)aObject.getParentVO();
//				if(generalh.getFisload() != null &&generalh.getFisload().equals(UFBoolean.TRUE)){
//					getBillUI().showWarningMessage("�Ѿ��γ�װж�Ѻ��㵥������ȡ��ǩ��");
//					return ;
//				}
//				if(generalh.getFistran() != null &&generalh.getFistran().equals(UFBoolean.TRUE)){
//					getBillUI().showWarningMessage("�Ѿ��γ��˷Ѻ��㵥������ȡ��ǩ��");
//					return ;
//				}
				generalh.setVbillstatus(IBillStatus.FREE);// ����״̬
				generalh.setCregister(null);// ǩ��������
				generalh.setTaccounttime(null);// ǩ��ʱ��
				generalh.setQianzidate(null);// ǩ������
				ArrayList<String> list = new ArrayList<String>();
				list.add(_getDate().toString());
				list.add(_getOperator());//��¼��
				//�����ű�
				PfUtilClient.processAction(getBillManageUI(), "CANELSIGN", getBillManageUI().getUIControl().getBillType(), _getDate().toString(), aObject,list );
				//���»���
				onBoRefresh();
			}
		}catch(Exception e){
			Logger.error(e);
			getBillManageUI().showErrorMessage("ȡ��ǩ��ʧ��:"+e.getMessage());
		}
	}

	protected void onzzdj(ButtonObject bo) throws Exception {
		super.onBoAdd(bo);
	}
	
	
	
	@Override
	public void onBoAdd(ButtonObject bo) throws Exception {
		super.onBoAdd(bo);
		getButtonManager().getButton(ISsButtun.fzgn).setEnabled(true);
		setInitByWhid(new String[]{"srl_pk","pk_cargdoc"});
	}

	private  EventHandlerTools getEventHanderTools(){
		if(tools == null){
			return new EventHandlerTools();
		}
		return tools;
	}

//	@Override
//	protected void onBoPrint() throws Exception {
//		super.onBoPrint();
//		Integer iprintcount =PuPubVO.getInteger_NullAs(getBillCardPanelWrapper().getBillCardPanel().getTailItem("iprintcount").getValueObject(), 0) ;
//		iprintcount=++iprintcount;
//		getBillCardPanelWrapper().getBillCardPanel().getTailItem("iprintcount").setValue(iprintcount);
//		getBufferData().getCurrentVO().getParentVO().setAttributeValue("iprintcount", iprintcount);
//		HYPubBO_Client.update((SuperVO)getBufferData().getCurrentVO().getParentVO());
//	
//		
//	}
	
	/**
	 * zhf add  ��֧���޸�ʱ �в���
	 */
	protected void onBoEdit() throws Exception {
		if (getBufferData().getCurrentVO() == null)
			return;
		UFBoolean isadjust = PuPubVO.getUFBoolean_NullAs(getBufferData().getCurrentVO().getParentVO().getAttributeValue("vuserdef15"), UFBoolean.FALSE);
		if(isadjust.booleanValue()){
			getBillUI().showHintMessage("���ڵ����ĳ��ⵥ�����޸�");
			return;
		}
        valuteOrder();
			
		super.onBoEdit();
		
		
		
		
		
		
		
		
		
//		zhf add
	//	setHeadPartEdit(false);
//		getButtonManager().getButton(IBillButton.Line).setEnabled(false);
//		getBillUI().updateButtons();
		setInitByWhid(new String[]{"srl_pk","pk_cargdoc"});
		
	}

	private void valuteOrder() throws Exception {
		AggregatedValueObject  vo=getBufferData().getCurrentVO();
		SuperVO[] vos=(SuperVO[]) vo.getChildrenVO();
		if(vos==null || vos.length==0)
			return;
		  SuperVO bvo=(SuperVO) vos[0];
		  String csid=PuPubVO.getString_TrimZeroLenAsNull(bvo.getAttributeValue("csourcebillhid"));
		  //��ѯ���˶������Ƿ��Ѿ�����
			 SuperVO[] ovs=  HYPubBO_Client.queryByCondition(SendorderVO.class, " isnull(dr,0)=0 and pk_sendorder='"+csid+"'");
			 if(ovs==null || ovs.length==0)
				 return;
			 SendorderVO head=(SendorderVO) ovs[0];
			 UFBoolean isdj=PuPubVO.getUFBoolean_NullAs(head.getFisended(), new UFBoolean(false));
			 if(isdj.booleanValue()==true){
				 throw new Exception("�����˵��Ѿ����� ,��������");
			 }
	
}

	@Override  //xjx  add   ��ӡ
	protected void onBoPrint() throws Exception {
		//��������б���棬ʹ��ListPanelPRTS����Դ
		if( getBillManageUI().isListPanelSelected() ){
			nc.ui.pub.print.IDataSource dataSource = new MyListDateSource(
					getBillUI()._getModuleCode(), ((BillManageUI) getBillUI())
					.getBillListPanel());
			nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(null,
					dataSource);
			print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(), getBillUI()
					._getModuleCode(), getBillUI()._getOperator(), getBillUI()
					.getBusinessType(), getBillUI().getNodeKey());
			if (print.selectTemplate() == 1)
				print.preview();
		}else{
		final nc.ui.pub.print.IDataSource dataSource = new MyDuoDateSource(
				getBillUI()._getModuleCode(), getBillCardPanelWrapper()
						.getBillCardPanel());
		final nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(
				null, dataSource);
		print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(),
				getBillUI()._getModuleCode(), getBillUI()._getOperator(),
				getBillUI().getBusinessType(), getBillUI().getNodeKey());
		if (print.selectTemplate() == 1)
			print.preview();
		// ��������Դ��֧�����̴�ӡ
	//	super.onBoPrint();
		}
		Integer iprintcount = PuPubVO.getInteger_NullAs(getBufferData()
				.getCurrentVO().getParentVO().getAttributeValue(
						"cdt_pk"), 0);
		iprintcount = iprintcount + 1;
		getBufferData().getCurrentVO().getParentVO().setAttributeValue(
				"iprintcount", iprintcount);
		try {
			HYPubBO_Client.update((SuperVO) getBufferData().getCurrentVO()
					.getParentVO());
			onBoRefresh();
		} catch (final UifException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   
	   }
	}
	
	protected void onBoDel() throws Exception {
		if (getBufferData().getCurrentVO() == null)
			return;
		UFBoolean isadjust = PuPubVO.getUFBoolean_NullAs(getBufferData().getCurrentVO().getParentVO().getAttributeValue("vuserdef15"), UFBoolean.FALSE);
		if(isadjust.booleanValue()){
			getBillUI().showHintMessage("���ڵ����ĳ��ⵥ����ɾ��");
			return;
		}
		super.onBoDel();
		
	}

	
}
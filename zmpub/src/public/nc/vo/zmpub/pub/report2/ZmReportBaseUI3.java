package nc.vo.zmpub.pub.report2;
import java.util.HashMap;
import java.util.Map;
import nc.bs.logging.Logger;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.zmpub.pub.report.buttonaction2.CrossAction;
import nc.ui.zmpub.pub.report.buttonaction2.IReportButton;
import nc.ui.zmpub.pub.report.buttonaction2.LevelSubTotalAction;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
/**
 * @author mlr
 *���ӱ��������ļ��Ĺ���
 */
public class ZmReportBaseUI3 extends ZmReportBaseUI2{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6092657701658462045L;
	
	private Map<String,ReportBuffer> buff = null;// �������û���

	public ReportBuffer getBuff() {
		
		if (buff == null || buff.get(getConfigPk())==null) {		
			ReportBuffer buf= queryBuffer(getConfigPk());
			if(buf==null){
				buf=new ReportBuffer();
				buf.setNodecode(_getModelCode());
			}
			buff=new HashMap<String, ReportBuffer>();
			buff.put(getConfigPk(), buf);
		}	
		return buff.get(getConfigPk());
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
					"queryById", ParameterTypes, ParameterValues);
			if (o != null) {
				buffer = (ReportBuffer) o;
			}
		} catch (Exception e) {
			Logger.error(e);
			MessageDialog.showErrorDlg(this, "����", e.getMessage());
		}
		return buffer;
	}
	
	/**
	 * ���������ð�ť
	 */
	public  void onBoSave() {
		ConfigDlg dig=new ConfigDlg(this);
		if (dig.showModal() == nc.ui.pub.beans.UIDialog.ID_OK) {
			boolean isnew =dig.getIsNew();
			String  detatil=dig.getDetail();
			if (getBuff() == null ) {
				return;
			} else {
				ReportBuffer buff=getBuff();
				if(isnew){
					buff.setPk_config(null);
				}
				buff.setDetatil(detatil);
				
				updateBuffer(getBuff());
			}
		}
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
	
	/**
	 * �Ӳ�ѯģ���������ļ�������
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ 
	 * @ʱ�䣺2012-9-5����06:35:37
	 *
	 */
	public String getConfigPk(){
		nc.vo.pub.query.ConditionVO[] vos= getQueryDlg().getConditionVO();
		if(vos==null|| vos.length==0)
			return null;
		String pk=null;
		for(int i=0;i<vos.length;i++){
			if(vos[i].getFieldCode().startsWith("sz")){
				return vos[i].getValue();
			}
		}		
		return null;
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
			action.setUi(null);
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

}

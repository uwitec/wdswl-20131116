package nc.ui.zmpub.pub.report.buttonaction2;
/**
 * �����Ǳ���Ԥ�ð�ť����Ӧ�¼���Map,����¼�˰�ť��λ����Ϣ���Լ����ɾ����ť�Ļ���
 * ����nc.ui.pm.pub.report.ReportBaseUI�ࡣ
 * @author guanyj1
 */
import java.util.ArrayList;
import java.util.HashMap;

import nc.ui.pub.ButtonObject;
import nc.ui.report.base.EmptyAction;
import nc.ui.report.base.IButtonActionAndState;
import nc.vo.zmpub.pub.report2.ReportBaseUI;

public class ButtonAssets extends HashMap {
	
	protected ReportBaseUI reportBaseUI = null;
	//��˳���¼��ť
	protected ArrayList<Object> buttonListByOrder = new ArrayList<Object>();
	
	private HashMap<Integer, ButtonObject> m_btnhs=new HashMap<Integer, ButtonObject>();
	
	
	public ButtonAssets(ReportBaseUI reportBaseUI) {
		this.reportBaseUI = reportBaseUI;
		initAllActions();
		initButtonActionMap();
		initButtonHash();
	}
	
/**
 * 
 * date:2007-8-22 ����12:58:08
 * @author:saf
 * void
 */
	private void initButtonHash() {
		m_btnhs.put( IReportButton.QueryBtn, m_boQuery);
		m_btnhs.put(CaPuBtnConst.onboRefresh, refresh);
		m_btnhs.put(CaPuBtnConst.save, save);
		m_btnhs.put( IReportButton.FilterBtn, m_boFilter);
	    m_btnhs.put( IReportButton.CrossBtn,m_boCross);//���洦��
		m_btnhs.put( IReportButton.ColumnFilterBtn,m_boColumnFilter);//��Ŀ����
		m_btnhs.put( IReportButton.LevelSubTotalBtn,m_boLevelSubTotal);
		m_btnhs.put( IReportButton.CodeLevelSubTotalBtn,m_boCodeLevelSubTotal);
		m_btnhs.put( IReportButton.SubTotalBtn,m_boSubTotal);
		m_btnhs.put( IReportButton.SortBtn,m_boSort);//����
		m_btnhs.put( IReportButton.PrintPreviewBtn,m_boPrintPreview);
		m_btnhs.put( IReportButton.PrintDirectBtn,m_boPrintDirect);
		m_btnhs.put( IReportButton.PrintTempletBtn,m_boPrintTemplet);
		m_btnhs.put( IReportButton.PrintBtn,m_boPrint);
		m_btnhs.put( IReportButton.PenerateBtn,m_boPenerate);
	}
	
	public static final ButtonObject m_boQuery = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000006")/*@res "��ѯ"*/, 
			nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000000")/*@res "��ѯ����"*/,
			0,"��ѯ");

	//protected IButtonActionAndState = new PageAction();
	public static final ButtonObject m_boPrintPreview = new ButtonObject(
			nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000001")/*@res "��ӡԤ��"*/, 
			nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000001")/*@res "��ӡԤ��"*/, 
			0,"��ӡԤ��");

	public static final ButtonObject m_boPrintDirect = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000002")/*@res "ֱ�Ӵ�ӡ"*/,
			nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000003")/*@res "��ӡ����"*/, 0
			,"ֱ�Ӵ�ӡ");

	public static final ButtonObject m_boPrintTemplet = new ButtonObject(
			nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000004")/*@res "ģ���ӡ"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000003")/*@res "��ӡ����"*/, 0
			,"ģ���ӡ");

	public static final ButtonObject m_boPrint = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000007")/*@res "��ӡ"*/,
			new ButtonObject[] { m_boPrintPreview, m_boPrintDirect,
				m_boPrintTemplet });
	static{
		m_boPrint.setCode("��ӡ");
	}

	public static final ButtonObject m_boSort = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000005")/*@res "����"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000006")/*@res "��������"*/,
			0,"����");
	public static final ButtonObject m_boLevelSubTotal = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000007")/*@res "�ּ�С��"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000007")/*@res "�ּ�С��"*/, 0,"�ּ�С��");	/*-=notranslate=-*/
	//public ButtonObject m_boTest = new ButtonObject("����", "����", 0);
	public static final ButtonObject m_boCodeLevelSubTotal = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000008")/*@res "���뼶��С��"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000008")/*@res "���뼶��С��"*/, 0,"���뼶��С��");	/*-=notranslate=-*/

	public static final ButtonObject m_boSubTotal =
		new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000009")/*@res "С�ƺϼ�"*/, new ButtonObject[] {m_boLevelSubTotal, m_boCodeLevelSubTotal});


	public static final ButtonObject m_boColumnFilter = new ButtonObject(
			nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000010")/*@res "��Ŀ����"*/, 
			nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000010")/*@res "��Ŀ����"*/, 0
			,"��Ŀ����");

	public static final ButtonObject m_boCross = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000011")/*@res "��������"*/,
			nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000011")/*@res "��������"*/, 0,"��������");

	public static final ButtonObject m_boFilter = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000012")/*@res "����"*/,
			nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000012")/*@res "����"*/, 0,"����");
	public static final ButtonObject refresh = new ButtonObject("ˢ��", "ˢ������",CaPuBtnConst.onboRefresh);
	public static final ButtonObject save = new ButtonObject("��������", "��������",CaPuBtnConst.save);

	
	
	public static final ButtonObject m_boRefresh = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000009")/*@res "ˢ��"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000009")/*@res "ˢ��"*/,
			0,"ˢ��");

	//public static final ButtonObject m_boGroup = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000013")/*@res "����"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory_report","UPPuifactory_report-000014")/*@res "������ʾ"*/,
	//		0,"������ʾ");
	
	public static final ButtonObject m_boPenerate = new ButtonObject("��͸","˫��ҵ����/����ɴ�͸",0,"��͸");
	
	protected IButtonActionAndState a_filter = null;

	protected IButtonActionAndState a_cross = null;

	protected IButtonActionAndState a_columnFilter = null;

	protected IButtonActionAndState a_query = null;

	protected IButtonActionAndState a_printPreview = null;

	protected IButtonActionAndState a_printDirect = null;

	protected IButtonActionAndState a_printTemplet = null;

	//protected IButtonActionAndState a_totalQueryInfo = null;
	protected IButtonActionAndState a_sort = null;

	protected IButtonActionAndState a_levelSubTotal = null;

	protected IButtonActionAndState a_nextPage = null;

	protected IButtonActionAndState a_beforePage = null;

	protected IButtonActionAndState a_firstPage = null;

	protected IButtonActionAndState a_lastPage = null;
	protected IButtonActionAndState a_refresh = null;

	protected IButtonActionAndState a_codeLevelSubtotal = null;

	//protected IButtonActionAndState a_group = null;
	
	protected IButtonActionAndState a_penerate = null;
	
	private void initButtonActionMap() {
		put(m_boQuery, a_query);
		//put(m_boTotalQueryInfo, a_totalQueryInfo);
		//put(m_boRefresh, new EmptyAction());//ˢ�� ������
		put(m_boCross, a_cross);//���洦��
		put(m_boColumnFilter, a_columnFilter);//��Ŀ����
		put(m_boLevelSubTotal, a_levelSubTotal);
		put(m_boCodeLevelSubTotal, a_codeLevelSubtotal);
		put(m_boSubTotal, new EmptyAction());
		put(m_boFilter, a_filter);//��������
		//put(m_boGroup, a_group);//����
		put(m_boSort, a_sort);//����
		put(m_boPrintPreview, a_printPreview);
		put(m_boPrintDirect, a_printDirect);
		put(m_boPrintTemplet, a_printTemplet);
		put(m_boPrint, new EmptyAction());
		put(m_boPenerate,a_penerate);
	}

	private void initAllActions() {
		a_query = new QueryAction(reportBaseUI);
		a_cross = new CrossAction(reportBaseUI);
		a_filter = new FilterAction(reportBaseUI);
		a_columnFilter = new ColumnFilterAction(reportBaseUI);
		a_levelSubTotal = new LevelSubTotalAction(reportBaseUI);
		a_codeLevelSubtotal = new CodeLevelSubtotalAction(reportBaseUI);
		//a_group = new GroupAction(reportBaseUI);
		a_sort = new SortAction(reportBaseUI);
		a_printPreview = new PrintPreviewAction(reportBaseUI);
		a_printDirect =  new PrintTempletAction(reportBaseUI);
		a_printTemplet = new PrintDirectAction(reportBaseUI);
		a_penerate = new PenerateAction(reportBaseUI);
	}

	public Object put(Object key, Object value)
	{
		return put(key, value, -1);
	}
	/**
	 * �ڼ���map֮ǰ������arraylist��¼λ����Ϣ
	 *
	 * @param key
	 *            ButtonObject
	 * @param value
	 *            IButtonActionAndState
	 * @param pos
	 *            ��ťλ��
	 * @return
	 */
	public Object put(Object key, Object value, int pos)
	{
		if (key == null || value == null)
			return null;
		if (pos == -1 && !isChild((ButtonObject) key))
			buttonListByOrder.add(key);
		else if (!isChild((ButtonObject) key))
			buttonListByOrder.add(pos, key);
		return super.put(key, value);
	}
	private boolean isChild(ButtonObject o)
	{
		return o.getParent() != null;
	}
	public void clear()
	{
		buttonListByOrder.clear();
		super.clear();
	}
	public Object remove(Object key)
	{
		//�Ƴ�����ť���Ӱ�ť������
		if (isChild((ButtonObject) key))
		{
			((ButtonObject) key).getParent().removeChildButton(
					(ButtonObject) key);
		}
		else if (((ButtonObject) key).getChildCount() > 0)
		{
			buttonListByOrder.remove(key);
			ButtonObject[] btnObjs = ((ButtonObject) key).getChildButtonGroup();
			for (int i = 0; i < btnObjs.length; i++)
			{
				super.remove(btnObjs[i]);
			}
		}
		else
		{
			buttonListByOrder.remove(key);
		}
		return super.remove(key);

	}
	/**
	 * @return �����Ӱ�ť��������а�ť ��͸��ť ��Ҫ����reportBaseUI.getPene2NodeInfo() == null
	 * �ж�
	 */
	public ArrayList getVisibleButtonsByOrder()
	{
		ArrayList<ButtonObject>al=new ArrayList<ButtonObject>();
		//saf/070822/
		if(reportBaseUI.getReportButtonAry()!=null&&reportBaseUI.getReportButtonAry().length>0){
			int[] buttonary=reportBaseUI.getReportButtonAry();
			for(int i=0;i<buttonary.length;i++){
				if(m_btnhs.containsKey(buttonary[i])){
					al.add(m_btnhs.get(buttonary[i]));
				}
			}
		}
		
		if(reportBaseUI.getPene2NodeInfo() != null){
			al.add(m_boPenerate);
		}
		
		return al;
		
		//guanyj begin
		//if(reportBaseUI.getPene2NodeInfo() == null){
		//	buttonListByOrder.remove(m_boPenerate);
		//}
		//return buttonListByOrder;
		//end
	}
	
	public HashMap getButtonMap(){
		return m_btnhs;
	}
	
}

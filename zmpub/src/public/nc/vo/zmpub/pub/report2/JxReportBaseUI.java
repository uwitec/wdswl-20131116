package nc.vo.zmpub.pub.report2;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ListSelectionModel;

import nc.bs.logging.Logger;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.report.ReportItem;
import nc.ui.trade.report.query.QueryDLG;
import nc.ui.zmpub.pub.report.buttonaction2.CaPuBtnConst;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.query.ConditionVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.zmpub.pub.report.ReportBaseVO;
/**
 * @���ߣ�mlr
 *        ֧��ui��ʼ��ʱ���ض�̬��
 *        ֧�ֲ�ѯ�������ɶ�̬��  ����̬������չ��
 * @ʱ�䣺2011-7-8����03:20:53
 */
abstract public class JxReportBaseUI extends ReportBaseUI{	
	private static final long serialVersionUID = -8293771841532487812L;  
    protected  ReportItem[]olditems=null; //��¼���μ��صı���Ԫ��  
    private  Integer location=0;//��ѯ��̬�еĲ���λ�� Ĭ�ϲ����0��
    
    
    private static String sql =null;
	public JxReportBaseUI() {
		super();		
		initReportUI();
		setDynamicColumn();
	}
	
	protected void initPrivateButton() {		 
		addPrivateButton("ˢ��", "ˢ������",CaPuBtnConst.onboRefresh);
		
	 }
	  /**
     * ִ�й�ʽ�� ��billmodel�����ݸ��µ�vo��
     * @author mlr
     * @˵�������׸ڿ�ҵ��
     * 2011-12-22����09:26:46
     */
	public void updateVOFromModel() {	
		    getReportBase().getBillModel().execLoadFormula();
	        getReportBase().getBillModel().updateValue();			
			ReportBaseVO[]  vos=new ReportBaseVO[getReportBase().getBillModel().getRowCount()];
			for(int i=0;i<vos.length;i++){
				vos[i]=new ReportBaseVO();
			}			
			getReportBase().getBillModel().getBodyValueVOs(vos);
			setBodyDataVO(vos, true);		
	}
	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ 
	 *       ��ʼ��ui��
	 * @ʱ�䣺2011-7-15����01:47:25
	 */
	public abstract void initReportUI();	
    /**
     * 
     * @���ߣ�mlr
     * @˵�������ɽ������Ŀ 
     *        ���ò�ѯ��̬��λ��
     * @ʱ�䣺2011-7-15����08:08:09
     * @param location1
     */
	public void setLocation(Integer location) {
		this.location=location;
	}
	@Override
	public ReportBaseVO[] getReportVO(String sql) throws BusinessException {
		 ReportBaseVO[] reportVOs = null;
	        try{
	        	this.sql=sql;
	            Class[] ParameterTypes = new Class[]{String.class};
	            Object[] ParameterValues = new Object[]{sql};
	            Object o = LongTimeTask.calllongTimeService("zmpub", this, 
	                    "���ڲ�ѯ...", 1, "nc.bs.wds.pub.report.ReportDMO", null, 
	                    "queryVOBySql", ParameterTypes, ParameterValues);
	            if(o != null){
	                reportVOs = (ReportBaseVO[])o;
	            }
	        }catch(Exception e){
	            Logger.error(e);
	            MessageDialog.showErrorDlg(this, "����", e.getMessage());
	        }
	        return reportVOs;
	}
	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ 
	 *       ����̬���뾲̬�� �ϲ�
	 * @ʱ�䣺2011-7-8����03:30:07
	 * @param olditems һ����Ϊ�Ǿ�̬��
	 * @param newitems һ����Ϊ�Ƕ�̬��
	 * @return
	 */
	protected ReportItem[] combin(ReportItem[] olditems, ReportItem[] newitems,int location) {
		if(newitems==null || newitems.length==0){
		  return olditems;	
		}
		ReportItem[] its=new ReportItem[olditems.length+newitems.length];
		System.arraycopy(olditems, 0, its, 0, location);
	    System.arraycopy(newitems, 0, its,location,newitems.length);
	    System.arraycopy(olditems, location, its, location+newitems.length, olditems.length-location);
		return its;
	}
    /**
     * 
     */
    protected void onBoElse(Integer intBtn) throws Exception {
    	switch (intBtn) {
		  case CaPuBtnConst.onboRefresh: {			
			onboRefresh();
			break;
		  }
		}    	
    	super.onBoElse(intBtn);	
    }
    public void onboRefresh()throws Exception{
      	   //��ձ�������
    	   clearBody();	    	         	
      	  //���ö�̬��
    	  setDynamicColumn1();
          //�õ���ѯ���
      	  ReportBaseVO[] vos=null;
    	  //���û����кϲ�
    	   setColumn();
    	  //����vo
    	  if(sql==null || sql.length()==0)
    		  return;
 		  vos=getReportVO(sql);		       	 
 		 vos=getReportVO(getQuerySQL());		       	 
         if(vos==null || vos.length==0)
        	 return;
       ReportBaseVO[]vos1=dealBeforeSetUI(vos);
         if(vos1 != null){                	
			super.updateBodyDigits();
			setBodyVO(vos1);		
			updateVOFromModel();//���¼��س�ʼ����ʽ  
			dealQueryAfter();//��ѯ��ĺ������� һ������ Ĭ�����ݽ���֮��Ĳ���
           // setTolal();	                
         }
         setQueryAfter(); 	        
         this.showHintMessage("ˢ�²�������");
    }
	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ 
	 *      ���ò�ѯ���ɵĶ�̬��
	 * @ʱ�䣺2011-7-15����01:10:00
	 */
	public void setDynamicColumn1() {
		 ReportItem[] newitems;
			try {
			        newitems = getNewItems1();
				    ReportItem[] allitems = combin(olditems,newitems,location);        
			        getReportBase().setBody_Items(allitems);
			        updateUI();
			} catch (Exception e) {
				e.printStackTrace();
			}           		
	}
	/**
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ 
	 *        ��ò�ѯ��̬��Ԫ��
	 * @ʱ�䣺2011-7-8����09:54:00
	 * @return
	 * @throws Exception 
	 */
	public ReportItem[] getNewItems1() throws Exception {
	 List<ReportItem> list=new ArrayList<ReportItem>();
	 ConditionVO[] vos=getQueryDlg().getConditionVO();
     if(vos==null || vos.length==0)
    	 return null; 
     for(int i=0;i<vos.length;i++){
    	if(vos[i].getFieldCode().startsWith("is")){
    	  if(PuPubVO.getUFBoolean_NullAs(vos[i].getValue(), UFBoolean.FALSE).booleanValue()==true){
    		  ReportItem it=ReportPubTool.getItem(vos[i].getFieldCode().substring(2),getSpitName(vos[i].getFieldName()),IBillItem.STRING,2, 80);
    		  list.add(it);
    	      setItemsAfter(it,list);
    	  }
    	}  
	 }
     if(list.size()==0){
    	 return null; 
     }else{
    	return list.toArray(new ReportItem[0]); 
     }	
	}
	/**
	 * ���뽫Ҫչ�����ֶ�  �ڱ��β�ѯ��
	 * ��һЩ��������
	 * @param it Ϊ��ǰ��Ҫչ���� ReportItem
	 * @param list װ�ص�ǰ��ѯ�������ɵ� ��̬�м��� 
	 */
	public void setItemsAfter(ReportItem it, List<ReportItem> list) {
		
	}
	/**
	 * ��ȡ�����ֶε�������
	 * ���� ���Ƿ񰴻�λչ��
	 * �� ȡ��λ �ӡ�������ʼȡֵ ����չ������  
	 * @author mlr
	 * @˵�������׸ڿ�ҵ��
	 * 2011-10-19����02:14:16
	 * @param name
	 * @return
	 */
    private String getSpitName(String name){
    	if(name==null || name.length()==0){
    		return null;
    	}
    	return name.substring(2,name.lastIndexOf("չ��"));
    }
    /**
     * �������ݴ���Ĳ���
     * (�������ݵ�ui֮ǰ����)
     * ��ѯ
     * 
     * ����(һ�㰴 �����������ڱ��id�������ݹ���)
     * 
     * ����
     * 
     * �ϲ�
     * 
     * ����
     * 
     * ׷��
     * (�������ݵ�ui֮����)
     * ����  
     * 
     * ���δ���
     * 
     * �ϼ�
     *
     */
	@Override
	public void onQuery() {
		getQueryDlg().showModal();
	     if (getQueryDlg().getResult() == UIDialog.ID_OK) {		  
	      try {
	    	 //��ձ�������
	    	 clearBody();	    	         	
        	//���ö�̬��
        	setDynamicColumn1();
            //�õ���ѯ���
        	ReportBaseVO[] vos=null;
        	//���û����кϲ�
        	 setColumn();
        	 //����vo
			 vos=getReportVO(getQuerySQL());		       	 
             if(vos==null || vos.length==0)
            	 return;
	       ReportBaseVO[]vos1=dealBeforeSetUI(vos);
             if(vos1 != null){                	
				super.updateBodyDigits();
				setBodyVO(vos1);		
				updateVOFromModel();//���¼��س�ʼ����ʽ  
				dealQueryAfter();//��ѯ��ĺ������� һ������ Ĭ�����ݽ���֮��Ĳ���
	           // setTolal();	                
             }
             setQueryAfter();
	  	} catch (BusinessException e) {
			e.printStackTrace();
			this.showErrorMessage(e.getMessage());

		} catch (Exception e) {			
			e.printStackTrace();
			this.showErrorMessage(e.getMessage());

		} 
	   }  
	}	
	public void dealQueryAfter() throws Exception {
		
	}

	public ReportBaseVO[] dealBeforeSetUI(ReportBaseVO[] vos) throws Exception{
		return vos;
	}

	public void setQueryAfter() throws Exception{
		
	}

	/**
     * �ϼ�
     */
    public void setTolal() throws Exception {
 
    }
	 /**
     * �����кϲ�
     */
    protected void setColumn() {
    	   
    }
	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ 
	 *        ÿ�β�ѯǰ��ձ�������
	 * @ʱ�䣺2011-7-16����02:38:27
	 */
	public void clearBody() {
		 setBodyVO(null);
    	 updateUI();		
	}
	@Override
	public void setUIAfterLoadTemplate() {
		
		
	}
	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ 
	 *        ���ö�̬��
	 *        �������ʵ��getNewItems()����
	 * @ʱ�䣺2011-7-8����03:22:30
	 */	
	public void setDynamicColumn() {
		 //-----------------------���� ģ��  ֧�ֶ�̬��      
		 getReportBase().getBillTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);	               
	        olditems = getReportBase().getBody_Items();       
	        ReportItem[] newitems=null;
			try {  
			        Map map = getNewItems();
			        Integer location=0;//��ʼ����̬�м���
			        if(map !=null){
			        location=PuPubVO.getInteger_NullAs(map.get("location"), new Integer(0));
			        newitems=(ReportItem[])map.get("items");
			        }
				    ReportItem[] allitems = combin(olditems,newitems,location);   
				    olditems=allitems;
			        getReportBase().setBody_Items(allitems);
			        updateUI();
			} catch (Exception e) {
				e.printStackTrace();
			}            		
	}
    /**
     * 
     * @���ߣ�mlr
     * @˵�������ɽ������Ŀ 
     *        ��ȡ����ģ���ʼ��ʱ�Ķ�̬��
     *        map key=location ��Ŷ�̬�в���λ��
     *            key=items    ��Ŷ�̬��Ԫ��
     * @ʱ�䣺2011-7-15����02:21:31
     * @return
     */
	abstract public Map getNewItems()throws Exception ;
	/**
	 * �ӶԻ����ȡ��ѯ����
	 * @author mlr
	 * @˵�������׸ڿ�ҵ��
	 * 2011-10-19����01:53:31
	 * @return
	 * @throws Exception
	 */
	protected String getQueryConditon()
	throws Exception {
		QueryDLG querylg= getQueryDlg();//��ȡ��ѯ�Ի���		
		ConditionVO[] vos=querylg.getConditionVO();//��ȡ�ѱ��û���д�Ĳ�ѯ����
		ConditionVO[] vos1=filterQuery(vos);
		String sql=querylg.getWhereSQL(vos1);
		return sql;
  } 	
/**
 * ���˵�չ��������
 * @author mlr
 * @˵�������׸ڿ�ҵ��
 * 2011-10-19����02:46:14
 * @param vos
 * @return
 */
  public ConditionVO[] filterQuery(ConditionVO[] vos) {
	   List<ConditionVO> list=new ArrayList<ConditionVO>();
	   if(vos==null || vos.length==0)
		   return null;
	   for(int i=0;i<vos.length;i++){		   
	      if(vos[i].getFieldCode().startsWith("is") || vos[i].getFieldCode().startsWith("sz")){
	    	  continue;
	      }
	      list.add(vos[i]);
	   }
	   if(list.size()==0)
		   return null;
		return list.toArray(new ConditionVO[0]);
  }
  /**
   * ���˳���չ��������
   * @author mlr
   * @˵�������׸ڿ�ҵ��
   * 2011-10-19����02:46:14
   * @param vos
   * @return
   */
    private ConditionVO[] filterOrderBy(ConditionVO[] vos) {
  	   List<ConditionVO> list=new ArrayList<ConditionVO>();
  	   if(vos==null || vos.length==0)
  		   return null;
  	   for(int i=0;i<vos.length;i++){		   
  	      if(vos[i].getFieldCode().startsWith("is")){
  	    	list.add(vos[i]);
  	      } 	      
  	   }
  	   if(list.size()==0)
  		   return null;
  		return list.toArray(new ConditionVO[0]);
    }
    /**
     * @���ߣ�mlr
     * @˵�������ɽ������Ŀ  
     *  �̳���JxReportBaseUI����� �˷�����д�淶
     *  Ҫ��֧�� ��ĳ���ֶ�չ��
     *  ���� �ڲ�ѯģ�� ����չ���ֶ�  ��is��ͷ  ֮���Ǳ��Ӧ��Ҫչ���ֶε�����  ����Ϊboolean����
     *  ������  Ϊ �Ƿ� ��xx�� չ��  
     *  �����ѯ�� ѡ�� �� �Ƿ񰴻�λչ�� �� ȡ'��λ'�ֶ���Ϊ����Ĳ�ѯ��̬�е� ��������
     *  getGroupByOrSelectConditon �˷������ ��ѯ��ȷ���� �����Ѿ�ȷ����չ�����ֶ�
     * @ʱ�䣺2011-5-10����09:41:31
     * 
     */
	
	public abstract String getQuerySQL() throws Exception;
/**
  * ��ȡ��Ҫչ�����ֶ� �� ��ȷ��������Ҫ��ѯ���ֶ�
  * �ڲ�ѯ���� ����  ������is��ͷ����������ݿ��Ӧ�ֶε�����
  * @author mlr
  * @˵�������׸ڿ�ҵ��
  * 2011-10-19����01:55:14
  * @return
  * @throws Exception
  */
  protected String getGroupByOrSelectConditon()
	throws Exception {
		StringBuffer sql=new StringBuffer();
		QueryDLG querylg= getQueryDlg();//��ȡ��ѯ�Ի���	
		ConditionVO[] vos=querylg.getConditionVO();//��ȡ�ѱ��û���д�Ĳ�ѯ����
		ConditionVO[] vos1=filterOrderBy(vos);
		if(vos1==null || vos1.length==0)
			return null;
		for(int i=0;i<vos1.length;i++){	
		
			    if(PuPubVO.getUFBoolean_NullAs(vos1[i].getValue(), UFBoolean.FALSE).booleanValue()==true){
			    	sql.append(vos1[i].getFieldCode().substring(2));
			    	if(i!=vos1.length-1){
			    	sql.append(" ,");
			    	}
			    }
					
	   }   
		return sql.toString();
  } 
}

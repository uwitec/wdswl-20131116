package nc.bs.zmpub.pub.excel;

import java.util.ArrayList;
import java.util.List;
import nc.bs.pf.pub.PfDataCache;
import nc.bs.pub.pf.PfUtilBO;
import nc.bs.pub.pf.PfUtilTools;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.zmpub.excel.CodeToIDInfor;
import nc.vo.zmpub.excel.ExcelToBillConst;
import nc.vo.zmpub.pub.bill.MyBillVO;
import nc.vo.zmpub.pub.report.ReportBaseVO;

/**
 * Excel���ݴ���
 * 
 * @author zhf
 */
public abstract class AbstractExcetBO {

	/**
	 * �Ե���excel��������ݽ��к�̨����
	 * @param vos
	 * @throws BusinessException
	 */
	public void dealSingleImportDatas(CircularlyAccessibleValueObject[] vos) throws BusinessException{
		if(vos == null || vos.length == 0)
			return;
		check(vos);
		TransCodeToIDBO.getInstance().transCodeToID(vos, getBodyTransFieldInfor());
		doSingleSave(vos);		
	}
	
	/**
	 * ���嵼������У��  ��������У�� �����Ӱ������ı���ת��
	 * @param vos
	 * @throws ValidationException
	 */
	protected abstract void check(CircularlyAccessibleValueObject[] vos) throws ValidationException;
	
	/**
	 * ���嵼������ݱ���  ������Ի�ʵ��
	 * @param vos
	 * @throws BusinessException
	 */
	protected abstract void doSingleSave(CircularlyAccessibleValueObject[] vos) throws BusinessException;
	
	public void dealBillImportDatas(ReportBaseVO[] rvos,String billtype,String tmpSourBilltype) throws Exception{
		if(rvos == null || rvos.length == 0)
			return;
		if(PuPubVO.getString_TrimZeroLenAsNull(billtype) == null)
			throw new BusinessException("��������Ϊ��");
		if(PuPubVO.getString_TrimZeroLenAsNull(tmpSourBilltype) == null){
			tmpSourBilltype = "EXCEL";
		}
		
//		����ת��
		AggregatedValueObject[] bills = transData(rvos,billtype);
		
		AggregatedValueObject[] newBills = PfUtilTools.runChangeDataAry(tmpSourBilltype, billtype, bills);
		
		if(newBills == null || newBills.length == 0)
			throw new BusinessException("����ת���쳣");
		
//		����У��
		check(newBills);
//		����ת��
		transCodeToID(newBills);
//		���ݲ���
		appData(newBills);
//		���±���
		pushBillSave(newBills,billtype);
//		�������
		afterSave(newBills);
	}
	
	protected abstract void afterSave(AggregatedValueObject[] newBills);
	
	/**
	 * ���±���
	 * @param newBills
	 * @param billtype
	 * @throws Exception
	 */
	protected void pushBillSave(AggregatedValueObject[] newBills,String billtype) throws Exception{
		PfUtilBO pfbo = new PfUtilBO();
		if (isPushSaveBatch()) {
			pfbo.processBatch(getPushSaveActionName(), billtype, new UFDate(
					System.currentTimeMillis()).toString(), newBills, null, null);
		}else{
			String date = new UFDate(System.currentTimeMillis()).toString();
			for(AggregatedValueObject newBill:newBills){
				pfbo.processAction(getPushSaveActionName(), billtype, date, null, newBill, null);
			}
		}
	}
	
	/**
	 * ��ȡ���幫˾�ֶ����� �������û�й�˾ʵ���ֶ� �� ���������ڹ�˾������ת�� ���ṩ��ʱ��˾�ֶ�
	 * @return
	 */
	protected abstract String getBillBodyCorpFieldName();
	
	/**
	 * ��ȡ��ͷ��˾�ֶ�  �������д
	 * @return
	 */
	protected  String getBillHeadCorpFieldName(){
		return "pk_corp";
	}
	
	
	/**
	 * ���ݽṹ��������  ����ת��
	 * @param newBills
	 * @throws BusinessException
	 */
	protected  void transCodeToID(AggregatedValueObject[] newBills) throws BusinessException{
		CircularlyAccessibleValueObject[] heads = new CircularlyAccessibleValueObject[newBills.length];
		int index = 0;
		CircularlyAccessibleValueObject[] bodys = null;
		for(AggregatedValueObject newBill:newBills){
			heads[index] = newBill.getParentVO();
			bodys = newBill.getChildrenVO();
			if(bodys == null || bodys.length == 0)
				continue;
			for(CircularlyAccessibleValueObject body:bodys){
				body.setAttributeValue(getBillBodyCorpFieldName(), heads[index].getAttributeValue(getBillHeadCorpFieldName()));
			}
			TransCodeToIDBO.getInstance().transCodeToID(bodys, getBodyTransFieldInfor());
			index++;
		}
		
		TransCodeToIDBO.getInstance().transCodeToID(heads, getHeadTransFieldInfor());
		for(String key:TransCodeToIDBO.getInstance().map.keySet()){
			System.out.println(key);
		}
		
		TransCodeToIDBO.getInstance().clearCathe();
	}
	
	/**
	 * ��ȡ��ͷ��Ҫ���б���ת��ID���ֶ�
	 * @return
	 */
	protected abstract CodeToIDInfor[] getHeadTransFieldInfor();
	/**
	 * ��ȡ������Ҫ���б���ת��ID���ֶ�  ���δ����ʱ  Ϊ������Ҫ���б���ת��ID���ֶ�
	 * ����������  ��˾�� ���� �����ṩ  ��˾����� ת��  ������vo������ ��˾  ����������ʱ�ֶ� ���ڹ�˾
	 * �����޷���ɹ�˾�����������ID��ת��
	 * @return
	 */
	protected abstract CodeToIDInfor[] getBodyTransFieldInfor();
	protected abstract boolean isPushSaveBatch();
	protected abstract String getPushSaveActionName() throws BusinessException;
	protected abstract void appData(AggregatedValueObject[] newBills) throws BusinessException;
	
	protected abstract void check(AggregatedValueObject[] newBills) throws ValidationException;
	
	protected abstract void beForeTransData(ReportBaseVO[] rvos);//֧��������չ����	
    protected abstract void afterTransData(ReportBaseVO[] rvos,AggregatedValueObject[] bills);//֧��������չ����		
    
    protected String getHeadFlag(){
    	return ExcelToBillConst.excel_head_flag_field;
    }
	
    /**
     * �����ݷ�ת�ɱ�׼�ۺ�vo
     * @param rvos
     * @param billtype
     * @return
     * @throws BusinessException
     */
	protected AggregatedValueObject[] transData(ReportBaseVO[] rvos,String billtype) throws BusinessException{
		beForeTransData(rvos);
		
		String[] voNames = PfDataCache.getStrBillVo(billtype);
		if(voNames == null || voNames.length == 0){
			throw new BusinessException("vo��Ϣδע��");
		}
		
		if(!PuPubVO.getUFBoolean_NullAs(rvos[0].getAttributeValue(getHeadFlag()), UFBoolean.FALSE).booleanValue()){
			throw new BusinessException("��ͷ��ϢΪ��");
		}
		
		List<MyBillVO> lbill = new ArrayList<MyBillVO>();
		MyBillVO bill = null;
		for(ReportBaseVO rvo:rvos){			
			if(PuPubVO.getUFBoolean_NullAs(rvo.getAttributeValue(getHeadFlag()), UFBoolean.FALSE).booleanValue()){
				bill = new MyBillVO();
				bill.setParentVO(rvo);
				lbill.add(bill);
			}else{
				lbill.get(lbill.size()-1).getLbody().add(rvo);
			}
			
		}
		
		if(lbill.size() <= 0)
			return null;
		
		for(MyBillVO tmpBill:lbill){
			List<CircularlyAccessibleValueObject> lbody = tmpBill.getLbody();
			if(lbody == null || lbody.size() == 0)
				continue;
			
			tmpBill.setChildrenVO(lbody.toArray(new CircularlyAccessibleValueObject[0]));
		}			
		
		if(lbill==null || lbill.size() == 0)
			return null;
		AggregatedValueObject[] bills = lbill.toArray(new AggregatedValueObject[0]);
		
		afterTransData(rvos,bills);
		return bills;
	}	
	
	
	
	
	
	
	
	
//	
//	
////---------------------------------------------������mlr��----------------------------------------------------
//	/**
//	 * ��excelt ����vo�� �ڳ�����vo
//	 * 
//	 * @author mlr
//	 * @˵�������׸ڿ�ҵ�� 2011-11-4����03:21:24
//	 * @param vos
//	 * @param m_user
//	 * @param logDate
//	 * @return
//	 * @throws Exception
//	 */
//	public SuperVO[] excelChangeToVO(String returnClass, ReportBaseVO[] vos,
//			String[] fieldsNames, String queryIds[], String queryTables[],
//			String querySelectIDs[], String[] queryCodeNames,
//			boolean[] isVlCorp, boolean[] isMutiTables, String[] setValueIds,
//			String pk_corp, String m_user, String logDate) throws Exception {
//		if (vos == null || vos.length == 0)
//			return null;
//		List<SuperVO> list = new ArrayList<SuperVO>();
//		String[] chafields = getNotQueryFields(queryIds, fieldsNames);
//		Class cl = Class.forName(returnClass);
//		for (int i = 0; i < vos.length; i++) {
//			SuperVO vo = (SuperVO) Class.forName(returnClass).newInstance();
//			for (int j = 0; j < queryIds.length; j++) {
//				String code = PuPubVO.getString_TrimZeroLenAsNull(vos[i]
//						.getAttributeValue(queryIds[j]));
//				String id = (String) querypkall(querySelectIDs[j],
//						queryTables[j], queryCodeNames[j], code, isVlCorp[j],
//						pk_corp, isMutiTables[j]);
//				vo.setAttributeValue(setValueIds[j], id);
//			}
//			for (int l = 0; l < chafields.length; l++) {
//				Object value = vos[i].getAttributeValue(chafields[l]);
//				vo.setAttributeValue(chafields[l], value);
//			}
//			vo.setAttributeValue("pk_corp", pk_corp);
//			list.add(vo);
//		}
//		if (list.size() != 0)
//			return list.toArray((SuperVO[]) java.lang.reflect.Array
//					.newInstance(cl, 0));
//		return null;
//	}
//
//	private String[] getNotQueryFields(String[] queryIds, String[] fields)
//			throws Exception {
//		List<String> list = new ArrayList<String>();
//		for (int i = 0; i < fields.length; i++) {
//			boolean flag = false;
//			for (int j = 0; j < queryIds.length; j++) {
//				if (fields[i].equalsIgnoreCase(queryIds[j])) {
//					flag = true;
//					break;
//				}
//			}
//			if (flag == false) {
//				list.add(fields[i]);
//			}
//		}
//		if (list.size() != 0)
//			return list.toArray(new String[0]);
//		return null;
//	}
//
//	/**
//	 * liuys ���ݸ��ֵ��������ѯ��pKֵ
//	 * 
//	 * @param tablename
//	 * @param selectname
//	 * @param passvalue
//	 * @param isMutitable
//	 *            �Ƿ���ű��� ����� �Ͳ��ܹ���dr��
//	 * @return
//	 * @throws DAOException
//	 */
//	public Object querypkall(String selectname, String tablename,
//			String codename, String passvalue, boolean bag, String pk_corp,
//			boolean isMutitable) throws DAOException {
//		String sqlpkcorp = "select " + selectname + " from " + tablename
//				+ " where " + codename + " = '" + passvalue + "'";
//		if (isMutitable == false) {
//			sqlpkcorp = sqlpkcorp + "  and isnull(dr,0)=0 ";
//		}
//		if (bag == true)
//			sqlpkcorp += " and pk_corp = '" + pk_corp + "'";
//		else {
//			sqlpkcorp += " ;";
//		}
//		Object obj = getDAO().executeQuery(sqlpkcorp,
//				ResultSetProcessorTool.COLUMNPROCESSOR);
//		return obj;
//	}

}

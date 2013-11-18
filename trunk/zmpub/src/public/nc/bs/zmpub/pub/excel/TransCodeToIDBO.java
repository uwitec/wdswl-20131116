package nc.bs.zmpub.pub.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.zmpub.excel.CodeToIDInfor;
import nc.vo.zmpub.excel.IDefTran;
import nc.vo.zmpub.pub.tool.ResultSetProcessorTool;
import nc.vo.zmpub.pub.tool.ZmPubTool;

public class TransCodeToIDBO {

	//����+��˾ID   ---->  ID       ���ձ�  ԭ����Ϊ����ȫϵͳΨһ   ���ܴ��ڱ��빫˾��Ψһ
	private java.util.Map<String,String> codeIdMap = new HashMap<String, String>();
	
	private static TransCodeToIDBO tb = new TransCodeToIDBO();
	
	private BaseDAO dao = null;

	private BaseDAO getDAO() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}
	
	private TransCodeToIDBO(){
		super();
	}
	
	public static TransCodeToIDBO getInstance(){
		return tb;
	}
	
	public void clearCathe(){
		codeIdMap.clear();
	}
	
	private Map<String, IDefTran> defTranBoMap = null;
	
	Map<String, IDefTran> getDefTranBoMap(){//��֤  �Զ���ת���� �ǵ���
		if(defTranBoMap == null)
			defTranBoMap = new HashMap<String, IDefTran>();
		return defTranBoMap;
	}
	private IDefTran getDefTranTool(CodeToIDInfor infor) throws BusinessException{
		if(PuPubVO.getString_TrimZeroLenAsNull(infor.getDefTranClassName())==null)
			throw new BusinessException("����["+infor.getThiscodename()+"]�Զ���ת����δע��");
		
		String className = infor.getDefTranClassName().trim();
		if(getDefTranBoMap().containsKey(className))
			return getDefTranBoMap().get(className);
		try {
			IDefTran tool = (IDefTran)Class.forName(className).newInstance();
			getDefTranBoMap().put(className, tool);
			return tool;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			throw new BusinessException(e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new BusinessException(e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new BusinessException(e);
		}
	}
	
	protected  String defTran(CircularlyAccessibleValueObject vo,CodeToIDInfor infor) throws BusinessException{
		String value =  null;
		IDefTran tranTool =  getDefTranTool(infor);
		value = tranTool.transCodeToID(getDAO(),vo,infor);
		return value;
	}
	
   public static Map<String,String> map=new HashMap<String,String>();
	
	/**
	 * ��ȡIDֵ
	 * @param infor
	 * @return
	 * @throws BusinessException
	 */
	private String getInforValue(CircularlyAccessibleValueObject vo,CodeToIDInfor infor)
	throws BusinessException {

		String key = null;
		if(infor.getIsCorp().booleanValue()){
			//			����+��˾ID  ��Ϊkey
			key = infor.getCodevalue()+infor.getCorpvalue();
		}else{
			key = infor.getCodevalue();
		}

		if (codeIdMap.containsKey(key))
			return codeIdMap.get(key);

		String value = null;
		if(infor.isDefTran.booleanValue()){
			value = defTran(vo, infor);
			if(value==null || value.length()==0)
				map.put(infor.getCodevalue(), "");
			
		}else if (infor.getIsBasic().booleanValue()) {//��׼��Ʒ����������ͨ����ʾ��ȡֵ  Ч�ʽϸ�
			String fou = infor.getFomular();
			if(infor.isCorp.booleanValue()){
				value = ZmPubTool.getString_NullAsTrimZeroLen(ZmPubTool
						.execFomular(fou, new String[] { infor.getThiscodename(),infor.getCorpname() },
								new String[] { infor.getCodevalue() ,infor.getCorpvalue()}));
			}else{
			value = ZmPubTool.getString_NullAsTrimZeroLen(ZmPubTool
					.execFomular(fou, new String[] { infor.getThiscodename() },
							new String[] { infor.getCodevalue() }));
			}

		} else {
			String sql = infor.getSelectSql();
			value = ZmPubTool.getString_NullAsTrimZeroLen(getDAO()
					.executeQuery(sql, ResultSetProcessorTool.COLUMNPROCESSOR));
		}

		if(infor.isCache.booleanValue())
			codeIdMap.put(key, value);
		return value;
	}


	/**
	 * ����ת��ΪID  ����excel����   �����ֶκ�ID�ֶ�  �� ����vo�� ռ��ͬһ���ֶΣ�
	 * ��ת��������Ҫ�������ڴ�����Ĺ�˾���룻ʵ�ʲ���ҪҲ���������ṩ��
	 * �����ת���ı������Ϊ��˾����  Ҳ���Բ��ṩ��˾�����ֶΣ������ݱ�ͷ�й�˾�����޹�˾��������ת��ʱ���ڹ�˾������
	 * �Ļ��������⴫�빫˾�������
	 * @author zhf
	 * @param vos
	 * @param infors
	 * @throws BusinessException
	 */
	protected  void transCodeToID(CircularlyAccessibleValueObject[] vos,CodeToIDInfor[] infors) throws BusinessException{

//		ת����ʼ
		String tmpValue = null;
		CodeToIDInfor corpInfor = new CodeToIDInfor();
		for (CircularlyAccessibleValueObject vo : vos) {

			// ��Ҫȷ����ǰvo�������ڵĹ�˾ �����ǵ��뵽�Ǹ���˾�� ���뵽��ͬ�Ĺ�˾ ��������Ӧ����ͬ��˾����
			// ����ת����˾ ����� ���ݱ������� û��ʵ�ʹ�˾�ֶε� ҲӦ�ṩ���⹫˾�ֶ� �ڴ˴���ʱʹ��
			for (CodeToIDInfor infor : infors) {
				if (infor.isCorpField.booleanValue()) {
					//					���湫˾��Ϣ
					corpInfor.setCorpname("pk_corp");
					corpInfor.setCorpvalue("1021");
//					if(infor.isSave.booleanValue())
//					//	vo.setAttributeValue(infor.getThiscodename(), tmpValue);// Ϊ��˾�ֶθ���ID
//					else 
//						vo.setAttributeValue(infor.getThiscodename(), null);
					break;
				}
			}
			if(vo==null){
				continue;
			}

			for (CodeToIDInfor infor : infors) {
				if (infor.isCorpField.booleanValue())
					continue;
				
				if(!infor.isSave.booleanValue()){
					vo.setAttributeValue(infor.getThiscodename(), null);//����ֶ�ֵ
					continue;
				}
				
				if(PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue(infor.getThiscodename()))==null)
					continue;

				infor.setCorpvalue(corpInfor.getCorpvalue());//��˾IDֵ
				//				��ǰ����ֵ
				infor.setCodevalue(ZmPubTool.getString_NullAsTrimZeroLen(vo.getAttributeValue(infor.getThiscodename())));
			//	if(PuPubVO.getString_TrimZeroLenAsNull(infor.getCorpname())==null)
				//	infor.setCorpname(corpInfor.getCorpname());

				tmpValue = getInforValue(vo,infor);
				if(infor.isSave.booleanValue())
					vo.setAttributeValue(infor.getThiscodename(), tmpValue);// Ϊ��˾�ֶθ���ID
			}			
		}
	}
}

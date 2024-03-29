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

	//编码+公司ID   ---->  ID       对照表  原则认为编码全系统唯一   可能存在编码公司级唯一
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
	
	Map<String, IDefTran> getDefTranBoMap(){//保证  自定义转换类 是单例
		if(defTranBoMap == null)
			defTranBoMap = new HashMap<String, IDefTran>();
		return defTranBoMap;
	}
	private IDefTran getDefTranTool(CodeToIDInfor infor) throws BusinessException{
		if(PuPubVO.getString_TrimZeroLenAsNull(infor.getDefTranClassName())==null)
			throw new BusinessException("编码["+infor.getThiscodename()+"]自定义转换类未注册");
		
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
	 * 获取ID值
	 * @param infor
	 * @return
	 * @throws BusinessException
	 */
	private String getInforValue(CircularlyAccessibleValueObject vo,CodeToIDInfor infor)
	throws BusinessException {

		String key = null;
		if(infor.getIsCorp().booleanValue()){
			//			编码+公司ID  作为key
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
			
		}else if (infor.getIsBasic().booleanValue()) {//标准产品基本档案可通过公示获取值  效率较高
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
	 * 编码转换为ID  用于excel导入   编码字段和ID字段  在 数据vo中 占用同一个字段；
	 * 待转换的数据要求必须存在待导入的公司编码；实际不需要也必须虚拟提供；
	 * 如果待转换的编码均不为公司级的  也可以不提供公司编码字段；但单据表头有公司表体无公司表体数据转换时存在公司级档案
	 * 的话必须虚拟传入公司编码对照
	 * @author zhf
	 * @param vos
	 * @param infors
	 * @throws BusinessException
	 */
	protected  void transCodeToID(CircularlyAccessibleValueObject[] vos,CodeToIDInfor[] infors) throws BusinessException{

//		转换开始
		String tmpValue = null;
		CodeToIDInfor corpInfor = new CodeToIDInfor();
		for (CircularlyAccessibleValueObject vo : vos) {

			// 需要确定当前vo数据所在的公司 数据是导入到那个公司的 导入到不同的公司 档案翻译应按不同公司翻译
			// 优先转换公司 如果是 单据表体数据 没有实际公司字段的 也应提供虚拟公司字段 在此处临时使用
			for (CodeToIDInfor infor : infors) {
				if (infor.isCorpField.booleanValue()) {
					//					缓存公司信息
					corpInfor.setCorpname("pk_corp");
					corpInfor.setCorpvalue("1021");
//					if(infor.isSave.booleanValue())
//					//	vo.setAttributeValue(infor.getThiscodename(), tmpValue);// 为公司字段附上ID
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
					vo.setAttributeValue(infor.getThiscodename(), null);//清空字段值
					continue;
				}
				
				if(PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue(infor.getThiscodename()))==null)
					continue;

				infor.setCorpvalue(corpInfor.getCorpvalue());//公司ID值
				//				当前编码值
				infor.setCodevalue(ZmPubTool.getString_NullAsTrimZeroLen(vo.getAttributeValue(infor.getThiscodename())));
			//	if(PuPubVO.getString_TrimZeroLenAsNull(infor.getCorpname())==null)
				//	infor.setCorpname(corpInfor.getCorpname());

				tmpValue = getInforValue(vo,infor);
				if(infor.isSave.booleanValue())
					vo.setAttributeValue(infor.getThiscodename(), tmpValue);// 为公司字段附上ID
			}			
		}
	}
}

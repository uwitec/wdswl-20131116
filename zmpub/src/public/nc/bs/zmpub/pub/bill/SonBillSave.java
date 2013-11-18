package nc.bs.zmpub.pub.bill;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.trade.business.HYSuperDMO;
import nc.bs.trade.comsave.BillSave;
import nc.itf.zmpub.pub.ISonVO;
import nc.itf.zmpub.pub.ISonVOH;
import nc.jdbc.framework.SQLParameter;
import nc.ui.scm.util.ObjectUtils;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.HYBillVO;
/**
 * ֧�� ���ı��湤����
 * @author mlr
 *
 */
public class SonBillSave extends BillSave{
	private HYSuperDMO dmo = null;
	private HYSuperDMO getSuperDMO(){
		if(dmo == null){
			dmo = new HYSuperDMO();
		}
		return dmo;
	}
	private BaseDAO dao=null;
	public BaseDAO getDao(){
		if(dao==null){
			dao=new BaseDAO();
		}
		return dao;
	}
	/**
	 * �����ӱ����ݡ�
	 * �������ڣ�(2004-2-27 20:59:29)
	 * @param vo nc.vo.pub.AggregatedValueObject
	 * @exception java.lang.Exception �쳣˵����
	 */
	private void setChildData(AggregatedValueObject vo,HYSuperDMO dmo)throws BusinessException {		
		{
			SuperVO[] itemVos = (SuperVO[]) vo.getChildrenVO();
			SuperVO headVo = (SuperVO) vo.getParentVO();
			vo.setChildrenVO(dmo.queryByWhereClause(itemVos[0].getClass(),itemVos[0].getParentPKFieldName()+ "='"
						+ headVo.getPrimaryKey()
						+ "' and isnull(dr,0)=0"));
		}
	}	
	/**
	 * ����VO����
	 * �������ڣ�(2004-2-27 11:15:29)
	 * @return ArrayList
	 * @param vo nc.vo.pub.AggregatedValueObject

	 */
	private ISonVOH ioh;
	public java.util.ArrayList saveBill1(nc.vo.pub.AggregatedValueObject billVo) throws Exception{
		if(billVo==null)
			throw new Exception("��������Ϊ��");
		if(billVo.getParentVO()==null ){
			return null;
		}
		if(billVo.getChildrenVO()==null || billVo.getChildrenVO().length==0)
			return null;
		if(!(billVo instanceof ISonVOH)){
			throw new Exception("�ۺ�voû��ʵ��ISonVOH�ӿ�");
		}
		
		if(!(billVo.getChildrenVO()[0] instanceof ISonVO)){
			throw new Exception("�ӱ�voû��ʵ��ISonVO�ӿ�");
		}
		ioh=(ISonVOH)billVo;
	    HYBillVO  oldbillvo=(HYBillVO) ObjectUtils.serializableClone(billVo);
	    HYBillVO billvov=(HYBillVO)billVo;
	    java.util.ArrayList retAry = super.saveBill(billvov);
		if(retAry == null || retAry.size() == 0){
			throw new BusinessException("����ʧ��");
		}
		HYBillVO newBillVo = (HYBillVO)retAry.get(1);
		if(oldbillvo.getParentVO().getPrimaryKey()==null){
			//���������ֶ����������Ϣ 
			//��Ϊ��������Ļ�  
			//����� ���صľۺ�vo �ǲ���������Ϣ��
			setChildData(newBillVo,getSuperDMO());			
		}				
		insertBody_Pk(newBillVo,oldbillvo);		
		if(oldbillvo.getParentVO().getPrimaryKey()==null){
			//�������� 
			//�������������Ļ���
			// ����Ҫ  ��billvo ����¡һ��  ��Ϊ ������Ϊ  oldbillvo
			// ����billvo  ��÷��ؽ��   Ȼ��ȡ�� billvo�ı��� ����  �����к���Ϣ  
			// ����������  ͬ���� oldbillvo�������
			// Ȼ�� ���������Ϣ
		     //������������			
			saveXiHa(oldbillvo);			
		}else{
			//����� �޸ı���Ļ�   
			// ����Ҫ  ��billvo ����¡һ��  ��Ϊ ������Ϊ  oldbillvo
			// ����billvo  ��÷��ؽ��   Ȼ��ȡ�� billvo�ı��� ����  �����к���Ϣ  
			// ����������  ͬ���� oldbillvo�������
			
			//Ȼ�� �ӽڵ�  oldbillvo �и���vo״̬ �ҳ�  ���� ɾ�� �޸ĵı�����Ϣ
			//������  ���������Ϣ
			//�޸ĵ�  ��ɾ��ԭ���������Ϣ  Ȼ�󱣴����µ������Ϣ
			//ɾ����  ɾ�������Ϣ		
		   //�����޸ı���
		   Map<String,List<SuperVO>>  map=splitVO((SuperVO[])oldbillvo.getChildrenVO());
		   List<SuperVO> adds=map.get("add");
		   List<SuperVO> edits=map.get("edit");
		   List<SuperVO> detes=map.get("dete");
		   List<SuperVO> unchg=map.get("unchg");
		   if(adds!=null && adds.size()!=0){
			  for(int i=0;i<adds.size();i++){
				 saveXiHa(((ISonVO)(adds.get(i))).getSonVOS());
			  } 
		   }
           if(edits!=null && edits.size()!=0){
        	   for(int i=0;i<edits.size();i++){
        		 deleteXiHa((edits.get(i)).getPrimaryKey());
				 saveXiHa(((ISonVO)(edits.get(i))).getSonVOS());
  			  } 
		   }
           if(detes!=null && detes.size()!=0){
        	   for(int i=0;i<detes.size();i++){
          		 deleteXiHa((detes.get(i)).getPrimaryKey()); 				 
    		  } 
		   }	
           if(unchg!=null && unchg.size()!=0){
        	   for(int i=0;i<unchg.size();i++){
          		     deleteXiHa((unchg.get(i)).getPrimaryKey());
    				 saveXiHa(((ISonVO)(unchg.get(i))).getSonVOS());
      		    } 
		   }	
		}
		ioh=null;
		sql=null;
        return retAry;
	}
	public void saveXiHa(HYBillVO oldbillvo) throws Exception {
		SuperVO[] oldchilds=(SuperVO[]) oldbillvo.getChildrenVO();
		if(oldchilds!=null || oldchilds.length!=0){
			List<SuperVO> list=new ArrayList<SuperVO>();
				for(int i=0;i<oldchilds.length;i++){					
					list.addAll(((ISonVO)oldchilds[i]).getSonVOS());
				}
				saveXiHa(list);
		}		
	}
	/**
	 * ����������� ͬ���� olbillvo�������
	 * @author mlr
	 * @˵�������׸ڿ�ҵ��
	 * 2011-10-12����06:07:16
	 * @param newBillVo
	 * @param oldbillvo
	 */
	public void insertBody_Pk(HYBillVO newBillVo,HYBillVO oldbillvo) {
		//���� �к� ͬ����������    ���Ǵ��ڱ����޸�ɾ����     �ֽ��������л�����к��ظ�������		
		//�������ӱ�  �Ѿ�ȥ����ɾ����  ��û��ȥ�������Ϣ  newChilds
	    //����ǰ���ӱ�  û��ȥ��ɾ���� ������vo״̬�� ��������ϢoldChilds		
		SuperVO[]  newChilds=(SuperVO[]) newBillVo.getChildrenVO();
		if(newChilds==null || newChilds.length==0 ){
			return;
		}
		SuperVO[] oldChilds=(SuperVO[]) oldbillvo.getChildrenVO();
		for(int i=0;i<newChilds.length;i++){
			String pk=newChilds[i].getPrimaryKey();
			String pk_h=PuPubVO.getString_TrimZeroLenAsNull(newChilds[i].getAttributeValue(newChilds[i].getParentPKFieldName()));
            ISonVO newso=(ISonVO)newChilds[i];
			String crowno=PuPubVO.getString_TrimZeroLenAsNull(newChilds[i].getAttributeValue(newso.getRowNumName()));   
            for(int j=0;j<oldChilds.length;j++){
               if(oldChilds[j].getStatus()==VOStatus.DELETED){
            	   continue;
               }
               ISonVO oldso=(ISonVO) oldChilds[j];
               if(crowno.equalsIgnoreCase(PuPubVO.getString_TrimZeroLenAsNull(oldChilds[i].getAttributeValue(oldso.getRowNumName())))){
            	   ArrayList<SuperVO> list= oldso.getSonVOS();
            	   if(list!=null && list.size()!=0){
            	      for(int k=0;k<list.size();k++){
       				    list.get(k).setPrimaryKey(pk);
       				    list.get(k).setAttributeValue(list.get(k).getParentPKFieldName(), pk_h);
       			      }
            	   }
               }
           }
		}
	}
	/**
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ��
	 * 2011-10-12����05:33:54
	 * @param pk_ca_makesuc_b1 �ӱ�����
	 * @throws Exception
	 */
	public void deleteXiHa(String pk)throws Exception{
		SQLParameter para=new SQLParameter();
		para.addParam(pk.trim());
	    String sql=getSql();	
		getDao().executeUpdate(sql, para);	
	}
	/**
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ��
	 * 2011-11-16����05:02:34
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private String sql=null;
	private String getSql() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
	  if(sql==null){
		String str=ioh.getSonClass();
		SuperVO ivo=(SuperVO) Class.forName(str).newInstance();
		sql=" update "+ivo.getTableName()+" set dr=1 where "+ivo.getParentPKFieldName()+"=?";
	  }	
	  return sql;
	}
	/**
	 * �������
	 * @author mlr
	 * @˵�������׸ڿ�ҵ��
	 * 2011-10-12����05:40:30
	 * @throws Exception
	 */
	public void saveXiHa(List  list) throws Exception{
		if(list==null || list.size()==0)
			return;
		for(int i=0;i<list.size();i++){
	      getDao().insertVO((SuperVO) list.get(i));
		}
	}
	/**
	 * ����vo״̬�� �����vo����
	 * ��Ϊ ���� �޸�  ɾ��
	 * ����һ��map  
	 * keyΪ add Ϊ����
	 * keyΪ edit Ϊ�޸�
	 * keyΪ dete Ϊɾ��
	 * keyΪ unchg Ϊû�б仯 ����������б仯 ���Ա��뽫���ȫɾȫ��
	 * @author mlr
	 * @˵�������׸ڿ�ҵ��
	 * 2011-10-12����05:45:51
	 * @param vos
	 * @return
	 */
	public Map<String,List<SuperVO>> splitVO(SuperVO[] vos){
		if(vos==null || vos.length==0){
			return new HashMap<String,List<SuperVO>>();
		}
		Map<String,List<SuperVO>>  map=new HashMap<String,List<SuperVO>>();
		for(int i=0;i<vos.length;i++){
			if(vos[i].getStatus()==VOStatus.NEW){
				if(map.containsKey("add")){
					map.get("add").add(vos[i]);
				}else{
					List<SuperVO> list=new ArrayList<SuperVO>();
					list.add(vos[i]);
					map.put("add", list);
				}
			}
			if(vos[i].getStatus()==VOStatus.DELETED){
				if(map.containsKey("dete")){
					map.get("dete").add(vos[i]);
				}else{
					List<SuperVO> list=new ArrayList<SuperVO>();
					list.add(vos[i]);
					map.put("dete", list);
				}
			}
			if(vos[i].getStatus()==VOStatus.UPDATED){
				if(map.containsKey("edit")){
					map.get("edit").add(vos[i]);
				}else{
					List<SuperVO> list=new ArrayList<SuperVO>();
					list.add(vos[i]);
					map.put("edit", list);
				}
			}
			if(vos[i].getStatus()==VOStatus.UNCHANGED){
				if(map.containsKey("unchg")){
					map.get("unchg").add(vos[i]);
				}else{
					List<SuperVO> list=new ArrayList<SuperVO>();
					list.add(vos[i]);
					map.put("unchg", list);
				}
			}
		}	
		return map;		
	}


}

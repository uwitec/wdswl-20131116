package nc.bs.zmpub.pub.check;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;

import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.NullFieldException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
	/**
	 * author:mlr
	 * ����Ϊ����ǰ�� ��̨У���� 
	 * 
	 * */
	public  class BsBeforeSaveValudate{		
	/**	
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ 
	 *        ����ļ����ֶι���Ψһ�ԣ�ÿһ�еļ�¼�ļ����ֶι�����Ψһ�Լ�¼���������бȽ� ������Ψһ��У��
	 * @ʱ�䣺2011-7-5����07:26:48
	 * @param chs
	 * @param fields
	 * @param displays
	 * @throws Exception
	 */	
	public static void beforeSaveBodyUnique(CircularlyAccessibleValueObject[] chs, String[] fields,String[] displays) throws ValidationException {
		if (chs == null || chs.length == 0) {
			return;
		}
		int num = chs.length;
		if (fields == null || fields.length == 0) {
			return;
		}
		if (num > 0) {
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < num; i++) {
				String key = "";
				for (String str : fields) {
					Object o1 = chs[i].getAttributeValue(str);
					key = key + "," + String.valueOf(o1);
				}
				String dis = "";
				for (int j = 0; j < displays.length; j++) {
					dis = dis + "[ " + displays[j] + " ]";
				}
				if (list.contains(key)) {
					throw new ValidationException("��[" + (i + 1) + "]�б����ֶ� "+ dis + " �����ظ�!");
				} else {
					list.add(key);
				}
			}
		}
	  }
	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ 
	 *       ����ֶε����佻���Ψһ��У��
	 *       ��У��ĳ��ά���£�����Сֵ �� ���ֵ ֮��ֻ���� һ����¼
	 *       
	 * @ʱ�䣺2011-7-6����09:50:30
	 * @param vo
	 * @param checkFields У����ֶ�����
	 * @param minField    ��Сֵ
	 * @param maxField    ���ֵ
	 * @param errorMessage ������ʾ��Ϣ
	 * @throws Exception
	 */
	public static void beforeSaveBodyUniqueInmet(SuperVO[] vos,String[] fields,String minField,
			String maxField,String[] displays,String minDisplay,String maxDisplay)throws ValidationException{
		if(isEmpty(vos)){
			return;
		}
		SuperVO[][] voss=(SuperVO[][]) SplitBillVOs.getSplitVOs(vos,fields);
		if(isEmpty(voss)){
			return;
		}
		if(isEmpty(fields)){
			throw new ValidationException("У����ֶβ�����Ϊ��");
		}
		if(isNULL(minField)|| isNULL(maxField)){
			throw new ValidationException("У��������Сֵ �� ���ֵ������Ϊ��");
		}
		if(isEmpty(displays)){
			throw new ValidationException("У�������ʾ�ֶβ�����Ϊ��");
		}
		//������ʾ�ֶ�
		String dis = "";
		for (int j = 0; j < displays.length; j++) {
			dis = dis + "[ " + displays[j] + " ]";
		}
		int size=voss.length;
		for(int i=0;i<size;i++){
			int size1=voss[i].length;
			for(int j=0;j<size1;j++){
				SuperVO vo=voss[i][j];
				for(int k=j+1;k<size1;k++){
					Object voMin=vo.getAttributeValue(minField);
					Object voMax=vo.getAttributeValue(maxField);
					Object svoMin=voss[i][k].getAttributeValue(minField);
					Object svoMax=voss[i][k].getAttributeValue(maxField);
					if(compareInto(voMax, svoMin)==-1 || compareInto(voMin, svoMax)==1){
						continue;
					}else{
						throw new ValidationException("�����ֶ�  "+ dis +"��ͬ�������  "+"["+
								minDisplay+"] �� ["+maxDisplay +"] ������ڽ���");
					} 
				}
			}			
		}
	}
	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ 
	 *        �Ƚ���������֮��Ĵ�С
	 *        ��� o1>o2 ���� 1
	 *        ��� o1=o2 ���� 0
	 *        ��� o1<o2 ���� -1
	 *        ��� �Ƿ����ݲ��ܱȽϷ��� -2 
	 * @ʱ�䣺2011-7-6����10:21:21
	 * @param o1
	 * @param o2
	 */
	 private static int compareInto(Object o1,Object o2) {
	   if(isEmpty(o1)|| isEmpty(o2)){
		   return -2;
	   }
	   if(o1 instanceof Integer && o2 instanceof Integer){
		  Integer i1=(Integer) o1;
		  Integer i2=(Integer) o2;
		  return  i1.compareTo(i2);	  
	   }
	   if(o1 instanceof UFDouble && o2 instanceof UFDouble){
		      UFDouble i1=(UFDouble) o1;
		      UFDouble i2=(UFDouble) o2;
			  return  i1.compareTo(i2);	  
	   }
	   if(o1 instanceof String && o2 instanceof String){
		      String i1=(String) o1;
		      String i2=(String) o2;
			  return  i1.compareTo(i2);	  
	   }
	   if(o1 instanceof UFDate && o2 instanceof UFDate){
		     UFDate i1=(UFDate) o1;
		     UFDate i2=(UFDate) o2;
		     if(i1.before(i2)){
		    	 return -1;
		     }else if(i1.after(i2)){
		    	 return 1;
		     }else{
		    	 return 0;
		     } 
	   }	 
	    return -2;
	 }
	
	/**
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ 
	 *        ���岻����Ϊ�յ�У��
	 * @ʱ�䣺2011-7-5����08:37:54
	 * @param vos
	 * @throws Exception
	 */
		public static void BodyNotNULL(CircularlyAccessibleValueObject[] vos) throws ValidationException{
			if(vos==null || vos.length==0){
				throw new ValidationException("���岻����Ϊ��");
			}
		}
		/**
		 * 
		 * @���ߣ�mlr
		 * @˵�������ɽ������Ŀ 
		 *        �ֶβ�Ϊ��У��(ֻУ��һ���ֶ�)
		 * @ʱ�䣺2011-7-5����08:41:08
		 * @param bodys
		 * @param checkField
		 * @param displayName
		 * @throws Exception
		 */
		public static void bodyFieldNotNull(CircularlyAccessibleValueObject[] bodys,String checkField,String displayName)throws ValidationException{
			
			  for(int i=0;i<bodys.length;i++){
	              if(isEmpty(bodys[i].getAttributeValue(checkField))){            	  
	            	  throw new ValidationException("�����"+(i+1)+"��"+displayName+"����Ϊ��");
	              }
	          }    	
		}		
		/**
		 * 	
		 * @���ߣ�mlr
		 * @˵�������ɽ������Ŀ 
		 *        �ֶβ�Ϊ�յ�У�� 
		 *        ����ǰ̨�ı�����У��
		 * 
		 * @ʱ�䣺2011-7-5����04:59:49
		 * @param vos ҪУ���vos
		 * @param fields ҪУ����ֶ�����
		 * @param fields Ҫ����������ʾ���ֶ�����
		 * @throws BusinessException
		 */
		public static void FieldNotNull(SuperVO[] vos,String[] fields,String[] names)throws BusinessException{
			if(isEmpty(vos) || isEmpty(fields)|| isEmpty(names)){
				return;
			}
			if(fields.length !=names.length){
				throw new BusinessException("У���ֶκ���ʾ�ֶ����鳤�Ȳ�һ��");
			}
			StringBuffer message=null;
			for(int i=0;i<vos.length;i++){
				SuperVO vo=vos[i];
				for(int j=0;j<fields.length;j++){
				Object o=vo.getAttributeValue(fields[j]);
				if(isNULL(o)){
					if (message == null)
						message = new StringBuffer();
					message.append("[");
					message.append(names[i]);
					message.append("]");
					message.append(",");
				  }
				}
			}
			if (message != null) {
				message.deleteCharAt(message.length() - 1);
				throw new NullFieldException(message.toString());
			}	
		}
     /**
      * 
      * @���ߣ�mlr
      * @˵�������ɽ������Ŀ 
      *       �ж϶����Ƿ�Ϊ��
      * @ʱ�䣺2011-7-5����08:43:29
      * @param value
      * @return
      */
		public static boolean isEmpty(Object value)
		{
			if (value == null)
				return true;
			if ((value instanceof String)
					&& (((String) value).trim().length() <= 0))
				return true;
			if ((value instanceof Object[]) && (((Object[]) value).length <= 0))
				return true;
			if ((value instanceof Collection) && ((Collection) value).size() <= 0)
				return true;
			if ((value instanceof Dictionary) && ((Dictionary) value).size() <= 0)
				return true;
			return false;
		}	
		/**
		 * 
		 * @���ߣ�mlr
		 * @˵�������ɽ������Ŀ 
		 *       �ж��ַ����Ƿ�Ϊ��
		 * @ʱ�䣺2011-7-5����08:44:48
		 * @param o
		 * @return
		 */
		public static boolean isNULL(Object o) {
			if (o == null || o.toString().trim().equals(""))
				return true;
			return false;
		}
	}



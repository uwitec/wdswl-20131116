package nc.vo.zmpub.excel;

import java.util.ArrayList;

import nc.bs.dao.BaseDAO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;

/**
 * zhf excel����ʱ�Զ���ı�����IDת������
 * @author Administrator
 *
 */
public interface IDefTran {
	

	public String transCodeToID(BaseDAO dao,CircularlyAccessibleValueObject vo,CodeToIDInfor infor) throws BusinessException;
}

package nc.itf.zmpub.pub;

import java.util.ArrayList;
import java.util.List;
import nc.vo.pub.SuperVO;

/**
 * ʵ������� �ӱ�vo����ʵ�ָýӿ�
 * 
 * @author mlr
 */
public interface ISonVO {
	/**
	 * ���������Ϣ
	 * 
	 * @return
	 */
	public abstract ArrayList<SuperVO> getSonVOS();

	/**
	 * �����к�����
	 * 
	 * @return
	 */
	public abstract String getRowNumName();

	/**
	 * ���������Ϣ
	 * 
	 * @param list
	 */
	public abstract void setSonVOS(List<SuperVO> list);
}

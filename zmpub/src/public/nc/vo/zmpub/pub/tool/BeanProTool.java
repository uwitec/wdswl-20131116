package nc.vo.zmpub.pub.tool;

import java.lang.reflect.Field;

import nc.vo.pub.BusinessException;

/**
 * @author mlr
 */
public class BeanProTool {
	public static void copyBeanParm(Object[] srcs, Object[] poss)
			throws Exception {
		if (srcs == null || srcs.length == 0 || poss == null
				|| poss.length == 0) {
			return;
		}
		if (srcs.length != poss.length) {
			throw new Exception("��Դ�������鳤�� �� Ŀ�Ķ������鳤��  ��һ��");
		}
		for (int i = 0; i < srcs.length; i++) {
			copyBeanParm(srcs[i], poss[i]);
		}
	}

	/**
	 * �� src �����е�����ֵ ��ֵ�� pos��
	 * 
	 * @param src
	 *            ԭ����
	 * @param pos
	 *            Ŀ�Ķ���
	 * @throws Exception
	 */
	public static void copyBeanParm(Object src, Object pos)
			throws BusinessException {
		Class sc = src.getClass();
		Class ps = pos.getClass();
		Field[] sfields = sc.getFields();
		Field[] pfields = ps.getFields();
		if (sfields == null || sfields.length == 0 || pfields == null
				|| pfields.length == 0) {
			return;
		}
		for (int i = 0; i < sfields.length; i++) {
			String name = sfields[i].getName();
			for (int j = 0; j < pfields.length; j++) {
				if (name.equalsIgnoreCase(pfields[j].getName())) {
					Object value;
					try {
						value = sfields[i].get(src);
						pfields[j].set(pos, value);
					} catch (Exception e) {
						e.printStackTrace();
						throw new BusinessException(e.getMessage());
					}
					break;
				}
			}
		}
	}
}

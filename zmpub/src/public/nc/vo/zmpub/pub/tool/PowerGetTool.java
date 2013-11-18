package nc.vo.zmpub.pub.tool;

import java.util.ArrayList;
import java.util.List;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.ui.dbcache.DBCacheFacade;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.scm.pu.PuPubVO;

/**
 *��ȡ�û�����Ȩ�޵Ĺ�����
 * 
 * @author mlr
 */
public class PowerGetTool {
	/**
	 * ���ĳ�û� ��ĳ�������� ����Ȩ���µ� ��ѯ�õ���������sql
	 * 
	 * @param tableName
	 *            ��
	 * @param pk_corp
	 *            ��˾
	 * @param pk_user
	 *            �û�
	 * @return
	 */
	public static String queryClassPowerSql(String tableName, String pk_corp,
			String pk_user) {
		// nc.ui.bd.ref.IRefUtilService refUtil = (nc.ui.bd.ref.IRefUtilService)
		// NCLocator
		// .getInstance().lookup("nc.ui.bd.ref.IRefUtilService");
		return null;
	}

	/**
	 * ����Ȩ�� ���� ĳһ��vo
	 * 
	 * @param tmpBodyVo
	 *            Ҫ���˵�vo����
	 * @param pk_name
	 *            Ҫ���˵�vo���� ��
	 * @param tableName
	 * @param pk_corp
	 * @param pk_user
	 * @return
	 * @throws BusinessException
	 */
	public static CircularlyAccessibleValueObject[] spiltByPower(
			CircularlyAccessibleValueObject[] tmpBodyVo, String pk_name,
			String tableName, String pk_corp, String pk_user)
			throws BusinessException {
		if (tmpBodyVo == null || tmpBodyVo.length == 0)
			return null;
		if (PowerGetTool.isStartPower(tableName, pk_corp, pk_user) == false)
			return tmpBodyVo;
		ArrayList pks = PowerGetTool
				.getPowerByUser(tableName, pk_corp, pk_user);
		// ��Ź��˺�ı�������
		List<CircularlyAccessibleValueObject> list = new ArrayList<CircularlyAccessibleValueObject>();
		for (int i = 0; i < tmpBodyVo.length; i++) {
			String pk = PuPubVO.getString_TrimZeroLenAsNull(tmpBodyVo[i]
					.getAttributeValue(pk_name));
			if (exist(pk, pks))
				list.add(tmpBodyVo[i]);
		}
		return list
				.toArray((CircularlyAccessibleValueObject[]) java.lang.reflect.Array
						.newInstance(tmpBodyVo[0].getClass(), list.size()));
	}

	/**
	 * �ж�pkֵ�Ƿ���arrayList�д���
	 * 
	 * @param pk
	 * @param arrayList2
	 * @return
	 */
	public static boolean exist(String pk, ArrayList arrayList) {
		if (pk == null || pk.length() == 0)
			return false;
		boolean isf = false;
		for (int i = 0; i < arrayList.size(); i++) {
			Object[] os = (Object[]) arrayList.get(i);
			if (pk.equals(os[0])) {
				isf = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * ���ĳ�û�����Ȩ���� ��ĳ������ ��������
	 * 
	 * @param tableName
	 * @param pk_corp
	 * @param pk_user
	 * @return
	 */
	public static ArrayList<String> getPowerByUser(String tableName,
			String pk_corp, String pk_user) {
		String sql = queryClassPowerSql(tableName, pk_corp, pk_user);
		ArrayListProcessor bc = new ArrayListProcessor();
		ArrayList alGroup = (ArrayList) DBCacheFacade.runQuery(sql, bc);
		return alGroup;
	}

	public static boolean isStartPower(String tableName, String pk_corp,
			String pk_user) {
		String str = queryClassPowerSql(tableName, pk_corp, pk_user);
		if (PuPubVO.getString_TrimZeroLenAsNull(str) == null) {
			return false;
		}
		return true;
	}

	/**
	 * �����ض���
	 * 
	 * @param tableName
	 * @return
	 */
	private static String getClassDataPowerShowNameByTableName(String tableName) {
		String dataPowerName = null;
		if (tableName == null || tableName.trim().length() == 0) {
			return null;
		}
		String dataPowerTableName = tableName.trim().split(" ")[0];

		if ("bd_areacl".equalsIgnoreCase(dataPowerTableName)) {
			dataPowerName = "��������";

		} else if ("bd_invcl".equalsIgnoreCase(dataPowerTableName)) {
			dataPowerName = "�������";

		} else if ("bd_deptdoc".equalsIgnoreCase(dataPowerTableName)) {
			dataPowerName = "���ŵ���";

		} else if ("bd_glbook".equalsIgnoreCase(dataPowerTableName)) {
			dataPowerName = "�����˲�";
		} else if ("bd_invmandoc".equalsIgnoreCase(dataPowerTableName)) {
			dataPowerName = "�������";
		}
		return dataPowerName;
	}
}

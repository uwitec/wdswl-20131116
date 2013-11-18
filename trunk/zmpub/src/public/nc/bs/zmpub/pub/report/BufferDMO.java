package nc.bs.zmpub.pub.report;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.zmpub.pub.report.ReportBufferVO;
import nc.vo.zmpub.pub.report2.ReportBuffer;

/**
 * ���������ò�ѯ��
 * 
 * @author mlr
 * 
 */
public class BufferDMO {
	private BaseDAO dao = null;

	public BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	/**
	 * ���ݹ��ܽڵ�� ��ѯ���û���
	 * 
	 * @param nodeid
	 * @return
	 * @throws DAOException
	 */
	public ReportBuffer queryByNodeId(String nodeid) throws DAOException {
		List<ReportBufferVO> cl = (List<ReportBufferVO>) getDao()
				.retrieveByClause(ReportBufferVO.class,
						"  nodecode = '" + nodeid + "'");
		if (cl == null || cl.size() == 0) {
			return null;
		}
		ReportBufferVO vo = cl.get(0);
		return createBuffer(vo);
	}
	/**
	 * �������� ��ѯ���û���
	 * 
	 * @param nodeid
	 * @return
	 * @throws DAOException
	 */
	public ReportBuffer queryById(String id) throws DAOException {
		List<ReportBufferVO> cl = (List<ReportBufferVO>) getDao()
				.retrieveByClause(ReportBufferVO.class,
						"  pk_config = '" + id + "' and isnull(dr,0)=0 ");
		if (cl == null || cl.size() == 0) {
			return null;
		}
		ReportBufferVO vo = cl.get(0);
		return createBuffer(vo);
	}

	/**
	 * �����������û���
	 * 
	 * @param vo
	 * @return
	 */
	private ReportBuffer createBuffer(ReportBufferVO vo) {
		String pk_config = vo.getPrimaryKey();// ����
		String nodecode = vo.getNodecode();// ���ܽڵ��
		// ��������
		String strRows = vo.getStrRows();// ������ ��&��Ϊ�ָ���
		String strCols = vo.getStrCols();// ������ ��&��Ϊ�ָ���
		String strVals = vo.getStrVals();// ����ֵ ��&��Ϊ�ָ���
		UFBoolean istotal = vo.getIstotal();

		Integer lel = vo.getLel();// ���ܼ���
		// �ϼ�����
		UFBoolean issub = vo.getIssub();// �Ƿ�С��
		UFBoolean issum = vo.getIssum();// �Ƿ�ϼ�
		String totfields = vo.getTotfields();// �ϼ�γ�� ��&��Ϊ�ָ���
		String totfieldsNames = vo.getTotfieldsNames();// �ϼ�γ������ ��&��Ϊ�ָ���
		ReportBuffer buffer = new ReportBuffer();
		buffer.setIssub(issub);
		buffer.setIssum(issum);
		buffer.setNodecode(nodecode);
		buffer.setPk_config(pk_config);
		buffer.setLel(lel);
		buffer.setIstotal(istotal);
		if (strRows != null && strRows.length() != 0) {
			buffer.setStrRows(strRows.split("&"));
		}
		if (strCols != null && strCols.length() != 0) {
			buffer.setStrCols(strCols.split("&"));
		}

		if (strVals != null && strVals.length() != 0) {
			buffer.setStrVals(strVals.split("&"));
		}

		if (totfields != null && totfields.length() != 0) {
			buffer.setTotfields(totfields.split("&"));
		}

		if (totfieldsNames != null && totfieldsNames.length() != 0) {
			buffer.setTotfieldsNames(totfieldsNames.split("&"));
		}
		return buffer;
	}

	/**
	 * ���»��浽���ݿ�
	 * 
	 * @param vo
	 * @return
	 * @throws DAOException
	 */
	public void updateBuffer(ReportBuffer vo) throws DAOException {
		String pk_config = vo.getPk_config();// ����
		String nodecode = vo.getNodecode();// ���ܽڵ��
		// ��������
		String[] strRows = vo.getStrRows();// ������ ��&��Ϊ�ָ���
		String[] strCols = vo.getStrCols();// ������ ��&��Ϊ�ָ���
		String[] strVals = vo.getStrVals();// ����ֵ ��&��Ϊ�ָ���
		Integer lel = vo.getLel();// ��û��ܼ���
		UFBoolean istotal = vo.getIstotal();
		// �ϼ�����
		UFBoolean issub = vo.getIssub();// �Ƿ�С��
		UFBoolean issum = vo.getIssum();// �Ƿ�ϼ�
		String totfields[] = vo.getTotfields();// �ϼ�γ�� ��&��Ϊ�ָ���
		String totfieldsNames[] = vo.getTotfieldsNames();// �ϼ�γ������ ��&��Ϊ�ָ���
		ReportBufferVO buffer = new ReportBufferVO();
		buffer.setIssub(issub);
		buffer.setIssum(issum);
		buffer.setNodecode(nodecode);
		buffer.setPk_config(pk_config);
		buffer.setStrRows(getStr(strRows));
		buffer.setStrCols(getStr(strCols));
		buffer.setStrVals(getStr(strVals));
		buffer.setLel(lel);
		buffer.setIstotal(istotal);
		buffer.setTotfields(getStr(totfields));
		buffer.setTotfieldsNames(getStr(totfieldsNames));
		buffer.setDetatil(vo.getDetatil());
		if (pk_config == null || pk_config.length() == 0) {
			getDao().insertVO(buffer);
		} else {
			getDao().updateVO(buffer);
		}

	}

	/**
	 * 
	 * @param strRows
	 * @return
	 */
	private String getStr(String[] strRows) {
		if (strRows == null || strRows.length == 0)
			return null;
		String s = "";
		for (int i = 0; i < strRows.length; i++) {
			s = s.trim() + strRows[i] + "&";
		}

		return s;
	}

}

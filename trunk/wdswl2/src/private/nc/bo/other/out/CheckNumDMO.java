package nc.bo.other.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.ic.other.out.TbOutgeneralBVO;


public class CheckNumDMO {
	private BaseDAO m_dao = null;
	private BaseDAO getDao() {
		if (m_dao == null) {
			m_dao = new BaseDAO();
		}
		return m_dao;
	}
	public Map<String,List<TbOutgeneralBVO>> doQuery(String[] pks) throws DAOException {
		TbOutgeneralBVO[] datas = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select nvl(t.nassarrangnum,0) - nvl(t.nassoutnum,0) nshouldoutnum,t.pk_soorder_b general_b_pk,");
		sql.append("      t.nassoutnum nshouldoutassistnum, b.invcode cinvbasid");
		sql.append("  from wds_soorder_b t, bd_invbasdoc b");
		sql.append(" where t.pk_soorder_b in("+getSubSql(pks)+")");
		sql.append("   and b.pk_invbasdoc = t.pk_invbasdoc;");
		Object o = getDao().executeQuery(sql.toString(), new BeanListProcessor(TbOutgeneralBVO.class));
		if (o == null)
			return null;
		ArrayList<TbOutgeneralBVO> list = (ArrayList<TbOutgeneralBVO>) o;
		if (list==null||list.size()==0) {
			return null;
		}
		String key = "";
		Map<String ,List<TbOutgeneralBVO>> map = new HashMap<String ,List<TbOutgeneralBVO>>();
		for (int i = 0; i < list.size(); i++) {
			List<TbOutgeneralBVO> l = new ArrayList<TbOutgeneralBVO>();
			TbOutgeneralBVO bvo = list.get(i);
			key = bvo.getGeneral_b_pk();
			if (map.get(key)!=null) {
				l = map.get(key);
			}
			l.add(bvo);
			map.put(key, l);
		}
		return map;
		
		
	}
	  private String getSubSql(String[] pks) {
		  StringBuffer insql= new StringBuffer();
		  for (int i = 0; i < pks.length; i++) {
			insql.append("'");
			insql.append(pks[i]);
			insql.append("',");
		}
		  if (insql.length() > 0 ) {
			insql.deleteCharAt(insql.length()-1);
		}
		return insql.toString();
	}
}

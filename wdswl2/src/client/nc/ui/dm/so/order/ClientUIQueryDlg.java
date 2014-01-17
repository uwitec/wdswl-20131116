package nc.ui.dm.so.order;
import java.awt.Container;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIPanel;
import nc.ui.wl.pub.WdsQueryDlg;


public class ClientUIQueryDlg extends WdsQueryDlg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	

	public ClientUIQueryDlg(Container parent, UIPanel normalPnl,
			String pk_corp, String moduleCode, String operator,
			String busiType, String nodeKey) {
		super(parent, normalPnl, pk_corp, moduleCode, operator, busiType,"wds_soorder.pk_outwhouse",null);
	init();
	}
	
	public void init(){
		setDefaultValue("wds_soorder.dbilldate",null,ClientEnvironment.getInstance().getDate().toString());
	}
	@Override
	public String getWhereSql() {
		String sql=super.getWhereSql();
		
		if(sql==null){
	      return null;
		}
		if(sql.contains("wds_soorder.vbillstatus = 0"))
		{
		  return sql.replace("wds_soorder.vbillstatus = 0", "wds_soorder.vbillstatus = 8");
		}
		//修改人：王刚 修改时间2013.12.4 修改原因：where条件拼接
		if(sql.contains("wds_soorder_b.vsourcebillcode"))
		{
			int i=sql.lastIndexOf("(wds_soorder_b.vsourcebillcode");
			int y=sql.indexOf("')", i)+2;
			System.out.println(i+"--------======"+y);
			String sqlwhere =sql.substring(i, y);
			sql=sql.replace(sqlwhere, " (wds_soorder.pk_soorder in(select pk_soorder from wds_soorder_b where "+sqlwhere+"))");
		}

//		return sql;
		return sql;
	}
	
	
	
	
}

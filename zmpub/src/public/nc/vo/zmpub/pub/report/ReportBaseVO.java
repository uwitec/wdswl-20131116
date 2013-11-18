package nc.vo.zmpub.pub.report;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import nc.vo.jcom.util.Convertor;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * �������ڣ�(2004-2-5 11:43:07)
 * 
 * @author��liujb
 */
public class ReportBaseVO extends nc.vo.pub.CircularlyAccessibleValueObject
		implements IReportVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4326797270945457280L;
	private boolean m_isSubtotal = false;
	private boolean m_isTotal = false;
	private boolean m_isCombined = false;
	private java.util.Vector m_vDetail = null;

	/**
	 * BgtBillReportVO ������ע�⡣
	 */
	public ReportBaseVO() {
		super();
	}

	public Object clone() {
		ReportBaseVO vo = null;
		try {
			vo = (ReportBaseVO) getClass().newInstance();
		} catch (Exception ex) {
			return null;
		}
		vo.setCombined(isCombined());
		Vector v = getDetailvo();
		if (v != null && v.size() > 0) {
			Vector vnew = new Vector();
			for (int i = 0; i < v.size(); i++) {
				vnew.add((ReportBaseVO) v.get(i));
			}
			vo.setDetailVO(vnew);
		}
		vo.setSubtotal(isSubtotal());
		vo.setTotal(isTotal());
		String[] attr = getAttributeNames();
		for (int i = 0; i < attr.length; i++) {
			vo.setAttributeValue(attr[i], getAttributeValue(attr[i]));
		}
		return vo;
	}

	public Object getAttributeValue(String attributeName) {
		return m_hData.get(attributeName.toLowerCase());
	}

	/**
	 * getDetailvo ����ע�⡣
	 */
	public java.util.Vector getDetailvo() {
		return m_vDetail;
	}

	public boolean isCombined() {
		return m_isCombined;
	}

	/**
	 * isSubtotal ����ע�⡣
	 */
	public boolean isSubtotal() {
		return m_isSubtotal;
	}

	/**
	 * isTotal ����ע�⡣
	 */
	public boolean isTotal() {
		return m_isTotal;
	}

	public void setAttributeValue(String key, Object value) {
		if (value == null)
			m_hData.remove(key.toLowerCase());
		else
			m_hData.put(key.toLowerCase(), value);
	}

	public void setCombined(boolean b) {
		m_isCombined = b;
	}

	/**
	 * setDetailVO ����ע�⡣
	 */
	public void setDetailVO(java.util.Vector v) {
		m_vDetail = v;
	}

	/**
	 * setSubtotal ����ע�⡣
	 */
	public void setSubtotal(boolean b) {
		m_isSubtotal = b;
	}

	/**
	 * setTotal ����ע�⡣
	 */
	public void setTotal(boolean b) {
		m_isTotal = b;
	}

	public void validate() {
		return;
	}

	private Map m_hData = new HashMap();

	public Object cloneIncludeDetail() {
		ReportBaseVO vo = null;
		try {
			vo = (ReportBaseVO) getClass().newInstance();
		} catch (Exception ex) {
			return null;
		}
		vo.setCombined(isCombined());
		Vector v = getDetailvo();
		if (v != null && v.size() > 0) {
			Vector vnew = new Vector();
			for (int i = 0; i < v.size(); i++) {
				vnew.add(((ReportBaseVO) v.get(i)).clone());
			}
			vo.setDetailVO(vnew);
		}
		vo.setSubtotal(isSubtotal());
		vo.setTotal(isTotal());
		String[] attr = getAttributeNames();
		for (int i = 0; i < attr.length; i++) {
			vo.setAttributeValue(attr[i], getAttributeValue(attr[i]));
		}
		return vo;
	}

	/**
	 *�õ���key��ֵ
	 **/
	public String[] getAllValueByKey(String key) {
		Vector v = getAllValueByKey(null, this, key);
		if (v != null)
			return (String[]) Convertor.convertVectorToArray(v, String.class);
		return null;
	}

	/**
	 *�õ���key��ֵ
	 **/
	public Vector getAllValueByKey(Vector v, ReportBaseVO vo, String key) {
		if (v == null)
			v = new Vector();
		if (vo.isCombined() || vo.isSubtotal() || vo.isTotal()) { // �ݹ�����
			Vector vDetail = vo.getDetailvo();
			if (vDetail != null && vDetail.size() > 0) {
				for (int i = 0; i < vDetail.size(); i++) {
					ReportBaseVO voTmp = (ReportBaseVO) vDetail.get(i);
					getAllValueByKey(v, voTmp, key);
				}
			}
			Object o = vo.getAttributeValue(key);
			if (o != null && !v.contains(o))
				v.add(vo.getAttributeValue(key));
		} else {
			Object o = vo.getAttributeValue(key);
			if (o != null && !v.contains(o))
				v.add(vo.getAttributeValue(key));
		}
		return v;
	}

	/**
	 *�õ������е�VO.
	 */
	public ReportBaseVO[] getAllVOs(String[] orderFlds, boolean isDesc) {
		SimpleSortVOTool sortTool = null;
		if (orderFlds != null)
			sortTool = new SimpleSortVOTool();
		Vector v = getAllVOs(null, this, orderFlds, sortTool, isDesc);
		return (ReportBaseVO[]) Convertor.convertVectorToArray(v,
				ReportBaseVO.class);
	}

	/**
	 *�ݹ�����:�ϲ�VO���͡� ִ�ж������������¼���VO.Ȼ����Լ���
	 */
	public Vector getAllVOs(Vector v, ReportBaseVO vo, String[] orderbyFlds,
			SimpleSortVOTool sortTool, boolean isDesc) {
		if (v == null)
			v = new Vector();
		if (vo.isCombined() || vo.isSubtotal() || vo.isTotal()) { // �ݹ�����
			Vector vDetail = vo.getDetailvo();
			if (sortTool != null && orderbyFlds != null)
				sortTool.sortVO(vDetail, orderbyFlds, isDesc);
			if (vDetail != null && vDetail.size() > 0) {
				for (int i = 0; i < vDetail.size(); i++) {
					ReportBaseVO voTmp = (ReportBaseVO) vDetail.get(i);
					getAllVOs(v, voTmp, orderbyFlds, sortTool, isDesc);
				}
			}
			v.add(vo);
		} else {
			v.add(vo);
		}
		return v;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(01-3-20 17:26:03)
	 * 
	 * @return java.lang.String[]
	 */
	public java.lang.String[] getAttributeNames() {
		Vector v = new Vector();
		Iterator it = m_hData.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry o = (Map.Entry) it.next();
			if (o.getKey() != null)
				v.add(o.getKey());
		}

		// Enumeration en = m_hData.keys();
		// Vector v = Convertor.getVector(en);
		String[] arsKey = new String[v.size()];
		v.copyInto(arsKey);
		return arsKey;
	}

	public Boolean getBooleanValue(String attrname) {
		Object o = getAttributeValue(attrname);
		if (o == null)
			return null;
		if (o instanceof Boolean)
			return (Boolean) o;
		return new Boolean(o.toString());

	}

	public UFDouble getDoubleValue(String attrname) {
		Object o = getAttributeValue(attrname);
		if (o == null) {
			setAttributeValue(attrname, new UFDouble(0));
			return getDoubleValue(attrname);
		} else if (o instanceof UFDouble)
			return (UFDouble) o;
		else
			return new UFDouble(o.toString());
	}

	public java.lang.String getEntityName() {
		return null;
	}

	public Integer getIntegerValue(String attrname) {
		Object o = getAttributeValue(attrname);
		if (o == null)
			return new Integer(0);
		if (o instanceof Integer)
			return (Integer) o;
		return new Integer(o.toString());
	}

	public String getStringValue(String attrname) {
		Object o = getAttributeValue(attrname);
		return o == null ? null : o.toString();

	}

	public UFDate getUFDateValue(String attrname) {
		Object o = getAttributeValue(attrname);
		if (o == null)
			return null;
		if (o instanceof UFDate)
			return (UFDate) o;
		return new UFDate(o.toString());
	}
}
package nc.vo.zmpub.pub.report2;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.DefaultTreeModel;

import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDouble;
import nc.vo.zmpub.pub.report.IReportVO;
import nc.vo.zmpub.pub.report.IUFTypes;
import nc.vo.zmpub.pub.report.SumInfoVO;
import nc.vo.zmpub.pub.report.Toolkit;
/**
	* �����ࣺ����ּ�ͳ��VO.
	* �������ڣ�(2003-11-13 22:33:57)
	* @author��������
  */
public class ReportLeveledTool {
	private ExTreeNode[] topnodes = null; //���ѡ��ڵ�
	private SumInfoVO m_voSumInfo = null; //��ϼ�VO
	private LevelByOrgContext m_voLevelInfo = null; //������֯�ּ�VO.
	private boolean isShowZero = true; //������е������Ϊ0�Ƿ���ʾ��
	private boolean m_isCheckDetailIterative = false; //�Ƿ���ڵ�С�ƺϼ�VO��ϸ���ظ���¼��
	//VO���͡�
	private final int NODEVO = 0; //�ڵ�VO
	private final int SUBTOTALVO = 1; //С��VO.
	private final int TOTALVO = 2; //�ϼ�VO.
	
	public class SumVisitor {
		public String[] m_arsValueFields = null;
		public int[] m_types = null;
		//�Ƿ�����ϸ�ظ���
		public boolean m_isCheckDetailIterative = false;
		public nc.vo.pub.CircularlyAccessibleValueObject m_resultVO = null;
		/**
		* SumVisitor ������ע�⡣
		*/
		public SumVisitor(SumInfoVO vo, boolean isCheckDetailIterative)
	{
			super();
			//���Ƶ����ࡣ
			m_arsValueFields = vo.getFields();
			m_types = vo.getTypes();
			m_isCheckDetailIterative = isCheckDetailIterative;
		}
		public void addDetail(nc.vo.pub.CircularlyAccessibleValueObject voTotal)
	{
			if (voTotal == null)
				return;
			java.util.Vector vDetail = ((IReportVO) getResult()).getDetailvo();
			//���ϼƵ���ϸ���ܡ�
			java.util.Vector vDetailOnTotal = ((IReportVO) voTotal).getDetailvo();
			if (vDetailOnTotal != null && vDetailOnTotal.size() > 0)
			{
				java.util.Vector v = ((IReportVO) voTotal).getDetailvo();
				//�����ڵ���ĳЩ������о���ظ���������������жϡ�
				for (int i = 0; i < v.size(); i++)
				{
					if (m_isCheckDetailIterative
						&& !vDetail.contains(v.elementAt(i))
						|| (!m_isCheckDetailIterative))
						vDetail.add(v.elementAt(i));
				}
			}
			else
				vDetail.add((nc.vo.pub.CircularlyAccessibleValueObject) voTotal);

		}
		public String[] getFields()
	{
			return m_arsValueFields;
		}
		public nc.vo.pub.CircularlyAccessibleValueObject getResult()
	{
			return m_resultVO;
		}
		public int[] getTypes()
	{
			return m_types;
		}
		public void sum(
			nc.vo.pub.CircularlyAccessibleValueObject voTotal,
			nc.vo.pub.CircularlyAccessibleValueObject voDetail)
	{ //��ʼvoTotal.
			if (voTotal == null)
			{
				if (voDetail != null)
					voTotal = (nc.vo.pub.CircularlyAccessibleValueObject) voDetail.clone();
				else
					return;
			} //��ʼ��m_resultVO.
			if (getResult() == null)
			{
				if (voTotal != null)
				{
					m_resultVO = (nc.vo.pub.CircularlyAccessibleValueObject) voTotal.clone();
					//addDetail(voTotal)��voTotal��Detail���Ƶ��µ�Vector;
					 ((IReportVO) m_resultVO).setDetailVO(new java.util.Vector());
					addDetail(voTotal);
				}
				return;
			}
			for (int i = 0; i < getFields().length; i++)
			{
				Object tmpobj = voTotal.getAttributeValue(getFields()[i]);
				Object resultobj = getResult().getAttributeValue(getFields()[i]);
				switch (getTypes()[i])
				{
					case IUFTypes.INT :
						int iresult = (resultobj == null ? 0 : ((Integer) resultobj).intValue());
						int itmp = (tmpobj == null ? 0 : ((Integer) tmpobj).intValue());
						getResult().setAttributeValue(getFields()[i], new Integer(iresult + itmp));
						continue;
					case IUFTypes.LONG :
						long lgtmp = (tmpobj == null ? 0 : ((Long) tmpobj).longValue());
						long lgresult = (resultobj == null ? 0 : ((Long) resultobj).longValue());
						if (tmpobj != null)
							getResult().setAttributeValue(getFields()[i], new Long(lgresult + lgtmp));
						continue;
					case IUFTypes.UFD :
						nc.vo.pub.lang.UFDouble ufdtmp =
							(tmpobj == null
								? new nc.vo.pub.lang.UFDouble("0")
								: (nc.vo.pub.lang.UFDouble) tmpobj);
						nc.vo.pub.lang.UFDouble ufdResult =
							(resultobj == null
								? new nc.vo.pub.lang.UFDouble("0")
								: (nc.vo.pub.lang.UFDouble) resultobj);
						getResult().setAttributeValue(getFields()[i], ufdResult.add(ufdtmp));
						continue;
				}
			}
			addDetail(voTotal);
		}
		public void visitForSum(ExTreeNode node)
	{
			sum(node.getTotalVO(), node.getNodeVO());
		}
	};

/**
 * AcctReportTool ������ע�⡣
 */
public ReportLeveledTool() {
	super();
}
private Vector combinArray(
	nc.vo.pub.CircularlyAccessibleValueObject[][] voss) {
	Vector v = new Vector();
	for (int i = 0; i < voss.length; i++) {
		for (int j = 0; j < voss[i].length; j++) {
			v.add(voss[i][j]);
		}
	}
	return v;
}
private ExTreeNode[] combinDown(ExTreeNode[] node) {
	for (int i = 0; i < node.length; i++) {
		node[i]=combinDown(node[i]);
	}
	System.out.println("�ڵ����ݺϲ����");
	return node;
}
private ExTreeNode combinDown(ExTreeNode node)
{
	SumVisitor visitor = new SumVisitor(getSumInfoVO(), m_isCheckDetailIterative);
	//����NodeVO.
	m_voLevelInfo.getAdjustNodeTool().adjustNodeVO(node);
	if (!node.isLeaf())
	{
		for (int i = 0; i < node.getChildObjtypecode().length; i++)
		{
			SumVisitor visitorTmp =
				new SumVisitor(getSumInfoVO(), m_isCheckDetailIterative);
			for (int j = 0;
				j < node.getChildNodeByObjcode(node.getChildObjtypecode()[i]).length;
				j++)
			{
				ExTreeNode nodetmp =
					node.getChildNodeByObjcode(node.getChildObjtypecode()[i])[j];
				nodetmp = combinDown(nodetmp);
				if (nodetmp.getTotalVO() == null || !nodetmp.isSelected())
					continue;
				visitorTmp.visitForSum(nodetmp);
				visitor.visitForSum(nodetmp);
			}
			//����С��VO.��ĩ�������ʼ��С�ơ�
			if (visitorTmp.getResult() != null)
			{
				if (node.getSubTotalVOByObjcode(node.getChildObjtypecode()[i]) == null)
				{
					node.setSubtotalVOByObjcode(
						node.getChildObjtypecode()[i],
						(CircularlyAccessibleValueObject) visitorTmp.getResult().clone());
				}
				(
					(IReportVO) node.getSubTotalVOByObjcode(
						node.getChildObjtypecode()[i])).setSubtotal(
					true);
				m_voLevelInfo.getAdjustNodeTool().adjustSubtotalVO(
					node,
					node.getChildObjtypecode()[i]);
			}
		}
		//���úϼ�VO.
		visitor.visitForSum(node);
		if (visitor.getResult() == null)
			return node;
		node.setTotalVO((CircularlyAccessibleValueObject) visitor.getResult());
		((IReportVO) node.getTotalVO()).setSubtotal(true);
		m_voLevelInfo.getAdjustNodeTool().adjustTotalVO(node);
	}
	else
	{
		visitor.visitForSum(node);
		if (visitor.getResult() != null)
			node.setTotalVO((CircularlyAccessibleValueObject) visitor.getResult());
	}
	return node;
}
public nc.vo.pub.CircularlyAccessibleValueObject[] convertArray(Vector v) {
	if (v == null)
		return null;
	CircularlyAccessibleValueObject[] vos = new CircularlyAccessibleValueObject[v.size()];
	v.copyInto(vos);
	return vos;
}
public LevelByOrgContext getLevelInfo()
{
	return m_voLevelInfo;
}
/*
��ڷ�����
*/
public nc.vo.pub.CircularlyAccessibleValueObject[] getLevelReportVO(
	LevelByOrgContext voLevelinfo,
	SumInfoVO voSumInfo,
	Boolean isCheckDetailIterative)
{
	//�������ݡ�
	m_voLevelInfo = voLevelinfo;
	m_voSumInfo = voSumInfo;
	if (isCheckDetailIterative != null)
		m_isCheckDetailIterative = isCheckDetailIterative.booleanValue();
	DefaultTreeModel tm1 = voLevelinfo.getCombinedTM();
	/*�õ�������߽ڵ㡣�п��ܵõ������*/
	topnodes = getTopNodes((ExTreeNode) tm1.getRoot(), null);
	/*** ��ÿһ���ڵ����µݹ���ܡ�
		*��ÿһ����ĩ���ڵ��ʼ�ϼ�С�Ƶ�VO.
		*���ڷ�ĩ���ڵ����С�ƺϼƵ����á��ϼƱ�Ϊ���˽ڵ�����ƣ���С�ƣ��ϼơ�
		*�ϼƵ�ֵΪ���е��ӽڵ�ĺͣ��������
		*�ڵ�Ҫʵ���Լ���DetailVO.
		*/
	topnodes = combinDown(topnodes);
	/*
	�õ�ÿһ��ѡ��ڵ��VO[].
	*/
	nc.vo.pub.CircularlyAccessibleValueObject[][] voss =
		getLevelvoByNodes(topnodes);
	//��VO[]���кϲ�ͬʱ��С�ƺϼơ�
	Vector levelvos = combinArray(voss);
	//����ж����߽ڵ�,����������ܵ�VO.
	if (topnodes != null && topnodes.length > 1)
	{
		CircularlyAccessibleValueObject voTotal = getTotalVO(topnodes);
		if (voTotal != null)
			levelvos.add(voTotal);
	}
	return convertArray(levelvos);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2003-11-15 11:42:36)
 * @return nc.vo.mbt.report.AcctReportVO[][]
 * @param nodes[i]s nc.ui.mbt.util.ExTreeNode
 */
 private CircularlyAccessibleValueObject[][] getLevelvoByNodes(ExTreeNode[] nodes) {
	CircularlyAccessibleValueObject[][] voss = new CircularlyAccessibleValueObject[nodes.length][];
	Vector v = null;
	for (int i = 0; i < nodes.length; i++) {
		v = getLevelvoByNodes(nodes[i], null);
		voss[i] = convertArray(v);
	}
	System.out.println("ÿ��top�ڵ��VO[]:" + voss.length);
	return voss;
}
private Vector getLevelvoByNodes(ExTreeNode node, Vector v) {
	if (v == null)
		v = new Vector();
	for (int i = 0; i < node.getChildObjtypecode().length; i++) {
		if (node.getNodeVO() != null&&isShow(node,NODEVO)&&i==0)
			v.add(node.getNodeVO());
		for (int j = 0;
			j < node.getChildNodeByObjcode(node.getChildObjtypecode()[i]).length;
			j++) {
			ExTreeNode tmp = node.getChildNodeByObjcode(node.getChildObjtypecode()[i])[j];
			if (!tmp.isLeaf()) {
				getLevelvoByNodes(tmp, v);
			}
			if (tmp.getNodeVO() != null &&tmp.isLeaf()&&isShow(tmp,NODEVO))
				v.add(tmp.getNodeVO());
		}
		if (node.getSubTotalVOByObjcode(node.getChildObjtypecode()[i]) != null
			&& !node.isLeaf()&&m_voLevelInfo.isShowSubtotal()&&isShow(node,SUBTOTALVO))
			v.add(node.getSubTotalVOByObjcode(node.getChildObjtypecode()[i]));
	}
	if(node.istopSelected()&&node.getNodeVO()!=null&&node.isLeaf()) 
	v.add(node.getNodeVO());
	if (node.getTotalVO() != null && !node.isLeaf()&&isShow(node,TOTALVO))
		v.add(node.getTotalVO());
	return v;
}
private SumInfoVO getSumInfoVO() {
	return m_voSumInfo;
}
//��߻��ܽڵ����Ϊ��һ������
private ExTreeNode[] getTopNodes(ExTreeNode node, Vector v) {
	if (v == null) {
		v = new Vector();
	}
	if (node.isSelected() && node.getParent() == null) {
		node.setTopSelected(true);
		v.add(node);
		return new ExTreeNode[] { node };
	}
	if (node.isSelected()
		&& !((ExTreeNode) node.getParent()).isSelected()
		&& node.getObjtypecode().equalsIgnoreCase(
			m_voLevelInfo.getObjcodeByLevel(0)[0])) {
		node.setTopSelected(true);
		v.add(node);
	} else {
		if (node.getChildCount() > 0) {
			Enumeration en = node.children();
			while (en.hasMoreElements()) {
				//�ݹ�õ����е���߻��ܽڵ㡣
				getTopNodes(((ExTreeNode) en.nextElement()), v);
			}
		}
	}
	ExTreeNode[] topnodes = new ExTreeNode[v.size()];
	for (int i = 0; i < topnodes.length; i++) {
		topnodes[i] = (ExTreeNode) v.elementAt(i);
	}
	return topnodes;
}
private CircularlyAccessibleValueObject getTotalVO(ExTreeNode[] nodes) {
	//  vo.setPubobj("-�ܼ�-");
	SumVisitor visitor = new SumVisitor(getSumInfoVO(),m_isCheckDetailIterative);
	for (int i = 0; i < nodes.length; i++) {
		if (nodes[i] != null && nodes[i].getTotalVO() != null) {
			visitor.visitForSum(nodes[i]);
		}
	}
	System.out.println("�õ�����VO!");
	if (visitor.getResult() != null) {
		m_voLevelInfo.getAdjustNodeTool().adjustAllTotalVO(visitor.getResult());
		return visitor.getResult();
	}
	return null;
}
/*
vo�Ƿ���ʾ���жϡ�
�����Ƿ�ȫ��Ϊ0�Ƿ���ʾ���жϡ�
���ڵĲ����ǣ����subtotal,totalvo��Ϊ0 ����˽ڵ㲻��ʾ��
����Ҫ�ĳ�ÿһ��VO�����ȶ��������ڵĽڵ㡣
�Ƿ�ֻ����ʾ��߽ڵ�Ҳ�����ˡ�
*/
private boolean isShow(ExTreeNode node, int iVOtype) {
	/**
	�������Ϊ��,��Ϊȫѡ
	�Ƿ�����ʾ�ļ�����.
	*/
	boolean isInLevel = m_voLevelInfo.getShowLevels() == null || Toolkit.binarySearch(m_voLevelInfo.getShowLevels(), Integer.toString(node.getLevel())) > -1;
	if (!isInLevel)
		return false;
	//�Ƿ���ʾ��ϸ.
	if (iVOtype == NODEVO && !m_voLevelInfo.isShowDetail() && node.isLeaf())
		return false;
	//�Ƿ���ʾС��.
	if (iVOtype == SUBTOTALVO && !m_voLevelInfo.isShowSubtotal())
		return false;
	//�˽ڵ��Ƿ�ѡ��
	if (!node.isSelected())
		return false;
	//Ϊ���Ƿ���ʾ.
	if (!isShowZero)
		return !visitForZero(node);
	else
		return isShowZero;
}
public CircularlyAccessibleValueObject[] reCalLevelVO()
{
	if (topnodes != null && topnodes.length == 0)
		return null;
	CircularlyAccessibleValueObject[][] voss = getLevelvoByNodes(topnodes);
	//��VO[]���кϲ�ͬʱ��С�ƺϼơ�
	Vector levelvos = combinArray(voss);
	//���������ܵ�VO.
	if (topnodes != null && topnodes.length > 1)
		levelvos.add(getTotalVO(topnodes));
	return convertArray(levelvos);
}
private  boolean visitForZero(ExTreeNode node) {
	CircularlyAccessibleValueObject nodeVO = node.getNodeVO();
	CircularlyAccessibleValueObject totalVO = node.getTotalVO();
	boolean b1 = true;
	if (nodeVO == null && totalVO == null) {
		String[] arsSubObjcode = node.getChildObjtypecode();
		boolean isSubtotalNull = true;
		for (int i = 0; i < arsSubObjcode.length; i++) {
			if (node.getSubTotalVOByObjcode(arsSubObjcode[i]) != null) {
				isSubtotalNull = false;
				break;
			}
		}
		if (isSubtotalNull)
			return true;
	}
	for (int i = 0; i < m_voSumInfo.getFields().length; i++) {
		boolean tmp = false;
		switch (m_voSumInfo.getTypes()[i]) {
			case IUFTypes.INT :
				if ((nodeVO == null
					|| ((Integer) nodeVO.getAttributeValue(m_voSumInfo.getFields()[i])).intValue()
						== 0)
					&& (totalVO == null
						|| ((Integer) totalVO.getAttributeValue(m_voSumInfo.getFields()[i])).intValue()
							== 0)) {
					tmp = true;
					continue;
				}
			case IUFTypes.LONG :
				if ((nodeVO == null
					|| ((Long) nodeVO.getAttributeValue(m_voSumInfo.getFields()[i])).longValue() == 0
					&& (totalVO == null
						|| ((Long) totalVO.getAttributeValue(m_voSumInfo.getFields()[i])).longValue()
							== 0))) {
					tmp = true;
					continue;
				}
			case IUFTypes.UFD :
				if ((nodeVO == null
					|| nodeVO.getAttributeValue(m_voSumInfo.getFields()[i]) == null
					|| ((UFDouble) nodeVO.getAttributeValue(m_voSumInfo.getFields()[i])).equals(
						new UFDouble("0")))
					&& (totalVO == null
						|| totalVO.getAttributeValue(m_voSumInfo.getFields()[i]) == null
						|| ((UFDouble) totalVO.getAttributeValue(m_voSumInfo.getFields()[i])).equals(
							new UFDouble("0")))) {
					tmp = true;
					continue;
				}
		}
		String[] arsSubObjcode = node.getChildObjtypecode();
		boolean tmp1 = false;
		for (int k = 0; k < arsSubObjcode.length; k++) {
			if (node.getSubTotalVOByObjcode(arsSubObjcode[k]) == null
				|| node.getSubTotalVOByObjcode(arsSubObjcode[k]).getAttributeValue(
					m_voSumInfo.getFields()[i])
					== null
				|| node
					.getSubTotalVOByObjcode(arsSubObjcode[k])
					.getAttributeValue(m_voSumInfo.getFields()[i])
					.equals(new nc.vo.pub.lang.UFDouble("0")))
				tmp1 = true;
		}
		b1 = b1 && tmp && tmp1;
	}
//	System.out.println("-->@:)this node is Zero?" + new Boolean(b1));
	return b1;
}
}

package nc.vo.zmpub.pub.report2;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.ValueObject;
import nc.vo.zmpub.pub.report.Toolkit;

/**
 * �������ڣ�(2004-4-14 14:43:11)
 * 
 * @author��������
 */
public class ExTreeNode extends nc.ui.pub.util.NCTreeNode {
	// ����ڵ����ݵ�VO.���粿�ŵ��������ǲ��ŵ�VO.
	private nc.vo.pub.ValueObject m_vo = null;

	/**
	 * ExTreeNode ������ע�⡣
	 * 
	 * @param userObject
	 *            java.lang.Object
	 */
	public ExTreeNode(Object userObject) {
		super(userObject);
	}

	/**
	 * ExTreeNode ������ע�⡣
	 * 
	 * @param userObject
	 *            java.lang.Object newOID String
	 */
	public ExTreeNode(Object userObject, String newOID) {
		super(userObject);
	}

	/**
	 * MyNode ������ע�⡣
	 * 
	 * @param userObject
	 *            java.lang.Object
	 */
	public ExTreeNode(Object userObject, nc.vo.pub.ValueObject vo) {
		super(userObject);
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-4-2 17:21:33)
	 */
	public nc.vo.pub.ValueObject getVO() {
		return m_vo;
	}

	// �ڵ��Ƿ�Ϊȡֵ�ڵ�
	private boolean isKeyNode = false;
	// �ڵ��Ƿ�ѡ��
	private boolean isSelected = false;
	// �Ƿ���ѡ������ϼ��ڵ㡣
	private boolean isTopSelected = false;
	// ��������->ChildNode.
	private Hashtable m_hObjChildNode = new Hashtable();
	// ��������->С��VO.
	private Hashtable m_hSubtotalvo = new Hashtable();
	// �Ƿ��Զ����
	private boolean m_isDef = false;
	// �Ƿ�ɱ༭��
	private boolean m_isEditable = true;
	// ���ڵ�ķ�����VO
	private CircularlyAccessibleValueObject m_nodevo = null;
	// �˽ڵ�ĺ���������͡�
	private String m_objtypecode = null;
	// �ڵ�������
	private String m_strNodeDesc = null;
	// ��һ���������͡�
	private String m_strParentCode = null;
	// ��һ����������ID.���û���ϼ���Ϊ�ա�
	private String m_superobjid = null;
	// �ϼ�VO.
	private nc.vo.pub.CircularlyAccessibleValueObject m_totalvo = null;

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-6-15 11:01:21)
	 * 
	 * @param userObject
	 *            java.lang.Object
	 * @param allowChildren
	 *            boolean
	 */
	public ExTreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}

	/**
	 * �������ڣ�(2002-5-23 15:09:37)
	 * 
	 * @return java.lang.Object
	 */
	public Object clone() {
		/**
		 * You must do like fellow,I think maybe this is a java bug.
		 */
		ExTreeNode newTreeNode = new ExTreeNode(getNodeDesc());
		// (ExTreeNode) super.clone();
		newTreeNode.setDef(isDef());
		newTreeNode.setId(getId());
		newTreeNode.setKeyNode(isKeyNode);
		newTreeNode.setNodeCode(getNodeCode());
		newTreeNode.setNodeDesc(getNodeDesc());
		newTreeNode.setNodeName(getNodeName());
		newTreeNode.setObjtypecode(getObjtypecode());
		newTreeNode.setParentCode(getParentCode());
		newTreeNode.setSelected(isSelected);
		newTreeNode.setSuperobjid(getSuperobjid());
		newTreeNode.setTopSelected(istopSelected());
		newTreeNode.setVO(getVO() == null ? null : (ValueObject) getVO()
				.clone());
		newTreeNode.setNodeVO(getNodeVO() == null ? null
				: (CircularlyAccessibleValueObject) getNodeVO().clone());
		newTreeNode.setTotalVO(getTotalVO() == null ? null
				: (CircularlyAccessibleValueObject) getTotalVO().clone());
		Vector vObjtypecode = nc.vo.jcom.util.Convertor.getVector(m_hSubtotalvo
				.keys());
		// xkf ����v5 convertor��仯
		// nc.vo.pub.util.Convertor.getVector(m_hSubtotalvo.keys());
		if (!Toolkit.isEmpty(vObjtypecode)) {
			for (int i = 0; i < vObjtypecode.size(); i++) {
				newTreeNode
						.setSubtotalVOByObjcode(
								vObjtypecode.elementAt(i).toString(),
								(CircularlyAccessibleValueObject) (getSubTotalVOByObjcode(vObjtypecode
										.elementAt(i).toString()).clone()));
			}
		}

		return newTreeNode;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-5-28 15:55:29)
	 * 
	 * @return nc.ui.pub.beans.tree.ExTreeNode
	 */
	public ExTreeNode cloneIncludeChildren(ExTreeNode sourceNode) {
		ExTreeNode targetNode = (ExTreeNode) sourceNode.clone();
		for (int i = 0; i < sourceNode.getChildCount(); i++) {
			ExTreeNode sourceNodeChild = (ExTreeNode) sourceNode.getChildAt(i);
			ExTreeNode targetNodeChild = cloneIncludeChildren(sourceNodeChild);
			targetNode.add(targetNodeChild);
		}
		return targetNode;
	}

	public void deSelectAllNodes() {
		if (isSelected())
			setSelected(false);
		if (this.getChildCount() > 0) {
			java.util.Enumeration en = this.children();
			while (en.hasMoreElements()) {
				ExTreeNode node1 = (ExTreeNode) en.nextElement();
				node1.deSelectAllNodes();
			}
		}
	}

	public void deSelectAllNodesBesidesName(String[] arsMutaxName) {
		if (isSelected()
				&& Toolkit.binarySearch(arsMutaxName, getNodeDesc()) < 0)
			setSelected(false);
		if (getChildCount() > 0) {
			java.util.Enumeration en = this.children();
			while (en.hasMoreElements()) {
				ExTreeNode node1 = (ExTreeNode) en.nextElement();
				node1.deSelectAllNodesBesidesName(arsMutaxName);
			}
		}
	}

	public void deSelectAllNodesByName(String[] arsMutaxName) {
		if (isSelected()
				&& Toolkit.binarySearch(arsMutaxName, getNodeDesc()) > -1)
			setSelected(false);
		if (getChildCount() > 0) {
			java.util.Enumeration en = this.children();
			while (en.hasMoreElements()) {
				ExTreeNode node1 = (ExTreeNode) en.nextElement();
				node1.deSelectAllNodesByName(arsMutaxName);
			}
		}
	}

	public Vector getAllKeyCode(Vector vCode) {
		// ���Ϊ�Զ������򷵻����ơ�
		if (vCode == null) {
			vCode = new Vector();
		}
		if (isKeyNode() && isSelected())
			vCode.add(this.getNodeCode());
		if (this.getChildCount() > 0) {
			Enumeration en = this.children();
			while (en.hasMoreElements()) {
				ExTreeNode node1 = (ExTreeNode) en.nextElement();
				node1.getAllKeyCode(vCode);
			}
		}
		return vCode;
	}

	public Vector getAllKeyNames(Vector vName) {
		if (vName == null) {
			vName = new Vector();
		}
		if (this.isKeyNode() && this.isSelected())
			vName.add(this.getNodeDesc());
		if (this.getChildCount() > 0) {
			Enumeration en = this.children();
			while (en.hasMoreElements()) {
				ExTreeNode node1 = (ExTreeNode) en.nextElement();
				node1.getAllKeyNames(vName);
			}
		}
		return vName;
	}

	public Vector getAllKeyOIDs(Vector vOID) {
		// ���Ϊ�Զ������򷵻����ơ�
		if (isDef())
			return getAllKeyNames(vOID);
		if (vOID == null) {
			vOID = new Vector();
		}
		if (isKeyNode() && isSelected())
			vOID.add(this.getOID());
		if (this.getChildCount() > 0) {
			Enumeration en = this.children();
			while (en.hasMoreElements()) {
				ExTreeNode node1 = (ExTreeNode) en.nextElement();
				node1.getAllKeyOIDs(vOID);
			}
		}
		return vOID;
	}

	public java.util.Vector getAllLeaf(Vector vleaf) {
		if (vleaf == null) {
			vleaf = new java.util.Vector();
		}
		if (this.isLeaf())
			vleaf.addElement(this);
		else {
			java.util.Enumeration en = this.children();
			while (en.hasMoreElements()) {
				ExTreeNode node1 = (ExTreeNode) en.nextElement();
				node1.getAllLeaf(vleaf);
			}
		}
		return vleaf;
	}

	public java.util.Hashtable getAllLeafByObjtypecode(Hashtable hnode,
			String objtypecode) {
		if (hnode == null) {
			hnode = new java.util.Hashtable();
		}
		if (this.isLeaf() && this.getOID() != null
				&& this.getObjtypecode().equalsIgnoreCase(objtypecode))
			hnode.put(this.getOID(), this);
		if (!this.isLeaf()) {
			java.util.Enumeration en = this.children();
			while (en.hasMoreElements()) {
				ExTreeNode node1 = (ExTreeNode) en.nextElement();
				node1.getAllLeafByObjtypecode(hnode, objtypecode);
			}
		}
		return hnode;
	}

	public java.util.Hashtable getAllNodeByObjtypecode(Hashtable hnode,
			String objtypecode) {
		if (hnode == null) {
			hnode = new java.util.Hashtable();
		}
		if (this.getOID() != null
				&& this.getObjtypecode().equalsIgnoreCase(objtypecode))
			hnode.put(this.getOID(), this);
		if (!this.isLeaf()) {
			java.util.Enumeration en = this.children();
			while (en.hasMoreElements()) {
				ExTreeNode node1 = (ExTreeNode) en.nextElement();
				node1.getAllNodeByObjtypecode(hnode, objtypecode);
			}
		}
		return hnode;
	}

	public java.util.Hashtable getAllNodeByObjtypecodeObjCodeAsKey(
			Hashtable hnode, String objtypecode) {
		if (hnode == null) {
			hnode = new java.util.Hashtable();
		}
		if (this.getOID() != null
				&& this.getObjtypecode().equalsIgnoreCase(objtypecode))
			hnode.put(this.getNodeCode(), this);
		if (!this.isLeaf()) {
			java.util.Enumeration en = this.children();
			while (en.hasMoreElements()) {
				ExTreeNode node1 = (ExTreeNode) en.nextElement();
				node1.getAllNodeByObjtypecodeObjCodeAsKey(hnode, objtypecode);
			}
		}
		return hnode;
	}

	public ExTreeNode[] getChildNodeByObjcode(String strObjcode) {
		if (m_hObjChildNode.get(strObjcode) == null
				|| ((ExTreeNode[]) m_hObjChildNode.get(strObjcode)).length < 1) {
			Enumeration en = this.children();
			Vector v = new Vector();
			while (en.hasMoreElements()) {
				ExTreeNode thisnode = (ExTreeNode) en.nextElement();
				if (thisnode.getObjtypecode().equals(strObjcode))
					v.addElement(thisnode);
			}
			ExTreeNode[] arNode = new ExTreeNode[v.size()];
			v.copyInto(arNode);
			m_hObjChildNode.put(strObjcode, arNode);
		}

		return (ExTreeNode[]) m_hObjChildNode.get(strObjcode);
	}

	public String[] getChildObjtypecode() {
		Vector v = new Vector();
		Enumeration en = this.children();
		while (en.hasMoreElements()) {
			ExTreeNode thisnode = (ExTreeNode) en.nextElement();
			boolean newflag = true;
			for (int i = 0; i < v.size(); i++) {
				if (v.elementAt(i).equals(thisnode.getObjtypecode())) {
					newflag = false;
					break;
				}
			}
			if (newflag)
				v.addElement(thisnode.getObjtypecode());
		}
		String[] arsObjcode = new String[v.size()];
		v.copyInto(arsObjcode);
		return arsObjcode;
	}

	public ExTreeNode getNodeByIdVobjtypecode(String strObjid,
			String strObjtypecode) {
		if (this.getOID() != null
				&& this.getObjtypecode().equals(strObjtypecode)
				&& this.getOID().equals(strObjid))
			return this;
		if (!this.isLeaf()) {
			Enumeration en = this.children();
			while (en.hasMoreElements()) {
				ExTreeNode nodeTmp = (ExTreeNode) en.nextElement();
				ExTreeNode node = nodeTmp.getNodeByIdVobjtypecode(
						strObjid == null ? null : strObjid.trim(),
						strObjtypecode);
				if (node != null)
					return node;
			}
		}
		return null;
	}

	public String getNodeDesc() {
		return m_strNodeDesc;
	}

	public CircularlyAccessibleValueObject getNodeVO() {
		return m_nodevo;
	}

	public String getObjtypecode() {
		return m_objtypecode;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-4-2 17:31:42)
	 * 
	 * @return java.lang.String
	 */
	public String getOID() {
		if (isDef())
			return getNodeDesc() == null ? null : getNodeDesc().trim();
		return getId();
	}

	public String getParentNodeCode() {
		return m_strParentCode;
	}

	public CircularlyAccessibleValueObject getSubTotalVOByObjcode(
			String strObjcode) {
		return (CircularlyAccessibleValueObject) m_hSubtotalvo.get(strObjcode);
	}

	public String getSuperobjid() {
		return m_superobjid;
	}

	public CircularlyAccessibleValueObject getTotalVO() {
		return m_totalvo;
	}

	public boolean isDef() {
		return m_isDef;
	}

	public boolean isEditable() {
		return m_isEditable;
	}

	public boolean isKeyNode() {
		return isKeyNode;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public boolean istopSelected() {
		return ((this.getParent() != null && !((ExTreeNode) this.getParent())
				.isSelected()) || this.getParent() == null)
				&& this.isSelected();
	}

	public void selectAllChild(ExTreeNode node, boolean isSelected) {
		Enumeration en = node.children();
		while (en.hasMoreElements()) {
			ExTreeNode node1 = (ExTreeNode) (en.nextElement());
			selectAllChild(node1, isSelected);
			node1.setSelected(isSelected);
		}
	}

	public void selectNodeByCode(String strFromCode, String strToCode) {
		setSelected(getNodeCode() == null ? false : true
				&& (strFromCode == null ? true : getNodeCode().compareTo(
						strFromCode) > -1)
				&& (strToCode == null ? true : strToCode
						.compareTo(getNodeCode()) > -1));
		if (getChildCount() > 0) {
			java.util.Enumeration en = children();
			while (en.hasMoreElements()) {
				ExTreeNode node1 = (ExTreeNode) en.nextElement();
				if (!node1.isLeaf()) {
					node1.selectNodeByCode(strFromCode, strToCode);
				} else {
					node1.setSelected((strFromCode == null ? true : node1
							.getNodeCode().compareTo(strFromCode) > -1)
							&& (strToCode == null ? true : strToCode
									.compareTo(node1.getNodeCode()) > -1));
				}
			}
		}
	}

	public void selectNodeByCodeMatch(String strCodeMatch) {
		this.setSelected(this.getNodeCode() != null
				&& this.getNodeCode().indexOf(strCodeMatch) != -1);
		if (!this.isLeaf()) {
			java.util.Enumeration en = this.children();
			while (en.hasMoreElements()) {
				ExTreeNode nodeTmp = (ExTreeNode) en.nextElement();
				if (!nodeTmp.isLeaf()) {
					nodeTmp.selectNodeByCodeMatch(strCodeMatch);
				} else {
					nodeTmp
							.setSelected(nodeTmp.getNodeCode() != null
									&& nodeTmp.getNodeCode().indexOf(
											strCodeMatch) != -1);
				}
			}
		}
	}

	public void selectNodeByNameMatch(String strNameMatch) {
		this.setSelected(this.getNodeDesc() != null
				&& this.getNodeDesc().indexOf(strNameMatch) != -1);
		if (!this.isLeaf()) {
			java.util.Enumeration en = this.children();
			while (en.hasMoreElements()) {
				ExTreeNode nodeTmp = (ExTreeNode) en.nextElement();
				if (!nodeTmp.isLeaf()) {
					nodeTmp.selectNodeByNameMatch(strNameMatch);
				} else {
					nodeTmp
							.setSelected(nodeTmp.getNodeDesc() != null
									&& nodeTmp.getNodeDesc().indexOf(
											strNameMatch) != -1);
				}
			}
		}
	}

	public void setDef(boolean b) {
		m_isDef = b;
	}

	public void setEditable(boolean b) {
		m_isEditable = b;
	}

	public void setKeyNode(boolean b) {
		isKeyNode = b;
	}

	public void setNodeDesc(String s) {
		m_strNodeDesc = s;
	}

	public void setNodeVO(CircularlyAccessibleValueObject vo) {
		m_nodevo = vo;
	}

	public void setObjtypecode(String objtypecode) {
		m_objtypecode = objtypecode;
	}

	public void setParentNodeCode(String strParentcode) {
		m_strParentCode = strParentcode;
	}

	public void setSelected(boolean b) {
		isSelected = b;
	}

	public void setSubtotalVOByObjcode(String strObjcode,
			CircularlyAccessibleValueObject vo) {
		m_hSubtotalvo.put(strObjcode, vo);
	}

	public void setSuperobjid(String id) {
		m_superobjid = id;
	}

	public void setTopSelected(boolean b) {
		isTopSelected = b;
	}

	public void setTotalVO(CircularlyAccessibleValueObject vo) {
		m_totalvo = vo;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-5-23 15:43:23)
	 * 
	 * @param vo
	 *            nc.vo.pub.ValueObject
	 */
	public void setVO(nc.vo.pub.ValueObject vo) {
		m_vo = vo;
	}

	public String toString() {
		return getNodeDesc();
	}
}
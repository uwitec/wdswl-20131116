package nc.ui.zmpub.pub.freeitem;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.bs.logging.Logger;
import nc.ui.pub.beans.UIDialog;
import nc.vo.zmpub.pub.freeitem.FreeVO;
import nc.vo.zmpub.pub.freeitem.VInvVO;

/**
 * �����ߣ������� �������ڣ�(2001-5-9 ���� 6:44) ���ܣ����������
 * 
 * wnj:2005-04-28:add comment below:
 * 
 * �����ڴ������������˳������free1-free5�����ͣ������ֵ��bd_defdef.pk_defdef -----------------
 * ���type='ͳ��' ���͵Ĳ���pk_defdoclist��Ϊ�գ�
 * ��ô��bd_defdoclist��pk_defdoclist��Ӧ����ͳ�����͵Ĳ������ͱ��롢���ơ����确���ż��𡯡���������������
 * ͳ�����͵Ĳ���������bd_defdoc�б��档 -----------------
 * ���pk_defdoclistΪ�գ�����pk_bdinfoָ���Ĳ���Ϊ׼��
 * bd_bdinfo�б������UAPȱʡ������Ʒģ�鶨��(ָ����RefModel�಻Ϊ��)�Ĳ��ա� -----------------
 * ���pk_bdinfoΪ�գ��Ǿ��������Ͳ��գ�����������ע���ı����Ȳ���Ҫ���յ����� -----------------
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 */
public class FreeItemRefPane extends nc.ui.pub.beans.UIRefPane {
	private FreeItemDlg m_dlgFreeItemDlg;
	private java.lang.String m_sCode = "";
	private java.lang.String m_sInventoryName = "";
	private java.lang.String m_sSpec = "";
	private java.lang.String m_sType = "";
	// ���������PK
	private ArrayList m_alFreeItemID = new ArrayList();
	// �������������
	private ArrayList m_alFreeItemNameIn = new ArrayList();
	// �������������
	private ArrayList m_alFreeItemReturnName = new ArrayList();
	// ������ֵID
	private ArrayList m_alFreeItemValue = new ArrayList();
	// ������ֵID
	private ArrayList m_alFreeItemValueIn = new ArrayList();
	// ������ֵID
	private ArrayList m_alFreeItemReturnValue = new ArrayList();
	// ������ֵ����
	private ArrayList m_alFreeItemReturnValueName = new ArrayList();

	private boolean JustClicked = false; // ����Ƿ�ոհ���Click��
	// private boolean m_bOpenFlag = false;//���������Ƿ����,��û�д������ʱ�������ǲ����ֵ�
	private Hashtable m_hHashtable = new Hashtable();
	// private final ArrayList m_alInherentConsult= new ArrayList();//�̶�����
	private Hashtable m_hInherentConsult = new Hashtable();

	/**
	 * FreeItemRefPane ������ע�⡣
	 */
	public FreeItemRefPane() {
		super();
		setReturnCode(true);
		this.setEditable(false);
		this.setIsBatchData(false);
		// setInherentConsult();
	}

	/**
	 * FreeItemRefPane ������ע�⡣
	 * 
	 * @param p0
	 *            java.awt.LayoutManager
	 */
	public FreeItemRefPane(java.awt.LayoutManager p0) {
		super(p0);
		this.setIsBatchData(false);
	}

	/**
	 * FreeItemRefPane ������ע�⡣
	 * 
	 * @param p0
	 *            java.awt.LayoutManager
	 * @param p1
	 *            boolean
	 */
	public FreeItemRefPane(java.awt.LayoutManager p0, boolean p1) {
		super(p0, p1);
		this.setIsBatchData(false);
	}

	/**
	 * FreeItemRefPane ������ע�⡣
	 * 
	 * @param p0
	 *            boolean
	 */
	public FreeItemRefPane(boolean p0) {
		super(p0);
		this.setIsBatchData(false);
	}

	/**
	 * �����ߣ������� ���ܣ�wnj:�Ƿ������������� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 ���� 7:36)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String[][]
	 */
	protected ArrayList checkFreeItemNameOID() {
		if ((null == m_alFreeItemID) || (m_alFreeItemID.size() == 0)) {
			return null;
		}
		boolean bAllNull = true;
		for (int i = 0; i < m_alFreeItemID.size(); i++) {
			if ((null != m_alFreeItemID.get(i))
					&& (m_alFreeItemID.get(i).toString().trim().length() != 0)) {
				bAllNull = false;
				break;
			}
		}
		if (bAllNull) {
			return null;
		}
		return m_alFreeItemID;
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-17 ���� 10:34) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return boolean
	 */
	protected boolean checkOpenFlag() {

		if ((null == m_sCode) || (null == m_sInventoryName)) {
			Logger.info("û�д�����������");
			return false;
		}
		if ((m_sCode.equals("")) || (m_sInventoryName.equals(""))
				|| (null == checkFreeItemNameOID())) {
			Logger.info("û�д����������ƻ�����������");
			return false;
		}
		return true;
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-17 ���� 10:34) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return boolean
	 */
	public void clearReturnAllValue() {
		m_alFreeItemReturnName = null;
		m_alFreeItemNameIn = null;
		m_alFreeItemReturnValue = null;
		m_alFreeItemReturnValueName = null;
	}

	/**
	 * �����ߣ������� ���ܣ��� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 ���� 6:52) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return
	 */
	protected void fromOIDtoName() {
		// ��m_alFreeItemNameOID�õ�m_alFreeItemName
		// if (null == m_alFreeItemID)
		// return;
		// //m_alFreeItemReturnName= null;
		// //m_alFreeItemReturnName= new ArrayList();
		// //m_alFreeItemReturnValueName= null;
		// //m_alFreeItemReturnValueName= new ArrayList();
		// m_alFreeItemNameIn= null;
		// m_alFreeItemNameIn= new ArrayList();
		//
		// UIRefPane urpUIRefPane= null;
		//
		// for (int j= 0; j < m_alFreeItemID.size(); j++) {
		// if ((null == m_alFreeItemID.get(j))
		// || (m_alFreeItemID.get(j).toString().trim().length() == 0)) {
		// continue;
		// }
		// String sOID= m_alFreeItemID.get(j).toString().trim();
		// //�ж��Ƿ��ǹ��в���
		// String sInherentConsultName= null;
		// if (m_hInherentConsult.containsKey(sOID)) {
		// sInherentConsultName= (String) m_hInherentConsult.get(sOID);
		// } else {
		// //�Զ�����壺
		// //bd_defdef����������Դ��pk_defdef�Զ�����������pk_bdinfo������������
		// try
		// {
		// Object obj= HYPubBO_Client.findColValue("bd_defdef", "pk_defdef",
		// " pk_bdinfo = '"+sOID+"' ");
		// if (obj != null
		// && ((Object[]) obj)[0] != null
		// && ((Object[]) obj)[0].toString().trim().length() != 0) {
		// //refnodename���սڵ��� ���硰��Ŀ��������
		// obj= HYPubBO_Client.findColValue("bd_bdinfo", "pk_bdinfo",
		// "refnodename= '"+ ((Object[]) obj)[0].toString()+"' ");
		// if (obj != null
		// && ((Object[]) obj)[0] != null
		// && ((Object[]) obj)[0].toString().trim().length() != 0) {
		// //BD������
		// sInherentConsultName= ((Object[]) obj)[0].toString().trim();
		// m_hInherentConsult.put(sOID, sInherentConsultName);
		// }
		// }
		// }
		// catch (BusinessException e)
		// {
		// Logger.info(e.getMessage());
		// }
		// }
		//
		//
		//
		// //�����BD����
		// if (null != sInherentConsultName) {
		// ArrayList alTemp= new ArrayList();
		// alTemp.add(new Integer(ConsultType.InherentConsult));
		// alTemp.add(sInherentConsultName);
		// //�����Զ�����յ���ʾ���ƣ��Ա���IFreeItemParamSeqDef��ƥ�� ������ 2009-05-06
		// alTemp.add(null);
		// alTemp.add(null);
		// alTemp.add(null);
		// alTemp.add(m_alFreeItemReturnName.get(j));
		// m_alFreeItemNameIn.add(alTemp);
		// //m_alFreeItemReturnName.add(sInherentConsultName);
		//			
		//			
		// // m_alFreeItemReturnName.add(j, sInherentConsultName);
		//
		//
		// if (null != m_alFreeItemValue.get(j)) {
		// urpUIRefPane= null;
		// urpUIRefPane= new UIRefPane();
		// urpUIRefPane.setRefNodeName(alTemp.get(1).toString().trim());
		// urpUIRefPane.setPK(m_alFreeItemValue.get(j).toString());
		// m_alFreeItemReturnValueName.add(urpUIRefPane.getRefName());
		// } else {
		// m_alFreeItemReturnValueName.add(null);
		//
		//
		// }
		//
		// continue;
		// }
		// //�������BD���գ��Ǿ����Զ������
		// //���Զ��������Ƿ���в���
		// //����Hashtable�в���
		// if (m_hHashtable.containsKey(sOID)) {
		// ArrayList alTemp= new ArrayList();
		// alTemp= (ArrayList) m_hHashtable.get(sOID);
		// m_alFreeItemNameIn.add(alTemp);
		// continue;
		// }
		//
		// //Hashtable��û�У��ڿ��в���
		// try {
		// //zpm ,ͨ���ӿ�ȥ��ѯ�Զ���ֵ
		//			
		// IMedDataOperate io =
		// (IMedDataOperate)NCLocator.getInstance().lookup(IMedDataOperate.class.getName());
		// DefdefVO dvoDefdefVO= io.findByPrimaryKey(sOID);
		// if ((null == dvoDefdefVO) || (null == dvoDefdefVO.getParentVO())) {
		// //ʲô��û��
		// Logger.info("Error:User def ref is not found."+sOID);
		// continue;
		// }
		//
		// if (dvoDefdefVO.getChildrenVO()==null ||
		// dvoDefdefVO.getChildrenVO().length == 0) {
		// Logger.info("ERROR:User def ref DATA are not found."+sOID);
		// //����û�ж���bd_defdoclist�ɹ�ѡ������ݡ��綨����רҵ���գ�����bd_defdoclist��û���ҵ���ص����ݡ�
		// ArrayList alTemp= new ArrayList();
		// alTemp.add(new Integer(ConsultType.NoConsult));
		// alTemp.add(((DefdefHeaderVO)
		// (dvoDefdefVO.getParentVO())).getDefname().toString().trim());
		// //�������������ʹ���
		// alTemp.add((DefdefHeaderVO) (dvoDefdefVO.getParentVO()));
		// m_alFreeItemNameIn.add(alTemp);
		//
		// //��Hashtable�м���
		// m_hHashtable.put(sOID, alTemp);
		// continue;
		// }
		// Logger.info("Hint:User def ref data are found."+sOID);
		//
		// //�ҵ��ˣ��� �Զ��������,˳������
		// // IFreeItemParamSeqDef {
		// // public int RET_TYPE=0; //���� domain is ConsultType
		// // public int DEF_REF_NAME=1; //��defname
		// // public int DEF_REF_PK=2; //PK_defdef
		// // public int DEF_LEN=3; //Lengthnum
		// // public int DEF_REF_PK_LIST=4;//pk_defdoclist
		// // }
		// ArrayList alTemp= new ArrayList();
		// alTemp.add(new Integer(ConsultType.UserDefConsult));
		// alTemp.add(((DefdefHeaderVO)
		// (dvoDefdefVO.getParentVO())).getDefname().toString().trim());
		// alTemp.add(((DefdefHeaderVO)
		// (dvoDefdefVO.getParentVO())).getPrimaryKey().toString().trim());
		// //�������ݳ���
		// alTemp.add(((DefdefHeaderVO)
		// (dvoDefdefVO.getParentVO())).getLengthnum());
		// //2005-01-17����������һ�У�
		// ���˾���ʹ���Զ����������MODEL������Զ���������޹�˾�������⡣getPk_defdoclist
		// alTemp.add(((DefdefHeaderVO)
		// (dvoDefdefVO.getParentVO())).getPk_defdoclist());
		// m_alFreeItemNameIn.add(alTemp);
		// //��Hashtable�м���
		// m_hHashtable.put(sOID, alTemp);
		//
		// } catch (Exception e) {
		// Logger.info("����ͨѶʧ�ܣ�");
		// //handleException(e);
		// }
		//
		// }
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 ���� 7:36) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String[][]
	 */
	protected String getDlgReturnFreeItem() {
		m_alFreeItemValue = null;
		m_alFreeItemValue = new ArrayList();
		m_alFreeItemReturnName = null;
		m_alFreeItemReturnName = new ArrayList();
		m_alFreeItemReturnValueName = null;
		m_alFreeItemReturnValueName = new ArrayList();

		String[][] dlgReturnFreeItem = getFreeItemDlg().getReturnFreeItem();
		int j = 0;
		for (int i = 0; i < m_alFreeItemID.size(); i++) {
			if ((null == m_alFreeItemID.get(i))
					|| (m_alFreeItemID.get(i).toString().trim().length() == 0)) {
				m_alFreeItemReturnName.add(null);
				m_alFreeItemValue.add(null);
				m_alFreeItemReturnValueName.add(null);
			} else {
				if (j >= dlgReturnFreeItem.length) {
					m_alFreeItemReturnName.add(null);
					m_alFreeItemValue.add(null);
					m_alFreeItemReturnValueName.add(null);
				} else {
					m_alFreeItemReturnName.add(dlgReturnFreeItem[j][0]);
					m_alFreeItemValue.add(dlgReturnFreeItem[j][2]);
					m_alFreeItemReturnValueName.add(dlgReturnFreeItem[j][2]);
					j++;
				}
			}
		}

		m_alFreeItemReturnValue = m_alFreeItemValue;

		// fromOIDtoName();
		if (null == m_alFreeItemReturnValueName)
			return "";

		String returnString = "";
		int k = 0;
		for (int i = 0; i < m_alFreeItemReturnValueName.size(); i++) {
			if ((dlgReturnFreeItem[k][0] == null)
					|| (dlgReturnFreeItem[k][1] == null)
					|| (dlgReturnFreeItem[k][2] == null)
					|| (null == m_alFreeItemReturnValueName.get(i))) {
				continue;
			}
			returnString = returnString + "[" + dlgReturnFreeItem[k][0] + ":"
					+ m_alFreeItemReturnValueName.get(i).toString().trim()
					+ "]";
			k++;
			if (k > dlgReturnFreeItem.length - 1)
				break;
		}

		return returnString;
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 ���� 6:52) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return nc.ui.ic.pub.freeitem.FreeItemDlg
	 */
	protected FreeItemDlg getFreeItemDlg() {
		if (m_dlgFreeItemDlg == null) {
			m_dlgFreeItemDlg = new FreeItemDlg(this.getParent());
			m_dlgFreeItemDlg.setLocationRelativeTo(this);
			m_dlgFreeItemDlg.setInventoryName(m_sCode, m_sInventoryName,
					m_sSpec, m_sType);

			// ��m_alFreeItemNameOID�õ�m_alFreeItemName��m_alFreeItemNameIn
			// fromOIDtoName();

			// ѹ������ֵ
			// packFreeItemName();
			// FreeVO freeItemVOs=getFreeVO();

			// m_dlgFreeItemDlg.setFreeItemName(itemReturnNames);
			m_dlgFreeItemDlg.setFreeItemName(m_alFreeItemNameIn);

			// ѹ������ֵ
			packFreeItemValue();
			m_dlgFreeItemDlg.setFreeItemValue(m_alFreeItemValueIn);

			m_dlgFreeItemDlg.addKeyListener(new IvjKeyListener());
			m_dlgFreeItemDlg.setVisible(true);
		}
		return m_dlgFreeItemDlg;
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 ���� 7:36) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String[][]
	 */
	public ArrayList getFreeItemName() {
		if ((null == m_alFreeItemReturnName)
				|| (m_alFreeItemReturnName.size() == 0)) {
			return null;
		}
		boolean bAllNull = true;
		for (int i = 0; i < m_alFreeItemReturnName.size(); i++) {
			if (null != m_alFreeItemReturnName.get(i)) {
				bAllNull = false;
				break;
			}
		}
		if (bAllNull) {
			return null;
		}
		return m_alFreeItemReturnName;
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 ���� 7:36) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String[][]
	 */
	public ArrayList getFreeItemValue() {
		if ((null == m_alFreeItemReturnValueName)
				|| (m_alFreeItemReturnValueName.size() == 0)) {
			return null;
		}
		boolean bAllNull = true;
		for (int i = 0; i < m_alFreeItemReturnValueName.size(); i++) {
			if (null != m_alFreeItemReturnValueName.get(i)) {
				bAllNull = false;
				break;
			}
		}
		if (bAllNull) {
			return null;
		}
		return m_alFreeItemReturnValueName;

	}

	public ArrayList getFreeItemValueAll() {
		return m_alFreeItemReturnValue;
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 ���� 7:36) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String[][]
	 */
	public ArrayList getFreeItemValueID() {
		if ((null == m_alFreeItemReturnValue)
				|| (m_alFreeItemReturnValue.size() == 0)) {
			return null;
		}
		boolean bAllNull = true;
		for (int i = 0; i < m_alFreeItemReturnValue.size(); i++) {
			if (null != m_alFreeItemReturnValue.get(i)) {
				bAllNull = false;
				break;
			}
		}
		if (bAllNull) {
			return null;
		}
		return m_alFreeItemReturnValue;
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-6-19 ���� 3:27) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return nc.vo.ic.pub.bill.FreeVO
	 */
	public FreeVO getFreeVO() {
		FreeVO fvoFVO = new FreeVO();
		if ((m_alFreeItemID != null) && (m_alFreeItemID.size() == 10)
				&& (m_alFreeItemReturnName != null)
				&& (m_alFreeItemReturnName.size() >= 10)
				&& (m_alFreeItemReturnValue != null)
				&& (m_alFreeItemReturnValue.size() >= 10)) {
			for (int i = 1; i <= 10; i++) {
				fvoFVO.setAttributeValue(
						"vfreeid" + Integer.toString(i).trim(), m_alFreeItemID
								.get(i - 1));
				fvoFVO.setAttributeValue("vfreename"
						+ Integer.toString(i).trim(), m_alFreeItemReturnName
						.get(i - 1));
				fvoFVO.setAttributeValue("vfree" + Integer.toString(i).trim(),
						m_alFreeItemReturnValue.get(i - 1));
				// fvoFVO.setAttributeValue(
				// "vfreevalue" + Integer.toString(i).trim(),
				// m_alFreeItemReturnValueName.get(i - 1));
			}
		} else {
			for (int i = 1; i <= 10; i++) {
				fvoFVO.setAttributeValue(
						"vfreeid" + Integer.toString(i).trim(), null);
				fvoFVO.setAttributeValue("vfreename"
						+ Integer.toString(i).trim(), null);
				fvoFVO.setAttributeValue("vfree" + Integer.toString(i).trim(),
						null);
				// fvoFVO.setAttributeValue("vfreevalue" +
				// Integer.toString(i).trim(), null);
			}
		}
		fvoFVO.setAttributeValue("vfree0", this.getText() == null ? null : this
				.getText().trim());
		return fvoFVO;
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-23 ���� 11:34) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return boolean
	 */
	protected boolean isJustClicked() {
		return JustClicked;
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 ���� 6:59) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public void onButtonClicked() {
		int lengthOfInput = 0, m_iMinusOfString = 0;
		String returnString = "";
		String tempString = "";
		m_dlgFreeItemDlg = null;
		if (checkOpenFlag()) {
			if (getFreeItemDlg().getResult() == UIDialog.ID_OK) {

				// �������
				returnString = getDlgReturnFreeItem();
				Logger.info(returnString);
				if (returnString.length() > (this.getMaxLength() - m_iMinusOfString)) {
					lengthOfInput = this.getMaxLength() - m_iMinusOfString;
				} else {
					lengthOfInput = returnString.length();
				}
				;
				tempString = returnString.substring(0, lengthOfInput);

				boolean bIsEditable = isEditable();
				if (!bIsEditable) {
					setEditable(true);
				}
				setText(tempString);
				setEditable(bIsEditable);

				setJustClicked(true);
			} else {
				setJustClicked(false);
			}
		}
		getUITextField().setRequestFocusEnabled(true);
		getUITextField().grabFocus();
		return;

	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 ���� 6:52) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return
	 */
	protected void packFreeItemName() {
		// ѹ��FreeItemValue
		m_alFreeItemNameIn = null;
		if (null == m_alFreeItemReturnName)
			return;

		m_alFreeItemNameIn = new ArrayList();
		for (int j = 0; j < m_alFreeItemReturnName.size(); j++) {
			if (null != m_alFreeItemReturnName.get(j)) {
				m_alFreeItemNameIn.add(m_alFreeItemReturnName.get(j));
			}
		}
	}

	/**
	 * �����ߣ������� ���ܣ�ɾ��m_alFreeItemValueIn�����õ�element ������ ���أ� ���⣺ ���ڣ�(2001-5-9 ����
	 * 6:52) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return
	 */
	protected void packFreeItemValue() {
		// ѹ��FreeItemValue
		m_alFreeItemValueIn = null;
		if (null == m_alFreeItemValue)
			return;

		m_alFreeItemValueIn = new ArrayList();
		for (int j = 0; j < m_alFreeItemValue.size(); j++) {
			if (null != m_alFreeItemValue.get(j)) {
				m_alFreeItemValueIn.add(m_alFreeItemValue.get(j));
			}
		}
	}

	/**
	 * �����ߣ�������
	 * 
	 * ���ܣ����ñ�Ҫ�Ĳ���
	 * 
	 * ������������룬������ƣ������񣬴���ͺţ�10�����������ƣ�10��������ֵ ���أ� ���⣺ ���ڣ�(2001-5-10 ���� 11:07)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param newM_sFreeItemName
	 *            java.lang.String[]
	 */
	protected void setFreeItemParam(ArrayList alParam) {
		// ����Ϊ�ջ��볤�Ȳ���
		if ((alParam == null) || (alParam.size() != 24)) {
			m_sCode = "";
			m_sInventoryName = "";
			m_sSpec = "";
			m_sType = "";
			m_alFreeItemValue = null;
			// ���������
			clearReturnAllValue();
			// ����ʾ���
			setText("");
		} else {
			// �������
			m_sCode = null;
			m_sInventoryName = null;
			m_sSpec = null;
			m_sType = null;
			if (null != alParam.get(0)) {
				m_sCode = alParam.get(0).toString().trim();
			}
			if (null != alParam.get(1)) {
				m_sInventoryName = alParam.get(1).toString().trim();
			}
			if (null != alParam.get(2)) {
				m_sSpec = alParam.get(2).toString().trim();
			}
			if (null != alParam.get(3)) {
				m_sType = alParam.get(3).toString().trim();
			}
			m_alFreeItemID = null;
			m_alFreeItemValue = null;
			m_alFreeItemID = new ArrayList();
			m_alFreeItemValue = new ArrayList();
			for (int i = 4; i < 14; i++) {
				if (null == alParam.get(i)) {
					m_alFreeItemID.add(null);
				} else {
					m_alFreeItemID.add(alParam.get(i).toString().trim());
				}
				if (null == alParam.get(i + 10)) {
					m_alFreeItemValue.add(null);
				} else {
					m_alFreeItemValue
							.add(alParam.get(i + 10).toString().trim());
				}
			}
			m_alFreeItemID = checkFreeItemNameOID();
			m_alFreeItemReturnName = null;
			m_alFreeItemNameIn = null;
			m_alFreeItemReturnValue = null;
			m_alFreeItemReturnValueName = null;
			fromOIDtoName();
		}
	}

	/**
	 * �����ߣ������� ���ܣ� ������������룬������ƣ������񣬴���ͺţ�10�����������ƣ�10��������ֵ ���أ� ���⣺ ���ڣ�(2001-5-10
	 * ���� 11:07) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param newM_sFreeItemName
	 *            java.lang.String[]
	 */
	public void setFreeItemParam(VInvVO ivoVO) {
		m_sCode = "";
		m_sInventoryName = "";
		m_sSpec = "";
		m_sType = "";
		// ����Ϊ�ջ��볤�Ȳ���
		if ((ivoVO == null)
				|| (null == ivoVO.getCinventoryid()
						|| (ivoVO.getCinventoryid().toString().trim().length() == 0) || (null == ivoVO
						.getIsFreeItemMgt() || (ivoVO.getIsFreeItemMgt()
						.toString().trim() == Integer.toString(0).trim())))) {
			m_alFreeItemValue = null;
			// ���������
			clearReturnAllValue();
			// ����ʾ���
			setText("");
		} else {
			// �������
			if (null != ivoVO.getCinventorycode()) {
				m_sCode = ivoVO.getCinventorycode().trim();
			}
			if (null != ivoVO.getInvname()) {
				m_sInventoryName = ivoVO.getInvname().trim();
			}
			if (null != ivoVO.getInvspec()) {
				m_sSpec = ivoVO.getInvspec().trim();
			}
			if (null != ivoVO.getInvtype()) {
				m_sType = ivoVO.getInvtype().trim();
			}
			m_alFreeItemID = null;
			m_alFreeItemValue = null;
			m_alFreeItemNameIn = null;
			m_alFreeItemReturnName = null;
			m_alFreeItemReturnName = new ArrayList();
			m_alFreeItemNameIn = new ArrayList();
			m_alFreeItemID = new ArrayList();
			m_alFreeItemValue = new ArrayList();
			int iIDCount = 0, iNameCount = 0;
			//
			if (ivoVO.getFreevo() != null) {
				for (int i = 1; i <= 10; i++) {
					if (null == ivoVO.getFreevo().getAttributeValue(
							"vfreeid" + Integer.toString(i).trim())) {
						m_alFreeItemID.add(null);
					} else {
						m_alFreeItemID.add(ivoVO.getFreevo().getAttributeValue(
								"vfreeid" + Integer.toString(i).trim())
								.toString().trim());
						iIDCount++;
					}
					if (null == ivoVO.getFreevo().getAttributeValue(
							"vfreename" + Integer.toString(i).trim())) {
						m_alFreeItemReturnName.add(null);
					} else {
						m_alFreeItemReturnName.add(ivoVO.getFreevo()
								.getAttributeValue(
										"vfreename"
												+ Integer.toString(i).trim())
								.toString().trim());
						iNameCount++;
					}
					if (null == ivoVO.getFreevo().getAttributeValue(
							"vfree" + Integer.toString(i).trim())) {
						m_alFreeItemValue.add(null);
					} else {
						m_alFreeItemValue.add(ivoVO.getFreevo()
								.getAttributeValue(
										"vfree" + Integer.toString(i).trim())
								.toString().trim());
					}
				}

				// ������ֵ����
				m_alFreeItemValueIn = m_alFreeItemValue;
				m_alFreeItemReturnValue = m_alFreeItemValue;
				m_alFreeItemReturnValueName = m_alFreeItemValue;

				if (null == ivoVO.getFreevo().getAttributeValue("vfree0")
						|| ivoVO.getFreevo().getAttributeValue("vfree0")
								.toString().trim().length() == 0) {
					this.setText(null);
				} else {
					this.setText(ivoVO.getFreevo().getAttributeValue("vfree0")
							.toString().trim());
				}

				// ��������ƥ��
				if (iNameCount != iIDCount) {
					m_sCode = "";
					m_sInventoryName = "";
					m_sSpec = "";
					m_sType = "";
					// ���������
					clearReturnAllValue();
					// ����ʾ���
					setText("");

					nc.ui.pub.beans.MessageDialog.showErrorDlg(this, "����",
							"���������ƥ�䣬�����������ж�������Ķ��壬�Ƿ�����Զ������ѱ�ɾ��");
				}

				fromOIDtoName();
			}
		}
	}

	/**
	 * �����ߣ������� ���ܣ����ù��еģ�BD�ṩ�ģ����ա� ������ ���أ� ���⣺ ���ڣ�(2001-5-17 ���� 10:34)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return
	 */
	protected void setInherentConsult() {
		/*
		 * ��Ա���� ��Ա����HR ���ŵ��� ��˾Ŀ¼(����)S ��˾Ŀ¼ ��˾Ŀ¼(����) ���̻������� ���̵��� �ͻ����� ��Ӧ�̵���
		 * ���̸������� �ͻ��������� ��Ӧ�̸������� ���̵����������� �ͻ������������� ��Ӧ�̵����������� ����������� ������� ���ϵ���
		 * ��ƿ�Ŀ ��Ա��� ������� ƾ֤��� �ո���Э�� ���㷽ʽ �������� ��������1 ��������2 ��������3 �ֿ⵵�� ���˷�ʽ �շ����
		 * �������� ����ժҪ �����Զ����� ��֧��Ŀ ������֧��Ŀ �շ���֧��Ŀ �������� ˰Ŀ˰�� ��Ŀ���� ��Ŀ���� ��Ŀ������
		 * ��Ŀ��������� ��λ���� ��ҵ��� ���̷�����ַ �Զ������ ���׼� ����ڼ� ҵ������ �����֯ Ȩ�޲���Ա ����Ա �ɹ���֯
		 * �������� �������� �ʻ� ������Ŀ ֧����Ŀ ���̵���(����) Ȩ�޹�˾Ŀ¼ Ȩ�޹�˾Ŀ¼(����) ������֯ �ֽ�������Ŀ
		 * �Զ�������б� ��Ŀ���� Ʊ������ Ʊ������1 Ʊ������2 Ʊ������3 Ʊ������4 Ʊ������5 �˻����� ����������Դ�б�
		 */
		/*
		 * String[] sTemp; sTemp=new String[]{"0","��Ա����"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"1","��Ա����HR"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"2","���ŵ���"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"3","��˾Ŀ¼(����)S"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"4","��˾Ŀ¼"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"5","��˾Ŀ¼(����)"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"6","���̻�������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"7","���̵���"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"8","�ͻ�����"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"9","��Ӧ�̵���"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"10","���̸�������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"11","�ͻ���������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"12","��Ӧ�̸�������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"13","���̵�����������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"14","�ͻ�������������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"15","��Ӧ�̵�����������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"16","�����������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"17","�������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"18","���ϵ���"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"19","��ƿ�Ŀ"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"20","��Ա���"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"21","�������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"22","ƾ֤���"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"23","�ո���Э��"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"24","���㷽ʽ"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"25","��������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"26","��������1"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"27","��������2"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"28","��������3"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"29","�ֿ⵵��"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"30","���˷�ʽ"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"31","�շ����"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"32","��������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"33","����ժҪ"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"34","�����Զ�����"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"35","��֧��Ŀ"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"36","������֧��Ŀ"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"37","�շ���֧��Ŀ"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"38","��������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"39","˰Ŀ˰��"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"40","��Ŀ����"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"41","��Ŀ����"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"42","��Ŀ������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"43","��Ŀ���������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"44","��λ����"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"45","��ҵ���"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"46","���̷�����ַ"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"47","�Զ������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"48","���׼�"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"49","����ڼ�"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"50","ҵ������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"51","�����֯"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"52","Ȩ�޲���Ա"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"53","����Ա"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"54","�ɹ���֯"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"55","��������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"56","��������"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"57","�ʻ�"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"58","������Ŀ"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"59","֧����Ŀ"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"60","���̵���(����)"};
		 * m_alInherentConsult.add(sTemp); sTemp=new String[]{"61","Ȩ�޹�˾Ŀ¼"};
		 * m_alInherentConsult.add(sTemp); sTemp=new
		 * String[]{"62","Ȩ�޹�˾Ŀ¼(����)"}; m_alInherentConsult.add(sTemp);
		 * sTemp=new String[]{"63","������֯"}; m_alInherentConsult.add(sTemp);
		 * sTemp=new String[]{"64","�ֽ�������Ŀ"}; m_alInherentConsult.add(sTemp);
		 * sTemp=new String[]{"65","�Զ�������б�"}; m_alInherentConsult.add(sTemp);
		 * sTemp=new String[]{"66","��Ŀ����"}; m_alInherentConsult.add(sTemp);
		 * sTemp=new String[]{"67","Ʊ������"}; m_alInherentConsult.add(sTemp);
		 * sTemp=new String[]{"68","Ʊ������1"}; m_alInherentConsult.add(sTemp);
		 * sTemp=new String[]{"69","Ʊ������2"}; m_alInherentConsult.add(sTemp);
		 * sTemp=new String[]{"70","Ʊ������3"}; m_alInherentConsult.add(sTemp);
		 * sTemp=new String[]{"71","Ʊ������4"}; m_alInherentConsult.add(sTemp);
		 * sTemp=new String[]{"72","Ʊ������5"}; m_alInherentConsult.add(sTemp);
		 * sTemp=new String[]{"73","�˻�����"}; m_alInherentConsult.add(sTemp);
		 * sTemp=new String[]{"74","����������Դ�б�"}; m_alInherentConsult.add(sTemp);
		 */
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-23 ���� 11:34) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param newJustClicked
	 *            boolean
	 */
	protected void setJustClicked(boolean newJustClicked) {
		JustClicked = newJustClicked;
	}

	class IvjKeyListener extends java.awt.event.KeyAdapter {
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
				FreeItemRefPane.this.getFreeItemDlg().closeCancel();
			}
		}
	}
}
package nc.vo.zmpub.pub.report2;
import java.util.Enumeration;
import java.util.Hashtable;
/**
 * �������ڣ�(2004-2-6 9:08:00)
 * @author��������
 */
public class LevelByOrgContext {
	//�������->����VO.
	private Hashtable m_hObjData = new Hashtable();
	//������롣
	private String[][] m_arsObjcode = null;
	//�������->��CirVO�У��˶���ID��Ӧ���ֶΡ�
	private Hashtable m_hObjIDFld = new Hashtable();
	//�������->��CirVO��,�˶�������Ӧ���ֶ�.
	private Hashtable m_hObjCodeFld = new Hashtable();
	private Hashtable m_hObjTreeModel = new Hashtable();
	//������룭>�������ơ�
	private Hashtable m_hObjcodeObjname = new Hashtable();
	//�Ƿ�����ֻ����ĩ����
	private boolean m_isLeaf = true;
	//�Ƿ���ݱ���ƥ������.
	private boolean isUseCodeMatchData = false;
	//Ϊ���Ƿ���ʾ��
	private boolean m_isShowZero = false;
	//�Ƿ�ֻ����ʾС�ƺϼơ�
	private boolean m_isShowSubtotal = false;
	//С�ƺϼ��Ƿ�ӱ��롣
	private boolean m_isSubtotalAddCode = true;
	//�Ƿ���ʾ��ϸ.
	private boolean m_isShowDetail = true;
	//��ʾ�ļ���.
	private String[] m_ShowLevels = null;
	//�ϲ�����ģ
	private javax.swing.tree.DefaultTreeModel m_tmCombined = null;
	//��һ����ģ
	private javax.swing.tree.DefaultTreeModel m_tmFirstLevel = null;
	private ITreeModelFactory m_tmf = null;
	private IAdjustNodeTool m_adjustNodeTool = null;
/**
 * LevelByOrgInfoVO ������ע�⡣
 */
public LevelByOrgContext() {
	super();
}
	public IAdjustNodeTool getAdjustNodeTool() {
		return m_adjustNodeTool;
	}
public String getCodeFldByObjcode(String strObjcode)
{
	return m_hObjCodeFld.get(strObjcode).toString();
}
public javax.swing.tree.DefaultTreeModel getCombinedTM()
{
	if (getLevelNumber() == 1)
		return getModelByObjcode(getObjcodeByLevel(0)[0]);
	ExTreeNode root =
		(ExTreeNode) getModelByObjcode(getObjcodeByLevel(0)[0]).getRoot();
	for (int i = 1; i < getLevelNumber(); i++)
	{
		for (int j = 0; j < getObjcodeByLevel(i).length; j++)
		{
			Hashtable hnode = null;
			String strParentCode =
				((ExTreeNode) getModelByObjcode(getObjcodeByLevel(i)[j]).getRoot())
					.getParentCode();
			hnode = root.getAllNodeByObjtypecode(null, strParentCode);
			//�õ���һ���������и��ڵ㡣
			Enumeration en =
				((ExTreeNode) getModelByObjcode(getObjcodeByLevel(i)[j]).getRoot()).children();
			//Ҷ�Ӷ���һ�����ĸ����кϲ���
			while (en.hasMoreElements())
			{
				ExTreeNode downtop = (ExTreeNode) en.nextElement();
				ExTreeNode supernode = (ExTreeNode) hnode.get(downtop.getSuperobjid());
				if (supernode != null && supernode.isSelected())
				{
					//���jdk��һ�����⡣
					ExTreeNode tmpnode = (ExTreeNode) downtop.clone();
					supernode.add(tmpnode);
				}
			}
		}
	}
	return getModelByObjcode(getObjcodeByLevel(0)[0]);
}
	public nc.vo.pub.CircularlyAccessibleValueObject getDataByObjcode(
		String strObjcode) {
		return (nc.vo.pub.CircularlyAccessibleValueObject) m_hObjData.get(strObjcode);
	}
	public javax.swing.tree.DefaultTreeModel getFirstLevelModel() {
		return m_tmFirstLevel;
	}
	public String getIDFldByObjcode(String strObjcode) {
		return m_hObjIDFld.get(strObjcode).toString();
	}
	public int getLevelNumber() {
		return m_arsObjcode.length;
	}
	public javax.swing.tree.DefaultTreeModel getModelByObjcode(String strObjcode) {
		return (javax.swing.tree.DefaultTreeModel) m_hObjTreeModel.get(strObjcode);
	}
	public String[] getObjcodeByLevel(int iLevel) {
		return m_arsObjcode[iLevel];
	}
	public String getObjnameByCode(String strObjcode) {
		return m_hObjcodeObjname.get(strObjcode).toString();
	}
	//�õ��������ڵؼ��Ρ�
	public int getObjPos(String strObjcode) {
		for (int i = 0; i < m_arsObjcode.length; i++) {
			for (int j = 0; j < m_arsObjcode[i].length; j++) {
				if (m_arsObjcode[i][j].equals(strObjcode))
					return i;
			}
		}
		return -1;
	}
	public String[] getShowLevels()
{
		return m_ShowLevels;
	}
	public ITreeModelFactory getTMFactory() {
		return m_tmf;
	}
private void initTMByData(
	String strObjcode,
	nc.vo.pub.CircularlyAccessibleValueObject[] vos)
{
	if (vos == null || vos.length == 0)
		return;
	Hashtable hnode = null;
	//��Ϊ�����ڵ㶼����������.
	if (isUsecodeMatchData())
		hnode =
			(
				(ExTreeNode) getModelByObjcode(strObjcode)
					.getRoot())
					.getAllNodeByObjtypecodeObjCodeAsKey(
				null,
				strObjcode);
	else
		hnode =
			((ExTreeNode) getModelByObjcode(strObjcode).getRoot()).getAllNodeByObjtypecode(
				null,
				strObjcode);

	//��һ������ȫѡ��
	if (getObjPos(strObjcode) > 0)
	{
		Enumeration en = hnode.elements();
		while (en.hasMoreElements())
		{
			ExTreeNode subnode = (ExTreeNode) en.nextElement();
			subnode.setSelected(true);
		}
	}
	for (int j = 0; j < vos.length; j++)
	{
		Object key = null;
		if (isUsecodeMatchData())
			key = vos[j].getAttributeValue(getCodeFldByObjcode(strObjcode));
		else
			key = vos[j].getAttributeValue(getIDFldByObjcode(strObjcode));
		if (key == null)
			continue;
		if (hnode.get(key.toString().trim()) == null)
			continue;
		ExTreeNode thisnode = (ExTreeNode) hnode.get(key.toString().trim());
		if (thisnode != null && thisnode.isSelected())
		{
			thisnode.setNodeVO(vos[j]);
		}
	}

}
public void initTMByObjcode(String[][] objcode) {
	if (m_tmFirstLevel != null)
		m_hObjTreeModel.put(objcode[0][0], m_tmFirstLevel);
	for (int i = 1; i < objcode.length; i++) {
		for (int j = 0; j < objcode[i].length; j++) {
			m_hObjTreeModel.put(
				objcode[i][j],
				getTMFactory().getModel(objcode[i][j]));
		}
	}
}
	public boolean isAddCodeToSubtotal() {
		return m_isSubtotalAddCode;
	}
	private boolean isOnlyOnLeaf(){
		return m_isLeaf;
	}
	public boolean isShowDetail()
{
		return m_isShowDetail;
	}
public boolean isShowSubtotal() {
	return m_isShowSubtotal ;
}
	public boolean isUsecodeMatchData()
{
		return isUseCodeMatchData;
	}
	public void setAddCodeToSubtotal(boolean b) {
		m_isSubtotalAddCode = b;
	}
	public void setAdjustNodeTool(IAdjustNodeTool adjustTool) {
		m_adjustNodeTool = adjustTool;
	}
	public void setCodeFldByObjCode(String strObjcode, String strFld) {
		m_hObjCodeFld.put(strObjcode, strFld);
	}
public void setDataByObjcode(
	String strObjcode,
	nc.vo.pub.CircularlyAccessibleValueObject[] vos)
{
	//��ʼ��ģ��
	initTMByData(strObjcode, vos);
	//�������ݡ�
	if (vos != null && vos.length > 0)
		m_hObjData.put(strObjcode, vos);
}
	public void setFirstLevelTM(javax.swing.tree.DefaultTreeModel tm){
		m_tmFirstLevel=tm;
	}
	public void setIDFldByObjCode(String strObjcode, String strFld) {
		m_hObjIDFld.put(strObjcode, strFld);
	}
	public void setObjcode(String[][] objcode) {
		//�õ���ģ�͡�	
		m_arsObjcode = objcode;	
		initTMByObjcode(objcode);
	}
	public void setObjnameByCode(String strObjcode, String strObjname) {
		m_hObjcodeObjname.put(strObjcode, strObjname);
	}
	public void setOnlyOnLeaf(boolean b){
		m_isLeaf=b;
	}
	public void setShowDetails(boolean b)
{
		m_isShowDetail = b;
	}
	public void setShowLevlels(String[] levels)
{
		m_ShowLevels = levels;
	}
public void setShowSubtotal(boolean b) {
	m_isShowSubtotal=b ;
}
	public void setTMFactory(ITreeModelFactory tmf) {
		m_tmf = tmf;
	}
	public void setUsecodeMatchData(boolean b)
{
		isUseCodeMatchData = b;
	}
}

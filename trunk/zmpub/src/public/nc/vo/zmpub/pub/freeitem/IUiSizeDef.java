package nc.vo.zmpub.pub.freeitem;

/**
 * @author wnj
 * 
 *         TODO ���ܸ��������룩
 * 
 *         ����ؼ��ߴ磬���� ��׼���� FROM 3.1
 * 
 *         �޸ļ�¼��
 * 
 */
public interface IUiSizeDef {

	public int BTN_WIDTH = 85;// �������ֵİ�ť����
	public int BTN_WIDTH_3W = 100;// ���������ֵİ�ť����
	public int BTN_HEIGHT = 25;// ��ť�߶�
	public int BTN_RIGHT_MARGIN = 20;// ��ť�봰���ұ߽��PIXEL��
	public int BTN_INTERVAL = 8;// ��ť���PIXEL��
	public int BTN_INTERVAL_V = 10;// ��ť��ֱ���PIXEL��
	public int BTN_X_CANCEL = (BTN_WIDTH + BTN_RIGHT_MARGIN);// CANCEL��X����=DLG_WIDTH-BTN_X_CANCEL
	public int BTN_X_OK = (BTN_WIDTH + BTN_WIDTH + BTN_INTERVAL + BTN_RIGHT_MARGIN);// OK��X����=DLG_WIDTH-BTN_X_OK
	public int BTN_X_3 = (BTN_WIDTH + BTN_WIDTH + BTN_INTERVAL + BTN_WIDTH
			+ BTN_INTERVAL + BTN_RIGHT_MARGIN);// OK��X����=DLG_WIDTH-BTN_X_OK

}

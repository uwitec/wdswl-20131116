package nc.ui.zmpub.pub.report;

/**
 * ���ܣ����ݺϲ�������ݵ��������� Ҳ���Զ��κ�һ��VO���е��������� �������ڣ�(2004-1-17 15:28:57)
 * 
 * @author��������
 */
public interface IAdjustCombinVOTool {
	/* �Ժϲ�֮���VO���д��� */
	public void adjustCombinedVO(nc.vo.pub.CircularlyAccessibleValueObject vo,
			Object oMsg);

	/* �ԲμӺϲ���VO���д��� */
	public void adjustDetailVO(nc.vo.pub.CircularlyAccessibleValueObject vo);
}

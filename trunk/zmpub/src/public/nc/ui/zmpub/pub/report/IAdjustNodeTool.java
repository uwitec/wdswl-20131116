package nc.ui.zmpub.pub.report;

/**
 *����:��һ���ڵ���ʵĶԴ˽ڵ�����ݵ��������� ���ܵĵ���Ϊ��С�ƣ��ϼƣ��ڵ�VO.
 * 
 * �������ڣ�(2004-1-15 19:32:30)
 * 
 * @author��owind
 */
public interface IAdjustNodeTool {
	/* �����ּ�ͳ�Ƶ��ܼ�VO. */
	public void adjustAllTotalVO(nc.vo.pub.CircularlyAccessibleValueObject vo);

	/* ����ÿһ���ڵ�VO. */
	public void adjustNodeVO(ExTreeNode node);

	/* ����С��VO */
	public void adjustSubtotalVO(ExTreeNode node, String strObjcode);

	/* �����ϼ�VO. */
	public void adjustTotalVO(ExTreeNode node);
}

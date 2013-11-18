package nc.ui.zmpub.pub.freeitem;

import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UIRefPaneTextField;
import nc.ui.pub.bill.BillCellEditor;

/**
 * 
 * ������ ���� �����ϵ� celleditor�� ��Ϊÿ�еĲ������Ͷ����ܲ�ͬ����ȱʡ��BillCellEditorʱ¼��С���о������⣬
 * �������ﵥ���ṩһ��CellEditor.
 * 
 * ������������������������롣�ԼӸ��졣
 * 
 * 2006-06-28��wnj
 * 
 */
public class FreeItemCellEditor extends BillCellEditor {

	public FreeItemCellEditor(final UIRefPane refTextField) {
		super(refTextField);
		refTextField.setReturnCode(false);
		editorComponent = refTextField;
		final nc.ui.pub.beans.UITextField textField = refTextField
				.getUITextField();
		this.clickCountToStart = 2;
		delegate = new EditorDelegate() {

			public void setValue(Object value) {
				// �������޷��ж�OLD VALUE ��
				if (refTextField instanceof nc.ui.pub.bill.BillObjectRefPane)
					((nc.ui.pub.bill.BillObjectRefPane) refTextField)
							.setBillObject((nc.ui.pub.bill.IBillObject) value);
				else {
					refTextField.setValue((String) value);
				}
				// ��ý��㣬��ѡ�����е����ݣ��Ա���ɾ�����޸ġ�
				if (!refTextField.getUITextField().hasFocus())
					refTextField.getUITextField().selectAll();

			}

			public Object getCellEditorValue() {

				if (refTextField instanceof nc.ui.pub.bill.BillObjectRefPane)
					return ((nc.ui.pub.bill.BillObjectRefPane) refTextField)
							.getBillObject();
				if (refTextField.getRefNodeName() != null
						&& refTextField.getRefNodeName().trim().length() > 0)
					return (refTextField.getRefName() == null ? ""
							: refTextField.getRefName());
				else {
					String text = (textField == null ? "" : textField.getText()
							.trim());
					// ����С��λ��Ϊ0ʱֻ��һ�������������� ��.�� �������������⡣
					if ("TextDbl".equals(refTextField.getTextType())// 'TextDbl'����FreeItemDlg���õ����͡�
							&& refTextField.getNumPoint() >= 0) {
						if (text.equals(".") || text.equals("-"))
							text = "";
						else if (text.endsWith(".")) {
							text = text.substring(0, text.length() - 1);
						}

					}
					// δ�����,��С��λ�������㡣
					if (text.length() > 0 && refTextField.getNumPoint() > 0) {
						if (text.startsWith(".")) // ����¼���Ե㿪ʼ .8213 �������
							text = "0" + text;
						text = new nc.vo.pub.lang.UFDouble(text, refTextField
								.getNumPoint()).toString();
					}
					return text;
				}
			}

			public boolean stopCellEditing() {
				// this line must before super.stopCellEditing()
				((UIRefPaneTextField) refTextField.getUITextField())
						.stopEditing();
				boolean br = super.stopCellEditing();
				return br;
			}
		};
		textField.addActionListener(delegate);
		textField.addFocusListener(delegate);
	}

}
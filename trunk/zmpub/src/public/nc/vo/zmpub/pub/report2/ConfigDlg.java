package nc.vo.zmpub.pub.report2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UICheckBox;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UISeparator;
import nc.ui.pub.beans.UITextArea;

/**
 * �������ñ���Ի���
 * @author mlr
 *
 */
public class ConfigDlg extends nc.ui.pub.beans.UIDialog implements  ActionListener
{
	private static final long serialVersionUID = 7304166825055828526L;
	
	private boolean isok=false;

	private UICheckBox sumChooser = new UICheckBox();//�Ƿ���Ϊ��������
	private UILabel la=new UILabel("�Ƿ���Ϊ��������: ");
	
	private UITextArea detail =new UITextArea("��������");
	
   private UISeparator sp=new UISeparator();
	private UILabel da=new UILabel("��������: ");
	
	private UIPanel ui=new UIPanel();
	
	private UIButton but=new UIButton("ȷ��");
	
	private UIButton cal=new UIButton("ȡ��");


	

	public ConfigDlg(Container container){
		super(container);
		initDlg1();
		initlisten();
	}


	private void initlisten() {
		but.addActionListener(this);
		cal.addActionListener(this);
	}


	private void initDlg1() {		
		ui.setLayout(new BorderLayout());
		ui.setSize(200, 600);
		UIPanel no=new UIPanel();
		no.setSize(180, 100);
		no.setLayout(new BorderLayout());
	    no.add(sp,BorderLayout.SOUTH);
		
		no.add(la,BorderLayout.WEST);
		no.add(sumChooser,BorderLayout.CENTER);
		ui.add(no,BorderLayout.NORTH);
		UIPanel ce=new UIPanel();
		ce.setSize(180,400);
		ce.setLayout(new BorderLayout());
		da.setSize(80, 40);
		ce.add(da,BorderLayout.NORTH);
		ce.add(detail,BorderLayout.CENTER);
		
		
		ui.add(ce,BorderLayout.CENTER);
		UIPanel so=new UIPanel();
		so.setSize(180, 100);
		so.setLayout(new FlowLayout());
	    so.add(but);
	    so.add(cal);
		ui.add(so,BorderLayout.SOUTH);
		setContentPane(ui);	
	}


	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ȷ��")){
			onOk();
		}else{
			onCal();
		}
		
	}


	private void onCal() {
		this.closeCancel();
	}


	private void onOk() {
	
	   this.closeOK();
	}
	
	public boolean getIsNew(){
		return sumChooser.isSelected();
	}
	
	public String getDetail(){
		return detail.getText();
	}
	


}

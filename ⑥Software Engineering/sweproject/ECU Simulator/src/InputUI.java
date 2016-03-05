import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class InputUI extends JPanel {

	private static final int TEXT_SIZE=50;
	JLabel sample_label=new JLabel();
	private static final Font FONT=new Font(Font.SERIF, Font.PLAIN, TEXT_SIZE); 
	
	
	private static final int hgap=10;
	private static final int vgap=0;
	
	private static final Color color_wholeBackgroud=new Color(204, 255, 102);
	private static final Color color_input_button=new Color(204, 255, 255);
	
	
	JPanel input_panel;
	JLabel input_label;
	JTextField input_textField;
	JButton input_button;
	
	Resource resource;
	
	public JTextField getTextField(){
		return this.input_textField;
	}
	public InputUI(Resource resource){
		
	this.resource=resource;
	
	input_panel = new JPanel();
	input_label=new JLabel("Battery(%) :");
	input_textField=new JTextField(5);
	input_button=new JButton("< Setting");
	
	input_panel.setFont(FONT);
	input_label.setFont(FONT);
	input_textField.setFont(FONT);
	input_button.setFont(FONT);
	
	input_panel.setLayout(new FlowLayout(FlowLayout.LEFT, hgap, vgap));
	input_panel.setSize(500, 100);
	input_textField.setSize(30, 5);
	input_textField.setText(""+resource.getBattery());
	
	System.out.println("txtF: "+input_textField.getText());
	input_panel.add(input_label);
	input_panel.add(input_textField);
	input_panel.add(input_button);
	
	this.setLayout(new FlowLayout(FlowLayout.CENTER));
	this.add(input_panel);
	
	input_label.setBackground(color_wholeBackgroud);
	input_button.setBackground(color_input_button);
	input_button.addActionListener(new customActionListener());
	
	input_panel.setBackground(color_wholeBackgroud);
	this.setBackground(color_wholeBackgroud);
	this.setForeground(color_wholeBackgroud);
	this.setSize(500, 100);
	this.setVisible(true);
	}

	
	class customActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object selectedItem=(Object)e.getSource();
			
			if(selectedItem==input_button){
				int value_battery=Integer.parseInt(input_textField.getText());
				System.out.println("battery input : "+value_battery);
				resource.setBattery(value_battery);
			}
		}
		
	}


}


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.ImageProducer;
import java.time.Year;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextField;


public class OutputUI extends JPanel {
	
	private static final int TEXT_SIZE=50;
	JLabel sample_label=new JLabel();
	private static final Font FONT=new Font(Font.SERIF, Font.PLAIN, TEXT_SIZE); 

	private static final int hgap=10;
	private static final int vgap=0;
	
	
	Resource resource;
	JTextField text_temperature;
	JTextField text_seat;
	JTextField text_airconditioner;
	JButton button_temperature;
	
	
	private static Color color_wholeBackgroud=new Color(255, 153, 102);

	public OutputUI(Resource resource){
		
		this.resource=resource;
		
		setUpDefaultValue();
		//setEditable(true);
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setBackground(color_wholeBackgroud);
		this.add(setPanel());
	}
	
	public void setText_temperature(String text_temperature) {
		this.text_temperature.setText(text_temperature);
	}

	public void setText_seat(String text_seat) {
		this.text_seat.setText(text_seat);
	}

	public void setText_airconditioner(String text_airconditioner) {
		this.text_airconditioner.setText(text_airconditioner);
	}

	private void setUpDefaultValue(){
		
		text_temperature=new JTextField(8);
		text_seat=new JTextField(8);
		text_airconditioner=new JTextField(8);
		button_temperature=new JButton("Setting");
		
		button_temperature.addActionListener(new customActionListener());
		
		text_temperature.setFont(FONT);
		text_seat.setFont(FONT);
		text_airconditioner.setFont(FONT);
		
		text_temperature.setText(""+resource.getTemperature());
		text_seat.setText(""+resource.isSeat());
		text_airconditioner.setText(""+resource.getAirconditioner());
		

	}
	
	private void setEditable(boolean value){
		

		text_temperature.setEditable(value);
		text_seat.setEditable(value);
		text_airconditioner.setEditable(value);
	}
	
	private JPanel setPanel(){
		
		JPanel parent_panel=new JPanel();
		parent_panel.setLayout(new BoxLayout(parent_panel, BoxLayout.Y_AXIS));
		
		JPanel panel_temperature=new JPanel(new FlowLayout(FlowLayout.LEFT, hgap+10, vgap));
		JLabel label_temperature=new JLabel("Temperature > ");
		panel_temperature.add(label_temperature);
		panel_temperature.add(text_temperature);
		panel_temperature.add(button_temperature);
		
		JPanel panel_seat=new JPanel(new FlowLayout(FlowLayout.LEFT, hgap+94, vgap));
		JLabel label_seat=new JLabel("Seat > ");
		panel_seat.add(label_seat);
		panel_seat.add(text_seat);
		
		JPanel panel_airconditioner=new JPanel(new FlowLayout(FlowLayout.LEFT, hgap-7, vgap));
		JLabel label_airconditioner=new JLabel("Airconditioner > ");
		panel_airconditioner.add(label_airconditioner);
		panel_airconditioner.add(text_airconditioner);
		
		
		label_temperature.setFont(FONT);
		label_seat.setFont(FONT);
		label_airconditioner.setFont(FONT);
		button_temperature.setFont(FONT);
		
		panel_temperature.setBackground(color_wholeBackgroud);
		panel_seat.setBackground(color_wholeBackgroud);
		panel_airconditioner.setBackground(color_wholeBackgroud);
		
		parent_panel.add(panel_temperature);
		parent_panel.add(panel_seat);
		parent_panel.add(panel_airconditioner);
		
		return parent_panel;
	}
	
	
	class customActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object selectedItem=(Object)e.getSource();
			int temperature=Integer.parseInt(text_temperature.getText());

			resource.setTemperature(temperature);
			resource.defineAirconLevel();
		}
		
	}
	
}

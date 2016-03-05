package SSV;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

public class GUI_Input extends JPanel {

	public JSlider slide_brake;
	public JSlider slide_accel;
	public ButtonGroup toggleGroup;
	public JToggleButton button_gear_P;
	public JToggleButton button_gear_R;
	public JToggleButton button_gear_N;
	public JToggleButton button_gear_D;
	public JComboBox combobox_coolant;
	public JComboBox combobox_slope;
	
	public JPanel brakePanel, accelPanel ,gearLeftPanel;
	public JPanel gearRightPanel, gearUpPanel, gearPanel;
	public JPanel coolantPanel, slopePanel, upPanel, downPanel;

	public GUI_Input() {
		super();
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		
		JPanel titlePanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel contentPanel=new JPanel();
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		contentPanel.add(getPedalPanel());
		contentPanel.add(getComboPanel());
		
		JLabel titleLabel=new JLabel("INPUT UI");
		titleLabel.setBackground(Color.RED);
		
		titlePanel.add(titleLabel);
		
		inputPanel.add(titlePanel);
		inputPanel.add(contentPanel);
		
		this.add(inputPanel);
		this.setBackground(Color.BLACK);
		this.setVisible(true);
	}

	public Component getSlideBrake() {
		return slide_brake;
	}

	public Component getSlideAccel() {
		return slide_accel;
	}

	protected Component getPedalPanel() {

		JPanel upPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		brakePanel = new JPanel();
		accelPanel = new JPanel();
		gearLeftPanel = new JPanel();
		gearRightPanel=new JPanel();
		gearUpPanel=new JPanel();
		gearPanel=new JPanel();
		
		JLabel brakeLabel=new JLabel("Brake");
		JLabel accelLabel=new JLabel("Accel");
		JLabel gearLabel=new JLabel("   Gear");

		JLabel imageLabel=new JLabel(new ImageIcon("gearImage.jpg"));

		brakePanel.setLayout(new BoxLayout(brakePanel, BoxLayout.Y_AXIS));
		accelPanel.setLayout(new BoxLayout(accelPanel, BoxLayout.Y_AXIS));
		gearLeftPanel.setLayout(new BoxLayout(gearLeftPanel, BoxLayout.Y_AXIS));
		gearRightPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		gearUpPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		gearPanel.setLayout(new BoxLayout(gearPanel, BoxLayout.Y_AXIS));
		
		slide_brake = new JSlider(JSlider.VERTICAL, ConstantsList.LEVEL_MIN, ConstantsList.LEVEL_MAX,
				ConstantsList.LEVEL_MIN);
		slide_accel = new JSlider(JSlider.VERTICAL, ConstantsList.LEVEL_MIN, ConstantsList.LEVEL_MAX,
				ConstantsList.LEVEL_MIN);
		slide_accel.setEnabled(false);
		slide_brake.setEnabled(false);
		button_gear_P = new JToggleButton("P",true);
		button_gear_R = new JToggleButton("R",false);
		button_gear_N = new JToggleButton("N",false);
		button_gear_D = new JToggleButton("D",false);
		// slide_brake.addChangeListener(this);
		slide_brake.setMajorTickSpacing(10);
		slide_brake.setPaintTicks(true);

		// Create the label table
		Hashtable labelTable_brake = new Hashtable();

		for (int i = 0; i < ConstantsList.LEVEL_MAX - ConstantsList.LEVEL_MIN + 1; i++) {
			labelTable_brake.put(new Integer(ConstantsList.LEVEL_MIN + i), new JLabel(""
					+ ConstantsList.LEVEL_MIN + i));
		}
		slide_brake.setLabelTable(labelTable_brake);
		slide_brake.setPaintLabels(true);

		// slide_accel.addChangeListener(this);
		slide_accel.setMajorTickSpacing(10);
		slide_accel.setPaintTicks(true);

		// Create the label table
		Hashtable labelTable_accel = new Hashtable();
		for (int i = 0; i < ConstantsList.LEVEL_MAX - ConstantsList.LEVEL_MIN + 1; i++) {
			labelTable_accel.put(new Integer(ConstantsList.LEVEL_MIN + i), new JLabel(""
					+ ConstantsList.LEVEL_MIN + i));
		}
		
		slide_accel.setLabelTable(labelTable_brake);
		slide_accel.setPaintLabels(true);

		brakePanel.add(slide_brake);
		brakePanel.add(brakeLabel);
		
		accelPanel.add(slide_accel);
		accelPanel.add(accelLabel);
		
		gearLeftPanel.add(button_gear_P);
		gearLeftPanel.add(button_gear_R);
		gearLeftPanel.add(button_gear_N);
		gearLeftPanel.add(button_gear_D);
		
		toggleGroup = new ButtonGroup(); // 토글버튼을 그룹으로 지정해서 한개만 선택되게 하였다.....
		toggleGroup.add(button_gear_P);
		toggleGroup.add(button_gear_R);
		toggleGroup.add(button_gear_N);
		toggleGroup.add(button_gear_D);
		
		gearUpPanel.add(gearLeftPanel);
		gearUpPanel.add(imageLabel);
		gearPanel.add(gearUpPanel);
		gearPanel.add(gearLabel);	
		
		upPanel.add(brakePanel);
		upPanel.add(accelPanel);
		upPanel.add(gearPanel);

		return upPanel;
	}

	protected Component getComboPanel() {

		JPanel downPanel = new JPanel();
		downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.Y_AXIS));
		
		coolantPanel = new JPanel();
		slopePanel = new JPanel();
		
		coolantPanel.setLayout(new BoxLayout(coolantPanel, BoxLayout.Y_AXIS));
		slopePanel.setLayout(new BoxLayout(slopePanel, BoxLayout.Y_AXIS));

		JLabel coolantLabel=new JLabel("temperature of coolant");
		JLabel slopeLabel=new JLabel("incline of slope");
		
		combobox_coolant = new JComboBox();
		combobox_slope = new JComboBox();

		for (int i = 0; i < ConstantsList.COOLANT_MAX_VALUE - ConstantsList.COOLANT_MIN_VALUE + 1; i++) {
			combobox_coolant.addItem(ConstantsList.COOLANT_MIN_VALUE + i);
		}

		for (int j = 0; j < ConstantsList.SLOPE_MAX_VALUE - ConstantsList.SLOPE_MIN_VALUE + 1; j++) {
			combobox_slope.addItem(ConstantsList.SLOPE_MIN_VALUE + j);
		}
		
		coolantPanel.add(combobox_coolant);
		coolantPanel.add(coolantLabel);
		slopePanel.add(combobox_slope);
		slopePanel.add(slopeLabel);
		
		downPanel.add(coolantPanel);
		downPanel.add(slopePanel);

		return downPanel;
	}

}

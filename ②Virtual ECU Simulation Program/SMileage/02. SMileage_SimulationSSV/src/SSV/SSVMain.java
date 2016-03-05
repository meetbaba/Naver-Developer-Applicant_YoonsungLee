package SSV;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SSVMain {

	public static void main(String[] args) {
		final Resource resource = new Resource();
		resource.start();
		frame1 inputFrame = new frame1(resource);
		frame2 dashFrame = new frame2(resource);
		frame3 drivingFrame = new frame3(resource);
		frame4 Frame = new frame4(resource);

	}
}

class frame1 extends JFrame {
	GUI_Input input_gui = new GUI_Input();
	Resource resource = null;

	public frame1(Resource re) {
		resource = re;
		JFrame frame = new JFrame();

		Container cp = frame.getContentPane();
		cp.add(input_gui);
		cp.setLayout(new FlowLayout());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		input_gui.slide_brake.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent brakeE) {
				// TODO Auto-generated method stub
				
				JSlider source = (JSlider) brakeE.getSource();
				resource.setBrakeState(source.getValue());
				if (source.getValue() > 0){
					input_gui.slide_accel.setEnabled(false);
					input_gui.button_gear_P.setEnabled(false);
				}
				else {
					input_gui.slide_accel.setEnabled(true);
					input_gui.button_gear_P.setEnabled(true);
				}
			}
		});
		input_gui.slide_accel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent accelE) {
				// TODO Auto-generated method stub
				JSlider source = (JSlider) accelE.getSource();
				resource.setAccelState(source.getValue());
				if (source.getValue() > 0){
					input_gui.slide_brake.setEnabled(false);
					input_gui.button_gear_P.setEnabled(false);
				}
				else {
					input_gui.slide_brake.setEnabled(true);
					input_gui.button_gear_P.setEnabled(true);
				}
			}
		});
		
		input_gui.combobox_coolant.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				resource.setCoolantState(input_gui.combobox_coolant.getSelectedIndex());
			}
		});
		input_gui.combobox_slope.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				resource.setSlopeState(input_gui.combobox_slope.getSelectedIndex());
			}
		});
		
		input_gui.button_gear_P.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				resource.setGearState(ConstantsList.GEAR_STATE_P);
				input_gui.slide_accel.setEnabled(false);
				input_gui.slide_brake.setEnabled(false);
			}
		});
		input_gui.button_gear_R.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				resource.setGearState(ConstantsList.GEAR_STATE_R);
				input_gui.slide_accel.setEnabled(true);
				input_gui.slide_brake.setEnabled(true);
			}
		});
		input_gui.button_gear_N.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resource.setGearState(ConstantsList.GEAR_STATE_N);
				input_gui.slide_accel.setEnabled(true);
				input_gui.slide_brake.setEnabled(true);
			}
		});
		input_gui.button_gear_D.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resource.setGearState(ConstantsList.GEAR_STATE_D);
				input_gui.slide_accel.setEnabled(true);
				input_gui.slide_brake.setEnabled(true);
			}
		});		
	}
}


class frame2 extends JFrame {
	
	Resource resource = null;
	JFrame frame = new JFrame();

	public frame2(Resource re) {
		resource=re;
		GUI_Dashboard dashBoard_gui = new GUI_Dashboard(re);
		Container cp = frame.getContentPane();
		cp.add(dashBoard_gui);
		cp.setLayout(new FlowLayout());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
};


class frame3 extends JFrame {
	
	Resource resource = null;
	JFrame frame = new JFrame();

	public frame3(Resource resource) {
		Container cp = frame.getContentPane();
		GUI_Driving driving_gui = new GUI_Driving(resource);
		cp.add(driving_gui);
		cp.setLayout(new BoxLayout(cp,BoxLayout.X_AXIS));

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 800, 500);
		frame.setVisible(true);
	}
};

class frame4 extends JFrame {
	
	Resource resource = null;
	JFrame frame = new JFrame();

	public frame4(Resource resource) {
		Container cp = frame.getContentPane();
		GUI_Combution combution_gui = new GUI_Combution(resource);
		cp.add(combution_gui);
		cp.setLayout(new BoxLayout(cp,BoxLayout.X_AXIS));

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 600, 800);
		frame.setVisible(true);
	}
};
	
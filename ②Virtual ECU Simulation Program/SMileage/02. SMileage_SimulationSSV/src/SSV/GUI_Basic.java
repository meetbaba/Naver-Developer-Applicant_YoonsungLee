package SSV;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.JPanel;

import com.jogamp.opengl.util.FPSAnimator;

public class GUI_Basic extends JPanel {

	GLProfile glprofile;
	GLCapabilities glcapabilities;
	GLJPanel gljpanel;
	
	FPSAnimator animator;
	
	public GUI_Basic(){
		
        glprofile = GLProfile.getDefault();
        glcapabilities = new GLCapabilities( glprofile );
        gljpanel = new GLJPanel( glcapabilities );
        
        animator = new FPSAnimator(gljpanel, 60);
        
        
        this.setLayout(new GridLayout(1,1));
        this.add(gljpanel);
        animator.start();
	}
}

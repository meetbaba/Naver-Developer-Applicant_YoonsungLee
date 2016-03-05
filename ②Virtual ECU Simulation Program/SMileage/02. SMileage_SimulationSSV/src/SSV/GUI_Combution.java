package SSV;

import java.awt.Color;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class GUI_Combution extends GUI_Basic{

	Resource re;
	
	public GUI_Combution(Resource resource) {
		// TODO Auto-generated constructor stub
		super();
		
		
		
		re=resource;
		
		gljpanel.addGLEventListener( new GLEventListener() {
            @Override
            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
                Drawing_Combution.setup( glautodrawable.getGL().getGL2(), width, height );
            
            }
            
            @Override
            public void init( GLAutoDrawable glautodrawable ) {
            
            }
            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
            }
            
            @Override
            public void display( GLAutoDrawable glautodrawable ) {
            	System.out.println("velocity: "+re.getVelocity());
            Drawing_Combution.update(re.getVelocity());	
            Drawing_Combution.render( glautodrawable.getGL().getGL2(), glautodrawable.getWidth(), glautodrawable.getHeight(), re.getFuelCutState() );
            }
        });
		
		this.setSize(640, 1000);
		this.setBackground(Color.BLACK);

        this.setVisible(true);
	}
}

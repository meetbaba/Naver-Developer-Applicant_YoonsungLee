package SSV;

import java.awt.Color;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class GUI_Driving extends GUI_Basic {
	Resource re;
	public GUI_Driving(Resource resource){
		
		super();
		 re = resource;
		gljpanel.addGLEventListener( new GLEventListener() {
            @Override
            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
                Drawing_Driving.setup( glautodrawable.getGL().getGL2(), width, height, re );
            }
            
            @Override
            public void init( GLAutoDrawable glautodrawable ) {
            
            }
            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
            }
            
            @Override
            public void display( GLAutoDrawable glautodrawable ) {
            	Drawing_Driving.update();		//update하면 움직임 update 함수에 정의된 대로
                Drawing_Driving.render( glautodrawable.getGL().getGL2(), glautodrawable.getWidth(), glautodrawable.getHeight() );
            }
        });
		
		this.setSize(640, 480);
		this.setBackground(Color.BLACK);

        this.setVisible(true);
	}
}

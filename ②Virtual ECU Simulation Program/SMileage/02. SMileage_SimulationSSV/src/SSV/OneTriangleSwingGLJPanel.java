package SSV;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.*;

import com.jogamp.opengl.util.FPSAnimator;	//FPS 볼수있게 하는 놈

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A minimal program that draws with JOGL in a Swing JFrame using the AWT GLJPanel.
 *
 * @author Wade Walker
 */
public class OneTriangleSwingGLJPanel {

    public static void main( String [] args ) {
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        GLJPanel gljpanel = new GLJPanel( glcapabilities );
        //GLJPanel gljpanel2 = new GLJPanel( glcapabilities ); 
        
        final JFrame jframe = new JFrame( "One Triangle Swing GLJPanel" );
        final FPSAnimator animator = new FPSAnimator(gljpanel, 60);	//애니메이터에게 줘야겠다. frame late는 내가 정할수 있음._1초에 60프레임 뿌린다는거
        //final FPSAnimator animator2 = new FPSAnimator(gljpanel2, 60);
        
        gljpanel.addGLEventListener( new GLEventListener() {
            @Override
            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
                OneTriangle.setup( glautodrawable.getGL().getGL2(), width, height );
            
            }
            
            @Override
            public void init( GLAutoDrawable glautodrawable ) {
            
            }
            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
            }
            
            @Override
            public void display( GLAutoDrawable glautodrawable ) {
            OneTriangle.update();		//update하면 움직임 update 함수에 정의된 대로
                OneTriangle.render( glautodrawable.getGL().getGL2(), glautodrawable.getWidth(), glautodrawable.getHeight() );
            }
        });
        /*
        gljpanel2.addGLEventListener( new GLEventListener() {
            @Override
            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
                OneTriangle.setup( glautodrawable.getGL().getGL2(), width, height );
            
            }
            
            @Override
            public void init( GLAutoDrawable glautodrawable ) {
            
            }
            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
            }
            
            @Override
            public void display( GLAutoDrawable glautodrawable ) {
            OneTriangle.update();
                OneTriangle.render( glautodrawable.getGL().getGL2(), glautodrawable.getWidth(), glautodrawable.getHeight() );
            }
        });
        */
        JButton button1 = new JButton("Button 1");
        //JButton button2 = new JButton("Button 2");
        //adding action listener to menu items 
        button1.addActionListener( 
              new ActionListener(){ 
                  @Override 
                  public void actionPerformed(ActionEvent e) 
                  { 
                      System.out.println("New is pressed"); 
                      
                      animator.start(); 
                      
                      jframe.validate(); //프레임에서 추가된게 있으면 갱신하게 됨.
                      jframe.repaint(); //
                  } 
              }); 
        /*
        button2.addActionListener( 
                new ActionListener(){ 
                    @Override 
                    public void actionPerformed(ActionEvent e) 
                    { 
                        System.out.println("New is pressed"); 
                        
                        animator2.start(); 
                        
                        jframe.validate(); 
                        jframe.repaint(); 
                    } 
                });
 */

        Container cp = jframe.getContentPane();
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(1,2));
        panel3.add(gljpanel);
        //panel3.add(gljpanel2);
        cp.add( panel3, BorderLayout.CENTER );
        JPanel panel = new JPanel(); 
        panel.add(new JLabel("JOGL Test"));
        cp.add( panel, BorderLayout.NORTH );
        JPanel panel2 = new JPanel();
        panel2.add(button1);
        //panel2.add(button2);
        cp.add(panel2, BorderLayout.SOUTH);
        jframe.setSize( 640, 480 );
        jframe.setVisible( true );
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

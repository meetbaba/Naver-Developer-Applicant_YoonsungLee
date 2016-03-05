package SSV;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

public class OneTriangle {
	private static double theta = 0;
	private static double vector_square = 0;
	private static double vector_triangle = 0;
	private static double vector_pentagonal =0;

	private static int FLAG_SQUARE=1;
	private static int FLAG_TRIANGLE=4;
	private static int FLAG_PENTAGONAL=6;
	
	private static final int SQUARE_FLAG_UP=1;
	private static final int SQUARE_FLAG_DOWN=2;
	
	private static final int TRIANGLE_FLAG_RIGHT=3;
	private static final int TRIANGLE_FLAG_LEFT=4;
	
	private static final int PENTAGONAL_FLAG_UP=5;
	private static final int PENTAGONAL_FLAG_DOWN=6;
	
	
	private static double decide_square_up;
	private static double decide_square_down;
	private static double decide_triangle_right;
	private static double decide_triangle_left;
	private static double decide_pentagonal_up;
	private static double decide_pentagonal_down;
	
	protected static void setup(GL2 gl2, int width, int height) {
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		// coordinate system origin at lower left with width and height same as
		// the window
		GLU glu = new GLU();
		glu.gluOrtho2D(0.0f, width, 0.0f, height);
		gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();
		gl2.glViewport(0, 0, width, height);
	}

	protected static void render(GL2 gl2, int width, int height) {
		
		float standard_line_left=(width*(1/(float)3))-50;
		float standard_line_right=(width*(2/(float)3))+50;

    	gl2.glClearColor(1, 1, 1, 0);

		gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
		// draw a triangle filling the window
		gl2.glLoadIdentity();
		

		
		gl2.glBegin(GL.GL_LINE_LOOP);
		gl2.glColor3f(1, 0, 0);
		gl2.glVertex2d(40, vector_square+60);
		gl2.glVertex2d(40, vector_square);
		gl2.glVertex2d(140, vector_square);
		gl2.glVertex2d(140, vector_square+60);
		gl2.glEnd();
		gl2.glFlush();
		
		decide_square_down=vector_square;
		decide_square_up=vector_square+60;
		
		if(decide_square_down<=20)
			FLAG_SQUARE=SQUARE_FLAG_UP;
		else if(decide_square_up>=height-20)
			FLAG_SQUARE=SQUARE_FLAG_DOWN;
		
		gl2.glBegin(GL.GL_TRIANGLES);
		gl2.glColor3f(0, 1, 0);
		gl2.glVertex2d(standard_line_right-30-vector_triangle, height*0.6 );
		gl2.glVertex2d(standard_line_right-60-vector_triangle, height*0.4  );
		gl2.glVertex2d(standard_line_right-vector_triangle, height*0.4 );
		gl2.glEnd();
		gl2.glFlush();
		
		decide_triangle_right=standard_line_right-vector_triangle;
		decide_triangle_left=standard_line_right-60-vector_triangle;
		
		if(decide_triangle_left<=standard_line_left+20)
			FLAG_TRIANGLE=TRIANGLE_FLAG_RIGHT;
		else if(decide_triangle_right>=standard_line_right-20)
			FLAG_TRIANGLE=TRIANGLE_FLAG_LEFT;
		
		gl2.glBegin(GL.GL_LINE_LOOP);
		gl2.glColor3f(0, 0, 1);
		gl2.glVertex2d(width-50, height-vector_pentagonal);
		gl2.glVertex2d(width-80, height-40-vector_pentagonal);
		gl2.glVertex2d(width-70, height-100-vector_pentagonal);
		gl2.glVertex2d(width-35, height-100-vector_pentagonal);
		gl2.glVertex2d(width-20, height-40-vector_pentagonal);
		gl2.glEnd();
		gl2.glFlush();
		
		decide_pentagonal_up=height-vector_pentagonal;
		decide_pentagonal_down=height-100-vector_pentagonal;
		
		if(decide_pentagonal_up>=height-20)
			FLAG_PENTAGONAL=PENTAGONAL_FLAG_DOWN;
		else if(decide_pentagonal_down<=20)
			FLAG_PENTAGONAL=PENTAGONAL_FLAG_UP;
		
	}

	protected static void update() {
		
		if(FLAG_SQUARE==SQUARE_FLAG_UP)
			vector_square+=5;
		else if(FLAG_SQUARE==SQUARE_FLAG_DOWN)
			vector_square-=5;
		
		if(FLAG_TRIANGLE==TRIANGLE_FLAG_LEFT)
			vector_triangle+=5;
		else if(FLAG_TRIANGLE==TRIANGLE_FLAG_RIGHT)
			vector_triangle-=5;
		
		if(FLAG_PENTAGONAL==PENTAGONAL_FLAG_DOWN)
			vector_pentagonal+=5;
		else if(FLAG_PENTAGONAL==PENTAGONAL_FLAG_UP)
			vector_pentagonal-=5;
	}
}

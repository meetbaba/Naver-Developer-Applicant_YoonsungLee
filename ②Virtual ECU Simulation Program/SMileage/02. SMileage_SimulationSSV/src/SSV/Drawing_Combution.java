package SSV;

import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.swt.GLCanvas;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;

public class Drawing_Combution extends javax.media.opengl.awt.GLCanvas {

	private static double s = 1;

	private static double theta = 0.0;
	private static double theta_cok_1=0.0;
	private static double theta_cok_2=0.0;

	
	private static final int STATE_INJECT_WAIT=0;
	private static final int STATE_INJECT_SHOOT=1;
	private static final int STATE_INJECT_EXPLOSION=2;
	private static final int EXPLOSION_STEP_1=1;
	private static final int EXPLOSION_STEP_2=2;
	private static final int EXPLOSION_STEP_3=3;
	private static final int EXPLOSION_STEP_4=4;
	
	private static final int STATE_FUELCUT_ON=1;
	private static final int STATE_FUELCUT_OFF=2;
	
	
	//added
	float rtri, rquad;

	private static double CRANK_CENTER_WIDTH;
	private static double CRANK_CENTER_HEIGHT;
	private static double TIP_CENTER_WIDTH;
	private static double TIP_CENTER_HEIGHT;
	private static double RADIUS_FROM_CENTER_OF_CRANK = 70;
	private static double RADIUS_CRANK_CENTER = 70;
	private static double RADIUS_CRANK_WHEEL = 50;
	private static double RADIUS_TIP_WHEEL=10;

	private static double HAMMER_WIDTH;
	private static double HAMMER_HEIGHT;
	
	private static double INJECTOR_START_WIDTH;
	private static double INJECTOR_END_WIDTH;
	private static double INJECTOR_START_HEIGHT;
	
	private static double INTAKE_START_WIDTH;
	private static double INTAKE_END_WIDTH;
	private static double INTAKE_START_HEIGHT;
	private static double INTAKE_END_HEIGHT;
	
	private static double EXHAUST_START_WIDTH;
	private static double EXHAUST_END_WIDTH;
	private static double EXHAUST_START_HEIGHT;
	private static double EXHAUST_END_HEIGHT;
	
	private static double OPTION_START_HEIGHT;
	private static double OPTION_END_HEIGHT;
	
	private static double ADJUST_VALUE=10;
	
	private static double POWER_LEFT_BOTTOM_WIDTH;
	private static double POWER_LEFT_TOP_WIDTH;
	private static double POWER_RIGHT_TOP_WIDTH;
	private static double POWER_RIGHT_BOTTOM_WIDTH;
	
	private static double POWER_LEFT_BOTTOM_HEIGHT;
	private static double POWER_LEFT_TOP_HEIGHT;
	private static double POWER_RIGHT_TOP_HEIGHT;
	private static double POWER_RIGHT_BOTTOM_HEIGHT;
	
	private static int FLAG_EXPLOSION_STEP=0;
	private static int FLAG_INJECT_STATE=0;
	
	private static int FLAG_ROTATE=0;
	
	private static int FLAG_COK_1=0;
	private static int FLAG_COK_2=0;
	private static int FLAG_EX_TO_IN=0;
	
	private static double basic_delta=0.0;
	
	
	private static int FLAG_FUELCUT_STATE=0;
	
	static Drawing_Combution dc;

	   
	   private static float textureTop, textureBottom, textureLeft, textureRight;
	   
	
	protected static void setup(GL2 gl, int width, int height) {
		
		dc = new Drawing_Combution();

		gl.glMatrixMode(gl.GL_PROJECTION);
		gl.glLoadIdentity();
		// coordinate system origin at lower left with width and height same as
		// the window
		GLU glu = new GLU();
		glu.gluOrtho2D(0.0f, width, 0.0f, height);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(gl.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
		gl.glShadeModel(gl.GL_SMOOTH);
		gl.glClearColor(1, 1, 1, 1);
		

	}

	protected static void render(GL2 gl, int width, int height, int fuelCut_state) {
		
		
	
		float r;
		float g;
		float b;
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		// draw a triangle filling the window
		gl.glLoadIdentity();
		
		/**
		 * injector color logic
		 */
		if(fuelCut_state==1){
			r=(float)139/255;
			g=(float)35/255;
			b=(float)154/255;
			FLAG_FUELCUT_STATE=STATE_FUELCUT_ON;
			FLAG_INJECT_STATE=STATE_INJECT_WAIT;
		}else if(((theta*2/Math.PI)%8 <= 2) && ((theta*2/Math.PI)%8 >= 1) ){

			r=1f;
			g=0f;
			b=0f;
			FLAG_FUELCUT_STATE=STATE_FUELCUT_OFF;
			FLAG_INJECT_STATE=STATE_INJECT_SHOOT;
			
		}else if(((theta*2/Math.PI)%8 >= 2) && ((theta*2/Math.PI)%8 <= 3)){
			
			FLAG_FUELCUT_STATE=STATE_FUELCUT_OFF;
			FLAG_INJECT_STATE=STATE_INJECT_EXPLOSION;
			
			r=(float)139/255;
			g=(float)35/255;
			b=(float)154/255;
		}else{
			FLAG_FUELCUT_STATE=STATE_FUELCUT_OFF;
			FLAG_INJECT_STATE=STATE_INJECT_WAIT;

			r=(float)139/255;
			g=(float)35/255;
			b=(float)154/255;
		}
		
		
		
		
		dc.drawBody(gl, 40, width - 40, 10, height - 100);
		dc.drawInjector(gl, INJECTOR_START_WIDTH, INJECTOR_END_WIDTH, INJECTOR_START_HEIGHT
				, height-10, r, g, b);
		dc.drawCrankCenter(gl, CRANK_CENTER_WIDTH, CRANK_CENTER_HEIGHT);
		dc.drawCrankWheel(gl, CRANK_CENTER_WIDTH + RADIUS_FROM_CENTER_OF_CRANK
				* Math.cos(theta), CRANK_CENTER_HEIGHT
				+ RADIUS_FROM_CENTER_OF_CRANK * Math.sin(theta),
				TIP_CENTER_WIDTH, TIP_CENTER_HEIGHT
						+ (RADIUS_FROM_CENTER_OF_CRANK * Math.sin(theta)));
		dc.drawIntakeEntrance(gl, theta, INTAKE_START_WIDTH, INTAKE_END_WIDTH, INTAKE_START_HEIGHT, height);
		dc.drawExhaust(gl, theta, EXHAUST_START_WIDTH, EXHAUST_END_WIDTH, EXHAUST_START_HEIGHT, height);
		dc.drawPower(gl);
	}

	protected static void update(float velocity) {

		
		if(velocity==0){
			basic_delta=0;
		}else if(velocity>0 && velocity < 10){
			basic_delta=Math.PI/100;
		}else if(velocity>10 && velocity < 20){
			basic_delta=Math.PI/100*2;
		}else if(velocity>20 && velocity < 30){
			basic_delta=Math.PI/100*3;
		}else if(velocity>30 && velocity < 40){
			basic_delta=Math.PI/100*4;
		}else if(velocity>0 && velocity < 50){
			basic_delta=Math.PI/100*5;
		}else if(velocity>0 && velocity < 60){
			basic_delta=Math.PI/100*6;
		}else if(velocity>0 && velocity < 70){
			basic_delta=Math.PI/100*7;
		}else if(velocity>0 && velocity < 80){
			basic_delta=Math.PI/100*8;
		}else if(velocity>0 && velocity < 90){
			basic_delta=Math.PI/100*9;
		}else if(velocity>0 && velocity < 100){
			basic_delta=Math.PI/100*10;
		}
		
		
		theta += basic_delta;
		if(Math.sin(theta)==1){
			FLAG_ROTATE++;
		}
		
		if(FLAG_COK_1==1){
			theta_cok_1+=(basic_delta)*2;
		}else{
			theta_cok_1=0.0;
		}
		
		if(FLAG_COK_2==1){
			theta_cok_2+=(basic_delta)*2;
		}else{
			theta_cok_2=0.0;
		}
		
	}
	
	

	public void drawBody(GL2 gl, int startWidth, int endWidth, int startHeight,
			int endHeight) {

		int width = endWidth - startWidth;
		int height = endHeight - startHeight;

		CRANK_CENTER_HEIGHT = startHeight + (height / 8) + (height / 8)
				* (3 / 2);
		CRANK_CENTER_WIDTH = startWidth + (width / 2);

		TIP_CENTER_WIDTH = startWidth + (width / 2);
		TIP_CENTER_HEIGHT = startHeight + (height / 8)*5;

		HAMMER_WIDTH=(width/5)*3;
		HAMMER_HEIGHT=(height/8)*2;
		
		INJECTOR_START_WIDTH=startWidth+((width/5)*2);
		INJECTOR_END_WIDTH=startWidth+((width/5)*3);
		INJECTOR_START_HEIGHT=endHeight;
		
		INTAKE_START_WIDTH=startWidth + (width / 5);
		INTAKE_END_WIDTH=startWidth + (width / 5)*2 ;
		INTAKE_START_HEIGHT=startHeight + (height / 8) * 7;
		INTAKE_END_HEIGHT=endHeight;
		
		EXHAUST_START_WIDTH=startWidth+(width/5)*3;
		EXHAUST_END_WIDTH=startWidth+(width/5)*4;
		EXHAUST_START_HEIGHT=startHeight + (height / 8) * 7;
		EXHAUST_END_HEIGHT=endHeight;
		
		OPTION_START_HEIGHT=startHeight + (height / 8) * 7+ADJUST_VALUE;
		OPTION_END_HEIGHT=endHeight;
		
		
		POWER_LEFT_TOP_WIDTH=startWidth + (width / 5) * 2;
		POWER_LEFT_TOP_HEIGHT=endHeight;
		POWER_RIGHT_TOP_WIDTH=startWidth + (width / 5) * 3;
		POWER_RIGHT_TOP_HEIGHT=endHeight;
		
		
		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glColor3f(0, 0, 0);
		gl.glVertex2d(startWidth, startHeight + (height / 2));
		gl.glVertex2d(startWidth + (width / 5), startHeight + (height / 8) * 4);
		gl.glVertex2d(startWidth + (width / 5), startHeight + (height / 8) * 7+ADJUST_VALUE);
		gl.glVertex2d(startWidth + (width / 5) * 2, endHeight);
		gl.glVertex2d(startWidth + (width / 5) * 3, endHeight);
		gl.glVertex2d(startWidth + (width / 5) * 4, startHeight + (height / 8)
				* 7+ADJUST_VALUE);
		gl.glVertex2d(startWidth + (width / 5) * 4, startHeight + (height / 8)
				* 4);
		gl.glVertex2d(endWidth, startHeight + (height / 8) * 4);
		gl.glVertex2d(endWidth, startHeight + (height / 8));
		gl.glVertex2d(startWidth + (width / 5) * 4, startHeight);
		gl.glVertex2d(startWidth + (width / 5), startHeight);
		gl.glVertex2d(startWidth, startHeight + (height / 8));
		gl.glEnd();
		gl.glFlush();
		
		
		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glColor3f(0, 0, 0);
		gl.glVertex2d(startWidth + (width / 5), startHeight + (height / 8) * 7+ADJUST_VALUE);
		gl.glVertex2d(startWidth + (width / 5) * 2, endHeight);
		gl.glVertex2d(startWidth + (width / 5), endHeight+30);
		gl.glVertex2d(startWidth , endHeight+30);
		gl.glVertex2d(startWidth , endHeight);
		gl.glVertex2d(startWidth + (width / 5) , endHeight);
		gl.glEnd();
		gl.glFlush();
		
		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glColor3f(0, 0, 0);
		gl.glVertex2d(startWidth + (width / 5) * 3, endHeight);
		gl.glVertex2d(startWidth + (width / 5) * 4, startHeight + (height / 8) * 7+ADJUST_VALUE);
		gl.glVertex2d(startWidth + (width / 5) * 4, endHeight) ;
		gl.glVertex2d(startWidth + (width / 5) * 5, endHeight );
		gl.glVertex2d(startWidth + (width / 5) * 5, endHeight + 30);
		gl.glVertex2d(startWidth + (width / 5) * 4, endHeight + 30);
		gl.glEnd();
		gl.glFlush();
		

	}

	public void drawPower(GL2 gl){
		
		double topWidth=POWER_RIGHT_TOP_WIDTH-POWER_LEFT_TOP_WIDTH;
		double bottomWidth=POWER_RIGHT_BOTTOM_WIDTH-POWER_LEFT_BOTTOM_WIDTH;
		
		
		if(FLAG_INJECT_STATE==STATE_INJECT_SHOOT){
			/**
			 * first line 3
			 */
			gl.glColor3f((float)128/255, (float)64/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2), (float)(POWER_LEFT_TOP_HEIGHT-10));
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2-5), (float)(POWER_LEFT_TOP_HEIGHT-30));
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2+5), (float)(POWER_LEFT_TOP_HEIGHT-30));
			gl.glEnd();
			gl.glFlush();
			
			gl.glColor3f((float)128/255, (float)64/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2-20), (float)(POWER_LEFT_TOP_HEIGHT-10));
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2-5-20), (float)(POWER_LEFT_TOP_HEIGHT-30));
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2+5-20), (float)(POWER_LEFT_TOP_HEIGHT-30));
			gl.glEnd();
			gl.glFlush();
			
			gl.glColor3f((float)128/255, (float)64/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2+20), (float)(POWER_LEFT_TOP_HEIGHT-10));
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2-5+20), (float)(POWER_LEFT_TOP_HEIGHT-30));
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2+5+20), (float)(POWER_LEFT_TOP_HEIGHT-30));
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * second line 4
			 */
			gl.glColor3f((float)128/255, (float)64/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2-10), (float)(POWER_LEFT_TOP_HEIGHT-40));
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2-5-10), (float)(POWER_LEFT_TOP_HEIGHT-60));
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2+5-10), (float)(POWER_LEFT_TOP_HEIGHT-60));
			gl.glEnd();
			gl.glFlush();
			
			gl.glColor3f((float)128/255, (float)64/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2+10), (float)(POWER_LEFT_TOP_HEIGHT-40));
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2-5+10), (float)(POWER_LEFT_TOP_HEIGHT-60));
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2+5+10), (float)(POWER_LEFT_TOP_HEIGHT-60));
			gl.glEnd();
			gl.glFlush();
			
			gl.glColor3f((float)128/255, (float)64/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2-20-10), (float)(POWER_LEFT_TOP_HEIGHT-40));
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2-5-20-10), (float)(POWER_LEFT_TOP_HEIGHT-60));
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2+5-20-10), (float)(POWER_LEFT_TOP_HEIGHT-60));
			gl.glEnd();
			gl.glFlush();
			
			gl.glColor3f((float)128/255, (float)64/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2+20+10), (float)(POWER_LEFT_TOP_HEIGHT-40));
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2-5+20+10), (float)(POWER_LEFT_TOP_HEIGHT-60));
			gl.glVertex2f((float)(POWER_LEFT_TOP_WIDTH+topWidth/2+5+20+10), (float)(POWER_LEFT_TOP_HEIGHT-60));
			gl.glEnd();
			gl.glFlush();
			
		}else if(FLAG_INJECT_STATE==STATE_INJECT_EXPLOSION){
			
			double pi2 = Math.PI*2;
			double radius ;
			double delta_theta = pi2 / 50;
			double xcenter;
			double ycenter;
			double x, y;
			double theta = 0.0;

			/**
			 * first line 2-1
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+topWidth/2;
			ycenter=POWER_LEFT_TOP_HEIGHT-25;
			
			radius=10;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			/**
			 * first explosion 2-1
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+topWidth/2-40;
			ycenter=POWER_LEFT_TOP_HEIGHT-25;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			/**
			 * second explosion 4-1
			 */
			xcenter=POWER_LEFT_TOP_WIDTH-10;
			ycenter=POWER_LEFT_TOP_HEIGHT-50;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * second explosion 4-2
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+15;
			ycenter=POWER_LEFT_TOP_HEIGHT-50;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * second explosion 4-3
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+50;
			ycenter=POWER_LEFT_TOP_HEIGHT-50;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * second explosion 4-4
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+110;
			ycenter=POWER_LEFT_TOP_HEIGHT-50;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			

			/**
			 * third explosion 6-1
			 */
			xcenter=POWER_LEFT_TOP_WIDTH-50;
			ycenter=POWER_LEFT_TOP_HEIGHT-80;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * third explosion 6-2
			 */
			xcenter=POWER_LEFT_TOP_WIDTH-20;
			ycenter=POWER_LEFT_TOP_HEIGHT-90;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * third explosion 6-3
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+15;
			ycenter=POWER_LEFT_TOP_HEIGHT-70;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * third explosion6-4
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+50;
			ycenter=POWER_LEFT_TOP_HEIGHT-80;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * third explosion 6-5
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+100;
			ycenter=POWER_LEFT_TOP_HEIGHT-90;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * third explosion 6-6
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+130;
			ycenter=POWER_LEFT_TOP_HEIGHT-70;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			
			/**
			 * fourth explosion 8-1
			 */
			xcenter=POWER_LEFT_TOP_WIDTH-70;
			ycenter=POWER_LEFT_TOP_HEIGHT-110;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * fourth explosion 8-2
			 */
			xcenter=POWER_LEFT_TOP_WIDTH-50;
			ycenter=POWER_LEFT_TOP_HEIGHT-100;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * fourth explosion 8-3
			 */
			xcenter=POWER_LEFT_TOP_WIDTH-15;
			ycenter=POWER_LEFT_TOP_HEIGHT-120;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * fourth explosion 8-4
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+20;
			ycenter=POWER_LEFT_TOP_HEIGHT-110;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * fourth explosion8-5
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+50;
			ycenter=POWER_LEFT_TOP_HEIGHT-100;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * fourth explosion 8-6
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+76;
			ycenter=POWER_LEFT_TOP_HEIGHT-120;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			

			/**
			 * fourth line 8-7
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+110;
			ycenter=POWER_LEFT_TOP_HEIGHT-100;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			

			/**
			 * fourth line 8-8
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+150;
			ycenter=POWER_LEFT_TOP_HEIGHT-110;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			

			
			/**
			 * explosion 8-1
			 */
			xcenter=POWER_LEFT_TOP_WIDTH-70;
			ycenter=POWER_LEFT_TOP_HEIGHT-150;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * explosion 8-2
			 */
			xcenter=POWER_LEFT_TOP_WIDTH-50;
			ycenter=POWER_LEFT_TOP_HEIGHT-140;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * explosion 8-3
			 */
			xcenter=POWER_LEFT_TOP_WIDTH-15;
			ycenter=POWER_LEFT_TOP_HEIGHT-160;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * explosion 8-4
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+20;
			ycenter=POWER_LEFT_TOP_HEIGHT-150;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * explosion 8-5
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+50;
			ycenter=POWER_LEFT_TOP_HEIGHT-140;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			/**
			 * explosion 8-6
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+76;
			ycenter=POWER_LEFT_TOP_HEIGHT-160;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			

			/**
			 * explosion 8-7
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+110;
			ycenter=POWER_LEFT_TOP_HEIGHT-140;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			

			/**
			 * explosion 8-8
			 */
			xcenter=POWER_LEFT_TOP_WIDTH+150;
			ycenter=POWER_LEFT_TOP_HEIGHT-150;
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=6;
			theta=0;
			gl.glColor3f((float)244/255, (float)104/255, (float)11/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			radius=3;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
		}
	};
	
	public void drawCrankCenter(GL2 gl, double centerOfWidth,
			double centerOfHeight) {

		double pi2 = Math.PI*2;
		double radius = RADIUS_CRANK_CENTER;
		double delta_theta = pi2 / 50;
		double xcenter = centerOfWidth, ycenter = centerOfHeight;
		double x, y;
		double theta = 0.0;

		/* circle */
		gl.glColor3f((float)61/255, (float)61/255, (float)61/255);
		gl.glBegin(gl.GL_POLYGON);
		while (theta <= pi2) {
			x = xcenter + radius * Math.sin(theta);
			y = ycenter + radius * Math.cos(theta);
			gl.glVertex2f((float) x, (float) y);
			theta += delta_theta;
		}
		;
		gl.glEnd();
		gl.glFlush();

	}

	public void drawCrankWheel(GL2 gl, double d, double e,
			double centerOfTipWidth, double centerOfTipHeight) {

		double pi2 = 6.28318530718;
		double radius = RADIUS_CRANK_WHEEL;
		double delta_theta = pi2 / 50;
		double xcenter = d, ycenter = e;
		double x, y;
		double theta = 0.0;
		
		POWER_LEFT_BOTTOM_WIDTH=centerOfTipWidth-HAMMER_WIDTH/2;
		POWER_LEFT_BOTTOM_HEIGHT=centerOfTipHeight+HAMMER_HEIGHT/2;
		
		POWER_RIGHT_BOTTOM_WIDTH=centerOfTipWidth+HAMMER_WIDTH/2;
		POWER_RIGHT_BOTTOM_HEIGHT=centerOfTipHeight+HAMMER_HEIGHT/2;
		
		
		//head
		
		gl.glColor3f((float)85/255, (float)95/255, (float)56/255);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(centerOfTipWidth-HAMMER_WIDTH/2), (float)(centerOfTipHeight+HAMMER_HEIGHT/2));
		gl.glVertex2f((float)(centerOfTipWidth+HAMMER_WIDTH/2), (float)(centerOfTipHeight+HAMMER_HEIGHT/2));
		gl.glVertex2f((float)(centerOfTipWidth+HAMMER_WIDTH/2), (float)(centerOfTipHeight-HAMMER_HEIGHT/2));
		gl.glVertex2f((float)(centerOfTipWidth-HAMMER_WIDTH/2), (float)(centerOfTipHeight-HAMMER_HEIGHT/2));
		gl.glEnd();
		gl.glFlush();
		
		
		// stick
		theta = 0.0;
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float) centerOfTipWidth, (float) centerOfTipHeight);
		while (theta <= (Math.PI)) {
			x = xcenter + radius * Math.sin(theta + Math.PI / 2);
			y = ycenter + radius * Math.cos(theta + Math.PI / 2);
			gl.glVertex2f((float) x, (float) y);
			theta += delta_theta;
		}
		;
		gl.glEnd();
		gl.glFlush();

		/* circle */
		theta=0.0;
		gl.glColor3i(153, 153, 153);
		gl.glBegin(gl.GL_POLYGON);
		while (theta <= pi2) {
			x = xcenter + radius * Math.sin(theta);
			y = ycenter + radius * Math.cos(theta);
			gl.glVertex2f((float) x, (float) y);
			theta += delta_theta;
		}
		;
		gl.glEnd();
		gl.glFlush();
		
		/* circle */
		theta=0.0;
		radius=RADIUS_TIP_WHEEL;
		gl.glColor3f(1.0f, 0.0f, 1.0f);
		gl.glBegin(gl.GL_POLYGON);
		while (theta <= pi2) {
			x = centerOfTipWidth + radius * Math.sin(theta);
			y = centerOfTipHeight + radius * Math.cos(theta);
			gl.glVertex2f((float) x, (float) y);
			theta += delta_theta;
		}
		;
		gl.glEnd();
		gl.glFlush();
		
	}
	

	
	public void drawInjector(GL2 gl, double startOfWidth, double endOfWidth, double startOfHeight,
			double endOfHeight, float r, float g, float b) {

		double width=endOfWidth-startOfWidth;
		double height=endOfHeight-startOfHeight;
		
		double tipHeight=height*(1/10);
		
		int smallGap=2;
		int bigGap=smallGap*2;

		
		double pi2 = Math.PI*2;
		double radius ;
		double delta_theta = pi2 / 50;
		double xcenter;
		double ycenter;
		double x, y;
		double theta = 0.0;
	
		if(FLAG_FUELCUT_STATE==STATE_FUELCUT_ON){

			/**
			 * alphabet o
			 */
			xcenter=endOfWidth+50;
			ycenter=startOfHeight+height*((double)3/4);
			
			radius=20;
			gl.glColor3f((float)255/255, (float)0/255, (float)0/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			radius=10;
			theta=0;
			gl.glColor3f((float)255/255, (float)255/255, (float)255/255);
			gl.glBegin(gl.GL_POLYGON);
			while (theta <= pi2) {
				x = xcenter + radius * Math.sin(theta);
				y = ycenter + radius * Math.cos(theta);
				gl.glVertex2f((float) x, (float) y);
				theta += delta_theta;
			}
			;
			gl.glEnd();
			gl.glFlush();
			
			
			gl.glColor3f(1f, 0f, 0f);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float)(endOfWidth+80), (float)(startOfHeight+height/2));
			gl.glVertex2f((float)(endOfWidth+80), (float)(startOfHeight+height/2+50));
			gl.glVertex2f((float)(endOfWidth+80+10), (float)(startOfHeight+height/2+50));
			gl.glVertex2f((float)(endOfWidth+80+10), (float)(startOfHeight+height/2));
			gl.glEnd();
			gl.glFlush();
			
			gl.glColor3f(1f, 0f, 0f);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float)(endOfWidth+80+10), (float)(startOfHeight+height/2+50));
			gl.glVertex2f((float)(endOfWidth+80+40), (float)(startOfHeight+height/2+20));
			gl.glVertex2f((float)(endOfWidth+80+40), (float)(startOfHeight+height/2));
			gl.glVertex2f((float)(endOfWidth+80+10), (float)(startOfHeight+height/2+50-20));
			gl.glEnd();
			gl.glFlush();
			
			gl.glColor3f(1f, 0f, 0f);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float)(endOfWidth+80+40), (float)(startOfHeight+height/2+50));
			gl.glVertex2f((float)(endOfWidth+80+10+40), (float)(startOfHeight+height/2+50));
			gl.glVertex2f((float)(endOfWidth+80+10+40), (float)(startOfHeight+height/2));
			gl.glVertex2f((float)(endOfWidth+80+40), (float)(startOfHeight+height/2));
			gl.glEnd();
			gl.glFlush();
			
		
			
		}else if(FLAG_FUELCUT_STATE==STATE_FUELCUT_OFF){
			
		}
		
		
		
		/**
		 * 
		 */
		gl.glColor3f(r, g, b);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/3)), (float)(endOfHeight));
		gl.glVertex2f((float)(startOfWidth+(width/3)*2), (float)(endOfHeight));
		gl.glVertex2f((float)(startOfWidth+(width/3)*2), (float)(endOfHeight-smallGap));
		gl.glVertex2f((float)(startOfWidth+(width/3)), (float)(endOfHeight-smallGap));
		gl.glEnd();
		gl.glFlush();
		
		gl.glColor3f(1f, 1f, 0f);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/5)*2), (float)(endOfHeight-smallGap));
		gl.glVertex2f((float)(startOfWidth+(width/5)*3), (float)(endOfHeight-smallGap));
		gl.glVertex2f((float)(startOfWidth+(width/5)*3), (float)(endOfHeight-smallGap*2));
		gl.glVertex2f((float)(startOfWidth+(width/5)*2), (float)(endOfHeight-smallGap*2));
		gl.glEnd();
		gl.glFlush();
		
		
		gl.glColor3f(r, g, b);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/3)), (float)(endOfHeight-smallGap*2));
		gl.glVertex2f((float)(startOfWidth+(width/3)*2), (float)(endOfHeight-smallGap*2));
		gl.glVertex2f((float)(startOfWidth+(width/3)*2), (float)(endOfHeight-smallGap*3));
		gl.glVertex2f((float)(startOfWidth+(width/3)), (float)(endOfHeight-smallGap*3));
		gl.glEnd();
		gl.glFlush();
		
		gl.glColor3f(1f, 1f, 0f);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/5)*2), (float)(endOfHeight-smallGap*3));
		gl.glVertex2f((float)(startOfWidth+(width/5)*3), (float)(endOfHeight-smallGap*3));
		gl.glVertex2f((float)(startOfWidth+(width/5)*3), (float)(endOfHeight-smallGap*4));
		gl.glVertex2f((float)(startOfWidth+(width/5)*2), (float)(endOfHeight-smallGap*4));
		gl.glEnd();
		gl.glFlush();
		
		gl.glColor3f(r, g, b);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/3)), (float)(endOfHeight-smallGap*4));
		gl.glVertex2f((float)(startOfWidth+(width/3)*2), (float)(endOfHeight-smallGap*4));
		gl.glVertex2f((float)(startOfWidth+(width/3)*2), (float)(endOfHeight-smallGap*5));
		gl.glVertex2f((float)(startOfWidth+(width/3)), (float)(endOfHeight-smallGap*5));
		gl.glEnd();
		gl.glFlush();
		
		gl.glColor3f(1f, 1f, 0f);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/5)*2), (float)(endOfHeight-smallGap*5));
		gl.glVertex2f((float)(startOfWidth+(width/5)*3), (float)(endOfHeight-smallGap*5));
		gl.glVertex2f((float)(startOfWidth+(width/5)*3), (float)(endOfHeight-smallGap*6));
		gl.glVertex2f((float)(startOfWidth+(width/5)*2), (float)(endOfHeight-smallGap*6));
		gl.glEnd();
		gl.glFlush();
		
		
		gl.glColor3f(r, g, b);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/3)), (float)(endOfHeight-smallGap*6));
		gl.glVertex2f((float)(startOfWidth+(width/3)*2), (float)(endOfHeight-smallGap*6));
		gl.glVertex2f((float)(startOfWidth+(width/3)*2), (float)(endOfHeight-smallGap*7));
		gl.glVertex2f((float)(startOfWidth+(width/3)), (float)(endOfHeight-smallGap*7));
		gl.glEnd();
		gl.glFlush();
		
		/**
		 * 
		 */
		
		gl.glColor3f(1f, 1f, 0f);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/5)*2), (float)(endOfHeight-smallGap*7));
		gl.glVertex2f((float)(startOfWidth+(width/5)*3), (float)(endOfHeight-smallGap*7));
		gl.glVertex2f((float)(startOfWidth+(width/5)*3), (float)(endOfHeight-smallGap*9));
		gl.glVertex2f((float)(startOfWidth+(width/5)*2), (float)(endOfHeight-smallGap*9));
		gl.glEnd();
		gl.glFlush();
		
		
		gl.glColor3f(r, g, b);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/7)*3), (float)(endOfHeight-smallGap*9));
		gl.glVertex2f((float)(startOfWidth+(width/7)*4), (float)(endOfHeight-smallGap*9));
		gl.glVertex2f((float)(startOfWidth+(width/7)*4), (float)(endOfHeight-smallGap*12));
		gl.glVertex2f((float)(startOfWidth+(width/7)*3), (float)(endOfHeight-smallGap*12));
		gl.glEnd();
		gl.glFlush();
		
		
		/**
		 * 
		 */
		
		double endOfRub=endOfHeight-(height/4);
		double startOfRub=endOfHeight-(height/4)-10;
		
		gl.glColor3f(0f, 0f, 0f);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth) , (float)(endOfRub));
		gl.glVertex2f((float)(endOfWidth), (float)(endOfRub));
		gl.glVertex2f((float)(endOfWidth), (float)(startOfRub));
		gl.glVertex2f((float)(startOfWidth), (float)(startOfRub));
		gl.glEnd();
		gl.glFlush();
		
		gl.glColor3f(r, g, b);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth) , (float)(startOfRub));
		gl.glVertex2f((float)(endOfWidth), (float)(startOfRub));
		gl.glVertex2f((float)(endOfWidth-smallGap), (float)(startOfRub-smallGap));
		gl.glVertex2f((float)(endOfWidth-smallGap*2), (float)(startOfRub-smallGap*2));
		gl.glVertex2f((float)(endOfWidth-smallGap*3), (float)(startOfRub-smallGap*3));
		gl.glVertex2f((float)(endOfWidth-smallGap*4), (float)(startOfRub-smallGap*4));
		gl.glVertex2f((float)(startOfWidth+smallGap*4), (float)(startOfRub-smallGap*4));
		gl.glVertex2f((float)(startOfWidth+smallGap*3), (float)(startOfRub-smallGap*3));
		gl.glVertex2f((float)(startOfWidth+smallGap*2), (float)(startOfRub-smallGap*2));
		gl.glVertex2f((float)(startOfWidth+smallGap*1), (float)(startOfRub-smallGap*1));
		gl.glVertex2f((float)(startOfWidth), (float)(startOfRub));
		gl.glEnd();
		gl.glFlush();
		
		
		/**
		 * middle valve
		 */
		double startOfHalf=startOfRub-(smallGap*4);
		
		gl.glColor3f(0f, 0f, 1f);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/7)*1) , (float)(startOfHalf));
		gl.glVertex2f((float)(startOfWidth+(width/7)*6), (float)(startOfHalf));
		gl.glVertex2f((float)(startOfWidth+(width/7)*6), (float)(startOfHalf-bigGap));
		gl.glVertex2f((float)(startOfWidth+(width/7)*1), (float)(startOfHalf-bigGap));
		gl.glEnd();
		gl.glFlush();
		
		
		gl.glColor3f(r, g, b);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/7)*2) , (float)(startOfHalf-bigGap));
		gl.glVertex2f((float)(startOfWidth+(width/7)*5), (float)(startOfHalf-bigGap));
		gl.glVertex2f((float)(startOfWidth+(width/7)*5), (float)(startOfHalf-bigGap*2));
		gl.glVertex2f((float)(startOfWidth+(width/7)*2), (float)(startOfHalf-bigGap*2));
		gl.glEnd();
		gl.glFlush();
		
		
		gl.glColor3f(0f, 0f, 1f);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/7)*1) , (float)(startOfHalf-bigGap*2));
		gl.glVertex2f((float)(startOfWidth+(width/7)*6), (float)(startOfHalf-bigGap*2));
		gl.glVertex2f((float)(startOfWidth+(width/7)*6), (float)(startOfHalf-bigGap*3));
		gl.glVertex2f((float)(startOfWidth+(width/7)*1), (float)(startOfHalf-bigGap*3));
		gl.glEnd();
		gl.glFlush();
		
		
		gl.glColor3f(r, g, b);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/7)*2) , (float)(startOfHalf-bigGap*3));
		gl.glVertex2f((float)(startOfWidth+(width/7)*5), (float)(startOfHalf-bigGap*3));
		gl.glVertex2f((float)(startOfWidth+(width/7)*5), (float)(startOfHalf-bigGap*4));
		gl.glVertex2f((float)(startOfWidth+(width/7)*2), (float)(startOfHalf-bigGap*4));
		gl.glEnd();
		gl.glFlush();
		
		
		gl.glColor3f(0f, 0f, 1f);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/7)*1) , (float)(startOfHalf-bigGap*4));
		gl.glVertex2f((float)(startOfWidth+(width/7)*6), (float)(startOfHalf-bigGap*4));
		gl.glVertex2f((float)(startOfWidth+(width/7)*6), (float)(startOfHalf-bigGap*5));
		gl.glVertex2f((float)(startOfWidth+(width/7)*1), (float)(startOfHalf-bigGap*5));
		gl.glEnd();
		gl.glFlush();
		
		
		/**
		 * tip
		 */
		
		double startOfValve=startOfHalf-bigGap*5;
		double endOfValve=startOfHeight+tipHeight;
		double gapMiddleOfValve=(startOfValve-endOfValve)/3;
		
		
		gl.glColor3f(r, g, b);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/7)*2) , (float)(startOfValve));
		gl.glVertex2f((float)(startOfWidth+(width/7)*5), (float)(startOfValve));
		gl.glVertex2f((float)(startOfWidth+(width/7)*5), (float)(startOfValve-gapMiddleOfValve));
		gl.glVertex2f((float)(startOfWidth+(width/7)*2), (float)(startOfValve-gapMiddleOfValve));
		gl.glEnd();
		gl.glFlush();
		
		gl.glColor3f(r, g, b);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/11)*4) , (float)(startOfValve-gapMiddleOfValve));
		gl.glVertex2f((float)(startOfWidth+(width/11)*7), (float)(startOfValve-gapMiddleOfValve));
		gl.glVertex2f((float)(startOfWidth+(width/11)*7), (float)(startOfValve-gapMiddleOfValve*2));
		gl.glVertex2f((float)(startOfWidth+(width/11)*4), (float)(startOfValve-gapMiddleOfValve*2));
		gl.glEnd();
		gl.glFlush();
		
		gl.glColor3f(r, g, b);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/7)*2) , (float)(startOfValve-gapMiddleOfValve*2));
		gl.glVertex2f((float)(startOfWidth+(width/7)*5), (float)(startOfValve-gapMiddleOfValve*2));
		gl.glVertex2f((float)(startOfWidth+(width/7)*5), (float)(endOfValve));
		gl.glVertex2f((float)(startOfWidth+(width/7)*2), (float)(endOfValve));
		gl.glEnd();
		gl.glFlush();
		
		/**
		 *  end
		 */
		gl.glColor3f(0f, 1f, 0f);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2f((float)(startOfWidth+(width/7)*3) , (float)(endOfValve));
		gl.glVertex2f((float)(startOfWidth+(width/7)*4), (float)(endOfValve));
		gl.glVertex2f((float)(startOfWidth+(width/7)*4), (float)(startOfHeight));
		gl.glVertex2f((float)(startOfWidth+(width/7)*3), (float)(startOfHeight));
		gl.glEnd();
		gl.glFlush();
		
	}

	public void drawExhaust(GL2 gl, double theta, double startOfWidth, double endOfWidth, double startOfHeight, double endOfHeight){
		
		double width=endOfWidth-startOfWidth;
		double height=endOfHeight-startOfHeight;
		
		
		if(((theta*2/Math.PI)%8 <= 5) && ((theta*2/Math.PI)%8 >= 3) ){
			
			FLAG_COK_2=1;
		}else{
			FLAG_COK_2=0;
		}
		
		if((FLAG_COK_2%2)>0){
			
			gl.glColor3f(0.0f, (float)240/255, (float)64/255);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float) (startOfWidth),
					(float) (EXHAUST_END_HEIGHT - ADJUST_VALUE*Math.sin(theta_cok_2-Math.PI/4)-8));
			gl.glVertex2f((float) (startOfWidth + width),
					(float) (startOfHeight - ADJUST_VALUE*Math.sin(theta_cok_2-Math.PI/4)));
			gl.glVertex2f((float) (startOfWidth + width),
					(float) (startOfHeight - ADJUST_VALUE*Math.sin(theta_cok_2-Math.PI/4)+20));
			gl.glVertex2f((float) (startOfWidth+20),
					(float) (EXHAUST_END_HEIGHT - ADJUST_VALUE*Math.sin(theta_cok_2-Math.PI/4)-8));
			gl.glEnd();
			gl.glFlush();
			

			//arrow 
			
			gl.glColor3f((float)122/255, (float)138/255, (float)117/255);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float) (endOfWidth+50*4),(float) (startOfHeight+ADJUST_VALUE+height/2 ));
			gl.glVertex2f((float) (endOfWidth+50*2),(float) (startOfHeight+ADJUST_VALUE+height/2 +40));
			gl.glVertex2f((float) (endOfWidth+50*2),(float) (startOfHeight+ADJUST_VALUE+height/2 +20));
			gl.glVertex2f((float) (endOfWidth+50),(float) (startOfHeight+ADJUST_VALUE+height/2 +20));
			
			gl.glVertex2f((float) (endOfWidth+50),(float) (startOfHeight+ADJUST_VALUE+height/2 -20));
			
			gl.glVertex2f((float) (endOfWidth+50*2),(float) (startOfHeight+ADJUST_VALUE+height/2-20 ));
			gl.glVertex2f((float) (endOfWidth+50*2),(float) (startOfHeight+ADJUST_VALUE+height/2-40 ));
			gl.glEnd();
			gl.glFlush();
		
		}else{
			gl.glColor3f(0.0f, (float)240/255, (float)64/255);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float) (startOfWidth), (float) (EXHAUST_END_HEIGHT+2 ));
			gl.glVertex2f((float) (startOfWidth + 20), (float) (EXHAUST_END_HEIGHT+2 ));
			gl.glVertex2f((float) (startOfWidth + width), (float) (startOfHeight+ADJUST_VALUE+20));
			gl.glVertex2f((float) (startOfWidth + width), (float) (startOfHeight+ADJUST_VALUE ));
			gl.glEnd();
			gl.glFlush();			
		}
	}
	
	public void drawIntakeEntrance(GL2 gl, double theta, double startOfWidth, double endOfWidth, double startOfHeight, double endOfHeight){
		
		double width=endOfWidth-startOfWidth;
		double height=endOfHeight-startOfHeight;
		
		
		double centerOfSpring_width=startOfWidth+width/2;
		double centerOfSpring_height=OPTION_START_HEIGHT+(OPTION_END_HEIGHT-OPTION_START_HEIGHT)/2;
		
		double pi2 = Math.PI*2;
		double radius = 10; 
		double delta_theta = pi2 / 50;
		double x, y;
		double theta_local = 0.0;

		/* circle. */
		
		if (((theta*2/Math.PI)%8 <= 7) && ((theta*2/Math.PI)%8 >= 5)) {
			
			FLAG_COK_1=1;
			FLAG_EX_TO_IN=0;
			
		}else{
			FLAG_COK_1=0;
		}
		
		if((FLAG_COK_1%2)>0){
			
			gl.glColor3f(0.0f, (float)240/255, (float)64/255);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float) (startOfWidth),
					(float) (startOfHeight - ADJUST_VALUE*Math.sin(theta_cok_1-Math.PI/4)));
			gl.glVertex2f((float) (startOfWidth + width),
					(float) (INTAKE_END_HEIGHT - ADJUST_VALUE*Math.sin(theta_cok_1-Math.PI/4)-8));
			gl.glVertex2f((float) (startOfWidth + width -20),
					(float) (INTAKE_END_HEIGHT - ADJUST_VALUE*Math.sin(theta_cok_1-Math.PI/4)-8));
			gl.glVertex2f((float) (startOfWidth),
					(float) (startOfHeight - ADJUST_VALUE*Math.sin(theta_cok_1-Math.PI/4) +20 ));
			gl.glEnd();
			gl.glFlush();
			
			//arrow
			
			gl.glColor3f((float)38/255, (float)217/255, (float)194/255);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float) (startOfWidth-50),(float) (startOfHeight+ADJUST_VALUE+height/2 ));
			gl.glVertex2f((float) (startOfWidth-50*2),(float) (startOfHeight+ADJUST_VALUE+height/2 +40));
			gl.glVertex2f((float) (startOfWidth-50*2),(float) (startOfHeight+ADJUST_VALUE+height/2 +20));
			gl.glVertex2f((float) (startOfWidth-50*4),(float) (startOfHeight+ADJUST_VALUE+height/2 +20));
			
			gl.glVertex2f((float) (startOfWidth-50*4),(float) (startOfHeight+ADJUST_VALUE+height/2 -20));
			
			gl.glVertex2f((float) (startOfWidth-50*2),(float) (startOfHeight+ADJUST_VALUE+height/2-20 ));
			gl.glVertex2f((float) (startOfWidth-50*2),(float) (startOfHeight+ADJUST_VALUE+height/2-40 ));
			gl.glEnd();
			gl.glFlush();
			
		}else{
			gl.glColor3f(0.0f, (float)240/255, (float)64/255);
			gl.glBegin(gl.GL_POLYGON);
			gl.glVertex2f((float) (startOfWidth),(float) (startOfHeight+ADJUST_VALUE ));
			gl.glVertex2f((float) (startOfWidth + width),(float) (INTAKE_END_HEIGHT +2));
			gl.glVertex2f((float) (startOfWidth + width-20),(float) (INTAKE_END_HEIGHT+2));
			gl.glVertex2f((float) (startOfWidth),(float) (startOfHeight+ADJUST_VALUE+20 ));
			gl.glEnd();
			gl.glFlush();
			
		}
		
	}
}
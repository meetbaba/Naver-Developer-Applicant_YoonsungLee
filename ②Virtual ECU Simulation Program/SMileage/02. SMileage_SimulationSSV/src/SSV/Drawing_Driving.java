package SSV;

import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;

public class Drawing_Driving {
	private static Drawing_Driving myObject = new Drawing_Driving();
	
	// Texture
	private static Texture textureCar, textureBackground, textureTest, textureTree;
	private static String textureCarFileName = "images/ferrari.png";
	private static String textureBackgroundFileName = "images/background.png";
	private static String textureTestFileName = "images/abc.png";
	private static String textureTreeFileName = "images/treetree.png";
	private static String textureFileType = ".png";
	
	// Texture image flips vertically. Shall use TextureCoords class to retrieve the
	// top, bottom, left and right coordinates.
	private static float textureCarTop, textureCarBottom, textureCarLeft, textureCarRight;
	private static float textureBackgroundTop, textureBackgroundBottom, textureBackgroundLeft, textureBackgroundRight;
	private static float textureTestTop, textureTestBottom, textureTestLeft, textureTestRight;
	private static float textureTreeTop, textureTreeBottom, textureTreeLeft, textureTreeRight;
	
	// Rotational angle about the x axes in degrees
	private static float slope = 0.0f;
	private static float angle = 0.0f;
	
	//private static float cameraX = 0.0f;
	//private static float cameraY = 0.0f;
	private static Resource resource;
	
	protected static void setup(GL2 gl2, int width, int height, Resource re) {
		resource = re;
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
		
		// Load texture from image
		try {
			// Create a OpenGL Texture object from (URL, mipmap, file suffix)
			// Use URL so that can read JAR and disk file.
			textureCar = TextureIO.newTexture(myObject.getClass().getClassLoader().getResource(textureCarFileName), false, textureFileType);
			textureBackground = TextureIO.newTexture(myObject.getClass().getClassLoader().getResource(textureBackgroundFileName), true, textureFileType);
			textureTest = TextureIO.newTexture(myObject.getClass().getClassLoader().getResource(textureTestFileName), true, textureFileType);
			textureTree = TextureIO.newTexture(myObject.getClass().getClassLoader().getResource(textureTreeFileName), true, textureFileType);
			
			// Use linear filter for texture if image is larger than the original texture
			gl2.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			gl2.glEnable(GL2.GL_BLEND);
			gl2.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
			
			// Texture image flips vertically. Shall use TextureCoords class to retrieve
			// the top, bottom, left and right coordinates, instead of using 0.0f and 1.0f
			TextureCoords textureCarCoords = textureCar.getImageTexCoords();
			textureCarTop = textureCarCoords.top();
			textureCarBottom = textureCarCoords.bottom();
			textureCarLeft = textureCarCoords.left();
			textureCarRight = textureCarCoords.right();

			TextureCoords textureBackgroundCoords = textureBackground.getImageTexCoords();
			textureBackgroundTop = textureBackgroundCoords.top();
			textureBackgroundBottom = textureBackgroundCoords.bottom();
			textureBackgroundLeft = textureBackgroundCoords.left();
			textureBackgroundRight = textureBackgroundCoords.right();
			
			TextureCoords textureTestCoords = textureTest.getImageTexCoords();
			textureTestTop = textureTestCoords.top();
			textureTestBottom = textureTestCoords.bottom();
			textureTestLeft = textureTestCoords.left();
			textureTestRight = textureTestCoords.right();
			
			TextureCoords textureTreeCoords = textureTree.getImageTexCoords();
			textureTreeTop = textureTreeCoords.top();
			textureTreeBottom = textureTreeCoords.bottom();
			textureTreeLeft = textureTreeCoords.left();
			textureTreeRight = textureTreeCoords.right();
			
		} catch(GLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		float aspect = (float)width / height;
		
		// Setup perspective projection, with aspect ratio matches viewport
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu.gluPerspective(45.0, aspect, 0.1, 100.0);	// fovy, aspect, zNear, zFar
		
		// Enable the model-view transform
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();
	}

	protected static void render(GL2 gl2, int width, int height) {
		gl2.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);	// clear color and depth buffers

		//int kX = (int) (cameraX / 1);
		//float kY = (float) cameraY / (float) Math.sin(slope);
		
		// Render a Background with texture
		gl2.glLoadIdentity();
		//gl2.glTranslatef((kX - cameraX), (2 * kX) * -kY + cameraY, -1.0f);	// translate into the screen
		//gl2.glTranslatef((kX - cameraX), 0.0f, -1.0f);	// translate into the screen
		gl2.glTranslatef(0.0f, -0.2f, -1.0f);	// translate into the screen
		gl2.glRotatef(slope, 0.0f, 0.0f, 1.0f);
		gl2.glScalef(0.5f, 0.01f, 0.15f);
		
		textureBackground.enable(gl2);
		textureBackground.bind(gl2);
		
		gl2.glBegin(GL2.GL_QUADS);
		
		// Front face
		gl2.glTexCoord2f(textureBackgroundLeft, textureBackgroundBottom);
		gl2.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl2.glTexCoord2f(textureBackgroundRight, textureBackgroundBottom);
		gl2.glVertex3f(1.0f, -1.0f, 1.0f);
		gl2.glTexCoord2f(textureBackgroundRight, textureBackgroundTop);
		gl2.glVertex3f(1.0f, 1.0f, 1.0f);
		gl2.glTexCoord2f(textureBackgroundLeft, textureBackgroundTop);
		gl2.glVertex3f(-1.0f, 1.0f, 1.0f);
		
		gl2.glEnd();
		
		// Render a Car with texture
		gl2.glLoadIdentity();
		gl2.glTranslatef((float) (0.522f/2.0f - 0.522f/2.0f * cos(slope)), (float) (-0.05f + 0.045 * sin(slope)), -1.0f);	// translate into the screen
		gl2.glRotatef(slope, 0.0f, 0.0f, 1.0f);
		gl2.glScalef(0.26f, 0.13f, 0.15f);
		
		// Enables this texture's target in the current GL context's state.
		textureCar.enable(gl2);
		textureCar.bind(gl2);
		
		gl2.glBegin(GL2.GL_QUADS);

		// Front face
		gl2.glTexCoord2f(textureCarLeft, textureCarBottom);
		gl2.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl2.glTexCoord2f(textureCarRight, textureCarBottom);
		gl2.glVertex3f(1.0f, -1.0f, 1.0f);
		gl2.glTexCoord2f(textureCarRight, textureCarTop);
		gl2.glVertex3f(1.0f, 1.0f, 1.0f);
		gl2.glTexCoord2f(textureCarLeft, textureCarTop);
		gl2.glVertex3f(-1.0f, 1.0f, 1.0f);
		
		gl2.glEnd();
		

		
		// Render a Test with texture
		gl2.glLoadIdentity();
		gl2.glTranslatef((float) (-0.17f + (0.17 - 0.17 * cos(slope))), (float) (-0.14f + -1 * (0.305f/2.0f * sin(slope))), -1.0f);	// translate into the screen
		gl2.glRotatef(angle, 0.0f, 0.0f, 1.0f);	// value 5 is constant of slope
		gl2.glScalef(.045f, .045f, 0.15f);
		
		textureTest.enable(gl2);
		textureTest.bind(gl2);
		
		gl2.glBegin(GL2.GL_QUADS);

		// Front face
		gl2.glTexCoord2f(textureTestLeft, textureTestBottom);
		gl2.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl2.glTexCoord2f(textureTestRight, textureTestBottom);
		gl2.glVertex3f(1.0f, -1.0f, 1.0f);
		gl2.glTexCoord2f(textureTestRight, textureTestTop);
		gl2.glVertex3f(1.0f, 1.0f, 1.0f);
		gl2.glTexCoord2f(textureTestLeft, textureTestTop);
		gl2.glVertex3f(-1.0f, 1.0f, 1.0f);
		
		gl2.glEnd();
		

		// Render a Test with texture
		gl2.glLoadIdentity();
		gl2.glTranslatef((float) (0.135f - (0.135f - 0.135f * cos(slope))), (float) (-0.14f + (0.305f/2.0f * sin(slope))), -1.0f);	// translate into the screen
		gl2.glRotatef(angle, 0.0f, 0.0f, 1.0f);	// value 5 is constant of slope
		gl2.glScalef(.045f, .045f, 0.15f);
		
		textureTest.enable(gl2);
		textureTest.bind(gl2);
		
		gl2.glBegin(GL2.GL_QUADS);

		// Front face
		gl2.glTexCoord2f(textureTestLeft, textureTestBottom);
		gl2.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl2.glTexCoord2f(textureTestRight, textureTestBottom);
		gl2.glVertex3f(1.0f, -1.0f, 1.0f);
		gl2.glTexCoord2f(textureTestRight, textureTestTop);
		gl2.glVertex3f(1.0f, 1.0f, 1.0f);
		gl2.glTexCoord2f(textureTestLeft, textureTestTop);
		gl2.glVertex3f(-1.0f, 1.0f, 1.0f);
		
		gl2.glEnd();
	}

	protected static void update() {
		//slope = -5;
		
		slope = resource.getSlopeState() * (-1);
		//angle-=10;
		angle -= resource.getVelocity();
		//cameraX += 0.01f * Math.cos(slope);		// 0.005f => Velocity
		//cameraY += 0.0001f;
		//cameraY += 0.01f * Math.sin(slope);
	}
	
	public static float sin(float s) {
		return (float) Math.sin(Math.toRadians(s));
	}
	
	public static float cos(float s) {
		return (float) Math.cos(Math.toRadians(s));
	}
}

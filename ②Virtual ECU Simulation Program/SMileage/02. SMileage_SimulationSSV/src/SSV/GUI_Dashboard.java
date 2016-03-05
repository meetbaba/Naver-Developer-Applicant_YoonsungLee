package SSV;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
 
class GUI_Dashboard extends JPanel {
 
    final float degrees06 = (float) Math.toRadians(6);
    final float degrees30 = degrees06 * 5;
    final float degrees90 = degrees30 * 3;
 
    final int size = 340;
    final int spacing = 30;
    final int diameter = size - 2 * spacing;
    final int x = 220;
    final int y = 125;
    
    final int gear=1;
    final int brake=1;
    BufferedImage img=null;
    static Resource resource=null;
    
    public GUI_Dashboard(Resource re) {

    	resource = re;
       
        setPreferredSize(new Dimension(700,270));
        try {
         img = ImageIO.read(new File("gage.jpg"));
      } catch (IOException e1) {
         e1.printStackTrace();
      }
 
        new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        }).start();
    }
    

    
    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(img, 0, 0, null);

        Calendar date = Calendar.getInstance();
        float temperRAW =0;
        float rpmRAW=(float)(39.3f+5.2/1000*resource.getRpm());
        float vecRAW=(float)(39.3f+40.0/220*resource.getVelocity());
        float temper=resource.getCoolantState();
        int gear=resource.getGearState();
        int brake=resource.getBrakeState();
        if(temper<95)
        	temperRAW = 22.5f-(float)(12.5/95*temper);
        else
        	temperRAW = 9f-(float)(1.5/15*(temper-95));
 
        float rpmAngle = degrees90 - (degrees06 * rpmRAW);
        float vecAngle = degrees90 - (degrees06 * vecRAW);
        float temperAngle =degrees90 - (degrees06 * temperRAW);
        
        g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, 0));
        drawHand(g, rpmAngle, diameter / 2 - 30, Color.red,0);
        drawHand(g, vecAngle, diameter / 2 - 30, Color.red,276);
        drawHand(g, temperAngle, diameter / 3 - 40, Color.red,-210);
        drawGear(g,gear);
        drawFuelCutState(g,resource.getFuelCutState());
        drawBrake(g,brake);

    }
 
    private void drawHand(Graphics2D g, float angle, int radius, Color color, int dis) {
    	
        int x2 = x + (int) (radius * Math.cos(angle));
        int y2 = y + (int) (radius * Math.sin(-angle)); // flip y-axis
        g.setColor(color);
        g.drawLine(x+dis, y, x2+dis, y2);     
        
    }
    ///기어제어
    private void drawGear(Graphics2D g,int gear){
       String gearC="";
       if(gear==1){
          gearC="P";
       }else if(gear==2){
          gearC="R";
       }else if(gear==3){
          gearC="N";
       }else if(gear==4){
          gearC="D";
       }
       
        g.setFont(g.getFont().deriveFont(Font.BOLD, 30f));
        g.drawString(gearC,70,230);
       //g.drawS
    }
    
    private void drawFuelCutState(Graphics2D g,int state){
		if (state == 0) {
			g.setFont(g.getFont().deriveFont(Font.ITALIC, 20f));
			g.drawString("", 455, 210);
		} 
		else {
			g.setFont(g.getFont().deriveFont(Font.ITALIC, 20f));
			g.drawString("Fuel Cut", 455, 210);
		}
    }
    
    private void drawBrake(Graphics g, int brake){
       if(brake>0){
       
          g.setFont(g.getFont().deriveFont(Font.BOLD, 15f));
              g.drawString("B",635,230);
              
              g.setColor (Color.red);
              g.fillRect (650, 200, 35, 35);
        
       }
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              
                JFrame f = new JFrame();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setTitle("Clock");
                f.setResizable(false);
                
                f.add(new GUI_Dashboard(resource), BorderLayout.CENTER);
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });
    }
}
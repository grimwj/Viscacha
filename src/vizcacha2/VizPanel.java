 package vizcacha2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class VizPanel extends JPanel 
{
    public static Semaphore semaphore = new Semaphore(1, true);
    
    public int RefreshRate = 150;
    
    public int screen_width;
    public int screen_height;
    
    String ConnectionStatus = "USB feeder not connected...";
    
    boolean AnimationThread_isActive = false;
    VizPanel_Updater AnimationThread;
    
    Color ScreenChangeColor;
    Color Background_Color;
    
    // 0 - start, 1 - trial, 2 - screen change, 3 - black screen untill end
    int screen_type=1;

    // Panel parameters
    String LeftPanelBackgroundType="0";
    String RightPanelBackgroundType="0";
    String LeftPanelShapeType="0";
    String RightPanelShapeType="0";
    String LeftPanelNoiseType="0";
    String RightPanelNoiseType="0";
    
    int LeftPanelBackgroundColor;
    int RightPanelBackgroundColor;
    
    Dots LeftBackgroundDots;
    Dots RightBackgroundDots;
    Dots LeftShapeDots;
    Dots RightShapeDots;
    Dots LeftNoiseDots;
    Dots RightNoiseDots;
    
    SineAnimation LeftPanelBackgroundSineAnimation;
    SineAnimation RightPanelBackgroundSineAnimation;
    SineAnimation LeftPanelShapeSineAnimation;
    SineAnimation RightPanelShapeSineAnimation;
    SineAnimation LeftPanelNoiseSineAnimation;
    SineAnimation RightPanelNoiseSineAnimation;
    
    RectAnimation LeftPanelBackgroundRectAnimation;
    RectAnimation RightPanelBackgroundRectAnimation;
    RectAnimation LeftPanelShapeRectAnimation;
    RectAnimation RightPanelShapeRectAnimation;
    RectAnimation LeftPanelNoiseRectAnimation;
    RectAnimation RightPanelNoiseRectAnimation;
    
    int LeftPanelBackgroundDotSize;
    int LeftPanelBackgroundSpeed; 
    int LeftPanelBackgroundDirection;
    double LeftPanelBackgroundCoherence=1;
    int LeftPanelBackgroundSpeedX;
    int LeftPanelBackgroundSpeedY;
    int LeftPanelBackgroundMaxNumber;
    int LeftPanelBackgroundDotColor;
    int LeftPanelBackgroundDotColor2;
    double LeftPanelBackgroundDotColorThreshold;
    double LeftPanelBackgroundLifeTimeS;
    int LeftPanelBackgroundDashedLength;
    int LeftPanelBackgroundDashedSpacing;
    
    double LeftPanelBackgroundMedium_Value;
    double LeftPanelBackgroundAmplitude;    
    int LeftPanelBackgroundWidth;
    double LeftPanelBackgroundDuty;
    int LeftPanelBackgroundMask;

    int RightPanelBackgroundDotSize;
    int RightPanelBackgroundSpeed; 
    int RightPanelBackgroundDirection;
    double RightPanelBackgroundCoherence=1;
    int RightPanelBackgroundSpeedX;
    int RightPanelBackgroundSpeedY;
    int RightPanelBackgroundMaxNumber;
    int RightPanelBackgroundDotColor;
    int RightPanelBackgroundDotColor2;
    double RightPanelBackgroundDotColorThreshold;
    double RightPanelBackgroundLifeTimeS;
    int RightPanelBackgroundDashedLength;
    int RightPanelBackgroundDashedSpacing;
    
    double RightPanelBackgroundMedium_Value;
    double RightPanelBackgroundAmplitude;    
    int RightPanelBackgroundWidth;
    double RightPanelBackgroundDuty;
    int RightPanelBackgroundMask;    
    
    int LeftPanelShapeBackgroundColor;
    int RightPanelShapeBackgroundColor;
    
    double LeftPanelShapeScale;
    double RightPanelShapeScale;

    double LeftPanelShapeFieldScale;
    double RightPanelShapeFieldScale;

    int LeftPanelShapeHorizontalOffset;
    int RightPanelShapeHorizontalOffset;
    
    double LeftPanelShapeMedium_Value;
    double LeftPanelShapeAmplitude;
    int LeftPanelShapeSpeed;
    int LeftPanelShapeDirection;
    int LeftPanelShapeWidth;
    double LeftPanelShapeDuty;
    int LeftPanelShapeMask;
    
    int LeftPanelShapeEllipseX;
    int LeftPanelShapeEllipseY;
    int LeftPanelShapeDotSize;
    double LeftPanelShapeCoherence=1;
    int LeftPanelShapeSpeedX;
    int LeftPanelShapeSpeedY;
    int LeftPanelShapeMaxNumber;
    int LeftPanelShapeDotColor;
    int LeftPanelShapeDotColor2;
    double LeftPanelShapeDotColorThreshold;
    double LeftPanelShapeLifeTimeS;
    int LeftPanelShapeDashedLength;
    int LeftPanelShapeDashedSpacing;

    double RightPanelShapeMedium_Value;
    double RightPanelShapeAmplitude;
    int RightPanelShapeSpeed;
    int RightPanelShapeDirection;
    int RightPanelShapeWidth;
    double RightPanelShapeDuty;
    int RightPanelShapeMask;
    
    int RightPanelShapeEllipseX;
    int RightPanelShapeEllipseY;
    int RightPanelShapeDotSize;
    double RightPanelShapeCoherence=1;
    int RightPanelShapeSpeedX;
    int RightPanelShapeSpeedY;
    int RightPanelShapeMaxNumber;
    int RightPanelShapeDotColor;
    int RightPanelShapeDotColor2;
    double RightPanelShapeDotColorThreshold;
    double RightPanelShapeLifeTimeS;
    int RightPanelShapeDashedLength;
    int RightPanelShapeDashedSpacing;
    
    
    int LeftPanelNoiseDotSize;
    int LeftPanelNoiseSpeed; 
    int LeftPanelNoiseDirection;
    double LeftPanelNoiseCoherence=1;
    int LeftPanelNoiseSpeedX;
    int LeftPanelNoiseSpeedY;
    int LeftPanelNoiseMaxNumber;
    int LeftPanelNoiseDotColor;
    int LeftPanelNoiseDotColor2;
    double LeftPanelNoiseDotColorThreshold;
    double LeftPanelNoiseLifeTimeS;
    int LeftPanelNoiseDashedLength;
    int LeftPanelNoiseDashedSpacing;
    
    int LeftPanelNoiseEllipseX = 0;
    int LeftPanelNoiseEllipseY = 0;
    double LeftPanelNoiseMedium_Value;
    double LeftPanelNoiseAmplitude;    
    int LeftPanelNoiseWidth;
    double LeftPanelNoiseDuty;
    int LeftPanelNoiseMask; 
    
    int RightPanelNoiseDotSize;
    int RightPanelNoiseSpeed; 
    int RightPanelNoiseDirection;
    double RightPanelNoiseCoherence=1;
    int RightPanelNoiseSpeedX;
    int RightPanelNoiseSpeedY;
    int RightPanelNoiseMaxNumber;
    int RightPanelNoiseDotColor;
    int RightPanelNoiseDotColor2;
    double RightPanelNoiseDotColorThreshold;
    double RightPanelNoiseLifeTimeS;
    
    int RightPanelNoiseEllipseX = 0;
    int RightPanelNoiseEllipseY = 0;
    double RightPanelNoiseMedium_Value;
    double RightPanelNoiseAmplitude;    
    int RightPanelNoiseWidth;
    double RightPanelNoiseDuty;
    int RightPanelNoiseMask;
    int RightPanelNoiseDashedLength;
    int RightPanelNoiseDashedSpacing;
    
    // parameter values
    double param_value;
    double positive_value;
    
    public void SetConnectionStatus(String status)
    {
        ConnectionStatus = status;
        this.revalidate();
        this.repaint();
    }
    
    
    public VizPanel()
    {
        screen_type=3;
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screen_width = (int) screenSize.getWidth();
        screen_height = (int) screenSize.getHeight();
        
        this.setSize(screen_width, screen_height);
        
        this.setBackground(Background_Color);
        this.setVisible(true);
    }
    
       
    @Override
    public void paintComponent(Graphics g) 
    {               
        long start_time = System.nanoTime();
        
        super.paintComponent(g);

        if (screen_type==0)
        {
            g.setColor(ScreenChangeColor);
            g.fillRect(0, 0, screen_width, screen_height);
            PaintFocusPoint(g);
            
            Font font = new Font ("Courier New", 1, 17);
            g.setFont(font);
            g.setColor(Color.WHITE);
            
            int str_width, str_height, str_xloc, str_yloc;
            
            String start_str = "Press S to begin the experiment...";
            str_width = g.getFontMetrics().stringWidth(start_str);
            str_height = g.getFontMetrics().getHeight();
            str_xloc = (screen_width - str_width)/2;
            str_yloc = 4*screen_height/5;
            g.drawString(start_str, str_xloc, str_yloc);
            
            String connection_str = ConnectionStatus;
            str_width = g.getFontMetrics().stringWidth(connection_str);
            str_xloc = (screen_width - str_width)/2;
            str_yloc += 2*str_height ;
            g.drawString(connection_str, str_xloc, str_yloc);
            
            font = new Font ("Courier New", 1, 13);
            g.setFont(font);
            g.setColor(Color.WHITE);
            
            String status_str = "Files left: ";
            status_str = status_str + ((int) (Vizcacha2.config_reader.sweep_files));
            status_str = status_str + ", Experiment:";
            status_str = status_str + (Vizcacha2.trials.ex_nbr+1);
            status_str = status_str + "/";
            status_str = status_str + Vizcacha2.reader.Experiment_Repeat;
            str_width = g.getFontMetrics().stringWidth(status_str);
            str_height = g.getFontMetrics().getHeight();
            str_xloc = screen_width - str_width;
            str_yloc = screen_height - str_height;
            g.drawString(status_str, str_xloc, str_yloc);
        }
        
        if (screen_type==1)
        {
            PaintComponent_LeftSide(g);
            PaintComponent_RightSide(g);
            PaintFocusPoint(g);
        }
        
        if (screen_type==2)
        {
            g.setColor(ScreenChangeColor);
            g.fillRect(0, 0, screen_width, screen_height);
            PaintFocusPoint(g);
        }
        if (screen_type==3)
        {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, screen_width, screen_height);
        }
        
        //long end_time=System.nanoTime();
        //long duration_ms=(end_time-start_time)/1000000;
        //System.out.println("Repaint duration:" + duration_ms);
        
        VizPanel.semaphore.release();
    }
    
    public void PaintBlackScreenUntilEnd()
    {
        screen_type=2;
        
        while(true)
        {
            try 
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException ex) 
            {
                Logger.getLogger(VizPanel_Updater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void New_Trial_Panel(int positive_one, double current_staircase_factor, double current_constant_factor, String experiment_type)
    {
        VizPanel_StopAnimation();
        
        System.out.println("New trial panel");

        if (positive_one==1)
        {
            PaintComponent_PreparePositive("left");
            PaintComponent_PrepareNegative("right", current_staircase_factor, current_constant_factor, experiment_type);
        }
        else
        {
            PaintComponent_PreparePositive("right");
            PaintComponent_PrepareNegative("left", current_staircase_factor, current_constant_factor, experiment_type);
        }
        
        int x0 = 0;
        int y0 = 0;
        int x1 = screen_width/2 - 2*LeftPanelBackgroundDotSize;
        int y1 = screen_height;
        
        Color color, color2;
        
        if (LeftPanelBackgroundType.equals("1"))
        {
            color = new Color(LeftPanelBackgroundDotColor, LeftPanelBackgroundDotColor, LeftPanelBackgroundDotColor);
            color2 = new Color(LeftPanelBackgroundDotColor2, LeftPanelBackgroundDotColor2, LeftPanelBackgroundDotColor2);
            if (LeftPanelBackgroundSpeed!=0)
            {
                LeftPanelBackgroundSpeedX = (int) (LeftPanelBackgroundSpeed*Math.cos(Math.toRadians(LeftPanelBackgroundDirection)));
                LeftPanelBackgroundSpeedY = (int) (LeftPanelBackgroundSpeed*Math.sin(Math.toRadians(LeftPanelBackgroundDirection)));
            }
            
            LeftBackgroundDots = new Dots(x0, x1, y0, y1, LeftPanelBackgroundDotSize, LeftPanelBackgroundSpeedX, LeftPanelBackgroundSpeedY, LeftPanelBackgroundMaxNumber, color, color2, LeftPanelBackgroundDotColorThreshold, (int) (1000*LeftPanelBackgroundLifeTimeS), 1, LeftPanelBackgroundCoherence);
            LeftBackgroundDots.Dots_Synchro();            
        }
        if (LeftPanelShapeType.equals("1"))
        {
            color = new Color(LeftPanelShapeDotColor, LeftPanelShapeDotColor, LeftPanelShapeDotColor);
            color2 = new Color(LeftPanelShapeDotColor2, LeftPanelShapeDotColor2, LeftPanelShapeDotColor2);
            if (LeftPanelShapeSpeed!=0)
            {
                LeftPanelShapeSpeedX = (int) (LeftPanelShapeSpeed*Math.cos(Math.toRadians(LeftPanelShapeDirection)));
                LeftPanelShapeSpeedY = (int) (LeftPanelShapeSpeed*Math.sin(Math.toRadians(LeftPanelShapeDirection)));
            }
            LeftShapeDots = new Dots(x0, x1, y0, y1, LeftPanelShapeDotSize, LeftPanelShapeSpeedX, LeftPanelShapeSpeedY, LeftPanelShapeMaxNumber, color, color2, LeftPanelShapeDotColorThreshold, (int) (1000*LeftPanelShapeLifeTimeS), 1, LeftPanelShapeCoherence);
            LeftShapeDots.Dots_Synchro();
        }
        if (LeftPanelNoiseType.equals("1"))
        {
            color = new Color(LeftPanelNoiseDotColor, LeftPanelNoiseDotColor, LeftPanelNoiseDotColor);
            color2 = new Color(LeftPanelNoiseDotColor2, LeftPanelNoiseDotColor2, LeftPanelNoiseDotColor2);
            if (LeftPanelNoiseSpeed!=0)
            {
                LeftPanelNoiseSpeedX = (int) (LeftPanelNoiseSpeed*Math.cos(Math.toRadians(LeftPanelNoiseDirection)));
                LeftPanelNoiseSpeedY = (int) (LeftPanelNoiseSpeed*Math.sin(Math.toRadians(LeftPanelNoiseDirection)));
            }            
            LeftNoiseDots = new Dots(x0, x1, y0, y1, LeftPanelNoiseDotSize, LeftPanelNoiseSpeedX, LeftPanelNoiseSpeedY, LeftPanelNoiseMaxNumber, color, color2, LeftPanelNoiseDotColorThreshold, (int) (1000*LeftPanelNoiseLifeTimeS), 0, LeftPanelNoiseCoherence);
            LeftNoiseDots.Dots_Synchro();
        }
        if (LeftPanelBackgroundType.equals("2"))
        {
            LeftPanelBackgroundSineAnimation = new SineAnimation(screen_width/2, screen_height, RightPanelBackgroundMedium_Value, RightPanelBackgroundAmplitude, RightPanelBackgroundSpeed, RightPanelBackgroundDirection, RightPanelBackgroundWidth, RightPanelBackgroundMask);
        }
        if (LeftPanelBackgroundType.equals("3"))
        {
            LeftPanelBackgroundRectAnimation = new RectAnimation(screen_width/2, screen_height, RightPanelBackgroundMedium_Value, RightPanelBackgroundAmplitude, RightPanelBackgroundSpeed, RightPanelBackgroundDirection, RightPanelBackgroundWidth, RightPanelBackgroundDuty, RightPanelBackgroundMask, RightPanelBackgroundDashedLength, RightPanelBackgroundDashedSpacing);
        }
        if (LeftPanelShapeType.equals("2"))
        {
            LeftPanelShapeSineAnimation = new SineAnimation(LeftPanelShapeEllipseX, LeftPanelShapeEllipseY, RightPanelShapeMedium_Value, RightPanelShapeAmplitude, RightPanelShapeSpeed, RightPanelShapeDirection, RightPanelShapeWidth, RightPanelShapeMask);
        }
        if (LeftPanelShapeType.equals("3"))
        {
            LeftPanelShapeRectAnimation = new RectAnimation(LeftPanelShapeEllipseX, LeftPanelShapeEllipseY, RightPanelShapeMedium_Value, RightPanelShapeAmplitude, RightPanelShapeSpeed, RightPanelShapeDirection, RightPanelShapeWidth, RightPanelShapeDuty, RightPanelShapeMask, RightPanelShapeDashedLength, RightPanelShapeDashedSpacing);
        }        
        if (LeftPanelNoiseType.equals("2"))
        {
            LeftPanelNoiseSineAnimation = new SineAnimation(screen_width/2, screen_height, LeftPanelNoiseMedium_Value, LeftPanelNoiseAmplitude, LeftPanelNoiseSpeed, LeftPanelNoiseDirection, LeftPanelNoiseWidth, LeftPanelNoiseMask);
        }
        if (LeftPanelNoiseType.equals("3"))
        {
            LeftPanelNoiseRectAnimation = new RectAnimation(screen_width/2, screen_height, LeftPanelNoiseMedium_Value, LeftPanelNoiseAmplitude, LeftPanelNoiseSpeed, LeftPanelNoiseDirection, LeftPanelNoiseWidth, LeftPanelNoiseDuty, LeftPanelNoiseMask, LeftPanelNoiseDashedLength, LeftPanelNoiseDashedSpacing);
        }
        
        System.out.println("/////////////////////////////");
        System.out.println(LeftPanelBackgroundDashedLength + " " + LeftPanelBackgroundDashedSpacing);
        System.out.println("/////////////////////////////");
        
        x0 = screen_width/2 + 2*LeftPanelBackgroundDotSize;
        y0 = 0;
        x1 = screen_width;
        y1 = screen_height;

        if (RightPanelBackgroundType.equals("1"))
        {
            color = new Color(RightPanelBackgroundDotColor, RightPanelBackgroundDotColor, RightPanelBackgroundDotColor);
            color2 = new Color(RightPanelBackgroundDotColor2, RightPanelBackgroundDotColor2, RightPanelBackgroundDotColor2);
            if (RightPanelBackgroundSpeed!=0)
            {
                RightPanelBackgroundSpeedX = (int) (RightPanelBackgroundSpeed*Math.cos(Math.toRadians(RightPanelBackgroundDirection)));
                RightPanelBackgroundSpeedY = (int) (RightPanelBackgroundSpeed*Math.sin(Math.toRadians(RightPanelBackgroundDirection)));
            }
            
            RightBackgroundDots = new Dots(x0, x1, y0, y1, RightPanelBackgroundDotSize, RightPanelBackgroundSpeedX, RightPanelBackgroundSpeedY, RightPanelBackgroundMaxNumber, color, color2, RightPanelBackgroundDotColorThreshold, (int) (1000*RightPanelBackgroundLifeTimeS), 1, RightPanelBackgroundCoherence);
            RightBackgroundDots.Dots_Synchro();            
        }
        if (RightPanelShapeType.equals("1"))
        {
            color = new Color(RightPanelShapeDotColor, RightPanelShapeDotColor, RightPanelShapeDotColor);
            color2 = new Color(RightPanelShapeDotColor2, RightPanelShapeDotColor2, RightPanelShapeDotColor2);
            if (RightPanelShapeSpeed!=0)
            {
                RightPanelShapeSpeedX = (int) (RightPanelShapeSpeed*Math.cos(Math.toRadians(RightPanelShapeDirection)));
                RightPanelShapeSpeedY = (int) (RightPanelShapeSpeed*Math.sin(Math.toRadians(RightPanelShapeDirection)));
            }
            RightShapeDots = new Dots(x0, x1, y0, y1, RightPanelShapeDotSize, RightPanelShapeSpeedX, RightPanelShapeSpeedY, RightPanelShapeMaxNumber, color, color2, RightPanelShapeDotColorThreshold, (int) (1000*RightPanelShapeLifeTimeS), 1, RightPanelShapeCoherence);
            RightShapeDots.Dots_Synchro();
        }
        if (RightPanelNoiseType.equals("1"))
        {
            color = new Color(RightPanelNoiseDotColor, RightPanelNoiseDotColor, RightPanelNoiseDotColor);
            color2 = new Color(RightPanelNoiseDotColor2, RightPanelNoiseDotColor2, RightPanelNoiseDotColor2);
            if (RightPanelNoiseSpeed!=0)
            {
                RightPanelNoiseSpeedX = (int) (RightPanelNoiseSpeed*Math.cos(Math.toRadians(RightPanelNoiseDirection)));
                RightPanelNoiseSpeedY = (int) (RightPanelNoiseSpeed*Math.sin(Math.toRadians(RightPanelNoiseDirection)));
            }            
            RightNoiseDots = new Dots(x0, x1, y0, y1, RightPanelNoiseDotSize, RightPanelNoiseSpeedX, RightPanelNoiseSpeedY, RightPanelNoiseMaxNumber, color, color2, RightPanelNoiseDotColorThreshold, (int) (1000*RightPanelNoiseLifeTimeS), 0, RightPanelNoiseCoherence);
            RightNoiseDots.Dots_Synchro();
        }
        if (RightPanelBackgroundType.equals("2"))
        {
            RightPanelBackgroundSineAnimation = new SineAnimation(screen_width/2, screen_height, RightPanelBackgroundMedium_Value, RightPanelBackgroundAmplitude, RightPanelBackgroundSpeed, RightPanelBackgroundDirection, RightPanelBackgroundWidth, RightPanelBackgroundMask);
        }
        if (RightPanelBackgroundType.equals("3"))
        {
            RightPanelBackgroundRectAnimation = new RectAnimation(screen_width/2, screen_height, RightPanelBackgroundMedium_Value, RightPanelBackgroundAmplitude, RightPanelBackgroundSpeed, RightPanelBackgroundDirection, RightPanelBackgroundWidth, RightPanelBackgroundDuty, RightPanelBackgroundMask, RightPanelBackgroundDashedLength, RightPanelBackgroundDashedSpacing);
        }
        if (RightPanelShapeType.equals("2"))
        {
            RightPanelShapeSineAnimation = new SineAnimation(RightPanelShapeEllipseX, RightPanelShapeEllipseY, RightPanelShapeMedium_Value, RightPanelShapeAmplitude, RightPanelShapeSpeed, RightPanelShapeDirection, RightPanelShapeWidth, RightPanelShapeMask);
        }
        if (RightPanelShapeType.equals("3"))
        {
            RightPanelShapeRectAnimation = new RectAnimation(RightPanelShapeEllipseX, RightPanelShapeEllipseY, RightPanelShapeMedium_Value, RightPanelShapeAmplitude, RightPanelShapeSpeed, RightPanelShapeDirection, RightPanelShapeWidth, RightPanelShapeDuty, RightPanelShapeMask, RightPanelShapeDashedLength, RightPanelShapeDashedSpacing);
        }        
        if (RightPanelNoiseType.equals("2"))
        {
            RightPanelNoiseSineAnimation = new SineAnimation(screen_width/2, screen_height, RightPanelNoiseMedium_Value, RightPanelNoiseAmplitude, RightPanelNoiseSpeed, RightPanelNoiseDirection, RightPanelNoiseWidth, RightPanelNoiseMask);
        }
        if (RightPanelNoiseType.equals("3"))
        {
            RightPanelNoiseRectAnimation = new RectAnimation(screen_width/2, screen_height, RightPanelNoiseMedium_Value, RightPanelNoiseAmplitude, RightPanelNoiseSpeed, RightPanelNoiseDirection, RightPanelNoiseWidth, RightPanelNoiseDuty, RightPanelNoiseMask, RightPanelNoiseDashedLength, RightPanelNoiseDashedSpacing);
        }
        
        VizPanel_StartAnimation();
    }
    
    private void PaintComponent_PreparePositive(String side)
    {
        if (side.equals("left"))
        {
            LeftPanelBackgroundColor = Vizcacha2.reader.Background;
            LeftPanelBackgroundType = Vizcacha2.reader.Positive_Background_Type;
              
            LeftPanelBackgroundDotSize = (int) Vizcacha2.reader.Positive_Background_Dot_Size;
            LeftPanelBackgroundSpeed = (int) Vizcacha2.reader.Positive_Background_Speed;
            LeftPanelBackgroundDirection = (int) Vizcacha2.reader.Positive_Background_Direction;
            LeftPanelBackgroundCoherence = Vizcacha2.reader.Positive_Background_Coherence;
            LeftPanelBackgroundSpeedX = (int) Vizcacha2.reader.Positive_Background_Speed_X;
            LeftPanelBackgroundSpeedY = (int) Vizcacha2.reader.Positive_Background_Speed_Y;
            LeftPanelBackgroundMaxNumber = (int) Vizcacha2.reader.Positive_Background_Dot_Max_Count;
            LeftPanelBackgroundDotColor = (int) Vizcacha2.reader.Positive_Background_Dot_Color;
            LeftPanelBackgroundDotColor2 = (int) Vizcacha2.reader.Positive_Background_Dot_Color_2;
            LeftPanelBackgroundDotColorThreshold = Vizcacha2.reader.Positive_Background_Dot_Color_Threshold;
            LeftPanelBackgroundLifeTimeS = Vizcacha2.reader.Positive_Background_Life_Time;

            LeftPanelBackgroundMedium_Value = Vizcacha2.reader.Positive_Background_Medium_Value;
            LeftPanelBackgroundAmplitude = Vizcacha2.reader.Positive_Background_Amplitude;
            LeftPanelBackgroundWidth = Vizcacha2.reader.Positive_Background_Width;
            LeftPanelBackgroundDuty = Vizcacha2.reader.Positive_Background_Duty;
            LeftPanelBackgroundMask = Vizcacha2.reader.Positive_Background_Mask;
            LeftPanelBackgroundDashedLength = Vizcacha2.reader.Positive_Background_Dashed_Length;
            LeftPanelBackgroundDashedSpacing = Vizcacha2.reader.Positive_Background_Dashed_Spacing;            
            
            LeftPanelShapeBackgroundColor = Vizcacha2.reader.Positive_Shape_Background_Color;
            
            LeftPanelShapeType = Vizcacha2.reader.Positive_Shape_Type;
            LeftPanelShapeScale = Vizcacha2.reader.Positive_Shape_Scale;
            LeftPanelShapeHorizontalOffset = Vizcacha2.reader.Positive_Shape_Horizontal_Offset;
            LeftPanelShapeFieldScale = Vizcacha2.reader.Positive_Shape_Field_Scale;
            LeftPanelShapeEllipseX = (int) Vizcacha2.reader.Positive_Shape_Ellipse_X;
            LeftPanelShapeEllipseY = (int) Vizcacha2.reader.Positive_Shape_Ellipse_Y;
            LeftPanelShapeDotSize = (int) Vizcacha2.reader.Positive_Shape_Dot_Size;
            LeftPanelShapeCoherence = Vizcacha2.reader.Positive_Shape_Coherence;
            LeftPanelShapeSpeedX = (int) Vizcacha2.reader.Positive_Shape_Speed_X;
            LeftPanelShapeSpeedY = (int) Vizcacha2.reader.Positive_Shape_Speed_Y;
            LeftPanelShapeMaxNumber = (int) Vizcacha2.reader.Positive_Shape_Dot_Max_Count;
            LeftPanelShapeDotColor = (int) Vizcacha2.reader.Positive_Shape_Dot_Color;
            LeftPanelShapeDotColor2 = (int) Vizcacha2.reader.Positive_Shape_Dot_Color_2;
            LeftPanelShapeDotColorThreshold = Vizcacha2.reader.Positive_Shape_Dot_Color_Threshold;
            LeftPanelShapeLifeTimeS = Vizcacha2.reader.Positive_Shape_Life_Time;
            
            LeftPanelShapeMedium_Value = Vizcacha2.reader.Positive_Shape_Medium_Value;
            LeftPanelShapeAmplitude = Vizcacha2.reader.Positive_Shape_Amplitude;
            LeftPanelShapeSpeed = (int) Vizcacha2.reader.Positive_Shape_Speed;
            LeftPanelShapeDirection = Vizcacha2.reader.Positive_Shape_Direction;
            LeftPanelShapeWidth = Vizcacha2.reader.Positive_Shape_Width;
            LeftPanelShapeDuty = Vizcacha2.reader.Positive_Shape_Duty;
            LeftPanelShapeMask = Vizcacha2.reader.Positive_Shape_Mask;
            LeftPanelShapeDashedLength = Vizcacha2.reader.Positive_Shape_Dashed_Length;
            LeftPanelShapeDashedSpacing = Vizcacha2.reader.Positive_Shape_Dashed_Spacing;
            
            LeftPanelNoiseType = Vizcacha2.reader.Positive_Noise_Type;
            LeftPanelNoiseDotSize = (int) Vizcacha2.reader.Positive_Noise_Dot_Size;
            LeftPanelNoiseSpeed = (int) Vizcacha2.reader.Positive_Noise_Speed;
            LeftPanelNoiseDirection = (int) Vizcacha2.reader.Positive_Noise_Direction;
            LeftPanelNoiseCoherence = Vizcacha2.reader.Positive_Noise_Coherence;
            LeftPanelNoiseSpeedX = (int) Vizcacha2.reader.Positive_Noise_Speed_X;
            LeftPanelNoiseSpeedY = (int) Vizcacha2.reader.Positive_Noise_Speed_Y;
            LeftPanelNoiseMaxNumber = (int) Vizcacha2.reader.Positive_Noise_Dot_Max_Count;
            LeftPanelNoiseDotColor = (int) Vizcacha2.reader.Positive_Noise_Dot_Color;
            LeftPanelNoiseDotColor2 = (int) Vizcacha2.reader.Positive_Noise_Dot_Color_2;
            LeftPanelNoiseDotColorThreshold = Vizcacha2.reader.Positive_Noise_Dot_Color_Threshold;
            LeftPanelNoiseLifeTimeS = Vizcacha2.reader.Positive_Noise_Life_Time;
            
            LeftPanelNoiseMedium_Value = Vizcacha2.reader.Positive_Noise_Medium_Value;
            LeftPanelNoiseAmplitude = Vizcacha2.reader.Positive_Noise_Amplitude;
            LeftPanelNoiseWidth = Vizcacha2.reader.Positive_Noise_Width;
            LeftPanelNoiseDuty = Vizcacha2.reader.Positive_Noise_Duty;
            LeftPanelNoiseMask = Vizcacha2.reader.Positive_Noise_Mask;
            LeftPanelNoiseDashedLength = Vizcacha2.reader.Positive_Noise_Dashed_Length;
            LeftPanelNoiseDashedSpacing = Vizcacha2.reader.Positive_Noise_Dashed_Spacing;

        }
        if (side.equals("right"))
        {
            RightPanelBackgroundColor = Vizcacha2.reader.Background;
            RightPanelBackgroundType = Vizcacha2.reader.Positive_Background_Type;
             
            RightPanelBackgroundDotSize = (int) Vizcacha2.reader.Positive_Background_Dot_Size;
            RightPanelBackgroundSpeed = (int) Vizcacha2.reader.Positive_Background_Speed;
            RightPanelBackgroundDirection = (int) Vizcacha2.reader.Positive_Background_Direction;
            RightPanelBackgroundCoherence = Vizcacha2.reader.Positive_Background_Coherence;
            RightPanelBackgroundSpeedX = (int) Vizcacha2.reader.Positive_Background_Speed_X;
            RightPanelBackgroundSpeedY = (int) Vizcacha2.reader.Positive_Background_Speed_Y;
            RightPanelBackgroundMaxNumber = (int) Vizcacha2.reader.Positive_Background_Dot_Max_Count;
            RightPanelBackgroundDotColor = (int) Vizcacha2.reader.Positive_Background_Dot_Color;
            RightPanelBackgroundDotColor2 = (int) Vizcacha2.reader.Positive_Background_Dot_Color_2;
            RightPanelBackgroundDotColorThreshold = Vizcacha2.reader.Positive_Background_Dot_Color_Threshold;
            RightPanelBackgroundLifeTimeS = Vizcacha2.reader.Positive_Background_Life_Time;

            RightPanelBackgroundMedium_Value = Vizcacha2.reader.Positive_Background_Medium_Value;
            RightPanelBackgroundAmplitude = Vizcacha2.reader.Positive_Background_Amplitude;
            RightPanelBackgroundWidth = Vizcacha2.reader.Positive_Background_Width;
            RightPanelBackgroundDuty = Vizcacha2.reader.Positive_Background_Duty;
            RightPanelBackgroundMask = Vizcacha2.reader.Positive_Background_Mask;
            RightPanelBackgroundDashedLength = Vizcacha2.reader.Positive_Background_Dashed_Length;
            RightPanelBackgroundDashedSpacing = Vizcacha2.reader.Positive_Background_Dashed_Spacing;
            
            RightPanelShapeBackgroundColor = Vizcacha2.reader.Positive_Shape_Background_Color;
            
            RightPanelShapeType = Vizcacha2.reader.Positive_Shape_Type;
            RightPanelShapeScale = Vizcacha2.reader.Positive_Shape_Scale;
            RightPanelShapeHorizontalOffset = Vizcacha2.reader.Positive_Shape_Horizontal_Offset;
            RightPanelShapeFieldScale = Vizcacha2.reader.Positive_Shape_Field_Scale;
            RightPanelShapeEllipseX = (int) Vizcacha2.reader.Positive_Shape_Ellipse_X;
            RightPanelShapeEllipseY = (int) Vizcacha2.reader.Positive_Shape_Ellipse_Y;
            RightPanelShapeDotSize = (int) Vizcacha2.reader.Positive_Shape_Dot_Size;
            RightPanelShapeCoherence = Vizcacha2.reader.Positive_Shape_Coherence;
            RightPanelShapeSpeedX = (int) Vizcacha2.reader.Positive_Shape_Speed_X;
            RightPanelShapeSpeedY = (int) Vizcacha2.reader.Positive_Shape_Speed_Y;
            RightPanelShapeMaxNumber = (int) Vizcacha2.reader.Positive_Shape_Dot_Max_Count;
            RightPanelShapeDotColor = (int) Vizcacha2.reader.Positive_Shape_Dot_Color;
            RightPanelShapeDotColor2 = (int) Vizcacha2.reader.Positive_Shape_Dot_Color_2;
            RightPanelShapeDotColorThreshold = Vizcacha2.reader.Positive_Shape_Dot_Color_Threshold;
            RightPanelShapeLifeTimeS = Vizcacha2.reader.Positive_Shape_Life_Time;
            
            RightPanelShapeMedium_Value = Vizcacha2.reader.Positive_Shape_Medium_Value;
            RightPanelShapeAmplitude = Vizcacha2.reader.Positive_Shape_Amplitude;
            RightPanelShapeSpeed = (int) Vizcacha2.reader.Positive_Shape_Speed;
            RightPanelShapeDirection = Vizcacha2.reader.Positive_Shape_Direction;
            RightPanelShapeWidth = Vizcacha2.reader.Positive_Shape_Width;
            RightPanelShapeDuty = Vizcacha2.reader.Positive_Shape_Duty;
            RightPanelShapeMask = Vizcacha2.reader.Positive_Shape_Mask;
            RightPanelShapeDashedLength = Vizcacha2.reader.Positive_Shape_Dashed_Length;
            RightPanelShapeDashedSpacing = Vizcacha2.reader.Positive_Shape_Dashed_Spacing;
            
            RightPanelNoiseType = Vizcacha2.reader.Positive_Noise_Type;
            RightPanelNoiseDotSize = (int) Vizcacha2.reader.Positive_Noise_Dot_Size;
            RightPanelNoiseSpeed = (int) Vizcacha2.reader.Positive_Noise_Speed;
            RightPanelNoiseDirection = (int) Vizcacha2.reader.Positive_Noise_Direction;
            RightPanelNoiseCoherence = Vizcacha2.reader.Positive_Noise_Coherence;
            RightPanelNoiseSpeedX = (int) Vizcacha2.reader.Positive_Noise_Speed_X;
            RightPanelNoiseSpeedY = (int) Vizcacha2.reader.Positive_Noise_Speed_Y;
            RightPanelNoiseMaxNumber = (int) Vizcacha2.reader.Positive_Noise_Dot_Max_Count;
            RightPanelNoiseDotColor = (int) Vizcacha2.reader.Positive_Noise_Dot_Color;
            RightPanelNoiseDotColor2 = (int) Vizcacha2.reader.Positive_Noise_Dot_Color_2;
            RightPanelNoiseDotColorThreshold = Vizcacha2.reader.Positive_Noise_Dot_Color_Threshold;
            RightPanelNoiseLifeTimeS = Vizcacha2.reader.Positive_Noise_Life_Time;
            
            RightPanelNoiseMedium_Value = Vizcacha2.reader.Positive_Noise_Medium_Value;
            RightPanelNoiseAmplitude = Vizcacha2.reader.Positive_Noise_Amplitude;
            RightPanelNoiseWidth = Vizcacha2.reader.Positive_Noise_Width;
            RightPanelNoiseDuty = Vizcacha2.reader.Positive_Noise_Duty;
            RightPanelNoiseMask = Vizcacha2.reader.Positive_Noise_Mask;
            RightPanelNoiseDashedLength = Vizcacha2.reader.Positive_Noise_Dashed_Length;
            RightPanelNoiseDashedSpacing = Vizcacha2.reader.Positive_Noise_Dashed_Spacing;
        }
    }
    
    private void PaintComponent_PrepareNegative(String side, double current_staircase_factor, double current_constant_factor, String experiment_type)
    {
        if (current_staircase_factor!=0)
        {
            param_value = Vizcacha2.trials.Trial_StaircaseValue(current_staircase_factor);
        }
        else
        {
            param_value = current_constant_factor;
        }
        
        if (side.equals("left"))
        {
            PaintComponent_PreparePositive("left");
            
            LeftPanelBackgroundDirection = (int) Vizcacha2.reader.Negative_Background_Direction;
            LeftPanelShapeDirection = Vizcacha2.reader.Negative_Shape_Direction;
            LeftPanelNoiseDirection = (int) Vizcacha2.reader.Negative_Noise_Direction;
            
            LeftPanelShapeHorizontalOffset = Vizcacha2.reader.Negative_Shape_Horizontal_Offset;
            
            LeftPanelShapeScale = Vizcacha2.reader.Negative_Shape_Scale;
            LeftPanelShapeFieldScale = Vizcacha2.reader.Negative_Shape_Field_Scale;
            
            LeftPanelShapeSpeed = Vizcacha2.reader.Negative_Shape_Speed;
            
            if (Vizcacha2.reader.Negative_Shape_Ellipse_X!=0)
                LeftPanelShapeEllipseX = Vizcacha2.reader.Negative_Shape_Ellipse_X;
            if (Vizcacha2.reader.Negative_Shape_Ellipse_Y!=0)
                LeftPanelShapeEllipseY = Vizcacha2.reader.Negative_Shape_Ellipse_Y;
            // dla gratingow
            if ("2".equals(LeftPanelShapeType))
            {
                LeftPanelShapeDirection = Vizcacha2.reader.Negative_Shape_Direction;
                LeftPanelBackgroundColor = (int) (LeftPanelShapeMedium_Value * 255);
                RightPanelBackgroundColor = (int) (RightPanelShapeMedium_Value * 255);
            }
            
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Coherence_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelBackgroundCoherence = param_value;
                LeftPanelBackgroundCoherence = param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Coherence;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Speed_X_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelBackgroundSpeedX = (int) param_value;
                LeftPanelBackgroundSpeedX = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Speed_X;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Speed_Y_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelBackgroundSpeedY = (int) param_value;
                LeftPanelBackgroundSpeedY = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Speed_Y;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Speed_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelBackgroundSpeed = (int) param_value;
                LeftPanelBackgroundSpeed = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Speed;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Direction_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelBackgroundDirection = (int) param_value;
                LeftPanelBackgroundDirection = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Direction;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Dot_Size_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelBackgroundDotSize = (int) param_value;
                LeftPanelBackgroundDotSize = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Dot_Size;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Dot_Color_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelBackgroundDotColor = (int) param_value;
                LeftPanelBackgroundDotColor = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Dot_Color;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Dot_Color_2_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelBackgroundDotColor2 = (int) param_value;
                LeftPanelBackgroundDotColor2 = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Dot_Color_2;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Dot_Color_Threshold_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelBackgroundDotColorThreshold = param_value;
                LeftPanelBackgroundDotColorThreshold = param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Dot_Color_Threshold;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Dot_Max_Count_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelBackgroundMaxNumber = (int) param_value;
                LeftPanelBackgroundMaxNumber = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Dot_Max_Count;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Life_Time_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelBackgroundLifeTimeS = (int) param_value;
                LeftPanelBackgroundLifeTimeS = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Life_Time;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Dashed_Length_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelBackgroundDashedLength = (int) param_value;
                LeftPanelBackgroundDashedLength = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Dashed_Length;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Dashed_Spacing_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelBackgroundDashedSpacing = (int) param_value;
                LeftPanelBackgroundDashedSpacing = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Dashed_Spacing;
            }               
            
            
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Horizontal_Offset_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeHorizontalOffset = (int) param_value;
                LeftPanelShapeHorizontalOffset = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Horizontal_Offset;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Field_Scale_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeFieldScale = param_value;
                LeftPanelShapeFieldScale = param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Field_Scale;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Scale_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeScale = param_value;
                LeftPanelShapeScale = param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Scale;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Background_Color_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeBackgroundColor = (int) param_value;
                LeftPanelShapeBackgroundColor = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Background_Color;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Ellipse_X_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeEllipseX = (int) param_value;
                LeftPanelShapeEllipseX = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Ellipse_X;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Ellipse_Y_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeEllipseY = (int) param_value;
                LeftPanelShapeEllipseY = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Ellipse_Y;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Medium_Value_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeMedium_Value = param_value;
                LeftPanelShapeMedium_Value = param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Medium_Value;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Amplitude_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeAmplitude = param_value;
                LeftPanelShapeAmplitude = param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Amplitude;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Speed_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeSpeed = (int) param_value;
                LeftPanelShapeSpeed = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Speed;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Direction_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeDirection = (int) param_value;
                LeftPanelShapeDirection = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Direction;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Width_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeWidth = (int) param_value;
                LeftPanelShapeWidth = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Width;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Duty_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeDuty = (int) param_value;
                LeftPanelShapeDuty = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Duty;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Mask_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeMask = (int) param_value;
                LeftPanelShapeMask = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Mask;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Coherence_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeCoherence = param_value;
                LeftPanelShapeCoherence = param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Coherence;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Speed_X_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeSpeedX = (int) param_value;
                LeftPanelShapeSpeedX = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Speed_X;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Speed_Y_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeSpeedY = (int) param_value;
                LeftPanelShapeSpeedY = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Speed_Y;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Dot_Size_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeDotSize = (int) param_value;
                LeftPanelShapeDotSize = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Dot_Size;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Dot_Color_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeDotColor = (int) param_value;
                LeftPanelShapeDotColor = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Dot_Color;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Dot_Color_2_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeDotColor2 = (int) param_value;
                LeftPanelShapeDotColor2 = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Dot_Color_2;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Dot_Color_Threshold_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeDotColorThreshold = param_value;
                LeftPanelShapeDotColorThreshold = param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Dot_Color_Threshold;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Dot_Max_Count_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeMaxNumber = (int) param_value;
                LeftPanelShapeMaxNumber = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Dot_Max_Count;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Life_Time_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeLifeTimeS = (int) param_value;
                LeftPanelShapeLifeTimeS = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Life_Time;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Dashed_Length_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeDashedLength = (int) param_value;
                LeftPanelShapeDashedLength = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Dashed_Length;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Dashed_Spacing_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelShapeDashedSpacing = (int) param_value;
                LeftPanelShapeDashedSpacing = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Dashed_Spacing;
            }
                        
            //System.out.println(Vizcacha2.reader.PARAM + " ---------------------------------- ");
            //System.out.println((Vizcacha2.reader.Stimuli_Noise_Coherence_String+Vizcacha2.reader.Automaton_State_Negative_Noise) + " ---------------------------------- ");
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Coherence_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelNoiseCoherence = param_value;
                LeftPanelNoiseCoherence = param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Coherence;
                //System.out.println("left noise coherence overwritten from param: " + LeftPanelNoiseCoherence);
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Speed_X_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelNoiseSpeedX = (int) param_value;
                LeftPanelNoiseSpeedX = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Speed_X;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Speed_Y_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelNoiseSpeedY = (int) param_value;
                LeftPanelNoiseSpeedY = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Speed_Y;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Dot_Size_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelNoiseDotSize = (int) param_value;
                LeftPanelNoiseDotSize = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Dot_Size;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Dot_Color_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelNoiseDotColor = (int) param_value;
                LeftPanelNoiseDotColor = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Dot_Color;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Dot_Color_2_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelNoiseDotColor2 = (int) param_value;
                LeftPanelNoiseDotColor2 = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Dot_Color_2;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Dot_Color_Threshold_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelNoiseDotColorThreshold = param_value;
                LeftPanelNoiseDotColorThreshold = param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Dot_Color_Threshold;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Dot_Max_Count_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelNoiseMaxNumber = (int) param_value;
                LeftPanelNoiseMaxNumber = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Dot_Max_Count;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Life_Time_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelNoiseLifeTimeS = (int) param_value;
                LeftPanelNoiseLifeTimeS = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Life_Time;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Dashed_Length_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelNoiseDashedLength = (int) param_value;
                LeftPanelNoiseDashedLength = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Dashed_Length;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Dashed_Spacing_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    RightPanelNoiseDashedSpacing = (int) param_value;
                LeftPanelNoiseDashedSpacing = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Dashed_Spacing;
            }            
            
            LeftPanelShapeEllipseX *= LeftPanelShapeScale;
            LeftPanelShapeEllipseY *= LeftPanelShapeScale;
            
            LeftPanelShapeEllipseX *= LeftPanelShapeFieldScale;
            LeftPanelShapeEllipseY /= LeftPanelShapeFieldScale;
        }
        else
        {
            PaintComponent_PreparePositive("right");
            
            RightPanelBackgroundDirection = (int) Vizcacha2.reader.Negative_Background_Direction;
            RightPanelShapeDirection = Vizcacha2.reader.Negative_Shape_Direction;
            RightPanelNoiseDirection = (int) Vizcacha2.reader.Negative_Noise_Direction;
            
            RightPanelShapeHorizontalOffset = Vizcacha2.reader.Negative_Shape_Horizontal_Offset;
            
            RightPanelShapeScale = Vizcacha2.reader.Negative_Shape_Scale;
            RightPanelShapeFieldScale = Vizcacha2.reader.Negative_Shape_Field_Scale;
            
            RightPanelShapeSpeed = Vizcacha2.reader.Negative_Shape_Speed;
            
            if (Vizcacha2.reader.Negative_Shape_Ellipse_X!=0)
                RightPanelShapeEllipseX = Vizcacha2.reader.Negative_Shape_Ellipse_X;
            if (Vizcacha2.reader.Negative_Shape_Ellipse_Y!=0)
                RightPanelShapeEllipseY = Vizcacha2.reader.Negative_Shape_Ellipse_Y;
            // dla gratingow
            if ("2".equals(RightPanelShapeType))
            {
                RightPanelShapeDirection = Vizcacha2.reader.Negative_Shape_Direction;
                RightPanelBackgroundColor = (int) (RightPanelShapeMedium_Value * 255);
                LeftPanelBackgroundColor = (int) (LeftPanelShapeMedium_Value * 255);
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Coherence_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelBackgroundCoherence = param_value;
                RightPanelBackgroundCoherence = param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Coherence;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Speed_X_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelBackgroundSpeedX = (int) param_value;
                RightPanelBackgroundSpeedX = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Speed_X;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Speed_Y_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelBackgroundSpeedY = (int) param_value;
                RightPanelBackgroundSpeedY = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Speed_Y;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Speed_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelBackgroundSpeed = (int) param_value;
                RightPanelBackgroundSpeed = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Speed;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Direction_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelBackgroundDirection = (int) param_value;
                RightPanelBackgroundDirection = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Direction;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Dot_Size_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelBackgroundDotSize = (int) param_value;
                RightPanelBackgroundDotSize = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Dot_Size;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Dot_Color_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelBackgroundDotColor = (int) param_value;
                RightPanelBackgroundDotColor = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Dot_Color;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Dot_Color_2_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelBackgroundDotColor2 = (int) param_value;
                RightPanelBackgroundDotColor2 = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Dot_Color_2;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Dot_Color_Threshold_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelBackgroundDotColorThreshold = param_value;
                RightPanelBackgroundDotColorThreshold = param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Dot_Color_Threshold;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Dot_Max_Count_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelBackgroundMaxNumber = (int) param_value;
                RightPanelBackgroundMaxNumber = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Dot_Max_Count;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Life_Time_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelBackgroundLifeTimeS = (int) param_value;
                RightPanelBackgroundLifeTimeS = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Life_Time;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Dashed_Length_String+Vizcacha2.reader.Automaton_State_Negative_Background) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelBackgroundDashedLength = (int) param_value;
                RightPanelBackgroundDashedLength = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Dashed_Length;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Background_Dashed_Spacing_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelBackgroundDashedSpacing = (int) param_value;
                RightPanelBackgroundDashedSpacing = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Background_Dashed_Spacing;
            }             
            
            
            
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Horizontal_Offset_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeHorizontalOffset = (int) param_value;
                RightPanelShapeHorizontalOffset = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Horizontal_Offset;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Field_Scale_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeFieldScale = param_value;
                RightPanelShapeFieldScale = param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Field_Scale;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Scale_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeScale = param_value;
                RightPanelShapeScale = param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Scale;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Background_Color_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeBackgroundColor = (int) param_value;
                RightPanelShapeBackgroundColor = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Background_Color;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Ellipse_X_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeEllipseX = (int) param_value;
                RightPanelShapeEllipseX = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Ellipse_X;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Ellipse_Y_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeEllipseY = (int) param_value;
                RightPanelShapeEllipseY = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Ellipse_Y;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Medium_Value_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeMedium_Value = param_value;
                RightPanelShapeMedium_Value = param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Medium_Value;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Amplitude_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeAmplitude = param_value;
                RightPanelShapeAmplitude = param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Amplitude;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Speed_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeSpeed = (int) param_value;
                RightPanelShapeSpeed = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Speed;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Direction_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeDirection = (int) param_value;
                RightPanelShapeDirection = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Direction;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Width_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeWidth = (int) param_value;
                RightPanelShapeWidth = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Width;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Duty_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeDuty = (int) param_value;
                RightPanelShapeDuty = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Duty;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Mask_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeMask = (int) param_value;
                RightPanelShapeMask = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Mask;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Coherence_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeCoherence = param_value;
                RightPanelShapeCoherence = param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Coherence;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Speed_X_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeSpeedX = (int) param_value;
                RightPanelShapeSpeedX = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Speed_X;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Speed_Y_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeSpeedY = (int) param_value;
                RightPanelShapeSpeedY = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Speed_Y;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Dot_Size_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeDotSize = (int) param_value;
                RightPanelShapeDotSize = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Dot_Size;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Dot_Color_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeDotColor = (int) param_value;
                RightPanelShapeDotColor = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Dot_Color;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Dot_Color_2_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeDotColor2 = (int) param_value;
                RightPanelShapeDotColor2 = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Dot_Color_2;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Dot_Color_Threshold_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeDotColorThreshold = param_value;
                RightPanelShapeDotColorThreshold = param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Dot_Color_Threshold;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Dot_Max_Count_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeMaxNumber = (int) param_value;
                RightPanelShapeMaxNumber = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Dot_Max_Count;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Life_Time_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeLifeTimeS = (int) param_value;
                RightPanelShapeLifeTimeS = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Life_Time;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Dashed_Length_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeDashedLength = (int) param_value;
                RightPanelShapeDashedLength = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Dashed_Length;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Shape_Dashed_Spacing_String+Vizcacha2.reader.Automaton_State_Negative_Shape) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelShapeDashedSpacing = (int) param_value;
                RightPanelShapeDashedSpacing = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Shape_Dashed_Spacing;
            }            
            
            
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Coherence_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelNoiseCoherence = param_value;
                RightPanelNoiseCoherence = param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Coherence;
                //System.out.println("left noise coherence overwritten from param: " + RightPanelNoiseCoherence);
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Speed_X_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelNoiseSpeedX = (int) param_value;
                RightPanelNoiseSpeedX = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Speed_X;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Speed_Y_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelNoiseSpeedY = (int) param_value;
                RightPanelNoiseSpeedY = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Speed_Y;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Dot_Size_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelNoiseDotSize = (int) param_value;
                RightPanelNoiseDotSize = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Dot_Size;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Dot_Color_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelNoiseDotColor = (int) param_value;
                RightPanelNoiseDotColor = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Dot_Color;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Dot_Color_2_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelNoiseDotColor2 = (int) param_value;
                RightPanelNoiseDotColor2 = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Dot_Color_2;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Dot_Color_Threshold_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelNoiseDotColorThreshold = param_value;
                RightPanelNoiseDotColorThreshold = param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Dot_Color_Threshold;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Dot_Max_Count_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelNoiseMaxNumber = (int) param_value;
                RightPanelNoiseMaxNumber = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Dot_Max_Count;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Life_Time_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelNoiseLifeTimeS = (int) param_value;
                RightPanelNoiseLifeTimeS = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Life_Time;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Dashed_Length_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelNoiseDashedLength = (int) param_value;
                RightPanelNoiseDashedLength = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Dashed_Length;
            }
            if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Stimuli_Noise_Dashed_Spacing_String+Vizcacha2.reader.Automaton_State_Negative_Noise) )
            {
                if (Vizcacha2.reader.Change_In_Both==1)
                    LeftPanelNoiseDashedSpacing = (int) param_value;
                RightPanelNoiseDashedSpacing = (int) param_value;
                positive_value = Vizcacha2.reader.Positive_Noise_Dashed_Spacing;
            }            
            
            
            RightPanelShapeEllipseX *= RightPanelShapeScale;
            RightPanelShapeEllipseY *= RightPanelShapeScale;
            
            RightPanelShapeEllipseX *= RightPanelShapeFieldScale;
            RightPanelShapeEllipseY /= RightPanelShapeFieldScale;
        }
        
        if ( Vizcacha2.reader.PARAM.equals(Vizcacha2.reader.Background_String) )
        {
            LeftPanelBackgroundColor = (int) param_value;
            RightPanelBackgroundColor = (int) param_value;
            positive_value = Vizcacha2.reader.Positive_Shape_Background_Color;
        }
    }
    
    private void PaintComponent_LeftSide(Graphics g)
    {
        PaintComponent_LeftSideBackground(g);
        PaintComponent_LeftSideShape(g);
        PaintComponent_LeftSideNoise(g);
    }
    
    private void PaintComponent_LeftSideBackground(Graphics g)
    {
        int x0 = 0;
        int y0 = 0;
        int w = screen_width/2;
        int h = screen_height;
                
        Color color = new Color(LeftPanelBackgroundColor, LeftPanelBackgroundColor, LeftPanelBackgroundColor);
        
        g.setColor(color);
        g.fillRect(x0, y0, w, h);
        
        if(LeftPanelBackgroundType.equals("0"))
        {
            
        }
        else if(LeftPanelBackgroundType.equals("1"))
        {
            //System.out.println("Drawing left background type 1");
            Rectangle2D.Double rect1 = new Rectangle2D.Double(0, 0, screen_width/2, screen_height);
            Area rect_clip = new Area(rect1);
            g.setClip(rect_clip);
            
            LeftBackgroundDots.Dots_Update();
            LeftBackgroundDots.Dots_SynchroUpdate();
            LeftBackgroundDots.Dots_Draw(g); 
            
            g.setClip(null);
        }
        else if(LeftPanelBackgroundType.equals("2"))
        {
            g.setClip(null);
            LeftPanelBackgroundSineAnimation.SineAnimation_UpdateAndDrawLayer(g, 0, 0, screen_width/2, screen_height/2);
        }
        else if(LeftPanelBackgroundType.equals("3"))
        {
            g.setClip(null);
            LeftPanelBackgroundRectAnimation.RectAnimation_UpdateAndDrawLayer(g, 0, 0, screen_width/2, screen_height/2);
        }
        g.setClip(null);
    }
    
    private void PaintComponent_LeftSideShape(Graphics g)
    {        
        if(LeftPanelShapeType.equals("0"))
        {
            
        }
        else if(LeftPanelShapeType.equals("1"))
        {
            int x0 = 0;
            int y0 = 0;
            int x1 = screen_width/2;
            int y1 = screen_height;
            int w = screen_width/2;
            int h = screen_height;
            
            //int ellipse_xloc = x0 + ((x1-x0)-LeftPanelShapeEllipseX)/2;
            int ellipse_xloc = x1 - LeftPanelShapeHorizontalOffset - LeftPanelShapeEllipseX/2;
            int ellipse_yloc = y0 + ((y1-y0)-LeftPanelShapeEllipseY)/2;

            Ellipse2D.Double ellipse1 = new Ellipse2D.Double(ellipse_xloc,ellipse_yloc,LeftPanelShapeEllipseX,LeftPanelShapeEllipseY);
            Area circle_inner = new Area(ellipse1);
            g.setClip(circle_inner);

            Color color = new Color(LeftPanelShapeBackgroundColor, LeftPanelShapeBackgroundColor, LeftPanelShapeBackgroundColor);
            
            g.setColor(color);
            g.fillRect(x0, y0, w, h);
            
            LeftShapeDots.Dots_Update();
            LeftShapeDots.Dots_SynchroUpdate();
            LeftShapeDots.Dots_Draw(g); 
        }
        else if(LeftPanelShapeType.equals("2"))
        {
            g.setClip(null);
            LeftPanelShapeSineAnimation.SineAnimation_UpdateAndDrawEllipse(g, screen_width/2 - LeftPanelShapeHorizontalOffset, screen_height/2);
        }
        else if(LeftPanelShapeType.equals("3"))
        {
            g.setClip(null);
            LeftPanelShapeRectAnimation.RectAnimation_UpdateAndDrawEllipse(g, screen_width/2 - LeftPanelShapeHorizontalOffset, screen_height/2);
        }
        g.setClip(null);
    }
    private void PaintComponent_LeftSideNoise(Graphics g)
    {
        if(LeftPanelNoiseType.equals("0"))
        {
            
        }
        else if(LeftPanelNoiseType.equals("1"))
        {
            //System.out.println("Drawing left background type 1");
            Rectangle2D.Double rect1 = new Rectangle2D.Double(0, 0, screen_width/2, screen_height);
            Area rect_clip = new Area(rect1);
            g.setClip(rect_clip);
            
            LeftNoiseDots.Dots_Update();
            LeftNoiseDots.Dots_SynchroUpdate();
            LeftNoiseDots.Dots_Draw(g); 
            
            g.setClip(null);
        }
        else if(LeftPanelNoiseType.equals("2"))
        {
            g.setClip(null);
            LeftPanelNoiseSineAnimation.SineAnimation_UpdateAndDrawLayer(g, 0, 0, screen_width/2, screen_height/2);
        }
        else if(LeftPanelNoiseType.equals("3"))
        {
            g.setClip(null);
            LeftPanelNoiseRectAnimation.RectAnimation_UpdateAndDrawLayer(g, 0, 0, screen_width/2, screen_height/2);
        }
        g.setClip(null);
    }
    
    private void PaintComponent_RightSide(Graphics g)
    {
        PaintComponent_RightSideBackground(g);
        PaintComponent_RightSideShape(g);
        PaintComponent_RightSideNoise(g);
    }
    
    private void PaintComponent_RightSideBackground(Graphics g)
    {
        int x0 = screen_width/2;
        int y0 = 0;
        int w = screen_width/2;
        int h = screen_height;
        
        Color color = new Color(RightPanelBackgroundColor, RightPanelBackgroundColor, RightPanelBackgroundColor);
        
        g.setColor(color);
        g.fillRect(x0, y0, w, h);
        
        if(RightPanelBackgroundType.equals("0"))
        {
            
        }
        else if(RightPanelBackgroundType.equals("1"))
        {
            //System.out.println("Drawing right background type 1");
            Rectangle2D.Double rect1 = new Rectangle2D.Double(screen_width/2, 0, screen_width, screen_height);
            Area rect_clip = new Area(rect1);
            g.setClip(rect_clip);
            
            RightBackgroundDots.Dots_Update();
            RightBackgroundDots.Dots_SynchroUpdate();
            RightBackgroundDots.Dots_Draw(g); 
            
            g.setClip(null);
        }
        else if(RightPanelBackgroundType.equals("2"))
        {
            g.setClip(null);
            RightPanelBackgroundSineAnimation.SineAnimation_UpdateAndDrawLayer(g, screen_width/2, 0, screen_width/2, screen_height/2);
        }
        else if(RightPanelBackgroundType.equals("3"))
        {
            g.setClip(null);
            RightPanelBackgroundRectAnimation.RectAnimation_UpdateAndDrawLayer(g, screen_width/2, 0, screen_width/2, screen_height/2);
        }
        g.setClip(null);
    }

    private void PaintComponent_RightSideShape(Graphics g)
    {        
        if(RightPanelShapeType.equals("0"))
        {
            
        }
        else if(RightPanelShapeType.equals("1"))
        {
            int x0 = screen_width/2;
            int y0 = 0;
            int x1 = screen_width;
            int y1 = screen_height;
            int w = screen_width/2;
            int h = screen_height;

            //int ellipse_xloc = x0 + ((x1-x0)-RightPanelShapeEllipseX)/2;
            int ellipse_xloc = x0 + RightPanelShapeHorizontalOffset - RightPanelShapeEllipseX/2;
            int ellipse_yloc = y0 + ((y1-y0)-RightPanelShapeEllipseY)/2;

            Ellipse2D.Double ellipse1 = new Ellipse2D.Double(ellipse_xloc,ellipse_yloc,RightPanelShapeEllipseX,RightPanelShapeEllipseY);
            Area circle_inner = new Area(ellipse1);
            g.setClip(circle_inner);

            Color color = new Color(RightPanelShapeBackgroundColor, RightPanelShapeBackgroundColor, RightPanelShapeBackgroundColor);
            
            g.setColor(color);
            g.fillRect(x0, y0, w, h);
            
            //System.out.println("Drawing left background type 1");
            RightShapeDots.Dots_Update();
            RightShapeDots.Dots_SynchroUpdate();
            RightShapeDots.Dots_Draw(g); 
        }
        else if(RightPanelShapeType.equals("2"))
        {
            g.setClip(null);
            RightPanelShapeSineAnimation.SineAnimation_UpdateAndDrawEllipse(g, RightPanelShapeHorizontalOffset + screen_width/2, screen_height/2);
        }
        else if(RightPanelShapeType.equals("3"))
        {
            g.setClip(null);
            RightPanelShapeRectAnimation.RectAnimation_UpdateAndDrawEllipse(g, RightPanelShapeHorizontalOffset + screen_width/2, screen_height/2);
        }
        
        g.setClip(null);
    }
    
    private void PaintComponent_RightSideNoise(Graphics g)
    {
        if(RightPanelNoiseType.equals("0"))
        {
            
        }
        else if(RightPanelNoiseType.equals("1"))
        {
            //System.out.println("Drawing left background type 1");
            
            Rectangle2D.Double rect1 = new Rectangle2D.Double(screen_width/2,0,screen_width,screen_height);
            Area rect_clip = new Area(rect1);
            g.setClip(rect_clip);
            
            RightNoiseDots.Dots_Update();
            RightNoiseDots.Dots_SynchroUpdate();
            RightNoiseDots.Dots_Draw(g); 
            
            g.setClip(null);
        }
        else if(RightPanelNoiseType.equals("2"))
        {
            g.setClip(null);
            RightPanelNoiseSineAnimation.SineAnimation_UpdateAndDrawLayer(g, screen_width/2, 0, screen_width/2, screen_height/2);
        }
        else if(RightPanelNoiseType.equals("3"))
        {
            g.setClip(null);
            RightPanelNoiseRectAnimation.RectAnimation_UpdateAndDrawLayer(g, screen_width/2, 0, screen_width/2, screen_height/2);
        }
        g.setClip(null);
    }
    
    public void VizPanel_WaitForStart(int color)
    {
        ScreenChangeColor = new Color(color,color,color);
        screen_type = 0;
        this.revalidate();
        this.repaint();
    }
    
    public void VizPanel_StartAnimation()
    {
        if (!AnimationThread_isActive)
        {
            screen_type = 1;
            AnimationThread_isActive = true;
            
            AnimationThread = new VizPanel_Updater();
            AnimationThread.start();
        }
    }
    
    public void VizPanel_StopAnimation()
    {
        if (AnimationThread_isActive)
        {
            AnimationThread_isActive = false;
            AnimationThread.end();
            this.revalidate();
            this.repaint();
        }
    }
    
    public void VizPanel_ChangeScreen(int color)
    {
        ScreenChangeColor = new Color(color,color,color);
        screen_type = 2;
    }
    
    private void PaintFocusPoint(Graphics g)
    {
        if (Vizcacha2.config_reader.focus_point!=0)
        {
            Color color1 = new Color(255, 255, 255);
            Color color2 = new Color(0, 0, 0);

            int o2_diameter = Vizcacha2.config_reader.focus_point_diameter;

            g.setColor(color2);
            g.fillOval((screen_width - o2_diameter)/2, (screen_height - o2_diameter)/2, o2_diameter, o2_diameter);

            int strip_thickness = o2_diameter/8;
            g.setColor(color1);
            g.fillRect((screen_width - o2_diameter)/2, (screen_height - strip_thickness)/2, o2_diameter, strip_thickness);

            g.setColor(color1);
            g.fillRect((screen_width - strip_thickness)/2, (screen_height - o2_diameter)/2, strip_thickness, o2_diameter);

            int o3_diameter = o2_diameter*15/80;

            g.setColor(color2);
            g.fillOval((screen_width - o3_diameter)/2, (screen_height - o3_diameter)/2, o3_diameter, o3_diameter);
        }
    }
    
}

class VizPanel_Updater extends Thread implements Runnable 
{
    int refresh_period = 1000/Vizcacha2.disp.panel.RefreshRate;
    boolean loop_condition = true;
    
    
    public long start_time = 0;
    public long end_time;
    public long duration_ms;
    
    @Override
    public void run()
    {
        while(loop_condition)
        {
            try {VizPanel.semaphore.acquire();} 
            catch (InterruptedException ex) {}
            
            VizPanel.semaphore.release();
            
            start_time = System.nanoTime();
            
            Vizcacha2.disp.panel.repaint();

            end_time=System.nanoTime();
            duration_ms=(end_time-start_time)/1000000;
            
            while (duration_ms<(1000/Vizcacha2.disp.panel.RefreshRate))
            {
                try 
                {
                    Thread.sleep((1000/Vizcacha2.disp.panel.RefreshRate) - duration_ms);
                    end_time=System.nanoTime();
                    duration_ms=(end_time-start_time)/1000000;
                } catch (InterruptedException ex) {
                    Logger.getLogger(VizPanel_Updater.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            end_time=System.nanoTime();
            duration_ms=(end_time-start_time)/1000000;
            //System.out.println("calculation done" + duration_ms);
        }
    }
    
    public void end()
    {
        loop_condition = false;
    }
}

package vizcacha2;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Display 
{
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice[] devices = ge.getScreenDevices();
    int deviceId = 1;
    
    //GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    JFrame frame;
    
    public VizPanel panel;
    
    public Display()
    {
        deviceId = Vizcacha2.config_reader.screen_select;
        GraphicsDevice device = devices[deviceId-1];
        int screen_width = devices[deviceId-1].getDisplayMode().getWidth();
        int screen_height = devices[deviceId-1].getDisplayMode().getHeight();
        //test
        
        frame = new JFrame("Display Mode");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        
        panel = new VizPanel();
        panel.PassScreenDimensions(screen_width, screen_height);
        
        frame.setLayout(null);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        
        device.setFullScreenWindow(frame);
    }
    
    public void Display_FullScreen()
    {
        devices[deviceId].setFullScreenWindow(frame);
    }
    
    public void Display_Normal()
    {
        devices[deviceId].setFullScreenWindow(null);
    }
    
    public void closeWindow()
    {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
    
}

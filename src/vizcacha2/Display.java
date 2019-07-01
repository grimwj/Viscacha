package vizcacha2;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Display 
{
    GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    final JFrame frame;
    
    public VizPanel panel;
    
    public Display()
    {
        frame = new JFrame("Display Mode");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        
        panel = new VizPanel();
        
        frame.setLayout(null);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        
        device.setFullScreenWindow(frame);
    }
    
    public void Display_FullScreen()
    {
        device.setFullScreenWindow(frame);
    }
    
    public void Display_Normal()
    {
        device.setFullScreenWindow(null);
    }
    
    public void closeWindow()
    {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
    
}

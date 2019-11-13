package vizcacha2;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Keyboard_inputs 
{
    public Keyboard_Timer timer_thread;
    
    public static int KEYBOARD_ESC;
    
    public static int KEYBOARD_LEFT;
    public static int KEYBOARD_TOP;
    public static int KEYBOARD_RIGHT;
    public static int KEYBOARD_DOWN;
    
    public static int KEYBOARD_SPACE;
    
    public static int KEYBOARD_S;
    public static int KEYBOARD_T;
    public static int KEYBOARD_ALT;
    
    public boolean S_Active = false;
    public boolean T_Active = true;
    public boolean LR_Active = false;
    
    
    public void Keyboard_Activate_LR()
    {
        LR_Active = true;
    }
    
    public void Keyboard_Deactivate_LR()
    {
        LR_Active = false;
    }
    
    public void Keyboard_Activate_S()
    {
        S_Active = true;
    }
    
    public void Keyboard_Deactivate_S()
    {
        S_Active = false;
    }
    
    public void Keyboard_Activate_T()
    {
        T_Active = true;
    }
    
    public void Keyboard_Deactivate_T()
    {
        T_Active = false;
    }    
    
    public Keyboard_inputs()
    {
        KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager(); 
    
        kfm.addKeyEventDispatcher(new KeyEventDispatcher() 
        { 

            @Override 
            public boolean dispatchKeyEvent(KeyEvent arg0) 
            { 
                if (arg0.getID() == KeyEvent.KEY_PRESSED)
                {
                    if (arg0.getKeyCode()==KEYBOARD_ESC)
                    {
                        Keyboard_ESC_Action();
                        return true;
                    }

                    if (arg0.getKeyCode()==KEYBOARD_LEFT && LR_Active==true)
                    {
                        Keyboard_Deactivate_LR();
                        Keyboard_LEFT_Action();
                        return true;
                    }
                    if (arg0.getKeyCode()==KEYBOARD_TOP)
                    {
                        try 
                        {
                            Keyboard_TOP_Action();
                        } 
                        catch (InterruptedException ex) 
                        {
                            Logger.getLogger(Keyboard_inputs.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        return true;
                    }
                    if (arg0.getKeyCode()==KEYBOARD_RIGHT && LR_Active==true)
                    {
                        Keyboard_Deactivate_LR();
                        Keyboard_RIGHT_Action();
                        return true;
                    }
                    if (arg0.getKeyCode()==KEYBOARD_DOWN)
                    {
                        Keyboard_DOWN_Action();
                        return true;
                    }

                    if (arg0.getKeyCode()==KEYBOARD_SPACE)
                    {
                        Keyboard_SPACE_Action();
                        return true;
                    }
                    
                    if (arg0.getKeyCode()==KEYBOARD_ALT)
                    {
                        Keyboard_ALT_Action();
                        return true;
                    }
                    
                    if (arg0.getKeyCode()==KEYBOARD_S)
                    {
                        if (S_Active==true)
                            Keyboard_S_Action();
                        
                        Keyboard_Deactivate_S();
                        
                        String msg = "Scanner trigger received";
                        Vizcacha2.trackerComm.SendMessage(msg);
                        Vizcacha2.trials.Trials_CVS_LOG_StartPressed();
                        Vizcacha2.logger.MyLogger_WriteLine(msg);
                        
                        return true;
                    }
                    
                    if (arg0.getKeyCode()==KEYBOARD_T && T_Active==true)
                    {
                        Keyboard_Deactivate_T();
                        Keyboard_T_Action();
                        
                        timer_thread = new Keyboard_Timer();
                        timer_thread.SetTimeMs(Vizcacha2.config_reader.feeding_time);
                        timer_thread.start();
                        
                        return true;
                    }                    
                }                                                                            
               
                return false;
            } 

        }); 
    }
    
    private void Keyboard_ESC_Action()
    {
        Vizcacha2.logger.MyLogger_WriteLine("ESC pressed");
        Vizcacha2.trackerComm.CloseDataFile();
        
        String filename = Vizcacha2.config_reader.patient_name;
        filename = filename.concat("_");
        filename = filename.concat(Vizcacha2.writer.filename);
        String filename_tmp = "TMP";
        Vizcacha2.trackerComm.ReceiveDataFile(filename_tmp, filename);
        Vizcacha2.trackerComm.FinishRecording();
        
        Vizcacha2.serialComm.CloseSerialComm();
        Vizcacha2.disp.closeWindow();
    }

    private void Keyboard_LEFT_Action()
    {
        Vizcacha2.logger.MyLogger_WriteLine("Left key pressed");
        Vizcacha2.trackerComm.SendMessage("Left stimuli chosen.");
        System.out.println("Left key pressed");
        Vizcacha2.trials.Trials_CVS_LOG_LeftPressed();
        Vizcacha2.trials.Trial_PassResponse("l");
    }
    private void Keyboard_TOP_Action() throws InterruptedException
    {
        Vizcacha2.logger.MyLogger_WriteLine("Top key pressed");
        System.out.println("Top key pressed");
    }
    private void Keyboard_RIGHT_Action()
    {
        Vizcacha2.logger.MyLogger_WriteLine("Right key pressed");
        Vizcacha2.trackerComm.SendMessage("Right stimuli chosen.");
        System.out.println("Right key pressed");
        Vizcacha2.trials.Trials_CVS_LOG_RightPressed();
        Vizcacha2.trials.Trial_PassResponse("r");
    }
    private void Keyboard_DOWN_Action()
    {
        Vizcacha2.logger.MyLogger_WriteLine("Down key pressed");
        System.out.println("Down key pressed");
    }

    private void Keyboard_SPACE_Action()
    {
        Vizcacha2.logger.MyLogger_WriteLine("SPACE pressed");
        System.out.println("Space key pressed");
        Vizcacha2.trials.Trial_PassExperimentatorRemark("E");
    }
    
    private void Keyboard_ALT_Action()
    {
        Vizcacha2.logger.MyLogger_WriteLine("ALT key pressed");
        System.out.println("ALT key pressed");
        Vizcacha2.trials.Trial_PassStimuliOccurrence("S");
    }

    private void Keyboard_S_Action()
    {
        Vizcacha2.logger.MyLogger_WriteLine("S key pressed");
        System.out.println("S key pressed");
        
        Vizcacha2.trials.Trials_ReleaseSemaphore();
    }
    
    private void Keyboard_T_Action()
    {
        Vizcacha2.logger.MyLogger_WriteLine("T key pressed");
        System.out.println("T key pressed");
        Vizcacha2.serialComm.SendFeederCMD();
    }    

}



class Keyboard_Timer extends Thread implements Runnable 
{
    int refresh_period = 25;
    boolean loop_condition = true;
    
    public long start_time = 0;
    public long end_time;
    public long duration_ms = 0;
    public long time_ms = 0;
    
    public void run()
    {
        start_time = System.nanoTime();
        while(loop_condition)
        {   
            try 
            {
                Thread.sleep(refresh_period);
            } catch (InterruptedException ex) {Logger.getLogger(VizPanel_Updater.class.getName()).log(Level.SEVERE, null, ex);}
            
            end_time=System.nanoTime();
            duration_ms=(end_time-start_time)/1000000;
            
            if (duration_ms >= time_ms)
            {
                Vizcacha2.keyboard.Keyboard_Activate_T();
                break;
            }
                
        }
    }
    
    public void end()
    {
        loop_condition = false;
    }
    
    public void SetTimeMs(long t)
    {
        time_ms = t;
    }
}
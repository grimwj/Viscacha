package vizcacha2;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Trials 
{
    public static Semaphore trials_semaphore;
    public Trials_Timer timer_thread;
    
    private long wait_time_ms;
    
    private int reversals, trials;
    
    // 1 for left, 2 for right
    private int current_positive = 0;
    
    public String selection_side = "x";
    public String selection_positive = "x";
    public String reversal_flag="0";
    public String last_success = "1";
    private String experimentator="";
    private String stimuli="";
    
    public double current_staircase_factor=0;
    
    public Random r;
    
    double ThresholdValue;
    
    public double ValueHistory[];
    public double ThresholdHistory[];
    
    public int ReversalOccurrences[];
    
    private int left_positive_counter=0;
    private int right_positive_counter=0;
    public int max_side_in_row=3;
    
    public Trials()
    {
        r = new Random();
    }
    
    public int ex_nbr;
    
    public void Trials_Staircase_Begin()
    {
        Vizcacha2.logger.MyLogger_WriteLine("New staircase experiment begins");
        
        trials_semaphore = new Semaphore(1, true);
        // loop for experiment count
        for(ex_nbr = 0; ex_nbr < Vizcacha2.reader.Experiment_Repeat; ex_nbr++)
        {   
            Vizcacha2.keyboard.Keyboard_Activate_S();

            try 
            {
                trials_semaphore.acquire();
            } catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}

            Vizcacha2.disp.panel.VizPanel_WaitForStart(0);

            try 
            {
                trials_semaphore.acquire();
            } catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}

            selection_side = "none";
            selection_positive = "none";
            reversal_flag="false";
            last_success = "none";

            ValueHistory = new double[Vizcacha2.reader.Staircase_Definition_Max_Trials];
            ThresholdHistory = new double[Vizcacha2.reader.Staircase_Definition_Max_Trials];

            ReversalOccurrences = new int[Vizcacha2.reader.Staircase_Definition_Max_Reversals];

            Vizcacha2.writer.MyFileWriter_WriteLine("Starting experiment number " + (ex_nbr+1) + "...");

            Vizcacha2.writer.MyFileWriter_WriteLine("Trial" + ";" + "Duration" + ";" + "Selection" + ";" + "Correct" + ";" + "Success" + ";" + "Experimentator" + ";" + "External Stimuli" + ";" + "Reversal" + ";" + "Staircase level");

            reversals = 0;
            trials = 0;

            current_staircase_factor = Trial_StaircaseFactorNormalized(Vizcacha2.reader.Staircase_Definition_Start);

            // Loop for single experiment
            while ((reversals < Vizcacha2.reader.Staircase_Definition_Max_Reversals) && (trials < Vizcacha2.reader.Staircase_Definition_Max_Trials))
            {
                // Display black screen
                Vizcacha2.disp.panel.VizPanel_ChangeScreen(0);
                // Screen change delay
                timer_thread = new Trials_Timer();
                wait_time_ms = (long) (1000*Vizcacha2.reader.Trial_Times_Initial_Screen_Change_Delay);
                timer_thread.SetTimeMs(wait_time_ms);
                timer_thread.start();
                try 
                {
                    trials_semaphore.acquire();
                } catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}

                // Init and display trial. Trial has to release thread semaphore after action performed
                Trial_Staircase(trials);

                trials++;
            }

            ThresholdValue = 0;
            // calculate threshold
            //int tmp_max = Vizcacha2.reader.Staircase_Definition_Max_Reversals - 1;
            int tmp_max = reversals-1;
            int tmp_min = tmp_max - Vizcacha2.reader.Staircase_Definition_Min_Reversals;

            System.out.println(tmp_max);
            System.out.println(tmp_min);

            for (int jj = tmp_max; jj>tmp_min;jj--)
            {
                ThresholdValue += ValueHistory[ReversalOccurrences[jj]];
            }

            //ThresholdValue = 1;
            ThresholdValue = ThresholdValue/Vizcacha2.reader.Staircase_Definition_Min_Reversals;

            if (reversals<Vizcacha2.reader.Staircase_Definition_Min_Reversals)
                ThresholdValue = 0;

            for (int ii=0; ii<trials;ii++)
            {
                ThresholdHistory[ii] = ThresholdValue;
            }

            Vizcacha2.writer.MyFileWriter_WriteLine(Vizcacha2.reader.PARAM + " value for positive stimuli: " + Vizcacha2.disp.panel.positive_value);
            Vizcacha2.writer.MyFileWriter_WriteLine(Vizcacha2.reader.PARAM + " starting value for negative stimuli: " + Vizcacha2.reader.Staircase_Definition_Start);

            Vizcacha2.writer.MyFileWriter_WriteLine("Trial number" + ";" + "PARAM" + ";" + "THRESHOLD" + ";" + "POSITIVE" + ";");

            for (int ii=0; ii<trials;ii++)
            {
                String tmp = new String(ii+1 + ";" + ValueHistory[ii] + ";" + ThresholdHistory[ii] + ";" + Vizcacha2.disp.panel.positive_value + ";");
                tmp = tmp.replace('.', ',');
                Vizcacha2.writer.MyFileWriter_WriteLine(tmp);
            }

            // Threshold calc debug
//            for (int jj = tmp_max; jj>tmp_min;jj--)
//            {
//                Vizcacha2.writer.MyFileWriter_WriteLine(Double.toString(ValueHistory[ReversalOccurrences[jj]]));
//                Vizcacha2.writer.MyFileWriter_WriteLine(Integer.toString(ReversalOccurrences[jj]));
//            }            
            
            // Threshold calc delay
            timer_thread = new Trials_Timer();
            wait_time_ms = (long) (1000*Vizcacha2.reader.Trial_Times_Initial_Threshold_Calc_Delay);
            timer_thread.SetTimeMs(wait_time_ms);
            timer_thread.start();
            //System.out.println("\t\t\t\t\t\t\t\t\t\t wait time: " + wait_time_ms);
            try 
            {
                trials_semaphore.acquire();
            } catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}

            trials_semaphore.release();
        }
        
        if (Vizcacha2.config_reader.sweep_files<=0)
        {
            Vizcacha2.disp.panel.PaintBlackScreenUntilEnd();
            //Vizcacha2.disp.frame.dispatchEvent(new WindowEvent(Vizcacha2.disp.frame, WindowEvent.WINDOW_CLOSING));
        }
        
        Vizcacha2.LoadAndBeginStimuli();
    }
    
    private void Trial_Staircase(int trials)
    {
        Vizcacha2.logger.MyLogger_WriteLine("New staricase trial begins");
        Vizcacha2.trackerComm.SendMessage("Staircase trial number " + trials + ".");
        
        current_positive = Trials_Randomize();
        selection_side = "none";
        selection_positive = "false";
        experimentator = "";
        stimuli = "";
        
        // Transfer data do Panel & display
        // Poza wyswietlaniem musi zostac przekazana informacja o wcisnietym klawiszu
        // liczenie rewersali- potrzebne sledzenie historii zmian
        Vizcacha2.disp.panel.New_Trial_Panel(current_positive, current_staircase_factor, 0, LoadExperimentData.Experiment_Type_Staircase_String);

        ValueHistory[trials] = Vizcacha2.disp.panel.param_value;
        
        // Max ans time delay
        timer_thread = new Trials_Timer();
        wait_time_ms = (long) (1000*Vizcacha2.reader.Trial_Times_Initial_Max_Answer_Time);
        timer_thread.SetTimeMs(wait_time_ms);
        timer_thread.start();
        
        try {Thread.sleep(10);} 
        catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}
        
        Vizcacha2.keyboard.Keyboard_Activate_LR();
        
        try 
        {
            trials_semaphore.acquire();
        } catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}

        // tutaj podac karme

        String current_positive_str = "l";
        if (current_positive==2)
            current_positive_str = "r";

        if ( (selection_side.equals("r")) && (current_positive_str.equals("r")) )
        {
            Vizcacha2.serialComm.SendFeederCMD();
        }
        
        if ( (selection_side.equals("l")) && (current_positive_str.equals("l")) )
        {
            Vizcacha2.serialComm.SendFeederCMD();
        }
        
        selection_positive = "x";
        reversal_flag = "0";

        if ((current_positive==1) && (selection_side=="r"))
            selection_positive = "0";
        if ((current_positive==2) && (selection_side=="l"))
            selection_positive = "0";

        if ((current_positive==1) && (selection_side=="l"))
            selection_positive = "1";
        if ((current_positive==2) && (selection_side=="r"))
            selection_positive = "1";
        
        
        if ((last_success != selection_positive) && (trials!=0) && (selection_side!="none"))
        {
            ReversalOccurrences[reversals] = trials;
            reversals++;
            reversal_flag = "1";
            System.out.println("Reversal occured");
        }

        if (selection_side!="none")
            last_success = selection_positive;

        
        //System.out.println("/t/t/t/t/t/t " + current_staircase_factor + "selection positive= " + selection_positive);
        
        //System.out.println("/t/t/t/t/t/t " + current_staircase_factor);
        
        if ((current_positive==1) && (selection_side=="l"))
        {
            selection_positive = "1";
            timer_thread = new Trials_Timer();
            wait_time_ms = (long) (1000*Vizcacha2.reader.Trial_Times_Initial_Delay_After_Positive);
            timer_thread.SetTimeMs(wait_time_ms);
            timer_thread.start();
            try 
            {
                trials_semaphore.acquire();
            } catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}
        }
        if ((current_positive==2) && (selection_side=="r"))
        {
            selection_positive = "1";
            timer_thread = new Trials_Timer();
            wait_time_ms = (long) (1000*Vizcacha2.reader.Trial_Times_Initial_Delay_After_Positive);
            timer_thread.SetTimeMs(wait_time_ms);
            timer_thread.start();
            try 
            {
                trials_semaphore.acquire();
            } catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}
        }
        
        String str = (trials+1) + ";" + ((double) timer_thread.duration_ms)/1000 + ";" + selection_side + ";" + current_positive_str + ";" + selection_positive + ";" + experimentator + ";" + stimuli + ";" + reversal_flag + ";" + current_staircase_factor;
        str = str.replace('.', ',');
        Vizcacha2.writer.MyFileWriter_WriteLine(str);
        
        if (selection_positive == "1")
        {
            current_staircase_factor /= (1 + Vizcacha2.reader.Staircase_Definition_Factor);
            //System.out.println("/t/t/t/t/t/t Decreasing, change factor=" + Vizcacha2.reader.Staircase_Definition_Factor + " current factor= "+ current_staircase_factor);
            if (current_staircase_factor<=0) current_staircase_factor=0;
        }
        else
        {
            current_staircase_factor *= (1 + Vizcacha2.reader.Staircase_Definition_Factor);
            //System.out.println("/t/t/t/t/t/t Increasing, change factor=" + Vizcacha2.reader.Staircase_Definition_Factor + " current factor= "+ current_staircase_factor);
            if (current_staircase_factor>=1) current_staircase_factor=1;
        }        
        
        trials++;
    }
    
    public void Trial_PassResponse(String response)
    {
        System.out.println("trail response passed");
        selection_side = response;
        timer_thread.end();
        //trials_semaphore.release();
    }
    
    public void Trial_PassExperimentatorRemark(String response)
    {
        experimentator = response;
    }
    
    public void Trial_PassStimuliOccurrence(String response)
    {
        stimuli = response;
    }
    
    public double Trial_StaircaseFactorNormalized(double param)
    {
        double hard = Vizcacha2.reader.Staircase_Definition_Hardest;
        double easy = Vizcacha2.reader.Staircase_Definition_Easiest;
        
        return (param-hard)/(easy-hard);
    }
    
    public double Trial_StaircaseValue(double norm)
    {
        double hard = Vizcacha2.reader.Staircase_Definition_Hardest;
        double easy = Vizcacha2.reader.Staircase_Definition_Easiest;
        
        return hard + norm*(easy-hard);
    }
    
    
    
    
    
    float correct_percent=0;
    float correct_percent_total=0;
    
    public void Trials_Constant_Begin()
    {
        Vizcacha2.logger.MyLogger_WriteLine("New constant experiment begins");
        
        trials_semaphore = new Semaphore(1, true);
        // loop for experiment count
        
        for(ex_nbr = 0; ex_nbr < Vizcacha2.reader.Experiment_Repeat; ex_nbr++)
        {   
            Vizcacha2.keyboard.Keyboard_Activate_S();
            
            selection_side = "none";
            selection_positive = "none";
            reversal_flag="false";
            last_success = "none";
            experimentator = "";
            
            int max_trials = Vizcacha2.reader.Constant_Definition_Level.length * Vizcacha2.reader.Constant_Definition_Repeat;
            
            ValueHistory = new double[max_trials];
            
            Vizcacha2.writer.MyFileWriter_WriteLine("Starting experiment number " + (ex_nbr+1) + "...");
            Vizcacha2.writer.MyFileWriter_WriteLine("Trial" + ";" + "Duration" + ";" + "Selection" + ";" + "Correct" + ";" + "Success" + ";" + "Experimentator" + ";" + "External Stimuli" + ";" + "Constant value");
            
            Vizcacha2.disp.panel.VizPanel_WaitForStart(0);
            
            try {trials_semaphore.acquire();} 
            catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}

            try {trials_semaphore.acquire();} 
            catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}
            
            trials = 0; 
            
            // Loop for single experiment
            while (trials < max_trials)
            {
                // Display black screen
                Vizcacha2.disp.panel.VizPanel_ChangeScreen(0);
                // Screen change delay
                timer_thread = new Trials_Timer();
                wait_time_ms = (long) (1000*Vizcacha2.reader.Trial_Times_Initial_Screen_Change_Delay);
                timer_thread.SetTimeMs(wait_time_ms);
                timer_thread.start();
                try 
                {
                    trials_semaphore.acquire();
                } catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}

                // Init and display trial. Trial has to release thread semaphore after action performed
                Trial_Constant(trials);
                
                trials++;
            }
            
            Vizcacha2.disp.panel.VizPanel_ChangeScreen(0);
            
            correct_percent_total *= 100;
            int tmp_trial_count = Vizcacha2.reader.Constant_Definition_Repeat * Vizcacha2.reader.Constant_Definition_Level.length;
            correct_percent_total /= tmp_trial_count;

            String correct_percent_total_str = String.format ("%.2f", correct_percent_total);

            String str = "Overall success rate:" + ";" + "---" + ";" + "---" + ";" + "---" + ";" + correct_percent_total_str + ";" + "---" + ";" + "---" + ";" + ValueHistory[trials-1];
            str = str.replace('.', ',');
            Vizcacha2.writer.MyFileWriter_WriteLine("");
            Vizcacha2.writer.MyFileWriter_WriteLine(str);

            correct_percent_total=0;
            
            Vizcacha2.writer.MyFileWriter_WriteLine("");
            Vizcacha2.writer.MyFileWriter_WriteLine("");
            Vizcacha2.writer.MyFileWriter_WriteLine(Vizcacha2.reader.PARAM + " value for positive stimuli: " + Vizcacha2.disp.panel.positive_value);
            
            // Threshold calc delay
            timer_thread = new Trials_Timer();
            wait_time_ms = (long) (1000*Vizcacha2.reader.Trial_Times_Initial_Threshold_Calc_Delay);
            timer_thread.SetTimeMs(wait_time_ms);
            timer_thread.start();
            //System.out.println("\t\t\t\t\t\t\t\t\t\t wait time: " + wait_time_ms);
            try 
            {
                trials_semaphore.acquire();
            } catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}
            
            trials_semaphore.release();
        }
        
        if (Vizcacha2.config_reader.sweep_files<=0)
        {
            Vizcacha2.disp.panel.PaintBlackScreenUntilEnd();
        }
    
        Vizcacha2.LoadAndBeginStimuli();
    }
    
    private void Trial_Constant(int trials)
    {
        Vizcacha2.trackerComm.SendMessage("Constant trial number " + trials + ".");
        Vizcacha2.logger.MyLogger_WriteLine("New constant trial begins");
        
        System.out.println("Trial begins..." + trials + "max: " + (Vizcacha2.reader.Constant_Definition_Level.length * Vizcacha2.reader.Constant_Definition_Repeat));
        
        Vizcacha2.keyboard.Keyboard_Activate_LR();
        
        current_positive = Trials_Randomize();
        selection_side = "none";
        selection_positive = "false";
        experimentator = "";
        stimuli = "";
        // Transfer data do Panel & display
        // Poza wyswietlaniem musi zostac przekazana informacja o wcisnietym klawiszu
        // liczenie rewersali- potrzebne sledzenie historii zmian
        int index = (int) (trials / Vizcacha2.reader.Constant_Definition_Repeat);
        double current_constant_factor = Vizcacha2.reader.Constant_Definition_Level[index];
        
        if (Vizcacha2.config_reader.overwrite_level != null)
        {
            current_constant_factor = Vizcacha2.config_reader.overwrite_level[index];
        }
        Vizcacha2.disp.panel.New_Trial_Panel(current_positive, 0, current_constant_factor, LoadExperimentData.Experiment_Type_Constant_String);

        ValueHistory[trials] = Vizcacha2.disp.panel.param_value;

        // Max ans time delay
        timer_thread = new Trials_Timer();
        wait_time_ms = (long) (1000*Vizcacha2.reader.Trial_Times_Initial_Max_Answer_Time);
        timer_thread.SetTimeMs(wait_time_ms);
        timer_thread.start();
        
        try {Thread.sleep(10);} 
        catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}
        
        Vizcacha2.keyboard.Keyboard_Activate_LR();
        
        try 
        {
            trials_semaphore.acquire();
        } catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}

        // tutaj podac karme
        
        String current_positive_str = "l";
        if (current_positive==2)
            current_positive_str = "r";

        if ( (selection_side.equals("r")) && (current_positive_str.equals("r")) )
        {
            Vizcacha2.serialComm.SendFeederCMD();
        }
        
        if ( (selection_side.equals("l")) && (current_positive_str.equals("l")) )
        {
            Vizcacha2.serialComm.SendFeederCMD();
        }
        
        if (selection_side!="none")
            last_success = selection_positive;
        
        selection_positive = "x";
        reversal_flag = "0";

        if ((current_positive==1) && (selection_side=="l"))
        {
            selection_positive = "1";
            
            timer_thread = new Trials_Timer();
            wait_time_ms = (long) (1000*Vizcacha2.reader.Trial_Times_Initial_Delay_After_Positive);
            timer_thread.SetTimeMs(wait_time_ms);
            timer_thread.start();
            try 
            {
                trials_semaphore.acquire();
            } catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}
        }
        if ((current_positive==2) && (selection_side=="r"))
        {
            selection_positive = "1";
            
            timer_thread = new Trials_Timer();
            wait_time_ms = (long) (1000*Vizcacha2.reader.Trial_Times_Initial_Delay_After_Positive);
            timer_thread.SetTimeMs(wait_time_ms);
            timer_thread.start();
            try 
            {
                trials_semaphore.acquire();
            } catch (InterruptedException ex) {Logger.getLogger(Trials.class.getName()).log(Level.SEVERE, null, ex);}            
        }
        
        if ((current_positive==1) && (selection_side=="r"))
            selection_positive = "0";
        if ((current_positive==2) && (selection_side=="l"))
            selection_positive = "0";

        if (selection_positive == "1")
        {
            correct_percent++;
            correct_percent_total++;
        }
            
        String str = (trials+1) + ";" + ((double) timer_thread.duration_ms)/1000 + ";" + selection_side + ";" + current_positive_str + ";" + selection_positive + ";" + experimentator + ";" + stimuli + ";" + ValueHistory[trials];
        str = str.replace('.', ',');
        Vizcacha2.writer.MyFileWriter_WriteLine(str);
        
        if ( ((trials+1)%Vizcacha2.reader.Constant_Definition_Repeat) == 0)
        {
            correct_percent *= 100;
            correct_percent /= Vizcacha2.reader.Constant_Definition_Repeat;
            
            String correct_percent_str = String.format ("%.2f", correct_percent);
            
            str = "Most recent " + Vizcacha2.reader.Constant_Definition_Repeat + ":" + ";" + "---" + ";" + "---" + ";" + "---" + ";" + correct_percent_str + ";" + "---" + ";" + "---" + ";" + ValueHistory[trials];
            str = str.replace('.', ',');
            Vizcacha2.writer.MyFileWriter_WriteLine(str);
            
            correct_percent=0;
        }
    }
    
    
    
    private int Trials_Randomize()
    {
        int ret = 1 + r.nextInt(2);
        
        if (ret==1)
        {
            right_positive_counter=0;
            left_positive_counter++;
            if (left_positive_counter>max_side_in_row)
            {
                left_positive_counter=0;
                right_positive_counter=1;
                ret=2;
            }
        }
        else
        {
            right_positive_counter++;
            left_positive_counter=0;
            if (right_positive_counter>max_side_in_row)
            {
                right_positive_counter=0;
                left_positive_counter=1;
                ret=1;
            }
        }
        return ret;
    }
    
    public void Trials_ReleaseSemaphore()
    {
        trials_semaphore.release();
    }
}











class Trials_Timer extends Thread implements Runnable 
{
    int refresh_period = 50;
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
                break;
        }

        Trials.trials_semaphore.release();

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

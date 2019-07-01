package vizcacha2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadExperimentData 
{
    public String filename="";
    private static final String PARAM_String = "PARAM";
    public String PARAM="";
    public int AutomatonPARAM;
    
    public static final String Experiment_Type_Constant_String = "Constant";
    public static final String Experiment_Type_Staircase_String = "Staircase";
    public String Experiment_Type;
    
    public static final String Background_String = "Background";
    public static final String Experiment_Repeat_String = "Experiment_Repeat";
    public static final String Experiment_Type_String = "Experiment_Type";

    public static final String Positive_String = "Positive";
    public static final String Negative_String = "Negative";
    
    public static final String Constant_Definition_String = "Constant_Definition";
    public static final String Staircase_Definition_String = "Staircase_Definition";
    public static final String Trial_Times_String = "Trial_Times";
    
    //stimuli
    public static final String Stimuli_Change_In_Both_String = "Change_In_Both";
    public static final String Stimuli_Background_String = "Background";
    public static final String Stimuli_Shape_String = "Shape";
    public static final String Stimuli_Noise_String = "Noise";
    
    //stimuli background 
    public static final String Stimuli_Background_Type_String = "Type";
    public static final String Stimuli_Background_Speed_String = "Speed";
    public static final String Stimuli_Background_Direction_String = "Direction";
    public static final String Stimuli_Background_Coherence_String = "Coherence";
    public static final String Stimuli_Background_Speed_X_String = "Speed_X";
    public static final String Stimuli_Background_Speed_Y_String = "Speed_Y";
    public static final String Stimuli_Background_Dot_Size_String = "Dot_Size";
    public static final String Stimuli_Background_Dot_Color_String = "Dot_Color";
    public static final String Stimuli_Background_Dot_Color_2_String = "Dot_Color_2";
    public static final String Stimuli_Background_Dot_Color_Threshold_String = "Dot_Color_Threshold";
    public static final String Stimuli_Background_Dot_Max_Count_String = "Dot_Max_Count";
    public static final String Stimuli_Background_Life_Time_String = "Life_Time";
    
    //stimuli shape
    public static final String Stimuli_Shape_Background_Color_String = "Background_Color";
    
    public static final String Stimuli_Shape_Horizontal_Offset_String = "Horizontal_Offset";
    public static final String Stimuli_Shape_Field_Scale_String = "Field_Scale";
    public static final String Stimuli_Shape_Scale_String = "Scale";
    public static final String Stimuli_Shape_Ellipse_X_String = "Ellipse_X";
    public static final String Stimuli_Shape_Ellipse_Y_String = "Ellipse_Y";
    public static final String Stimuli_Shape_Type_String = "Type";
    public static final String Stimuli_Shape_Medium_Value_String = "Medium_Value";
    public static final String Stimuli_Shape_Amplitude_String = "Amplitude";
    public static final String Stimuli_Shape_Speed_String = "Speed";
    public static final String Stimuli_Shape_Direction_String = "Direction";
    public static final String Stimuli_Shape_Width_String = "Width";
    public static final String Stimuli_Shape_Duty_String = "Duty";
    public static final String Stimuli_Shape_Mask_String = "Mask";
    
    public static final String Stimuli_Shape_Coherence_String = "Coherence";
    public static final String Stimuli_Shape_Speed_X_String = "Speed_X";
    public static final String Stimuli_Shape_Speed_Y_String = "Speed_Y";
    public static final String Stimuli_Shape_Dot_Size_String = "Dot_Size";
    public static final String Stimuli_Shape_Dot_Color_String = "Dot_Color";
    public static final String Stimuli_Shape_Dot_Color_2_String = "Dot_Color_2";
    public static final String Stimuli_Shape_Dot_Color_Threshold_String = "Dot_Color_Threshold";
    public static final String Stimuli_Shape_Dot_Max_Count_String = "Dot_Max_Count";
    public static final String Stimuli_Shape_Life_Time_String = "Life_Time";
    
    //stimuli noise
    public static final String Stimuli_Noise_Type_String = "Type";
    public static final String Stimuli_Noise_Speed_String = "Speed";
    public static final String Stimuli_Noise_Direction_String = "Direction";
    public static final String Stimuli_Noise_Coherence_String = "Coherence";
    public static final String Stimuli_Noise_Speed_X_String = "Speed_X";
    public static final String Stimuli_Noise_Speed_Y_String = "Speed_Y";
    public static final String Stimuli_Noise_Dot_Size_String = "Dot_Size";
    public static final String Stimuli_Noise_Dot_Color_String = "Dot_Color";
    public static final String Stimuli_Noise_Dot_Color_2_String = "Dot_Color_2";
    public static final String Stimuli_Noise_Dot_Color_Threshold_String = "Dot_Color_Threshold";
    public static final String Stimuli_Noise_Dot_Max_Count_String = "Dot_Max_Count";
    public static final String Stimuli_Noise_Life_Time_String = "Life_Time";
        
    //staircase definition
    public static final String Staircase_Definition_Easiest_String = "Easiest";
    public static final String Staircase_Definition_Hardest_String = "Hardest";
    public static final String Staircase_Definition_Start_String = "Start";
    public static final String Staircase_Definition_Max_Reversals_String = "Max_Reversals";
    public static final String Staircase_Definition_Factor_String = "Factor";
    public static final String Staircase_Definition_Min_Reversals_String = "Min_Reversals";
    public static final String Staircase_Definition_Max_Trials_String = "Max_Trials";
    
    //constant definition
    public static final String Constant_Definition_Level_String = "Level";
    public static final String Constant_Definition_Repeat_String = "Repeat";
    public static final String Constant_Definition_Max_Trials_String = "Max_Trials";
    
    //trial times
    public static final String Trial_Times_Initial_Delay_String = "Initial_Delay";
    public static final String Trial_Times_Initial_Screen_Change_Delay_String = "Screen_Change_Delay";
    public static final String Trial_Times_Initial_Max_Answer_Time_String = "Max_Answer_Time";
    public static final String Trial_Times_Initial_Delay_After_Positive_String = "Delay_After_Positive";
    public static final String Trial_Times_Initial_Threshold_Calc_Delay_String = "Threshold_Calc_Delay";
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    int Automaton_State;
    public static final int Automaton_State_Default = 0;
    public static final int Automaton_State_Positive = 1;
    public static final int Automaton_State_Positive_Background = 10;
    public static final int Automaton_State_Positive_Shape = 11;
    public static final int Automaton_State_Positive_Noise = 12;
    public static final int Automaton_State_Negative = 2;
    public static final int Automaton_State_Negative_Background = 20;
    public static final int Automaton_State_Negative_Shape = 21;
    public static final int Automaton_State_Negative_Noise = 22;
    public static final int Automaton_State_Staircase = 3;
    private static final int Automaton_State_Constant = 4;
    private static final int Automaton_State_Trial = 5;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public int Background = 0;
    public int Experiment_Repeat = 1;
    
    //staircase definition
    public double Staircase_Definition_Easiest;
    public double Staircase_Definition_Hardest;
    public double Staircase_Definition_Start;
    public int Staircase_Definition_Max_Reversals;
    public double Staircase_Definition_Factor;
    public int Staircase_Definition_Min_Reversals;
    public int Staircase_Definition_Max_Trials;
    
    //constant definition
    public double[] Constant_Definition_Level;
    public int Constant_Definition_Repeat;
    public int Constant_Definition_Max_Trials;
    
    //trial times
    public double Trial_Times_Initial_Delay;
    public double Trial_Times_Initial_Screen_Change_Delay;
    public double Trial_Times_Initial_Max_Answer_Time;
    public double Trial_Times_Initial_Delay_After_Positive;
    public double Trial_Times_Initial_Threshold_Calc_Delay;
    
    public int Change_In_Both=0;
    
    //Negative stimuli background 
    public String Negative_Background_Type;
    public double Negative_Background_Speed;
    public double Negative_Background_Direction;
    public double Negative_Background_Coherence;
    public double Negative_Background_Speed_X;
    public double Negative_Background_Speed_Y;
    public int Negative_Background_Dot_Size;
    public int Negative_Background_Dot_Color;
    public int Negative_Background_Dot_Color_2;
    public double Negative_Background_Dot_Color_Threshold=1;
    public int Negative_Background_Dot_Max_Count;
    public double Negative_Background_Life_Time;
    
    //Negative stimuli shape
    public int Negative_Shape_Horizontal_Offset=-1;
    public double Negative_Shape_Field_Scale=1;
    public double Negative_Shape_Scale=1;
    public int Negative_Shape_Background_Color;
    public int Negative_Shape_Ellipse_X=0;
    public int Negative_Shape_Ellipse_Y=0;
    public String Negative_Shape_Type;
    public double Negative_Shape_Medium_Value;
    public double Negative_Shape_Amplitude;
    public int Negative_Shape_Speed;
    public int Negative_Shape_Direction=0;
    public int Negative_Shape_Width=0;
    public double Negative_Shape_Duty=0;
    public int Negative_Shape_Mask;
    public double Negative_Shape_Coherence;
    public double Negative_Shape_Speed_X;
    public double Negative_Shape_Speed_Y;
    public int Negative_Shape_Dot_Size;
    public int Negative_Shape_Dot_Color;
    public int Negative_Shape_Dot_Color_2;
    public double Negative_Shape_Dot_Color_Threshold=1;
    public int Negative_Shape_Dot_Max_Count;
    public double Negative_Shape_Life_Time;
    
    
    //Negative stimuli noise
    public String Negative_Noise_Type;
    public double Negative_Noise_Speed;
    public double Negative_Noise_Direction;
    public double Negative_Noise_Coherence;
    public double Negative_Noise_Speed_X;
    public double Negative_Noise_Speed_Y;
    public int Negative_Noise_Dot_Size;
    public int Negative_Noise_Dot_Color;
    public int Negative_Noise_Dot_Color_2;
    public double Negative_Noise_Dot_Color_Threshold=1;
    public int Negative_Noise_Dot_Max_Count;
    public double Negative_Noise_Life_Time;
    
    //positive stimuli background 
    public String Positive_Background_Type;
    public double Positive_Background_Speed;
    public double Positive_Background_Direction;
    public double Positive_Background_Coherence;
    public double Positive_Background_Speed_X;
    public double Positive_Background_Speed_Y;
    public int Positive_Background_Dot_Size;
    public int Positive_Background_Dot_Color;
    public int Positive_Background_Dot_Color_2;
    public double Positive_Background_Dot_Color_Threshold=1;
    public int Positive_Background_Dot_Max_Count;
    public double Positive_Background_Life_Time;
    
    //positive stimuli shape
    public int Positive_Shape_Horizontal_Offset=-1;
    public double Positive_Shape_Field_Scale;
    public double Positive_Shape_Scale;
    public int Positive_Shape_Background_Color;
    public int Positive_Shape_Ellipse_X;
    public int Positive_Shape_Ellipse_Y;
    public String Positive_Shape_Type;
    public double Positive_Shape_Medium_Value;
    public double Positive_Shape_Amplitude;
    public double Positive_Shape_Speed=0;
    public int Positive_Shape_Direction=0;
    public int Positive_Shape_Width;
    public double Positive_Shape_Duty;
    public int Positive_Shape_Mask;
    public double Positive_Shape_Coherence;
    public double Positive_Shape_Speed_X;
    public double Positive_Shape_Speed_Y;
    public int Positive_Shape_Dot_Size;
    public int Positive_Shape_Dot_Color;
    public int Positive_Shape_Dot_Color_2;
    public double Positive_Shape_Dot_Color_Threshold=1;    
    public int Positive_Shape_Dot_Max_Count;
    public double Positive_Shape_Life_Time;
    
    
    //positive stimuli noise
    public String Positive_Noise_Type;
    public double Positive_Noise_Speed;
    public double Positive_Noise_Direction;
    public double Positive_Noise_Coherence;
    public double Positive_Noise_Speed_X;
    public double Positive_Noise_Speed_Y;
    public int Positive_Noise_Dot_Size;
    public int Positive_Noise_Dot_Color;
    public int Positive_Noise_Dot_Color_2;
    public double Positive_Noise_Dot_Color_Threshold=1;
    public int Positive_Noise_Dot_Max_Count;
    public double Positive_Noise_Life_Time;
    
    
    public LoadExperimentData(String filename)
    {
        this.filename = filename;
        Automaton_State = 0;
        
        BufferedReader br = null;
        try 
        {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {Logger.getLogger(LoadExperimentData.class.getName()).log(Level.SEVERE, null, ex);}
        
        try 
        {
            String line = br.readLine();

            while (line != null) 
            {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(LoadExperimentData.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                System.out.println(Automaton_State + " " + line);

                if(line.length()>0)
                {
                    line = line.replaceAll("\\s","");
                    line = line.replaceAll("\\t","");

                    if(line.length()>0)
                    {
                        if (line.charAt(0) !='#')
                        {
                            Automaton_reader(line);
                        }
                    }
                }
                line = br.readLine();
            }
        } 
        catch (IOException ex) {Logger.getLogger(LoadExperimentData.class.getName()).log(Level.SEVERE, null, ex);}
        finally 
        {
            try 
            {
                br.close();
            } catch (IOException ex) {Logger.getLogger(LoadExperimentData.class.getName()).log(Level.SEVERE, null, ex);}
        }
        
        
        System.out.println("reader out");
    }
    
    private void Automaton_reader(String line)
    {
        switch(Automaton_State)
        {
            case Automaton_State_Default:
                Automaton_State_Default_Routine(line);
                break;
            case Automaton_State_Positive:
                Automaton_State_Positive_Routine(line);
                break;
            case Automaton_State_Positive_Background:
                Automaton_State_Positive_Background_Routine(line);
                break;
            case Automaton_State_Positive_Shape:
                Automaton_State_Positive_Shape_Routine(line);
                break;
            case Automaton_State_Positive_Noise:
                Automaton_State_Positive_Noise_Routine(line);
                break;
            case Automaton_State_Negative:
                Automaton_State_Negative_Routine(line);
                break;
            case Automaton_State_Negative_Background:
                Automaton_State_Negative_Background_Routine(line);
                break;
            case Automaton_State_Negative_Shape:
                Automaton_State_Negative_Shape_Routine(line);
                break;
            case Automaton_State_Negative_Noise:
                Automaton_State_Negative_Noise_Routine(line);
                break;
            case Automaton_State_Staircase:
                Automaton_State_Staircase_Routine(line);
                break;
            case Automaton_State_Constant:
                Automaton_State_Constant_Routine(line);
                break;
            case Automaton_State_Trial:
                Automaton_State_Trial_Routine(line);
                break;
        }
    }
    
    private void Automaton_State_Default_Routine(String line)
    {
        if (line.contains(Background_String+"="))
        {
            line = line.replace(Background_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Background_String;
            else
                Background = Integer.parseInt(line);
        }
        if (line.contains(Experiment_Repeat_String+"="))
        {
            line = line.replace(Experiment_Repeat_String+"=", "");
            Experiment_Repeat = Integer.parseInt(line);
        }
        if (line.contains(Experiment_Type_String+"="))
        {
            line = line.replace(Experiment_Type_String+"=", "");
            Experiment_Type = line;
        }
        
        if (line.contains(Positive_String))
        {
            Automaton_State = Automaton_State_Positive;
        }
        if (line.contains(Negative_String))
        {
            Automaton_State = Automaton_State_Negative;
        }

        if (line.contains(Staircase_Definition_String))
        {
            Automaton_State = Automaton_State_Staircase;
        }
        if (line.contains(Constant_Definition_String))
        {
            Automaton_State = Automaton_State_Constant;
        }
        if (line.contains(Trial_Times_String))
        {
            Automaton_State = Automaton_State_Trial;
        }
        
        if (line.contains(Background_String+"="))
        {

        }
    }
    
    private void Automaton_State_Positive_Routine(String line)
    {
        if (line.contains(Stimuli_Background_String))
        {
            Automaton_State = Automaton_State_Positive_Background;
        }
        if (line.contains(Stimuli_Shape_String))
        {
            Automaton_State = Automaton_State_Positive_Shape;
        }
        if (line.contains(Stimuli_Noise_String))
        {
            Automaton_State = Automaton_State_Positive_Noise;
        }
        if (line.contains("}"))
        {
            Automaton_State = Automaton_State_Default;
        }
    }
    
    private void Automaton_State_Negative_Routine(String line)
    {
        if (line.contains(Stimuli_Change_In_Both_String))
        {
            line = line.replace(Stimuli_Change_In_Both_String+"=", "");
            Change_In_Both = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Background_String))
        {
            Automaton_State = Automaton_State_Negative_Background;
        }
        if (line.contains(Stimuli_Shape_String))
        {
            Automaton_State = Automaton_State_Negative_Shape;
        }
        if (line.contains(Stimuli_Noise_String))
        {
            Automaton_State = Automaton_State_Negative_Noise;
        }
        if (line.contains("}"))
        {
            Automaton_State = Automaton_State_Default;
        }
    }
    
    private void Automaton_State_Staircase_Routine(String line)
    {
        if (line.contains(Staircase_Definition_Easiest_String+"="))
        {
            line = line.replace(Staircase_Definition_Easiest_String+"=", "");
            Staircase_Definition_Easiest = Double.parseDouble(line);
        }
        if (line.contains(Staircase_Definition_Hardest_String+"="))
        {
            line = line.replace(Staircase_Definition_Hardest_String+"=", "");
            Staircase_Definition_Hardest = Double.parseDouble(line);
        }
        if (line.contains(Staircase_Definition_Start_String+"="))
        {
            line = line.replace(Staircase_Definition_Start_String+"=", "");
            Staircase_Definition_Start = Double.parseDouble(line);
        }
        if (line.contains(Staircase_Definition_Factor_String+"="))
        {
            line = line.replace(Staircase_Definition_Factor_String+"=", "");
            Staircase_Definition_Factor = Double.parseDouble(line);
        }
        
        if (line.contains(Staircase_Definition_Max_Reversals_String+"="))
        {
            line = line.replace(Staircase_Definition_Max_Reversals_String+"=", "");
            Staircase_Definition_Max_Reversals = Integer.parseInt(line);
        }
        if (line.contains(Staircase_Definition_Min_Reversals_String+"="))
        {
            line = line.replace(Staircase_Definition_Min_Reversals_String+"=", "");
            Staircase_Definition_Min_Reversals = Integer.parseInt(line);
        }
        if (line.contains(Staircase_Definition_Max_Trials_String+"="))
        {
            line = line.replace(Staircase_Definition_Max_Trials_String+"=", "");
            Staircase_Definition_Max_Trials = Integer.parseInt(line);
        }
        if (line.contains("}"))
        {
            Automaton_State = Automaton_State_Default;
        }
    }

    private void Automaton_State_Constant_Routine(String line)
    {
        if (line.contains(Constant_Definition_Level_String+"="))
        {
            line = line.replace(Constant_Definition_Level_String+"=", "");
            
            String [] str = line.split(",");
            
            Constant_Definition_Level = new double[str.length];
            
            for (int i=0;i<str.length;i++)
            {
                Constant_Definition_Level[i] = Double.parseDouble(str[i]);
            }
        }
        if (line.contains(Constant_Definition_Repeat_String+"="))
        {
            line = line.replace(Constant_Definition_Repeat_String+"=", "");
            System.out.println(line);
            Constant_Definition_Repeat = Integer.parseInt(line);
        }
        if (line.contains(Constant_Definition_Max_Trials_String+"="))
        {
            line = line.replace(Constant_Definition_Max_Trials_String+"=", "");
            Constant_Definition_Max_Trials = Integer.parseInt(line);
        }
        if (line.contains("}"))
        {
            Automaton_State = Automaton_State_Default;
        }
    }
    
    private void Automaton_State_Trial_Routine(String line)
    {
        if (line.contains(Trial_Times_Initial_Delay_String+"="))
        {
            line = line.replace(Trial_Times_Initial_Delay_String+"=", "");
            Trial_Times_Initial_Delay = Double.parseDouble(line);
        }
        if (line.contains(Trial_Times_Initial_Screen_Change_Delay_String+"="))
        {
            line = line.replace(Trial_Times_Initial_Screen_Change_Delay_String+"=", "");
            Trial_Times_Initial_Screen_Change_Delay = Double.parseDouble(line);
        }
        if (line.contains(Trial_Times_Initial_Max_Answer_Time_String+"="))
        {
            line = line.replace(Trial_Times_Initial_Max_Answer_Time_String+"=", "");
            Trial_Times_Initial_Max_Answer_Time = Double.parseDouble(line);
        }
        if (line.contains(Trial_Times_Initial_Delay_After_Positive_String+"="))
        {
            line = line.replace(Trial_Times_Initial_Delay_After_Positive_String+"=", "");
            Trial_Times_Initial_Delay_After_Positive = Double.parseDouble(line);
        }
        if (line.contains(Trial_Times_Initial_Threshold_Calc_Delay_String+"="))
        {
            line = line.replace(Trial_Times_Initial_Threshold_Calc_Delay_String+"=", "");
            Trial_Times_Initial_Threshold_Calc_Delay = Double.parseDouble(line);
        }
        if (line.contains("}"))
        {
            Automaton_State = Automaton_State_Default;
        }
    }
    
    private void Automaton_State_Positive_Background_Routine(String line)
    {
        if (line.contains(Stimuli_Background_Type_String+"="))
        {
            line = line.replace(Stimuli_Background_Type_String+"=", "");
            Positive_Background_Type = line;
        }
        if (line.contains(Stimuli_Background_Speed_String+"="))
        {
            line = line.replace(Stimuli_Background_Speed_String+"=", "");
            Positive_Background_Speed = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Background_Direction_String+"="))
        {
            line = line.replace(Stimuli_Background_Direction_String+"=", "");
            Positive_Background_Direction = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Background_Coherence_String+"="))
        {
            line = line.replace(Stimuli_Background_Coherence_String+"=", "");
            Positive_Background_Coherence = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Background_Speed_X_String+"="))
        {
            line = line.replace(Stimuli_Background_Speed_X_String+"=", "");
            Positive_Background_Speed_X = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Background_Speed_Y_String+"="))
        {
            line = line.replace(Stimuli_Background_Speed_Y_String+"=", "");
            Positive_Background_Speed_Y = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Background_Dot_Size_String+"="))
        {
            line = line.replace(Stimuli_Background_Dot_Size_String+"=", "");
            Positive_Background_Dot_Size = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Background_Dot_Color_String+"="))
        {
            line = line.replace(Stimuli_Background_Dot_Color_String+"=", "");
            Positive_Background_Dot_Color = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Background_Dot_Color_2_String+"="))
        {
            line = line.replace(Stimuli_Background_Dot_Color_2_String+"=", "");
            Positive_Background_Dot_Color_2 = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Background_Dot_Color_Threshold_String+"="))
        {
            line = line.replace(Stimuli_Background_Dot_Color_Threshold_String+"=", "");
            Positive_Background_Dot_Color_Threshold = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Background_Dot_Max_Count_String+"="))
        {
            line = line.replace(Stimuli_Background_Dot_Max_Count_String+"=", "");
            Positive_Background_Dot_Max_Count = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Background_Life_Time_String+"="))
        {
            line = line.replace(Stimuli_Background_Life_Time_String+"=", "");
            Positive_Background_Life_Time = Double.parseDouble(line);
        }        
        if (line.contains("}"))
        {
            Automaton_State = Automaton_State_Positive;
        }
    }
    private void Automaton_State_Positive_Shape_Routine(String line)
    {
        if (line.contains(Stimuli_Shape_Horizontal_Offset_String+"="))
        {
            line = line.replace(Stimuli_Shape_Horizontal_Offset_String+"=", "");
            Positive_Shape_Horizontal_Offset = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Field_Scale_String+"="))
        {
            line = line.replace(Stimuli_Shape_Field_Scale_String+"=", "");
            Positive_Shape_Field_Scale = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Scale_String+"="))
        {
            line = line.replace(Stimuli_Shape_Scale_String+"=", "");
            Positive_Shape_Scale = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Background_Color_String+"="))
        {
            line = line.replace(Stimuli_Shape_Background_Color_String+"=", "");
            Positive_Shape_Background_Color = Integer.parseInt(line);
        }        
        if (line.contains(Stimuli_Shape_Ellipse_X_String+"="))
        {
            line = line.replace(Stimuli_Shape_Ellipse_X_String+"=", "");
            Positive_Shape_Ellipse_X = Integer.parseInt(line);
            //System.out.println("\t\t\t\t\t\t\t Positive READ Ellipse_X " + Positive_Shape_Ellipse_X);
        }
        if (line.contains(Stimuli_Shape_Ellipse_Y_String+"="))
        {
            line = line.replace(Stimuli_Shape_Ellipse_Y_String+"=", "");
            Positive_Shape_Ellipse_Y = Integer.parseInt(line);
            //System.out.println("\t\t\t\t\t\t\t Positive READ Ellipse_Y " + Positive_Shape_Ellipse_Y);
        }
        if (line.contains(Stimuli_Shape_Type_String+"="))
        {
            line = line.replace(Stimuli_Shape_Type_String+"=", "");
            Positive_Shape_Type = line;
        }
        if (line.contains(Stimuli_Shape_Medium_Value_String+"="))
        {
            line = line.replace(Stimuli_Shape_Medium_Value_String+"=", "");
            Positive_Shape_Medium_Value = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Amplitude_String+"="))
        {
            line = line.replace(Stimuli_Shape_Amplitude_String+"=", "");
            Positive_Shape_Amplitude = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Speed_String+"="))
        {
            line = line.replace(Stimuli_Shape_Speed_String+"=", "");
            Positive_Shape_Speed = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Direction_String+"="))
        {
            line = line.replace(Stimuli_Shape_Direction_String+"=", "");
            Positive_Shape_Direction = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Width_String+"="))
        {
            line = line.replace(Stimuli_Shape_Width_String+"=", "");
            Positive_Shape_Width = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Duty_String+"="))
        {
            line = line.replace(Stimuli_Shape_Duty_String+"=", "");
            Positive_Shape_Duty = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Mask_String+"="))
        {
            line = line.replace(Stimuli_Shape_Mask_String+"=", "");
            Positive_Shape_Mask = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Coherence_String+"="))
        {
            line = line.replace(Stimuli_Shape_Coherence_String+"=", "");
            Positive_Shape_Coherence = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Speed_X_String+"="))
        {
            line = line.replace(Stimuli_Shape_Speed_X_String+"=", "");
            Positive_Shape_Speed_X = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Speed_Y_String+"="))
        {
            line = line.replace(Stimuli_Shape_Speed_Y_String+"=", "");
            Positive_Shape_Speed_Y = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Dot_Size_String+"="))
        {
            line = line.replace(Stimuli_Shape_Dot_Size_String+"=", "");
            Positive_Shape_Dot_Size = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Dot_Color_String+"="))
        {
            line = line.replace(Stimuli_Shape_Dot_Color_String+"=", "");
            Positive_Shape_Dot_Color = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Dot_Color_2_String+"="))
        {
            line = line.replace(Stimuli_Shape_Dot_Color_2_String+"=", "");
            Positive_Shape_Dot_Color_2 = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Dot_Color_Threshold_String+"="))
        {
            line = line.replace(Stimuli_Shape_Dot_Color_Threshold_String+"=", "");
            Positive_Shape_Dot_Color_Threshold = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Dot_Max_Count_String+"="))
        {
            line = line.replace(Stimuli_Shape_Dot_Max_Count_String+"=", "");
            Positive_Shape_Dot_Max_Count = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Life_Time_String+"="))
        {
            line = line.replace(Stimuli_Shape_Life_Time_String+"=", "");
            Positive_Shape_Life_Time = Double.parseDouble(line);
        }  
        
        if (line.contains("}"))
        {
            Automaton_State = Automaton_State_Positive;
        }
    }
    private void Automaton_State_Positive_Noise_Routine(String line)
    {
        if (line.contains(Stimuli_Noise_Type_String+"="))
        {
            line = line.replace(Stimuli_Noise_Type_String+"=", "");
            Positive_Noise_Type = line;
        }
        if (line.contains(Stimuli_Noise_Speed_String+"="))
        {
            line = line.replace(Stimuli_Noise_Speed_String+"=", "");
            Positive_Noise_Speed = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Noise_Direction_String+"="))
        {
            line = line.replace(Stimuli_Noise_Direction_String+"=", "");
            Positive_Noise_Direction = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Noise_Coherence_String+"="))
        {
            line = line.replace(Stimuli_Noise_Coherence_String+"=", "");
            Positive_Noise_Coherence = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Noise_Speed_X_String+"="))
        {
            line = line.replace(Stimuli_Noise_Speed_X_String+"=", "");
            Positive_Noise_Speed_X = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Noise_Speed_Y_String+"="))
        {
            line = line.replace(Stimuli_Noise_Speed_Y_String+"=", "");
            Positive_Noise_Speed_Y = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Noise_Dot_Size_String+"="))
        {
            line = line.replace(Stimuli_Noise_Dot_Size_String+"=", "");
            Positive_Noise_Dot_Size = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Noise_Dot_Color_String+"="))
        {
            line = line.replace(Stimuli_Noise_Dot_Color_String+"=", "");
            Positive_Noise_Dot_Color = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Noise_Dot_Color_2_String+"="))
        {
            line = line.replace(Stimuli_Noise_Dot_Color_2_String+"=", "");
            Positive_Noise_Dot_Color_2 = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Noise_Dot_Color_Threshold_String+"="))
        {
            line = line.replace(Stimuli_Noise_Dot_Color_Threshold_String+"=", "");
            Positive_Noise_Dot_Color_Threshold = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Noise_Dot_Max_Count_String+"="))
        {
            line = line.replace(Stimuli_Noise_Dot_Max_Count_String+"=", "");
            Positive_Noise_Dot_Max_Count = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Noise_Life_Time_String+"="))
        {
            line = line.replace(Stimuli_Noise_Life_Time_String+"=", "");
            Positive_Noise_Life_Time = Double.parseDouble(line);
        }
        
        if (line.contains("}"))
        {
            Automaton_State = Automaton_State_Positive;
        }
    }

    
    private void Automaton_State_Negative_Background_Routine(String line)
    {
        if (line.contains(Stimuli_Background_Type_String+"="))
        {
            line = line.replace(Stimuli_Background_Type_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Background_Type_String+Automaton_State_Negative_Background;
            else
                Negative_Background_Type = line;
        }
        if (line.contains(Stimuli_Background_Speed_String+"="))
        {
            line = line.replace(Stimuli_Background_Speed_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Background_Speed_String+Automaton_State_Negative_Background;
            else
                Negative_Background_Speed = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Background_Direction_String+"="))
        {
            line = line.replace(Stimuli_Background_Direction_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Background_Direction_String+Automaton_State_Negative_Background;
            else
                Negative_Background_Direction = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Background_Coherence_String+"="))
        {
            line = line.replace(Stimuli_Background_Coherence_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Background_Coherence_String+Automaton_State_Negative_Background;
            else
                Negative_Background_Coherence = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Background_Speed_X_String+"="))
        {
            line = line.replace(Stimuli_Background_Speed_X_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Background_Speed_X_String+Automaton_State_Negative_Background;
            else
                Negative_Background_Speed_X = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Background_Speed_Y_String+"="))
        {
            line = line.replace(Stimuli_Background_Speed_Y_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Background_Speed_Y_String+Automaton_State_Negative_Background;
            else
                Negative_Background_Speed_Y = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Background_Dot_Size_String+"="))
        {
            line = line.replace(Stimuli_Background_Dot_Size_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Background_Dot_Size_String+Automaton_State_Negative_Background;
            else
                Negative_Background_Dot_Size = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Background_Dot_Color_String+"="))
        {
            line = line.replace(Stimuli_Background_Dot_Color_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Background_Dot_Color_String+Automaton_State_Negative_Background;
            else
                Negative_Background_Dot_Color = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Background_Dot_Color_2_String+"="))
        {
            line = line.replace(Stimuli_Background_Dot_Color_2_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Background_Dot_Color_2_String+Automaton_State_Negative_Background;
            else
                Negative_Background_Dot_Color_2 = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Background_Dot_Color_Threshold_String+"="))
        {
            line = line.replace(Stimuli_Background_Dot_Color_Threshold_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Background_Dot_Color_Threshold_String+Automaton_State_Negative_Background;
            else
                Negative_Background_Dot_Color_Threshold = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Background_Dot_Max_Count_String+"="))
        {
            line = line.replace(Stimuli_Background_Dot_Max_Count_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Background_Dot_Max_Count_String+Automaton_State_Negative_Background;
            else
                Negative_Background_Dot_Max_Count = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Background_Life_Time_String+"="))
        {
            line = line.replace(Stimuli_Background_Life_Time_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Background_Life_Time_String+Automaton_State_Negative_Background;
            else
                Negative_Background_Life_Time = Double.parseDouble(line);
        }   
        
        if (line.contains("}"))
        {
            Automaton_State = Automaton_State_Negative;
        }
    }
    
    
    private void Automaton_State_Negative_Shape_Routine(String line)
    {
        if (line.contains(Stimuli_Shape_Horizontal_Offset_String+"="))
        {
            line = line.replace(Stimuli_Shape_Horizontal_Offset_String+"=", "");
            
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Horizontal_Offset_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Horizontal_Offset = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Field_Scale_String+"="))
        {
            line = line.replace(Stimuli_Shape_Field_Scale_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Field_Scale_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Field_Scale = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Scale_String+"="))
        {
            line = line.replace(Stimuli_Shape_Scale_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Scale_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Scale = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Background_Color_String+"="))
        {
            line = line.replace(Stimuli_Shape_Background_Color_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Background_Color_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Background_Color = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Ellipse_X_String+"="))
        {
            line = line.replace(Stimuli_Shape_Ellipse_X_String+"=", "");
            if (line.equals(PARAM_String))
            {
                PARAM = Stimuli_Shape_Ellipse_X_String+Automaton_State_Negative_Shape;
            }
            else
            {
                Negative_Shape_Ellipse_X = Integer.parseInt(line);
                //System.out.println("\t\t\t\t\t\t\t Negative READ Ellipse_X " + Positive_Shape_Ellipse_X);
            }
        }
        if (line.contains(Stimuli_Shape_Ellipse_Y_String+"="))
        {
            line = line.replace(Stimuli_Shape_Ellipse_Y_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Ellipse_Y_String+Automaton_State_Negative_Shape;
            else
            {
                Negative_Shape_Ellipse_Y = Integer.parseInt(line);
                //System.out.println("\t\t\t\t\t\t\t Negative READ Ellipse_Y " + Positive_Shape_Ellipse_Y);
            }
        }
        if (line.contains(Stimuli_Shape_Type_String+"="))
        {
            line = line.replace(Stimuli_Shape_Type_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Type_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Type = line;
        }
        if (line.contains(Stimuli_Shape_Medium_Value_String+"="))
        {
            line = line.replace(Stimuli_Shape_Medium_Value_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Medium_Value_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Medium_Value = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Amplitude_String+"="))
        {
            line = line.replace(Stimuli_Shape_Amplitude_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Amplitude_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Amplitude = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Speed_String+"="))
        {
            line = line.replace(Stimuli_Shape_Speed_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Speed_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Speed = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Direction_String+"="))
        {
            line = line.replace(Stimuli_Shape_Direction_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Direction_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Direction = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Width_String+"="))
        {
            line = line.replace(Stimuli_Shape_Width_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Width_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Width = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Duty_String+"="))
        {
            line = line.replace(Stimuli_Shape_Duty_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Duty_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Duty = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Mask_String+"="))
        {
            line = line.replace(Stimuli_Shape_Mask_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Mask_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Mask = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Coherence_String+"="))
        {
            line = line.replace(Stimuli_Shape_Coherence_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Coherence_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Coherence = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Speed_X_String+"="))
        {
            line = line.replace(Stimuli_Shape_Speed_X_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Speed_X_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Speed_X = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Speed_Y_String+"="))
        {
            line = line.replace(Stimuli_Shape_Speed_Y_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Speed_Y_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Speed_Y = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Shape_Dot_Size_String+"="))
        {
            line = line.replace(Stimuli_Shape_Dot_Size_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Dot_Size_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Dot_Size = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Dot_Color_String+"="))
        {
            line = line.replace(Stimuli_Shape_Dot_Color_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Dot_Color_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Dot_Color = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Dot_Color_2_String+"="))
        {
            line = line.replace(Stimuli_Shape_Dot_Color_2_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Dot_Color_2_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Dot_Color_2 = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Dot_Color_Threshold_String+"="))
        {
            line = line.replace(Stimuli_Shape_Dot_Color_Threshold_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Dot_Color_Threshold_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Dot_Color_Threshold = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Dot_Max_Count_String+"="))
        {
            line = line.replace(Stimuli_Shape_Dot_Max_Count_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Dot_Max_Count_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Dot_Max_Count = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Shape_Life_Time_String+"="))
        {
            line = line.replace(Stimuli_Shape_Life_Time_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Shape_Life_Time_String+Automaton_State_Negative_Shape;
            else
                Negative_Shape_Life_Time = Double.parseDouble(line);
        } 
        if (line.contains("}"))
        {
            Automaton_State = Automaton_State_Negative;
        }
    }
    private void Automaton_State_Negative_Noise_Routine(String line)
    {
        if (line.contains(Stimuli_Noise_Type_String+"="))
        {
            line = line.replace(Stimuli_Noise_Type_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Noise_Type_String+Automaton_State_Negative_Noise;
            else
                Negative_Noise_Type = line;
        }
        if (line.contains(Stimuli_Noise_Speed_String+"="))
        {
            line = line.replace(Stimuli_Noise_Speed_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Noise_Speed_String+Automaton_State_Negative_Noise;
            else
                Negative_Noise_Speed = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Noise_Direction_String+"="))
        {
            line = line.replace(Stimuli_Noise_Direction_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Noise_Direction_String+Automaton_State_Negative_Noise;
            else
                Negative_Noise_Direction = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Noise_Coherence_String+"="))
        {
            line = line.replace(Stimuli_Noise_Coherence_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Noise_Coherence_String+Automaton_State_Negative_Noise;
            else
                Negative_Noise_Coherence = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Noise_Speed_X_String+"="))
        {
            line = line.replace(Stimuli_Noise_Speed_X_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Noise_Speed_X_String+Automaton_State_Negative_Noise;
            else
                Negative_Noise_Speed_X = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Noise_Speed_Y_String+"="))
        {
            line = line.replace(Stimuli_Noise_Speed_Y_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Noise_Speed_Y_String+Automaton_State_Negative_Noise;
            else
                Negative_Noise_Speed_Y = Double.parseDouble(line);
        }
        if (line.contains(Stimuli_Noise_Dot_Size_String+"="))
        {
            line = line.replace(Stimuli_Noise_Dot_Size_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Noise_Dot_Size_String+Automaton_State_Negative_Noise;
            else
                Negative_Noise_Dot_Size = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Noise_Dot_Color_String+"="))
        {
            line = line.replace(Stimuli_Noise_Dot_Color_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Noise_Dot_Color_String+Automaton_State_Negative_Noise;
            else
                Negative_Noise_Dot_Color = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Noise_Dot_Color_2_String+"="))
        {
            line = line.replace(Stimuli_Noise_Dot_Color_2_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Noise_Dot_Color_2_String+Automaton_State_Negative_Noise;
            else
                Negative_Noise_Dot_Color_2 = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Noise_Dot_Color_Threshold_String+"="))
        {
            line = line.replace(Stimuli_Noise_Dot_Color_Threshold_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Noise_Dot_Color_Threshold_String+Automaton_State_Negative_Noise;
            else
                Negative_Noise_Dot_Color_Threshold = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Noise_Dot_Max_Count_String+"="))
        {
            line = line.replace(Stimuli_Noise_Dot_Max_Count_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Noise_Dot_Max_Count_String+Automaton_State_Negative_Noise;
            else
                Negative_Noise_Dot_Max_Count = Integer.parseInt(line);
        }
        if (line.contains(Stimuli_Noise_Life_Time_String+"="))
        {
            line = line.replace(Stimuli_Noise_Life_Time_String+"=", "");
            if (line.equals(PARAM_String))
                PARAM = Stimuli_Noise_Life_Time_String+Automaton_State_Negative_Noise;
            else
                Negative_Noise_Life_Time = Double.parseDouble(line);
        }
        
        
        
        if (line.contains("}"))
        {
            Automaton_State = Automaton_State_Negative;
        }
    }

}











package vizcacha2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadConfig 
{
    public static final String KEY_EXIT_String = "KEY_EXIT=";
    public int KEY_EXIT;
    
    public static final String KEY_LEFT_String = "KEY_LEFT=";
    public int KEY_LEFT;
    
    public static final String KEY_RIGHT_String = "KEY_RIGHT=";
    public int KEY_RIGHT;
    
    public static final String KEY_STIMULI_String = "KEY_STIMULI=";
    public int KEY_STIMULI;
    
    public static final String KEY_REMARK_String = "KEY_REMARK=";
    public int KEY_REMARK;
    
    public static final String KEY_FEEDER_String = "KEY_FEEDER=";
    public int KEY_FEEDER;
    
    public static final String KEY_START_String = "KEY_START=";
    public int KEY_START;
    
    public static final String Filename_String = "filename=";
    public String filename="";
    public String filename_no_ext="";
    
    public static final String PatientName_String = "patient_name=";
    public String patient_name="";
    
    public static final String FocusPoint_String = "focus_point=";
    public int focus_point=0;
    
    public static final String FocusPointDiameter_String = "focus_point_diameter=";
    public int focus_point_diameter=100;
    
    public static final String ScreenResolution_V_String = "resolution_v=";
    public int resolution_v=0;
    
    public static final String ScreenResolution_H_String = "resolution_h=";
    public int resolution_h=0;
    
    public static final String DiagonalInch_String = "diagonal_inch=";
    public double diagonal_inch=0;
    
    public static final String FullAngle_V_String = "full_angle_v=";
    public double full_angle_v=0;
    
    public static final String FullAngle_H_String = "full_angle_h=";
    public double full_angle_h=0;
    
    public static final String SweepFiles_String = "sweep_files=";
    public double sweep_files=0;
    
    public static final String FeederTimer_String = "feeding_time_ms=";
    public int feeding_time;
    
    public static final String UseEyeTracker_String = "use_eyetracker=";
    public int use_eyetracker=0;
    
    public static final String OverwriteLevel_String = "overwrite_level=";
    public double[] overwrite_level;
    
    private int filename_flag = 0;
    private int sweep_flag = 0;
    
    public LoadConfig()
    {
        BufferedReader br = null;
        try 
        {
            br = new BufferedReader(new FileReader("config.txt"));
        } catch (FileNotFoundException ex) {Logger.getLogger(LoadConfig.class.getName()).log(Level.SEVERE, null, ex);}
        
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
                        
                        PassLine_reader(line);
                    }
                }
                line = br.readLine();
            }
        } 
        catch (IOException ex) {Logger.getLogger(LoadConfig.class.getName()).log(Level.SEVERE, null, ex);}
        finally 
        {
            try 
            {
                br.close();
            } catch (IOException ex) {Logger.getLogger(LoadConfig.class.getName()).log(Level.SEVERE, null, ex);}
        }
        
        Path path = Paths.get("config.txt");
        
        try {Files.delete(path);}
        catch (Exception e) {}
        
        // File (or directory) with old name
        File file = new File("config.txt.tmp");

        // File (or directory) with new name
        File file2 = new File("config.txt");

        // Rename file (or directory)
        boolean success = file.renameTo(file2);

        if (!success) 
        {
            System.out.println("ERROR renaming file");
        }
        
        
        System.out.println("reader out!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
    
    private void Automaton_reader(String line)
    {
        ReadKeyboardCodes(line);
        
        if (line.contains(Filename_String))
        {
            line = line.replace(Filename_String, "");
            filename = line;
            String[] tmp_strings = filename.split("\\.");
            filename_no_ext = tmp_strings[0];
            filename_flag=1;
        }
        if (line.contains(PatientName_String))
        {
            line = line.replace(PatientName_String, "");
            patient_name = line;
        }
        if (line.contains(FocusPoint_String))
        {
            line = line.replace(FocusPoint_String, "");
            focus_point = Integer.parseInt(line);
        }
        if (line.contains(FocusPointDiameter_String))
        {
            line = line.replace(FocusPointDiameter_String, "");
            focus_point_diameter = Integer.parseInt(line);
        }
        
        if (line.contains(ScreenResolution_V_String))
        {
            line = line.replace(ScreenResolution_V_String, "");
            resolution_v = Integer.parseInt(line);
        }
        if (line.contains(ScreenResolution_H_String))
        {
            line = line.replace(ScreenResolution_H_String, "");
            resolution_h = Integer.parseInt(line);
        }
        if (line.contains(DiagonalInch_String))
        {
            line = line.replace(DiagonalInch_String, "");
            diagonal_inch = Double.parseDouble(line);
        }
        if (line.contains(FullAngle_V_String))
        {
            line = line.replace(FullAngle_V_String, "");
            full_angle_v = Double.parseDouble(line);
        }
        if (line.contains(FullAngle_H_String))
        {
            line = line.replace(FullAngle_H_String, "");
            full_angle_h = Double.parseDouble(line);
        }
        if (line.contains(SweepFiles_String))
        {
            line = line.replace(SweepFiles_String, "");
            sweep_files = Double.parseDouble(line);
            sweep_flag = 1;
        }
        if (line.contains(FeederTimer_String))
        {
            line = line.replace(FeederTimer_String, "");
            feeding_time = Integer.parseInt(line);
        }
        if (line.contains(UseEyeTracker_String))
        {
            line = line.replace(UseEyeTracker_String, "");
            use_eyetracker = Integer.parseInt(line);
        }
        if (line.contains(OverwriteLevel_String))
        {
            line = line.replace(OverwriteLevel_String, "");
            
            String [] str = line.split(",");
            
            overwrite_level = new double[str.length];
            
            for (int i=0;i<str.length;i++)
            {
                overwrite_level[i] = Double.parseDouble(str[i]);
            }
        }
        
    }
    
    private void ReadKeyboardCodes(String line)
    {
        if (line.contains(KEY_EXIT_String))
        {
            line = line.replace(KEY_EXIT_String, "");
            KEY_EXIT = Integer.parseInt(line);
            Keyboard_inputs.KEYBOARD_ESC = KEY_EXIT;
        }
        if (line.contains(KEY_LEFT_String))
        {
            line = line.replace(KEY_LEFT_String, "");
            KEY_LEFT = Integer.parseInt(line);
            Keyboard_inputs.KEYBOARD_LEFT = KEY_LEFT;
        }
        if (line.contains(KEY_RIGHT_String))
        {
            line = line.replace(KEY_RIGHT_String, "");
            KEY_RIGHT = Integer.parseInt(line);
            Keyboard_inputs.KEYBOARD_RIGHT = KEY_RIGHT;
        }
        if (line.contains(KEY_STIMULI_String))
        {
            line = line.replace(KEY_STIMULI_String, "");
            KEY_STIMULI = Integer.parseInt(line);
            Keyboard_inputs.KEYBOARD_ALT = KEY_STIMULI;
        }
        if (line.contains(KEY_REMARK_String))
        {
            line = line.replace(KEY_REMARK_String, "");
            KEY_REMARK = Integer.parseInt(line);
            Keyboard_inputs.KEYBOARD_SPACE = KEY_REMARK;
        }
        if (line.contains(KEY_FEEDER_String))
        {
            line = line.replace(KEY_FEEDER_String, "");
            KEY_FEEDER = Integer.parseInt(line);
            Keyboard_inputs.KEYBOARD_T = KEY_FEEDER;
        }
        if (line.contains(KEY_START_String))
        {
            line = line.replace(KEY_START_String, "");
            KEY_START = Integer.parseInt(line);
            Keyboard_inputs.KEYBOARD_S = KEY_START;
        }
        
    }

    private void PassLine_reader(String line)
    {
        PrintNewConfig(line);
    }
    
    private void PrintNewConfig(String line)
    {
        PrintWriter writer = null;
        try {writer = new PrintWriter(new FileWriter("config.txt.tmp", true));} 
        catch (Exception e) {}

        String tmp = line;
        
        if (sweep_files>0)
        {
            int str_len = line.length();
            
            if (filename_flag==1)
            {
                writer.print("#"+line);
                filename_flag=2;
            }
            else if (filename_flag==2 && str_len>9)
            {
                if ("#filename".equals(line.substring(0, 9)))
                {
                    String tmp2 = line.substring(1, str_len);
                    System.out.println(tmp2);
                    writer.print(tmp2);
                    filename_flag=0;
                }
            }
            else if (sweep_flag==1)
            {
                if ("sweep_files=".equals(line.substring(0, 12)))
                {
                    String tmp2 = line.substring(12, str_len);
                    System.out.println(tmp2);
                    int sweep_nb = Integer.parseInt(tmp2);
                    sweep_nb = sweep_nb - 1;
                    String tmp3 = line.substring(0, 12) + Integer.toString(sweep_nb);
                    
                    System.out.println("\t\t\t Decrementing sweep files!!!!!");
                    System.out.println("\t\t\t sweep files =" + tmp3);
                    
                    writer.print(tmp3);
                    sweep_flag = 0;
                }
            }
            else
            {
                writer.print(line);  
            }
        }
        else
        {
            writer.print(line);
        }
        
        writer.println("");
        writer.close();
    }
    

}











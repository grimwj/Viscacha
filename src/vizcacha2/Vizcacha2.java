package vizcacha2;

public class Vizcacha2 
{
    public static MyLogger logger; 
    public static Display disp;
    public static MyFileWriter writer;
    public static LoadConfig config_reader;
    public static LoadExperimentData reader;
    public static Trials trials;
    
    public static double height_mm;
    public static double width_mm;
    public static double distance_mm;
    public static double pixel_to_angle_factor=1;
    
    public static Keyboard_inputs keyboard;
    
    public static SerialComm serialComm;
    
    public static EyeTrackerComm trackerComm;
    
    public static boolean firstrun = true;
    
    public static void main(String[] args) 
    {
        serialComm = null;
        trackerComm = new EyeTrackerComm();
        
        logger = new MyLogger();
        logger.MyLogger_WriteLine("Logger begins");
        
        LoadAndBeginStimuli();
    }
    
    public static void LoadAndBeginStimuli()
    {
        config_reader = new LoadConfig();
        logger.MyLogger_WriteLine("Configuration file loaded");
        
        writer = new MyFileWriter();
        writer.MyFileWriter_WriteLine("Start of experiment, " + writer.filename);
        writer.MyFileWriter_WriteLine("Source file name: " + config_reader.filename);
        
        Calculate_PixelToAngle_Factor();
        String tmp;
        tmp = "Screen height mm: " + height_mm;
        tmp = tmp.replace('.', ',');
        writer.MyFileWriter_WriteLine(tmp);
        tmp = "Screen width mm: " + width_mm;
        tmp = tmp.replace('.', ',');
        writer.MyFileWriter_WriteLine(tmp);
        tmp = "Distance mm: " + distance_mm;
        tmp = tmp.replace('.', ',');
        writer.MyFileWriter_WriteLine(tmp);
        tmp = "Pixel to angle multiplier: " + pixel_to_angle_factor;
        tmp = tmp.replace('.', ',');
        writer.MyFileWriter_WriteLine(tmp);
        
        writer.MyFileWriter_WriteLine("Patient name: " + config_reader.patient_name);
        
        reader = new LoadExperimentData(config_reader.filename);
        
        logger.MyLogger_WriteLine("Stimuli definition file loaded");
        
        writer.MyFileWriter_WriteLine("Data loaded, variable PARAM = " + reader.PARAM);
        writer.MyFileWriter_WriteLine("Experiment type = " + reader.Experiment_Type);
        
        keyboard = new Keyboard_inputs();
        
        if (disp == null)
        {
            disp = new Display();
            logger.MyLogger_WriteLine("Display layer initialized");
        }
        
        if (serialComm == null)
            serialComm = new SerialComm();
        
        serialComm.UpdateStatus();
        
        trials = new Trials();
        if (reader.Experiment_Type.equals(LoadExperimentData.Experiment_Type_Constant_String) )
            trials.Trials_Constant_Begin();
        else if (reader.Experiment_Type.equals(LoadExperimentData.Experiment_Type_Staircase_String) )
            trials.Trials_Staircase_Begin();
    }
    
    private static void Calculate_PixelToAngle_Factor()
    {
        int res_x = config_reader.resolution_h;
        int res_y = config_reader.resolution_v;
        double diag_inch = config_reader.diagonal_inch;
        double angle_x = config_reader.full_angle_h;
        double angle_y = config_reader.full_angle_v;
        
        double diag_mm = diag_inch*25.4;
        
        String tmp;
        
        writer.MyFileWriter_WriteLine("Screen resolution h: " + res_x);
        writer.MyFileWriter_WriteLine("Screen resolution v: " + res_y);
        tmp = "Diagonal inch: " + diag_inch;
        tmp = tmp.replace('.', ',');
        writer.MyFileWriter_WriteLine(tmp);
        
        
        double vision_angular_rad = 0;
        if (angle_x==0)
        {
            vision_angular_rad = angle_y * 2 * Math.PI / 360;
            tmp = "Vertical angle: " + angle_y;
            tmp = tmp.replace('.', ',');
            writer.MyFileWriter_WriteLine(tmp);
        }
        
        if (angle_y==0)
        {
            vision_angular_rad = angle_x * 2 * Math.PI / 360;
            tmp = "Horizontal angle: " + angle_x;
            tmp = tmp.replace('.', ',');
            writer.MyFileWriter_WriteLine(tmp);
        }
        
        double k = (double) res_x / (double) res_y;
        
        height_mm = diag_mm / Math.sqrt(1 + k*k);
        width_mm = k * height_mm;
        
        if (angle_x==0)
        {
            distance_mm = (height_mm/2) / Math.tan(vision_angular_rad/2);
            pixel_to_angle_factor = angle_y / res_y;
        }
        
        if (angle_y==0)
        {
            distance_mm = (width_mm/2) / Math.tan(vision_angular_rad/2);
            pixel_to_angle_factor = angle_x / res_x;
        }   
    }
}

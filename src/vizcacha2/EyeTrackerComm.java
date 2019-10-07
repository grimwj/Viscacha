package vizcacha2;

import com.srresearch.eyelink.*;

public class EyeTrackerComm 
{
    Tracker tracker;
    int use_tracker = 0;
    
    EyeTrackerComm()
    {
        // load eljava.dll
        System.loadLibrary("eljava");
        tracker = new Tracker();
    }
    
    public int OpenConnection()
    {
        if (Vizcacha2.config_reader.use_eyetracker==1)
        {
            tracker.open();
        }
        return 0;
    }
    
    public int CreateDataFile(String filename)
    {
        if (Vizcacha2.config_reader.use_eyetracker==1)
        {
            // name - max 8 char
            String filename2 = filename + ".edf";
            tracker.openDataFile(filename2);
            //tracker.sendCommand("Openfile");
        }
        return 0;
    }
    
    public int CloseDataFile()
    {
        if (Vizcacha2.config_reader.use_eyetracker==1)
        {
            tracker.sendCommand("CloseFile");
        }
        return 0;
    }
    
    public int StartRecording()
    {
        if (Vizcacha2.config_reader.use_eyetracker==1)
        {
            tracker.startRecording(true,true,true,true); // start recording.
        }
        return 0;
    }
    
    public int FinishRecording()
    {
        if (Vizcacha2.config_reader.use_eyetracker==1)
        {
            tracker.sendCommand("set_idle_mode");
            tracker.sendCommand("ShutDown");
        }
        return 0;
    }
    
    public int SendMessage(String message)
    {
        if (Vizcacha2.config_reader.use_eyetracker==1)
        {
            tracker.sendMessage(message);
        }
        return 0;
    }
    
    public int SendCommand(String command)
    {
        if (Vizcacha2.config_reader.use_eyetracker==1)
        {
            tracker.sendCommand(command);
        }
        return 0;
    }
    
    public int ReceiveDataFile(String filename1, String filename2)
    {
        if (Vizcacha2.config_reader.use_eyetracker==1)
        {
            filename2 = filename2.concat(".edf");
            tracker.receiveDataFile(filename1, filename2);
        }
        return 0;
    }
    
    
}

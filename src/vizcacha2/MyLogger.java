package vizcacha2;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class MyLogger 
{
    public String filename;
    
    public String[] experimentinfo;
    public String[] columns = new String[12];
    
    String DirName0= "experiment_data";
    String DirName = DirName0 + "/logs";
    
    File theDir0 = new File(DirName0);
    File theDir = new File(DirName);
    
    static Date date;
    static DateFormat dateFormat;
    static String start_time;
    
    static int Log_ID=1;
    
    public MyLogger()
    {
        if (!theDir0.exists()) 
        {
            try
            {
                theDir0.mkdir();
            } 
            catch(SecurityException se){}        
        }
        
        if (!theDir.exists()) 
        {
            try
            {
                theDir.mkdir();
            } 
            catch(SecurityException se){}        
        }
        
        dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
        date = new Date();
        filename= dateFormat.format(date) + "_logs";
    }
    
    public void MyLogger_WriteLine(String data)
    {           
        PrintWriter writer = null;
        try {writer = new PrintWriter(new FileWriter(DirName+"/"+filename+".txt", true));} 
        catch (Exception e) {}

        dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
        date = new Date();
        
        String ID_String = "/Log number " + Log_ID + "/";
        writer.println("");
        writer.println(ID_String);
        writer.print(data);
        writer.println("");
        writer.close();
        Log_ID++;
    }
}

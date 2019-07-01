package vizcacha2;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MyFileReader
{
    public String filename;
    
    public String[] experimentinfo;
    public String[] columns = new String[12];
    
    String DirName = "experiment_data";
    
    File theDir = new File(DirName);
    
    static Date date;
    static DateFormat dateFormat;
    static String start_time;
    static String current_time;
    long currentmilis=0;
    
    public MyFileReader()
    {
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
        filename=dateFormat.format(date);
    }

    public void MyFileWriter_WriteLine(String data)
    {           
        PrintWriter writer = null;
        try {writer = new PrintWriter(new FileWriter(DirName+"/"+filename+".csv", true));} 
        catch (Exception e) {}

        writer.print(data);
        writer.println("");
        writer.close();
    }
    
    
    public void MyFileWriter_WriteLine(String[] data, String separator)
    {   
        int i;
        int nb_of_strings = data.length;
        
        PrintWriter writer = null;
        try {writer = new PrintWriter(new FileWriter(DirName+"/"+filename+".csv", true));} 
        catch (Exception e) {}

        for (i=0;i<nb_of_strings;i++) writer.print(data[i]+separator);
        
        writer.println("");
        writer.close();
    }
    
    private String[] AddToStringArray(String[] array, String element)
    {
        String[] result = Arrays.copyOf(array, array.length +1);
        result[array.length] = element;
        return result;
    }
    
    


}


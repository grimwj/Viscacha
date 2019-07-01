package vizcacha2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MyFileWriter
{
    public String filename;
    public String patientname = Vizcacha2.config_reader.patient_name;
    public String stimuliname = Vizcacha2.config_reader.filename_no_ext;
    
    public String[] experimentinfo;
    public String[] columns = new String[12];
    
    String DirName0= "experiment_data";
    String DirName1 = DirName0 + "/" + patientname;
    String DirName = DirName1 + "/" + stimuliname;
    
    File theDir0 = new File(DirName0);
    File theDir1 = new File(DirName1);
    File theDir = new File(DirName);
    
    static Date date;
    static DateFormat dateFormat;
    static String start_time;
    
    public MyFileWriter()
    {
        if (!theDir0.exists()) 
        {
            try
            {
                theDir0.mkdir();
            } 
            catch(SecurityException se){}        
        }
        
        if (!theDir1.exists()) 
        {
            try
            {
                theDir1.mkdir();
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
        //filename= Vizcacha2.config_reader.patient_name + "_" + dateFormat.format(date);
        filename= dateFormat.format(date);
        
        String src = "config.txt";
        String dst = DirName + "\\" + filename + "_config.txt";
        
        try 
        {
            Files.copy(Paths.get(src), Paths.get(dst), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {}
        
        src = Vizcacha2.config_reader.filename;
        dst = DirName + "\\" + filename + "_init.txt";
        
        try 
        {
            Files.copy(Paths.get(src), Paths.get(dst), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {}
    }

    public void MyFileWriter_WriteLine(String data)
    {           
        PrintWriter writer = null;
        try {writer = new PrintWriter(new FileWriter(DirName+"/"+filename+".csv", true));} 
        catch (Exception e) {}

        System.out.println(data);
        
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


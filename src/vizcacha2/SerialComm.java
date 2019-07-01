/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vizcacha2;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.*;

/**
 *
 * @author Grim
 */
public class SerialComm 
{
    //String[] portNames;
    String portNames;
    
    SerialPort serialPort;
    
    Scanner_Thread scanner_thread;
    
    byte[] FeederCMD = {2, 0 , 5, 17, 1};
    byte[] FirmwareCMD = {2, 0 , 5, (byte) 255, 1};
    byte[] SetTimeCMD = {2, 0 , 7, 66, 4, 0, 1};
    
    String VizcachaUSBFeederResponse = "VIZCACHA DRIVER V1.0";
    
    String SerialPortString = "COM";
    
    boolean ConnectionEstablished = false;
    
    int ResponseTimeoutMs = 500;
    
    public void UpdateStatus()
    {
        if (ConnectionEstablished)
        {
            String ConnStatus = "Feeder connected on port " + SerialPortString;
            Vizcacha2.disp.panel.SetConnectionStatus(ConnStatus);
        }
    }
    
    SerialComm()
    {
        scanner_thread = new Scanner_Thread();
        scanner_thread.start();
    }
    
    public boolean IsConnected()
    {
        return ConnectionEstablished;
    }
    
    void SerialComm_PrintPorts()
    {
        /*
        for (int i = 0; i < portNames.length; i++)
        {
            System.out.println(portNames[i]);
        }
        */
    }
    
    public boolean ScanPortsForFeeder() throws TimeoutException, ExecutionException
    {
        //portNames = SerialPortList.getPortNames();
        portNames = "COM3";
        
        System.out.println("Listuje porty COM:");
        Vizcacha2.logger.MyLogger_WriteLine("Scanning for USB feeder");
        
        SerialComm_PrintPorts();
        
        //for (int i = 0; i < portNames.length; i++)
        for (int i = 0; i < 1; i++)
        {
            //SerialPortString = portNames[i];
            SerialPortString = portNames;
            String ConnStatus = "Trying to connect at port " + SerialPortString;
            Vizcacha2.disp.panel.SetConnectionStatus(ConnStatus);
            
            System.out.println("Opening port: " + SerialPortString);
            
            //boolean isOpened = Receive(portNames[i]);
            boolean isOpened = Receive(portNames);
            
            if (isOpened)
            {
                try 
                {
                    serialPort.purgePort(1);
                    serialPort.purgePort(2);
                } 
                catch (SerialPortException ex) 
                {
                    Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println("Port opened: " + SerialPortString);
                SendFirmwareCMD();
            }
            
            try {Thread.sleep(ResponseTimeoutMs);} 
            catch (InterruptedException ex) {Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);}
            
            System.out.println("Port timeout: " + SerialPortString);
            
            if (ConnectionEstablished)
            {   
                System.out.println("Scan successful!");
                Vizcacha2.logger.MyLogger_WriteLine("Connection with feeder established at " + portNames);
                SendSettings();
                return true;
            }
            else
            {
                CloseSerialComm();
            }
        }
        System.out.println("Scan failed!");
        return false;
    }
    
    boolean Receive(String portNumber)
    {
        serialPort = new SerialPort(portNumber);
        try {
            serialPort.openPort();

            serialPort.setParams(SerialPort.BAUDRATE_9600,
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);

            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
                                          SerialPort.FLOWCONTROL_RTSCTS_OUT);

            serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
            
            return true;
        }
        catch (Exception ex) {
            System.out.println("Exception occured while opening port: " + portNumber);
            return false;
        }
    }
    
    public void SendSettings()
    {
        int time = Vizcacha2.config_reader.feeding_time;
        
        byte b1 = (byte) ((time/256)%256);
        byte b2 = (byte) (time % 256);
        
        System.out.println("TIME:   " + time);
        System.out.println("b1:   " + b1);
        System.out.println("b2:   " + b2);
        
        SetTimeCMD[4] = b1;
        SetTimeCMD[5] = b2;
        
        try 
        {
            serialPort.writeBytes(SetTimeCMD);
        } 
        catch (SerialPortException ex) {
            Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void SendFirmwareCMD() throws TimeoutException, ExecutionException
    {
        final Runnable stuffToDo = new Thread() 
        {
            @Override 
            public void run() 
            { 
                try 
                {
                    System.out.println("Sending Firmware CMD");
                    serialPort.writeBytes(FirmwareCMD);
                    System.out.println("Data sent: " + SerialPortString);
                }
                catch (SerialPortException ex) {Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);}
            }
        };

        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future future = executor.submit(stuffToDo);
        executor.shutdown(); // This does not cancel the already-scheduled task.

        try 
        { 
            future.get(2, TimeUnit.SECONDS); 
        }
        catch (InterruptedException ie) 
        { 
            System.out.println("Sending exception occured " + SerialPortString);
        }
        catch (ExecutionException ee) 
        { 
            System.out.println("Sending exception occured " + SerialPortString);
        }
        catch (TimeoutException te) 
        { 
            System.out.println("Sending timeout " + SerialPortString);
        }
        
        if (!executor.isTerminated())
        executor.shutdownNow(); // If you want to stop the code that hasn't finished.
    }
    
    public void SendFeederCMD()
    {
        if (IsConnected())
        {
            try 
            {
                serialPort.writeBytes(FeederCMD);
            } 
            catch (SerialPortException ex) 
            {
                Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
                
                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                String eString = sw.toString();

                Vizcacha2.logger.MyLogger_WriteLine(eString);                
            }
        }
    }
    
    public void CloseSerialComm()
    {
        try 
        {
            serialPort.closePort();
        } 
        catch (Exception e) 
        {
            Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, e);
            
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String eString = sw.toString();
            
            Vizcacha2.logger.MyLogger_WriteLine(eString);
        }
    }
    
    private class PortReader implements SerialPortEventListener 
    {
        @Override
        public void serialEvent(SerialPortEvent event) 
        {
            if(event.isRXCHAR() && event.getEventValue() > 0) 
            {
                try 
                {
                    String receivedData = serialPort.readString(event.getEventValue());
                    System.out.println("Received response: " + receivedData);
                    //System.out.println("Received response length: " + receivedData.length());
                    
                    String shortened_data = receivedData.substring(0, receivedData.length()-2);
                    
                    //System.out.println("Shortened response: " + shortened_data);
                    //System.out.println("Shortened response length: " + shortened_data.length());
                    
                    
                    if (shortened_data.equals(VizcachaUSBFeederResponse))
                    {
                        //System.out.println("Right key pressed");
                        ConnectionEstablished = true;
                        scanner_thread.end();
                        System.out.println("Feeder responded: " + shortened_data);
                        String ConnStatus = "Feeder connected on port " + SerialPortString;
                        Vizcacha2.disp.panel.SetConnectionStatus(ConnStatus);
                    }
                    
                    if (shortened_data.equals("L") && (Vizcacha2.keyboard.LR_Active==true))
                    {
                        //System.out.println("Left key pressed");
                        Vizcacha2.keyboard.Keyboard_Deactivate_LR();
                        Vizcacha2.trials.Trial_PassResponse("l");
                        System.out.println("Left PILOT pressed");
                    }
                    
                    if (shortened_data.equals("R") && (Vizcacha2.keyboard.LR_Active==true))
                    {
                        //System.out.println("Right key pressed");
                        Vizcacha2.keyboard.Keyboard_Deactivate_LR();
                        Vizcacha2.trials.Trial_PassResponse("r");
                        System.out.println("Right PILOT pressed");
                    }
                    
                }
                catch (SerialPortException ex) 
                {
                    System.out.println("Error in receiving string from COM-port: " + ex);
                    
                    StringWriter sw = new StringWriter();
                    ex.printStackTrace(new PrintWriter(sw));
                    String eString = sw.toString();

                    Vizcacha2.logger.MyLogger_WriteLine(eString);                    
                }
            }
        }

    }
}
    
    
class Scanner_Thread extends Thread implements Runnable 
{
    public boolean loop_condition = true;
    int refresh_period = 1000;
    
    public void run()
    {
        while(loop_condition)
        {
            
            try 
            {
                Vizcacha2.serialComm.ScanPortsForFeeder();
            } 
            catch (TimeoutException ex) {
                Logger.getLogger(Scanner_Thread.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (ExecutionException ex) {
                Logger.getLogger(Scanner_Thread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {Thread.sleep(refresh_period);} 
            catch (InterruptedException ex) {Logger.getLogger(VizPanel_Updater.class.getName()).log(Level.SEVERE, null, ex);}   
        }
    }
    
    public void end()
    {
        loop_condition = false;
    }
}



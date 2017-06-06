package application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package queuingsystem;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Paths;
import java.util.ArrayList;
/**
 *
 * 
 */
public class TaskOneSimulator 
{
    private Scanner scanner;
    private ArrayList<Packet> queue;
    private final int capacity = 10; //10Mbps
    private String traceFilePath;
    
    public TaskOneSimulator(String path)
    {
        traceFilePath = path;
        openFile();
        queue = new ArrayList();
    }
    
    private void openFile()
    {
        try
        {
            //scanner = new Scanner(Paths.get("C:\\Users\\motsamai\\Documents\\Courseware\\UP\\B.Eng\\Post Admission\\ERN 780\\Project\\tracefile.txt"));
            scanner = new Scanner(Paths.get(traceFilePath));
        }
        catch( IOException ex )
        {
            System.err.println("Trace File cannot be found");
            System.exit(1);
        }
    }
    
    public ArrayList<Packet> getPacketQueue()
    {
        return queue;
    }
    
    public Results runSimulator()
    {
        Results result = new Results();
        scanner.nextLine(); //discard the first line (column descriptions)
        
        while( scanner.hasNext() )
        {
            String line = scanner.nextLine();
            String [] parameters = line.split(",");
            
            int arrivalTime = Integer.parseInt(parameters[0]);
            int packetSize = Integer.parseInt(parameters[1]);
            int absArrivalTime;
            if( queue.isEmpty() )
                absArrivalTime = arrivalTime;
            else
                absArrivalTime = queue.get( queue.size() - 1 ).getAbsoluteTime() + arrivalTime;
            
            Packet newPacket = new Packet(arrivalTime, packetSize, absArrivalTime);
            
            //calculate other packet parameters
            calcPacketParameters(newPacket);
            
            queue.add(newPacket);
        }
        scanner.close();
        calculateResults(result);
        return result;
    }
    
    private void calcPacketParameters(Packet packet)
    {
        packet.setTransmissionTime(packet.getPacketSize()/(double)capacity);
        
        //calculate the queueing delay
        if( !queue.isEmpty() )
        {
            double delay = queue.get( queue.size() - 1 ).getTransCompleteTime() - packet.getAbsoluteTime();
            if( delay < 0 )
                packet.setQueueingDelay(0);
            else
                packet.setQueueingDelay(delay);
        }
        else
            packet.setQueueingDelay(0);
        
        
        //calculate transmission complete time
        packet.setTransCompleteTime(
            packet.getAbsoluteTime() + packet.getQueueingDelay() + packet.getTransmissionTime()
        );
    }
    
    private void calculateResults(Results result)
    {
        result.mu = calcMu();
        result.lambda = calcLambda();
        result.averageQueueingDelay = calcAvQdelay();
    }
    
    private double calcMu()
    {
        double avePacketSize = 0;
        double packetSizeSummation = 0;
        
        //Sum all packet sizes
        for( Packet packet : queue )
            packetSizeSummation += packet.getPacketSize();
       
        try
        {
            //calculate average packet size
            avePacketSize = packetSizeSummation / queue.size();
        }
        catch( Exception ex )
        {
            System.err.println("Error calculating average packet size, the trace file might be empty");
            System.err.println("Terminating...");
            System.exit(1);
        }
        
        //calcute average service rate (mu)
        return capacity / avePacketSize;
    }
    
    private double calcLambda()
    {
        
        double arrivalTimeSum = 0;
        for( Packet packet : queue )
            arrivalTimeSum += packet.getInterArrivalTime();
        
        try
        {
            return arrivalTimeSum / queue.size();
        }
        catch( Exception ex )
        {
            System.err.println("Error calcuating average arrival time. The trace file might be empty");
            System.err.println("Terminating");
            System.exit(1);
        }
        
        //this statement will not be reached.
        return -1;
    }
    
    private double calcAvQdelay()
    {
        double qDelaySum = 0;
        for( Packet packet : queue )
            qDelaySum += packet.getQueueingDelay();
        
        try
        {
            return qDelaySum / queue.size();
        }
        catch( Exception ex )
        {
            System.err.println("Error calcuating average queueing delay. The trace file might be empty");
            System.err.println("Terminating");
            System.exit(1);
        }
        
        //this statement will never be reached.
        return -1;
    }
}

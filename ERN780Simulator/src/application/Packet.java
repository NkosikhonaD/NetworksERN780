package application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */
public class Packet 
{
    private final int interArrivalTime;
    private final int packetSize;
    
    //previous packet absolute arival time plus this packet's inter arrival time
    private final int absoluteArrivalTime;
    
    //The absolute time when the packet transmission completes.
    private double transmissionCompleteTime;
    
    //Queueing delay equals the time the packet has to wait before its transmission could start.
    private double queueingDelay; 
    
    private double transmissionTime;
    
    public Packet( int arrivalTime, int size, int absoluteTime )
    {
        interArrivalTime = arrivalTime;
        packetSize = size;
        absoluteArrivalTime = absoluteTime;
    }
    
    public int getPacketSize()
    {
        return packetSize;
    }
    
    public int getInterArrivalTime()
    {
        return interArrivalTime;
    }
    
    public void setTransCompleteTime( double time )
    {
        transmissionCompleteTime = time;
    }
    
    public double getTransCompleteTime()
    {
        return transmissionCompleteTime;
    }
    
    public void setQueueingDelay( double delay )
    {
        queueingDelay = delay;
    }
    
    public double getQueueingDelay()
    {
        return queueingDelay;
    }
    
    public int getAbsoluteTime()
    {
        return absoluteArrivalTime;
    }
    
    public double getTransmissionTime()
    {
        return transmissionTime;
    }
    
    public void setTransmissionTime( double time )
    {
        transmissionTime = time;
    }
}

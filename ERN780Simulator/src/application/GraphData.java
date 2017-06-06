package application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package queuingsystem;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * 
 */
public class GraphData 
{
    private TaskOneSimulator taskOneSimulator;   
    ArrayList<Packet> packetQueue;
           
    public GraphData( String traceFile )
    {
        taskOneSimulator = new TaskOneSimulator(traceFile);
    }
    
    public ArrayList<Point> getGraphData()
    {
        taskOneSimulator.runSimulator(); //we just need this function to build a queue of packets
        packetQueue = taskOneSimulator.getPacketQueue();
        
        ArrayList<Point> points = new ArrayList();
        int numberOfPackets;
        
        //using the time interval of 5 us to 2000us 
        for( int i = 0; i <= 600; i++ )
        {
            numberOfPackets = 0;
            for( int j = 0; j < packetQueue.size(); j++ )
            {
                if( packetQueue.get(j).getAbsoluteTime() <= i && packetQueue.get(j).getTransCompleteTime() > i )
                    numberOfPackets++;
            }
            
            points.add( new Point(i, numberOfPackets) );
        }
        
        return points;
    }
           
}
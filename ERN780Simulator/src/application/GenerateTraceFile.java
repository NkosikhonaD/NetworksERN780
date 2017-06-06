package application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.DataInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import cern.jet.random.Poisson;
import cern.jet.random.engine.RandomEngine;
import cern.jet.random.engine.RandomSeedGenerator;
import cern.jet.random.engine.DRand;

public class GenerateTraceFile {
    

	 /*
	  * Computes service rate , as link capacity divided by package size average
	  * @param lc , link capacity ,
	  * @param packAv average packet size 
	  * 
	  *  returns service rate
	  */
	 
	 public static double calculateServiceRate(double lc,double packAv)
	 {
		 return (lc/packAv);
	 }
	 /*
	  * Computes arrival work load based on percentage of service Rate  100% arrival = service rate , 10 % arrival = 10% of serviceRate , as link 			capacity divided by package size average
	  * @param servRate , service rate ,
	  * @param percentage percentage 10.0,  20.0...90. 
	  *
	  *  returns arrival Rate or workload
	  */
	 public static double  calculateArrivalRate(double servRate, double percentage)
	 {
		 return (percentage/100)*servRate;
	 }
	 
	 /*Computes arrival Average 1/arrival Rate 
	  * @param arrivalRate,arrvial rate . 
	  * return arrival average in micro-seconds that is why its is multiplied by 1000000
	  */
	 public static double calculateArrivalRateAverage(double arrivalRate)
	 {
		 return (1.0/arrivalRate)*1000000; 
	 }
	 
	public static void writeToFile(double workLoad,double linkCapacity,double averagePacketSize,String pathToTracefile)
	{
		double servRate = calculateServiceRate(linkCapacity,averagePacketSize);
		double arrRate = calculateArrivalRate(servRate, workLoad);
		double averInterArrivalRate = calculateArrivalRateAverage(arrRate);
		Poisson interArrival;
		Poisson packetSize;
		String content="";
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToTracefile,true))) 
		{
			
			System.out.println("Writting to file \n ");
			for(int j=0;j<100;j++) 
			{
				DRand engine = new DRand();
				interArrival =new Poisson(averInterArrivalRate,engine);
				
				packetSize =new Poisson(averagePacketSize,engine);
				for(int k=0;k<50000;k++)
				{
					content=interArrival.nextInt()+","+packetSize.nextInt()+"\n";
					bw.write(content);
					System.out.println(content);	
				}
			}
			System.out.println("Done");

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		 writeToFile(10.0,100000000.0,1000.0,"/home/hltuser/networksLoad.txt");
	}
}
	 
package com.iptv.Bandwidth;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MeasureBandwidth {
	public String fileUrl;
	public double fileSize;
	
	public MeasureBandwidth(String fileUrl,double fileSize){
		this.fileUrl = fileUrl;
		this.fileSize = fileSize;
	}
	
	public void startMeasure(){
		BufferedReader br = null;
		URLConnection uc = null;
	    InputStream in =null;
	    long startTime = 0;
		try {
			URL fileURL = new URL(fileUrl);
			startTime = System.currentTimeMillis();
			uc = fileURL.openConnection();
			InputStream rawStream = uc.getInputStream();
			in = new BufferedInputStream(rawStream);
			
			int contentLength = uc.getContentLength();
		    
		    byte[] data = new byte[contentLength];
		    int bytesRead = 0;
		    int offset = 0;
		    
		    System.out.println("start download" + contentLength);
		    while (offset < contentLength) {
		    	try {
		    		System.out.println("rest .... " + (contentLength-offset));
		    		bytesRead = in.read(data, offset, data.length - offset);
		    	}catch (IOException e) {
		    		System.out.println("could not read");
		    		e.printStackTrace();
		    	}

				if (bytesRead == -1)
					break;
				offset += bytesRead;
		    }
		    
		    long endTime = System.currentTimeMillis();
		    
		    double speed = SpeedCalculation(startTime,endTime);
			
		    System.out.println("download speed "+ speed + "Kbps" + "duration sec"+ (endTime-startTime)/1000 );
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
		    try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private double SpeedCalculation(long startTime, long endTime) {
		int duration = (int) (endTime-startTime)/1000;
		long bitsLoad = (long)(fileSize * 1000 * 1000 * 8) ; //assume fileSize in bytes
		double speedBps = (bitsLoad/duration);
		double speedKbs = (speedBps/1024);
		double speedMbs = (speedKbs/1024);
		return speedKbs;
	}
}

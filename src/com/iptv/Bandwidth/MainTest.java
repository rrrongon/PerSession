package com.iptv.Bandwidth;

public class MainTest {

	public static void main(String[] args) {
		MeasureBandwidth measure = new MeasureBandwidth("http://38.108.92.184/720.ts", 4.4);
		measure.startMeasure();
	}

}

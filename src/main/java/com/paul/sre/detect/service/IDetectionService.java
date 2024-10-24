package com.paul.sre.detect.service;

import com.github.ruananswer.anomaly.DetectAnoms.ANOMSResult;

public interface IDetectionService {

	public ANOMSResult check(double maxAnoms, int numObsPerPeriod, double threshold, double alpha, long[] timestamps,
			double[] series);
	
	public ANOMSResult check(long[] timestamps, double[] series);

}

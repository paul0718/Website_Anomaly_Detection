package com.paul.sre.detect.service.impl;

import java.util.Arrays;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.github.ruananswer.anomaly.DetectAnoms;
import com.github.ruananswer.anomaly.DetectAnoms.ANOMSResult;
import com.paul.sre.detect.service.IDetectionService;

@Service
public class DetectionServiceImpl implements IDetectionService {

	private static final Logger logger = LogManager.getLogger(DetectionServiceImpl.class);

	@Override
	public ANOMSResult check(double maxAnoms, int numObsPerPeriod, double threshold, double alpha, long[] timestamps,
			double[] series) {
		if (timestamps.length <= 2 * numObsPerPeriod) {
			return null;
		}

		// TODO check arguments.....
		DetectAnoms.Config config = new DetectAnoms.Config();
		config.setMaxAnoms(maxAnoms);
		config.setNumObsPerPeriod(numObsPerPeriod);
		config.setAnomsThreshold(threshold);
		config.setAlpha(alpha);
		DetectAnoms detectAnoms = new DetectAnoms(config);

		return detectAnoms.anomalyDetection(timestamps, series);
	}

	@Override
	public ANOMSResult check(long[] timestamps, double[] series) {
		return this.check(0.49, 30, 1.05, 0.05, timestamps, series);
	}

	public long[] check(double[] series, double[]... histories) {
		return this.check(3, series, histories);
	}

	public long[] check(double alpha, double[] series, double[]... histories) {
		if (null == series || null == histories || series.length < 2 || histories.length < 1) {
			throw new IllegalArgumentException("invalid arguments");
		}
		logger.info("series: \t{}", Arrays.toString(series));
		logger.info("series: \t{}", Arrays.toString(histories[0]));

		double[] history = new double[series.length];
		int shortLength = (series.length < histories[0].length ? series.length : histories[0].length);
		if (histories.length > 1) {
			int length = histories.length;
			for (int i = 0; i < shortLength; i++) {
				double sum = 0;
				for (int j = 0; j < length; j++) {
					sum += histories[j][i];
				}
				history[i] = sum / length;
			}
			/**
			 * logger.info("history: \t{}", Arrays.toString(history)); for (int i = 0; i <
			 * histories.length; i++) { logger.info("history: \t{}",
			 * Arrays.toString(histories[i])); }
			 **/
		} else {
			// logger.info("history: \t{}", Arrays.toString(histories[0]));
			history = histories[0];
		}

		DescriptiveStatistics ds = null;
		double stdDevSum = 0.0;
		for (int i = 0; i < histories.length; i++) {
			ds = new DescriptiveStatistics(histories[i]);
			stdDevSum += ds.getStandardDeviation();
		}
		double stdDev = stdDevSum / histories.length;

		double dev = stdDev * alpha;
		long[] anomalyIndex = new long[series.length];
		int index = 0;
		for (int i = 0; i < shortLength; i++) {
			if (dev < Math.abs((series[i] - history[i]))) {
				anomalyIndex[index++] = i;
			}
		}
		if (index > 0) {
			if (index * 4 > series.length) {
				return this.check(1 + alpha, series, histories);
			}
			index -= 1;
		}
		logger.info("{} anomalyIndex: \t{}", index, Arrays.toString(anomalyIndex));
		return Arrays.copyOfRange(anomalyIndex, 0, index);
	}
}

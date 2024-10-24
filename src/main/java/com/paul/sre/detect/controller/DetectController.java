package com.paul.sre.detect.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ruananswer.anomaly.DetectAnoms.ANOMSResult;
import com.paul.sre.detect.controller.vo.DetectResult;
import com.paul.sre.detect.controller.vo.FetchFromPrometheusForm;
import com.paul.sre.detect.service.IDetectionService;
import com.paul.sre.detect.service.IPrometheusService;

@Controller()
@RequestMapping(value="detect")
public class DetectController {

	private static final Logger logger = LogManager.getLogger(DetectController.class);
	
	@Autowired
	private IDetectionService detectService;
	
	@Autowired
	private IPrometheusService prometheusService;
	
	public void detectWithData() {
		
	}
	
	@RequestMapping(value="detectWithFetch", method=RequestMethod.POST)
	public @ResponseBody DetectResult detectWithFetch(FetchFromPrometheusForm form) {
		//TODO validate the form
		String[][] points = this.prometheusService.queryRange(form.getEndpoint(), form.getQuery(), form.getStart(), form.getEnd(), form.getStep());
		logger.info("we get {} points", points.length);
		AnomalyDetectData convertedData = this.convertPrometheusFormatToAnomalyDetectFormat(points);
		ANOMSResult detectResult = this.detectService.check(convertedData.getTimestamps(), convertedData.getSeries());
		
		DetectResult result = new DetectResult();
		result.setTimestamps(convertedData.getTimestamps());
		result.setSeries(convertedData.getSeries());
		if (null != detectResult && null != detectResult.getAnomsIndex() && detectResult.getAnomsIndex().length > 0) {
			logger.info(detectResult.getAnomsIndex().length);
			result.setHasAnomaly(true);
			result.setAnomalyIndex(detectResult.getAnomsIndex());
			result.setAnomalyScore(detectResult.getAnomsScore());
		}
		
		return result;
	}
	
	private AnomalyDetectData convertPrometheusFormatToAnomalyDetectFormat(String[][] points) {
		if (null == points) {
			return null;
		}
		
		
		int length = points.length;
		long[] timestamps = new long[length];
		double[] series = new double[length];
		for (int i = 0; i < length; i++) {
			timestamps[i] = Long.valueOf(points[i][0]);
			series[i] = Double.valueOf(points[i][1]);
		}
		AnomalyDetectData data = new AnomalyDetectData();
		data.setSeries(series);
		data.setTimestamps(timestamps);
		
		return data;
	}
	
	static class AnomalyDetectData {
		private long[] timestamps;
		private double[] series;
		public long[] getTimestamps() {
			return timestamps;
		}
		public void setTimestamps(long[] timestamps) {
			this.timestamps = timestamps;
		}
		public double[] getSeries() {
			return series;
		}
		public void setSeries(double[] series) {
			this.series = series;
		}
	}
}

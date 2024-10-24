package com.paul.sre.detect.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.paul.sre.detect.service.IPrometheusService;

@Service
public class PrometheusServiceImpl implements IPrometheusService {

	private static final Logger logger = LogManager.getLogger(PrometheusServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	public String[][] queryRange(String endpoint, String query, long start, long end, int step) {
		//sample http://localhost:9090/api/v1/query_range?query=system_cpu_usage&start=1625994600&end=1626016200&step=20
		String queryUrl = String.format("%s/api/v1/query_range?query=%s&start=%d&end=%d&step=%d", endpoint, query, start, end, step);
		logger.info("queryUrl: " + queryUrl);
		
		PrometheusResponse rsps = restTemplate.getForObject(queryUrl, PrometheusResponse.class);
		if ("success".equals(rsps.getStatus())) {
			return rsps.getData().getResult().get(0).getValues();
		}
		
		return null;
	}
}
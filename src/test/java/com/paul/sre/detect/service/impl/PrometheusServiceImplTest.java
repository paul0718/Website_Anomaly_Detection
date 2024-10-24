package com.paul.sre.detect.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.paul.sre.detect.service.IPrometheusService;

@SpringBootTest
public class PrometheusServiceImplTest {

	private static final Logger logger = LogManager.getLogger(PrometheusServiceImplTest.class);
	
	@Autowired
	private IPrometheusService prometheusService;
	
	@Test
	public void testQueryRange() {
		String endpoint = "http://localhost:9090";
		String query = "system_cpu_usage";
		long start = 1625994600;
		long end = 1626016200;
		int step = 20;
		Object[][] result = prometheusService.queryRange(endpoint, query, start, end, step);
		logger.info("result is : " + result.length);
		
	}
}

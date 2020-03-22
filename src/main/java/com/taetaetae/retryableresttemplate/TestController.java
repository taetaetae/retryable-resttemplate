package com.taetaetae.retryableresttemplate;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

	@Autowired
	private RestTemplate retryableRestTemplate;

	@GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String employees() throws URISyntaxException {
		final String baseUrl = "http://dummy.restapiexample.com/api/v1/employeeszzz";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> exchange = retryableRestTemplate.exchange(uri, HttpMethod.GET, null, String.class);
		return exchange.getBody();
	}
}

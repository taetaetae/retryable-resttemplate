package com.taetaetae.retryableresttemplate;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@EnableRetry
@Configuration
public class RetryableRestTemplateConfiguration {

	@Bean
	public RestTemplate retryableRestTemplate() {
		SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		clientHttpRequestFactory.setReadTimeout(2000);
		clientHttpRequestFactory.setConnectTimeout(500);

		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory) {
			@Override
			@Retryable(value = RestClientException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000)) // retry 3, delay 1000ms
			public <T> ResponseEntity<T> exchange(URI url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType)
				throws RestClientException {
				return super.exchange(url, method, requestEntity, responseType);
			}

			@Recover
			public <T> ResponseEntity<String> exchangeRecover(RestClientException e) {
				return ResponseEntity.badRequest().body("bad request T.T");
			}
		};

		return restTemplate;
	}
}
package com.knewin.data.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

/**
 * Parent class that implements generic methods to request content from web service.
 * 
 * @since 1.0.0
 * 
 */
public abstract class DataRequest {

	final Gson jsonBuilder = new Gson();


	/**
	 * Request content using HTTP GET method.
	 * 
	 * @param url the URL
	 * 
	 * @return the response content
	 * 
	 * @throws IOException error in case of problems to request remote content
	 */
	String request(final String url) throws IOException {
		final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(30000).setSocketTimeout(30000)
			.setConnectTimeout(30000).build();

		try (final CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {
			return this.request(url, httpClient);

		}
	}


	/**
	 * Request content using HTTP GET method.
	 * 
	 * @param url the URL
	 * @param httpClient a {@link CloseableHttpClient} instance
	 * 
	 * @return the response content
	 * 
	 * @throws IOException error in case of problems to request remote content
	 */
	String request(final String url, final CloseableHttpClient httpClient) throws IOException {
		try (final CloseableHttpResponse httpResponse = httpClient.execute(new HttpGet(url))) {
			return EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
		}
	}


	/**
	 * Request content using HTTP POST method.
	 * 
	 * @param bodyContent the body content that contains the parameters request
	 * @param url the URL
	 * 
	 * @return the response content
	 * 
	 * @throws IOException error in case of problems to request remote content
	 */
	String request(final String bodyContent, final String url) throws IOException {
		final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(30000).setSocketTimeout(30000)
			.setConnectTimeout(30000).build();

		try (final CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {
			return this.request(bodyContent, url, httpClient);

		}
	}


	/**
	 * Request content using HTTP POST method.
	 * 
	 * @param bodyContent the body content that contains the parameters request
	 * @param url the URL
	 * @param httpClient a {@link CloseableHttpClient} instance
	 * 
	 * @return the response content
	 * 
	 * @throws IOException error in case of problems to request remote content
	 */
	String request(final String postContent, final String url, final CloseableHttpClient httpClient) throws IOException {
		final HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(postContent, StandardCharsets.UTF_8));

		try (final CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
			return EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
		}
	}

}

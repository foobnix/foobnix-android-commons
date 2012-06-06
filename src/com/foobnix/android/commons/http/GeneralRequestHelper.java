package com.foobnix.android.commons.http;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.foobnix.android.commons.util.LOG;
import com.google.common.base.Strings;

public abstract class GeneralRequestHelper {

	protected final DefaultHttpClient client;

	public GeneralRequestHelper() {
		client = new DefaultHttpClient();
	}

	public String get(String url) {
		LOG.d("GET");
		HttpGet request = new HttpGet(url);
		return httpRequest(request);
	}

	public String get(String url, BasicNameValuePair... params) {
		return get(url, Arrays.asList(params));
	}

	public String get(String url, List<BasicNameValuePair> params) {
		LOG.d("GET");
		HttpGet request = null;
		if (params != null) {
			String paramsList = URLEncodedUtils.format(params, "UTF-8");
			String reqUrl = url + "?" + paramsList;
			LOG.d("GET", reqUrl);
			request = new HttpGet(reqUrl);
		} else {
			request = new HttpGet(url);
		}
		return httpRequest(request);
	}

	public String delete(String url, List<BasicNameValuePair> params) {
		LOG.d("DELETE");
		HttpDelete request = null;
		if (params != null) {
			String paramsList = URLEncodedUtils.format(params, "UTF-8");
			String reqUrl = url + "?" + paramsList;
			LOG.d("GET", reqUrl);
			request = new HttpDelete(reqUrl);
		} else {
			request = new HttpDelete(url);
		}
		return httpRequest(request);
	}

	public String post(String url, List<BasicNameValuePair> params) {
		LOG.d("POST");
		logParams(params);
		HttpPost request = null;

		if (params != null) {
			request = new HttpPost(url);
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
				request.setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				LOG.e("POST exception", e);
			}

		} else {
			request = new HttpPost(url);
		}
		return httpRequest(request);
	}

	public String post(String url, String value) {
		LOG.d("POST");

		HttpPost request = new HttpPost(url);
		StringEntity entity;
		try {
			entity = new StringEntity(value, "UTF-8");
			request.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			LOG.e(e);
			return "";
		}

		return httpRequest(request);
	}

	public String put(String url, String value) {
		LOG.d("PUT", value);
		HttpPut request = new HttpPut(url);
		StringEntity entity;
		try {
			entity = new StringEntity(value, "UTF-8");
			request.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			LOG.e(e);
			return "";
		}

		return httpRequest(request);
	}

	public String put(String url, List<BasicNameValuePair> params) {
		logParams(params);
		HttpPut request = null;
		if (params != null) {
			request = new HttpPut(url);
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
				request.setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				LOG.e("POST exception", e);
			}

		} else {
			request = new HttpPut(url);
		}
		return httpRequest(request);
	}

	private void logParams(List<BasicNameValuePair> params) {
		for (BasicNameValuePair param : params) {
			LOG.d("PARAM ", param.getName(), " = ", param.getValue());
		}

	}

	public abstract String getAcceptHeaderString();

	private synchronized String httpRequest(HttpRequestBase request) {
		LOG.d("Request URI", request.getURI());
		LOG.d("Request METHOD", request.getMethod());

		String strResponse = "";
		try {
			if (!Strings.isNullOrEmpty(getAcceptHeaderString())) {
				request.addHeader("Accept", getAcceptHeaderString());
			}
			HttpResponse response = client.execute(request);

			HttpEntity entity = response.getEntity();
			strResponse = EntityUtils.toString(entity);
			LOG.d("Http Response");
			LOG.d("==========");
			LOG.d(strResponse);
			LOG.d("==========");
		} catch (Exception e) {
			LOG.e("Error Response ", e);
			return null;
		} finally {

		}
		return strResponse;
	}
}

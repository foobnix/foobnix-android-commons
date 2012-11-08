package com.foobnix.android.commons.http;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.foobnix.android.commons.util.LOG;
import com.google.common.base.Strings;

public class GeneralRequestHelper {

	private static final String UTF_8 = "UTF-8";
	private final DefaultHttpClient client;

	public GeneralRequestHelper() {
		HttpParams params = new BasicHttpParams();
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		ClientConnectionManager cm = new ThreadSafeClientConnManager(params, registry);
		client = new DefaultHttpClient(cm, params);
	}

	public String get(String url) {
		LOG.d("GET");
		HttpGet request = new HttpGet(url);
		return httpRequest(request);
	}

	public String get(String url, List<BasicNameValuePair> params) {
		return get(url, params, UTF_8);
	}

	public String get(String url, List<BasicNameValuePair> params, String encoding) {
		LOG.d("GET");
		HttpGet request = null;
		if (params != null) {
			String paramsList = URLEncodedUtils.format(params, encoding);
			String reqUrl = url + "?" + paramsList;
			LOG.d("GET", reqUrl);
			request = new HttpGet(reqUrl);
		} else {
			request = new HttpGet(url);
		}
		return httpRequest(request);
	}

	public boolean download(String url, File toFile) {
		LOG.d("Download", url, toFile.getPath());
		try {
			HttpGet request = new HttpGet(url);
			HttpResponse response = getClient().execute(request);
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity buf = new BufferedHttpEntity(entity);
			InputStream is = buf.getContent();

			OutputStream stream = new BufferedOutputStream(new FileOutputStream(toFile));
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				stream.write(buffer, 0, len);
			}
			if (stream != null)
				stream.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.e(e);
			return false;
		}
		return true;

	}

	public String delete(String url, List<BasicNameValuePair> params) {
		LOG.d("DELETE");
		HttpDelete request = null;
		if (params != null) {
			String paramsList = URLEncodedUtils.format(params, UTF_8);
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
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, UTF_8);
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
			entity = new StringEntity(value, UTF_8);
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
			entity = new StringEntity(value, UTF_8);
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
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, UTF_8);
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

	public String getAcceptHeaderString() {
		return "";
	}

	private synchronized String httpRequest(HttpRequestBase request) {
		LOG.d("Request URI", request.getURI());
		LOG.d("Request METHOD", request.getMethod());

		String strResponse = "";
		try {
			if (!Strings.isNullOrEmpty(getAcceptHeaderString())) {
				request.addHeader("Accept", getAcceptHeaderString());
			}

			HttpResponse response = getClient().execute(request);

			HttpEntity entity = response.getEntity();
			if (entity.getContentEncoding() != null) {
				LOG.d("Encoding", entity.getContentEncoding());
			}
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

	public DefaultHttpClient getClient() {
		return client;
	}
}

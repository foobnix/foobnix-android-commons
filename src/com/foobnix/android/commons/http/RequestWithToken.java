/**
 * 
 */
package com.foobnix.android.commons.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.google.common.base.Strings;

/**
 * @author iivanenko
 * 
 */
public abstract class RequestWithToken {

	private final String url;
	private final GeneralRequestHelper requestHelper;

	public RequestWithToken(String url, GeneralRequestHelper requestHelper) {
		if (Strings.isNullOrEmpty(url)) {
			throw new IllegalArgumentException("Illegar server url name (EMPTY)");
		}
		this.url = url;
		this.requestHelper = requestHelper;
	}

	public String doGET(String method, List<BasicNameValuePair> params) {
		return requestHelper.get(url + method, signSID(params));
	}

	public String doGET(String method) {
		return requestHelper.get(url + method, signSID());
	}

	public String doDELETE(String method, List<BasicNameValuePair> params) {
		return requestHelper.delete(url + method, signSID(params));
	}

	public String doDELETE(String method) {
		return requestHelper.delete(url + method, signSID());
	}

	public String doPOST(String method, List<BasicNameValuePair> params) {
		return requestHelper.post(url + method, signSID(params));
	}

	public String doPOST(String method, String value) {
		return requestHelper.post(url + method, value);
	}

	public String doPUT(String method, List<BasicNameValuePair> params) {
		return requestHelper.put(url + method, signSID(params));
	}

	public String doPUT(String method, String value) {
		return requestHelper.put(url + method, value);
	}

	private synchronized List<BasicNameValuePair> signSID() {
		return signSID(null);
	}

	private synchronized List<BasicNameValuePair> signSID(List<BasicNameValuePair> params) {
		if (getBasicNameValuePairs() == null) {
			return Collections.EMPTY_LIST;
		}
		ArrayList<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();

		for (BasicNameValuePair param : getBasicNameValuePairs()) {
			if (param != null && !Strings.isNullOrEmpty(param.getValue())) {
				list.add(param);
			}
		}

		if (params != null) {
			list.addAll(params);
		}
		return list;
	}

	public abstract List<BasicNameValuePair> getBasicNameValuePairs();

	public abstract void setAccessToken(String accessToken);

	public GeneralRequestHelper getRequestHelper() {
		return requestHelper;
	}

}

package halo.akwei.util.httpclient;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class HttpClient4Util {

	private static final TrustManager easyTrustManager = new X509TrustManager() {

		@Override
		public void checkClientTrusted(
		        X509Certificate[] chain,
		        String authType) throws CertificateException {
			// Oh, I am easy!
		}

		@Override
		public void checkServerTrusted(
		        X509Certificate[] chain,
		        String authType) throws CertificateException {
			// Oh, I am easy!
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

	public static HttpClient createHttpClient(int timeout) {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
		        timeout);
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
		return httpclient;
	}

	public static HttpClient createSSLHttpClient(int timeout) {
		HttpClient httpclient = createHttpClient(timeout);
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] { easyTrustManager }, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext,
			        SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme("https", 443, socketFactory);
			httpclient.getConnectionManager().getSchemeRegistry().register(sch);
			return httpclient;
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		catch (KeyManagementException e) {
			throw new RuntimeException(e);
		}
	}

	public static HttpResp doGet(HttpClient httpClient, String url) throws ClientProtocolException,
	        IOException {
		return doGet(httpClient, url, null, null);
	}

	public static HttpResp doGet(HttpClient httpClient, String url, HttpParameters httpParameter,
	        String charset) throws ClientProtocolException, IOException {
		StringBuilder sb = new StringBuilder(url);
		if (httpParameter != null && !httpParameter.isAllParameterEmpty()) {
			if (url.indexOf("?") == -1) {
				sb.append("?");
			}
			if (sb.charAt(sb.length() - 1) != ('?')) {
				sb.append("&");
			}
			for (BasicParameter o : httpParameter.getBasicParameters()) {
				sb.append(URLEncoder.encode(o.getName(), charset));
				sb.append("=");
				sb.append(URLEncoder.encode(o.getValue(), charset));
				sb.append("&");
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
		}
		HttpGet httpGet = new HttpGet(sb.toString());
		if (httpParameter != null && !httpParameter.isEmptyHeader()) {
			Set<Entry<String, String>> set = httpParameter.getHeaderMap()
			        .entrySet();
			for (Entry<String, String> e : set) {
				httpGet.addHeader(e.getKey(), e.getValue());
			}
		}
		return execute(httpClient, httpGet);
	}

	public static HttpResp doPostStringBody(HttpClient httpClient, String url, String string,
	        String charset)
	        throws ClientProtocolException, IOException {
		HttpEntity entity = new StringEntity(string, charset);
		return doPostBody(httpClient, url, entity);
	}

	public static HttpResp doPostBytesBody(HttpClient httpClient, String url, byte[] bytes)
	        throws ClientProtocolException, IOException {
		HttpEntity entity = new ByteArrayEntity(bytes);
		return doPostBody(httpClient, url, entity);
	}

	private static HttpResp doPostBody(HttpClient httpClient, String url, HttpEntity entity)
	        throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);
		return execute(httpClient, httpPost);
	}

	public static HttpResp doPost(HttpClient httpClient, String url, HttpParameters httpParameter,
	        String charset) throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		if (httpParameter.isFileParameterEmpty()) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			for (BasicParameter e : httpParameter.getBasicParameters()) {
				nameValuePairs.add(new BasicNameValuePair(e.getName(), e
				        .getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, charset));
		}
		else {
			MultipartEntity reqEntity = new MultipartEntity();
			for (FileParameter e : httpParameter.getFileParameters()) {
				if (e.getFile() != null) {
					reqEntity.addPart(e.getName(), new FileBody(e.getFile()));
				}
				else {
					reqEntity.addPart(e.getName(),
					        new ByteArrayBody(e.getData(), e.getFileName()));
				}
			}
			for (BasicParameter e : httpParameter.getBasicParameters()) {
				reqEntity.addPart(e.getName(), new StringBody(e.getValue(),
				        Charset.forName(charset)));
			}
			httpPost.setEntity(reqEntity);
		}
		if (httpParameter != null && !httpParameter.isEmptyHeader()) {
			Set<Entry<String, String>> set = httpParameter.getHeaderMap()
			        .entrySet();
			for (Entry<String, String> e : set) {
				httpPost.addHeader(e.getKey(), e.getValue());
			}
		}
		return execute(httpClient, httpPost);
	}

	private static HttpResp execute(HttpClient httpClient, HttpRequestBase request)
	        throws ClientProtocolException, IOException {
		HttpEntity entity = null;
		try {
			HttpResponse httpResponse = httpClient.execute(request);
			HttpResp httpResp = new HttpResp();
			httpResp.setStatusCode(httpResponse.getStatusLine().getStatusCode());
			httpResp.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
			entity = httpResponse.getEntity();
			httpResp.setBytes(EntityUtils.toByteArray(entity));
			return httpResp;
		}
		catch (ClientProtocolException e) {
			throw e;
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			if (entity != null) {
				EntityUtils.consume(entity);
			}
		}
	}

	public static void shutdown(HttpClient httpClient) {
		httpClient.getConnectionManager().shutdown();
	}
}
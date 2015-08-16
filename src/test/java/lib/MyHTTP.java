/* ===========================================================================
Created:	2015/08/12 - https://github.com/yes4me/
Author:		Thomas Nguyen - thomas_ejob@hotmail.com
Purpose:	My library
Inspiration:
	http://rest.elkstein.org/2008/02/using-rest-in-java.html
	http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
=========================================================================== */


package lib;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class MyHTTP {
	private Map<String, String> HTTPheader	= new HashMap<String, String>();
	private Map<String, String> HTTPbody	= new HashMap<String, String>();

	//========================================================
	// Constructor
	//========================================================
	public MyHTTP() {
	}

	//========================================================
	// UTILS
	//========================================================
	private static List<NameValuePair> convertList(Map<String, String> data) {
		List<NameValuePair> result = new ArrayList<NameValuePair>();

		Set<String> keys		= data.keySet();
		Iterator<String> it	= keys.iterator();
		while (it.hasNext())
		{
			String key		= it.next();
			String value	= data.get(key);
			result.add(new BasicNameValuePair(key, value));
		}
		return result;
	}

	//========================================================
	// Getters and setters
	//========================================================
	public void setHTTPheader(Map<String, String> hTTPheader) {
		this.HTTPheader = hTTPheader;
	}
	public void setHTTPbody(Map<String, String> hTTPbody) {
		this.HTTPbody = hTTPbody;
	}

	//========================================================
	// GET AND POST
	//========================================================
	public String GetString(String url) {
		CloseableHttpClient httpClient	= HttpClients.createDefault();
		HttpGet httpGet					= new HttpGet(url);
		String result					= "";

		//add request header
		if (!HTTPheader.isEmpty())
		{
			Set<String> keys		= HTTPheader.keySet();
			Iterator<String> it		= keys.iterator();
			while (it.hasNext())
			{
				String key		= it.next();
				String value 	= HTTPheader.get(key);
				httpGet.addHeader(key, value);
			}
		}

		try {
			// Execute the method and get the response
			CloseableHttpResponse response = httpClient.execute(httpGet);

			// Print the HTTP status
			//System.out.println("getStatusLine:"+ response.getStatusLine());

			// get the entity from the response
			HttpEntity entity = response.getEntity();

			// Convert the entity into a string
			result = EntityUtils.toString(entity);

			httpClient.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean GetFile(String url, String filePath) {
		CloseableHttpClient httpClient	= HttpClients.createDefault();
		HttpGet httpGet					= new HttpGet(url);

		//add request header
		if (!HTTPheader.isEmpty())
		{
			Set<String> keys		= HTTPheader.keySet();
			Iterator<String> it		= keys.iterator();
			while (it.hasNext())
			{
				String key		= it.next();
				String value 	= HTTPheader.get(key);
				httpGet.addHeader(key, value);
			}
		}

		try {
			// Execute the method and get the response
			CloseableHttpResponse response = httpClient.execute(httpGet);

			// Print the HTTP status
			//System.out.println("getStatusLine:"+ response.getStatusLine());

			// get the entity from the response
			HttpEntity entity = response.getEntity();

			// Convert the entity into a file
			BufferedInputStream bis = new BufferedInputStream(entity.getContent());
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			int inByte;
			while((inByte = bis.read()) != -1)
				bos.write(inByte);
			bis.close();
			bos.close();			

			httpClient.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//The old way to do stuff
	public String Get_old(String urlParam) {
		// Buffer the result into a string
		try {
			//Make connection
			URL url = new URL(urlParam);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			//add request header
			if (!HTTPheader.isEmpty())
			{
				Set<String> keys		= HTTPheader.keySet();
				Iterator<String> it		= keys.iterator();
				while (it.hasNext())
				{
					String key		= it.next();
					String value 	= HTTPheader.get(key);
					conn.setRequestProperty(key, value);
				}
			}

			if (conn.getResponseCode() != 200)
				return "";

			//BufferedReader br	= new BufferedReader(new InputStreamReader( url.openStream()) );
			BufferedReader br	= new BufferedReader( new InputStreamReader( conn.getInputStream() ));
			StringBuilder sb	= new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				sb.append(line);
			}
			br.close();

			conn.disconnect();
			return sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	// ##########################################################################

	public String Post(String url) throws Exception {
		CloseableHttpClient httpClient	= HttpClients.createDefault();
		HttpPost httpPost				= new HttpPost(url);
		String result					= "";

		//add request header
		if (!HTTPheader.isEmpty())
		{
			Set<String> keys		= HTTPheader.keySet();
			Iterator<String> it		= keys.iterator();
			while (it.hasNext())
			{
				String key		= it.next();
				String value 	= HTTPheader.get(key);
				httpPost.setHeader(key, value);
			}
		}

		//add request body
		if (!HTTPbody.isEmpty())
		{
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters = convertList(HTTPbody);
			httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
		}

		// Execute POST
		CloseableHttpResponse response = httpClient.execute(httpPost);

		try {
			//Print the status code
			//System.out.println("getStatusLine:"+ response.getStatusLine());	//HTTP/1.1 200 OK
			//Print the response code
			//System.out.println("Response Code:"+ response.getStatusLine().getStatusCode());
			//Print the re-direct URL
			//System.out.println("Location:"+ response.getFirstHeader("Location"));
			//Print post parameters
			//System.out.println("Post parameters:"+ httpPost.getEntity());

			BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
		} finally {
			httpClient.close();
		}
		return result;
	}

	//========================================================
	// FOR TESTING PURPOSE ONLY
	//========================================================
	public static void main(String[] args) throws Exception {
		MyHTTP o = new MyHTTP();
		String response = o.GetString("http://www.thomas-bayer.com/sqlrest/");
		//String response = o.Post("http://www.httpbin.org/forms/post");
		System.out.println("##"+ response +"##");
	}
}
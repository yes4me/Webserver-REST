/* ===========================================================================
Created:	2015/08/12 - https://github.com/yes4me/
Author:		Thomas Nguyen - thomas_ejob@hotmail.com
Purpose:	Download the last "TWITTER_COUNT" tweets from "TWITTER_USER"
Inspiration for tweets:
	https://dev.twitter.com/oauth/application-only
	https://dev.twitter.com/rest/public/timelines
	https://dev.twitter.com/rest/reference/get/statuses/user_timeline
=========================================================================== */

package edu.ucsc.extension.rest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.net.URLEncoder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lib.MyFile;
import lib.MyHTTP;
import lib.MyJSON;

import org.apache.commons.codec.binary.Base64;

public class Twitter {
	private static final String FILENAME_JSON		= "tweets.json";

	private static final String API_KEY				= "ENTER_YOUR__APP _API__KEY";
	private static final String API_SECRET			= "ENTER_YOUR_APP_API_SECRET__SHOULD_BE_THE_SAME_SIZE";

	private static final String TWITTER_AUTH_URL	= "https://api.twitter.com/oauth2/token";
	private static final String TWITTER_HOST		= "api.twitter.com";
	private static final String TWITTER_USER		= "berniesanders";
	private static final int	TWITTER_COUNT		= 50;
	private static final String TWITTER_URL			= "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name="+ TWITTER_USER +"&count="+ TWITTER_COUNT;


	// Encodes the consumer key and secret to create the basic authorization key
	private static String encodeKeys(String consumerKey, String consumerSecret) {
		try {
			String encodedConsumerKey		= URLEncoder.encode(consumerKey, "UTF-8");
			String encodedConsumerSecret	= URLEncoder.encode(consumerSecret, "UTF-8");
			String bearerTokenCredentials	= encodedConsumerKey + ":" + encodedConsumerSecret;
			byte[] encodedBytes = Base64.encodeBase64(bearerTokenCredentials.getBytes());
			return new String(encodedBytes); 
		}
		catch (UnsupportedEncodingException e) {
		}
		return new String();
	}

	public String getBearerToken()
	{
		//STEP 1: ENCODE CONSUMER KEY AND SECRET
		String Base64EncodedBearerTokenCredentials = encodeKeys(API_KEY, API_SECRET);
		//System.out.println("Base64EncodedBearerTokenCredentials="+ Base64EncodedBearerTokenCredentials);


		//STEP 2: OBTAIN A BEARER TOKEN
		MyHTTP myHTTP = new MyHTTP();

		//Setup the HTTP header
		HashMap<String, String> HTTPheader = new HashMap<String, String>();
		HTTPheader.put("Host", TWITTER_HOST);
		HTTPheader.put("User-Agent", "My Twitter App v1.0.23");
		HTTPheader.put("Authorization","Basic " + Base64EncodedBearerTokenCredentials);
		HTTPheader.put("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		HTTPheader.put("Accept-Encoding", "gzip");
		HTTPheader.put("grant_type", "client_credentials");
		myHTTP.setHTTPheader(HTTPheader);

		//Setup the HTTP body
		HashMap<String, String> HTTPbody = new HashMap<String, String>();
		HTTPbody.put("grant_type", "client_credentials");
		myHTTP.setHTTPbody(HTTPbody);

		String resultJSON;
		try {
			resultJSON = myHTTP.Post(TWITTER_AUTH_URL);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		//System.out.println("result="+ resultJSON);


		//STEP 3: AUTHENTICATE API REQUESTS WITH THE BEARER TOKEN
		MyJSON myJSON = new MyJSON(resultJSON);
		String tokenType	= myJSON.getValue("token_type");		//Should be "bearer"
		String accessToken	= myJSON.getValue("access_token");
		//System.out.println("tokenType="+ tokenType );
		//System.out.println("accessToken="+ accessToken );
		return ((tokenType.equals("bearer")) && (accessToken!=null))? accessToken : "";

	}

	//It is probably best to save the tweets as the JSON is large
	public boolean saveJSON(String url, String accessToken, String filePath) {
		MyHTTP myHTTP = new MyHTTP();

		//Setup the HTTP header
		HashMap<String, String> HTTPheader2 = new HashMap<String, String>();
		HTTPheader2.put("Host", TWITTER_HOST);
		HTTPheader2.put("User-Agent", "My Twitter App v1.0.23");
		HTTPheader2.put("Authorization", "Bearer "+ accessToken);
		myHTTP.setHTTPheader(HTTPheader2);
		//return myHTTP.GetString(url); 
		return myHTTP.GetFile(url, filePath); 
	}
	//STEP 4: Get the last 50 tweets
	public void getTweets(String key, String filePath) {
		if (!MyFile.fileExist(filePath))
			return;

		JsonParser jsonParser = new JsonParser();
		JsonArray resultArray	= jsonParser.parse( MyFile.reader(filePath) ).getAsJsonArray();
		for (int i=0; i<resultArray.size(); i++) {
			JsonElement result = resultArray.get(i);
			JsonObject jsonObj = result.getAsJsonObject();
			String tweet = jsonObj.get(key).toString();
			tweet = tweet.replaceAll("^[\"]", "");
			tweet = tweet.replaceAll("[\"]$", "");

			System.out.println("tweet#"+ i +":"+ tweet);
		}
	}

	public static void main(String[] args) {
		Twitter twitter		= new Twitter();
		String accessToken	= twitter.getBearerToken();
		twitter.saveJSON(TWITTER_URL, accessToken, FILENAME_JSON);
		twitter.getTweets("text", FILENAME_JSON);
	}
}
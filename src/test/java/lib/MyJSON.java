/* ===========================================================================
Created:	2015/08/14 - https://github.com/yes4me/
Author:		Thomas Nguyen - thomas_ejob@hotmail.com
Purpose:	My library
=========================================================================== */

package lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class MyJSON {
	private Map<String, String> data = new HashMap<String, String>();

	//========================================================
	// Constructor
	//========================================================
	public MyJSON(String JSONstr)
	{
		data = convertMap(JSONstr);
	}

	//========================================================
	// CONVERSION
	//========================================================
	public Map<String, String> convertMap(String JSONstr)
	{
		Gson gson = new Gson();
		try {
			return gson.fromJson(JSONstr, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//========================================================
	// OTHERS
	//========================================================
	public String getAllKeys() {
		return data.keySet().toString();
	}

	public String getValue(String key) {
		return data.get(key);
	}
	public String getValue(String JSONstr, String key) {
		data = convertMap(JSONstr);
		return data.get(key);
	}

	/*
	public List<String> getAllValues(String JSONstr, String key) {
		List<String> values	= new ArrayList<String>();
		return values;
	}
	*/
}
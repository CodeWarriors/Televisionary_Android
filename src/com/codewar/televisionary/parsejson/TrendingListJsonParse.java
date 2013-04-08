package com.codewar.televisionary.parsejson;

import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codewar.televisionary_android.R;



public class TrendingListJsonParse {
	
	public HashMap<String, Object> parse(JSONArray result){
		JSONArray trendingJsonArray = null;

		return getTrendingShow(result);
	}


	public HashMap<String, Object> getTrendingShow(
			JSONArray trendingJsonArray) {
		
		int ObjectCount =  trendingJsonArray.length();

		HashMap<String, Object> Show = new HashMap<String, Object>();

		try {

			for (int i = 0; i <ObjectCount; i++) {
				JSONObject jShowObject = trendingJsonArray.getJSONObject(i);
				
				String title = jShowObject.getString("title");
				String overview = jShowObject.getString("overview");
				JSONObject jShowImages = jShowObject.getJSONObject("images");
				String pos_url = jShowImages.getString("screen");
				
				
				Show.put("title", title);
				Show.put("overview", overview);
				Show.put("screen",	R.drawable.blank);
				Show.put("image_url", pos_url);
				
				
			//	mylist.add(map);
			//	showData.add(new ShowData(title,overview,pos_url));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return Show;
	}

}

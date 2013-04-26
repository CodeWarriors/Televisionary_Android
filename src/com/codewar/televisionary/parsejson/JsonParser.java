
package com.codewar.televisionary.parsejson;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codewar.televisionary.sourcelinks.ImageUrlFormat;

import android.util.Log;

public class JsonParser{

	private JSONArray	                       jtrendingListArray	   = null;
	private JSONArray	                       jSeasonListArray	       = null;
	private ArrayList<HashMap<String, Object>>	showList	           = new ArrayList<HashMap<String, Object>>( );
	private ArrayList<HashMap<String, Object>>	seasonList	           = new ArrayList<HashMap<String, Object>>( );
	private ArrayList<String>	               details	               = new ArrayList<String>( );
	private HashMap<String, Object>	               profileDetails	       = new HashMap<String, Object>( );

	private final static String	               SHOW_TITLE	           = "title";
	private final static String	               SHOW_YEAR	           = "year";
	private final static String	               SHOW_URL	               = "url";
	private final static String	               SHOW_FIRST_AIRED	       = "first_aired";
	private final static String	               SHOW_COUNTRY	           = "country";
	private final static String	               SHOW_OVERVIEW	       = "overview";
	private final static String	               SHOW_RUNTIME	           = "runtime";
	private final static String	               SHOW_NETWORK	           = "network";
	private final static String	               SHOW_AIR_DAY	           = "air_day";
	private final static String	               SHOW_AIR_TIME	       = "air_time";
	private final static String	               SHOW_CERTIFICATION	   = "certification";
	private final static String	               SHOW_IMDB_ID	           = "imdb_id";
	private final static String	               SHOW_TVDB_ID	           = "tvdb_id";
	private final static String	               SHOW_TVRAGE_ID	       = "tvrage_id";
	private final static String	               SHOW_IMAGES_POSTER	   = "poster";
	private final static String	               SHOW_IMAGES_FANART	   = "fanart";
	private final static String	               SHOW_IMAGES_BANNER	   = "banner";
	private final static String	               SHOW_WATCHERS	       = "watchers";
	private final static String	               SHOW_RATINGS_PERCENTAGE	= "percentage";
	private final static String	               SHOW_RATINGS_VOTES	   = "votes";
	private final static String	               SHOW_RATINGS_LOVED	   = "loved";
	private final static String	               SHOW_RATINGS_HATED	   = "hated";
	private final static String	               SHOW_GENRES	           = "genres";
	private final static String	               SHOW_RATINGS	           = "ratings";
	private final static String	               SHOW_IMAGES	           = "images";

	// season list
	private final static String	               SEASON_NO	           = "season";
	private final static String	               SEASON_EPISODE_COUNT	   = "episodes";
	private final static String	               SEASON_URL	           = "url";
	private final static String	               SEASON_POSTER	       = "poster";

	//profile details
	private final static String LOGIN_STATUS = "status";
	private final static String LOGIN_ERROR = "error";
	private final static String LOGIN_MESSAGE = "message";
	private final static String PROFILE = "profile";
	private final static String PROFILE_USERNAME = "username";
	private final static String PROFILE_FULLNAME = "fullname";
	private final static String PROFILE_GENDER = "gender";
	private final static String PROFILE_AGE = "age";
	private final static String PROFILE_LOCATION = "location";
	private final static String PROFILE_ABOUT ="about";
	private final static String PROFILE_JOINED  = "joined";
	private final static String PROFILE_LAST_LOGIN  ="last_login";
	private final static String PROFILE_AVATAR = "avatar";
	private final static String PROFILE_URL = "url";
	private final static String PROFILE_VIP  = "vip";
	private final static String ACCOUNT = "account";
	private final static String ACCOUNT_TIMEZONE = "timezone";
	private final static String ACCOUNT_USE_24HR = "use_24hr";
	private final static String ACCOUNT_PROTECTED = "protected";//there're some more functions 
	private final static String SHARING_TEXT ="sharing_text";
	private final static String SHARING_TEXT_WATCHING = "watching";
	private final static String SHARING_TEXT_WATCHED = "watched";
	
	
	
	public JsonParser(String Jsondata, int key){

		switch(key){
		case 1:
			setTrendingListData(Jsondata);
			break;
		case 2:
			setShowDetailsData(Jsondata);
			break;
		case 3:
			setSeasonData(Jsondata);
		case 4:
			setProfileDetails(Jsondata);
		default:
			break;
		}
	}
	
	

	public void setProfileDetails(String _jsondata){

		JSONObject jLoginObject;


		try{
			jLoginObject = new JSONObject(_jsondata);
			
			
			if(jLoginObject.get(LOGIN_STATUS).toString( ).equals("success") ){
				JSONObject jProfile = jLoginObject.getJSONObject(PROFILE);
				JSONObject jAccount = jLoginObject.getJSONObject(ACCOUNT);
				JSONObject jSharingText = jLoginObject.getJSONObject(SHARING_TEXT);

				profileDetails.put(LOGIN_STATUS, jLoginObject.get(LOGIN_STATUS));
				profileDetails.put(LOGIN_MESSAGE, jLoginObject.get(LOGIN_MESSAGE));
				
				profileDetails.put(PROFILE_USERNAME, jProfile.get(PROFILE_USERNAME));
				profileDetails.put(PROFILE_FULLNAME, jProfile.get(PROFILE_FULLNAME));
				profileDetails.put(PROFILE_GENDER, jProfile.get(PROFILE_GENDER));
				profileDetails.put(PROFILE_AGE, jProfile.get(PROFILE_AGE));
				profileDetails.put(PROFILE_LOCATION, jProfile.get(PROFILE_LOCATION));
				profileDetails.put(PROFILE_ABOUT, jProfile.get(PROFILE_ABOUT));
				profileDetails.put(PROFILE_JOINED, jProfile.get(PROFILE_JOINED));
				profileDetails.put(PROFILE_LAST_LOGIN, jProfile.get(PROFILE_LAST_LOGIN));
				profileDetails.put(PROFILE_AVATAR, jProfile.get(PROFILE_AVATAR));
				profileDetails.put(PROFILE_URL, jProfile.get(PROFILE_URL));
				profileDetails.put(PROFILE_VIP, jProfile.get(PROFILE_VIP));
				
				profileDetails.put(ACCOUNT_TIMEZONE, jAccount.get(ACCOUNT_TIMEZONE));
				profileDetails.put(ACCOUNT_USE_24HR, jAccount.get(ACCOUNT_USE_24HR));
				profileDetails.put(ACCOUNT_PROTECTED, jAccount.get(ACCOUNT_PROTECTED));
				
				profileDetails.put(SHARING_TEXT_WATCHED, jSharingText.get(SHARING_TEXT_WATCHED));
				profileDetails.put(SHARING_TEXT_WATCHING, jSharingText.get(SHARING_TEXT_WATCHING));
			}else{
				profileDetails.put(LOGIN_STATUS, jLoginObject.get(LOGIN_STATUS));
				profileDetails.put(LOGIN_ERROR, jLoginObject.get(LOGIN_ERROR));
			}
			
			

		} catch(JSONException e){
			e.printStackTrace( );

		} catch(Exception e){
			e.printStackTrace( );

		}
	}

	public HashMap<String , Object> getProfileDetails( ){
		return profileDetails;
	}
	
	
	
	

	public void setTrendingListData(String _jsondata){

		try{
			jtrendingListArray = new JSONArray(_jsondata);
			for(int i = 0; i < 20; i++){
				HashMap<String, Object> Show = new HashMap<String, Object>( );

				JSONObject jShowObject = jtrendingListArray.getJSONObject(i);
				JSONObject jShowImages = jShowObject.getJSONObject(SHOW_IMAGES);
				JSONObject jShowRatings = jShowObject.getJSONObject(SHOW_RATINGS);

				String title = jShowObject.getString(SHOW_TITLE);
				int year = jShowObject.getInt(SHOW_YEAR);
				String url = jShowObject.getString(SHOW_URL);
				int first_aired = jShowObject.getInt(SHOW_FIRST_AIRED);
				String country = jShowObject.getString(SHOW_COUNTRY);
				String overview = jShowObject.getString(SHOW_OVERVIEW);
				int runtime = jShowObject.getInt(SHOW_RUNTIME);
				String network = jShowObject.getString(SHOW_NETWORK);
				String air_day = jShowObject.getString(SHOW_AIR_DAY);
				String air_time = jShowObject.getString(SHOW_AIR_TIME);
				String certification = jShowObject.getString(SHOW_CERTIFICATION);
				String imdb_id = jShowObject.getString(SHOW_IMDB_ID);
				String tvdb_id = jShowObject.getString(SHOW_TVDB_ID);
				String tvrage_id = jShowObject.getString(SHOW_TVRAGE_ID);
				String pos_url = jShowImages.getString(SHOW_IMAGES_POSTER);
				String fan_url = jShowImages.getString(SHOW_IMAGES_FANART);
				String banner_url = jShowImages.getString(SHOW_IMAGES_BANNER);
				int watchers = jShowObject.getInt(SHOW_WATCHERS);
				int ratings_percentage = jShowRatings.getInt(SHOW_RATINGS_PERCENTAGE);
				int ratings_votes = jShowRatings.getInt(SHOW_RATINGS_VOTES);
				int ratings_loved = jShowRatings.getInt(SHOW_RATINGS_LOVED);
				int ratings_hated = jShowRatings.getInt(SHOW_RATINGS_HATED);

				Show.put(SHOW_TITLE, title);
				Show.put(SHOW_YEAR, year);
				Show.put(SHOW_URL, url);
				Show.put(SHOW_FIRST_AIRED, first_aired);
				Show.put(SHOW_COUNTRY, country);
				Show.put(SHOW_OVERVIEW, overview);
				Show.put(SHOW_RUNTIME, runtime);
				Show.put(SHOW_NETWORK, network);
				Show.put(SHOW_AIR_DAY, air_day);
				Show.put(SHOW_AIR_TIME, air_time);
				Show.put(SHOW_CERTIFICATION, certification);
				Show.put(SHOW_IMDB_ID, imdb_id);
				Show.put(SHOW_TVDB_ID, tvdb_id);
				Show.put(SHOW_TVRAGE_ID, tvrage_id);
				Show.put(SHOW_IMAGES_POSTER, pos_url);
				Show.put(SHOW_IMAGES_FANART, fan_url);
				Show.put(SHOW_IMAGES_BANNER, banner_url);
				Show.put(SHOW_WATCHERS, watchers);
				Show.put(SHOW_RATINGS_PERCENTAGE, ratings_percentage);
				Show.put(SHOW_RATINGS_VOTES, ratings_votes);
				Show.put(SHOW_RATINGS_LOVED, ratings_loved);
				Show.put(SHOW_RATINGS_HATED, ratings_hated);

				showList.add(Show);
			}
		} catch(JSONException e){
			e.printStackTrace( );
		} catch(Exception e){
			e.printStackTrace( );
		}
	}

	public ArrayList<HashMap<String, Object>> getTrendingListData( ){
		return showList;
	}

	public void setShowDetailsData(String _jsondata){

		JSONObject jDetailObject;
		JSONObject jShowImages;
		JSONObject jRaitng;

		try{
			jDetailObject = new JSONObject(_jsondata);
			jShowImages = jDetailObject.getJSONObject("images");
			jRaitng = jDetailObject.getJSONObject("ratings");

			String title = jDetailObject.getString("title");
			String overview = jDetailObject.getString("overview");
			String air_day = jDetailObject.getString("air_day");
			String air_time = jDetailObject.getString("air_time");
			String network = jDetailObject.getString("network");
			String pos_url = jShowImages.getString("poster");
			String fanart_url = jShowImages.getString("fanart");
			String banner_url = jShowImages.getString("banner");
			String rating_percentage = jRaitng.getString("percentage");
			String reting_loved = jRaitng.getString("loved");

			details.add(title);
			details.add(overview);
			details.add(air_day);
			details.add(air_time);
			details.add(network);
			details.add(pos_url);
			details.add(fanart_url);
			details.add(banner_url);
			details.add(rating_percentage);
			details.add(reting_loved);

		} catch(JSONException e){
			e.printStackTrace( );

		} catch(Exception e){
			e.printStackTrace( );

		}
	}

	public ArrayList<String> getShowDetailData( ){
		return details;
	}

	public void setSeasonData(String _jsondata){
		Log.d("Received Json Data", _jsondata);
		try{
			jSeasonListArray = new JSONArray(_jsondata);

			for(int i = 0; i < jSeasonListArray.length( ); i++){
				HashMap<String, Object> Season = new HashMap<String, Object>( );
				JSONObject jSeasonObject = jSeasonListArray.getJSONObject(i);
				String season = jSeasonObject.getString(SEASON_NO);
				String episode_count = jSeasonObject.getString(SEASON_EPISODE_COUNT);
				String poster_url = jSeasonObject.getString(SEASON_POSTER);

				Season.put(SEASON_NO, season);
				Season.put(SEASON_EPISODE_COUNT, episode_count);
				Season.put(SEASON_POSTER, poster_url);
				seasonList.add(Season);
			}
		} catch(JSONException e){
			e.printStackTrace( );
		} catch(Exception e){
			e.printStackTrace( );
		}
	}

	public ArrayList<HashMap<String, Object>> getSeasonData( ){
		return seasonList;
	}
}

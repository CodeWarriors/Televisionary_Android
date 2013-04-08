package com.codewar.televisionary.sourcelinks;

public class SourceLinks {

			private static final String API_KEY= "18cced3e20fb3673dab6f5deb5dd56be";
			
			private static final String TRENDING_SHOWS_LINK = "http://api.trakt.tv/shows/trending.json/"+API_KEY;
			
			private static final String SHOW_DETAILS_LINK = "http://api.trakt.tv/show/summary.json/"+API_KEY;
			
			private static final String SHOW_SEASONS_LINK = "http://api.trakt.tv/show/seasons.json/"+API_KEY;
			
			private static final String SHOW_EPISODES_LINK = "http://api.trakt.tv/show/season.json/"+API_KEY;
			
			private static final String SHOW_EPISODE_DETAIL_LINK = "http://api.trakt.tv/show/episode/summary.json/"+API_KEY;
			
			public String trending_shows;
			public String show_details;
			public String show_seasons;
			public String show_episodes;
			public String show_episode_details;
			public String formattedShowName;
			
	
			//get trending show list
			public String getTrending_shows() {
				return trending_shows;
			}
			public void setTrending_shows() {
				this.trending_shows = TRENDING_SHOWS_LINK;
			}
			
			//get show details
			public String getShow_details() {
				return show_details;
			}
			public void setShow_details(String showName) {
				setShowName(showName);
				this.show_details = SHOW_DETAILS_LINK+"/"+getShowName() ;
			}
			
			//get season list
			public String getShow_seasons() {
				return show_seasons;
			}
			public void setShow_seasons(String showName) {
				setShowName(showName);
				this.show_seasons = SHOW_SEASONS_LINK+"/"+getShowName();
			}
			
			//get episode list
			public String getShow_episodes() {
				return show_episodes;
			}
			public void setShow_episodes(String showName, String season) {
				setShowName(showName);
				this.show_episodes = SHOW_EPISODES_LINK+"/"+getShowName()+"/"+season;
			}
			
			//get episode details
			public String getShow_episode_details() {
				return show_episode_details;
			}
			public void setShow_episode_details(String showName, String season, String episode) {
				setShowName(showName);
				this.show_episode_details = SHOW_EPISODE_DETAIL_LINK+"/"+getShowName()+"/"+season+"/"+episode;
			}

			
			//format show name string
			private String getShowName(){
				return formattedShowName;
				}
			private void setShowName(String showName){
				String showname_lower = showName.toLowerCase();
				formattedShowName = showname_lower.replace(" ", "-");
			}
}


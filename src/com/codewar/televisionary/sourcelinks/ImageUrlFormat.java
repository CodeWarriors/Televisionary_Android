
package com.codewar.televisionary.sourcelinks;

public class ImageUrlFormat{

	/**For Posters
	 * uncompressed 1000 x 1500		
	 * small posters 138 x 203 	 key = -138
	 * large posters 300 x 450   key = -300
	 */
	
	/**For Fanart
	 * uncompressed 1920 x 1080
	 * large fanart 940 x 529 	 key = -940
	 * small fanart 218 x 123    key = -218
	 */
	
	/**For Episodes Images
	 * uncompressed 400 x 225
	 * small episode 218 x 123 	 key = -218
	 */
	
	/**For Banners
	 * Uncompressed 758 x 140
	 */
	
	private String	new_url;

	public ImageUrlFormat(String Url, String size){
		setImageUrl(Url, size);
	}

	public void setImageUrl(String u, String f){
		String[] splitStr = u.split(".jpg");
		new_url = splitStr[0] + "-" + f + ".jpg";
	}

	public String getImageUrl( ){
		return new_url;
	}

}

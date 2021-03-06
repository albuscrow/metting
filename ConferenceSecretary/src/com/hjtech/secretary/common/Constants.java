package com.hjtech.secretary.common;

/**
 * The Interface Constants.
 * 储存一些常量
 * @author albuscrow
 */
public interface Constants {
	
	/** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY. */
	public static final String APP_KEY      = "2019371749";

	/** 
	 * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
	 * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
	 */
//	public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
	public static final String REDIRECT_URL = "http://www.hjtechcn.cn";

	/** WeiboSDKDemo 应用对应的权限，第三方开发者一般不需要这么多，可直接设置成空即可。 详情请查看 Demo 中对应的注释。. */
	public static final String SCOPE = 
			"email,direct_messages_read,direct_messages_write,"
					+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
					+ "follow_app_official_microblog," + "invitation_write";
	
	/** The Constant APP_SECRET. */
	public static final String APP_SECRET = "83c5851074fc2c15f9530d81acf84c5d";
	
	/** The Constant APP_ID. */
	public static final String APP_ID = "wxbc34d7b4abdd9efd";
	
	/** The Constant XG_V2_SECRET_KEY. */
	public static final String XG_V2_SECRET_KEY = "d477e8a3accb1464457677520fc05aee";

	
}

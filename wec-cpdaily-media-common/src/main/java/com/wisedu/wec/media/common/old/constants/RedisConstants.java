package com.wisedu.wec.media.common.old.constants;

/**
 * @author 01112143
 */
public class RedisConstants {

	// redis存储值的region
	public static final String REDIS_REGION_CPDAILY_SESSION = "session";// 今日校园使用的缓存key
	public static final String REDIS_REGION_CPDAILY_UID_SESSION = "userSessions"; // 校园号管理平台所使用的缓存key
	public static final String REDIS_REGION_MEDIA_QRCODE = "mediaQrCode";
	public static final String REDIS_REGION_MEDIA_SESSION = "mediaSession";
	public static final String REDIS_REGION_LOGIN_ID = "mediaLoginId";
	public static final String REDIS_REGION_MEDIA_ID = "mediaId";

	public static final String REDIS_KEY_USER_CONTEXT = "userContext";// 将userContext存到redis供校园活动取用
	public static final String REDIS_REGION_MEDIA_SESSION_TOKEN = "mediaSessionToken";//redis中，mediaSessionToken的key
	public static final String CPDAILY_SESSION_TOKEN_HERDER_KEY = "cpdailySessionToken";//今日校园APP放在头里的sessionToken的key


	public static final String CPDAILY_USERINFO_HEADER_KEY = "cpdailyUserInfo";
	public static final String MEDIA_LOGIN_KEY = "mediaLoginKey";
	public static final int DEFAULT_CACHE_TIMEOUT = 2*3600;
}

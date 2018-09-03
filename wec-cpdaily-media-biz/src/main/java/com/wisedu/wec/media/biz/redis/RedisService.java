package com.wisedu.wec.media.biz.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 01112143
 */
@Service
public class RedisService {

	private Logger LOGGER = LoggerFactory.getLogger(RedisService.class);

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

//	@Value("${redis.host}")
//	private String redisHost;
//	@Value("${redis.default.db}")
//	private int redisDb;
//	@Value("${mybatis_jdbc_url}")
//	private String jdbcUrl;
//	@Value("${mybatis_jdbc_password}")
//	private String jdbcPwd;
//	@Value("${mybatis_jdbc_username}")
//	private String jdbcUserName;
//	@Value("${jdbc.driverClassName}")
//	private String jdbcDriverName;

	private void log(){
//		LOGGER.info("redis host:{}，redisDb：{},jdbcUrl:{}jdbcPwd:{}jdbcUserName:{}jdbcDriverName:{}" , redisHost , redisDb, jdbcUrl,jdbcPwd,jdbcUserName,jdbcDriverName);
	}
	/**
	 * 根据region保存数据
	 *
	 * @param region
	 * @param key
	 * @param object
	 * @param timeout
	 *            过期时间，单位：秒
	 */
	public void setObjByRegion(String region, String key, Object object, int timeout) {

		log();
		redisTemplate.opsForValue().set(buildRegionKey(region, key), object, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 保存字符串
	 *
	 * @param region
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void setStringByRegion(String region, String key, String value, int timeout) {
		log();
		stringRedisTemplate.opsForValue().set(buildRegionKey(region, key), value, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 保存字符串
	 *
	 * @param region
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void setStringValue(String key, String value, int timeout) {
		log();
		stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 保存list数据
	 *
	 * @param region
	 * @param key
	 * @param value
	 */
	public void leftPush(String region, String key, String value) {
		log();
		stringRedisTemplate.opsForList().leftPush(buildRegionKey(region, key), value);
	}

	/**
	 * 获取数据
	 *
	 * @param region
	 * @param key
	 * @return
	 */
	public Object leftPop(String region, String key) {
		log();
		return stringRedisTemplate.opsForList().leftPop(buildRegionKey(region, key));
	}

	/**
	 * 根据region获取数据
	 *
	 * @param region
	 * @param key
	 * @return
	 */
	public Object getObjByRegion(String region, String key) {
		log();
		return null == key ? null : redisTemplate.opsForValue().get(buildRegionKey(region, key));
	}

	/**
	 * 根据region获取数据
	 *
	 * @param region
	 * @param key
	 * @return
	 */
	public String getStringByRegion(String region, String key) {
		log();
		return null == key ? null : stringRedisTemplate.opsForValue().get(buildRegionKey(region, key));
	}

	/**
	 * 根据key获取数据
	 *
	 * @param region
	 * @param key
	 * @return
	 */
	public String getStringValue(String key) {
		log();
		return null == key ? null : stringRedisTemplate.opsForValue().get(key);
	}

	/**
	 * 删除数据
	 *
	 * @param region
	 * @param key
	 */
	public void delByRegion(String region, String key) {
		log();
		redisTemplate.delete(buildRegionKey(region, key));
	}

	/**
	 * 删除数据
	 *
	 * @param key
	 */
	public void delByKey(String key) {
		log();
		redisTemplate.delete(key);
	}
	
	private String buildRegionKey(String region, String key) {
		log();
		return region + "_" + key;
	}

	public Long getExpireSecond(String region, String key) {
		log();
		return redisTemplate.getExpire(buildRegionKey(region, key), TimeUnit.SECONDS);
	}

	public void setExpireSecond(String region, String key, long timeout) {
		log();
		redisTemplate.expire(buildRegionKey(region, key), timeout, TimeUnit.SECONDS);
	}

}

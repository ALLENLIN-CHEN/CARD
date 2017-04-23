package org.scut.mychart.redis.impl;

import org.apache.log4j.Logger;
import org.scut.mychart.redis.RedisBase;
import org.scut.mychart.redis.RedisBaseDao;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;


@Service
public class RedisBaseDaoImpl extends RedisBase implements RedisBaseDao {
	
	private static final Logger log = Logger.getLogger(RedisBaseDaoImpl.class);

	@Override
	public String getRedisData(String type) {
		Jedis jedis = null;
		String data = "";
		try {
			jedis = getJedis();
			data = jedis.get(type);
		} catch (Exception e) {
			log.error("RedisBaseDaoImpl get data error: " + e.getMessage());
			returnBrokenResource(jedis);
		} finally {
			returnResource(jedis);
		}
		
		return data;
	}

	@Override
	public void setRedisData(String type, String data) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(type, data);
		} catch (Exception e) {
			log.error("RedisBaseDaoImpl set data error: " + e.getMessage());
			returnBrokenResource(jedis);
		} finally {
			returnResource(jedis);
		}
		
	}

}

package org.scut.mychart.redis.impl;

import org.apache.log4j.Logger;
import org.scut.mychart.redis.RedisBase;
import org.scut.mychart.redis.Hospital_2RedisDao;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;


@Service
public class HospitalDaoImpl extends RedisBase implements Hospital_2RedisDao {
	
	private static final Logger log = Logger.getLogger(HospitalDaoImpl.class);

	@Override
	public String getWordcloudData(String type) {
		Jedis jedis = null;
		String data = "";
		try {
			jedis = getJedis();
			data = jedis.get(type);
		} catch (Exception e) {
			log.error("WordcloudRedisDaoImpl get data error: " + e.getMessage());
			returnBrokenResource(jedis);
		} finally {
			returnResource(jedis);
		}
		
		return data;
	}

	@Override
	public void setWordcloudData(String type, String data) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(type, data);
		} catch (Exception e) {
			log.error("WordcloudRedisDaoImpl set data error: " + e.getMessage());
			returnBrokenResource(jedis);
		} finally {
			returnResource(jedis);
		}
		
	}

}

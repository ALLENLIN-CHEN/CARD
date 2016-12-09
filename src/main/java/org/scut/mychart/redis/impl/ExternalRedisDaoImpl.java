package org.scut.mychart.redis.impl;

import org.apache.log4j.Logger;
import org.scut.mychart.redis.ExternalRedisDao;
import org.scut.mychart.redis.RedisBase;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * Created by Allen on 2016/11/30.
 */
@Service
public class ExternalRedisDaoImpl extends RedisBase implements ExternalRedisDao {
    private static final Logger log = Logger.getLogger(ExternalRedisDao.class);

    @Override
    public String getExternalData(String type) {
        Jedis jedis = null;
        String data = "";
        try {
            jedis = getJedis();
            data = jedis.get(type);
        } catch (Exception e) {
            log.error("ExternalRedisDaoImpl get data error: " + e.getMessage());
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return data;
    }

    @Override
    public void setExternalData(String type, String data) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(type, data);
        } catch (Exception e) {
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
    }
}

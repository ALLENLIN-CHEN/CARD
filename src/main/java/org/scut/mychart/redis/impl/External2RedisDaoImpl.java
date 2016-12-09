package org.scut.mychart.redis.impl;

import org.apache.log4j.Logger;
import org.scut.mychart.redis.External2RedisDao;
import org.scut.mychart.redis.ExternalRedisDao;
import org.scut.mychart.redis.RedisBase;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * Created by Allen on 2016/12/2.
 */
@Service
public class External2RedisDaoImpl extends RedisBase implements External2RedisDao {
    private static final Logger log = Logger.getLogger(External2RedisDao.class);

    @Override
    public String getExternal2Data(String type) {
        Jedis jedis = null;
        String data = "";
        try {
            jedis = getJedis();
            data = jedis.get(type);
        } catch (Exception e) {
            log.error("External2RedisDaoImpl get data error: " + e.getMessage());
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }
        return data;
    }

    @Override
    public void setExternal2Data(String type, String data) {
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

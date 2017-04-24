package org.scut.mychart.redis;

public interface RedisBaseDao {
	public String getRedisData(String key);
	
	public void setRedisData(String key, String value);


}

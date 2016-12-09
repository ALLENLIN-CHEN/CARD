package org.scut.mychart.redis;

public interface Hospital_2RedisDao {
	public String getWordcloudData(String type);
	
	public void setWordcloudData(String type, String data);
}

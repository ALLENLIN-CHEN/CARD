package org.scut.mychart.redis;

/**
 * Created by Allen on 2016/12/3.
 */
public interface External3RedisDao {
    public void setExternal3Data(String type,String data);
    public String getExternal3Data(String type);
}

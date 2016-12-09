package org.scut.mychart.redis;

/**
 * Created by Allen on 2016/12/2.
 */
public interface External2RedisDao {
    public void setExternal2Data(String type,String data);
    public String getExternal2Data(String type);
}

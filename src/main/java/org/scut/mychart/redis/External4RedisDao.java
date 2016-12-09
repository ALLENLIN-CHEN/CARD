package org.scut.mychart.redis;

/**
 * Created by Allen on 2016/12/6.
 */
public interface External4RedisDao {
    public void setExternal4Data(String type,String data);
    public String getExternal4Data(String type);
}

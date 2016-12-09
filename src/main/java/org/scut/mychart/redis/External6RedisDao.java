package org.scut.mychart.redis;

/**
 * Created by Allen on 2016/12/6.
 */
public interface External6RedisDao {
    public String getExternal6Data(String type);

    public void setExternal6Data(String type, String data);
}

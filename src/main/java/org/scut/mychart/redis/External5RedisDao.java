package org.scut.mychart.redis;

/**
 * Created by Allen on 2016/12/6.
 */
public interface External5RedisDao {
    public String getExternal5Data(String type);

    public void setExternal5Data(String type, String data);
}

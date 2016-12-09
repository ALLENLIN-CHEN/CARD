package org.scut.mychart.redis;

/**
 * Created by Allen on 2016/12/6.
 */
public interface External7RedisDao {
    public String getExternal7Data(String type);

    public void setExternal7Data(String type, String data);
}

package org.scut.mychart.redis;

/**
 * Created by Allen on 2016/11/30.
 */
public interface ExternalRedisDao {
    public String getExternalData(String type);

    public void setExternalData(String type, String data);
}

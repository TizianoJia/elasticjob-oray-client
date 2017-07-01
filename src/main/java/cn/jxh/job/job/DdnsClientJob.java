package cn.jxh.job.job;

import cn.jxh.job.utils.Base64Utils;
import cn.jxh.job.utils.HttpUtils;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the job class for using OrayClient.
 *
 * @author JiaXiaohei[i@jiaxiaohei.com]
 */
public class DdnsClientJob implements SimpleJob {

    private static Log log = LogFactory.getLog(DdnsClientJob.class);

    @Value(value = "#{propertiesReader['oray.user']}")
    private String OrayUser;

    @Value(value = "#{propertiesReader['oray.pwd']}")
    private String OrayPwd;

    public void execute(ShardingContext context) {
        String host = "http://ddns.oray.com";
        String path = "/ph/update";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + Base64Utils.String2Base(OrayUser + ":" + OrayPwd));
        headers.put("User-Agent", "Oray");
        Map<String, String> querys = new HashMap<String, String>();

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                log.info(EntityUtils.toString(entity));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



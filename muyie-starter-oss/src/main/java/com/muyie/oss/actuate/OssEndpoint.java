package com.muyie.oss.actuate;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author larry
 * @since 2.7.13
 */
@Endpoint(id = "oss")
public class OssEndpoint {
  @Autowired
  private ApplicationContext applicationContext;

  @ReadOperation
  public Map<String, Object> invoke() {
    Map<String, Object> result = new HashMap<>();
    Map<String, OSSClient> ossClientMap = this.applicationContext.getBeansOfType(OSSClient.class);
    int size = ossClientMap.size();
    List<Object> ossClientList = new ArrayList<>();
    ossClientMap.keySet().forEach((beanName) -> {
      Map<String, Object> ossProperties = new HashMap<>();
      OSSClient client = ossClientMap.get(beanName);
      ossProperties.put("beanName", beanName);
      ossProperties.put("endpoint", client.getEndpoint().toString());
      ossProperties.put("clientConfiguration", client.getClientConfiguration());
      ossProperties.put("credentials", client.getCredentialsProvider().getCredentials());
      ossProperties.put("bucketList", client.listBuckets().stream().map(Bucket::getName).toArray());
      ossClientList.add(ossProperties);
    });
    result.put("size", size);
    result.put("info", ossClientList);
    return result;
  }
}

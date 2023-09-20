package com.muyie.oss.context;

import com.aliyun.oss.OSS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Map;

/**
 * @author larry
 * @since 2.7.13
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "muyie.oss.enabled", havingValue = "true", matchIfMissing = true)
public class OssApplicationListener implements ApplicationListener<ContextClosedEvent> {

  @Override
  public void onApplicationEvent(ContextClosedEvent event) {
    Map<String, OSS> ossClientMap = event.getApplicationContext().getBeansOfType(OSS.class);
    log.info("{} OSSClients will be shutdown soon", ossClientMap.size());
    ossClientMap.keySet().forEach((beanName) -> {
      log.info("shutdown ossClient: {}", beanName);
      ossClientMap.get(beanName).shutdown();
    });
  }
}

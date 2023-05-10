package cn.konker.listen;

import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author exuebo@gmail.com
 * @date 2023-02-23 18:00
 */
@Component
public class EurekaInstanceRegisteredListener implements ApplicationListener<EurekaInstanceRegisteredEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaInstanceRegisteredListener.class);

    private static final String GATEWAY = "GATEWAY";

    @Override
    public void onApplicationEvent(EurekaInstanceRegisteredEvent eurekaInstanceRegisteredEvent) {
        if (eurekaInstanceRegisteredEvent.isReplication()) {
            try {
                InstanceInfo instanceInfo = eurekaInstanceRegisteredEvent.getInstanceInfo();
                if (!instanceInfo.getAppName().equals(GATEWAY)) {
                    //todo 通过消息将服务注册信息同步到gateway中的swagger
//                    swaggerFeignClient.registerSwagger(instanceInfo.getAppName().toLowerCase());
                    LOGGER.info("enums instance up {} and register swagger", instanceInfo.getAppName());
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

}
package top.misec.applemonitor.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * @author Moshi
 */
@Data
@Slf4j
public class AppleTaskConfig {
    public List<DeviceItem> deviceCodeList;
    public String location;
    public String cronExpressions;
    public String country;

    public boolean valid() {
        if (CollectionUtil.isEmpty(deviceCodeList)) {
            log.info("需要监控的设备型号号码不能为空，类似于 MQ0D3CH/A ");
            return false;
        }
        if (StrUtil.isBlank(location)) {
            log.info("需要监控的地区不能为空，类似于 广东 深圳 南山区 ，请使用苹果官网的地区格式");
            return false;
        }

        if (StrUtil.isBlank(cronExpressions)) {
            log.info("监控的时间表达式不能为空，类似于 0 0 0/1 * * ? ");
            return false;
        }

        if (StrUtil.isBlank(country)) {
            log.info("国家代码不能为空，类似于 CN , JP");
            return false;
        }

        deviceCodeList.forEach(k -> {
            if (k.getStoreWhiteList() == null) {
                k.setStoreWhiteList(Collections.emptyList());
                log.info("{},需要监控的门店为空，默认监控您附近的所有门店", k.getDeviceCode());
            }
        });


        log.info("配置校验通过，开始监控{}附近的Apple直营店", location);

        return true;

    }
}

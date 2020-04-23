package com.majinhu.user.utils;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Random;

/**
 * @ClassName RandomLoadBalance
 * @Description
 * @Author majinhu
 * @Date 2020/2/20 16:44
 * @Version 1.0
 **/
public class RandomLoadBalance extends LoadBalance {
    @Override
    public String choseServerHost() {
        String result = "";
        if(!CollectionUtils.isEmpty(SERVICE_LIST)){
            Random random = new Random();
            int pos = random.nextInt(SERVICE_LIST.size());
            result = SERVICE_LIST.get(pos);

        }
        return result;
    }
}

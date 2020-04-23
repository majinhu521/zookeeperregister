package com.majinhu.user.utils;

import java.util.List;

/**
 * @ClassName LoadBalance
 * @Description
 * @Author majinhu
 * @Date 2020/2/20 16:41
 * @Version 1.0
 **/
public abstract class LoadBalance {
    public volatile static List<String> SERVICE_LIST;
    public abstract  String choseServerHost();
}

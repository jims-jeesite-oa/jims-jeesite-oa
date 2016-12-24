package com.thinkgem.jeesite.modules.form.util;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 组件初始化值
 * @author lgx
 * @version 2016-12-24
 */
public class InitUtils {

    /**
     * 获取当前用户姓名
     * @return
     */
    public static String getUsername(){
        return UserUtils.getUser().getName();
    }
}

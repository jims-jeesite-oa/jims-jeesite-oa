package com.thinkgem.jeesite.modules.oa.units;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.oa.dao.OaAuditManDao;
import com.thinkgem.jeesite.modules.oa.dao.OaNotifyDao;
import com.thinkgem.jeesite.modules.oa.dao.OaScheduleDao;
import com.thinkgem.jeesite.modules.oa.dao.OaSummaryPermissionDao;
import com.thinkgem.jeesite.modules.oa.entity.OaAuditMan;
import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
import com.thinkgem.jeesite.modules.oa.entity.OaSchedule;
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryPermission;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import sun.tools.tree.NewArrayExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * 信息工具类
 * @author lgx
 * @version 2016-12-05
 */
public class InfoUtils {

	private static OaNotifyDao oaNotifyDao = SpringContextHolder.getBean(OaNotifyDao.class);
	private static OaScheduleDao oaScheduleDao = SpringContextHolder.getBean(OaScheduleDao.class);

	public static List<OaNotify> getMyNotify(){
        OaNotify notify = new OaNotify();
        notify.setSelf(true);
        List<OaNotify> notifys = oaNotifyDao.findList(notify);
		if (notifys==null){
            notifys = Lists.newArrayList();
		}
		return notifys;
	}

    public static List<OaSchedule> getSchedules(String flag){
        OaSchedule oaSchedule = new OaSchedule();
        oaSchedule.setFlag(flag);
        oaSchedule.setLoginId(UserUtils.getUser().getId());
        List<OaSchedule> schedules = oaScheduleDao.findList(oaSchedule);
        if (schedules==null){
            schedules = Lists.newArrayList();
        }
        return schedules;
    }

}

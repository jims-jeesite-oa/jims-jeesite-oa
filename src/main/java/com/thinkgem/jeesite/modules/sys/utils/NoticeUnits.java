package com.thinkgem.jeesite.modules.sys.utils;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.oa.dao.OaNewsDao;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;
import com.thinkgem.jeesite.modules.sys.entity.User;

import java.util.List;

/**
 * Created by lgx on 2016/11/18.
 */
public class NoticeUnits {

    private static OaNewsDao newsDao = SpringContextHolder.getBean(OaNewsDao.class);

    private static ActTaskService actTaskService = SpringContextHolder.getBean(ActTaskService.class);

    /**
     * 获取当前用户可审核的新闻公告
     * @return
     */
    public static List<OaNews> getAuditNews(){
        User user = UserUtils.getUser();
        if(user != null){
            OaNews news = new OaNews();
            news.setAuditMan(user.getId());
            news.setAuditFlag("0");
            List<OaNews> newsList = newsDao.findList(news);
            if(newsList == null){
                newsList = Lists.newArrayList();
            }
            return newsList;
        }
        return Lists.newArrayList();
    }

    /**
     * 获取待办列表
     * @return
     */
    public static List<Act> getTodo(){
        List<Act> acts = actTaskService.todoList(new Act());
        if(acts == null){
            acts = Lists.newArrayList();
        }
        return acts;
    }

    /**
     * 获取当前用户可审核的新闻公告
     * @return
     */
    public static List<OaNews> getAllNews(){
        OaNews news = new OaNews();
        news.setAuditFlag("1");
        List<OaNews> newsList = newsDao.findList(news);
        if(newsList == null){
            newsList = Lists.newArrayList();
        }
        return newsList;
    }
}

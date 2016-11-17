/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.oa.entity.OaSchedule;
import com.thinkgem.jeesite.modules.oa.service.OaScheduleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryDay;
import com.thinkgem.jeesite.modules.oa.service.OaSummaryDayService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * 工作日志Controller
 *
 * @author yangruidong
 * @version 2016-11-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaSummaryDay")
public class OaSummaryDayController extends BaseController {

    @Autowired
    private OaSummaryDayService oaSummaryDayService;

    @Autowired
    private OaScheduleService oaScheduleService;

    @ModelAttribute
    public OaSummaryDay get(@RequestParam(required = false) String id) {
        OaSummaryDay entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = oaSummaryDayService.get(id);
        }
        if (entity == null) {
            entity = new OaSummaryDay();
        }
        return entity;
    }

    //@RequiresPermissions("oa:oaSummaryDay:view")
    @RequestMapping(value = {"list", ""})
    public String list(@ModelAttribute("oaSummaryDay") OaSummaryDay oaSummaryDay, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        //当前日期
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(new Date());
        Date date = sdf.parse(s);

        //如果有日期，点击查询时的操作
        if (oaSummaryDay.getSumDate() != null) {
            Date before=oaSummaryDay.getSumDate();
            oaSummaryDay = oaSummaryDayService.findByDate(oaSummaryDay);
            OaSchedule oaSchedule = new OaSchedule();
            oaSchedule.setScheduleDate(before);
            List<OaSchedule> list = oaScheduleService.completeBy(oaSchedule);
            if(oaSummaryDay!=null){
                oaSummaryDay.setOaScheduleList(list);
                model.addAttribute("oaSummaryDay",oaSummaryDay);
            } else{
                model.addAttribute("oaSummaryDay",new OaSummaryDay(list,before));
            }
        } else {
            oaSummaryDay.setSumDate(date);
            oaSummaryDay = oaSummaryDayService.findByDate(oaSummaryDay);
            if (oaSummaryDay != null) {
                OaSchedule oaSchedule = new OaSchedule();
                oaSchedule.setScheduleDate(oaSummaryDay.getSumDate());
                List<OaSchedule> list = oaScheduleService.completeBy(oaSchedule);
                oaSummaryDay.setOaScheduleList(list);
                model.addAttribute("oaSummaryDay", oaSummaryDay);
            } else{
                OaSchedule oaSchedule = new OaSchedule();
                oaSchedule.setScheduleDate(date);
                List<OaSchedule> list = oaScheduleService.completeBy(oaSchedule);
                model.addAttribute("oaSummaryDay", new OaSummaryDay(list,date));
            }
        }
        return "modules/oa/oaSummaryDayList";
    }


    @RequestMapping(value = "complete")
    public String complete(OaSchedule oaSchedule, Model model) {
        List<OaSchedule> list = oaScheduleService.completeBy(oaSchedule);
        model.addAttribute("result", list);
        return "modules/oa/oaSummaryDayList";
    }




    @RequiresPermissions("oa:oaSummaryDay:view")
    @RequestMapping(value = "form")
    public String form(OaSummaryDay oaSummaryDay, Model model) {
        model.addAttribute("oaSummaryDay", oaSummaryDay);
        return "modules/oa/oaSummaryDayForm";
    }

    @RequiresPermissions("oa:oaSummaryDay:edit")
    @RequestMapping(value = "save")
    public String save(OaSummaryDay oaSummaryDay, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, oaSummaryDay)) {
            return form(oaSummaryDay, model);
        }
        oaSummaryDayService.save(oaSummaryDay);
        String id = oaSummaryDay.getId();
        oaSummaryDay = oaSummaryDayService.get(id);
        OaSchedule oaSchedule = new OaSchedule();
        oaSchedule.setScheduleDate(oaSummaryDay.getSumDate());
        List<OaSchedule> list = oaScheduleService.completeBy(oaSchedule);
        oaSummaryDay.setOaScheduleList(list);
        model.addAttribute("oaSummaryDay", oaSummaryDay);
        addMessage(redirectAttributes, "保存工作日志成功");
        return "modules/oa/oaSummaryDayList";

    }

    @RequiresPermissions("oa:oaSummaryDay:edit")
    @RequestMapping(value = "delete")
    public String delete(OaSummaryDay oaSummaryDay, RedirectAttributes redirectAttributes) {
        oaSummaryDayService.delete(oaSummaryDay);
        addMessage(redirectAttributes, "删除工作日志成功");
        return "redirect:" + Global.getAdminPath() + "/oa/oaSummaryDay/?repage";
    }

}
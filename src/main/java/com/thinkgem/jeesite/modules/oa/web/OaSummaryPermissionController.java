/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.cms.service.CategoryService;
import com.thinkgem.jeesite.modules.oa.entity.*;
import com.thinkgem.jeesite.modules.oa.service.*;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 评阅管理Controller
 * @author yangruidong
 * @version 2016-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaSummaryPermission")
public class OaSummaryPermissionController extends BaseController {



	@Autowired
	private OaSummaryPermissionService oaSummaryPermissionService;

    @Autowired
    private OaAppraiseService oaAppraiseService;

    @Autowired
    private OaSummaryDayService oaSummaryDayService;

    @Autowired
    private OaScheduleService oaScheduleService;
    @Autowired
    private OaSummaryWeekService oaSummaryWeekService;

    @Autowired
    private OaWeekAppraiseService oaWeekAppraiseService;

//    @RequiresPermissions("oa:oaSummaryPermission:view")
    @RequestMapping(value = {"list", ""})
    public String index(OaSummaryPermission oaSummaryPermission, Model model) {
        model.addAttribute("oaSummaryPermission", oaSummaryPermission);
        return "modules/oa/oaSummaryPermissionList";
    }





    @RequiresPermissions("oa:oaSummaryPermission:view")
    @RequestMapping(value = "form")
    public String form(OaSummaryPermission oaSummaryPermission, Model model) {
        model.addAttribute("oaSummaryPermission", oaSummaryPermission);
        return "modules/oa/oaSummaryPermissionForm";
    }

    /**
     * 保存评阅权限
     * @param oaSummaryPermission
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "save")
    public String save(OaSummaryPermission oaSummaryPermission,Model model,RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, oaSummaryPermission)){
            return form(oaSummaryPermission, model);
        }
        //获取被评阅人的所有id并根据逗号进行拆分
        String[]  ids=oaSummaryPermission.getEvaluateById().split(",");
        String[]  idss=oaSummaryPermission.getEvaluateId().split(",");
        for(int i=0;i<ids.length;i++){
            for(int j=0;j<idss.length;j++){
                OaSummaryPermission oaSummaryPermission2=new OaSummaryPermission();
                oaSummaryPermission2.setEvaluateId(ids[i]);
                oaSummaryPermission2.setEvaluateById(idss[j]);
                OaSummaryPermission oa=oaSummaryPermissionService.get(oaSummaryPermission2);
                if(oa==null){
                    OaSummaryPermission oaSummaryPermission1=new OaSummaryPermission();
                    oaSummaryPermission1.setEvaluateById(ids[i]);
                    oaSummaryPermission1.setEvaluateId(idss[j]);
                    oaSummaryPermissionService.save(oaSummaryPermission1);
                }
            }
        }


        addMessage(redirectAttributes, "保存评阅成功");
        return "redirect:"+Global.getAdminPath()+"/oa/oaSummaryPermission/?repage";
    }




    /**
     * 同事评阅周总结
     * @param
     * @param model
     * @return
     */
    @RequiresPermissions("oa:oaSummaryPermission:view")
    @RequestMapping(value = "formWeek")
    public String formWeek(OaSummaryWeek oaSummaryWeek, Model model)throws  Exception{
        //获取当前想要格式的日期
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(new Date());
        Date date = sdf.parse(s);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        oaSummaryWeek.setWeekOfYear(calendar.get(Calendar.WEEK_OF_YEAR));
        oaSummaryWeek.setFlagBy("1");
        model.addAttribute("oaSummaryWeek", oaSummaryWeek);
        return "modules/oa/oaPermissionForm";
    }



    Date currentFirstDate;
    List dd = new ArrayList();

    //格式化日期
    public String formatDate(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        String year = cale.get(Calendar.YEAR) + "-";
        String month = cale.get(Calendar.MONTH) + 1 + "-";
        String day = cale.get(Calendar.DATE) + "";
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return year + month + day + ' ' + weekDays[w];
    }

    public Date addDate(Date date, int n) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.DAY_OF_MONTH, n);
        return cale.getTime();
    }

    public List setDate(Date date) {
        Calendar cale1 = Calendar.getInstance();
        cale1.setTime(date);
        int week = cale1.get(Calendar.DAY_OF_WEEK);
        date = addDate(date, 1 - week);
        currentFirstDate = date;
        dd = new ArrayList();
        for (int i = 0; i < 7; i++) {
            dd.add(formatDate(i == 0 ? date : addDate(date, i)));
        }
        return dd;
    }

    //查询本周周总结
    @RequestMapping(value = "formId")
    public String form(OaSummaryWeek oaSummaryWeek, Model model) throws Exception {
        String loginId=oaSummaryWeek.getLogin();
        User user = UserUtils.getUser();
        if(StringUtils.isNotEmpty(loginId)){
            //获取当前想要格式的日期
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(new Date());
            Date date = sdf.parse(s);
            //根据当前的日期来设置第一天从星期日开始
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.SUNDAY);
            calendar.setTime(date);
            List date1 = setDate(new Date());
            if(oaSummaryWeek!=null) {
                if (oaSummaryWeek.getWeekOfYear() != null) {
                    oaSummaryWeek.setWeekOfYear(oaSummaryWeek.getWeekOfYear());
                } else {
                    oaSummaryWeek.setWeekOfYear(calendar.get(Calendar.WEEK_OF_YEAR));
                }
            }
            oaSummaryWeek.setLoginId(loginId);
            oaSummaryWeek = oaSummaryWeekService.findByWeek(oaSummaryWeek);
            List<OaSchedule> list = null;
            List<OaVo> oa = new ArrayList<OaVo>();

            //根据日期拿到每个日期的任务完成和工作总结
            for (int j = 0; j < date1.size(); j++) {
                OaSchedule oaSchedule = new OaSchedule();
                String sum = date1.get(j).toString().substring(0, 10);
                String begin = sdf.format(DateFormat.getDateInstance().parse(sum));
                Date scheduleDate = sdf.parse(begin);
                OaVo oaVo = null;
                oaSchedule.setScheduleDate(scheduleDate);
                oaSchedule.setLoginId(loginId);
                List<OaSchedule> list1 = oaScheduleService.completeBy(oaSchedule);
                String con = null;
                String app=null;
                StringBuffer cons = new StringBuffer();
                for (int i = 0; i < list1.size(); i++) {
                    con = cons.append(list1.get(i).getContent()).append("<br>").toString();
                }

                StringBuilder sb=new StringBuilder();
                OaAppraise oaAppraise=new OaAppraise();
                oaAppraise.setEvaluateId(loginId);
                oaAppraise.setAppraiseDate(scheduleDate);
                List<OaAppraise> appraises=oaAppraiseService.findByEvaluate(oaAppraise);
                if(appraises!=null){
                    for(int i=0;i<appraises.size();i++){
                        app= sb.append(appraises.get(i).getContent()+"("+appraises.get(i).getName()+");<br>").toString();
                    }
                }
                OaSummaryDay oaSummaryDay = new OaSummaryDay();
                oaSummaryDay.setSumDate(scheduleDate);
                oaSummaryDay.setLoginId(loginId);
                oaSummaryDay = oaSummaryDayService.findByDate(oaSummaryDay);
                oaVo = new OaVo();
                oaVo.setContent(con);
                oaVo.setAppraise(app);
                oaVo.setDate(date1.get(j).toString());
                if (oaSummaryDay != null) {
                    oaVo.setStatus(oaSummaryDay.getContent());
                }
                oa.add(oaVo);
            }
            if (oa != null && oaSummaryWeek!=null) {
                oaSummaryWeek.setOaVos(oa);
            } else{
                oaSummaryWeek=new OaSummaryWeek();
                oaSummaryWeek.setOaVos(oa);
            }
            // oaSummaryWeek.setOaVos(oa);
            String year= sdf.format(currentFirstDate);
            //拿到当前的年
            String currentYear = year.substring(0, 10);
            String year1 = year.substring(0, 4);
            oaSummaryWeek.setLogin(loginId);
            StringBuilder sb=new StringBuilder();
            OaWeekAppraise op=new OaWeekAppraise();
            op.setYear(year1);
            op.setEvaluateId(loginId);
            op.setWeek(calendar.get(Calendar.WEEK_OF_YEAR)+"");
            List<OaWeekAppraise> appraises=oaWeekAppraiseService.findByEvaluate(op);
            if(appraises!=null){
                for(int i=0;i<appraises.size();i++){
                    if(StringUtils.equals(appraises.get(i).getFlag(),"1")){
                        sb.append(appraises.get(i).getContent()+"("+appraises.get(i).getName()+");");
                    }
                }
            }
            if(sb!=null && sb.toString()!=""){
                oaSummaryWeek.setEvaluate(sb.toString());
            }

            oaSummaryWeek.setWeekOfYear(calendar.get(Calendar.WEEK_OF_YEAR));
            oaSummaryWeek.setFlagBy("1");
        }
        model.addAttribute("oaSummaryWeek", oaSummaryWeek);
        return "modules/oa/oaPermissionForm";
    }


    /**
     * 保存周总结的评阅
     *
     * @param oaSummaryWeek
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "saveWeek")
    public String saveWeek(OaSummaryWeek oaSummaryWeek, Model model, RedirectAttributes redirectAttributes) throws Exception {
        String loginId=oaSummaryWeek.getLoginId();
        if(StringUtils.isNotEmpty(loginId)){
            User user = UserUtils.getUser();
            //获取当前想要格式的日期
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String year= sdf.format(currentFirstDate);
            //拿到当前的年
            String currentYear = year.substring(0, 10);
            String year1 = year.substring(0, 4);
            Date date = sdf.parse(currentYear);
            List date1 = setDate(date);
            oaSummaryWeek.setYear(year1);
            oaSummaryWeek.setWeek(oaSummaryWeek.getWeekOfYear() + "");
            oaSummaryWeek.setSumDate(new Date());
            List<OaSchedule> list = null;
            List<OaVo> oa = new ArrayList<OaVo>();
            //根据日期拿到每个日期的任务完成和工作总结
            for (int j = 0; j < date1.size(); j++) {
                OaSchedule oaSchedule = new OaSchedule();
                String sum = date1.get(j).toString().substring(0, 10);
                String begin = sdf.format(DateFormat.getDateInstance().parse(sum));
                Date scheduleDate = sdf.parse(begin);
                OaVo oaVo = null;
                String app=null;
                oaSchedule.setScheduleDate(scheduleDate);
                oaSchedule.setLoginId(loginId);
                List<OaSchedule> list1 = oaScheduleService.completeBy(oaSchedule);
                String con = null;
                StringBuffer cons = new StringBuffer();
                for (int i = 0; i < list1.size(); i++) {
                    con = cons.append(list1.get(i).getContent()).append("<br>").toString();
                }

                StringBuilder sb=new StringBuilder();
                OaAppraise oaAppraise=new OaAppraise();
                oaAppraise.setEvaluateId(loginId);
                oaAppraise.setAppraiseDate(scheduleDate);
                List<OaAppraise> appraises=oaAppraiseService.findByEvaluate(oaAppraise);
                if(appraises!=null){
                    for(int i=0;i<appraises.size();i++){
                        app= sb.append(appraises.get(i).getContent()+"("+appraises.get(i).getName()+");<br>").toString();
                    }
                }
                OaSummaryDay oaSummaryDay = new OaSummaryDay();
                oaSummaryDay.setSumDate(scheduleDate);
                oaSummaryDay.setLoginId(loginId);
                oaSummaryDay = oaSummaryDayService.findByDate(oaSummaryDay);
                oaVo = new OaVo();
                oaVo.setContent(con);
                oaVo.setAppraise(app);
                oaVo.setDate(date1.get(j).toString());
                if (oaSummaryDay != null) {
                    oaVo.setStatus(oaSummaryDay.getContent());
                }
                oa.add(oaVo);
            }
            oaSummaryWeek.setOaVos(oa);
//        oaSummaryWeek.setLoginId(loginId);
            oaSummaryWeek.setEvaluateMan(user.getName());
            oaSummaryWeek.setEvaluateManId(user.getId());

            OaWeekAppraise oaWeekAppraise=new OaWeekAppraise();

            oaWeekAppraise.setLoginId(user.getId());
            oaWeekAppraise.setFlag(oaSummaryWeek.getFlagBy());
            oaWeekAppraise.setEvaluateId(loginId);
            oaWeekAppraise.setContent(oaSummaryWeek.getEvaluateContent());
            oaWeekAppraise.setAppraiseWeekDate(oaSummaryWeek.getSumDate());
            oaWeekAppraise.setYear(year1);
            oaWeekAppraise.setWeek(oaSummaryWeek.getWeekOfYear() + "");

            oaWeekAppraiseService.save(oaWeekAppraise);
            StringBuilder sb=new StringBuilder();
            OaWeekAppraise op=new OaWeekAppraise();
            op.setYear(year1);
            op.setEvaluateId(loginId);
            op.setWeek(oaSummaryWeek.getWeekOfYear()+"");
            List<OaWeekAppraise> appraises=oaWeekAppraiseService.findByEvaluate(op);
            if(appraises!=null){
                for(int i=0;i<appraises.size();i++){
                    if(StringUtils.equals(appraises.get(i).getFlag(),"1")){
                        sb.append(appraises.get(i).getContent()+"("+appraises.get(i).getName()+");");
                    }
                }
            }
            if(sb!=null && sb.toString()!=""){
                oaSummaryWeek.setEvaluate(sb.toString());
            }
        }
        oaSummaryWeek.setEvaluateContent("");
        oaSummaryWeek.setLogin(loginId);
        oaSummaryWeek.setFlagBy("1");
        addMessage(redirectAttributes, "保存周总结评阅成功");
        model.addAttribute("oaSummaryWeek", oaSummaryWeek);
        return "modules/oa/oaPermissionForm";
    }


    String year;

    /**
     * 上一周   本周   下一周
     *
     * @param flag
     * @param weekOfYear
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "lackWeek")
    public String lackWeek(@RequestParam("flag") String flag, @RequestParam("weekOfYear") Integer weekOfYear,@RequestParam("login")String login, Model model) throws Exception {
        OaSummaryWeek oaSummaryWeek = new OaSummaryWeek();
       if(StringUtils.isNotEmpty(login)){
           //获取当前想要格式的日期
           DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           String s = sdf.format(new Date());
           Date date = sdf.parse(s);
           //根据当前的日期来设置第一天从星期日开始
           Calendar calendar = Calendar.getInstance();
           calendar.setFirstDayOfWeek(Calendar.SUNDAY);
           calendar.setTime(date);
           List date1 = new ArrayList();
           //本周
           if (StringUtils.equals(flag, "1")) {
               if(weekOfYear==1){
                   oaSummaryWeek.setWeekOfYear(53);
                   date1 = setDate(addDate(currentFirstDate, -7));
                   year = date1.get(0).toString();
               }else{
                   oaSummaryWeek.setWeekOfYear(weekOfYear - 1);
                   date1 = setDate(addDate(currentFirstDate, -7));
                   year = date1.get(0).toString();
               }
           } else if (StringUtils.equals(flag, "2")) {
               if (weekOfYear == 53) {
                   oaSummaryWeek.setWeekOfYear(1);
               } else {
                   oaSummaryWeek.setWeekOfYear(weekOfYear + 1);
               }

               date1 = setDate(addDate(currentFirstDate, 7));
               year = date1.get(0).toString();
           } else {
               oaSummaryWeek.setWeekOfYear(calendar.get(Calendar.WEEK_OF_YEAR));
               date1 = setDate(new Date());
           }
           oaSummaryWeek.setLoginId(login);
           OaSummaryWeek oaSummaryWeek1 = oaSummaryWeekService.findByWeek(oaSummaryWeek);
           if (oaSummaryWeek1 != null) {
               oaSummaryWeek.setContent(oaSummaryWeek1.getContent());
               oaSummaryWeek.setNextPlanContent(oaSummaryWeek1.getNextPlanContent());
               oaSummaryWeek.setNextPlanTitle(oaSummaryWeek1.getNextPlanTitle());
               oaSummaryWeek.setId(oaSummaryWeek1.getId());
           }
           List<OaVo> oa = new ArrayList<OaVo>();
           //根据日期拿到每个日期的任务完成和工作总结
           for (int j = 0; j < date1.size(); j++) {
               OaSchedule oaSchedule = new OaSchedule();
               String sum = date1.get(j).toString().substring(0, 10);
               String begin = sdf.format(DateFormat.getDateInstance().parse(sum));
               Date scheduleDate = sdf.parse(begin);
               OaVo oaVo = null;
               oaSchedule.setScheduleDate(scheduleDate);
               oaSchedule.setLoginId(login);
               List<OaSchedule> list1 = oaScheduleService.completeBy(oaSchedule);
               String con = null;
               String app=null;
               StringBuffer cons = new StringBuffer();
               if (list1.size() > 0) {
                   for (int i = 0; i < list1.size(); i++) {
                       con = cons.append(list1.get(i).getContent()).append("<br>").toString();
                   }
               }


               StringBuilder sb=new StringBuilder();
               OaAppraise oaAppraise=new OaAppraise();
               oaAppraise.setEvaluateId(login);
               oaAppraise.setAppraiseDate(scheduleDate);
               List<OaAppraise> appraises=oaAppraiseService.findByEvaluate(oaAppraise);
               if(appraises!=null){
                   for(int i=0;i<appraises.size();i++){
                       app= sb.append(appraises.get(i).getContent()+"("+appraises.get(i).getName()+");<br>").toString();
                   }
               }
               OaSummaryDay oaSummaryDay = new OaSummaryDay();
               oaSummaryDay.setSumDate(scheduleDate);
               oaSummaryDay.setLoginId(login);
               oaSummaryDay = oaSummaryDayService.findByDate(oaSummaryDay);

               oaVo = new OaVo();
               oaVo.setContent(con);
               oaVo.setAppraise(app);
               oaVo.setDate(date1.get(j).toString());
               if (oaSummaryDay != null) {
                   oaVo.setStatus(oaSummaryDay.getContent());
               }
               oa.add(oaVo);
           }
           oaSummaryWeek.setOaVos(oa);
           String currentYear = year.substring(0, 10);
           String year1 = year.substring(0, 4);
           StringBuilder sb=new StringBuilder();
           OaWeekAppraise op=new OaWeekAppraise();
           op.setYear(year1);
           op.setEvaluateId(login);
           op.setWeek(oaSummaryWeek.getWeekOfYear()+"");
           List<OaWeekAppraise> appraises=oaWeekAppraiseService.findByEvaluate(op);
           if(appraises!=null){
               for(int i=0;i<appraises.size();i++){
                   if(StringUtils.equals(appraises.get(i).getFlag(),"1")){
                       sb.append(appraises.get(i).getContent()+"("+appraises.get(i).getName()+");");
                   }
               }
           }
           if(sb!=null && sb.toString()!=""){
               oaSummaryWeek.setEvaluate(sb.toString());
           }
       }
        oaSummaryWeek.setFlagBy("1");
        oaSummaryWeek.setLogin(login);

        model.addAttribute("oaSummaryWeek", oaSummaryWeek);
        return "modules/oa/oaPermissionForm";
    }


    /**
     *查询评阅人
     * @param oaSummaryPermission
     * @param model
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "findById")
    public String findById(OaSummaryPermission oaSummaryPermission, Model model, RedirectAttributes redirectAttributes,String id) throws Exception {
        oaSummaryPermission= oaSummaryPermissionService.findById(id);
        oaSummaryPermission.setEvaluateById(id);
        model.addAttribute("oaSummaryPermission",oaSummaryPermission);
        return "modules/oa/oaSummaryPermissionList";
    }

}
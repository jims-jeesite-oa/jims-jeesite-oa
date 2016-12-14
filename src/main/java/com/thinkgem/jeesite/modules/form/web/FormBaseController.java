package com.thinkgem.jeesite.modules.form.web;


import com.thinkgem.jeesite.modules.form.service.OaFormMasterService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * created by chenxy
 * 外置表单入口
 */
@Controller
@RequestMapping(value="/outForm")
public class FormBaseController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private OaFormMasterService oaFormMasterService;

    @RequestMapping(value="/tableName")
    public String redirectForm(){
        String tableName=request.getParameter("tableName");
        if(tableName==null&&"".equals(tableName)){
            throw new IllegalArgumentException("tableName 为空！");
        }
        String officeId=UserUtils.getUser()!=null?UserUtils.getUser().getOffice().getId():null;
        this.oaFormMasterService.findFormContentByTableName(tableName,officeId);
        return null;
    }




}

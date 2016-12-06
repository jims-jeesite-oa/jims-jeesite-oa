/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.form.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableColumnDao;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import com.thinkgem.jeesite.modules.table.service.OaControlTypeService;
import com.thinkgem.jeesite.modules.table.service.OaPersonDefineTableService;
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
import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;
import com.thinkgem.jeesite.modules.form.service.OaFormMasterService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 编辑器设计表单Controller
 * @author chenxy
 * @version 2016-11-18
 */
@Controller
@RequestMapping(value = "${adminPath}/form/oaFormMaster")
public class OaFormMasterController extends BaseController {

	@Autowired
	private OaFormMasterService oaFormMasterService;

    @Autowired
    private OaPersonDefineTableService oaPersonDefineTableService;

    @Autowired
    private OaControlTypeService oaControlTypeService;

	
	@ModelAttribute
	public OaFormMaster get(@RequestParam(required=false) String id) {
		OaFormMaster entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaFormMasterService.get(id);
		}
		if (entity == null){
			entity = new OaFormMaster();
		}
		return entity;
	}
	
	@RequiresPermissions("form:oaFormMaster:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaFormMaster oaFormMaster, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaFormMaster> page = oaFormMasterService.findPage(new Page<OaFormMaster>(request, response), oaFormMaster); 
		model.addAttribute("page", page);
		return "modules/form/oaFormMasterList";
	}

	@RequiresPermissions("form:oaFormMaster:view")
	@RequestMapping(value = "form")
	public String form(OaFormMaster oaFormMaster, Model model) {
		model.addAttribute("oaFormMaster", oaFormMaster);
		return "modules/form/oaFormMasterForm";
	}

	@RequiresPermissions("form:oaFormMaster:edit")
	@RequestMapping(value = "save")
	public String save(OaFormMaster oaFormMaster, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaFormMaster)){
			return form(oaFormMaster, model);
		}
        //上传到项目中一份--form 文件夹上面
        /**
         *  1 从oa_person_define_table 表中  根据table_name 和 officeId 查询 对象
         *  2 根据对象的Id,从oa_person_define_table_column 表中 查询此表列的信息
         *  3 拿到list<OaPersonDefineTableColumn> ,遍历每一个列信息的columnComment和controlTypeId
         *  4 根据controlTypeId ,在表oa_control_type 表中查询出controlContent内容
         *  5 根据注释将 编辑器保存的代码利用controlContent 代替
         *  6生成一个html文件
         *  7 将html文件上传到服务器
         */
        OaPersonDefineTable oaPersonDefineTable=this.oaPersonDefineTableService.findByTableName(oaFormMaster.getTableName(),oaFormMaster.getOffice().getId());
        List<OaPersonDefineTableColumn> oaPersonDefineTableColumns=this.oaPersonDefineTableService.findColumnListByTableId(oaPersonDefineTable.getId());
        Map<String,List<String>> OneToOne=new HashMap<String,List<String>>();
        for(OaPersonDefineTableColumn column:oaPersonDefineTableColumns){
            if(column!=null&&!"".equals(column)){
                List<String> value= Lists.newArrayList();
                value.add(this.oaControlTypeService.get(column.getControlTypeId()).getControlcontent());
                value.add(column.getColumnName());
                OneToOne.put(column.getColumnComment(),value);
            }
        }
        String tableContent=oaFormMaster.getContent();
        for (String comment:OneToOne.keySet()){
            List<String> list=OneToOne.get(comment);
            tableContent=tableContent.replace("["+comment+"]",list.get(0).replace("name=\"\"","name=\""+list.get(1)+"\"").replace("value=\"\"","value=\"${"+list.get(1)+"}\""));
         }
        oaFormMaster.setContent(tableContent);
 		oaFormMasterService.save(oaFormMaster);
		addMessage(redirectAttributes, "保存编辑器设计表单成功");
		return "redirect:"+Global.getAdminPath()+"/form/oaFormMaster/?repage";
	}
	
	@RequiresPermissions("form:oaFormMaster:edit")
	@RequestMapping(value = "delete")
	public String delete(OaFormMaster oaFormMaster, RedirectAttributes redirectAttributes) {
		oaFormMasterService.delete(oaFormMaster);
		addMessage(redirectAttributes, "删除编辑器设计表单成功");
		return "redirect:"+Global.getAdminPath()+"/form/oaFormMaster/?repage";
	}

}
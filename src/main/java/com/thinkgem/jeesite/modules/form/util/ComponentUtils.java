package com.thinkgem.jeesite.modules.form.util;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.form.entity.Component;
import com.thinkgem.jeesite.modules.form.entity.Components;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * xml
 * @author lgx
 * @version 2016-12-16
 */
public class ComponentUtils {
    private static Logger logger = LoggerFactory.getLogger(ComponentUtils.class);

    public static final String COMPONENT_LIST = "component_list";
    public static final String CONTROLTYPE_LIST = "controltype_list";
    public static final String TEMPLATE_LIST = "tmeplate_list";
    public static final String INIT_LIST = "init_list";

    /**
     * 获取xml信息
     * @return
     */
    public static Components getComponents(){
        Components components = (Components) CacheUtils.get(COMPONENT_LIST);
        if(components != null) return components;
        try {
            Resource resource = new ClassPathResource("/component.xml");
            InputStream is = resource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = br.readLine();
                if (line == null){
                    break;
                }
                sb.append(line).append("\r\n");
            }
            if (is != null) {
                is.close();
            }
            if (br != null) {
                br.close();
            }
            components = JaxbMapper.fromXml(sb.toString(), Components.class);
            CacheUtils.put(COMPONENT_LIST, components);
            return components;
        } catch (IOException e) {
            logger.warn("Error file convert: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 获取所有xml组件集合
     * @return
     */
    public static List<Component> getComponentList(){
        Components components = getComponents();
        if(components != null) return components.getComponent();
        return Lists.newArrayList();
    }

    /**
     * 获取所有控件
     * @return
     */
    public static List<Component> getControls(){
        List<Component> controls = (List<Component>) CacheUtils.get(CONTROLTYPE_LIST);
        if(controls == null) {
            List<Component> components = getComponentList();
            controls = new ArrayList<>();
            for(Component c : components ){
                if("controlType".equals(c.getType())) {
                    controls.add(c);
                }
            }
            CacheUtils.put(CONTROLTYPE_LIST,controls);
        }
        return controls;
    }

    /**
     * 获取所有模板
     * @return
     */
    public static List<Component> getTemplates(){
        List<Component> templates = (List<Component>) CacheUtils.get(TEMPLATE_LIST);
        if(templates == null) {
            List<Component> components = getComponentList();
            templates = new ArrayList<>();
            for(Component c : components ){
                if("controlType".equals(c.getType())) {
                    templates.add(c);
                }
            }
            CacheUtils.put(TEMPLATE_LIST,templates);
        }
        return templates;
    }

    public static List<Component> getInits() {
        List<Component> inits = (List<Component>) CacheUtils.get(INIT_LIST);
        if(inits == null) {
            List<Component> components = getComponentList();
            inits = new ArrayList<>();
            for(Component c : components ){
                if("initJs".equals(c.getType())) {
                    inits.add(c);
                }
            }
            CacheUtils.put(INIT_LIST,inits);
        }
        return inits;
    }

    /**
     * 根据组件值获取组件
     * @param value
     * @return
     */
    public static Component getComponent(String value,String type){
        if(StringUtils.isBlank(value)) return null;
        List<Component> components = null;
        if("template".equals(type)) {
            components = getTemplates();
        } else if("controlType".equals(type)) {
            components = getControls();
        } else if("initJs".equals(type)) {
            components = getInits();
        } else {
            components = getComponentList();
        }
        for(Component component : components){
            if(value.equals(component.getValue())){
                return component;
            }
        }
        return null;
    }

    public static Component getComponent(String value){
        return getComponent(value,null);
    }

    /**
     * 获取控件初始值
     * @param component
     * @return
     */
    public static String getInitValue(Component component) {
        if(component.getInitValue() == null) {
            return getInitValue(component.getInitMethod());
        }
        return component.getInitValue();
    }

    /**
     * 根据方法路径获取值
     * @param methodStr
     * @return
     */
    public static String getInitValue(String methodStr) {
        if(StringUtils.isNotBlank(methodStr)) {
            try {
                int index = methodStr.lastIndexOf(".");
                Object obj = Class.forName(methodStr.substring(0, index)).newInstance();
                Method method = obj.getClass().getMethod(methodStr.substring(index + 1));
                Object o = method.invoke(null);
                if (o != null) {
                    return (String) o;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 获取处理后的控件
     * @param columnMap value,columnName
     * @param initValue
     * @return
     */
    public static String initComponent(Map<String,Object> columnMap,boolean initValue) {
        String value = null;
        String columnName =null;
        String content = "";
        if(columnMap != null) {
            value = (String) columnMap.get("value");
            columnName = (String) columnMap.get("columnName");
        }
        if(StringUtils.isNotBlank(value) && StringUtils.isNotBlank(columnName)) {
            Component component = ComponentUtils.getComponent(value);
            if(component != null) {
                content = component.getContent();
                if(chargeMoreData(value)) {
                    String defalutValue = initValue ? getInitValue(component) : null;
                    content = handlerMoreDateComponent(value, content, (List<Dict>) columnMap.get("optData"), defalutValue);
                } else {
                    content = content.replace("${colValue}",initValue ? getInitValue(component) : ("${" + columnName + "}"));
                }
                content = content.replace("${colName}", columnName);
            }
        }
        return content;
    }

    public static String handlerMoreDateComponent(String value,String content,List<Dict> datas,String defaultValue) {
        if (chargeMoreData(value) && datas != null && datas.size() > 0 ) {
            StringBuilder html = new StringBuilder();
            String temp = content;
            for (Dict dict : datas) {
                temp = temp.replace("${value}", StringUtils.isBlank(dict.getValue()) ? "" : dict.getValue())
                        .replace("${label}", StringUtils.isBlank(dict.getLabel()) ? "" : dict.getLabel());
                if ("radio".equals(value)) {
                    temp = temp.replace("${checked}", StringUtils.isBlank(dict.getValue()) ? "" : (dict.getValue().equals(defaultValue) ? "checked" : ""));
                } else if ("checkbox".equals(value)) {
                    temp = temp.replace("${checked}", StringUtils.isBlank(defaultValue) ? "" : (("," + defaultValue + ",").indexOf("," + dict.getValue() + ",") > -1 ? "checked" : ""));
                } else if ("select".equals(value)) {
                    temp = temp.replace("${selected}", StringUtils.isBlank(defaultValue) ? "" : (("," + defaultValue + ",").indexOf("," + dict.getValue() + ",") > -1 ? "checked" : ""));
                }
                html.append(temp);
                temp = content;
            }
            if("select".equals(value)) {
                html.insert(0,"<select id=\"${colName}\" name=\"${colName}\">");
                html.append("</select>");
            }
            return html.toString();
        }
        return null;
    }

    //判断是否为多值控件
    public static boolean chargeMoreData(String value){
        if(StringUtils.isNotBlank(value) && !"radio".equalsIgnoreCase(value)
                && !"checkbox".equalsIgnoreCase(value) && !"select".equalsIgnoreCase(value)) {
            return false;
        }
        return true;
    }

    /**
     * 清除当前用户缓存
     */
    public static void clearCache(){
        UserUtils.removeCache(COMPONENT_LIST);
    }

}

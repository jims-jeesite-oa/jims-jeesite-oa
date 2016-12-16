package com.thinkgem.jeesite.modules.form.util;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.form.entity.Component;
import com.thinkgem.jeesite.modules.form.entity.Components;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * xml
 * @author lgx
 * @version 2016-12-16
 */
public class ComponentUtils {
    private static Logger logger = LoggerFactory.getLogger(ComponentUtils.class);

    public static final String COMPONENT_LIST = "component_list";

    public static Components getComponents(){
        clearCache();
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

    public static List<Component> getComponentList(){
        Components components = getComponents();
        if(components != null) return components.getComponent();
        return null;
    }

    public static Component getComponent(String value){
        if(StringUtils.isBlank(value)) return null;
        List<Component> components = ComponentUtils.getComponentList();
        for(Component component : components){
            if(value.equals(component.getValue())){
                return component;
            }
        }
        return null;
    }

    /**
     * 清除当前用户缓存
     */
    public static void clearCache(){
        UserUtils.removeCache(COMPONENT_LIST);
    }
}

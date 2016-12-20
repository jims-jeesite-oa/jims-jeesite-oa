package com.thinkgem.jeesite.modules.oa.units;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * freemarker工具类
 * @author lgx
 * @version 2016-12-20
 */
public class FreemarkerUtils {
    public static Template stringToTemplate(String content){
        Configuration cfg = new Configuration();
        cfg.setClassicCompatible(true);
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("template",content);
        cfg.setTemplateLoader(stringLoader);
        try {
            return cfg.getTemplate("template","utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String process(String content,Map params){
        StringWriter writer = new StringWriter();
        Template template = stringToTemplate(content);
        try {
            template.process(params,writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }
}

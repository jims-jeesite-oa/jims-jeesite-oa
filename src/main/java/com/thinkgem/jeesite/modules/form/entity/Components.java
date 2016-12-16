package com.thinkgem.jeesite.modules.form.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 自定义组件
 * @author lgx
 * @version 2016-12-16
 */
@XmlRootElement(name = "components")
@XmlAccessorType(XmlAccessType.FIELD)
public class Components {

    @XmlElement(name = "component")
    private List<Component> component;

    public List<Component> getComponent() {
        return component;
    }

    public void setComponent(List<Component> component) {
        this.component = component;
    }
}

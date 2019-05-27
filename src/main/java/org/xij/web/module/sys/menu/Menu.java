package org.xij.web.module.sys.menu;

import org.xij.web.module.base.BusinessEntity;
import org.xij.web.module.base.TreeEntity;

public class Menu extends BusinessEntity implements TreeEntity {
    private String name;
    private String href;
    private String icon;
    private String type;
    private String pId;
    private Integer sortCode;

    public Menu() {
    }

    public Menu(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }
}

package org.xij.web.module.base;

import org.xij.util.Id;

import java.util.Date;

/**
 * 业务实体
 */
public class BusinessEntity extends Entity {
    public static final String STATUS_NORMAL = "0";
    public static final String STATUS_DELETED = "1";

    protected String status;
    protected String createBy;
    protected String createDept;
    protected String updateBy;
    protected String updateDept;
    protected Date updateTime;
    protected String remark;

    public BusinessEntity() {
    }

    public BusinessEntity(String id) {
        super(id);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDept() {
        return createDept;
    }

    public void setCreateDept(String createDept) {
        this.createDept = createDept;
    }

    public Long getCreateTime() {
        if (id == null)
            return null;
        return Id.getTimeMillis(id);
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDept() {
        return updateDept;
    }

    public void setUpdateDept(String updateDept) {
        this.updateDept = updateDept;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

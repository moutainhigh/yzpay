package com.yunpay.permission.entity;

import com.yunpay.permission.entity.PermissionBaseEntity;

public class SysAppRelease  extends PermissionBaseEntity{
  
	private static final long serialVersionUID = 1L;
    private String appCode;   // app编号
    private String appName;  // app名称
    private String appApk;
    private String verName;  // 版本名称
    private String verCode;  // 版本编号
    private String appLink;  // 下载链接
    private String appIcon;  // app图标
  

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getAppApk() {
        return appApk;
    }

    public void setAppApk(String appApk) {
        this.appApk = appApk == null ? null : appApk.trim();
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName == null ? null : verName.trim();
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode == null ? null : verCode.trim();
    }

    public String getAppLink() {
        return appLink;
    }

    public void setAppLink(String appLink) {
        this.appLink = appLink == null ? null : appLink.trim();
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon == null ? null : appIcon.trim();
    }
}
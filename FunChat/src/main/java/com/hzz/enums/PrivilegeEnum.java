package com.hzz.enums;

public enum PrivilegeEnum {
    BLACK("黑名单"),
    AUTOCHAT("自动回复"),
    MASS("联系人群发"),
    UPLOAD("上传文件"),
    DOWNLOAD("下载文件"),
    CONTROL("控制电脑");
    private String value;
    PrivilegeEnum(String value){
        this.value=value;
    }
    public String getValue(){
        return  value;
    }



}

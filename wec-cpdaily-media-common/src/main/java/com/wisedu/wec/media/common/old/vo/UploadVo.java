package com.wisedu.wec.media.common.old.vo;

import java.io.Serializable;

/**
 * Created by croyson on 2017/10/23.
 * success:Boolean,id:String,name:String,fileUrl:String
 */
public class UploadVo implements Serializable {
    private static final long serialVersionUID = -3056360495959959148L;
    private Boolean success;
    private String id;
    private String name;
    private String fileUrl;

    public UploadVo(Boolean success, String id, String name, String fileUrl) {
        this.success = success;
        this.id = id;
        this.name = name;
        this.fileUrl = fileUrl;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}

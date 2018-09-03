package com.wisedu.wec.media.common.old.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by croyson on 2017/10/23.
 * success:Boolean,id:String,name:String,fileUrl:String
 */
public class ImgUploadReqVo implements Serializable {


    private static final long serialVersionUID = -3842103487609755276L;
    /* base64数据 */
    private String data;
    /* 文件名称 */
    private String name;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

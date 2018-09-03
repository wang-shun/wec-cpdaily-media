package com.wisedu.wec.media.biz.service;

import com.wisedu.wec.media.common.vo.OssUploadPolicyInfo;
import com.wisedu.wecloud.commons.model.ResponseResult;



public interface FileService {

    public ResponseResult<?> getImgUploadPolicy(String bizType);

}

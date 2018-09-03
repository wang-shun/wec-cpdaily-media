package com.wisedu.wec.media.web.controller;

import com.wisedu.wec.media.biz.service.FileService;
import com.wisedu.wecloud.commons.model.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FileController {

	@Autowired
	private FileService service;

    @RequestMapping(value = "/file/upload/policy", method = RequestMethod.GET,
            produces = { "application/json" },
            consumes = { "application/json" })
    public ResponseResult<?> getImgUploadPolicy(@RequestParam(value = "bizType", required = false) String bizType) {
        // do some magic!
        
        return service.getImgUploadPolicy(bizType);
    }

}

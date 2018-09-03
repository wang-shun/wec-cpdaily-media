package com.wisedu.wec.media.web.controller;

import com.wisedu.wec.media.biz.service.TenantService;
import com.wisedu.wecloud.commons.model.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TenantController {

	@Autowired
	private TenantService service;

    @RequestMapping(value = "/tenant/list", method = RequestMethod.GET)
    public ResponseResult<?> getUserRelatedTenantList() {
        // do some magic!
        
        return service.getUserRelatedTenantList();
    }

}

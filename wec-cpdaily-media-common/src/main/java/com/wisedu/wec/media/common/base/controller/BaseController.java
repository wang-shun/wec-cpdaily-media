package com.wisedu.wec.media.common.base.controller;

import com.wisedu.wec.media.common.base.Page;
import com.wisedu.wec.media.common.base.response.BaseRestResponse;
import com.wisedu.wec.media.common.base.response.ListRestResponse;
import com.wisedu.wec.media.common.base.response.PageRestResponse;
import com.wisedu.wec.media.common.base.response.RestResponse;
import com.wisedu.wec.media.common.exception.ValidateException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.io.Serializable;
import java.util.List;

public class BaseController {

    public void handleHibernateError(BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                throw new ValidateException(error.getDefaultMessage());
            }
        }
    }

    public BaseRestResponse createReponse(){
        return new BaseRestResponse();
    }
    public BaseRestResponse createErrorReponse(String msg){
        return new BaseRestResponse().error(msg);
    }

    public <T> RestResponse<T> createReponse(T result){
        RestResponse<T> res = new RestResponse<>();
        res.setData(result);
        return res;
    }

    public <T> ListRestResponse<T> createListReponse(List<T> items){
        ListRestResponse<T> res = new ListRestResponse<>();
        res.setData(items);
        return res;
    }

    public <T extends Serializable> PageRestResponse<T> createPagedResponse(List<T> items, Page page){
        PageRestResponse<T> res = new PageRestResponse<>();
        res.setData(items);
        res.setPage(page);
        return res;
    }
}

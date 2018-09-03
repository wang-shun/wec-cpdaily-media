package com.wisedu.wec.media.common.base.response;

import com.wisedu.wec.media.common.base.Page;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PageRestResponse<T extends Serializable> extends BaseRestResponse {

    private List<T> data;
    private Page page = new Page();

    public PageRestResponse<T> page(Page page){
        this.page=page;
        return this;
    }

}

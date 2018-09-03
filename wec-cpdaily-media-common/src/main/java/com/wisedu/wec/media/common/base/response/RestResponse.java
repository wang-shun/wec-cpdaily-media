package com.wisedu.wec.media.common.base.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponse<T> extends BaseRestResponse {
    private T data;
}

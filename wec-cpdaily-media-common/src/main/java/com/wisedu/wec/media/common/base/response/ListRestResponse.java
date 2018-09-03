package com.wisedu.wec.media.common.base.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListRestResponse<T> extends BaseRestResponse {
    private List<T> data;
}

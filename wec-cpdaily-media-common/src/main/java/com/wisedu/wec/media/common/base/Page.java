package com.wisedu.wec.media.common.base;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by huhaichao on 2017/6/8.
 */
@Getter
@Setter
public class Page {

    private int pageNo = 1;

    private int pageSize = 5;

    private long totalCount = 0;


    public long getTotalPage() {
        return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
    }

    public int limitStart() {
        return pageSize * (pageNo - 1);
    }

    public int limitEnd() {
        return pageSize;
    }

    /**
     * 限制分页条数，防止用户乱填写造成内存溢出
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        if (pageSize > 100) {
            pageSize = 100;
        }
        this.pageSize = pageSize;
    }

}

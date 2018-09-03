package com.wisedu.wec.media.common.old.vo;

import java.io.Serializable;
import java.util.List;


public class PageVo<T> implements Serializable{

    private static final long serialVersionUID = 2965906993600811416L;

    /* 数据总数 */
    private int totalSize;
    /* 每页大小 */
    private int pageSize;
    /* 当前页码 */
    private int pageNumber;
    /* 数据 */
    private List<T> rows;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}

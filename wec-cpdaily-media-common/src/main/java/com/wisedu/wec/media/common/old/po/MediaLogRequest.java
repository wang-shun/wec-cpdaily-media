package com.wisedu.wec.media.common.old.po;

/**
 * @author zsl
 */
public class MediaLogRequest {

    private String beginDate;

    private String endDate;

    private int pageNum = 1;

    private int pageSize = 10;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MediaLogRequest{");
        sb.append(", beginDate='").append(beginDate).append('\'');
        sb.append(", endDate='").append(endDate).append('\'');
        sb.append(", pageNum=").append(pageNum);
        sb.append(", pageSize=").append(pageSize);
        sb.append('}');
        return sb.toString();
    }
}

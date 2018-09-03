package com.wisedu.wec.media.common.old.po;

/**
 * @author 01112143
 */
public class MediaRankRequest {

    private String periodType;

    private String tenantId;

    private String mediaType;

    private int periodIndex;

    public String getPeriodType() {
        return periodType;
    }

    private int pageNum = 1;

    private int pageSize = 10;


    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public int getPeriodIndex() {
        return periodIndex;
    }

    public void setPeriodIndex(int periodIndex) {
        this.periodIndex = periodIndex;
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
        final StringBuilder sb = new StringBuilder("MediaListRequest{");
        sb.append("periodType='").append(periodType).append('\'');
        sb.append(", tenantId='").append(tenantId).append('\'');
        sb.append(", mediaType='").append(mediaType).append('\'');
        sb.append(", periodIndex='").append(periodIndex).append('\'');
        sb.append(", pageNum=").append(pageNum);
        sb.append(", pageSize=").append(pageSize);
        sb.append('}');
        return sb.toString();
    }
}

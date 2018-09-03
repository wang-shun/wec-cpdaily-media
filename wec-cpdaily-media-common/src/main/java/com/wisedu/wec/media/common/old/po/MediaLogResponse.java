package com.wisedu.wec.media.common.old.po;

import java.util.List;

/**
 * @author 01112143
 */
public class MediaLogResponse {

    private List<Log> logs;

    private long total;

    private int pageNum;

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RankMediaResponse{");
        sb.append("medias=").append(logs);
        sb.append(", total=").append(total);
        sb.append(", pageNum=").append(pageNum);
        sb.append('}');
        return sb.toString();
    }
}

package com.wisedu.wec.media.common.old.po;

import java.util.List;

/**
 * @author 01112143
 */
public class RankMediaResponse {

    private List<RankMedia> medias;

    private long total;

    private int pageNum;

    public List<RankMedia> getMedias() {
        return medias;
    }

    public void setMedias(List<RankMedia> medias) {
        this.medias = medias;
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
        sb.append("medias=").append(medias);
        sb.append(", total=").append(total);
        sb.append(", pageNum=").append(pageNum);
        sb.append('}');
        return sb.toString();
    }
}

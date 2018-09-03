package com.wisedu.wec.media.common.old.po;

/**
 * 校园号粉丝数查询结果
 * @author mdmo
 *
 */
public class MediaFansCount {
	
	private String mediaId;
	
	private int fansCount;

	public int getFansCount() {
		return fansCount;
	}

	public void setFansCount(int fansCount) {
		this.fansCount = fansCount;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}

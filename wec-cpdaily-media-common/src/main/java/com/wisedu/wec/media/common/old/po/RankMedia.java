package com.wisedu.wec.media.common.old.po;

import java.io.Serializable;

/**
 * 带排名的校园号
 * 
 * @author zsl
 * @since 1.0.0
 */
public class RankMedia implements Serializable {
	private static final long serialVersionUID = -5430778494274405912L;

	private String wid;
	private String name; // 名称
	private String description; // 名称
	private String icon; //图标
	private String tenantId; // 学校id
	private String tenantName; // 学校名称
	private String mediaType; //校园号类型
	private String createTime;
	private Float score; // 影响力
	private int order; // 排名

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Media{");
		sb.append("wid='").append(wid).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", icon='").append(icon).append('\'');
		sb.append(", tenantId='").append(tenantId).append('\'');
		sb.append(", tenantName='").append(tenantName).append('\'');
		sb.append(", mediaType='").append(mediaType).append('\'');
		sb.append(", createTime=").append(createTime);
		sb.append(", score=").append(score);
		sb.append('}');
		return sb.toString();
	}
}

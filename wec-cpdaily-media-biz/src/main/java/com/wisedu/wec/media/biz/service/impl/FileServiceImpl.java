package com.wisedu.wec.media.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.vo.OssUploadPolicyInfo;
import com.wisedu.wec.media.biz.service.FileService;
import com.wisedu.wecloud.commons.model.ResponseResult;
import com.wisedu.wecloud.commons.util.UniqueIdentifierUtil;

@Service
public class FileServiceImpl implements FileService{
	private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
	
    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.resource}")
    private String bucket;
    
	@Value("${oss.accessKeyId}")
	private String accessKeyId;

	@Autowired
	private OSSClient ossClient;
	
	@Override
	public ResponseResult<?> getImgUploadPolicy(String bizType) {
		UserInfoContext userContext = ThreadLocalUserInfo.getContext();
		String tenantId = userContext.getTenantId(); // 上传文件前缀
		String mediaId = userContext.getMediaId();
		String fileId = UniqueIdentifierUtil.generateUUID();
		
		String fileSavePath = getPath(tenantId, mediaId, bizType, fileId);
		String dir = fileSavePath.substring(0,fileSavePath.lastIndexOf("/"));
		long ossExpireTime = 60; // 过期时间
		long expireEndTime = System.currentTimeMillis() + ossExpireTime * 1000; // 过期结束时间
		Date expiration = new Date(expireEndTime);
		
		OssUploadPolicyInfo ossUploadPolicyInfo = new OssUploadPolicyInfo();
		// 获得OSS链接
		String host = "https://" + bucket + "." + ossClient.getEndpoint().getAuthority();

		try{
			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
		
			String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
			byte[] binaryData = postPolicy.getBytes("utf-8");
			String encodedPolicy = BinaryUtil.toBase64String(binaryData);
			String postSignature = ossClient.calculatePostSignature(postPolicy);
		
			ossUploadPolicyInfo.setPolicy(encodedPolicy);
			ossUploadPolicyInfo.setSignature(postSignature);
		}catch (Exception e) {
			LOGGER.error("获取OSS上传凭证失败",e);
			return ResponseResult.failure("获取OSS上传凭证失败"+e.getMessage());
		}
		ossUploadPolicyInfo.setAccessid(accessKeyId);
		ossUploadPolicyInfo.setDir(dir);
		ossUploadPolicyInfo.setHost(host);
		ossUploadPolicyInfo.setFileName(fileSavePath);

		return ResponseResult.success(ossUploadPolicyInfo);
		
	}
	 /**
     * 获取文件上传路径
     */
    private String getPath(String tenant, String mediaId, String bizType, String fileId) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        return sb.append(tenant).append("/").append(mediaId).append("/").append(sdf.format(d)).
                append("/").append(bizType).append("/").append(fileId).append(".jpg").toString();

    }
}

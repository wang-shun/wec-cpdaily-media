package com.wisedu.wec.media.biz.old.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aliyun.oss.OSSClient;
import com.wisedu.wec.media.common.old.util.ImgUtils;
import com.wisedu.wec.media.common.old.vo.ApiResp;
import com.wisedu.wec.media.common.old.vo.OssStsInfo;
import com.wisedu.wec.media.common.old.vo.UploadVo;

/**
 * @author 14116004
 */
@Service
public class OssService {

    private static OssStsInfo ossStsInfo = null;

    private static OSSClient ossClient = null;

    @Value("${sts.url}")
    private String stsUrl;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.resource}")
    private String bucket;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 获取oss客户端
     *
     * @param
     * @return
     * @throws IOException
     */
    private OSSClient getClient() throws Exception {
        if (null != ossClient) {
            //如果过期了重新生成
            if (ossStsInfo.getExpiration().before(new Date())) {
                //重新获取配置信息
                ossStsInfo = getOssConfig();
                //重新生成ossClient
                ossClient = new OSSClient(endpoint, ossStsInfo.getAccessKeyId(),
                        ossStsInfo.getAccessKeySecret(), ossStsInfo.getSecurityToken());
            }
        } else {
            //获取配置信息
            ossStsInfo = getOssConfig();
            //生成client
            ossClient = new OSSClient(endpoint, ossStsInfo.getAccessKeyId(),
                    ossStsInfo.getAccessKeySecret(), ossStsInfo.getSecurityToken());

        }
        return ossClient;
    }

    /**
     * 获取sts授权信息
     *
     * @param
     * @return
     * @throws IOException
     */
    private OssStsInfo getOssConfig() throws Exception {
        ApiResp resp = restTemplate.getForEntity(stsUrl, ApiResp.class).getBody();
        if (resp != null && resp.getErrCode() == 0) {
            ossStsInfo = resp.getData();
        }
        return ossStsInfo;
    }


    /**
     * 文件上传
     *
     * @param
     * @return
     * @throws IOException
     */
    public UploadVo upload(String tenant, String fileName, String fileType, byte[] file) throws Exception {
        OSSClient client = getClient();
        UploadVo uploadVo;
        if (null != client) {
            String fileId = ImgUtils.getRandomString(36);
            String path = getPath(tenant, fileId, fileName);
            client.putObject(bucket, path, new ByteArrayInputStream(file));
            uploadVo = new UploadVo(true, fileId, fileName,ImgUtils.imgUrlsSAddHost(path));

        } else {
            uploadVo = new UploadVo(false, "", "", "");
        }
        return uploadVo;
    }



    /**
     * 文件上传
     *
     * @param
     * @return
     * @throws IOException
     */
    public UploadVo uploadBase64(String tenant,String file) throws Exception {
        OSSClient client = getClient();
        UploadVo uploadVo;
        if (null != client) {
            String fileId = ImgUtils.getRandomString(36);
            String path = getPath(tenant, fileId, fileId+".jpg");
            if(file.indexOf("data:image/jpg")>=0){
            	file = file.replace("data:image/jpg;base64,","");
            }
            if(file.indexOf("data:image/jpeg")>=0){
            	file = file.replace("data:image/jpeg;base64,","");
            }
            if(file.indexOf("data:image/png")>=0){
            	file = file.replace("data:image/png;base64,","");
            }
            if(file.indexOf("data:image/bmp")>=0){
            	file = file.replace("data:image/bmp;base64,","");
            }
            client.putObject(bucket, path, new ByteArrayInputStream(Base64.decodeBase64(file)));
            uploadVo = new UploadVo(true, fileId, fileId+".jpg",ImgUtils.imgUrlsSAddHost(path));

        } else {
            uploadVo = new UploadVo(false, "", "", "");
        }
        return uploadVo;
    }

    /**
     * 获取文件上传路径
     */
    private String getPath(String tenant, String fileId, String fileName) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        return sb.append(tenant).append("/").append(sdf.format(d)).
                append("/").append(fileId).append("/").append(StringUtils.replaceAll(fileName, "[!$'()*,;&=]", "a")).toString();

    }
}

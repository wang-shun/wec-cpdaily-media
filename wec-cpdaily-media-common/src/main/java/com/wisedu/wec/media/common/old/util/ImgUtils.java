package com.wisedu.wec.media.common.old.util;

import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by croyson on 2017/10/23.
 */
public class ImgUtils {

    private final static String imgDomain = "https://img.cpdaily.com";

    private final static String crawlerResHost = "cpdaily-crawler.oss-cn-hangzhou.aliyuncs.com";

    private final static String crawlerCdnHost = "crawler.cpdaily.com";

    private final static String imgResHost = "cpdaily-resource.oss-cn-hangzhou.aliyuncs.com";

    private final static String imgCdnHost = "img.cpdaily.com";

    /**
     * 随机生成文件名称
     */
    public static String getRandomString(int len) {
        Random random = new Random();
        int idx = 0;
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String rst = "";
        while (idx < len) {
            int num = random.nextInt(62);
            rst = rst + str.charAt(num);
            idx = idx + 1;
        }
        return rst;
    }

    /**
     * 根据filename获取文件后缀
     */
    public static String getImgType(String fileName) {
        if (fileName.indexOf(".") > -1) {
            return fileName.substring(fileName.lastIndexOf("."), fileName.length());
        } else {
            return "";
        }
    }

    /**
     * 根据url获取文件名称
     */
    public static String getFileName(String url) throws Exception {
        return URLDecoder.decode(url.substring(url.lastIndexOf("/") + 1, url.length()), "utf-8");
    }


    /**
     * 地址注入域名
     */
    public static String imgUrlsSAddHost(String imgUrls) {
        String[] imgs = imgUrls.split(",");
        List<String> imgList = new ArrayList();
        for (String item : imgs) {
            String img = item.trim();
            if (StringUtils.isBlank(img))
                continue;
            if (img.startsWith("http") || img.startsWith("www.") || img.startsWith("//")) {
                if (img.indexOf(crawlerResHost) > -1)
                    imgList.add(img.replace(crawlerResHost, crawlerCdnHost));
                else if (img.indexOf(imgResHost) > -1) {
                    imgList.add(img.replace(imgResHost, imgCdnHost));
                } else {
                    imgList.add(img);
                }
            } else {
                imgList.add(imgDomain + "/" + img);
            }
        }
        return StringUtils.join(imgList, ",");
    }
}

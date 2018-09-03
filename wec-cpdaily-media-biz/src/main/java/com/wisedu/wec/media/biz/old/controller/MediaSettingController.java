package com.wisedu.wec.media.biz.old.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.wisedu.wec.media.common.old.constants.MediaAuthStatus;
import com.wisedu.wec.media.common.old.po.CpdailyTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wisedu.wec.media.biz.old.service.impl.MediaAccountService;
import com.wisedu.wec.media.biz.old.service.impl.UserService;
import com.wisedu.wec.media.common.old.constants.MediaPlatformCode;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.po.CampusMedia;
import com.wisedu.wec.media.common.old.util.QRCodeUtil;
import com.wisedu.wec.media.common.old.vo.CampusMediaVo;
import com.wisedu.wec.media.common.old.vo.CommonVO;

/**
 * 媒体号设置
 *
 * @author dell
 *
 */
@RestController
@RequestMapping("/v3/campusmedia/setting")
public class MediaSettingController extends BaseController {

	@Value("${cpdaily.downloadUrl}")
	private String cpdailyDownloadUrl;

	@Value("${login.qrcodeHost}")
	private String qrcodeHost;

	@Autowired
	private UserService oldUserService;
	@Autowired
	private MediaAccountService mediaAccountService;

	/**
	 * 获取校园号账号信息
	 *
	 * @param mediaId 媒体号唯一ID
	 * @return
	 */
	@RequestMapping("/mediaInfo/{mediaId}")
	public CommonVO mediaInfoById(@PathVariable String mediaId) {
		if (mediaId.equals("-1")) {
			mediaId = ThreadLocalUserInfo.getContext().getMediaId();
		}
		CampusMedia media = oldUserService.mediaInfoById(mediaId);
		return successResponseWithData(media);
	}

	/**
	 * 媒体号二维码图片数据
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = { "/mediaQrcode/{mediaId}", "/mediaQrcode/{mediaId}/{type}" })
	public void getQrcodeImage(@PathVariable String mediaId, @PathVariable String type, HttpServletResponse response) throws Exception {

		CampusMedia media = oldUserService.mediaInfoById(mediaId);
		if (media != null) {
			String qrcodeUrl = qrcodeHost + "/v3/campusmedia/setting/mediaQrcodeUrl?action=mamp://homepage/" + mediaId;
			String headUrl = media.getIcon();
			if (headUrl != null) {
				if (headUrl.indexOf("http") == -1) {
					headUrl = "https://img.cpdaily.com" + headUrl;
				}
			}
			if (headUrl != null) {
				// headUrl:二维码中间logo图标
				QRCodeUtil.encode(qrcodeUrl, headUrl, type, response.getOutputStream(), true);
			} else {
				QRCodeUtil.encode(qrcodeUrl, type, response.getOutputStream());
			}
		}
	}

	/**
	 * 直接请求二维码地址时返回数据
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/mediaQrcodeUrl", method = RequestMethod.GET)
	public void mediaQrCodeUrl(HttpServletResponse response) throws IOException {
		response.sendRedirect(cpdailyDownloadUrl);
	}

	/**
	 * 修改媒体号资料
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/updateMedia", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public CommonVO updateUserInfo(@RequestBody CampusMedia media) {

		// 如果是重新申请操作（修改的校园号之前的状态是已拒绝）
		CampusMedia oldMediaInfo = oldUserService.mediaInfoById(media.getWid());
		if(MediaAuthStatus.AUTH_FAIL.toString().equals(oldMediaInfo.getAuthStatus())){
			//校验校园号数量是否已经达到上限
			boolean canApply = mediaAccountService.checkMediaCount();
			if(!canApply){
				return failResponseWithMsg(MediaPlatformCode.MEDIA_COUNT_LIMITED, "每人最多只能拥有5个校园号</br>包括审核通过、待审核、待认证的哦～");
			}
		}

		if(!media.getName().equals(oldMediaInfo.getName()) && mediaAccountService.mediaNameExist(media.getTenantId(),media.getName())){
			return failResponseWithMsg(MediaPlatformCode.PARAM_ILLEGAL,"名称已经被占用");
		}

//		String mediaId = ThreadLocalUserInfo.getContext().getMediaId();
//		media.setWid(mediaId);
		oldUserService.updateMediaInfo(media);
		return successResponse();

	}

	/**
	 * 修改密码
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/update/password", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public CommonVO updatePassword(@RequestParam String oldCode, @RequestParam String newCode) {

		String mediaId = ThreadLocalUserInfo.getContext().getMediaId();

		oldUserService.updateMediaPassword(mediaId, oldCode, newCode);

		return successResponse();
	}

	/**
	 * 获取校园号账号信息
	 *
	 * @param mediaId 媒体号唯一ID
	 * @return
	 */
	@RequestMapping("/mediaTenant/{mediaId}")
	public CommonVO getMediaTenant(@PathVariable String mediaId) {
		if (mediaId.equals("-1")) {
			mediaId = ThreadLocalUserInfo.getContext().getMediaId();
		}
		CpdailyTenant tenant = oldUserService.getMediaTenant(mediaId);
		return successResponseWithData(tenant);
	}

}

package com.wisedu.wec.media.web.controller;

import com.wisedu.wec.media.biz.old.controller.BaseController;
import com.wisedu.wec.media.biz.old.service.impl.MediaAccountService;
import com.wisedu.wec.media.biz.service.impl.MediaRankingService;
import com.wisedu.wec.media.common.old.constants.MediaPlatformCode;
import com.wisedu.wec.media.common.old.constants.RedisConstants;
import com.wisedu.wec.media.common.old.context.MediaInfoContext;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.old.exception.BadRequestException;
import com.wisedu.wec.media.common.old.po.*;
import com.wisedu.wec.media.common.old.util.CookieUtils;
import com.wisedu.wec.media.common.old.util.InputValidator;
import com.wisedu.wec.media.common.old.vo.CommonVO;
import com.wisedu.wec.media.dal.mybatis.CpdailyTenantMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 管理者页面
 *
 * @author dell
 *
 */
@RestController
public class RankController extends BaseController {

	@Autowired
	private MediaRankingService mediaRankingService;
	
	/**
	 * 校园号排行
	 */
	@RequestMapping("/rankings")
	public CommonVO listRank(@RequestBody MediaRankRequest mediaRankRequest) {

		RankMediaResponse mediaRankResponse = mediaRankingService.getMediaRankings(mediaRankRequest);
		return successResponseWithData(mediaRankResponse);
	}

}
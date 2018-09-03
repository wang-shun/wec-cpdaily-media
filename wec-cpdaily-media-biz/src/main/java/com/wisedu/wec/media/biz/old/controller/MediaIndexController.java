package com.wisedu.wec.media.biz.old.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wisedu.wec.media.biz.old.service.impl.IndexService;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.vo.CommonVO;
import com.wisedu.wec.media.common.old.vo.IndexVo;
import com.wisedu.wec.media.common.old.vo.MediaMsg7DayVo;
import com.wisedu.wec.media.dal.mybatis.CpdailyUserMapper;


/**
 * 首页（媒体号首页）
 *
 * @author dell
 *
 */
@RestController
public class MediaIndexController extends BaseController {

  private static final Logger logger = LoggerFactory.getLogger(MediaIndexController.class);

  @Autowired
  private IndexService indexService;

  @Autowired
  private CpdailyUserMapper cpdailyUserMapper;

  /**
   * 首页
   *
   * @param pageSize
   * @return
   */
  @RequestMapping(value = "/v3/campusmedia/index", produces = {MediaType.APPLICATION_JSON_VALUE})
  public CommonVO index(@RequestParam Integer pageSize) {
    IndexVo vo = new IndexVo();

    String mediaId = ThreadLocalUserInfo.getContext().getMediaId();

    int fansCount = cpdailyUserMapper.selectFansNumber(mediaId);

    vo.setFansCount(fansCount);
    vo.setMsgMap(indexService.query7DaysMsg(1, pageSize));
    return successResponseWithData(vo);

  }

  /**
   * 首页最近7天群发消息
   *
   * @param pageNumber
   * @param pageSize
   * @return
   */
  @RequestMapping(value = "/v3/campusmedia/index/msg", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public CommonVO index(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {

    Map<String, List<MediaMsg7DayVo>> msgMap = indexService.query7DaysMsg(pageNumber, pageSize);
    return successResponseWithData(msgMap);

  }
}

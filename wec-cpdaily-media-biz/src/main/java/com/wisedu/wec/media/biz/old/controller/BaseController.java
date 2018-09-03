package com.wisedu.wec.media.biz.old.controller;

import com.wisedu.wec.media.common.old.vo.CommonVO;

public class BaseController {

  public CommonVO successResponse() {
    CommonVO vo = new CommonVO();
    vo.setErrCode("0");
    vo.setErrMsg("success");
    vo.setCode("0");
    vo.setMessage("success");
    return vo;
  }

  public CommonVO failResponseWithMsg(String msg) {
    CommonVO vo = new CommonVO();
    vo.setCode("-1");
    vo.setMessage(msg);
    vo.setErrCode("-1");
    vo.setErrMsg(msg);
    return vo;
  }

  public CommonVO failResponseWithMsg(String code, String msg) {
    CommonVO vo = new CommonVO();
    vo.setCode(code);
    vo.setMessage(msg);
    vo.setErrCode(code);
    vo.setErrMsg(msg);
    return vo;
  }
  public CommonVO illegalParameterResponse() {
    CommonVO vo = new CommonVO();
    vo.setCode("-1");
    vo.setMessage("illegal parameter");
    vo.setErrCode("-1");
    vo.setErrMsg("illegal parameter");
    return vo;
  }

  public CommonVO notFoundResponse() {
    CommonVO vo = new CommonVO();
    vo.setCode("-1");
    vo.setMessage("not found");
    vo.setErrCode("-1");
    vo.setErrMsg("not found");
    return vo;
  }

  public CommonVO successResponseWithData(Object data) {
    CommonVO vo = new CommonVO();
    vo.setCode("0");
    vo.setMessage("success");
    vo.setErrCode("0");
    vo.setErrMsg("success");
    vo.setDatas(data);
    return vo;
  }

  public CommonVO failResponseWithData(Object data) {
    CommonVO vo = new CommonVO();
    vo.setCode("-1");
    vo.setMessage("fail");
    vo.setErrCode("-1");
    vo.setErrMsg("fail");
    vo.setDatas(data);
    return vo;
  }

}

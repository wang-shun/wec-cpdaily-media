package com.wisedu.wec.media.dal.mybatis;

import com.wisedu.wec.media.common.old.po.AutoReplyMsg;

public interface AutoReplyMsgMapper {

  int insert(AutoReplyMsg record);

  AutoReplyMsg selectByPrimaryKey(String mediaId);

  int updateByPrimaryKey(AutoReplyMsg record);

}

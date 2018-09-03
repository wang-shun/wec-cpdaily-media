package com.wisedu.wec.media.common.to;

/**
 * Created by zsl on 2018/4/20.
 */
public class SendMessageRequestBean {

    private String appId;
    private long timestamp;
    private String sign;
    private ReceiverBean receiver;
    private InnerInfoSenderBean sender;
    private boolean isPush;
    private int msgType;
    private ImgInnerMsgBean imgInnerMsg;
    private AttachInnerMsgBean attachInnerMsg;
    private CommonInnerMsgBean commonInnerMsg;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public ReceiverBean getReceiver() {
        return receiver;
    }

    public void setReceiver(ReceiverBean receiver) {
        this.receiver = receiver;
    }

    public InnerInfoSenderBean getSender() {
        return sender;
    }

    public void setSender(InnerInfoSenderBean sender) {
        this.sender = sender;
    }

    public boolean getIsPush() {
        return isPush;
    }
    public void setIsPush(boolean push) {
        isPush = push;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public ImgInnerMsgBean getImgInnerMsg() {
        return imgInnerMsg;
    }

    public void setImgInnerMsg(ImgInnerMsgBean imgInnerMsg) {
        this.imgInnerMsg = imgInnerMsg;
    }

    public AttachInnerMsgBean getAttachInnerMsg() {
        return attachInnerMsg;
    }

    public void setAttachInnerMsg(AttachInnerMsgBean attachInnerMsg) {
        this.attachInnerMsg = attachInnerMsg;
    }

    public CommonInnerMsgBean getCommonInnerMsg() {
        return commonInnerMsg;
    }

    public void setCommonInnerMsg(CommonInnerMsgBean commonInnerMsg) {
        this.commonInnerMsg = commonInnerMsg;
    }
}

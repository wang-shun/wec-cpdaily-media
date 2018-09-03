package com.wisedu.wec.media.common.old.constants;


public class MsgErrConstants {

    private static final int BASE_ERROR_CODE = 11000;

    /**
     * 用户组类型
     */
    public enum MsgErrTypeE implements BaseErrTypeE {
        NOT_EXIST(MsgErrConstants.BASE_ERROR_CODE + 1, "消息不存在"),
        NO_PERMISSIONS(MsgErrConstants.BASE_ERROR_CODE + 2, "消息没有权限");

        private int nCode;
        private String desc;

        MsgErrTypeE(int _nCode, String desc) {
            this.nCode = _nCode;
            this.desc = desc;
        }

        public int getnCode() {
            return nCode;
        }

        public void setnCode(int nCode) {
            this.nCode = nCode;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}

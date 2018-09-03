package com.wisedu.wec.media.common.old.constants;

import java.text.SimpleDateFormat;

public class MsgConstants {

    public static final String DEP_OWNER_MARK = "_other";

    public static final String TEACHER = "teacher";

    public static final String STUDENT = "student";

    public static final String NEWER = "new";

    public static final String FANS = "fans";

    public static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat yyyyMMdd= new SimpleDateFormat("yyyy-MM-dd");

    public static final String DEFAULT_FILE_ICON = "fans";

    public static final String IM_MSG_TYPE = "mediaMessage";

    public static final String IM_MSG_READ_URL = "/wec-cpdaily-media/v3/campusmedia/gateway/msg/read/";

    /**
     * 消息状态
     */
    public enum MsgStatus {
        SEND("未发送"), SEND_IN("发送中"), SEND_END("发送成功"), SEND_ERROR("发送失败");

        // 成员变量
        private String name;
        MsgStatus(String name){
            this.name = name;
        }

        public static String getName(String value) {
            for (MsgStatus c : MsgStatus.values()) {
                if (c.toString().equals(value)) {
                    return c.name;
                }
            }
            return null;
        }
    }

    /**
     * 消息操作类型
     */
    public enum OpTypeStatus {
        SAVE(0), SAVE_SEND(1);

        private int nCode;

        OpTypeStatus(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }

        public boolean equals(String obj) {
            return String.valueOf(this.nCode).equals(obj);
        }
    }

    /**
     * 用户组类型
     */
    public enum SendUserGroupType {
        PERSONAL(0), //个人创建的用户组
        ACADEMY_MAJOR(1), //学生的学院\专业
        DEPART(2); //教师的部门

        private int nCode;

        SendUserGroupType(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }

        public boolean equals(String obj) {
            return String.valueOf(this.nCode).equals(obj);
        }
    }

}

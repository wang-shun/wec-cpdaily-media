package com.wisedu.wec.media.common.vo;

import java.io.Serializable;
import java.util.Objects;


public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;


    private String code = "0";

    private String message = null;

    public BaseResponse code(String code) {
        this.code = code;
        return this;
    }
    public BaseResponse message(String message) {
        this.message = message;
        return this;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseResponse baseResponse = (BaseResponse) o;
        return Objects.equals(code, baseResponse.code) &&
                Objects.equals(message, baseResponse.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BaseResponse {\n");

        sb.append("    code: ").append(toIndentedString(code)).append("\n");
        sb.append("    message: ").append(toIndentedString(message)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}


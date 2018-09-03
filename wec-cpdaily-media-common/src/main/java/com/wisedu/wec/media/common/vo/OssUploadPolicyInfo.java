package com.wisedu.wec.media.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;


public class OssUploadPolicyInfo  implements Serializable {

  private static final long serialVersionUID = 1L;
  
  
  private String accessid = null;
  
  private String dir = null;
  
  private String host = null;
  
  private String policy = null;
  
  private String signature = null;
  
  private String fileName = null;

  public String getAccessid() {
    return accessid;
  }

  public void setAccessid(String accessid) {
    this.accessid = accessid;
  }

  public String getDir() {
    return dir;
  }

  public void setDir(String dir) {
    this.dir = dir;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPolicy() {
    return policy;
  }

  public void setPolicy(String policy) {
    this.policy = policy;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OssUploadPolicyInfo ossUploadPolicyInfo = (OssUploadPolicyInfo) o;
    return Objects.equals(accessid, ossUploadPolicyInfo.accessid) &&
        Objects.equals(dir, ossUploadPolicyInfo.dir) &&
        Objects.equals(host, ossUploadPolicyInfo.host) &&
        Objects.equals(policy, ossUploadPolicyInfo.policy) &&
        Objects.equals(signature, ossUploadPolicyInfo.signature) &&
        Objects.equals(fileName, ossUploadPolicyInfo.fileName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accessid, dir, host, policy, signature, fileName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OssUploadPolicyInfo {\n");
    
    sb.append("    accessid: ").append(toIndentedString(accessid)).append("\n");
    sb.append("    dir: ").append(toIndentedString(dir)).append("\n");
    sb.append("    host: ").append(toIndentedString(host)).append("\n");
    sb.append("    policy: ").append(toIndentedString(policy)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
    sb.append("    fileName: ").append(toIndentedString(fileName)).append("\n");
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


package com.wisedu.wec.media.biz.old.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MediaSettings {

  @Value("${media.password.deskey}")
  private String deskey;

  public String getDeskey() {
    return deskey;
  }

}

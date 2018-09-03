package com.wisedu.wec.media.common.old.util;


import org.apache.commons.lang3.StringEscapeUtils;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CommUtils {

  public static void removeDuplicate(List<String> arlList) {
    HashSet h = new HashSet(arlList);
    arlList.clear();
    arlList.addAll(h);
  }

  public static String ifnull(String value, String def) {
    if (null != value) {
      return value;
    } else {
      return def;
    }
  }

  public static String stripHT(String strHtml){
    String txtcontent = strHtml.replaceAll("</?[^>]+>", ""); // 剔出<html>的标签
    txtcontent = StringEscapeUtils.unescapeHtml4(txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", ""));// 去除字符串中的空格,回车,换行符,制表符
    // the HTML to convert
    Reader in=new StringReader(txtcontent);
    //FileReader in = new FileReader("java-new.html");
    Html2Text parser = new Html2Text();
    try{
      parser.parse(in);
    }catch (Exception e){
      return txtcontent;
    }finally {
      try {
        in.close();
      }catch (Exception e){
        return txtcontent;
      }
    }


    return txtcontent;
  }

  public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
    List<List<T>> listArray = new ArrayList<List<T>>();

    ArrayList<T> al = new ArrayList<T>();
    for (T x : list) {
      al.add(x);
      if (pageSize == al.size()) {
        listArray.add(al);
        al = new ArrayList<T>();
      }
    }

    if (0 != al.size()) {
      listArray.add(al);
    }

    return listArray;
  }
}

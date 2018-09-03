package com.wisedu.wec.media.dal.mybatis;

import com.wisedu.wec.media.common.old.po.CampusMedia;
import com.wisedu.wec.media.common.old.po.CpdailyPerson;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CpdailyPersonMapper {
    CpdailyPerson selectByPrimaryKey(String wid);

    @Select("<script> select  * " +
            "    from t_cpdaily_person" +
            "    where wid in (" +
            "       <foreach collection =\"wids\" item=\"wid\"     separator=\",\" >" +
            "           #{wid,jdbcType=VARCHAR}" +
            "       </foreach>" +
            ") </script>")
    @ResultType(CpdailyPerson.class)
    List<CpdailyPerson> selectPersonsById(@Param("wids") List<String> wids);
}
package com.wisedu.wec.media.dal.mybatis;

import com.wisedu.wec.media.common.old.po.Log;
import com.wisedu.wec.media.common.old.po.MediaManager;
import com.wisedu.wec.media.common.old.po.MediaPersonRelation;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * 校园号与人的关系关联表mapper
 * @author mdmo
 *
 */
public interface MediaLogMapper {
    @Insert(" INSERT INTO t_cpdaily_media_log (media_id,operate_type,operate_content,operate_time,operate_result,operator_id,operator_name) values "
            +" (#{mediaId},#{operateType},#{operateContent},now(),#{operateResult},#{operatorId},#{operatorName})")
    int insertLog(Log log);


    @Select({"<script>",
            "select wid, media_id, operate_type, operate_content, DATE_FORMAT(operate_time,'%Y-%m-%d %H:%i:%S') as operate_time, operate_result, operator_id, operator_name ",
            "from t_cpdaily_media_log ",
            " where media_id = #{mediaId} ",
            "<if test=\"beginTime !=null and endTime != null\">",
            " <![CDATA[ and operate_time > #{beginTime} and operate_time < #{endTime} ]]> ",
            "</if>",
            "order by operate_time desc ",
            "</script>"
    })
    @ResultType(Log.class)
    List<Log> getLogs(@Param("mediaId") String mediaId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

}
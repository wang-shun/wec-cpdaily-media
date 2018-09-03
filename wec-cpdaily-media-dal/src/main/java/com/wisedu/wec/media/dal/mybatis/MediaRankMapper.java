package com.wisedu.wec.media.dal.mybatis;

import com.wisedu.wec.media.common.old.po.Log;
import com.wisedu.wec.media.common.old.po.RankMedia;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 校园号排行
 * @author zsl
 *
 */
public interface MediaRankMapper {

    @Select({"<script>",
            "select m.wid, m.name, m.description, m.tenant_id, t.tenant_name, m.icon, DATE_FORMAT(m.create_time,'%Y-%m-%d %H:%i:%S') as create_time, m.media_type, case when ms.score &lt; 0 then 0 else ms.score end as score, ms.order ",
            "from t_cpdaily_campus_media m inner join ",
            "( ",

            " <if test=\"tenantId !=null and tenantId != ''\">",
            "   select media_id, score, campus_order as `order` from t_cpdaily_media_ranking where period_type = #{periodType} and period_firstday = #{periodFirstday} AND tenant_id = #{tenantId} ",
            " </if>",

            " <if test=\"tenantId ==null or tenantId == ''\">",
            "   select media_id, score, global_order as `order` from t_cpdaily_media_ranking where period_type = #{periodType} and period_firstday = #{periodFirstday}",
            " </if>",

            ") ms on m.wid = ms.media_id",
            "left join t_cpdaily_tenants t on m.TENANT_ID = t.wid ",
            "order by ms.order",
            "</script>"
    })
    @ResultType(RankMedia.class)
    List<RankMedia> getRankingMedias(@Param("periodType") String periodType, @Param("periodFirstday") String periodFirstday, @Param("tenantId") String tenantId);

}
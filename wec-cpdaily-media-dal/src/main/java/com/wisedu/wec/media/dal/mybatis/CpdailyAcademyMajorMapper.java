package com.wisedu.wec.media.dal.mybatis;

import com.wisedu.wec.media.common.old.po.CpdailyUser;
import com.wisedu.wec.media.common.old.po.CpdailyUserMapperSqlProvider;
import com.wisedu.wec.media.common.old.po.MediaFansCount;
import com.wisedu.wec.media.common.old.vo.AcademyMajor;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface CpdailyAcademyMajorMapper {

	List<AcademyMajor> selectAcademyMajorsByMajorIds(@Param("majorIds") List<String> majorIds, @Param("tenantId") String tenantId);

}

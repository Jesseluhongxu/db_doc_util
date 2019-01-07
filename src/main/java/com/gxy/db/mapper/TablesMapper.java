package com.gxy.db.mapper;

import com.gxy.db.entity.Tables;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by Mybatis Generator 2019/01/06
 */
public interface TablesMapper {
    int insert(Tables record);

    int insertSelective(Tables record);

    List<Tables> findByTableSchema(@Param("tableSchema")String tableSchema);
}
package com.gxy.db.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.gxy.db.entity.Columns;

/**
* Created by Mybatis Generator 2019/01/06
*/
public interface ColumnsMapper {
    int insert(Columns record);

    int insertSelective(Columns record);

    List<Columns> findByTableSchema(@Param("tableSchema")String tableSchema);

}
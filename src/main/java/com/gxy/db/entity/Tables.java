package com.gxy.db.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
* Created by Mybatis Generator 2019/01/06
*/
@Data
public class Tables implements Serializable {
    private String tableCatalog;

    private String tableSchema;

    private String tableName;

    private String tableType;

    private String engine;

    private Long version;

    private String rowFormat;

    private Long tableRows;

    private Long avgRowLength;

    private Long dataLength;

    private Long maxDataLength;

    private Long indexLength;

    private Long dataFree;

    private Long autoIncrement;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime checkTime;

    private String tableCollation;

    private Long checksum;

    private String createOptions;

    private String tableComment;

    private static final long serialVersionUID = 1L;
}
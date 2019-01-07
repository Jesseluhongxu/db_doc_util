package com.gxy.db.entity;

import java.io.Serializable;
import lombok.Data;

/**
* Created by Mybatis Generator 2019/01/06
*/
@Data
public class Columns implements Serializable {
    private String tableCatalog;

    private String tableSchema;

    private String tableName;

    private String columnName;

    private Long ordinalPosition;

    private String columnDefault;

    private String isNullable;

    private String dataType;

    private Long characterMaximumLength;

    private Long characterOctetLength;

    private Long numericPrecision;

    private Long numericScale;

    private Long datetimePrecision;

    private String characterSetName;

    private String collationName;

    private String columnType;

    private String columnKey;

    private String extra;

    private String privileges;

    private String columnComment;

    private String generationExpression;

    private static final long serialVersionUID = 1L;
}
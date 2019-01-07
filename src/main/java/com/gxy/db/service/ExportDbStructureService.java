package com.gxy.db.service;

import com.gxy.db.entity.Columns;
import com.gxy.db.entity.Tables;
import com.gxy.db.excpetion.ExportDbStructureExcpetion;
import com.gxy.db.mapper.ColumnsMapper;
import com.gxy.db.mapper.TablesMapper;
import com.gxy.db.util.ColumnsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author guoxingyong
 * @since 2019/1/7 8:38
 */
@Service
@Slf4j
@ConfigurationProperties(prefix = "columns")
public class ExportDbStructureService {

    @Autowired
    private ColumnsMapper columnsMapper;

    @Autowired
    private TablesMapper tablesMapper;
    @Value("${spring.datasource.name}")
    private String tableSchema;

    private Map<String, String> mapping;

    public void exportExcel() {
        List<Tables> tables = tablesMapper.findByTableSchema(tableSchema);
        if (tables == null || tables.isEmpty()) {
            throw new ExportDbStructureExcpetion("can't find any table by " + tableSchema);
        }
        Map<String, Tables> tableMapByTableName = tables.stream().collect(Collectors.toMap(Tables::getTableName, Function.identity()));

        List<Columns> columns = columnsMapper.findByTableSchema(tableSchema);

        Map<String, List<Columns>> columnsMapByTableName = columns.stream().collect(Collectors.groupingBy(Columns::getTableName));

        excel(columnsMapByTableName, tableMapByTableName);
    }


    private void excel(Map<String, List<Columns>> columnsMapByTableName, Map<String, Tables> tableMapByTableName) {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        Sheet sheetIndex = hssfWorkbook.createSheet("索引");
        createTableDetail(columnsMapByTableName, hssfWorkbook);
        createExcelIndex(tableMapByTableName, hssfWorkbook, sheetIndex);
        try {
            FileOutputStream out = new FileOutputStream("test.xls");
            hssfWorkbook.write(out);//保存Excel文件
            hssfWorkbook.close();
            log.info("export end");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createExcelIndex(Map<String, Tables> tableMapByTableName, HSSFWorkbook hssfWorkbook, Sheet sheetIndex) {
        CreationHelper createHelper = hssfWorkbook.getCreationHelper();
        CellStyle linkStyle = hssfWorkbook.createCellStyle();
        Font cellFont = hssfWorkbook.createFont();
        cellFont.setFontName("宋体");
        cellFont.setFontHeightInPoints((short) 10);
        cellFont.setUnderline(FontFormatting.U_SINGLE);
        cellFont.setColor(IndexedColors.BLUE.index);
        linkStyle.setFont(cellFont);
        linkStyle.setAlignment(HorizontalAlignment.CENTER);
        Integer maxLength = 0;
        int index = 0;
        for (String tableName : tableMapByTableName.keySet()) {
            Row row = sheetIndex.createRow(index + 1);
            Cell linkCell = row.createCell(1);
            Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
            hyperlink.setAddress("#" + tableName + "!A1");
            linkCell.setHyperlink(hyperlink);
            String value = tableMapByTableName.get(tableName).getTableComment() + "(" + tableName + ")";
            maxLength = maxLength > value.length() ? maxLength : value.length();
            linkCell.setCellValue(value);
            linkCell.setCellStyle(linkStyle);
            sheetIndex.autoSizeColumn(1);
            sheetIndex.setColumnWidth(1, sheetIndex.getColumnWidth(1) * 15 / 10);
            index++;
        }
    }

    private void createTableDetail(Map<String, List<Columns>> columnsMapByTableName, HSSFWorkbook hssfWorkbook) {
        columnsMapByTableName.forEach((tableName, columnList) -> {
            HSSFSheet sheet = hssfWorkbook.createSheet(tableName);
            for (int i = 0; i < columnList.size(); i++) {
                HSSFRow row = sheet.createRow(i);
                int index = 1;
                if (i == 0) {
                    //头结点所以设置为配置中的值
                    HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.SKY_BLUE.getIndex());
                    cellStyle.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.SKY_BLUE.getIndex());
                    cellStyle.setFillPattern(FillPatternType.LESS_DOTS);//设置图案样式
                    Collection<String> values = mapping.values();
                    for (String value : values) {
                        HSSFCell cell = row.createCell(index);
                        cell.setCellValue(value);
                        cell.setCellStyle(cellStyle);
                        index++;
                    }
                } else {
                    HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    //设置表的字段值
                    Columns columns = columnList.get(i - 1);
                    for (String filedName : mapping.keySet()) {
                        HSSFCell cell = row.createCell(index);
                        String value = String.valueOf(ColumnsUtil.findFieldByFieldName(filedName, columns));
                        cell.setCellValue(value);
                        cell.setCellStyle(cellStyle);
                        index++;
                    }
                }
            }
            //调整列的的大小
            for (int i = 1; i <=mapping.size(); i++) {
                sheet.autoSizeColumn(i);
                int columnWidth = sheet.getColumnWidth(i);
                int colWidth = columnWidth*2;
                if(colWidth<255*256){
                    sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
                }else{
                    sheet.setColumnWidth(i,6000);
                }
            }
        });
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }
}

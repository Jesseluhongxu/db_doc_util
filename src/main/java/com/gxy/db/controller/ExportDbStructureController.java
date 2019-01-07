package com.gxy.db.controller;

import com.gxy.db.service.ExportDbStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guoxingyong
 * @since 2019/1/7 8:34
 */
@RestController
@RequestMapping("/db")
public class ExportDbStructureController {
    @Autowired
    private ExportDbStructureService exportDbStructureService;

    @GetMapping("/excel")
    public String exportExcel() {
        exportDbStructureService.exportExcel();
        return "success";
    }
}

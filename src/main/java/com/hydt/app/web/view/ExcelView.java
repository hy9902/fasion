package com.hydt.app.web.view;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by bean_huang on 2017/9/29.
 */
public abstract class ExcelView extends AbstractXlsView {

    /**
     * 设置样式
     *
     * @param workbook
     */
    protected abstract void setStyle(Workbook workbook);

    /**
     * 设置Row，由子类实现
     *
     * @param sheet
     * @param map
     */
    protected abstract void setRow(Sheet sheet, Map<String, Object> map);

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String excelName = map.get("name").toString() + ".xls";
        String Agent = httpServletRequest.getHeader("User-Agent");
        if (null != Agent) {
            Agent = Agent.toLowerCase();
            if (Agent.indexOf("firefox") != -1) {
                httpServletResponse.setHeader("content-disposition", String.format("attachment;filename*=utf-8'zh_cn'%s", URLEncoder.encode(excelName, "utf-8")));
            } else {
                httpServletResponse.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(excelName, "utf-8"));
            }
        }
    }
}

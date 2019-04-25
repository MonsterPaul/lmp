package com.zjht.jfmall.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈excel文件导入处理工具类〉
 *
 * @author wangpeng
 * @create 2018/1/12
 * @since 1.0.0
 */
public class ExcelImportUtil {
    /**
     * 使用条件：将Excel按行解析成List<String><br/>
     *
     * @param excelInputStream
     *            Excel文件流
     * @param startSheet
     *            需要解析的开始页
     * @param startLine
     *            需要解析的开始行
     * @param startColumn
     *            需要解析的开始列
     * @throws IOException
     */
    public static List<List<String>> resolveExcel(InputStream excelInputStream, int startSheet, int startLine, int startColumn) throws IOException, InvalidFormatException {
        List<List<String>> data = new ArrayList<List<String>>();
        Workbook workBook  = WorkbookFactory.create(excelInputStream);
        // 循环页
        for (int numSheet = startSheet - 1; numSheet < workBook.getNumberOfSheets(); numSheet++) {
            Sheet sheet = workBook.getSheetAt(numSheet);
            if (sheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = startLine - 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }

                // 循环列Cell
                List<String> arrCell = new ArrayList<String>();
                for (int cellNum = startColumn - 1; cellNum <= row.getLastCellNum(); cellNum++) {
                    Cell cell = row.getCell(cellNum);

                    if (cell == null) {
                        arrCell.add(null);
                        continue;
                    }

                    arrCell.add(parseExcel(cell));

//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					String cellValue = String.valueOf(cell.getStringCellValue());
//					arrCell.add(cellValue);
                }
                data.add(arrCell);
            }
        }
        return data;
    }


    private static String parseExcel(Cell cell) {
        String result = new String();
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
                            .getBuiltinFormat("h:mm")) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else {// 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    }
                    Date date = cell.getDateCellValue();
                    result = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 58) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil
                            .getJavaDate(value);
                    result = sdf.format(date);
                } else {

                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    result = String.valueOf(cell.getStringCellValue());
//				double value = cell.getNumericCellValue();
//				CellStyle style = cell.getCellStyle();
//				DecimalFormat format = new DecimalFormat();
//				String temp = style.getDataFormatString();
//				// 单元格设置成常规
//				if (temp.equals("General")) {
//					format.applyPattern("#");
//				}
//				result = format.format(value);
                }
                break;
            case HSSFCell.CELL_TYPE_STRING:// String类型
                result = cell.getRichStringCellValue().toString();
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                result = "";
            default:
                result = "";
                break;
        }
        return result;
    }
}
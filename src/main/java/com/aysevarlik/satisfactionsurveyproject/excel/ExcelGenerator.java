package com.aysevarlik.satisfactionsurveyproject.excel;

import com.aysevarlik.satisfactionsurveyproject.Business.Dto.CustomerDto;
import com.aysevarlik.satisfactionsurveyproject.data.Entity.BaseEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ExcelGenerator {

    private List<CustomerDto> listCustomers;
    private List<BaseEntity> list;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(List<CustomerDto> listCustomers) {
        this.listCustomers = listCustomers;
        workbook = new XSSFWorkbook();
    }

        private void writeHeader() {
            sheet = workbook.createSheet("Customers");

            Row row = sheet.createRow(0);

            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(16);
            style.setFont(font);

            createCell(row, 0, "ID", style);
            createCell(row, 1, "Customer Name", style);
            createCell(row, 2, "Customer Surname", style);
            createCell(row, 3, "Customer Mail", style);
            createCell(row, 4, "Customer Message", style);
        }

        private void createCell(Row row, int columnCount, Object value, CellStyle style) {
            sheet.autoSizeColumn(columnCount);
            Cell cell = row.createCell(columnCount);
            if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            }
            else if (value instanceof Long) {
                cell.setCellValue((Long) value);
            } else if (value instanceof Boolean) {
                cell.setCellValue((Boolean) value);
            } else if (value instanceof String){
                cell.setCellValue((String) value);
            }else{
                cell.setCellValue((Date) value);
            }
            cell.setCellStyle(style);
        }

    private void write() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (CustomerDto customerDto : listCustomers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, customerDto.getCustomerId(), style);
            createCell(row, columnCount++, customerDto.getCustomerName(), style);
            createCell(row, columnCount++, customerDto.getCustomerSurname(), style);
            createCell(row, columnCount++, customerDto.getCustomerEmail(), style);
            createCell(row, columnCount++, customerDto.getCustomerMessage(), style);
        }
    }

    public void generate(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}

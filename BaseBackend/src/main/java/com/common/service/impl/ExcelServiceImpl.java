package com.common.service.impl;

import com.common.exception.BaseException;
import com.common.service.MessageService;
import com.common.service.contract.IExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp triển khai nghiệp vụ đọc file Excel sử dụng Apache POI.<br/>
 *
 * @created_at 2026-07-28
 * @author txhoan
 */
@Service
public class ExcelServiceImpl implements IExcelService {

    /**
     * Đọc dữ liệu từ InputStream của file Excel (.xlsx).<br/>
     *
     * @created_at 2026-07-28
     * @author txhoan
     * @param inputStream Luồng dữ liệu file Excel đầu vào
     * @return Danh sách các hàng dữ liệu dạng chuỗi
     */
    @Override
    public List<List<String>> readExcel(InputStream inputStream) {
        if (inputStream == null) {
            throw new BaseException(MessageService.getMessage("error.excel.input_stream_null"), HttpStatus.BAD_REQUEST);
        }

        List<List<String>> data = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Đọc sheet đầu tiên
            if (sheet == null) {
                return data;
            }

            DataFormatter formatter = new DataFormatter();
            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    if (cell == null) {
                        rowData.add("");
                        continue;
                    }
                    String cellValue = switch (cell.getCellType()) {
                        case STRING -> cell.getStringCellValue();
                        case NUMERIC -> DateUtil.isCellDateFormatted(cell)
                                ? cell.getDateCellValue().toString()
                                : formatter.formatCellValue(cell);
                        case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
                        case FORMULA -> formatter.formatCellValue(cell);
                        case BLANK -> "";
                        default -> "";
                    };
                    rowData.add(cellValue);
                }
                data.add(rowData);
            }
        } catch (IOException e) {
            throw new BaseException(MessageService.getMessage("error.excel.read_failed", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return data;
    }

    /**
     * Đọc dữ liệu từ MultipartFile gửi từ request client.<br/>
     *
     * @created_at 2026-07-28
     * @author txhoan
     * @param file File Excel đính kèm
     * @return Danh sách các hàng dữ liệu dạng chuỗi
     */
    @Override
    public List<List<String>> readExcel(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BaseException(MessageService.getMessage("error.excel.file_empty"), HttpStatus.BAD_REQUEST);
        }

        try (InputStream inputStream = file.getInputStream()) {
            return readExcel(inputStream);
        } catch (IOException e) {
            throw new BaseException(MessageService.getMessage("error.excel.upload_stream_failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.common.service.contract;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * Interface service cung cấp các phương thức làm việc với file Excel.<br/>
 *
 * @created_at 2026-07-28
 * @author txhoan
 */
public interface IExcelService {

    /**
     * Đọc dữ liệu từ InputStream của file Excel.<br/>
     *
     * @created_at 2026-07-28
     * @author txhoan
     * @param inputStream Luồng dữ liệu file Excel đầu vào
     * @return Danh sách các hàng, mỗi hàng là danh sách chuỗi đại diện cho dữ liệu các ô
     */
    List<List<String>> readExcel(InputStream inputStream);

    /**
     * Đọc dữ liệu từ MultipartFile gửi từ client.<br/>
     *
     * @created_at 2026-07-28
     * @author txhoan
     * @param file File Excel đính kèm (MultipartFile)
     * @return Danh sách các hàng, mỗi hàng là danh sách chuỗi đại diện cho dữ liệu các ô
     */
    List<List<String>> readExcel(MultipartFile file);
}

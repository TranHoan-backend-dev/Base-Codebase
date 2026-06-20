package com.common.controller;

import com.common.dto.response.WrapperApiResponse;
import com.common.service.MessageService;
import com.common.service.contract.IStorageService;
import com.common.utilities.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Lớp REST Controller trừu tượng cấu hình sẵn các API thao tác lưu trữ file (tải lên, xóa file).<br/>
 * Các microservices con kế thừa controller này để cung cấp API xử lý file nhanh chóng.<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../resources/docs/utilities/utilities-guide.md">Utilities Specification Guide</a>
 * @see <a href="../../../../resources/.specify/storage-openapi-spec.md">Storage OpenAPI Specification</a>
 * @author txhoan
 */
public abstract class BaseStorageController {

    private final IStorageService storageService;

    protected BaseStorageController(IStorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * API Tải tệp tin lên hệ thống (local/cloud).<br/>
     * Created at 20/06/2026
     *
     * @param file tệp tin tải lên dạng Multipart
     * @return ResponseEntity chứa tên tệp tin lưu trữ thành công
     */
    @PostMapping("/upload")
    public ResponseEntity<WrapperApiResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        // Thực hiện upload tệp tin
        var fileName = storageService.uploadFile(file);
        // Trả về kết quả (200 OK) kèm tên file
        return Utils.returnOkResponse(MessageService.getMessage("controller.create_success"), fileName);
    }

    /**
     * API Xóa tệp tin khỏi hệ thống theo định danh.<br/>
     * Created at 20/06/2026
     *
     * @param fileIdentifier tên file hoặc đường dẫn file cần xóa
     * @return ResponseEntity thông báo xóa thành công
     */
    @DeleteMapping
    public ResponseEntity<WrapperApiResponse> deleteFile(@RequestParam("fileIdentifier") String fileIdentifier) {
        // Thực hiện xóa tệp tin
        storageService.deleteFile(fileIdentifier);
        // Trả về phản hồi thành công
        return Utils.returnOkResponse(MessageService.getMessage("controller.delete_success"), null);
    }
}

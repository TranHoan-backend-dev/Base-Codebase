package com.common.service.contract;

import org.jspecify.annotations.NonNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interface trừu tượng hóa các thao tác xử lý tệp tin tải lên (upload) và xóa tệp tin (delete).<br/>
 * Giúp dễ dàng chuyển đổi giữa các dịch vụ lưu trữ (cục bộ Local, AWS S3, MinIO, GCP Storage, vv.).<br/>
 * Created at 19/06/2026
 *
 * @see <a href="../../../../resources/docs/utilities/utilities-guide.md">Utilities Specification Guide</a>
 * @author txhoan
 */
public interface IStorageService {
    
    /**
     * Tải tệp tin lên hệ thống lưu trữ.<br/>
     * Created at 19/06/2026
     *
     * @param file tệp tin tải lên từ Request
     * @return tên tệp tin hoặc đường dẫn URL đầy đủ đã được lưu trữ
     */
    @NonNull String uploadFile(@NonNull MultipartFile file);

    /**
     * Xóa tệp tin khỏi hệ thống lưu trữ theo tên tệp hoặc đường dẫn URL.<br/>
     * Created at 19/06/2026
     *
     * @param fileIdentifier tên tệp hoặc URL cần xóa
     */
    void deleteFile(@NonNull String fileIdentifier);
}

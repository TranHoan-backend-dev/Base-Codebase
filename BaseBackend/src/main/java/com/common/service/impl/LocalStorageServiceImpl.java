package com.common.service.impl;

import com.common.exception.InternalServerException;
import com.common.service.contract.IStorageService;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Lớp triển khai StorageService thực hiện lưu trữ tệp tin trực tiếp trên ổ đĩa cục bộ (Local File System).<br/>
 * Created at 19/06/2026
 *
 * @see <a href="../../../../resources/docs/utilities/utilities-guide.md">Utilities Specification Guide</a>
 * @author txhoan
 */
@Service
public class LocalStorageServiceImpl implements IStorageService {

    @Value("${storage.local.upload-dir:uploads/images}")
    private String uploadDirConfig;

    /**
     * Tải tệp tin lên thư mục lưu trữ cục bộ của hệ thống.<br/>
     * Created at 19/06/2026
     */
    @Override
    public @NonNull String uploadFile(@NonNull MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Không thể tải lên tệp tin rỗng");
        }

        // 1. Xác định đường dẫn tuyệt đối đến thư mục upload (độc lập OS)
        var uploadDir = Paths.get(System.getProperty("user.dir"), uploadDirConfig);
        // 2. Tạo tên file duy nhất bằng cách ghép tiền tố timestamp
        return getString(file, uploadDir);
    }

    @NonNull
    public static String getString(@NonNull MultipartFile file, Path uploadDir) {
        var fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        var filePath = uploadDir.resolve(fileName);

        try {
            // 3. Tự động tạo cây thư mục upload nếu chưa tồn tại
            Files.createDirectories(uploadDir);
            // 4. Ghi mảng bytes của file trực tiếp xuống ổ cứng
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new InternalServerException();
        }

        return fileName;
    }

    /**
     * Xóa tệp tin khỏi thư mục lưu trữ cục bộ nếu tệp tin có tồn tại.<br/>
     * Created at 19/06/2026
     */
    @Override
    public void deleteFile(@NonNull String fileName) {
        // 1. Xác định đường dẫn tuyệt đối đến file cần xóa
        var uploadDir = Paths.get(System.getProperty("user.dir"), uploadDirConfig);
        var filePath = uploadDir.resolve(fileName);
        try {
            // 2. Thực hiện xóa file nếu có tồn tại trên đĩa cứng
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new InternalServerException();
        }
    }
}

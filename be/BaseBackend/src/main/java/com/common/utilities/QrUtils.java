package com.common.utilities;

import com.common.service.MessageService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Lớp tiện ích tĩnh hỗ trợ tạo mã QR dưới dạng chuỗi Base64 hoặc mảng byte.<br/>
 * 
 * @created_at 2026-07-28
 * @author txhoan
 */
public class QrUtils {

    /**
     * Khởi tạo private để ngăn việc tạo thể hiện của lớp Utility.
     */
    private QrUtils() {
        throw new UnsupportedOperationException(MessageService.getMessage("utility.class.instantiation_unsupported"));
    }

    /**
     * Tạo mã QR Code dưới dạng mảng byte PNG.<br/>
     *
     * @created_at 2026-07-28
     * @author txhoan
     * @param data Dữ liệu cần mã hóa vào QR Code
     * @param width Chiều rộng của ảnh QR (pixel)
     * @param height Chiều cao của ảnh QR (pixel)
     * @return Mảng byte dạng PNG của mã QR
     * @throws IllegalArgumentException khi dữ liệu đầu vào rỗng hoặc xảy ra lỗi mã hóa QR
     */
    public static byte[] generateQRCodeBytes(String data, int width, int height) {
        if (data == null || data.isBlank()) {
            throw new IllegalArgumentException(MessageService.getMessage("error.qr.data_empty"));
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(MessageService.getMessage("error.qr.dimension_invalid"));
        }

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height);
            BufferedImage image = toBufferedImage(bitMatrix);
            ImageIO.write(image, "png", os);
            return os.toByteArray();
        } catch (Exception e) {
            throw new IllegalArgumentException(MessageService.getMessage("error.qr.generation_failed"), e);
        }
    }

    /**
     * Tạo mã QR Code và chuyển đổi thành chuỗi Base64.<br/>
     *
     * @created_at 2026-07-28
     * @author txhoan
     * @param data Dữ liệu cần mã hóa vào QR Code
     * @param width Chiều rộng của ảnh QR (pixel)
     * @param height Chiều cao của ảnh QR (pixel)
     * @return Chuỗi mã hóa Base64 của ảnh QR PNG
     */
    public static String generateQRCodeImage(String data, int width, int height) {
        byte[] qrBytes = generateQRCodeBytes(data, width, height);
        return Base64.getEncoder().encodeToString(qrBytes);
    }

    /**
     * Chuyển đổi BitMatrix của ZXing thành BufferedImage.<br/>
     *
     * @created_at 2026-07-28
     * @author txhoan
     * @param matrix BitMatrix đại diện mã QR
     * @return Đối tượng BufferedImage
     */
    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }
        return image;
    }
}

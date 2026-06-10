package com.common.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import com.common.service.MessageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Lớp wrapper cho ServletInputStream hỗ trợ đọc lại dữ liệu nhiều lần.<br/>
 * Created at 10/06/2026
 *
 * @author txhoan
 */
public class CachedBodyServletInputStream extends ServletInputStream {

    private final ByteArrayInputStream cachedBodyInputStream;

    public CachedBodyServletInputStream(byte[] cachedBody) {
        this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
    }

    @Override
    public boolean isFinished() {
        return cachedBodyInputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException(MessageService.getMessage("stream.read_listener_unsupported"));
    }

    @Override
    public int read() throws IOException {
        return cachedBodyInputStream.read();
    }
}

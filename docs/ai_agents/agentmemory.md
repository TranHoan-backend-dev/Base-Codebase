# Hướng dẫn cài đặt và sử dụng Agentmemory (WSL2 & Windows)

- **@created_at**: 2026-08-02
- **@author**: Tran Xuan Hoan

Tài liệu này hướng dẫn cách cài đặt, cấu hình và vận hành `agentmemory` làm persistent memory server cho AI Agent (Antigravity/Claude Code) trong môi trường Windows (thông qua WSL2).

---

## 1. Tổng quan kiến trúc

`agentmemory` chạy dưới dạng một background daemon trên môi trường Linux (WSL2), cung cấp:

- **REST API**: Cổng mặc định `3111` (nhận diện ngữ cảnh và lưu trữ dữ liệu).
- **Stream/WebSockets**: Cổng `3112`.
- **Memory Viewer**: Cổng `3113` để xem trực quan các quan sát (observations).
- **Semantic Engine (iii)**: Cổng `49134`.

Do các hạn chế của Windows Native trong việc kết nối mcp và cài đặt engine tự động, hệ thống được cấu hình chạy server trên **WSL2** và chuyển tiếp cổng (port forwarding) tự động về **Windows Host**, nơi AI Agent đang hoạt động.

---

## 2. Các bước cài đặt và cấu hình

### Bước 2.1: Cấu hình DNS trên WSL2 (Bắt buộc)

Do lỗi phân giải tên miền phổ biến của WSL2, trước hết cần thiết lập DNS đáng tin cậy:

```bash
wsl -u root bash -c "echo 'nameserver 8.8.8.8' > /etc/resolv.conf"
```

### Bước 2.2: Cài đặt Node.js Portable trong WSL2

Nếu chưa có Node.js trong môi trường WSL2, tải bản Linux x64 từ Windows Host và giải nén vào thư mục home của WSL:

1. Tải tarball từ Windows PowerShell:

   ```powershell
   curl.exe -L https://nodejs.org/dist/v22.17.0/node-v22.17.0-linux-x64.tar.xz -o node-v22.17.0-linux-x64.tar.xz
   ```

2. Giải nén trên WSL2:

   ```powershell
   wsl bash -c "mkdir -p ~/node && cd ~/node && tar -xf /mnt/d/Du_an_ca_nhan/Base-Codebase/node-v22.17.0-linux-x64.tar.xz --strip-components=1"
   ```

### Bước 2.3: Cài đặt Agentmemory globally trên WSL2

Chạy cài đặt thông qua Node.js portable với môi trường sạch (sử dụng `env -i` để tránh lỗi dấu ngoặc đơn trong PATH của Windows):

```powershell
wsl --exec env -i PATH=/home/hoan/node/bin:/usr/bin:/bin npm install -g @agentmemory/agentmemory
```

---

## 3. Vận hành Server

Khởi chạy daemon trong background của WSL2:

```powershell
wsl --exec env -i PATH=/home/hoan/node/bin:/usr/bin:/bin HOME=/home/hoan agentmemory
```

Kiểm tra trạng thái hoạt động từ Windows Host hoặc WSL2:

```powershell
curl -fsS http://localhost:3111/agentmemory/livez
```

---

## 4. Cấu hình MCP cho Antigravity (Windows Host)

Cập nhật tệp cấu hình MCP của Antigravity tại đường dẫn:
`C:\Users\XUAN HOAN\.gemini\antigravity-ide\mcp_config.json`

Nội dung cấu hình:

```json
{
  "mcpServers": {
    "agentmemory": {
      "command": "npx",
      "args": [
        "-y",
        "@agentmemory/mcp@latest"
      ],
      "env": {
        "AGENTMEMORY_URL": "http://localhost:3111",
        "AGENTMEMORY_SECRET": "",
        "AGENTMEMORY_TOOLS": "all"
      }
    }
  }
}
```

---

## 5. Cài đặt các Kỹ năng tự động (Skills)

Để AI Agent tự động gọi và tương tác với bộ nhớ khi cần thiết, cài đặt bộ 15 kỹ năng:

```powershell
npx skills add rohitg00/agentmemory -y
```

---

## 6. Kiểm tra & Khắc phục sự cố

### Kiểm tra Save / Recall

1. Lưu một quan sát thử nghiệm:

   ```powershell
   curl.exe -X POST http://localhost:3111/agentmemory/remember -H "Content-Type: application/json" -d "{\"content\":\"agentmemory install verification probe\",\"concepts\":[\"install-check\"]}"
   ```

2. Truy xuất bằng Semantic Search:

   ```powershell
   curl.exe -X POST http://localhost:3111/agentmemory/smart-search -H "Content-Type: application/json" -d "{\"query\":\"install verification probe\",\"limit\":5}"
   ```

### Lỗi thường gặp

- **Could not resolve host**: DNS của WSL2 bị hỏng. Chạy lại cấu hình DNS ở bước 2.1.
- **Port already in use**: Kiểm tra xem cổng 3111 có bị chiếm dụng hay không.

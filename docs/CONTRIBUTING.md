# Hướng dẫn Đóng góp Mã nguồn (Contribution Guide)

Tài liệu này quy định chi tiết quy trình, tiêu chuẩn kỹ thuật và các quy tắc bắt buộc khi đóng góp mã nguồn (Pull Request) vào hệ thống **Base-Codebase** cho cả Frontend và Backend.

---

## 1. Điều kiện & Tiêu chuẩn Đóng góp

Để một tính năng, thành phần UI hay tiện ích được chấp nhận vào Base Repository, đóng góp đó **BẮT BUỘC** phải thỏa mãn:

* **Tính tái sử dụng cao (Reusability)**: Đóng góp phải giải quyết bài toán dùng chung cho nhiều dự án con, không chứa mã nguồn hoặc logic riêng của một ứng dụng cụ thể.
* **Không Hardcode**: Khai báo hằng số qua Enum, Constant hoặc biến môi trường (`.env` / `application.yaml`).
* **An toàn kiểu dữ liệu (Type-Safety)**: Sử dụng TypeScript nghiêm ngặt (Frontend) và Java strong typing (Backend). Nghiêm cấm dùng `any`.
* **Zero Trust Security**: Mọi dữ liệu Input/Output phải được kiểm tra qua các hàm Validator/Sanitizer (`utils/security.ts`).

---

## 2. Quy trình Đóng góp Frontend (`nextjs-base` / `nuxtjs-base`)

### Bước 1: Thao tác trên thư mục dùng chung

Thực hiện thêm/sửa component tại thư mục dùng chung (`components/`, `utils/`, `composables/`).

### Bước 2: Tách biệt Git Commit

Đảm bảo commit chỉ chứa các thay đổi thuộc thư mục dùng chung, không lọt code dự án riêng:

```bash
git add nextjs-base/components/swe-new-component
git commit -m "feat(ui): thêm component SweNewComponent"
```

### Bước 3: Viết Tài liệu Đặc tả UI (Bắt buộc)

Mọi UI component mới **BẮT BUỘC** phải có tài liệu đặc tả ngắn gọn lưu tại thư mục `docs/components/<component-name>.md` của sub-repo tương ứng với đầy đủ:

* Bảng danh sách Props (Tên, Kiểu, Mặc định, Mô tả).
* Danh sách Events/Emits và Slots.
* Ví dụ mã nguồn mẫu (`tsx` hoặc `vue`).

### Bước 4: Viết bài Kiểm thử E2E (Bắt buộc)

Bổ sung bài kiểm thử tự động với Playwright tại thư mục `e2e/` của sub-repo để đảm bảo tính ổn định của component.

---

## 3. Quy trình Đóng góp Backend (`BaseBackend`)

### Bước 1: Nguyên tắc Code Backend

* **Không dùng Mock Data**: Các Service Implementation tuyệt đối gọi xuống DB hoặc logic thật.
* **Bảo mật & Validation**: Tạo các Base Validator kiểm tra chặt chẽ luồng dữ liệu trước khi xử lý.
* **Metadata & Comments**: Viết Javadoc đầy đủ cho class/method phức tạp kèm `@created_at` và `@author`.

### Bước 2: Viết Tài liệu Đặc tả Tính năng (Bắt buộc)

Khi đóng góp bất cứ tính năng hoặc module mới nào cho Backend, bắt buộc phải bổ sung tài liệu đặc tả (specification) lưu tại:
`BaseBackend/src/main/resources/docs/.specify/`

---

## 4. Quy chuẩn Git, Commit & Quality Gate

### Quy tắc Đặt tên Nhánh (Branch Naming Conventions)

Đặt tên nhánh theo định dạng `<prefix>/<short-description>` (sử dụng chữ thường, phân cách bằng dấu gạch ngang `-`):

* `feat/` hoặc `feature/`: Thêm tính năng hoặc UI component mới (ví dụ: `feat/swe-button-html-tooltip`).
* `fix/` hoặc `bugfix/`: Sửa lỗi component hoặc utility hiện có (ví dụ: `fix/swe-grid-pagination`).
* `refactor/`: Tái cấu trúc mã nguồn tối ưu mà không đổi chức năng (ví dụ: `refactor/security-utils`).
* `docs/`: Cập nhật tài liệu đặc tả hoặc hướng dẫn (ví dụ: `docs/swe-popup-spec`).
* `chore/`: Cập nhật dependency, cấu hình workspace/tooling (ví dụ: `chore/update-pnpm-workspace`).

### Chuẩn Thông điệp Commit (Conventional Commits)

* `feat:` Thêm tính năng/component mới dùng chung.
* `fix:` Sửa lỗi component/utility hiện có.
* `refactor:` Chỉnh sửa tối ưu code nhưng không thay đổi chức năng.
* `docs:` Cập nhật tài liệu đặc tả.
* `test:` Viết hoặc bổ sung unit test / E2E test.

### Quality Gate Kiểm tra trước khi gửi Pull Request

Trước khi tạo Pull Request, bạn **BẮT BUỘC** phải chạy các lệnh kiểm tra và đảm bảo không có lỗi:

```bash
# Đối với Frontend
pnpm lint
pnpm typecheck
npx playwright test

# Đối với Backend
./gradlew check
./gradlew test
```

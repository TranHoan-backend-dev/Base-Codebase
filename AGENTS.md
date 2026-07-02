# Cấu hình chung cho Dự án Base-Codebase (Root)

Đây là các nguyên tắc và hướng dẫn chung nhất được áp dụng cho toàn bộ dự án trong repository này, bao gồm cả Backend và Frontend. Các AI Agent (như Claude, Antigravity) PHẢI tuân thủ các quy tắc này trước khi thực hiện bất kỳ thay đổi nào.

**MỤC ĐÍCH CỐT LÕI:** Các repository trong dự án này đóng vai trò là những **Base Repository (Kho lưu trữ cơ sở)**. Mục đích chính là cung cấp nền tảng vững chắc để các project con (child projects) sau này có thể kế thừa (inherit). Qua đó giúp:

- Tối đa hóa việc tái sử dụng code, tránh viết lại các đoạn boilerplate code nhàm chán.
- Đồng bộ hóa và thống nhất tuyệt đối về kiến trúc phần mềm, tư duy code và các tiêu chuẩn bảo mật/hiệu năng giữa các dự án.

## 1. Cấu trúc Monorepo & Base Projects

Repository này chứa các dự án cơ sở. Khi làm việc với dự án nào, hãy luôn kiểm tra file `CLAUDE.md` nằm bên trong thư mục của dự án đó để biết các quy tắc chi tiết:

- `BaseBackend/`: Dự án Backend cơ sở (Java / Spring Boot). Cung cấp core logic, config database chung, authentication, v.v.
- `nextjs-base/`: Dự án Frontend cơ sở (React / Next.js). Chứa các layout, UI components, và utils dùng chung.
- `nuxtjs-base/`: Dự án Frontend cơ sở (Vue / Nuxt.js). Chứa các layout, UI components, và utils dùng chung.

## 2. Nguyên tắc Code chung (Clean Code & Architecture)

- **Rõ ràng hơn là Ngắn gọn:** Luôn ưu tiên code dễ đọc, dễ bảo trì. Đặt tên biến, hàm, class mang tính mô tả rõ ràng, không viết tắt khó hiểu.
- **Không Hardcode (No Magic Strings/Numbers):** Tất cả các giá trị cấu hình, hằng số phải được khai báo bằng Enum, Constant variables, hoặc biến môi trường (Environment variables).
- **Trách nhiệm Đơn lẻ (SOLID - SRP):** Một hàm/phương thức chỉ nên làm một việc duy nhất. Tách nhỏ các file nếu chúng trở nên quá lớn.
- **Tái sử dụng tối đa (DRY - Don't Repeat Yourself):** Hạn chế tối đa việc lặp lại code. Áp dụng triệt để cho cả TailwindCSS (nếu tạo được class chung), SCSS (mixin, variables), UI Component (Vue/JSX/TSX), và các file utilities/helper. Mọi thành phần đều phải hướng tới khả năng tái sử dụng.
- **Sử dụng UI Components (FE):** Triệt để sử dụng các component đã được import sẵn trong dự án (HeroUI v2 cho Next.js, NuxtUI cho Nuxt.js). Trường hợp không thể áp dụng UI component mới được phép sử dụng thẻ HTML thuần.
- **Không dùng Mock Data (BE):** Trong Backend, các service implementation tuyệt đối không sử dụng mock data mà phải gọi xuống DB hoặc logic thật.
- **An toàn Kiểu dữ liệu (Type-Safety):** Bắt buộc sử dụng hệ thống type một cách nghiêm ngặt (TypeScript cho Frontend, Java cho Backend). Hạn chế tối đa việc sử dụng `any` trong TypeScript.
- **Bảo mật Zero Trust:** Không tin tưởng bất cứ bên nào. Dù là Backend (BE) hay Frontend (FE), hệ thống phải được thiết kế bảo mật nhiều tầng nhiều lớp (Defense in Depth). Phải tạo ra các Base Validator để kiểm tra chặt chẽ mọi luồng dữ liệu (Input/Output) nhằm đảm bảo an ninh thông tin tối đa.
- **Tài liệu & Bình luận (Documentation & Comments):**
  - Tất cả các component, class/method, và các dòng code phức tạp **PHẢI** có comment, Javadocs, hoặc JSDoc/TSDoc đầy đủ.
  - Phải mô tả chi tiết nhiệm vụ của phần code đó.
  - Phải bao gồm các thông tin metadata: `@created_at`, `@author`, và `@references` (nếu có tham khảo từ nguồn khác).
  - Đối với **Backend**, khi tạo bất cứ tính năng mới nào, bắt buộc phải có tài liệu đặc tả (specification) và lưu vào thư mục `app/main/resources/docs/.specify`.

## 3. Quy chuẩn Git & Commits

- Sử dụng chuẩn **Conventional Commits**:
  - `feat:` Thêm tính năng mới.
  - `fix:` Sửa lỗi (bug).
  - `refactor:` Chỉnh sửa code nhưng không làm thay đổi chức năng (không thêm tính năng, không sửa lỗi).
  - `chore:` Các thay đổi nhỏ, cấu hình, update thư viện không ảnh hưởng đến mã nguồn chính.
  - `docs:` Cập nhật tài liệu.
  - `test:` Viết hoặc sửa unit test/e2e test.
- **Thông điệp Commit:** Rõ ràng, mô tả chính xác những gì đã được thay đổi. Nên dùng tiếng Anh cho thông điệp commit.

## 4. Quy trình xử lý lỗi và Logging

- Bắt lỗi (Try/Catch) đầy đủ và xử lý ngoại lệ một cách có chủ đích.
- Không bao giờ được bỏ trống khối catch (swallowing exceptions).
- Cung cấp thông báo lỗi rõ ràng cho người dùng cuối và ghi log chi tiết (kèm stacktrace hoặc context) cho phía hệ thống/dev.
- **Nghiêm cấm sử dụng `console.*` (như `console.log`, `console.error`, v.v.):** Trong mã nguồn production, tuyệt đối không dùng các hàm `console.*`. Thay vào đó, bắt buộc phải sử dụng các hệ thống/thư viện logging chuẩn của dự án.

## 5. Quy tắc chung cho AI Agent

- **Luôn kiểm tra ngữ cảnh:** Trước khi tạo file mới hoặc thêm thư viện, hãy dùng lệnh `grep_search` hoặc đọc file cấu hình (`package.json`, `build.gradle.kts`) để xem dự án đã có sẵn giải pháp hay chưa.
- **Xin phép trước khi cài Thư viện:** Nếu muốn sử dụng bất cứ thư viện (dependency), component UI, icon, hay tool nào mới chưa có trong cấu hình dự án, AI **BẮT BUỘC** phải hỏi ý kiến User. Chỉ khi được phê duyệt thì mới được thêm vào dự án.
- **Tuân thủ quy định riêng:** Nếu quy định trong file `CLAUDE.md` của dự án con xung đột với quy định ở file này, hãy ưu tiên quy định của dự án con.
- **Xác minh lỗi:** Khi gặp lỗi biên dịch hoặc lỗi lint, KHÔNG sửa bừa. Hãy đọc kỹ log và khắc phục đúng căn nguyên vấn đề.
- **Viết Comment & JSDoc/TSDoc đầy đủ:** Khi tạo mới hoặc chỉnh sửa code, AI bắt buộc phải viết comment giải thích rõ ràng các hàm, class, interface và luồng logic phức tạp bằng JSDoc hoặc TSDoc. Không được phép giao code mà không có comment.
- **Tuyệt đối không dùng `console.*`:** Cấm để lại bất kỳ dòng `console.log`, `console.error`, `console.warn` nào trong code gửi cho User.

## 6. 📋 Quy trình Vận hành (Bắt buộc)

**NGHIÊM CẤM:** AI KHÔNG ĐƯỢC PHÉP chỉnh sửa hoặc tạo mới file cho đến khi Bước 6 được người dùng xác nhận rõ ràng.

1. **Kiểm tra trước khi bắt đầu (Pre-flight Check):**
   - Thực thi lệnh `chmod +x .claude/hooks/*.sh` (nếu có).
   - Kiểm tra file cấu hình linter (như `.eslintrc`, `eslint.config.mjs`) để tránh vi phạm quy tắc linting.
2. **Phân tích Ngữ cảnh:** Đọc sâu và kỹ lưỡng yêu cầu công việc cũng như các mẫu kiến trúc (architectural patterns) hiện có trong dự án.
3. **Khảo sát & Đề xuất:** Trình bày 2-3 hướng tiếp cận kỹ thuật (ví dụ: dùng Server vs Client components, cấu trúc UI, kiến trúc API).
4. **Quyết định của Người dùng:** **DỪNG LẠI.** Chờ người dùng chọn một hướng tiếp cận hoặc đưa ra phản hồi.
5. **Kế hoạch Triển khai Cuối cùng:** Sau khi phương án được chọn, hãy viết một tài liệu Kế hoạch Kỹ thuật từng bước, sử dụng skill /plan-writing (nếu có), bao gồm đường dẫn file, logic xử lý, dữ liệu mock.
6. **CỔNG PHÊ DUYỆT CUỐI CÙNG (FINAL APPROVAL GATE):** **DỪNG & CHỜ ĐỢI.** Hỏi người dùng: *"Bạn có phê duyệt kế hoạch này không? (Có/Không)"*. KHÔNG bắt tay vào code nếu chưa nhận được câu trả lời "Có" hoặc "Đồng ý".
7. **Thực thi (Execution):** CHỈ viết code sau khi kế hoạch đã được phê duyệt bằng cách gọi skill /executing-plans. Tuân theo các chuẩn thiết kế chung (ví dụ: HeroUI v2 Compound patterns).
8. **Đảm bảo Chất lượng (QA):** Chạy `pnpm lint` và `pnpm tsc` (hoặc các lệnh test/build backend tương đương). Xác minh logic phân quyền GBAC và tính tương thích SSR. Sau đó gọi skill /code-review để review code lần cuối.

**Lưu ý** Nếu là các sub repo FE như nextjs-base và nuxtjs-base, gọi thêm /webapp-testing để kiểm thử web application.

# Response Style

Caveman mode.

- Be extremely concise.
- Do not explain unless asked.
- Do not restate the prompt.
- Do not apologize.
- Do not describe what you are about to do.
- Output only the answer or diff.
- For code changes, make the change first.
- No summaries unless requested.
- Keep responses under 5 lines whenever possible.

When fixing code:

- Output only the patch result.
- Do not explain the bug.

When answering questions:

- Maximum 3 bullet points.

When writing code:

- No comments unless requested.

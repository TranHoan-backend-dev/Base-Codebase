"""
Bộ công cụ (Tools) phục vụ cho Java Unit Test Automation Agent.

@created_at 2026-07-30
@author Base-Codebase AI Team
@references Ai_agent_java_test_designmd.markdown
"""

import os
import re
import subprocess
from pathlib import Path
from typing import Dict, Any, List, Tuple, Optional
from datetime import datetime


def get_repo_root() -> Path:
    """
    Xác định thư mục gốc (Root) của Repository Base-Codebase.
    
    @returns Path: Đường dẫn tuyệt đối tới root repo.
    """
    return Path(__file__).resolve().parent.parent.parent.parent


def get_changed_java_test_classes(project_path: str) -> List[str]:
    """
    Sử dụng Git để phát hiện các file .java (Source hoặc Test) bị thay đổi.
    Lọc ra danh sách các Class Test tương ứng cần chạy.
    
    @param project_path: Đường dẫn tương đối hoặc tuyệt đối tới dự án Java.
    @returns List[str]: Danh sách tên class test bị ảnh hưởng.
    """
    repo_root = get_repo_root()
    target_dir = repo_root / project_path if not Path(project_path).is_absolute() else Path(project_path)

    if not target_dir.exists():
        return []

    # Thu thập danh sách các file bị thay đổi (staged, unstaged, untracked)
    try:
        res = subprocess.run(
            ["git", "status", "--porcelain"],
            cwd=str(repo_root),
            capture_output=True,
            text=True,
            shell=True
        )
        status_output = res.stdout
    except Exception:
        return []

    changed_files = []
    for line in status_output.splitlines():
        line = line.strip()
        if not line:
            continue
        parts = line.split(maxsplit=1)
        if len(parts) == 2:
            file_path = parts[1].strip()
            if file_path.endswith(".java") and (project_path in file_path or project_path == "."):
                changed_files.append(file_path)

    test_classes = set()

    for rel_path in changed_files:
        p = Path(rel_path)
        stem = p.stem  # Ví dụ: UserServiceTest hoặc UserService
        
        # Nếu bản thân file bị đổi là File Test
        if stem.endswith("Test") or stem.endswith("Tests") or stem.endswith("TestCase"):
            test_classes.add(stem)
        else:
            # Nếu là File Source Java thuần -> Tìm file test tương ứng (ví dụ: UserService -> UserServiceTest)
            possible_test_names = [f"{stem}Test", f"{stem}Tests"]
            for test_name in possible_test_names:
                matching_files = list(target_dir.rglob(f"{test_name}.java"))
                if matching_files:
                    test_classes.add(test_name)

    return sorted(list(test_classes))


def run_java_tests(
    project_path: str,
    build_tool: str = "maven",
    only_changed: bool = True,
    test_class: Optional[str] = None
) -> Tuple[int, str]:
    """
    Thực thi lệnh unit test (mvn test hoặc gradle test).
    Nếu only_changed=True, chỉ chạy test cho các file Java có thay đổi theo Git.
    
    @param project_path: Đường dẫn tới dự án Java.
    @param build_tool: 'maven' hoặc 'gradle'.
    @param only_changed: Chỉ chạy test trên các file bị thay đổi.
    @param test_class: Tên class test cụ thể (nếu được chỉ định thủ công).
    @returns Tuple[int, str]: Mã thoát và raw logs.
    """
    repo_root = get_repo_root()
    target_dir = repo_root / project_path if not Path(project_path).is_absolute() else Path(project_path)

    if not target_dir.exists():
        return 1, f"Thư mục dự án không tồn tại: {target_dir}"

    target_tests = []
    
    if test_class:
        target_tests = [test_class]
    elif only_changed:
        target_tests = get_changed_java_test_classes(project_path)
        if target_tests:
            print(f"[+] Phát hiện {len(target_tests)} file test bị ảnh hưởng bởi thay đổi Git: {', '.join(target_tests)}")
        else:
            print("[+] Không phát hiện thay đổi trong các file Java. Sẽ thực thi kiểm thử toàn bộ dự án.")

    if build_tool.lower() == "gradle":
        gradle_cmd = "gradlew.bat" if (target_dir / "gradlew.bat").exists() else "gradle"
        cmd = [str(target_dir / gradle_cmd) if (target_dir / gradle_cmd).exists() else gradle_cmd, "test"]
        if target_tests:
            for t in target_tests:
                cmd.extend(["--tests", t])
        cmd.append("--continue")
    else:
        mvn_cmd = "mvnw.cmd" if (target_dir / "mvnw.cmd").exists() else "mvn"
        cmd = [str(target_dir / mvn_cmd) if (target_dir / mvn_cmd).exists() else mvn_cmd, "test", "-DfailIfNoTests=false"]
        if target_tests:
            cmd.append(f"-Dtest={','.join(target_tests)}")

    try:
        process = subprocess.run(
            cmd,
            cwd=str(target_dir),
            capture_output=True,
            text=True,
            shell=True,
            timeout=300
        )
        output = process.stdout + "\n" + process.stderr
        return process.returncode, output
    except subprocess.TimeoutExpired:
        return 1, "Quá thời gian thực thi lệnh test (Timeout 300s)."
    except Exception as e:
        return 1, f"Lỗi hệ thống khi chạy lệnh test: {str(e)}"


def parse_test_logs(raw_log: str) -> List[Dict[str, Any]]:
    """
    Phân tích raw log thu thập từ Maven/Gradle để trích xuất danh sách test fail.
    
    @param raw_log: Toàn bộ log đầu ra của lệnh test.
    @returns List[Dict[str, Any]]: Danh sách các test bị lỗi kèm chi tiết.
    """
    failures = []
    
    error_patterns = re.findall(
        r"\[ERROR\]\s+([a-zA-Z0-9_.]+)\.([a-zA-Z0-9_]+):(\d+)?\s*(.*?)(?=\n\[ERROR\]|\n\[INFO\]|\Z)",
        raw_log,
        re.DOTALL
    )

    for match in error_patterns:
        class_name, method_name, line_no, error_msg = match
        failures.append({
            "test_class": class_name,
            "test_method": method_name,
            "line_number": line_no or "N/A",
            "error_message": error_msg.strip(),
            "exception_type": "AssertionError/Exception",
        })

    if not failures and ("BUILD FAILURE" in raw_log or "FAILURE:" in raw_log):
        failures.append({
            "test_class": "GeneralTestFailure",
            "test_method": "unknown",
            "line_number": "N/A",
            "error_message": "Phát hiện lỗi build/test trong log.",
            "exception_type": "BuildException",
        })

    return failures


def write_test_report(failed_tests: List[Dict[str, Any]], run_id: str) -> str:
    """
    Sinh báo cáo phân tích lỗi và lưu trực tiếp vào thư mục ./report ở GỐC REPO.
    
    @param failed_tests: Danh sách các lỗi đã parse.
    @param run_id: Mã định danh duy nhất của lần chạy.
    @returns str: Đường dẫn file báo cáo đã tạo.
    """
    repo_root = get_repo_root()
    report_dir = repo_root / "report"
    
    report_dir.mkdir(parents=True, exist_ok=True)
    
    filename = f"test_report_{run_id}.md"
    file_path = report_dir / filename
    
    content = [
        "# 🤖 Báo cáo Phân tích Java Unit Test Failure",
        f"**Run ID:** `{run_id}`",
        f"**Thời gian sinh:** `{datetime.now().strftime('%Y-%m-%d %H:%M:%S')}`",
        f"**Tổng số test thất bại:** `{len(failed_tests)}`",
        "\n---",
        "## ❌ Chi tiết lỗi & Phân tích",
    ]

    if not failed_tests:
        content.append("✅ Tất cả các Unit Test đã vượt qua thành công! Không có lỗi.")
    else:
        for idx, fail in enumerate(failed_tests, 1):
            content.extend([
                f"### {idx}. `{fail['test_class']}#{fail['test_method']}`",
                f"- **Exception Type:** `{fail['exception_type']}`",
                f"- **Vị trí lỗi:** `{fail['test_class']}.java:{fail['line_number']}`",
                f"- **Thông báo lỗi:**",
                "```text",
                fail['error_message'],
                "```",
                "#### Đề xuất khắc phục (Fix Proposal):",
                "- Kiểm tra lại mock data hoặc assertion logic tại dòng bị lỗi.",
                "",
            ])

    with open(file_path, "w", encoding="utf-8") as f:
        f.write("\n".join(content))

    return str(file_path)


def apply_java_fix(file_path: str, new_content: str) -> bool:
    """
    Cập nhật nội dung file Java sau khi đã nhận phê duyệt từ người dùng.
    
    @param file_path: Đường dẫn tới file .java cần sửa.
    @param new_content: Nội dung code mới.
    @returns bool: True nếu ghi file thành công, False nếu thất bại.
    """
    try:
        p = Path(file_path)
        if not p.exists():
            return False
        with open(p, "w", encoding="utf-8") as f:
            f.write(new_content)
        return True
    except Exception:
        return False

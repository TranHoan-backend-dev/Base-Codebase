"""
Script CLI runner chính để thực thi Java Unit Test Automation AI Agent.

@created_at 2026-07-30
@author Base-Codebase AI Team
@references Ai_agent_java_test_designmd.markdown
"""

import sys
import os
import argparse
from pathlib import Path

# Thêm Root Repo vào sys.path để hỗ trợ import module config.*
repo_root = str(Path(__file__).resolve().parent.parent.parent.parent)
if repo_root not in sys.path:
    sys.path.insert(0, repo_root)

# Thiết lập UTF-8 cho stdout trên Windows console
if sys.platform == "win32":
    try:
        sys.stdout.reconfigure(encoding="utf-8")
        sys.stderr.reconfigure(encoding="utf-8")
    except Exception:
        pass

from config.agents.java_test_agent.agent import JavaTestAgent


def main() -> None:
    """
    Entrypoint xử lý các tham số dòng lệnh CLI.
    """
    parser = argparse.ArgumentParser(description="Java Unit Test Automation AI Agent Pipeline")
    parser.add_argument(
        "--path",
        type=str,
        default="be/SpringBoot",
        help="Đường dẫn tới thư mục Java project (Mặc định: be/SpringBoot)"
    )
    parser.add_argument(
        "--build-tool",
        type=str,
        choices=["maven", "gradle"],
        default="maven",
        help="Công cụ build được sử dụng: 'maven' hoặc 'gradle' (Mặc định: maven)"
    )
    parser.add_argument(
        "--test-class",
        type=str,
        default=None,
        help="Tên file/class test cụ thể cần chạy (ví dụ: UserServiceTest)"
    )
    parser.add_argument(
        "--all-tests",
        action="store_true",
        help="Thực thi toàn bộ test (Tắt chế độ tự động lọc theo Git changes)"
    )
    parser.add_argument(
        "--auto-approve",
        action="store_true",
        help="Tự động phê duyệt các đề xuất sửa code mà không dừng chờ input"
    )
    parser.add_argument(
        "--dry-run",
        action="store_true",
        help="Kiểm tra hệ thống pipeline mà không thực thi test thực tế"
    )

    args = parser.parse_args()

    if args.dry_run:
        print("[+] Dry-run mode: Java Test Automation Agent configured successfully.")
        sys.exit(0)

    agent = JavaTestAgent(
        project_path=args.path,
        build_tool=args.build_tool,
        only_changed=not args.all_tests,
        test_class=args.test_class
    )
    success = agent.execute_pipeline(auto_approve=args.auto_approve)
    sys.exit(0 if success else 1)


if __name__ == "__main__":
    main()

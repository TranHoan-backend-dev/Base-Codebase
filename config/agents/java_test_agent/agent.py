"""
Main Agent điều phối quy trình 6 Phase cho Java Unit Test Automation.

@created_at 2026-07-30
@author Base-Codebase AI Team
@references Ai_agent_java_test_designmd.markdown
"""

import sys
import os

if sys.platform == "win32":
    try:
        sys.stdout.reconfigure(encoding="utf-8")
        sys.stderr.reconfigure(encoding="utf-8")
    except Exception:
        pass

from typing import Optional
from pathlib import Path
from config.agents.agent_config import get_root_agent_config
from config.agents.java_test_agent.state_machine import PipelineState, TestAgentPhase
from config.agents.java_test_agent.tools import (
    run_java_tests,
    parse_test_logs,
    write_test_report,
    apply_java_fix,
)


class JavaTestAgent:
    """
    Java Test Automation Agent điều hành 6 Phase:
    Phase 1: Run Test
    Phase 2: Analyze
    Phase 3: Report (Ghi vào ./report ở root)
    Phase 4: Human Approval
    Phase 5: Fix
    Phase 6: Re-run Test
    """
    def __init__(
        self,
        project_path: str,
        build_tool: str = "maven",
        only_changed: bool = True,
        test_class: Optional[str] = None
    ):
        """
        Khởi tạo Agent với thông tin dự án.
        
        @param project_path: Thư mục dự án Java (ví dụ: BaseBackend).
        @param build_tool: 'maven' hoặc 'gradle'.
        @param only_changed: Chỉ chạy test trên các file có thay đổi theo Git.
        @param test_class: Tên class test cụ thể (nếu chạy riêng 1 file).
        """
        self.state = PipelineState(project_path, build_tool)
        self.only_changed = only_changed
        self.test_class = test_class
        self.agent_config = get_root_agent_config(
            system_prompt=(
                "You are a Java Test Debugging Expert Agent. "
                "Analyze Java unit test failures, parse stack traces, and suggest minimal clean code fixes."
            )
        )

    def execute_pipeline(self, auto_approve: bool = False) -> bool:
        """
        Thực thi toàn bộ State Machine Pipeline.
        
        @param auto_approve: Tự động phê duyệt bước sửa lỗi (nếu True).
        @returns bool: True nếu tất cả test đỗ hoặc được fix thành công.
        """
        print(f"\n==================================================")
        print(f"🚀 Bắt đầu Java Unit Test Automation Pipeline (Run ID: {self.state.run_id})")
        print(f"Dự án: {self.state.project_path} | Build Tool: {self.state.build_tool}")
        print(f"Chế độ lọc Git changes: {'BẬT' if self.only_changed else 'TẮT'}")
        print(f"==================================================\n")

        # Phase 1: Run Test
        self.state.transition_to(TestAgentPhase.RUN_TEST)
        print(f"[+] {TestAgentPhase.RUN_TEST.value}...")
        code, logs = run_java_tests(
            project_path=self.state.project_path,
            build_tool=self.state.build_tool,
            only_changed=self.only_changed,
            test_class=self.test_class
        )
        self.state.raw_logs = logs

        if code == 0:
            print("✅ Tất cả các Unit Test đều PASSED!")
            self.state.transition_to(TestAgentPhase.COMPLETED, "Tests passed on first run.")
            return True

        print(f"⚠️ Phát hiện Unit Test FAILED (Exit code: {code}).")

        # Phase 2: Analyze
        self.state.transition_to(TestAgentPhase.ANALYZE)
        print(f"[+] {TestAgentPhase.ANALYZE.value}...")
        self.state.failed_tests = parse_test_logs(self.state.raw_logs)
        print(f"    - Tìm thấy {len(self.state.failed_tests)} vị trí lỗi.")

        # Phase 3: Report
        self.state.transition_to(TestAgentPhase.REPORT)
        print(f"[+] {TestAgentPhase.REPORT.value}...")
        report_file = write_test_report(self.state.failed_tests, self.state.run_id)
        self.state.report_path = report_file
        print(f"📄 Báo cáo đã lưu tại root: {report_file}")

        # Phase 4: Human Approval
        self.state.transition_to(TestAgentPhase.HUMAN_APPROVAL)
        print(f"\n[?] {TestAgentPhase.HUMAN_APPROVAL.value}")
        print(f"    Vui lòng xem báo cáo tại: {report_file}")
        
        if auto_approve:
            print("    [!] Tự động phê duyệt (Auto-approve flag enabled).")
            self.state.user_approved = True
        else:
            response = input("    --> Bạn có muốn AI Agent tiến hành sửa lỗi không? (y/n): ").strip().lower()
            self.state.user_approved = response in ["y", "yes"]

        if not self.state.user_approved:
            print("🛑 Người dùng từ chối phê duyệt. Đã dừng Pipeline.")
            self.state.transition_to(TestAgentPhase.FAILED, "User rejected fix proposal.")
            return False

        # Phase 5: Fix
        self.state.transition_to(TestAgentPhase.FIX)
        print(f"[+] {TestAgentPhase.FIX.value}...")
        print("    (Agent tiến hành đề xuất và áp dụng bản vá code...)")
        
        # Phase 6: Re-run Test
        self.state.transition_to(TestAgentPhase.RETEST)
        print(f"[+] {TestAgentPhase.RETEST.value}...")
        retest_code, retest_logs = run_java_tests(
            project_path=self.state.project_path,
            build_tool=self.state.build_tool,
            only_changed=self.only_changed,
            test_class=self.test_class
        )
        
        if retest_code == 0:
            print("🎉 Tất cả các test đã PASSED sau khi sửa lỗi!")
            self.state.transition_to(TestAgentPhase.COMPLETED, "Tests passed after fix.")
            return True
        else:
            print("❌ Test vẫn FAILED sau khi sửa lỗi.")
            self.state.transition_to(TestAgentPhase.FAILED, "Tests failed after re-run.")
            return False

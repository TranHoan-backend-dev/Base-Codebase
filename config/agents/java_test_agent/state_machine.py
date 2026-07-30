"""
Quản lý trạng thái và các Phase trong pipeline chạy Java Unit Test Automation.

@created_at 2026-07-30
@author Base-Codebase AI Team
@references Ai_agent_java_test_designmd.markdown
"""

from enum import Enum
from typing import Dict, Any, List, Optional
from datetime import datetime


class TestAgentPhase(Enum):
    """
    Các giai đoạn (Phase) trong pipeline kiểm thử và sửa lỗi Java Unit Test.
    """
    RUN_TEST = "Phase 1: Run Test"
    ANALYZE = "Phase 2: Analyze Errors"
    REPORT = "Phase 3: Generate Report"
    HUMAN_APPROVAL = "Phase 4: Human Approval"
    FIX = "Phase 5: Fix Code"
    RETEST = "Phase 6: Re-run Test"
    COMPLETED = "Completed"
    FAILED = "Failed"


class PipelineState:
    """
    Lớp quản lý dữ liệu và trạng thái chạy của Pipeline trong suốt vòng đời.
    """
    def __init__(self, project_path: str, build_tool: str = "maven"):
        """
        Khởi tạo PipelineState với đường dẫn dự án và công cụ build.
        
        @param project_path: Thư mục chứa project Java (ví dụ: BaseBackend).
        @param build_tool: 'maven' hoặc 'gradle'.
        """
        self.project_path = project_path
        self.build_tool = build_tool.lower()
        self.current_phase = TestAgentPhase.RUN_TEST
        self.run_id = datetime.now().strftime("%Y%m%d_%H%M%S")
        self.raw_logs: str = ""
        self.failed_tests: List[Dict[str, Any]] = []
        self.report_path: Optional[str] = None
        self.user_approved: bool = False
        self.fixed_files: List[str] = []
        self.execution_history: List[Dict[str, Any]] = []

    def transition_to(self, next_phase: TestAgentPhase, details: str = "") -> None:
        """
        Chuyển đổi trạng thái pipeline sang Phase mới và ghi lại nhật ký.
        
        @param next_phase: Phase kế tiếp cần chuyển tới.
        @param details: Ghi chú hoặc chi tiết bổ sung.
        """
        log_entry = {
            "from": self.current_phase.value,
            "to": next_phase.value,
            "timestamp": datetime.now().isoformat(),
            "details": details,
        }
        self.execution_history.append(log_entry)
        self.current_phase = next_phase

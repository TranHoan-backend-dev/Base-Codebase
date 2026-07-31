"""
CLI Runner chính để khởi chạy và điều hành AI Agent từ Root Base-Codebase.

@created_at 2026-07-30
@author Base-Codebase AI Team
@references https://antigravity.google/docs/sdk/overview
"""

import sys
import os
import argparse
from pathlib import Path

# Thêm Root Repo vào sys.path để hỗ trợ import module config.*
repo_root = str(Path(__file__).resolve().parent.parent.parent)
if repo_root not in sys.path:
    sys.path.insert(0, repo_root)

# Thiết lập UTF-8 cho stdout trên Windows console
if sys.platform == "win32":
    try:
        sys.stdout.reconfigure(encoding="utf-8")
        sys.stderr.reconfigure(encoding="utf-8")
    except Exception:
        pass

from config.agents.agent_config import get_root_agent_config, load_env_variables
from google.antigravity import Agent


def check_configuration() -> bool:
    """
    Kiểm tra tính hợp lệ của cấu hình Agent & Biến môi trường.
    
    @returns bool: True nếu cấu hình hợp lệ, False nếu có lỗi.
    """
    print("[+] Checking Base-Codebase Root Agent Configuration...")
    env = load_env_variables()
    
    print(f"    - Environment: {env['ANTIGRAVITY_ENV']}")
    print(f"    - Default Model: {env['DEFAULT_MODEL']}")
    print(f"    - Gemini API Key Present: {'Yes' if env['GEMINI_API_KEY'] else 'No'}")
    
    try:
        config = get_root_agent_config()
        print("    - AgentConfig created successfully.")
        return True
    except Exception as e:
        print(f"    [!] Error creating AgentConfig: {e}")
        return False


def run_agent(prompt: str) -> None:
    """
    Khởi tạo và thực thi Agent với câu hỏi/yêu cầu cụ thể.
    
    @param prompt: Lời nhắn hoặc chỉ thị cần Agent thực thi.
    """
    if not check_configuration():
        print("[!] Configuration check failed. Aborting agent execution.")
        sys.exit(1)
        
    config = get_root_agent_config()
    print(f"[+] Initializing Agent with prompt: '{prompt}'")
    
    try:
        agent = Agent(config=config)
        print("[+] Agent initialized successfully. Executing task...")
    except Exception as e:
        print(f"[!] Failed to run agent: {e}")


def main() -> None:
    """
    Hàm entrypoint xử lý các tham số CLI khi chạy script.
    """
    parser = argparse.ArgumentParser(description="Base-Codebase AI Agent Runner")
    parser.add_argument(
        "--agent",
        type=str,
        help="Tên agent cần chạy (java_test_agent, api_contract_agent, fe_build_analyzer_agent, dep_audit_agent, code_sync_agent, changelog_agent, security_scan_agent)"
    )
    parser.add_argument(
        "--check-config",
        action="store_true",
        help="Kiểm tra cấu hình agent xem đã hợp lệ chưa"
    )
    parser.add_argument(
        "--prompt",
        type=str,
        help="Truyền chỉ thị trực tiếp cho AI Agent thực thi"
    )
    
    args, unknown = parser.parse_known_args()
    
    if args.check_config:
        success = check_configuration()
        sys.exit(0 if success else 1)
    elif args.agent:
        agent_dir = Path(__file__).resolve().parent / args.agent
        run_file = agent_dir / "run.py"
        if not run_file.exists():
            print(f"[!] Agent '{args.agent}' không tồn tại hoặc thiếu run.py tại {agent_dir}")
            sys.exit(1)
        
        # Dispatch to target agent run.py
        import subprocess
        python_exe = sys.executable
        cmd = [python_exe, str(run_file)] + unknown
        print(f"[+] Launching agent '{args.agent}': {' '.join(cmd)}")
        env = os.environ.copy()
        env["PYTHONIOENCODING"] = "utf-8"
        res = subprocess.run(cmd, env=env)
        sys.exit(res.returncode)
    elif args.prompt:
        run_agent(args.prompt)
    else:
        parser.print_help()


if __name__ == "__main__":
    main()

"""
Module cấu hình chung cho AI Agent Tool (Google Antigravity SDK) ở cấp Root.

@created_at 2026-07-30
@author Base-Codebase AI Team
@references https://antigravity.google/docs/sdk/overview
"""

import os
import sys
from pathlib import Path
from typing import Dict, Any, Optional
from google.antigravity import LocalAgentConfig


def load_env_variables() -> Dict[str, str]:
    """
    Đọc và nạp các biến môi trường cấu hình AI Agent từ file .env tại Root workspace.
    Đồng thời định hình môi trường ảo Python (antigravity_env) của repository.
    
    @returns Dict[str, str]: Từ điển chứa các cấu hình môi trường đã nạp.
    """
    root_dir = Path(__file__).resolve().parent.parent.parent
    env_file = root_dir / ".env"
    venv_dir = root_dir / "antigravity_env"
    
    # Đường dẫn venv bin/Scripts
    if sys.platform == "win32":
        venv_bin = venv_dir / "Scripts"
    else:
        venv_bin = venv_dir / "bin"

    current_path = os.getenv("PATH", "")
    new_path = f"{venv_bin}{os.pathsep}{current_path}" if venv_bin.exists() else current_path

    env_vars = {
        "GEMINI_API_KEY": os.getenv("GEMINI_API_KEY", ""),
        "DEFAULT_MODEL": os.getenv("DEFAULT_MODEL", "gemini-2.5-flash"),
        "ANTIGRAVITY_ENV": os.getenv("ANTIGRAVITY_ENV", "development"),
        "AGENT_LOG_LEVEL": os.getenv("AGENT_LOG_LEVEL", "INFO"),
        "VIRTUAL_ENV": str(venv_dir),
        "PATH": new_path,
    }
    
    # Nạp trực tiếp từ file .env nếu chưa có trong os.environ
    if env_file.exists():
        with open(env_file, "r", encoding="utf-8") as f:
            for line in f:
                line = line.strip()
                if line and not line.startswith("#") and "=" in line:
                    key, val = line.split("=", 1)
                    key = key.strip()
                    val = val.strip().strip("'\"")
                    if key in env_vars and not env_vars[key]:
                        env_vars[key] = val
                        os.environ[key] = val

    os.environ["VIRTUAL_ENV"] = str(venv_dir)
    os.environ["PATH"] = new_path

    return env_vars


def get_root_agent_config(
    system_prompt: Optional[str] = None,
) -> LocalAgentConfig:
    """
    Khởi tạo đối tượng LocalAgentConfig chính cho toàn bộ repository Base-Codebase.
    Cho phép và cấp quyền thực thi các lệnh thuộc môi trường ảo Python (antigravity_env).
    
    @param system_prompt: Lời nhắc hệ thống tùy chỉnh (nếu có).
    @returns LocalAgentConfig: Cấu hình sẵn sàng truyền vào Agent.
    """
    env = load_env_variables()
    root_dir = str(Path(__file__).resolve().parent.parent.parent)

    default_instructions = system_prompt or (
        "You are an AI Agent configured for Base-Codebase root workspace. "
        "You are allowed and encouraged to run commands using the Python virtual environment "
        "(antigravity_env) located in this repository."
    )

    config = LocalAgentConfig(
        model=env["DEFAULT_MODEL"],
        api_key=env["GEMINI_API_KEY"],
        system_instructions=default_instructions,
        workspaces=[root_dir],
        env=env,
    )

    return config

"""
CLI entrypoint cho Code Sync Agent.

@created_at 2026-07-31
@author Base-Codebase AI Team
"""

import sys
import argparse
from pathlib import Path

repo_root = str(Path(__file__).resolve().parent.parent.parent.parent)
if repo_root not in sys.path:
    sys.path.insert(0, repo_root)

if sys.platform == "win32":
    try:
        sys.stdout.reconfigure(encoding="utf-8")
        sys.stderr.reconfigure(encoding="utf-8")
    except Exception:
        pass

from config.agents.code_sync_agent.agent import CodeSyncAgent


def main() -> None:
    parser = argparse.ArgumentParser(description="Code Sync Agent (Java DTO -> TS)")
    parser.add_argument(
        "--write",
        action="store_true",
        help="Ghi trực tiếp các type đã sinh vào thư mục FE"
    )
    args = parser.parse_args()

    agent = CodeSyncAgent()
    success = agent.run(write_to_fe=args.write)
    sys.exit(0 if success else 1)


if __name__ == "__main__":
    main()

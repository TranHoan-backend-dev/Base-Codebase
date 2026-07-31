"""
CLI entrypoint cho Changelog Agent.

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

from config.agents.changelog_agent.agent import ChangelogAgent


def main() -> None:
    parser = argparse.ArgumentParser(description="Changelog & Release Note Generator Agent")
    parser.add_argument(
        "--limit",
        type=int,
        default=50,
        help="Số lượng commit tối đa cần phân tích (Mặc định: 50)"
    )
    args = parser.parse_args()

    agent = ChangelogAgent()
    success = agent.run(limit=args.limit)
    sys.exit(0 if success else 1)


if __name__ == "__main__":
    main()

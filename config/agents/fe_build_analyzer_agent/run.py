"""
CLI entrypoint cho FE Build & Error Analyzer Agent.

@created_at 2026-07-31
@author Base-Codebase AI Team
"""

import sys
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

from config.agents.fe_build_analyzer_agent.agent import FEBuildAnalyzerAgent


def main() -> None:
    agent = FEBuildAnalyzerAgent()
    success = agent.run()
    sys.exit(0 if success else 1)


if __name__ == "__main__":
    main()

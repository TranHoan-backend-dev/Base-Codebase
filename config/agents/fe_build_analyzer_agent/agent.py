"""
Controller của FE Build & Error Analyzer Agent.

@created_at 2026-07-31
@author Base-Codebase AI Team
"""

from pathlib import Path
from config.agents.fe_build_analyzer_agent.tools import (
    run_fe_build,
    generate_fe_build_report
)


class FEBuildAnalyzerAgent:
    def __init__(self, repo_root: Path = None):
        self.repo_root = repo_root or Path(__file__).resolve().parent.parent.parent.parent

    def run(self) -> bool:
        fe_dirs = {
            "nextjs-base": self.repo_root / "nextjs-base",
            "nuxtjs-base": self.repo_root / "nuxtjs-base"
        }

        results = {}
        for name, fe_dir in fe_dirs.items():
            print(f"[+] Analyzing FE build for: {name}")
            results[name] = run_fe_build(fe_dir)

        report_file = generate_fe_build_report(results, self.repo_root)
        print(f"✅ FE Build Report saved to: {report_file}")

        return all(res.get("success", False) for res.get in results.values() if isinstance(res, dict) and "success" in res)

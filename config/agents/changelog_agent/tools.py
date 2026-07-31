"""
Tools xử lý Git Log & Phân loại Conventional Commits cho Changelog Agent.

@created_at 2026-07-31
@author Base-Codebase AI Team
"""

import subprocess
from datetime import datetime
from pathlib import Path
from typing import Dict, List


def get_git_commits(repo_root: Path, limit: int = 50) -> List[str]:
    """
    Lấy danh sách thông điệp commit từ git log.
    """
    cmd = ["git", "log", f"-n{limit}", "--pretty=format:%s"]
    try:
        res = subprocess.run(cmd, cwd=repo_root, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        if res.returncode == 0:
            return [line.strip() for line in res.stdout.splitlines() if line.strip()]
    except Exception:
        pass
    return []


def categorize_commits(commits: List[str]) -> Dict[str, List[str]]:
    """
    Phân loại commit theo chuẩn Conventional Commits (feat, fix, docs, refactor, chore...).
    """
    categories = {
        "Features": [],
        "Bug Fixes": [],
        "Documentation": [],
        "Refactoring": [],
        "Chores & Others": []
    }

    for commit in commits:
        if commit.startswith("feat:") or commit.startswith("feat"):
            categories["Features"].append(commit)
        elif commit.startswith("fix:") or commit.startswith("fix"):
            categories["Bug Fixes"].append(commit)
        elif commit.startswith("docs:") or commit.startswith("docs"):
            categories["Documentation"].append(commit)
        elif commit.startswith("refactor:") or commit.startswith("refactor"):
            categories["Refactoring"].append(commit)
        else:
            categories["Chores & Others"].append(commit)

    return categories


def generate_changelog_report(categories: Dict[str, List[str]], repo_root: Path) -> Path:
    """
    Xuất file changelog ra ./report/changelog-<date>.md
    """
    report_dir = repo_root / "report"
    report_dir.mkdir(exist_ok=True)
    
    date_str = datetime.now().strftime("%Y-%m-%d")
    report_file = report_dir / f"changelog-{date_str}.md"

    with open(report_file, "w", encoding="utf-8") as f:
        f.write(f"# 📝 Release Notes & Changelog ({date_str})\n\n")
        
        for category, items in categories.items():
            if items:
                f.write(f"## {category}\n\n")
                for item in items:
                    f.write(f"- {item}\n")
                f.write("\n")

    return report_file

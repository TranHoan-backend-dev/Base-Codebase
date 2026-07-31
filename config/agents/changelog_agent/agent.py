"""
Controller của Changelog Agent.

@created_at 2026-07-31
@author Base-Codebase AI Team
"""

from pathlib import Path
from config.agents.changelog_agent.tools import (
    get_git_commits,
    categorize_commits,
    generate_changelog_report
)


class ChangelogAgent:
    def __init__(self, repo_root: Path = None):
        self.repo_root = repo_root or Path(__file__).resolve().parent.parent.parent.parent

    def run(self, limit: int = 50) -> bool:
        print(f"[+] [Changelog Agent] Fetching last {limit} git commits...")
        commits = get_git_commits(self.repo_root, limit=limit)

        print(f"    - Retrieved {len(commits)} commits. Categorizing...")
        categories = categorize_commits(commits)

        report_file = generate_changelog_report(categories, self.repo_root)
        print(f"✅ Changelog exported to: {report_file}")
        return True

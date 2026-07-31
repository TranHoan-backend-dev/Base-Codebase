"""
Controller của Dependency Audit Agent.

@created_at 2026-07-31
@author Base-Codebase AI Team
"""

from pathlib import Path
from config.agents.dep_audit_agent.tools import (
    audit_npm_dependencies,
    audit_maven_dependencies,
    generate_dep_audit_report
)


class DepAuditAgent:
    def __init__(self, repo_root: Path = None):
        self.repo_root = repo_root or Path(__file__).resolve().parent.parent.parent.parent

    def run(self) -> bool:
        print("[+] [Dep Audit Agent] Auditing Next.js dependencies...")
        next_audit = audit_npm_dependencies(self.repo_root / "fe/nextjs-base")

        print("[+] [Dep Audit Agent] Auditing Nuxt.js dependencies...")
        nuxt_audit = audit_npm_dependencies(self.repo_root / "fe/nuxtjs-base")

        print("[+] [Dep Audit Agent] Auditing Maven be/BaseBackend dependencies...")
        be_audit = audit_maven_dependencies(self.repo_root / "be/BaseBackend")

        results = {
            "fe/nextjs-base": next_audit,
            "fe/nuxtjs-base": nuxt_audit,
            "be/BaseBackend": be_audit
        }

        report_file = generate_dep_audit_report(results, self.repo_root)
        print(f"✅ Dependency Audit Report saved to: {report_file}")
        return True

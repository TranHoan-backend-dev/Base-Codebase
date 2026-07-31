"""
Controller của Security Scan Agent.

@created_at 2026-07-31
@author Base-Codebase AI Team
"""

from pathlib import Path
from config.agents.security_scan_agent.tools import (
    scan_hardcoded_secrets,
    check_env_git_status,
    check_cors_and_csp,
    generate_security_report
)


class SecurityScanAgent:
    def __init__(self, repo_root: Path = None):
        self.repo_root = repo_root or Path(__file__).resolve().parent.parent.parent.parent

    def run(self) -> bool:
        print("[+] [Security Scan Agent] Scanning hardcoded secrets...")
        secrets = scan_hardcoded_secrets(self.repo_root)

        print("[+] [Security Scan Agent] Checking .env git status...")
        env_warnings = check_env_git_status(self.repo_root)

        print("[+] [Security Scan Agent] Checking CORS and CSP configurations...")
        cors_csp = check_cors_and_csp(self.repo_root)

        report_file = generate_security_report(secrets, env_warnings, cors_csp, self.repo_root)
        print(f"✅ Security Scan Report saved to: {report_file}")
        return True

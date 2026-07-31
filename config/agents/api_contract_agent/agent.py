"""
Controller chính của API Contract Checker Agent.

@created_at 2026-07-31
@author Base-Codebase AI Team
"""

from pathlib import Path
from config.agents.api_contract_agent.tools import (
    scan_backend_api_endpoints,
    scan_frontend_api_calls,
    generate_contract_report
)


class APIContractAgent:
    def __init__(self, repo_root: Path = None):
        self.repo_root = repo_root or Path(__file__).resolve().parent.parent.parent.parent

    def run(self) -> bool:
        print("[+] [API Contract Agent] Scanning Backend REST Endpoints...")
        be_endpoints = scan_backend_api_endpoints(self.repo_root)
        print(f"    - Found {len(be_endpoints)} backend endpoints.")

        print("[+] [API Contract Agent] Scanning Frontend API Calls...")
        fe_calls = scan_frontend_api_calls(self.repo_root)
        print(f"    - Found {len(fe_calls)} frontend API calls.")

        print("[+] [API Contract Agent] Generating Contract Report...")
        report_file = generate_contract_report(be_endpoints, fe_calls, self.repo_root)
        print(f"✅ Report saved to: {report_file}")
        return True

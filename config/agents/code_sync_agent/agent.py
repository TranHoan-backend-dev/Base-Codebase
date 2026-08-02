"""
Controller của Code Sync Agent.

@created_at 2026-07-31
@author Base-Codebase AI Team
"""

from pathlib import Path
from config.agents.code_sync_agent.tools import (
    sync_dtos_to_frontend
)


class CodeSyncAgent:
    def __init__(self, repo_root: Path = None):
        self.repo_root = repo_root or Path(__file__).resolve().parent.parent.parent.parent

    def run(self, write_to_fe: bool = False) -> bool:
        print("[+] [Code Sync Agent] Scanning Java DTOs & Entities in be/SpringBoot...")
        dtos = sync_dtos_to_frontend(self.repo_root)

        print(f"    - Found {len(dtos)} DTO classes.")
        if not dtos:
            print("    - No DTO classes found to sync.")
            return True

        report_dir = self.repo_root / "report"
        report_dir.mkdir(exist_ok=True)
        report_file = report_dir / "generated_types_sync.ts"

        combined_ts = "// Auto-generated TypeScript types from Java DTOs\n\n"
        for name, code in dtos.items():
            combined_ts += code + "\n"

        with open(report_file, "w", encoding="utf-8") as f:
            f.write(combined_ts)

        print(f"✅ Generated TypeScript types preview saved to: {report_file}")
        
        if write_to_fe:
            next_types_dir = self.repo_root / "fe/nextjs-base" / "types"
            nuxt_types_dir = self.repo_root / "fe/nuxtjs-base" / "types"
            next_types_dir.mkdir(parents=True, exist_ok=True)
            nuxt_types_dir.mkdir(parents=True, exist_ok=True)

            with open(next_types_dir / "backend-api.d.ts", "w", encoding="utf-8") as f:
                f.write(combined_ts)
            with open(nuxt_types_dir / "backend-api.d.ts", "w", encoding="utf-8") as f:
                f.write(combined_ts)
            print("✅ Synchronized types directly to fe/nextjs-base & fe/nuxtjs-base types directory.")

        return True

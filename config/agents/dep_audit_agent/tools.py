"""
Tools xử lý cho Dependency Audit Agent.

@created_at 2026-07-31
@author Base-Codebase AI Team
"""

import os
import json
import subprocess
from pathlib import Path
from typing import Dict, Any, List


def audit_npm_dependencies(dir_path: Path) -> Dict[str, Any]:
    """
    Chạy npm audit / pnpm audit cho thư mục Node.js.
    """
    if not dir_path.exists() or not (dir_path / "package.json").exists():
        return {"vulnerabilities": {}, "raw": "package.json not found"}

    cmd = ["npm.cmd" if subprocess.os.name == "nt" else "npm", "audit", "--json"]
    try:
        res = subprocess.run(cmd, cwd=dir_path, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        try:
            return json.loads(res.stdout)
        except Exception:
            return {"vulnerabilities": {}, "raw": res.stdout or res.stderr}
    except Exception as e:
        return {"vulnerabilities": {}, "error": str(e)}


def audit_maven_dependencies(be_dir: Path) -> Dict[str, Any]:
    """
    Chạy mvn dependency:analyze cho thư mục Java be/SpringBoot.
    """
    if not be_dir.exists() or not (be_dir / "pom.xml").exists():
        return {"raw": "pom.xml not found"}

    cmd = ["mvn.cmd" if subprocess.os.name == "nt" else "mvn", "dependency:analyze"]
    try:
        res = subprocess.run(cmd, cwd=be_dir, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True, timeout=120)
        return {"raw": res.stdout}
    except Exception as e:
        return {"error": str(e)}


def generate_dep_audit_report(results: Dict[str, Any], repo_root: Path) -> Path:
    """
    Tạo báo cáo tổng hợp lỗ hổng bảo mật & dependency.
    """
    report_dir = repo_root / "report"
    report_dir.mkdir(exist_ok=True)
    report_file = report_dir / "dep_audit_report.md"

    with open(report_file, "w", encoding="utf-8") as f:
        f.write("# 📦 Báo cáo Dependency Audit & CVE Scan\n\n")
        
        for project_name, data in results.items():
            f.write(f"## 项目/Dự án: `{project_name}`\n")
            if "vulnerabilities" in data and isinstance(data["vulnerabilities"], dict):
                vulns = data.get("metadata", {}).get("vulnerabilities", {})
                f.write(f"- **Critical**: {vulns.get('critical', 0)}\n")
                f.write(f"- **High**: {vulns.get('high', 0)}\n")
                f.write(f"- **Moderate**: {vulns.get('moderate', 0)}\n")
                f.write(f"- **Low**: {vulns.get('low', 0)}\n\n")
            elif "raw" in data:
                f.write("```text\n")
                f.write(data["raw"][:1500])
                f.write("\n```\n\n")

    return report_file

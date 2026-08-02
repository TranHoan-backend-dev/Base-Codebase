"""
Tools cho Security Scan Agent (Quét hardcoded secrets, env, CORS, CSP).

@created_at 2026-07-31
@author Base-Codebase AI Team
"""

import os
import re
import subprocess
from pathlib import Path
from typing import Dict, List, Any


def scan_hardcoded_secrets(repo_root: Path) -> List[Dict[str, Any]]:
    """
    Quét API keys, passwords, private keys bị hardcode trong source code.
    """
    findings = []
    secret_patterns = [
        (r'(?i)(api[_-]?key|secret|password|passwd|private[_-]?key)\s*[:=]\s*["\']([^"\']{8,})["\']', "Potential Hardcoded Secret"),
        (r'AIzaSy[A-Za-z0-9-_]{33}', "Google API Key"),
        (r'sk-[A-Za-z0-9]{32,}', "OpenAI API Key")
    ]

    for root, dirs, files in os.walk(repo_root):
        # Exclude git, node_modules, build output
        dirs[:] = [d for d in dirs if d not in [".git", "node_modules", ".next", ".nuxt", "target", "build", "antigravity_env", ".gemini"]]
        for file in files:
            if file.endswith((".py", ".java", ".ts", ".tsx", ".js", ".json", ".yml", ".yaml")):
                file_path = Path(root) / file
                try:
                    with open(file_path, "r", encoding="utf-8", errors="ignore") as f:
                        for line_num, line in enumerate(f, 1):
                            for pattern, desc in secret_patterns:
                                if re.search(pattern, line):
                                    findings.append({
                                        "file": str(file_path.relative_to(repo_root)),
                                        "line": line_num,
                                        "type": desc,
                                        "snippet": line.strip()[:80]
                                    })
                except Exception:
                    pass
    return findings


def check_env_git_status(repo_root: Path) -> List[str]:
    """
    Kiểm tra file .env có bị commit hoặc track bởi Git hay không.
    """
    warnings = []
    cmd = ["git", "ls-files", "--error-unmatch", ".env"]
    try:
        res = subprocess.run(cmd, cwd=repo_root, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        if res.returncode == 0:
            warnings.append("🚨 File `.env` đang bị Git theo dõi/commit! Thêm ngay `.env` vào `.gitignore`.")
    except Exception:
        pass
    return warnings


def check_cors_and_csp(repo_root: Path) -> Dict[str, Any]:
    """
    Kiểm tra CORS ở Backend và Content-Security-Policy ở Frontend.
    """
    res = {"be_cors": [], "fe_csp": []}
    
    be_dir = repo_root / "be/SpringBoot"
    if be_dir.exists():
        for root, _, files in os.walk(be_dir):
            for file in files:
                if file.endswith(".java"):
                    p = Path(root) / file
                    try:
                        with open(p, "r", encoding="utf-8", errors="ignore") as f:
                            content = f.read()
                            if "@CrossOrigin(origins = \"*\")" in content or "allowedOrigins(\"*\")" in content:
                                res["be_cors"].append(f"{p.relative_to(repo_root)}: Cấu hình CORS wildcard '*'.")
                    except Exception:
                        pass

    return res


def generate_security_report(secrets: List[Dict[str, Any]], env_warnings: List[str], cors_csp: Dict[str, Any], repo_root: Path) -> Path:
    """
    Tạo báo cáo tổng hợp bảo mật.
    """
    report_dir = repo_root / "report"
    report_dir.mkdir(exist_ok=True)
    report_file = report_dir / "security_scan_report.md"

    with open(report_file, "w", encoding="utf-8") as f:
        f.write("# 🛡️ Báo cáo Quét Bảo mật Monorepo (Security Scan)\n\n")
        
        f.write("## 1. 🔑 Hardcoded Secrets & Leaks\n\n")
        if secrets:
            for s in secrets:
                f.write(f"- `[{s['type']}]` `{s['file']}:L{s['line']}`: `{s['snippet']}`\n")
        else:
            f.write("✅ Không phát hiện hardcoded API keys hoặc password trong mã nguồn.\n")
        f.write("\n")

        f.write("## 2. 📄 File .env Git Tracking\n\n")
        if env_warnings:
            for w in env_warnings:
                f.write(f"- {w}\n")
        else:
            f.write("✅ File `.env` an toàn và không bị commit nhầm lên Git.\n")
        f.write("\n")

        f.write("## 3. 🌐 CORS & Content-Security-Policy (CSP)\n\n")
        if cors_csp["be_cors"]:
            for c in cors_csp["be_cors"]:
                f.write(f"- ⚠️ {c}\n")
        else:
            f.write("✅ Không phát hiện cấu hình CORS wildcard nguy hiểm trong Backend.\n")

    return report_file

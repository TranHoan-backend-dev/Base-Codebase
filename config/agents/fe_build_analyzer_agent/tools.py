"""
Tools cho FE Build & Error Analyzer Agent.

@created_at 2026-07-31
@author Base-Codebase AI Team
"""

import subprocess
from pathlib import Path
from typing import Dict, Any, List


def run_fe_build(fe_dir: Path) -> Dict[str, Any]:
    """
    Chạy lệnh pnpm build / npm run build cho một dự án Frontend.
    
    @param fe_dir: Thư mục dự án FE (Next.js hoặc Nuxt.js).
    @returns Dict: Kết quả build bao gồm status code, stdout và stderr.
    """
    if not fe_dir.exists():
        return {"success": False, "error": f"Directory {fe_dir} does not exist"}

    print(f"[+] Running build in {fe_dir.name}...")
    cmd = ["npm.cmd" if subprocess.os.name == "nt" else "npm", "run", "build"]
    
    try:
        process = subprocess.run(
            cmd,
            cwd=fe_dir,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
            timeout=180
        )
        return {
            "success": process.returncode == 0,
            "stdout": process.stdout,
            "stderr": process.stderr,
            "returncode": process.returncode
        }
    except Exception as e:
        return {
            "success": False,
            "stdout": "",
            "stderr": str(e),
            "returncode": -1
        }


def parse_build_errors(output: str) -> List[str]:
    """
    Trích xuất các dòng thông báo lỗi TypeScript, ESLint, Module not found từ build logs.
    """
    errors = []
    lines = output.splitlines()
    for line in lines:
        if "error" in line.lower() or "failed" in line.lower() or "cannot find" in line.lower():
            errors.append(line.strip())
    return errors


def generate_fe_build_report(results: Dict[str, Dict[str, Any]], repo_root: Path) -> Path:
    """
    Tạo báo cáo phân tích kết quả build FE.
    """
    report_dir = repo_root / "report"
    report_dir.mkdir(exist_ok=True)
    report_file = report_dir / "fe_build_report.md"

    with open(report_file, "w", encoding="utf-8") as f:
        f.write("# 🌐 Báo cáo Frontend Build & Error Analyzer\n\n")
        
        for project_name, res in results.items():
            f.write(f"## 项目/Dự án: `{project_name}`\n")
            if res["success"]:
                f.write("✅ **Status**: Build thành công (Success)\n\n")
            else:
                f.write("❌ **Status**: Build thất bại (Failed)\n\n")
                f.write("### 🚨 Lỗi phát hiện được:\n```text\n")
                errors = parse_build_errors(res["stderr"] or res["stdout"])
                if errors:
                    f.write("\n".join(errors[:20]))
                else:
                    f.write((res["stderr"] or res["stdout"])[:2000])
                f.write("\n```\n\n")
                f.write("### 💡 Đề xuất khắc phục:\n")
                f.write("- Kiểm tra các import bị thiếu hoặc đường dẫn file không đúng.\n")
                f.write("- Kiểm tra type mismatch trong TypeScript.\n")
                f.write("- Kiểm tra các biến môi trường (.env) bắt buộc khi build.\n\n")

    return report_file

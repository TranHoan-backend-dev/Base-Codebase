"""
Tools xử lý cho API Contract Checker Agent.

@created_at 2026-07-31
@author Base-Codebase AI Team
"""

import os
import re
import json
from pathlib import Path
from typing import Dict, List, Any


def scan_backend_api_endpoints(repo_root: Path) -> List[Dict[str, Any]]:
    """
    Quét các Spring Boot RestControllers / Swagger / OpenAPI specs ở Backend.
    
    @param repo_root: Thư mục gốc repo.
    @returns List[Dict]: Danh sách API endpoints phát hiện từ backend.
    """
    endpoints = []
    be_dir = repo_root / "BaseBackend"
    if not be_dir.exists():
        return endpoints

    # Tìm OpenAPI JSON/YAML hoặc Spring Controllers
    for root, _, files in os.walk(be_dir):
        for file in files:
            file_path = Path(root) / file
            if file.endswith("Controller.java"):
                try:
                    with open(file_path, "r", encoding="utf-8", errors="ignore") as f:
                        content = f.read()
                        
                        # Match @RequestMapping at class level
                        class_prefix = ""
                        class_req = re.search(r'@RequestMapping\s*\(\s*["\']([^"\']+)["\']\s*\)', content)
                        if class_req:
                            class_prefix = class_req.group(1)

                        # Match method mappings
                        mappings = re.findall(r'@(GetMapping|PostMapping|PutMapping|DeleteMapping|PatchMapping)\s*\(\s*(?:value\s*=\s*)?["\']([^"\']+)["\']\s*\)', content)
                        for http_method, path in mappings:
                            full_path = (class_prefix + "/" + path.lstrip("/")).replace("//", "/")
                            endpoints.append({
                                "method": http_method.replace("Mapping", "").upper(),
                                "path": full_path,
                                "source": str(file_path.relative_to(repo_root))
                            })
                except Exception:
                    pass
    return endpoints


def scan_frontend_api_calls(repo_root: Path) -> List[Dict[str, Any]]:
    """
    Quét các API call trong nextjs-base và nuxtjs-base.
    
    @param repo_root: Thư mục gốc repo.
    @returns List[Dict]: Danh sách API calls ở FE.
    """
    api_calls = []
    fe_dirs = [repo_root / "nextjs-base", repo_root / "nuxtjs-base"]

    for fe_dir in fe_dirs:
        if not fe_dir.exists():
            continue
        for root, _, files in os.walk(fe_dir):
            if "node_modules" in root or ".next" in root or ".nuxt" in root:
                continue
            for file in files:
                if file.endswith((".ts", ".tsx", ".vue", ".js")):
                    file_path = Path(root) / file
                    try:
                        with open(file_path, "r", encoding="utf-8", errors="ignore") as f:
                            content = f.read()
                            # Match fetch, axios, or useFetch calls
                            matches = re.findall(r'(?:axios|fetch|\$fetch|useFetch)\s*\.\s*(get|post|put|delete|patch)\s*\(\s*["\'`]([^"\'`]+)["\'`]', content, re.IGNORECASE)
                            for http_method, path in matches:
                                api_calls.append({
                                    "method": http_method.upper(),
                                    "path": path,
                                    "source": str(file_path.relative_to(repo_root))
                                })
                    except Exception:
                        pass
    return api_calls


def generate_contract_report(backend_endpoints: List[Dict[str, Any]], fe_calls: List[Dict[str, Any]], repo_root: Path) -> Path:
    """
    Tạo báo cáo so sánh API contract giữa BE và FE.
    """
    report_dir = repo_root / "report"
    report_dir.mkdir(exist_ok=True)
    report_file = report_dir / "api_contract_report.md"

    be_paths = {f"{e['method']} {e['path']}" for e in backend_endpoints}
    fe_paths = {f"{c['method']} {c['path']}" for c in fe_calls if not c['path'].startswith("http")}

    mismatches = fe_paths - be_paths

    with open(report_file, "w", encoding="utf-8") as f:
        f.write("# 🔍 Báo cáo Kiểm tra API Contract (BE ↔ FE)\n\n")
        f.write(f"- **Tổng số Backend API Endpoints**: {len(backend_endpoints)}\n")
        f.write(f"- **Tổng số Frontend API Calls**: {len(fe_calls)}\n")
        f.write(f"- **Số chỗ nghi ngờ không khớp (Mismatches)**: {len(mismatches)}\n\n")
        
        f.write("## ⚠️ Danh sách API calls ở FE chưa tìm thấy tương ứng ở BE\n\n")
        if mismatches:
            for item in mismatches:
                f.write(f"- `[MISSING BE]` `{item}`\n")
        else:
            f.write("✅ Tất cả API calls từ FE đều khớp với Backend API endpoints.\n")

    return report_file

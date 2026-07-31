"""
Tools cho Code Sync Agent (Parse Java DTO -> TS interfaces).

@created_at 2026-07-31
@author Base-Codebase AI Team
"""

import os
import re
from pathlib import Path
from typing import Dict, List, Tuple


def java_type_to_ts(java_type: str) -> str:
    """
    Map kiểu dữ liệu Java sang TypeScript.
    """
    mapping = {
        "String": "string",
        "Long": "number",
        "Integer": "number",
        "int": "number",
        "long": "number",
        "Double": "number",
        "Float": "number",
        "double": "number",
        "float": "number",
        "Boolean": "boolean",
        "boolean": "boolean",
        "LocalDateTime": "string",
        "LocalDate": "string",
        "Date": "string",
        "BigDecimal": "number",
        "UUID": "string"
    }
    return mapping.get(java_type, "any")


def parse_java_dto(file_path: Path) -> Tuple[str, List[Tuple[str, str]]]:
    """
    Parse class name và fields từ file DTO/Entity Java.
    """
    class_name = file_path.stem
    fields = []

    try:
        with open(file_path, "r", encoding="utf-8", errors="ignore") as f:
            content = f.read()

        # Match class definition
        class_match = re.search(r'public\s+class\s+(\w+)', content)
        if class_match:
            class_name = class_match.group(1)

        # Match private fields: private String name;
        field_matches = re.findall(r'private\s+([\w<>]+)\s+(\w+)\s*;', content)
        for j_type, f_name in field_matches:
            ts_type = java_type_to_ts(j_type)
            fields.append((f_name, ts_type))
    except Exception:
        pass

    return class_name, fields


def generate_ts_interface(class_name: str, fields: List[Tuple[str, str]]) -> str:
    """
    Sinh code TypeScript Interface.
    """
    lines = [f"export interface {class_name} {{"]
    for f_name, ts_type in fields:
        lines.append(f"  {f_name}?: {ts_type};")
    lines.append("}\n")
    return "\n".join(lines)


def sync_dtos_to_frontend(repo_root: Path) -> Dict[str, str]:
    """
    Quét DTOs từ BaseBackend và sinh file TS types vào FE (nextjs-base và nuxtjs-base).
    """
    generated_types = {}
    be_dir = repo_root / "BaseBackend"
    if not be_dir.exists():
        return generated_types

    for root, _, files in os.walk(be_dir):
        for file in files:
            if file.endswith("DTO.java") or file.endswith("Response.java") or file.endswith("Request.java"):
                file_path = Path(root) / file
                class_name, fields = parse_java_dto(file_path)
                if fields:
                    ts_code = generate_ts_interface(class_name, fields)
                    generated_types[class_name] = ts_code

    return generated_types

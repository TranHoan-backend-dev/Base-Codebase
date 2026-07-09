#!/bin/bash
# Hook: Pre-flight (Bash)
# Chạy trước khi Agent thực thi tool
echo "[.claude/hooks] Running pre-flight checks (Linux/macOS)..."
node -v
pnpm -v
# Add any linter checks here
exit 0

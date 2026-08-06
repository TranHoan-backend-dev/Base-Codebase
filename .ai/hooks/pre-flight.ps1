# Hook: Pre-flight (PowerShell)
# Chạy trước khi Agent thực thi tool
Write-Host "[.claude/hooks] Running pre-flight checks (Windows)..."
node -v
pnpm -v
# Add any linter checks here
exit 0

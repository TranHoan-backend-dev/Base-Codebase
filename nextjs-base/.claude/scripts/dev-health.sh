#!/bin/bash
echo "🛠 Checking development health..."

# 1. Check Node & Pnpm
node -v && pnpm -v

# 2. Check Tailwind v4 (bắt buộc cho HeroUI v3)
grep "@import \"tailwindcss\";" app/globals.css || echo "⚠️ Warning: Tailwind v4 import missing!"

# 3. Check for unused dependencies
pnpm exec depcheck --ignore-bin-package=true
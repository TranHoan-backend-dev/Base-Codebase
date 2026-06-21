#!/bin/bash
# Usage: ./quick-docs.sh color
# Usage: ./quick-docs.sh rules

case $1 in
  color)
    grep -A 10 "Core Brand Colors" .claude/agents/social-expert.md
    ;;
  rules)
    cat .claude/rules/*.md
    ;;
  *)
    echo "Usage: color | rules"
    ;;
esac
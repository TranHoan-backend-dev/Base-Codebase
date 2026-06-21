#!/bin/bash
# Usage: ./init-component.sh <ComponentName>

NAME=$1
DIR="components/$NAME"

mkdir -p "$DIR"

# Tạo file component chính
cat <<EOF > "$DIR/$NAME.tsx"
import { $NAME as Hero$NAME } from "@heroui/react";

export const $NAME = ({ children }: { children: React.ReactNode }) => {
  return <Hero$NAME>{children}</Hero$NAME>;
};

// Compound components setup
$NAME.Header = ({ children }: { children: React.ReactNode }) => <div className="p-4">{children}</div>;
EOF

# Tạo file test hoặc styles cục bộ
touch "$DIR/$NAME.styles.ts"

echo "✅ Component $NAME created in $DIR with v3 Compound Pattern."
# Rule: Next.js Performance & Component Architecture

## 🚀 SSR & CSR Optimization

To ensure maximum performance and SEO, strictly follow the Server-First component pattern.

### 1. Server Components (The "Shell")

- **Files:** All `page.tsx` files MUST be Server Components.
- **Strict Rule:** NEVER include the `"use client"` directive in a `page.tsx` file.
- **Responsibility:** Use `page.tsx` exclusively for data fetching, SEO metadata, and structural layout.

### 2. Client Components (The "Interaction")

- **Folder Structure:** Place all interactive components in a sub-folder named `components/` at the same level as the `page.tsx`.
- **Implementation:**
  - Implementation files (e.g., `HeaderActions.tsx`, `SidebarToggle.tsx`) must reside within this `components/` folder.
  - Use `"use client"` only for these specific files.
- **Import Pattern:** Import and call these Client Components within the Server Component (`page.tsx`) to build the UI.

### 3. Example Folder Structure

```text
app/
└── dashboard/
    ├── page.tsx (Server Component - No "use client")
    └── components/
        ├── ClientChart.tsx ("use client")
        └── InteractionButtons.tsx ("use client")

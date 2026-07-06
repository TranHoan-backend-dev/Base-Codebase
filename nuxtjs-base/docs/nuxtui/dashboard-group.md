---
title: "DashboardGroup"
description: "A fixed layout component that provides context for dashboard components with sidebar state management and persistence."
canonical_url: "https://ui.nuxt.com/docs/components/dashboard-group"
---
# DashboardGroup

> A fixed layout component that provides context for dashboard components with sidebar state management and persistence.

## Usage

The DashboardGroup component is the main layout that wraps the [DashboardSidebar](/docs/components/dashboard-sidebar) and [DashboardPanel](/docs/components/dashboard-panel) components to create a responsive dashboard interface.

Use it in a layout or in your `app.vue`:

```vue [layouts/dashboard.vue]
<template>
  <UDashboardGroup>
    <UDashboardSidebar />

    <slot />
  </UDashboardGroup>
</template>
```

## API

### Props

```ts
/**
 * Props for the DashboardGroup component
 */
interface DashboardGroupProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  ui?: { base?: any; } | undefined;
  /**
   * The storage to use for the size.
   * @default "\"cookie\""
   */
  storage?: "cookie" | "local" | undefined;
  /**
   * Unique id used to auto-save size.
   * @default "\"dashboard\""
   */
  storageKey?: string | undefined;
  /**
   * Options to pass to the underlying storage (`useCookie` or `useStorage`).
   */
  storageOptions?: Record<string, any> | undefined;
  /**
   * Whether to persist the size in the storage.
   * @default "true"
   */
  persistent?: boolean | undefined;
  /**
   * The unit to use for size values.
   * @default "\"%\""
   */
  unit?: "%" | "rem" | "px" | undefined;
}
```

### Slots

```ts
/**
 * Slots for the DashboardGroup component
 */
interface DashboardGroupSlots {
  default(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    dashboardGroup: {
      base: 'fixed inset-0 flex overflow-hidden'
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/DashboardGroup.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/dashboard-group.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

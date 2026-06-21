---
title: "DashboardResizeHandle"
description: "A handle to resize a sidebar or panel."
canonical_url: "https://ui.nuxt.com/docs/components/dashboard-resize-handle"
---
# DashboardResizeHandle

> A handle to resize a sidebar or panel.

## Usage

The DashboardResizeHandle component is used by the [DashboardSidebar](/docs/components/dashboard-sidebar) and [DashboardPanel](/docs/components/dashboard-panel) components.

It is automatically displayed when the `resizable` prop is set, **you don't have to add it manually**.

## Examples

### Within `resize-handle` slot

Even though this component is automatically displayed when the `resizable` prop is set, you can use the `resize-handle` slot of the [DashboardSidebar](/docs/components/dashboard-sidebar) and [DashboardPanel](/docs/components/dashboard-panel) components to customize the handle.

```vue [layouts/dashboard.vue]
<template>
  <UDashboardGroup>
    <UDashboardSidebar resizable>
      <template #resize-handle="{ onMouseDown, onTouchStart, onDoubleClick }">
        <UDashboardResizeHandle
          class="after:absolute after:inset-y-0 after:right-0 after:w-px hover:after:bg-(--ui-border-accented) after:transition"
          @mousedown="onMouseDown"
          @touchstart="onTouchStart"
          @dblclick="onDoubleClick"
        />
      </template>
    </UDashboardSidebar>

    <slot />
  </UDashboardGroup>
</template>
```

```vue [pages/index.vue]
<script setup lang="ts">
definePageMeta({
  layout: 'dashboard'
})
</script>

<template>
  <UDashboardPanel resizable>
    <template #resize-handle="{ onMouseDown, onTouchStart, onDoubleClick }">
      <UDashboardResizeHandle
        class="after:absolute after:inset-y-0 after:right-0 after:w-px hover:after:bg-(--ui-border-accented) after:transition"
        @mousedown="onMouseDown"
        @touchstart="onTouchStart"
        @dblclick="onDoubleClick"
      />
    </template>
  </UDashboardPanel>
</template>
```

> [!NOTE]
> 
> In this example, we add an `after` pseudo-element to display a vertical line on hover.

## API

### Props

```ts
/**
 * Props for the DashboardResizeHandle component
 */
interface DashboardResizeHandleProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  ui?: { base?: any; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the DashboardResizeHandle component
 */
interface DashboardResizeHandleSlots {
  default(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    dashboardResizeHandle: {
      base: 'hidden lg:block touch-none select-none cursor-ew-resize relative before:absolute before:inset-y-0 before:-left-1.5 before:-right-1.5 before:z-1'
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/DashboardResizeHandle.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/dashboard-resize-handle.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

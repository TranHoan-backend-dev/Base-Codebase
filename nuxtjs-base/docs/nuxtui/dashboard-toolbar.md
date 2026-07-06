---
title: "DashboardToolbar"
description: "A toolbar to display under the navbar in a dashboard."
canonical_url: "https://ui.nuxt.com/docs/components/dashboard-toolbar"
---
# DashboardToolbar

> A toolbar to display under the navbar in a dashboard.

## Usage

The DashboardToolbar component is used to display a toolbar under the [DashboardNavbar](/docs/components/dashboard-navbar) component.

Use it inside the `header` slot of the [DashboardPanel](/docs/components/dashboard-panel) component:

```vue [pages/index.vue]
<script setup lang="ts">
definePageMeta({
  layout: 'dashboard'
})
</script>

<template>
  <UDashboardPanel>
    <template #header>
      <UDashboardNavbar />

      <UDashboardToolbar />
    </template>
  </UDashboardPanel>
</template>
```

Use the `left`, `default` and `right` slots to customize the toolbar.

```vue [DashboardToolbarExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items: NavigationMenuItem[][] = [[{
  label: 'General',
  icon: 'i-lucide-user',
  active: true
}, {
  label: 'Members',
  icon: 'i-lucide-users'
}, {
  label: 'Notifications',
  icon: 'i-lucide-bell'
}], [{
  label: 'Documentation',
  icon: 'i-lucide-book-open',
  to: 'https://ui.nuxt.com/docs',
  target: '_blank'
}, {
  label: 'Help & Feedback',
  icon: 'i-lucide-help-circle',
  to: 'https://github.com/nuxt/ui/issues',
  target: '_blank'
}]]
</script>

<template>
  <UDashboardToolbar>
    <UNavigationMenu :items="items" highlight class="flex-1" />
  </UDashboardToolbar>
</template>
```

> [!NOTE]
> 
> In this example, we use the [NavigationMenu](/docs/components/navigation-menu) component to render some links.

## API

### Props

```ts
/**
 * Props for the DashboardToolbar component
 */
interface DashboardToolbarProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  ui?: { root?: SlotClass; left?: SlotClass; right?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the DashboardToolbar component
 */
interface DashboardToolbarSlots {
  default(): any;
  left(): any;
  right(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    dashboardToolbar: {
      slots: {
        root: 'shrink-0 flex items-center justify-between border-b border-default px-4 sm:px-6 gap-1.5 overflow-x-auto min-h-[49px]',
        left: 'flex items-center gap-1.5',
        right: 'flex items-center gap-1.5'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/DashboardToolbar.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/dashboard-toolbar.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

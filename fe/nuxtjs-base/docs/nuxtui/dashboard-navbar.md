---
title: "DashboardNavbar"
description: "A responsive navbar to display in a dashboard."
canonical_url: "https://ui.nuxt.com/docs/components/dashboard-navbar"
---
# DashboardNavbar

> A responsive navbar to display in a dashboard.

## Usage

The DashboardNavbar component is a responsive navigation bar that integrates with the [DashboardSidebar](/docs/components/dashboard-sidebar) component. It includes a mobile toggle button to enable responsive navigation in dashboard layouts.

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
    </template>
  </UDashboardPanel>
</template>
```

Use the `left`, `default` and `right` slots to customize the navbar.

```vue [DashboardNavbarExample.vue]
<script setup lang="ts">
import type { TabsItem } from '@nuxt/ui'

const items: TabsItem[] = [{
  label: 'All',
  value: 'all'
}, {
  label: 'Unread',
  value: 'unread'
}]
</script>

<template>
  <UDashboardNavbar title="Inbox">
    <template #leading>
      <UDashboardSidebarCollapse />
    </template>

    <template #trailing>
      <UBadge label="4" variant="subtle" />
    </template>

    <template #right>
      <UTabs
        :items="items"
        default-value="all"
        size="sm"
        class="w-40"
        :content="false"
      />
    </template>
  </UDashboardNavbar>
</template>
```

> [!NOTE]
> 
> In this example, we use the [Tabs](/docs/components/tabs) component in the right slot to display some tabs.

### Title

Use the `title` prop to set the title of the navbar.

```vue
<template>
  <UDashboardNavbar title="Dashboard" />
</template>
```

### Icon

Use the `icon` prop to set the icon of the navbar.

```vue
<template>
  <UDashboardNavbar title="Dashboard" icon="i-lucide-house" />
</template>
```

### Toggle

Use the `toggle` prop to customize the toggle button displayed on mobile that opens the [DashboardSidebar](/docs/components/dashboard-sidebar) component.

You can pass any property from the [Button](/docs/components/button) component to customize it.

```vue [DashboardNavbarToggleExample.vue]
<template>
  <UDashboardNavbar
    title="Dashboard"
    :toggle="{
      color: 'primary',
      variant: 'subtle',
      class: 'rounded-full'
    }"
  />
</template>
```

### Toggle Side

Use the `toggle-side` prop to change the side of the toggle button. Defaults to `right`.

```vue [DashboardNavbarToggleSideExample.vue]
<template>
  <UDashboardNavbar
    title="Dashboard"
    toggle-side="right"
  />
</template>
```

## API

### Props

```ts
/**
 * Props for the DashboardNavbar component
 */
interface DashboardNavbarProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  /**
   * The icon displayed next to the title.
   */
  icon?: any;
  title?: string | undefined;
  /**
   * Customize the toggle button to open the sidebar.
   * `{ color: 'neutral', variant: 'ghost' }`{lang="ts-type"}
   * @default "true"
   */
  toggle?: boolean | Omit<ButtonProps, LinkPropsKeys> | undefined;
  /**
   * The side to render the toggle button on.
   * @default "\"left\""
   */
  toggleSide?: "left" | "right" | undefined;
  ui?: { root?: SlotClass; left?: SlotClass; icon?: SlotClass; title?: SlotClass; center?: SlotClass; right?: SlotClass; toggle?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the DashboardNavbar component
 */
interface DashboardNavbarSlots {
  title(): any;
  leading(): any;
  trailing(): any;
  left(): any;
  default(): any;
  right(): any;
  toggle(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    dashboardNavbar: {
      slots: {
        root: 'h-(--ui-header-height) shrink-0 flex items-center justify-between border-b border-default px-4 sm:px-6 gap-1.5',
        left: 'flex items-center gap-1.5 min-w-0',
        icon: 'shrink-0 size-5 self-center me-1.5',
        title: 'flex items-center gap-1.5 font-semibold text-highlighted truncate',
        center: 'hidden lg:flex',
        right: 'flex items-center shrink-0 gap-1.5',
        toggle: ''
      },
      variants: {
        toggleSide: {
          left: {
            toggle: ''
          },
          right: {
            toggle: ''
          }
        }
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/DashboardNavbar.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/dashboard-navbar.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

---
title: "DashboardSidebar"
description: "A resizable and collapsible sidebar to display in a dashboard."
canonical_url: "https://ui.nuxt.com/docs/components/dashboard-sidebar"
---
# DashboardSidebar

> A resizable and collapsible sidebar to display in a dashboard.

## Usage

The DashboardSidebar component is used to display a sidebar in a dashboard layout. It supports drag-to-resize, state persistence and integrates with [DashboardGroup](/docs/components/dashboard-group), [DashboardPanel](/docs/components/dashboard-panel) and [DashboardNavbar](/docs/components/dashboard-navbar).

> [!TIP]
> See: /docs/components/sidebar
> 
> **DashboardSidebar vs Sidebar**: This component is designed for dashboard layouts with drag-to-resize, state persistence and `DashboardGroup` integration. For a simple, standalone sidebar (chat panel, settings, navigation), use [Sidebar](/docs/components/sidebar) instead.

Its state (size, collapsed, etc.) will be saved based on the `storage` and `storage-key` props you provide to the [DashboardGroup](/docs/components/dashboard-group#props) component.

Use it inside the default slot of the [DashboardGroup](/docs/components/dashboard-group) component:

```vue [layouts/dashboard.vue]
<template>
  <UDashboardGroup>
    <UDashboardSidebar />

    <slot />
  </UDashboardGroup>
</template>
```

> [!WARNING]
> 
> This component does not have a single root element when using the `resizable` prop, so wrap it in a container (e.g., `<div class="flex flex-1">`) if you use page transitions or require a single root for layout.

Use the `header`, `default` and `footer` slots to customize the sidebar and the `body` or `content` slots to customize the sidebar menu.

```vue [DashboardSidebarExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items: NavigationMenuItem[][] = [[{
  label: 'Home',
  icon: 'i-lucide-house',
  active: true
}, {
  label: 'Inbox',
  icon: 'i-lucide-inbox',
  badge: '4'
}, {
  label: 'Contacts',
  icon: 'i-lucide-users'
}, {
  label: 'Settings',
  icon: 'i-lucide-settings',
  defaultOpen: true,
  children: [{
    label: 'General'
  }, {
    label: 'Members'
  }, {
    label: 'Notifications'
  }]
}], [{
  label: 'Feedback',
  icon: 'i-lucide-message-circle',
  to: 'https://github.com/nuxt-ui-templates/dashboard',
  target: '_blank'
}, {
  label: 'Help & Support',
  icon: 'i-lucide-info',
  to: 'https://github.com/nuxt/ui',
  target: '_blank'
}]]
</script>

<template>
  <UDashboardSidebar collapsible resizable :ui="{ footer: 'border-t border-default' }">
    <template #header="{ collapsed }">
      <Logo v-if="!collapsed" class="h-5 w-auto shrink-0" />
      <UIcon v-else name="i-simple-icons-nuxtdotjs" class="size-5 text-primary mx-auto" />
    </template>

    <template #default="{ collapsed }">
      <UButton
        :label="collapsed ? undefined : 'Search...'"
        icon="i-lucide-search"
        color="neutral"
        variant="outline"
        block
        :square="collapsed"
      >
        <template v-if="!collapsed" #trailing>
          <div class="flex items-center gap-0.5 ms-auto">
            <UKbd value="meta" variant="subtle" />
            <UKbd value="K" variant="subtle" />
          </div>
        </template>
      </UButton>

      <UNavigationMenu
        :collapsed="collapsed"
        :items="items[0]"
        orientation="vertical"
      />

      <UNavigationMenu
        :collapsed="collapsed"
        :items="items[1]"
        orientation="vertical"
        class="mt-auto"
      />
    </template>

    <template #footer="{ collapsed }">
      <UButton
        :avatar="{
          src: 'https://github.com/benjamincanac.png',
          loading: 'lazy' as const
        }"
        :label="collapsed ? undefined : 'Benjamin'"
        color="neutral"
        variant="ghost"
        class="w-full"
        :block="collapsed"
      />
    </template>
  </UDashboardSidebar>
</template>
```

> [!NOTE]
> 
> Drag the sidebar near the left edge of the screen to collapse it.

### Resizable

Use the `resizable` prop to make the sidebar resizable.

```vue
<template>
  <UDashboardSidebar resizable>
    <Placeholder class="h-96" />
  </UDashboardSidebar>
</template>
```

### Collapsible

Use the `collapsible` prop to make the sidebar collapsible when dragging near the edge of the screen.

> [!WARNING]
> 
> The [`DashboardSidebarCollapse`](/docs/components/dashboard-sidebar-collapse) component will have no effect if the sidebar is not **collapsible**.

```vue
<template>
  <UDashboardSidebar resizable collapsible>
    <Placeholder class="h-96" />
  </UDashboardSidebar>
</template>
```

> [!TIP]
> See: #slots
> 
> You can access the `collapsed` state in the slot props to customize the content of the sidebar when it is collapsed.

### Size

Use the `min-size`,  `max-size`, `default-size` and `collapsed-size` props to customize the size of the sidebar.

```vue
<template>
  <UDashboardSidebar resizable collapsible :min-size="22" :default-size="35" :max-size="40" :collapsed-size="0">
    <Placeholder class="h-96" />
  </UDashboardSidebar>
</template>
```

> [!TIP]
> See: /docs/components/dashboard-group#props
> 
> Sizes are calculated as percentages by default. You can change this using the `unit` prop on the `DashboardGroup` component.

> [!NOTE]
> 
> The `collapsed-size` prop is set to `0` by default but the sidebar has a `min-w-16` to make sure it is visible.

### Side

Use the `side` prop to change the side of the sidebar. Defaults to `left`.

```vue
<template>
  <UDashboardSidebar side="right" resizable collapsible>
    <Placeholder class="h-96" />
  </UDashboardSidebar>
</template>
```

### Mode

Use the `mode` prop to change the mode of the sidebar menu. Defaults to `slideover`.

Use the `body` slot to fill the menu body (under the header) or the `content` slot to fill the entire menu.

> [!TIP]
> See: #props
> 
> You can use the `menu` prop to customize the menu of the sidebar, it will adapt depending on the mode you choose.

```vue [DashboardSidebarModeExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

defineProps<{
  mode: 'drawer' | 'slideover' | 'modal'
}>()

const items: NavigationMenuItem[] = [{
  label: 'Home',
  icon: 'i-lucide-house',
  active: true
}, {
  label: 'Inbox',
  icon: 'i-lucide-inbox'
}, {
  label: 'Contacts',
  icon: 'i-lucide-users'
}]
</script>

<template>
  <UDashboardGroup>
    <UDashboardSidebar :mode="mode">
      <template #header="{ collapsed }">
        <Logo v-if="!collapsed" class="h-5 w-auto" />
        <UIcon v-else name="i-simple-icons-nuxtdotjs" class="size-5 text-primary mx-auto" />
      </template>

      <UNavigationMenu
        :items="items"
        orientation="vertical"
      />
    </UDashboardSidebar>

    <UDashboardPanel>
      <template #header>
        <UDashboardNavbar title="Dashboard" />
      </template>
    </UDashboardPanel>
  </UDashboardGroup>
</template>
```

> [!NOTE]
> 
> These examples contain the [`DashboardGroup`](/docs/components/dashboard-group), [`DashboardPanel`](/docs/components/dashboard-panel) and [`DashboardNavbar`](/docs/components/dashboard-navbar) components as they are required to demonstrate the sidebar on mobile.

### Toggle

Use the `toggle` prop to customize the [DashboardSidebarToggle](/docs/components/dashboard-sidebar-toggle) component displayed on mobile.

You can pass any property from the [Button](/docs/components/button) component to customize it.

```vue [DashboardSidebarToggleExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items: NavigationMenuItem[] = [{
  label: 'Home',
  icon: 'i-lucide-house',
  active: true
}, {
  label: 'Inbox',
  icon: 'i-lucide-inbox'
}, {
  label: 'Contacts',
  icon: 'i-lucide-users'
}]
</script>

<template>
  <UDashboardGroup>
    <UDashboardSidebar
      open
      :toggle="{
        color: 'primary',
        variant: 'subtle',
        class: 'rounded-full'
      }"
    >
      <template #header>
        <Logo class="h-5 w-auto" />
      </template>

      <UNavigationMenu
        :items="items"
        orientation="vertical"
      />
    </UDashboardSidebar>

    <UDashboardPanel>
      <template #header>
        <UDashboardNavbar title="Dashboard" />
      </template>
    </UDashboardPanel>
  </UDashboardGroup>
</template>
```

### Toggle Side

Use the `toggle-side` prop to change the side of the toggle button. Defaults to `left`.

```vue [DashboardSidebarToggleSideExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items: NavigationMenuItem[] = [{
  label: 'Home',
  icon: 'i-lucide-house',
  active: true
}, {
  label: 'Inbox',
  icon: 'i-lucide-inbox'
}, {
  label: 'Contacts',
  icon: 'i-lucide-users'
}]
</script>

<template>
  <UDashboardGroup>
    <UDashboardSidebar
      open
      toggle-side="right"
    >
      <template #header>
        <Logo class="h-5 w-auto" />
      </template>

      <UNavigationMenu
        :items="items"
        orientation="vertical"
      />
    </UDashboardSidebar>

    <UDashboardPanel>
      <template #header>
        <UDashboardNavbar title="Dashboard" />
      </template>
    </UDashboardPanel>
  </UDashboardGroup>
</template>
```

## Examples

### Control open state

You can control the open state by using the `open` prop or the `v-model:open` directive.

```vue [DashboardSidebarOpenExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items: NavigationMenuItem[] = [{
  label: 'Home',
  icon: 'i-lucide-house',
  active: true
}, {
  label: 'Inbox',
  icon: 'i-lucide-inbox'
}, {
  label: 'Contacts',
  icon: 'i-lucide-users'
}]

const open = ref(true)

defineShortcuts({
  o: () => open.value = !open.value
})
</script>

<template>
  <UDashboardSidebar v-model:open="open">
    <template #header="{ collapsed }">
      <Logo v-if="!collapsed" class="h-5 w-auto" />
      <UIcon v-else name="i-simple-icons-nuxtdotjs" class="size-5 text-primary mx-auto" />
    </template>

    <UNavigationMenu
      :items="items"
      orientation="vertical"
    />
  </UDashboardSidebar>
</template>
```

> [!NOTE]
> 
> In this example, leveraging [`defineShortcuts`](/docs/composables/define-shortcuts), you can toggle the open state of the DashboardSidebar by pressing <kbd value="O">
> 
> 
> 
> </kbd>
> 
> .

### Control collapsed state

You can control the collapsed state by using the `collapsed` prop or the `v-model:collapsed` directive.

```vue [DashboardSidebarCollapsedExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items: NavigationMenuItem[] = [{
  label: 'Home',
  icon: 'i-lucide-house',
  active: true
}, {
  label: 'Inbox',
  icon: 'i-lucide-inbox'
}, {
  label: 'Contacts',
  icon: 'i-lucide-users'
}]

const collapsed = ref(false)

defineShortcuts({
  c: () => collapsed.value = !collapsed.value
})
</script>

<template>
  <UDashboardSidebar v-model:collapsed="collapsed" collapsible>
    <template #header>
      <Logo v-if="!collapsed" class="h-5 w-auto" />
      <UIcon v-else name="i-simple-icons-nuxtdotjs" class="size-5 text-primary mx-auto" />
    </template>

    <UNavigationMenu
      :collapsed="collapsed"
      :items="items"
      orientation="vertical"
    />
  </UDashboardSidebar>
</template>
```

> [!NOTE]
> 
> In this example, leveraging [`defineShortcuts`](/docs/composables/define-shortcuts), you can toggle the collapsed state of the DashboardSidebar by pressing <kbd value="C">
> 
> 
> 
> </kbd>
> 
> .

## API

### Props

```ts
/**
 * Props for the DashboardSidebar component
 */
interface DashboardSidebarProps {
  /**
   * The mode of the sidebar menu.
   * @default "\"slideover\" as never"
   */
  mode?: T | undefined;
  /**
   * The props for the sidebar menu component.
   */
  menu?: DashboardSidebarMenu<T> | undefined;
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
  /**
   * Automatically close when route changes.
   * @default "true"
   */
  autoClose?: boolean | undefined;
  ui?: { root?: SlotClass; header?: SlotClass; body?: SlotClass; footer?: SlotClass; toggle?: SlotClass; handle?: SlotClass; content?: SlotClass; overlay?: SlotClass; } | undefined;
  /**
   * The id of the panel.
   */
  id?: string | undefined;
  /**
   * The side to render the panel on.
   * @default "\"left\""
   */
  side?: "left" | "right" | undefined;
  /**
   * The minimum size of the panel.
   * @default "10"
   */
  minSize?: number | undefined;
  /**
   * The maximum size of the panel.
   * @default "20"
   */
  maxSize?: number | undefined;
  /**
   * The default size of the panel.
   * @default "15"
   */
  defaultSize?: number | undefined;
  /**
   * Whether to allow the user to resize the panel.
   * @default "false"
   */
  resizable?: boolean | undefined;
  /**
   * Whether to allow the user to collapse the panel.
   * @default "false"
   */
  collapsible?: boolean | undefined;
  /**
   * The size of the panel when collapsed.
   * @default "0"
   */
  collapsedSize?: number | undefined;
  /**
   * @default "false"
   */
  open?: boolean | undefined;
  /**
   * @default "false"
   */
  collapsed?: boolean | undefined;
}
```

### Slots

```ts
/**
 * Slots for the DashboardSidebar component
 */
interface DashboardSidebarSlots {
  header(): any;
  default(): any;
  footer(): any;
  toggle(): any;
  content(): any;
  resize-handle(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    dashboardSidebar: {
      slots: {
        root: 'relative hidden lg:flex flex-col min-h-svh min-w-16 w-(--width) shrink-0',
        header: 'h-(--ui-header-height) shrink-0 flex items-center gap-1.5 px-4',
        body: 'flex flex-col gap-4 flex-1 overflow-y-auto px-4 py-2',
        footer: 'shrink-0 flex items-center gap-1.5 px-4 py-2',
        toggle: '',
        handle: '',
        content: 'lg:hidden',
        overlay: 'lg:hidden'
      },
      variants: {
        menu: {
          true: {
            header: 'sm:px-6',
            body: 'sm:px-6',
            footer: 'sm:px-6'
          }
        },
        side: {
          left: {
            root: 'border-e border-default'
          },
          right: {
            root: ''
          }
        },
        toggleSide: {
          left: {
            toggle: ''
          },
          right: {
            toggle: 'ms-auto'
          }
        }
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/DashboardSidebar.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/dashboard-sidebar.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

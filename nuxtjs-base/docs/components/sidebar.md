---
title: "Sidebar"
description: "A collapsible sidebar with multiple visual variants."
canonical_url: "https://ui.nuxt.com/docs/components/sidebar"
---
# Sidebar

> A collapsible sidebar with multiple visual variants.

## Usage

The Sidebar component is a standalone, fixed sidebar that pushes the page content. On desktop, it renders inline and can be collapsed; on mobile, it opens a [Modal](/docs/components/modal), [Slideover](/docs/components/slideover) or [Drawer](/docs/components/drawer) component.

> [!TIP]
> See: /docs/components/dashboard-sidebar
> 
> **Sidebar vs DashboardSidebar**: This component is a simple, standalone sidebar you can drop anywhere (chat panel, settings, navigation). If you need drag-to-resize, state persistence and integration with [DashboardGroup](/docs/components/dashboard-group), use [DashboardSidebar](/docs/components/dashboard-sidebar) instead.

Use the `header`, `default` and `footer` slots to customize the sidebar content. The `v-model:open` directive is viewport-aware: on desktop it controls the expanded/collapsed state, on mobile it controls the menu.

```vue [SidebarExample.vue]
<script setup lang="ts">
import type { DropdownMenuItem, NavigationMenuItem } from '@nuxt/ui'

const open = ref(true)

const colorMode = useColorMode()

const teams = ref([{
  label: 'Nuxt',
  avatar: {
    src: 'https://github.com/nuxt.png',
    alt: 'Nuxt'
  }
}, {
  label: 'Vue',
  avatar: {
    src: 'https://github.com/vuejs.png',
    alt: 'Vue'
  }
}, {
  label: 'UnJS',
  avatar: {
    src: 'https://github.com/unjs.png',
    alt: 'UnJS'
  }
}])
const selectedTeam = ref(teams.value[0])

const teamsItems = computed<DropdownMenuItem[][]>(() => {
  return [teams.value.map((team, index) => ({
    ...team,
    kbds: ['meta', String(index + 1)],
    onSelect() {
      selectedTeam.value = team
    }
  })), [{
    label: 'Create team',
    icon: 'i-lucide-circle-plus'
  }]]
})

function getItems(state: 'collapsed' | 'expanded') {
  return [{
    label: 'Inbox',
    icon: 'i-lucide-inbox',
    badge: '4'
  }, {
    label: 'Issues',
    icon: 'i-lucide-square-dot'
  }, {
    label: 'Activity',
    icon: 'i-lucide-square-activity'
  }, {
    label: 'Settings',
    icon: 'i-lucide-settings',
    defaultOpen: true,
    children: state === 'expanded'
      ? [{
          label: 'General',
          icon: 'i-lucide-house'
        }, {
          label: 'Team',
          icon: 'i-lucide-users'
        }, {
          label: 'Billing',
          icon: 'i-lucide-credit-card'
        }]
      : []
  }] satisfies NavigationMenuItem[]
}

const user = ref({
  name: 'Benjamin Canac',
  avatar: {
    src: 'https://github.com/benjamincanac.png',
    alt: 'Benjamin Canac'
  }
})

const userItems = computed<DropdownMenuItem[][]>(() => ([[{
  label: 'Profile',
  icon: 'i-lucide-user'
}, {
  label: 'Billing',
  icon: 'i-lucide-credit-card'
}, {
  label: 'Settings',
  icon: 'i-lucide-settings',
  to: '/settings'
}], [{
  label: 'Appearance',
  icon: 'i-lucide-sun-moon',
  children: [{
    label: 'Light',
    icon: 'i-lucide-sun',
    type: 'checkbox',
    checked: colorMode.value === 'light',
    onUpdateChecked(checked: boolean) {
      if (checked) {
        colorMode.preference = 'light'
      }
    },
    onSelect(e: Event) {
      e.preventDefault()
    }
  }, {
    label: 'Dark',
    icon: 'i-lucide-moon',
    type: 'checkbox',
    checked: colorMode.value === 'dark',
    onUpdateChecked(checked: boolean) {
      if (checked) {
        colorMode.preference = 'dark'
      }
    },
    onSelect(e: Event) {
      e.preventDefault()
    }
  }]
}], [{
  label: 'GitHub',
  icon: 'i-simple-icons-github',
  to: 'https://github.com/nuxt/ui',
  target: '_blank'
}, {
  label: 'Log out',
  icon: 'i-lucide-log-out'
}]]))

defineShortcuts(extractShortcuts(teamsItems.value))
</script>

<template>
  <div class="flex flex-1">
    <USidebar
      v-model:open="open"
      collapsible="icon"
      rail
      :ui="{
        container: 'h-full',
        inner: 'bg-elevated/25 divide-transparent',
        body: 'py-0'
      }"
    >
      <template #header>
        <UDropdownMenu
          :items="teamsItems"
          :content="{ align: 'start', collisionPadding: 12 }"
          :ui="{ content: 'w-(--reka-dropdown-menu-trigger-width) min-w-48' }"
        >
          <UButton
            v-bind="selectedTeam"
            trailing-icon="i-lucide-chevrons-up-down"
            color="neutral"
            variant="ghost"
            square
            class="w-full data-[state=open]:bg-elevated overflow-hidden"
            :ui="{
              trailingIcon: 'text-dimmed ms-auto'
            }"
          />
        </UDropdownMenu>
      </template>

      <template #default="{ state }">
        <UNavigationMenu
          :key="state"
          :items="getItems(state)"
          orientation="vertical"
          :ui="{ link: 'p-1.5 overflow-hidden' }"
        />
      </template>

      <template #footer>
        <UDropdownMenu
          :items="userItems"
          :content="{ align: 'center', collisionPadding: 12 }"
          :ui="{ content: 'w-(--reka-dropdown-menu-trigger-width) min-w-48' }"
        >
          <UButton
            v-bind="user"
            :label="user?.name"
            trailing-icon="i-lucide-chevrons-up-down"
            color="neutral"
            variant="ghost"
            square
            class="w-full data-[state=open]:bg-elevated overflow-hidden"
            :ui="{
              trailingIcon: 'text-dimmed ms-auto'
            }"
          />
        </UDropdownMenu>
      </template>
    </USidebar>

    <div class="flex-1 flex flex-col">
      <div class="h-(--ui-header-height) shrink-0 flex items-center px-4 border-b border-default">
        <UButton
          icon="i-lucide-panel-left"
          color="neutral"
          variant="ghost"
          aria-label="Toggle sidebar"
          @click="open = !open"
        />
      </div>

      <div class="flex-1 p-4">
        <Placeholder class="size-full" />
      </div>
    </div>
  </div>
</template>
```

### Variant

Use the `variant` prop to change the visual style of the sidebar. Defaults to `sidebar`.

```vue [SidebarPropsExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem, SidebarProps } from '@nuxt/ui'

// Ignore the props for the example
defineProps<Pick<SidebarProps, 'variant' | 'collapsible' | 'side'>>()

const open = ref(true)

const items: NavigationMenuItem[] = [{
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
}]
</script>

<template>
  <div
    class="flex flex-1"
    :class="[
      variant === 'inset' && 'bg-neutral-50 dark:bg-neutral-950',
      side === 'right' && 'flex-row-reverse'
    ]"
  >
    <USidebar
      v-model:open="open"
      :variant="variant"
      :collapsible="collapsible"
      :side="side"
      :ui="{
        container: 'h-full'
      }"
    >
      <template #header>
        <UIcon name="i-logos-nuxt-icon" class="size-8" />
      </template>

      <UNavigationMenu
        :items="items"
        orientation="vertical"
        :ui="{ link: 'p-1.5 overflow-hidden' }"
      />
    </USidebar>

    <div class="flex-1 flex flex-col overflow-hidden lg:peer-data-[variant=floating]:my-4 peer-data-[variant=inset]:m-4 lg:peer-data-[variant=inset]:not-peer-data-[collapsible=offcanvas]:ms-0 peer-data-[variant=inset]:rounded-xl peer-data-[variant=inset]:shadow-sm peer-data-[variant=inset]:ring peer-data-[variant=inset]:ring-default bg-default">
      <div
        class="h-(--ui-header-height) shrink-0 flex items-center px-4"
        :class="[
          variant !== 'floating' && 'border-b border-default',
          side === 'right' && 'justify-end'
        ]"
      >
        <UButton
          :icon="side === 'left' ? 'i-lucide-panel-left' : 'i-lucide-panel-right'"
          color="neutral"
          variant="ghost"
          aria-label="Toggle sidebar"
          @click="open = !open"
        />
      </div>

      <div class="flex-1 p-4">
        <Placeholder class="size-full" />
      </div>
    </div>
  </div>
</template>
```

### Collapsible

Use the `collapsible` prop to change the collapse behavior of the sidebar. Defaults to `offcanvas`.

- `offcanvas`: The sidebar slides out of view completely.
- `icon`: The sidebar shrinks to icon-only width.
- `none`: The sidebar is not collapsible.

```vue [SidebarPropsExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem, SidebarProps } from '@nuxt/ui'

// Ignore the props for the example
defineProps<Pick<SidebarProps, 'variant' | 'collapsible' | 'side'>>()

const open = ref(true)

const items: NavigationMenuItem[] = [{
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
}]
</script>

<template>
  <div
    class="flex flex-1"
    :class="[
      variant === 'inset' && 'bg-neutral-50 dark:bg-neutral-950',
      side === 'right' && 'flex-row-reverse'
    ]"
  >
    <USidebar
      v-model:open="open"
      :variant="variant"
      :collapsible="collapsible"
      :side="side"
      :ui="{
        container: 'h-full'
      }"
    >
      <template #header>
        <UIcon name="i-logos-nuxt-icon" class="size-8" />
      </template>

      <UNavigationMenu
        :items="items"
        orientation="vertical"
        :ui="{ link: 'p-1.5 overflow-hidden' }"
      />
    </USidebar>

    <div class="flex-1 flex flex-col overflow-hidden lg:peer-data-[variant=floating]:my-4 peer-data-[variant=inset]:m-4 lg:peer-data-[variant=inset]:not-peer-data-[collapsible=offcanvas]:ms-0 peer-data-[variant=inset]:rounded-xl peer-data-[variant=inset]:shadow-sm peer-data-[variant=inset]:ring peer-data-[variant=inset]:ring-default bg-default">
      <div
        class="h-(--ui-header-height) shrink-0 flex items-center px-4"
        :class="[
          variant !== 'floating' && 'border-b border-default',
          side === 'right' && 'justify-end'
        ]"
      >
        <UButton
          :icon="side === 'left' ? 'i-lucide-panel-left' : 'i-lucide-panel-right'"
          color="neutral"
          variant="ghost"
          aria-label="Toggle sidebar"
          @click="open = !open"
        />
      </div>

      <div class="flex-1 p-4">
        <Placeholder class="size-full" />
      </div>
    </div>
  </div>
</template>
```

> [!TIP]
> See: #slots
> 
> You can access the `state` in the slot props to customize the content of the sidebar when it is collapsed.

### Side

Use the `side` prop to change the side of the sidebar. Defaults to `left`.

```vue [SidebarPropsExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem, SidebarProps } from '@nuxt/ui'

// Ignore the props for the example
defineProps<Pick<SidebarProps, 'variant' | 'collapsible' | 'side'>>()

const open = ref(true)

const items: NavigationMenuItem[] = [{
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
}]
</script>

<template>
  <div
    class="flex flex-1"
    :class="[
      variant === 'inset' && 'bg-neutral-50 dark:bg-neutral-950',
      side === 'right' && 'flex-row-reverse'
    ]"
  >
    <USidebar
      v-model:open="open"
      :variant="variant"
      :collapsible="collapsible"
      :side="side"
      :ui="{
        container: 'h-full'
      }"
    >
      <template #header>
        <UIcon name="i-logos-nuxt-icon" class="size-8" />
      </template>

      <UNavigationMenu
        :items="items"
        orientation="vertical"
        :ui="{ link: 'p-1.5 overflow-hidden' }"
      />
    </USidebar>

    <div class="flex-1 flex flex-col overflow-hidden lg:peer-data-[variant=floating]:my-4 peer-data-[variant=inset]:m-4 lg:peer-data-[variant=inset]:not-peer-data-[collapsible=offcanvas]:ms-0 peer-data-[variant=inset]:rounded-xl peer-data-[variant=inset]:shadow-sm peer-data-[variant=inset]:ring peer-data-[variant=inset]:ring-default bg-default">
      <div
        class="h-(--ui-header-height) shrink-0 flex items-center px-4"
        :class="[
          variant !== 'floating' && 'border-b border-default',
          side === 'right' && 'justify-end'
        ]"
      >
        <UButton
          :icon="side === 'left' ? 'i-lucide-panel-left' : 'i-lucide-panel-right'"
          color="neutral"
          variant="ghost"
          aria-label="Toggle sidebar"
          @click="open = !open"
        />
      </div>

      <div class="flex-1 p-4">
        <Placeholder class="size-full" />
      </div>
    </div>
  </div>
</template>
```

### Title

Use the `title` prop to set the title of the sidebar header.

```vue
<template>
  <USidebar title="Navigation">
    <Placeholder class="h-full" />
  </USidebar>
</template>
```

### Description

Use the `description` prop to set the description of the sidebar header.

```vue
<template>
  <USidebar title="Navigation" description="Browse your workspace">
    <Placeholder class="h-full" />
  </USidebar>
</template>
```

### Rail

Use the `rail` prop to display a thin interactive edge on the sidebar that toggles the collapsed state on click. The rail is only rendered when `collapsible` is not `none`.

```vue
<template>
  <USidebar rail collapsible="icon" title="Navigation">
    <Placeholder class="h-full" />
  </USidebar>
</template>
```

### Close

Use the `close` prop to display a close button in the sidebar header. The close button is only rendered when `collapsible` is not `none`.

You can pass any property from the [Button](/docs/components/button) component to customize it.

```vue
<template>
  <USidebar close rail collapsible="icon" title="Navigation">
    <Placeholder class="h-full" />
  </USidebar>
</template>
```

### Close Icon

Use the `close-icon` prop to customize the close button [Icon](/docs/components/icon). Defaults to `i-lucide-x`.

```vue
<template>
  <USidebar close close-icon="i-lucide-panel-right-close" rail collapsible="icon" side="right" title="Navigation">
    <Placeholder class="h-full" />
  </USidebar>
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.close` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.close` key.

### Mode

Use the `mode` prop to change the mode of the sidebar menu on mobile. Defaults to `slideover`.

```vue [SidebarModeExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem, SidebarProps } from '@nuxt/ui'

// Ignore the props for the example
defineProps<Pick<SidebarProps, 'mode'>>()

const open = ref(true)

const items: NavigationMenuItem[] = [{
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
}]
</script>

<template>
  <div class="flex flex-1">
    <USidebar v-model:open="open" :mode="mode" title="Navigation">
      <UNavigationMenu
        :items="items"
        orientation="vertical"
        :ui="{ link: 'p-1.5 overflow-hidden' }"
      />
    </USidebar>

    <div class="flex-1 flex flex-col">
      <div class="h-(--ui-header-height) shrink-0 flex items-center px-4 border-b border-default">
        <UButton
          icon="i-lucide-panel-left"
          color="neutral"
          variant="ghost"
          aria-label="Toggle sidebar"
          @click="open = !open"
        />
      </div>

      <div class="flex-1 p-4">
        <Placeholder class="size-full" />
      </div>
    </div>
  </div>
</template>
```

> [!TIP]
> See: #props
> 
> You can use the `menu` prop to customize the menu of the sidebar, it will adapt depending on the mode you choose.

## Examples

### Control open state

You can control the open state by using the `open` prop or the `v-model:open` directive. On desktop it controls the expanded/collapsed state, on mobile it opens/closes the sheet menu.

```vue [SidebarOpenExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const open = ref(true)

defineShortcuts({
  o: () => open.value = !open.value
})

const items: NavigationMenuItem[] = [{
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
}]
</script>

<template>
  <div class="flex flex-1">
    <USidebar v-model:open="open" title="Navigation" collapsible="icon">
      <UNavigationMenu
        :items="items"
        orientation="vertical"
        :ui="{ link: 'p-1.5 overflow-hidden' }"
      />
    </USidebar>

    <div class="flex-1 flex flex-col">
      <div class="h-(--ui-header-height) shrink-0 flex items-center px-4 border-b border-default">
        <UButton
          icon="i-lucide-panel-left"
          color="neutral"
          variant="ghost"
          :aria-label="open ? 'Close sidebar' : 'Open sidebar'"
          @click="open = !open"
        />
      </div>

      <div class="flex-1 p-4">
        <Placeholder class="size-full" />
      </div>
    </div>
  </div>
</template>
```

> [!NOTE]
> 
> In this example, leveraging [`defineShortcuts`](/docs/composables/define-shortcuts), you can toggle the open state of the Sidebar by pressing <kbd value="O">
> 
> 
> 
> </kbd>
> 
> .

### Persist open state

Use [`useLocalStorage`](https://vueuse.org/core/useLocalStorage/) from VueUse or [`useCookie`](https://nuxt.com/docs/4.x/api/composables/use-cookie) instead of `ref` to persist the sidebar state across page reloads.

```vue [SidebarPersistExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const open = useLocalStorage('sidebar-open', true)

defineShortcuts({
  o: () => open.value = !open.value
})

const items: NavigationMenuItem[] = [{
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
}]
</script>

<template>
  <div class="flex flex-1">
    <USidebar v-model:open="open" title="Navigation" collapsible="icon">
      <UNavigationMenu
        :items="items"
        orientation="vertical"
        :ui="{ link: 'p-1.5 overflow-hidden' }"
      />
    </USidebar>

    <div class="flex-1 flex flex-col">
      <div class="h-(--ui-header-height) shrink-0 flex items-center px-4 border-b border-default">
        <UButton
          icon="i-lucide-panel-left"
          color="neutral"
          variant="ghost"
          aria-label="Toggle sidebar"
          @click="open = !open"
        />
      </div>

      <div class="flex-1 p-4">
        <Placeholder class="size-full" />
      </div>
    </div>
  </div>
</template>
```

> [!NOTE]
> 
> The only difference with the previous example is replacing `ref(true)` with `useLocalStorage('sidebar-open', true)`.

### With custom width

The sidebar width is controlled by the `--sidebar-width` CSS variable (defaults to `16rem`). The collapsed icon width is controlled by `--sidebar-width-icon` (defaults to `4rem`).

Override them globally in your CSS or per-instance with the `style` attribute.

```vue [SidebarWidthExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const open = ref(true)

const items: NavigationMenuItem[] = [{
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
}]
</script>

<template>
  <div class="flex flex-1">
    <USidebar
      v-model:open="open"
      collapsible="icon"
      :style="{ '--sidebar-width': '20rem' }"
    >
      <UNavigationMenu
        :items="items"
        orientation="vertical"
        :ui="{ link: 'p-1.5 overflow-hidden' }"
      />
    </USidebar>

    <div class="flex-1 flex flex-col">
      <div class="h-(--ui-header-height) shrink-0 flex items-center px-4 border-b border-default">
        <UButton
          icon="i-lucide-panel-left"
          color="neutral"
          variant="ghost"
          aria-label="Toggle sidebar"
          @click="open = !open"
        />
      </div>

      <div class="flex-1 p-4">
        <Placeholder class="size-full" />
      </div>
    </div>
  </div>
</template>
```

### With header

To position the sidebar below a [Header](/docs/components/header), customize the `gap` and `container` using the `ui` prop.

```vue [SidebarHeaderExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const open = ref(true)

const items: NavigationMenuItem[] = [{
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
}]
</script>

<template>
  <div class="flex flex-col flex-1">
    <UHeader toggle-side="left" :ui="{ container: 'px-4!' }">
      <template #toggle>
        <UButton
          icon="i-lucide-panel-left"
          color="neutral"
          variant="ghost"
          aria-label="Toggle sidebar"
          @click="open = !open"
        />
      </template>
    </UHeader>

    <div class="flex flex-1 min-h-0">
      <USidebar
        v-model:open="open"
        collapsible="icon"
        :ui="{
          gap: 'h-[calc(100%-var(--ui-header-height))]',
          container: 'absolute top-(--ui-header-height) bottom-0 h-[calc(100%-var(--ui-header-height))]'
        }"
      >
        <UNavigationMenu
          :items="items"
          orientation="vertical"
          :ui="{ link: 'p-1.5 overflow-hidden' }"
        />
      </USidebar>

      <div class="flex-1 p-4">
        <Placeholder class="size-full" />
      </div>
    </div>
  </div>
</template>
```

> [!NOTE]
> 
> The `--ui-header-height` variable defaults to `4rem` and is used by the Header. Adjust it if your navbar uses a different height.

### With AI chat

Use the sidebar on the right side with [ChatMessages](/docs/components/chat-messages) and [ChatPrompt](/docs/components/chat-prompt) to create an AI chat panel.

```vue [SidebarChatExample.vue]
<script setup lang="ts">
import type { UIMessage } from 'ai'
import { isTextUIPart } from 'ai'
import { Chat } from '@ai-sdk/vue'
import { isPartStreaming } from '@nuxt/ui/utils/ai'
import { Comark } from '@comark/vue'
import highlight from '@comark/vue/plugins/highlight'

const open = ref(true)
const input = ref('')

const messages: UIMessage[] = [{
  id: '1',
  role: 'user',
  parts: [{ type: 'text', text: 'What is Nuxt UI?' }]
}, {
  id: '2',
  role: 'assistant',
  parts: [{ type: 'text', text: 'Nuxt UI is a Vue component library built on Reka UI, Tailwind CSS, and Tailwind Variants. It provides 125+ accessible components for building modern web apps.' }]
}]

const chat = new Chat({
  messages
})

function onSubmit() {
  if (!input.value.trim()) return

  chat.sendMessage({ text: input.value })

  input.value = ''
}

const ui = {
  prose: {
    p: { base: 'my-2 text-sm/6' },
    li: { base: 'my-0.5 text-sm/6' },
    ul: { base: 'my-2' },
    ol: { base: 'my-2' },
    h1: { base: 'text-xl mb-4' },
    h2: { base: 'text-lg mt-6 mb-3' },
    h3: { base: 'text-base mt-4 mb-2' },
    h4: { base: 'text-sm mt-3 mb-1.5' },
    code: { base: 'text-xs' },
    pre: { root: 'my-2', base: 'text-xs/5' },
    table: { root: 'my-2' },
    hr: { base: 'my-4' }
  }
}
</script>

<template>
  <div class="flex flex-1">
    <div class="flex-1 flex flex-col">
      <div class="h-(--ui-header-height) shrink-0 flex items-center justify-end px-4 border-b border-default">
        <UButton
          icon="i-lucide-panel-right"
          color="neutral"
          variant="ghost"
          aria-label="Toggle sidebar"
          @click="open = !open"
        />
      </div>

      <div class="flex-1 p-4">
        <Placeholder class="size-full" />
      </div>
    </div>

    <USidebar
      v-model:open="open"
      side="right"
      title="AI Chat"
      close
      :style="{ '--sidebar-width': '20rem' }"
      :ui="{ container: 'h-full' }"
    >
      <UTheme :ui="ui">
        <UChatMessages
          :messages="chat.messages"
          :status="chat.status"
          compact
          class="px-0"
        >
          <template #content="{ message }">
            <template v-for="(part, index) in message.parts" :key="`${message.id}-${part.type}-${index}`">
              <template v-if="isTextUIPart(part)">
                <Comark
                  v-if="message.role === 'assistant'"
                  :markdown="part.text"
                  :streaming="isPartStreaming(part)"
                  :plugins="[highlight()]"
                  class="*:first:mt-0 *:last:mb-0"
                />
                <p v-else-if="message.role === 'user'" class="whitespace-pre-wrap text-sm/6">
                  {{ part.text }}
                </p>
              </template>
            </template>
          </template>
        </UChatMessages>
      </UTheme>

      <template #footer>
        <UChatPrompt
          v-model="input"
          :error="chat.error"
          :autofocus="false"
          variant="subtle"
          size="sm"
          :ui="{ base: 'px-0' }"
          @submit="onSubmit"
        >
          <UChatPromptSubmit
            size="sm"
            :status="chat.status"
            @stop="chat.stop()"
            @reload="chat.regenerate()"
          />
        </UChatPrompt>
      </template>
    </USidebar>
  </div>
</template>
```

## API

### Props

```ts
/**
 * Props for the Sidebar component
 */
interface SidebarProps {
  /**
   * The element or component this component should render as.
   * @default "\"aside\""
   */
  as?: any;
  /**
   * The visual variant of the sidebar.
   * @default "\"sidebar\""
   */
  variant?: "floating" | "sidebar" | "inset" | undefined;
  /**
   * The collapse behavior of the sidebar.
   * - `offcanvas`: The sidebar slides out of view completely.
   * - `icon`: The sidebar shrinks to icon-only width.
   * - `none`: The sidebar is not collapsible.
   * @default "\"offcanvas\""
   */
  collapsible?: "offcanvas" | "icon" | "none" | undefined;
  /**
   * The side to render the sidebar on.
   * @default "\"left\""
   */
  side?: "left" | "right" | undefined;
  /**
   * The title displayed in the sidebar header.
   */
  title?: string | undefined;
  /**
   * The description displayed in the sidebar header.
   */
  description?: string | undefined;
  /**
   * Display a close button to collapse the sidebar.
   * Only renders when `collapsible` is not `none`.
   * `{ size: 'md', color: 'neutral', variant: 'ghost' }`{lang="ts-type"}
   * @default "false"
   */
  close?: boolean | Omit<ButtonProps, LinkPropsKeys> | undefined;
  /**
   * The icon displayed in the close button.
   */
  closeIcon?: any;
  /**
   * Display a rail on the sidebar edge to toggle collapse.
   * Only renders when `collapsible` is not `none`.
   * @default "false"
   */
  rail?: boolean | undefined;
  /**
   * Animate the sidebar when collapsing or expanding.
   * @default "true"
   */
  transition?: boolean | undefined;
  /**
   * The mode of the sidebar menu on mobile.
   * @default "\"slideover\" as never"
   */
  mode?: T | undefined;
  /**
   * The props for the sidebar menu component on mobile.
   */
  menu?: SidebarMenu<T> | undefined;
  ui?: { root?: SlotClass; gap?: SlotClass; container?: SlotClass; inner?: SlotClass; header?: SlotClass; wrapper?: SlotClass; title?: SlotClass; description?: SlotClass; actions?: SlotClass; close?: SlotClass; body?: SlotClass; footer?: SlotClass; rail?: SlotClass; } | undefined;
  /**
   * @default "true"
   */
  open?: boolean | undefined;
}
```

### Slots

```ts
/**
 * Slots for the Sidebar component
 */
interface SidebarSlots {
  header(): any;
  title(): any;
  description(): any;
  actions(): any;
  close(): any;
  default(): any;
  footer(): any;
  rail(): any;
  content(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    sidebar: {
      slots: {
        root: 'peer [--sidebar-width:16rem] [--sidebar-width-icon:4rem]',
        gap: 'relative w-(--sidebar-width) bg-transparent',
        container: 'fixed inset-y-0 z-10 hidden h-svh w-(--sidebar-width) lg:flex',
        inner: 'flex size-full flex-col overflow-hidden divide-y divide-default',
        header: 'flex items-center gap-1.5 overflow-hidden px-4 min-h-(--ui-header-height)',
        wrapper: 'min-w-0 flex-1',
        title: 'text-highlighted font-semibold truncate',
        description: 'text-muted text-sm truncate',
        actions: 'flex items-center gap-1.5 shrink-0',
        close: '',
        body: 'flex min-h-0 flex-1 flex-col gap-4 overflow-y-auto p-4',
        footer: 'flex items-center gap-1.5 overflow-hidden p-4',
        rail: [
          'absolute inset-y-0 z-20 hidden w-4 after:absolute after:inset-y-0 after:left-1/2 after:w-px lg:flex hover:after:bg-(--ui-border-accented)',
          'after:transition-colors'
        ]
      },
      variants: {
        transition: {
          true: {
            gap: 'transition-[width] duration-200 ease-out',
            container: 'transition-[left,right,width] duration-200 ease-out',
            rail: 'transition-all ease-out'
          }
        },
        side: {
          left: {
            container: 'left-0 border-e border-default',
            rail: 'end-0 translate-x-1/2'
          },
          right: {
            container: 'right-0 border-s border-default',
            rail: '-start-px -translate-x-1/2'
          }
        },
        collapsible: {
          offcanvas: {
            root: 'group/sidebar hidden lg:block',
            gap: 'data-[state=collapsed]:w-0'
          },
          icon: {
            root: 'group/sidebar hidden lg:block',
            gap: 'data-[state=collapsed]:w-(--sidebar-width-icon)',
            container: 'data-[state=collapsed]:w-(--sidebar-width-icon)',
            actions: 'group-data-[state=collapsed]/sidebar:hidden',
            body: 'group-data-[state=collapsed]/sidebar:overflow-hidden'
          },
          none: {
            root: 'h-full w-(--sidebar-width)'
          }
        },
        variant: {
          sidebar: {},
          floating: {
            container: 'p-4 border-transparent',
            inner: 'rounded-lg ring ring-default shadow-lg',
            rail: 'inset-y-4'
          },
          inset: {
            container: 'py-4 border-transparent',
            inner: 'divide-transparent',
            rail: 'inset-y-4'
          }
        }
      },
      compoundVariants: [
        {
          side: 'left',
          collapsible: [
            'offcanvas',
            'icon'
          ],
          class: {
            rail: 'cursor-w-resize data-[state=collapsed]:cursor-e-resize'
          }
        },
        {
          side: 'right',
          collapsible: [
            'offcanvas',
            'icon'
          ],
          class: {
            rail: 'cursor-e-resize data-[state=collapsed]:cursor-w-resize'
          }
        },
        {
          side: 'left',
          collapsible: 'none',
          class: {
            root: 'border-e border-default'
          }
        },
        {
          side: 'right',
          collapsible: 'none',
          class: {
            root: 'border-s border-default'
          }
        },
        {
          side: 'left',
          collapsible: 'offcanvas',
          class: {
            container: 'data-[state=collapsed]:-left-(--sidebar-width)'
          }
        },
        {
          side: 'right',
          collapsible: 'offcanvas',
          class: {
            container: 'data-[state=collapsed]:-right-(--sidebar-width)'
          }
        },
        {
          variant: 'floating',
          collapsible: 'icon',
          class: {
            gap: 'data-[state=collapsed]:w-[calc(var(--sidebar-width-icon)+--spacing(8))]',
            container: 'data-[state=collapsed]:w-[calc(var(--sidebar-width-icon)+--spacing(8)+2px)]'
          }
        },
        {
          variant: 'floating',
          collapsible: 'none',
          class: {
            root: 'p-4 border-0'
          }
        },
        {
          variant: 'inset',
          collapsible: 'none',
          class: {
            root: 'py-4 border-0'
          }
        },
        {
          variant: 'floating',
          side: 'left',
          class: {
            rail: 'end-4'
          }
        },
        {
          variant: 'floating',
          side: 'right',
          class: {
            rail: 'start-[calc(--spacing(4)-1px)]'
          }
        }
      ]
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Sidebar.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/sidebar.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

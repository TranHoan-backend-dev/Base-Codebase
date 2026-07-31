---
title: "SelectMenu"
description: "An advanced searchable select element."
canonical_url: "https://ui.nuxt.com/docs/components/select-menu"
---
# SelectMenu

> An advanced searchable select element.

## Usage

Use the `v-model` directive to control the value of the SelectMenu or the `default-value` prop to set the initial value when you do not need to control its state.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref("Backlog")
</script>

<template>
  <USelectMenu v-model="value" :items="items" />
</template>
```

> [!TIP]
> 
> Use this over a [`Select`](/docs/components/select) to take advantage of Reka UI's [`Combobox`](https://reka-ui.com/docs/components/combobox) component that offers search capabilities and multiple selection.

> [!NOTE]
> 
> This component is similar to the [`InputMenu`](/docs/components/input-menu) but it's using a Select instead of an Input with the search inside the menu.

### Items

Use the `items` prop as an array of strings, numbers or booleans:

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref("Backlog")
</script>

<template>
  <USelectMenu v-model="value" :items="items" class="w-48" />
</template>
```

You can also pass an array of objects with the following properties:

- `label?: string`
- [`type?: "label" | "separator" | "item"`](#with-items-type)
- [`icon?: string`](#with-icons-in-items)
- [`avatar?: AvatarProps`](#with-avatar-in-items)
- [`chip?: ChipProps`](#with-chip-in-items)
- `disabled?: boolean`
- `onSelect?: (e: Event) => void`
- `class?: any`
- `ui?: { label?: ClassNameValue, separator?: ClassNameValue, item?: ClassNameValue, itemLeadingIcon?: ClassNameValue, itemLeadingAvatarSize?: ClassNameValue, itemLeadingAvatar?: ClassNameValue, itemLeadingChipSize?: ClassNameValue, itemLeadingChip?: ClassNameValue, itemLabel?: ClassNameValue, itemTrailing?: ClassNameValue, itemTrailingIcon?: ClassNameValue }`

```vue
<script setup lang="ts">
import type { SelectMenuItem } from '@nuxt/ui'

const items = ref<SelectMenuItem[]>([
  {
    label: "Backlog"
  },
  {
    label: "Todo"
  },
  {
    label: "In Progress"
  },
  {
    label: "Done"
  }
])
const value = ref({
  label: "Todo"
})
</script>

<template>
  <USelectMenu v-model="value" :items="items" class="w-48" />
</template>
```

> [!CAUTION]
> 
> Unlike the [`Select`](/docs/components/select) component, the SelectMenu expects the whole object to be passed to the `v-model` directive or the `default-value` prop by default.

You can also pass an array of arrays to the `items` prop to display separated groups of items.

```vue
<script setup lang="ts">
const items = ref([
  [
    "Apple",
    "Banana",
    "Blueberry",
    "Grapes",
    "Pineapple"
  ],
  [
    "Aubergine",
    "Broccoli",
    "Carrot",
    "Courgette",
    "Leek"
  ]
])
const value = ref("Apple")
</script>

<template>
  <USelectMenu v-model="value" :items="items" class="w-48" />
</template>
```

### Value Key

You can choose to bind a single property of the object rather than the whole object by using the `value-key` prop. Defaults to `undefined`.

```vue
<script setup lang="ts">
import type { SelectMenuItem } from '@nuxt/ui'

const items = ref<SelectMenuItem[]>([
  {
    label: "Backlog",
    id: "backlog"
  },
  {
    label: "Todo",
    id: "todo"
  },
  {
    label: "In Progress",
    id: "in_progress"
  },
  {
    label: "Done",
    id: "done"
  }
])
const value = ref("todo")
</script>

<template>
  <USelectMenu v-model="value" value-key="id" :items="items" class="w-48" />
</template>
```

> [!TIP]
> 
> Use the `by` prop to compare objects by a field instead of reference when the `model-value` is an object.

### Multiple

Use the `multiple` prop to allow multiple selections, the selected items will be separated by a comma in the trigger.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref([
  "Backlog",
  "Todo"
])
</script>

<template>
  <USelectMenu v-model="value" multiple :items="items" class="w-48" />
</template>
```

> [!CAUTION]
> 
> Ensure to pass an array to the `default-value` prop or the `v-model` directive.

### Placeholder

Use the `placeholder` prop to set a placeholder text.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
</script>

<template>
  <USelectMenu placeholder="Select status" :items="items" class="w-48" />
</template>
```

### Search Input

Use the `search-input` prop to customize or hide the search input (with `false` value).

You can pass any property from the [Input](/docs/components/input) component to customize it.

```vue
<script setup lang="ts">
import type { SelectMenuItem } from '@nuxt/ui'

const items = ref<SelectMenuItem[]>([
  {
    label: "Backlog",
    icon: "i-lucide-circle-help"
  },
  {
    label: "Todo",
    icon: "i-lucide-circle-plus"
  },
  {
    label: "In Progress",
    icon: "i-lucide-circle-arrow-up"
  },
  {
    label: "Done",
    icon: "i-lucide-circle-check"
  }
])
const value = ref({
  label: "Backlog",
  icon: "i-lucide-circle-help"
})
</script>

<template>
  <USelectMenu v-model="value" :search-input="{
  placeholder: 'Filter...',
  icon: 'i-lucide-search'
}" :items="items" class="w-48" />
</template>
```

> [!TIP]
> 
> You can set the `search-input` prop to `false` to hide the search input.

### Content

Use the `content` prop to control how the SelectMenu content is rendered, like its `align` or `side` for example.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref("Backlog")
</script>

<template>
  <USelectMenu v-model="value" :content="{
  align: 'center',
  side: 'bottom',
  sideOffset: 8
}" :items="items" class="w-48" />
</template>
```

### Arrow

Use the `arrow` prop to display an arrow on the SelectMenu.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref("Backlog")
</script>

<template>
  <USelectMenu v-model="value" arrow :items="items" class="w-48" />
</template>
```

### Color

Use the `color` prop to change the ring color when the SelectMenu is focused.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref("Backlog")
</script>

<template>
  <USelectMenu v-model="value" color="neutral" highlight :items="items" class="w-48" />
</template>
```

> [!NOTE]
> 
> The `highlight` prop is used here to show the focus state. It's used internally when a validation error occurs.

### Variant

Use the `variant` prop to change the variant of the SelectMenu.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref("Backlog")
</script>

<template>
  <USelectMenu v-model="value" color="neutral" variant="subtle" :highlight="false" :items="items" class="w-48" />
</template>
```

### Size

Use the `size` prop to change the size of the SelectMenu.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref("Backlog")
</script>

<template>
  <USelectMenu v-model="value" size="xl" :items="items" class="w-48" />
</template>
```

### Icon

Use the `icon` prop to show an [Icon](/docs/components/icon) inside the SelectMenu.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref("Backlog")
</script>

<template>
  <USelectMenu v-model="value" icon="i-lucide-search" size="md" :items="items" class="w-48" />
</template>
```

### Trailing Icon

Use the `trailing-icon` prop to customize the trailing [Icon](/docs/components/icon). Defaults to `i-lucide-chevron-down`.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref("Backlog")
</script>

<template>
  <USelectMenu v-model="value" trailing-icon="i-lucide-arrow-down" size="md" :items="items" class="w-48" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.chevronDown` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.chevronDown` key.

### Selected Icon

Use the `selected-icon` prop to customize the icon when an item is selected. Defaults to `i-lucide-check`.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref("Backlog")
</script>

<template>
  <USelectMenu v-model="value" selected-icon="i-lucide-flame" size="md" :items="items" class="w-48" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.check` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.check` key.

### Clear `4.4+`

Use the `clear` prop to display a clear button when a value is selected.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref("Backlog")
</script>

<template>
  <USelectMenu v-model="value" clear :items="items" class="w-48" />
</template>
```

### Clear Icon `4.4+`

Use the `clear-icon` prop to customize the clear button [Icon](/docs/components/icon). Defaults to `i-lucide-x`.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref("Backlog")
</script>

<template>
  <USelectMenu v-model="value" clear clear-icon="i-lucide-trash" :items="items" class="w-48" />
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

### Avatar

Use the `avatar` prop to display an [Avatar](/docs/components/avatar) inside the SelectMenu.

```vue
<script setup lang="ts">
const items = ref([
  "Nuxt",
  "NuxtHub",
  "NuxtLabs",
  "Nuxt Modules",
  "Nuxt Community"
])
const value = ref("Nuxt")
</script>

<template>
  <USelectMenu v-model="value" :avatar="{
  src: 'https://github.com/nuxt.png',
  loading: 'lazy'
}" :items="items" class="w-48" />
</template>
```

### Loading

Use the `loading` prop to show a loading icon on the SelectMenu.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref("Backlog")
</script>

<template>
  <USelectMenu v-model="value" loading :trailing="false" :items="items" class="w-48" />
</template>
```

### Loading Icon

Use the `loading-icon` prop to customize the loading icon. Defaults to `i-lucide-loader-circle`.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
const value = ref("Backlog")
</script>

<template>
  <USelectMenu v-model="value" loading loading-icon="i-lucide-loader" :items="items" class="w-48" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.loading` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.loading` key.

### Disabled

Use the `disabled` prop to disable the SelectMenu.

```vue
<script setup lang="ts">
const items = ref([
  "Backlog",
  "Todo",
  "In Progress",
  "Done"
])
</script>

<template>
  <USelectMenu disabled placeholder="Select status" :items="items" class="w-48" />
</template>
```

## Examples

### With items type

You can use the `type` property with `separator` to display a separator between items or `label` to display a label.

```vue
<script setup lang="ts">
import type { SelectMenuItem } from '@nuxt/ui'

const items = ref<SelectMenuItem[]>([
  {
    type: "label",
    label: "Fruits"
  },
  "Apple",
  "Banana",
  "Blueberry",
  "Grapes",
  "Pineapple",
  {
    type: "separator"
  },
  {
    type: "label",
    label: "Vegetables"
  },
  "Aubergine",
  "Broccoli",
  "Carrot",
  "Courgette",
  "Leek"
])
const value = ref("Apple")
</script>

<template>
  <USelectMenu v-model="value" :items="items" class="w-48" />
</template>
```

### With icon in items

You can use the `icon` property to display an [Icon](/docs/components/icon) inside the items.

```vue [SelectMenuItemsIconExample.vue]
<script setup lang="ts">
import type { SelectMenuItem } from '@nuxt/ui'

const items = ref([
  {
    label: 'Backlog',
    value: 'backlog',
    icon: 'i-lucide-circle-help'
  },
  {
    label: 'Todo',
    value: 'todo',
    icon: 'i-lucide-circle-plus'
  },
  {
    label: 'In Progress',
    value: 'in_progress',
    icon: 'i-lucide-circle-arrow-up'
  },
  {
    label: 'Done',
    value: 'done',
    icon: 'i-lucide-circle-check'
  }
] satisfies SelectMenuItem[])

const value = ref(items.value[0])
</script>

<template>
  <USelectMenu v-model="value" :icon="value?.icon" :items="items" class="w-48" />
</template>
```

> [!TIP]
> 
> You can also use the `#leading` slot to display the selected icon.

### With avatar in items

You can use the `avatar` property to display an [Avatar](/docs/components/avatar) inside the items.

```vue [SelectMenuItemsAvatarExample.vue]
<script setup lang="ts">
import type { SelectMenuItem } from '@nuxt/ui'

const items = ref([
  {
    label: 'benjamincanac',
    value: 'benjamincanac',
    avatar: {
      src: 'https://github.com/benjamincanac.png',
      alt: 'benjamincanac',
      loading: 'lazy' as const
    }
  },
  {
    label: 'romhml',
    value: 'romhml',
    avatar: {
      src: 'https://github.com/romhml.png',
      alt: 'romhml',
      loading: 'lazy' as const
    }
  },
  {
    label: 'noook',
    value: 'noook',
    avatar: {
      src: 'https://github.com/noook.png',
      alt: 'noook',
      loading: 'lazy' as const
    }
  },
  {
    label: 'sandros94',
    value: 'sandros94',
    avatar: {
      src: 'https://github.com/sandros94.png',
      alt: 'sandros94',
      loading: 'lazy' as const
    }
  }
] satisfies SelectMenuItem[])

const value = ref(items.value[0])
</script>

<template>
  <USelectMenu v-model="value" :avatar="value?.avatar" :items="items" class="w-48" />
</template>
```

> [!TIP]
> 
> You can also use the `#leading` slot to display the selected avatar.

### With chip in items

You can use the `chip` property to display a [Chip](/docs/components/chip) inside the items.

```vue [SelectMenuItemsChipExample.vue]
<script setup lang="ts">
import type { SelectMenuItem, ChipProps } from '@nuxt/ui'

const items = ref([
  {
    label: 'bug',
    value: 'bug',
    chip: {
      color: 'error'
    }
  },
  {
    label: 'feature',
    value: 'feature',
    chip: {
      color: 'success'
    }
  },
  {
    label: 'enhancement',
    value: 'enhancement',
    chip: {
      color: 'info'
    }
  }
] satisfies SelectMenuItem[])

const value = ref(items.value[0])
</script>

<template>
  <USelectMenu v-model="value" :items="items" class="w-48">
    <template #leading="{ modelValue, ui }">
      <UChip
        v-if="modelValue"
        v-bind="modelValue.chip"
        inset
        standalone
        :size="(ui.itemLeadingChipSize() as ChipProps['size'])"
        :class="ui.itemLeadingChip()"
      />
    </template>
  </USelectMenu>
</template>
```

> [!NOTE]
> 
> In this example, the `#leading` slot is used to display the selected chip.

### Control open state

You can control the open state by using the `default-open` prop or the `v-model:open` directive.

```vue [SelectMenuOpenExample.vue]
<script setup lang="ts">
const open = ref(false)
const items = ref(['Backlog', 'Todo', 'In Progress', 'Done'])
const value = ref('Backlog')

defineShortcuts({
  o: () => open.value = !open.value
})
</script>

<template>
  <USelectMenu v-model="value" v-model:open="open" :items="items" class="w-48" />
</template>
```

> [!NOTE]
> 
> In this example, leveraging [`defineShortcuts`](/docs/composables/define-shortcuts), you can toggle the SelectMenu by pressing <kbd value="O">
> 
> 
> 
> </kbd>
> 
> .

### Control search term

Use the `v-model:search-term` directive to control the search term.

```vue [SelectMenuSearchTermExample.vue]
<script setup lang="ts">
const searchTerm = ref('D')
const items = ref(['Backlog', 'Todo', 'In Progress', 'Done'])
const value = ref('Backlog')
</script>

<template>
  <USelectMenu v-model="value" v-model:search-term="searchTerm" :items="items" class="w-48" />
</template>
```

### With rotating icon

Here is an example with a rotating icon that indicates the open state of the SelectMenu.

```vue [SelectMenuIconExample.vue]
<script setup lang="ts">
const items = ref(['Backlog', 'Todo', 'In Progress', 'Done'])
const value = ref('Backlog')
</script>

<template>
  <USelectMenu
    v-model="value"
    :items="items"
    :ui="{
      trailingIcon: 'group-data-[state=open]:rotate-180 transition-transform duration-200'
    }"
    class="w-48"
  />
</template>
```

### With create item

Use the `create-item` prop to enable users to add custom values that aren't in the predefined options.

```vue [SelectMenuCreateItemExample.vue]
<script setup lang="ts">
const items = ref(['Backlog', 'Todo', 'In Progress', 'Done'])
const value = ref('Backlog')

function onCreate(item: string) {
  items.value.push(item)

  value.value = item
}
</script>

<template>
  <USelectMenu
    v-model="value"
    create-item
    :items="items"
    class="w-48"
    @create="onCreate"
  />
</template>
```

> [!NOTE]
> 
> The create option shows when no match is found by default. Set it to `always` to show it even when similar values exist.

> [!TIP]
> See: #emits
> 
> Use the `@create` event to handle the creation of the item. You will receive the event and the item as arguments.

### With fetched items

You can fetch items from an API and use them in the SelectMenu.

```vue [SelectMenuFetchExample.vue]
<script setup lang="ts">
import type { AvatarProps } from '@nuxt/ui'

const { data: users, status, execute } = await useLazyFetch('https://jsonplaceholder.typicode.com/users', {
  key: 'typicode-users',
  transform: (data: { id: number, name: string }[]) => {
    return data?.map(user => ({
      label: user.name,
      value: String(user.id),
      avatar: { src: `https://i.pravatar.cc/120?img=${user.id}`, loading: 'lazy' as const }
    }))
  },
  immediate: false
})

function onOpen() {
  if (!users.value?.length) {
    execute()
  }
}
</script>

<template>
  <USelectMenu
    :items="users"
    :loading="status === 'pending'"
    icon="i-lucide-user"
    placeholder="Select user"
    class="w-48"
    @update:open="onOpen"
  >
    <template #leading="{ modelValue, ui }">
      <UAvatar
        v-if="modelValue"
        v-bind="modelValue.avatar"
        :size="(ui.leadingAvatarSize() as AvatarProps['size'])"
        :class="ui.leadingAvatar()"
      />
    </template>
  </USelectMenu>
</template>
```

> [!NOTE]
> 
> This example uses `useLazyFetch` with `immediate: false` to only fetch data when the menu opens, avoiding unnecessary API calls on page load.

### With ignore filter

Set the `ignore-filter` prop to `true` to disable the internal search and use your own search logic.

```vue [SelectMenuIgnoreFilterExample.vue]
<script setup lang="ts">
import { refDebounced } from '@vueuse/core'
import type { AvatarProps } from '@nuxt/ui'

const searchTerm = ref('')
const searchTermDebounced = refDebounced(searchTerm, 200)

const { data: users, status, execute } = await useLazyFetch('https://jsonplaceholder.typicode.com/users', {
  key: 'select-menu-users-search',
  params: { q: searchTermDebounced },
  transform: (data: { id: number, name: string }[]) => {
    return data?.map(user => ({
      label: user.name,
      value: String(user.id),
      avatar: { src: `https://i.pravatar.cc/120?img=${user.id}`, loading: 'lazy' as const }
    }))
  },
  immediate: false
})

function onOpen() {
  if (!users.value?.length) {
    execute()
  }
}
</script>

<template>
  <USelectMenu
    v-model:search-term="searchTerm"
    :items="users"
    :search-input="{
      icon: 'i-lucide-search',
      loading: status === 'pending'
    }"
    ignore-filter
    icon="i-lucide-user"
    placeholder="Select user"
    class="w-48"
    @update:open="onOpen"
  >
    <template #leading="{ modelValue, ui }">
      <UAvatar
        v-if="modelValue"
        v-bind="modelValue.avatar"
        :size="(ui.leadingAvatarSize() as AvatarProps['size'])"
        :class="ui.leadingAvatar()"
      />
    </template>
  </USelectMenu>
</template>
```

> [!NOTE]
> 
> This example uses [`refDebounced`](https://vueuse.org/shared/refDebounced/#refdebounced) to debounce the API calls. The fetch is deferred with `immediate: false` so no request is made until the menu opens.

### With filter fields

Use the `filter-fields` prop with an array of fields to filter on. Defaults to `[labelKey]`.

```vue [SelectMenuFilterFieldsExample.vue]
<script setup lang="ts">
import type { AvatarProps } from '@nuxt/ui'

const { data: users, status, execute } = await useLazyFetch('https://jsonplaceholder.typicode.com/users', {
  key: 'typicode-users-email',
  transform: (data: { id: number, name: string, email: string }[]) => {
    return data?.map(user => ({
      label: user.name,
      email: user.email,
      value: String(user.id),
      avatar: { src: `https://i.pravatar.cc/120?img=${user.id}`, loading: 'lazy' as const }
    }))
  },
  immediate: false
})

function onOpen() {
  if (!users.value?.length) {
    execute()
  }
}
</script>

<template>
  <USelectMenu
    :items="users"
    :loading="status === 'pending'"
    :filter-fields="['label', 'email']"
    icon="i-lucide-user"
    placeholder="Select user"
    class="w-80"
    @update:open="onOpen"
  >
    <template #leading="{ modelValue, ui }">
      <UAvatar
        v-if="modelValue"
        v-bind="modelValue.avatar"
        :size="(ui.leadingAvatarSize() as AvatarProps['size'])"
        :class="ui.leadingAvatar()"
      />
    </template>

    <template #item-label="{ item }">
      {{ item.label }}

      <span class="text-muted">
        {{ item.email }}
      </span>
    </template>
  </USelectMenu>
</template>
```

> [!NOTE]
> 
> This example uses `useLazyFetch` with `immediate: false` to only fetch data when the menu opens, avoiding unnecessary API calls on page load.

### With virtualization `4.1+`

Use the `virtualize` prop to enable virtualization for large lists as a boolean or an object with options like `{ estimateSize: 32, overscan: 12 }`.

> [!WARNING]
> See: https://github.com/unovue/reka-ui/issues/1885
> 
> When enabled, all groups are flattened into a single list due to a limitation of Reka UI.

```vue [SelectMenuVirtualizeExample.vue]
<script setup lang="ts">
import type { SelectMenuItem } from '@nuxt/ui'

const items: SelectMenuItem[] = Array(1000).fill(0).map((_, i) => ({
  label: `item-${i}`,
  value: i
}))
</script>

<template>
  <USelectMenu virtualize :items="items" class="w-48" />
</template>
```

### With infinite scroll `4.4+`

You can use the [`useInfiniteScroll`](https://vueuse.org/core/useInfiniteScroll/) composable to load more data as the user scrolls.

```vue [SelectMenuInfiniteScrollExample.vue]
<script setup lang="ts">
import { useInfiniteScroll } from '@vueuse/core'

type User = {
  firstName: string
}

type UserResponse = {
  users: User[]
  total: number
  skip: number
  limit: number
}

const skip = ref(0)

const { data, status, execute } = await useLazyFetch('https://dummyjson.com/users?limit=10&select=firstName', {
  key: 'select-menu-users-infinite-scroll',
  params: { skip },
  transform: (data?: UserResponse) => {
    return data?.users.map(user => user.firstName)
  },
  immediate: false
})

const users = ref<string[]>([])

watch(data, () => {
  users.value = [
    ...users.value,
    ...(data.value || [])
  ]
})

function onOpen() {
  if (!users.value?.length) {
    execute()
  }
}

const selectMenu = useTemplateRef('selectMenu')

onMounted(() => {
  useInfiniteScroll(() => selectMenu.value?.viewportRef, () => {
    skip.value += 10
  }, {
    canLoadMore: () => {
      return status.value !== 'pending'
    }
  })
})
</script>

<template>
  <USelectMenu
    ref="selectMenu"
    placeholder="Select user"
    :items="users"
    @update:open="onOpen"
  />
</template>
```

> [!NOTE]
> 
> This example uses `useLazyFetch` with `immediate: false` so data is only loaded as the user scrolls.

### With full content width

You can expand the content to the full width of its items by adding the `min-w-fit` class on the `ui.content` slot.

```vue [SelectMenuContentWidthExample.vue]
<script setup lang="ts">
const { data: users, execute } = await useLazyFetch('https://jsonplaceholder.typicode.com/users', {
  key: 'typicode-users-email',
  transform: (data: { id: number, name: string, email: string }[]) => {
    return data?.map(user => ({
      label: user.name,
      email: user.email,
      value: String(user.id),
      avatar: { src: `https://i.pravatar.cc/120?img=${user.id}`, loading: 'lazy' as const }
    }))
  },
  immediate: false
})

function onOpen() {
  if (!users.value?.length) {
    execute()
  }
}
</script>

<template>
  <USelectMenu
    :items="users"
    icon="i-lucide-user"
    placeholder="Select user"
    :ui="{ content: 'min-w-fit' }"
    class="w-48"
    @update:open="onOpen"
  >
    <template #item-label="{ item }">
      {{ item.label }}

      <span class="text-muted">
        {{ item.email }}
      </span>
    </template>
  </USelectMenu>
</template>
```

> [!TIP]
> 
> You can also change the content width globally in your `app.config.ts`:
> 
> ```text
> export default defineAppConfig({
>   ui: {
>     selectMenu: {
>       slots: {
>         content: 'min-w-fit'
>       }
>     }
>   }
> })
> ```

### As a country picker

You can use the SelectMenu as a country picker with lazy loading. Countries are only fetched when the menu is first opened.

```vue [SelectMenuCountriesExample.vue]
<script setup lang="ts">
const { data: countries, status, execute } = await useLazyFetch<{
  name: string
  code: string
  emoji: string
}[]>('/api/countries.json', {
  key: 'api-countries',
  immediate: false
})

function onOpen() {
  if (!countries.value?.length) {
    execute()
  }
}
</script>

<template>
  <USelectMenu
    :items="countries"
    :loading="status === 'pending'"
    label-key="name"
    :search-input="{ icon: 'i-lucide-search' }"
    placeholder="Select country"
    class="w-48"
    @update:open="onOpen"
  >
    <template #leading="{ modelValue, ui }">
      <span v-if="modelValue" class="size-5 text-center">
        {{ modelValue?.emoji }}
      </span>
      <UIcon v-else name="i-lucide-earth" :class="ui.leadingIcon()" />
    </template>
    <template #item-leading="{ item }">
      <span class="size-5 text-center">
        {{ item.emoji }}
      </span>
    </template>
  </USelectMenu>
</template>
```

> [!NOTE]
> 
> This example uses `useLazyFetch` with `immediate: false` to only load countries when the menu is first opened.

## API

### Props

```ts
/**
 * Props for the SelectMenu component
 */
interface SelectMenuProps {
  id?: string | undefined;
  /**
   * The placeholder text when the select is empty.
   */
  placeholder?: string | undefined;
  /**
   * Whether to display the search input or not.
   * Can be an object to pass additional props to the input.
   * `{ placeholder: 'Search...', variant: 'none' }`{lang="ts-type"}
   * @default "true"
   */
  searchInput?: boolean | Omit<InputProps<AcceptableValue, ModelModifiers>, "modelValue" | "defaultValue"> | undefined;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  variant?: "outline" | "soft" | "subtle" | "ghost" | "none" | undefined;
  size?: "sm" | "md" | "xs" | "lg" | "xl" | undefined;
  required?: boolean | undefined;
  /**
   * The icon displayed to open the menu.
   */
  trailingIcon?: any;
  /**
   * The icon displayed when an item is selected.
   */
  selectedIcon?: any;
  /**
   * Display a clear button to reset the model value.
   * Can be an object to pass additional props to the Button.
   */
  clear?: (C & boolean) | (C & Partial<Omit<ButtonProps, LinkPropsKeys>>) | undefined;
  /**
   * The icon displayed in the clear button.
   */
  clearIcon?: any;
  /**
   * The content of the menu.
   */
  content?: (Omit<ComboboxContentProps, "asChild" | "as" | "forceMount"> & Partial<EmitsToProps<DismissableLayerEmits>>) | undefined;
  /**
   * Display an arrow alongside the menu.
   * `{ rounded: true }`{lang="ts-type"}
   */
  arrow?: boolean | Omit<ComboboxArrowProps, "asChild" | "as"> | undefined;
  /**
   * Render the menu in a portal.
   * @default "true"
   */
  portal?: string | boolean | HTMLElement | undefined;
  /**
   * Enable virtualization for large lists.
   * Note: when enabled, all groups are flattened into a single list due to a limitation of Reka UI (https://github.com/unovue/reka-ui/issues/1885).
   * @default "false"
   */
  virtualize?: boolean | { overscan?: number | undefined; estimateSize?: number | ((index: number) => number) | undefined; } | undefined;
  /**
   * When `items` is an array of objects, select the field to use as the value instead of the object itself.
   */
  valueKey?: VK | undefined;
  /**
   * When `items` is an array of objects, select the field to use as the label.
   * @default "\"label\""
   */
  labelKey?: GetItemKeys<T> | undefined;
  /**
   * When `items` is an array of objects, select the field to use as the description.
   * @default "\"description\""
   */
  descriptionKey?: GetItemKeys<T> | undefined;
  items?: T | undefined;
  /**
   * The value of the SelectMenu when initially rendered. Use when you do not need to control the state of the SelectMenu.
   */
  defaultValue?: _Number<_Optional<_Nullable<GetModelValue<T, VK, M, ExcludeItem>, Mod>, Mod>, Mod> | IsClearUsed<M, C> | undefined;
  /**
   * The controlled value of the SelectMenu. Can be binded-with with `v-model`.
   */
  modelValue?: _Number<_Optional<_Nullable<GetModelValue<T, VK, M, ExcludeItem>, Mod>, Mod>, Mod> | IsClearUsed<M, C> | undefined;
  modelModifiers?: Mod | undefined;
  /**
   * Whether multiple options can be selected or not.
   */
  multiple?: M | undefined;
  /**
   * Highlight the ring color like a focus state.
   */
  highlight?: boolean | undefined;
  /**
   * Determines if custom user input that does not exist in options can be added.
   */
  createItem?: boolean | "always" | { position?: "top" | "bottom" | undefined; when?: "always" | "empty" | undefined; } | undefined;
  /**
   * Fields to filter items by.
   */
  filterFields?: string[] | undefined;
  /**
   * When `true`, disable the default filters, useful for custom filtering (useAsyncData, useFetch, etc.).
   */
  ignoreFilter?: boolean | undefined;
  autofocus?: boolean | undefined;
  /**
   * @default "0"
   */
  autofocusDelay?: number | undefined;
  ui?: { base?: SlotClass; leading?: SlotClass; leadingIcon?: SlotClass; leadingAvatar?: SlotClass; leadingAvatarSize?: SlotClass; trailing?: SlotClass; trailingIcon?: SlotClass; value?: SlotClass; placeholder?: SlotClass; arrow?: SlotClass; content?: SlotClass; viewport?: SlotClass; group?: SlotClass; empty?: SlotClass; label?: SlotClass; separator?: SlotClass; item?: SlotClass; itemLeadingIcon?: SlotClass; itemLeadingAvatar?: SlotClass; itemLeadingAvatarSize?: SlotClass; itemLeadingChip?: SlotClass; itemLeadingChipSize?: SlotClass; itemTrailing?: SlotClass; itemTrailingIcon?: SlotClass; itemWrapper?: SlotClass; itemLabel?: SlotClass; itemDescription?: SlotClass; input?: SlotClass; focusScope?: SlotClass; trailingClear?: SlotClass; } | undefined;
  /**
   * When `true`, prevents the user from interacting with listbox
   */
  disabled?: boolean | undefined;
  /**
   * The controlled open state of the Combobox. Can be binded with `v-model:open`.
   */
  open?: boolean | undefined;
  /**
   * The open state of the combobox when it is initially rendered. <br> Use when you do not need to control its open state.
   */
  defaultOpen?: boolean | undefined;
  /**
   * The name of the field. Submitted with its owning form as part of a name/value pair.
   */
  name?: string | undefined;
  /**
   * Whether to reset the searchTerm when the Combobox input blurred
   * @default "true"
   */
  resetSearchTermOnBlur?: boolean | undefined;
  /**
   * Whether to reset the searchTerm when the Combobox value is selected
   * @default "true"
   */
  resetSearchTermOnSelect?: boolean | undefined;
  /**
   * When `true` the `modelValue` will be reset to `null` (or `[]` if `multiple`)
   * @default "true"
   */
  resetModelValueOnClear?: boolean | undefined;
  /**
   * When `true`, hover over item will trigger highlight
   */
  highlightOnHover?: boolean | undefined;
  /**
   * Use this to compare objects by a particular field, or pass your own comparison function for complete control over how objects are compared.
   */
  by?: string | ((a: T, b: T) => boolean) | undefined;
  /**
   * Display an icon based on the `leading` and `trailing` props.
   */
  icon?: any;
  /**
   * Display an avatar on the left side.
   */
  avatar?: AvatarProps | undefined;
  /**
   * When `true`, the icon will be displayed on the left side.
   */
  leading?: boolean | undefined;
  /**
   * Display an icon on the left side.
   */
  leadingIcon?: any;
  /**
   * When `true`, the icon will be displayed on the right side.
   */
  trailing?: boolean | undefined;
  /**
   * When `true`, the loading icon will be displayed.
   */
  loading?: boolean | undefined;
  /**
   * The icon when the `loading` prop is `true`.
   */
  loadingIcon?: any;
  form?: string | undefined;
  formaction?: string | undefined;
  formenctype?: string | undefined;
  formmethod?: string | undefined;
  formnovalidate?: Booleanish | undefined;
  formtarget?: string | undefined;
  /**
   * @default "\"\""
   */
  searchTerm?: string | undefined;
}
```

> [!NOTE]
> See: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/button#attributes
> 
> This component also supports all native `<button>` HTML attributes.

### Slots

```ts
/**
 * Slots for the SelectMenu component
 */
interface SelectMenuSlots {
  leading(): any;
  default(): any;
  trailing(): any;
  empty(): any;
  item(): any;
  item-leading(): any;
  item-label(): any;
  item-description(): any;
  item-trailing(): any;
  content-top(): any;
  content-bottom(): any;
  create-item-label(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the SelectMenu component
 */
interface SelectMenuEmits {
  update:open: (payload: [value: boolean]) => void;
  change: (payload: [event: Event]) => void;
  blur: (payload: [event: FocusEvent]) => void;
  focus: (payload: [event: FocusEvent]) => void;
  create: (payload: [item: string]) => void;
  clear: (payload: []) => void;
  highlight: (payload: [payload: { ref: HTMLElement; value: _Number<_Optional<_Nullable<GetModelValue<T, VK, M, ExcludeItem>, Mod>, Mod>, Mod> | IsClearUsed<M, C>; } | undefined]) => void;
  update:modelValue: (payload: [value: _Number<_Optional<_Nullable<GetModelValue<T, VK, M, ExcludeItem>, Mod>, Mod>, Mod> | IsClearUsed<M, C>]) => void;
  update:searchTerm: (payload: [value: string]) => void;
}
```

### Expose

When accessing the component via a template ref, you can use the following:

<table>
<thead>
  <tr>
    <th>
      Name
    </th>
    
    <th>
      Type
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          triggerRef
        </span>
      </code>
    </td>
    
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          Ref
        </span>
        
        <span class="sMK4o">
          <
        </span>
        
        <span class="sBMFI">
          HTMLButtonElement
        </span>
        
        <span class="sMK4o">
          |
        </span>
        
        <span class="sBMFI">
          null
        </span>
        
        <span class="sMK4o">
          >
        </span>
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          viewportRef
        </span>
      </code>
    </td>
    
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          Ref
        </span>
        
        <span class="sMK4o">
          <
        </span>
        
        <span class="sBMFI">
          HTMLDivElement
        </span>
        
        <span class="sMK4o">
          |
        </span>
        
        <span class="sBMFI">
          null
        </span>
        
        <span class="sMK4o">
          >
        </span>
      </code>
    </td>
  </tr>
</tbody>
</table>

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    selectMenu: {
      slots: {
        base: [
          'relative group rounded-md inline-flex items-center disabled:cursor-not-allowed disabled:opacity-75',
          'transition-colors'
        ],
        leading: 'absolute inset-y-0 start-0 flex items-center',
        leadingIcon: 'shrink-0 text-dimmed',
        leadingAvatar: 'shrink-0',
        leadingAvatarSize: '',
        trailing: 'absolute inset-y-0 end-0 flex items-center',
        trailingIcon: 'shrink-0 text-dimmed',
        value: 'truncate pointer-events-none',
        placeholder: 'truncate text-dimmed',
        arrow: 'fill-bg stroke-default',
        content: [
          'max-h-[min(15rem,var(--reka-select-content-available-height,15rem))] w-(--reka-select-trigger-width) bg-default shadow-lg rounded-md ring ring-default overflow-hidden origin-(--reka-select-content-transform-origin) pointer-events-auto flex flex-col',
          'max-h-[min(15rem,var(--reka-combobox-content-available-height,15rem))] origin-(--reka-combobox-content-transform-origin) w-(--reka-combobox-trigger-width)'
        ],
        viewport: 'relative scroll-py-1 overflow-y-auto flex-1',
        group: 'p-1 isolate',
        empty: 'text-center text-muted',
        label: 'font-semibold text-highlighted',
        separator: '-mx-1 my-1 h-px bg-border',
        item: [
          'group relative w-full flex items-start select-none outline-none before:absolute before:z-[-1] before:inset-px before:rounded-md data-disabled:cursor-not-allowed data-disabled:opacity-75 text-default data-highlighted:not-data-disabled:text-highlighted data-highlighted:not-data-disabled:before:bg-elevated/50',
          'transition-colors before:transition-colors'
        ],
        itemLeadingIcon: [
          'shrink-0 text-dimmed group-data-highlighted:not-group-data-disabled:text-default',
          'transition-colors'
        ],
        itemLeadingAvatar: 'shrink-0',
        itemLeadingAvatarSize: '',
        itemLeadingChip: 'shrink-0',
        itemLeadingChipSize: '',
        itemTrailing: 'ms-auto inline-flex gap-1.5 items-center',
        itemTrailingIcon: 'shrink-0',
        itemWrapper: 'flex-1 flex flex-col min-w-0',
        itemLabel: 'truncate',
        itemDescription: 'truncate text-muted',
        input: 'border-b border-default',
        focusScope: 'flex flex-col min-h-0',
        trailingClear: 'p-0'
      },
      variants: {
        fieldGroup: {
          horizontal: 'not-only:first:rounded-e-none not-only:last:rounded-s-none not-last:not-first:rounded-none focus-visible:z-[1]',
          vertical: 'not-only:first:rounded-b-none not-only:last:rounded-t-none not-last:not-first:rounded-none focus-visible:z-[1]'
        },
        size: {
          xs: {
            base: 'px-2 py-1 text-xs gap-1',
            leading: 'ps-2',
            trailing: 'pe-2',
            leadingIcon: 'size-4',
            leadingAvatarSize: '3xs',
            trailingIcon: 'size-4',
            label: 'p-1 text-[10px]/3 gap-1',
            item: 'p-1 text-xs gap-1',
            itemLeadingIcon: 'size-4',
            itemLeadingAvatarSize: '3xs',
            itemLeadingChip: 'size-4',
            itemLeadingChipSize: 'sm',
            itemTrailingIcon: 'size-4',
            empty: 'p-2 text-xs'
          },
          sm: {
            base: 'px-2.5 py-1.5 text-xs gap-1.5',
            leading: 'ps-2.5',
            trailing: 'pe-2.5',
            leadingIcon: 'size-4',
            leadingAvatarSize: '3xs',
            trailingIcon: 'size-4',
            label: 'p-1.5 text-[10px]/3 gap-1.5',
            item: 'p-1.5 text-xs gap-1.5',
            itemLeadingIcon: 'size-4',
            itemLeadingAvatarSize: '3xs',
            itemLeadingChip: 'size-4',
            itemLeadingChipSize: 'sm',
            itemTrailingIcon: 'size-4',
            empty: 'p-2.5 text-xs'
          },
          md: {
            base: 'px-2.5 py-1.5 text-sm gap-1.5',
            leading: 'ps-2.5',
            trailing: 'pe-2.5',
            leadingIcon: 'size-5',
            leadingAvatarSize: '2xs',
            trailingIcon: 'size-5',
            label: 'p-1.5 text-xs gap-1.5',
            item: 'p-1.5 text-sm gap-1.5',
            itemLeadingIcon: 'size-5',
            itemLeadingAvatarSize: '2xs',
            itemLeadingChip: 'size-5',
            itemLeadingChipSize: 'md',
            itemTrailingIcon: 'size-5',
            empty: 'p-2.5 text-sm'
          },
          lg: {
            base: 'px-3 py-2 text-sm gap-2',
            leading: 'ps-3',
            trailing: 'pe-3',
            leadingIcon: 'size-5',
            leadingAvatarSize: '2xs',
            trailingIcon: 'size-5',
            label: 'p-2 text-xs gap-2',
            item: 'p-2 text-sm gap-2',
            itemLeadingIcon: 'size-5',
            itemLeadingAvatarSize: '2xs',
            itemLeadingChip: 'size-5',
            itemLeadingChipSize: 'md',
            itemTrailingIcon: 'size-5',
            empty: 'p-3 text-sm'
          },
          xl: {
            base: 'px-3 py-2 text-base gap-2',
            leading: 'ps-3',
            trailing: 'pe-3',
            leadingIcon: 'size-6',
            leadingAvatarSize: 'xs',
            trailingIcon: 'size-6',
            label: 'p-2 text-sm gap-2',
            item: 'p-2 text-base gap-2',
            itemLeadingIcon: 'size-6',
            itemLeadingAvatarSize: 'xs',
            itemLeadingChip: 'size-6',
            itemLeadingChipSize: 'lg',
            itemTrailingIcon: 'size-6',
            empty: 'p-3 text-base'
          }
        },
        variant: {
          outline: 'text-highlighted bg-default ring ring-inset ring-accented hover:bg-elevated disabled:bg-default',
          soft: 'text-highlighted bg-elevated/50 hover:bg-elevated focus:bg-elevated disabled:bg-elevated/50',
          subtle: 'text-highlighted bg-elevated ring ring-inset ring-accented hover:bg-accented/75 disabled:bg-elevated',
          ghost: 'text-highlighted bg-transparent hover:bg-elevated focus:bg-elevated disabled:bg-transparent dark:disabled:bg-transparent',
          none: 'text-highlighted bg-transparent focus:outline-none'
        },
        color: {
          primary: '',
          secondary: '',
          success: '',
          info: '',
          warning: '',
          error: '',
          neutral: ''
        },
        leading: {
          true: ''
        },
        trailing: {
          true: ''
        },
        loading: {
          true: ''
        },
        highlight: {
          true: ''
        },
        fixed: {
          false: ''
        },
        type: {
          file: 'file:me-1.5 file:font-medium file:text-muted file:outline-none'
        },
        position: {
          popper: {
            content: 'data-[state=open]:animate-[scale-in_100ms_ease-out] data-[state=closed]:animate-[scale-out_100ms_ease-in]'
          },
          'item-aligned': {
            content: ''
          }
        },
        multiple: {
          true: ''
        },
        virtualize: {
          true: {
            viewport: 'p-1 isolate'
          },
          false: {
            viewport: 'divide-y divide-default'
          }
        }
      },
      compoundVariants: [
        {
          color: 'primary',
          variant: [
            'outline',
            'subtle'
          ],
          class: 'outline-primary/25 focus-visible:outline-3 focus-visible:ring-primary'
        },
        {
          color: 'secondary',
          variant: [
            'outline',
            'subtle'
          ],
          class: 'outline-secondary/25 focus-visible:outline-3 focus-visible:ring-secondary'
        },
        {
          color: 'success',
          variant: [
            'outline',
            'subtle'
          ],
          class: 'outline-success/25 focus-visible:outline-3 focus-visible:ring-success'
        },
        {
          color: 'info',
          variant: [
            'outline',
            'subtle'
          ],
          class: 'outline-info/25 focus-visible:outline-3 focus-visible:ring-info'
        },
        {
          color: 'warning',
          variant: [
            'outline',
            'subtle'
          ],
          class: 'outline-warning/25 focus-visible:outline-3 focus-visible:ring-warning'
        },
        {
          color: 'error',
          variant: [
            'outline',
            'subtle'
          ],
          class: 'outline-error/25 focus-visible:outline-3 focus-visible:ring-error'
        },
        {
          color: 'primary',
          variant: [
            'soft',
            'ghost'
          ],
          class: 'outline-primary/25 focus-visible:outline-3'
        },
        {
          color: 'secondary',
          variant: [
            'soft',
            'ghost'
          ],
          class: 'outline-secondary/25 focus-visible:outline-3'
        },
        {
          color: 'success',
          variant: [
            'soft',
            'ghost'
          ],
          class: 'outline-success/25 focus-visible:outline-3'
        },
        {
          color: 'info',
          variant: [
            'soft',
            'ghost'
          ],
          class: 'outline-info/25 focus-visible:outline-3'
        },
        {
          color: 'warning',
          variant: [
            'soft',
            'ghost'
          ],
          class: 'outline-warning/25 focus-visible:outline-3'
        },
        {
          color: 'error',
          variant: [
            'soft',
            'ghost'
          ],
          class: 'outline-error/25 focus-visible:outline-3'
        },
        {
          color: 'primary',
          highlight: true,
          class: 'ring ring-inset ring-primary'
        },
        {
          color: 'secondary',
          highlight: true,
          class: 'ring ring-inset ring-secondary'
        },
        {
          color: 'success',
          highlight: true,
          class: 'ring ring-inset ring-success'
        },
        {
          color: 'info',
          highlight: true,
          class: 'ring ring-inset ring-info'
        },
        {
          color: 'warning',
          highlight: true,
          class: 'ring ring-inset ring-warning'
        },
        {
          color: 'error',
          highlight: true,
          class: 'ring ring-inset ring-error'
        },
        {
          color: 'neutral',
          variant: [
            'outline',
            'subtle'
          ],
          class: 'outline-inverted/25 focus-visible:outline-3 focus-visible:ring-inverted'
        },
        {
          color: 'neutral',
          variant: [
            'soft',
            'ghost'
          ],
          class: 'outline-inverted/25 focus-visible:outline-3'
        },
        {
          color: 'neutral',
          highlight: true,
          class: 'ring ring-inset ring-inverted'
        },
        {
          leading: true,
          size: 'xs',
          class: 'ps-7'
        },
        {
          leading: true,
          size: 'sm',
          class: 'ps-8'
        },
        {
          leading: true,
          size: 'md',
          class: 'ps-9'
        },
        {
          leading: true,
          size: 'lg',
          class: 'ps-10'
        },
        {
          leading: true,
          size: 'xl',
          class: 'ps-11'
        },
        {
          trailing: true,
          size: 'xs',
          class: 'pe-7'
        },
        {
          trailing: true,
          size: 'sm',
          class: 'pe-8'
        },
        {
          trailing: true,
          size: 'md',
          class: 'pe-9'
        },
        {
          trailing: true,
          size: 'lg',
          class: 'pe-10'
        },
        {
          trailing: true,
          size: 'xl',
          class: 'pe-11'
        },
        {
          loading: true,
          leading: true,
          class: {
            leadingIcon: 'animate-spin'
          }
        },
        {
          loading: true,
          leading: false,
          trailing: true,
          class: {
            trailingIcon: 'animate-spin'
          }
        },
        {
          fixed: false,
          size: 'xs',
          class: 'md:text-xs'
        },
        {
          fixed: false,
          size: 'sm',
          class: 'md:text-xs'
        },
        {
          fixed: false,
          size: 'md',
          class: 'md:text-sm'
        },
        {
          fixed: false,
          size: 'lg',
          class: 'md:text-sm'
        }
      ],
      defaultVariants: {
        size: 'md',
        color: 'primary',
        variant: 'outline',
        position: 'popper'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/SelectMenu.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/select-menu.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

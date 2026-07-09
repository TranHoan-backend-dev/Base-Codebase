---
title: "Listbox"
description: "A selectable list of items with search, virtualization and rich item rendering."
canonical_url: "https://ui.nuxt.com/docs/components/listbox"
---
# Listbox

> A selectable list of items with search, virtualization and rich item rendering.

## Usage

Use the `v-model` directive to control the value of the Listbox or the `default-value` prop to set the initial value when you do not need to control its state.

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[]>([
  {
    label: "France",
    icon: "i-lucide-map-pin",
    value: "FR"
  },
  {
    label: "Germany",
    icon: "i-lucide-map-pin",
    value: "DE"
  },
  {
    label: "Italy",
    icon: "i-lucide-map-pin",
    value: "IT"
  },
  {
    label: "Spain",
    icon: "i-lucide-map-pin",
    value: "ES"
  },
  {
    label: "Netherlands",
    icon: "i-lucide-map-pin",
    value: "NL"
  },
  {
    label: "Poland",
    icon: "i-lucide-map-pin",
    value: "PL"
  },
  {
    label: "Belgium",
    icon: "i-lucide-map-pin",
    value: "BE"
  },
  {
    label: "Portugal",
    icon: "i-lucide-map-pin",
    value: "PT"
  },
  {
    label: "Austria",
    icon: "i-lucide-map-pin",
    value: "AT"
  },
  {
    label: "Sweden",
    icon: "i-lucide-map-pin",
    value: "SE"
  }
])
const value = ref({
  label: "France",
  icon: "i-lucide-map-pin",
  value: "FR"
})
</script>

<template>
  <UListbox v-model="value" :items="items" />
</template>
```

### Items

Use the `items` prop as an array of objects with the following properties:

- `label?: string`
- [`description?: string`](#with-description-in-items)
- [`type?: "label" | "separator" | "item"`](#with-items-type)
- [`icon?: string`](#with-icon-in-items)
- [`avatar?: AvatarProps`](#with-avatar-in-items)
- [`chip?: ChipProps`](#with-chip-in-items)
- `disabled?: boolean`
- `onSelect?: (e: Event) => void`
- `class?: any`
- `ui?: { label?: ClassNameValue, separator?: ClassNameValue, item?: ClassNameValue, itemLeadingIcon?: ClassNameValue, ... }`

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[]>([
  {
    label: "France",
    description: "The Hexagon",
    icon: "i-lucide-map-pin",
    value: "FR"
  },
  {
    label: "Germany",
    description: "The Federal Republic",
    icon: "i-lucide-map-pin",
    value: "DE"
  },
  {
    label: "Italy",
    description: "The Boot",
    icon: "i-lucide-map-pin",
    value: "IT"
  },
  {
    label: "Spain",
    description: "The Bull Skin",
    icon: "i-lucide-map-pin",
    value: "ES"
  }
])
</script>

<template>
  <UListbox :items="items" />
</template>
```

You can also pass an array of arrays to the `items` prop to display separated groups of items.

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[][]>([
  [
    {
      label: "France",
      icon: "i-lucide-map-pin",
      value: "FR"
    },
    {
      label: "Germany",
      icon: "i-lucide-map-pin",
      value: "DE"
    },
    {
      label: "Italy",
      icon: "i-lucide-map-pin",
      value: "IT"
    }
  ],
  [
    {
      label: "Brazil",
      icon: "i-lucide-map-pin",
      value: "BR"
    },
    {
      label: "Argentina",
      icon: "i-lucide-map-pin",
      value: "AR"
    }
  ]
])
</script>

<template>
  <UListbox :items="items" />
</template>
```

### Multiple

Use the `multiple` prop to allow selecting multiple items. When enabled, the `v-model` will be an array.

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[]>([
  {
    label: "France",
    icon: "i-lucide-map-pin",
    value: "FR"
  },
  {
    label: "Germany",
    icon: "i-lucide-map-pin",
    value: "DE"
  },
  {
    label: "Italy",
    icon: "i-lucide-map-pin",
    value: "IT"
  },
  {
    label: "Spain",
    icon: "i-lucide-map-pin",
    value: "ES"
  }
])
</script>

<template>
  <UListbox multiple :items="items" />
</template>
```

### Value Key

You can choose to bind a single property of the object rather than the whole object by using the `value-key` prop. Defaults to `undefined`.

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[]>([
  {
    label: "France",
    icon: "i-lucide-map-pin",
    value: "FR"
  },
  {
    label: "Germany",
    icon: "i-lucide-map-pin",
    value: "DE"
  },
  {
    label: "Italy",
    icon: "i-lucide-map-pin",
    value: "IT"
  },
  {
    label: "Spain",
    icon: "i-lucide-map-pin",
    value: "ES"
  }
])
const value = ref("FR")
</script>

<template>
  <UListbox v-model="value" value-key="value" :items="items" class="w-full" />
</template>
```

### Filter

Use the `filter` prop to display a filter input or pass an object to customize the [Input](/docs/components/input) component. Defaults to `false`.

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[]>([
  {
    label: "France",
    icon: "i-lucide-map-pin",
    value: "FR"
  },
  {
    label: "Germany",
    icon: "i-lucide-map-pin",
    value: "DE"
  },
  {
    label: "Italy",
    icon: "i-lucide-map-pin",
    value: "IT"
  },
  {
    label: "Spain",
    icon: "i-lucide-map-pin",
    value: "ES"
  },
  {
    label: "Netherlands",
    icon: "i-lucide-map-pin",
    value: "NL"
  },
  {
    label: "Poland",
    icon: "i-lucide-map-pin",
    value: "PL"
  }
])
</script>

<template>
  <UListbox :filter="{
  placeholder: 'Filter...',
  icon: 'i-lucide-search'
}" :items="items" />
</template>
```

### Selected Icon

Use the `selected-icon` prop to customize the icon when an item is selected. Defaults to `i-lucide-check`.

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[]>([
  {
    label: "France",
    icon: "i-lucide-map-pin",
    value: "FR"
  },
  {
    label: "Germany",
    icon: "i-lucide-map-pin",
    value: "DE"
  },
  {
    label: "Italy",
    icon: "i-lucide-map-pin",
    value: "IT"
  },
  {
    label: "Spain",
    icon: "i-lucide-map-pin",
    value: "ES"
  }
])
const value = ref("FR")
</script>

<template>
  <UListbox v-model="value" selected-icon="i-lucide-flame" value-key="value" :items="items" class="w-full" />
</template>
```

### Size

Use the `size` prop to change the size of the Listbox.

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[]>([
  {
    label: "France",
    icon: "i-lucide-map-pin",
    value: "FR"
  },
  {
    label: "Germany",
    icon: "i-lucide-map-pin",
    value: "DE"
  },
  {
    label: "Italy",
    icon: "i-lucide-map-pin",
    value: "IT"
  },
  {
    label: "Spain",
    icon: "i-lucide-map-pin",
    value: "ES"
  }
])
</script>

<template>
  <UListbox size="xl" :items="items" />
</template>
```

### Loading

Use the `loading` prop to display a loading indicator. Use the `loading-icon` prop to customize the icon.

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[]>([
  {
    label: "France",
    icon: "i-lucide-map-pin",
    value: "FR"
  },
  {
    label: "Germany",
    icon: "i-lucide-map-pin",
    value: "DE"
  }
])
</script>

<template>
  <UListbox loading :items="items" />
</template>
```

### Disabled

Use the `disabled` prop to prevent any user interaction with the Listbox.

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[]>([
  {
    label: "France",
    icon: "i-lucide-map-pin",
    value: "FR"
  },
  {
    label: "Germany",
    icon: "i-lucide-map-pin",
    value: "DE"
  },
  {
    label: "Italy",
    icon: "i-lucide-map-pin",
    value: "IT"
  },
  {
    label: "Spain",
    icon: "i-lucide-map-pin",
    value: "ES"
  }
])
</script>

<template>
  <UListbox disabled :items="items" />
</template>
```

## Examples

### With items type

You can use the `type` property with `separator` to display a separator between items or `label` to display a label.

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[][]>([
  [
    {
      type: "label",
      label: "Fruits"
    },
    {
      label: "Apple"
    },
    {
      label: "Banana"
    },
    {
      label: "Blueberry"
    },
    {
      label: "Grapes"
    },
    {
      label: "Pineapple"
    }
  ],
  [
    {
      type: "label",
      label: "Vegetables"
    },
    {
      label: "Aubergine"
    },
    {
      label: "Broccoli"
    },
    {
      label: "Carrot"
    },
    {
      label: "Courgette"
    },
    {
      label: "Leek"
    }
  ]
])
</script>

<template>
  <UListbox :items="items" />
</template>
```

### With icon in items

You can use the `icon` property to display an [Icon](/docs/components/icon) inside the items.

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[]>([
  {
    label: "Backlog",
    icon: "i-lucide-circle-help",
    value: "backlog"
  },
  {
    label: "Todo",
    icon: "i-lucide-circle-plus",
    value: "todo"
  },
  {
    label: "In Progress",
    icon: "i-lucide-circle-arrow-up",
    value: "in_progress"
  },
  {
    label: "Done",
    icon: "i-lucide-circle-check",
    value: "done"
  }
])
</script>

<template>
  <UListbox :items="items" />
</template>
```

### With avatar in items

You can use the `avatar` property to display an [Avatar](/docs/components/avatar) inside the items.

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[]>([
  {
    label: "benjamincanac",
    avatar: {
      src: "https://github.com/benjamincanac.png"
    }
  },
  {
    label: "romhml",
    avatar: {
      src: "https://github.com/romhml.png"
    }
  },
  {
    label: "atinux",
    avatar: {
      src: "https://github.com/atinux.png"
    }
  },
  {
    label: "HugoRCD",
    avatar: {
      src: "https://github.com/HugoRCD.png"
    }
  }
])
</script>

<template>
  <UListbox :items="items" />
</template>
```

### With chip in items

You can use the `chip` property to display a [Chip](/docs/components/chip) inside the items.

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[]>([
  {
    label: "bug",
    chip: {
      color: "error"
    }
  },
  {
    label: "feature",
    chip: {
      color: "success"
    }
  },
  {
    label: "enhancement",
    chip: {
      color: "info"
    }
  }
])
</script>

<template>
  <UListbox :items="items" />
</template>
```

### With description in items

You can use the `description` property to display additional text below the label.

```vue
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items = ref<ListboxItem[]>([
  {
    label: "France",
    description: "The Hexagon",
    icon: "i-lucide-map-pin",
    value: "FR"
  },
  {
    label: "Germany",
    description: "The Federal Republic",
    icon: "i-lucide-map-pin",
    value: "DE"
  },
  {
    label: "Italy",
    description: "The Boot",
    icon: "i-lucide-map-pin",
    value: "IT"
  },
  {
    label: "Spain",
    description: "The Bull Skin",
    icon: "i-lucide-map-pin",
    value: "ES"
  }
])
</script>

<template>
  <UListbox :items="items" />
</template>
```

### Control selected items

You can control the selected item by using the `default-value` prop or the `v-model` directive.

```vue [ListboxModelValueExample.vue]
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items: ListboxItem[] = [
  { label: 'France', icon: 'i-lucide-map-pin', value: 'FR' },
  { label: 'Germany', icon: 'i-lucide-map-pin', value: 'DE' },
  { label: 'Italy', icon: 'i-lucide-map-pin', value: 'IT' },
  { label: 'Spain', icon: 'i-lucide-map-pin', value: 'ES' },
  { label: 'Netherlands', icon: 'i-lucide-map-pin', value: 'NL' },
  { label: 'Poland', icon: 'i-lucide-map-pin', value: 'PL' }
]

const value = ref<ListboxItem>(items[2]!)
</script>

<template>
  <UListbox
    v-model="value"
    :items="items"
    class="w-full"
  />
</template>
```

### Control search term

Use the `v-model:search-term` directive to control the search term.

```vue [ListboxSearchTermExample.vue]
<script setup lang="ts">
const searchTerm = ref('F')
const items = ref([
  { label: 'France', icon: 'i-lucide-map-pin', value: 'FR' },
  { label: 'Germany', icon: 'i-lucide-map-pin', value: 'DE' },
  { label: 'Italy', icon: 'i-lucide-map-pin', value: 'IT' },
  { label: 'Spain', icon: 'i-lucide-map-pin', value: 'ES' },
  { label: 'Netherlands', icon: 'i-lucide-map-pin', value: 'NL' },
  { label: 'Poland', icon: 'i-lucide-map-pin', value: 'PL' }
])
const value = ref()
</script>

<template>
  <UListbox v-model="value" v-model:search-term="searchTerm" filter :items="items" class="w-full" />
</template>
```

### With ignore filter

Set the `ignore-filter` prop to `true` to disable the internal search and use your own search logic.

```vue [ListboxIgnoreFilterExample.vue]
<script setup lang="ts">
import { refDebounced } from '@vueuse/core'

const searchTerm = ref('')
const searchTermDebounced = refDebounced(searchTerm, 200)

const { data: users, status, execute } = await useLazyFetch('https://jsonplaceholder.typicode.com/users', {
  key: 'listbox-users-search',
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

onMounted(() => {
  execute()
})
</script>

<template>
  <UListbox
    v-model:search-term="searchTerm"
    :items="users || []"
    :filter="{
      icon: 'i-lucide-search',
      loading: status === 'pending'
    }"
    ignore-filter
    class="w-full"
  />
</template>
```

> [!NOTE]
> 
> This example uses [`refDebounced`](https://vueuse.org/shared/refDebounced/#refdebounced) to debounce the API calls.

### With filter fields

Use the `filter-fields` prop with an array of fields to filter on. Defaults to `[labelKey]`.

```vue [ListboxFilterFieldsExample.vue]
<script setup lang="ts">
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

onMounted(() => {
  execute()
})
</script>

<template>
  <UListbox
    :items="users || []"
    :loading="status === 'pending'"
    :filter-fields="['label', 'email']"
    filter
    class="w-full"
  >
    <template #item-label="{ item }">
      {{ item.label }}

      <span class="text-muted">
        {{ item.email }}
      </span>
    </template>
  </UListbox>
</template>
```

### With virtualization

Use the `virtualize` prop to enable virtualization for large lists as a boolean or an object with options like `{ estimateSize: 32, overscan: 12 }`.

```vue [ListboxVirtualizeExample.vue]
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items: ListboxItem[] = Array.from({ length: 1000 }, (_, i) => ({
  label: `Item ${i + 1}`,
  icon: 'i-lucide-file',
  value: i + 1
}))

const value = ref<ListboxItem[]>([])
</script>

<template>
  <UListbox
    v-model="value"
    :items="items"
    multiple
    virtualize
    class="w-full"
  />
</template>
```

### As a transfer list

You can compose two Listbox components with [Button](/docs/components/button) controls to build a transfer list pattern.

```vue [ListboxTransferListExample.vue]
<script setup lang="ts">
import type { ListboxItem } from '@nuxt/ui'

const items: ListboxItem[] = [
  { label: 'France', icon: 'i-lucide-map-pin', value: 'FR' },
  { label: 'Germany', icon: 'i-lucide-map-pin', value: 'DE' },
  { label: 'Italy', icon: 'i-lucide-map-pin', value: 'IT' },
  { label: 'Spain', icon: 'i-lucide-map-pin', value: 'ES' },
  { label: 'Netherlands', icon: 'i-lucide-map-pin', value: 'NL' },
  { label: 'Poland', icon: 'i-lucide-map-pin', value: 'PL' },
  { label: 'Belgium', icon: 'i-lucide-map-pin', value: 'BE' },
  { label: 'Portugal', icon: 'i-lucide-map-pin', value: 'PT' }
]

const targetItems = ref<ListboxItem[]>([])
const sourceSelection = ref<ListboxItem[]>([])
const targetSelection = ref<ListboxItem[]>([])

const sourceItems = computed(() => items.filter(item => !targetItems.value.some(t => t.value === item.value)))

function transferSelected() {
  targetItems.value = [...targetItems.value, ...sourceSelection.value]
  sourceSelection.value = []
}

function removeSelected() {
  targetItems.value = targetItems.value.filter(item => !targetSelection.value.some(t => t.value === item.value))
  targetSelection.value = []
}
</script>

<template>
  <div class="flex items-stretch gap-4 w-full">
    <div class="flex flex-col flex-1 gap-1">
      <span class="text-sm font-medium text-highlighted">Available</span>

      <UListbox
        v-model="sourceSelection"
        :items="sourceItems"
        multiple
        filter
        class="size-full"
      />
    </div>

    <div class="flex flex-col items-center justify-center gap-1">
      <UButton
        icon="i-lucide-chevron-right"
        color="neutral"
        variant="outline"
        :disabled="!sourceSelection.length"
        @click="transferSelected"
      />
      <UButton
        icon="i-lucide-chevron-left"
        color="neutral"
        variant="outline"
        :disabled="!targetSelection.length"
        @click="removeSelected"
      />
    </div>

    <div class="flex flex-col flex-1 gap-1">
      <span class="text-sm font-medium text-highlighted">Selected</span>

      <UListbox
        v-model="targetSelection"
        :items="targetItems"
        multiple
        filter
        class="size-full"
      />
    </div>
  </div>
</template>
```

## API

### Props

```ts
/**
 * Props for the Listbox component
 */
interface ListboxProps {
  id?: string | undefined;
  /**
   * The element or component this component should render as.
   */
  as?: any;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  size?: "sm" | "md" | "xs" | "lg" | "xl" | undefined;
  /**
   * The items to display in the list.
   */
  items?: T | undefined;
  /**
   * The controlled value of the Listbox. Can be bound with `v-model`.
   */
  modelValue?: _Number<_Optional<_Nullable<GetModelValue<T, VK, M, undefined>, Mod>, Mod>, Mod> | undefined;
  modelModifiers?: Mod | undefined;
  /**
   * The default value when not controlled.
   */
  defaultValue?: _Number<_Optional<_Nullable<GetModelValue<T, VK, M, undefined>, Mod>, Mod>, Mod> | undefined;
  /**
   * Whether multiple items can be selected.
   */
  multiple?: M | undefined;
  /**
   * When `items` is an array of objects, select the field to use as the value instead of the object itself.
   */
  valueKey?: VK | undefined;
  /**
   * The key used to get the label from the item.
   * @default "\"label\""
   */
  labelKey?: GetItemKeys<T> | undefined;
  /**
   * The key used to get the description from the item.
   * @default "\"description\""
   */
  descriptionKey?: GetItemKeys<T> | undefined;
  /**
   * Whether the list is in a loading state.
   */
  loading?: boolean | undefined;
  /**
   * The icon displayed when loading.
   */
  loadingIcon?: any;
  /**
   * Whether to display a filter input or not.
   * Can be an object to pass additional props to the input.
   * `{ placeholder: 'Search...', variant: 'none' }`{lang="ts-type"}
   * @default "false"
   */
  filter?: boolean | Omit<InputProps<AcceptableValue, ModelModifiers>, "modelValue" | "defaultValue"> | undefined;
  /**
   * The fields to filter by.
   */
  filterFields?: string[] | undefined;
  /**
   * When `true`, disable the default filters, useful for custom filtering (useAsyncData, useFetch, etc.).
   */
  ignoreFilter?: boolean | undefined;
  /**
   * The icon displayed when an item is selected.
   */
  selectedIcon?: any;
  /**
   * Enable virtualization for large lists.
   * @default "false"
   */
  virtualize?: boolean | { overscan?: number | undefined; estimateSize?: number | ((index: number) => number) | undefined; } | undefined;
  /**
   * Highlight the ring color like a focus state.
   */
  highlight?: boolean | undefined;
  autofocus?: boolean | undefined;
  /**
   * @default "0"
   */
  autofocusDelay?: number | undefined;
  ui?: { root?: SlotClass; input?: SlotClass; content?: SlotClass; group?: SlotClass; label?: SlotClass; separator?: SlotClass; empty?: SlotClass; loading?: SlotClass; loadingIcon?: SlotClass; item?: SlotClass; itemLeadingIcon?: SlotClass; itemLeadingAvatar?: SlotClass; itemLeadingAvatarSize?: SlotClass; itemLeadingChip?: SlotClass; itemLeadingChipSize?: SlotClass; itemWrapper?: SlotClass; itemLabel?: SlotClass; itemDescription?: SlotClass; itemTrailing?: SlotClass; itemTrailingIcon?: SlotClass; } | undefined;
  /**
   * Use this to compare objects by a particular field, or pass your own comparison function for complete control over how objects are compared.
   */
  by?: string | ((a: AcceptableValue, b: AcceptableValue) => boolean) | undefined;
  /**
   * When `true`, prevents the user from interacting with listbox
   */
  disabled?: boolean | undefined;
  /**
   * When `true`, hover over item will trigger highlight
   * @default "true"
   */
  highlightOnHover?: boolean | undefined;
  /**
   * The name of the field. Submitted with its owning form as part of a name/value pair.
   */
  name?: string | undefined;
  /**
   * The orientation of the listbox. <br>Mainly so arrow navigation is done accordingly (left & right vs. up & down)
   */
  orientation?: DataOrientation | undefined;
  /**
   * When `true`, indicates that the user must set the value before the owning form can be submitted.
   */
  required?: boolean | undefined;
  /**
   * How multiple selection should behave in the collection.
   */
  selectionBehavior?: "replace" | "toggle" | undefined;
  /**
   * @default "\"\""
   */
  searchTerm?: string | undefined;
}
```

### Slots

```ts
/**
 * Slots for the Listbox component
 */
interface ListboxSlots {
  loading(): any;
  empty(): any;
  item(): any;
  item-leading(): any;
  item-label(): any;
  item-description(): any;
  item-trailing(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the Listbox component
 */
interface ListboxEmits {
  entryFocus: (payload: [event: CustomEvent<any>]) => void;
  highlight: (payload: [payload: { ref: HTMLElement; value: AcceptableValue; } | undefined]) => void;
  leave: (payload: [event: Event]) => void;
  change: (payload: [event: Event]) => void;
  update:modelValue: (payload: [value: _Number<_Optional<_Nullable<GetModelValue<T, VK, M, undefined>, Mod>, Mod>, Mod>]) => void;
  update:searchTerm: (payload: [value: string]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    listbox: {
      slots: {
        root: 'flex flex-col min-h-0 min-w-0 ring ring-inset ring-default rounded-lg overflow-hidden',
        input: 'border-b border-default',
        content: 'relative overflow-y-auto flex-1 max-h-60 scroll-py-1 focus:outline-none',
        group: 'p-1 isolate',
        label: 'font-semibold text-highlighted',
        separator: '-mx-1 my-1 h-px bg-border',
        empty: 'text-center text-muted',
        loading: 'flex items-center justify-center text-muted',
        loadingIcon: 'animate-spin shrink-0',
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
        itemWrapper: 'flex-1 flex flex-col min-w-0',
        itemLabel: 'truncate',
        itemDescription: 'truncate text-muted',
        itemTrailing: 'ms-auto inline-flex gap-1.5 items-center',
        itemTrailingIcon: 'shrink-0'
      },
      variants: {
        size: {
          xs: {
            label: 'p-1 text-[10px]/3 gap-1',
            empty: 'py-3 text-xs',
            loading: 'py-3',
            loadingIcon: 'size-4',
            item: 'p-1 text-xs gap-1',
            itemLeadingIcon: 'size-4',
            itemLeadingAvatarSize: '3xs',
            itemLeadingChip: 'size-4',
            itemLeadingChipSize: 'sm',
            itemTrailingIcon: 'size-4'
          },
          sm: {
            label: 'p-1.5 text-[10px]/3 gap-1.5',
            empty: 'py-4 text-xs',
            loading: 'py-4',
            loadingIcon: 'size-4',
            item: 'p-1.5 text-xs gap-1.5',
            itemLeadingIcon: 'size-4',
            itemLeadingAvatarSize: '3xs',
            itemLeadingChip: 'size-4',
            itemLeadingChipSize: 'sm',
            itemTrailingIcon: 'size-4'
          },
          md: {
            label: 'p-1.5 text-xs gap-1.5',
            empty: 'py-6 text-sm',
            loading: 'py-6',
            loadingIcon: 'size-5',
            item: 'p-1.5 text-sm gap-1.5',
            itemLeadingIcon: 'size-5',
            itemLeadingAvatarSize: '2xs',
            itemLeadingChip: 'size-5',
            itemLeadingChipSize: 'md',
            itemTrailingIcon: 'size-5'
          },
          lg: {
            label: 'p-2 text-xs gap-2',
            empty: 'py-7 text-sm',
            loading: 'py-7',
            loadingIcon: 'size-5',
            item: 'p-2 text-sm gap-2',
            itemLeadingIcon: 'size-5',
            itemLeadingAvatarSize: '2xs',
            itemLeadingChip: 'size-5',
            itemLeadingChipSize: 'md',
            itemTrailingIcon: 'size-5'
          },
          xl: {
            label: 'p-2 text-sm gap-2',
            empty: 'py-8 text-base',
            loading: 'py-8',
            loadingIcon: 'size-6',
            item: 'p-2 text-base gap-2',
            itemLeadingIcon: 'size-6',
            itemLeadingAvatarSize: 'xs',
            itemLeadingChip: 'size-6',
            itemLeadingChipSize: 'lg',
            itemTrailingIcon: 'size-6',
            itemDescription: 'text-sm'
          }
        },
        color: {
          primary: {
            root: 'outline-primary/25 has-focus-visible:outline-3 has-focus-visible:ring-primary'
          },
          secondary: {
            root: 'outline-secondary/25 has-focus-visible:outline-3 has-focus-visible:ring-secondary'
          },
          success: {
            root: 'outline-success/25 has-focus-visible:outline-3 has-focus-visible:ring-success'
          },
          info: {
            root: 'outline-info/25 has-focus-visible:outline-3 has-focus-visible:ring-info'
          },
          warning: {
            root: 'outline-warning/25 has-focus-visible:outline-3 has-focus-visible:ring-warning'
          },
          error: {
            root: 'outline-error/25 has-focus-visible:outline-3 has-focus-visible:ring-error'
          },
          neutral: {
            root: 'outline-inverted/25 has-focus-visible:outline-3 has-focus-visible:ring-inverted'
          }
        },
        virtualize: {
          true: {
            content: 'p-1 isolate'
          },
          false: {
            content: 'divide-y divide-default'
          }
        },
        disabled: {
          true: {
            root: 'opacity-75 cursor-not-allowed'
          }
        },
        highlight: {
          true: ''
        }
      },
      compoundVariants: [
        {
          color: 'primary',
          highlight: true,
          class: {
            root: 'ring ring-inset ring-primary'
          }
        },
        {
          color: 'secondary',
          highlight: true,
          class: {
            root: 'ring ring-inset ring-secondary'
          }
        },
        {
          color: 'success',
          highlight: true,
          class: {
            root: 'ring ring-inset ring-success'
          }
        },
        {
          color: 'info',
          highlight: true,
          class: {
            root: 'ring ring-inset ring-info'
          }
        },
        {
          color: 'warning',
          highlight: true,
          class: {
            root: 'ring ring-inset ring-warning'
          }
        },
        {
          color: 'error',
          highlight: true,
          class: {
            root: 'ring ring-inset ring-error'
          }
        },
        {
          color: 'neutral',
          highlight: true,
          class: {
            root: 'ring ring-inset ring-inverted'
          }
        }
      ],
      defaultVariants: {
        color: 'primary',
        size: 'md'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Listbox.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/listbox.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

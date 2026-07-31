---
title: "Tree"
description: "A tree view component to display and interact with hierarchical data structures."
canonical_url: "https://ui.nuxt.com/docs/components/tree"
---
# Tree

> A tree view component to display and interact with hierarchical data structures.

## Usage

Use the Tree component to display a hierarchical structure of items.

```vue
<script setup lang="ts">
import type { TreeItem } from '@nuxt/ui'

const items = ref<TreeItem[]>([
  {
    label: "app/",
    defaultExpanded: true,
    children: [
      {
        label: "composables/",
        children: [
          {
            label: "useAuth.ts",
            icon: "i-vscode-icons-file-type-typescript"
          },
          {
            label: "useUser.ts",
            icon: "i-vscode-icons-file-type-typescript"
          }
        ]
      },
      {
        label: "components/",
        defaultExpanded: true,
        children: [
          {
            label: "Card.vue",
            icon: "i-vscode-icons-file-type-vue"
          },
          {
            label: "Button.vue",
            icon: "i-vscode-icons-file-type-vue"
          }
        ]
      }
    ]
  },
  {
    label: "app.vue",
    icon: "i-vscode-icons-file-type-vue"
  },
  {
    label: "nuxt.config.ts",
    icon: "i-vscode-icons-file-type-nuxt"
  }
])
</script>

<template>
  <UTree :items="items" />
</template>
```

### Items

Use the `items` prop as an array of objects with the following properties:

- `icon?: string`
- `label?: string`
- `trailingIcon?: string`
- `defaultExpanded?: boolean`
- `disabled?: boolean`
- `slot?: string`
- `children?: TreeItem[]`
- `onToggle?: (e: TreeItemToggleEvent<TreeItem>) => void`
- `onSelect?: (e: TreeItemSelectEvent<TreeItem>) => void`
- `class?: any`
- `ui?: { item?: ClassNameValue, itemWithChildren?: ClassNameValue, link?: ClassNameValue, linkLeadingIcon?: ClassNameValue, linkLabel?: ClassNameValue, linkTrailing?: ClassNameValue, linkTrailingIcon?: ClassNameValue, listWithChildren?: ClassNameValue }`

> [!NOTE]
> 
> A unique identifier is required for each item. The component will use the `label` prop as identifier if no `get-key` is provided. Ideally you should provide a `get-key` function prop to return a unique identifier. Alternatively, you can use the `labelKey` prop to specify which property to use as the unique identifier.

```vue
<script setup lang="ts">
import type { TreeItem } from '@nuxt/ui'

const items = ref<TreeItem[]>([
  {
    label: "app/",
    defaultExpanded: true,
    children: [
      {
        label: "composables/",
        children: [
          {
            label: "useAuth.ts",
            icon: "i-vscode-icons-file-type-typescript"
          },
          {
            label: "useUser.ts",
            icon: "i-vscode-icons-file-type-typescript"
          }
        ]
      },
      {
        label: "components/",
        defaultExpanded: true,
        children: [
          {
            label: "Card.vue",
            icon: "i-vscode-icons-file-type-vue"
          },
          {
            label: "Button.vue",
            icon: "i-vscode-icons-file-type-vue"
          }
        ]
      }
    ]
  },
  {
    label: "app.vue",
    icon: "i-vscode-icons-file-type-vue"
  },
  {
    label: "nuxt.config.ts",
    icon: "i-vscode-icons-file-type-nuxt"
  }
])
</script>

<template>
  <UTree :items="items" />
</template>
```

### Multiple

Use the `multiple` prop to allow multiple item selections.

```vue
<script setup lang="ts">
import type { TreeItem } from '@nuxt/ui'

const items = ref<TreeItem[]>([
  {
    label: "app/",
    defaultExpanded: true,
    children: [
      {
        label: "composables/",
        children: [
          {
            label: "useAuth.ts",
            icon: "i-vscode-icons-file-type-typescript"
          },
          {
            label: "useUser.ts",
            icon: "i-vscode-icons-file-type-typescript"
          }
        ]
      },
      {
        label: "components/",
        defaultExpanded: true,
        children: [
          {
            label: "Card.vue",
            icon: "i-vscode-icons-file-type-vue"
          },
          {
            label: "Button.vue",
            icon: "i-vscode-icons-file-type-vue"
          }
        ]
      }
    ]
  },
  {
    label: "app.vue",
    icon: "i-vscode-icons-file-type-vue"
  },
  {
    label: "nuxt.config.ts",
    icon: "i-vscode-icons-file-type-nuxt"
  }
])
</script>

<template>
  <UTree multiple :items="items" />
</template>
```

### Nested `4.1+`

Use the `nested` prop to control whether the Tree is rendered with nested structure or as a flat list. Defaults to `true`.

```vue
<script setup lang="ts">
import type { TreeItem } from '@nuxt/ui'

const items = ref<TreeItem[]>([
  {
    label: "app/",
    defaultExpanded: true,
    children: [
      {
        label: "composables/",
        children: [
          {
            label: "useAuth.ts",
            icon: "i-vscode-icons-file-type-typescript"
          },
          {
            label: "useUser.ts",
            icon: "i-vscode-icons-file-type-typescript"
          }
        ]
      },
      {
        label: "components/",
        defaultExpanded: true,
        children: [
          {
            label: "Card.vue",
            icon: "i-vscode-icons-file-type-vue"
          },
          {
            label: "Button.vue",
            icon: "i-vscode-icons-file-type-vue"
          }
        ]
      }
    ]
  },
  {
    label: "app.vue",
    icon: "i-vscode-icons-file-type-vue"
  },
  {
    label: "nuxt.config.ts",
    icon: "i-vscode-icons-file-type-nuxt"
  }
])
</script>

<template>
  <UTree :nested="false" :items="items" />
</template>
```

> [!NOTE]
> See: #with-virtualization
> 
> When `nested` is `false`, all items are rendered at the same level with indentation to indicate hierarchy. This is useful for virtualization or drag and drop functionality.

### Color

Use the `color` prop to change the color of the Tree.

```vue
<script setup lang="ts">
import type { TreeItem } from '@nuxt/ui'

const items = ref<TreeItem[]>([
  {
    label: "app/",
    defaultExpanded: true,
    children: [
      {
        label: "composables/",
        children: [
          {
            label: "useAuth.ts",
            icon: "i-vscode-icons-file-type-typescript"
          },
          {
            label: "useUser.ts",
            icon: "i-vscode-icons-file-type-typescript"
          }
        ]
      },
      {
        label: "components/",
        defaultExpanded: true,
        children: [
          {
            label: "Card.vue",
            icon: "i-vscode-icons-file-type-vue"
          },
          {
            label: "Button.vue",
            icon: "i-vscode-icons-file-type-vue"
          }
        ]
      }
    ]
  },
  {
    label: "app.vue",
    icon: "i-vscode-icons-file-type-vue"
  },
  {
    label: "nuxt.config.ts",
    icon: "i-vscode-icons-file-type-nuxt"
  }
])
</script>

<template>
  <UTree color="neutral" :items="items" />
</template>
```

### Size

Use the `size` prop to change the size of the Tree.

```vue
<script setup lang="ts">
import type { TreeItem } from '@nuxt/ui'

const items = ref<TreeItem[]>([
  {
    label: "app/",
    defaultExpanded: true,
    children: [
      {
        label: "composables/",
        children: [
          {
            label: "useAuth.ts",
            icon: "i-vscode-icons-file-type-typescript"
          },
          {
            label: "useUser.ts",
            icon: "i-vscode-icons-file-type-typescript"
          }
        ]
      },
      {
        label: "components/",
        defaultExpanded: true,
        children: [
          {
            label: "Card.vue",
            icon: "i-vscode-icons-file-type-vue"
          },
          {
            label: "Button.vue",
            icon: "i-vscode-icons-file-type-vue"
          }
        ]
      }
    ]
  },
  {
    label: "app.vue",
    icon: "i-vscode-icons-file-type-vue"
  },
  {
    label: "nuxt.config.ts",
    icon: "i-vscode-icons-file-type-nuxt"
  }
])
</script>

<template>
  <UTree size="xl" :items="items" />
</template>
```

### Trailing Icon

Use the `trailing-icon` prop to customize the trailing [Icon](/docs/components/icon) of a parent node. Defaults to `i-lucide-chevron-down`.

> [!NOTE]
> 
> If an icon is specified for an item, it will always take precedence over these props.

```vue
<script setup lang="ts">
import type { TreeItem } from '@nuxt/ui'

const items = ref<TreeItem[]>([
  {
    label: "app/",
    defaultExpanded: true,
    children: [
      {
        label: "composables/",
        trailingIcon: "i-lucide-chevron-down",
        children: [
          {
            label: "useAuth.ts",
            icon: "i-vscode-icons-file-type-typescript"
          },
          {
            label: "useUser.ts",
            icon: "i-vscode-icons-file-type-typescript"
          }
        ]
      },
      {
        label: "components/",
        defaultExpanded: true,
        children: [
          {
            label: "Card.vue",
            icon: "i-vscode-icons-file-type-vue"
          },
          {
            label: "Button.vue",
            icon: "i-vscode-icons-file-type-vue"
          }
        ]
      }
    ]
  },
  {
    label: "app.vue",
    icon: "i-vscode-icons-file-type-vue"
  },
  {
    label: "nuxt.config.ts",
    icon: "i-vscode-icons-file-type-nuxt"
  }
])
</script>

<template>
  <UTree trailing-icon="i-lucide-arrow-down" :items="items" />
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

### Expanded Icon

Use the `expanded-icon` and `collapsed-icon` props to customize the icons of a parent node when it is expanded or collapsed. Defaults to `i-lucide-folder-open` and `i-lucide-folder` respectively.

```vue
<script setup lang="ts">
import type { TreeItem } from '@nuxt/ui'

const items = ref<TreeItem[]>([
  {
    label: "app/",
    defaultExpanded: true,
    children: [
      {
        label: "composables/",
        children: [
          {
            label: "useAuth.ts",
            icon: "i-vscode-icons-file-type-typescript"
          },
          {
            label: "useUser.ts",
            icon: "i-vscode-icons-file-type-typescript"
          }
        ]
      },
      {
        label: "components/",
        defaultExpanded: true,
        children: [
          {
            label: "Card.vue",
            icon: "i-vscode-icons-file-type-vue"
          },
          {
            label: "Button.vue",
            icon: "i-vscode-icons-file-type-vue"
          }
        ]
      }
    ]
  },
  {
    label: "app.vue",
    icon: "i-vscode-icons-file-type-vue"
  },
  {
    label: "nuxt.config.ts",
    icon: "i-vscode-icons-file-type-nuxt"
  }
])
</script>

<template>
  <UTree expanded-icon="i-lucide-book-open" collapsed-icon="i-lucide-book" :items="items" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize these icons globally in your `app.config.ts` under `ui.icons.folder` and `ui.icons.folderOpen` keys.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize these icons globally in your `vite.config.ts` under `ui.icons.folder` and `ui.icons.folderOpen` keys.

### Disabled

Use the `disabled` prop to prevent any user interaction with the Tree.

```vue
<script setup lang="ts">
import type { TreeItem } from '@nuxt/ui'

const items = ref<TreeItem[]>([
  {
    label: "app",
    icon: "i-lucide-folder",
    defaultExpanded: true,
    children: [
      {
        label: "composables",
        icon: "i-lucide-folder",
        children: [
          {
            label: "useAuth.ts",
            icon: "i-vscode-icons-file-type-typescript"
          },
          {
            label: "useUser.ts",
            icon: "i-vscode-icons-file-type-typescript"
          }
        ]
      },
      {
        label: "components",
        icon: "i-lucide-folder",
        children: [
          {
            label: "Home",
            icon: "i-lucide-folder",
            children: [
              {
                label: "Card.vue",
                icon: "i-vscode-icons-file-type-vue"
              },
              {
                label: "Button.vue",
                icon: "i-vscode-icons-file-type-vue"
              }
            ]
          }
        ]
      }
    ]
  },
  {
    label: "app.vue",
    icon: "i-vscode-icons-file-type-vue"
  },
  {
    label: "nuxt.config.ts",
    icon: "i-vscode-icons-file-type-nuxt"
  }
])
</script>

<template>
  <UTree disabled :items="items" />
</template>
```

> [!NOTE]
> 
> You can also disable individual items using `item.disabled`.

## Examples

### Control selected item(s)

You can control the selected item(s) by using the `default-value` prop or the `v-model` directive.

```vue [TreeModelValueExample.vue]
<script setup lang="ts">
import type { TreeItem } from '@nuxt/ui'

const items: TreeItem[] = [
  {
    label: 'app/',
    defaultExpanded: true,
    children: [
      {
        label: 'composables/',
        children: [
          { label: 'useAuth.ts', icon: 'i-vscode-icons-file-type-typescript' },
          { label: 'useUser.ts', icon: 'i-vscode-icons-file-type-typescript' }
        ]
      },
      {
        label: 'components/',
        defaultExpanded: true,
        children: [
          { label: 'Card.vue', icon: 'i-vscode-icons-file-type-vue' },
          { label: 'Button.vue', icon: 'i-vscode-icons-file-type-vue' }
        ]
      }
    ]
  },
  { label: 'app.vue', icon: 'i-vscode-icons-file-type-vue' },
  { label: 'nuxt.config.ts', icon: 'i-vscode-icons-file-type-nuxt' }
]

const value = ref()
</script>

<template>
  <UTree v-model="value" :items="items" />
</template>
```

> [!TIP]
> 
> Use the `get-key` prop to change the function used to get the unique key from each item when a `v-model` or `default-value` is provided.

If you want to prevent an item from being selected, you can use the `item.onSelect()` property or the global `select` event:

```vue [TreeOnSelectExample.vue]
<script setup lang="ts">
import type { TreeItemSelectEvent } from 'reka-ui'
import type { TreeItem } from '@nuxt/ui'

const items: TreeItem[] = [
  {
    label: 'app/',
    defaultExpanded: true,
    onSelect: (e: Event) => {
      e.preventDefault()
    },
    children: [
      {
        label: 'composables/',
        children: [
          { label: 'useAuth.ts', icon: 'i-vscode-icons-file-type-typescript' },
          { label: 'useUser.ts', icon: 'i-vscode-icons-file-type-typescript' }
        ]
      },
      {
        label: 'components/',
        defaultExpanded: true,
        children: [
          { label: 'Card.vue', icon: 'i-vscode-icons-file-type-vue' },
          { label: 'Button.vue', icon: 'i-vscode-icons-file-type-vue' }
        ]
      }
    ]
  },
  { label: 'app.vue', icon: 'i-vscode-icons-file-type-vue' },
  { label: 'nuxt.config.ts', icon: 'i-vscode-icons-file-type-nuxt' }
]

function onSelect(e: TreeItemSelectEvent<TreeItem>) {
  if (e.detail.originalEvent.type === 'click') {
    e.preventDefault()
  }
}
</script>

<template>
  <UTree :items="items" @select="onSelect" />
</template>
```

> [!NOTE]
> 
> This lets you expand or collapse a parent item without selecting it.

### Control expanded items

You can control the expanded items by using the `default-expanded` prop or the `v-model` directive.

```vue [TreeExpandedExample.vue]
<script setup lang="ts">
import type { TreeItem } from '@nuxt/ui'

const items = [
  {
    label: 'app/',
    id: 'app',
    children: [
      {
        label: 'composables/',
        id: 'app/composables',
        children: [
          { label: 'useAuth.ts', icon: 'i-vscode-icons-file-type-typescript' },
          { label: 'useUser.ts', icon: 'i-vscode-icons-file-type-typescript' }
        ]
      },
      {
        label: 'components/',
        id: 'app/components',
        children: [
          { label: 'Card.vue', icon: 'i-vscode-icons-file-type-vue' },
          { label: 'Button.vue', icon: 'i-vscode-icons-file-type-vue' }
        ]
      }
    ]
  },
  { label: 'app.vue', id: 'app.vue', icon: 'i-vscode-icons-file-type-vue' },
  { label: 'nuxt.config.ts', id: 'nuxt.config.ts', icon: 'i-vscode-icons-file-type-nuxt' }
] satisfies TreeItem[]

const expanded = ref(['app', 'app/composables'])
</script>

<template>
  <UTree v-model:expanded="expanded" :items="items" :get-key="i => i.id" />
</template>
```

If you want to prevent an item from being expanded, you can use the `item.onToggle()` property or the global `toggle` event:

```vue [TreeOnToggleExample.vue]
<script setup lang="ts">
import type { TreeItemToggleEvent } from 'reka-ui'
import type { TreeItem } from '@nuxt/ui'

const items: TreeItem[] = [
  {
    label: 'app/',
    defaultExpanded: true,
    onToggle: (e: Event) => {
      e.preventDefault()
    },
    children: [
      {
        label: 'composables/',
        children: [
          { label: 'useAuth.ts', icon: 'i-vscode-icons-file-type-typescript' },
          { label: 'useUser.ts', icon: 'i-vscode-icons-file-type-typescript' }
        ]
      },
      {
        label: 'components/',
        defaultExpanded: true,
        children: [
          { label: 'Card.vue', icon: 'i-vscode-icons-file-type-vue' },
          { label: 'Button.vue', icon: 'i-vscode-icons-file-type-vue' }
        ]
      }
    ]
  },
  { label: 'app.vue', icon: 'i-vscode-icons-file-type-vue' },
  { label: 'nuxt.config.ts', icon: 'i-vscode-icons-file-type-nuxt' }
]

function onToggle(e: TreeItemToggleEvent<TreeItem>) {
  if (e.detail.originalEvent.type === 'keydown') {
    e.preventDefault()
  }
}
</script>

<template>
  <UTree :items="items" @toggle="onToggle" />
</template>
```

> [!NOTE]
> 
> This lets you select a parent item without expanding or collapsing its children.

### With checkbox in items `4.1+`

You can use the `item-leading` slot to add a [Checkbox](/docs/components/checkbox) to the items. Use the `multiple`, `propagate-select` and `bubble-select` props to enable multi-selection with parent-child relationship and the `select` and `toggle` events to control the selected and expanded state of the items.

```vue [TreeCheckboxItemsExample.vue]
<script setup lang="ts">
import type { TreeItemSelectEvent } from 'reka-ui'
import type { TreeItem } from '@nuxt/ui'

const items: TreeItem[] = [
  {
    label: 'app/',
    defaultExpanded: true,
    children: [
      {
        label: 'composables/',
        children: [
          { label: 'useAuth.ts' },
          { label: 'useUser.ts' }
        ]
      },
      {
        label: 'components/',
        defaultExpanded: true,
        children: [
          { label: 'Card.vue' },
          { label: 'Button.vue' }
        ]
      }
    ]
  },
  { label: 'app.vue' },
  { label: 'nuxt.config.ts' }
]

const value = ref<(typeof items)>([])

function onSelect(e: TreeItemSelectEvent<TreeItem>) {
  if (e.detail.originalEvent.type === 'click') {
    e.preventDefault()
  }
}
</script>

<template>
  <UTree
    v-model="value"
    :as="{ link: 'div' }"
    :items="items"
    multiple
    propagate-select
    bubble-select
    @select="onSelect"
  >
    <template #item-leading="{ selected, indeterminate, handleSelect }">
      <UCheckbox
        :model-value="indeterminate ? 'indeterminate' : selected"
        tabindex="-1"
        @change="handleSelect"
        @click.stop
      />
    </template>
  </UTree>
</template>
```

> [!NOTE]
> 
> This example uses the `as` prop to change the items from `button` to `div` as the [`Checkbox`](/docs/components/checkbox) is also rendered as a `button`.

### With drag and drop `4.1+`

Use the [`useSortable`](https://vueuse.org/integrations/useSortable/) composable from [`@vueuse/integrations`](https://vueuse.org/integrations/README.html) to enable drag and drop functionality on the Tree. This integration wraps [Sortable.js](https://sortablejs.github.io/Sortable/) to provide a seamless drag and drop experience.

```vue [TreeDragAndDropExample.vue]
<script setup lang="ts">
import type { TreeItem } from '@nuxt/ui'
import { useSortable } from '@vueuse/integrations/useSortable'

const items = shallowRef<TreeItem[]>([
  {
    label: 'app/',
    defaultExpanded: true,
    children: [
      {
        label: 'composables/',
        children: [
          { label: 'useAuth.ts', icon: 'i-vscode-icons-file-type-typescript' },
          { label: 'useUser.ts', icon: 'i-vscode-icons-file-type-typescript' }
        ]
      },
      {
        label: 'components/',
        defaultExpanded: true,
        children: [
          { label: 'Card.vue', icon: 'i-vscode-icons-file-type-vue' },
          { label: 'Button.vue', icon: 'i-vscode-icons-file-type-vue' }
        ]
      }
    ]
  },
  { label: 'app.vue', icon: 'i-vscode-icons-file-type-vue' },
  { label: 'nuxt.config.ts', icon: 'i-vscode-icons-file-type-nuxt' }
])

function flatten(items: TreeItem[], parent = items): { item: TreeItem, parent: TreeItem[], index: number }[] {
  return items.flatMap((item, index) => [
    { item, parent, index },
    ...(item.children?.length && item.defaultExpanded ? flatten(item.children, item.children) : [])
  ])
}

function moveItem(oldIndex: number, newIndex: number) {
  if (oldIndex === newIndex) return

  const flat = flatten(items.value)
  const source = flat[oldIndex]
  const target = flat[newIndex]

  if (!source || !target) return

  const [moved] = source.parent.splice(source.index, 1)
  if (!moved) return

  const updatedFlat = flatten(items.value)
  const updatedTarget = updatedFlat.find(({ item }) => item === target.item)
  if (!updatedTarget) return

  const insertIndex = oldIndex < newIndex ? updatedTarget.index + 1 : updatedTarget.index
  updatedTarget.parent.splice(insertIndex, 0, moved)
}

const tree = useTemplateRef<HTMLElement>('tree')

useSortable(tree, items, {
  animation: 150,
  ghostClass: 'opacity-50',
  onUpdate: (e: any) => moveItem(e.oldIndex, e.newIndex)
})
</script>

<template>
  <UTree ref="tree" :nested="false" :unmount-on-hide="false" :items="items" />
</template>
```

> [!NOTE]
> 
> This example sets the `nested` prop to `false` to have a flat list of items so that the items can be dragged and dropped.

### With virtualization `4.1+`

Use the `virtualize` prop to enable virtualization for large lists as a boolean or an object with options like `{ estimateSize: 32, overscan: 12 }`.

> [!WARNING]
> 
> When virtualization is enabled, the tree structure is flattened, similar to setting the `nested` prop to `false`.

```vue [TreeVirtualizeExample.vue]
<script setup lang="ts">
import type { TreeItem } from '@nuxt/ui'

const items: TreeItem[] = Array(1000).fill(0).map((_, i) => ({
  label: `Item ${i + 1}`,
  children: [
    { label: `Child ${i + 1}-1`, icon: 'i-lucide-file' },
    { label: `Child ${i + 1}-2`, icon: 'i-lucide-file' }
  ]
}))
</script>

<template>
  <UTree virtualize :items="items" class="h-80" />
</template>
```

### With custom slot

Use the `slot` property to customize a specific item.

You will have access to the following slots:

- `#{{ item.slot }}-wrapper`
- `#{{ item.slot }}`
- `#{{ item.slot }}-leading`
- `#{{ item.slot }}-label`
- `#{{ item.slot }}-trailing`

```vue [TreeCustomSlotExample.vue]
<script setup lang="ts">
import type { TreeItem } from '@nuxt/ui'

const items = [
  {
    label: 'app/',
    slot: 'app' as const,
    defaultExpanded: true,
    children: [
      {
        label: 'composables/',
        children: [
          { label: 'useAuth.ts', icon: 'i-vscode-icons-file-type-typescript' },
          { label: 'useUser.ts', icon: 'i-vscode-icons-file-type-typescript' }
        ]
      },
      {
        label: 'components/',
        defaultExpanded: true,
        children: [
          { label: 'Card.vue', icon: 'i-vscode-icons-file-type-vue' },
          { label: 'Button.vue', icon: 'i-vscode-icons-file-type-vue' }
        ]
      }
    ]
  },
  { label: 'app.vue', icon: 'i-vscode-icons-file-type-vue' },
  { label: 'nuxt.config.ts', icon: 'i-vscode-icons-file-type-nuxt' }
] satisfies TreeItem[]
</script>

<template>
  <UTree :items="items">
    <template #app="{ item }">
      <p class="italic font-bold">
        {{ item.label }}
      </p>
    </template>
  </UTree>
</template>
```

## API

### Props

```ts
/**
 * Props for the Tree component
 */
interface TreeProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  size?: "md" | "xs" | "sm" | "lg" | "xl" | undefined;
  /**
   * This function is passed the index of each item and should return a unique key for that item
   */
  getKey?: ((val: T[number]) => string) | undefined;
  /**
   * The key used to get the label from the item.
   * @default "\"label\""
   */
  labelKey?: GetItemKeys<T> | undefined;
  /**
   * The icon displayed on the right side of a parent node.
   */
  trailingIcon?: any;
  /**
   * The icon displayed when a parent node is expanded.
   */
  expandedIcon?: any;
  /**
   * The icon displayed when a parent node is collapsed.
   */
  collapsedIcon?: any;
  items?: T | undefined;
  /**
   * The controlled value of the Tree. Can be bind as `v-model`.
   */
  modelValue?: (M extends true ? T[number][] : T[number]) | undefined;
  /**
   * The value of the Tree when initially rendered. Use when you do not need to control the state of the Tree.
   */
  defaultValue?: (M extends true ? T[number][] : T[number]) | undefined;
  /**
   * Whether multiple options can be selected or not.
   */
  multiple?: M | undefined;
  /**
   * Use nested DOM structure (children inside parents) vs flattened structure (all items at same level).
   * When `virtualize` is enabled, this is automatically set to `false`.
   * @default "true"
   */
  nested?: boolean | undefined;
  /**
   * Enable virtualization for large lists.
   * Note: when enabled, the tree structure is flattened like if `nested` was set to `false`.
   * @default "false"
   */
  virtualize?: boolean | { overscan?: number | undefined; estimateSize?: number | ((index: number) => number) | undefined; } | undefined;
  onSelect?: ((e: SelectEvent<T[number]>, item: T[number]) => void) | undefined;
  onToggle?: ((e: ToggleEvent<T[number]>, item: T[number]) => void) | undefined;
  ui?: { root?: SlotClass; item?: SlotClass; listWithChildren?: SlotClass; itemWithChildren?: SlotClass; link?: SlotClass; linkLeadingIcon?: SlotClass; linkLabel?: SlotClass; linkTrailing?: SlotClass; linkTrailingIcon?: SlotClass; } | undefined;
  /**
   * The controlled value of the expanded item. Can be binded with `v-model`.
   */
  expanded?: string[] | undefined;
  /**
   * The value of the expanded tree when initially rendered. Use when you do not need to control the state of the expanded tree
   */
  defaultExpanded?: string[] | undefined;
  /**
   * How multiple selection should behave in the collection.
   */
  selectionBehavior?: "replace" | "toggle" | undefined;
  /**
   * When `true`, selecting parent will select the descendants.
   */
  propagateSelect?: boolean | undefined;
  /**
   * When `true`, prevents the user from interacting with tree
   */
  disabled?: boolean | undefined;
  /**
   * When `true`, selecting children will update the parent state.
   */
  bubbleSelect?: boolean | undefined;
}
```

### Slots

```ts
/**
 * Slots for the Tree component
 */
interface TreeSlots {
  item-wrapper(): any;
  item(): any;
  item-leading(): any;
  item-label(): any;
  item-trailing(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the Tree component
 */
interface TreeEmits {
  update:modelValue: (payload: [val: M extends true ? T[number][] : T[number]]) => void;
  update:expanded: (payload: [val: string[]]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    tree: {
      slots: {
        root: 'relative isolate',
        item: 'w-full',
        listWithChildren: 'border-s border-default',
        itemWithChildren: 'ps-1.5 -ms-px',
        link: 'relative group w-full flex items-center text-sm select-none before:absolute before:inset-y-px before:inset-x-0 before:z-[-1] before:rounded-md focus:outline-none focus-visible:outline-none focus-visible:before:outline-3',
        linkLeadingIcon: 'shrink-0 relative',
        linkLabel: 'truncate',
        linkTrailing: 'ms-auto inline-flex gap-1.5 items-center',
        linkTrailingIcon: 'shrink-0 transform transition-transform duration-200 group-data-expanded:rotate-180'
      },
      variants: {
        virtualize: {
          true: {
            root: 'overflow-y-auto'
          }
        },
        color: {
          primary: {
            link: 'before:outline-primary/25'
          },
          secondary: {
            link: 'before:outline-secondary/25'
          },
          success: {
            link: 'before:outline-success/25'
          },
          info: {
            link: 'before:outline-info/25'
          },
          warning: {
            link: 'before:outline-warning/25'
          },
          error: {
            link: 'before:outline-error/25'
          },
          neutral: {
            link: 'before:outline-inverted/25'
          }
        },
        size: {
          xs: {
            listWithChildren: 'ms-4',
            link: 'px-2 py-1 text-xs gap-1',
            linkLeadingIcon: 'size-4',
            linkTrailingIcon: 'size-4'
          },
          sm: {
            listWithChildren: 'ms-4.5',
            link: 'px-2.5 py-1.5 text-xs gap-1.5',
            linkLeadingIcon: 'size-4',
            linkTrailingIcon: 'size-4'
          },
          md: {
            listWithChildren: 'ms-5',
            link: 'px-2.5 py-1.5 text-sm gap-1.5',
            linkLeadingIcon: 'size-5',
            linkTrailingIcon: 'size-5'
          },
          lg: {
            listWithChildren: 'ms-5.5',
            link: 'px-3 py-2 text-sm gap-2',
            linkLeadingIcon: 'size-5',
            linkTrailingIcon: 'size-5'
          },
          xl: {
            listWithChildren: 'ms-6',
            link: 'px-3 py-2 text-base gap-2',
            linkLeadingIcon: 'size-6',
            linkTrailingIcon: 'size-6'
          }
        },
        selected: {
          true: {
            link: 'before:bg-elevated'
          }
        },
        disabled: {
          true: {
            link: 'cursor-not-allowed opacity-75'
          }
        }
      },
      compoundVariants: [
        {
          color: 'primary',
          selected: true,
          class: {
            link: 'text-primary'
          }
        },
        {
          color: 'secondary',
          selected: true,
          class: {
            link: 'text-secondary'
          }
        },
        {
          color: 'success',
          selected: true,
          class: {
            link: 'text-success'
          }
        },
        {
          color: 'info',
          selected: true,
          class: {
            link: 'text-info'
          }
        },
        {
          color: 'warning',
          selected: true,
          class: {
            link: 'text-warning'
          }
        },
        {
          color: 'error',
          selected: true,
          class: {
            link: 'text-error'
          }
        },
        {
          color: 'neutral',
          selected: true,
          class: {
            link: 'text-highlighted'
          }
        },
        {
          selected: false,
          disabled: false,
          class: {
            link: [
              'hover:text-highlighted hover:before:bg-elevated/50',
              'transition-colors before:transition-colors'
            ]
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

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Tree.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/tree.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

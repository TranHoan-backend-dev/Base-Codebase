---
title: "ContextMenu"
description: "A menu to display actions when right-clicking on an element."
canonical_url: "https://ui.nuxt.com/docs/components/context-menu"
---
# ContextMenu

> A menu to display actions when right-clicking on an element.

## Usage

Use anything you like in the default slot of the ContextMenu, and right-click on it to display the menu.

```vue
<script setup lang="ts">
import type { ContextMenuItem } from '@nuxt/ui'

const items = ref<ContextMenuItem[][]>([
  [
    {
      label: "Appearance",
      children: [
        {
          label: "System",
          icon: "i-lucide-monitor"
        },
        {
          label: "Light",
          icon: "i-lucide-sun"
        },
        {
          label: "Dark",
          icon: "i-lucide-moon"
        }
      ]
    }
  ],
  [
    {
      label: "Show Sidebar",
      kbds: [
        "meta",
        "s"
      ]
    },
    {
      label: "Show Toolbar",
      kbds: [
        "shift",
        "meta",
        "d"
      ]
    },
    {
      label: "Collapse Pinned Tabs",
      disabled: true
    }
  ],
  [
    {
      label: "Refresh the Page"
    },
    {
      label: "Clear Cookies and Refresh"
    },
    {
      label: "Clear Cache and Refresh"
    },
    {
      type: "separator"
    },
    {
      label: "Developer",
      children: [
        [
          {
            label: "View Source",
            kbds: [
              "meta",
              "shift",
              "u"
            ]
          },
          {
            label: "Developer Tools",
            kbds: [
              "option",
              "meta",
              "i"
            ]
          },
          {
            label: "Inspect Elements",
            kbds: [
              "option",
              "meta",
              "c"
            ]
          }
        ],
        [
          {
            label: "JavaScript Console",
            kbds: [
              "option",
              "meta",
              "j"
            ]
          }
        ]
      ]
    }
  ]
])
</script>

<template>
  <UContextMenu :items="items">
    <div class="flex items-center justify-center rounded-md border border-dashed border-accented text-sm aspect-video w-72">
      Right click here
    </div>
  </UContextMenu>
</template>
```

### Items

Use the `items` prop as an array of objects with the following properties:

- `label?: string`
- `icon?: string`
- `avatar?: AvatarProps`
- `kbds?: string[] | KbdProps[]`
- [`type?: "link" | "label" | "separator" | "checkbox"`](#with-checkbox-items)
- [`color?: "error" | "primary" | "secondary" | "success" | "info" | "warning" | "neutral"`](#with-color-items)
- [`checked?: boolean`](#with-checkbox-items)
- `disabled?: boolean`
- [`slot?: string`](#with-custom-slot)
- `onSelect?: (e: Event) => void`
- [`onUpdateChecked?: (checked: boolean) => void`](#with-checkbox-items)
- `children?: ContextMenuItem[] | ContextMenuItem[][]`
- `class?: any`
- `ui?: { item?: ClassNameValue, label?: ClassNameValue, separator?: ClassNameValue, itemLeadingIcon?: ClassNameValue, itemLeadingAvatarSize?: ClassNameValue, itemLeadingAvatar?: ClassNameValue, itemLabel?: ClassNameValue, itemLabelExternalIcon?: ClassNameValue, itemTrailing?: ClassNameValue, itemTrailingIcon?: ClassNameValue, itemTrailingKbds?: ClassNameValue, itemTrailingKbdsSize?: ClassNameValue }`

You can pass any property from the [Link](/docs/components/link#props) component such as `to`, `target`, etc.

```vue
<script setup lang="ts">
import type { ContextMenuItem } from '@nuxt/ui'

const items = ref<ContextMenuItem[][]>([
  [
    {
      label: "Appearance",
      children: [
        {
          label: "System",
          icon: "i-lucide-monitor"
        },
        {
          label: "Light",
          icon: "i-lucide-sun"
        },
        {
          label: "Dark",
          icon: "i-lucide-moon"
        }
      ]
    }
  ],
  [
    {
      label: "Show Sidebar",
      kbds: [
        "meta",
        "s"
      ]
    },
    {
      label: "Show Toolbar",
      kbds: [
        "shift",
        "meta",
        "d"
      ]
    },
    {
      label: "Collapse Pinned Tabs",
      disabled: true
    }
  ],
  [
    {
      label: "Refresh the Page"
    },
    {
      label: "Clear Cookies and Refresh"
    },
    {
      label: "Clear Cache and Refresh"
    },
    {
      type: "separator"
    },
    {
      label: "Developer",
      children: [
        [
          {
            label: "View Source",
            kbds: [
              "meta",
              "shift",
              "u"
            ]
          },
          {
            label: "Developer Tools",
            kbds: [
              "option",
              "meta",
              "i"
            ]
          },
          {
            label: "Inspect Elements",
            kbds: [
              "option",
              "meta",
              "c"
            ]
          }
        ],
        [
          {
            label: "JavaScript Console",
            kbds: [
              "option",
              "meta",
              "j"
            ]
          }
        ]
      ]
    }
  ]
])
</script>

<template>
  <UContextMenu :items="items" :ui="{
  content: 'w-48'
}">
    <div class="flex items-center justify-center rounded-md border border-dashed border-accented text-sm aspect-video w-72">
      Right click here
    </div>
  </UContextMenu>
</template>
```

> [!NOTE]
> 
> You can also pass an array of arrays to the `items` prop to create separated groups of items.

> [!TIP]
> 
> Each item can take a `children` array of objects with the same properties as the `items` prop to create a nested menu which can be controlled using the `open`, `defaultOpen` and `content` properties.

### Size

Use the `size` prop to change the size of the ContextMenu.

```vue
<script setup lang="ts">
import type { ContextMenuItem } from '@nuxt/ui'

const items = ref<ContextMenuItem[]>([
  {
    label: "System",
    icon: "i-lucide-monitor"
  },
  {
    label: "Light",
    icon: "i-lucide-sun"
  },
  {
    label: "Dark",
    icon: "i-lucide-moon"
  }
])
</script>

<template>
  <UContextMenu size="xl" :items="items" :ui="{
  content: 'w-48'
}">
    <div class="flex items-center justify-center rounded-md border border-dashed border-accented text-sm aspect-video w-72">
      Right click here
    </div>
  </UContextMenu>
</template>
```

### Modal

Use the `modal` prop to control whether the ContextMenu blocks interaction with outside content. Defaults to `true`.

```vue
<script setup lang="ts">
import type { ContextMenuItem } from '@nuxt/ui'

const items = ref<ContextMenuItem[]>([
  {
    label: "System",
    icon: "i-lucide-monitor"
  },
  {
    label: "Light",
    icon: "i-lucide-sun"
  },
  {
    label: "Dark",
    icon: "i-lucide-moon"
  }
])
</script>

<template>
  <UContextMenu :modal="false" :items="items" :ui="{
  content: 'w-48'
}">
    <div class="flex items-center justify-center rounded-md border border-dashed border-accented text-sm aspect-video w-72">
      Right click here
    </div>
  </UContextMenu>
</template>
```

### Disabled

Use the `disabled` prop to disable the ContextMenu.

```vue
<script setup lang="ts">
import type { ContextMenuItem } from '@nuxt/ui'

const items = ref<ContextMenuItem[]>([
  {
    label: "System",
    icon: "i-lucide-monitor"
  },
  {
    label: "Light",
    icon: "i-lucide-sun"
  },
  {
    label: "Dark",
    icon: "i-lucide-moon"
  }
])
</script>

<template>
  <UContextMenu disabled :items="items" :ui="{
  content: 'w-48'
}">
    <div class="flex items-center justify-center rounded-md border border-dashed border-accented text-sm aspect-video w-72">
      Right click here
    </div>
  </UContextMenu>
</template>
```

## Examples

### With checkbox items

You can use the `type` property with `checkbox` and use the `checked` / `onUpdateChecked` properties to control the checked state of the item.

```vue [ContextMenuCheckboxItemsExample.vue]
<script setup lang="ts">
import type { ContextMenuItem } from '@nuxt/ui'

const showSidebar = ref(true)
const showToolbar = ref(false)

const items = computed<ContextMenuItem[]>(() => [{
  label: 'View',
  type: 'label' as const
}, {
  type: 'separator' as const
}, {
  label: 'Show Sidebar',
  type: 'checkbox' as const,
  checked: showSidebar.value,
  onUpdateChecked(checked: boolean) {
    showSidebar.value = checked
  },
  onSelect(e: Event) {
    e.preventDefault()
  }
}, {
  label: 'Show Toolbar',
  type: 'checkbox' as const,
  checked: showToolbar.value,
  onUpdateChecked(checked: boolean) {
    showToolbar.value = checked
  }
}, {
  label: 'Collapse Pinned Tabs',
  type: 'checkbox' as const,
  disabled: true
}])
</script>

<template>
  <UContextMenu :items="items" :ui="{ content: 'w-48' }">
    <div class="flex items-center justify-center rounded-md border border-dashed border-accented text-sm aspect-video w-72">
      Right click here
    </div>
  </UContextMenu>
</template>
```

> [!NOTE]
> 
> To ensure reactivity for the `checked` state of items, it's recommended to wrap your `items` array inside a `computed`.

### With color items

You can use the `color` property to highlight certain items with a color.

```vue [ContextMenuColorItemsExample.vue]
<script setup lang="ts">
import type { ContextMenuItem } from '@nuxt/ui'

const items: ContextMenuItem[][] = [
  [
    {
      label: 'View',
      icon: 'i-lucide-eye'
    },
    {
      label: 'Copy',
      icon: 'i-lucide-copy'
    },
    {
      label: 'Edit',
      icon: 'i-lucide-pencil'
    }
  ],
  [
    {
      label: 'Delete',
      color: 'error' as const,
      icon: 'i-lucide-trash'
    }
  ]
]
</script>

<template>
  <UContextMenu :items="items" :ui="{ content: 'w-48' }">
    <div class="flex items-center justify-center rounded-md border border-dashed border-accented text-sm aspect-video w-72">
      Right click here
    </div>
  </UContextMenu>
</template>
```

### With custom slot

Use the `slot` property to customize a specific item.

You will have access to the following slots:

- `#{{ item.slot }}`
- `#{{ item.slot }}-leading`
- `#{{ item.slot }}-label`
- `#{{ item.slot }}-trailing`

```vue [ContextMenuCustomSlotExample.vue]
<script setup lang="ts">
import type { ContextMenuItem } from '@nuxt/ui'

const loading = ref(true)

const items = [
  {
    label: 'Refresh the Page',
    slot: 'refresh' as const
  },
  {
    label: 'Clear Cookies and Refresh'
  },
  {
    label: 'Clear Cache and Refresh'
  }
] satisfies ContextMenuItem[]
</script>

<template>
  <UContextMenu :items="items" :ui="{ content: 'w-48' }">
    <div class="flex items-center justify-center rounded-md border border-dashed border-accented text-sm aspect-video w-72">
      Right click here
    </div>

    <template #refresh-label>
      {{ loading ? 'Refreshing...' : 'Refresh the Page' }}
    </template>

    <template #refresh-trailing>
      <UIcon v-if="loading" name="i-lucide-loader-circle" class="shrink-0 size-5 text-primary animate-spin" />
    </template>
  </UContextMenu>
</template>
```

> [!TIP]
> See: #slots
> 
> You can also use the `#item`, `#item-leading`, `#item-label` and `#item-trailing` slots to customize all items.

### Extract shortcuts

Use the [extractShortcuts](/docs/composables/extract-shortcuts) utility to automatically define shortcuts from menu items with a `kbds` property. It recursively extracts shortcuts and returns an object compatible with [defineShortcuts](/docs/composables/define-shortcuts).

```vue
<script setup lang="ts">
const items = [
  [{
    label: 'Show Sidebar',
    kbds: ['meta', 'S'],
    onSelect() {
      console.log('Show Sidebar clicked')
    }
  }, {
    label: 'Show Toolbar',
    kbds: ['shift', 'meta', 'D'],
    onSelect() {
      console.log('Show Toolbar clicked')
    }
  }, {
    label: 'Collapse Pinned Tabs',
    disabled: true
  }], [{
    label: 'Refresh the Page'
  }, {
    label: 'Clear Cookies and Refresh'
  }, {
    label: 'Clear Cache and Refresh'
  }, {
    type: 'separator' as const
  }, {
    label: 'Developer',
    children: [[{
      label: 'View Source',
      kbds: ['option', 'meta', 'U'],
      onSelect() {
        console.log('View Source clicked')
      }
    }, {
      label: 'Developer Tools',
      kbds: ['option', 'meta', 'I'],
      onSelect() {
        console.log('Developer Tools clicked')
      }
    }], [{
      label: 'Inspect Elements',
      kbds: ['option', 'meta', 'C'],
      onSelect() {
        console.log('Inspect Elements clicked')
      }
    }], [{
      label: 'JavaScript Console',
      kbds: ['option', 'meta', 'J'],
      onSelect() {
        console.log('JavaScript Console clicked')
      }
    }]]
  }]
]

defineShortcuts(extractShortcuts(items))
</script>
```

> [!NOTE]
> 
> In this example, <kbd value="meta">
> 
> 
> 
> </kbd>
> 
>  <kbd value="S" className="ms-px">
> 
> 
> 
> </kbd>
> 
> , <kbd value="shift">
> 
> 
> 
> </kbd>
> 
>  <kbd value="meta" className="ms-px">
> 
> 
> 
> </kbd>
> 
>  <kbd value="D" className="ms-px">
> 
> 
> 
> </kbd>
> 
> , <kbd value="option">
> 
> 
> 
> </kbd>
> 
>  <kbd value="meta" className="ms-px">
> 
> 
> 
> </kbd>
> 
>  <kbd value="U" className="ms-px">
> 
> 
> 
> </kbd>
> 
> , <kbd value="option">
> 
> 
> 
> </kbd>
> 
>  <kbd value="meta" className="ms-px">
> 
> 
> 
> </kbd>
> 
>  <kbd value="I" className="ms-px">
> 
> 
> 
> </kbd>
> 
> , <kbd value="option">
> 
> 
> 
> </kbd>
> 
>  <kbd value="meta" className="ms-px">
> 
> 
> 
> </kbd>
> 
>  <kbd value="C" className="ms-px">
> 
> 
> 
> </kbd>
> 
>  and <kbd value="option">
> 
> 
> 
> </kbd>
> 
>  <kbd value="meta" className="ms-px">
> 
> 
> 
> </kbd>
> 
>  <kbd value="J" className="ms-px">
> 
> 
> 
> </kbd>
> 
>  would trigger the `select` function of the corresponding item.

## API

### Props

```ts
/**
 * Props for the ContextMenu component
 */
interface ContextMenuProps {
  size?: "sm" | "md" | "xs" | "lg" | "xl" | undefined;
  items?: T | undefined;
  /**
   * The icon displayed when an item is checked.
   */
  checkedIcon?: any;
  /**
   * The icon displayed when an item is loading.
   */
  loadingIcon?: any;
  /**
   * The icon displayed when the item is an external link.
   * Set to `false` to hide the external icon.
   * @default "true"
   */
  externalIcon?: any;
  /**
   * The content of the menu.
   */
  content?: (Omit<ContextMenuContentProps, "as" | "asChild" | "forceMount"> & Partial<EmitsToProps<MenuContentEmits>>) | undefined;
  /**
   * Render the menu in a portal.
   * @default "true"
   */
  portal?: string | boolean | HTMLElement | undefined;
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
  disabled?: boolean | undefined;
  ui?: { content?: SlotClass; viewport?: SlotClass; group?: SlotClass; label?: SlotClass; separator?: SlotClass; item?: SlotClass; itemLeadingIcon?: SlotClass; itemLeadingAvatar?: SlotClass; itemLeadingAvatarSize?: SlotClass; itemTrailing?: SlotClass; itemTrailingIcon?: SlotClass; itemTrailingKbds?: SlotClass; itemTrailingKbdsSize?: SlotClass; itemWrapper?: SlotClass; itemLabel?: SlotClass; itemDescription?: SlotClass; itemLabelExternalIcon?: SlotClass; } | undefined;
  /**
   * The modality of the dropdown menu.
   * 
   * When set to `true`, interaction with outside elements will be disabled and only menu content will be visible to screen readers.
   * @default "true"
   */
  modal?: boolean | undefined;
  /**
   * The duration from when the trigger is pressed until the menu opens.
   */
  pressOpenDelay?: number | undefined;
}
```

### Slots

```ts
/**
 * Slots for the ContextMenu component
 */
interface ContextMenuSlots {
  default(): any;
  item(): any;
  item-leading(): any;
  item-label(): any;
  item-description(): any;
  item-trailing(): any;
  content-top(): any;
  content-bottom(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the ContextMenu component
 */
interface ContextMenuEmits {
  update:open: (payload: [payload: boolean]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    contextMenu: {
      slots: {
        content: 'min-w-32 max-h-(--reka-context-menu-content-available-height) bg-default shadow-lg rounded-md ring ring-default overflow-hidden data-[state=open]:animate-[scale-in_100ms_ease-out] data-[state=closed]:animate-[scale-out_100ms_ease-in] origin-(--reka-context-menu-content-transform-origin) flex flex-col',
        viewport: 'relative divide-y divide-default scroll-py-1 overflow-y-auto flex-1',
        group: 'p-1 isolate',
        label: 'w-full flex items-center font-semibold text-highlighted',
        separator: '-mx-1 my-1 h-px bg-border',
        item: 'group relative w-full flex items-start select-none outline-none before:absolute before:z-[-1] before:inset-px before:rounded-md data-disabled:cursor-not-allowed data-disabled:opacity-75',
        itemLeadingIcon: 'shrink-0',
        itemLeadingAvatar: 'shrink-0',
        itemLeadingAvatarSize: '',
        itemTrailing: 'ms-auto inline-flex gap-1.5 items-center',
        itemTrailingIcon: 'shrink-0',
        itemTrailingKbds: 'hidden lg:inline-flex items-center shrink-0',
        itemTrailingKbdsSize: '',
        itemWrapper: 'flex-1 flex flex-col text-start min-w-0',
        itemLabel: 'truncate',
        itemDescription: 'truncate text-muted',
        itemLabelExternalIcon: 'inline-block size-3 align-top text-dimmed'
      },
      variants: {
        color: {
          primary: '',
          secondary: '',
          success: '',
          info: '',
          warning: '',
          error: '',
          neutral: ''
        },
        active: {
          true: {
            item: 'text-highlighted before:bg-elevated',
            itemLeadingIcon: 'text-default'
          },
          false: {
            item: [
              'text-default data-highlighted:text-highlighted data-[state=open]:text-highlighted data-highlighted:before:bg-elevated/50 data-[state=open]:before:bg-elevated/50',
              'transition-colors before:transition-colors'
            ],
            itemLeadingIcon: [
              'text-dimmed group-data-highlighted:text-default group-data-[state=open]:text-default',
              'transition-colors'
            ]
          }
        },
        loading: {
          true: {
            itemLeadingIcon: 'animate-spin'
          }
        },
        size: {
          xs: {
            label: 'p-1 text-xs gap-1',
            item: 'p-1 text-xs gap-1',
            itemLeadingIcon: 'size-4',
            itemLeadingAvatarSize: '3xs',
            itemTrailingIcon: 'size-4',
            itemTrailingKbds: 'gap-0.5',
            itemTrailingKbdsSize: 'sm'
          },
          sm: {
            label: 'p-1.5 text-xs gap-1.5',
            item: 'p-1.5 text-xs gap-1.5',
            itemLeadingIcon: 'size-4',
            itemLeadingAvatarSize: '3xs',
            itemTrailingIcon: 'size-4',
            itemTrailingKbds: 'gap-0.5',
            itemTrailingKbdsSize: 'sm'
          },
          md: {
            label: 'p-1.5 text-sm gap-1.5',
            item: 'p-1.5 text-sm gap-1.5',
            itemLeadingIcon: 'size-5',
            itemLeadingAvatarSize: '2xs',
            itemTrailingIcon: 'size-5',
            itemTrailingKbds: 'gap-0.5',
            itemTrailingKbdsSize: 'md'
          },
          lg: {
            label: 'p-2 text-sm gap-2',
            item: 'p-2 text-sm gap-2',
            itemLeadingIcon: 'size-5',
            itemLeadingAvatarSize: '2xs',
            itemTrailingIcon: 'size-5',
            itemTrailingKbds: 'gap-1',
            itemTrailingKbdsSize: 'md'
          },
          xl: {
            label: 'p-2 text-base gap-2',
            item: 'p-2 text-base gap-2',
            itemLeadingIcon: 'size-6',
            itemLeadingAvatarSize: 'xs',
            itemTrailingIcon: 'size-6',
            itemTrailingKbds: 'gap-1',
            itemTrailingKbdsSize: 'lg'
          }
        }
      },
      compoundVariants: [
        {
          color: 'primary',
          active: false,
          class: {
            item: 'text-primary data-highlighted:text-primary data-highlighted:before:bg-primary/10 data-[state=open]:before:bg-primary/10',
            itemLeadingIcon: 'text-primary/75 group-data-highlighted:text-primary group-data-[state=open]:text-primary'
          }
        },
        {
          color: 'secondary',
          active: false,
          class: {
            item: 'text-secondary data-highlighted:text-secondary data-highlighted:before:bg-secondary/10 data-[state=open]:before:bg-secondary/10',
            itemLeadingIcon: 'text-secondary/75 group-data-highlighted:text-secondary group-data-[state=open]:text-secondary'
          }
        },
        {
          color: 'success',
          active: false,
          class: {
            item: 'text-success data-highlighted:text-success data-highlighted:before:bg-success/10 data-[state=open]:before:bg-success/10',
            itemLeadingIcon: 'text-success/75 group-data-highlighted:text-success group-data-[state=open]:text-success'
          }
        },
        {
          color: 'info',
          active: false,
          class: {
            item: 'text-info data-highlighted:text-info data-highlighted:before:bg-info/10 data-[state=open]:before:bg-info/10',
            itemLeadingIcon: 'text-info/75 group-data-highlighted:text-info group-data-[state=open]:text-info'
          }
        },
        {
          color: 'warning',
          active: false,
          class: {
            item: 'text-warning data-highlighted:text-warning data-highlighted:before:bg-warning/10 data-[state=open]:before:bg-warning/10',
            itemLeadingIcon: 'text-warning/75 group-data-highlighted:text-warning group-data-[state=open]:text-warning'
          }
        },
        {
          color: 'error',
          active: false,
          class: {
            item: 'text-error data-highlighted:text-error data-highlighted:before:bg-error/10 data-[state=open]:before:bg-error/10',
            itemLeadingIcon: 'text-error/75 group-data-highlighted:text-error group-data-[state=open]:text-error'
          }
        },
        {
          color: 'primary',
          active: true,
          class: {
            item: 'text-primary before:bg-primary/10',
            itemLeadingIcon: 'text-primary'
          }
        },
        {
          color: 'secondary',
          active: true,
          class: {
            item: 'text-secondary before:bg-secondary/10',
            itemLeadingIcon: 'text-secondary'
          }
        },
        {
          color: 'success',
          active: true,
          class: {
            item: 'text-success before:bg-success/10',
            itemLeadingIcon: 'text-success'
          }
        },
        {
          color: 'info',
          active: true,
          class: {
            item: 'text-info before:bg-info/10',
            itemLeadingIcon: 'text-info'
          }
        },
        {
          color: 'warning',
          active: true,
          class: {
            item: 'text-warning before:bg-warning/10',
            itemLeadingIcon: 'text-warning'
          }
        },
        {
          color: 'error',
          active: true,
          class: {
            item: 'text-error before:bg-error/10',
            itemLeadingIcon: 'text-error'
          }
        }
      ],
      defaultVariants: {
        size: 'md'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/ContextMenu.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/context-menu.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

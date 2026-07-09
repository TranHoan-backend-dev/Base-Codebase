---
title: "DropdownMenu"
description: "A menu to display actions when clicking on an element."
canonical_url: "https://ui.nuxt.com/docs/components/dropdown-menu"
---
# DropdownMenu

> A menu to display actions when clicking on an element.

## Usage

Use a [Button](/docs/components/button) or any other component in the default slot of the DropdownMenu.

```vue
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items = ref<DropdownMenuItem[][]>([
  [
    {
      label: "Benjamin",
      avatar: {
        src: "https://github.com/benjamincanac.png",
        loading: "lazy"
      },
      type: "label"
    }
  ],
  [
    {
      label: "Profile",
      icon: "i-lucide-user"
    },
    {
      label: "Billing",
      icon: "i-lucide-credit-card"
    },
    {
      label: "Settings",
      icon: "i-lucide-cog",
      kbds: [
        ","
      ]
    },
    {
      label: "Keyboard shortcuts",
      icon: "i-lucide-monitor"
    }
  ],
  [
    {
      label: "Team",
      icon: "i-lucide-users",
      filter: {
        placeholder: "Search members..."
      },
      children: [
        [
          {
            label: "benjamincanac",
            avatar: {
              src: "https://github.com/benjamincanac.png",
              loading: "lazy"
            }
          },
          {
            label: "HugoRCD",
            avatar: {
              src: "https://github.com/HugoRCD.png",
              loading: "lazy"
            }
          },
          {
            label: "romhml",
            avatar: {
              src: "https://github.com/romhml.png",
              loading: "lazy"
            }
          },
          {
            label: "sandros94",
            avatar: {
              src: "https://github.com/sandros94.png",
              loading: "lazy"
            }
          },
          {
            label: "hywax",
            avatar: {
              src: "https://github.com/hywax.png",
              loading: "lazy"
            }
          },
          {
            label: "J-Michalek",
            avatar: {
              src: "https://github.com/J-Michalek.png",
              loading: "lazy"
            }
          },
          {
            label: "genu",
            avatar: {
              src: "https://github.com/genu.png",
              loading: "lazy"
            }
          }
        ]
      ]
    },
    {
      label: "Invite users",
      icon: "i-lucide-user-plus",
      children: [
        [
          {
            label: "Email",
            icon: "i-lucide-mail"
          },
          {
            label: "Message",
            icon: "i-lucide-message-square"
          }
        ],
        [
          {
            label: "More",
            icon: "i-lucide-circle-plus",
            children: [
              {
                label: "Import from Slack",
                icon: "i-simple-icons-slack",
                to: "https://slack.com",
                target: "_blank"
              },
              {
                label: "Import from Trello",
                icon: "i-simple-icons-trello"
              },
              {
                label: "Import from Asana",
                icon: "i-simple-icons-asana"
              }
            ]
          }
        ]
      ]
    },
    {
      label: "New team",
      icon: "i-lucide-plus",
      kbds: [
        "meta",
        "n"
      ]
    }
  ],
  [
    {
      label: "GitHub",
      icon: "i-simple-icons-github",
      to: "https://github.com/nuxt/ui",
      target: "_blank"
    },
    {
      label: "Support",
      icon: "i-lucide-life-buoy",
      to: "/docs/components/dropdown-menu"
    },
    {
      label: "API",
      icon: "i-lucide-cloud",
      disabled: true
    }
  ],
  [
    {
      label: "Logout",
      icon: "i-lucide-log-out",
      color: "error",
      kbds: [
        "shift",
        "meta",
        "q"
      ]
    }
  ]
])
</script>

<template>
  <UDropdownMenu :items="items">
    <UButton icon="i-lucide-menu" color="neutral" variant="outline" />
  </UDropdownMenu>
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
- `children?: DropdownMenuItem[] | DropdownMenuItem[][]`
- [`filter?: boolean | InputProps`](#with-filter-items)
- `filterFields?: string[]`
- `ignoreFilter?: boolean`
- `class?: any`
- `ui?: { item?: ClassNameValue, label?: ClassNameValue, separator?: ClassNameValue, itemLeadingIcon?: ClassNameValue, itemLeadingAvatarSize?: ClassNameValue, itemLeadingAvatar?: ClassNameValue, itemLabel?: ClassNameValue, itemLabelExternalIcon?: ClassNameValue, itemTrailing?: ClassNameValue, itemTrailingIcon?: ClassNameValue, itemTrailingKbds?: ClassNameValue, itemTrailingKbdsSize?: ClassNameValue }`

You can pass any property from the [Link](/docs/components/link#props) component such as `to`, `target`, etc.

```vue
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items = ref<DropdownMenuItem[][]>([
  [
    {
      label: "Benjamin",
      avatar: {
        src: "https://github.com/benjamincanac.png",
        loading: "lazy"
      },
      type: "label"
    }
  ],
  [
    {
      label: "Profile",
      icon: "i-lucide-user"
    },
    {
      label: "Billing",
      icon: "i-lucide-credit-card"
    },
    {
      label: "Settings",
      icon: "i-lucide-cog",
      kbds: [
        ","
      ]
    },
    {
      label: "Keyboard shortcuts",
      icon: "i-lucide-monitor"
    }
  ],
  [
    {
      label: "Team",
      icon: "i-lucide-users"
    },
    {
      label: "Invite users",
      icon: "i-lucide-user-plus",
      children: [
        [
          {
            label: "Email",
            icon: "i-lucide-mail"
          },
          {
            label: "Message",
            icon: "i-lucide-message-square"
          }
        ],
        [
          {
            label: "More",
            icon: "i-lucide-circle-plus",
            children: [
              {
                label: "Import from Slack",
                icon: "i-simple-icons-slack",
                to: "https://slack.com",
                target: "_blank"
              },
              {
                label: "Import from Trello",
                icon: "i-simple-icons-trello"
              },
              {
                label: "Import from Asana",
                icon: "i-simple-icons-asana"
              }
            ]
          }
        ]
      ]
    },
    {
      label: "New team",
      icon: "i-lucide-plus",
      kbds: [
        "meta",
        "n"
      ]
    }
  ],
  [
    {
      label: "GitHub",
      icon: "i-simple-icons-github",
      to: "https://github.com/nuxt/ui",
      target: "_blank"
    },
    {
      label: "Support",
      icon: "i-lucide-life-buoy",
      to: "/docs/components/dropdown-menu"
    },
    {
      label: "API",
      icon: "i-lucide-cloud",
      disabled: true
    }
  ],
  [
    {
      label: "Logout",
      icon: "i-lucide-log-out",
      kbds: [
        "shift",
        "meta",
        "q"
      ]
    }
  ]
])
</script>

<template>
  <UDropdownMenu :items="items" :ui="{
  content: 'w-48'
}">
    <UButton icon="i-lucide-menu" color="neutral" variant="outline" />
  </UDropdownMenu>
</template>
```

> [!NOTE]
> 
> You can also pass an array of arrays to the `items` prop to create separated groups of items.

> [!TIP]
> 
> Each item can take a `children` array of objects with the same properties as the `items` prop to create a nested menu which can be controlled using the `open`, `defaultOpen` and `content` properties.

### Content

Use the `content` prop to control how the DropdownMenu content is rendered, like its `align` or `side` for example.

```vue
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items = ref<DropdownMenuItem[]>([
  {
    label: "Profile",
    icon: "i-lucide-user"
  },
  {
    label: "Billing",
    icon: "i-lucide-credit-card"
  },
  {
    label: "Settings",
    icon: "i-lucide-cog"
  }
])
</script>

<template>
  <UDropdownMenu :items="items" :content="{
  align: 'start',
  side: 'bottom',
  sideOffset: 8
}" :ui="{
  content: 'w-48'
}">
    <UButton label="Open" icon="i-lucide-menu" color="neutral" variant="outline" />
  </UDropdownMenu>
</template>
```

### Filter `4.6+`

Use the `filter` prop to display a filter input inside the DropdownMenu. Defaults to `false`.

> [!NOTE]
> See: #with-ignore-filter
> 
> Use the `ignore-filter` prop to disable the internal search and use your own search logic.

> [!NOTE]
> See: #with-filter-fields
> 
> Use the `filter-fields` prop to specify which fields to filter by. By default, it uses the `labelKey` prop.

You can pass any property from the [Input](/docs/components/input) component to customize it.

```vue
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items = ref<DropdownMenuItem[]>([
  {
    label: "Profile",
    icon: "i-lucide-user"
  },
  {
    label: "Billing",
    icon: "i-lucide-credit-card"
  },
  {
    label: "Settings",
    icon: "i-lucide-cog"
  },
  {
    label: "Team",
    icon: "i-lucide-users"
  },
  {
    label: "Invite users",
    icon: "i-lucide-user-plus"
  },
  {
    label: "New team",
    icon: "i-lucide-plus"
  }
])
</script>

<template>
  <UDropdownMenu :filter="{
  icon: 'i-lucide-search'
}" :items="items" :content="{
  align: 'start'
}" :ui="{
  content: 'w-48'
}">
    <UButton label="Open" icon="i-lucide-menu" color="neutral" variant="outline" />
  </UDropdownMenu>
</template>
```

> [!TIP]
> See: #with-filter-items
> 
> You can also enable the filter on specific sub-menus using the `filter` field on items with `children`.

### Arrow

Use the `arrow` prop to display an arrow on the DropdownMenu.

```vue
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items = ref<DropdownMenuItem[]>([
  {
    label: "Profile",
    icon: "i-lucide-user"
  },
  {
    label: "Billing",
    icon: "i-lucide-credit-card"
  },
  {
    label: "Settings",
    icon: "i-lucide-cog"
  }
])
</script>

<template>
  <UDropdownMenu arrow :items="items" :ui="{
  content: 'w-48'
}">
    <UButton label="Open" icon="i-lucide-menu" color="neutral" variant="outline" />
  </UDropdownMenu>
</template>
```

### Size

Use the `size` prop to control the size of the DropdownMenu.

```vue
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items = ref<DropdownMenuItem[]>([
  {
    label: "Profile",
    icon: "i-lucide-user"
  },
  {
    label: "Billing",
    icon: "i-lucide-credit-card"
  },
  {
    label: "Settings",
    icon: "i-lucide-cog"
  }
])
</script>

<template>
  <UDropdownMenu size="xl" :items="items" :content="{
  align: 'start'
}" :ui="{
  content: 'w-48'
}">
    <UButton size="xl" label="Open" icon="i-lucide-menu" color="neutral" variant="outline" />
  </UDropdownMenu>
</template>
```

> [!WARNING]
> 
> The `size` prop will not be proxied to the Button, you need to set it yourself.

> [!NOTE]
> 
> When using the same size, the DropdownMenu items will be perfectly aligned with the Button.

### Modal

Use the `modal` prop to control whether the DropdownMenu blocks interaction with outside content. Defaults to `true`.

```vue
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items = ref<DropdownMenuItem[]>([
  {
    label: "Profile",
    icon: "i-lucide-user"
  },
  {
    label: "Billing",
    icon: "i-lucide-credit-card"
  },
  {
    label: "Settings",
    icon: "i-lucide-cog"
  }
])
</script>

<template>
  <UDropdownMenu :modal="false" :items="items" :ui="{
  content: 'w-48'
}">
    <UButton label="Open" icon="i-lucide-menu" color="neutral" variant="outline" />
  </UDropdownMenu>
</template>
```

### Disabled

Use the `disabled` prop to disable the DropdownMenu.

```vue
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items = ref<DropdownMenuItem[]>([
  {
    label: "Profile",
    icon: "i-lucide-user"
  },
  {
    label: "Billing",
    icon: "i-lucide-credit-card"
  },
  {
    label: "Settings",
    icon: "i-lucide-cog"
  }
])
</script>

<template>
  <UDropdownMenu disabled :items="items" :ui="{
  content: 'w-48'
}">
    <UButton label="Open" icon="i-lucide-menu" color="neutral" variant="outline" />
  </UDropdownMenu>
</template>
```

## Examples

### With checkbox items

You can use the `type` property with `checkbox` and use the `checked` / `onUpdateChecked` properties to control the checked state of the item.

```vue [DropdownMenuCheckboxItemsExample.vue]
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const showBookmarks = ref(true)
const showHistory = ref(false)
const showDownloads = ref(false)

const items = computed(() => [{
  label: 'Interface',
  icon: 'i-lucide-app-window',
  type: 'label' as const
}, {
  type: 'separator' as const
}, {
  label: 'Show Bookmarks',
  icon: 'i-lucide-bookmark',
  type: 'checkbox' as const,
  checked: showBookmarks.value,
  onUpdateChecked(checked: boolean) {
    showBookmarks.value = checked
  },
  onSelect(e: Event) {
    e.preventDefault()
  }
}, {
  label: 'Show History',
  icon: 'i-lucide-clock',
  type: 'checkbox' as const,
  checked: showHistory.value,
  onUpdateChecked(checked: boolean) {
    showHistory.value = checked
  }
}, {
  label: 'Show Downloads',
  icon: 'i-lucide-download',
  type: 'checkbox' as const,
  checked: showDownloads.value,
  onUpdateChecked(checked: boolean) {
    showDownloads.value = checked
  }
}] satisfies DropdownMenuItem[])
</script>

<template>
  <UDropdownMenu :items="items" :content="{ align: 'start' }" :ui="{ content: 'w-48' }">
    <UButton label="Open" color="neutral" variant="outline" icon="i-lucide-menu" />
  </UDropdownMenu>
</template>
```

> [!NOTE]
> 
> To ensure reactivity for the `checked` state of items, it's recommended to wrap your `items` array inside a `computed`.

### With color items

You can use the `color` property to highlight certain items with a color.

```vue [DropdownMenuColorItemsExample.vue]
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items: DropdownMenuItem[][] = [
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
      color: 'error',
      icon: 'i-lucide-trash'
    }
  ]
]
</script>

<template>
  <UDropdownMenu :items="items" :ui="{ content: 'w-48' }">
    <UButton label="Open" color="neutral" variant="outline" icon="i-lucide-menu" />
  </UDropdownMenu>
</template>
```

### With filter items `4.6+`

You can use the `filter` property on items with `children` to display a filter input inside the sub-menu.

```vue [DropdownMenuFilterItemsExample.vue]
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items: DropdownMenuItem[][] = [[{
  label: 'Profile',
  icon: 'i-lucide-user'
}, {
  label: 'Billing',
  icon: 'i-lucide-credit-card'
}, {
  label: 'Settings',
  icon: 'i-lucide-cog'
}], [{
  label: 'Team',
  icon: 'i-lucide-users',
  filter: {
    placeholder: 'Search members...'
  },
  children: [
    {
      label: 'benjamincanac',
      avatar: { src: 'https://github.com/benjamincanac.png', loading: 'lazy' as const }
    },
    {
      label: 'HugoRCD',
      avatar: { src: 'https://github.com/HugoRCD.png', loading: 'lazy' as const }
    },
    {
      label: 'romhml',
      avatar: { src: 'https://github.com/romhml.png', loading: 'lazy' as const }
    },
    {
      label: 'sandros94',
      avatar: { src: 'https://github.com/sandros94.png', loading: 'lazy' as const }
    },
    {
      label: 'hywax',
      avatar: { src: 'https://github.com/hywax.png', loading: 'lazy' as const }
    },
    {
      label: 'J-Michalek',
      avatar: { src: 'https://github.com/J-Michalek.png', loading: 'lazy' as const }
    },
    {
      label: 'genu',
      avatar: { src: 'https://github.com/genu.png', loading: 'lazy' as const }
    }
  ]
}, {
  label: 'Invite users',
  icon: 'i-lucide-user-plus',
  children: [{
    label: 'Invite by email',
    icon: 'i-lucide-mail'
  }, {
    label: 'Invite by link',
    icon: 'i-lucide-link'
  }, {
    label: 'Invite by SMS',
    icon: 'i-lucide-message-square'
  }, {
    label: 'Invite by WhatsApp',
    icon: 'i-simple-icons-whatsapp'
  }]
}, {
  label: 'New team',
  icon: 'i-lucide-plus'
}]]
</script>

<template>
  <UDropdownMenu :items="items" :content="{ align: 'start' }" :ui="{ content: 'w-48' }">
    <UButton label="Open" color="neutral" variant="outline" icon="i-lucide-menu" />
  </UDropdownMenu>
</template>
```

### Control open state

You can control the open state by using the `default-open` prop or the `v-model:open` directive.

```vue [DropdownMenuOpenExample.vue]
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const open = ref(false)

defineShortcuts({
  o: () => open.value = !open.value
})

const items: DropdownMenuItem[] = [
  {
    label: 'Profile',
    icon: 'i-lucide-user'
  }, {
    label: 'Billing',
    icon: 'i-lucide-credit-card'
  }, {
    label: 'Settings',
    icon: 'i-lucide-cog'
  }
]
</script>

<template>
  <UDropdownMenu v-model:open="open" :items="items" :ui="{ content: 'w-48' }">
    <UButton label="Open" color="neutral" variant="outline" icon="i-lucide-menu" />
  </UDropdownMenu>
</template>
```

> [!NOTE]
> 
> In this example, leveraging [`defineShortcuts`](/docs/composables/define-shortcuts), you can toggle the DropdownMenu by pressing <kbd value="O">
> 
> 
> 
> </kbd>
> 
> .

### With custom slot

Use the `slot` property to customize a specific item.

You will have access to the following slots:

- `#{{ item.slot }}`
- `#{{ item.slot }}-leading`
- `#{{ item.slot }}-label`
- `#{{ item.slot }}-trailing`

```vue [DropdownMenuCustomSlotExample.vue]
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items = [
  {
    label: 'Profile',
    icon: 'i-lucide-user',
    slot: 'profile' as const
  }, {
    label: 'Billing',
    icon: 'i-lucide-credit-card'
  }, {
    label: 'Settings',
    icon: 'i-lucide-cog'
  }
] satisfies DropdownMenuItem[]
</script>

<template>
  <UDropdownMenu :items="items" :ui="{ content: 'w-48' }">
    <UButton label="Open" color="neutral" variant="outline" icon="i-lucide-menu" />

    <template #profile-trailing>
      <UIcon name="i-lucide-badge-check" class="shrink-0 size-5 text-primary" />
    </template>
  </UDropdownMenu>
</template>
```

> [!TIP]
> See: #slots
> 
> You can also use the `#item`, `#item-leading`, `#item-label` and `#item-trailing` slots to customize all items.

### With switch in items

You can use the `slot` property with a `#{{ slot }}-trailing` slot to render a [Switch](/docs/components/switch) inside an item.

```vue [DropdownMenuSwitchItemsExample.vue]
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const showBookmarks = ref(true)
const showHistory = ref(false)
const showDownloads = ref(false)

const items = computed(() => [{
  label: 'Show Bookmarks',
  icon: 'i-lucide-bookmark',
  slot: 'switch' as const,
  checked: showBookmarks.value,
  onSelect(e: Event) {
    e.preventDefault()
    showBookmarks.value = !showBookmarks.value
  }
}, {
  label: 'Show History',
  icon: 'i-lucide-clock',
  slot: 'switch' as const,
  checked: showHistory.value,
  onSelect(e: Event) {
    e.preventDefault()
    showHistory.value = !showHistory.value
  }
}, {
  label: 'Show Downloads',
  icon: 'i-lucide-download',
  slot: 'switch' as const,
  checked: showDownloads.value,
  onSelect(e: Event) {
    e.preventDefault()
    showDownloads.value = !showDownloads.value
  }
}] satisfies DropdownMenuItem[])
</script>

<template>
  <UDropdownMenu :items="items" :content="{ align: 'start' }" :ui="{ content: 'w-48' }">
    <UButton label="Open" color="neutral" variant="outline" icon="i-lucide-menu" />

    <template #switch-trailing="{ item }">
      <USwitch :model-value="item.checked" tabindex="-1" />
    </template>
  </UDropdownMenu>
</template>
```

### With ignore filter `4.6+`

When using the `filter` prop or the `filter` field on items with `children`, you can set the `ignore-filter` prop to `true` to disable the internal search and use your own search logic.

```vue [DropdownMenuIgnoreFilterExample.vue]
<script setup lang="ts">
import { refDebounced } from '@vueuse/core'
import type { DropdownMenuItem } from '@nuxt/ui'

const searchTerm = ref('')
const searchTermDebounced = refDebounced(searchTerm, 200)

const { data: users, status, execute } = await useLazyFetch('https://jsonplaceholder.typicode.com/users', {
  key: 'dropdown-menu-users-search',
  params: { q: searchTermDebounced },
  transform: (data: { id: number, name: string }[]) => {
    return data?.map(user => ({
      label: user.name,
      avatar: { src: `https://i.pravatar.cc/120?img=${user.id}`, loading: 'lazy' as const }
    })) as DropdownMenuItem[]
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
  <UDropdownMenu
    v-model:search-term="searchTerm"
    :items="users || []"
    :filter="{
      icon: 'i-lucide-search',
      loading: status === 'pending'
    }"
    ignore-filter
    :content="{ align: 'start' }"
    :ui="{ content: 'w-48' }"
    @update:open="onOpen"
  >
    <UButton label="Open" color="neutral" variant="outline" icon="i-lucide-menu" />
  </UDropdownMenu>
</template>
```

> [!NOTE]
> 
> This example uses [`refDebounced`](https://vueuse.org/shared/refDebounced/#refdebounced) to debounce the API calls. The fetch is deferred with `immediate: false` so no request is made until the menu opens.

### With filter fields `4.6+`

When using the `filter` prop or the `filter` field on items with `children`, you can set the `filter-fields` prop with an array of fields to filter on. Defaults to `[labelKey]`.

```vue [DropdownMenuFilterFieldsExample.vue]
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items: DropdownMenuItem[] = [{
  label: 'Leanne Graham',
  description: 'Sincere@april.biz'
}, {
  label: 'Ervin Howell',
  description: 'Shanna@melissa.tv'
}, {
  label: 'Clementine Bauch',
  description: 'Nathan@yesenia.net'
}, {
  label: 'Patricia Lebsack',
  description: 'Julianne.OConner@kory.org'
}, {
  label: 'Chelsey Dietrich',
  description: 'Lucio_Hettinger@annie.me'
}]
</script>

<template>
  <UDropdownMenu
    :items="items"
    filter
    :filter-fields="['label', 'description']"
    :content="{ align: 'start' }"
    :ui="{ content: 'w-64' }"
  >
    <UButton label="Open" color="neutral" variant="outline" icon="i-lucide-menu" />
  </UDropdownMenu>
</template>
```

### With trigger content width

You can expand the content to the full width of its button by adding the `w-(--reka-dropdown-menu-trigger-width)` class on the `ui.content` slot.

```vue [DropdownMenuContentWidthExample.vue]
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items: DropdownMenuItem[][] = [
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
      color: 'error',
      icon: 'i-lucide-trash'
    }
  ]
]
</script>

<template>
  <UDropdownMenu :items="items" :ui="{ content: 'w-(--reka-dropdown-menu-trigger-width)' }">
    <UButton
      label="Open"
      class="w-46"
      color="neutral"
      variant="outline"
      block
      trailing-icon="i-lucide-chevron-down"
    />
  </UDropdownMenu>
</template>
```

> [!TIP]
> 
> You can also change the content width globally in your `app.config.ts`:
> 
> ```text
> export default defineAppConfig({
>   ui: {
>     dropdownMenu: {
>       slots: {
>         content: 'w-(--reka-dropdown-menu-trigger-width)'
>       }
>     }
>   }
> })
> ```

### Extract shortcuts

Use the [extractShortcuts](/docs/composables/extract-shortcuts) utility to automatically define shortcuts from menu items with a `kbds` property. It recursively extracts shortcuts and returns an object compatible with [defineShortcuts](/docs/composables/define-shortcuts).

```vue
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items: DropdownMenuItem[] = [{
  label: 'Invite users',
  icon: 'i-lucide-user-plus',
  children: [{
    label: 'Invite by email',
    icon: 'i-lucide-send-horizontal',
    kbds: ['meta', 'e'],
    onSelect() {
      console.log('Invite by email clicked')
    }
  }, {
    label: 'Invite by link',
    icon: 'i-lucide-link',
    kbds: ['meta', 'i'],
    onSelect() {
      console.log('Invite by link clicked')
    }
  }]
}, {
  label: 'New team',
  icon: 'i-lucide-plus',
  kbds: ['meta', 'n'],
  onSelect() {
    console.log('New team clicked')
  }
}]

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
>  <kbd value="E" className="ms-px">
> 
> 
> 
> </kbd>
> 
> , <kbd value="meta">
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
>  and <kbd value="meta">
> 
> 
> 
> </kbd>
> 
>  <kbd value="N" className="ms-px">
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
 * Props for the DropdownMenu component
 */
interface DropdownMenuProps {
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
  content?: (Omit<DropdownMenuContentProps, "as" | "asChild" | "forceMount"> & Partial<EmitsToProps<MenuContentEmits>>) | undefined;
  /**
   * Display an arrow alongside the menu.
   * `{ rounded: true }`{lang="ts-type"}
   */
  arrow?: boolean | Omit<DropdownMenuArrowProps, "as" | "asChild"> | undefined;
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
   * When `true`, items will not be filtered which is useful for custom filtering.
   * @default "false"
   */
  ignoreFilter?: boolean | undefined;
  disabled?: boolean | undefined;
  ui?: { content?: SlotClass; input?: SlotClass; empty?: SlotClass; viewport?: SlotClass; arrow?: SlotClass; group?: SlotClass; label?: SlotClass; separator?: SlotClass; item?: SlotClass; itemLeadingIcon?: SlotClass; itemLeadingAvatar?: SlotClass; itemLeadingAvatarSize?: SlotClass; itemTrailing?: SlotClass; itemTrailingIcon?: SlotClass; itemTrailingKbds?: SlotClass; itemTrailingKbdsSize?: SlotClass; itemWrapper?: SlotClass; itemLabel?: SlotClass; itemDescription?: SlotClass; itemLabelExternalIcon?: SlotClass; } | undefined;
  /**
   * The open state of the dropdown menu when it is initially rendered. Use when you do not need to control its open state.
   */
  defaultOpen?: boolean | undefined;
  /**
   * The controlled open state of the menu. Can be used as `v-model:open`.
   */
  open?: boolean | undefined;
  /**
   * The modality of the dropdown menu.
   * 
   * When set to `true`, interaction with outside elements will be disabled and only menu content will be visible to screen readers.
   * @default "true"
   */
  modal?: boolean | undefined;
  /**
   * @default "\"\""
   */
  searchTerm?: string | undefined;
}
```

### Slots

```ts
/**
 * Slots for the DropdownMenu component
 */
interface DropdownMenuSlots {
  default(): any;
  item(): any;
  item-leading(): any;
  item-label(): any;
  item-description(): any;
  item-trailing(): any;
  empty(): any;
  content-top(): any;
  content-bottom(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the DropdownMenu component
 */
interface DropdownMenuEmits {
  update:open: (payload: [payload: boolean]) => void;
  update:searchTerm: (payload: [value: string]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    dropdownMenu: {
      slots: {
        content: 'min-w-32 max-h-(--reka-dropdown-menu-content-available-height) bg-default shadow-lg rounded-md ring ring-default overflow-hidden data-[state=open]:animate-[scale-in_100ms_ease-out] data-[state=closed]:animate-[scale-out_100ms_ease-in] origin-(--reka-dropdown-menu-content-transform-origin) flex flex-col',
        input: 'border-b border-default',
        empty: 'text-center text-muted',
        viewport: 'relative divide-y divide-default scroll-py-1 overflow-y-auto flex-1',
        arrow: 'fill-bg stroke-default',
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
            empty: 'p-2 text-xs',
            itemLeadingIcon: 'size-4',
            itemLeadingAvatarSize: '3xs',
            itemTrailingIcon: 'size-4',
            itemTrailingKbds: 'gap-0.5',
            itemTrailingKbdsSize: 'sm'
          },
          sm: {
            label: 'p-1.5 text-xs gap-1.5',
            item: 'p-1.5 text-xs gap-1.5',
            empty: 'p-2.5 text-xs',
            itemLeadingIcon: 'size-4',
            itemLeadingAvatarSize: '3xs',
            itemTrailingIcon: 'size-4',
            itemTrailingKbds: 'gap-0.5',
            itemTrailingKbdsSize: 'sm'
          },
          md: {
            label: 'p-1.5 text-sm gap-1.5',
            item: 'p-1.5 text-sm gap-1.5',
            empty: 'p-2.5 text-sm',
            itemLeadingIcon: 'size-5',
            itemLeadingAvatarSize: '2xs',
            itemTrailingIcon: 'size-5',
            itemTrailingKbds: 'gap-0.5',
            itemTrailingKbdsSize: 'md'
          },
          lg: {
            label: 'p-2 text-sm gap-2',
            item: 'p-2 text-sm gap-2',
            empty: 'p-3 text-sm',
            itemLeadingIcon: 'size-5',
            itemLeadingAvatarSize: '2xs',
            itemTrailingIcon: 'size-5',
            itemTrailingKbds: 'gap-1',
            itemTrailingKbdsSize: 'md'
          },
          xl: {
            label: 'p-2 text-base gap-2',
            item: 'p-2 text-base gap-2',
            empty: 'p-3 text-base',
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

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/DropdownMenu.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/dropdown-menu.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

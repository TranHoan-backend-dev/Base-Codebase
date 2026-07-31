---
title: "Breadcrumb"
description: "A hierarchy of links to navigate through a website."
canonical_url: "https://ui.nuxt.com/docs/components/breadcrumb"
---
# Breadcrumb

> A hierarchy of links to navigate through a website.

## Usage

Use the Breadcrumb component to show the current page's location in your site's hierarchy.

```vue
<script setup lang="ts">
import type { BreadcrumbItem } from '@nuxt/ui'

const items = ref<BreadcrumbItem[]>([
  {
    label: "Docs",
    icon: "i-lucide-book-open",
    to: "/docs"
  },
  {
    label: "Components",
    icon: "i-lucide-box",
    to: "/docs/components"
  },
  {
    label: "Breadcrumb",
    icon: "i-lucide-link",
    to: "/docs/components/breadcrumb"
  }
])
</script>

<template>
  <UBreadcrumb :items="items" />
</template>
```

### Items

Use the `items` prop as an array of objects with the following properties:

- `label?: string`
- `icon?: string`
- `avatar?: AvatarProps`
- [`slot?: string`](#with-custom-slot)
- `class?: any`
- `ui?: { item?: ClassNameValue, link?: ClassNameValue, linkLeadingIcon?: ClassNameValue, linkLeadingAvatar?: ClassNameValue, linkLabel?: ClassNameValue, separator?: ClassNameValue, separatorIcon?: ClassNameValue }`

You can pass any property from the [Link](/docs/components/link#props) component such as `to`, `target`, etc.

```vue
<script setup lang="ts">
import type { BreadcrumbItem } from '@nuxt/ui'

const items = ref<BreadcrumbItem[]>([
  {
    label: "Docs",
    icon: "i-lucide-book-open",
    to: "/docs"
  },
  {
    label: "Components",
    icon: "i-lucide-box",
    to: "/docs/components"
  },
  {
    label: "Breadcrumb",
    icon: "i-lucide-link",
    to: "/docs/components/breadcrumb"
  }
])
</script>

<template>
  <UBreadcrumb :items="items" />
</template>
```

> [!NOTE]
> 
> A `span` is rendered instead of a link when the `to` property is not defined.

### Separator Icon

Use the `separator-icon` prop to customize the [Icon](/docs/components/icon) between each item. Defaults to `i-lucide-chevron-right`.

```vue
<script setup lang="ts">
import type { BreadcrumbItem } from '@nuxt/ui'

const items = ref<BreadcrumbItem[]>([
  {
    label: "Docs",
    icon: "i-lucide-book-open",
    to: "/docs"
  },
  {
    label: "Components",
    icon: "i-lucide-box",
    to: "/docs/components"
  },
  {
    label: "Breadcrumb",
    icon: "i-lucide-link",
    to: "/docs/components/breadcrumb"
  }
])
</script>

<template>
  <UBreadcrumb separator-icon="i-lucide-arrow-right" :items="items" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.chevronRight` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.chevronRight` key.

### Color `4.8+`

Use the `color` prop to change the color of the active Breadcrumb.

```vue
<script setup lang="ts">
import type { BreadcrumbItem } from '@nuxt/ui'

const items = ref<BreadcrumbItem[]>([
  {
    label: "Docs",
    icon: "i-lucide-book-open",
    to: "/docs"
  },
  {
    label: "Components",
    icon: "i-lucide-box",
    to: "/docs/components"
  },
  {
    label: "Breadcrumb",
    icon: "i-lucide-link",
    to: "/docs/components/breadcrumb"
  }
])
</script>

<template>
  <UBreadcrumb color="secondary" :items="items" />
</template>
```

## Examples

### With separator slot

Use the `#separator` slot to customize the separator between each item.

```vue [BreadcrumbSeparatorSlotExample.vue]
<script setup lang="ts">
import type { BreadcrumbItem } from '@nuxt/ui'

const items: BreadcrumbItem[] = [
  {
    label: 'Docs',
    to: '/docs'
  },
  {
    label: 'Components',
    to: '/docs/components'
  },
  {
    label: 'Breadcrumb',
    to: '/docs/components/breadcrumb'
  }
]
</script>

<template>
  <UBreadcrumb :items="items">
    <template #separator>
      <span class="mx-2 text-muted">/</span>
    </template>
  </UBreadcrumb>
</template>
```

### With custom slot

Use the `slot` property to customize a specific item.

You will have access to the following slots:

- `#{{ item.slot }}`
- `#{{ item.slot }}-leading`
- `#{{ item.slot }}-label`
- `#{{ item.slot }}-trailing`

```vue [BreadcrumbCustomSlotExample.vue]
<script setup lang="ts">
import type { BreadcrumbItem } from '@nuxt/ui'

const items = [
  {
    label: 'Home',
    to: '/'
  },
  {
    slot: 'dropdown' as const,
    icon: 'i-lucide-ellipsis',
    children: [
      {
        label: 'Documentation',
        to: '/docs'
      },
      {
        label: 'Themes'
      },
      {
        label: 'GitHub'
      }
    ]
  },
  {
    label: 'Components',
    to: '/docs/components'
  },
  {
    label: 'Breadcrumb',
    to: '/docs/components/breadcrumb'
  }
] satisfies BreadcrumbItem[]
</script>

<template>
  <UBreadcrumb :items="items">
    <template #dropdown="{ item }">
      <UDropdownMenu :items="item.children">
        <UButton :icon="item.icon" color="neutral" variant="link" class="p-0.5" />
      </UDropdownMenu>
    </template>
  </UBreadcrumb>
</template>
```

> [!TIP]
> See: #slots
> 
> You can also use the `#item`, `#item-leading`, `#item-label` and `#item-trailing` slots to customize all items.

## API

### Props

```ts
/**
 * Props for the Breadcrumb component
 */
interface BreadcrumbProps {
  /**
   * The element or component this component should render as.
   * @default "\"nav\""
   */
  as?: any;
  items?: T[] | undefined;
  /**
   * The icon to use as a separator.
   */
  separatorIcon?: any;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  /**
   * The key used to get the label from the item.
   * @default "\"label\""
   */
  labelKey?: GetItemKeys<T> | undefined;
  ui?: { root?: SlotClass; list?: SlotClass; item?: SlotClass; link?: SlotClass; linkLeadingIcon?: SlotClass; linkLeadingAvatar?: SlotClass; linkLeadingAvatarSize?: SlotClass; linkLabel?: SlotClass; separator?: SlotClass; separatorIcon?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the Breadcrumb component
 */
interface BreadcrumbSlots {
  item(): any;
  item-leading(): any;
  item-label(): any;
  item-trailing(): any;
  separator(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    breadcrumb: {
      slots: {
        root: 'relative min-w-0',
        list: 'flex items-center gap-1.5',
        item: 'flex min-w-0',
        link: 'group relative flex items-center gap-1.5 text-sm min-w-0 rounded-md',
        linkLeadingIcon: 'shrink-0 size-5',
        linkLeadingAvatar: 'shrink-0',
        linkLeadingAvatarSize: '2xs',
        linkLabel: 'truncate',
        separator: 'flex',
        separatorIcon: 'shrink-0 size-5 text-muted'
      },
      variants: {
        active: {
          true: {
            link: 'font-semibold'
          },
          false: {
            link: 'text-muted font-medium'
          }
        },
        disabled: {
          true: {
            link: 'cursor-not-allowed opacity-75'
          }
        },
        to: {
          true: ''
        },
        color: {
          primary: {
            link: 'outline-primary/25 focus-visible:outline-3'
          },
          secondary: {
            link: 'outline-secondary/25 focus-visible:outline-3'
          },
          success: {
            link: 'outline-success/25 focus-visible:outline-3'
          },
          info: {
            link: 'outline-info/25 focus-visible:outline-3'
          },
          warning: {
            link: 'outline-warning/25 focus-visible:outline-3'
          },
          error: {
            link: 'outline-error/25 focus-visible:outline-3'
          },
          neutral: {
            link: 'outline-inverted/25 focus-visible:outline-3'
          }
        }
      },
      compoundVariants: [
        {
          disabled: false,
          active: false,
          to: true,
          class: {
            link: [
              'hover:text-default',
              'transition-colors'
            ]
          }
        },
        {
          color: 'primary',
          active: true,
          class: {
            link: 'text-primary'
          }
        },
        {
          color: 'secondary',
          active: true,
          class: {
            link: 'text-secondary'
          }
        },
        {
          color: 'success',
          active: true,
          class: {
            link: 'text-success'
          }
        },
        {
          color: 'info',
          active: true,
          class: {
            link: 'text-info'
          }
        },
        {
          color: 'warning',
          active: true,
          class: {
            link: 'text-warning'
          }
        },
        {
          color: 'error',
          active: true,
          class: {
            link: 'text-error'
          }
        },
        {
          color: 'neutral',
          active: true,
          class: {
            link: 'text-highlighted'
          }
        }
      ],
      defaultVariants: {
        color: 'primary'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Breadcrumb.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/breadcrumb.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

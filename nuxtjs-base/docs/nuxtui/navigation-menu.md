---
title: "NavigationMenu"
description: "A list of links that can be displayed horizontally or vertically."
canonical_url: "https://ui.nuxt.com/docs/components/navigation-menu"
---
# NavigationMenu

> A list of links that can be displayed horizontally or vertically.

## Usage

Use the NavigationMenu component to display a list of links horizontally or vertically.

```vue
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = ref<NavigationMenuItem[]>([
  {
    label: "Guide",
    icon: "i-lucide-book-open",
    to: "/docs/getting-started",
    children: [
      {
        label: "Introduction",
        description: "Fully styled and customizable components for Nuxt.",
        icon: "i-lucide-house"
      },
      {
        label: "Installation",
        description: "Learn how to install and configure Nuxt UI in your application.",
        icon: "i-lucide-cloud-download"
      },
      {
        label: "Icons",
        icon: "i-lucide-smile",
        description: "You have nothing to do, @nuxt/icon will handle it automatically."
      },
      {
        label: "Colors",
        icon: "i-lucide-swatch-book",
        description: "Choose a primary and a neutral color from your Tailwind CSS theme."
      },
      {
        label: "Theme",
        icon: "i-lucide-cog",
        description: "You can customize components by using the `class` / `ui` props or in your app.config.ts."
      }
    ]
  },
  {
    label: "Composables",
    icon: "i-lucide-database",
    to: "/docs/composables",
    children: [
      {
        label: "defineShortcuts",
        icon: "i-lucide-file-text",
        description: "Define shortcuts for your application.",
        to: "/docs/composables/define-shortcuts"
      },
      {
        label: "useOverlay",
        icon: "i-lucide-file-text",
        description: "Display a modal/slideover within your application.",
        to: "/docs/composables/use-overlay"
      },
      {
        label: "useToast",
        icon: "i-lucide-file-text",
        description: "Display a toast within your application.",
        to: "/docs/composables/use-toast"
      }
    ]
  },
  {
    label: "Components",
    icon: "i-lucide-box",
    to: "/docs/components",
    active: true,
    children: [
      {
        label: "Link",
        icon: "i-lucide-file-text",
        description: "Use NuxtLink with superpowers.",
        to: "/docs/components/link"
      },
      {
        label: "Modal",
        icon: "i-lucide-file-text",
        description: "Display a modal within your application.",
        to: "/docs/components/modal"
      },
      {
        label: "NavigationMenu",
        icon: "i-lucide-file-text",
        description: "Display a list of links.",
        to: "/docs/components/navigation-menu"
      },
      {
        label: "Pagination",
        icon: "i-lucide-file-text",
        description: "Display a list of pages.",
        to: "/docs/components/pagination"
      },
      {
        label: "Popover",
        icon: "i-lucide-file-text",
        description: "Display a non-modal dialog that floats around a trigger element.",
        to: "/docs/components/popover"
      },
      {
        label: "Progress",
        icon: "i-lucide-file-text",
        description: "Show a horizontal bar to indicate task progression.",
        to: "/docs/components/progress"
      }
    ]
  },
  {
    label: "GitHub",
    icon: "i-simple-icons-github",
    badge: "6k",
    to: "https://github.com/nuxt/ui",
    target: "_blank"
  },
  {
    label: "Help",
    icon: "i-lucide-circle-help",
    disabled: true
  }
])
</script>

<template>
  <UNavigationMenu :items="items" />
</template>
```

### Items

Use the `items` prop as an array of objects with the following properties:

- `label?: string`
- `icon?: string`
- `avatar?: AvatarProps`
- `badge?: string | number | BadgeProps`
- [`chip?: boolean | ChipProps`](#with-chip-in-items)
- [`tooltip?: TooltipProps`](#with-tooltip-in-items)
- [`popover?: PopoverProps`](#with-popover-in-items)
- `trailingIcon?: string`
- `type?: 'label' | 'trigger' | 'link'`
- `defaultOpen?: boolean`
- `open?: boolean`
- `value?: string`
- `disabled?: boolean`
- [`slot?: string`](#with-custom-slot)
- `onSelect?: (e: Event) => void`
- `children?: NavigationMenuChildItem[]`
- `class?: any`
- `ui?: { linkLeadingAvatarSize?: ClassNameValue, linkLeadingAvatar?: ClassNameValue, linkLeadingIcon?: ClassNameValue, linkLeadingChipSize?: ClassNameValue, linkLabel?: ClassNameValue, linkLabelExternalIcon?: ClassNameValue, linkTrailing?: ClassNameValue, linkTrailingBadgeSize?: ClassNameValue, linkTrailingBadge?: ClassNameValue, linkTrailingIcon?: ClassNameValue, label?: ClassNameValue, link?: ClassNameValue, content?: ClassNameValue, childList?: ClassNameValue, childLabel?: ClassNameValue, childItem?: ClassNameValue, childLink?: ClassNameValue, childLinkIcon?: ClassNameValue, childLinkWrapper?: ClassNameValue, childLinkLabel?: ClassNameValue, childLinkLabelExternalIcon?: ClassNameValue, childLinkDescription?: ClassNameValue }`

You can pass any property from the [Link](/docs/components/link#props) component such as `to`, `target`, etc.

```vue
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = ref<NavigationMenuItem[]>([
  {
    label: "Guide",
    icon: "i-lucide-book-open",
    to: "/docs/getting-started",
    children: [
      {
        label: "Introduction",
        description: "Fully styled and customizable components for Nuxt.",
        icon: "i-lucide-house"
      },
      {
        label: "Installation",
        description: "Learn how to install and configure Nuxt UI in your application.",
        icon: "i-lucide-cloud-download"
      },
      {
        label: "Icons",
        icon: "i-lucide-smile",
        description: "You have nothing to do, @nuxt/icon will handle it automatically."
      },
      {
        label: "Colors",
        icon: "i-lucide-swatch-book",
        description: "Choose a primary and a neutral color from your Tailwind CSS theme."
      },
      {
        label: "Theme",
        icon: "i-lucide-cog",
        description: "You can customize components by using the `class` / `ui` props or in your app.config.ts."
      }
    ]
  },
  {
    label: "Composables",
    icon: "i-lucide-database",
    to: "/docs/composables",
    children: [
      {
        label: "defineShortcuts",
        icon: "i-lucide-file-text",
        description: "Define shortcuts for your application.",
        to: "/docs/composables/define-shortcuts"
      },
      {
        label: "useOverlay",
        icon: "i-lucide-file-text",
        description: "Display a modal/slideover within your application.",
        to: "/docs/composables/use-overlay"
      },
      {
        label: "useToast",
        icon: "i-lucide-file-text",
        description: "Display a toast within your application.",
        to: "/docs/composables/use-toast"
      }
    ]
  },
  {
    label: "Components",
    icon: "i-lucide-box",
    to: "/docs/components",
    active: true,
    children: [
      {
        label: "Link",
        icon: "i-lucide-file-text",
        description: "Use NuxtLink with superpowers.",
        to: "/docs/components/link"
      },
      {
        label: "Modal",
        icon: "i-lucide-file-text",
        description: "Display a modal within your application.",
        to: "/docs/components/modal"
      },
      {
        label: "NavigationMenu",
        icon: "i-lucide-file-text",
        description: "Display a list of links.",
        to: "/docs/components/navigation-menu"
      },
      {
        label: "Pagination",
        icon: "i-lucide-file-text",
        description: "Display a list of pages.",
        to: "/docs/components/pagination"
      },
      {
        label: "Popover",
        icon: "i-lucide-file-text",
        description: "Display a non-modal dialog that floats around a trigger element.",
        to: "/docs/components/popover"
      },
      {
        label: "Progress",
        icon: "i-lucide-file-text",
        description: "Show a horizontal bar to indicate task progression.",
        to: "/docs/components/progress"
      }
    ]
  },
  {
    label: "GitHub",
    icon: "i-simple-icons-github",
    badge: "6k",
    to: "https://github.com/nuxt/ui",
    target: "_blank"
  },
  {
    label: "Help",
    icon: "i-lucide-circle-help",
    disabled: true
  }
])
</script>

<template>
  <UNavigationMenu :items="items" class="w-full justify-center" />
</template>
```

> [!NOTE]
> 
> You can also pass an array of arrays to the `items` prop to display groups of items.

> [!TIP]
> 
> Each item can take a `children` array of objects with the following properties to create submenus:
> 
> - `label: string`
> - `description?: string`
> - `icon?: string`
> - `onSelect?: (e: Event) => void`
> - `class?: any`

### Orientation

Use the `orientation` prop to change the orientation of the NavigationMenu.

> [!NOTE]
> 
> When orientation is `vertical`, an [Accordion](/docs/components/accordion) component is used to display each group. You can control the open state of each item using the `open` and `defaultOpen` properties and change the behavior using the [`collapsible`](/docs/components/accordion#collapsible) and [`type`](/docs/components/accordion#multiple) props.

```vue
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = ref<NavigationMenuItem[][]>([
  [
    {
      label: "Links",
      type: "label"
    },
    {
      label: "Guide",
      icon: "i-lucide-book-open",
      children: [
        {
          label: "Introduction",
          description: "Fully styled and customizable components for Nuxt.",
          icon: "i-lucide-house"
        },
        {
          label: "Installation",
          description: "Learn how to install and configure Nuxt UI in your application.",
          icon: "i-lucide-cloud-download"
        },
        {
          label: "Icons",
          icon: "i-lucide-smile",
          description: "You have nothing to do, @nuxt/icon will handle it automatically."
        },
        {
          label: "Colors",
          icon: "i-lucide-swatch-book",
          description: "Choose a primary and a neutral color from your Tailwind CSS theme."
        },
        {
          label: "Theme",
          icon: "i-lucide-cog",
          description: "You can customize components by using the `class` / `ui` props or in your app.config.ts."
        }
      ]
    },
    {
      label: "Composables",
      icon: "i-lucide-database",
      children: [
        {
          label: "defineShortcuts",
          icon: "i-lucide-file-text",
          description: "Define shortcuts for your application.",
          to: "/docs/composables/define-shortcuts"
        },
        {
          label: "useOverlay",
          icon: "i-lucide-file-text",
          description: "Display a modal/slideover within your application.",
          to: "/docs/composables/use-overlay"
        },
        {
          label: "useToast",
          icon: "i-lucide-file-text",
          description: "Display a toast within your application.",
          to: "/docs/composables/use-toast"
        }
      ]
    },
    {
      label: "Components",
      icon: "i-lucide-box",
      to: "/docs/components",
      type: "trigger",
      active: true,
      defaultOpen: true,
      children: [
        {
          label: "Link",
          icon: "i-lucide-file-text",
          description: "Use NuxtLink with superpowers.",
          to: "/docs/components/link"
        },
        {
          label: "Modal",
          icon: "i-lucide-file-text",
          description: "Display a modal within your application.",
          to: "/docs/components/modal"
        },
        {
          label: "NavigationMenu",
          icon: "i-lucide-file-text",
          description: "Display a list of links.",
          to: "/docs/components/navigation-menu"
        },
        {
          label: "Pagination",
          icon: "i-lucide-file-text",
          description: "Display a list of pages.",
          to: "/docs/components/pagination"
        },
        {
          label: "Popover",
          icon: "i-lucide-file-text",
          description: "Display a non-modal dialog that floats around a trigger element.",
          to: "/docs/components/popover"
        },
        {
          label: "Progress",
          icon: "i-lucide-file-text",
          description: "Show a horizontal bar to indicate task progression.",
          to: "/docs/components/progress"
        }
      ]
    }
  ],
  [
    {
      label: "GitHub",
      icon: "i-simple-icons-github",
      badge: "6k",
      to: "https://github.com/nuxt/ui",
      target: "_blank"
    },
    {
      label: "Help",
      icon: "i-lucide-circle-help",
      disabled: true
    }
  ]
])
</script>

<template>
  <UNavigationMenu orientation="vertical" :items="items" class="data-[orientation=vertical]:w-48" />
</template>
```

> [!NOTE]
> 
> Groups will be spaced when orientation is `horizontal` and separated when orientation is `vertical`.

### Collapsed

In `vertical` orientation, use the `collapsed` prop to collapse the NavigationMenu, this can be useful in a sidebar for example.

> [!NOTE]
> 
> You can use the [`tooltip`](#with-tooltip-in-items) and [`popover`](#with-popover-in-items) props to display more information on the collapsed items.

```vue
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = ref<NavigationMenuItem[][]>([
  [
    {
      label: "Links",
      type: "label"
    },
    {
      label: "Guide",
      icon: "i-lucide-book-open",
      children: [
        {
          label: "Introduction",
          description: "Fully styled and customizable components for Nuxt.",
          icon: "i-lucide-house"
        },
        {
          label: "Installation",
          description: "Learn how to install and configure Nuxt UI in your application.",
          icon: "i-lucide-cloud-download"
        },
        {
          label: "Icons",
          icon: "i-lucide-smile",
          description: "You have nothing to do, @nuxt/icon will handle it automatically."
        },
        {
          label: "Colors",
          icon: "i-lucide-swatch-book",
          description: "Choose a primary and a neutral color from your Tailwind CSS theme."
        },
        {
          label: "Theme",
          icon: "i-lucide-cog",
          description: "You can customize components by using the `class` / `ui` props or in your app.config.ts."
        }
      ]
    },
    {
      label: "Composables",
      icon: "i-lucide-database",
      children: [
        {
          label: "defineShortcuts",
          icon: "i-lucide-file-text",
          description: "Define shortcuts for your application.",
          to: "/docs/composables/define-shortcuts"
        },
        {
          label: "useOverlay",
          icon: "i-lucide-file-text",
          description: "Display a modal/slideover within your application.",
          to: "/docs/composables/use-overlay"
        },
        {
          label: "useToast",
          icon: "i-lucide-file-text",
          description: "Display a toast within your application.",
          to: "/docs/composables/use-toast"
        }
      ]
    },
    {
      label: "Components",
      icon: "i-lucide-box",
      to: "/docs/components",
      active: true,
      children: [
        {
          label: "Link",
          icon: "i-lucide-file-text",
          description: "Use NuxtLink with superpowers.",
          to: "/docs/components/link"
        },
        {
          label: "Modal",
          icon: "i-lucide-file-text",
          description: "Display a modal within your application.",
          to: "/docs/components/modal"
        },
        {
          label: "NavigationMenu",
          icon: "i-lucide-file-text",
          description: "Display a list of links.",
          to: "/docs/components/navigation-menu"
        },
        {
          label: "Pagination",
          icon: "i-lucide-file-text",
          description: "Display a list of pages.",
          to: "/docs/components/pagination"
        },
        {
          label: "Popover",
          icon: "i-lucide-file-text",
          description: "Display a non-modal dialog that floats around a trigger element.",
          to: "/docs/components/popover"
        },
        {
          label: "Progress",
          icon: "i-lucide-file-text",
          description: "Show a horizontal bar to indicate task progression.",
          to: "/docs/components/progress"
        }
      ]
    }
  ],
  [
    {
      label: "GitHub",
      icon: "i-simple-icons-github",
      badge: "6k",
      to: "https://github.com/nuxt/ui",
      target: "_blank"
    },
    {
      label: "Help",
      icon: "i-lucide-circle-help",
      disabled: true
    }
  ]
])
</script>

<template>
  <UNavigationMenu collapsed :tooltip="false" :popover="false" orientation="vertical" :items="items" />
</template>
```

### Highlight

Use the `highlight` prop to display a highlighted border for the active item.

Use the `highlight-color` prop to change the color of the border. It defaults to the `color` prop.

```vue
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = ref<NavigationMenuItem[][]>([
  [
    {
      label: "Guide",
      icon: "i-lucide-book-open",
      children: [
        {
          label: "Introduction",
          description: "Fully styled and customizable components for Nuxt.",
          icon: "i-lucide-house"
        },
        {
          label: "Installation",
          description: "Learn how to install and configure Nuxt UI in your application.",
          icon: "i-lucide-cloud-download"
        },
        {
          label: "Icons",
          icon: "i-lucide-smile",
          description: "You have nothing to do, @nuxt/icon will handle it automatically."
        },
        {
          label: "Colors",
          icon: "i-lucide-swatch-book",
          description: "Choose a primary and a neutral color from your Tailwind CSS theme."
        },
        {
          label: "Theme",
          icon: "i-lucide-cog",
          description: "You can customize components by using the `class` / `ui` props or in your app.config.ts."
        }
      ]
    },
    {
      label: "Composables",
      icon: "i-lucide-database",
      children: [
        {
          label: "defineShortcuts",
          icon: "i-lucide-file-text",
          description: "Define shortcuts for your application.",
          to: "/docs/composables/define-shortcuts"
        },
        {
          label: "useOverlay",
          icon: "i-lucide-file-text",
          description: "Display a modal/slideover within your application.",
          to: "/docs/composables/use-overlay"
        },
        {
          label: "useToast",
          icon: "i-lucide-file-text",
          description: "Display a toast within your application.",
          to: "/docs/composables/use-toast"
        }
      ]
    },
    {
      label: "Components",
      icon: "i-lucide-box",
      to: "/docs/components",
      active: true,
      defaultOpen: true,
      children: [
        {
          label: "Link",
          icon: "i-lucide-file-text",
          description: "Use NuxtLink with superpowers.",
          to: "/docs/components/link"
        },
        {
          label: "Modal",
          icon: "i-lucide-file-text",
          description: "Display a modal within your application.",
          to: "/docs/components/modal"
        },
        {
          label: "NavigationMenu",
          icon: "i-lucide-file-text",
          description: "Display a list of links.",
          to: "/docs/components/navigation-menu"
        },
        {
          label: "Pagination",
          icon: "i-lucide-file-text",
          description: "Display a list of pages.",
          to: "/docs/components/pagination"
        },
        {
          label: "Popover",
          icon: "i-lucide-file-text",
          description: "Display a non-modal dialog that floats around a trigger element.",
          to: "/docs/components/popover"
        },
        {
          label: "Progress",
          icon: "i-lucide-file-text",
          description: "Show a horizontal bar to indicate task progression.",
          to: "/docs/components/progress"
        }
      ]
    }
  ],
  [
    {
      label: "GitHub",
      icon: "i-simple-icons-github",
      badge: "6k",
      to: "https://github.com/nuxt/ui",
      target: "_blank"
    },
    {
      label: "Help",
      icon: "i-lucide-circle-help",
      disabled: true
    }
  ]
])
</script>

<template>
  <UNavigationMenu highlight highlight-color="primary" orientation="horizontal" :items="items" class="data-[orientation=horizontal]:border-b border-default data-[orientation=horizontal]:w-full data-[orientation=vertical]:w-48" />
</template>
```

> [!NOTE]
> 
> In this example, the `border-b` class is applied to display a border in `horizontal` orientation, this is not done by default to let you have a clean slate to work with.

> [!CAUTION]
> 
> In `vertical` orientation, the `highlight` prop only highlights the border of active children.

### Color

Use the `color` prop to change the color of the NavigationMenu.

```vue
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = ref<NavigationMenuItem[][]>([
  [
    {
      label: "Guide",
      icon: "i-lucide-book-open",
      to: "/docs/getting-started"
    },
    {
      label: "Composables",
      icon: "i-lucide-database",
      to: "/docs/composables"
    },
    {
      label: "Components",
      icon: "i-lucide-box",
      to: "/docs/components",
      active: true
    }
  ],
  [
    {
      label: "GitHub",
      icon: "i-simple-icons-github",
      badge: "6k",
      to: "https://github.com/nuxt/ui",
      target: "_blank"
    }
  ]
])
</script>

<template>
  <UNavigationMenu color="neutral" :items="items" class="w-full" />
</template>
```

### Variant

Use the `variant` prop to change the variant of the NavigationMenu.

```vue
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = ref<NavigationMenuItem[][]>([
  [
    {
      label: "Guide",
      icon: "i-lucide-book-open",
      to: "/docs/getting-started"
    },
    {
      label: "Composables",
      icon: "i-lucide-database",
      to: "/docs/composables"
    },
    {
      label: "Components",
      icon: "i-lucide-box",
      to: "/docs/components",
      active: true
    }
  ],
  [
    {
      label: "GitHub",
      icon: "i-simple-icons-github",
      badge: "6k",
      to: "https://github.com/nuxt/ui",
      target: "_blank"
    }
  ]
])
</script>

<template>
  <UNavigationMenu color="neutral" variant="link" :highlight="false" :items="items" class="w-full" />
</template>
```

> [!NOTE]
> 
> The `highlight` prop changes the `pill` variant active item style. Try it out to see the difference.

### Trailing Icon

Use the `trailing-icon` prop to customize the trailing [Icon](/docs/components/icon) of each item. Defaults to `i-lucide-chevron-down`. This icon is only displayed when an item has children.

> [!TIP]
> 
> You can also set an icon for a specific item by using the `trailingIcon` property in the item object.

```vue
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = ref<NavigationMenuItem[]>([
  {
    label: "Guide",
    icon: "i-lucide-book-open",
    to: "/docs/getting-started",
    children: [
      {
        label: "Introduction",
        description: "Fully styled and customizable components for Nuxt.",
        icon: "i-lucide-house"
      },
      {
        label: "Installation",
        description: "Learn how to install and configure Nuxt UI in your application.",
        icon: "i-lucide-cloud-download"
      },
      {
        label: "Icons",
        icon: "i-lucide-smile",
        description: "You have nothing to do, @nuxt/icon will handle it automatically."
      },
      {
        label: "Colors",
        icon: "i-lucide-swatch-book",
        description: "Choose a primary and a neutral color from your Tailwind CSS theme."
      },
      {
        label: "Theme",
        icon: "i-lucide-cog",
        description: "You can customize components by using the `class` / `ui` props or in your app.config.ts."
      }
    ]
  },
  {
    label: "Composables",
    icon: "i-lucide-database",
    to: "/docs/composables",
    children: [
      {
        label: "defineShortcuts",
        icon: "i-lucide-file-text",
        description: "Define shortcuts for your application.",
        to: "/docs/composables/define-shortcuts"
      },
      {
        label: "useOverlay",
        icon: "i-lucide-file-text",
        description: "Display a modal/slideover within your application.",
        to: "/docs/composables/use-overlay"
      },
      {
        label: "useToast",
        icon: "i-lucide-file-text",
        description: "Display a toast within your application.",
        to: "/docs/composables/use-toast"
      }
    ]
  },
  {
    label: "Components",
    icon: "i-lucide-box",
    to: "/docs/components",
    active: true,
    children: [
      {
        label: "Link",
        icon: "i-lucide-file-text",
        description: "Use NuxtLink with superpowers.",
        to: "/docs/components/link"
      },
      {
        label: "Modal",
        icon: "i-lucide-file-text",
        description: "Display a modal within your application.",
        to: "/docs/components/modal"
      },
      {
        label: "NavigationMenu",
        icon: "i-lucide-file-text",
        description: "Display a list of links.",
        to: "/docs/components/navigation-menu"
      },
      {
        label: "Pagination",
        icon: "i-lucide-file-text",
        description: "Display a list of pages.",
        to: "/docs/components/pagination"
      },
      {
        label: "Popover",
        icon: "i-lucide-file-text",
        description: "Display a non-modal dialog that floats around a trigger element.",
        to: "/docs/components/popover"
      },
      {
        label: "Progress",
        icon: "i-lucide-file-text",
        description: "Show a horizontal bar to indicate task progression.",
        to: "/docs/components/progress"
      }
    ]
  }
])
</script>

<template>
  <UNavigationMenu trailing-icon="i-lucide-arrow-down" :items="items" class="w-full justify-center" />
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

### Arrow

Use the `arrow` prop to display an arrow on the NavigationMenu content when items have children.

```vue
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = ref<NavigationMenuItem[]>([
  {
    label: "Guide",
    icon: "i-lucide-book-open",
    to: "/docs/getting-started",
    children: [
      {
        label: "Introduction",
        description: "Fully styled and customizable components for Nuxt.",
        icon: "i-lucide-house"
      },
      {
        label: "Installation",
        description: "Learn how to install and configure Nuxt UI in your application.",
        icon: "i-lucide-cloud-download"
      },
      {
        label: "Icons",
        icon: "i-lucide-smile",
        description: "You have nothing to do, @nuxt/icon will handle it automatically."
      },
      {
        label: "Colors",
        icon: "i-lucide-swatch-book",
        description: "Choose a primary and a neutral color from your Tailwind CSS theme."
      },
      {
        label: "Theme",
        icon: "i-lucide-cog",
        description: "You can customize components by using the `class` / `ui` props or in your app.config.ts."
      }
    ]
  },
  {
    label: "Composables",
    icon: "i-lucide-database",
    to: "/docs/composables",
    children: [
      {
        label: "defineShortcuts",
        icon: "i-lucide-file-text",
        description: "Define shortcuts for your application.",
        to: "/docs/composables/define-shortcuts"
      },
      {
        label: "useOverlay",
        icon: "i-lucide-file-text",
        description: "Display a modal/slideover within your application.",
        to: "/docs/composables/use-overlay"
      },
      {
        label: "useToast",
        icon: "i-lucide-file-text",
        description: "Display a toast within your application.",
        to: "/docs/composables/use-toast"
      }
    ]
  },
  {
    label: "Components",
    icon: "i-lucide-box",
    to: "/docs/components",
    active: true,
    children: [
      {
        label: "Link",
        icon: "i-lucide-file-text",
        description: "Use NuxtLink with superpowers.",
        to: "/docs/components/link"
      },
      {
        label: "Modal",
        icon: "i-lucide-file-text",
        description: "Display a modal within your application.",
        to: "/docs/components/modal"
      },
      {
        label: "NavigationMenu",
        icon: "i-lucide-file-text",
        description: "Display a list of links.",
        to: "/docs/components/navigation-menu"
      },
      {
        label: "Pagination",
        icon: "i-lucide-file-text",
        description: "Display a list of pages.",
        to: "/docs/components/pagination"
      },
      {
        label: "Popover",
        icon: "i-lucide-file-text",
        description: "Display a non-modal dialog that floats around a trigger element.",
        to: "/docs/components/popover"
      },
      {
        label: "Progress",
        icon: "i-lucide-file-text",
        description: "Show a horizontal bar to indicate task progression.",
        to: "/docs/components/progress"
      }
    ]
  }
])
</script>

<template>
  <UNavigationMenu arrow :items="items" class="w-full justify-center" />
</template>
```

> [!NOTE]
> 
> The arrow is animated to follow the active item.

### Content Orientation

Use the `content-orientation` prop to change the orientation of the content.

> [!WARNING]
> 
> This prop only works when `orientation` is `horizontal`.

```vue
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = ref<NavigationMenuItem[]>([
  {
    label: "Guide",
    icon: "i-lucide-book-open",
    to: "/docs/getting-started",
    children: [
      {
        label: "Introduction",
        description: "Fully styled and customizable components for Nuxt.",
        icon: "i-lucide-house"
      },
      {
        label: "Installation",
        description: "Learn how to install and configure Nuxt UI in your application.",
        icon: "i-lucide-cloud-download"
      },
      {
        label: "Icons",
        icon: "i-lucide-smile",
        description: "You have nothing to do, @nuxt/icon will handle it automatically."
      }
    ]
  },
  {
    label: "Composables",
    icon: "i-lucide-database",
    to: "/docs/composables",
    children: [
      {
        label: "defineShortcuts",
        icon: "i-lucide-file-text",
        description: "Define shortcuts for your application.",
        to: "/docs/composables/define-shortcuts"
      },
      {
        label: "useOverlay",
        icon: "i-lucide-file-text",
        description: "Display a modal/slideover within your application.",
        to: "/docs/composables/use-overlay"
      },
      {
        label: "useToast",
        icon: "i-lucide-file-text",
        description: "Display a toast within your application.",
        to: "/docs/composables/use-toast"
      }
    ]
  },
  {
    label: "Components",
    icon: "i-lucide-box",
    to: "/docs/components",
    active: true,
    children: [
      {
        label: "Link",
        icon: "i-lucide-file-text",
        description: "Use NuxtLink with superpowers.",
        to: "/docs/components/link"
      },
      {
        label: "Modal",
        icon: "i-lucide-file-text",
        description: "Display a modal within your application.",
        to: "/docs/components/modal"
      },
      {
        label: "NavigationMenu",
        icon: "i-lucide-file-text",
        description: "Display a list of links.",
        to: "/docs/components/navigation-menu"
      },
      {
        label: "Pagination",
        icon: "i-lucide-file-text",
        description: "Display a list of pages.",
        to: "/docs/components/pagination"
      }
    ]
  }
])
</script>

<template>
  <UNavigationMenu arrow content-orientation="vertical" :items="items" class="w-full justify-center" />
</template>
```

### Unmount

Use the `unmount-on-hide` prop to control the content unmounting behavior. Defaults to `true`.

```vue
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = ref<NavigationMenuItem[]>([
  {
    label: "Guide",
    icon: "i-lucide-book-open",
    to: "/docs/getting-started",
    children: [
      {
        label: "Introduction",
        description: "Fully styled and customizable components for Nuxt.",
        icon: "i-lucide-house"
      },
      {
        label: "Installation",
        description: "Learn how to install and configure Nuxt UI in your application.",
        icon: "i-lucide-cloud-download"
      },
      {
        label: "Icons",
        icon: "i-lucide-smile",
        description: "You have nothing to do, @nuxt/icon will handle it automatically."
      },
      {
        label: "Colors",
        icon: "i-lucide-swatch-book",
        description: "Choose a primary and a neutral color from your Tailwind CSS theme."
      },
      {
        label: "Theme",
        icon: "i-lucide-cog",
        description: "You can customize components by using the `class` / `ui` props or in your app.config.ts."
      }
    ]
  },
  {
    label: "Composables",
    icon: "i-lucide-database",
    to: "/docs/composables",
    children: [
      {
        label: "defineShortcuts",
        icon: "i-lucide-file-text",
        description: "Define shortcuts for your application.",
        to: "/docs/composables/define-shortcuts"
      },
      {
        label: "useOverlay",
        icon: "i-lucide-file-text",
        description: "Display a modal/slideover within your application.",
        to: "/docs/composables/use-overlay"
      },
      {
        label: "useToast",
        icon: "i-lucide-file-text",
        description: "Display a toast within your application.",
        to: "/docs/composables/use-toast"
      }
    ]
  },
  {
    label: "Components",
    icon: "i-lucide-box",
    to: "/docs/components",
    active: true,
    children: [
      {
        label: "Link",
        icon: "i-lucide-file-text",
        description: "Use NuxtLink with superpowers.",
        to: "/docs/components/link"
      },
      {
        label: "Modal",
        icon: "i-lucide-file-text",
        description: "Display a modal within your application.",
        to: "/docs/components/modal"
      },
      {
        label: "NavigationMenu",
        icon: "i-lucide-file-text",
        description: "Display a list of links.",
        to: "/docs/components/navigation-menu"
      },
      {
        label: "Pagination",
        icon: "i-lucide-file-text",
        description: "Display a list of pages.",
        to: "/docs/components/pagination"
      },
      {
        label: "Popover",
        icon: "i-lucide-file-text",
        description: "Display a non-modal dialog that floats around a trigger element.",
        to: "/docs/components/popover"
      },
      {
        label: "Progress",
        icon: "i-lucide-file-text",
        description: "Show a horizontal bar to indicate task progression.",
        to: "/docs/components/progress"
      }
    ]
  }
])
</script>

<template>
  <UNavigationMenu :unmount-on-hide="false" :items="items" class="w-full justify-center" />
</template>
```

> [!NOTE]
> 
> You can inspect the DOM to see each item's content being rendered.

## Examples

### Control active item

You can control the active item(s) by using the `default-value` prop or the `v-model` directive with the `value` of the item. If no `value` is provided, it defaults to `item-${index}` for top-level items or `item-${level}-${index}` for nested items.

```vue [NavigationMenuModelValueExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items: NavigationMenuItem[] = [
  {
    label: 'Guide',
    icon: 'i-lucide-book-open',
    children: [
      {
        label: 'Introduction',
        description: 'Fully styled and customizable components for Nuxt.',
        icon: 'i-lucide-house'
      },
      {
        label: 'Installation',
        description: 'Learn how to install and configure Nuxt UI in your application.',
        icon: 'i-lucide-cloud-download'
      },
      {
        label: 'Icons',
        icon: 'i-lucide-smile',
        description: 'You have nothing to do, @nuxt/icon will handle it automatically.'
      },
      {
        label: 'Colors',
        icon: 'i-lucide-swatch-book',
        description: 'Choose a primary and a neutral color from your Tailwind CSS theme.'
      },
      {
        label: 'Theme',
        icon: 'i-lucide-cog',
        description: 'You can customize components by using the `class` / `ui` props or in your app.config.ts.'
      }
    ]
  },
  {
    label: 'Composables',
    icon: 'i-lucide-database',
    children: [
      {
        label: 'defineShortcuts',
        icon: 'i-lucide-file-text',
        description: 'Define shortcuts for your application.'
      },
      {
        label: 'useOverlay',
        icon: 'i-lucide-file-text',
        description: 'Display a modal/slideover within your application.'
      },
      {
        label: 'useToast',
        icon: 'i-lucide-file-text',
        description: 'Display a toast within your application.'
      }
    ]
  },
  {
    label: 'Components',
    icon: 'i-lucide-box',
    children: [
      {
        label: 'Link',
        icon: 'i-lucide-file-text',
        description: 'Use NuxtLink with superpowers.'
      },
      {
        label: 'Modal',
        icon: 'i-lucide-file-text',
        description: 'Display a modal within your application.'
      },
      {
        label: 'NavigationMenu',
        icon: 'i-lucide-file-text',
        description: 'Display a list of links.'
      },
      {
        label: 'Pagination',
        icon: 'i-lucide-file-text',
        description: 'Display a list of pages.'
      },
      {
        label: 'Popover',
        icon: 'i-lucide-file-text',
        description: 'Display a non-modal dialog that floats around a trigger element.'
      },
      {
        label: 'Progress',
        icon: 'i-lucide-file-text',
        description: 'Show a horizontal bar to indicate task progression.'
      }
    ]
  }
]

const active = ref()

defineShortcuts({
  1: () => {
    active.value = 'item-0'
  },
  2: () => {
    active.value = 'item-1'
  },
  3: () => {
    active.value = 'item-2'
  }
})
</script>

<template>
  <UNavigationMenu v-model="active" :items="items" class="w-full justify-center" />
</template>
```

> [!TIP]
> 
> Use the `value-key` prop to change the key used to match items when a `v-model` or `default-value` is provided.

> [!NOTE]
> 
> In this example, leveraging [`defineShortcuts`](/docs/composables/define-shortcuts), you can switch the active item by pressing <kbd value="1">
> 
> 
> 
> </kbd>
> 
> , <kbd value="2">
> 
> 
> 
> </kbd>
> 
> , or <kbd value="3">
> 
> 
> 
> </kbd>
> 
> .

### With tooltip in items

When orientation is `vertical` and the menu is `collapsed`, you can set the `tooltip` prop to `true` to display a [Tooltip](/docs/components/tooltip) around items with their label but you can also use the `tooltip` property on each item to override the default tooltip. In `horizontal` orientation, you can use the `tooltip` property on each item to display a [Tooltip](/docs/components/tooltip) around items.

> [!NOTE]
> 
> The `tooltip` property on an item will always display a tooltip regardless of the global `tooltip` prop.

You can pass any property from the [Tooltip](/docs/components/tooltip) component globally or on each item.

```vue
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = ref<NavigationMenuItem[][]>([
  [
    {
      label: "Links",
      type: "label"
    },
    {
      label: "Guide",
      icon: "i-lucide-book-open",
      children: [
        {
          label: "Introduction",
          description: "Fully styled and customizable components for Nuxt.",
          icon: "i-lucide-house"
        },
        {
          label: "Installation",
          description: "Learn how to install and configure Nuxt UI in your application.",
          icon: "i-lucide-cloud-download"
        },
        {
          label: "Icons",
          icon: "i-lucide-smile",
          description: "You have nothing to do, @nuxt/icon will handle it automatically."
        },
        {
          label: "Colors",
          icon: "i-lucide-swatch-book",
          description: "Choose a primary and a neutral color from your Tailwind CSS theme."
        },
        {
          label: "Theme",
          icon: "i-lucide-cog",
          description: "You can customize components by using the `class` / `ui` props or in your app.config.ts."
        }
      ]
    },
    {
      label: "Composables",
      icon: "i-lucide-database",
      children: [
        {
          label: "defineShortcuts",
          icon: "i-lucide-file-text",
          description: "Define shortcuts for your application.",
          to: "/docs/composables/define-shortcuts"
        },
        {
          label: "useOverlay",
          icon: "i-lucide-file-text",
          description: "Display a modal/slideover within your application.",
          to: "/docs/composables/use-overlay"
        },
        {
          label: "useToast",
          icon: "i-lucide-file-text",
          description: "Display a toast within your application.",
          to: "/docs/composables/use-toast"
        }
      ]
    },
    {
      label: "Components",
      icon: "i-lucide-box",
      to: "/docs/components",
      active: true,
      children: [
        {
          label: "Link",
          icon: "i-lucide-file-text",
          description: "Use NuxtLink with superpowers.",
          to: "/docs/components/link"
        },
        {
          label: "Modal",
          icon: "i-lucide-file-text",
          description: "Display a modal within your application.",
          to: "/docs/components/modal"
        },
        {
          label: "NavigationMenu",
          icon: "i-lucide-file-text",
          description: "Display a list of links.",
          to: "/docs/components/navigation-menu"
        },
        {
          label: "Pagination",
          icon: "i-lucide-file-text",
          description: "Display a list of pages.",
          to: "/docs/components/pagination"
        },
        {
          label: "Popover",
          icon: "i-lucide-file-text",
          description: "Display a non-modal dialog that floats around a trigger element.",
          to: "/docs/components/popover"
        },
        {
          label: "Progress",
          icon: "i-lucide-file-text",
          description: "Show a horizontal bar to indicate task progression.",
          to: "/docs/components/progress"
        }
      ]
    }
  ],
  [
    {
      label: "GitHub",
      icon: "i-simple-icons-github",
      badge: "6k",
      to: "https://github.com/nuxt/ui",
      target: "_blank",
      tooltip: {
        text: "Open on GitHub",
        kbds: [
          "6k"
        ]
      }
    },
    {
      label: "Help",
      icon: "i-lucide-circle-help",
      disabled: true
    }
  ]
])
</script>

<template>
  <UNavigationMenu tooltip collapsed orientation="vertical" :items="items" />
</template>
```

### With popover in items

When orientation is `vertical` and the menu is `collapsed`, you can set the `popover` prop to `true` to display a [Popover](/docs/components/popover) around items with their children but you can also use the `popover` property on each item to override the default popover.

> [!NOTE]
> 
> The `popover` property on an item will always display a popover regardless of the global `popover` prop.

You can pass any property from the [Popover](/docs/components/popover) component globally or on each item.

```vue
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = ref<NavigationMenuItem[][]>([
  [
    {
      label: "Links",
      type: "label"
    },
    {
      label: "Guide",
      icon: "i-lucide-book-open",
      children: [
        {
          label: "Introduction",
          description: "Fully styled and customizable components for Nuxt.",
          icon: "i-lucide-house"
        },
        {
          label: "Installation",
          description: "Learn how to install and configure Nuxt UI in your application.",
          icon: "i-lucide-cloud-download"
        },
        {
          label: "Icons",
          icon: "i-lucide-smile",
          description: "You have nothing to do, @nuxt/icon will handle it automatically."
        },
        {
          label: "Colors",
          icon: "i-lucide-swatch-book",
          description: "Choose a primary and a neutral color from your Tailwind CSS theme."
        },
        {
          label: "Theme",
          icon: "i-lucide-cog",
          description: "You can customize components by using the `class` / `ui` props or in your app.config.ts."
        }
      ]
    },
    {
      label: "Composables",
      icon: "i-lucide-database",
      popover: {
        mode: "click"
      },
      children: [
        {
          label: "defineShortcuts",
          icon: "i-lucide-file-text",
          description: "Define shortcuts for your application.",
          to: "/docs/composables/define-shortcuts"
        },
        {
          label: "useOverlay",
          icon: "i-lucide-file-text",
          description: "Display a modal/slideover within your application.",
          to: "/docs/composables/use-overlay"
        },
        {
          label: "useToast",
          icon: "i-lucide-file-text",
          description: "Display a toast within your application.",
          to: "/docs/composables/use-toast"
        }
      ]
    },
    {
      label: "Components",
      icon: "i-lucide-box",
      to: "/docs/components",
      active: true,
      children: [
        {
          label: "Link",
          icon: "i-lucide-file-text",
          description: "Use NuxtLink with superpowers.",
          to: "/docs/components/link"
        },
        {
          label: "Modal",
          icon: "i-lucide-file-text",
          description: "Display a modal within your application.",
          to: "/docs/components/modal"
        },
        {
          label: "NavigationMenu",
          icon: "i-lucide-file-text",
          description: "Display a list of links.",
          to: "/docs/components/navigation-menu"
        },
        {
          label: "Pagination",
          icon: "i-lucide-file-text",
          description: "Display a list of pages.",
          to: "/docs/components/pagination"
        },
        {
          label: "Popover",
          icon: "i-lucide-file-text",
          description: "Display a non-modal dialog that floats around a trigger element.",
          to: "/docs/components/popover"
        },
        {
          label: "Progress",
          icon: "i-lucide-file-text",
          description: "Show a horizontal bar to indicate task progression.",
          to: "/docs/components/progress"
        }
      ]
    }
  ],
  [
    {
      label: "GitHub",
      icon: "i-simple-icons-github",
      badge: "6k",
      to: "https://github.com/nuxt/ui",
      target: "_blank",
      tooltip: {
        text: "Open on GitHub",
        kbds: [
          "6k"
        ]
      }
    },
    {
      label: "Help",
      icon: "i-lucide-circle-help",
      disabled: true
    }
  ]
])
</script>

<template>
  <UNavigationMenu popover collapsed orientation="vertical" :items="items" />
</template>
```

> [!TIP]
> See: #with-content-slot
> 
> You can use the `#content` slot to customize the content of the popover in the `vertical` orientation.

### With chip in items `4.5+`

Use the `chip` property to display a [Chip](/docs/components/chip) around the icon of the items, you can pass any of its props.

```vue
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = ref<NavigationMenuItem[][]>([
  [
    {
      label: "Guide",
      icon: "i-lucide-book-open",
      chip: {
        color: "error"
      }
    },
    {
      label: "Composables",
      icon: "i-lucide-database",
      chip: {
        color: "info",
        text: 3
      }
    },
    {
      label: "Components",
      icon: "i-lucide-box",
      to: "/docs/components",
      active: true,
      chip: true
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
      label: "Help",
      icon: "i-lucide-circle-help",
      disabled: true
    }
  ]
])
</script>

<template>
  <UNavigationMenu collapsed orientation="vertical" :items="items" />
</template>
```

### With bottom tab bar

Use the `ui` prop to transform the NavigationMenu into a mobile-style bottom tab bar with icons and small labels, similar to YouTube or Instagram.

```vue [NavigationMenuBottomTabBarExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items: NavigationMenuItem[] = [
  {
    label: 'Home',
    icon: 'i-lucide-house',
    active: true
  },
  {
    label: 'Samples',
    icon: 'i-lucide-circle-play'
  },
  {
    label: 'Explore',
    icon: 'i-lucide-compass'
  },
  {
    label: 'Library',
    icon: 'i-lucide-bookmark'
  }
]
</script>

<template>
  <UNavigationMenu
    :items="items"
    :ui="{
      root: 'justify-around border-t border-default py-2',
      item: 'py-0',
      link: 'flex-col gap-1 px-3',
      linkLeadingIcon: 'size-5',
      linkLabel: 'text-[10px]/3 font-normal'
    }"
    class="w-full"
  />
</template>
```

### With collapsed labels

Use the `ui` prop to display a label underneath each icon when collapsed.

```vue [NavigationMenuCollapsedLabelExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items: NavigationMenuItem[] = [
  {
    label: 'Home',
    icon: 'i-lucide-house'
  },
  {
    label: 'Search',
    icon: 'i-lucide-search'
  },
  {
    label: 'Users',
    icon: 'i-lucide-users',
    active: true
  },
  {
    label: 'Settings',
    icon: 'i-lucide-cog'
  }
]
</script>

<template>
  <UNavigationMenu
    collapsed
    orientation="vertical"
    :items="items"
    :ui="{
      link: 'flex-col gap-1',
      linkLabel: 'block text-[10px]/3 text-center'
    }"
  />
</template>
```

> [!TIP]
> 
> You can also do this globally through the `app.config.ts` using [`compoundVariants`](/docs/getting-started/theme/components#compound-variants):
> 
> ```ts [app/app.config.ts]
> export default defineAppConfig({
>   ui: {
>     navigationMenu: {
>       compoundVariants: [{
>         orientation: 'vertical',
>         collapsed: true,
>         class: {
>           link: 'flex-col',
>           linkLabel: 'block text-[10px]/3 text-center'
>         }
>       }]
>     }
>   }
> })
> ```

### With custom slot

Use the `slot` property to customize a specific item.

You will have access to the following slots:

- `#{{ item.slot }}`
- `#{{ item.slot }}-leading`
- `#{{ item.slot }}-label`
- `#{{ item.slot }}-trailing`
- `#{{ item.slot }}-content`

```vue [NavigationMenuCustomSlotExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem, DropdownMenuItem } from '@nuxt/ui'

const items = [
  {
    label: 'Guide',
    icon: 'i-lucide-book-open',
    to: '/docs/getting-started'
  },
  {
    label: 'Composables',
    icon: 'i-lucide-database',
    to: '/docs/composables',
    class: 'hidden'
  },
  {
    label: 'Components',
    icon: 'i-lucide-box',
    to: '/docs/components',
    class: 'hidden'
  },
  {
    slot: 'more' as const,
    as: 'span',
    class: 'p-0',
    content: {
      align: 'start' as const
    },
    items: [
      {
        label: 'Composables',
        icon: 'i-lucide-database',
        to: '/docs/composables'
      },
      {
        label: 'Components',
        icon: 'i-lucide-box',
        to: '/docs/components'
      }
    ] satisfies DropdownMenuItem[]
  },
  {
    label: 'GitHub',
    icon: 'i-simple-icons-github',
    to: 'https://github.com/nuxt/ui',
    target: '_blank',
    slot: 'github' as const
  }
] satisfies NavigationMenuItem[]
</script>

<template>
  <UNavigationMenu :items="items" class="w-full justify-center">
    <template #more="{ item }">
      <UDropdownMenu :content="item.content" :items="item.items">
        <UButton icon="i-lucide-ellipsis" color="neutral" variant="link" />
      </UDropdownMenu>
    </template>

    <template #github-trailing>
      <UBadge label="6k+" color="neutral" variant="subtle" size="sm" />
    </template>
  </UNavigationMenu>
</template>
```

> [!TIP]
> See: #slots
> 
> You can also use the `#item`, `#item-leading`, `#item-label`, `#item-trailing` and `#item-content` slots to customize all items.

### With trailing slot

Use the `#item-trailing` slot or the `slot` property (`#{{ item.slot }}-trailing`) to add a [DropdownMenu](/docs/components/dropdown-menu) that appears on hover, similar to Notion or Linear.

```vue [NavigationMenuTrailingSlotExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem, DropdownMenuItem } from '@nuxt/ui'

const items: NavigationMenuItem[][] = [
  [
    {
      label: 'Personal',
      type: 'label',
      slot: 'personal-label' as const
    },
    {
      label: 'Design System',
      icon: 'i-lucide-folder',
      active: true
    },
    {
      label: 'Travel',
      icon: 'i-lucide-folder'
    }
  ],
  [
    {
      label: 'Teams',
      type: 'label',
      slot: 'teams-label' as const
    },
    {
      label: 'Engineering',
      icon: 'i-lucide-folder'
    },
    {
      label: 'Marketing',
      icon: 'i-lucide-folder'
    }
  ]
]

const dropdownItems: DropdownMenuItem[][] = [
  [
    { label: 'View Project', icon: 'i-lucide-folder-open' },
    { label: 'Share Project', icon: 'i-lucide-share' }
  ],
  [
    { label: 'Delete Project', icon: 'i-lucide-trash', color: 'error' }
  ]
]
</script>

<template>
  <UNavigationMenu
    orientation="vertical"
    :items="items"
    :ui="{ link: 'overflow-hidden has-data-[state=open]:before:bg-elevated/50' }"
    class="w-48"
  >
    <template #personal-label-trailing>
      <UButton icon="i-lucide-plus" color="neutral" variant="ghost" size="xs" />
    </template>

    <template #teams-label-trailing>
      <UButton icon="i-lucide-plus" color="neutral" variant="ghost" size="xs" />
    </template>

    <template #item-trailing>
      <div class="flex -mr-1.5 -my-0.5 translate-x-full group-hover:translate-x-0 has-data-[state=open]:translate-x-0 transition-transform">
        <UDropdownMenu
          :items="dropdownItems"
          :content="{ align: 'start' }"
          :modal="false"
          size="xs"
        >
          <UButton
            as="div"
            icon="i-lucide-ellipsis"
            color="neutral"
            variant="ghost"
            size="xs"
            class="text-muted hover:text-highlighted hover:bg-accented/50 data-[state=open]:bg-accented/50 mr-1.5"
          />
        </UDropdownMenu>
      </div>
    </template>
  </UNavigationMenu>
</template>
```

### With content slot

Use the `#item-content` slot or the `slot` property (`#{{ item.slot }}-content`) to customize the content of a specific item.

```vue [NavigationMenuContentSlotExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items = [
  {
    label: 'Docs',
    icon: 'i-lucide-book-open',
    slot: 'docs' as const,
    children: [
      {
        label: 'Icons',
        description: 'You have nothing to do, @nuxt/icon will handle it automatically.'
      },
      {
        label: 'Colors',
        description: 'Choose a primary and a neutral color from your Tailwind CSS theme.'
      },
      {
        label: 'Theme',
        description: 'You can customize components by using the `class` / `ui` props or in your app.config.ts.'
      }
    ]
  },
  {
    label: 'Components',
    icon: 'i-lucide-box',
    slot: 'components' as const,
    children: [
      {
        label: 'Link',
        description: 'Use NuxtLink with superpowers.'
      },
      {
        label: 'Modal',
        description: 'Display a modal within your application.'
      },
      {
        label: 'NavigationMenu',
        description: 'Display a list of links.'
      },
      {
        label: 'Pagination',
        description: 'Display a list of pages.'
      },
      {
        label: 'Popover',
        description: 'Display a non-modal dialog that floats around a trigger element.'
      },
      {
        label: 'Progress',
        description: 'Show a horizontal bar to indicate task progression.'
      }
    ]
  },
  {
    label: 'GitHub',
    icon: 'i-simple-icons-github'
  }
] satisfies NavigationMenuItem[]
</script>

<template>
  <UNavigationMenu
    :items="items"
    :ui="{
      viewport: 'sm:w-(--reka-navigation-menu-viewport-width)',
      content: 'sm:w-auto',
      childList: 'sm:w-96',
      childLinkDescription: 'text-balance line-clamp-2'
    }"
    class="w-full justify-center"
  >
    <template #docs-content="{ item }">
      <ul class="grid gap-2 p-4 lg:w-[500px] lg:grid-cols-[minmax(0,.75fr)_minmax(0,1fr)]">
        <li class="row-span-3">
          <Placeholder class="size-full min-h-48" />
        </li>

        <li v-for="child in item.children" :key="child.label">
          <ULink class="text-sm text-left rounded-md p-3 transition-colors hover:bg-elevated/50">
            <p class="font-medium text-highlighted">
              {{ child.label }}
            </p>
            <p class="text-muted line-clamp-2">
              {{ child.description }}
            </p>
          </ULink>
        </li>
      </ul>
    </template>
  </UNavigationMenu>
</template>
```

> [!NOTE]
> 
> In this example, we add the `sm:w-(--reka-navigation-menu-viewport-width)` class on the `viewport` to have a dynamic width. This requires to set a width on the content's first child.

## API

### Props

```ts
/**
 * Props for the NavigationMenu component
 */
interface NavigationMenuProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  /**
   * Determines whether a "single" or "multiple" items can be selected at a time.
   * 
   * Only works when `orientation` is `vertical`.
   * @default "\"multiple\" as never"
   */
  type?: K | undefined;
  /**
   * The controlled value of the active item(s).
   * - In horizontal orientation: always `string`
   * - In vertical orientation with `type="single"`: `string`
   * - In vertical orientation with `type="multiple"`: `string[]`
   * 
   * Use this when you need to control the state of the items. Can be binded with `v-model`
   */
  modelValue?: NavigationMenuModelValue<K, O> | undefined;
  /**
   * The default active value of the item(s).
   * - In horizontal orientation: always `string`
   * - In vertical orientation with `type="single"`: `string`
   * - In vertical orientation with `type="multiple"`: `string[]`
   * 
   * Use when you do not need to control the state of the item(s).
   */
  defaultValue?: NavigationMenuModelValue<K, O> | undefined;
  /**
   * The icon displayed to open the menu.
   */
  trailingIcon?: any;
  /**
   * The icon displayed when the item is an external link.
   * Set to `false` to hide the external icon.
   * @default "true"
   */
  externalIcon?: any;
  items?: T | undefined;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  variant?: "pill" | "link" | undefined;
  /**
   * The orientation of the menu.
   * @default "\"horizontal\" as never"
   */
  orientation?: O | undefined;
  /**
   * Collapse the navigation menu to only show icons.
   * Only works when `orientation` is `vertical`.
   */
  collapsed?: boolean | undefined;
  /**
   * Display a tooltip on the items with the label of the item.
   * Only works when `orientation` is `vertical` and `collapsed` is `true`.
   * `{ delayDuration: 0, content: { side: 'right' } }`{lang="ts-type"}
   */
  tooltip?: boolean | TooltipProps | undefined;
  /**
   * Display a popover on the items when the menu is collapsed with the children list.
   * `{ mode: 'hover', content: { side: 'right', align: 'start', alignOffset: 2 } }`{lang="ts-type"}
   */
  popover?: boolean | PopoverProps<PopoverMode> | undefined;
  /**
   * Display a line next to the active item.
   */
  highlight?: boolean | undefined;
  highlightColor?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  /**
   * The content of the menu.
   */
  content?: (Omit<NavigationMenuContentProps, "as" | "asChild" | "forceMount"> & Partial<EmitsToProps<DismissableLayerEmits>>) | undefined;
  /**
   * The orientation of the content.
   * Only works when `orientation` is `horizontal`.
   * @default "\"horizontal\""
   */
  contentOrientation?: "horizontal" | "vertical" | undefined;
  /**
   * Display an arrow alongside the menu.
   */
  arrow?: boolean | undefined;
  /**
   * The key used to get the value from the item.
   * @default "\"value\""
   */
  valueKey?: GetItemKeys<T> | undefined;
  /**
   * The key used to get the label from the item.
   * @default "\"label\""
   */
  labelKey?: GetItemKeys<T> | undefined;
  ui?: { root?: SlotClass; list?: SlotClass; label?: SlotClass; item?: SlotClass; link?: SlotClass; linkLeadingIcon?: SlotClass; linkLeadingAvatar?: SlotClass; linkLeadingAvatarSize?: SlotClass; linkLeadingChipSize?: SlotClass; linkTrailing?: SlotClass; linkTrailingBadge?: SlotClass; linkTrailingBadgeSize?: SlotClass; linkTrailingIcon?: SlotClass; linkLabel?: SlotClass; linkLabelExternalIcon?: SlotClass; childList?: SlotClass; childLabel?: SlotClass; childItem?: SlotClass; childLink?: SlotClass; childLinkWrapper?: SlotClass; childLinkIcon?: SlotClass; childLinkLabel?: SlotClass; childLinkLabelExternalIcon?: SlotClass; childLinkDescription?: SlotClass; separator?: SlotClass; viewportWrapper?: SlotClass; viewport?: SlotClass; content?: SlotClass; indicator?: SlotClass; arrow?: SlotClass; } | undefined;
  /**
   * The duration from when the pointer enters the trigger until the tooltip gets opened.
   * @default "0"
   */
  delayDuration?: number | undefined;
  /**
   * If `true`, menu cannot be open by click on trigger
   */
  disableClickTrigger?: boolean | undefined;
  /**
   * If `true`, menu cannot be open by hover on trigger
   */
  disableHoverTrigger?: boolean | undefined;
  /**
   * How much time a user has to enter another trigger without incurring a delay again.
   */
  skipDelayDuration?: number | undefined;
  /**
   * If `true`, menu will not close during pointer leave event
   */
  disablePointerLeaveClose?: boolean | undefined;
  /**
   * When `true`, the element will be unmounted on closed state.
   * @default "true"
   */
  unmountOnHide?: boolean | undefined;
  /**
   * When `true`, prevents the user from interacting with the accordion and all its items
   */
  disabled?: boolean | undefined;
  /**
   * When type is "single", allows closing content when clicking trigger for an open item.
   * When type is "multiple", this prop has no effect.
   * @default "true"
   */
  collapsible?: boolean | undefined;
}
```

### Slots

```ts
/**
 * Slots for the NavigationMenu component
 */
interface NavigationMenuSlots {
  item(): any;
  item-leading(): any;
  item-label(): any;
  item-trailing(): any;
  item-content(): any;
  list-leading(): any;
  list-trailing(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the NavigationMenu component
 */
interface NavigationMenuEmits {
  update:modelValue: (payload: [value: NavigationMenuModelValue<K, O> | undefined]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    navigationMenu: {
      slots: {
        root: 'relative flex gap-1.5 [&>div]:min-w-0',
        list: 'isolate min-w-0',
        label: 'w-full flex items-center gap-1.5 font-semibold text-xs/5 text-highlighted px-2.5 py-1.5',
        item: 'min-w-0',
        link: 'group relative w-full flex items-center gap-1.5 font-medium text-sm before:absolute before:z-[-1] before:rounded-md focus:outline-none focus-visible:outline-none focus-visible:before:outline-3',
        linkLeadingIcon: 'shrink-0 size-5',
        linkLeadingAvatar: 'shrink-0',
        linkLeadingAvatarSize: '2xs',
        linkLeadingChipSize: 'sm',
        linkTrailing: 'group ms-auto inline-flex gap-1.5 items-center',
        linkTrailingBadge: 'shrink-0',
        linkTrailingBadgeSize: 'sm',
        linkTrailingIcon: 'size-5 transform shrink-0 group-data-[state=open]:rotate-180 transition-transform duration-200',
        linkLabel: 'truncate',
        linkLabelExternalIcon: 'inline-block size-3 align-top text-dimmed',
        childList: 'isolate',
        childLabel: 'text-xs text-highlighted',
        childItem: '',
        childLink: 'group relative size-full flex items-start text-start text-sm before:absolute before:z-[-1] before:rounded-md focus:outline-none focus-visible:outline-none focus-visible:before:outline-3',
        childLinkWrapper: 'min-w-0',
        childLinkIcon: 'size-5 shrink-0',
        childLinkLabel: 'truncate',
        childLinkLabelExternalIcon: 'inline-block size-3 align-top text-dimmed',
        childLinkDescription: 'text-muted',
        separator: 'px-2 h-px bg-border',
        viewportWrapper: 'absolute top-full left-0 flex w-full',
        viewport: 'relative overflow-hidden bg-default shadow-lg rounded-md ring ring-default h-(--reka-navigation-menu-viewport-height) w-full transition-[width,height,left,right] duration-200 origin-[top_center] data-[state=open]:animate-[scale-in_100ms_ease-out] data-[state=closed]:animate-[scale-out_100ms_ease-in] z-1',
        content: '',
        indicator: 'absolute left-0 data-[state=visible]:animate-[fade-in_100ms_ease-out] data-[state=hidden]:animate-[fade-out_100ms_ease-in] data-[state=hidden]:opacity-0 bottom-0 z-2 w-(--reka-navigation-menu-indicator-size) translate-x-(--reka-navigation-menu-indicator-position) flex h-2.5 items-end justify-center overflow-hidden transition-[translate,width] duration-200',
        arrow: 'relative top-[50%] size-2.5 rotate-45 border border-default bg-default z-1 rounded-xs'
      },
      variants: {
        color: {
          primary: {
            link: 'before:outline-primary/25',
            childLink: 'before:outline-primary/25'
          },
          secondary: {
            link: 'before:outline-secondary/25',
            childLink: 'before:outline-secondary/25'
          },
          success: {
            link: 'before:outline-success/25',
            childLink: 'before:outline-success/25'
          },
          info: {
            link: 'before:outline-info/25',
            childLink: 'before:outline-info/25'
          },
          warning: {
            link: 'before:outline-warning/25',
            childLink: 'before:outline-warning/25'
          },
          error: {
            link: 'before:outline-error/25',
            childLink: 'before:outline-error/25'
          },
          neutral: {
            link: 'before:outline-inverted/25',
            childLink: 'before:outline-inverted/25'
          }
        },
        highlightColor: {
          primary: '',
          secondary: '',
          success: '',
          info: '',
          warning: '',
          error: '',
          neutral: ''
        },
        variant: {
          pill: '',
          link: ''
        },
        orientation: {
          horizontal: {
            root: 'items-center justify-between',
            list: 'flex items-center',
            item: 'py-2',
            link: 'px-2.5 py-1.5 before:inset-x-px before:inset-y-0',
            childList: 'grid p-2',
            childLink: 'px-3 py-2 gap-2 before:inset-x-px before:inset-y-0',
            childLinkLabel: 'font-medium',
            content: 'absolute top-0 left-0 w-full max-h-[70vh] overflow-y-auto'
          },
          vertical: {
            root: 'flex-col',
            link: 'flex-row px-2.5 py-1.5 before:inset-y-px before:inset-x-0',
            childLabel: 'px-1.5 py-0.5',
            childLink: 'p-1.5 gap-1.5 before:inset-y-px before:inset-x-0'
          }
        },
        contentOrientation: {
          horizontal: {
            viewportWrapper: 'justify-center',
            content: 'data-[motion=from-start]:animate-[enter-from-left_200ms_ease] data-[motion=from-end]:animate-[enter-from-right_200ms_ease] data-[motion=to-start]:animate-[exit-to-left_200ms_ease] data-[motion=to-end]:animate-[exit-to-right_200ms_ease]'
          },
          vertical: {
            viewport: 'sm:w-(--reka-navigation-menu-viewport-width) left-(--reka-navigation-menu-viewport-left) rtl:left-auto rtl:right-[calc(100%-var(--reka-navigation-menu-viewport-left)-var(--reka-navigation-menu-viewport-width))]'
          }
        },
        active: {
          true: {
            childLink: 'before:bg-elevated text-highlighted',
            childLinkIcon: 'text-default'
          },
          false: {
            link: 'text-muted',
            linkLeadingIcon: 'text-dimmed',
            childLink: [
              'hover:before:bg-elevated/50 text-default hover:text-highlighted',
              'transition-colors before:transition-colors'
            ],
            childLinkIcon: [
              'text-dimmed group-hover:text-default',
              'transition-colors'
            ]
          }
        },
        disabled: {
          true: {
            link: 'cursor-not-allowed opacity-75'
          }
        },
        highlight: {
          true: ''
        },
        level: {
          true: ''
        },
        collapsed: {
          true: ''
        }
      },
      compoundVariants: [
        {
          orientation: 'horizontal',
          contentOrientation: 'horizontal',
          class: {
            childList: 'grid-cols-2 gap-2'
          }
        },
        {
          orientation: 'horizontal',
          contentOrientation: 'vertical',
          class: {
            childList: 'gap-1',
            content: 'w-60'
          }
        },
        {
          orientation: 'vertical',
          collapsed: false,
          class: {
            childList: 'ms-5 border-s border-default',
            childItem: 'ps-1.5 -ms-px',
            content: 'data-[state=open]:animate-[collapsible-down_200ms_ease-out] data-[state=closed]:animate-[collapsible-up_200ms_ease-out] data-[state=closed]:overflow-hidden'
          }
        },
        {
          orientation: 'vertical',
          collapsed: true,
          class: {
            link: 'px-1.5',
            linkLabel: 'hidden',
            linkTrailing: 'hidden',
            content: 'shadow-sm rounded-sm min-h-6 p-1'
          }
        },
        {
          orientation: 'horizontal',
          highlight: true,
          class: {
            link: [
              'after:absolute after:-bottom-2 after:inset-x-2.5 after:block after:h-px after:rounded-full',
              'after:transition-colors'
            ]
          }
        },
        {
          orientation: 'vertical',
          highlight: true,
          level: true,
          class: {
            link: [
              'after:absolute after:-start-1.5 after:inset-y-0.5 after:block after:w-px after:rounded-full',
              'after:transition-colors'
            ]
          }
        },
        {
          disabled: false,
          active: false,
          variant: 'pill',
          class: {
            link: [
              'hover:text-highlighted hover:before:bg-elevated/50',
              'transition-colors before:transition-colors'
            ],
            linkLeadingIcon: [
              'group-hover:text-default',
              'transition-colors'
            ]
          }
        },
        {
          disabled: false,
          active: false,
          variant: 'pill',
          orientation: 'horizontal',
          class: {
            link: 'data-[state=open]:text-highlighted',
            linkLeadingIcon: 'group-data-[state=open]:text-default'
          }
        },
        {
          disabled: false,
          variant: 'pill',
          highlight: true,
          orientation: 'horizontal',
          class: {
            link: 'data-[state=open]:before:bg-elevated/50'
          }
        },
        {
          disabled: false,
          variant: 'pill',
          highlight: false,
          active: false,
          orientation: 'horizontal',
          class: {
            link: 'data-[state=open]:before:bg-elevated/50'
          }
        },
        {
          color: 'primary',
          variant: 'pill',
          active: true,
          class: {
            link: 'text-primary',
            linkLeadingIcon: 'text-primary group-data-[state=open]:text-primary'
          }
        },
        {
          color: 'secondary',
          variant: 'pill',
          active: true,
          class: {
            link: 'text-secondary',
            linkLeadingIcon: 'text-secondary group-data-[state=open]:text-secondary'
          }
        },
        {
          color: 'success',
          variant: 'pill',
          active: true,
          class: {
            link: 'text-success',
            linkLeadingIcon: 'text-success group-data-[state=open]:text-success'
          }
        },
        {
          color: 'info',
          variant: 'pill',
          active: true,
          class: {
            link: 'text-info',
            linkLeadingIcon: 'text-info group-data-[state=open]:text-info'
          }
        },
        {
          color: 'warning',
          variant: 'pill',
          active: true,
          class: {
            link: 'text-warning',
            linkLeadingIcon: 'text-warning group-data-[state=open]:text-warning'
          }
        },
        {
          color: 'error',
          variant: 'pill',
          active: true,
          class: {
            link: 'text-error',
            linkLeadingIcon: 'text-error group-data-[state=open]:text-error'
          }
        },
        {
          color: 'neutral',
          variant: 'pill',
          active: true,
          class: {
            link: 'text-highlighted',
            linkLeadingIcon: 'text-highlighted group-data-[state=open]:text-highlighted'
          }
        },
        {
          variant: 'pill',
          active: true,
          highlight: false,
          class: {
            link: 'before:bg-elevated'
          }
        },
        {
          variant: 'pill',
          active: true,
          highlight: true,
          disabled: false,
          class: {
            link: [
              'hover:before:bg-elevated/50',
              'before:transition-colors'
            ]
          }
        },
        {
          disabled: false,
          active: false,
          variant: 'link',
          class: {
            link: [
              'hover:text-highlighted',
              'transition-colors'
            ],
            linkLeadingIcon: [
              'group-hover:text-default',
              'transition-colors'
            ]
          }
        },
        {
          disabled: false,
          active: false,
          variant: 'link',
          orientation: 'horizontal',
          class: {
            link: 'data-[state=open]:text-highlighted',
            linkLeadingIcon: 'group-data-[state=open]:text-default'
          }
        },
        {
          color: 'primary',
          variant: 'link',
          active: true,
          class: {
            link: 'text-primary',
            linkLeadingIcon: 'text-primary group-data-[state=open]:text-primary'
          }
        },
        {
          color: 'secondary',
          variant: 'link',
          active: true,
          class: {
            link: 'text-secondary',
            linkLeadingIcon: 'text-secondary group-data-[state=open]:text-secondary'
          }
        },
        {
          color: 'success',
          variant: 'link',
          active: true,
          class: {
            link: 'text-success',
            linkLeadingIcon: 'text-success group-data-[state=open]:text-success'
          }
        },
        {
          color: 'info',
          variant: 'link',
          active: true,
          class: {
            link: 'text-info',
            linkLeadingIcon: 'text-info group-data-[state=open]:text-info'
          }
        },
        {
          color: 'warning',
          variant: 'link',
          active: true,
          class: {
            link: 'text-warning',
            linkLeadingIcon: 'text-warning group-data-[state=open]:text-warning'
          }
        },
        {
          color: 'error',
          variant: 'link',
          active: true,
          class: {
            link: 'text-error',
            linkLeadingIcon: 'text-error group-data-[state=open]:text-error'
          }
        },
        {
          color: 'neutral',
          variant: 'link',
          active: true,
          class: {
            link: 'text-highlighted',
            linkLeadingIcon: 'text-highlighted group-data-[state=open]:text-highlighted'
          }
        },
        {
          highlightColor: 'primary',
          highlight: true,
          level: true,
          active: true,
          class: {
            link: 'after:bg-primary'
          }
        },
        {
          highlightColor: 'secondary',
          highlight: true,
          level: true,
          active: true,
          class: {
            link: 'after:bg-secondary'
          }
        },
        {
          highlightColor: 'success',
          highlight: true,
          level: true,
          active: true,
          class: {
            link: 'after:bg-success'
          }
        },
        {
          highlightColor: 'info',
          highlight: true,
          level: true,
          active: true,
          class: {
            link: 'after:bg-info'
          }
        },
        {
          highlightColor: 'warning',
          highlight: true,
          level: true,
          active: true,
          class: {
            link: 'after:bg-warning'
          }
        },
        {
          highlightColor: 'error',
          highlight: true,
          level: true,
          active: true,
          class: {
            link: 'after:bg-error'
          }
        },
        {
          highlightColor: 'neutral',
          highlight: true,
          level: true,
          active: true,
          class: {
            link: 'after:bg-inverted'
          }
        }
      ],
      defaultVariants: {
        color: 'primary',
        highlightColor: 'primary',
        variant: 'pill'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/NavigationMenu.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/navigation-menu.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

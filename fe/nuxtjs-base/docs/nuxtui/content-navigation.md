---
title: "ContentNavigation"
description: "An accordion-style navigation component for organizing page links."
canonical_url: "https://ui.nuxt.com/docs/components/content-navigation"
---
# ContentNavigation

> An accordion-style navigation component for organizing page links.

> [!WARNING]
> See: /docs/getting-started/integrations/content
> 
> This component is only available when the `@nuxt/content` module is installed.

## Usage

Use the `navigation` prop with the `navigation` value you get when fetching the navigation of your app.

```vue [ContentNavigationExample.vue]
<script setup lang="ts">
import type { ContentNavigationItem } from '@nuxt/content'

const navigation = inject<Ref<ContentNavigationItem[]>>('navigation')
</script>

<template>
  <UContentNavigation :navigation="navigation" highlight />
</template>
```

### Type

Set the `type` prop to `single` to only allow one item to be open at a time. Defaults to `multiple`.

```vue
<script setup lang="ts">
import type { ContentNavigationLink } from '@nuxt/ui'

const navigation = ref<ContentNavigationLink[]>([
  {
    title: "Guide",
    icon: "i-lucide-book-open",
    path: "#getting-started",
    children: [
      {
        title: "Introduction",
        path: "#introduction",
        active: true
      },
      {
        title: "Installation",
        path: "#installation"
      }
    ]
  },
  {
    title: "Composables",
    icon: "i-lucide-database",
    path: "#composables",
    children: [
      {
        title: "defineShortcuts",
        path: "#defineshortcuts"
      },
      {
        title: "useModal",
        path: "#usemodal"
      }
    ]
  }
])
</script>

<template>
  <UContentNavigation type="single" />
</template>
```

### Color

Use the `color` prop to change the color of the navigation links.

```vue
<script setup lang="ts">
import type { ContentNavigationLink } from '@nuxt/ui'

const navigation = ref<ContentNavigationLink[]>([
  {
    title: "Guide",
    icon: "i-lucide-book-open",
    path: "#getting-started",
    children: [
      {
        title: "Introduction",
        path: "#introduction",
        active: true
      },
      {
        title: "Installation",
        path: "#installation"
      }
    ]
  },
  {
    title: "Composables",
    icon: "i-lucide-database",
    path: "#composables",
    children: [
      {
        title: "defineShortcuts",
        path: "#defineshortcuts"
      },
      {
        title: "useModal",
        path: "#usemodal"
      }
    ]
  }
])
</script>

<template>
  <UContentNavigation color="neutral" />
</template>
```

### Variant

Use the `variant` prop to change the variant of the navigation links.

```vue
<script setup lang="ts">
import type { ContentNavigationLink } from '@nuxt/ui'

const navigation = ref<ContentNavigationLink[]>([
  {
    title: "Guide",
    icon: "i-lucide-book-open",
    path: "#getting-started",
    children: [
      {
        title: "Introduction",
        path: "#introduction",
        active: true
      },
      {
        title: "Installation",
        path: "#installation"
      }
    ]
  },
  {
    title: "Composables",
    icon: "i-lucide-database",
    path: "#composables",
    children: [
      {
        title: "defineShortcuts",
        path: "#defineshortcuts"
      },
      {
        title: "useModal",
        path: "#usemodal"
      }
    ]
  }
])
</script>

<template>
  <UContentNavigation variant="link" />
</template>
```

### Highlight

Use the `highlight` prop to display a highlighted border for the active link.

Use the `highlight-color` prop to change the color of the border. It defaults to the `color` prop.

```vue
<script setup lang="ts">
import type { ContentNavigationLink } from '@nuxt/ui'

const navigation = ref<ContentNavigationLink[]>([
  {
    title: "Guide",
    icon: "i-lucide-book-open",
    path: "#getting-started",
    children: [
      {
        title: "Introduction",
        path: "#introduction",
        active: true
      },
      {
        title: "Installation",
        path: "#installation"
      }
    ]
  },
  {
    title: "Composables",
    icon: "i-lucide-database",
    path: "#composables",
    children: [
      {
        title: "defineShortcuts",
        path: "#defineshortcuts"
      },
      {
        title: "useModal",
        path: "#usemodal"
      }
    ]
  }
])
</script>

<template>
  <UContentNavigation highlight highlight-color="primary" color="primary" variant="pill" />
</template>
```

### Trailing Icon

```vue
<script setup lang="ts">
import type { ContentNavigationLink } from '@nuxt/ui'

const navigation = ref<ContentNavigationLink[]>([
  {
    title: "Guide",
    icon: "i-lucide-book-open",
    path: "#getting-started",
    children: [
      {
        title: "Introduction",
        path: "#introduction",
        active: true
      },
      {
        title: "Installation",
        path: "#installation"
      }
    ]
  },
  {
    title: "Composables",
    icon: "i-lucide-database",
    path: "#composables",
    children: [
      {
        title: "defineShortcuts",
        path: "#defineshortcuts"
      },
      {
        title: "useModal",
        path: "#usemodal"
      }
    ]
  }
])
</script>

<template>
  <UContentNavigation trailing-icon="i-lucide-arrow-up" />
</template>
```

## Examples

### Within a layout

Use the ContentNavigation component inside a [PageAside](/docs/components/page-aside) component within a layout to display the navigation of the page:

```vue [layouts/docs.vue]
<script setup lang="ts">
import type { ContentNavigationItem } from '@nuxt/content'

const navigation = inject<Ref<ContentNavigationItem[]>>('navigation')
</script>

<template>
  <UPage>
    <template #left>
      <UPageAside>
        <UContentNavigation :navigation="navigation" highlight />
      </UPageAside>
    </template>

    <slot />
  </UPage>
</template>
```

### Within a header

Use the ContentNavigation component inside the `content` slot of a [Header](/docs/components/header) component to display the navigation of the page on mobile:

```vue [components/Header.vue]
<script setup lang="ts">
import type { ContentNavigationItem } from '@nuxt/content'

const navigation = inject<Ref<ContentNavigationItem[]>>('navigation')
</script>

<template>
  <UHeader>
    <template #body>
      <UContentNavigation :navigation="navigation" highlight />
    </template>
  </UHeader>
</template>
```

## API

### Props

```ts
/**
 * Props for the ContentNavigation component
 */
interface ContentNavigationProps {
  /**
   * The element or component this component should render as.
   * @default "\"nav\""
   */
  as?: any;
  /**
   * When `true`, the tree will be opened based on the current route.
   * When `false`, the tree will be closed.
   * When `undefined` (default), the first item will be opened with `type="single"` and the first level will be opened with `type="multiple"`.
   * @default "undefined"
   */
  defaultOpen?: boolean | undefined;
  /**
   * The icon displayed to toggle the accordion.
   */
  trailingIcon?: any;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  variant?: "pill" | "link" | undefined;
  /**
   * Display a line next to the active link.
   * @default "false"
   */
  highlight?: boolean | undefined;
  highlightColor?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  /**
   * When type is "single", prevents closing the open item when clicking its trigger.
   * When type is "multiple", disables the collapsible behavior.
   * @default "true"
   */
  collapsible?: boolean | undefined;
  /**
   * @default "0"
   */
  level?: number | undefined;
  navigation?: T[] | undefined;
  ui?: { root?: SlotClass; content?: SlotClass; list?: SlotClass; item?: SlotClass; listWithChildren?: SlotClass; itemWithChildren?: SlotClass; trigger?: SlotClass; link?: SlotClass; linkLeadingIcon?: SlotClass; linkTrailing?: SlotClass; linkTrailingBadge?: SlotClass; linkTrailingBadgeSize?: SlotClass; linkTrailingIcon?: SlotClass; linkTitle?: SlotClass; linkTitleExternalIcon?: SlotClass; } | undefined;
  /**
   * When `true`, prevents the user from interacting with the accordion and all its items
   */
  disabled?: boolean | undefined;
  /**
   * Determines whether a "single" or "multiple" items can be selected at a time.
   * 
   * This prop will overwrite the inferred type from `modelValue` and `defaultValue`.
   * @default "\"multiple\""
   */
  type?: SingleOrMultipleType | undefined;
  /**
   * When `true`, the element will be unmounted on closed state.
   * @default "true"
   */
  unmountOnHide?: boolean | undefined;
}
```

### Slots

```ts
/**
 * Slots for the ContentNavigation component
 */
interface ContentNavigationSlots {
  link(): any;
  link-leading(): any;
  link-title(): any;
  link-trailing(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the ContentNavigation component
 */
interface ContentNavigationEmits {
  update:modelValue: (payload: [value: string | string[] | undefined]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    contentNavigation: {
      slots: {
        root: '',
        content: 'data-[state=open]:animate-[accordion-down_200ms_ease-out] data-[state=closed]:animate-[accordion-up_200ms_ease-out] data-[state=closed]:overflow-hidden focus:outline-none',
        list: 'isolate -mx-2.5 -mt-1.5',
        item: '',
        listWithChildren: 'ms-5 border-s border-default',
        itemWithChildren: 'flex flex-col data-[state=open]:mb-1.5',
        trigger: 'font-semibold',
        link: 'group relative w-full px-2.5 py-1.5 before:inset-y-px before:inset-x-0 flex items-center gap-1.5 text-sm before:absolute before:z-[-1] before:rounded-md focus:outline-none focus-visible:outline-none focus-visible:before:outline-3',
        linkLeadingIcon: 'shrink-0 size-5',
        linkTrailing: 'ms-auto inline-flex gap-1.5 items-center',
        linkTrailingBadge: 'shrink-0',
        linkTrailingBadgeSize: 'sm',
        linkTrailingIcon: 'size-5 transform transition-transform duration-200 shrink-0 group-data-[state=open]:rotate-180',
        linkTitle: 'truncate',
        linkTitleExternalIcon: 'size-3 align-top text-dimmed'
      },
      variants: {
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
        active: {
          true: {
            link: 'font-medium'
          },
          false: {
            link: 'text-muted',
            linkLeadingIcon: 'text-dimmed'
          }
        },
        disabled: {
          true: {
            trigger: 'data-[state=open]:text-highlighted'
          }
        },
        highlight: {
          true: {}
        },
        level: {
          true: {
            item: 'ps-1.5 -ms-px',
            itemWithChildren: 'ps-1.5 -ms-px'
          }
        }
      },
      compoundVariants: [
        {
          highlight: true,
          level: true,
          class: {
            link: [
              'after:absolute after:-left-1.5 after:inset-y-0.5 after:block after:w-px after:rounded-full',
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
              'hover:text-highlighted hover:before:bg-elevated/50 data-[state=open]:text-highlighted',
              'transition-colors before:transition-colors'
            ],
            linkLeadingIcon: [
              'group-hover:text-default group-data-[state=open]:text-default',
              'transition-colors'
            ]
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
              'hover:text-highlighted data-[state=open]:text-highlighted',
              'transition-colors'
            ],
            linkLeadingIcon: [
              'group-hover:text-default group-data-[state=open]:text-default',
              'transition-colors'
            ]
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

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/content/ContentNavigation.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/content/content-navigation.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

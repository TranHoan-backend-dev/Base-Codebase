---
title: "ContentToc"
description: "A sticky Table of Contents with automatic active anchor link highlighting."
canonical_url: "https://ui.nuxt.com/docs/components/content-toc"
---
# ContentToc

> A sticky Table of Contents with automatic active anchor link highlighting.

> [!WARNING]
> See: /docs/getting-started/integrations/content
> 
> This component is only available when the `@nuxt/content` module is installed.

## Usage

Use the `links` prop with the `page?.body?.toc?.links` you get when fetching a page.

```vue [ContentTocExample.vue]
<script setup lang="ts">
const route = useRoute()

const { data: page } = await useAsyncData(route.path, () => queryCollection('docs').path(route.path).first())
if (!page.value) {
  throw createError({ statusCode: 404, statusMessage: 'Page not found', fatal: true })
}
</script>

<template>
  <UContentToc :links="page?.body?.toc?.links" />
</template>
```

### Title

Use the `title` prop to change the title of the Table of Contents.

```vue
<script setup lang="ts">
import type { ContentTocLink } from '@nuxt/ui'

const links = ref<ContentTocLink[]>([
  {
    id: "usage",
    depth: 2,
    text: "Usage",
    children: [
      {
        id: "title",
        depth: 3,
        text: "Title"
      },
      {
        id: "color",
        depth: 3,
        text: "Color"
      },
      {
        id: "highlight",
        depth: 3,
        text: "Highlight"
      },
      {
        id: "highlight-color",
        depth: 3,
        text: "Highlight Color"
      },
      {
        id: "highlight-variant",
        depth: 3,
        text: "Highlight Variant"
      }
    ]
  }
])
</script>

<template>
  <UContentToc title="On this page" :links="links" />
</template>
```

### Color

Use the `color` prop to change the color of the links.

```vue
<script setup lang="ts">
import type { ContentTocLink } from '@nuxt/ui'

const links = ref<ContentTocLink[]>([
  {
    id: "usage",
    depth: 2,
    text: "Usage",
    children: [
      {
        id: "title",
        depth: 3,
        text: "Title"
      },
      {
        id: "color",
        depth: 3,
        text: "Color"
      },
      {
        id: "highlight",
        depth: 3,
        text: "Highlight"
      },
      {
        id: "highlight-color",
        depth: 3,
        text: "Highlight Color"
      },
      {
        id: "highlight-variant",
        depth: 3,
        text: "Highlight Variant"
      }
    ]
  }
])
</script>

<template>
  <UContentToc color="neutral" :links="links" />
</template>
```

### Highlight

Use the `highlight` prop to display a highlighted border for the active item.

```vue
<script setup lang="ts">
import type { ContentTocLink } from '@nuxt/ui'

const links = ref<ContentTocLink[]>([
  {
    id: "usage",
    depth: 2,
    text: "Usage",
    children: [
      {
        id: "title",
        depth: 3,
        text: "Title"
      },
      {
        id: "color",
        depth: 3,
        text: "Color"
      },
      {
        id: "highlight",
        depth: 3,
        text: "Highlight"
      },
      {
        id: "highlight-color",
        depth: 3,
        text: "Highlight Color"
      },
      {
        id: "highlight-variant",
        depth: 3,
        text: "Highlight Variant"
      }
    ]
  }
])
</script>

<template>
  <UContentToc highlight :links="links" />
</template>
```

### Highlight Color

Use the `highlight-color` prop to change the color of the highlight. It defaults to the `color` prop.

```vue
<script setup lang="ts">
import type { ContentTocLink } from '@nuxt/ui'

const links = ref<ContentTocLink[]>([
  {
    id: "usage",
    depth: 2,
    text: "Usage",
    children: [
      {
        id: "title",
        depth: 3,
        text: "Title"
      },
      {
        id: "color",
        depth: 3,
        text: "Color"
      },
      {
        id: "highlight",
        depth: 3,
        text: "Highlight"
      },
      {
        id: "highlight-color",
        depth: 3,
        text: "Highlight Color"
      },
      {
        id: "highlight-variant",
        depth: 3,
        text: "Highlight Variant"
      }
    ]
  }
])
</script>

<template>
  <UContentToc highlight highlight-color="neutral" :links="links" />
</template>
```

### Highlight Variant `4.6+`

Use the `highlight-variant` prop to change the style of the highlight. Defaults to `straight`.

```vue
<script setup lang="ts">
import type { ContentTocLink } from '@nuxt/ui'

const links = ref<ContentTocLink[]>([
  {
    id: "usage",
    depth: 2,
    text: "Usage",
    children: [
      {
        id: "title",
        depth: 3,
        text: "Title"
      },
      {
        id: "color",
        depth: 3,
        text: "Color"
      },
      {
        id: "highlight",
        depth: 3,
        text: "Highlight"
      },
      {
        id: "highlight-color",
        depth: 3,
        text: "Highlight Color"
      },
      {
        id: "highlight-variant",
        depth: 3,
        text: "Highlight Variant"
      }
    ]
  },
  {
    id: "examples",
    depth: 2,
    text: "Examples",
    children: [
      {
        id: "within-a-page",
        depth: 3,
        text: "Within a Page"
      }
    ]
  },
  {
    id: "api",
    depth: 2,
    text: "API",
    children: [
      {
        id: "props",
        depth: 3,
        text: "Props"
      },
      {
        id: "slots",
        depth: 3,
        text: "Slots"
      },
      {
        id: "emits",
        depth: 3,
        text: "Emits"
      }
    ]
  },
  {
    id: "theme",
    depth: 2,
    text: "Theme"
  }
])
</script>

<template>
  <UContentToc highlight highlight-color="primary" highlight-variant="circuit" :links="links" />
</template>
```

## Examples

### Within a page

Use the ContentToc component in a page to display the Table of Contents:

```vue [pages/[...slug].vue]
<script setup lang="ts">
const route = useRoute()

const { data: page } = await useAsyncData(route.path, () => queryCollection('docs').path(route.path).first())
if (!page.value) {
  throw createError({ statusCode: 404, statusMessage: 'Page not found', fatal: true })
}
</script>

<template>
  <UPage v-if="page">
    <UPageHeader :title="page.title" />

    <UPageBody>
      <ContentRenderer v-if="page.body" :value="page" />

      <USeparator v-if="surround?.filter(Boolean).length" />

      <UContentSurround :surround="(surround as any)" />
    </UPageBody>

    <template v-if="page?.body?.toc?.links?.length" #right>
      <UContentToc :links="page.body.toc.links" />
    </template>
  </UPage>
</template>
```

## API

### Props

```ts
/**
 * Props for the ContentToc component
 */
interface ContentTocProps {
  /**
   * The element or component this component should render as.
   * @default "\"nav\""
   */
  as?: any;
  /**
   * The icon displayed to collapse the content.
   */
  trailingIcon?: any;
  /**
   * The title of the table of contents.
   */
  title?: string | undefined;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  /**
   * Display a line next to the active link.
   */
  highlight?: boolean | undefined;
  highlightColor?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  /**
   * The variant of the highlight indicator.
   */
  highlightVariant?: "straight" | "circuit" | undefined;
  links?: T[] | undefined;
  ui?: { root?: SlotClass; container?: SlotClass; top?: SlotClass; bottom?: SlotClass; trigger?: SlotClass; title?: SlotClass; trailing?: SlotClass; trailingIcon?: SlotClass; content?: SlotClass; list?: SlotClass; listWithChildren?: SlotClass; item?: SlotClass; itemWithChildren?: SlotClass; link?: SlotClass; linkText?: SlotClass; indicator?: SlotClass; indicatorLine?: SlotClass; indicatorActive?: SlotClass; } | undefined;
  /**
   * The open state of the collapsible when it is initially rendered. <br> Use when you do not need to control its open state.
   */
  defaultOpen?: boolean | undefined;
  /**
   * The controlled open state of the collapsible. Can be binded with `v-model`.
   */
  open?: boolean | undefined;
}
```

### Slots

```ts
/**
 * Slots for the ContentToc component
 */
interface ContentTocSlots {
  leading(): any;
  default(): any;
  trailing(): any;
  content(): any;
  link(): any;
  top(): any;
  bottom(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the ContentToc component
 */
interface ContentTocEmits {
  update:open: (payload: [value: boolean]) => void;
  move: (payload: [id: string]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    contentToc: {
      slots: {
        root: 'sticky top-(--ui-header-height) z-10 bg-default/75 lg:bg-[initial] backdrop-blur -mx-4 px-4 sm:px-6 sm:-mx-6 lg:ms-0 overflow-y-auto max-h-[calc(100vh-var(--ui-header-height))]',
        container: 'pt-4 sm:pt-6 pb-2.5 sm:pb-4.5 lg:py-8 border-b border-dashed border-default lg:border-0 flex flex-col',
        top: '',
        bottom: 'hidden lg:flex lg:flex-col gap-6',
        trigger: 'group text-sm font-semibold flex-1 flex items-center gap-1.5 py-1.5 -mt-1.5 rounded-sm outline-primary/25 focus-visible:outline-3',
        title: 'truncate',
        trailing: 'ms-auto inline-flex gap-1.5 items-center',
        trailingIcon: 'size-5 transform transition-transform duration-200 shrink-0 group-data-[state=open]:rotate-180 lg:hidden',
        content: 'relative data-[state=open]:animate-[collapsible-down_200ms_ease-out] data-[state=closed]:animate-[collapsible-up_200ms_ease-out] data-[state=closed]:overflow-hidden focus:outline-none',
        list: 'min-w-0',
        listWithChildren: 'ms-3',
        item: 'min-w-0',
        itemWithChildren: '',
        link: 'group relative text-sm flex items-center rounded-sm outline-primary/25 focus-visible:outline-3 py-1',
        linkText: 'truncate',
        indicator: '',
        indicatorLine: '',
        indicatorActive: ''
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
        highlightColor: {
          primary: {
            indicatorActive: 'bg-primary'
          },
          secondary: {
            indicatorActive: 'bg-secondary'
          },
          success: {
            indicatorActive: 'bg-success'
          },
          info: {
            indicatorActive: 'bg-info'
          },
          warning: {
            indicatorActive: 'bg-warning'
          },
          error: {
            indicatorActive: 'bg-error'
          },
          neutral: {
            indicatorActive: 'bg-inverted'
          }
        },
        active: {
          false: {
            link: [
              'text-muted hover:text-default',
              'transition-colors'
            ]
          }
        },
        highlight: {
          true: ''
        },
        highlightVariant: {
          straight: '',
          circuit: ''
        },
        body: {
          true: {
            bottom: 'mt-6'
          }
        }
      },
      compoundVariants: [
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
        },
        {
          highlight: true,
          highlightVariant: 'straight',
          class: {
            list: 'ms-2.5 ps-4 border-s border-default',
            item: '-ms-px',
            indicator: 'absolute ms-2.5 transition-[translate,height] duration-200 h-(--indicator-size) translate-y-(--indicator-position) w-px rounded-full',
            indicatorLine: 'hidden',
            indicatorActive: 'w-full h-full'
          }
        },
        {
          highlight: true,
          highlightVariant: 'circuit',
          class: {
            list: 'ps-6.5',
            item: '-ms-px',
            itemWithChildren: 'ps-px',
            indicator: 'absolute ms-2.5 start-0 top-0 rtl:-scale-x-100',
            indicatorLine: 'absolute inset-0 bg-(--ui-border)',
            indicatorActive: 'absolute w-full h-(--indicator-size) translate-y-(--indicator-position) transition-[translate,height] duration-200 ease-out'
          }
        }
      ],
      defaultVariants: {
        color: 'primary',
        highlightColor: 'primary',
        highlightVariant: 'straight'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/content/ContentToc.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/content/content-toc.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

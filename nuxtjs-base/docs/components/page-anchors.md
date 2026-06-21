---
title: "PageAnchors"
description: "A list of anchors to be displayed in the page."
canonical_url: "https://ui.nuxt.com/docs/components/page-anchors"
---
# PageAnchors

> A list of anchors to be displayed in the page.

## Usage

Use the PageAnchors component to display a list of links.

```vue
<script setup lang="ts">
import type { PageAnchor } from '@nuxt/ui'

const links = ref<PageAnchor[]>([
  {
    label: "Documentation",
    icon: "i-lucide-book-open",
    to: "/docs/getting-started"
  },
  {
    label: "Components",
    icon: "i-lucide-box",
    to: "/docs/components"
  },
  {
    label: "Figma Kit",
    icon: "i-simple-icons-figma",
    to: "https://go.nuxt.com/figma-ui",
    target: "_blank"
  },
  {
    label: "Releases",
    icon: "i-simple-icons-github",
    to: "https://github.com/nuxt/ui/releases",
    target: "_blank"
  }
])
</script>

<template>
  <UPageAnchors :links="links" />
</template>
```

### Links

Use the `links` prop as an array of objects with the following properties:

- `label: string`
- `icon?: string`
- `class?: any`
- `ui?: { item?: ClassNameValue, link?: ClassNameValue, linkLabel?: ClassNameValue, linkLabelExternalIcon?: ClassNameValue, linkLeading?: ClassNameValue, linkLeadingIcon?: ClassNameValue }`

You can pass any property from the [Link](/docs/components/link#props) component such as `to`, `target`, etc.

```vue
<script setup lang="ts">
import type { PageAnchor } from '@nuxt/ui'

const links = ref<PageAnchor[]>([
  {
    label: "Documentation",
    icon: "i-lucide-book-open",
    to: "/docs/getting-started"
  },
  {
    label: "Components",
    icon: "i-lucide-box",
    to: "/docs/components"
  },
  {
    label: "Figma Kit",
    icon: "i-simple-icons-figma",
    to: "https://go.nuxt.com/figma-ui",
    target: "_blank"
  },
  {
    label: "Releases",
    icon: "i-simple-icons-github",
    to: "https://github.com/nuxt/ui/releases",
    target: "_blank"
  }
])
</script>

<template>
  <UPageAnchors :links="links" />
</template>
```

## Examples

> [!NOTE]
> 
> While these examples use [Nuxt Content](https://content.nuxt.com), the components can be integrated with any content management system.

### Within a layout

Use the PageAnchors component inside the [PageAside](/docs/components/page-aside) component to display a list of links above the navigation.

```vue [layouts/docs.vue]
<script setup lang="ts">
import type { PageAnchor } from '@nuxt/ui'
import type { ContentNavigationItem } from '@nuxt/content'

const navigation = inject<ContentNavigationItem[]>('navigation')

const links: PageAnchor[] = [{
  label: 'Documentation',
  icon: 'i-lucide-book-open',
  to: '/docs/getting-started'
}, {
  label: 'Components',
  icon: 'i-lucide-box',
  to: '/docs/components'
}, {
  label: 'Figma Kit',
  icon: 'i-simple-icons-figma',
  to: 'https://go.nuxt.com/figma-ui',
  target: '_blank'
}, {
  label: 'Releases',
  icon: 'i-lucide-rocket',
  to: 'https://github.com/nuxt/ui/releases',
  target: '_blank'
}]
</script>

<template>
  <UPage>
    <template #left>
      <UPageAside>
        <UPageAnchors :links="links" />

        <USeparator type="dashed" />

        <UContentNavigation :navigation="navigation" />
      </UPageAside>
    </template>

    <slot />
  </UPage>
</template>
```

## API

### Props

```ts
/**
 * Props for the PageAnchors component
 */
interface PageAnchorsProps {
  /**
   * The element or component this component should render as.
   * @default "\"nav\""
   */
  as?: any;
  links?: T[] | undefined;
  ui?: { root?: SlotClass; list?: SlotClass; item?: SlotClass; link?: SlotClass; linkLeading?: SlotClass; linkLeadingIcon?: SlotClass; linkLabel?: SlotClass; linkLabelExternalIcon?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the PageAnchors component
 */
interface PageAnchorsSlots {
  link(): any;
  link-leading(): any;
  link-label(): any;
  link-trailing(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    pageAnchors: {
      slots: {
        root: '',
        list: '',
        item: 'relative',
        link: 'group text-sm flex items-center gap-1.5 py-1 rounded-sm outline-primary/25 focus-visible:outline-3',
        linkLeading: 'rounded-md p-1 inline-flex ring-inset ring',
        linkLeadingIcon: 'size-4 shrink-0',
        linkLabel: 'truncate',
        linkLabelExternalIcon: 'size-3 absolute top-0 text-dimmed'
      },
      variants: {
        active: {
          true: {
            link: 'text-primary font-semibold',
            linkLeading: 'bg-primary ring-primary text-inverted'
          },
          false: {
            link: [
              'text-muted hover:text-default font-medium',
              'transition-colors'
            ],
            linkLeading: [
              'bg-elevated/50 ring-accented text-dimmed group-hover:bg-primary group-hover:ring-primary group-hover:text-inverted',
              'transition'
            ]
          }
        }
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/PageAnchors.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/page-anchors.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

---
title: "PageHeader"
description: "A responsive header for your pages."
canonical_url: "https://ui.nuxt.com/docs/components/page-header"
---
# PageHeader

> A responsive header for your pages.

## Usage

The PageHeader component displays a header for your page.

Use it inside the default slot of the [Page](/docs/components/page) component, before the [PageBody](/docs/components/page-body) component:

```vue
<template>
  <UPage>
    <UPageHeader />

    <UPageBody />
  </UPage>
</template>
```

### Title

Use the `title` prop to display a title in the header.

```vue
<template>
  <UPageHeader title="PageHeader" />
</template>
```

### Description

Use the `description` prop to display a description in the header.

```vue
<template>
  <UPageHeader title="PageHeader" description="A responsive page header with title, description and actions." />
</template>
```

### Headline

Use the `headline` prop to display a headline in the header.

```vue
<template>
  <UPageHeader title="PageHeader" description="A responsive page header with title, description and actions." headline="Components" />
</template>
```

### Links

Use the `links` prop to display a list of [Button](/docs/components/button) in the header.

```vue
<script setup lang="ts">
import type { ButtonProps } from '@nuxt/ui'

const links = ref<ButtonProps[]>([
  {
    label: "GitHub",
    icon: "i-simple-icons-github",
    to: "https://github.com/nuxt/ui/tree/v4/src/runtime/components/PageHeader.vue",
    target: "_blank"
  }
])
</script>

<template>
  <UPageHeader title="PageHeader" description="A responsive page header with title, description and actions." headline="Components" :links="links" />
</template>
```

## Examples

> [!NOTE]
> 
> While these examples use [Nuxt Content](https://content.nuxt.com), the components can be integrated with any content management system.

### Within a page

Use the PageHeader component in a page to display the header of the page:

```vue [pages/[...slug].vue]
<script setup lang="ts">
const route = useRoute()

definePageMeta({
  layout: 'docs'
})

const { data: page } = await useAsyncData(route.path, () => {
  return queryCollection('docs').path(route.path).first()
})

const { data: surround } = await useAsyncData(`${route.path}-surround`, () => {
  return queryCollectionItemSurroundings('content', route.path)
})
</script>

<template>
  <UPage>
    <UPageHeader
      :title="page.title"
      :description="page.description"
      :headline="page.headline"
      :links="page.links"
    />

    <UPageBody>
      <ContentRenderer :value="page" />

      <USeparator />

      <UContentSurround :surround="surround" />
    </UPageBody>

    <template #right>
      <UContentToc :links="page.body.toc.links" />
    </template>
  </UPage>
</template>
```

## API

### Props

```ts
/**
 * Props for the PageHeader component
 */
interface PageHeaderProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  headline?: string | undefined;
  title?: string | undefined;
  description?: string | undefined;
  /**
   * Display a list of Button next to the title.
   * `{ color: 'neutral', variant: 'outline' }`{lang="ts-type"}
   */
  links?: ButtonProps[] | undefined;
  ui?: { root?: SlotClass; container?: SlotClass; wrapper?: SlotClass; headline?: SlotClass; title?: SlotClass; description?: SlotClass; links?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the PageHeader component
 */
interface PageHeaderSlots {
  headline(): any;
  title(): any;
  description(): any;
  links(): any;
  default(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    pageHeader: {
      slots: {
        root: 'relative border-b border-default py-8',
        container: '',
        wrapper: 'flex flex-col lg:flex-row lg:items-center lg:justify-between gap-4',
        headline: 'mb-2.5 text-sm font-semibold text-primary flex items-center gap-1.5',
        title: 'text-3xl sm:text-4xl text-pretty font-bold text-highlighted',
        description: 'text-lg text-pretty text-muted',
        links: 'flex flex-wrap items-center gap-1.5'
      },
      variants: {
        title: {
          true: {
            description: 'mt-4'
          }
        }
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/PageHeader.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/page-header.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

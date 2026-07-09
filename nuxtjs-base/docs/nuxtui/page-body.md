---
title: "PageBody"
description: "The main content of your page."
canonical_url: "https://ui.nuxt.com/docs/components/page-body"
---
# PageBody

> The main content of your page.

## Usage

The PageBody component wraps your main content and adds some padding for consistent spacing.

Use it inside the default slot of the [Page](/docs/components/page) component, after the [PageHeader](/docs/components/page-header) component:

```vue
<template>
  <UPage>
    <UPageHeader />

    <UPageBody />
  </UPage>
</template>
```

## Examples

> [!NOTE]
> 
> While these examples use [Nuxt Content](https://content.nuxt.com), the components can be integrated with any content management system.

### Within a page

Use the PageBody component in a page to display the content of the page:

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
    <UPageHeader :title="page.title" :description="page.description" />

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

> [!NOTE]
> 
> In this example, we use the [`ContentRenderer`](https://content.nuxt.com/docs/components/content-renderer) component from `@nuxt/content` to render the content of the page.

## API

### Props

```ts
/**
 * Props for the PageBody component
 */
interface PageBodyProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  ui?: { base?: any; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the PageBody component
 */
interface PageBodySlots {
  default(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    pageBody: {
      base: 'mt-8 pb-24 space-y-12'
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/PageBody.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/page-body.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

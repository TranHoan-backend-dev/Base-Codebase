---
title: "PageAside"
description: "A sticky aside to display your page navigation."
canonical_url: "https://ui.nuxt.com/docs/components/page-aside"
---
# PageAside

> A sticky aside to display your page navigation.

## Usage

The PageAside component is a sticky `<aside>` element that is only displayed starting from the [`lg` breakpoint](https://tailwindcss.com/docs/breakpoints).

> [!TIP]
> See: /docs/getting-started/theme/css-variables#header
> 
> The PageAside component uses the `--ui-header-height` CSS variable to position itself correctly below the `Header`.

Use it inside the `left` or `right` slot of the [Page](/docs/components/page) component:

```vue
<template>
  <UPage>
    <template #left>
      <UPageAside />
    </template>
  </UPage>
</template>
```

## Examples

> [!NOTE]
> 
> While these examples use [Nuxt Content](https://content.nuxt.com), the components can be integrated with any content management system.

### Within a layout

Use the PageAside component in a layout to display the navigation:

```vue [layouts/docs.vue]
<script setup lang="ts">
import type { ContentNavigationItem } from '@nuxt/content'

const navigation = inject<Ref<ContentNavigationItem[]>>('navigation')
</script>

<template>
  <UPage>
    <template #left>
      <UPageAside>
        <UContentNavigation :navigation="navigation" />
      </UPageAside>
    </template>

    <slot />
  </UPage>
</template>
```

> [!NOTE]
> 
> In this example, we use the `ContentNavigation` component to display the navigation injected in `app.vue`.

## API

### Props

```ts
/**
 * Props for the PageAside component
 */
interface PageAsideProps {
  /**
   * The element or component this component should render as.
   * @default "\"aside\""
   */
  as?: any;
  ui?: { root?: SlotClass; container?: SlotClass; top?: SlotClass; topHeader?: SlotClass; topBody?: SlotClass; topFooter?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the PageAside component
 */
interface PageAsideSlots {
  top(): any;
  default(): any;
  bottom(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    pageAside: {
      slots: {
        root: 'hidden overflow-y-auto lg:block lg:max-h-[calc(100vh-var(--ui-header-height))] lg:sticky lg:top-(--ui-header-height) py-8 lg:ps-4 lg:-ms-4 lg:pe-6.5',
        container: 'relative',
        top: 'sticky -top-8 -mt-8 pointer-events-none z-[1]',
        topHeader: 'h-8 bg-default -mx-4 px-4',
        topBody: 'bg-default relative pointer-events-auto flex flex-col -mx-4 px-4',
        topFooter: 'h-8 bg-gradient-to-b from-default -mx-4 px-4'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/PageAside.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/page-aside.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

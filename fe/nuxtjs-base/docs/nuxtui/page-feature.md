---
title: "PageFeature"
description: "A component to showcase key features of your application."
canonical_url: "https://ui.nuxt.com/docs/components/page-feature"
---
# PageFeature

> A component to showcase key features of your application.

## Usage

The PageFeature component is used by the [PageSection](/docs/components/page-section) component to display [features](/docs/components/page-section#features).

### Title

Use the `title` prop to set the title of the feature.

```vue
<template>
  <UPageFeature title="Theme" />
</template>
```

### Description

Use the `description` prop to set the description of the feature.

```vue
<template>
  <UPageFeature title="Theme" description="Customize Nuxt UI with your own colors, fonts, and more." />
</template>
```

### Icon

Use the `icon` prop to set the icon of the feature.

```vue
<template>
  <UPageFeature title="Theme" description="Customize Nuxt UI with your own colors, fonts, and more." icon="i-lucide-swatch-book" />
</template>
```

### Link

You can pass any property from the [`<NuxtLink>`](https://nuxt.com/docs/api/components/nuxt-link) component such as `to`, `target`, `rel`, etc.

```vue
<template>
  <UPageFeature title="Theme" description="Customize Nuxt UI with your own colors, fonts, and more." icon="i-lucide-swatch-book" to="/docs/getting-started/theme/design-system" target="_blank" />
</template>
```

### Orientation

Use the `orientation` prop to change the orientation of the feature. Defaults to `horizontal`.

```vue
<template>
  <UPageFeature orientation="vertical" title="Theme" description="Customize Nuxt UI with your own colors, fonts, and more." icon="i-lucide-swatch-book" />
</template>
```

## API

### Props

```ts
/**
 * Props for the PageFeature component
 */
interface PageFeatureProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  /**
   * The icon displayed next to the title when `orientation` is `horizontal` and above the title when `orientation` is `vertical`.
   */
  icon?: any;
  title?: string | undefined;
  description?: string | undefined;
  /**
   * The orientation of the page feature.
   * @default "\"horizontal\""
   */
  orientation?: "horizontal" | "vertical" | undefined;
  to?: string | it | et | undefined;
  target?: "_blank" | "_parent" | "_self" | "_top" | (string & {}) | null | undefined;
  onClick?: ((event: MouseEvent) => void | Promise<void>) | undefined;
  ui?: { root?: SlotClass; wrapper?: SlotClass; leading?: SlotClass; leadingIcon?: SlotClass; title?: SlotClass; description?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the PageFeature component
 */
interface PageFeatureSlots {
  leading(): any;
  title(): any;
  description(): any;
  default(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    pageFeature: {
      slots: {
        root: 'relative rounded-sm',
        wrapper: '',
        leading: 'inline-flex items-center justify-center',
        leadingIcon: 'size-5 shrink-0 text-primary',
        title: 'text-base text-pretty font-semibold text-highlighted',
        description: 'text-[15px] text-pretty text-muted'
      },
      variants: {
        orientation: {
          horizontal: {
            root: 'flex items-start gap-2.5',
            leading: 'p-0.5'
          },
          vertical: {
            leading: 'mb-2.5'
          }
        },
        to: {
          true: {
            root: [
              'outline-primary/25 has-focus-visible:outline-3',
              'transition'
            ]
          }
        },
        title: {
          true: {
            description: 'mt-1'
          }
        }
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/PageFeature.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/page-feature.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

---
title: "Container"
description: "A container lets you center and constrain the width of your content."
canonical_url: "https://ui.nuxt.com/docs/components/container"
---
# Container

> A container lets you center and constrain the width of your content.

## Usage

Use the default slot to center and constrain the width of your content.

> [!TIP]
> See: /docs/getting-started/theme/css-variables#container
> 
> Its max width is controlled by the `--ui-container` CSS variable.

```vue [ContainerExample.vue]
<template>
  <UContainer>
    <Placeholder class="h-32" />
  </UContainer>
</template>
```

## API

### Props

```ts
/**
 * Props for the Container component
 */
interface ContainerProps {
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
 * Slots for the Container component
 */
interface ContainerSlots {
  default(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    container: {
      base: 'w-full max-w-(--ui-container) mx-auto px-4 sm:px-6 lg:px-8'
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Container.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/container.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

---
title: "Skeleton"
description: "A placeholder to show while content is loading."
canonical_url: "https://ui.nuxt.com/docs/components/skeleton"
---
# Skeleton

> A placeholder to show while content is loading.

## Usage

Use the Skeleton component as-is to display a placeholder.

```vue [SkeletonExample.vue]
<template>
  <div class="flex items-center gap-4">
    <USkeleton class="size-12 rounded-full" />

    <div class="grid gap-2">
      <USkeleton class="h-4 w-[250px]" />
      <USkeleton class="h-4 w-[200px]" />
    </div>
  </div>
</template>
```

## API

### Props

```ts
/**
 * Props for the Skeleton component
 */
interface SkeletonProps {
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
 * Slots for the Skeleton component
 */
interface SkeletonSlots {
  default(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    skeleton: {
      base: 'animate-pulse rounded-md bg-elevated'
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Skeleton.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/skeleton.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

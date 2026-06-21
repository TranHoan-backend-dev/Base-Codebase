---
title: "Main"
description: "A main element that fills the available viewport height."
canonical_url: "https://ui.nuxt.com/docs/components/main"
---
# Main

> A main element that fills the available viewport height.

## Usage

The Main component renders a `<main>` element that works together with the [Header](/docs/components/header) component to create a full-height layout that extends to the viewport's available height.

> [!TIP]
> See: /docs/getting-started/theme/css-variables#header
> 
> The Main component uses the `--ui-header-height` CSS variable to position itself correctly below the `Header`.

## Examples

### Within `app.vue`

Use the Main component in your `app.vue` or in a layout:

```vue [app.vue]
<template>
  <UApp>
    <UHeader />

    <UMain>
      <NuxtLayout>
        <NuxtPage />
      </NuxtLayout>
    </UMain>

    <UFooter />
  </UApp>
</template>
```

## API

### Props

```ts
/**
 * Props for the Main component
 */
interface MainProps {
  /**
   * The element or component this component should render as.
   * @default "\"main\""
   */
  as?: any;
  ui?: { base?: any; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the Main component
 */
interface MainSlots {
  default(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    main: {
      base: 'min-h-[calc(100vh-var(--ui-header-height))]'
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Main.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/main.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

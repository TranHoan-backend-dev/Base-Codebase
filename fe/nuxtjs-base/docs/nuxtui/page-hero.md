---
title: "PageHero"
description: "A responsive hero for your pages."
canonical_url: "https://ui.nuxt.com/docs/components/page-hero"
---
# PageHero

> A responsive hero for your pages.

## Usage

The PageHero component wraps your content in a [Container](/docs/components/container) while maintaining full-width flexibility making it easy to add background colors, images or patterns. It provides a flexible way to display content with an illustration in the default slot.

```vue
<template>
  <u-page-hero description=A Nuxt/Vue-integrated UI library providing a rich set of fully-styled, accessible and highly customizable components for building modern web applications. title=Ultimate Vue UI library>
  <u-page-card variant=subtle>
  <template unwrap=p v-slot:default=>
  <p>
  <img alt=App screenshot src=/blocks/image4.png /></p></template></u-page-card></u-page-hero>
</template>
```

### Title

Use the `title` prop to set the title of the hero.

```vue
<template>
  <UPageHero title="Ultimate Vue UI library" />
</template>
```

### Description

Use the `description` prop to set the description of the hero.

```vue
<template>
  <UPageHero title="Ultimate Vue UI library" description="A Nuxt/Vue-integrated UI library providing a rich set of fully-styled, accessible and highly customizable components for building modern web applications." />
</template>
```

### Headline

Use the `headline` prop to set the headline of the hero.

```vue
<template>
  <UPageHero title="Ultimate Vue UI library" description="A Nuxt/Vue-integrated UI library providing a rich set of fully-styled, accessible and highly customizable components for building modern web applications." headline="New release" />
</template>
```

### Links

Use the `links` prop to display a list of [Button](/docs/components/button) under the description.

```vue
<script setup lang="ts">
import type { ButtonProps } from '@nuxt/ui'

const links = ref<ButtonProps[]>([
  {
    label: "Get started",
    to: "/docs/getting-started",
    icon: "i-lucide-square-play"
  },
  {
    label: "Learn more",
    to: "/docs/getting-started/theme/design-system",
    color: "neutral",
    variant: "subtle",
    trailingIcon: "i-lucide-arrow-right"
  }
])
</script>

<template>
  <UPageHero title="Ultimate Vue UI library" description="A Nuxt/Vue-integrated UI library providing a rich set of fully-styled, accessible and highly customizable components for building modern web applications." :links="links" />
</template>
```

### Orientation

Use the `orientation` prop to change the orientation with the default slot. Defaults to `vertical`.

```vue
<script setup lang="ts">
import type { ButtonProps } from '@nuxt/ui'

const links = ref<ButtonProps[]>([
  {
    label: "Get started",
    to: "/docs/getting-started",
    icon: "i-lucide-square-play"
  },
  {
    label: "Learn more",
    to: "/docs/getting-started/theme/design-system",
    color: "neutral",
    variant: "subtle",
    trailingIcon: "i-lucide-arrow-right"
  }
])
</script>

<template>
  <UPageHero title="Ultimate Vue UI library" description="A Nuxt/Vue-integrated UI library providing a rich set of fully-styled, accessible and highly customizable components for building modern web applications." headline="New release" orientation="horizontal" :links="links">
    <img src="/blocks/image4.png" alt="App screenshot" class="rounded-lg shadow-2xl ring ring-default" />
  </UPageHero>
</template>
```

### Reverse

Use the `reverse` prop to reverse the orientation of the default slot.

```vue
<script setup lang="ts">
import type { ButtonProps } from '@nuxt/ui'

const links = ref<ButtonProps[]>([
  {
    label: "Get started",
    to: "/docs/getting-started",
    icon: "i-lucide-square-play"
  },
  {
    label: "Learn more",
    to: "/docs/getting-started/theme/design-system",
    color: "neutral",
    variant: "subtle",
    trailingIcon: "i-lucide-arrow-right"
  }
])
</script>

<template>
  <UPageHero title="Ultimate Vue UI library" description="A Nuxt/Vue-integrated UI library providing a rich set of fully-styled, accessible and highly customizable components for building modern web applications." headline="New release" orientation="horizontal" reverse :links="links">
    <img src="/blocks/image4.png" alt="App screenshot" class="rounded-lg shadow-2xl ring ring-default" />
  </UPageHero>
</template>
```

## API

### Props

```ts
/**
 * Props for the PageHero component
 */
interface PageHeroProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  headline?: string | undefined;
  title?: string | undefined;
  description?: string | undefined;
  /**
   * Display a list of Button under the description.
   * `{ size: 'xl' }`{lang="ts-type"}
   */
  links?: ButtonProps[] | undefined;
  /**
   * The orientation of the page hero.
   * @default "\"vertical\""
   */
  orientation?: "horizontal" | "vertical" | undefined;
  /**
   * Reverse the order of the default slot.
   */
  reverse?: boolean | undefined;
  ui?: { root?: SlotClass; container?: SlotClass; wrapper?: SlotClass; header?: SlotClass; headline?: SlotClass; title?: SlotClass; description?: SlotClass; body?: SlotClass; footer?: SlotClass; links?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the PageHero component
 */
interface PageHeroSlots {
  top(): any;
  header(): any;
  headline(): any;
  title(): any;
  description(): any;
  body(): any;
  footer(): any;
  links(): any;
  default(): any;
  bottom(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    pageHero: {
      slots: {
        root: 'relative isolate',
        container: 'flex flex-col lg:grid py-24 sm:py-32 lg:py-40 gap-16 sm:gap-y-24',
        wrapper: '',
        header: '',
        headline: 'mb-4',
        title: 'text-5xl sm:text-7xl text-pretty tracking-tight font-bold text-highlighted',
        description: 'text-lg sm:text-xl/8 text-muted',
        body: 'mt-10',
        footer: 'mt-10',
        links: 'flex flex-wrap gap-x-6 gap-y-3'
      },
      variants: {
        orientation: {
          horizontal: {
            container: 'lg:grid-cols-2 lg:items-center',
            description: 'text-pretty'
          },
          vertical: {
            container: '',
            headline: 'justify-center',
            wrapper: 'text-center',
            description: 'text-balance',
            links: 'justify-center'
          }
        },
        reverse: {
          true: {
            wrapper: 'order-last'
          }
        },
        headline: {
          true: {
            headline: 'font-semibold text-primary flex items-center gap-1.5'
          }
        },
        title: {
          true: {
            description: 'mt-6'
          }
        }
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/PageHero.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/page-hero.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

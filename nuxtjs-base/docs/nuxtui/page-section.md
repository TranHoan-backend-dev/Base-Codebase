---
title: "PageSection"
description: "A responsive section for your pages."
canonical_url: "https://ui.nuxt.com/docs/components/page-section"
---
# PageSection

> A responsive section for your pages.

## Usage

The PageSection component wraps your content in a [Container](/docs/components/container) while maintaining full-width flexibility making it easy to add background colors, images or patterns. It provides a flexible way to display content with an illustration in the default slot.

```vue
<template>
  <u-page-section :features=[{"title":"Icons","description":"Nuxt UI integrates with Nuxt Icon to access over 200,000+ icons from Iconify.","icon":"i-lucide-smile","to":"/docs/getting-started/integrations/icons"},{"title":"Fonts","description":"Nuxt UI integrates with Nuxt Fonts to provide plug-and-play font optimization.","icon":"i-lucide-a-large-small","to":"/docs/getting-started/integrations/fonts"},{"title":"Color Mode","description":"Nuxt UI integrates with Nuxt Color Mode to switch between light and dark.","icon":"i-lucide-sun-moon","to":"/docs/getting-started/integrations/color-mode"}] description=Nuxt UI provides a comprehensive suite of components and utilities to help you build beautiful and accessible web applications with Vue and Nuxt. headline=Features title=Beautiful Vue UI components />
</template>
```

Use it after a [PageHero](/docs/components/page-hero) component:

```vue
<template>
  <UPageHero />

  <UPageSection />
</template>
```

### Title

Use the `title` prop to set the title of the section.

```vue
<template>
  <UPageSection title="Beautiful Vue UI components" />
</template>
```

### Description

Use the `description` prop to set the description of the section.

```vue
<template>
  <UPageSection title="Beautiful Vue UI components" description="Nuxt UI provides a comprehensive suite of components and utilities to help you build beautiful and accessible web applications with Vue and Nuxt." />
</template>
```

### Headline

Use the `headline` prop to set the headline of the section.

```vue
<template>
  <UPageSection title="Beautiful Vue UI components" description="Nuxt UI provides a comprehensive suite of components and utilities to help you build beautiful and accessible web applications with Vue and Nuxt." headline="Features" />
</template>
```

### Icon

Use the `icon` prop to set the icon of the section.

```vue
<template>
  <UPageSection title="Beautiful Vue UI components" description="Nuxt UI provides a comprehensive suite of components and utilities to help you build beautiful and accessible web applications with Vue and Nuxt." icon="i-lucide-rocket" />
</template>
```

### Features

Use the `features` prop to display a list of [PageFeature](/docs/components/page-feature) under the description as an array of objects with the following properties:

- `title?: string`
- `description?: string`
- `icon?: string`
- `orientation?: 'horizontal' | 'vertical'`

You can pass any property from the [Link](/docs/components/link#props) component such as `to`, `target`, etc.

```vue
<script setup lang="ts">
import type { PageFeatureProps } from '@nuxt/ui'

const features = ref<PageFeatureProps[]>([
  {
    title: "Icons",
    description: "Nuxt UI integrates with Nuxt Icon to access over 200,000+ icons from Iconify.",
    icon: "i-lucide-smile",
    to: "/docs/getting-started/integrations/icons"
  },
  {
    title: "Fonts",
    description: "Nuxt UI integrates with Nuxt Fonts to provide plug-and-play font optimization.",
    icon: "i-lucide-a-large-small",
    to: "/docs/getting-started/integrations/fonts"
  },
  {
    title: "Color Mode",
    description: "Nuxt UI integrates with Nuxt Color Mode to switch between light and dark.",
    icon: "i-lucide-sun-moon",
    to: "/docs/getting-started/integrations/color-mode"
  }
])
</script>

<template>
  <UPageSection title="Beautiful Vue UI components" description="Nuxt UI provides a comprehensive suite of components and utilities to help you build beautiful and accessible web applications with Vue and Nuxt." :features="features" />
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
    icon: "i-lucide-square-play",
    color: "neutral"
  },
  {
    label: "Explore components",
    to: "/docs/components/app",
    color: "neutral",
    variant: "subtle",
    trailingIcon: "i-lucide-arrow-right"
  }
])
</script>

<template>
  <UPageSection title="Beautiful Vue UI components" description="Nuxt UI provides a comprehensive suite of components and utilities to help you build beautiful and accessible web applications with Vue and Nuxt." :links="links" />
</template>
```

### Orientation

Use the `orientation` prop to change the orientation with the default slot. Defaults to `vertical`.

```vue
<script setup lang="ts">
import type { PageFeatureProps, ButtonProps } from '@nuxt/ui'

const features = ref<PageFeatureProps[]>([
  {
    title: "Icons",
    description: "Nuxt UI integrates with Nuxt Icon to access over 200,000+ icons from Iconify.",
    icon: "i-lucide-smile",
    to: "/docs/getting-started/integrations/icons"
  },
  {
    title: "Fonts",
    description: "Nuxt UI integrates with Nuxt Fonts to provide plug-and-play font optimization.",
    icon: "i-lucide-a-large-small",
    to: "/docs/getting-started/integrations/fonts"
  },
  {
    title: "Color Mode",
    description: "Nuxt UI integrates with Nuxt Color Mode to switch between light and dark.",
    icon: "i-lucide-sun-moon",
    to: "/docs/getting-started/integrations/color-mode"
  }
])
const links = ref<ButtonProps[]>([
  {
    label: "Explore components",
    to: "/docs/components/app",
    color: "neutral",
    variant: "subtle",
    trailingIcon: "i-lucide-arrow-right"
  }
])
</script>

<template>
  <UPageSection title="Beautiful Vue UI components" description="Nuxt UI provides a comprehensive suite of components and utilities to help you build beautiful and accessible web applications with Vue and Nuxt." icon="i-lucide-rocket" orientation="horizontal" :features="features" :links="links">
    <img src="https://picsum.photos/704/1294" width="352" height="647" alt="Illustration" class="w-full rounded-lg" loading="lazy" />
  </UPageSection>
</template>
```

### Reverse

Use the `reverse` prop to reverse the orientation of the default slot.

```vue
<script setup lang="ts">
import type { PageFeatureProps, ButtonProps } from '@nuxt/ui'

const features = ref<PageFeatureProps[]>([
  {
    title: "Icons",
    description: "Nuxt UI integrates with Nuxt Icon to access over 200,000+ icons from Iconify.",
    icon: "i-lucide-smile",
    to: "/docs/getting-started/integrations/icons"
  },
  {
    title: "Fonts",
    description: "Nuxt UI integrates with Nuxt Fonts to provide plug-and-play font optimization.",
    icon: "i-lucide-a-large-small",
    to: "/docs/getting-started/integrations/fonts"
  },
  {
    title: "Color Mode",
    description: "Nuxt UI integrates with Nuxt Color Mode to switch between light and dark.",
    icon: "i-lucide-sun-moon",
    to: "/docs/getting-started/integrations/color-mode"
  }
])
const links = ref<ButtonProps[]>([
  {
    label: "Explore components",
    to: "/docs/components/app",
    color: "neutral",
    variant: "subtle",
    trailingIcon: "i-lucide-arrow-right"
  }
])
</script>

<template>
  <UPageSection title="Beautiful Vue UI components" description="Nuxt UI provides a comprehensive suite of components and utilities to help you build beautiful and accessible web applications with Vue and Nuxt." icon="i-lucide-rocket" orientation="horizontal" reverse :features="features" :links="links">
    <img src="https://picsum.photos/704/1294" width="352" height="647" alt="Illustration" class="w-full rounded-lg" loading="lazy" />
  </UPageSection>
</template>
```

## API

### Props

```ts
/**
 * Props for the PageSection component
 */
interface PageSectionProps {
  /**
   * The element or component this component should render as.
   * @default "\"section\""
   */
  as?: any;
  /**
   * The headline displayed above the title.
   */
  headline?: string | undefined;
  /**
   * The icon displayed above the title.
   */
  icon?: any;
  title?: string | undefined;
  description?: string | undefined;
  /**
   * Display a list of Button under the description.
   * `{ size: 'lg' }`{lang="ts-type"}
   */
  links?: ButtonProps[] | undefined;
  /**
   * Display a list of PageFeature under the description.
   */
  features?: PageFeatureProps[] | undefined;
  /**
   * The orientation of the section.
   * @default "\"vertical\""
   */
  orientation?: "vertical" | "horizontal" | undefined;
  /**
   * Reverse the order of the default slot.
   */
  reverse?: boolean | undefined;
  ui?: { root?: SlotClass; container?: SlotClass; wrapper?: SlotClass; header?: SlotClass; leading?: SlotClass; leadingIcon?: SlotClass; headline?: SlotClass; title?: SlotClass; description?: SlotClass; body?: SlotClass; features?: SlotClass; footer?: SlotClass; links?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the PageSection component
 */
interface PageSectionSlots {
  top(): any;
  header(): any;
  leading(): any;
  headline(): any;
  title(): any;
  description(): any;
  body(): any;
  features(): any;
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
    pageSection: {
      slots: {
        root: 'relative isolate',
        container: 'flex flex-col lg:grid py-16 sm:py-24 lg:py-32 gap-8 sm:gap-16',
        wrapper: '',
        header: '',
        leading: 'flex items-center mb-6',
        leadingIcon: 'size-10 shrink-0 text-primary',
        headline: 'mb-3',
        title: 'text-3xl sm:text-4xl lg:text-5xl text-pretty tracking-tight font-bold text-highlighted',
        description: 'text-base sm:text-lg text-muted',
        body: 'mt-8',
        features: 'grid',
        footer: 'mt-8',
        links: 'flex flex-wrap gap-x-6 gap-y-3'
      },
      variants: {
        orientation: {
          horizontal: {
            container: 'lg:grid-cols-2 lg:items-center',
            description: 'text-pretty',
            features: 'gap-4'
          },
          vertical: {
            container: '',
            headline: 'justify-center',
            leading: 'justify-center',
            title: 'text-center',
            description: 'text-center text-balance',
            links: 'justify-center',
            features: 'sm:grid-cols-2 lg:grid-cols-3 gap-8'
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
        },
        description: {
          true: ''
        },
        body: {
          true: ''
        }
      },
      compoundVariants: [
        {
          orientation: 'vertical',
          title: true,
          class: {
            body: 'mt-16'
          }
        },
        {
          orientation: 'vertical',
          description: true,
          class: {
            body: 'mt-16'
          }
        },
        {
          orientation: 'vertical',
          body: true,
          class: {
            footer: 'mt-16'
          }
        }
      ]
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/PageSection.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/page-section.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

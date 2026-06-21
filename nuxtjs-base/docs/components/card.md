---
title: "Card"
description: "Display content in a card with a header, body and footer."
canonical_url: "https://ui.nuxt.com/docs/components/card"
---
# Card

> Display content in a card with a header, body and footer.

## Usage

Use the `header`, `default` and `footer` slots to add content to the Card.

```vue
<template>
  <UCard>
    <Placeholder class="h-32" />
  
    <template #header>
      <Placeholder class="h-8" />
    </template>
    <template #footer>
      <Placeholder class="h-8" />
    </template></UCard>
</template>
```

### Title `4.7+`

Use the `title` prop to set the title of the Card's header.

```vue
<template>
  <UCard title="Card with title" class="w-full">
    <Placeholder class="h-32" />
  </UCard>
</template>
```

### Description `4.7+`

Use the `description` prop to set the description of the Card's header.

```vue
<template>
  <UCard title="Card with description" description="Lorem ipsum dolor sit amet, consectetur adipiscing elit." class="w-full">
    <Placeholder class="h-32" />
  </UCard>
</template>
```

### Variant

Use the `variant` prop to change the variant of the Card.

```vue
<template>
  <UCard variant="subtle">
    <Placeholder class="h-32" />
  
    <template #header>
      <Placeholder class="h-8" />
    </template>
    <template #footer>
      <Placeholder class="h-8" />
    </template></UCard>
</template>
```

## API

### Props

```ts
/**
 * Props for the Card component
 */
interface CardProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  title?: string | undefined;
  description?: string | undefined;
  variant?: "solid" | "outline" | "soft" | "subtle" | undefined;
  ui?: { root?: SlotClass; header?: SlotClass; title?: SlotClass; description?: SlotClass; body?: SlotClass; footer?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the Card component
 */
interface CardSlots {
  header(): any;
  title(): any;
  description(): any;
  default(): any;
  footer(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    card: {
      slots: {
        root: 'rounded-lg overflow-hidden',
        header: 'p-4 sm:px-6',
        title: 'text-highlighted font-semibold',
        description: 'mt-1 text-muted text-sm',
        body: 'p-4 sm:p-6',
        footer: 'p-4 sm:px-6'
      },
      variants: {
        variant: {
          solid: {
            root: 'bg-inverted text-inverted',
            title: 'text-inverted',
            description: 'text-dimmed'
          },
          outline: {
            root: 'bg-default ring ring-default divide-y divide-default'
          },
          soft: {
            root: 'bg-elevated/50 divide-y divide-default'
          },
          subtle: {
            root: 'bg-elevated/50 ring ring-default divide-y divide-default'
          }
        }
      },
      defaultVariants: {
        variant: 'outline'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Card.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/card.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

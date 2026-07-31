---
title: "Kbd"
description: "A kbd element to display a keyboard key."
canonical_url: "https://ui.nuxt.com/docs/components/kbd"
---
# Kbd

> A kbd element to display a keyboard key.

## Usage

Use the default slot to set the value of the Kbd.

```vue
<template>
  <UKbd>
    K
  </UKbd>
</template>
```

### Value

Use the `value` prop to set the value of the Kbd.

```vue
<template>
  <UKbd value="K" />
</template>
```

You can pass special keys to the `value` prop that goes through the [`useKbd`](https://github.com/nuxt/ui/blob/v4/src/runtime/composables/useKbd.ts) composable. For example, the `meta` key displays as `⌘` on macOS and `Ctrl` on other platforms.

```vue
<template>
  <UKbd value="meta" />
</template>
```

### Color

Use the `color` prop to change the color of the Kbd.

```vue
<template>
  <UKbd color="neutral">
    K
  </UKbd>
</template>
```

### Variant

Use the `variant` prop to change the variant of the Kbd.

```vue
<template>
  <UKbd color="neutral" variant="solid">
    K
  </UKbd>
</template>
```

### Size

Use the `size` prop to change the size of the Kbd.

```vue
<template>
  <UKbd size="lg">
    K
  </UKbd>
</template>
```

## Examples

### `class` prop

Use the `class` prop to override the base styles of the Badge.

```vue
<template>
  <UKbd class="font-bold rounded-full" variant="subtle">
    K
  </UKbd>
</template>
```

## API

### Props

```ts
/**
 * Props for the Kbd component
 */
interface KbdProps {
  /**
   * The element or component this component should render as.
   * @default "\"kbd\""
   */
  as?: any;
  value?: string | undefined;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  variant?: "outline" | "soft" | "subtle" | "solid" | undefined;
  size?: "sm" | "md" | "lg" | undefined;
  ui?: { base?: any; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the Kbd component
 */
interface KbdSlots {
  default(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    kbd: {
      base: 'inline-flex items-center justify-center px-1 rounded-sm font-medium font-sans uppercase',
      variants: {
        color: {
          primary: '',
          secondary: '',
          success: '',
          info: '',
          warning: '',
          error: '',
          neutral: ''
        },
        variant: {
          solid: '',
          outline: '',
          soft: '',
          subtle: ''
        },
        size: {
          sm: 'h-4 min-w-[16px] text-[10px]',
          md: 'h-5 min-w-[20px] text-[11px]',
          lg: 'h-6 min-w-[24px] text-[12px]'
        }
      },
      compoundVariants: [
        {
          color: 'primary',
          variant: 'solid',
          class: 'text-inverted bg-primary'
        },
        {
          color: 'secondary',
          variant: 'solid',
          class: 'text-inverted bg-secondary'
        },
        {
          color: 'success',
          variant: 'solid',
          class: 'text-inverted bg-success'
        },
        {
          color: 'info',
          variant: 'solid',
          class: 'text-inverted bg-info'
        },
        {
          color: 'warning',
          variant: 'solid',
          class: 'text-inverted bg-warning'
        },
        {
          color: 'error',
          variant: 'solid',
          class: 'text-inverted bg-error'
        },
        {
          color: 'primary',
          variant: 'outline',
          class: 'ring ring-inset ring-primary/50 text-primary'
        },
        {
          color: 'secondary',
          variant: 'outline',
          class: 'ring ring-inset ring-secondary/50 text-secondary'
        },
        {
          color: 'success',
          variant: 'outline',
          class: 'ring ring-inset ring-success/50 text-success'
        },
        {
          color: 'info',
          variant: 'outline',
          class: 'ring ring-inset ring-info/50 text-info'
        },
        {
          color: 'warning',
          variant: 'outline',
          class: 'ring ring-inset ring-warning/50 text-warning'
        },
        {
          color: 'error',
          variant: 'outline',
          class: 'ring ring-inset ring-error/50 text-error'
        },
        {
          color: 'primary',
          variant: 'soft',
          class: 'text-primary bg-primary/10'
        },
        {
          color: 'secondary',
          variant: 'soft',
          class: 'text-secondary bg-secondary/10'
        },
        {
          color: 'success',
          variant: 'soft',
          class: 'text-success bg-success/10'
        },
        {
          color: 'info',
          variant: 'soft',
          class: 'text-info bg-info/10'
        },
        {
          color: 'warning',
          variant: 'soft',
          class: 'text-warning bg-warning/10'
        },
        {
          color: 'error',
          variant: 'soft',
          class: 'text-error bg-error/10'
        },
        {
          color: 'primary',
          variant: 'subtle',
          class: 'text-primary ring ring-inset ring-primary/25 bg-primary/10'
        },
        {
          color: 'secondary',
          variant: 'subtle',
          class: 'text-secondary ring ring-inset ring-secondary/25 bg-secondary/10'
        },
        {
          color: 'success',
          variant: 'subtle',
          class: 'text-success ring ring-inset ring-success/25 bg-success/10'
        },
        {
          color: 'info',
          variant: 'subtle',
          class: 'text-info ring ring-inset ring-info/25 bg-info/10'
        },
        {
          color: 'warning',
          variant: 'subtle',
          class: 'text-warning ring ring-inset ring-warning/25 bg-warning/10'
        },
        {
          color: 'error',
          variant: 'subtle',
          class: 'text-error ring ring-inset ring-error/25 bg-error/10'
        },
        {
          color: 'neutral',
          variant: 'solid',
          class: 'text-inverted bg-inverted'
        },
        {
          color: 'neutral',
          variant: 'outline',
          class: 'ring ring-inset ring-accented text-default bg-default'
        },
        {
          color: 'neutral',
          variant: 'soft',
          class: 'text-default bg-elevated'
        },
        {
          color: 'neutral',
          variant: 'subtle',
          class: 'ring ring-inset ring-accented text-default bg-elevated'
        }
      ],
      defaultVariants: {
        variant: 'outline',
        color: 'neutral',
        size: 'md'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Kbd.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/kbd.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

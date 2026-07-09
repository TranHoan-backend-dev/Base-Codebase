---
title: "Avatar"
description: "An img element with fallback and Nuxt Image support."
canonical_url: "https://ui.nuxt.com/docs/components/avatar"
---
# Avatar

> An img element with fallback and Nuxt Image support.

## Usage

The Avatar uses the `<NuxtImg>` component when [`@nuxt/image`](https://github.com/nuxt/image) is installed, falling back to `img` otherwise.

```vue
<template>
  <UAvatar src="https://github.com/benjamincanac.png" />
</template>
```

> [!NOTE]
> 
> You can pass any property from the HTML `<img>` element such as `alt`, `loading`, etc.

> [!TIP]
> 
> To opt-out of `@nuxt/image`, use the `as` prop: `:as="{ img: 'img' }"`.

### Src

Use the `src` prop to set the image URL.

```vue
<template>
  <UAvatar src="https://github.com/benjamincanac.png" loading="lazy" />
</template>
```

### Size

Use the `size` prop to set the size of the Avatar.

```vue
<template>
  <UAvatar src="https://github.com/benjamincanac.png" size="xl" loading="lazy" />
</template>
```

> [!NOTE]
> 
> The `<img>` element's `width` and `height` are automatically set based on the `size` prop.

### Icon

Use the `icon` prop to display a fallback [Icon](/docs/components/icon).

```vue
<template>
  <UAvatar icon="i-lucide-image" size="md" />
</template>
```

### Text

Use the `text` prop to display a fallback text.

```vue
<template>
  <UAvatar text="+1" size="md" />
</template>
```

### Alt

When no icon or text is provided, the **initials** of the `alt` prop is used as fallback.

```vue
<template>
  <UAvatar alt="Benjamin Canac" size="md" />
</template>
```

> [!NOTE]
> 
> The `alt` prop is passed to the `img` element as the `alt` attribute.

### Color `4.8+`

Use the `color` prop to change the color of the Avatar.

```vue
<template>
  <UAvatar color="primary" alt="Benjamin Canac" />
</template>
```

### Chip

Use the `chip` prop to display a chip around the Avatar.

```vue
<template>
  <UAvatar src="https://github.com/benjamincanac.png" loading="lazy" :chip="{
  inset: true
}" />
</template>
```

## Examples

### With tooltip

You can use a [Tooltip](/docs/components/tooltip) component to display a tooltip when hovering the Avatar.

```vue [AvatarTooltipExample.vue]
<template>
  <UTooltip text="Benjamin Canac">
    <UAvatar
      src="https://github.com/benjamincanac.png"
      alt="Benjamin Canac"
      loading="lazy"
    />
  </UTooltip>
</template>
```

### With mask

You can use a CSS mask to display an Avatar with a custom shape instead of a simple circle.

```vue [AvatarMaskExample.vue]
<template>
  <UAvatar class="rounded-none squircle" src="https://avatars.githubusercontent.com/u/739984?v=4" alt="Benjamin Canac" loading="lazy" />
</template>

<style>
.squircle {
  mask-image: url("data:image/svg+xml,%3csvg width='200' height='200' xmlns='http://www.w3.org/2000/svg'%3e%3cpath d='M100 0C20 0 0 20 0 100s20 100 100 100 100-20 100-100S180 0 100 0Z'/%3e%3c/svg%3e");
  mask-size: contain;
  mask-position: center;
  mask-repeat: no-repeat;
}
</style>
```

## API

### Props

```ts
/**
 * Props for the Avatar component
 */
interface AvatarProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  src?: string | undefined;
  alt?: string | undefined;
  icon?: any;
  text?: string | undefined;
  size?: "md" | "xs" | "sm" | "lg" | "xl" | "3xs" | "2xs" | "2xl" | "3xl" | undefined;
  color?: "error" | "primary" | "secondary" | "success" | "info" | "warning" | "neutral" | undefined;
  chip?: boolean | ChipProps | undefined;
  ui?: { root?: SlotClass; image?: SlotClass; fallback?: SlotClass; icon?: SlotClass; } | undefined;
  loading?: "lazy" | "eager" | undefined;
  referrerpolicy?: HTMLAttributeReferrerPolicy | undefined;
  crossorigin?: "" | "anonymous" | "use-credentials" | undefined;
  decoding?: "async" | "auto" | "sync" | undefined;
  height?: Numberish | undefined;
  sizes?: string | undefined;
  srcset?: string | undefined;
  usemap?: string | undefined;
  width?: Numberish | undefined;
}
```

> [!NOTE]
> See: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/img#attributes
> 
> This component also supports all native `<img>` HTML attributes.

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    avatar: {
      slots: {
        root: 'inline-flex items-center justify-center shrink-0 select-none rounded-full align-middle',
        image: 'h-full w-full rounded-[inherit] object-cover',
        fallback: 'font-medium truncate',
        icon: 'shrink-0'
      },
      variants: {
        color: {
          primary: {
            root: 'bg-primary/10',
            fallback: 'text-primary',
            icon: 'text-primary'
          },
          secondary: {
            root: 'bg-secondary/10',
            fallback: 'text-secondary',
            icon: 'text-secondary'
          },
          success: {
            root: 'bg-success/10',
            fallback: 'text-success',
            icon: 'text-success'
          },
          info: {
            root: 'bg-info/10',
            fallback: 'text-info',
            icon: 'text-info'
          },
          warning: {
            root: 'bg-warning/10',
            fallback: 'text-warning',
            icon: 'text-warning'
          },
          error: {
            root: 'bg-error/10',
            fallback: 'text-error',
            icon: 'text-error'
          },
          neutral: {
            root: 'bg-elevated',
            fallback: 'text-muted',
            icon: 'text-muted'
          }
        },
        size: {
          '3xs': {
            root: 'size-4 text-[8px]'
          },
          '2xs': {
            root: 'size-5 text-[10px]'
          },
          xs: {
            root: 'size-6 text-xs'
          },
          sm: {
            root: 'size-7 text-sm'
          },
          md: {
            root: 'size-8 text-base'
          },
          lg: {
            root: 'size-9 text-lg'
          },
          xl: {
            root: 'size-10 text-xl'
          },
          '2xl': {
            root: 'size-11 text-[22px]'
          },
          '3xl': {
            root: 'size-12 text-2xl'
          }
        }
      },
      defaultVariants: {
        size: 'md',
        color: 'neutral'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Avatar.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/avatar.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

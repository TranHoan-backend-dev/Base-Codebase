---
title: "ColorModeAvatar"
description: "An Avatar with a different source for light and dark mode."
canonical_url: "https://ui.nuxt.com/docs/components/color-mode-avatar"
---
# ColorModeAvatar

> An Avatar with a different source for light and dark mode.

## Usage

The ColorModeAvatar component extends the [Avatar](/docs/components/avatar) component, so you can pass any property such as `size`, `icon`, etc.

Use the `light` and `dark` props to define the source for light and dark mode.

```vue
<template>
  <UColorModeAvatar light="https://github.com/vuejs.png" dark="https://github.com/nuxt.png" />
</template>
```

> [!NOTE]
> 
> Switch between light and dark mode to see the different images: <u-color-mode-select size="sm">
> 
> 
> 
> </u-color-mode-select>

## API

### Props

```ts
/**
 * Props for the ColorModeAvatar component
 */
interface ColorModeAvatarProps {
  light: string;
  dark: string;
  /**
   * The element or component this component should render as.
   */
  as?: any;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  ui?: { root?: SlotClass; image?: SlotClass; fallback?: SlotClass; icon?: SlotClass; } | undefined;
  alt?: string | undefined;
  crossorigin?: "" | "anonymous" | "use-credentials" | undefined;
  decoding?: "async" | "auto" | "sync" | undefined;
  height?: Numberish | undefined;
  loading?: "lazy" | "eager" | undefined;
  referrerpolicy?: HTMLAttributeReferrerPolicy | undefined;
  sizes?: string | undefined;
  srcset?: string | undefined;
  usemap?: string | undefined;
  width?: Numberish | undefined;
  icon?: any;
  text?: string | undefined;
  size?: "md" | "3xs" | "2xs" | "xs" | "sm" | "lg" | "xl" | "2xl" | "3xl" | undefined;
  chip?: boolean | ChipProps | undefined;
}
```

> [!NOTE]
> See: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/img#attributes
> 
> This component also supports all native `<img>` HTML attributes.

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/color-mode/ColorModeAvatar.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/color-mode/color-mode-avatar.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

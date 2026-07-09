---
title: "ColorModeButton"
description: "A Button to switch between light and dark mode."
canonical_url: "https://ui.nuxt.com/docs/components/color-mode-button"
---
# ColorModeButton

> A Button to switch between light and dark mode.

## Usage

The ColorModeButton component extends the [Button](/docs/components/button) component, so you can pass any property such as `color`, `variant`, `size`, etc.

```vue
<template>
  <UColorModeButton />
</template>
```

> [!NOTE]
> 
> The button defaults to `color="neutral"` and `variant="ghost"`.

## Examples

### With custom icons

**Nuxt:**

Use the `app.config.ts` to customize the icon with the `ui.icons` property:

```ts [app/app.config.ts]
export default defineAppConfig({
  ui: {
    icons: {
      light: 'i-ph-sun',
      dark: 'i-ph-moon'
    }
  }
})
```

**Vue:**

Use the `vite.config.ts` to customize the icon with the `ui.icons` property:

```ts [vite.config.ts]
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import ui from '@nuxt/ui/vite'

export default defineConfig({
  plugins: [
    vue(),
    ui({
      ui: {
        icons: {
          light: 'i-ph-sun',
          dark: 'i-ph-moon'
        }
      }
    })
  ]
})
```

### With fallback slot

As the button is wrapped in a [ClientOnly](https://nuxt.com/docs/api/components/client-only) component, you can pass a `fallback` slot to display a placeholder while the component is loading.

```vue
<template>
  <UColorModeButton>
    <template #fallback>
      <UButton loading variant="ghost" color="neutral" />
    </template></UColorModeButton>
</template>
```

## API

### Props

```ts
/**
 * Props for the ColorModeButton component
 */
interface ColorModeButtonProps {
  /**
   * @default "\"neutral\""
   */
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  /**
   * @default "\"ghost\""
   */
  variant?: "link" | "ghost" | "solid" | "outline" | "soft" | "subtle" | undefined;
  /**
   * The element or component this component should render as when not a link.
   */
  as?: any;
  /**
   * Display an icon on the right side.
   */
  trailingIcon?: any;
  ui?: { base?: SlotClass; label?: SlotClass; leadingIcon?: SlotClass; leadingAvatar?: SlotClass; leadingAvatarSize?: SlotClass; trailingIcon?: SlotClass; } | undefined;
  name?: string | undefined;
  /**
   * When `true`, the icon will be displayed on the right side.
   */
  trailing?: boolean | undefined;
  /**
   * When `true`, the loading icon will be displayed.
   */
  loading?: boolean | undefined;
  onClick?: ((event: MouseEvent) => void | Promise<void>) | ((event: MouseEvent) => void | Promise<void>)[] | undefined;
  /**
   * Display an icon based on the `leading` and `trailing` props.
   */
  icon?: any;
  size?: "md" | "xs" | "sm" | "lg" | "xl" | undefined;
  /**
   * Display an avatar on the left side.
   */
  avatar?: AvatarProps | undefined;
  /**
   * Class to apply when the link is exact active
   */
  exactActiveClass?: string | undefined;
  /**
   * Pass the returned promise of `router.push()` to `document.startViewTransition()` if supported.
   */
  viewTransition?: boolean | undefined;
  autofocus?: Booleanish | undefined;
  disabled?: boolean | undefined;
  form?: string | undefined;
  formaction?: string | undefined;
  formenctype?: string | undefined;
  formmethod?: string | undefined;
  formnovalidate?: Booleanish | undefined;
  formtarget?: string | undefined;
  /**
   * The type of the button when not a link.
   */
  type?: "reset" | "submit" | "button" | undefined;
  label?: string | undefined;
  activeColor?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  activeVariant?: "link" | "ghost" | "solid" | "outline" | "soft" | "subtle" | undefined;
  /**
   * Render the button with equal padding on all sides.
   */
  square?: boolean | undefined;
  /**
   * Render the button full width.
   */
  block?: boolean | undefined;
  /**
   * Set loading state automatically based on the `@click` promise state
   */
  loadingAuto?: boolean | undefined;
  /**
   * When `true`, the icon will be displayed on the left side.
   */
  leading?: boolean | undefined;
  /**
   * Display an icon on the left side.
   */
  leadingIcon?: any;
  /**
   * The icon when the `loading` prop is `true`.
   */
  loadingIcon?: any;
}
```

> [!NOTE]
> See: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/button#attributes
> 
> This component also supports all native `<button>` HTML attributes.

### Slots

```ts
/**
 * Slots for the ColorModeButton component
 */
interface ColorModeButtonSlots {
}
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/color-mode/ColorModeButton.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/color-mode/color-mode-button.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

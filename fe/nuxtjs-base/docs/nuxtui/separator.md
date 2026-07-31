---
title: "Separator"
description: "Separates content horizontally or vertically."
canonical_url: "https://ui.nuxt.com/docs/components/separator"
---
# Separator

> Separates content horizontally or vertically.

## Usage

Use the Separator component as-is to separate content.

```vue
<template>
  <USeparator />
</template>
```

### Orientation

Use the `orientation` prop to change the orientation of the Separator. Defaults to `horizontal`.

```vue
<template>
  <USeparator orientation="vertical" class="h-48" />
</template>
```

### Label

Use the `label` prop to display a label in the middle of the Separator.

```vue
<template>
  <USeparator label="Hello World" />
</template>
```

### Position `4.8+`

Use the `position` prop to change the position of the content of the Separator. Defaults to `center`.

```vue
<template>
  <USeparator position="start" label="Hello World" />
</template>
```

### Icon

Use the `icon` prop to display an icon in the middle of the Separator.

```vue
<template>
  <USeparator icon="i-simple-icons-nuxtdotjs" />
</template>
```

### Avatar

Use the `avatar` prop to display an avatar in the middle of the Separator.

```vue
<template>
  <USeparator :avatar="{
  src: 'https://github.com/nuxt.png',
  loading: 'lazy'
}" />
</template>
```

### Color

Use the `color` prop to change the color of the Separator. Defaults to `neutral`.

```vue
<template>
  <USeparator color="primary" type="solid" />
</template>
```

### Type

Use the `type` prop to change the type of the Separator. Defaults to `solid`.

```vue
<template>
  <USeparator type="dashed" />
</template>
```

### Size

Use the `size` prop to change the size of the Separator. Defaults to `xs`.

```vue
<template>
  <USeparator size="lg" />
</template>
```

## API

### Props

```ts
/**
 * Props for the Separator component
 */
interface SeparatorProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  /**
   * Display a label on the separator.
   */
  label?: string | undefined;
  /**
   * Display an icon on the separator.
   */
  icon?: any;
  /**
   * Display an avatar on the separator.
   */
  avatar?: AvatarProps | undefined;
  color?: "error" | "neutral" | "primary" | "secondary" | "success" | "info" | "warning" | undefined;
  size?: "xs" | "sm" | "md" | "lg" | "xl" | undefined;
  type?: "solid" | "dashed" | "dotted" | undefined;
  /**
   * The orientation of the separator.
   * @default "\"horizontal\""
   */
  orientation?: "horizontal" | "vertical" | undefined;
  /**
   * The position of the content.
   * @default "\"center\""
   */
  position?: "center" | "start" | "end" | undefined;
  ui?: { root?: SlotClass; border?: SlotClass; container?: SlotClass; icon?: SlotClass; avatar?: SlotClass; avatarSize?: SlotClass; label?: SlotClass; } | undefined;
  /**
   * Whether or not the component is purely decorative. <br>When `true`, accessibility-related attributes
   * are updated so that that the rendered element is removed from the accessibility tree.
   */
  decorative?: boolean | undefined;
}
```

### Slots

```ts
/**
 * Slots for the Separator component
 */
interface SeparatorSlots {
  default(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    separator: {
      slots: {
        root: 'flex items-center align-center text-center',
        border: '',
        container: 'font-medium text-default flex',
        icon: 'shrink-0 size-5',
        avatar: 'shrink-0',
        avatarSize: '2xs',
        label: 'text-sm'
      },
      variants: {
        color: {
          primary: {
            border: 'border-primary'
          },
          secondary: {
            border: 'border-secondary'
          },
          success: {
            border: 'border-success'
          },
          info: {
            border: 'border-info'
          },
          warning: {
            border: 'border-warning'
          },
          error: {
            border: 'border-error'
          },
          neutral: {
            border: 'border-default'
          }
        },
        orientation: {
          horizontal: {
            root: 'w-full flex-row',
            border: 'w-full',
            container: 'whitespace-nowrap'
          },
          vertical: {
            root: 'h-full flex-col',
            border: 'h-full',
            container: ''
          }
        },
        size: {
          xs: '',
          sm: '',
          md: '',
          lg: '',
          xl: ''
        },
        position: {
          start: '',
          center: '',
          end: ''
        },
        type: {
          solid: {
            border: 'border-solid'
          },
          dashed: {
            border: 'border-dashed'
          },
          dotted: {
            border: 'border-dotted'
          }
        }
      },
      compoundVariants: [
        {
          orientation: 'horizontal',
          position: 'start',
          class: {
            container: 'me-3'
          }
        },
        {
          orientation: 'horizontal',
          position: 'center',
          class: {
            container: 'mx-3'
          }
        },
        {
          orientation: 'horizontal',
          position: 'end',
          class: {
            container: 'ms-3'
          }
        },
        {
          orientation: 'vertical',
          position: 'start',
          class: {
            container: 'mb-2'
          }
        },
        {
          orientation: 'vertical',
          position: 'center',
          class: {
            container: 'my-2'
          }
        },
        {
          orientation: 'vertical',
          position: 'end',
          class: {
            container: 'mt-2'
          }
        },
        {
          orientation: 'horizontal',
          size: 'xs',
          class: {
            border: 'border-t'
          }
        },
        {
          orientation: 'horizontal',
          size: 'sm',
          class: {
            border: 'border-t-[2px]'
          }
        },
        {
          orientation: 'horizontal',
          size: 'md',
          class: {
            border: 'border-t-[3px]'
          }
        },
        {
          orientation: 'horizontal',
          size: 'lg',
          class: {
            border: 'border-t-[4px]'
          }
        },
        {
          orientation: 'horizontal',
          size: 'xl',
          class: {
            border: 'border-t-[5px]'
          }
        },
        {
          orientation: 'vertical',
          size: 'xs',
          class: {
            border: 'border-s'
          }
        },
        {
          orientation: 'vertical',
          size: 'sm',
          class: {
            border: 'border-s-[2px]'
          }
        },
        {
          orientation: 'vertical',
          size: 'md',
          class: {
            border: 'border-s-[3px]'
          }
        },
        {
          orientation: 'vertical',
          size: 'lg',
          class: {
            border: 'border-s-[4px]'
          }
        },
        {
          orientation: 'vertical',
          size: 'xl',
          class: {
            border: 'border-s-[5px]'
          }
        }
      ],
      defaultVariants: {
        color: 'neutral',
        size: 'xs',
        type: 'solid'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Separator.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/separator.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

---
title: "Switch"
description: "A control that toggles between two states."
canonical_url: "https://ui.nuxt.com/docs/components/switch"
---
# Switch

> A control that toggles between two states.

## Usage

Use the `v-model` directive to control the checked state of the Switch.

```vue
<script setup lang="ts">
const value = ref(true)
</script>

<template>
  <USwitch v-model="value" />
</template>
```

Use the `default-value` prop to set the initial value when you do not need to control its state.

```vue
<template>
  <USwitch default-value />
</template>
```

### Label

Use the `label` prop to set the label of the Switch.

```vue
<template>
  <USwitch label="Check me" />
</template>
```

When using the `required` prop, an asterisk is added next to the label.

```vue
<template>
  <USwitch required label="Check me" />
</template>
```

### Description

Use the `description` prop to set the description of the Switch.

```vue
<template>
  <USwitch label="Check me" description="This is a checkbox." />
</template>
```

### Icon

Use the `checked-icon` and `unchecked-icon` props to set the icons of the Switch when checked and unchecked.

```vue
<template>
  <USwitch unchecked-icon="i-lucide-x" checked-icon="i-lucide-check" default-value label="Check me" />
</template>
```

### Loading

Use the `loading` prop to show a loading icon on the Switch.

```vue
<template>
  <USwitch loading default-value label="Check me" />
</template>
```

### Loading Icon

Use the `loading-icon` prop to customize the loading icon. Defaults to `i-lucide-loader-circle`.

```vue
<template>
  <USwitch loading loading-icon="i-lucide-loader" default-value label="Check me" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.loading` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.loading` key.

### Color

Use the `color` prop to change the color of the Switch.

```vue
<template>
  <USwitch color="neutral" default-value label="Check me" />
</template>
```

### Size

Use the `size` prop to change the size of the Switch.

```vue
<template>
  <USwitch size="xl" default-value label="Check me" />
</template>
```

### Disabled

Use the `disabled` prop to disable the Switch.

```vue
<template>
  <USwitch disabled label="Check me" />
</template>
```

## API

### Props

```ts
/**
 * Props for the Switch component
 */
interface SwitchProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  size?: "md" | "xs" | "sm" | "lg" | "xl" | undefined;
  /**
   * Highlight the ring color like a focus state.
   */
  highlight?: boolean | undefined;
  /**
   * When `true`, the loading icon will be displayed.
   */
  loading?: boolean | undefined;
  /**
   * The icon when the `loading` prop is `true`.
   */
  loadingIcon?: any;
  /**
   * Display an icon when the switch is checked.
   */
  checkedIcon?: any;
  /**
   * Display an icon when the switch is unchecked.
   */
  uncheckedIcon?: any;
  label?: string | undefined;
  description?: string | undefined;
  ui?: { root?: SlotClass; base?: SlotClass; container?: SlotClass; thumb?: SlotClass; icon?: SlotClass; wrapper?: SlotClass; label?: SlotClass; description?: SlotClass; } | undefined;
  /**
   * When `true`, prevents the user from interacting with the switch.
   */
  disabled?: boolean | undefined;
  id?: string | undefined;
  /**
   * The name of the field. Submitted with its owning form as part of a name/value pair.
   */
  name?: string | undefined;
  /**
   * When `true`, indicates that the user must set the value before the owning form can be submitted.
   */
  required?: boolean | undefined;
  /**
   * The value given as data when submitted with a `name`.
   */
  value?: string | undefined;
  /**
   * The state of the switch when it is initially rendered. Use when you do not need to control its state.
   */
  defaultValue?: T | undefined;
  /**
   * The controlled state of the switch. Can be bind as `v-model`.
   */
  modelValue?: T | null | undefined;
  /**
   * The value used when the switch is on. Defaults to `true`.
   */
  trueValue?: T | undefined;
  /**
   * The value used when the switch is off. Defaults to `false`.
   */
  falseValue?: T | undefined;
  autofocus?: Booleanish | undefined;
  form?: string | undefined;
  formaction?: string | undefined;
  formenctype?: string | undefined;
  formmethod?: string | undefined;
  formnovalidate?: Booleanish | undefined;
  formtarget?: string | undefined;
}
```

> [!NOTE]
> See: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/button#attributes
> 
> This component also supports all native `<button>` HTML attributes.

### Slots

```ts
/**
 * Slots for the Switch component
 */
interface SwitchSlots {
  label(): any;
  description(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the Switch component
 */
interface SwitchEmits {
  change: (payload: [event: Event]) => void;
  update:modelValue: (payload: [payload: T]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    switch: {
      slots: {
        root: 'relative flex items-start',
        base: [
          'inline-flex items-center shrink-0 rounded-full border-2 border-transparent focus-visible:outline-3 data-[state=unchecked]:bg-accented',
          'transition-[background] duration-200'
        ],
        container: 'flex items-center',
        thumb: 'group pointer-events-none rounded-full bg-default shadow-lg ring-0 transition-transform duration-200 data-[state=unchecked]:translate-x-0 data-[state=unchecked]:rtl:-translate-x-0 flex items-center justify-center',
        icon: [
          'absolute shrink-0 group-data-[state=unchecked]:text-dimmed opacity-0 size-10/12',
          'transition-[color,opacity] duration-200'
        ],
        wrapper: 'ms-2',
        label: 'block font-medium text-default',
        description: 'text-muted'
      },
      variants: {
        color: {
          primary: {
            base: 'data-[state=checked]:bg-primary outline-primary/25',
            icon: 'group-data-[state=checked]:text-primary'
          },
          secondary: {
            base: 'data-[state=checked]:bg-secondary outline-secondary/25',
            icon: 'group-data-[state=checked]:text-secondary'
          },
          success: {
            base: 'data-[state=checked]:bg-success outline-success/25',
            icon: 'group-data-[state=checked]:text-success'
          },
          info: {
            base: 'data-[state=checked]:bg-info outline-info/25',
            icon: 'group-data-[state=checked]:text-info'
          },
          warning: {
            base: 'data-[state=checked]:bg-warning outline-warning/25',
            icon: 'group-data-[state=checked]:text-warning'
          },
          error: {
            base: 'data-[state=checked]:bg-error outline-error/25',
            icon: 'group-data-[state=checked]:text-error'
          },
          neutral: {
            base: 'data-[state=checked]:bg-inverted outline-inverted/25',
            icon: 'group-data-[state=checked]:text-highlighted'
          }
        },
        size: {
          xs: {
            base: 'w-7',
            container: 'h-4',
            thumb: 'size-3 data-[state=checked]:translate-x-3 data-[state=checked]:rtl:-translate-x-3',
            wrapper: 'text-xs'
          },
          sm: {
            base: 'w-8',
            container: 'h-4',
            thumb: 'size-3.5 data-[state=checked]:translate-x-3.5 data-[state=checked]:rtl:-translate-x-3.5',
            wrapper: 'text-xs'
          },
          md: {
            base: 'w-9',
            container: 'h-5',
            thumb: 'size-4 data-[state=checked]:translate-x-4 data-[state=checked]:rtl:-translate-x-4',
            wrapper: 'text-sm'
          },
          lg: {
            base: 'w-10',
            container: 'h-5',
            thumb: 'size-4.5 data-[state=checked]:translate-x-4.5 data-[state=checked]:rtl:-translate-x-4.5',
            wrapper: 'text-sm'
          },
          xl: {
            base: 'w-11',
            container: 'h-6',
            thumb: 'size-5 data-[state=checked]:translate-x-5 data-[state=checked]:rtl:-translate-x-5',
            wrapper: 'text-base'
          }
        },
        checked: {
          true: {
            icon: 'group-data-[state=checked]:opacity-100'
          }
        },
        unchecked: {
          true: {
            icon: 'group-data-[state=unchecked]:opacity-100'
          }
        },
        loading: {
          true: {
            icon: 'animate-spin'
          }
        },
        highlight: {
          true: ''
        },
        required: {
          true: {
            label: "after:content-['*'] after:ms-0.5 after:text-error"
          }
        },
        disabled: {
          true: {
            root: 'opacity-75',
            base: 'cursor-not-allowed',
            label: 'cursor-not-allowed',
            description: 'cursor-not-allowed'
          }
        }
      },
      compoundVariants: [
        {
          color: 'primary',
          highlight: true,
          class: {
            base: 'ring ring-primary'
          }
        },
        {
          color: 'secondary',
          highlight: true,
          class: {
            base: 'ring ring-secondary'
          }
        },
        {
          color: 'success',
          highlight: true,
          class: {
            base: 'ring ring-success'
          }
        },
        {
          color: 'info',
          highlight: true,
          class: {
            base: 'ring ring-info'
          }
        },
        {
          color: 'warning',
          highlight: true,
          class: {
            base: 'ring ring-warning'
          }
        },
        {
          color: 'error',
          highlight: true,
          class: {
            base: 'ring ring-error'
          }
        },
        {
          color: 'neutral',
          highlight: true,
          class: {
            base: 'ring ring-inverted'
          }
        }
      ],
      defaultVariants: {
        color: 'primary',
        size: 'md'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Switch.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/switch.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

---
title: "Checkbox"
description: "An input element to toggle between checked and unchecked states."
canonical_url: "https://ui.nuxt.com/docs/components/checkbox"
---
# Checkbox

> An input element to toggle between checked and unchecked states.

## Usage

Use the `v-model` directive to control the checked state of the Checkbox.

```vue
<script setup lang="ts">
const value = ref(true)
</script>

<template>
  <UCheckbox v-model="value" />
</template>
```

Use the `default-value` prop to set the initial value when you do not need to control its state.

```vue
<template>
  <UCheckbox default-value />
</template>
```

### Indeterminate

Use the `indeterminate` value in the `v-model` directive or `default-value` prop to set the Checkbox to an [indeterminate state](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/checkbox#indeterminate_state_checkboxes).

```vue
<template>
  <UCheckbox default-value="indeterminate" />
</template>
```

### Indeterminate Icon

Use the `indeterminate-icon` prop to customize the indeterminate icon. Defaults to `i-lucide-minus`.

```vue
<template>
  <UCheckbox default-value="indeterminate" indeterminate-icon="i-lucide-plus" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.minus` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.minus` key.

### Label

Use the `label` prop to set the label of the Checkbox.

```vue
<template>
  <UCheckbox label="Check me" />
</template>
```

When using the `required` prop, an asterisk is added next to the label.

```vue
<template>
  <UCheckbox required label="Check me" />
</template>
```

### Description

Use the `description` prop to set the description of the Checkbox.

```vue
<template>
  <UCheckbox label="Check me" description="This is a checkbox." />
</template>
```

### Icon

Use the `icon` prop to set the icon of the Checkbox when it is checked. Defaults to `i-lucide-check`.

```vue
<template>
  <UCheckbox icon="i-lucide-heart" default-value label="Check me" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.check` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.check` key.

### Color

Use the `color` prop to change the color of the Checkbox.

```vue
<template>
  <UCheckbox color="neutral" default-value label="Check me" />
</template>
```

### Variant

Use the `variant` prop to change the variant of the Checkbox.

```vue
<template>
  <UCheckbox color="primary" variant="card" default-value label="Check me" />
</template>
```

### Size

Use the `size` prop to change the size of the Checkbox.

```vue
<template>
  <UCheckbox size="xl" variant="list" default-value label="Check me" />
</template>
```

### Indicator

Use the `indicator` prop to change the position or hide the indicator. Defaults to `start`.

```vue
<template>
  <UCheckbox indicator="end" variant="card" default-value label="Check me" />
</template>
```

### Disabled

Use the `disabled` prop to disable the Checkbox.

```vue
<template>
  <UCheckbox disabled label="Check me" />
</template>
```

## API

### Props

```ts
/**
 * Props for the Checkbox component
 */
interface CheckboxProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  label?: string | undefined;
  description?: string | undefined;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  variant?: "card" | "list" | undefined;
  size?: "xs" | "sm" | "md" | "lg" | "xl" | undefined;
  /**
   * Position of the indicator.
   */
  indicator?: "start" | "end" | "hidden" | undefined;
  /**
   * Highlight the ring color like a focus state.
   */
  highlight?: boolean | undefined;
  /**
   * The icon displayed when checked.
   */
  icon?: any;
  /**
   * The icon displayed when the checkbox is indeterminate.
   */
  indeterminateIcon?: any;
  ui?: { root?: SlotClass; container?: SlotClass; base?: SlotClass; indicator?: SlotClass; icon?: SlotClass; wrapper?: SlotClass; label?: SlotClass; description?: SlotClass; } | undefined;
  /**
   * When `true`, prevents the user from interacting with the checkbox
   */
  disabled?: boolean | undefined;
  /**
   * When `true`, indicates that the user must set the value before the owning form can be submitted.
   */
  required?: boolean | undefined;
  /**
   * The name of the field. Submitted with its owning form as part of a name/value pair.
   */
  name?: string | undefined;
  /**
   * The value given as data when submitted with a `name`.
   */
  value?: AcceptableValue | undefined;
  /**
   * Id of the element
   */
  id?: string | undefined;
  /**
   * The value of the checkbox when it is initially rendered. Use when you do not need to control its value.
   */
  defaultValue?: T | "indeterminate" | undefined;
  /**
   * The controlled value of the checkbox. Can be binded with v-model.
   */
  modelValue?: T | "indeterminate" | null | undefined;
  /**
   * The value used when the checkbox is checked. Defaults to `true`.
   */
  trueValue?: T | undefined;
  /**
   * The value used when the checkbox is unchecked. Defaults to `false`.
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
 * Slots for the Checkbox component
 */
interface CheckboxSlots {
  label(): any;
  description(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the Checkbox component
 */
interface CheckboxEmits {
  change: (payload: [event: Event]) => void;
  update:modelValue: (payload: [value: T | "indeterminate"]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    checkbox: {
      slots: {
        root: 'relative flex items-start',
        container: 'flex items-center',
        base: 'rounded-sm ring ring-inset ring-accented overflow-hidden focus-visible:outline-3',
        indicator: 'flex items-center justify-center size-full text-inverted',
        icon: 'shrink-0 size-full',
        wrapper: 'w-full',
        label: 'block font-medium text-default',
        description: 'text-muted'
      },
      variants: {
        color: {
          primary: {
            base: 'outline-primary/25 focus-visible:ring-primary',
            indicator: 'bg-primary'
          },
          secondary: {
            base: 'outline-secondary/25 focus-visible:ring-secondary',
            indicator: 'bg-secondary'
          },
          success: {
            base: 'outline-success/25 focus-visible:ring-success',
            indicator: 'bg-success'
          },
          info: {
            base: 'outline-info/25 focus-visible:ring-info',
            indicator: 'bg-info'
          },
          warning: {
            base: 'outline-warning/25 focus-visible:ring-warning',
            indicator: 'bg-warning'
          },
          error: {
            base: 'outline-error/25 focus-visible:ring-error',
            indicator: 'bg-error'
          },
          neutral: {
            base: 'outline-inverted/25 focus-visible:ring-inverted',
            indicator: 'bg-inverted'
          }
        },
        variant: {
          list: {
            root: ''
          },
          card: {
            root: 'border border-muted rounded-lg'
          }
        },
        indicator: {
          start: {
            root: 'flex-row',
            wrapper: 'ms-2'
          },
          end: {
            root: 'flex-row-reverse',
            wrapper: 'me-2'
          },
          hidden: {
            base: 'sr-only',
            wrapper: 'text-center'
          }
        },
        size: {
          xs: {
            base: 'size-3',
            container: 'h-4',
            wrapper: 'text-xs'
          },
          sm: {
            base: 'size-3.5',
            container: 'h-4',
            wrapper: 'text-xs'
          },
          md: {
            base: 'size-4',
            container: 'h-5',
            wrapper: 'text-sm'
          },
          lg: {
            base: 'size-4.5',
            container: 'h-5',
            wrapper: 'text-sm'
          },
          xl: {
            base: 'size-5',
            container: 'h-6',
            wrapper: 'text-base'
          }
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
        },
        highlight: {
          true: ''
        },
        checked: {
          true: ''
        }
      },
      compoundVariants: [
        {
          size: 'xs',
          variant: 'card',
          class: {
            root: 'p-2.5'
          }
        },
        {
          size: 'sm',
          variant: 'card',
          class: {
            root: 'p-3'
          }
        },
        {
          size: 'md',
          variant: 'card',
          class: {
            root: 'p-3.5'
          }
        },
        {
          size: 'lg',
          variant: 'card',
          class: {
            root: 'p-4'
          }
        },
        {
          size: 'xl',
          variant: 'card',
          class: {
            root: 'p-4.5'
          }
        },
        {
          color: 'primary',
          variant: 'card',
          class: {
            root: 'has-data-[state=checked]:border-primary'
          }
        },
        {
          color: 'secondary',
          variant: 'card',
          class: {
            root: 'has-data-[state=checked]:border-secondary'
          }
        },
        {
          color: 'success',
          variant: 'card',
          class: {
            root: 'has-data-[state=checked]:border-success'
          }
        },
        {
          color: 'info',
          variant: 'card',
          class: {
            root: 'has-data-[state=checked]:border-info'
          }
        },
        {
          color: 'warning',
          variant: 'card',
          class: {
            root: 'has-data-[state=checked]:border-warning'
          }
        },
        {
          color: 'error',
          variant: 'card',
          class: {
            root: 'has-data-[state=checked]:border-error'
          }
        },
        {
          color: 'neutral',
          variant: 'card',
          class: {
            root: 'has-data-[state=checked]:border-inverted'
          }
        },
        {
          variant: 'card',
          disabled: true,
          class: {
            root: 'cursor-not-allowed'
          }
        },
        {
          color: 'primary',
          highlight: true,
          class: {
            base: 'ring-primary'
          }
        },
        {
          color: 'secondary',
          highlight: true,
          class: {
            base: 'ring-secondary'
          }
        },
        {
          color: 'success',
          highlight: true,
          class: {
            base: 'ring-success'
          }
        },
        {
          color: 'info',
          highlight: true,
          class: {
            base: 'ring-info'
          }
        },
        {
          color: 'warning',
          highlight: true,
          class: {
            base: 'ring-warning'
          }
        },
        {
          color: 'error',
          highlight: true,
          class: {
            base: 'ring-error'
          }
        },
        {
          color: 'neutral',
          highlight: true,
          class: {
            base: 'ring-inverted'
          }
        }
      ],
      defaultVariants: {
        size: 'md',
        color: 'primary',
        variant: 'list',
        indicator: 'start'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Checkbox.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/checkbox.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

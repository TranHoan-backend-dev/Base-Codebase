---
title: "CheckboxGroup"
description: "A set of checklist buttons to select multiple option from a list."
canonical_url: "https://ui.nuxt.com/docs/components/checkbox-group"
---
# CheckboxGroup

> A set of checklist buttons to select multiple option from a list.

## Usage

Use the `v-model` directive to control the value of the CheckboxGroup or the `default-value` prop to set the initial value when you do not need to control its state.

```vue
<script setup lang="ts">
const items = ref([
  "System",
  "Light",
  "Dark"
])
const value = ref([
  "System"
])
</script>

<template>
  <UCheckboxGroup v-model="value" :items="items" />
</template>
```

### Items

Use the `items` prop as an array of strings or numbers:

```vue
<script setup lang="ts">
const items = ref([
  "System",
  "Light",
  "Dark"
])
const value = ref([
  "System"
])
</script>

<template>
  <UCheckboxGroup v-model="value" :items="items" />
</template>
```

You can also pass an array of objects with the following properties:

- `label?: string`
- `description?: string`
- [`value?: string`](#value-key)
- `disabled?: boolean`
- `class?: any`
- `ui?: { item?: ClassNameValue, container?: ClassNameValue, base?: ClassNameValue, 'indicator'?: ClassNameValue, icon?: ClassNameValue, wrapper?: ClassNameValue, label?: ClassNameValue, description?: ClassNameValue }`

```vue
<script setup lang="ts">
import type { CheckboxGroupItem } from '@nuxt/ui'

const items = ref<CheckboxGroupItem[]>([
  {
    label: "System",
    description: "This is the first option.",
    value: "system"
  },
  {
    label: "Light",
    description: "This is the second option.",
    value: "light"
  },
  {
    label: "Dark",
    description: "This is the third option.",
    value: "dark"
  }
])
const value = ref([
  "system"
])
</script>

<template>
  <UCheckboxGroup v-model="value" :items="items" />
</template>
```

> [!CAUTION]
> 
> When using objects, you need to reference the `value` property of the object in the `v-model` directive or the `default-value` prop.

### Value Key

You can change the property that is used to set the value by using the `value-key` prop. Defaults to `value`.

```vue
<script setup lang="ts">
import type { CheckboxGroupItem } from '@nuxt/ui'

const items = ref<CheckboxGroupItem[]>([
  {
    label: "System",
    description: "This is the first option.",
    id: "system"
  },
  {
    label: "Light",
    description: "This is the second option.",
    id: "light"
  },
  {
    label: "Dark",
    description: "This is the third option.",
    id: "dark"
  }
])
const value = ref([
  "light"
])
</script>

<template>
  <UCheckboxGroup v-model="value" value-key="id" :items="items" />
</template>
```

### Legend

Use the `legend` prop to set the legend of the CheckboxGroup.

```vue
<script setup lang="ts">
const items = ref([
  "System",
  "Light",
  "Dark"
])
</script>

<template>
  <UCheckboxGroup legend="Theme" :default-value="[
  'System'
]" :items="items" />
</template>
```

### Color

Use the `color` prop to change the color of the CheckboxGroup.

```vue
<script setup lang="ts">
const items = ref([
  "System",
  "Light",
  "Dark"
])
</script>

<template>
  <UCheckboxGroup color="neutral" :default-value="[
  'System'
]" :items="items" />
</template>
```

### Variant

Use the `variant` prop to change the variant of the CheckboxGroup.

```vue
<script setup lang="ts">
const items = ref([
  "System",
  "Light",
  "Dark"
])
</script>

<template>
  <UCheckboxGroup color="primary" variant="card" :default-value="[
  'System'
]" :items="items" />
</template>
```

### Size

Use the `size` prop to change the size of the CheckboxGroup.

```vue
<script setup lang="ts">
const items = ref([
  "System",
  "Light",
  "Dark"
])
</script>

<template>
  <UCheckboxGroup size="xl" variant="list" :default-value="[
  'System'
]" :items="items" />
</template>
```

### Orientation

Use the `orientation` prop to change the orientation of the CheckboxGroup. Defaults to `vertical`.

```vue
<script setup lang="ts">
const items = ref([
  "System",
  "Light",
  "Dark"
])
</script>

<template>
  <UCheckboxGroup orientation="horizontal" variant="list" :default-value="[
  'System'
]" :items="items" />
</template>
```

### Indicator

Use the `indicator` prop to change the position or hide the indicator. Defaults to `start`.

```vue
<script setup lang="ts">
const items = ref([
  "System",
  "Light",
  "Dark"
])
</script>

<template>
  <UCheckboxGroup indicator="end" variant="card" :default-value="[
  'System'
]" :items="items" />
</template>
```

### Disabled

Use the `disabled` prop to disable the CheckboxGroup.

```vue
<script setup lang="ts">
const items = ref([
  "System",
  "Light",
  "Dark"
])
</script>

<template>
  <UCheckboxGroup disabled :default-value="[
  'System'
]" :items="items" />
</template>
```

## API

### Props

```ts
/**
 * Props for the CheckboxGroup component
 */
interface CheckboxGroupProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  legend?: string | undefined;
  /**
   * When `items` is an array of objects, select the field to use as the value.
   * @default "\"value\" as never"
   */
  valueKey?: VK | undefined;
  /**
   * When `items` is an array of objects, select the field to use as the label.
   * @default "\"label\""
   */
  labelKey?: GetItemKeys<T> | undefined;
  /**
   * When `items` is an array of objects, select the field to use as the description.
   * @default "\"description\""
   */
  descriptionKey?: GetItemKeys<T> | undefined;
  items?: T | undefined;
  /**
   * The controlled value of the CheckboxGroup. Can be bind as `v-model`.
   */
  modelValue?: GetItemValue<T, VK, undefined, NestedItem<T>>[] | undefined;
  /**
   * The value of the CheckboxGroup when initially rendered. Use when you do not need to control the state of the CheckboxGroup.
   */
  defaultValue?: GetItemValue<T, VK, undefined, NestedItem<T>>[] | undefined;
  size?: "xs" | "sm" | "md" | "lg" | "xl" | undefined;
  variant?: "table" | "list" | "card" | undefined;
  /**
   * The orientation the checkbox buttons are laid out.
   * @default "\"vertical\""
   */
  orientation?: "horizontal" | "vertical" | undefined;
  ui?: ({ root?: SlotClass; fieldset?: SlotClass; legend?: SlotClass; item?: SlotClass; } & { root?: SlotClass; container?: SlotClass; base?: SlotClass; indicator?: SlotClass; icon?: SlotClass; wrapper?: SlotClass; label?: SlotClass; description?: SlotClass; }) | undefined;
  /**
   * When `true`, prevents the user from interacting with the checkboxes
   */
  disabled?: boolean | undefined;
  /**
   * Whether keyboard navigation should loop around
   */
  loop?: boolean | undefined;
  /**
   * The name of the field. Submitted with its owning form as part of a name/value pair.
   */
  name?: string | undefined;
  /**
   * When `true`, indicates that the user must set the value before the owning form can be submitted.
   */
  required?: boolean | undefined;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  /**
   * Highlight the ring color like a focus state.
   */
  highlight?: boolean | undefined;
  /**
   * Position of the indicator.
   */
  indicator?: "start" | "end" | "hidden" | undefined;
  /**
   * The icon displayed when checked.
   */
  icon?: any;
}
```

### Slots

```ts
/**
 * Slots for the CheckboxGroup component
 */
interface CheckboxGroupSlots {
  legend(): any;
  label(): any;
  description(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the CheckboxGroup component
 */
interface CheckboxGroupEmits {
  update:modelValue: (payload: [value: GetItemValue<T, VK, undefined, NestedItem<T>>[]]) => void;
  change: (payload: [event: Event]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    checkboxGroup: {
      slots: {
        root: 'relative',
        fieldset: 'flex gap-x-2',
        legend: 'mb-1 block font-medium text-default',
        item: ''
      },
      variants: {
        orientation: {
          horizontal: {
            fieldset: 'flex-row'
          },
          vertical: {
            fieldset: 'flex-col'
          }
        },
        color: {
          primary: {},
          secondary: {},
          success: {},
          info: {},
          warning: {},
          error: {},
          neutral: {}
        },
        variant: {
          list: {},
          card: {},
          table: {
            item: 'border border-muted'
          }
        },
        size: {
          xs: {
            fieldset: 'gap-y-0.5',
            legend: 'text-xs'
          },
          sm: {
            fieldset: 'gap-y-0.5',
            legend: 'text-xs'
          },
          md: {
            fieldset: 'gap-y-1',
            legend: 'text-sm'
          },
          lg: {
            fieldset: 'gap-y-1',
            legend: 'text-sm'
          },
          xl: {
            fieldset: 'gap-y-1.5',
            legend: 'text-base'
          }
        },
        required: {
          true: {
            legend: "after:content-['*'] after:ms-0.5 after:text-error"
          }
        },
        disabled: {
          true: {}
        }
      },
      compoundVariants: [
        {
          size: 'xs',
          variant: 'table',
          class: {
            item: 'p-2.5'
          }
        },
        {
          size: 'sm',
          variant: 'table',
          class: {
            item: 'p-3'
          }
        },
        {
          size: 'md',
          variant: 'table',
          class: {
            item: 'p-3.5'
          }
        },
        {
          size: 'lg',
          variant: 'table',
          class: {
            item: 'p-4'
          }
        },
        {
          size: 'xl',
          variant: 'table',
          class: {
            item: 'p-4.5'
          }
        },
        {
          orientation: 'horizontal',
          variant: 'table',
          class: {
            item: 'first-of-type:rounded-s-lg last-of-type:rounded-e-lg',
            fieldset: 'gap-0 -space-x-px'
          }
        },
        {
          orientation: 'vertical',
          variant: 'table',
          class: {
            item: 'first-of-type:rounded-t-lg last-of-type:rounded-b-lg',
            fieldset: 'gap-0 -space-y-px'
          }
        },
        {
          color: 'primary',
          variant: 'table',
          class: {
            item: 'has-data-[state=checked]:bg-primary/10 has-data-[state=checked]:border-primary/50 has-data-[state=checked]:z-[1]'
          }
        },
        {
          color: 'secondary',
          variant: 'table',
          class: {
            item: 'has-data-[state=checked]:bg-secondary/10 has-data-[state=checked]:border-secondary/50 has-data-[state=checked]:z-[1]'
          }
        },
        {
          color: 'success',
          variant: 'table',
          class: {
            item: 'has-data-[state=checked]:bg-success/10 has-data-[state=checked]:border-success/50 has-data-[state=checked]:z-[1]'
          }
        },
        {
          color: 'info',
          variant: 'table',
          class: {
            item: 'has-data-[state=checked]:bg-info/10 has-data-[state=checked]:border-info/50 has-data-[state=checked]:z-[1]'
          }
        },
        {
          color: 'warning',
          variant: 'table',
          class: {
            item: 'has-data-[state=checked]:bg-warning/10 has-data-[state=checked]:border-warning/50 has-data-[state=checked]:z-[1]'
          }
        },
        {
          color: 'error',
          variant: 'table',
          class: {
            item: 'has-data-[state=checked]:bg-error/10 has-data-[state=checked]:border-error/50 has-data-[state=checked]:z-[1]'
          }
        },
        {
          color: 'neutral',
          variant: 'table',
          class: {
            item: 'has-data-[state=checked]:bg-elevated has-data-[state=checked]:border-inverted/50 has-data-[state=checked]:z-[1]'
          }
        },
        {
          variant: 'table',
          disabled: true,
          class: {
            item: 'cursor-not-allowed'
          }
        }
      ],
      defaultVariants: {
        size: 'md',
        variant: 'list',
        color: 'primary'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/CheckboxGroup.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/checkbox-group.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

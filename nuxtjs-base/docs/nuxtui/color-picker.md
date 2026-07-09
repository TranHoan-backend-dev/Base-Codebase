---
title: "ColorPicker"
description: "A component to select a color."
canonical_url: "https://ui.nuxt.com/docs/components/color-picker"
---
# ColorPicker

> A component to select a color.

## Usage

Use the `v-model` directive to control the value of the ColorPicker.

```vue
<script setup lang="ts">
const value = ref("#00C16A")
</script>

<template>
  <UColorPicker v-model="value" />
</template>
```

Use the `default-value` prop to set the initial value when you do not need to control its state.

```vue
<template>
  <UColorPicker default-value="#00BCD4" />
</template>
```

### RGB Format

Use the `format` prop to set `rgb` value of the ColorPicker.

```vue
<script setup lang="ts">
const value = ref("rgb(0, 193, 106)")
</script>

<template>
  <UColorPicker format="rgb" v-model="value" />
</template>
```

### HSL Format

Use the `format` prop to set `hsl` value of the ColorPicker.

```vue
<script setup lang="ts">
const value = ref("hsl(153, 100%, 37.8%)")
</script>

<template>
  <UColorPicker format="hsl" v-model="value" />
</template>
```

### CMYK Format

Use the `format` prop to set `cmyk` value of the ColorPicker.

```vue
<script setup lang="ts">
const value = ref("cmyk(100%, 0%, 45.08%, 24.31%)")
</script>

<template>
  <UColorPicker format="cmyk" v-model="value" />
</template>
```

### CIELab Format

Use the `format` prop to set `lab` value of the ColorPicker.

```vue
<script setup lang="ts">
const value = ref("lab(68.88% -60.41% 32.55%)")
</script>

<template>
  <UColorPicker format="lab" v-model="value" />
</template>
```

### Throttle

Use the `throttle` prop to set the throttle value of the ColorPicker.

```vue
<script setup lang="ts">
const value = ref("#00C16A")
</script>

<template>
  <UColorPicker :throttle="100" v-model="value" />
</template>
```

### Size

Use the `size` prop to set the size of the ColorPicker.

```vue
<template>
  <UColorPicker size="xl" />
</template>
```

### Disabled

Use the `disabled` prop to disable the ColorPicker.

```vue
<template>
  <UColorPicker disabled />
</template>
```

## Examples

### As a color chooser

Use a [Button](/docs/components/button) and a [Popover](/docs/components/popover) component to create a color chooser.

```vue [ColorPickerChooserExample.vue]
<script setup lang="ts">
const color = ref('#00C16A')

const chip = computed(() => ({ backgroundColor: color.value }))
</script>

<template>
  <UPopover>
    <UButton label="Choose color" color="neutral" variant="outline">
      <template #leading>
        <span :style="chip" class="size-3 rounded-full" />
      </template>
    </UButton>

    <template #content>
      <UColorPicker v-model="color" class="p-2" />
    </template>
  </UPopover>
</template>
```

## API

### Props

```ts
/**
 * Props for the ColorPicker component
 */
interface ColorPickerProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  /**
   * Throttle time in ms for the color picker
   * @default "50"
   */
  throttle?: number | undefined;
  /**
   * Disable the color picker
   */
  disabled?: boolean | undefined;
  /**
   * The default value of the color picker
   * @default "\"#FFFFFF\""
   */
  defaultValue?: string | undefined;
  /**
   * Format of the color
   * @default "\"hex\""
   */
  format?: "hex" | "rgb" | "hsl" | "cmyk" | "lab" | undefined;
  size?: "xs" | "sm" | "md" | "lg" | "xl" | undefined;
  ui?: { root?: SlotClass; picker?: SlotClass; selector?: SlotClass; selectorBackground?: SlotClass; selectorThumb?: SlotClass; track?: SlotClass; trackThumb?: SlotClass; } | undefined;
  modelValue?: string | undefined;
}
```

### Emits

```ts
/**
 * Emitted events for the ColorPicker component
 */
interface ColorPickerEmits {
  update:modelValue: (payload: [value: string | undefined]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    colorPicker: {
      slots: {
        root: 'data-[disabled]:opacity-75',
        picker: 'flex gap-4',
        selector: 'rounded-md touch-none',
        selectorBackground: 'w-full h-full relative rounded-md',
        selectorThumb: '-translate-y-1/2 -translate-x-1/2 absolute size-4 ring-2 ring-white rounded-full cursor-pointer data-[disabled]:cursor-not-allowed',
        track: 'w-[8px] relative rounded-md touch-none',
        trackThumb: 'absolute transform -translate-y-1/2 -translate-x-[4px] rtl:translate-x-[4px] size-4 rounded-full ring-2 ring-white cursor-pointer data-[disabled]:cursor-not-allowed'
      },
      variants: {
        size: {
          xs: {
            selector: 'w-38 h-38',
            track: 'h-38'
          },
          sm: {
            selector: 'w-40 h-40',
            track: 'h-40'
          },
          md: {
            selector: 'w-42 h-42',
            track: 'h-42'
          },
          lg: {
            selector: 'w-44 h-44',
            track: 'h-44'
          },
          xl: {
            selector: 'w-46 h-46',
            track: 'h-46'
          }
        }
      },
      compoundVariants: [],
      defaultVariants: {
        size: 'md'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/ColorPicker.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/color-picker.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

---
title: "InputTags"
description: "An input element that displays interactive tags."
canonical_url: "https://ui.nuxt.com/docs/components/input-tags"
---
# InputTags

> An input element that displays interactive tags.

## Usage

Use the `v-model` directive to control the value of the InputTags.

```vue
<script setup lang="ts">
const value = ref([
  "Vue"
])
</script>

<template>
  <UInputTags v-model="value" />
</template>
```

Use the `default-value` prop to set the initial value when you do not need to control its state.

```vue
<template>
  <UInputTags :default-value="[
  'Vue'
]" />
</template>
```

### Placeholder

Use the `placeholder` prop to set a placeholder text.

```vue
<template>
  <UInputTags placeholder="Enter tags..." />
</template>
```

### Max Length

Use the `max-length` prop to set the maximum number of characters allowed in a tag.

```vue
<template>
  <UInputTags :max-length="4" />
</template>
```

### Color

Use the `color` prop to change the ring color when the InputTags is focused.

```vue
<script setup lang="ts">
const value = ref([
  "Vue"
])
</script>

<template>
  <UInputTags v-model="value" color="neutral" highlight />
</template>
```

> [!NOTE]
> 
> The `highlight` prop is used here to show the focus state. It's used internally when a validation error occurs.

### Variants

Use the `variant` prop to change the appearance of the InputTags.

```vue
<script setup lang="ts">
const value = ref([
  "Vue"
])
</script>

<template>
  <UInputTags v-model="value" variant="subtle" color="neutral" :highlight="false" />
</template>
```

### Sizes

Use the `size` prop to adjust the size of the InputTags.

```vue
<script setup lang="ts">
const value = ref([
  "Vue"
])
</script>

<template>
  <UInputTags v-model="value" size="xl" />
</template>
```

### Icon

Use the `icon` prop to show an [Icon](/docs/components/icon) inside the InputTags.

```vue
<script setup lang="ts">
const value = ref([
  "Vue"
])
</script>

<template>
  <UInputTags v-model="value" icon="i-lucide-search" size="md" variant="outline" />
</template>
```

> [!NOTE]
> 
> Use the `leading` and `trailing` props to set the icon position or the `leading-icon` and `trailing-icon` props to set a different icon for each position.

### Avatar

Use the `avatar` prop to show an [Avatar](/docs/components/avatar) inside the InputTags.

```vue
<script setup lang="ts">
const value = ref([
  "Vue"
])
</script>

<template>
  <UInputTags v-model="value" :avatar="{
  src: 'https://github.com/vuejs.png',
  loading: 'lazy'
}" size="md" variant="outline" />
</template>
```

### Delete Icon

Use the `delete-icon` prop to customize the delete [Icon](/docs/components/icon) in the tags. Defaults to `i-lucide-x`.

```vue
<script setup lang="ts">
const value = ref([
  "Vue"
])
</script>

<template>
  <UInputTags v-model="value" delete-icon="i-lucide-trash" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.close` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.close` key.

### Loading

Use the `loading` prop to show a loading icon on the InputTags.

```vue
<script setup lang="ts">
const value = ref([
  "Vue"
])
</script>

<template>
  <UInputTags v-model="value" loading :trailing="false" />
</template>
```

### Loading Icon

Use the `loading-icon` prop to customize the loading icon. Defaults to `i-lucide-loader-circle`.

```vue
<script setup lang="ts">
const value = ref([
  "Vue"
])
</script>

<template>
  <UInputTags v-model="value" loading loading-icon="i-lucide-loader" />
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

### Disabled

Use the `disabled` prop to disable the InputTags.

```vue
<script setup lang="ts">
const value = ref([
  "Vue"
])
</script>

<template>
  <UInputTags v-model="value" disabled />
</template>
```

## Examples

### Within a FormField

You can use the InputTags within a [FormField](/docs/components/form-field) component to display a label, help text, required indicator, etc.

```vue [InputTagsFormFieldExample.vue]
<script setup lang="ts">
const tags = ref(['Vue'])
</script>

<template>
  <UFormField label="Tags" required>
    <UInputTags v-model="tags" placeholder="Enter tags..." />
  </UFormField>
</template>
```

## API

### Props

```ts
/**
 * Props for the InputTags component
 */
interface InputTagsProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  /**
   * The placeholder text when the input is empty.
   */
  placeholder?: string | undefined;
  /**
   * The maximum number of character allowed.
   */
  maxLength?: number | undefined;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  variant?: "outline" | "soft" | "subtle" | "ghost" | "none" | undefined;
  size?: "xs" | "sm" | "md" | "lg" | "xl" | undefined;
  autofocus?: boolean | undefined;
  /**
   * @default "0"
   */
  autofocusDelay?: number | undefined;
  /**
   * The icon displayed to delete a tag.
   */
  deleteIcon?: any;
  /**
   * Highlight the ring color like a focus state.
   */
  highlight?: boolean | undefined;
  /**
   * Keep the mobile text size on all breakpoints.
   */
  fixed?: boolean | undefined;
  ui?: { root?: SlotClass; base?: SlotClass; leading?: SlotClass; leadingIcon?: SlotClass; leadingAvatar?: SlotClass; leadingAvatarSize?: SlotClass; trailing?: SlotClass; trailingIcon?: SlotClass; item?: SlotClass; itemText?: SlotClass; itemDelete?: SlotClass; itemDeleteIcon?: SlotClass; input?: SlotClass; } | undefined;
  /**
   * The controlled value of the tags input. Can be bind as `v-model`.
   */
  modelValue?: T[] | null | undefined;
  /**
   * The value of the tags that should be added. Use when you do not need to control the state of the tags input
   */
  defaultValue?: T[] | undefined;
  /**
   * When `true`, allow adding tags on paste. Work in conjunction with delimiter prop.
   */
  addOnPaste?: boolean | undefined;
  /**
   * When `true` allow adding tags on tab keydown
   */
  addOnTab?: boolean | undefined;
  /**
   * When `true` allow adding tags blur input
   */
  addOnBlur?: boolean | undefined;
  /**
   * When `true`, allow duplicated tags.
   */
  duplicate?: boolean | undefined;
  /**
   * When `true`, prevents the user from interacting with the tags input.
   */
  disabled?: boolean | undefined;
  /**
   * The character or regular expression to trigger the addition of a new tag. Also used to split tags for `@paste` event
   */
  delimiter?: string | RegExp | undefined;
  /**
   * Maximum number of tags.
   */
  max?: number | undefined;
  id?: string | undefined;
  /**
   * Convert the input value to the desired type. Mandatory when using objects as values and using `TagsInputInput`
   */
  convertValue?: ((value: string) => T) | undefined;
  /**
   * Display the value of the tag. Useful when you want to apply modifications to the value like adding a suffix or when using object as values
   */
  displayValue?: ((value: T) => string) | undefined;
  /**
   * The name of the field. Submitted with its owning form as part of a name/value pair.
   */
  name?: string | undefined;
  /**
   * When `true`, indicates that the user must set the value before the owning form can be submitted.
   */
  required?: boolean | undefined;
  /**
   * Display an icon based on the `leading` and `trailing` props.
   */
  icon?: any;
  /**
   * Display an avatar on the left side.
   */
  avatar?: AvatarProps | undefined;
  /**
   * When `true`, the icon will be displayed on the left side.
   */
  leading?: boolean | undefined;
  /**
   * Display an icon on the left side.
   */
  leadingIcon?: any;
  /**
   * When `true`, the icon will be displayed on the right side.
   */
  trailing?: boolean | undefined;
  /**
   * Display an icon on the right side.
   */
  trailingIcon?: any;
  /**
   * When `true`, the loading icon will be displayed.
   */
  loading?: boolean | undefined;
  /**
   * The icon when the `loading` prop is `true`.
   */
  loadingIcon?: any;
  enterKeyHint?: "enter" | "done" | "go" | "next" | "previous" | "search" | "send" | undefined;
  form?: string | undefined;
  formaction?: string | undefined;
  formenctype?: string | undefined;
  formmethod?: string | undefined;
  formnovalidate?: Booleanish | undefined;
  formtarget?: string | undefined;
  list?: string | undefined;
  readonly?: Booleanish | undefined;
  autocomplete?: "on" | "off" | (string & {}) | undefined;
}
```

> [!NOTE]
> See: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#attributes
> 
> This component also supports all native `<input>` HTML attributes.

### Slots

```ts
/**
 * Slots for the InputTags component
 */
interface InputTagsSlots {
  leading(): any;
  default(): any;
  trailing(): any;
  item-text(): any;
  item-delete(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the InputTags component
 */
interface InputTagsEmits {
  change: (payload: [event: Event]) => void;
  blur: (payload: [event: FocusEvent]) => void;
  focus: (payload: [event: FocusEvent]) => void;
  update:modelValue: (payload: [payload: T[]]) => void;
  invalid: (payload: [payload: T]) => void;
  addTag: (payload: [payload: T]) => void;
  removeTag: (payload: [payload: T]) => void;
}
```

### Expose

When accessing the component via a template ref, you can use the following:

<table>
<thead>
  <tr>
    <th>
      Name
    </th>
    
    <th>
      Type
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          inputRef
        </span>
      </code>
    </td>
    
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          Ref
        </span>
        
        <span class="sMK4o">
          <
        </span>
        
        <span class="sBMFI">
          HTMLInputElement
        </span>
        
        <span class="sMK4o">
          |
        </span>
        
        <span class="sBMFI">
          null
        </span>
        
        <span class="sMK4o">
          >
        </span>
      </code>
    </td>
  </tr>
</tbody>
</table>

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    inputTags: {
      slots: {
        root: [
          'relative inline-flex items-center',
          'flex-wrap'
        ],
        base: [
          'rounded-md',
          'transition-colors'
        ],
        leading: 'absolute inset-y-0 start-0 flex items-center',
        leadingIcon: 'shrink-0 text-dimmed',
        leadingAvatar: 'shrink-0',
        leadingAvatarSize: '',
        trailing: 'absolute inset-y-0 end-0 flex items-center',
        trailingIcon: 'shrink-0 text-dimmed',
        item: 'px-1.5 py-0.5 rounded-sm font-medium inline-flex items-center gap-0.5 ring ring-inset ring-accented bg-elevated text-default data-disabled:cursor-not-allowed data-disabled:opacity-75 wrap-anywhere data-[state="active"]:bg-accented',
        itemText: '',
        itemDelete: [
          'inline-flex items-center rounded-xs text-dimmed hover:text-default hover:bg-accented/75 disabled:pointer-events-none',
          'transition-colors'
        ],
        itemDeleteIcon: 'shrink-0',
        input: 'flex-1 border-0 bg-transparent placeholder:text-dimmed focus:outline-none disabled:cursor-not-allowed disabled:opacity-75'
      },
      variants: {
        fieldGroup: {
          horizontal: 'not-only:first:rounded-e-none not-only:last:rounded-s-none not-last:not-first:rounded-none focus-visible:z-[1]',
          vertical: 'not-only:first:rounded-b-none not-only:last:rounded-t-none not-last:not-first:rounded-none focus-visible:z-[1]'
        },
        size: {
          xs: {
            base: 'px-2 py-1 text-sm/4 gap-1',
            leading: 'ps-2',
            trailing: 'pe-2',
            leadingIcon: 'size-4',
            leadingAvatarSize: '3xs',
            trailingIcon: 'size-4',
            item: 'text-[10px]/3',
            itemDeleteIcon: 'size-3'
          },
          sm: {
            base: 'px-2.5 py-1.5 text-sm/4 gap-1.5',
            leading: 'ps-2.5',
            trailing: 'pe-2.5',
            leadingIcon: 'size-4',
            leadingAvatarSize: '3xs',
            trailingIcon: 'size-4',
            item: 'text-[10px]/3',
            itemDeleteIcon: 'size-3'
          },
          md: {
            base: 'px-2.5 py-1.5 text-base/5 gap-1.5',
            leading: 'ps-2.5',
            trailing: 'pe-2.5',
            leadingIcon: 'size-5',
            leadingAvatarSize: '2xs',
            trailingIcon: 'size-5',
            item: 'text-xs',
            itemDeleteIcon: 'size-3.5'
          },
          lg: {
            base: 'px-3 py-2 text-base/5 gap-2',
            leading: 'ps-3',
            trailing: 'pe-3',
            leadingIcon: 'size-5',
            leadingAvatarSize: '2xs',
            trailingIcon: 'size-5',
            item: 'text-xs',
            itemDeleteIcon: 'size-3.5'
          },
          xl: {
            base: 'px-3 py-2 text-base gap-2',
            leading: 'ps-3',
            trailing: 'pe-3',
            leadingIcon: 'size-6',
            leadingAvatarSize: 'xs',
            trailingIcon: 'size-6',
            item: 'text-sm',
            itemDeleteIcon: 'size-4'
          }
        },
        variant: {
          outline: 'text-highlighted bg-default ring ring-inset ring-accented',
          soft: 'text-highlighted bg-elevated/50 hover:bg-elevated has-focus:bg-elevated disabled:bg-elevated/50',
          subtle: 'text-highlighted bg-elevated ring ring-inset ring-accented',
          ghost: 'text-highlighted bg-transparent hover:bg-elevated has-focus:bg-elevated disabled:bg-transparent dark:disabled:bg-transparent',
          none: 'text-highlighted bg-transparent has-focus:outline-none'
        },
        color: {
          primary: '',
          secondary: '',
          success: '',
          info: '',
          warning: '',
          error: '',
          neutral: ''
        },
        leading: {
          true: ''
        },
        trailing: {
          true: ''
        },
        loading: {
          true: ''
        },
        highlight: {
          true: ''
        },
        fixed: {
          false: ''
        },
        type: {
          file: 'file:me-1.5 file:font-medium file:text-muted file:outline-none'
        }
      },
      compoundVariants: [
        {
          color: 'primary',
          variant: [
            'outline',
            'subtle'
          ],
          class: 'outline-primary/25 has-focus-visible:outline-3 has-focus-visible:ring-primary'
        },
        {
          color: 'secondary',
          variant: [
            'outline',
            'subtle'
          ],
          class: 'outline-secondary/25 has-focus-visible:outline-3 has-focus-visible:ring-secondary'
        },
        {
          color: 'success',
          variant: [
            'outline',
            'subtle'
          ],
          class: 'outline-success/25 has-focus-visible:outline-3 has-focus-visible:ring-success'
        },
        {
          color: 'info',
          variant: [
            'outline',
            'subtle'
          ],
          class: 'outline-info/25 has-focus-visible:outline-3 has-focus-visible:ring-info'
        },
        {
          color: 'warning',
          variant: [
            'outline',
            'subtle'
          ],
          class: 'outline-warning/25 has-focus-visible:outline-3 has-focus-visible:ring-warning'
        },
        {
          color: 'error',
          variant: [
            'outline',
            'subtle'
          ],
          class: 'outline-error/25 has-focus-visible:outline-3 has-focus-visible:ring-error'
        },
        {
          color: 'primary',
          variant: [
            'soft',
            'ghost'
          ],
          class: 'outline-primary/25 has-focus-visible:outline-3'
        },
        {
          color: 'secondary',
          variant: [
            'soft',
            'ghost'
          ],
          class: 'outline-secondary/25 has-focus-visible:outline-3'
        },
        {
          color: 'success',
          variant: [
            'soft',
            'ghost'
          ],
          class: 'outline-success/25 has-focus-visible:outline-3'
        },
        {
          color: 'info',
          variant: [
            'soft',
            'ghost'
          ],
          class: 'outline-info/25 has-focus-visible:outline-3'
        },
        {
          color: 'warning',
          variant: [
            'soft',
            'ghost'
          ],
          class: 'outline-warning/25 has-focus-visible:outline-3'
        },
        {
          color: 'error',
          variant: [
            'soft',
            'ghost'
          ],
          class: 'outline-error/25 has-focus-visible:outline-3'
        },
        {
          color: 'primary',
          highlight: true,
          class: 'ring ring-inset ring-primary'
        },
        {
          color: 'secondary',
          highlight: true,
          class: 'ring ring-inset ring-secondary'
        },
        {
          color: 'success',
          highlight: true,
          class: 'ring ring-inset ring-success'
        },
        {
          color: 'info',
          highlight: true,
          class: 'ring ring-inset ring-info'
        },
        {
          color: 'warning',
          highlight: true,
          class: 'ring ring-inset ring-warning'
        },
        {
          color: 'error',
          highlight: true,
          class: 'ring ring-inset ring-error'
        },
        {
          color: 'neutral',
          variant: [
            'outline',
            'subtle'
          ],
          class: 'outline-inverted/25 has-focus-visible:outline-3 has-focus-visible:ring-inverted'
        },
        {
          color: 'neutral',
          variant: [
            'soft',
            'ghost'
          ],
          class: 'outline-inverted/25 has-focus-visible:outline-3'
        },
        {
          color: 'neutral',
          highlight: true,
          class: 'ring ring-inset ring-inverted'
        },
        {
          leading: true,
          size: 'xs',
          class: 'ps-7'
        },
        {
          leading: true,
          size: 'sm',
          class: 'ps-8'
        },
        {
          leading: true,
          size: 'md',
          class: 'ps-9'
        },
        {
          leading: true,
          size: 'lg',
          class: 'ps-10'
        },
        {
          leading: true,
          size: 'xl',
          class: 'ps-11'
        },
        {
          trailing: true,
          size: 'xs',
          class: 'pe-7'
        },
        {
          trailing: true,
          size: 'sm',
          class: 'pe-8'
        },
        {
          trailing: true,
          size: 'md',
          class: 'pe-9'
        },
        {
          trailing: true,
          size: 'lg',
          class: 'pe-10'
        },
        {
          trailing: true,
          size: 'xl',
          class: 'pe-11'
        },
        {
          loading: true,
          leading: true,
          class: {
            leadingIcon: 'animate-spin'
          }
        },
        {
          loading: true,
          leading: false,
          trailing: true,
          class: {
            trailingIcon: 'animate-spin'
          }
        },
        {
          fixed: false,
          size: 'xs',
          class: 'md:text-xs'
        },
        {
          fixed: false,
          size: 'sm',
          class: 'md:text-xs'
        },
        {
          fixed: false,
          size: 'md',
          class: 'md:text-sm'
        },
        {
          fixed: false,
          size: 'lg',
          class: 'md:text-sm'
        }
      ],
      defaultVariants: {
        size: 'md',
        color: 'primary',
        variant: 'outline'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/InputTags.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/input-tags.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

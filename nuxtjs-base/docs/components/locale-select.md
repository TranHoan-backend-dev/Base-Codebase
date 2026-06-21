---
title: "LocaleSelect"
description: "A Select to switch between locales."
canonical_url: "https://ui.nuxt.com/docs/components/locale-select"
---
# LocaleSelect

> A Select to switch between locales.

## Usage

The LocaleSelect component extends the [SelectMenu](/docs/components/select-menu) component, so you can pass any property such as `color`, `variant`, `size`, etc.

**Nuxt:**

> [!NOTE]
> See: /docs/getting-started/integrations/i18n/nuxt
> 
> This component is meant to be used with the **i18n** system. Learn more about it in the guide.

**Vue:**

> [!NOTE]
> See: /docs/getting-started/integrations/i18n/vue
> 
> This component is meant to be used with the **i18n** system. Learn more about it in the guide.

> [!WARNING]
> 
> The flags are displayed using Unicode characters. This may result in a different display, e.g. Microsoft Edge under Windows displays the ISO 3166-1 alpha-2 code instead, as no flag icons are shipped with the OS fonts.

### Locales

Use the `locales` prop with an array of locales from `@nuxt/ui/locale`.

```vue [LocaleSelectExample.vue]
<script setup lang="ts">
import * as locales from '@nuxt/ui/locale'

const locale = ref('en')
</script>

<template>
  <ULocaleSelect v-model="locale" :locales="Object.values(locales)" class="w-48" />
</template>
```

You can pass only the locales you need in your application:

```vue
<script setup lang="ts">
import { en, es, fr } from '@nuxt/ui/locale'

const locale = ref('en')
</script>

<template>
  <ULocaleSelect v-model="locale" :locales="[en, es, fr]" />
</template>
```

### Dynamic locale

**Nuxt:**

You can use it with Nuxt i18n:

```vue
<script setup lang="ts">
import * as locales from '@nuxt/ui/locale'

const { locale, setLocale } = useI18n()
</script>

<template>
  <ULocaleSelect
    :model-value="locale"
    :locales="Object.values(locales)"
    @update:model-value="setLocale($event)"
  />
</template>
```

**Vue:**

You can use it with Vue i18n:

```vue
<script setup lang="ts">
import { useI18n } from 'vue-i18n'
import * as locales from '@nuxt/ui/locale'

const { locale, setLocale } = useI18n()
</script>

<template>
  <ULocaleSelect
    :model-value="locale"
    :locales="Object.values(locales)"
    @update:model-value="setLocale($event)"
  />
</template>
```

## API

### Props

```ts
/**
 * Props for the LocaleSelect component
 */
interface LocaleSelectProps {
  modelValue: string;
  locales?: Locale<any>[] | undefined;
  /**
   * The name of the field. Submitted with its owning form as part of a name/value pair.
   */
  name?: string | undefined;
  /**
   * Display an avatar on the left side.
   */
  avatar?: AvatarProps | undefined;
  size?: "md" | "xs" | "sm" | "lg" | "xl" | undefined;
  ui?: { base?: SlotClass; leading?: SlotClass; leadingIcon?: SlotClass; leadingAvatar?: SlotClass; leadingAvatarSize?: SlotClass; trailing?: SlotClass; trailingIcon?: SlotClass; value?: SlotClass; placeholder?: SlotClass; arrow?: SlotClass; content?: SlotClass; viewport?: SlotClass; group?: SlotClass; empty?: SlotClass; label?: SlotClass; separator?: SlotClass; item?: SlotClass; itemLeadingIcon?: SlotClass; itemLeadingAvatar?: SlotClass; itemLeadingAvatarSize?: SlotClass; itemLeadingChip?: SlotClass; itemLeadingChipSize?: SlotClass; itemTrailing?: SlotClass; itemTrailingIcon?: SlotClass; itemWrapper?: SlotClass; itemLabel?: SlotClass; itemDescription?: SlotClass; input?: SlotClass; focusScope?: SlotClass; trailingClear?: SlotClass; } | undefined;
  /**
   * When `true`, the loading icon will be displayed.
   */
  loading?: boolean | undefined;
  /**
   * Display an icon based on the `leading` and `trailing` props.
   */
  icon?: any;
  color?: "error" | "neutral" | "primary" | "secondary" | "success" | "info" | "warning" | undefined;
  autofocus?: boolean | undefined;
  /**
   * When `true`, prevents the user from interacting with listbox
   */
  disabled?: boolean | undefined;
  form?: string | undefined;
  formaction?: string | undefined;
  formenctype?: string | undefined;
  formmethod?: string | undefined;
  formnovalidate?: Booleanish | undefined;
  formtarget?: string | undefined;
  /**
   * The controlled open state of the Combobox. Can be binded with `v-model:open`.
   */
  open?: boolean | undefined;
  /**
   * The open state of the combobox when it is initially rendered. <br> Use when you do not need to control its open state.
   */
  defaultOpen?: boolean | undefined;
  /**
   * Whether to reset the searchTerm when the Combobox input blurred
   */
  resetSearchTermOnBlur?: boolean | undefined;
  /**
   * Whether to reset the searchTerm when the Combobox value is selected
   */
  resetSearchTermOnSelect?: boolean | undefined;
  /**
   * When `true` the `modelValue` will be reset to `null` (or `[]` if `multiple`)
   */
  resetModelValueOnClear?: boolean | undefined;
  /**
   * When `true`, hover over item will trigger highlight
   */
  highlightOnHover?: boolean | undefined;
  /**
   * Use this to compare objects by a particular field, or pass your own comparison function for complete control over how objects are compared.
   */
  by?: string | ((a: Locale<any>[], b: Locale<any>[]) => boolean) | undefined;
  /**
   * The value of the SelectMenu when initially rendered. Use when you do not need to control the state of the SelectMenu.
   */
  defaultValue?: string | undefined;
  /**
   * Whether multiple options can be selected or not.
   */
  multiple?: false | undefined;
  required?: boolean | undefined;
  id?: string | undefined;
  /**
   * The placeholder text when the select is empty.
   */
  placeholder?: string | undefined;
  /**
   * Whether to display the search input or not.
   * Can be an object to pass additional props to the input.
   * `{ placeholder: 'Search...', variant: 'none' }`{lang="ts-type"}
   * @default "false"
   */
  searchInput?: boolean | Omit<InputProps<AcceptableValue, ModelModifiers>, "modelValue" | "defaultValue"> | undefined;
  variant?: "outline" | "soft" | "subtle" | "ghost" | "none" | undefined;
  /**
   * The icon displayed to open the menu.
   */
  trailingIcon?: any;
  /**
   * The icon displayed when an item is selected.
   */
  selectedIcon?: any;
  /**
   * Display a clear button to reset the model value.
   * Can be an object to pass additional props to the Button.
   */
  clear?: false | (false & Partial<Omit<ButtonProps, LinkPropsKeys>>) | undefined;
  /**
   * The icon displayed in the clear button.
   */
  clearIcon?: any;
  /**
   * The content of the menu.
   */
  content?: (Omit<ComboboxContentProps, "as" | "asChild" | "forceMount"> & Partial<EmitsToProps<DismissableLayerEmits>>) | undefined;
  /**
   * Display an arrow alongside the menu.
   * `{ rounded: true }`{lang="ts-type"}
   */
  arrow?: boolean | Omit<ComboboxArrowProps, "as" | "asChild"> | undefined;
  /**
   * Render the menu in a portal.
   */
  portal?: string | boolean | HTMLElement | undefined;
  /**
   * Enable virtualization for large lists.
   * Note: when enabled, all groups are flattened into a single list due to a limitation of Reka UI (https://github.com/unovue/reka-ui/issues/1885).
   */
  virtualize?: boolean | { overscan?: number | undefined; estimateSize?: number | ((index: number) => number) | undefined; } | undefined;
  /**
   * When `items` is an array of objects, select the field to use as the value instead of the object itself.
   * @default "\"code\""
   */
  valueKey?: "code" | undefined;
  /**
   * When `items` is an array of objects, select the field to use as the label.
   * @default "\"name\""
   */
  labelKey?: GetItemKeys<Locale<any>[]> | undefined;
  /**
   * When `items` is an array of objects, select the field to use as the description.
   */
  descriptionKey?: GetItemKeys<Locale<any>[]> | undefined;
  modelModifiers?: Omit<ModelModifiers, "lazy"> | undefined;
  /**
   * Highlight the ring color like a focus state.
   */
  highlight?: boolean | undefined;
  /**
   * Determines if custom user input that does not exist in options can be added.
   */
  createItem?: boolean | "always" | { position?: "top" | "bottom" | undefined; when?: "empty" | "always" | undefined; } | undefined;
  /**
   * Fields to filter items by.
   */
  filterFields?: string[] | undefined;
  /**
   * When `true`, disable the default filters, useful for custom filtering (useAsyncData, useFetch, etc.).
   */
  ignoreFilter?: boolean | undefined;
  autofocusDelay?: number | undefined;
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
   * The icon when the `loading` prop is `true`.
   */
  loadingIcon?: any;
}
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/locale/LocaleSelect.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/locale/locale-select.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

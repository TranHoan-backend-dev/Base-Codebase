---
title: "InputDate"
description: "An input component for date selection."
canonical_url: "https://ui.nuxt.com/docs/components/input-date"
---
# InputDate

> An input component for date selection.

## Usage

Use the `v-model` directive to control the selected date.

```vue
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const value = shallowRef(new CalendarDate(2022, 2, 3))
</script>

<template>
  <UInputDate v-model="value" />
</template>
```

Use the `default-value` prop to set the initial value when you do not need to control its state.

```vue
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const defaultValue = shallowRef(new CalendarDate(2022, 2, 6))
</script>

<template>
  <UInputDate :default-value="defaultValue" />
</template>
```

**Nuxt:**

> [!NOTE]
> See: /docs/getting-started/integrations/i18n/nuxt#locale
> 
> This component uses the `@internationalized/date` package for locale-aware formatting. The date format is determined by the `locale` prop of the App component.

**Vue:**

> [!NOTE]
> See: /docs/getting-started/integrations/i18n/vue#locale
> 
> This component uses the `@internationalized/date` package for locale-aware formatting. The date format is determined by the `locale` prop of the App component.

### Range

Use the `range` prop to select a range of dates.

```vue
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const value = shallowRef({ start: new CalendarDate(2022, 2, 3), end: new CalendarDate(2022, 2, 20) })
</script>

<template>
  <UInputDate range v-model="value" />
</template>
```

### Color

Use the `color` prop to change the color of the InputDate.

```vue
<template>
  <UInputDate color="neutral" highlight />
</template>
```

### Variant

Use the `variant` prop to change the variant of the InputDate.

```vue
<template>
  <UInputDate variant="subtle" />
</template>
```

### Size

Use the `size` prop to change the size of the InputDate.

```vue
<template>
  <UInputDate size="xl" />
</template>
```

### Icon

Use the `icon` prop to show an [Icon](/docs/components/icon) inside the InputDate.

```vue
<template>
  <UInputDate icon="i-lucide-calendar" />
</template>
```

> [!NOTE]
> 
> Use the `leading` and `trailing` props to set the icon position or the `leading-icon` and `trailing-icon` props to set a different icon for each position.

### Separator Icon

Use the `separator-icon` prop to change the [Icon](/docs/components/icon) of the range separator. Defaults to `i-lucide-minus`.

```vue
<template>
  <UInputDate range separator-icon="i-lucide-arrow-right" />
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

### Avatar

Use the `avatar` prop to show an [Avatar](/docs/components/avatar) inside the InputDate.

```vue
<template>
  <UInputDate :avatar="{
  src: 'https://github.com/vuejs.png',
  loading: 'lazy'
}" size="md" variant="outline" />
</template>
```

### Disabled

Use the `disabled` prop to disable the InputDate.

```vue
<template>
  <UInputDate disabled />
</template>
```

## Examples

### With unavailable dates

Use the `is-date-unavailable` prop with a function to mark specific dates as unavailable.

```vue [InputDateUnavailableDatesExample.vue]
<script setup lang="ts">
import type { DateValue } from '@internationalized/date'
import { CalendarDate } from '@internationalized/date'

const modelValue = shallowRef({
  start: new CalendarDate(2022, 1, 1),
  end: new CalendarDate(2022, 1, 9)
})

const isDateUnavailable = (date: DateValue) => {
  return date.day >= 10 && date.day <= 16
}
</script>

<template>
  <UInputDate v-model="modelValue" :is-date-unavailable="isDateUnavailable" range />
</template>
```

### With min/max dates

Use the `min-value` and `max-value` props to limit the dates.

```vue [InputDateMinMaxDatesExample.vue]
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const modelValue = shallowRef(new CalendarDate(2023, 9, 10))
const minDate = new CalendarDate(2023, 9, 1)
const maxDate = new CalendarDate(2023, 9, 30)
</script>

<template>
  <UInputDate v-model="modelValue" :min-value="minDate" :max-value="maxDate" />
</template>
```

### As a date picker

Use a [Calendar](/docs/components/calendar) and a [Popover](/docs/components/popover) component to create a date picker.

```vue [InputDateDatePickerExample.vue]
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const inputDate = useTemplateRef('inputDate')

const modelValue = shallowRef(new CalendarDate(2022, 1, 10))
</script>

<template>
  <UInputDate ref="inputDate" v-model="modelValue">
    <template #trailing>
      <UPopover :reference="inputDate?.inputsRef[3]?.$el">
        <UButton
          color="neutral"
          variant="link"
          size="sm"
          icon="i-lucide-calendar"
          aria-label="Select a date"
          class="px-0"
        />

        <template #content>
          <UCalendar v-model="modelValue" class="p-2" />
        </template>
      </UPopover>
    </template>
  </UInputDate>
</template>
```

### As a date range picker

Use a [Calendar](/docs/components/calendar) and a [Popover](/docs/components/popover) component to create a date range picker.

```vue [InputDateDateRangePickerExample.vue]
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const inputDate = useTemplateRef('inputDate')

const modelValue = shallowRef({
  start: new CalendarDate(2022, 1, 10),
  end: new CalendarDate(2022, 1, 20)
})
</script>

<template>
  <UInputDate ref="inputDate" v-model="modelValue" range>
    <template #trailing>
      <UPopover :reference="inputDate?.inputsRef[0]?.$el">
        <UButton
          color="neutral"
          variant="link"
          size="sm"
          icon="i-lucide-calendar"
          aria-label="Select a date range"
          class="px-0"
        />

        <template #content>
          <UCalendar v-model="modelValue" class="p-2" :number-of-months="2" range />
        </template>
      </UPopover>
    </template>
  </UInputDate>
</template>
```

## API

### Props

```ts
/**
 * Props for the InputDate component
 */
interface InputDateProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  variant?: "outline" | "soft" | "subtle" | "ghost" | "none" | undefined;
  size?: "xs" | "sm" | "md" | "lg" | "xl" | undefined;
  /**
   * Highlight the ring color like a focus state.
   */
  highlight?: boolean | undefined;
  /**
   * Keep the mobile text size on all breakpoints.
   */
  fixed?: boolean | undefined;
  autofocus?: boolean | undefined;
  /**
   * @default "0"
   */
  autofocusDelay?: number | undefined;
  /**
   * The icon to use as a range separator.
   */
  separatorIcon?: any;
  /**
   * Whether or not a range of dates can be selected
   */
  range?: R | undefined;
  defaultValue?: CalendarDate | CalendarDateTime | ZonedDateTime | DateRange;
  modelValue?: null | CalendarDate | CalendarDateTime | ZonedDateTime | DateRange;
  ui?: { base?: SlotClass; leading?: SlotClass; leadingIcon?: SlotClass; leadingAvatar?: SlotClass; leadingAvatarSize?: SlotClass; trailing?: SlotClass; trailingIcon?: SlotClass; segment?: SlotClass; separatorIcon?: SlotClass; } | undefined;
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
  defaultPlaceholder?: CalendarDate | CalendarDateTime | ZonedDateTime;
  placeholder?: CalendarDate | CalendarDateTime | ZonedDateTime;
  /**
   * The hour cycle used for formatting times. Defaults to the local preference
   */
  hourCycle?: HourCycle;
  /**
   * The stepping interval for the time fields. Defaults to `1`.
   */
  step?: DateStep | undefined;
  /**
   * The granularity to use for formatting times. Defaults to day if a CalendarDate is provided, otherwise defaults to minute. The field will render segments for each part of the date up to and including the specified granularity
   */
  granularity?: Granularity | undefined;
  /**
   * Whether or not to hide the time zone segment of the field
   */
  hideTimeZone?: boolean | undefined;
  maxValue?: CalendarDate | CalendarDateTime | ZonedDateTime;
  minValue?: CalendarDate | CalendarDateTime | ZonedDateTime;
  /**
   * The locale to use for formatting dates
   */
  locale?: string | undefined;
  /**
   * Whether or not the date field is disabled
   */
  disabled?: boolean | undefined;
  /**
   * Whether or not the date field is readonly
   */
  readonly?: boolean | undefined;
  /**
   * A function that returns whether or not a date is unavailable
   */
  isDateUnavailable?: Matcher | undefined;
  /**
   * Id of the element
   */
  id?: string | undefined;
  /**
   * The name of the field. Submitted with its owning form as part of a name/value pair.
   */
  name?: string | undefined;
  /**
   * When `true`, indicates that the user must set the value before the owning form can be submitted.
   */
  required?: boolean | undefined;
}
```

### Slots

```ts
/**
 * Slots for the InputDate component
 */
interface InputDateSlots {
  leading(): any;
  default(): any;
  trailing(): any;
  separator(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the InputDate component
 */
interface InputDateEmits {
  update:modelValue: (payload: [value: InputDateModelValue<R>]) => void;
  update:placeholder: (payload: [date: DateValue] & [date: DateValue]) => void;
  change: (payload: [event: Event]) => void;
  blur: (payload: [event: FocusEvent]) => void;
  focus: (payload: [event: FocusEvent]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    inputDate: {
      slots: {
        base: [
          'group relative inline-flex items-center rounded-md select-none',
          'transition-colors'
        ],
        leading: 'absolute inset-y-0 start-0 flex items-center',
        leadingIcon: 'shrink-0 text-dimmed',
        leadingAvatar: 'shrink-0',
        leadingAvatarSize: '',
        trailing: 'absolute inset-y-0 end-0 flex items-center',
        trailingIcon: 'shrink-0 text-dimmed',
        segment: [
          'rounded text-center outline-hidden data-placeholder:text-dimmed data-[segment=literal]:text-muted data-invalid:text-error data-disabled:cursor-not-allowed data-disabled:opacity-75',
          'transition-colors'
        ],
        separatorIcon: 'shrink-0 size-4 text-muted'
      },
      variants: {
        fieldGroup: {
          horizontal: 'not-only:first:rounded-e-none not-only:last:rounded-s-none not-last:not-first:rounded-none focus-visible:z-[1]',
          vertical: 'not-only:first:rounded-b-none not-only:last:rounded-t-none not-last:not-first:rounded-none focus-visible:z-[1]'
        },
        size: {
          xs: {
            base: [
              'px-2 py-1 text-sm/4 gap-1',
              'gap-0.25'
            ],
            leading: 'ps-2',
            trailing: 'pe-2',
            leadingIcon: 'size-4',
            leadingAvatarSize: '3xs',
            trailingIcon: 'size-4',
            segment: 'data-[segment=day]:w-8 data-[segment=month]:w-8 data-[segment=year]:w-10'
          },
          sm: {
            base: [
              'px-2.5 py-1.5 text-sm/4 gap-1.5',
              'gap-0.5'
            ],
            leading: 'ps-2.5',
            trailing: 'pe-2.5',
            leadingIcon: 'size-4',
            leadingAvatarSize: '3xs',
            trailingIcon: 'size-4',
            segment: 'data-[segment=day]:w-8 data-[segment=month]:w-8 data-[segment=year]:w-10'
          },
          md: {
            base: [
              'px-2.5 py-1.5 text-base/5 gap-1.5',
              'gap-0.5'
            ],
            leading: 'ps-2.5',
            trailing: 'pe-2.5',
            leadingIcon: 'size-5',
            leadingAvatarSize: '2xs',
            trailingIcon: 'size-5',
            segment: 'data-[segment=day]:w-9 data-[segment=month]:w-9 data-[segment=year]:w-11'
          },
          lg: {
            base: [
              'px-3 py-2 text-base/5 gap-2',
              'gap-0.75'
            ],
            leading: 'ps-3',
            trailing: 'pe-3',
            leadingIcon: 'size-5',
            leadingAvatarSize: '2xs',
            trailingIcon: 'size-5',
            segment: 'data-[segment=day]:w-9 data-[segment=month]:w-9 data-[segment=year]:w-11'
          },
          xl: {
            base: [
              'px-3 py-2 text-base gap-2',
              'gap-0.75'
            ],
            leading: 'ps-3',
            trailing: 'pe-3',
            leadingIcon: 'size-6',
            leadingAvatarSize: 'xs',
            trailingIcon: 'size-6',
            segment: 'data-[segment=day]:w-10 data-[segment=month]:w-10 data-[segment=year]:w-12'
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
        },
        {
          variant: 'outline',
          class: {
            segment: 'focus:bg-elevated'
          }
        },
        {
          variant: 'soft',
          class: {
            segment: 'focus:bg-accented/50 group-hover:focus:bg-accented'
          }
        },
        {
          variant: 'subtle',
          class: {
            segment: 'focus:bg-accented'
          }
        },
        {
          variant: 'ghost',
          class: {
            segment: 'focus:bg-elevated group-hover:focus:bg-accented'
          }
        },
        {
          variant: 'none',
          class: {
            segment: 'focus:bg-elevated'
          }
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

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/InputDate.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/input-date.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

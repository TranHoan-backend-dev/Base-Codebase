---
title: "Calendar"
description: "A calendar component for selecting single dates, multiple dates or date ranges."
canonical_url: "https://ui.nuxt.com/docs/components/calendar"
---
# Calendar

> A calendar component for selecting single dates, multiple dates or date ranges.

## Usage

Use the `v-model` directive to control the selected date.

```vue
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const value = shallowRef(new CalendarDate(2022, 2, 3))
</script>

<template>
  <UCalendar v-model="value" />
</template>
```

Use the `default-value` prop to set the initial value when you do not need to control its state.

```vue
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const defaultValue = shallowRef(new CalendarDate(2022, 2, 6))
</script>

<template>
  <UCalendar :default-value="defaultValue" />
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

### Type `4.9+`

Use the `type` prop to change what the calendar selects. Defaults to `date`.

When using `date`, click the heading to switch from the day view to a month then year view for quick navigation, then drill back down to pick a date.

```vue
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const value = shallowRef(new CalendarDate(2022, 2, 1))
</script>

<template>
  <UCalendar type="month" v-model="value" />
</template>
```

Use `type="year"` to render a standalone year picker.

```vue
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const value = shallowRef(new CalendarDate(2022, 1, 1))
</script>

<template>
  <UCalendar type="year" v-model="value" />
</template>
```

### Multiple

Use the `multiple` prop to allow multiple selections.

```vue
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const value = shallowRef([new CalendarDate(2022, 2, 4), new CalendarDate(2022, 2, 6), new CalendarDate(2022, 2, 8)])
</script>

<template>
  <UCalendar multiple v-model="value" />
</template>
```

### Range

Use the `range` prop to select a range of dates.

```vue
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const value = shallowRef({ start: new CalendarDate(2022, 2, 3), end: new CalendarDate(2022, 2, 20) })
</script>

<template>
  <UCalendar range v-model="value" />
</template>
```

The `range` prop also works with `type="month"` and `type="year"`, letting you select a range of months or years.

```vue
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const value = shallowRef({ start: new CalendarDate(2022, 2, 1), end: new CalendarDate(2022, 6, 1) })
</script>

<template>
  <UCalendar type="month" range v-model="value" />
</template>
```

### Number Of Months

Use the `numberOfMonths` prop to change the number of months in the calendar.

```vue
<template>
  <UCalendar :number-of-months="3" />
</template>
```

### Month Controls

Use the `month-controls` prop to show the month controls. Defaults to `true`.

```vue
<template>
  <UCalendar :month-controls="false" />
</template>
```

Use the `prev-month` and `next-month` props to override the month buttons.

```vue
<template>
  <UCalendar :prev-month="{
  color: 'primary',
  variant: 'soft'
}" :next-month="{
  color: 'primary',
  variant: 'soft'
}" />
</template>
```

### Year Controls

Use the `year-controls` prop to show the year controls. Defaults to `true`.

```vue
<template>
  <UCalendar :year-controls="false" />
</template>
```

Use the `prev-year` and `next-year` props to override the year buttons.

```vue
<template>
  <UCalendar :prev-year="{
  color: 'primary',
  variant: 'soft'
}" :next-year="{
  color: 'primary',
  variant: 'soft'
}" />
</template>
```

### View Control `4.9+`

Use the `view-control` prop to make the heading a button that switches between the day, month and year views. Defaults to `true`.

```vue
<template>
  <UCalendar :view-control="false" />
</template>
```

Set the `view-control` prop to an object to override the heading button.

```vue
<template>
  <UCalendar :view-control="{
  color: 'primary',
  variant: 'soft'
}" />
</template>
```

### Fixed Weeks

Use the `fixed-weeks` prop to display the calendar with fixed weeks.

```vue
<template>
  <UCalendar :fixed-weeks="false" />
</template>
```

### Week Numbers `4.4+`

Use the `week-numbers` prop to display week numbers in the calendar.

```vue
<template>
  <UCalendar week-numbers fixed-weeks />
</template>
```

### Color

Use the `color` prop to change the color of the calendar.

```vue
<template>
  <UCalendar color="neutral" />
</template>
```

### Variant

Use the `variant` prop to change the variant of the calendar.

```vue
<template>
  <UCalendar variant="subtle" />
</template>
```

### Size

Use the `size` prop to change the size of the calendar.

```vue
<template>
  <UCalendar size="xl" />
</template>
```

### Disabled

Use the `disabled` prop to disable the calendar.

```vue
<template>
  <UCalendar disabled />
</template>
```

## Examples

### With chip events

Use the [Chip](/docs/components/chip) component to add events to specific days.

```vue [CalendarEventsExample.vue]
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const modelValue = shallowRef(new CalendarDate(2022, 1, 10))

function getColorByDate(date: Date) {
  const isWeekend = date.getDay() % 6 == 0
  const isDayMeeting = date.getDay() % 3 == 0

  if (isWeekend) {
    return undefined
  }

  if (isDayMeeting) {
    return 'error'
  }

  return 'success'
}
</script>

<template>
  <UCalendar v-model="modelValue">
    <template #day="{ day }">
      <UChip :show="!!getColorByDate(day.toDate('UTC'))" :color="getColorByDate(day.toDate('UTC'))" size="2xs">
        {{ day.day }}
      </UChip>
    </template>
  </UCalendar>
</template>
```

### With disabled dates

Use the `is-date-disabled` prop with a function to mark specific dates as disabled. When using `type="month"` or `type="year"`, use the `is-month-disabled` or `is-year-disabled` prop instead.

```vue [CalendarDisabledDatesExample.vue]
<script setup lang="ts">
import type { DateValue } from '@internationalized/date'
import { CalendarDate } from '@internationalized/date'

const modelValue = shallowRef({
  start: new CalendarDate(2022, 1, 1),
  end: new CalendarDate(2022, 1, 9)
})

const isDateDisabled = (date: DateValue) => {
  return date.day >= 10 && date.day <= 16
}
</script>

<template>
  <UCalendar v-model="modelValue" :is-date-disabled="isDateDisabled" range />
</template>
```

### With unavailable dates

Use the `is-date-unavailable` prop with a function to mark specific dates as unavailable. When using `type="month"` or `type="year"`, use the `is-month-unavailable` or `is-year-unavailable` prop instead.

```vue [CalendarUnavailableDatesExample.vue]
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
  <UCalendar v-model="modelValue" :is-date-unavailable="isDateUnavailable" range />
</template>
```

### With min/max dates

Use the `min-value` and `max-value` props to limit the dates.

```vue [CalendarMinMaxDatesExample.vue]
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const modelValue = shallowRef(new CalendarDate(2023, 9, 10))
const minDate = new CalendarDate(2023, 9, 1)
const maxDate = new CalendarDate(2023, 9, 30)
</script>

<template>
  <UCalendar v-model="modelValue" :min-value="minDate" :max-value="maxDate" />
</template>
```

### With other calendar systems

You can use other calenders from `@internationalized/date` to implement a different calendar system.

```vue [CalendarOtherSystemExample.vue]
<script lang="ts" setup>
import { CalendarDate, HebrewCalendar } from '@internationalized/date'

const hebrewDate = shallowRef(new CalendarDate(new HebrewCalendar(), 5781, 1, 1))
</script>

<template>
  <UCalendar v-model="hebrewDate" />
</template>
```

> [!NOTE]
> See: https://react-spectrum.adobe.com/internationalized/date/Calendar.html#implementations
> 
> You can check all the available calendars on `@internationalized/date` docs.

### With external controls

You can control the calendar with external controls by manipulating the date passed in the `v-model`.

```vue [CalendarExternalControlsExample.vue]
<script setup lang="ts">
import { CalendarDate } from '@internationalized/date'

const date = shallowRef(new CalendarDate(2025, 4, 2))
</script>

<template>
  <div class="flex flex-col gap-4">
    <UCalendar v-model="date" :month-controls="false" :year-controls="false" />

    <div class="flex justify-between gap-4">
      <UButton color="neutral" variant="outline" @click="date = date.subtract({ months: 1 })">
        Prev
      </UButton>

      <UButton color="neutral" variant="outline" @click="date = date.add({ months: 1 })">
        Next
      </UButton>
    </div>
  </div>
</template>
```

### With today's date

Use the `today` function from `@internationalized/date` with `getLocalTimeZone` to set the value to the current date.

```vue [CalendarTodayExample.vue]
<script setup lang="ts">
import { getLocalTimeZone, today } from '@internationalized/date'

const date = shallowRef(today(getLocalTimeZone()))
</script>

<template>
  <div class="flex flex-col gap-4">
    <UCalendar v-model="date" />

    <UButton color="neutral" variant="outline" class="justify-center" @click="date = today(getLocalTimeZone())">
      Today
    </UButton>
  </div>
</template>
```

### As a date picker

Use a [Button](/docs/components/button) and a [Popover](/docs/components/popover) component to create a date picker.

```vue [CalendarDatePickerExample.vue]
<script setup lang="ts">
import { CalendarDate, DateFormatter, getLocalTimeZone } from '@internationalized/date'

const df = new DateFormatter('en-US', {
  dateStyle: 'medium'
})

const modelValue = shallowRef(new CalendarDate(2022, 1, 10))
</script>

<template>
  <UPopover>
    <UButton color="neutral" variant="subtle" icon="i-lucide-calendar">
      {{ modelValue ? df.format(modelValue.toDate(getLocalTimeZone())) : 'Select a date' }}
    </UButton>

    <template #content>
      <UCalendar v-model="modelValue" class="p-2" />
    </template>
  </UPopover>
</template>
```

### As a date range picker

Use a [Button](/docs/components/button) and a [Popover](/docs/components/popover) component to create a date range picker with preset ranges.

```vue [CalendarDateRangePickerExample.vue]
<script setup lang="ts">
import { breakpointsTailwind, useBreakpoints } from '@vueuse/core'
import { DateFormatter, getLocalTimeZone, today } from '@internationalized/date'

const df = new DateFormatter('en-US', { dateStyle: 'medium' })
const tz = getLocalTimeZone()
const breakpoints = useBreakpoints(breakpointsTailwind)
const isDesktop = breakpoints.greaterOrEqual('sm')

const ranges = [
  { label: 'Last 7 days', days: 7 },
  { label: 'Last 14 days', days: 14 },
  { label: 'Last 30 days', days: 30 },
  { label: 'Last 3 months', months: 3 },
  { label: 'Last 6 months', months: 6 },
  { label: 'Last year', years: 1 }
]

const initialEnd = today(tz)
const modelValue = shallowRef({
  start: initialEnd.subtract({ days: 14 }),
  end: initialEnd
})

const label = computed(() => {
  const { start, end } = modelValue.value
  if (!start) return 'Pick a date'
  if (!end) return df.format(start.toDate(tz))
  return `${df.format(start.toDate(tz))} - ${df.format(end.toDate(tz))}`
})

function computeStart(range: typeof ranges[number]) {
  const end = today(tz)
  return { start: end.subtract({ days: range.days, months: range.months, years: range.years }), end }
}

function isRangeSelected(range: typeof ranges[number]) {
  if (!modelValue.value?.start || !modelValue.value?.end) return false
  const { start, end } = computeStart(range)
  return modelValue.value.start.compare(start) === 0 && modelValue.value.end.compare(end) === 0
}

function selectRange(range: typeof ranges[number]) {
  modelValue.value = computeStart(range)
}
</script>

<template>
  <UPopover :content="{ align: 'center' }">
    <UButton color="neutral" variant="subtle" icon="i-lucide-calendar">
      {{ label }}
    </UButton>

    <template #content>
      <div class="flex items-stretch divide-x divide-(--ui-border)">
        <div class="hidden sm:flex flex-col justify-center py-2">
          <UButton
            v-for="(range, index) in ranges"
            :key="index"
            :label="range.label"
            color="neutral"
            variant="ghost"
            class="rounded-none px-4"
            :class="[isRangeSelected(range) ? 'bg-elevated' : 'hover:bg-elevated/50']"
            truncate
            @click="selectRange(range)"
          />
        </div>

        <UCalendar v-model="modelValue" class="p-2" :number-of-months="isDesktop ? 2 : 1" range />
      </div>
    </template>
  </UPopover>
</template>
```

## API

### Props

```ts
/**
 * Props for the Calendar component
 */
interface CalendarProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  /**
   * The type of picker.
   * - `date` renders a day calendar whose heading can switch to a month then year view.
   * - `month` renders a standalone month picker.
   * - `year` renders a standalone year picker.
   * @default "\"date\""
   */
  type?: CalendarType | undefined;
  /**
   * The icon to use for the next year control.
   */
  nextYearIcon?: any;
  /**
   * Configure the next year button.
   * `{ color: 'neutral', variant: 'ghost' }`{lang="ts-type"}
   */
  nextYear?: Omit<ButtonProps, LinkPropsKeys> | undefined;
  /**
   * The icon to use for the next month control.
   */
  nextMonthIcon?: any;
  /**
   * Configure the next month button.
   * `{ color: 'neutral', variant: 'ghost' }`{lang="ts-type"}
   */
  nextMonth?: Omit<ButtonProps, LinkPropsKeys> | undefined;
  /**
   * The icon to use for the previous year control.
   */
  prevYearIcon?: any;
  /**
   * Configure the prev year button.
   * `{ color: 'neutral', variant: 'ghost' }`{lang="ts-type"}
   */
  prevYear?: Omit<ButtonProps, LinkPropsKeys> | undefined;
  /**
   * The icon to use for the previous month control.
   */
  prevMonthIcon?: any;
  /**
   * Configure the prev month button.
   * `{ color: 'neutral', variant: 'ghost' }`{lang="ts-type"}
   */
  prevMonth?: Omit<ButtonProps, LinkPropsKeys> | undefined;
  /**
   * Whether to make the heading a button that switches between the day, month and year views.
   * Has no effect when `type` is `year`. Can be an object to override the button props.
   * `{ color: 'neutral', variant: 'ghost', block: true }`{lang="ts-type"}
   * @default "true"
   */
  viewControl?: boolean | Omit<ButtonProps, LinkPropsKeys> | undefined;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  variant?: "solid" | "outline" | "soft" | "subtle" | undefined;
  size?: "xs" | "sm" | "md" | "lg" | "xl" | undefined;
  /**
   * Whether or not a range of dates can be selected
   */
  range?: R | undefined;
  /**
   * Whether or not multiple dates can be selected
   */
  multiple?: M | undefined;
  /**
   * Show month controls
   * @default "true"
   */
  monthControls?: boolean | undefined;
  /**
   * Show year controls
   * @default "true"
   */
  yearControls?: boolean | undefined;
  defaultValue?: CalendarDate | CalendarDateTime | ZonedDateTime | DateRange | DateValue[];
  modelValue?: null | CalendarDate | CalendarDateTime | ZonedDateTime | DateRange | DateValue[];
  weekNumbers?: boolean | undefined;
  ui?: { root?: SlotClass; header?: SlotClass; body?: SlotClass; heading?: SlotClass; headingLabel?: SlotClass; grid?: SlotClass; gridRow?: SlotClass; gridWeekDaysRow?: SlotClass; gridBody?: SlotClass; headCell?: SlotClass; headCellWeek?: SlotClass; cell?: SlotClass; cellTrigger?: SlotClass; cellWeek?: SlotClass; } | undefined;
  defaultPlaceholder?: CalendarDate | CalendarDateTime | ZonedDateTime;
  placeholder?: CalendarDate | CalendarDateTime | ZonedDateTime;
  /**
   * When combined with `isDateUnavailable`, determines whether non-contiguous ranges, i.e. ranges containing unavailable dates, may be selected.
   */
  allowNonContiguousRanges?: boolean | undefined;
  /**
   * This property causes the previous and next buttons to navigate by the number of months displayed at once, rather than one month
   */
  pagedNavigation?: boolean | undefined;
  /**
   * Whether or not to prevent the user from deselecting a date without selecting another date first
   */
  preventDeselect?: boolean | undefined;
  /**
   * The maximum number of days that can be selected in a range
   */
  maximumDays?: number | undefined;
  /**
   * The day of the week to start the calendar on
   */
  weekStartsOn?: WeekStartsOn | undefined;
  /**
   * The format to use for the weekday strings provided via the weekdays slot prop
   */
  weekdayFormat?: WeekDayFormat | undefined;
  /**
   * Whether or not to always display 6 weeks in the calendar
   * @default "true"
   */
  fixedWeeks?: boolean | undefined;
  maxValue?: CalendarDate | CalendarDateTime | ZonedDateTime;
  minValue?: CalendarDate | CalendarDateTime | ZonedDateTime;
  /**
   * The locale to use for formatting dates
   */
  locale?: string | undefined;
  /**
   * The number of months to display at once
   */
  numberOfMonths?: number | undefined;
  /**
   * Whether or not the calendar is disabled
   */
  disabled?: boolean | undefined;
  /**
   * Whether or not the calendar is readonly
   */
  readonly?: boolean | undefined;
  /**
   * If true, the calendar will focus the selected day, today, or the first day of the month depending on what is visible when the calendar is mounted
   */
  initialFocus?: boolean | undefined;
  /**
   * A function that returns whether or not a date is disabled
   */
  isDateDisabled?: Matcher | undefined;
  /**
   * A function that returns whether or not a date is unavailable
   */
  isDateUnavailable?: Matcher | undefined;
  /**
   * A function that returns whether or not a date is hightable
   */
  isDateHighlightable?: Matcher | undefined;
  /**
   * A function that returns the next page of the calendar. It receives the current placeholder as an argument inside the component.
   */
  nextPage?: ((placeholder: DateValue) => DateValue) | undefined;
  /**
   * A function that returns the previous page of the calendar. It receives the current placeholder as an argument inside the component.
   */
  prevPage?: ((placeholder: DateValue) => DateValue) | undefined;
  /**
   * Whether or not to disable days outside the current view.
   */
  disableDaysOutsideCurrentView?: boolean | undefined;
  /**
   * Which part of the range should be fixed
   */
  fixedDate?: "start" | "end" | undefined;
  /**
   * A function that returns whether or not a month is disabled
   */
  isMonthDisabled?: Matcher | undefined;
  /**
   * A function that returns whether or not a month is unavailable
   */
  isMonthUnavailable?: Matcher | undefined;
  /**
   * A function that returns whether or not a year is disabled
   */
  isYearDisabled?: Matcher | undefined;
  /**
   * A function that returns whether or not a year is unavailable
   */
  isYearUnavailable?: Matcher | undefined;
}
```

### Slots

```ts
/**
 * Slots for the Calendar component
 */
interface CalendarSlots {
  heading(): any;
  day(): any;
  week-day(): any;
  month-cell(): any;
  year-cell(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the Calendar component
 */
interface CalendarEmits {
  update:modelValue: (payload: [value: CalendarModelValue<R, M>]) => void;
  update:placeholder: (payload: [date: DateValue]) => void;
  update:validModelValue: (payload: [date: DateRange]) => void;
  update:startValue: (payload: [date: DateValue | undefined]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    calendar: {
      slots: {
        root: '',
        header: 'flex items-center justify-between',
        body: 'flex flex-col space-y-4 pt-4 sm:flex-row sm:space-x-4 sm:space-y-0',
        heading: 'flex-1 min-w-0 text-center',
        headingLabel: 'font-medium block truncate p-1.5',
        grid: 'w-full border-collapse select-none space-y-1 focus:outline-none',
        gridRow: 'grid',
        gridWeekDaysRow: 'mb-1 grid w-full grid-cols-7',
        gridBody: 'grid',
        headCell: 'rounded-md',
        headCellWeek: 'rounded-md text-muted',
        cell: 'relative text-center',
        cellTrigger: [
          'm-0.5 relative flex items-center justify-center whitespace-nowrap focus-visible:outline-3 data-disabled:text-muted data-unavailable:line-through data-unavailable:text-muted data-unavailable:pointer-events-none data-today:font-semibold',
          'transition'
        ],
        cellWeek: 'relative text-center text-muted'
      },
      variants: {
        color: {
          primary: {
            headCell: 'text-primary',
            cellTrigger: 'outline-primary/25'
          },
          secondary: {
            headCell: 'text-secondary',
            cellTrigger: 'outline-secondary/25'
          },
          success: {
            headCell: 'text-success',
            cellTrigger: 'outline-success/25'
          },
          info: {
            headCell: 'text-info',
            cellTrigger: 'outline-info/25'
          },
          warning: {
            headCell: 'text-warning',
            cellTrigger: 'outline-warning/25'
          },
          error: {
            headCell: 'text-error',
            cellTrigger: 'outline-error/25'
          },
          neutral: {
            headCell: 'text-highlighted',
            cellTrigger: 'outline-inverted/25'
          }
        },
        variant: {
          solid: '',
          outline: '',
          soft: '',
          subtle: ''
        },
        size: {
          xs: {
            headingLabel: 'text-xs',
            cell: 'text-xs',
            cellWeek: 'text-xs',
            headCell: 'text-[10px]',
            headCellWeek: 'text-[10px]',
            body: 'space-y-2 pt-2'
          },
          sm: {
            headingLabel: 'text-xs',
            headCell: 'text-xs',
            headCellWeek: 'text-xs',
            cellWeek: 'text-xs',
            cell: 'text-xs'
          },
          md: {
            headingLabel: 'text-sm',
            headCell: 'text-xs',
            headCellWeek: 'text-xs',
            cellWeek: 'text-xs',
            cell: 'text-sm'
          },
          lg: {
            headingLabel: 'text-md',
            headCell: 'text-md',
            headCellWeek: 'text-md'
          },
          xl: {
            headingLabel: 'text-lg',
            headCell: 'text-lg',
            headCellWeek: 'text-lg'
          }
        },
        view: {
          day: {
            gridRow: 'grid-cols-7 place-items-center',
            cellTrigger: 'rounded-full data-outside-view:text-muted'
          },
          month: {
            gridRow: 'grid-cols-4',
            cellTrigger: 'rounded-md'
          },
          year: {
            gridRow: 'grid-cols-4',
            cellTrigger: 'rounded-md'
          }
        },
        weekNumbers: {
          true: ''
        }
      },
      compoundVariants: [
        {
          color: 'primary',
          variant: 'solid',
          class: {
            cellTrigger: 'data-selected:bg-primary data-selected:text-inverted data-today:not-data-selected:text-primary data-highlighted:bg-primary/20 hover:not-data-selected:bg-primary/20'
          }
        },
        {
          color: 'secondary',
          variant: 'solid',
          class: {
            cellTrigger: 'data-selected:bg-secondary data-selected:text-inverted data-today:not-data-selected:text-secondary data-highlighted:bg-secondary/20 hover:not-data-selected:bg-secondary/20'
          }
        },
        {
          color: 'success',
          variant: 'solid',
          class: {
            cellTrigger: 'data-selected:bg-success data-selected:text-inverted data-today:not-data-selected:text-success data-highlighted:bg-success/20 hover:not-data-selected:bg-success/20'
          }
        },
        {
          color: 'info',
          variant: 'solid',
          class: {
            cellTrigger: 'data-selected:bg-info data-selected:text-inverted data-today:not-data-selected:text-info data-highlighted:bg-info/20 hover:not-data-selected:bg-info/20'
          }
        },
        {
          color: 'warning',
          variant: 'solid',
          class: {
            cellTrigger: 'data-selected:bg-warning data-selected:text-inverted data-today:not-data-selected:text-warning data-highlighted:bg-warning/20 hover:not-data-selected:bg-warning/20'
          }
        },
        {
          color: 'error',
          variant: 'solid',
          class: {
            cellTrigger: 'data-selected:bg-error data-selected:text-inverted data-today:not-data-selected:text-error data-highlighted:bg-error/20 hover:not-data-selected:bg-error/20'
          }
        },
        {
          color: 'primary',
          variant: 'outline',
          class: {
            cellTrigger: 'data-selected:ring data-selected:ring-inset data-selected:ring-primary/50 data-selected:text-primary data-selected:focus-visible:ring-primary data-today:not-data-selected:text-primary data-highlighted:bg-primary/10 hover:not-data-selected:bg-primary/10'
          }
        },
        {
          color: 'secondary',
          variant: 'outline',
          class: {
            cellTrigger: 'data-selected:ring data-selected:ring-inset data-selected:ring-secondary/50 data-selected:text-secondary data-selected:focus-visible:ring-secondary data-today:not-data-selected:text-secondary data-highlighted:bg-secondary/10 hover:not-data-selected:bg-secondary/10'
          }
        },
        {
          color: 'success',
          variant: 'outline',
          class: {
            cellTrigger: 'data-selected:ring data-selected:ring-inset data-selected:ring-success/50 data-selected:text-success data-selected:focus-visible:ring-success data-today:not-data-selected:text-success data-highlighted:bg-success/10 hover:not-data-selected:bg-success/10'
          }
        },
        {
          color: 'info',
          variant: 'outline',
          class: {
            cellTrigger: 'data-selected:ring data-selected:ring-inset data-selected:ring-info/50 data-selected:text-info data-selected:focus-visible:ring-info data-today:not-data-selected:text-info data-highlighted:bg-info/10 hover:not-data-selected:bg-info/10'
          }
        },
        {
          color: 'warning',
          variant: 'outline',
          class: {
            cellTrigger: 'data-selected:ring data-selected:ring-inset data-selected:ring-warning/50 data-selected:text-warning data-selected:focus-visible:ring-warning data-today:not-data-selected:text-warning data-highlighted:bg-warning/10 hover:not-data-selected:bg-warning/10'
          }
        },
        {
          color: 'error',
          variant: 'outline',
          class: {
            cellTrigger: 'data-selected:ring data-selected:ring-inset data-selected:ring-error/50 data-selected:text-error data-selected:focus-visible:ring-error data-today:not-data-selected:text-error data-highlighted:bg-error/10 hover:not-data-selected:bg-error/10'
          }
        },
        {
          color: 'primary',
          variant: 'soft',
          class: {
            cellTrigger: 'data-selected:bg-primary/10 data-selected:text-primary data-today:not-data-selected:text-primary data-highlighted:bg-primary/20 hover:not-data-selected:bg-primary/20'
          }
        },
        {
          color: 'secondary',
          variant: 'soft',
          class: {
            cellTrigger: 'data-selected:bg-secondary/10 data-selected:text-secondary data-today:not-data-selected:text-secondary data-highlighted:bg-secondary/20 hover:not-data-selected:bg-secondary/20'
          }
        },
        {
          color: 'success',
          variant: 'soft',
          class: {
            cellTrigger: 'data-selected:bg-success/10 data-selected:text-success data-today:not-data-selected:text-success data-highlighted:bg-success/20 hover:not-data-selected:bg-success/20'
          }
        },
        {
          color: 'info',
          variant: 'soft',
          class: {
            cellTrigger: 'data-selected:bg-info/10 data-selected:text-info data-today:not-data-selected:text-info data-highlighted:bg-info/20 hover:not-data-selected:bg-info/20'
          }
        },
        {
          color: 'warning',
          variant: 'soft',
          class: {
            cellTrigger: 'data-selected:bg-warning/10 data-selected:text-warning data-today:not-data-selected:text-warning data-highlighted:bg-warning/20 hover:not-data-selected:bg-warning/20'
          }
        },
        {
          color: 'error',
          variant: 'soft',
          class: {
            cellTrigger: 'data-selected:bg-error/10 data-selected:text-error data-today:not-data-selected:text-error data-highlighted:bg-error/20 hover:not-data-selected:bg-error/20'
          }
        },
        {
          color: 'primary',
          variant: 'subtle',
          class: {
            cellTrigger: 'data-selected:bg-primary/10 data-selected:text-primary data-selected:ring data-selected:ring-inset data-selected:ring-primary/25 data-selected:focus-visible:ring-primary data-today:not-data-selected:text-primary data-highlighted:bg-primary/20 hover:not-data-selected:bg-primary/20'
          }
        },
        {
          color: 'secondary',
          variant: 'subtle',
          class: {
            cellTrigger: 'data-selected:bg-secondary/10 data-selected:text-secondary data-selected:ring data-selected:ring-inset data-selected:ring-secondary/25 data-selected:focus-visible:ring-secondary data-today:not-data-selected:text-secondary data-highlighted:bg-secondary/20 hover:not-data-selected:bg-secondary/20'
          }
        },
        {
          color: 'success',
          variant: 'subtle',
          class: {
            cellTrigger: 'data-selected:bg-success/10 data-selected:text-success data-selected:ring data-selected:ring-inset data-selected:ring-success/25 data-selected:focus-visible:ring-success data-today:not-data-selected:text-success data-highlighted:bg-success/20 hover:not-data-selected:bg-success/20'
          }
        },
        {
          color: 'info',
          variant: 'subtle',
          class: {
            cellTrigger: 'data-selected:bg-info/10 data-selected:text-info data-selected:ring data-selected:ring-inset data-selected:ring-info/25 data-selected:focus-visible:ring-info data-today:not-data-selected:text-info data-highlighted:bg-info/20 hover:not-data-selected:bg-info/20'
          }
        },
        {
          color: 'warning',
          variant: 'subtle',
          class: {
            cellTrigger: 'data-selected:bg-warning/10 data-selected:text-warning data-selected:ring data-selected:ring-inset data-selected:ring-warning/25 data-selected:focus-visible:ring-warning data-today:not-data-selected:text-warning data-highlighted:bg-warning/20 hover:not-data-selected:bg-warning/20'
          }
        },
        {
          color: 'error',
          variant: 'subtle',
          class: {
            cellTrigger: 'data-selected:bg-error/10 data-selected:text-error data-selected:ring data-selected:ring-inset data-selected:ring-error/25 data-selected:focus-visible:ring-error data-today:not-data-selected:text-error data-highlighted:bg-error/20 hover:not-data-selected:bg-error/20'
          }
        },
        {
          color: 'neutral',
          variant: 'solid',
          class: {
            cellTrigger: 'data-selected:bg-inverted data-selected:text-inverted data-today:not-data-selected:text-highlighted data-highlighted:bg-inverted/20 hover:not-data-selected:bg-inverted/10'
          }
        },
        {
          color: 'neutral',
          variant: 'outline',
          class: {
            cellTrigger: 'data-selected:ring data-selected:ring-inset data-selected:ring-accented data-selected:text-default data-selected:bg-default data-selected:focus-visible:ring-inverted data-today:not-data-selected:text-highlighted data-highlighted:bg-inverted/10 hover:not-data-selected:bg-inverted/10'
          }
        },
        {
          color: 'neutral',
          variant: 'soft',
          class: {
            cellTrigger: 'data-selected:bg-elevated data-selected:text-default data-today:not-data-selected:text-highlighted data-highlighted:bg-inverted/20 hover:not-data-selected:bg-inverted/10'
          }
        },
        {
          color: 'neutral',
          variant: 'subtle',
          class: {
            cellTrigger: 'data-selected:bg-elevated data-selected:text-default data-selected:ring data-selected:ring-inset data-selected:ring-accented data-selected:focus-visible:ring-inverted data-today:not-data-selected:text-highlighted data-highlighted:bg-inverted/20 hover:not-data-selected:bg-inverted/10'
          }
        },
        {
          size: 'xs',
          view: 'day',
          class: {
            cellTrigger: 'size-7'
          }
        },
        {
          size: 'sm',
          view: 'day',
          class: {
            cellTrigger: 'size-7'
          }
        },
        {
          size: 'md',
          view: 'day',
          class: {
            cellTrigger: 'size-8'
          }
        },
        {
          size: 'lg',
          view: 'day',
          class: {
            cellTrigger: 'size-9 text-md'
          }
        },
        {
          size: 'xl',
          view: 'day',
          class: {
            cellTrigger: 'size-10 text-lg'
          }
        },
        {
          size: 'xs',
          view: [
            'month',
            'year'
          ],
          class: {
            cellTrigger: 'h-7 px-2'
          }
        },
        {
          size: 'sm',
          view: [
            'month',
            'year'
          ],
          class: {
            cellTrigger: 'h-7 px-2'
          }
        },
        {
          size: 'md',
          view: [
            'month',
            'year'
          ],
          class: {
            cellTrigger: 'h-8 px-3'
          }
        },
        {
          size: 'lg',
          view: [
            'month',
            'year'
          ],
          class: {
            cellTrigger: 'h-9 px-4 text-md'
          }
        },
        {
          size: 'xl',
          view: [
            'month',
            'year'
          ],
          class: {
            cellTrigger: 'h-10 px-5 text-lg'
          }
        },
        {
          view: 'day',
          weekNumbers: true,
          class: {
            gridRow: 'grid-cols-8',
            gridWeekDaysRow: 'grid-cols-8 [&>*:first-child]:col-start-2'
          }
        }
      ],
      defaultVariants: {
        size: 'md',
        color: 'primary',
        variant: 'solid',
        view: 'day'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Calendar.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/calendar.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

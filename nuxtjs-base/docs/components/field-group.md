---
title: "FieldGroup"
description: "Group multiple button-like elements together."
canonical_url: "https://ui.nuxt.com/docs/components/field-group"
---
# FieldGroup

> Group multiple button-like elements together.

## Usage

Wrap multiple [Button](/components/button) within a FieldGroup to group them together.

```vue
<template>
  <UFieldGroup>
    <UButton color="neutral" variant="subtle" label="Button" />
    <UButton color="neutral" variant="outline" icon="i-lucide-chevron-down" />
  </UFieldGroup>
</template>
```

### Size

Use the `size` prop to change the size of all the buttons.

```vue
<template>
  <UFieldGroup size="xl">
    <UButton color="neutral" variant="subtle" label="Button" />
    <UButton color="neutral" variant="outline" icon="i-lucide-chevron-down" />
  </UFieldGroup>
</template>
```

### Orientation

Use the `orientation` prop to change the orientation of the buttons. Defaults to `horizontal`.

```vue
<template>
  <UFieldGroup orientation="vertical">
    <UButton color="neutral" variant="subtle" label="Submit" />
    <UButton color="neutral" variant="outline" label="Cancel" />
  </UFieldGroup>
</template>
```

## Examples

### With input

You can use components like [Input](/components/input), [InputMenu](/components/input-menu), [Select](/components/select) [SelectMenu](/components/select-menu), etc. within a field group.

```vue
<template>
  <UFieldGroup>
    <UInput color="neutral" variant="outline" placeholder="Enter token" />

    <UButton color="neutral" variant="subtle" icon="i-lucide-clipboard" />
  </UFieldGroup>
</template>
```

### With tooltip

You can use a [Tooltip](/components/tooltip) within a field group.

```vue [FieldGroupTooltipExample.vue]
<template>
  <UFieldGroup>
    <UInput color="neutral" variant="outline" placeholder="Enter token" />

    <UTooltip text="Copy to clipboard">
      <UButton
        color="neutral"
        variant="subtle"
        icon="i-lucide-clipboard"
      />
    </UTooltip>
  </UFieldGroup>
</template>
```

### With dropdown menu

You can use a [DropdownMenu](/components/dropdown-menu) within a field group.

```vue [FieldGroupDropdownExample.vue]
<script setup lang="ts">
import type { DropdownMenuItem } from '@nuxt/ui'

const items: DropdownMenuItem[] = [
  {
    label: 'Team',
    icon: 'i-lucide-users'
  },
  {
    label: 'Invite users',
    icon: 'i-lucide-user-plus',
    children: [
      {
        label: 'Invite by email',
        icon: 'i-lucide-send-horizontal'
      },
      {
        label: 'Invite by link',
        icon: 'i-lucide-link'
      }
    ]
  },
  {
    label: 'New team',
    icon: 'i-lucide-plus'
  }
]
</script>

<template>
  <UFieldGroup>
    <UButton color="neutral" variant="subtle" label="Settings" />

    <UDropdownMenu :items="items">
      <UButton
        color="neutral"
        variant="outline"
        icon="i-lucide-chevron-down"
      />
    </UDropdownMenu>
  </UFieldGroup>
</template>
```

### With badge

You can use a [Badge](/components/badge) within a field group.

```vue [FieldGroupBadgeExample.vue]
<template>
  <UFieldGroup>
    <UBadge color="neutral" variant="outline" size="lg" label="https://" />

    <UInput color="neutral" variant="outline" placeholder="www.example.com" />
  </UFieldGroup>
</template>
```

## API

### Props

```ts
/**
 * Props for the FieldGroup component
 */
interface FieldGroupProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  size?: "xs" | "sm" | "md" | "lg" | "xl" | undefined;
  /**
   * The orientation the buttons are laid out.
   * @default "\"horizontal\""
   */
  orientation?: "horizontal" | "vertical" | undefined;
  ui?: { base?: any; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the FieldGroup component
 */
interface FieldGroupSlots {
  default(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    fieldGroup: {
      base: 'relative',
      variants: {
        size: {
          xs: '',
          sm: '',
          md: '',
          lg: '',
          xl: ''
        },
        orientation: {
          horizontal: 'inline-flex -space-x-px',
          vertical: 'flex flex-col -space-y-px'
        }
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/FieldGroup.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/field-group.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

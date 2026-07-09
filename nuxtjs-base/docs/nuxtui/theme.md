---
title: "Theme"
description: "A headless component to theme child components."
canonical_url: "https://ui.nuxt.com/docs/components/theme"
---
# Theme

> A headless component to theme child components.

## Usage

The Theme component allows you to override default **slot classes** and **props** of all child components without modifying each one individually. It uses Vue's `provide` / `inject` mechanism under the hood, so the overrides apply at any depth.

> [!NOTE]
> 
> The Theme component doesn't render any HTML element, it only provides theme overrides to its children.

**Nuxt:**

> [!TIP]
> 
> For app-level theme configuration, we recommend using the `app.config.ts` file instead.

**Vue:**

> [!TIP]
> 
> For app-level theme configuration, we recommend using the `vite.config.ts` file instead.

### Slot classes

Use the `ui` prop to override slot classes of descendant components. Keys are component names (camelCase) and values are their slot class overrides.

```vue [ThemeUiExample.vue]
<template>
  <UTheme
    :ui="{
      button: {
        base: 'rounded-full'
      }
    }"
  >
    <div class="flex items-center gap-2">
      <UButton label="Button" color="neutral" />
      <UButton label="Button" color="neutral" variant="outline" />
      <UButton label="Button" color="neutral" variant="subtle" />
    </div>
  </UTheme>
</template>
```

### Prop defaults `4.8+`

Use the `props` prop to override the default value of any prop on descendant components. Each key maps to a partial of that component's props.

```vue [ThemePropsExample.vue]
<template>
  <UTheme
    :props="{
      button: { color: 'neutral', variant: 'subtle', size: 'lg' },
      tooltip: { delayDuration: 0, arrow: true }
    }"
  >
    <div class="flex items-center gap-2">
      <UTooltip text="Inherits delayDuration from theme">
        <UButton label="Hover me" />
      </UTooltip>
      <UButton label="With icon" icon="i-lucide-rocket" />
      <UButton label="Square" icon="i-lucide-star" square />
    </div>
  </UTheme>
</template>
```

> [!TIP]
> 
> Explicit props on a component (e.g. `<UButton color="primary" />`) always win over `<UTheme :props>`. Theme defaults only apply when the prop wasn't passed explicitly.

## Examples

### Multiple components

Use different keys in `ui` or `props` to theme multiple component types at once.

```vue [ThemeMultipleExample.vue]
<template>
  <UTheme
    :props="{
      button: { color: 'neutral', variant: 'outline', size: 'lg' },
      input: { size: 'lg' },
      select: { size: 'lg' }
    }"
    :ui="{
      button: { base: 'rounded-full' },
      input: { base: 'rounded-full' },
      select: { base: 'rounded-full' }
    }"
  >
    <div class="flex items-center gap-2">
      <UButton label="Button" />
      <UInput placeholder="Search..." />
      <USelect placeholder="Select" :items="['Item 1', 'Item 2', 'Item 3']" />
    </div>
  </UTheme>
</template>
```

### Nested themes

Nest multiple Theme components to compose overrides. The innermost Theme takes precedence, while unoverridden keys are inherited from the outer Theme.

```vue [ThemeNestedExample.vue]
<template>
  <UTheme
    :ui="{
      button: {
        base: 'rounded-full'
      }
    }"
  >
    <div class="flex flex-col items-start gap-4 border border-muted p-4 rounded-lg">
      <div class="flex items-center gap-2">
        <UButton label="Outer theme" />
        <UButton label="Outer theme" color="neutral" variant="outline" />
      </div>

      <UTheme
        :ui="{
          button: {
            base: 'font-black uppercase'
          }
        }"
      >
        <div class="border border-muted p-4 rounded-lg">
          <div class="flex items-center gap-2">
            <UButton label="Inner theme" />
            <UButton label="Inner theme" color="neutral" variant="outline" />
          </div>
        </div>
      </UTheme>
    </div>
  </UTheme>
</template>
```

### Explicit priority

Explicitly setting any prop (including `ui`) on an individual component always takes priority over the Theme component.

```vue [ThemePriorityExample.vue]
<template>
  <UTheme
    :ui="{
      button: {
        base: 'rounded-full'
      }
    }"
  >
    <div class="flex items-center gap-2">
      <UButton label="Themed" />
      <UButton label="Overridden" :ui="{ base: 'rounded-none' }" />
    </div>
  </UTheme>
</template>
```

### Deep propagation

The overrides are available to all descendant components regardless of how deeply nested they are.

```vue [ThemeDeepExample.vue]
<script setup lang="ts">
import MyButton from './MyButton.vue'
</script>

<template>
  <UTheme
    :ui="{
      button: {
        base: 'rounded-full'
      }
    }"
  >
    <UCard :ui="{ body: 'flex items-center gap-2 sm:flex-row flex-col' }">
      <UButton label="Direct child" />
      <MyButton />
    </UCard>
  </UTheme>
</template>
```

> [!NOTE]
> 
> In this example, `MyButton` is a custom component that renders a `UButton` internally. The theme overrides still apply because they propagate through the entire component tree.

### Form components

Use the Theme component to apply consistent styling across a group of form components.

```vue [ThemeFormExample.vue]
<script setup lang="ts">
import * as z from 'zod'
import type { FormSubmitEvent } from '@nuxt/ui'

const schema = z.object({
  name: z.string().min(2, 'Too short'),
  email: z.email('Invalid email'),
  bio: z.string().optional()
})

type Schema = z.output<typeof schema>

const state = reactive<Partial<Schema>>({
  name: 'John Doe',
  email: 'john@example.com',
  bio: undefined
})

const toast = useToast()
async function onSubmit(event: FormSubmitEvent<Schema>) {
  toast.add({ title: 'Saved', description: 'Your profile has been updated.', color: 'success' })
  console.log(event.data)
}
</script>

<template>
  <UTheme
    :props="{
      input: { size: 'lg' },
      textarea: { size: 'lg' }
    }"
    :ui="{
      formField: {
        root: 'flex max-sm:flex-col justify-between gap-4',
        wrapper: 'w-full sm:max-w-xs'
      }
    }"
  >
    <UForm :schema="schema" :state="state" class="space-y-4 w-full" @submit="onSubmit">
      <UFormField label="Name" name="name" description="Your public display name.">
        <UInput v-model="state.name" />
      </UFormField>

      <UFormField label="Email" name="email" description="Used for notifications.">
        <UInput v-model="state.email" type="email" />
      </UFormField>

      <UFormField label="Bio" name="bio" description="A short description about yourself.">
        <UTextarea v-model="state.bio" placeholder="Tell us about yourself" />
      </UFormField>

      <div class="flex justify-end">
        <UButton type="submit">
          Save changes
        </UButton>
      </div>
    </UForm>
  </UTheme>
</template>
```

> [!TIP]
> 
> `<UFormField>`, `<UFieldGroup>` and `<UAvatarGroup>` keep precedence over `<UTheme :props>` for `size`, `color` and `highlight`. Validation errors also force the `error` color over any theme value.

### Prose components

Use the `prose` namespace to theme typography components. Keys are nested under `prose` (e.g. `prose.p`, `prose.code`).

```vue [ThemeProseExample.vue]
<script setup lang="ts">
const ui = {
  prose: {
    p: { base: 'my-2.5 text-sm/6' },
    li: { base: 'my-0.5 text-sm/6' },
    ul: { base: 'my-2.5' },
    ol: { base: 'my-2.5' },
    h1: { base: 'text-xl mb-4' },
    h2: { base: 'text-lg mt-6 mb-3' },
    h3: { base: 'text-base mt-4 mb-2' },
    h4: { base: 'text-sm mt-3 mb-1.5' },
    code: { base: 'text-xs' },
    pre: { root: 'my-2.5', base: 'text-xs/5' },
    table: { root: 'my-2.5' },
    hr: { base: 'my-5' }
  }
}

const value = `## Getting started

This is a paragraph with a **tighter typographic scale** applied through the \`Theme\` component.

- First item
- Second item
- Third item

> The spacing and font sizes are smaller than the defaults, making it ideal for compact content areas.`
</script>

<template>
  <UTheme :ui="ui">
    <MDC :value="value" />
  </UTheme>
</template>
```

## API

### Props

```ts
/**
 * Props for the Theme component
 */
interface ThemeProps {
  /**
   * Per-component prop defaults that flow through `useComponentProps` to
   * every descendant. Each key maps to a partial of that component's props.
   */
  props?: ThemeDefaults | undefined;
  /**
   * Per-component slot class overrides (flat shorthand for `:props.<name>.ui`).
   */
  ui?: ThemeUI | undefined;
}
```

### Slots

```ts
/**
 * Slots for the Theme component
 */
interface ThemeSlots {
  default(): any;
}
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Theme.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/theme.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

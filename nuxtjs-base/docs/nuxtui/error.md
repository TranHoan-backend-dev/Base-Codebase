---
title: "Error"
description: "A pre-built error component with NuxtError support."
canonical_url: "https://ui.nuxt.com/docs/components/error"
---
# Error

> A pre-built error component with NuxtError support.

## Usage

The Error component renders a `<main>` element that works together with the [Header](/docs/components/header) component to create a full-height layout that extends to the viewport's available height.

> [!TIP]
> See: /docs/getting-started/theme/css-variables#header
> 
> The Error component uses the `--ui-header-height` CSS variable to position itself correctly below the `Header`.

### Error

Use the `error` prop to display an error message.

> [!NOTE]
> See: https://nuxt.com/docs/guide/directory-structure/error
> 
> In most cases, you will receive the `error` prop in your `error.vue` file.

```vue
<template>
  <UError :error="{
  statusCode: 404,
  statusMessage: 'Page not found',
  message: 'The page you are looking for does not exist.'
}" />
</template>
```

### Icon `4.8+`

Use the `icon` prop to display an icon above the status code.

```vue
<template>
  <UError icon="i-lucide-file-x" :error="{
  statusCode: 404,
  statusMessage: 'Page not found',
  message: 'The page you are looking for does not exist.'
}" />
</template>
```

Use the `#leading` slot to display a custom element, such as a logo.

```vue
<template>
  <UError :error="{
  statusCode: 404,
  statusMessage: 'Page not found',
  message: 'The page you are looking for does not exist.'
}">
    <template #leading>
      <img src="https://github.com/nuxt.png" alt="Logo" class="size-10 rounded-full">
    </template></UError>
</template>
```

### Clear

Use the `clear` prop to customize or hide the clear button (with `false` value).

You can pass any property from the [Button](/docs/components/button) component to customize it.

```vue
<template>
  <UError :clear="{
  color: 'neutral',
  size: 'xl',
  icon: 'i-lucide-arrow-left',
  class: 'rounded-full'
}" :error="{
  statusCode: 404,
  statusMessage: 'Page not found',
  message: 'The page you are looking for does not exist.'
}" />
</template>
```

### Redirect

Use the `redirect` prop to redirect the user to a different page when the clear button is clicked. Defaults to `/`.

```vue
<template>
  <UError redirect="/docs/getting-started" :error="{
  statusCode: 404,
  statusMessage: 'Page not found',
  message: 'The page you are looking for does not exist.'
}" />
</template>
```

## Examples

### Within `error.vue`

Use the Error component in your `error.vue`:

```vue [error.vue]
<script setup lang="ts">
import type { NuxtError } from '#app'

const props = defineProps<{
  error: NuxtError
}>()
</script>

<template>
  <UApp>
    <UHeader />

    <UError :error="error" />

    <UFooter />
  </UApp>
</template>
```

> [!TIP]
> 
> You might want to replicate the code of your `app.vue` inside your `error.vue` file to have the same layout and features, here is an example: [https://github.com/nuxt/ui/blob/v4/docs/app/error.vue](https://github.com/nuxt/ui/blob/v4/docs/app/error.vue)

> [!NOTE]
> 
> You can read more about how to handle errors in the [Nuxt documentation](https://nuxt.com/docs/getting-started/error-handling#error-page), but when using `nuxt generate` it is recommended to add `fatal: true` inside your `createError` call to make sure the error page is displayed:
> 
> ```vue [pages/[...slug].vue]
> <script setup lang="ts">
> const route = useRoute()
> 
> const { data: page } = await useAsyncData(route.path, () => {
>   return queryCollection('docs').path(route.path).first()
> })
> if (!page.value) {
>   throw createError({ statusCode: 404, statusMessage: 'Page not found', fatal: true })
> }
> </script>
> ```

## API

### Props

```ts
/**
 * Props for the Error component
 */
interface ErrorProps {
  /**
   * The element or component this component should render as.
   * @default "\"main\""
   */
  as?: any;
  /**
   * The icon displayed above the status code.
   */
  icon?: any;
  error?: Partial<NuxtError<unknown> & { message: string; }> | undefined;
  /**
   * The URL to redirect to when the error is cleared.
   * @default "\"/\""
   */
  redirect?: string | undefined;
  /**
   * Display a button to clear the error in the links slot.
   * `{ size: 'lg', color: 'primary', variant: 'solid', label: 'Back to home' }`{lang="ts-type"}
   * @default "true"
   */
  clear?: boolean | ButtonProps | undefined;
  ui?: { root?: SlotClass; leading?: SlotClass; leadingIcon?: SlotClass; statusCode?: SlotClass; statusMessage?: SlotClass; message?: SlotClass; links?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the Error component
 */
interface ErrorSlots {
  default(): any;
  leading(): any;
  statusCode(): any;
  statusMessage(): any;
  message(): any;
  links(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    error: {
      slots: {
        root: 'min-h-[calc(100vh-var(--ui-header-height))] flex flex-col items-center justify-center text-center',
        leading: 'mb-4 flex items-center justify-center',
        leadingIcon: 'size-10 shrink-0 text-primary',
        statusCode: 'text-base font-semibold text-primary',
        statusMessage: 'mt-2 text-4xl sm:text-5xl font-bold text-highlighted text-balance',
        message: 'mt-4 text-lg text-muted text-balance',
        links: 'mt-8 flex items-center justify-center gap-6'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Error.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/error.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

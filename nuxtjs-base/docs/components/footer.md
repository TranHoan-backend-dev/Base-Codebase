---
title: "Footer"
description: "A responsive footer component."
canonical_url: "https://ui.nuxt.com/docs/components/footer"
---
# Footer

> A responsive footer component.

## Usage

The Footer component renders a `<footer>` element.

Use the `left`, `default` and `right` slots to customize the footer.

```vue [FooterExample.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items: NavigationMenuItem[] = [{
  label: 'Figma Kit',
  to: 'https://go.nuxt.com/figma-ui',
  target: '_blank'
}, {
  label: 'Playground',
  to: 'https://stackblitz.com/edit/nuxt-ui',
  target: '_blank'
}, {
  label: 'Releases',
  to: 'https://github.com/nuxt/ui/releases',
  target: '_blank'
}]
</script>

<template>
  <UFooter>
    <template #left>
      <p class="text-muted text-sm">
        Copyright © {{ new Date().getFullYear() }}
      </p>
    </template>

    <UNavigationMenu :items="items" variant="link" />

    <template #right>
      <UButton
        icon="i-simple-icons-discord"
        color="neutral"
        variant="ghost"
        to="https://go.nuxt.com/discord"
        target="_blank"
        aria-label="Discord"
      />
      <UButton
        icon="i-simple-icons-x"
        color="neutral"
        variant="ghost"
        to="https://go.nuxt.com/x"
        target="_blank"
        aria-label="X"
      />
      <UButton
        icon="i-simple-icons-github"
        color="neutral"
        variant="ghost"
        to="https://github.com/nuxt/nuxt"
        target="_blank"
        aria-label="GitHub"
      />
    </template>
  </UFooter>
</template>
```

> [!NOTE]
> 
> In this example, we use the [NavigationMenu](/docs/components/navigation-menu) component to render the footer links in the center.

> [!TIP]
> See: /docs/components/footer-columns
> 
> You can use the `FooterColumns` component to display a list of links inside the `top` slot.

## Examples

### Within `app.vue`

Use the Footer component in your `app.vue` or in a layout:

```vue [app.vue]
<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const items: NavigationMenuItem[] = [{
  label: 'Figma Kit',
  to: 'https://go.nuxt.com/figma-ui',
  target: '_blank'
}, {
  label: 'Playground',
  to: 'https://stackblitz.com/edit/nuxt-ui',
  target: '_blank'
}, {
  label: 'Releases',
  to: 'https://github.com/nuxt/ui/releases',
  target: '_blank'
}]
</script>

<template>
  <UApp>
    <UHeader />

    <UMain>
      <NuxtLayout>
        <NuxtPage />
      </NuxtLayout>
    </UMain>

    <USeparator icon="i-simple-icons-nuxtdotjs" type="dashed" class="h-px" />

    <UFooter>
      <template #left>
        <p class="text-muted text-sm">
          Copyright © {{ new Date().getFullYear() }}
        </p>
      </template>

      <UNavigationMenu :items="items" variant="link" />

      <template #right>
        <UButton
          icon="i-simple-icons-discord"
          color="neutral"
          variant="ghost"
          to="https://go.nuxt.com/discord"
          target="_blank"
          aria-label="Discord"
        />
        <UButton
          icon="i-simple-icons-x"
          color="neutral"
          variant="ghost"
          to="https://go.nuxt.com/x"
          target="_blank"
          aria-label="X"
        />
        <UButton
          icon="i-simple-icons-github"
          color="neutral"
          variant="ghost"
          to="https://github.com/nuxt/nuxt"
          target="_blank"
          aria-label="GitHub"
        />
      </template>
    </UFooter>
  </UApp>
</template>
```

> [!NOTE]
> 
> In this example, we use the [Separator](/docs/components/separator) component to add a border above the footer.

## API

### Props

```ts
/**
 * Props for the Footer component
 */
interface FooterProps {
  /**
   * The element or component this component should render as.
   * @default "\"footer\""
   */
  as?: any;
  ui?: { root?: SlotClass; top?: SlotClass; bottom?: SlotClass; container?: SlotClass; left?: SlotClass; center?: SlotClass; right?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the Footer component
 */
interface FooterSlots {
  left(): any;
  default(): any;
  right(): any;
  top(): any;
  bottom(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    footer: {
      slots: {
        root: '',
        top: 'py-8 lg:py-12',
        bottom: 'py-8 lg:py-12',
        container: 'py-8 lg:py-4 lg:flex lg:items-center lg:justify-between lg:gap-x-3',
        left: 'flex items-center justify-center lg:justify-start lg:flex-1 gap-x-1.5 mt-3 lg:mt-0 lg:order-1',
        center: 'mt-3 lg:mt-0 lg:order-2 flex items-center justify-center',
        right: 'lg:flex-1 flex items-center justify-center lg:justify-end gap-x-1.5 lg:order-3'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Footer.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/footer.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

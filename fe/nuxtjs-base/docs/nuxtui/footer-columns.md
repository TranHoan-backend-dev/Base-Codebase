---
title: "FooterColumns"
description: "A list of links as columns to display in your Footer."
canonical_url: "https://ui.nuxt.com/docs/components/footer-columns"
---
# FooterColumns

> A list of links as columns to display in your Footer.

## Usage

The FooterColumns component renders a list of columns to display in your Footer.

Use it in the `top` slot of the [Footer](/docs/components/footer) component:

```vue
<template>
  <UFooter>
    <template #top>
      <UContainer>
        <UFooterColumns />
      </UContainer>
    </template>
  </UFooter>
</template>
```

### Columns

Use the `columns` prop as an array of objects with the following properties:

- `label: string`
- `children?: FooterColumnLink[]`

Each column contains a `children` array of objects that define the links. Each link can have the following properties:

- `label?: string`
- `icon?: string`
- `class?: any`
- `ui?: { item?: ClassNameValue, link?: ClassNameValue, linkLabel?: ClassNameValue, linkLabelExternalIcon?: ClassNameValue, linkLeadingIcon?: ClassNameValue }`

You can pass any property from the [Link](/docs/components/link#props) component such as `to`, `target`, etc.

```vue [FooterColumnsExample.vue]
<script setup lang="ts">
import type { FooterColumn } from '@nuxt/ui'

const columns: FooterColumn[] = [{
  label: 'Community',
  children: [{
    label: 'Nuxters',
    to: 'https://nuxters.nuxt.com',
    target: '_blank'
  }, {
    label: 'Video Courses',
    to: 'https://masteringnuxt.com/nuxt3?ref=nuxt',
    target: '_blank'
  }, {
    label: 'Nuxt on GitHub',
    to: 'https://github.com/nuxt',
    target: '_blank'
  }]
}, {
  label: 'Solutions',
  children: [{
    label: 'Nuxt Content',
    to: 'https://content.nuxt.com/',
    target: '_blank'
  }, {
    label: 'Nuxt DevTools',
    to: 'https://devtools.nuxt.com/',
    target: '_blank'
  }, {
    label: 'Nuxt Image',
    to: 'https://image.nuxt.com/',
    target: '_blank'
  }, {
    label: 'Nuxt UI',
    to: 'https://ui.nuxt.com/',
    target: '_blank'
  }]
}]
</script>

<template>
  <UFooterColumns :columns="columns">
    <template #right>
      <UFormField name="email" label="Subscribe to our newsletter" size="lg">
        <UInput type="email" class="w-full">
          <template #trailing>
            <UButton type="submit" size="xs" color="neutral" label="Subscribe" />
          </template>
        </UInput>
      </UFormField>
    </template>
  </UFooterColumns>
</template>
```

## API

### Props

```ts
/**
 * Props for the FooterColumns component
 */
interface FooterColumnsProps {
  /**
   * The element or component this component should render as.
   * @default "\"nav\""
   */
  as?: any;
  columns?: FooterColumn<T>[] | undefined;
  ui?: { root?: SlotClass; left?: SlotClass; center?: SlotClass; right?: SlotClass; label?: SlotClass; list?: SlotClass; item?: SlotClass; link?: SlotClass; linkLeadingIcon?: SlotClass; linkLabel?: SlotClass; linkLabelExternalIcon?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the FooterColumns component
 */
interface FooterColumnsSlots {
  left(): any;
  default(): any;
  right(): any;
  column-label(): any;
  link(): any;
  link-leading(): any;
  link-label(): any;
  link-trailing(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    footerColumns: {
      slots: {
        root: 'xl:grid xl:grid-cols-3 xl:gap-8',
        left: 'mb-10 xl:mb-0',
        center: 'flex flex-col lg:grid grid-flow-col auto-cols-fr gap-8 xl:col-span-2',
        right: 'mt-10 xl:mt-0',
        label: 'text-sm font-semibold',
        list: 'mt-6 space-y-4',
        item: 'relative',
        link: 'group text-sm flex items-center gap-1.5 rounded-sm outline-primary/25 focus-visible:outline-3',
        linkLeadingIcon: 'size-5 shrink-0',
        linkLabel: 'truncate',
        linkLabelExternalIcon: 'size-3 absolute top-0 text-dimmed inline-block'
      },
      variants: {
        active: {
          true: {
            link: 'text-primary font-medium'
          },
          false: {
            link: [
              'text-muted hover:text-default',
              'transition-colors'
            ]
          }
        }
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/FooterColumns.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/footer-columns.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

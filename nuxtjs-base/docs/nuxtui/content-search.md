---
title: "ContentSearch"
description: "A ready to use CommandPalette to add to your documentation."
canonical_url: "https://ui.nuxt.com/docs/components/content-search"
---
# ContentSearch

> A ready to use CommandPalette to add to your documentation.

> [!WARNING]
> See: /docs/getting-started/integrations/content
> 
> This component is only available when the `@nuxt/content` module is installed.

## Usage

The ContentSearch component extends the [CommandPalette](/docs/components/command-palette) component with built-in [`@nuxt/content`](https://content.nuxt.com) search support, navigation grouping and color mode commands. It supports both client-side [Fuse.js](https://www.fusejs.io/) filtering and server-side [FTS5 full-text search](https://www.sqlite.org/fts5.html). You can pass any CommandPalette property such as `icon`, `placeholder`, etc.

```vue [ContentSearchExample.vue]
<script setup lang="ts">
import type { ContentNavigationItem } from '@nuxt/content'

const { data: files } = useLazyAsyncData('content-search-example', () => queryCollectionSearchSections('docs', {
  ignoredTags: ['style']
}), {
  server: false
})

const navigation = inject<Ref<ContentNavigationItem[]>>('navigation')

const searchTerm = ref('')
</script>

<template>
  <ClientOnly>
    <LazyUContentSearch
      v-model:search-term="searchTerm"
      open
      :autofocus="false"
      :files="files"
      :navigation="navigation"
      :fuse="{ resultLimit: 42 }"
    />
  </ClientOnly>
</template>
```

> [!NOTE]
> 
> You can open the CommandPalette by pressing <kbd value="meta">
> 
> 
> 
> </kbd>
> 
>  <kbd value="K" className="ms-px">
> 
> 
> 
> </kbd>
> 
> , by using the [ContentSearchButton](/docs/components/content-search-button) component or by using the `useContentSearch` composable: `const { open } = useContentSearch()`.

> [!TIP]
> 
> It is recommended to wrap the `ContentSearch` component in a [ClientOnly](https://nuxt.com/docs/api/components/client-only) component so it's not rendered on the server.

### Navigation

Use the `navigation` prop with [`queryCollectionNavigation`](https://content.nuxt.com/docs/utils/query-collection-navigation) to group search results by section:

```vue [app.vue]
<script setup lang="ts">
const { data: navigation } = await useAsyncData('navigation', () => queryCollectionNavigation('content'))
</script>

<template>
  <UApp>
    <ClientOnly>
      <LazyUContentSearch
        :navigation="navigation"
      />
    </ClientOnly>
  </UApp>
</template>
```

### Files

Use the `files` prop with [`queryCollectionSearchSections`](https://content.nuxt.com/docs/utils/query-collection-search-sections) to load all search sections upfront and use client-side [Fuse.js](https://www.fusejs.io/) filtering:

```vue [app.vue]
<script setup lang="ts">
const { data: navigation } = await useAsyncData('navigation', () => queryCollectionNavigation('content'))

const { data: files } = useLazyAsyncData('search', () => queryCollectionSearchSections('docs', {
  ignoredTags: ['style']
}), {
  server: false
})
</script>

<template>
  <UApp>
    <ClientOnly>
      <LazyUContentSearch
        :navigation="navigation"
        :files="files"
        :fuse="{ resultLimit: 20, fuseOptions: { threshold: 0.2 } }"
      />
    </ClientOnly>
  </UApp>
</template>
```

> [!TIP]
> 
> Use the `fuse` prop to configure [useFuse](https://vueuse.org/integrations/useFuse) options passed to the underlying [CommandPalette](/docs/components/command-palette) such as `resultLimit` (default `12`) and `fuseOptions.threshold` (default `0.1`).

### Search `4.8+`

Use the `search` prop with [`useSearchCollection`](https://content.nuxt.com/docs/utils/use-search-collection) for server-side [FTS5 full-text search](https://www.sqlite.org/fts5.html) with highlighted snippets instead of client-side filtering:

> [!WARNING]
> 
> Requires `@nuxt/content` v3.14+.

```vue [app.vue]
<script setup lang="ts">
const { data: navigation } = await useAsyncData('navigation', () => queryCollectionNavigation('content'))

const { search, status, init } = useSearchCollection('content', {
  immediate: false,
  ignoredTags: ['style']
})

const { open } = useContentSearch()

// Defer index initialization until the user opens the palette when using `immediate: false`
watch(open, (value) => {
  if (value && status.value === 'idle') {
    init()
  }
})
</script>

<template>
  <UApp>
    <ClientOnly>
      <LazyUContentSearch
        :navigation="navigation"
        :search="search"
        :search-status="status"
      />
    </ClientOnly>
  </UApp>
</template>
```

> [!TIP]
> 
> Pass `search-status` so the component can automatically re-trigger the search once the index becomes ready. Use `search-delay` (default `100ms`) to control how long typing must pause before the search fires. The `fuse.resultLimit` option caps the total results returned across all groups (search results, links, theme, etc.).

> [!NOTE]
> 
> When using the `search` prop, you don't need to pass `files`. The component calls the async search function on each keystroke instead of Fuse.js. Results are automatically mapped and grouped by navigation with highlighted snippets. Unlike the `files` approach which loads all search sections upfront and lets you browse navigation items before typing, the `search` prop only returns results after a query is entered.

### Shortcut

Use the `shortcut` prop to change the shortcut used in [defineShortcuts](/docs/composables/define-shortcuts) to open the ContentSearch component. Defaults to `meta_k` (<kbd value="meta">



</kbd>

 <kbd value="K">



</kbd>

).

```vue [app.vue]
<template>
  <UApp>
    <ClientOnly>
      <LazyUContentSearch
        shortcut="meta_k"
      />
    </ClientOnly>
  </UApp>
</template>
```

### Links

Use the `links` prop to add a group of quick-access links at the top of the command palette:

```vue [app.vue]
<script setup lang="ts">
const links = [{
  label: 'Docs',
  icon: 'i-lucide-book',
  to: '/docs/getting-started'
}, {
  label: 'Components',
  icon: 'i-lucide-box',
  to: '/docs/components'
}, {
  label: 'Showcase',
  icon: 'i-lucide-presentation',
  to: '/showcase'
}]
</script>

<template>
  <UApp>
    <ClientOnly>
      <LazyUContentSearch
        :links="links"
      />
    </ClientOnly>
  </UApp>
</template>
```

### Color Mode

By default, a group of commands will be added to the command palette so you can switch between light and dark mode. This will only take effect if the `colorMode` is not forced in a specific page which can be achieved through `definePageMeta`:

```vue [pages/index.vue]
<script setup lang="ts">
definePageMeta({
  colorMode: 'dark'
})
</script>
```

You can disable this behavior by setting the `color-mode` prop to `false`:

```vue [app.vue]
<template>
  <UApp>
    <ClientOnly>
      <LazyUContentSearch
        :color-mode="false"
      />
    </ClientOnly>
  </UApp>
</template>
```

## API

### Props

```ts
/**
 * Props for the ContentSearch component
 */
interface ContentSearchProps {
  size?: "sm" | "md" | "xs" | "lg" | "xl" | undefined;
  /**
   * Display a close button in the input (useful when inside a Modal for example).
   * `{ size: 'md', color: 'neutral', variant: 'ghost' }`{lang="ts-type"}
   * @default "true"
   */
  close?: boolean | Omit<ButtonProps, LinkPropsKeys> | undefined;
  /**
   * Configure the input or hide it with `false`.
   * `{ fixed: true }`{lang="ts-type"}
   */
  input?: boolean | Omit<InputProps<AcceptableValue, ModelModifiers>, "modelValue" | "defaultValue"> | undefined;
  /**
   * Keyboard shortcut to open the search (used by [`defineShortcuts`](https://ui.nuxt.com/docs/composables/define-shortcuts))
   * @default "\"meta_k\""
   */
  shortcut?: string | undefined;
  /**
   * Links group displayed as the first group in the command palette.
   */
  links?: T[] | undefined;
  navigation?: ContentNavigationItem[] | undefined;
  files?: ContentSearchFile[] | undefined;
  /**
   * Options for [useFuse](https://vueuse.org/integrations/useFuse) passed to the [CommandPalette](https://ui.nuxt.com/docs/components/command-palette).
   */
  fuse?: UseFuseOptions<T> | undefined;
  /**
   * Async search function (e.g. from [`useSearchCollection`](https://content.nuxt.com/docs/utils/use-search-collection)).
   * When provided, ContentSearch calls it on each keystroke and uses the results instead of Fuse.
   * Results are mapped, sanitized, and grouped by navigation internally.
   */
  search?: ContentSearchFn | undefined;
  /**
   * Status of the async search index (e.g. from `useSearchCollection`).
   * When the status transitions to `'ready'`, the search is automatically re-triggered if there's a pending term.
   */
  searchStatus?: ContentSearchStatus | undefined;
  /**
   * Delay (in milliseconds) before the search is triggered (debounced).
   * Keeps the input responsive by only running the search after typing settles.
   * Set to `0` to disable.
   * @default "100"
   */
  searchDelay?: number | undefined;
  /**
   * When `true`, the theme command will be added to the groups.
   * @default "true"
   */
  colorMode?: boolean | undefined;
  ui?: ({ modal?: SlotClass; input?: SlotClass; } & { root?: SlotClass; input?: SlotClass; close?: SlotClass; back?: SlotClass; content?: SlotClass; footer?: SlotClass; viewport?: SlotClass; group?: SlotClass; empty?: SlotClass; label?: SlotClass; item?: SlotClass; itemLeadingIcon?: SlotClass; itemLeadingAvatar?: SlotClass; itemLeadingAvatarSize?: SlotClass; itemLeadingChip?: SlotClass; itemLeadingChipSize?: SlotClass; itemTrailing?: SlotClass; itemTrailingIcon?: SlotClass; itemTrailingHighlightedIcon?: SlotClass; itemTrailingKbds?: SlotClass; itemTrailingKbdsSize?: SlotClass; itemWrapper?: SlotClass; itemLabel?: SlotClass; itemLabelBase?: SlotClass; itemLabelPrefix?: SlotClass; itemLabelSuffix?: SlotClass; itemDescription?: SlotClass; }) | undefined;
  title?: string | undefined;
  description?: string | undefined;
  /**
   * Render an overlay behind the modal.
   */
  overlay?: boolean | undefined;
  /**
   * Animate the modal when opening or closing.
   */
  transition?: boolean | undefined;
  /**
   * The content of the modal.
   */
  content?: (Omit<DialogContentProps, "as" | "asChild" | "forceMount"> & Partial<EmitsToProps<DialogContentImplEmits>>) | undefined;
  /**
   * When `false`, the modal will not close when clicking outside or pressing escape.
   */
  dismissible?: boolean | undefined;
  /**
   * When `true`, the modal will take up the full screen.
   * @default "false"
   */
  fullscreen?: boolean | undefined;
  /**
   * The modality of the dialog When set to `true`, <br>
   * interaction with outside elements will be disabled and only dialog content will be visible to screen readers.
   */
  modal?: boolean | undefined;
  /**
   * Render the modal in a portal.
   */
  portal?: string | boolean | HTMLElement | undefined;
  /**
   * The icon displayed in the input. Set to `false` to hide the icon.
   */
  icon?: any;
  /**
   * The icon displayed on the right side of the input.
   */
  trailingIcon?: any;
  /**
   * The icon displayed when an item is selected.
   */
  selectedIcon?: any;
  /**
   * The icon displayed when an item has children.
   */
  childrenIcon?: any;
  /**
   * The placeholder text for the input.
   */
  placeholder?: string | undefined;
  /**
   * Automatically focus the input when component is mounted.
   */
  autofocus?: boolean | undefined;
  /**
   * When `true`, the loading icon will be displayed.
   */
  loading?: boolean | undefined;
  /**
   * The icon when the `loading` prop is `true`.
   */
  loadingIcon?: any;
  /**
   * The icon displayed in the close button.
   */
  closeIcon?: any;
  /**
   * Display a button to navigate back in history.
   * `{ size: 'md', color: 'neutral', variant: 'link' }`{lang="ts-type"}
   */
  back?: boolean | Omit<ButtonProps, LinkPropsKeys> | undefined;
  /**
   * The icon displayed in the back button.
   */
  backIcon?: any;
  /**
   * When `true`, prevents the user from interacting with listbox
   */
  disabled?: boolean | undefined;
  /**
   * When `true`, hover over item will trigger highlight
   */
  highlightOnHover?: boolean | undefined;
  /**
   * The key used to get the label from the item.
   */
  labelKey?: string | undefined;
  /**
   * The key used to get the description from the item.
   */
  descriptionKey?: string | undefined;
  /**
   * Whether to preserve the order of groups as defined in the `groups` prop when filtering.
   * When `false`, groups will appear based on item matches.
   */
  preserveGroupOrder?: boolean | undefined;
  /**
   * Enable virtualization for large lists.
   * Note: when enabled, all groups are flattened into a single list due to a limitation of Reka UI (https://github.com/unovue/reka-ui/issues/1885).
   */
  virtualize?: boolean | { overscan?: number | undefined; estimateSize?: number | ((index: number) => number) | undefined; } | undefined;
  groups?: CommandPaletteGroup<ContentSearchItem>[] | undefined;
  /**
   * @default "\"\""
   */
  searchTerm?: string | undefined;
}
```

### Slots

```ts
/**
 * Slots for the ContentSearch component
 */
interface ContentSearchSlots {
  empty(): any;
  footer(): any;
  back(): any;
  close(): any;
  item(): any;
  item-leading(): any;
  item-label(): any;
  item-description(): any;
  item-trailing(): any;
  group-label(): any;
  content(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the ContentSearch component
 */
interface ContentSearchEmits {
  update:searchTerm: (payload: [value: string]) => void;
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
          commandPaletteRef
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
          InstanceType
        </span>
        
        <span class="sMK4o">
          <typeof
        </span>
        
        <span class="sTEyZ">
          UCommandPalette
        </span>
        
        <span class="sMK4o">
          >
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
    contentSearch: {
      slots: {
        modal: '',
        input: ''
      },
      variants: {
        fullscreen: {
          false: {
            modal: 'sm:max-w-3xl h-full sm:h-[28rem]'
          }
        },
        size: {
          xs: {},
          sm: {},
          md: {},
          lg: {},
          xl: {}
        }
      },
      defaultVariants: {
        size: 'md'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/content/ContentSearch.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/content/content-search.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

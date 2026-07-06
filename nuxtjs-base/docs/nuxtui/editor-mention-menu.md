---
title: "EditorMentionMenu"
description: "A mention menu that displays user suggestions when typing a trigger character in the editor."
canonical_url: "https://ui.nuxt.com/docs/components/editor-mention-menu"
---
# EditorMentionMenu

> A mention menu that displays user suggestions when typing a trigger character in the editor.

## Usage

The EditorMentionMenu component displays a menu of user suggestions when typing a trigger character (defaults to `@`) in the editor and inserts the selected mention using the `@tiptap/extension-mention` package. The trigger character is also used as the prefix when rendering the inserted mention.

> [!NOTE]
> 
> It uses the `useEditorMenu` composable built on top of TipTap's [Suggestion](https://tiptap.dev/docs/editor/api/utilities/suggestion) utility to filter items as you type and support keyboard navigation (arrow keys, enter to select, escape to close).

> [!CAUTION]
> 
> It must be used inside an [Editor](/docs/components/editor) component's default slot to have access to the editor instance.

```vue [EditorMentionMenuExample.vue]
<script setup lang="ts">
import type { EditorMentionMenuItem } from '@nuxt/ui'

const value = ref(`# Mention Menu

Type @ to mention someone and select from the list of available users.`)

const items: EditorMentionMenuItem[] = [{
  label: 'benjamincanac',
  avatar: {
    src: 'https://avatars.githubusercontent.com/u/739984?v=4',
    loading: 'lazy' as const
  }
}, {
  label: 'atinux',
  avatar: {
    src: 'https://avatars.githubusercontent.com/u/904724?v=4',
    loading: 'lazy' as const
  }
}, {
  label: 'danielroe',
  avatar: {
    src: 'https://avatars.githubusercontent.com/u/28706372?v=4',
    loading: 'lazy' as const
  }
}, {
  label: 'pi0',
  avatar: {
    src: 'https://avatars.githubusercontent.com/u/5158436?v=4',
    loading: 'lazy' as const
  }
}]

// SSR-safe function to append menus to body (avoids z-index issues in docs)
const appendToBody = import.meta.client ? () => document.body : undefined
</script>

<template>
  <UEditor
    v-slot="{ editor }"
    v-model="value"
    content-type="markdown"
    placeholder="Type @ to mention someone..."
    class="w-full min-h-21"
  >
    <UEditorMentionMenu :editor="editor" :items="items" :append-to="appendToBody" />
  </UEditor>
</template>
```

> [!NOTE]
> See: https://tiptap.dev/docs/editor/extensions/nodes/mention
> 
> Learn more about the Mention extension in the TipTap documentation.

### Items

Use the `items` prop as an array of objects with the following properties:

- `label: string`
- `avatar?: AvatarProps`
- `icon?: string`
- `description?: string`
- `disabled?: boolean`

```vue [EditorMentionMenuItemsExample.vue]
<script setup lang="ts">
import type { EditorMentionMenuItem } from '@nuxt/ui'

const value = ref(`Type @ to mention a user.

You can customize the items with avatars, icons, and descriptions.`)

const items: EditorMentionMenuItem[] = [{
  label: 'benjamincanac',
  avatar: { src: 'https://avatars.githubusercontent.com/u/739984?v=4', loading: 'lazy' as const }
}, {
  label: 'HugoRCD',
  avatar: { src: 'https://avatars.githubusercontent.com/u/71938701?v=4', loading: 'lazy' as const }
}, {
  label: 'romhml',
  avatar: { src: 'https://avatars.githubusercontent.com/u/25613751?v=4', loading: 'lazy' as const }
}, {
  label: 'sandros94',
  avatar: { src: 'https://avatars.githubusercontent.com/u/13056429?v=4', loading: 'lazy' as const }
}, {
  label: 'hywax',
  avatar: { src: 'https://avatars.githubusercontent.com/u/149865959?v=4', loading: 'lazy' as const }
}, {
  label: 'J-Michalek',
  avatar: { src: 'https://avatars.githubusercontent.com/u/71264422?v=4', loading: 'lazy' as const }
}, {
  label: 'genu',
  avatar: { src: 'https://avatars.githubusercontent.com/u/928780?v=4', loading: 'lazy' as const }
}]
</script>

<template>
  <UEditor
    v-slot="{ editor }"
    v-model="value"
    content-type="markdown"
    placeholder="Type @ to mention..."
    class="w-full min-h-19"
  >
    <UEditorMentionMenu :editor="editor" :items="items" />
  </UEditor>
</template>
```

> [!NOTE]
> 
> You can also pass an array of arrays to the `items` prop to create separated groups of items.

### Char

Use the `char` prop to change the trigger character. Defaults to `@`. The trigger character is also used as the prefix when rendering the inserted mention (e.g. `#channel` instead of `@channel`).

```vue
<template>
  <UEditor v-slot="{ editor }">
    <UEditorMentionMenu :editor="editor" :items="channels" char="#" />
  </UEditor>
</template>
```

> [!NOTE]
> 
> You can use multiple `EditorMentionMenu` components on the same editor with different `char` and `plugin-key` props to support different mention types.
> 
> ```vue
> <template>
>   <UEditor v-slot="{ editor }">
>     <UEditorMentionMenu :editor="editor" :items="users" plugin-key="mentionMenu" />
>     <UEditorMentionMenu :editor="editor" :items="tags" char="#" plugin-key="tagMenu" />
>   </UEditor>
> </template>
> ```

### Suggestion `4.7+`

Use the `suggestion` prop to customize TipTap's [Suggestion matching behavior](https://tiptap.dev/docs/editor/api/utilities/suggestion#settings).

This is useful when the trigger character should open directly after other characters instead of requiring the default whitespace prefix.

```vue
<template>
  <UEditor v-slot="{ editor }">
    <UEditorMentionMenu
      :editor="editor"
      :items="items"
      char="#"
      :suggestion="{
        allowedPrefixes: null
      }"
    />
  </UEditor>
</template>
```

### Options

Use the `options` prop to customize the positioning behavior using [Floating UI options](https://floating-ui.com/docs/computeposition#options).

```vue
<template>
  <UEditor v-slot="{ editor }">
    <UEditorMentionMenu
      :editor="editor"
      :items="items"
      :options="{
        placement: 'bottom-start',
        offset: 4
      }"
    />
  </UEditor>
</template>
```

## Examples

### With ignore filter `4.4+`

You can set the `ignore-filter` prop to `true` to disable the internal search and use your own search logic. Use `v-model:search-term` to access the current search term and fetch items from an API.

```vue [EditorMentionMenuIgnoreFilterExample.vue]
<script setup lang="ts">
import { refDebounced } from '@vueuse/core'

const value = ref(`# Async Mention Menu

Type @ to mention someone. Results are fetched from an API as you type.`)

const searchTerm = ref('')
const searchTermDebounced = refDebounced(searchTerm, 200)

const { data: items } = useLazyFetch('https://dummyjson.com/users/search?limit=10', {
  key: 'editor-mention-users-search',
  params: { q: searchTermDebounced },
  transform: (data: { users: { id: number, firstName: string, lastName: string, image: string }[] }) => {
    return data.users?.map(user => ({ id: user.id, label: `${user.firstName} ${user.lastName}`, avatar: { src: user.image, loading: 'lazy' as const } })) || []
  },
  server: false
})

// SSR-safe function to append menus to body (avoids z-index issues in docs)
const appendToBody = import.meta.client ? () => document.body : undefined
</script>

<template>
  <UEditor
    v-slot="{ editor }"
    v-model="value"
    content-type="markdown"
    placeholder="Type @ to mention someone..."
    class="w-full min-h-21"
  >
    <UEditorMentionMenu
      v-model:search-term="searchTerm"
      :editor="editor"
      :items="items"
      :append-to="appendToBody"
      ignore-filter
    />
  </UEditor>
</template>
```

> [!NOTE]
> 
> This example uses [`refDebounced`](https://vueuse.org/shared/refDebounced/) to debounce the API calls.

## API

### Props

```ts
/**
 * Props for the EditorMentionMenu component
 */
interface EditorMentionMenuProps {
  size?: "xs" | "md" | "sm" | "lg" | "xl" | undefined;
  items?: T[] | T[][] | undefined;
  ui?: { content?: SlotClass; viewport?: SlotClass; group?: SlotClass; label?: SlotClass; separator?: SlotClass; item?: SlotClass; itemLeadingIcon?: SlotClass; itemLeadingAvatar?: SlotClass; itemLeadingAvatarSize?: SlotClass; itemWrapper?: SlotClass; itemLabel?: SlotClass; itemDescription?: SlotClass; itemLabelExternalIcon?: SlotClass; } | undefined;
  editor?: Editor;
  /**
   * The trigger character (e.g., '/', '@', ':')
   * @default "\"@\""
   */
  char?: string | undefined;
  /**
   * Plugin key to identify this menu
   * @default "\"mentionMenu\""
   */
  pluginKey?: string | undefined;
  /**
   * Fields to filter items by.
   */
  filterFields?: string[] | undefined;
  /**
   * Maximum number of items to display
   */
  limit?: number | undefined;
  /**
   * The options for positioning the menu. Those are passed to Floating UI and include options for the placement, offset, flip, shift, size, autoPlacement, hide, and inline middleware.
   */
  options?: FloatingUIOptions | undefined;
  /**
   * Optional TipTap Suggestion matching options.
   */
  suggestion?: Omit<Partial<SuggestionOptions<any, any>>, "editor" | "char" | "pluginKey" | "items" | "command" | "render"> | undefined;
  /**
   * The DOM element to append the menu to. Default is the editor's parent element.
   * 
   * Sometimes the menu needs to be appended to a different DOM context due to accessibility, clipping, or z-index issues.
   */
  appendTo?: HTMLElement | (() => HTMLElement) | undefined;
  /**
   * Whether to ignore the default filtering.
   * When `true`, items will not be filtered which is useful for custom filtering (useAsyncData, useFetch, etc.).
   */
  ignoreFilter?: boolean | undefined;
  /**
   * @default "\"\""
   */
  searchTerm?: string | undefined;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    editorMentionMenu: {
      slots: {
        content: 'min-w-48 max-w-60 max-h-96 bg-default shadow-lg rounded-md ring ring-default overflow-hidden data-[state=open]:animate-[scale-in_100ms_ease-out] data-[state=closed]:animate-[scale-out_100ms_ease-in] origin-(--reka-dropdown-menu-content-transform-origin) flex flex-col',
        viewport: 'relative divide-y divide-default scroll-py-1 overflow-y-auto flex-1',
        group: 'p-1 isolate',
        label: 'w-full flex items-center font-semibold text-highlighted',
        separator: '-mx-1 my-1 h-px bg-border',
        item: 'group relative w-full flex items-start select-none outline-none before:absolute before:z-[-1] before:inset-px before:rounded-md data-disabled:cursor-not-allowed data-disabled:opacity-75',
        itemLeadingIcon: 'shrink-0 flex items-center justify-center',
        itemLeadingAvatar: 'shrink-0',
        itemLeadingAvatarSize: '',
        itemWrapper: 'flex-1 flex flex-col text-start min-w-0',
        itemLabel: 'truncate',
        itemDescription: 'truncate text-muted',
        itemLabelExternalIcon: 'inline-block size-3 align-top text-dimmed'
      },
      variants: {
        size: {
          xs: {
            label: 'p-1 text-[10px]/3 gap-1',
            item: 'p-1 text-xs gap-1',
            itemLeadingIcon: 'size-4 text-sm',
            itemLeadingAvatarSize: '3xs'
          },
          sm: {
            label: 'p-1.5 text-[10px]/3 gap-1.5',
            item: 'p-1.5 text-xs gap-1.5',
            itemLeadingIcon: 'size-4 text-sm',
            itemLeadingAvatarSize: '3xs'
          },
          md: {
            label: 'p-1.5 text-xs gap-1.5',
            item: 'p-1.5 text-sm gap-1.5',
            itemLeadingIcon: 'size-5 text-base',
            itemLeadingAvatarSize: '2xs'
          },
          lg: {
            label: 'p-2 text-xs gap-2',
            item: 'p-2 text-sm gap-2',
            itemLeadingIcon: 'size-5 text-base',
            itemLeadingAvatarSize: '2xs'
          },
          xl: {
            label: 'p-2 text-sm gap-2',
            item: 'p-2 text-base gap-2',
            itemLeadingIcon: 'size-6 text-xl',
            itemLeadingAvatarSize: 'xs'
          }
        },
        active: {
          true: {
            item: 'text-highlighted before:bg-elevated/75',
            itemLeadingIcon: 'text-default'
          },
          false: {
            item: [
              'text-default data-highlighted:not-data-disabled:text-highlighted data-highlighted:not-data-disabled:before:bg-elevated/50',
              'transition-colors before:transition-colors'
            ],
            itemLeadingIcon: [
              'text-dimmed group-data-highlighted:not-group-data-disabled:text-default',
              'transition-colors'
            ]
          }
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

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/EditorMentionMenu.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/editor-mention-menu.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

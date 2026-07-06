---
title: "EditorEmojiMenu"
description: "An emoji picker menu that displays emoji suggestions when typing the : character in the editor."
canonical_url: "https://ui.nuxt.com/docs/components/editor-emoji-menu"
---
# EditorEmojiMenu

> An emoji picker menu that displays emoji suggestions when typing the : character in the editor.

## Usage

The EditorEmojiMenu component displays a menu of emoji suggestions when typing the `:` character in the editor and inserts the selected emoji. It works alongside the `@tiptap/extension-emoji` package to provide emoji support.

> [!NOTE]
> 
> It uses the `useEditorMenu` composable built on top of TipTap's [Suggestion](https://tiptap.dev/docs/editor/api/utilities/suggestion) utility to filter items as you type and support keyboard navigation (arrow keys, enter to select, escape to close).

> [!CAUTION]
> 
> It must be used inside an [Editor](/docs/components/editor) component's default slot to have access to the editor instance.

```vue [EditorEmojiMenuExample.vue]
<script setup lang="ts">
import type { EditorEmojiMenuItem } from '@nuxt/ui'
import { Emoji, gitHubEmojis } from '@tiptap/extension-emoji'

const value = ref(`# Emoji Menu

Type : to insert emojis and select from the list of available emojis.`)

const items: EditorEmojiMenuItem[] = gitHubEmojis.filter(emoji => !emoji.name.startsWith('regional_indicator_'))

// SSR-safe function to append menus to body (avoids z-index issues in docs)
const appendToBody = import.meta.client ? () => document.body : undefined
</script>

<template>
  <UEditor
    v-slot="{ editor }"
    v-model="value"
    :extensions="[Emoji]"
    content-type="markdown"
    placeholder="Type : to add emojis..."
    class="w-full min-h-21"
  >
    <UEditorEmojiMenu :editor="editor" :items="items" :append-to="appendToBody" />
  </UEditor>
</template>
```

> [!WARNING]
> 
> The `@tiptap/extension-emoji` package is not installed by default, you need to install it separately.

> [!NOTE]
> See: https://tiptap.dev/docs/editor/extensions/nodes/emoji
> 
> Learn more about the Emoji extension in the TipTap documentation.

### Items

Use the `items` prop as an array of objects with the following properties:

- `name: string`
- `emoji: string`
- `shortcodes?: string[]`
- `tags?: string[]`
- `group?: string`
- `fallbackImage?: string`

```vue [EditorEmojiMenuItemsExample.vue]
<script setup lang="ts">
import type { EditorEmojiMenuItem } from '@nuxt/ui'
import { Emoji } from '@tiptap/extension-emoji'

const value = ref(`Type : to see a custom emoji set.

You can also install the \`@tiptap/extension-emoji\` extension to use a comprehensive set with over 1800 emojis.`)

const items: EditorEmojiMenuItem[] = [{
  name: 'smile',
  emoji: '😄',
  shortcodes: ['smile'],
  tags: ['happy', 'joy', 'pleased']
}, {
  name: 'heart',
  emoji: '❤️',
  shortcodes: ['heart'],
  tags: ['love', 'like']
}, {
  name: 'thumbsup',
  emoji: '👍',
  shortcodes: ['thumbsup', '+1'],
  tags: ['approve', 'ok']
}, {
  name: 'fire',
  emoji: '🔥',
  shortcodes: ['fire'],
  tags: ['hot', 'burn']
}, {
  name: 'rocket',
  emoji: '🚀',
  shortcodes: ['rocket'],
  tags: ['ship', 'launch']
}, {
  name: 'eyes',
  emoji: '👀',
  shortcodes: ['eyes'],
  tags: ['look', 'watch']
}, {
  name: 'tada',
  emoji: '🎉',
  shortcodes: ['tada'],
  tags: ['party', 'celebration']
}, {
  name: 'thinking',
  emoji: '🤔',
  shortcodes: ['thinking'],
  tags: ['hmm', 'think', 'consider']
}]
</script>

<template>
  <UEditor
    v-slot="{ editor }"
    v-model="value"
    :extensions="[Emoji]"
    content-type="markdown"
    placeholder="Type : to add emojis..."
    class="w-full min-h-26"
  >
    <UEditorEmojiMenu :editor="editor" :items="items" />
  </UEditor>
</template>
```

> [!NOTE]
> 
> You can also pass an array of arrays to the `items` prop to create separated groups of items.

### Char

Use the `char` prop to change the trigger character. Defaults to `:`.

```vue
<template>
  <UEditor v-slot="{ editor }">
    <UEditorEmojiMenu :editor="editor" :items="items" char=";" />
  </UEditor>
</template>
```

### Suggestion `4.7+`

Use the `suggestion` prop to customize TipTap's [Suggestion matching behavior](https://tiptap.dev/docs/editor/api/utilities/suggestion#settings).

This is useful when the trigger character should open directly after other characters instead of requiring the default whitespace prefix.

```vue
<template>
  <UEditor v-slot="{ editor }">
    <UEditorEmojiMenu
      :editor="editor"
      :items="items"
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
    <UEditorEmojiMenu
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

## API

### Props

```ts
/**
 * Props for the EditorEmojiMenu component
 */
interface EditorEmojiMenuProps {
  size?: "xs" | "md" | "sm" | "lg" | "xl" | undefined;
  items?: T[] | T[][] | undefined;
  ui?: { content?: SlotClass; viewport?: SlotClass; group?: SlotClass; label?: SlotClass; separator?: SlotClass; item?: SlotClass; itemLeadingIcon?: SlotClass; itemLeadingAvatar?: SlotClass; itemLeadingAvatarSize?: SlotClass; itemWrapper?: SlotClass; itemLabel?: SlotClass; itemDescription?: SlotClass; itemLabelExternalIcon?: SlotClass; } | undefined;
  editor?: Editor;
  /**
   * The trigger character (e.g., '/', '@', ':')
   * @default "\":\""
   */
  char?: string | undefined;
  /**
   * Plugin key to identify this menu
   * @default "\"emojiMenu\""
   */
  pluginKey?: string | undefined;
  /**
   * Fields to filter items by.
   * @default "[\"name\", \"shortcodes\", \"tags\"]"
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
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    editorEmojiMenu: {
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

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/EditorEmojiMenu.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/editor-emoji-menu.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

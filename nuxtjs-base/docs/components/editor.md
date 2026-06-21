---
title: "Editor"
description: "A rich text editor component based on TipTap with support for markdown, HTML, and JSON content types."
canonical_url: "https://ui.nuxt.com/docs/components/editor"
---
# Editor

> A rich text editor component based on TipTap with support for markdown, HTML, and JSON content types.

## Usage

The Editor component provides a powerful rich text editing experience built on [TipTap](https://tiptap.dev/). It supports multiple content formats (JSON, HTML, Markdown), customizable toolbars, drag-and-drop block reordering, slash commands, mentions, emoji picker, and extensible architecture for adding custom functionality.

```vue [EditorExample.vue]
<script setup lang="ts">
import type { EditorCustomHandlers, EditorToolbarItem, EditorSuggestionMenuItem, EditorMentionMenuItem, EditorEmojiMenuItem, DropdownMenuItem } from '@nuxt/ui'
import type { Editor, JSONContent } from '@tiptap/vue-3'
import { upperFirst } from 'scule'
import { mapEditorItems } from '@nuxt/ui/utils/editor'
import { Emoji, gitHubEmojis } from '@tiptap/extension-emoji'
import { TextAlign } from '@tiptap/extension-text-align'
import { CodeBlockShiki } from 'tiptap-extension-code-block-shiki'
import { ImageUpload } from './EditorImageUploadExtension'
import { useEditorCompletion } from './EditorUseCompletion'
import EditorLinkPopover from './EditorLinkPopover.vue'

const editorRef = useTemplateRef('editorRef')

const value = ref(`# Building Modern Interfaces with Nuxt UI

Welcome to the **Nuxt UI Editor** — a powerful rich text editing experience built on [TipTap](https://tiptap.dev). This editor combines *flexibility* with ease of use, making content creation a breeze.

![Placeholder](/placeholder.jpeg)

## Rich Formatting Options

The editor supports all common text formatting including **bold**, *italic*, <u>underline</u>, ~~strikethrough~~, and \`inline code\`. You can also combine them for **_bold and italic_** text.

### Interactive Features

Try out these powerful capabilities:

- **Bubble Menu** — Select any text to see formatting options appear
- **Slash Commands** — Type \`/\` for quick access to blocks and formatting
- **Mentions** — Use \`@\` to tag people or entities
- **Emoji Picker** — Type \`:\` followed by an emoji name like :smile:
- **Drag & Drop** — Hover over any block to see the drag handle

> **Pro tip:** You can use keyboard shortcuts like Cmd/Ctrl + B for bold, Cmd/Ctrl + I for italic, and more!

### Advanced Capabilities

1. **Custom Extensions** — Add your own TipTap extensions seamlessly
2. **Multiple Content Types** — Support for JSON, HTML, and Markdown
3. **Customizable Toolbars** — Fixed, bubble, and floating layouts
4. **Theme Integration** — Fully styled with Nuxt UI theme system

#### Code Blocks

Perfect for technical documentation:

\`\`\`vue
<template>
  <UEditor v-model="value" content-type="markdown" />
</template>
\`\`\`

---

Whether you're building a blog, documentation site, or content management system, the Nuxt UI Editor provides everything you need for a professional editing experience. Visit [ui.nuxt.com](https://ui.nuxt.com) to explore more components.`)

const { extension: completionExtension, handlers: aiHandlers, isLoading: aiLoading } = useEditorCompletion(editorRef)

const customHandlers = {
  imageUpload: {
    canExecute: (editor: Editor) => editor.can().insertContent({ type: 'imageUpload' }),
    execute: (editor: Editor) => editor.chain().focus().insertContent({ type: 'imageUpload' }),
    isActive: (editor: Editor) => editor.isActive('imageUpload'),
    isDisabled: undefined
  },
  ...aiHandlers
} satisfies EditorCustomHandlers

const fixedToolbarItems = [[{
  kind: 'undo',
  icon: 'i-lucide-undo',
  tooltip: { text: 'Undo' }
}, {
  kind: 'redo',
  icon: 'i-lucide-redo',
  tooltip: { text: 'Redo' }
}], [{
  icon: 'i-lucide-heading',
  tooltip: { text: 'Headings' },
  content: {
    align: 'start'
  },
  items: [{
    kind: 'heading',
    level: 1,
    icon: 'i-lucide-heading-1',
    label: 'Heading 1'
  }, {
    kind: 'heading',
    level: 2,
    icon: 'i-lucide-heading-2',
    label: 'Heading 2'
  }, {
    kind: 'heading',
    level: 3,
    icon: 'i-lucide-heading-3',
    label: 'Heading 3'
  }, {
    kind: 'heading',
    level: 4,
    icon: 'i-lucide-heading-4',
    label: 'Heading 4'
  }]
}, {
  icon: 'i-lucide-list',
  tooltip: { text: 'Lists' },
  content: {
    align: 'start'
  },
  items: [{
    kind: 'bulletList',
    icon: 'i-lucide-list',
    label: 'Bullet List'
  }, {
    kind: 'orderedList',
    icon: 'i-lucide-list-ordered',
    label: 'Ordered List'
  }]
}, {
  kind: 'blockquote',
  icon: 'i-lucide-text-quote',
  tooltip: { text: 'Blockquote' }
}, {
  kind: 'codeBlock',
  icon: 'i-lucide-square-code',
  tooltip: { text: 'Code Block' }
}], [{
  kind: 'mark',
  mark: 'bold',
  icon: 'i-lucide-bold',
  tooltip: { text: 'Bold' }
}, {
  kind: 'mark',
  mark: 'italic',
  icon: 'i-lucide-italic',
  tooltip: { text: 'Italic' }
}, {
  kind: 'mark',
  mark: 'underline',
  icon: 'i-lucide-underline',
  tooltip: { text: 'Underline' }
}, {
  kind: 'mark',
  mark: 'strike',
  icon: 'i-lucide-strikethrough',
  tooltip: { text: 'Strikethrough' }
}, {
  kind: 'mark',
  mark: 'code',
  icon: 'i-lucide-code',
  tooltip: { text: 'Code' }
}], [{
  slot: 'link' as const,
  icon: 'i-lucide-link'
}, {
  kind: 'imageUpload',
  icon: 'i-lucide-image',
  tooltip: { text: 'Image' }
}], [{
  icon: 'i-lucide-align-justify',
  tooltip: { text: 'Text Align' },
  content: {
    align: 'end'
  },
  items: [{
    kind: 'textAlign',
    align: 'left',
    icon: 'i-lucide-align-left',
    label: 'Align Left'
  }, {
    kind: 'textAlign',
    align: 'center',
    icon: 'i-lucide-align-center',
    label: 'Align Center'
  }, {
    kind: 'textAlign',
    align: 'right',
    icon: 'i-lucide-align-right',
    label: 'Align Right'
  }, {
    kind: 'textAlign',
    align: 'justify',
    icon: 'i-lucide-align-justify',
    label: 'Align Justify'
  }]
}]] satisfies EditorToolbarItem<typeof customHandlers>[][]

const bubbleToolbarItems = computed(() => [[{
  icon: 'i-lucide-sparkles',
  label: 'Improve',
  activeColor: 'neutral',
  activeVariant: 'ghost',
  loading: aiLoading.value,
  content: {
    align: 'start'
  },
  items: [{
    kind: 'aiFix',
    icon: 'i-lucide-spell-check',
    label: 'Fix spelling & grammar'
  }, {
    kind: 'aiExtend',
    icon: 'i-lucide-unfold-vertical',
    label: 'Extend text'
  }, {
    kind: 'aiReduce',
    icon: 'i-lucide-fold-vertical',
    label: 'Reduce text'
  }, {
    kind: 'aiSimplify',
    icon: 'i-lucide-lightbulb',
    label: 'Simplify text'
  }, {
    kind: 'aiContinue',
    icon: 'i-lucide-text',
    label: 'Continue sentence'
  }, {
    kind: 'aiSummarize',
    icon: 'i-lucide-list',
    label: 'Summarize'
  }, {
    icon: 'i-lucide-languages',
    label: 'Translate',
    children: [{
      kind: 'aiTranslate',
      language: 'English',
      label: 'English'
    }, {
      kind: 'aiTranslate',
      language: 'French',
      label: 'French'
    }, {
      kind: 'aiTranslate',
      language: 'Spanish',
      label: 'Spanish'
    }, {
      kind: 'aiTranslate',
      language: 'German',
      label: 'German'
    }]
  }]
}], [{
  label: 'Turn into',
  trailingIcon: 'i-lucide-chevron-down',
  activeColor: 'neutral',
  activeVariant: 'ghost',
  tooltip: { text: 'Turn into' },
  content: {
    align: 'start'
  },
  ui: {
    label: 'text-xs'
  },
  items: [{
    type: 'label',
    label: 'Turn into'
  }, {
    kind: 'paragraph',
    label: 'Paragraph',
    icon: 'i-lucide-type'
  }, {
    kind: 'heading',
    level: 1,
    icon: 'i-lucide-heading-1',
    label: 'Heading 1'
  }, {
    kind: 'heading',
    level: 2,
    icon: 'i-lucide-heading-2',
    label: 'Heading 2'
  }, {
    kind: 'heading',
    level: 3,
    icon: 'i-lucide-heading-3',
    label: 'Heading 3'
  }, {
    kind: 'heading',
    level: 4,
    icon: 'i-lucide-heading-4',
    label: 'Heading 4'
  }, {
    kind: 'bulletList',
    icon: 'i-lucide-list',
    label: 'Bullet List'
  }, {
    kind: 'orderedList',
    icon: 'i-lucide-list-ordered',
    label: 'Ordered List'
  }, {
    kind: 'blockquote',
    icon: 'i-lucide-text-quote',
    label: 'Blockquote'
  }, {
    kind: 'codeBlock',
    icon: 'i-lucide-square-code',
    label: 'Code Block'
  }]
}], [{
  kind: 'mark',
  mark: 'bold',
  icon: 'i-lucide-bold',
  tooltip: { text: 'Bold' }
}, {
  kind: 'mark',
  mark: 'italic',
  icon: 'i-lucide-italic',
  tooltip: { text: 'Italic' }
}, {
  kind: 'mark',
  mark: 'underline',
  icon: 'i-lucide-underline',
  tooltip: { text: 'Underline' }
}, {
  kind: 'mark',
  mark: 'strike',
  icon: 'i-lucide-strikethrough',
  tooltip: { text: 'Strikethrough' }
}, {
  kind: 'mark',
  mark: 'code',
  icon: 'i-lucide-code',
  tooltip: { text: 'Code' }
}], [{
  slot: 'link' as const,
  icon: 'i-lucide-link'
}, {
  kind: 'imageUpload',
  icon: 'i-lucide-image',
  tooltip: { text: 'Image' }
}], [{
  icon: 'i-lucide-align-justify',
  tooltip: { text: 'Text Align' },
  content: {
    align: 'end'
  },
  items: [{
    kind: 'textAlign',
    align: 'left',
    icon: 'i-lucide-align-left',
    label: 'Align Left'
  }, {
    kind: 'textAlign',
    align: 'center',
    icon: 'i-lucide-align-center',
    label: 'Align Center'
  }, {
    kind: 'textAlign',
    align: 'right',
    icon: 'i-lucide-align-right',
    label: 'Align Right'
  }, {
    kind: 'textAlign',
    align: 'justify',
    icon: 'i-lucide-align-justify',
    label: 'Align Justify'
  }]
}]] satisfies EditorToolbarItem<typeof customHandlers>[][])

const imageToolbarItems = (editor: Editor): EditorToolbarItem[][] => {
  const node = editor.state.doc.nodeAt(editor.state.selection.from)

  return [[{
    icon: 'i-lucide-download',
    to: node?.attrs?.src,
    download: true,
    tooltip: { text: 'Download' }
  }, {
    icon: 'i-lucide-refresh-cw',
    tooltip: { text: 'Replace' },
    onClick: () => {
      const { state } = editor
      const { selection } = state

      const pos = selection.from
      const node = state.doc.nodeAt(pos)

      if (node && node.type.name === 'image') {
        editor.chain().focus().deleteRange({ from: pos, to: pos + node.nodeSize }).insertContentAt(pos, { type: 'imageUpload' }).run()
      }
    }
  }], [{
    icon: 'i-lucide-trash',
    tooltip: { text: 'Delete' },
    onClick: () => {
      const { state } = editor
      const { selection } = state

      const pos = selection.from
      const node = state.doc.nodeAt(pos)

      if (node && node.type.name === 'image') {
        editor.chain().focus().deleteRange({ from: pos, to: pos + node.nodeSize }).run()
      }
    }
  }]]
}

const selectedNode = ref<{ node: JSONContent, pos: number }>()

const handleItems = (editor: Editor): DropdownMenuItem[][] => {
  if (!selectedNode.value?.node?.type) {
    return []
  }

  return mapEditorItems(editor, [[
    {
      type: 'label',
      label: upperFirst(selectedNode.value.node.type)
    },
    {
      label: 'Turn into',
      icon: 'i-lucide-repeat-2',
      children: [
        { kind: 'paragraph', label: 'Paragraph', icon: 'i-lucide-type' },
        { kind: 'heading', level: 1, label: 'Heading 1', icon: 'i-lucide-heading-1' },
        { kind: 'heading', level: 2, label: 'Heading 2', icon: 'i-lucide-heading-2' },
        { kind: 'heading', level: 3, label: 'Heading 3', icon: 'i-lucide-heading-3' },
        { kind: 'heading', level: 4, label: 'Heading 4', icon: 'i-lucide-heading-4' },
        { kind: 'bulletList', label: 'Bullet List', icon: 'i-lucide-list' },
        { kind: 'orderedList', label: 'Ordered List', icon: 'i-lucide-list-ordered' },
        { kind: 'blockquote', label: 'Blockquote', icon: 'i-lucide-text-quote' },
        { kind: 'codeBlock', label: 'Code Block', icon: 'i-lucide-square-code' }
      ]
    },
    {
      kind: 'clearFormatting',
      pos: selectedNode.value?.pos,
      label: 'Reset formatting',
      icon: 'i-lucide-rotate-ccw'
    }
  ], [
    {
      kind: 'duplicate',
      pos: selectedNode.value?.pos,
      label: 'Duplicate',
      icon: 'i-lucide-copy'
    },
    {
      label: 'Copy to clipboard',
      icon: 'i-lucide-clipboard',
      onSelect: async () => {
        if (!selectedNode.value) return

        const pos = selectedNode.value.pos
        const node = editor.state.doc.nodeAt(pos)
        if (node) {
          await navigator.clipboard.writeText(node.textContent)
        }
      }
    }
  ], [
    {
      kind: 'moveUp',
      pos: selectedNode.value?.pos,
      label: 'Move up',
      icon: 'i-lucide-arrow-up'
    },
    {
      kind: 'moveDown',
      pos: selectedNode.value?.pos,
      label: 'Move down',
      icon: 'i-lucide-arrow-down'
    }
  ], [
    {
      kind: 'delete',
      pos: selectedNode.value?.pos,
      label: 'Delete',
      icon: 'i-lucide-trash'
    }
  ]], customHandlers) as DropdownMenuItem[][]
}

const suggestionItems = [[{
  type: 'label',
  label: 'AI'
}, {
  kind: 'aiContinue',
  label: 'Continue writing',
  icon: 'i-lucide-sparkles'
}], [{
  type: 'label',
  label: 'Style'
}, {
  kind: 'paragraph',
  label: 'Paragraph',
  icon: 'i-lucide-type'
}, {
  kind: 'heading',
  level: 1,
  label: 'Heading 1',
  icon: 'i-lucide-heading-1'
}, {
  kind: 'heading',
  level: 2,
  label: 'Heading 2',
  icon: 'i-lucide-heading-2'
}, {
  kind: 'heading',
  level: 3,
  label: 'Heading 3',
  icon: 'i-lucide-heading-3'
}, {
  kind: 'bulletList',
  label: 'Bullet List',
  icon: 'i-lucide-list'
}, {
  kind: 'orderedList',
  label: 'Numbered List',
  icon: 'i-lucide-list-ordered'
}, {
  kind: 'blockquote',
  label: 'Blockquote',
  icon: 'i-lucide-text-quote'
}, {
  kind: 'codeBlock',
  label: 'Code Block',
  icon: 'i-lucide-square-code'
}], [{
  type: 'label',
  label: 'Insert'
}, {
  kind: 'mention',
  label: 'Mention',
  icon: 'i-lucide-at-sign'
}, {
  kind: 'emoji',
  label: 'Emoji',
  icon: 'i-lucide-smile-plus'
}, {
  kind: 'imageUpload',
  label: 'Image',
  icon: 'i-lucide-image'
}, {
  kind: 'horizontalRule',
  label: 'Horizontal Rule',
  icon: 'i-lucide-separator-horizontal'
}]] satisfies EditorSuggestionMenuItem<typeof customHandlers>[][]

const mentionItems: EditorMentionMenuItem[] = [{
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

const emojiItems: EditorEmojiMenuItem[] = gitHubEmojis.filter(emoji => !emoji.name.startsWith('regional_indicator_'))
</script>

<template>
  <UEditor
    ref="editorRef"
    v-slot="{ editor, handlers }"
    v-model="value"
    content-type="markdown"
    :extensions="[
      Emoji,
      TextAlign.configure({ types: ['heading', 'paragraph'] }),
      ImageUpload,
      CodeBlockShiki.configure({
        defaultTheme: 'material-theme',
        themes: {
          light: 'material-theme-lighter',
          dark: 'material-theme-palenight'
        }
      }),
      completionExtension
    ]"
    :handlers="customHandlers"
    placeholder="Write, type '/' for commands..."
    :ui="{ base: 'p-8 sm:px-16 py-13.5' }"
    class="w-full"
  >
    <UEditorToolbar :editor="editor" :items="fixedToolbarItems" class="border-b border-muted sticky top-0 inset-x-0 px-8 sm:px-16 py-2 z-50 bg-default overflow-x-auto">
      <template #link>
        <EditorLinkPopover :editor="editor" auto-open />
      </template>
    </UEditorToolbar>

    <UEditorToolbar
      :editor="editor"
      :items="bubbleToolbarItems"
      layout="bubble"
      :should-show="({ editor, view, state }) => {
        if (editor.isActive('imageUpload') || editor.isActive('image')) {
          return false
        }
        const { selection } = state
        return view.hasFocus() && !selection.empty
      }"
    >
      <template #link>
        <EditorLinkPopover :editor="editor" />
      </template>
    </UEditorToolbar>

    <UEditorToolbar
      :editor="editor"
      :items="imageToolbarItems(editor)"
      layout="bubble"
      :should-show="({ editor, view }) => {
        return editor.isActive('image') && view.hasFocus()
      }"
    />

    <UEditorSuggestionMenu :editor="editor" :items="suggestionItems" />

    <UEditorMentionMenu :editor="editor" :items="mentionItems" />

    <UEditorEmojiMenu :editor="editor" :items="emojiItems" />

    <UEditorDragHandle v-slot="{ ui, onClick }" :editor="editor" @node-change="selectedNode = $event">
      <UButton
        icon="i-lucide-plus"
        color="neutral"
        variant="ghost"
        size="sm"
        :class="ui.handle()"
        @click="(e) => {
          e.stopPropagation()

          const selected = onClick()
          handlers.suggestion?.execute(editor, { pos: selected?.pos }).run()
        }"
      />

      <UDropdownMenu
        v-slot="{ open }"
        :modal="false"
        :items="handleItems(editor)"
        :content="{ side: 'left' }"
        :ui="{ content: 'w-48', label: 'text-xs' }"
        @update:open="editor.chain().setMeta('lockDragHandle', $event).run()"
      >
        <UButton
          color="neutral"
          variant="ghost"
          active-variant="soft"
          size="sm"
          icon="i-lucide-grip-vertical"
          :active="open"
          :class="ui.handle()"
        />
      </UDropdownMenu>
    </UEditorDragHandle>
  </UEditor>
</template>

<style>
html.dark .tiptap .shiki,
html.dark .tiptap .shiki span {
  color: var(--shiki-dark) !important;
  background-color: var(--ui-bg-muted) !important;
}
</style>
```

> [!NOTE]
> See: https://github.com/nuxt/ui/blob/v4/docs/app/components/content/examples/editor/EditorExample.vue
> 
> This example demonstrates a production-ready Editor component. Check out the source code on GitHub.

> [!WARNING]
> 
> If you encounter prosemirror-related errors such as `Adding different instances of a keyed plugin` when using the Editor component or its extensions, you may need to add prosemirror packages to the `vite.optimizeDeps.include` list in your `nuxt.config.ts` file. This ensures Vite pre-bundles these dependencies to avoid loading multiple instances.
> 
> ```ts [nuxt.config.ts]
> export default defineNuxtConfig({
>   vite: {
>     optimizeDeps: {
>       include: [
>         '@nuxt/ui > prosemirror-state',
>         '@nuxt/ui > prosemirror-transform',
>         '@nuxt/ui > prosemirror-model',
>         '@nuxt/ui > prosemirror-view',
>         '@nuxt/ui > prosemirror-gapcursor'
>       ]
>     }
>   }
> })
> ```

### Content

Use the `v-model` directive to control the value of the Editor.

```vue
<script setup lang="ts">
const value = ref({
  type: "doc",
  content: [
    {
      type: "heading",
      attrs: {
        level: 1
      },
      content: [
        {
          type: "text",
          text: "Hello World"
        }
      ]
    },
    {
      type: "paragraph",
      content: [
        {
          type: "text",
          text: "This is a "
        },
        {
          type: "text",
          marks: [
            {
              type: "bold"
            }
          ],
          text: "rich text"
        },
        {
          type: "text",
          text: " editor."
        }
      ]
    }
  ]
})
</script>

<template>
  <UEditor v-model="value" class="w-full min-h-21" />
</template>
```

### Content Type

The Editor automatically detects the content format based on `v-model` type: strings are treated as `html` and objects as `json`.

You can explicitly set the format using the `content-type` prop: `json`, `html`, or `markdown`.

```vue
<script setup lang="ts">
const value = ref("<h1>Hello World</h1>\n<p>This is a <strong>rich text</strong> editor.</p>\n")
</script>

<template>
  <UEditor v-model="value" content-type="html" class="w-full min-h-21" />
</template>
```

### Extensions

The Editor includes the following extensions by default:

- [**StarterKit**](#starter-kit) - Core editing features (bold, italic, headings, lists, etc.)
- [**Placeholder**](#placeholder) - Show placeholder text (when placeholder prop is provided)
- **Image** - Insert and display images
- **Mention** - Add @ mentions support
- **Markdown** - Parse and serialize markdown (when content type is markdown)

> [!NOTE]
> 
> Each built-in extension can be configured using its corresponding prop (`starter-kit`, `placeholder`, `image`, `mention`, `markdown`) to customize its behavior with TipTap options.

You can use the `extensions` prop to add additional TipTap extensions to enhance the Editor's capabilities:

```vue
<script setup lang="ts">
import { Emoji } from '@tiptap/extension-emoji'
import { TextAlign } from '@tiptap/extension-text-align'

const value = ref('<h1>Hello World</h1>\n')
</script>

<template>
  <UEditor
    v-model="value"
    :extensions="[
      Emoji,
      TextAlign.configure({
        types: ['heading', 'paragraph']
      })
    ]"
  />
</template>
```

> [!TIP]
> See: #with-image-upload
> 
> Check out the image upload example for creating custom TipTap extensions.

### Placeholder

Use the `placeholder` prop to set a placeholder text that shows in empty paragraphs.

```vue
<script setup lang="ts">
const value = ref("")
</script>

<template>
  <UEditor placeholder="Start writing..." class="w-full min-h-7" />
</template>
```

> [!NOTE]
> 
> The `placeholder` prop accepts a string or an object with [PlaceholderOptions](https://tiptap.dev/docs/editor/extensions/functionality/placeholder) and an additional `mode` property:
> 
> - `everyLine`: Display placeholder on every empty line when focused (default).
> - `firstLine`: Display placeholder only on the first line when the editor is empty.
> 
> ```vue
> <template>
>   <UEditor :placeholder="{ placeholder: 'Start writing...', mode: 'firstLine' }" />
> </template>
> ```

> [!TIP]
> 
> By default, placeholders only appear on top-level empty nodes. To show placeholders in nested elements like list items, set `includeChildren` to `true`:
> 
> ```vue
> <template>
>   <UEditor :placeholder="{ placeholder: 'Start writing...', includeChildren: true }" />
> </template>
> ```

> [!NOTE]
> See: https://tiptap.dev/docs/editor/extensions/functionality/placeholder
> 
> Learn more about Placeholder extension in the TipTap documentation.

### Starter Kit

Use the `starter-kit` prop to configure the built-in TipTap StarterKit extension which includes common editor features like bold, italic, headings, lists, blockquotes, code blocks, and more.

```vue
<script setup lang="ts">
const value = ref('<h1>Hello World</h1>\n')
</script>

<template>
  <UEditor
    v-model="value"
    :starter-kit="{
      blockquote: false,
      headings: {
        levels: [1, 2, 3, 4]
      },
      dropcursor: {
        color: 'var(--ui-primary)',
        width: 2
      },
      link: {
        openOnClick: false
      }
    }"
  />
</template>
```

> [!NOTE]
> See: https://tiptap.dev/docs/editor/extensions/functionality/starterkit
> 
> Learn more about StarterKit extension in the TipTap documentation.

### Handlers

Handlers wrap TipTap's built-in commands to provide a unified interface for editor actions. When you add a `kind` property to a [EditorToolbar](/docs/components/editor-toolbar) or [EditorSuggestionMenu](/docs/components/editor-suggestion-menu) item, the corresponding handler executes the TipTap command and manages its state (active, disabled, etc.).

#### Default handlers

The Editor component provides these default handlers, which you can reference in toolbar or suggestion menu items using the `kind` property:

<table>
<thead>
  <tr>
    <th>
      Handler
    </th>
    
    <th>
      Description
    </th>
    
    <th>
      Usage
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          mark
        </span>
      </code>
    </td>
    
    <td>
      Toggle text marks (bold, italic, strike, code, underline)
    </td>
    
    <td>
      Requires <code>
        mark
      </code>
      
       property in item
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          textAlign
        </span>
      </code>
    </td>
    
    <td>
      Set text alignment (left, center, right, justify)
    </td>
    
    <td>
      Requires <code>
        align
      </code>
      
       property in item
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          heading
        </span>
      </code>
    </td>
    
    <td>
      Toggle heading levels (1-6)
    </td>
    
    <td>
      Requires <code>
        level
      </code>
      
       property in item
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          link
        </span>
      </code>
    </td>
    
    <td>
      Add, edit, or remove links
    </td>
    
    <td>
      Prompts for URL if not provided
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          image
        </span>
      </code>
    </td>
    
    <td>
      Insert images
    </td>
    
    <td>
      Prompts for URL if not provided
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          blockquote
        </span>
      </code>
    </td>
    
    <td>
      Toggle blockquotes
    </td>
    
    <td>
      
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          bulletList
        </span>
      </code>
    </td>
    
    <td>
      Toggle bullet lists
    </td>
    
    <td>
      Handles list conversions
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          orderedList
        </span>
      </code>
    </td>
    
    <td>
      Toggle ordered lists
    </td>
    
    <td>
      Handles list conversions
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          taskList
        </span>
      </code>
    </td>
    
    <td>
      Toggle task lists
    </td>
    
    <td>
      Handles list conversions
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          codeBlock
        </span>
      </code>
    </td>
    
    <td>
      Toggle code blocks
    </td>
    
    <td>
      
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          horizontalRule
        </span>
      </code>
    </td>
    
    <td>
      Insert horizontal rules
    </td>
    
    <td>
      
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          paragraph
        </span>
      </code>
    </td>
    
    <td>
      Set paragraph format
    </td>
    
    <td>
      
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          undo
        </span>
      </code>
    </td>
    
    <td>
      Undo last change
    </td>
    
    <td>
      
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          redo
        </span>
      </code>
    </td>
    
    <td>
      Redo last undone change
    </td>
    
    <td>
      
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          clearFormatting
        </span>
      </code>
    </td>
    
    <td>
      Remove all formatting
    </td>
    
    <td>
      Works with selection or position
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          duplicate
        </span>
      </code>
    </td>
    
    <td>
      Duplicate a node
    </td>
    
    <td>
      Requires <code>
        pos
      </code>
      
       property in item
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          delete
        </span>
      </code>
    </td>
    
    <td>
      Delete a node
    </td>
    
    <td>
      Requires <code>
        pos
      </code>
      
       property in item
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          moveUp
        </span>
      </code>
    </td>
    
    <td>
      Move a node up
    </td>
    
    <td>
      Requires <code>
        pos
      </code>
      
       property in item
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          moveDown
        </span>
      </code>
    </td>
    
    <td>
      Move a node down
    </td>
    
    <td>
      Requires <code>
        pos
      </code>
      
       property in item
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          suggestion
        </span>
      </code>
    </td>
    
    <td>
      Trigger suggestion menu
    </td>
    
    <td>
      Inserts <code>
        /
      </code>
      
       character
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          mention
        </span>
      </code>
    </td>
    
    <td>
      Trigger mention menu
    </td>
    
    <td>
      Inserts <code>
        @
      </code>
      
       character
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          emoji
        </span>
      </code>
    </td>
    
    <td>
      Trigger emoji picker
    </td>
    
    <td>
      Inserts <code>
        :
      </code>
      
       character
    </td>
  </tr>
</tbody>
</table>

> [!WARNING]
> 
> The `taskList` and `textAlign` handlers only work when their respective extensions are installed, as they are not included in the Editor by default.

Here's how to use default handlers in toolbar or suggestion menu items:

```vue
<script setup lang="ts">
import type { EditorToolbarItem } from '@nuxt/ui'

const value = ref('<h1>Hello World</h1>\n')

const items: EditorToolbarItem[] = [
  { kind: 'mark', mark: 'bold', icon: 'i-lucide-bold' },
  { kind: 'mark', mark: 'italic', icon: 'i-lucide-italic' },
  { kind: 'heading', level: 1, icon: 'i-lucide-heading-1' },
  { kind: 'heading', level: 2, icon: 'i-lucide-heading-2' },
  { kind: 'textAlign', align: 'left', icon: 'i-lucide-align-left' },
  { kind: 'textAlign', align: 'center', icon: 'i-lucide-align-center' },
  { kind: 'bulletList', icon: 'i-lucide-list' },
  { kind: 'orderedList', icon: 'i-lucide-list-ordered' },
  { kind: 'blockquote', icon: 'i-lucide-quote' },
  { kind: 'link', icon: 'i-lucide-link' }
]
</script>

<template>
  <UEditor v-slot="{ editor }" v-model="value">
    <UEditorToolbar :editor="editor" :items="items" />
  </UEditor>
</template>
```

#### Custom handlers

Use the `handlers` prop to extend or override the default handlers. Custom handlers are merged with the default handlers, allowing you to add new actions or modify existing behavior.

Each handler implements the `EditorHandler` interface:

```ts
interface EditorHandler {
  /* Checks if the command can be executed in the current editor state */
  canExecute: (editor: Editor, item?: any) => boolean
  /* Executes the command and returns a Tiptap chain */
  execute: (editor: Editor, item?: any) => any
  /* Determines if the item should appear active (used for toggle states) */
  isActive: (editor: Editor, item?: any) => boolean
  /* Optional additional check to disable the item (combined with `canExecute`) */
  isDisabled?: (editor: Editor, item?: any) => boolean
}
```

Here's an example of creating custom handlers:

```vue
<script setup lang="ts">
import type { Editor } from '@tiptap/vue-3'
import type { EditorCustomHandlers, EditorToolbarItem } from '@nuxt/ui'

const value = ref('<h1>Hello World</h1>\n')

const customHandlers = {
  highlight: {
    canExecute: (editor: Editor) => editor.can().toggleHighlight(),
    execute: (editor: Editor) => editor.chain().focus().toggleHighlight(),
    isActive: (editor: Editor) => editor.isActive('highlight'),
    isDisabled: (editor: Editor) => !editor.isEditable
  }
} satisfies EditorCustomHandlers

const items = [
  // Built-in handler
  { kind: 'mark', mark: 'bold', icon: 'i-lucide-bold' },
  // Custom handler
  { kind: 'highlight', icon: 'i-lucide-highlighter' }
] satisfies EditorToolbarItem<typeof customHandlers>[]
</script>

<template>
  <UEditor v-slot="{ editor }" v-model="value" :handlers="customHandlers">
    <UEditorToolbar :editor="editor" :items="items" />
  </UEditor>
</template>
```

> [!TIP]
> See: #with-image-upload
> 
> Check out the image upload example for a complete implementation with custom handlers.

## Examples

> [!NOTE]
> See: https://github.com/nuxt-ui-templates/editor
> 
> Check out the source code of our **Editor template** on GitHub for a real-life example.

### With toolbar

You can use the [EditorToolbar](/docs/components/editor-toolbar) component to add a `fixed`, `bubble`, or `floating` toolbar to the Editor with common formatting actions.

```vue [EditorToolbarExample.vue]
<script setup lang="ts">
import type { EditorToolbarItem } from '@nuxt/ui'

const value = ref(`# Toolbar

Select some text to see the formatting toolbar appear above your selection.`)

const items: EditorToolbarItem[][] = [[{
  icon: 'i-lucide-heading',
  tooltip: { text: 'Headings' },
  content: {
    align: 'start'
  },
  items: [{
    kind: 'heading',
    level: 1,
    icon: 'i-lucide-heading-1',
    label: 'Heading 1'
  }, {
    kind: 'heading',
    level: 2,
    icon: 'i-lucide-heading-2',
    label: 'Heading 2'
  }, {
    kind: 'heading',
    level: 3,
    icon: 'i-lucide-heading-3',
    label: 'Heading 3'
  }, {
    kind: 'heading',
    level: 4,
    icon: 'i-lucide-heading-4',
    label: 'Heading 4'
  }]
}], [{
  kind: 'mark',
  mark: 'bold',
  icon: 'i-lucide-bold',
  tooltip: { text: 'Bold' }
}, {
  kind: 'mark',
  mark: 'italic',
  icon: 'i-lucide-italic',
  tooltip: { text: 'Italic' }
}, {
  kind: 'mark',
  mark: 'underline',
  icon: 'i-lucide-underline',
  tooltip: { text: 'Underline' }
}, {
  kind: 'mark',
  mark: 'strike',
  icon: 'i-lucide-strikethrough',
  tooltip: { text: 'Strikethrough' }
}, {
  kind: 'mark',
  mark: 'code',
  icon: 'i-lucide-code',
  tooltip: { text: 'Code' }
}]]
</script>

<template>
  <UEditor
    v-slot="{ editor }"
    v-model="value"
    content-type="markdown"
    class="w-full min-h-21"
  >
    <UEditorToolbar :editor="editor" :items="items" layout="bubble" />
  </UEditor>
</template>
```

### With drag handle

You can use the [EditorDragHandle](/docs/components/editor-drag-handle) component to add a draggable handle for reordering blocks.

```vue [EditorDragHandleExample.vue]
<script setup lang="ts">
const value = ref(`# Drag Handle

Hover over the left side of this block to see the drag handle appear and reorder blocks.`)
</script>

<template>
  <UEditor
    v-slot="{ editor }"
    v-model="value"
    content-type="markdown"
    class="w-full min-h-21"
  >
    <UEditorDragHandle :editor="editor" />
  </UEditor>
</template>
```

### With suggestion menu

You can use the [EditorSuggestionMenu](/docs/components/editor-suggestion-menu) component to add slash commands for quick formatting and insertions.

```vue [EditorSuggestionMenuExample.vue]
<script setup lang="ts">
import type { EditorSuggestionMenuItem } from '@nuxt/ui'

const value = ref(`# Suggestion Menu

Type / to open the suggestion menu and browse available formatting commands.`)

const items: EditorSuggestionMenuItem[][] = [[{
  type: 'label',
  label: 'Text'
}, {
  kind: 'paragraph',
  label: 'Paragraph',
  icon: 'i-lucide-type'
}, {
  kind: 'heading',
  level: 1,
  label: 'Heading 1',
  icon: 'i-lucide-heading-1'
}, {
  kind: 'heading',
  level: 2,
  label: 'Heading 2',
  icon: 'i-lucide-heading-2'
}, {
  kind: 'heading',
  level: 3,
  label: 'Heading 3',
  icon: 'i-lucide-heading-3'
}], [{
  type: 'label',
  label: 'Lists'
}, {
  kind: 'bulletList',
  label: 'Bullet List',
  icon: 'i-lucide-list'
}, {
  kind: 'orderedList',
  label: 'Numbered List',
  icon: 'i-lucide-list-ordered'
}], [{
  type: 'label',
  label: 'Insert'
}, {
  kind: 'blockquote',
  label: 'Blockquote',
  icon: 'i-lucide-text-quote'
}, {
  kind: 'codeBlock',
  label: 'Code Block',
  icon: 'i-lucide-square-code'
}, {
  kind: 'horizontalRule',
  label: 'Divider',
  icon: 'i-lucide-separator-horizontal'
}]]

// SSR-safe function to append menus to body (avoids z-index issues in docs)
const appendToBody = import.meta.client ? () => document.body : undefined
</script>

<template>
  <UEditor
    v-slot="{ editor }"
    v-model="value"
    content-type="markdown"
    placeholder="Type / for commands..."
    class="w-full min-h-21"
  >
    <UEditorSuggestionMenu :editor="editor" :items="items" :append-to="appendToBody" />
  </UEditor>
</template>
```

### With mention menu

You can use the [EditorMentionMenu](/docs/components/editor-mention-menu) component to add @ mentions for tagging users or entities.

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

### With emoji menu

You can use the [EditorEmojiMenu](/docs/components/editor-emoji-menu) component to add emoji picker support.

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

### With image upload

This example demonstrates how to create an image upload feature using the `extensions` prop to register a custom TipTap node and the `handlers` prop to define how the toolbar button triggers the upload flow.

1. Create a Vue component that uses the [FileUpload](/docs/components/file-upload) component:

```vue [EditorImageUploadNode.vue]
<script setup lang="ts">
import type { NodeViewProps } from '@tiptap/vue-3'
import { NodeViewWrapper } from '@tiptap/vue-3'

const props = defineProps<NodeViewProps>()

const file = ref<File | null>(null)
const loading = ref(false)

watch(file, async (newFile) => {
  if (!newFile) return

  loading.value = true

  const reader = new FileReader()
  reader.onload = async (e) => {
    const dataUrl = e.target?.result as string
    if (!dataUrl) {
      loading.value = false
      return
    }

    // Simulate upload delay
    await new Promise(resolve => setTimeout(resolve, 1000))

    const pos = props.getPos()
    if (typeof pos !== 'number') {
      loading.value = false
      return
    }

    props.editor
      .chain()
      .focus()
      .deleteRange({ from: pos, to: pos + 1 })
      .setImage({ src: dataUrl })
      .run()

    loading.value = false
  }
  reader.readAsDataURL(newFile)
})
</script>

<template>
  <NodeViewWrapper>
    <UFileUpload
      v-model="file"
      accept="image/*"
      label="Upload an image"
      description="SVG, PNG, JPG or GIF (max. 2MB)"
      :preview="false"
      class="min-h-48"
    >
      <template #leading>
        <UAvatar
          :icon="loading ? 'i-lucide-loader-circle' : 'i-lucide-image'"
          size="xl"
          :ui="{ icon: [loading && 'animate-spin'] }"
        />
      </template>
    </UFileUpload>
  </NodeViewWrapper>
</template>
```

1. Create a custom TipTap extension to register the node:

```vue [EditorImageUploadExtension.vue]
import { Node, mergeAttributes } from '@tiptap/core'
import type { CommandProps, NodeViewRenderer } from '@tiptap/core'
import { VueNodeViewRenderer } from '@tiptap/vue-3'
import ImageUploadNodeComponent from './EditorImageUploadNode.vue'

declare module '@tiptap/core' {
  interface Commands<ReturnType> {
    imageUpload: {
      insertImageUpload: () => ReturnType
    }
  }
}

export const ImageUpload = Node.create({
  name: 'imageUpload',
  group: 'block',
  atom: true,
  draggable: true,
  addAttributes() {
    return {}
  },
  parseHTML() {
    return [{
      tag: 'div[data-type="image-upload"]'
    }]
  },
  renderHTML({ HTMLAttributes }) {
    return ['div', mergeAttributes(HTMLAttributes, { 'data-type': 'image-upload' })]
  },
  addNodeView(): NodeViewRenderer {
    return VueNodeViewRenderer(ImageUploadNodeComponent)
  },
  addCommands() {
    return {
      insertImageUpload: () => ({ commands }: CommandProps) => {
        return commands.insertContent({ type: this.name })
      }
    }
  }
})

export default ImageUpload
```

1. Use the custom extension in the Editor:

```vue [EditorImageUploadExample.vue]
<script setup lang="ts">
import type { EditorCustomHandlers, EditorToolbarItem } from '@nuxt/ui'
import type { Editor } from '@tiptap/vue-3'
import { ImageUpload } from './EditorImageUploadExtension'

const value = ref(`# Image Upload

This editor demonstrates how to create a custom TipTap extension with handlers. Click the image button in the toolbar to upload a file — it will show a custom [FileUpload](/docs/components/file-upload) interface before inserting the image.

Try uploading an image below:

`)

const customHandlers = {
  imageUpload: {
    canExecute: (editor: Editor) => editor.can().insertContent({ type: 'imageUpload' }),
    execute: (editor: Editor) => editor.chain().focus().insertContent({ type: 'imageUpload' }),
    isActive: (editor: Editor) => editor.isActive('imageUpload'),
    isDisabled: undefined
  }
} satisfies EditorCustomHandlers

const items = [[{
  kind: 'imageUpload',
  icon: 'i-lucide-image',
  label: 'Add image',
  variant: 'soft'
}], [{
  icon: 'i-lucide-heading',
  content: {
    align: 'start'
  },
  items: [{
    kind: 'heading',
    level: 1,
    icon: 'i-lucide-heading-1',
    label: 'Heading 1'
  }, {
    kind: 'heading',
    level: 2,
    icon: 'i-lucide-heading-2',
    label: 'Heading 2'
  }, {
    kind: 'heading',
    level: 3,
    icon: 'i-lucide-heading-3',
    label: 'Heading 3'
  }, {
    kind: 'heading',
    level: 4,
    icon: 'i-lucide-heading-4',
    label: 'Heading 4'
  }]
}], [{
  kind: 'mark',
  mark: 'bold',
  icon: 'i-lucide-bold'
}, {
  kind: 'mark',
  mark: 'italic',
  icon: 'i-lucide-italic'
}, {
  kind: 'mark',
  mark: 'underline',
  icon: 'i-lucide-underline'
}, {
  kind: 'mark',
  mark: 'strike',
  icon: 'i-lucide-strikethrough'
}, {
  kind: 'mark',
  mark: 'code',
  icon: 'i-lucide-code'
}]] satisfies EditorToolbarItem<typeof customHandlers>[][]
</script>

<template>
  <UEditor
    v-slot="{ editor }"
    v-model="value"
    :extensions="[ImageUpload]"
    :handlers="customHandlers"
    content-type="markdown"
    :ui="{ base: 'p-8 sm:px-16' }"
    class="w-full min-h-74"
  >
    <UEditorToolbar :editor="editor" :items="items" class="border-b border-muted py-2 px-8 sm:px-16 overflow-x-auto" />
  </UEditor>
</template>
```

> [!NOTE]
> See: https://tiptap.dev/docs/editor/extensions/custom-extensions
> 
> Learn more about creating custom extensions in the TipTap documentation.

### With AI completion

This example demonstrates how to add AI-powered features to the Editor using the [Vercel AI SDK](https://ai-sdk.dev/), specifically the [`useCompletion`](https://ai-sdk.dev/docs/reference/ai-sdk-ui/use-completion) composable for streaming text completions, combined with the [Vercel AI Gateway](https://vercel.com/ai-gateway) to access AI models through a centralized endpoint. It includes ghost text autocompletion and text transformation actions (fix grammar, extend, reduce, simplify, translate, etc.).

> [!NOTE]
> 
> You need to install these dependencies first to use this example:
> 
> ```bash [pnpm]
> pnpm add ai @ai-sdk/gateway @ai-sdk/vue
> ```
> 
> ```bash [yarn]
> yarn add ai @ai-sdk/gateway @ai-sdk/vue
> ```
> 
> ```bash [npm]
> npm install ai @ai-sdk/gateway @ai-sdk/vue
> ```
> 
> ```bash [bun]
> bun add ai @ai-sdk/gateway @ai-sdk/vue
> ```

1. Create a custom TipTap extension that handles inline ghost text suggestions:

```vue [EditorCompletionExtension.vue]
import { Extension } from '@tiptap/core'
import { Decoration, DecorationSet } from '@tiptap/pm/view'
import { Plugin, PluginKey } from '@tiptap/pm/state'
import type { Editor } from '@tiptap/vue-3'
import { useDebounceFn } from '@vueuse/core'

export interface CompletionOptions {
  /**
   * Debounce delay in ms before triggering completion
   * @defaultValue 250
   */
  debounce?: number
  /**
   * Whether to automatically trigger completion while typing
   * @defaultValue false
   */
  autoTrigger?: boolean
  /**
   * Characters that should prevent completion from triggering
   * @defaultValue ['/', ':', '@']
   */
  triggerCharacters?: string[]
  /**
   * Called when completion should be triggered, receives the editor instance
   */
  onTrigger?: (editor: Editor) => void
  /**
   * Called when suggestion is accepted
   */
  onAccept?: () => void
  /**
   * Called when suggestion is dismissed
   */
  onDismiss?: () => void
}

export interface CompletionStorage {
  suggestion: string
  position: number | undefined
  visible: boolean
  debouncedTrigger: ((editor: Editor) => void) | null
  setSuggestion: (text: string) => void
  clearSuggestion: () => void
}

export const completionPluginKey = new PluginKey('completion')

export const Completion = Extension.create<CompletionOptions, CompletionStorage>({
  name: 'completion',

  addOptions() {
    return {
      debounce: 250,
      autoTrigger: false,
      triggerCharacters: ['/', ':', '@'],
      onTrigger: undefined,
      onAccept: undefined,
      onDismiss: undefined
    }
  },

  addStorage() {
    return {
      suggestion: '',
      position: undefined as number | undefined,
      visible: false,
      debouncedTrigger: null as ((editor: Editor) => void) | null,
      setSuggestion(text: string) {
        this.suggestion = text
      },
      clearSuggestion() {
        this.suggestion = ''
        this.position = undefined
        this.visible = false
      }
    }
  },

  addProseMirrorPlugins() {
    const storage = this.storage

    return [
      new Plugin({
        key: completionPluginKey,
        props: {
          decorations(state) {
            if (!storage.visible || !storage.suggestion || storage.position === undefined) {
              return DecorationSet.empty
            }

            const widget = Decoration.widget(storage.position, () => {
              const span = document.createElement('span')
              span.className = 'completion-suggestion'
              span.textContent = storage.suggestion
              span.style.cssText = 'color: var(--ui-text-muted); opacity: 0.6; pointer-events: none;'
              return span
            }, { side: 1 })

            return DecorationSet.create(state.doc, [widget])
          }
        }
      })
    ]
  },

  addKeyboardShortcuts() {
    return {
      'Mod-j': ({ editor }) => {
        // Clear any existing suggestion first to avoid flickering
        if (this.storage.visible) {
          this.storage.clearSuggestion()
          this.options.onDismiss?.()
        }
        // Manually trigger completion
        this.storage.debouncedTrigger?.(editor as Editor)
        return true
      },
      'Tab': ({ editor }) => {
        if (!this.storage.visible || !this.storage.suggestion || this.storage.position === undefined) {
          return false
        }

        // Store values before clearing
        const suggestion = this.storage.suggestion
        const position = this.storage.position

        // Clear suggestion first
        this.storage.clearSuggestion()

        // Force decoration update
        editor.view.dispatch(editor.state.tr.setMeta('completionUpdate', true))

        // Insert the suggestion text
        editor.chain().focus().insertContentAt(position, suggestion).run()

        this.options.onAccept?.()
        return true
      },
      'Escape': ({ editor }) => {
        if (this.storage.visible) {
          this.storage.clearSuggestion()
          // Force decoration update
          editor.view.dispatch(editor.state.tr.setMeta('completionUpdate', true))
          this.options.onDismiss?.()
          return true
        }
        return false
      }
    }
  },

  onUpdate({ editor }) {
    // Clear suggestion on any edit
    if (this.storage.visible) {
      this.storage.clearSuggestion()
      // Force decoration update
      editor.view.dispatch(editor.state.tr.setMeta('completionUpdate', true))
      this.options.onDismiss?.()
    }

    // Debounced trigger check (only if autoTrigger is enabled)
    if (this.options.autoTrigger) {
      this.storage.debouncedTrigger?.(editor as Editor)
    }
  },

  onSelectionUpdate({ editor }) {
    if (this.storage.visible) {
      this.storage.clearSuggestion()
      // Force decoration update
      editor.view.dispatch(editor.state.tr.setMeta('completionUpdate', true))
      this.options.onDismiss?.()
    }
  },

  onCreate() {
    const storage = this.storage
    const options = this.options

    // Create debounced trigger function for this instance
    this.storage.debouncedTrigger = useDebounceFn((editor: Editor) => {
      if (!options.onTrigger) return

      const { state } = editor
      const { selection } = state
      const { $from } = selection

      // Only suggest at end of block with content
      const isAtEndOfBlock = $from.parentOffset === $from.parent.content.size
      const hasContent = $from.parent.textContent.trim().length > 0
      const textContent = $from.parent.textContent

      // Don't trigger if sentence is complete (ends with punctuation)
      const endsWithPunctuation = /[.!?]\s*$/.test(textContent)

      // Don't trigger if text ends with trigger characters
      const triggerChars = options.triggerCharacters || []
      const endsWithTrigger = triggerChars.some(char => textContent.endsWith(char))

      if (!isAtEndOfBlock || !hasContent || endsWithPunctuation || endsWithTrigger) {
        return
      }

      // Set position and mark as visible
      storage.position = selection.from
      storage.visible = true

      // Pass editor to let the handler extract content (e.g., as markdown)
      options.onTrigger(editor)
    }, options.debounce || 250)
  },

  onDestroy() {
    this.storage.debouncedTrigger = null
  }
})

export default Completion
```

1. Create a composable that manages AI completion state and handlers:

```vue [EditorUseCompletion.vue]
import { useCompletion } from '@ai-sdk/vue'
import type { Editor } from '@tiptap/vue-3'
import { Completion } from './EditorCompletionExtension'
import type { CompletionStorage } from './EditorCompletionExtension'

type CompletionMode = 'continue' | 'fix' | 'extend' | 'reduce' | 'simplify' | 'summarize' | 'translate'

export interface UseEditorCompletionOptions {
  api?: string
}

export function useEditorCompletion(editorRef: Ref<{ editor: Editor | undefined } | null | undefined>, options: UseEditorCompletionOptions = {}) {
  // State for direct insertion/transform mode
  const insertState = ref<{
    pos: number
    deleteRange?: { from: number, to: number }
  }>()
  const mode = ref<CompletionMode>('continue')
  const language = ref<string>()

  // Helper to get completion storage
  function getCompletionStorage() {
    const storage = editorRef.value?.editor?.storage as Record<string, CompletionStorage> | undefined
    return storage?.completion
  }

  const { completion, complete, isLoading, stop, setCompletion } = useCompletion({
    api: options.api || '/api/completion',
    streamProtocol: 'text',
    body: computed(() => ({
      mode: mode.value,
      language: language.value
    })),
    onFinish: (_prompt, completionText) => {
      // For inline suggestion mode, don't clear - let user accept with Tab
      const storage = getCompletionStorage()
      if (mode.value === 'continue' && storage?.visible) {
        return
      }

      // For transform modes, insert the full completion with markdown parsing
      const transformModes = ['fix', 'extend', 'reduce', 'simplify', 'summarize', 'translate']
      if (transformModes.includes(mode.value) && insertState.value && completionText) {
        const editor = editorRef.value?.editor
        if (editor) {
          // Delete the original selection if not already done
          if (insertState.value.deleteRange) {
            editor.chain()
              .focus()
              .deleteRange(insertState.value.deleteRange)
              .run()
          }

          // Insert with markdown parsing
          editor.chain()
            .focus()
            .insertContentAt(insertState.value.pos, completionText, { contentType: 'markdown' })
            .run()
        }
      }

      insertState.value = undefined
    },
    onError: (error) => {
      console.error('AI completion error:', error)
      insertState.value = undefined
      getCompletionStorage()?.clearSuggestion()
    }
  })

  // Watch completion for inline suggestion updates
  watch(completion, (newCompletion, oldCompletion) => {
    const editor = editorRef.value?.editor
    if (!editor || !newCompletion) return

    const storage = getCompletionStorage()
    if (storage?.visible) {
      // Update inline suggestion
      // Add space prefix if needed (so preview matches what will be inserted)
      let suggestionText = newCompletion
      if (storage.position !== undefined) {
        const textBefore = editor.state.doc.textBetween(Math.max(0, storage.position - 1), storage.position)
        if (textBefore && !/\s/.test(textBefore) && !suggestionText.startsWith(' ')) {
          suggestionText = ' ' + suggestionText
        }
      }
      storage.setSuggestion(suggestionText)
      editor.view.dispatch(editor.state.tr.setMeta('completionUpdate', true))
    } else if (insertState.value) {
      // Direct insertion/transform mode (from toolbar actions)

      // Transform modes use markdown insertion - wait for full completion
      const transformModes = ['fix', 'extend', 'reduce', 'simplify', 'summarize', 'translate']
      if (transformModes.includes(mode.value)) {
        // Don't stream - will be handled in onFinish
        return
      }

      // If this is the first chunk and we have a selection to replace, delete it first
      if (insertState.value.deleteRange && !oldCompletion) {
        editor.chain()
          .focus()
          .deleteRange(insertState.value.deleteRange)
          .run()
        insertState.value.deleteRange = undefined
      }

      let delta = newCompletion.slice(oldCompletion?.length || 0)
      if (delta) {
        // For single-paragraph transforms, replace all line breaks with spaces
        if (['fix', 'simplify', 'translate'].includes(mode.value)) {
          delta = delta.replace(/[\r\n]+/g, ' ').replace(/\s{2,}/g, ' ')
        }

        // For "continue" mode, add a space before if needed (first chunk only)
        if (mode.value === 'continue' && !oldCompletion) {
          const textBefore = editor.state.doc.textBetween(Math.max(0, insertState.value.pos - 1), insertState.value.pos)
          if (textBefore && !/\s/.test(textBefore)) {
            delta = ' ' + delta
          }
        }

        editor.chain().focus().command(({ tr }) => {
          tr.insertText(delta, insertState.value!.pos)
          return true
        }).run()
        insertState.value.pos += delta.length
      }
    }
  })

  function triggerTransform(editor: Editor, transformMode: Exclude<CompletionMode, 'continue'>, lang?: string) {
    if (isLoading.value) return

    getCompletionStorage()?.clearSuggestion()

    const { state } = editor
    const { selection } = state

    if (selection.empty) return

    mode.value = transformMode
    language.value = lang
    const selectedText = state.doc.textBetween(selection.from, selection.to)

    // Replace the selected text with the transformed version
    insertState.value = { pos: selection.from, deleteRange: { from: selection.from, to: selection.to } }

    complete(selectedText)
  }

  function getMarkdownBefore(editor: Editor, pos: number): string {
    const { state } = editor
    const serializer = (editor.storage.markdown as { serializer?: { serialize: (content: unknown) => string } })?.serializer
    if (serializer) {
      const slice = state.doc.slice(0, pos)
      return serializer.serialize(slice.content)
    }
    // Fallback to plain text
    return state.doc.textBetween(0, pos, '\n')
  }

  function triggerContinue(editor: Editor) {
    if (isLoading.value) return

    mode.value = 'continue'
    getCompletionStorage()?.clearSuggestion()
    const { state } = editor
    const { selection } = state

    if (selection.empty) {
      // No selection: continue from cursor position
      const textBefore = getMarkdownBefore(editor, selection.from)
      insertState.value = { pos: selection.from }
      complete(textBefore)
    } else {
      // Text selected: append completion after the selection
      const textBefore = getMarkdownBefore(editor, selection.to)
      insertState.value = { pos: selection.to }
      complete(textBefore)
    }
  }

  // Configure Completion extension
  const extension = Completion.configure({
    onTrigger: (editor) => {
      if (isLoading.value) return
      mode.value = 'continue'
      const textBefore = getMarkdownBefore(editor, editor.state.selection.from)
      complete(textBefore)
    },
    onAccept: () => {
      setCompletion('')
    },
    onDismiss: () => {
      stop()
      setCompletion('')
    }
  })

  // Create handlers for toolbar
  const handlers = {
    aiContinue: {
      canExecute: () => !isLoading.value,
      execute: (editor: Editor) => {
        triggerContinue(editor)
        return editor.chain()
      },
      isActive: () => !!(isLoading.value && mode.value === 'continue'),
      isDisabled: () => !!isLoading.value
    },
    aiFix: {
      canExecute: (editor: Editor) => !editor.state.selection.empty && !isLoading.value,
      execute: (editor: Editor) => {
        triggerTransform(editor, 'fix')
        return editor.chain()
      },
      isActive: () => !!(isLoading.value && mode.value === 'fix'),
      isDisabled: (editor: Editor) => editor.state.selection.empty || !!isLoading.value
    },
    aiExtend: {
      canExecute: (editor: Editor) => !editor.state.selection.empty && !isLoading.value,
      execute: (editor: Editor) => {
        triggerTransform(editor, 'extend')
        return editor.chain()
      },
      isActive: () => !!(isLoading.value && mode.value === 'extend'),
      isDisabled: (editor: Editor) => editor.state.selection.empty || !!isLoading.value
    },
    aiReduce: {
      canExecute: (editor: Editor) => !editor.state.selection.empty && !isLoading.value,
      execute: (editor: Editor) => {
        triggerTransform(editor, 'reduce')
        return editor.chain()
      },
      isActive: () => !!(isLoading.value && mode.value === 'reduce'),
      isDisabled: (editor: Editor) => editor.state.selection.empty || !!isLoading.value
    },
    aiSimplify: {
      canExecute: (editor: Editor) => !editor.state.selection.empty && !isLoading.value,
      execute: (editor: Editor) => {
        triggerTransform(editor, 'simplify')
        return editor.chain()
      },
      isActive: () => !!(isLoading.value && mode.value === 'simplify'),
      isDisabled: (editor: Editor) => editor.state.selection.empty || !!isLoading.value
    },
    aiSummarize: {
      canExecute: (editor: Editor) => !editor.state.selection.empty && !isLoading.value,
      execute: (editor: Editor) => {
        triggerTransform(editor, 'summarize')
        return editor.chain()
      },
      isActive: () => !!(isLoading.value && mode.value === 'summarize'),
      isDisabled: (editor: Editor) => editor.state.selection.empty || !!isLoading.value
    },
    aiTranslate: {
      canExecute: (editor: Editor) => !editor.state.selection.empty && !isLoading.value,
      execute: (editor: Editor, cmd: { language?: string } | undefined) => {
        triggerTransform(editor, 'translate', cmd?.language)
        return editor.chain()
      },
      isActive: (_editor: Editor, cmd: { language?: string } | undefined) => !!(isLoading.value && mode.value === 'translate' && language.value === cmd?.language),
      isDisabled: (editor: Editor) => editor.state.selection.empty || !!isLoading.value
    }
  }

  return {
    extension,
    handlers,
    isLoading,
    mode
  }
}
```

1. Create a server API endpoint to handle completion requests using [`streamText`](https://ai-sdk.dev/docs/reference/ai-sdk-core/stream-text#streamtext):

```ts [server/api/completion.post.ts]
import { streamText } from 'ai'
import { gateway } from '@ai-sdk/gateway'

export default defineEventHandler(async (event) => {
  const { prompt, mode, language } = await readBody(event)
  if (!prompt) {
    throw createError({ statusCode: 400, message: 'Prompt is required' })
  }

  let system: string
  let maxOutputTokens: number

  const preserveMarkdown = 'IMPORTANT: Preserve all markdown formatting (bold, italic, links, etc.) exactly as in the original.'

  switch (mode) {
    case 'fix':
      system = `You are a writing assistant. Fix all spelling and grammar errors in the given text. ${preserveMarkdown} Only output the corrected text, nothing else.`
      maxOutputTokens = 500
      break
    case 'extend':
      system = `You are a writing assistant. Extend the given text with more details, examples, and explanations while maintaining the same style. ${preserveMarkdown} Only output the extended text, nothing else.`
      maxOutputTokens = 500
      break
    case 'reduce':
      system = `You are a writing assistant. Make the given text more concise by removing unnecessary words while keeping the meaning. ${preserveMarkdown} Only output the reduced text, nothing else.`
      maxOutputTokens = 300
      break
    case 'simplify':
      system = `You are a writing assistant. Simplify the given text to make it easier to understand, using simpler words and shorter sentences. ${preserveMarkdown} Only output the simplified text, nothing else.`
      maxOutputTokens = 400
      break
    case 'summarize':
      system = 'You are a writing assistant. Summarize the given text concisely while keeping the key points. Only output the summary, nothing else.'
      maxOutputTokens = 200
      break
    case 'translate':
      system = `You are a writing assistant. Translate the given text to ${language || 'English'}. ${preserveMarkdown} Only output the translated text, nothing else.`
      maxOutputTokens = 500
      break
    case 'continue':
    default:
      system = `You are a writing assistant providing inline autocompletions.
CRITICAL RULES:
- Output ONLY the NEW text that comes AFTER the user's input
- NEVER repeat any words from the end of the user's text
- Keep completions short (1 sentence max)
- Match the tone and style of the existing text
- ${preserveMarkdown}`
      maxOutputTokens = 25
      break
  }

  return streamText({
    model: gateway('anthropic/claude-haiku-4.5'),
    system,
    prompt,
    maxOutputTokens
  }).toTextStreamResponse()
})
```

1. Use the composable in the Editor:

```vue [EditorCompletionExample.vue]
<script setup lang="ts">
import type { EditorCustomHandlers, EditorToolbarItem } from '@nuxt/ui'
import { useEditorCompletion } from './EditorUseCompletion'

const editorRef = useTemplateRef('editorRef')

const value = ref(`# AI Completion

This editor demonstrates how to add AI-powered features using the [Vercel AI SDK](https://ai-sdk.dev/). It includes ghost text autocompletion that appears as you type (press Tab to accept) and text transformation actions.

Try selecting some text and using the AI dropdown to fix grammar, extend, or simplify it.`)

const { extension: completionExtension, handlers: aiHandlers, isLoading: aiLoading } = useEditorCompletion(editorRef)

const customHandlers = {
  ...aiHandlers
} satisfies EditorCustomHandlers

const items = computed(() => [[{
  icon: 'i-lucide-sparkles',
  label: 'Improve',
  variant: 'soft',
  loading: aiLoading.value,
  content: {
    align: 'start'
  },
  items: [{
    kind: 'aiFix',
    icon: 'i-lucide-spell-check',
    label: 'Fix spelling & grammar'
  }, {
    kind: 'aiExtend',
    icon: 'i-lucide-unfold-vertical',
    label: 'Extend text'
  }, {
    kind: 'aiReduce',
    icon: 'i-lucide-fold-vertical',
    label: 'Reduce text'
  }, {
    kind: 'aiSimplify',
    icon: 'i-lucide-lightbulb',
    label: 'Simplify text'
  }, {
    kind: 'aiContinue',
    icon: 'i-lucide-text',
    label: 'Continue sentence'
  }, {
    kind: 'aiSummarize',
    icon: 'i-lucide-list',
    label: 'Summarize'
  }, {
    icon: 'i-lucide-languages',
    label: 'Translate',
    children: [{
      kind: 'aiTranslate',
      language: 'English',
      label: 'English'
    }, {
      kind: 'aiTranslate',
      language: 'French',
      label: 'French'
    }, {
      kind: 'aiTranslate',
      language: 'Spanish',
      label: 'Spanish'
    }, {
      kind: 'aiTranslate',
      language: 'German',
      label: 'German'
    }]
  }]
}], [{
  icon: 'i-lucide-heading',
  content: {
    align: 'start'
  },
  items: [{
    kind: 'heading',
    level: 1,
    icon: 'i-lucide-heading-1',
    label: 'Heading 1'
  }, {
    kind: 'heading',
    level: 2,
    icon: 'i-lucide-heading-2',
    label: 'Heading 2'
  }, {
    kind: 'heading',
    level: 3,
    icon: 'i-lucide-heading-3',
    label: 'Heading 3'
  }]
}], [{
  kind: 'mark',
  mark: 'bold',
  icon: 'i-lucide-bold'
}, {
  kind: 'mark',
  mark: 'italic',
  icon: 'i-lucide-italic'
}, {
  kind: 'mark',
  mark: 'underline',
  icon: 'i-lucide-underline'
}]] satisfies EditorToolbarItem<typeof customHandlers>[][])
</script>

<template>
  <UEditor
    ref="editorRef"
    v-slot="{ editor }"
    v-model="value"
    :extensions="[completionExtension]"
    :handlers="customHandlers"
    content-type="markdown"
    :ui="{ base: 'p-8 sm:px-16' }"
    class="w-full min-h-74"
  >
    <UEditorToolbar :editor="editor" :items="items" class="border-b border-muted py-2 px-8 sm:px-16 overflow-x-auto" />
  </UEditor>
</template>
```

> [!NOTE]
> 
> The completion extension can be configured with `autoTrigger: true` to automatically suggest completions while typing (disabled by default). You can also manually trigger it with <kbd value="meta">
> 
> 
> 
> </kbd>
> 
>  <kbd value="j" className="ms-px">
> 
> 
> 
> </kbd>
> 
> .

> [!NOTE]
> See: https://ai-sdk.dev/
> 
> Learn more about the Vercel AI SDK and available providers.

## API

### Props

```ts
/**
 * Props for the Editor component
 */
interface EditorProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  modelValue?: null | string | JSONContent | JSONContent[];
  /**
   * The content type the content is provided as.
   * When not specified, it's automatically inferred: strings are treated as 'html', objects as 'json'.
   */
  contentType?: EditorContentType | undefined;
  /**
   * The starter kit options to configure the editor.
   */
  starterKit?: Partial<StarterKitOptions> | undefined;
  /**
   * The placeholder text to show in empty paragraphs. Can be a string or PlaceholderOptions from `@tiptap/extension-placeholder`.
   */
  placeholder?: string | (Partial<PlaceholderOptions> & { mode?: "firstLine" | "everyLine" | undefined; }) | undefined;
  /**
   * The markdown extension options to configure markdown parsing and serialization.
   */
  markdown?: Partial<MarkdownExtensionOptions> | undefined;
  /**
   * The image extension options to configure image handling. Set to `false` to disable the extension.
   * @default "true"
   */
  image?: boolean | Partial<ImageOptions> | undefined;
  /**
   * The mention extension options to configure mention handling. Set to `false` to disable the extension.
   * The `suggestion` and `suggestions` options are omitted as they are managed by the `EditorMentionMenu` component.
   * @default "true"
   */
  mention?: boolean | Partial<Omit<MentionOptions<any, MentionNodeAttrs>, "suggestion" | "suggestions">> | undefined;
  /**
   * Custom item handlers to override or extend the default handlers.
   * These handlers are provided to all child components (toolbar, suggestion menu, etc.).
   */
  handlers?: H | undefined;
  ui?: { root?: SlotClass; content?: SlotClass; base?: SlotClass; } | undefined;
  /**
   * The extensions to use
   */
  extensions?: Extensions | undefined;
  /**
   * Whether to inject base CSS styles
   */
  injectCSS?: boolean | undefined;
  /**
   * A nonce to use for CSP while injecting styles
   */
  injectNonce?: string | undefined;
  /**
   * The editor's initial focus position
   */
  autofocus?: FocusPosition | undefined;
  /**
   * Whether the editor is editable
   */
  editable?: boolean | undefined;
  /**
   * The default text direction for all content in the editor.
   * When set to 'ltr' or 'rtl', all nodes will have the corresponding dir attribute.
   * When set to 'auto', the dir attribute will be set based on content detection.
   * When undefined, no dir attribute will be added.
   */
  textDirection?: "ltr" | "rtl" | "auto" | undefined;
  /**
   * The editor's props
   */
  editorProps?: EditorProps<any> | undefined;
  parseOptions?: ParseOptions;
  /**
   * The editor's core extension options
   */
  coreExtensionOptions?: { clipboardTextSerializer?: { blockSeparator?: string | undefined; } | undefined; tabindex?: { value?: string | undefined; } | undefined; delete?: { async?: boolean | undefined; filterTransaction?: ((transaction: Transaction) => boolean) | undefined; } | undefined; } | undefined;
  /**
   * Whether to enable input rules behavior
   */
  enableInputRules?: EnableRules | undefined;
  /**
   * Whether to enable paste rules behavior
   */
  enablePasteRules?: EnableRules | undefined;
  /**
   * Determines whether core extensions are enabled.
   * 
   * If set to `false`, all core extensions will be disabled.
   * To disable specific core extensions, provide an object where the keys are the extension names and the values are `false`.
   * Extensions not listed in the object will remain enabled.
   */
  enableCoreExtensions?: boolean | Partial<Record<"editable" | "textDirection" | "clipboardTextSerializer" | "commands" | "focusEvents" | "keymap" | "tabindex" | "drop" | "paste" | "delete", false>> | undefined;
  /**
   * If `true`, the editor will check the content for errors on initialization.
   * Emitting the `contentError` event if the content is invalid.
   * Which can be used to show a warning or error message to the user.
   */
  enableContentCheck?: boolean | undefined;
  /**
   * If `true`, the editor will emit the `contentError` event if invalid content is
   * encountered but `enableContentCheck` is `false`. This lets you preserve the
   * invalid editor content while still showing a warning or error message to
   * the user.
   */
  emitContentError?: boolean | undefined;
  /**
   * Called before the editor is constructed.
   */
  onBeforeCreate?: ((props: { editor: Editor; }) => void) | undefined;
  /**
   * Called after the editor is constructed.
   */
  onCreate?: ((props: { editor: Editor; }) => void) | undefined;
  /**
   * Called when the editor is mounted.
   */
  onMount?: ((props: { editor: Editor; }) => void) | undefined;
  /**
   * Called when the editor is unmounted.
   */
  onUnmount?: ((props: { editor: Editor; }) => void) | undefined;
  /**
   * Called when the editor encounters an error while parsing the content.
   * Only enabled if `enableContentCheck` is `true`.
   */
  onContentError?: ((props: { editor: Editor; error: Error; disableCollaboration: () => void; }) => void) | undefined;
  /**
   * Called when the editor's content is updated.
   */
  onUpdate?: ((props: { editor: Editor; transaction: Transaction; appendedTransactions: Transaction[]; }) => void) | undefined;
  /**
   * Called when the editor's selection is updated.
   */
  onSelectionUpdate?: ((props: { editor: Editor; transaction: Transaction; }) => void) | undefined;
  /**
   * Called after a transaction is applied to the editor.
   */
  onTransaction?: ((props: { editor: Editor; transaction: Transaction; appendedTransactions: Transaction[]; }) => void) | undefined;
  /**
   * Called on focus events.
   */
  onFocus?: ((props: { editor: Editor; event: FocusEvent; transaction: Transaction; }) => void) | undefined;
  /**
   * Called on blur events.
   */
  onBlur?: ((props: { editor: Editor; event: FocusEvent; transaction: Transaction; }) => void) | undefined;
  /**
   * Called when the editor is destroyed.
   */
  onDestroy?: ((props: void) => void) | undefined;
  /**
   * Called when content is pasted into the editor.
   */
  onPaste?: ((e: ClipboardEvent, slice: Slice) => void) | undefined;
  /**
   * Called when content is dropped into the editor.
   */
  onDrop?: ((e: DragEvent, slice: Slice, moved: boolean) => void) | undefined;
  /**
   * Called when content is deleted from the editor.
   */
  onDelete?: ((props: { editor: Editor; deletedRange: Range; newRange: Range; transaction: Transaction; combinedTransform: Transform; partial: boolean; from: number; to: number; } & ({ type: "node"; node: Node; newFrom: number; newTo: number; } | { type: "mark"; mark: Mark; })) => void) | undefined;
  /**
   * Whether to enable extension-level dispatching of transactions.
   * If `false`, extensions cannot define their own `dispatchTransaction` hook.
   */
  enableExtensionDispatchTransaction?: boolean | undefined;
}
```

### Slots

```ts
/**
 * Slots for the Editor component
 */
interface EditorSlots {
  default(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the Editor component
 */
interface EditorEmits {
  update:modelValue: (payload: [value: T]) => void;
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
          editor
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
          Editor
        </span>
        
        <span class="sMK4o">
          |
        </span>
        
        <span class="sBMFI">
          undefined
        </span>
        
        <span class="sMK4o">
          >
        </span>
      </code>
    </td>
  </tr>
</tbody>
</table>

> [!NOTE]
> See: https://tiptap.dev/docs/editor/api/editor
> 
> The exposed editor instance is the TipTap Editor API. Check the TipTap documentation for all available methods and properties.

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    editor: {
      slots: {
        root: '',
        content: 'relative size-full flex-1',
        base: [
          'w-full outline-none *:my-5 *:first:mt-0 *:last:mb-0 sm:px-8 selection:bg-primary/20',
          '[&_p]:leading-7',
          '[&_a]:text-primary [&_a]:border-b [&_a]:border-transparent [&_a]:hover:border-primary [&_a]:font-medium',
          '[&_a]:transition-colors',
          '[&_a>code]:border-dashed [&_a:hover>code]:border-primary [&_a:hover>code]:text-primary',
          '[&_a>code]:transition-colors',
          '[&_.mention]:text-primary [&_.mention]:font-medium',
          '[&_:is(h1,h2,h3,h4,h5,h6)]:text-highlighted [&_:is(h1,h2,h3,h4,h5,h6)]:font-bold',
          '[&_h1]:text-3xl',
          '[&_h2]:text-2xl',
          '[&_h3]:text-xl',
          '[&_h4]:text-lg',
          '[&_h5]:text-base',
          '[&_h6]:text-base',
          '[&_:is(h1,h2,h3,h4,h5,h6)>code]:border-dashed [&_:is(h1,h2,h3,h4,h5,h6)>code]:font-bold',
          '[&_h2>code]:text-xl/6',
          '[&_h3>code]:text-lg/5',
          '[&_blockquote]:border-s-4 [&_blockquote]:border-accented [&_blockquote]:ps-4 [&_blockquote]:italic',
          '[&_[data-type=horizontalRule]]:my-8 [&_[data-type=horizontalRule]]:py-2',
          '[&_hr]:border-t [&_hr]:border-default',
          '[&_pre]:text-sm/6 [&_pre]:border [&_pre]:border-muted [&_pre]:bg-muted [&_pre]:rounded-md [&_pre]:px-4 [&_pre]:py-3 [&_pre]:whitespace-pre-wrap [&_pre]:break-words [&_pre]:overflow-x-auto',
          '[&_pre_code]:p-0 [&_pre_code]:text-inherit [&_pre_code]:font-inherit [&_pre_code]:rounded-none [&_pre_code]:inline [&_pre_code]:border-0 [&_pre_code]:bg-transparent',
          '[&_code]:px-1.5 [&_code]:py-0.5 [&_code]:text-sm [&_code]:font-mono [&_code]:font-medium [&_code]:rounded-md [&_code]:inline-block [&_code]:border [&_code]:border-muted [&_code]:text-highlighted [&_code]:bg-muted',
          '[&_:is(ul,ol)]:ps-6',
          '[&_ul]:list-disc [&_ul]:marker:text-(--ui-border-accented)',
          '[&_ol]:list-decimal [&_ol]:marker:text-muted',
          '[&_li]:my-1.5 [&_li]:ps-1.5',
          '[&_img]:rounded-md [&_img]:block [&_img]:max-w-full [&_img.ProseMirror-selectednode]:outline-2 [&_img.ProseMirror-selectednode]:outline-primary',
          '[&_.ProseMirror-selectednode:not(img):not(pre):not([data-node-view-wrapper])]:bg-primary/20'
        ]
      },
      variants: {
        placeholderMode: {
          firstLine: {
            base: '[&_:is(p,h1,h2,h3,h4,h5,h6).is-editor-empty:first-child]:before:content-[attr(data-placeholder)] [&_:is(p,h1,h2,h3,h4,h5,h6).is-editor-empty:first-child]:before:text-dimmed [&_:is(p,h1,h2,h3,h4,h5,h6).is-editor-empty:first-child]:before:float-start [&_:is(p,h1,h2,h3,h4,h5,h6).is-editor-empty:first-child]:before:h-0 [&_:is(p,h1,h2,h3,h4,h5,h6).is-editor-empty:first-child]:before:pointer-events-none'
          },
          everyLine: {
            base: '[&_:is(p,h1,h2,h3,h4,h5,h6).is-empty]:before:content-[attr(data-placeholder)] [&_:is(p,h1,h2,h3,h4,h5,h6).is-empty]:before:text-dimmed [&_:is(p,h1,h2,h3,h4,h5,h6).is-empty]:before:float-start [&_:is(p,h1,h2,h3,h4,h5,h6).is-empty]:before:h-0 [&_:is(p,h1,h2,h3,h4,h5,h6).is-empty]:before:pointer-events-none'
          }
        }
      },
      defaultVariants: {
        placeholderMode: 'everyLine'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Editor.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/editor.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

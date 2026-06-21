---
title: "ChatPalette"
description: "A chat palette to create a chatbot interface inside an overlay."
canonical_url: "https://ui.nuxt.com/docs/components/chat-palette"
---
# ChatPalette

> A chat palette to create a chatbot interface inside an overlay.

## Usage

The ChatPalette component is a structured layout wrapper that organizes [ChatMessages](/docs/components/chat-messages) in a scrollable content area and [ChatPrompt](/docs/components/chat-prompt) in a fixed bottom section, creating cohesive chatbot interfaces for modals, slideovers, or drawers.

```vue
<template>
  <UChatPalette>
    <UChatMessages />

    <template #prompt>
      <UChatPrompt />
    </template>
  </UChatPalette>
</template>
```

## Examples

> [!TIP]
> See: /docs/components/chat
> 
> Check the **Chat** overview page for installation instructions, server setup and usage examples.

### Within a Modal

You can use the ChatPalette component inside a [Modal](/docs/components/modal)'s content.

```vue [ChatPaletteModalExample.vue]
<script setup lang="ts">
import { isTextUIPart } from 'ai'
import type { UIMessage } from 'ai'
import { Chat } from '@ai-sdk/vue'
import { isPartStreaming } from '@nuxt/ui/utils/ai'
import { Comark } from '@comark/vue'
import highlight from '@comark/vue/plugins/highlight'

const messages: UIMessage[] = [{
  id: '1',
  role: 'user',
  parts: [{ type: 'text', text: 'What is Nuxt UI?' }]
}, {
  id: '2',
  role: 'assistant',
  parts: [{ type: 'text', text: 'Nuxt UI is a Vue component library built on Reka UI, Tailwind CSS, and Tailwind Variants. It provides 125+ accessible components for building modern web apps.' }]
}]
const input = ref('')

const chat = new Chat({
  messages
})

function onSubmit() {
  if (!input.value.trim()) return

  chat.sendMessage({ text: input.value })

  input.value = ''
}

const ui = {
  prose: {
    p: { base: 'my-2 leading-6' },
    li: { base: 'my-0.5 leading-6' },
    ul: { base: 'my-2' },
    ol: { base: 'my-2' },
    h1: { base: 'text-xl my-2' },
    h2: { base: 'text-lg my-2' },
    h3: { base: 'text-base my-2' },
    h4: { base: 'text-sm my-2' },
    pre: { root: 'my-2' },
    table: { root: 'my-2' },
    hr: { base: 'my-2' }
  }
}
</script>

<template>
  <UModal open :ui="{ content: 'sm:max-w-3xl sm:h-[28rem]' }">
    <template #content>
      <UTheme :ui="ui">
        <UChatPalette>
          <UChatMessages
            :messages="chat.messages"
            :status="chat.status"
            :user="{ side: 'left', variant: 'naked', avatar: { src: 'https://github.com/benjamincanac.png', loading: 'lazy' as const } }"
            :assistant="{ icon: 'i-lucide-bot' }"
          >
            <template #content="{ message }">
              <template v-for="(part, index) in message.parts" :key="`${message.id}-${part.type}-${index}`">
                <template v-if="isTextUIPart(part)">
                  <Comark
                    v-if="message.role === 'assistant'"
                    :markdown="part.text"
                    :streaming="isPartStreaming(part)"
                    :plugins="[highlight()]"
                    class="*:first:mt-0 *:last:mb-0"
                  />
                  <p v-else-if="message.role === 'user'" class="whitespace-pre-wrap leading-6">
                    {{ part.text }}
                  </p>
                </template>
              </template>
            </template>
          </UChatMessages>

          <template #prompt>
            <UChatPrompt
              v-model="input"
              icon="i-lucide-search"
              variant="naked"
              :error="chat.error"
              @submit="onSubmit"
            />
          </template>
        </UChatPalette>
      </UTheme>
    </template>
  </UModal>
</template>
```

### Within ContentSearch

You can use the ChatPalette component conditionally inside [ContentSearch](/docs/components/content-search)'s content to display a chatbot interface when a user selects an item.

```vue [ChatPaletteContentSearchExample.vue]
<script setup lang="ts">
import { isTextUIPart } from 'ai'
import type { UIMessage } from 'ai'
import { Chat } from '@ai-sdk/vue'
import { isPartStreaming } from '@nuxt/ui/utils/ai'
import { Comark } from '@comark/vue'
import highlight from '@comark/vue/plugins/highlight'

const messages: UIMessage[] = []
const input = ref('')

const groups = computed(() => [{
  id: 'ai',
  ignoreFilter: true,
  items: [{
    label: searchTerm.value ? `Ask AI for “${searchTerm.value}”` : 'Ask AI',
    icon: 'i-lucide-bot',
    onSelect: (e: any) => {
      e.preventDefault()

      ai.value = true

      if (searchTerm.value) {
        messages.push({
          id: '1',
          role: 'user',
          parts: [{ type: 'text', text: searchTerm.value }]
        })

        chat.regenerate()
      }
    }
  }]
}])

const ai = ref(false)
const searchTerm = ref('')

const chat = new Chat({
  messages
})

function onSubmit() {
  if (!input.value.trim()) return

  chat.sendMessage({ text: input.value })

  input.value = ''
}

function onClose(e: Event) {
  e.preventDefault()

  ai.value = false
}

const ui = {
  prose: {
    p: { base: 'my-2 leading-6' },
    li: { base: 'my-0.5 leading-6' },
    ul: { base: 'my-2' },
    ol: { base: 'my-2' },
    h1: { base: 'text-xl my-2' },
    h2: { base: 'text-lg my-2' },
    h3: { base: 'text-base my-2' },
    h4: { base: 'text-sm my-2' },
    pre: { root: 'my-2' },
    table: { root: 'my-2' },
    hr: { base: 'my-2' }
  }
}
</script>

<template>
  <UContentSearch v-model:search-term="searchTerm" open :groups="groups">
    <template v-if="ai" #content>
      <UTheme :ui="ui">
        <UChatPalette>
          <UChatMessages
            :messages="chat.messages"
            :status="chat.status"
            :user="{ side: 'left', variant: 'naked', avatar: { src: 'https://github.com/benjamincanac.png', loading: 'lazy' as const } }"
            :assistant="{ icon: 'i-lucide-bot' }"
          >
            <template #content="{ message }">
              <template v-for="(part, index) in message.parts" :key="`${message.id}-${part.type}-${index}`">
                <template v-if="isTextUIPart(part)">
                  <Comark
                    v-if="message.role === 'assistant'"
                    :markdown="part.text"
                    :streaming="isPartStreaming(part)"
                    :plugins="[highlight()]"
                    class="*:first:mt-0 *:last:mb-0"
                  />
                  <p v-else-if="message.role === 'user'" class="whitespace-pre-wrap leading-6">
                    {{ part.text }}
                  </p>
                </template>
              </template>
            </template>
          </UChatMessages>

          <template #prompt>
            <UChatPrompt
              v-model="input"
              icon="i-lucide-search"
              variant="naked"
              :error="chat.error"
              @submit="onSubmit"
              @close="onClose"
            />
          </template>
        </UChatPalette>
      </UTheme>
    </template>
  </UContentSearch>
</template>
```

## API

### Props

```ts
/**
 * Props for the ChatPalette component
 */
interface ChatPaletteProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  ui?: { root?: SlotClass; prompt?: SlotClass; close?: SlotClass; content?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the ChatPalette component
 */
interface ChatPaletteSlots {
  default(): any;
  prompt(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    chatPalette: {
      slots: {
        root: 'relative flex-1 flex flex-col min-h-0 min-w-0',
        prompt: 'px-0 rounded-t-none border-t border-default',
        close: '',
        content: 'overflow-y-auto flex-1 flex flex-col py-3'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/ChatPalette.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/chat-palette.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

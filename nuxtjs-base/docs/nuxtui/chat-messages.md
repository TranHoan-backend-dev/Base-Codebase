---
title: "ChatMessages"
description: "Display a list of chat messages, designed to work seamlessly with Vercel AI SDK."
canonical_url: "https://ui.nuxt.com/docs/components/chat-messages"
---
# ChatMessages

> Display a list of chat messages, designed to work seamlessly with Vercel AI SDK.

## Usage

The ChatMessages component displays a list of [ChatMessage](/docs/components/chat-message) components using either the default slot or the `messages` prop.

```vue
<template>
  <UChatMessages>
    <UChatMessage
      v-for="(message, index) in messages"
      :key="index"
      v-bind="message"
    />
  </UChatMessages>
</template>
```

> [!NOTE]
> 
> This component is purpose-built for AI chatbots with features like:
> 
> - Initial scroll to the bottom upon loading ([`shouldScrollToBottom`](#should-scroll-to-bottom)).
> - Continuous scrolling down as new messages arrive ([`shouldAutoScroll`](#should-auto-scroll)).
> - An "Auto scroll" button appears when scrolled up, allowing users to jump back to the latest messages ([`autoScroll`](#auto-scroll)).
> - A loading indicator displays while the assistant is processing ([`status`](#status)).
> - Submitted messages are scrolled to the top of the viewport and the height of the last user message is dynamically adjusted.

### Messages

Use the `messages` prop to display a list of chat messages.

```vue
<script setup lang="ts">
const messages = ref([
  {
    id: "6045235a-a435-46b8-989d-2df38ca2eb47",
    role: "user",
    parts: [
      {
        type: "text",
        text: "Hello, how are you?"
      }
    ]
  },
  {
    id: "7a92b3c1-d5f8-4e76-b8a9-3c1e5fb2e0d8",
    role: "assistant",
    parts: [
      {
        type: "text",
        text: "I am doing well, thank you for asking! How can I assist you today?"
      }
    ]
  },
  {
    id: "9c84d6a7-8b23-4f12-a1d5-e7f3b9c05e2a",
    role: "user",
    parts: [
      {
        type: "text",
        text: "What is the current weather in Tokyo?"
      }
    ]
  },
  {
    id: "b2e5f8c3-a1d9-4e67-b3f2-c9d8e7a6b5f4",
    role: "assistant",
    parts: [
      {
        type: "text",
        text: "Based on the latest data, Tokyo is currently experiencing sunny weather with temperatures around 24°C (75°F). It's a beautiful day with clear skies."
      }
    ]
  }
])
</script>

<template>
  <UChatMessages :messages="messages" />
</template>
```

### Status

Use the `status` prop to display a visual indicator when the assistant is processing.

```vue
<script setup lang="ts">
const messages = ref([
  {
    id: "6045235a-a435-46b8-989d-2df38ca2eb47",
    role: "user",
    parts: [
      {
        type: "text",
        text: "Hello, how are you?"
      }
    ]
  }
])
</script>

<template>
  <UChatMessages status="submitted" :messages="messages" />
</template>
```

> [!NOTE]
> 
> Here's the detail of the different statuses from the AI SDK Chat class:
> 
> - `submitted`: The message has been sent to the API and we're awaiting the start of the response stream.
> - `streaming`: The response is actively streaming in from the API, receiving chunks of data.
> - `ready`: The full response has been received and processed; a new user message can be submitted.
> - `error`: An error occurred during the API request, preventing successful completion.

### User

Use the `user` prop to change the [ChatMessage](/docs/components/chat-message) props for `user` messages. Defaults to:

- `side: 'right'`
- `variant: 'soft'`

```vue
<script setup lang="ts">
const messages = ref([
  {
    id: "6045235a-a435-46b8-989d-2df38ca2eb47",
    role: "user",
    parts: [
      {
        type: "text",
        text: "Hello, how are you?"
      }
    ]
  },
  {
    id: "7a92b3c1-d5f8-4e76-b8a9-3c1e5fb2e0d8",
    role: "assistant",
    parts: [
      {
        type: "text",
        text: "I am doing well, thank you for asking! How can I assist you today?"
      }
    ]
  },
  {
    id: "9c84d6a7-8b23-4f12-a1d5-e7f3b9c05e2a",
    role: "user",
    parts: [
      {
        type: "text",
        text: "What is the current weather in Tokyo?"
      }
    ]
  },
  {
    id: "b2e5f8c3-a1d9-4e67-b3f2-c9d8e7a6b5f4",
    role: "assistant",
    parts: [
      {
        type: "text",
        text: "Based on the latest data, Tokyo is currently experiencing sunny weather with temperatures around 24°C (75°F). It's a beautiful day with clear skies."
      }
    ]
  }
])
</script>

<template>
  <UChatMessages :user="{
  side: 'left',
  variant: 'solid',
  avatar: {
    src: 'https://github.com/benjamincanac.png',
    loading: 'lazy'
  }
}" :messages="messages" />
</template>
```

### Assistant

Use the `assistant` prop to change the [ChatMessage](/docs/components/chat-message) props for `assistant` messages. Defaults to:

- `side: 'left'`
- `variant: 'naked'`

```vue
<script setup lang="ts">
const messages = ref([
  {
    id: "6045235a-a435-46b8-989d-2df38ca2eb47",
    role: "user",
    parts: [
      {
        type: "text",
        text: "Hello, how are you?"
      }
    ]
  },
  {
    id: "7a92b3c1-d5f8-4e76-b8a9-3c1e5fb2e0d8",
    role: "assistant",
    parts: [
      {
        type: "text",
        text: "I am doing well, thank you for asking! How can I assist you today?"
      }
    ]
  },
  {
    id: "9c84d6a7-8b23-4f12-a1d5-e7f3b9c05e2a",
    role: "user",
    parts: [
      {
        type: "text",
        text: "What is the current weather in Tokyo?"
      }
    ]
  },
  {
    id: "b2e5f8c3-a1d9-4e67-b3f2-c9d8e7a6b5f4",
    role: "assistant",
    parts: [
      {
        type: "text",
        text: "Based on the latest data, Tokyo is currently experiencing sunny weather with temperatures around 24°C (75°F). It's a beautiful day with clear skies."
      }
    ]
  }
])
</script>

<template>
  <UChatMessages :assistant="{
  side: 'left',
  variant: 'outline',
  avatar: {
    icon: 'i-lucide-bot'
  },
  actions: [
    {
      label: 'Copy to clipboard',
      icon: 'i-lucide-copy'
    }
  ]
}" :messages="messages" />
</template>
```

### Auto Scroll

Use the `auto-scroll` prop to customize or hide the auto scroll button (with `false` value) displayed when scrolling to the top of the chat. Defaults to:

- `color: 'neutral'`
- `variant: 'outline'`

You can pass any property from the [Button](/docs/components/button) component to customize it.

```vue
<script setup lang="ts">
const messages = ref([
  {
    id: "6045235a-a435-46b8-989d-2df38ca2eb47",
    role: "user",
    parts: [
      {
        type: "text",
        text: "Hello, how are you?"
      }
    ]
  },
  {
    id: "7a92b3c1-d5f8-4e76-b8a9-3c1e5fb2e0d8",
    role: "assistant",
    parts: [
      {
        type: "text",
        text: "I am doing well, thank you for asking! How can I assist you today?"
      }
    ]
  },
  {
    id: "9c84d6a7-8b23-4f12-a1d5-e7f3b9c05e2a",
    role: "user",
    parts: [
      {
        type: "text",
        text: "What is the current weather in Tokyo?"
      }
    ]
  },
  {
    id: "b2e5f8c3-a1d9-4e67-b3f2-c9d8e7a6b5f4",
    role: "assistant",
    parts: [
      {
        type: "text",
        text: "Based on the latest data, Tokyo is currently experiencing sunny weather with temperatures around 24°C (75°F). It's a beautiful day with clear skies. The forecast for the rest of the week shows a slight chance of rain on Thursday, with temperatures gradually rising to 28°C by the weekend. Humidity levels are moderate at around 65%, and wind speeds are light at 8 km/h from the southeast. Air quality is good with an index of 42. The UV index is high at 7, so it's recommended to wear sunscreen if you're planning to spend time outdoors. Sunrise was at 5:24 AM and sunset will be at 6:48 PM, giving Tokyo approximately 13 hours and 24 minutes of daylight today. The moon is currently in its waxing gibbous phase."
      }
    ]
  },
  {
    id: "c3e5f8c3-a1d9-4e67-b3f2-c9d8e7a6b5f4",
    role: "user",
    parts: [
      {
        type: "text",
        text: "Can you recommend some popular tourist attractions in Kyoto?"
      }
    ]
  },
  {
    id: "d4f5g8c3-a1d9-4e67-b3f2-c9d8e7a6b5f4",
    role: "assistant",
    parts: [
      {
        type: "text",
        text: "Kyoto is known for its beautiful temples, traditional tea houses, and gardens. Some popular attractions include Kinkaku-ji (Golden Pavilion) with its stunning gold leaf exterior reflecting in the mirror pond, Fushimi Inari Shrine with its thousands of vermilion torii gates winding up the mountainside, Arashiyama Bamboo Grove where towering stalks create an otherworldly atmosphere, Kiyomizu-dera Temple perched on a hillside offering panoramic views of the city, and the historic Gion district where you might spot geisha hurrying to evening appointments through narrow stone-paved streets lined with traditional wooden machiya houses."
      }
    ]
  }
])
</script>

<template>
  <UChatMessages :auto-scroll="{
  color: 'neutral',
  variant: 'outline'
}" :should-scroll-to-bottom="false" :messages="messages" />
</template>
```

### Auto Scroll Icon

Use the `auto-scroll-icon` prop to customize the auto scroll button [Icon](/docs/components/icon). Defaults to `i-lucide-arrow-down`.

```vue
<script setup lang="ts">
const messages = ref([
  {
    id: "6045235a-a435-46b8-989d-2df38ca2eb47",
    role: "user",
    parts: [
      {
        type: "text",
        text: "Hello, how are you?"
      }
    ]
  },
  {
    id: "7a92b3c1-d5f8-4e76-b8a9-3c1e5fb2e0d8",
    role: "assistant",
    parts: [
      {
        type: "text",
        text: "I am doing well, thank you for asking! How can I assist you today?"
      }
    ]
  },
  {
    id: "9c84d6a7-8b23-4f12-a1d5-e7f3b9c05e2a",
    role: "user",
    parts: [
      {
        type: "text",
        text: "What is the current weather in Tokyo?"
      }
    ]
  },
  {
    id: "b2e5f8c3-a1d9-4e67-b3f2-c9d8e7a6b5f4",
    role: "assistant",
    parts: [
      {
        type: "text",
        text: "Based on the latest data, Tokyo is currently experiencing sunny weather with temperatures around 24°C (75°F). It's a beautiful day with clear skies. The forecast for the rest of the week shows a slight chance of rain on Thursday, with temperatures gradually rising to 28°C by the weekend. Humidity levels are moderate at around 65%, and wind speeds are light at 8 km/h from the southeast. Air quality is good with an index of 42. The UV index is high at 7, so it's recommended to wear sunscreen if you're planning to spend time outdoors. Sunrise was at 5:24 AM and sunset will be at 6:48 PM, giving Tokyo approximately 13 hours and 24 minutes of daylight today. The moon is currently in its waxing gibbous phase."
      }
    ]
  },
  {
    id: "c3e5f8c3-a1d9-4e67-b3f2-c9d8e7a6b5f4",
    role: "user",
    parts: [
      {
        type: "text",
        text: "Can you recommend some popular tourist attractions in Kyoto?"
      }
    ]
  },
  {
    id: "d4f5g8c3-a1d9-4e67-b3f2-c9d8e7a6b5f4",
    role: "assistant",
    parts: [
      {
        type: "text",
        text: "Kyoto is known for its beautiful temples, traditional tea houses, and gardens. Some popular attractions include Kinkaku-ji (Golden Pavilion) with its stunning gold leaf exterior reflecting in the mirror pond, Fushimi Inari Shrine with its thousands of vermilion torii gates winding up the mountainside, Arashiyama Bamboo Grove where towering stalks create an otherworldly atmosphere, Kiyomizu-dera Temple perched on a hillside offering panoramic views of the city, and the historic Gion district where you might spot geisha hurrying to evening appointments through narrow stone-paved streets lined with traditional wooden machiya houses."
      }
    ]
  }
])
</script>

<template>
  <UChatMessages auto-scroll-icon="i-lucide-chevron-down" :should-scroll-to-bottom="false" :messages="messages" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.arrowDown` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.arrowDown` key.

### Should Auto Scroll

Use the `should-auto-scroll` prop to enable/disable continuous auto scroll while messages are streaming. Defaults to `false`.

```vue
<template>
  <UChatMessages :messages="messages" should-auto-scroll />
</template>
```

### Should Scroll To Bottom

Use the `should-scroll-to-bottom` prop to enable/disable bottom auto scroll when the component is mounted. Defaults to `true`.

```vue
<template>
  <UChatMessages :messages="messages" :should-scroll-to-bottom="false" />
</template>
```

## Examples

> [!TIP]
> See: /docs/components/chat
> 
> Check the **Chat** overview page for installation instructions, server setup and usage examples.

### With indicator slot

Use the `#indicator` slot to customize the loading indicator with a [`ChatShimmer`](/docs/components/chat-shimmer) effect.

```vue [ChatMessagesIndicatorSlotExample.vue]
<script setup lang="ts">
import type { UIMessage } from 'ai'
import { Chat } from '@ai-sdk/vue'

const messages: UIMessage[] = [{
  id: '1',
  role: 'user',
  parts: [{ type: 'text', text: 'Hello! Can you help me with something?' }]
}]

const chat = new Chat({
  messages
})

const size = 4
const gap = 2
const totalDots = size * size

const patterns = [
  [[0], [1], [2], [3], [7], [11], [15], [14], [13], [12], [8], [4], [5], [6], [10], [9]],
  [[0, 4, 8, 12], [1, 5, 9, 13], [2, 6, 10, 14], [3, 7, 11, 15]],
  [[5, 6, 9, 10], [1, 4, 7, 8, 11, 14], [0, 3, 12, 15], [1, 4, 7, 8, 11, 14], [5, 6, 9, 10]],
  [[0], [1, 4], [2, 5, 8], [3, 6, 9, 12], [7, 10, 13], [11, 14], [15]]
]

const activeDots = ref<Set<number>>(new Set())
let patternIndex = 0
let stepIndex = 0

function nextStep() {
  const pattern = patterns[patternIndex]
  if (!pattern) return

  activeDots.value = new Set(pattern[stepIndex])
  stepIndex++

  if (stepIndex >= pattern.length) {
    stepIndex = 0
    patternIndex = (patternIndex + 1) % patterns.length
  }
}

const statusMessages = ['Searching...', 'Reading...', 'Analyzing...', 'Thinking...']
const currentIndex = ref(0)
const displayedText = ref(statusMessages[0]!)
const chars = 'abcdefghijklmnopqrstuvwxyz'

function scramble(from: string, to: string) {
  const maxLength = Math.max(from.length, to.length)
  let frame = 0
  const totalFrames = 15

  const step = () => {
    frame++
    let result = ''
    const progress = (frame / totalFrames) * maxLength

    for (let i = 0; i < maxLength; i++) {
      if (i < progress - 2) {
        result += to[i] || ''
      } else if (i < progress) {
        result += chars[Math.floor(Math.random() * chars.length)]
      } else {
        result += from[i] || ''
      }
    }

    displayedText.value = result

    if (frame < totalFrames) {
      requestAnimationFrame(step)
    } else {
      displayedText.value = to
    }
  }

  requestAnimationFrame(step)
}

let matrixInterval: ReturnType<typeof setInterval> | undefined
let textInterval: ReturnType<typeof setInterval> | undefined

onMounted(() => {
  nextStep()
  matrixInterval = setInterval(nextStep, 120)
  textInterval = setInterval(() => {
    const prev = displayedText.value
    currentIndex.value = (currentIndex.value + 1) % statusMessages.length
    scramble(prev, statusMessages[currentIndex.value]!)
  }, 3000)
})

onUnmounted(() => {
  clearInterval(matrixInterval)
  clearInterval(textInterval)
})
</script>

<template>
  <UChatMessages
    :messages="chat.messages"
    status="submitted"
    :should-scroll-to-bottom="false"
  >
    <template #indicator>
      <div class="flex items-center gap-2 text-muted overflow-hidden">
        <div
          class="shrink-0 grid size-4"
          :style="{
            gridTemplateColumns: `repeat(${size}, 1fr)`,
            gap: `${gap}px`
          }"
        >
          <span
            v-for="i in totalDots"
            :key="i"
            class="rounded-sm bg-current transition-opacity duration-100"
            :class="activeDots.has(i - 1) ? 'opacity-100' : 'opacity-20'"
          />
        </div>

        <UChatShimmer :text="displayedText" class="text-sm font-mono" />
      </div>
    </template>
  </UChatMessages>
</template>
```

## API

### Props

```ts
/**
 * Props for the ChatMessages component
 */
interface ChatMessagesProps {
  messages?: T | undefined;
  status?: ChatStatus | undefined;
  /**
   * Whether to automatically scroll to the bottom when a message is streaming.
   * @default "false"
   */
  shouldAutoScroll?: boolean | undefined;
  /**
   * Whether to scroll to the bottom on mounted.
   * @default "true"
   */
  shouldScrollToBottom?: boolean | undefined;
  /**
   * Display an auto scroll button.
   * `{ size: 'md', color: 'neutral', variant: 'outline' }`{lang="ts-type"}
   * @default "true"
   */
  autoScroll?: boolean | Omit<ButtonProps, LinkPropsKeys> | undefined;
  /**
   * The icon displayed in the auto scroll button.
   */
  autoScrollIcon?: any;
  /**
   * The `user` messages props.
   * `{ side: 'right', variant: 'soft' }`{lang="ts-type"}
   */
  user?: Pick<PropsBase<T>, "actions" | "ui" | "variant" | "icon" | "avatar" | "side"> | undefined;
  /**
   * The `assistant` messages props.
   * `{ side: 'left', variant: 'naked' }`{lang="ts-type"}
   */
  assistant?: Pick<PropsBase<T>, "actions" | "ui" | "variant" | "icon" | "avatar" | "side"> | undefined;
  /**
   * Render the messages in a compact style.
   * This is done automatically when used inside a `UChatPalette`{lang="ts-type"}.
   */
  compact?: boolean | undefined;
  /**
   * The spacing offset for the last message in px. Can be useful when the prompt is sticky for example.
   * @default "0"
   */
  spacingOffset?: number | undefined;
  ui?: { root?: SlotClass; indicator?: SlotClass; viewport?: SlotClass; autoScroll?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the ChatMessages component
 */
interface ChatMessagesSlots {
  default(): any;
  indicator(): any;
  viewport(): any;
  header(): any;
  leading(): any;
  files(): any;
  body(): any;
  content(): any;
  actions(): any;
}
```

> [!TIP]
> 
> You can use all the slots of the [`ChatMessage`](/docs/components/chat-message#slots) component inside ChatMessages, they are automatically forwarded allowing you to customize individual messages when using the `messages` prop.
> 
> ```vue
> <script setup lang="ts">
> import { isTextUIPart } from 'ai'
> </script>
> 
> <template>
>   <UChatMessages :messages="messages" :status="status">
>     <template #content="{ message }">
>       <template
>         v-for="(part, index) in message.parts"
>         :key="`${message.id}-${part.type}-${index}`"
>       >
>         <p v-if="isTextUIPart(part)" class="whitespace-pre-wrap">
>           {{ part.text }}
>         </p>
>       </template>
>     </template>
>   </UChatMessages>
> </template>
> ```

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
          registerMessageRef
        </span>
        
        <span class="sMK4o">
          (
        </span>
        
        <span class="sHdIc">
          id
        </span>
        
        <span class="sMK4o">
          :
        </span>
        
        <span class="sBMFI">
          string
        </span>
        
        <span class="sMK4o">
          ,
        </span>
        
        <span class="sHdIc">
          element
        </span>
        
        <span class="sMK4o">
          :
        </span>
        
        <span class="sBMFI">
          ComponentPublicInstance
        </span>
        
        <span class="sMK4o">
          |
        </span>
        
        <span class="sBMFI">
          null
        </span>
        
        <span class="sMK4o">
          )
        </span>
      </code>
    </td>
    
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          void
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
    chatMessages: {
      slots: {
        root: 'w-full flex flex-col gap-1 flex-1 px-2.5 [&>article]:last-of-type:min-h-(--last-message-height)',
        indicator: 'h-6 flex items-center gap-1 py-3 *:size-2 *:rounded-full *:bg-elevated [&>*:nth-child(1)]:animate-[bounce_1s_infinite] [&>*:nth-child(2)]:animate-[bounce_1s_0.15s_infinite] [&>*:nth-child(3)]:animate-[bounce_1s_0.3s_infinite]',
        viewport: 'absolute inset-x-0 top-[86%] data-[state=open]:animate-[fade-in_200ms_ease-out] data-[state=closed]:animate-[fade-out_200ms_ease-in]',
        autoScroll: 'rounded-full absolute right-1/2 translate-x-1/2 bottom-0'
      },
      variants: {
        compact: {
          true: '',
          false: ''
        }
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/ChatMessages.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/chat-messages.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

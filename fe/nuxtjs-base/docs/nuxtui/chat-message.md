---
title: "ChatMessage"
description: "Display a chat message with icon, avatar, and actions."
canonical_url: "https://ui.nuxt.com/docs/components/chat-message"
---
# ChatMessage

> Display a chat message with icon, avatar, and actions.

## Usage

The ChatMessage component renders an `<article>` element for a `user` or `assistant` chat message.

```vue
<template>
  <u-chat-message :avatar={"src":"https://github.com/benjamincanac.png","loading":"lazy"} :parts=[{"type":"text","id":"1","text":"Hello! Tell me more about building AI chatbots with Nuxt UI."}] id=1 role=user side=right variant=soft />
</template>
```

> [!TIP]
> See: /docs/components/chat-messages
> 
> Use the `ChatMessages` component to display a list of chat messages.

### Parts

Use the `parts` prop to display the message content using the AI SDK format.

```vue
<template>
  <UChatMessage :parts="[
  {
    type: 'text',
    id: '1',
    text: 'Hello! Tell me more about building AI chatbots with Nuxt UI.'
  }
]" role="user" id="1" />
</template>
```

> [!NOTE]
> 
> The `parts` prop is the recommended format for the AI SDK. Each part has a `type` (e.g., 'text') and corresponding content. The ChatMessage component also supports the deprecated `content` prop for backward compatibility.

### Side

Use the `side` prop to display the message on the left or right.

```vue
<template>
  <UChatMessage side="right" :parts="[
  {
    type: 'text',
    id: '1',
    text: 'Hello! Tell me more about building AI chatbots with Nuxt UI.'
  }
]" role="user" id="1" />
</template>
```

> [!NOTE]
> 
> When using the [`ChatMessages`](/docs/components/chat-messages) component, the `side` prop is set to `left` for `assistant` messages and `right` for `user` messages.

### Variant

Use the `variant` prop to change style of the message.

```vue
<template>
  <UChatMessage variant="soft" :parts="[
  {
    type: 'text',
    id: '1',
    text: 'Hello! Tell me more about building AI chatbots with Nuxt UI.'
  }
]" role="user" id="1" />
</template>
```

> [!NOTE]
> 
> When using the [`ChatMessages`](/docs/components/chat-messages) component, the `variant` prop is set to `naked` for `assistant` messages and `soft` for `user` messages.

### Color `4.8+`

Use the `color` prop to change the color of the message.

```vue
<template>
  <UChatMessage variant="soft" color="primary" :parts="[
  {
    type: 'text',
    id: '1',
    text: 'Hello! Tell me more about building AI chatbots with Nuxt UI.'
  }
]" role="user" id="1" />
</template>
```

### Icon

Use the `icon` prop to display an [Icon](/docs/components/icon) component next to the message.

```vue
<template>
  <UChatMessage icon="i-lucide-user" variant="soft" side="right" :parts="[
  {
    type: 'text',
    id: '1',
    text: 'Hello! Tell me more about building AI chatbots with Nuxt UI.'
  }
]" role="user" id="1" />
</template>
```

### Avatar

Use the `avatar` prop to display an [Avatar](/docs/components/avatar) component next to the message.

```vue
<template>
  <UChatMessage :avatar="{
  src: 'https://github.com/benjamincanac.png',
  loading: 'lazy'
}" variant="soft" side="right" :parts="[
  {
    type: 'text',
    id: '1',
    text: 'Hello! Tell me more about building AI chatbots with Nuxt UI.'
  }
]" role="user" id="1" />
</template>
```

You can also use the `avatar.icon` prop to display an icon as the avatar.

```vue
<template>
  <UChatMessage :avatar="{
  icon: 'i-lucide-bot'
}" :parts="[
  {
    type: 'text',
    id: '1',
    text: 'Nuxt UI offers several features for building AI chatbots including the ChatMessage, ChatMessages, and ChatPrompt components. Best practices include using the Chat class from AI SDK, implementing proper message styling with variants, and utilizing the built-in actions for message interactions. The components are fully customizable with theming support and responsive design.'
  }
]" role="assistant" id="1" />
</template>
```

### Actions

Use the `actions` prop to display actions below the message that will be displayed when hovering over the message.

```vue
<script setup lang="ts">
import type { ButtonProps } from '@nuxt/ui'

const actions = ref<ButtonProps[]>([
  {
    label: "Copy to clipboard",
    icon: "i-lucide-copy"
  }
])
</script>

<template>
  <UChatMessage :actions="actions" :parts="[
  {
    type: 'text',
    id: '1',
    text: 'Nuxt UI offers several features for building AI chatbots including the ChatMessage, ChatMessages, and ChatPrompt components. Best practices include using the Chat class from AI SDK, implementing proper message styling with variants, and utilizing the built-in actions for message interactions. The components are fully customizable with theming support and responsive design.'
  }
]" role="user" id="1" />
</template>
```

## Examples

> [!TIP]
> See: /docs/components/chat
> 
> Check the **Chat** overview page for installation instructions, server setup and usage examples.

## API

### Props

```ts
/**
 * Props for the ChatMessage component
 */
interface ChatMessageProps {
  /**
   * A unique identifier for the message.
   */
  id: string;
  /**
   * The role of the message.
   */
  role: "system" | "user" | "assistant";
  /**
   * The parts of the message. Use this for rendering the message in the UI.
   * 
   * System messages should be avoided (set the system prompt on the server instead).
   * They can have text parts.
   * 
   * User messages can have text parts and file parts.
   * 
   * Assistant messages can have text, reasoning, tool invocation, and file parts.
   */
  parts: UIMessagePart<TDataParts, TTools>[];
  /**
   * The element or component this component should render as.
   * @default "\"article\""
   */
  as?: any;
  icon?: any;
  avatar?: (AvatarProps & { [key: string]: any; }) | undefined;
  variant?: "solid" | "outline" | "soft" | "subtle" | "naked" | undefined;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  side?: "left" | "right" | undefined;
  /**
   * Display a list of actions under the message.
   * The `label` will be used in a tooltip.
   * `{ size: 'xs', color: 'neutral', variant: 'ghost' }`{lang="ts-type"}
   */
  actions?: (Omit<ButtonProps, "onClick"> & { onClick?: ((e: MouseEvent, message: UIMessage<TMetadata, TDataParts, TTools>) => void) | undefined; })[] | undefined;
  /**
   * Render the message in a compact style.
   * This is done automatically when used inside a `UChatPalette`{lang="ts-type"}.
   */
  compact?: boolean | undefined;
  content?: string | undefined;
  ui?: { root?: SlotClass; header?: SlotClass; container?: SlotClass; body?: SlotClass; leading?: SlotClass; leadingIcon?: SlotClass; leadingAvatar?: SlotClass; leadingAvatarSize?: SlotClass; files?: SlotClass; content?: SlotClass; actions?: SlotClass; } | undefined;
  /**
   * The metadata of the message.
   */
  metadata?: TMetadata | undefined;
}
```

### Slots

```ts
/**
 * Slots for the ChatMessage component
 */
interface ChatMessageSlots {
  header(): any;
  leading(): any;
  files(): any;
  body(): any;
  content(): any;
  actions(): any;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    chatMessage: {
      slots: {
        root: 'group/message relative w-full',
        header: 'flex mb-1.5',
        container: 'relative flex items-start',
        body: 'min-w-0',
        leading: 'inline-flex items-center justify-center min-h-6',
        leadingIcon: 'shrink-0',
        leadingAvatar: 'shrink-0',
        leadingAvatarSize: '',
        files: 'flex items-center gap-1.5',
        content: 'relative text-pretty wrap-break-word *:first:mt-0 *:last:mb-0',
        actions: [
          '[@media(hover:hover)]:opacity-0 group-hover/message:opacity-100 absolute bottom-0 flex items-center',
          'transition-opacity'
        ]
      },
      variants: {
        variant: {
          solid: '',
          outline: '',
          soft: '',
          subtle: '',
          naked: ''
        },
        color: {
          primary: '',
          secondary: '',
          success: '',
          info: '',
          warning: '',
          error: '',
          neutral: ''
        },
        side: {
          left: {},
          right: {
            container: 'justify-end ms-auto max-w-[75%]',
            header: 'justify-end',
            actions: 'right-0'
          }
        },
        leading: {
          true: ''
        },
        actions: {
          true: ''
        },
        compact: {
          true: {
            root: 'scroll-mt-3',
            container: 'gap-1.5 pb-3',
            content: 'space-y-2',
            leadingIcon: 'size-5',
            leadingAvatarSize: '2xs'
          },
          false: {
            root: 'scroll-mt-4 sm:scroll-mt-6',
            container: 'gap-3 pb-8',
            content: 'space-y-4',
            leadingIcon: 'size-8',
            leadingAvatarSize: 'md'
          }
        }
      },
      compoundVariants: [
        {
          compact: true,
          actions: true,
          class: {
            container: 'pb-8'
          }
        },
        {
          variant: [
            'solid',
            'outline',
            'soft',
            'subtle'
          ],
          compact: false,
          class: {
            content: 'px-4 py-3 rounded-lg min-h-12',
            leading: 'mt-2'
          }
        },
        {
          variant: [
            'solid',
            'outline',
            'soft',
            'subtle'
          ],
          compact: true,
          class: {
            content: 'px-2 py-1 rounded-lg min-h-8',
            leading: 'mt-1'
          }
        },
        {
          variant: 'naked',
          side: 'left',
          class: {
            content: 'w-full'
          }
        },
        {
          color: 'primary',
          variant: 'solid',
          class: {
            content: 'bg-primary text-inverted'
          }
        },
        {
          color: 'secondary',
          variant: 'solid',
          class: {
            content: 'bg-secondary text-inverted'
          }
        },
        {
          color: 'success',
          variant: 'solid',
          class: {
            content: 'bg-success text-inverted'
          }
        },
        {
          color: 'info',
          variant: 'solid',
          class: {
            content: 'bg-info text-inverted'
          }
        },
        {
          color: 'warning',
          variant: 'solid',
          class: {
            content: 'bg-warning text-inverted'
          }
        },
        {
          color: 'error',
          variant: 'solid',
          class: {
            content: 'bg-error text-inverted'
          }
        },
        {
          color: 'primary',
          variant: 'outline',
          class: {
            content: 'text-primary ring ring-primary/25'
          }
        },
        {
          color: 'secondary',
          variant: 'outline',
          class: {
            content: 'text-secondary ring ring-secondary/25'
          }
        },
        {
          color: 'success',
          variant: 'outline',
          class: {
            content: 'text-success ring ring-success/25'
          }
        },
        {
          color: 'info',
          variant: 'outline',
          class: {
            content: 'text-info ring ring-info/25'
          }
        },
        {
          color: 'warning',
          variant: 'outline',
          class: {
            content: 'text-warning ring ring-warning/25'
          }
        },
        {
          color: 'error',
          variant: 'outline',
          class: {
            content: 'text-error ring ring-error/25'
          }
        },
        {
          color: 'primary',
          variant: 'soft',
          class: {
            content: 'bg-primary/10 text-primary'
          }
        },
        {
          color: 'secondary',
          variant: 'soft',
          class: {
            content: 'bg-secondary/10 text-secondary'
          }
        },
        {
          color: 'success',
          variant: 'soft',
          class: {
            content: 'bg-success/10 text-success'
          }
        },
        {
          color: 'info',
          variant: 'soft',
          class: {
            content: 'bg-info/10 text-info'
          }
        },
        {
          color: 'warning',
          variant: 'soft',
          class: {
            content: 'bg-warning/10 text-warning'
          }
        },
        {
          color: 'error',
          variant: 'soft',
          class: {
            content: 'bg-error/10 text-error'
          }
        },
        {
          color: 'primary',
          variant: 'subtle',
          class: {
            content: 'bg-primary/10 text-primary ring ring-primary/25'
          }
        },
        {
          color: 'secondary',
          variant: 'subtle',
          class: {
            content: 'bg-secondary/10 text-secondary ring ring-secondary/25'
          }
        },
        {
          color: 'success',
          variant: 'subtle',
          class: {
            content: 'bg-success/10 text-success ring ring-success/25'
          }
        },
        {
          color: 'info',
          variant: 'subtle',
          class: {
            content: 'bg-info/10 text-info ring ring-info/25'
          }
        },
        {
          color: 'warning',
          variant: 'subtle',
          class: {
            content: 'bg-warning/10 text-warning ring ring-warning/25'
          }
        },
        {
          color: 'error',
          variant: 'subtle',
          class: {
            content: 'bg-error/10 text-error ring ring-error/25'
          }
        },
        {
          color: 'primary',
          variant: 'naked',
          class: {
            content: 'text-primary'
          }
        },
        {
          color: 'secondary',
          variant: 'naked',
          class: {
            content: 'text-secondary'
          }
        },
        {
          color: 'success',
          variant: 'naked',
          class: {
            content: 'text-success'
          }
        },
        {
          color: 'info',
          variant: 'naked',
          class: {
            content: 'text-info'
          }
        },
        {
          color: 'warning',
          variant: 'naked',
          class: {
            content: 'text-warning'
          }
        },
        {
          color: 'error',
          variant: 'naked',
          class: {
            content: 'text-error'
          }
        },
        {
          color: 'neutral',
          variant: 'solid',
          class: {
            content: 'bg-inverted text-inverted'
          }
        },
        {
          color: 'neutral',
          variant: 'outline',
          class: {
            content: 'bg-default ring ring-default'
          }
        },
        {
          color: 'neutral',
          variant: 'soft',
          class: {
            content: 'bg-elevated/50'
          }
        },
        {
          color: 'neutral',
          variant: 'subtle',
          class: {
            content: 'bg-elevated/50 ring ring-default'
          }
        }
      ],
      defaultVariants: {
        side: 'left',
        variant: 'naked',
        color: 'neutral'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/ChatMessage.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/chat-message.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

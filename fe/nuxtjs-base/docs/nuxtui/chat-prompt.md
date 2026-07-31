---
title: "ChatPrompt"
description: "An enhanced Textarea for submitting prompts in AI chat interfaces."
canonical_url: "https://ui.nuxt.com/docs/components/chat-prompt"
---
# ChatPrompt

> An enhanced Textarea for submitting prompts in AI chat interfaces.

## Usage

The ChatPrompt component renders a `<form>` element and extends the [Textarea](/docs/components/textarea) component so you can pass any property such as `icon`, `placeholder`, `autofocus`, etc.

```vue
<template>
  <u-chat-prompt variant=subtle>
  <u-chat-prompt-submit color=neutral />
  <template v-slot:footer=>
  <u-select :items=[{"label":"Claude Opus 4.6","value":"claude-opus-4.6","icon":"i-simple-icons-anthropic"},{"label":"Gemini 3 Pro","value":"gemini-3-pro","icon":"i-simple-icons-googlegemini"},{"label":"GPT-5","value":"gpt-5","icon":"i-simple-icons-openai"}] icon=i-simple-icons-anthropic modelValue=claude-opus-4.6 placeholder=Select a model variant=ghost /></template></u-chat-prompt>
</template>
```

> [!NOTE]
> 
> The ChatPrompt handles the following events:
> 
> - The form is submitted when the user presses <kbd value="enter">
> 
> 
> 
> </kbd>
> 
>  or when the user clicks on the submit button. Set the `submit-on-enter` prop to `false` to submit with <kbd value="ctrl">
> 
> 
> 
> </kbd>
> 
>  + <kbd value="enter">
> 
> 
> 
> </kbd>
> 
>  (or <kbd value="cmd">
> 
> 
> 
> </kbd>
> 
>  + <kbd value="enter">
> 
> 
> 
> </kbd>
> 
>  on macOS) instead, allowing <kbd value="enter">
> 
> 
> 
> </kbd>
> 
>  to insert a newline.
> - The textarea is blurred when <kbd value="escape">
> 
> 
> 
> </kbd>
> 
>  is pressed and emits a `close` event.

### Variant

Use the `variant` prop to change the style of the prompt. Defaults to `outline`.

```vue
<template>
  <UChatPrompt variant="soft" />
</template>
```

## Examples

> [!TIP]
> See: /docs/components/chat
> 
> Check the **Chat** overview page for installation instructions, server setup and usage examples.

### As a starting point

You can also use it as a starting point for a chat interface.

```vue [pages/index.vue]
<script setup lang="ts">
import { Chat } from '@ai-sdk/vue'

const input = ref('')

const chat = new Chat()

async function onSubmit() {
  chat.sendMessage({ text: input.value })

  // Navigate to chat page after first message
  if (chat.messages.length === 1) {
    await navigateTo('/chat')
  }
}
</script>

<template>
  <UDashboardPanel>
    <template #body>
      <UContainer>
        <h1>How can I help you today?</h1>

        <UChatPrompt v-model="input" @submit="onSubmit">
          <UChatPromptSubmit :status="chat.status" />
        </UChatPrompt>
      </UContainer>
    </template>
  </UDashboardPanel>
</template>
```

## API

### Props

```ts
/**
 * Props for the ChatPrompt component
 */
interface ChatPromptProps {
  /**
   * The element or component this component should render as.
   * @default "\"form\""
   */
  as?: any;
  /**
   * The placeholder text for the textarea.
   */
  placeholder?: string | undefined;
  variant?: "outline" | "soft" | "subtle" | "naked" | undefined;
  /**
   * When `true`, pressing `Enter` submits and `Shift+Enter` inserts a newline.
   * When `false`, pressing `Enter` inserts a newline and `Ctrl+Enter` / `Cmd+Enter` submits.
   * @default "true"
   */
  submitOnEnter?: boolean | undefined;
  error?: Error | undefined;
  ui?: ({ root?: SlotClass; header?: SlotClass; body?: SlotClass; footer?: SlotClass; base?: SlotClass; } & { root?: SlotClass; base?: SlotClass; leading?: SlotClass; leadingIcon?: SlotClass; leadingAvatar?: SlotClass; leadingAvatarSize?: SlotClass; trailing?: SlotClass; trailingIcon?: SlotClass; }) | undefined;
  /**
   * @default "true"
   */
  autofocus?: boolean | undefined;
  disabled?: boolean | undefined;
  /**
   * Display an icon based on the `leading` and `trailing` props.
   */
  icon?: any;
  /**
   * Display an avatar on the left side.
   */
  avatar?: AvatarProps | undefined;
  /**
   * When `true`, the loading icon will be displayed.
   */
  loading?: boolean | undefined;
  /**
   * The icon when the `loading` prop is `true`.
   */
  loadingIcon?: any;
  /**
   * @default "1"
   */
  rows?: number | undefined;
  autofocusDelay?: number | undefined;
  /**
   * @default "true"
   */
  autoresize?: boolean | undefined;
  autoresizeDelay?: number | undefined;
  maxrows?: number | undefined;
  /**
   * @default "\"\""
   */
  modelValue?: string | undefined;
}
```

> [!NOTE]
> See: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/textarea#attributes
> 
> This component also supports all native `<textarea>` HTML attributes.

### Slots

```ts
/**
 * Slots for the ChatPrompt component
 */
interface ChatPromptSlots {
  header(): any;
  footer(): any;
  leading(): any;
  default(): any;
  trailing(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the ChatPrompt component
 */
interface ChatPromptEmits {
  submit: (payload: [event: Event]) => void;
  close: (payload: [event: Event]) => void;
  update:modelValue: (payload: [value: string]) => void;
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
          textareaRef
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
          HTMLTextAreaElement
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
    chatPrompt: {
      slots: {
        root: 'relative flex flex-col items-stretch gap-2 px-2.5 py-2 w-full rounded-lg backdrop-blur',
        header: 'flex items-center gap-1.5',
        body: 'items-start',
        footer: 'flex items-center justify-between gap-1.5',
        base: ''
      },
      variants: {
        variant: {
          outline: {
            root: 'bg-default/75 ring ring-default'
          },
          soft: {
            root: 'bg-elevated/50'
          },
          subtle: {
            root: 'bg-elevated/50 ring ring-default'
          },
          naked: {
            root: ''
          }
        }
      },
      defaultVariants: {
        variant: 'outline'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/ChatPrompt.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/chat-prompt.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

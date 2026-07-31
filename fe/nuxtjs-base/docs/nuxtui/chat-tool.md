---
title: "ChatTool"
description: "Display a collapsible AI tool invocation status."
canonical_url: "https://ui.nuxt.com/docs/components/chat-tool"
---
# ChatTool

> Display a collapsible AI tool invocation status.

## Usage

The ChatTool component renders a collapsible block that displays AI tool invocation status, such as "Searching components" or "Reading documentation". When a default slot is provided, it becomes collapsible to reveal tool output.

```vue [ChatToolExample.vue]
<script setup lang="ts">
const streaming = ref(true)
const result = ref(`$ pnpm run lint

> eslint .

✔ No lint errors found.
`)

let timer: ReturnType<typeof setTimeout> | undefined

onMounted(() => {
  timer = setTimeout(() => {
    streaming.value = false
  }, 5000)
})

onUnmounted(() => {
  clearTimeout(timer)
})
</script>

<template>
  <UChatTool
    :text="streaming ? 'Running lint checks' : 'Lint checks completed'"
    suffix="cd, pnpm run"
    :streaming="streaming"
    icon="i-lucide-terminal"
    variant="card"
    chevron="leading"
    class="w-80"
  >
    <pre language="bash" v-text="result" />
  </UChatTool>
</template>
```

### Text

Use the `text` prop to set the tool status text.

```vue
<template>
  <UChatTool text="Searched components" />
</template>
```

### Suffix

Use the `suffix` prop to display secondary text after the main label.

```vue
<template>
  <UChatTool text="Reading component" suffix="Button" />
</template>
```

### Streaming

Use the `streaming` prop to indicate the tool is actively running. The text displays a shimmer animation.

```vue
<template>
  <UChatTool streaming text="Searching components..." />
</template>
```

> [!TIP]
> 
> Use the `isToolStreaming` utility from `@nuxt/ui/utils/ai` to determine if a tool part is still running.

### Shimmer

When streaming, the trigger label uses the [`ChatShimmer`](/docs/components/chat-shimmer) component. Use the `shimmer` prop to customize its `duration` and `spread`.

```vue
<template>
  <UChatTool streaming text="Searching components..." :shimmer="{
  duration: 2,
  spread: 2
}" />
</template>
```

### Icon

Use the `icon` prop to display an [Icon](/docs/components/icon) component next to the trigger.

```vue
<template>
  <UChatTool icon="i-lucide-search" text="Searched components" />
</template>
```

### Loading

Use the `loading` prop to show a loading indicator. Use the `loading-icon` prop to customize the loading icon.

```vue
<template>
  <UChatTool loading text="Searching components..." />
</template>
```

### Loading Icon

Use the `loading-icon` prop to customize the loading icon. Defaults to `i-lucide-loader-circle`.

```vue
<template>
  <UChatTool loading loading-icon="i-lucide-loader" text="Searching components..." />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.loading` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.loading` key.

### Chevron

Use the `chevron` prop to change the position of the chevron icon.

> [!NOTE]
> 
> When `chevron` is set to `leading` with an `icon`, the icon swaps with the chevron on hover and when open.

```vue
<template>
  <UChatTool chevron="leading" icon="i-lucide-search" text="Searched components">
    Tool output content
  </UChatTool>
</template>
```

### Chevron Icon

Use the `chevron-icon` prop to customize the chevron [Icon](/docs/components/icon). Defaults to `i-lucide-chevron-down`.

```vue
<template>
  <UChatTool chevron-icon="i-lucide-arrow-down" text="Searched components">
    Tool output content
  </UChatTool>
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.chevronDown` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.chevronDown` key.

### Variant

Use the `variant` prop to change the visual style. Defaults to `inline`.

```vue
<template>
  <UChatTool variant="card" text="Searched components" icon="i-lucide-search" chevron="trailing">
    Tool output content
  </UChatTool>
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
 * Props for the ChatTool component
 */
interface ChatToolProps {
  /**
   * The text content to display.
   */
  text?: string | undefined;
  /**
   * The suffix text displayed after the main text.
   */
  suffix?: string | undefined;
  /**
   * The icon displayed next to the trigger.
   */
  icon?: any;
  /**
   * Whether the tool is in a loading state.
   * @default "false"
   */
  loading?: boolean | undefined;
  /**
   * The icon displayed when loading.
   */
  loadingIcon?: any;
  /**
   * Whether the tool content is currently streaming.
   * @default "false"
   */
  streaming?: boolean | undefined;
  /**
   * The visual variant of the tool display.
   * @default "\"inline\""
   */
  variant?: "inline" | "card" | undefined;
  /**
   * The position of the chevron icon.
   * @default "\"trailing\""
   */
  chevron?: "leading" | "trailing" | undefined;
  /**
   * The icon displayed as the chevron.
   */
  chevronIcon?: any;
  /**
   * Customize the [`ChatShimmer`](https://ui.nuxt.com/docs/components/chat-shimmer) component when streaming.
   */
  shimmer?: Partial<Omit<ChatShimmerProps, "text">> | undefined;
  ui?: { root?: SlotClass; trigger?: SlotClass; leading?: SlotClass; leadingIcon?: SlotClass; chevronIcon?: SlotClass; label?: SlotClass; suffix?: SlotClass; trailingIcon?: SlotClass; content?: SlotClass; body?: SlotClass; } | undefined;
  /**
   * When `true`, prevents the user from interacting with the collapsible.
   */
  disabled?: boolean | undefined;
  /**
   * The controlled open state of the collapsible. Can be binded with `v-model`.
   * @default "undefined"
   */
  open?: boolean | undefined;
  /**
   * The open state of the collapsible when it is initially rendered. <br> Use when you do not need to control its open state.
   */
  defaultOpen?: boolean | undefined;
  /**
   * When `true`, the element will be unmounted on closed state.
   * @default "false"
   */
  unmountOnHide?: boolean | undefined;
}
```

### Slots

```ts
/**
 * Slots for the ChatTool component
 */
interface ChatToolSlots {
  default(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the ChatTool component
 */
interface ChatToolEmits {
  update:open: (payload: [value: boolean]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    chatTool: {
      slots: {
        root: '',
        trigger: [
          'group flex w-full items-center gap-1.5 text-muted text-sm disabled:cursor-default disabled:hover:text-muted hover:text-default min-w-0',
          'transition-colors'
        ],
        leading: 'relative size-4 shrink-0',
        leadingIcon: 'size-4 shrink-0',
        chevronIcon: 'size-4 shrink-0 group-data-[state=open]:rotate-180 transition-transform duration-200',
        label: 'truncate',
        suffix: 'text-dimmed ms-1',
        trailingIcon: 'size-4 shrink-0 group-data-[state=open]:rotate-180 transition-transform duration-200',
        content: 'data-[state=open]:animate-[collapsible-down_200ms_ease-out] data-[state=closed]:animate-[collapsible-up_200ms_ease-out] data-[state=closed]:overflow-hidden',
        body: 'text-sm text-dimmed whitespace-pre-wrap'
      },
      variants: {
        variant: {
          inline: {
            trigger: 'rounded-sm outline-primary/25 focus-visible:outline-3',
            body: 'pt-2'
          },
          card: {
            root: 'rounded-md ring ring-default overflow-hidden outline-primary/25 has-focus-visible:outline-3 has-focus-visible:ring-primary',
            trigger: 'px-2 py-1 focus:outline-none',
            trailingIcon: 'ms-auto',
            body: 'border-t border-default p-2 max-h-[200px] overflow-y-auto focus:outline-none'
          }
        },
        chevron: {
          leading: {
            leadingIcon: 'group-hover:opacity-0'
          },
          trailing: ''
        },
        loading: {
          true: {
            leadingIcon: 'animate-spin'
          }
        },
        alone: {
          false: {
            leadingIcon: [
              'absolute inset-0 group-data-[state=open]:opacity-0',
              'transition-opacity duration-200'
            ],
            chevronIcon: [
              'absolute inset-0 opacity-0 group-hover:opacity-100 group-data-[state=open]:opacity-100',
              'transition-[rotate,opacity] duration-200'
            ]
          }
        }
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/ChatTool.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/chat-tool.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

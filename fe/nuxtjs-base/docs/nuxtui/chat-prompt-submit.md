---
title: "ChatPromptSubmit"
description: "A Button for submitting chat prompts with automatic status handling."
canonical_url: "https://ui.nuxt.com/docs/components/chat-prompt-submit"
---
# ChatPromptSubmit

> A Button for submitting chat prompts with automatic status handling.

## Usage

The ChatPromptSubmit component is used inside the [ChatPrompt](/docs/components/chat-prompt) component to submit the prompt. It automatically handles the different `status` values to control the chat.

It extends the [Button](/docs/components/button) component, so you can pass any property such as `color`, `variant`, `size`, etc.

```vue
<template>
  <u-chat-prompt-submit />
  <template v-slot:code=>
  <pre className=language-vue shiki shiki-themes material-theme-lighter material-theme material-theme-palenight code=<template>
    <UChatPrompt>
      <UChatPromptSubmit />
    </UChatPrompt>
  </template>
   language=vue meta= style=>
  <code __ignoreMap=>
  <span class=line>
  <span class=sMK4o>
  <</span>
  <span class=swJcz>
  template</span>
  <span class=sMK4o>
  >
  </span></span>
  <span class=line>
  <span class=sMK4o>
    <</span>
  <span class=swJcz>
  UChatPrompt</span>
  <span class=sMK4o>
  >
  </span></span>
  <span class=line>
  <span class=sMK4o>
      <</span>
  <span class=swJcz>
  UChatPromptSubmit</span>
  <span class=sMK4o>
   />
  </span></span>
  <span class=line>
  <span class=sMK4o>
    </</span>
  <span class=swJcz>
  UChatPrompt</span>
  <span class=sMK4o>
  >
  </span></span>
  <span class=line>
  <span class=sMK4o>
  </</span>
  <span class=swJcz>
  template</span>
  <span class=sMK4o>
  >
  </span></span></code></pre></template>
</template>
```

> [!NOTE]
> 
> You can also use it inside the `footer` slot of the [`ChatPrompt`](/docs/components/chat-prompt) component.

### Ready

When its status is `ready`, use the `color`, `variant` and `icon` props to customize the Button. Defaults to:

- `color="primary"`
- `variant="solid"`
- `icon="i-lucide-arrow-up"`

```vue
<template>
  <UChatPromptSubmit color="primary" variant="solid" icon="i-lucide-arrow-up" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.arrowUp` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.arrowUp` key.

### Submitted

When its status is `submitted`, use the `submitted-color`, `submitted-variant` and `submitted-icon` props to customize the Button. Defaults to:

- `submittedColor="neutral"`
- `submittedVariant="subtle"`
- `submittedIcon="i-lucide-square"`

> [!NOTE]
> 
> The `stop` event is emitted when the user clicks on the Button.

```vue
<template>
  <UChatPromptSubmit submitted-color="neutral" submitted-variant="subtle" submitted-icon="i-lucide-square" status="submitted" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.stop` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.stop` key.

### Streaming

When its status is `streaming`, use the `streaming-color`, `streaming-variant` and `streaming-icon` props to customize the Button. Defaults to:

- `streamingColor="neutral"`
- `streamingVariant="subtle"`
- `streamingIcon="i-lucide-square"`

> [!NOTE]
> 
> The `stop` event is emitted when the user clicks on the Button.

```vue
<template>
  <UChatPromptSubmit streaming-color="neutral" streaming-variant="subtle" streaming-icon="i-lucide-square" status="streaming" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.stop` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.stop` key.

### Error

When its status is `error`, use the `error-color`, `error-variant` and `error-icon` props to customize the Button. Defaults to:

- `errorColor="error"`
- `errorVariant="soft"`
- `errorIcon="i-lucide-rotate-ccw"`

> [!NOTE]
> 
> The `reload` event is emitted when the user clicks on the Button.

```vue
<template>
  <UChatPromptSubmit error-color="error" error-variant="soft" error-icon="i-lucide-rotate-ccw" status="error" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.reload` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.reload` key.

## Examples

> [!TIP]
> See: /docs/components/chat
> 
> Check the **Chat** overview page for installation instructions, server setup and usage examples.

## API

### Props

```ts
/**
 * Props for the ChatPromptSubmit component
 */
interface ChatPromptSubmitProps {
  /**
   * @default "\"ready\""
   */
  status?: ChatStatus | undefined;
  /**
   * The icon displayed in the button when the status is `ready`. Set to `false` to hide the icon.
   */
  icon?: any;
  /**
   * The color of the button when the status is `ready`.
   */
  color?: "error" | "neutral" | "primary" | "secondary" | "success" | "info" | "warning" | undefined;
  /**
   * The variant of the button when the status is `ready`.
   */
  variant?: "link" | "solid" | "outline" | "soft" | "subtle" | "ghost" | undefined;
  /**
   * The icon displayed in the button when the status is `streaming`.
   */
  streamingIcon?: any;
  /**
   * The color of the button when the status is `streaming`.
   * @default "\"neutral\""
   */
  streamingColor?: "error" | "neutral" | "primary" | "secondary" | "success" | "info" | "warning" | undefined;
  /**
   * The variant of the button when the status is `streaming`.
   * @default "\"subtle\""
   */
  streamingVariant?: "link" | "solid" | "outline" | "soft" | "subtle" | "ghost" | undefined;
  /**
   * The icon displayed in the button when the status is `submitted`.
   */
  submittedIcon?: any;
  /**
   * The color of the button when the status is `submitted`.
   * @default "\"neutral\""
   */
  submittedColor?: "error" | "neutral" | "primary" | "secondary" | "success" | "info" | "warning" | undefined;
  /**
   * The variant of the button when the status is `submitted`.
   * @default "\"subtle\""
   */
  submittedVariant?: "link" | "solid" | "outline" | "soft" | "subtle" | "ghost" | undefined;
  /**
   * The icon displayed in the button when the status is `error`.
   */
  errorIcon?: any;
  /**
   * The color of the button when the status is `error`.
   * @default "\"error\""
   */
  errorColor?: "error" | "neutral" | "primary" | "secondary" | "success" | "info" | "warning" | undefined;
  /**
   * The variant of the button when the status is `error`.
   * @default "\"soft\""
   */
  errorVariant?: "link" | "solid" | "outline" | "soft" | "subtle" | "ghost" | undefined;
  ui?: ({ base?: SlotClass; } & { base?: SlotClass; label?: SlotClass; leadingIcon?: SlotClass; leadingAvatar?: SlotClass; leadingAvatarSize?: SlotClass; trailingIcon?: SlotClass; }) | undefined;
  /**
   * When `true`, the icon will be displayed on the left side.
   */
  leading?: boolean | undefined;
  /**
   * Class to apply when the link is exact active
   */
  exactActiveClass?: string | undefined;
  /**
   * Pass the returned promise of `router.push()` to `document.startViewTransition()` if supported.
   */
  viewTransition?: boolean | undefined;
  autofocus?: Booleanish | undefined;
  disabled?: boolean | undefined;
  form?: string | undefined;
  formaction?: string | undefined;
  formenctype?: string | undefined;
  formmethod?: string | undefined;
  formnovalidate?: Booleanish | undefined;
  formtarget?: string | undefined;
  name?: string | undefined;
  /**
   * The type of the button when not a link.
   */
  type?: "button" | "submit" | "reset" | undefined;
  onClick?: ((event: MouseEvent) => void | Promise<void>) | ((event: MouseEvent) => void | Promise<void>)[] | undefined;
  /**
   * The element or component this component should render as when not a link.
   */
  as?: any;
  label?: string | undefined;
  activeColor?: "error" | "neutral" | "primary" | "secondary" | "success" | "info" | "warning" | undefined;
  activeVariant?: "link" | "solid" | "outline" | "soft" | "subtle" | "ghost" | undefined;
  size?: "md" | "xs" | "sm" | "lg" | "xl" | undefined;
  /**
   * Render the button with equal padding on all sides.
   */
  square?: boolean | undefined;
  /**
   * Render the button full width.
   */
  block?: boolean | undefined;
  /**
   * Set loading state automatically based on the `@click` promise state
   */
  loadingAuto?: boolean | undefined;
  /**
   * Display an avatar on the left side.
   */
  avatar?: AvatarProps | undefined;
  /**
   * Display an icon on the left side.
   */
  leadingIcon?: any;
  /**
   * When `true`, the icon will be displayed on the right side.
   */
  trailing?: boolean | undefined;
  /**
   * Display an icon on the right side.
   */
  trailingIcon?: any;
  /**
   * When `true`, the loading icon will be displayed.
   */
  loading?: boolean | undefined;
  /**
   * The icon when the `loading` prop is `true`.
   */
  loadingIcon?: any;
}
```

> [!NOTE]
> See: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/button#attributes
> 
> This component also supports all native `<button>` HTML attributes.

### Slots

```ts
/**
 * Slots for the ChatPromptSubmit component
 */
interface ChatPromptSubmitSlots {
  leading(): any;
  default(): any;
  trailing(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the ChatPromptSubmit component
 */
interface ChatPromptSubmitEmits {
  stop: (payload: [event: MouseEvent]) => void;
  reload: (payload: [event: MouseEvent]) => void;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    chatPromptSubmit: {
      slots: {
        base: ''
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/ChatPromptSubmit.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/chat-prompt-submit.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

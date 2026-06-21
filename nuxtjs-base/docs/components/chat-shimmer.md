---
title: "ChatShimmer"
description: "Display a text shimmer animation effect."
canonical_url: "https://ui.nuxt.com/docs/components/chat-shimmer"
---
# ChatShimmer

> Display a text shimmer animation effect.

## Usage

The ChatShimmer component renders an element with an animated shimmer gradient over text, commonly used to indicate streaming or loading states in chat interfaces.

> [!NOTE]
> 
> This component is automatically used by the [`ChatTool`](/docs/components/chat-tool) and [`ChatReasoning`](/docs/components/chat-reasoning) components when streaming.

### Text

Use the `text` prop to set the shimmer text.

```vue
<template>
  <UChatShimmer text="Thinking..." />
</template>
```

### Duration

Use the `duration` prop to control the animation speed in seconds.

```vue
<template>
  <UChatShimmer text="Thinking..." :duration="4" />
</template>
```

### Spread

Use the `spread` prop to control the width of the shimmer highlight. The actual spread is computed as `text.length * spread` in pixels.

```vue
<template>
  <UChatShimmer text="Thinking..." :spread="5" />
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
 * Props for the ChatShimmer component
 */
interface ChatShimmerProps {
  /**
   * The text to display with the shimmer effect.
   */
  text: string;
  /**
   * The element or component this component should render as.
   * @default "\"span\""
   */
  as?: any;
  /**
   * The duration of the shimmer animation in seconds.
   * @default "2"
   */
  duration?: number | undefined;
  /**
   * The spread multiplier for the shimmer highlight. The actual spread is computed as `text.length * spread` in pixels.
   * @default "2"
   */
  spread?: number | undefined;
  ui?: {} | undefined;
}
```

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    chatShimmer: {
      base: 'text-transparent bg-clip-text bg-no-repeat bg-size-[calc(200%+var(--spread)*2+2px)_100%,auto] bg-[image:linear-gradient(90deg,#0000_calc(50%-var(--spread)),var(--ui-text-highlighted),#0000_calc(50%+var(--spread))),linear-gradient(var(--ui-text-muted),var(--ui-text-muted))] animate-[shimmer_var(--duration)_linear_infinite] rtl:animate-[shimmer-rtl_var(--duration)_linear_infinite] will-change-[background-position]'
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/ChatShimmer.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/chat-shimmer.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

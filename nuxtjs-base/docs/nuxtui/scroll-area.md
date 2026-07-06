---
title: "ScrollArea"
description: "A flexible scroll container with virtualization support."
canonical_url: "https://ui.nuxt.com/docs/components/scroll-area"
---
# ScrollArea

> A flexible scroll container with virtualization support.

## Usage

The ScrollArea component creates scrollable containers with optional virtualization for large lists.

```vue [ScrollAreaExample.vue]
<script setup lang="ts">
const heights = [320, 480, 640, 800]

// Pseudo-random height selection with longer cycle to avoid alignment patterns
function getHeight(index: number) {
  const seed = (index * 11 + 7) % 17
  return heights[seed % heights.length]!
}

const items = Array.from({ length: 1000 }).map((_, index) => {
  const height = getHeight(index)

  return {
    id: index,
    title: `Item ${index + 1}`,
    src: `https://picsum.photos/640/${height}?v=${index}`,
    width: 640,
    height
  }
})
</script>

<template>
  <UScrollArea
    v-slot="{ item, index }"
    :items="items"
    orientation="vertical"
    :virtualize="{
      gap: 16,
      lanes: 3,
      estimateSize: 480
    }"
    class="w-full h-128 p-4"
  >
    <img
      :src="item.src"
      :alt="item.title"
      :width="item.width"
      :height="item.height"
      :loading="index > 8 ? 'lazy' : 'eager'"
      class="rounded-md size-full object-cover"
    >
  </UScrollArea>
</template>
```

### Items

Use the `items` prop as an array and render each item using the default slot:

```vue [ScrollAreaItemsExample.vue]
<script setup lang="ts">
const items = Array.from({ length: 30 }, (_, i) => ({
  id: i + 1,
  title: `Item ${i + 1}`,
  description: `Description for item ${i + 1}`
}))
</script>

<template>
  <UScrollArea
    v-slot="{ item, index }"
    :items="items"
    class="w-full h-96"
  >
    <UPageCard
      v-bind="item"
      :variant="index % 2 === 0 ? 'soft' : 'outline'"
      class="rounded-none"
    />
  </UScrollArea>
</template>
```

> [!TIP]
> See: #with-default-slot
> 
> You can also use the default slot without the `items` prop to render custom scrollable content directly.

### Orientation

Use the `orientation` prop to change the scroll direction. Defaults to `vertical`.

```vue [ScrollAreaOrientationExample.vue]
<script setup lang="ts">
defineProps<{
  orientation?: 'vertical' | 'horizontal'
}>()

const items = Array.from({ length: 30 }, (_, i) => ({
  id: i + 1,
  title: `Item ${i + 1}`,
  description: `Description for item ${i + 1}`
}))
</script>

<template>
  <UScrollArea
    v-slot="{ item, index }"
    :items="items"
    :orientation="orientation"
    class="w-full data-[orientation=vertical]:h-96"
  >
    <UPageCard
      v-bind="item"
      :variant="index % 2 === 0 ? 'soft' : 'outline'"
      class="rounded-none"
    />
  </UScrollArea>
</template>
```

### Virtualize

Use the `virtualize` prop to render only the items currently in view, significantly boosting performance when working with large datasets.

> [!NOTE]
> 
> When virtualization is **enabled**, customize spacing via the `virtualize` prop options like `gap`, `paddingStart`, and `paddingEnd`. Otherwise, use the `ui` prop to apply classes like `gap p-4` on the `viewport` slot.

> [!TIP]
> 
> If all your items have the **same height**, set `skipMeasurement` to `true` in the `virtualize` prop to skip per-item DOM measurement and rely on `estimateSize` instead. This significantly improves performance for large uniform lists.

```vue [ScrollAreaVirtualizeExample.vue]
<script setup lang="ts">
defineProps<{
  orientation?: 'vertical' | 'horizontal'
}>()

const items = computed(() => Array.from({ length: 1000 }, (_, i) => ({
  id: i + 1,
  title: `Item ${i + 1}`,
  description: `Description for item ${i + 1}`
})))
</script>

<template>
  <UScrollArea
    v-slot="{ item, index }"
    :items="items"
    :orientation="orientation"
    virtualize
    class="w-full data-[orientation=vertical]:h-96 data-[orientation=horizontal]:h-24.5"
  >
    <UPageCard
      v-bind="item"
      :variant="index % 2 === 0 ? 'soft' : 'outline'"
      class="rounded-none"
    />
  </UScrollArea>
</template>
```

### Shadow `4.9+`

Use the `shadow` prop to display fade shadows on the scrollable edges, indicating that more content is available in the scroll direction. The fade automatically follows the `orientation` and only appears when the content overflows.

```vue [ScrollAreaShadowExample.vue]
<template>
  <div class="max-w-sm bg-elevated/50 rounded-lg">
    <UScrollArea
      shadow
      class="p-4 h-72"
      :ui="{ viewport: 'gap-4' }"
    >
      <p v-for="i in 6" :key="i">
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam pulvinar risus non risus hendrerit venenatis. Pellentesque sit amet hendrerit risus, sed porttitor quam. Morbi accumsan cursus enim, sed ultricies sapien.
      </p>
    </UScrollArea>
  </div>
</template>
```

> [!TIP]
> 
> Pass an object to the `shadow` prop to configure the fade size, e.g. `:shadow="{ size: 48 }"`.

## Examples

### As masonry layout

Use the `virtualize` prop with `lanes`, `gap`, and `estimateSize` options to create Pinterest-style masonry layouts with variable height items.

```vue [ScrollAreaMasonryLayoutExample.vue]
<script setup lang="ts">
withDefaults(defineProps<{
  orientation?: 'vertical' | 'horizontal'
  lanes?: number
  gap?: number
}>(), {
  orientation: 'vertical',
  lanes: 3,
  gap: 16
})

const heights = [320, 480, 640, 800]

function getHeight(index: number) {
  const seed = (index * 11 + 7) % 17
  return heights[seed % heights.length]!
}

const items = Array.from({ length: 1000 }).map((_, index) => {
  const height = getHeight(index)

  return {
    id: index,
    title: `Item ${index + 1}`,
    src: `https://picsum.photos/640/${height}?v=${index}`,
    width: 640,
    height
  }
})
</script>

<template>
  <UScrollArea
    v-slot="{ item }"
    :items="items"
    :orientation="orientation"
    :virtualize="{
      gap,
      lanes,
      estimateSize: 480
    }"
    class="w-full h-128 p-4"
  >
    <img
      :src="item.src"
      :alt="item.title"
      :width="item.width"
      :height="item.height"
      loading="lazy"
      class="rounded-md size-full object-cover"
    >
  </UScrollArea>
</template>
```

> [!TIP]
> 
> For optimal performance, set `estimateSize` close to your average item height. Increasing `overscan` improves scrolling smoothness but renders more off-screen items.

### With responsive lanes

You can use the [`useWindowSize`](https://vueuse.org/core/useWindowSize/) (for viewport-based) or [`useElementSize`](https://vueuse.org/core/useElementSize/) (for container-based) composables to make the `lanes` reactive.

```vue [ScrollAreaResponsiveLanesExample.vue]
<script setup lang="ts">
const items = Array.from({ length: 1000 }).map((_, index) => ({
  id: index,
  title: `Item ${index + 1}`,
  src: `https://picsum.photos/640/480?v=${index}`,
  width: 640,
  height: 480
}))

const gap = 16
const scrollArea = useTemplateRef('scrollArea')
const { width } = useElementSize(() => scrollArea.value?.$el)

const lanes = computed(() => Math.max(1, Math.min(4, Math.floor(width.value / 200))))
const laneWidth = computed(() => (width.value - (lanes.value - 1) * gap) / lanes.value)
const estimateSize = computed(() => laneWidth.value * (480 / 640))
</script>

<template>
  <UScrollArea
    ref="scrollArea"
    v-slot="{ item }"
    :items="items"
    :virtualize="{
      gap,
      lanes,
      estimateSize,
      skipMeasurement: true
    }"
    class="w-full h-96 p-4"
  >
    <img
      :src="item.src"
      :alt="item.title"
      :width="item.width"
      :height="item.height"
      loading="lazy"
      class="rounded-md size-full object-cover"
    >
  </UScrollArea>
</template>
```

### With programmatic scroll

You can use the exposed `virtualizer` to programmatically control scroll position.

```vue [ScrollAreaScrollToExample.vue]
<script setup lang="ts">
const items = computed(() => Array.from({ length: 1000 }, (_, i) => ({
  id: i + 1,
  title: `Item ${i + 1}`
})))

const scrollArea = useTemplateRef('scrollArea')

const targetIndex = ref(500)

function scrollToTop() {
  scrollArea.value?.virtualizer?.scrollToIndex(0, { align: 'start', behavior: 'smooth' })
}

function scrollToBottom() {
  scrollArea.value?.virtualizer?.scrollToIndex(items.value.length - 1, { align: 'end', behavior: 'smooth' })
}

function scrollToItem(index: number) {
  scrollArea.value?.virtualizer?.scrollToIndex(index - 1, { align: 'center', behavior: 'smooth' })
}
</script>

<template>
  <div class="w-full">
    <UScrollArea
      v-slot="{ item, index }"
      ref="scrollArea"
      :items="items"
      :virtualize="{
        estimateSize: 72,
        skipMeasurement: true
      }"
      class="h-96 w-full"
    >
      <UPageCard
        v-bind="item"
        :variant="index % 2 === 0 ? 'soft' : 'outline'"
        class="rounded-none isolate"
        :class="[index === (targetIndex - 1) && 'bg-primary']"
      />
    </UScrollArea>

    <UFieldGroup size="sm" class="px-4 py-3 border-t border-muted w-full">
      <UButton icon="i-lucide-arrow-up-to-line" color="neutral" variant="outline" @click="scrollToTop">
        Top
      </UButton>
      <UButton icon="i-lucide-arrow-down-to-line" color="neutral" variant="outline" @click="scrollToBottom">
        Bottom
      </UButton>
      <UButton icon="i-lucide-navigation" color="neutral" variant="outline" @click="scrollToItem(targetIndex || 500)">
        Go to {{ targetIndex || 500 }}
      </UButton>
    </UFieldGroup>
  </div>
</template>
```

### With infinite scroll

You can use the [`useInfiniteScroll`](https://vueuse.org/core/useInfiniteScroll/) composable to load more data as the user scrolls.

```vue [ScrollAreaInfiniteScrollExample.vue]
<script setup lang="ts">
import { useInfiniteScroll } from '@vueuse/core'

type User = {
  id: number
  firstName: string
  lastName: string
  username: string
  email: string
  image: string
}

type UserResponse = {
  users: User[]
  total: number
  skip: number
  limit: number
}

const skip = ref(0)

const { data, status } = useLazyFetch('https://dummyjson.com/users?limit=10&select=firstName,lastName,username,email,image', {
  key: 'scroll-area-users-infinite-scroll',
  params: { skip },
  transform: (data?: UserResponse) => {
    return data?.users
  },
  server: false
})

const users = ref<User[]>([])

watch(data, () => {
  users.value = [
    ...users.value,
    ...(data.value || [])
  ]
})

const scrollArea = useTemplateRef('scrollArea')

onMounted(() => {
  useInfiniteScroll(scrollArea.value?.$el, () => {
    skip.value += 10
  }, {
    distance: 200,
    canLoadMore: () => {
      return status.value !== 'pending'
    }
  })
})
</script>

<template>
  <UScrollArea
    ref="scrollArea"
    v-slot="{ item }"
    :items="users"
    :virtualize="{
      estimateSize: 88,
      skipMeasurement: true
    }"
    class="h-96 w-full"
  >
    <UPageCard
      orientation="horizontal"
      class="rounded-none"
    >
      <UUser
        :name="`${item.firstName} ${item.lastName}`"
        :description="item.email"
        :avatar="{ src: item.image, alt: item.firstName, loading: 'lazy' as const }"
        size="lg"
      />
    </UPageCard>
  </UScrollArea>

  <UProgress
    v-if="status === 'pending' || status === 'idle'"
    indeterminate
    size="xs"
    class="absolute top-0 inset-x-0 z-1"
    :ui="{ base: 'bg-default' }"
  />
</template>
```

> [!NOTE]
> 
> This example uses `useLazyFetch` with `server: false` to fetch data on the client without blocking the initial render. The loading state checks for both `pending` and `idle` status to display a loading indicator before and during the fetch. Additional pages are loaded as the user scrolls.

### With default slot

You can use the default slot without the `items` prop to render custom scrollable content directly.

```vue [ScrollAreaDefaultSlotExample.vue]
<template>
  <UScrollArea class="h-96 w-full" :ui="{ viewport: 'gap-4 p-4' }">
    <UPageCard title="Section 1" description="Custom content without using the items prop." />
    <UPageCard title="Section 2" description="Custom content without using the items prop." />
    <UPageCard title="Section 3" description="Custom content without using the items prop." />
    <UPageCard title="Section 4" description="Custom content without using the items prop." />
    <UPageCard title="Section 5" description="Custom content without using the items prop." />
    <UPageCard title="Section 6" description="Custom content without using the items prop." />
  </UScrollArea>
</template>
```

## API

### Props

```ts
/**
 * Props for the ScrollArea component
 */
interface ScrollAreaProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  /**
   * The scroll direction.
   * @default "\"vertical\""
   */
  orientation?: "vertical" | "horizontal" | undefined;
  /**
   * Array of items to render.
   */
  items?: T[] | undefined;
  /**
   * Enable virtualization for large lists.
   * @default "false"
   */
  virtualize?: boolean | ScrollAreaVirtualizeOptions | undefined;
  /**
   * Display fade shadows on the scrollable edges to indicate more content.
   * Pass an object to configure the shadow size (in px).
   * @default "false"
   */
  shadow?: boolean | { size?: number | undefined; } | undefined;
  ui?: { root?: SlotClass; viewport?: SlotClass; item?: SlotClass; } | undefined;
}
```

### Slots

```ts
/**
 * Slots for the ScrollArea component
 */
interface ScrollAreaSlots {
  default(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the ScrollArea component
 */
interface ScrollAreaEmits {
  scroll: (payload: [isScrolling: boolean]) => void;
}
```

### Expose

You can access the typed component instance using [`useTemplateRef`](https://vuejs.org/api/composition-api-helpers.html#usetemplateref).

```vue
<script setup lang="ts">
const scrollArea = useTemplateRef('scrollArea')

// Scroll to a specific item
function scrollToItem(index: number) {
  scrollArea.value?.virtualizer?.scrollToIndex(index, { align: 'center' })
}
</script>

<template>
  <UScrollArea ref="scrollArea" :items="items" virtualize />
</template>
```

This will give you access to the following:

<table>
<thead>
  <tr>
    <th>
      Name
    </th>
    
    <th>
      Type
    </th>
    
    <th>
      Description
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          $el
        </span>
      </code>
    </td>
    
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          HTMLElement
        </span>
      </code>
    </td>
    
    <td>
      The root element of the component.
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          virtualizer
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
          Virtualizer
        </span>
        
        <span class="sMK4o">
          >
        </span>
        
        <span class="sMK4o">
          |
        </span>
        
        <span class="sBMFI">
          undefined
        </span>
      </code>
    </td>
    
    <td>
      The <a href="https://tanstack.com/virtual/latest/docs/api/virtualizer" rel="nofollow">
        TanStack Virtual
      </a>
      
       virtualizer instance (<code>
        undefined
      </code>
      
       if virtualization is disabled).
    </td>
  </tr>
</tbody>
</table>

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    scrollArea: {
      slots: {
        root: 'relative outline-primary/25 focus-visible:outline-3',
        viewport: 'relative flex',
        item: ''
      },
      variants: {
        orientation: {
          vertical: {
            root: 'overflow-y-auto overflow-x-hidden',
            viewport: 'flex-col',
            item: ''
          },
          horizontal: {
            root: 'overflow-x-auto overflow-y-hidden',
            viewport: 'flex-row',
            item: ''
          }
        }
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/ScrollArea.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/scroll-area.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

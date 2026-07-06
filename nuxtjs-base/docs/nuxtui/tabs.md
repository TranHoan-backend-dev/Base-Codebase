---
title: "Tabs"
description: "A set of tab panels that are displayed one at a time."
canonical_url: "https://ui.nuxt.com/docs/components/tabs"
---
# Tabs

> A set of tab panels that are displayed one at a time.

## Usage

Use the Tabs component to display a list of items in a tabs.

```vue [TabsExample.vue]
<script setup lang="ts">
const items = [
  {
    label: 'Account',
    icon: 'i-lucide-user',
    slot: 'account'
  },
  {
    label: 'Password',
    icon: 'i-lucide-lock',
    slot: 'password'
  }
]

const state = reactive({
  name: 'Benjamin Canac',
  username: 'benjamincanac',
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})
</script>

<template>
  <UTabs :items="items">
    <template #account>
      <UForm :state="state" class="flex flex-col gap-4">
        <UFormField label="Name" name="name">
          <UInput v-model="state.name" class="w-full" />
        </UFormField>
        <UFormField label="Username" name="username">
          <UInput v-model="state.username" class="w-full" />
        </UFormField>
      </UForm>
    </template>

    <template #password>
      <UForm :state="state" class="flex flex-col gap-4">
        <UFormField label="Current Password" name="current" required>
          <UInput v-model="state.currentPassword" type="password" required class="w-full" />
        </UFormField>
        <UFormField label="New Password" name="new" required>
          <UInput v-model="state.newPassword" type="password" required class="w-full" />
        </UFormField>
        <UFormField label="Confirm Password" name="confirm" required>
          <UInput v-model="state.confirmPassword" type="password" required class="w-full" />
        </UFormField>
      </UForm>
    </template>
  </UTabs>
</template>
```

### Items

Use the `items` prop as an array of objects with the following properties:

- `label?: string`
- `icon?: string`
- `avatar?: AvatarProps`
- `badge?: string | number | BadgeProps`
- `content?: string`
- `value?: string | number`
- `disabled?: boolean`
- [`slot?: string`](#with-custom-slot)
- `class?: any`
- `ui?: { trigger?: ClassNameValue, leadingIcon?: ClassNameValue, leadingAvatar?: ClassNameValue, leadingAvatarSize?: ClassNameValue, label?: ClassNameValue, trailingBadge?: ClassNameValue, trailingBadgeSize?: ClassNameValue, content?: ClassNameValue }`

```vue
<script setup lang="ts">
import type { TabsItem } from '@nuxt/ui'

const items = ref<TabsItem[]>([
  {
    label: "Account",
    icon: "i-lucide-user",
    content: "This is the account content."
  },
  {
    label: "Password",
    icon: "i-lucide-lock",
    content: "This is the password content."
  }
])
</script>

<template>
  <UTabs :items="items" class="w-full" />
</template>
```

### Content

Set the `content` prop to `false` to turn the Tabs into a toggle-only control without displaying any content. Defaults to `true`.

```vue
<script setup lang="ts">
import type { TabsItem } from '@nuxt/ui'

const items = ref<TabsItem[]>([
  {
    label: "Account",
    icon: "i-lucide-user",
    content: "This is the account content."
  },
  {
    label: "Password",
    icon: "i-lucide-lock",
    content: "This is the password content."
  }
])
</script>

<template>
  <UTabs :content="false" :items="items" class="w-full" />
</template>
```

### Unmount

Use the `unmount-on-hide` prop to prevent the content from being unmounted when the Tabs is collapsed. Defaults to `true`.

```vue
<script setup lang="ts">
import type { TabsItem } from '@nuxt/ui'

const items = ref<TabsItem[]>([
  {
    label: "Account",
    icon: "i-lucide-user",
    content: "This is the account content."
  },
  {
    label: "Password",
    icon: "i-lucide-lock",
    content: "This is the password content."
  }
])
</script>

<template>
  <UTabs :unmount-on-hide="false" :items="items" class="w-full" />
</template>
```

> [!NOTE]
> 
> You can inspect the DOM to see each item's content being rendered.

### Color

Use the `color` prop to change the color of the Tabs.

```vue
<script setup lang="ts">
import type { TabsItem } from '@nuxt/ui'

const items = ref<TabsItem[]>([
  {
    label: "Account"
  },
  {
    label: "Password"
  }
])
</script>

<template>
  <UTabs color="neutral" :content="false" :items="items" class="w-full" />
</template>
```

### Variant

Use the `variant` prop to change the variant of the Tabs.

```vue
<script setup lang="ts">
import type { TabsItem } from '@nuxt/ui'

const items = ref<TabsItem[]>([
  {
    label: "Account"
  },
  {
    label: "Password"
  }
])
</script>

<template>
  <UTabs color="neutral" variant="link" :content="false" :items="items" class="w-full" />
</template>
```

### Size

Use the `size` prop to change the size of the Tabs.

```vue
<script setup lang="ts">
import type { TabsItem } from '@nuxt/ui'

const items = ref<TabsItem[]>([
  {
    label: "Account"
  },
  {
    label: "Password"
  }
])
</script>

<template>
  <UTabs size="md" variant="pill" :content="false" :items="items" class="w-full" />
</template>
```

### Orientation

Use the `orientation` prop to change the orientation of the Tabs. Defaults to `horizontal`.

```vue
<script setup lang="ts">
import type { TabsItem } from '@nuxt/ui'

const items = ref<TabsItem[]>([
  {
    label: "Account"
  },
  {
    label: "Password"
  }
])
</script>

<template>
  <UTabs orientation="vertical" variant="pill" :content="false" :items="items" class="w-full" />
</template>
```

## Examples

### Control active item

You can control the active item by using the `default-value` prop or the `v-model` directive with the `value` of the item. If no `value` is provided, it defaults to the index **as a string**.

```vue [TabsModelValueExample.vue]
<script setup lang="ts">
import type { TabsItem } from '@nuxt/ui'

const items: TabsItem[] = [
  {
    label: 'Account',
    icon: 'i-lucide-user'
  },
  {
    label: 'Password',
    icon: 'i-lucide-lock'
  }
]

const active = ref('0')

// Note: This is for demonstration purposes only. Don't do this at home.
onMounted(() => {
  setInterval(() => {
    active.value = String((Number(active.value) + 1) % items.length)
  }, 2000)
})
</script>

<template>
  <UTabs v-model="active" :content="false" :items="items" class="w-full" />
</template>
```

> [!TIP]
> 
> Use the `value-key` prop to change the key used to match items when a `v-model` or `default-value` is provided.

### With route query

You can control the active item by a URL query parameter, using `route.query.tab` as the `value` of the item.

```vue [TabsRouteQueryExample.vue]
<script setup lang="ts">
import type { TabsItem } from '@nuxt/ui'

const route = useRoute()
const router = useRouter()

const items: TabsItem[] = [
  {
    label: 'Account',
    icon: 'i-lucide-user',
    value: 'account'
  },
  {
    label: 'Password',
    icon: 'i-lucide-lock',
    value: 'password'
  }
]

const active = computed({
  get() {
    return (route.query.tab as string) || 'account'
  },
  set(tab) {
    // Hash is specified here to prevent the page from scrolling to the top
    router.push({
      path: '/docs/components/tabs',
      query: { tab },
      hash: '#with-route-query'
    })
  }
})
</script>

<template>
  <UTabs v-model="active" :content="false" :items="items" class="w-full" />
</template>
```

### With content slot

Use the `#content` slot to customize the content of each item.

```vue [TabsContentSlotExample.vue]
<script setup lang="ts">
import type { TabsItem } from '@nuxt/ui'

const items: TabsItem[] = [
  {
    label: 'Account',
    icon: 'i-lucide-user'
  },
  {
    label: 'Password',
    icon: 'i-lucide-lock'
  }
]
</script>

<template>
  <UTabs :items="items" class="w-full">
    <template #content="{ item }">
      <p>This is the {{ item.label }} tab.</p>
    </template>
  </UTabs>
</template>
```

### With bottom tab bar

Use the `ui` prop to transform the Tabs into a mobile-style bottom tab bar with icons and small labels, similar to YouTube or Instagram.

```vue [TabsBottomTabBarExample.vue]
<script setup lang="ts">
import type { TabsItem } from '@nuxt/ui'

const items: TabsItem[] = [
  {
    label: 'Home',
    icon: 'i-lucide-house'
  },
  {
    label: 'Activity',
    icon: 'i-lucide-activity'
  },
  {
    label: 'Settings',
    icon: 'i-lucide-settings'
  },
  {
    label: 'Profile',
    icon: 'i-lucide-user'
  }
]
</script>

<template>
  <UTabs
    :items="items"
    :content="false"
    :ui="{
      list: 'justify-around w-full',
      trigger: 'grow flex-col gap-1 py-1',
      label: 'text-[10px]/3'
    }"
    class="w-full"
  />
</template>
```

### With custom slot

Use the `slot` property to customize a specific item.

You will have access to the following slots:

- `#{{ item.slot }}`

```vue [TabsCustomSlotExample.vue]
<script setup lang="ts">
import type { TabsItem } from '@nuxt/ui'

const items = [
  {
    label: 'Account',
    description: 'Make changes to your account here. Click save when you\'re done.',
    icon: 'i-lucide-user',
    slot: 'account' as const
  },
  {
    label: 'Password',
    description: 'Change your password here. After saving, you\'ll be logged out.',
    icon: 'i-lucide-lock',
    slot: 'password' as const
  }
] satisfies TabsItem[]

const state = reactive({
  name: 'Benjamin Canac',
  username: 'benjamincanac',
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})
</script>

<template>
  <UTabs :items="items" variant="link" :ui="{ trigger: 'grow' }" class="gap-4 w-full">
    <template #account="{ item }">
      <p class="text-muted mb-4">
        {{ item.description }}
      </p>

      <UForm :state="state" class="flex flex-col gap-4">
        <UFormField label="Name" name="name">
          <UInput v-model="state.name" class="w-full" />
        </UFormField>
        <UFormField label="Username" name="username">
          <UInput v-model="state.username" class="w-full" />
        </UFormField>

        <UButton label="Save changes" type="submit" variant="soft" class="self-end" />
      </UForm>
    </template>

    <template #password="{ item }">
      <p class="text-muted mb-4">
        {{ item.description }}
      </p>

      <UForm :state="state" class="flex flex-col gap-4">
        <UFormField label="Current Password" name="current" required>
          <UInput v-model="state.currentPassword" type="password" required class="w-full" />
        </UFormField>
        <UFormField label="New Password" name="new" required>
          <UInput v-model="state.newPassword" type="password" required class="w-full" />
        </UFormField>
        <UFormField label="Confirm Password" name="confirm" required>
          <UInput v-model="state.confirmPassword" type="password" required class="w-full" />
        </UFormField>

        <UButton label="Change password" type="submit" variant="soft" class="self-end" />
      </UForm>
    </template>
  </UTabs>
</template>
```

## API

### Props

```ts
/**
 * Props for the Tabs component
 */
interface TabsProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  items?: T[] | undefined;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  variant?: "pill" | "link" | undefined;
  size?: "sm" | "xs" | "md" | "lg" | "xl" | undefined;
  /**
   * The orientation of the tabs.
   * @default "\"horizontal\""
   */
  orientation?: "horizontal" | "vertical" | undefined;
  /**
   * The content of the tabs, can be disabled to prevent rendering the content.
   * @default "true"
   */
  content?: boolean | undefined;
  /**
   * The key used to get the value from the item.
   * @default "\"value\""
   */
  valueKey?: GetItemKeys<T> | undefined;
  /**
   * The key used to get the label from the item.
   * @default "\"label\""
   */
  labelKey?: GetItemKeys<T> | undefined;
  ui?: { root?: SlotClass; list?: SlotClass; indicator?: SlotClass; trigger?: SlotClass; leadingIcon?: SlotClass; leadingAvatar?: SlotClass; leadingAvatarSize?: SlotClass; label?: SlotClass; trailingBadge?: SlotClass; trailingBadgeSize?: SlotClass; content?: SlotClass; } | undefined;
  /**
   * The value of the tab that should be active when initially rendered. Use when you do not need to control the state of the tabs
   * @default "\"0\""
   */
  defaultValue?: string | number | undefined;
  /**
   * The controlled value of the tab to activate. Can be bind as `v-model`.
   */
  modelValue?: string | number | undefined;
  /**
   * Whether a tab is activated automatically (on focus) or manually (on click).
   */
  activationMode?: "automatic" | "manual" | undefined;
  /**
   * When `true`, the element will be unmounted on closed state.
   * @default "true"
   */
  unmountOnHide?: boolean | undefined;
}
```

### Slots

```ts
/**
 * Slots for the Tabs component
 */
interface TabsSlots {
  leading(): any;
  default(): any;
  trailing(): any;
  content(): any;
  list-leading(): any;
  list-trailing(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the Tabs component
 */
interface TabsEmits {
  update:modelValue: (payload: [payload: string | number]) => void;
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
          triggersRef
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
          ComponentPublicInstance
        </span>
        
        <span class="sTEyZ">
          []
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
    tabs: {
      slots: {
        root: 'flex items-center gap-2',
        list: 'relative flex p-1 group',
        indicator: 'absolute transition-[translate,width] duration-200',
        trigger: [
          'group relative inline-flex items-center min-w-0 data-[state=inactive]:text-muted hover:data-[state=inactive]:not-disabled:text-default font-medium rounded-md disabled:cursor-not-allowed disabled:opacity-75',
          'transition-colors'
        ],
        leadingIcon: 'shrink-0',
        leadingAvatar: 'shrink-0',
        leadingAvatarSize: '',
        label: 'truncate',
        trailingBadge: 'shrink-0',
        trailingBadgeSize: 'sm',
        content: 'w-full rounded-md focus-visible:outline-3'
      },
      variants: {
        color: {
          primary: {
            content: 'outline-primary/25'
          },
          secondary: {
            content: 'outline-secondary/25'
          },
          success: {
            content: 'outline-success/25'
          },
          info: {
            content: 'outline-info/25'
          },
          warning: {
            content: 'outline-warning/25'
          },
          error: {
            content: 'outline-error/25'
          },
          neutral: {
            content: 'outline-inverted/25'
          }
        },
        variant: {
          pill: {
            list: 'bg-elevated rounded-lg',
            trigger: [
              'grow',
              "in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:before:content-[''] in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:before:absolute in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:before:inset-0 in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:before:rounded-md in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:before:shadow-xs in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:before:-z-10 in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:isolate"
            ],
            indicator: 'rounded-md shadow-xs'
          },
          link: {
            list: 'border-default',
            indicator: 'rounded-full',
            trigger: "in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:content-[''] in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:absolute in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:rounded-full"
          }
        },
        orientation: {
          horizontal: {
            root: 'flex-col',
            list: 'w-full',
            indicator: 'left-0 w-(--reka-tabs-indicator-size) translate-x-(--reka-tabs-indicator-position)',
            trigger: 'justify-center'
          },
          vertical: {
            list: 'flex-col',
            indicator: 'top-0 h-(--reka-tabs-indicator-size) translate-y-(--reka-tabs-indicator-position)'
          }
        },
        size: {
          xs: {
            trigger: 'px-2 py-1 text-xs gap-1',
            leadingIcon: 'size-4',
            leadingAvatarSize: '3xs'
          },
          sm: {
            trigger: 'px-2.5 py-1.5 text-xs gap-1.5',
            leadingIcon: 'size-4',
            leadingAvatarSize: '3xs'
          },
          md: {
            trigger: 'px-3 py-1.5 text-sm gap-1.5',
            leadingIcon: 'size-5',
            leadingAvatarSize: '2xs'
          },
          lg: {
            trigger: 'px-3 py-2 text-sm gap-2',
            leadingIcon: 'size-5',
            leadingAvatarSize: '2xs'
          },
          xl: {
            trigger: 'px-3 py-2 text-base gap-2',
            leadingIcon: 'size-6',
            leadingAvatarSize: 'xs'
          }
        }
      },
      compoundVariants: [
        {
          orientation: 'horizontal',
          variant: 'pill',
          class: {
            indicator: 'inset-y-1'
          }
        },
        {
          orientation: 'horizontal',
          variant: 'link',
          class: {
            list: 'border-b -mb-px',
            indicator: '-bottom-px h-px',
            trigger: 'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:inset-x-0 in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:-bottom-[calc(var(--spacing)+1px)] in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:h-px'
          }
        },
        {
          orientation: 'vertical',
          variant: 'pill',
          class: {
            indicator: 'inset-x-1',
            list: 'items-center',
            trigger: 'w-full justify-center'
          }
        },
        {
          orientation: 'vertical',
          variant: 'link',
          class: {
            list: 'border-s -ms-px',
            indicator: '-start-px w-px',
            trigger: 'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:inset-y-0 in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:-start-[calc(var(--spacing)+1px)] in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:w-px'
          }
        },
        {
          color: 'primary',
          variant: 'pill',
          class: {
            indicator: 'bg-primary',
            trigger: [
              'data-[state=active]:text-inverted outline-primary/25 focus-visible:outline-3',
              'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:before:bg-primary'
            ]
          }
        },
        {
          color: 'secondary',
          variant: 'pill',
          class: {
            indicator: 'bg-secondary',
            trigger: [
              'data-[state=active]:text-inverted outline-secondary/25 focus-visible:outline-3',
              'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:before:bg-secondary'
            ]
          }
        },
        {
          color: 'success',
          variant: 'pill',
          class: {
            indicator: 'bg-success',
            trigger: [
              'data-[state=active]:text-inverted outline-success/25 focus-visible:outline-3',
              'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:before:bg-success'
            ]
          }
        },
        {
          color: 'info',
          variant: 'pill',
          class: {
            indicator: 'bg-info',
            trigger: [
              'data-[state=active]:text-inverted outline-info/25 focus-visible:outline-3',
              'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:before:bg-info'
            ]
          }
        },
        {
          color: 'warning',
          variant: 'pill',
          class: {
            indicator: 'bg-warning',
            trigger: [
              'data-[state=active]:text-inverted outline-warning/25 focus-visible:outline-3',
              'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:before:bg-warning'
            ]
          }
        },
        {
          color: 'error',
          variant: 'pill',
          class: {
            indicator: 'bg-error',
            trigger: [
              'data-[state=active]:text-inverted outline-error/25 focus-visible:outline-3',
              'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:before:bg-error'
            ]
          }
        },
        {
          color: 'neutral',
          variant: 'pill',
          class: {
            indicator: 'bg-inverted',
            trigger: [
              'data-[state=active]:text-inverted outline-inverted/25 focus-visible:outline-3',
              'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:before:bg-inverted'
            ]
          }
        },
        {
          color: 'primary',
          variant: 'link',
          class: {
            indicator: 'bg-primary',
            trigger: [
              'data-[state=active]:text-primary outline-primary/25 focus-visible:outline-3',
              'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:bg-primary'
            ]
          }
        },
        {
          color: 'secondary',
          variant: 'link',
          class: {
            indicator: 'bg-secondary',
            trigger: [
              'data-[state=active]:text-secondary outline-secondary/25 focus-visible:outline-3',
              'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:bg-secondary'
            ]
          }
        },
        {
          color: 'success',
          variant: 'link',
          class: {
            indicator: 'bg-success',
            trigger: [
              'data-[state=active]:text-success outline-success/25 focus-visible:outline-3',
              'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:bg-success'
            ]
          }
        },
        {
          color: 'info',
          variant: 'link',
          class: {
            indicator: 'bg-info',
            trigger: [
              'data-[state=active]:text-info outline-info/25 focus-visible:outline-3',
              'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:bg-info'
            ]
          }
        },
        {
          color: 'warning',
          variant: 'link',
          class: {
            indicator: 'bg-warning',
            trigger: [
              'data-[state=active]:text-warning outline-warning/25 focus-visible:outline-3',
              'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:bg-warning'
            ]
          }
        },
        {
          color: 'error',
          variant: 'link',
          class: {
            indicator: 'bg-error',
            trigger: [
              'data-[state=active]:text-error outline-error/25 focus-visible:outline-3',
              'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:bg-error'
            ]
          }
        },
        {
          color: 'neutral',
          variant: 'link',
          class: {
            indicator: 'bg-inverted',
            trigger: [
              'data-[state=active]:text-highlighted outline-inverted/25 focus-visible:outline-3',
              'in-[[data-slot=list]:not(:has([data-slot=indicator]))]:data-[state=active]:after:bg-inverted'
            ]
          }
        }
      ],
      defaultVariants: {
        color: 'primary',
        variant: 'pill',
        size: 'md'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Tabs.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/tabs.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

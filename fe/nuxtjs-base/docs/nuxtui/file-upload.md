---
title: "FileUpload"
description: "An input element to upload files."
canonical_url: "https://ui.nuxt.com/docs/components/file-upload"
---
# FileUpload

> An input element to upload files.

## Usage

Use the `v-model` directive to control the value of the FileUpload.

```vue
<script setup lang="ts">
const value = ref(null)
</script>

<template>
  <UFileUpload class="w-96 min-h-48" />
</template>
```

### Multiple

Use the `multiple` prop to allow multiple files to be selected.

```vue
<template>
  <UFileUpload multiple class="w-96 min-h-48" />
</template>
```

### Dropzone

Use the `dropzone` prop to enable/disable the droppable area. Defaults to `true`.

```vue
<template>
  <UFileUpload :dropzone="false" class="w-96 min-h-48" />
</template>
```

### Interactive

Use the `interactive` prop to enable/disable the clickable area. Defaults to `true`.

> [!TIP]
> See: #with-files-bottom-slot
> 
> This can be useful when adding a `Button` component in the `#actions` slot.

```vue
<template>
  <UFileUpload :interactive="false" class="w-96 min-h-48" />
</template>
```

### Accept

Use the `accept` prop to specify the allowed file types for the input. Provide a comma-separated list of [MIME types](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/MIME_types) or file extensions (e.g., `image/png,application/pdf,.jpg`). Defaults to `*` (all file types).

```vue
<template>
  <UFileUpload accept="image/*" class="w-96 min-h-48" />
</template>
```

### Label

Use the `label` prop to set the label of the FileUpload.

```vue
<template>
  <UFileUpload label="Drop your image here" class="w-96 min-h-48" />
</template>
```

### Description

Use the `description` prop to set the description of the FileUpload.

```vue
<template>
  <UFileUpload label="Drop your image here" description="SVG, PNG, JPG or GIF (max. 2MB)" class="w-96 min-h-48" />
</template>
```

### Icon

Use the `icon` prop to set the icon of the FileUpload. Defaults to `i-lucide-upload`.

```vue
<template>
  <UFileUpload icon="i-lucide-image" label="Drop your image here" description="SVG, PNG, JPG or GIF (max. 2MB)" class="w-96 min-h-48" />
</template>
```

**Nuxt:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/nuxt#theme
> 
> You can customize this icon globally in your `app.config.ts` under `ui.icons.upload` key.

**Vue:**

> [!TIP]
> See: /docs/getting-started/integrations/icons/vue#theme
> 
> You can customize this icon globally in your `vite.config.ts` under `ui.icons.upload` key.

### Color

Use the `color` prop to change the color of the FileUpload.

```vue
<template>
  <UFileUpload color="neutral" highlight label="Drop your image here" description="SVG, PNG, JPG or GIF (max. 2MB)" class="w-96 min-h-48" />
</template>
```

> [!NOTE]
> 
> The `highlight` prop is used here to show the focus state. It's used internally when a validation error occurs.

### Variant

Use the `variant` prop to change the variant of the FileUpload.

```vue
<template>
  <UFileUpload variant="button" />
</template>
```

### Size

Use the `size` prop to change the size of the FileUpload.

```vue
<template>
  <UFileUpload size="xl" variant="area" label="Drop your image here" description="SVG, PNG, JPG or GIF (max. 2MB)" />
</template>
```

### Layout

Use the `layout` prop to change how the files are displayed in the FileUpload. Defaults to `grid`.

> [!WARNING]
> 
> This prop only works when `variant` is `area`.

```vue
<template>
  <UFileUpload layout="list" multiple label="Drop your images here" description="SVG, PNG, JPG or GIF (max. 2MB)" class="w-96" :ui="{
  base: 'min-h-48'
}" />
</template>
```

### Position

Use the `position` prop to change the position of the files in the FileUpload. Defaults to `outside`.

> [!WARNING]
> 
> This prop only works when `variant` is `area` and when `layout` is `list`.

```vue
<template>
  <UFileUpload position="inside" layout="list" multiple label="Drop your images here" description="SVG, PNG, JPG or GIF (max. 2MB)" class="w-96" :ui="{
  base: 'min-h-48'
}" />
</template>
```

## Examples

### With Form validation

You can use the FileUpload within a [Form](/docs/components/form) and [FormField](/docs/components/form-field) components to handle validation and error handling.

```vue [FileUploadFormValidationExample.vue]
<script setup lang="ts">
import * as z from 'zod'
import type { FormSubmitEvent } from '@nuxt/ui'

const MAX_FILE_SIZE = 2 * 1024 * 1024 // 2MB
const MIN_DIMENSIONS = { width: 200, height: 200 }
const MAX_DIMENSIONS = { width: 4096, height: 4096 }
const ACCEPTED_IMAGE_TYPES = ['image/jpeg', 'image/jpg', 'image/png', 'image/webp']

const formatBytes = (bytes: number, decimals = 2) => {
  if (bytes === 0) return '0 Bytes'
  const k = 1024
  const dm = decimals < 0 ? 0 : decimals
  const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return Number.parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i]
}

const schema = z.object({
  image: z
    .instanceof(File, {
      message: 'Please select an image file.'
    })
    .refine(file => file.size <= MAX_FILE_SIZE, {
      message: `The image is too large. Please choose an image smaller than ${formatBytes(MAX_FILE_SIZE)}.`
    })
    .refine(file => ACCEPTED_IMAGE_TYPES.includes(file.type), {
      message: 'Please upload a valid image file (JPEG, PNG, or WebP).'
    })
    .refine(
      file =>
        new Promise((resolve) => {
          const reader = new FileReader()
          reader.onload = (e) => {
            const img = new Image()
            img.onload = () => {
              const meetsDimensions
                = img.width >= MIN_DIMENSIONS.width
                  && img.height >= MIN_DIMENSIONS.height
                  && img.width <= MAX_DIMENSIONS.width
                  && img.height <= MAX_DIMENSIONS.height
              resolve(meetsDimensions)
            }
            img.src = e.target?.result as string
          }
          reader.readAsDataURL(file)
        }),
      {
        message: `The image dimensions are invalid. Please upload an image between ${MIN_DIMENSIONS.width}x${MIN_DIMENSIONS.height} and ${MAX_DIMENSIONS.width}x${MAX_DIMENSIONS.height} pixels.`
      }
    )
})

type Schema = z.output<typeof schema>

const state = reactive<Partial<Schema>>({
  image: undefined
})

async function onSubmit(event: FormSubmitEvent<Schema>) {
  console.log(event.data)
}
</script>

<template>
  <UForm :schema="schema" :state="state" class="space-y-4 w-96" @submit="onSubmit">
    <UFormField name="image" label="Image" description="JPG, GIF or PNG. 2MB Max.">
      <UFileUpload v-model="state.image" accept="image/*" class="min-h-48" />
    </UFormField>

    <UButton type="submit" label="Submit" color="neutral" />
  </UForm>
</template>
```

### With default slot

You can use the default slot to make your own FileUpload component.

```vue [FileUploadDefaultSlotExample.vue]
<script setup lang="ts">
import * as z from 'zod'
import type { FormSubmitEvent } from '@nuxt/ui'

const MAX_FILE_SIZE = 2 * 1024 * 1024 // 2MB
const MIN_DIMENSIONS = { width: 200, height: 200 }
const MAX_DIMENSIONS = { width: 4096, height: 4096 }
const ACCEPTED_IMAGE_TYPES = ['image/jpeg', 'image/jpg', 'image/png', 'image/webp']

const formatBytes = (bytes: number, decimals = 2) => {
  if (bytes === 0) return '0 Bytes'
  const k = 1024
  const dm = decimals < 0 ? 0 : decimals
  const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return Number.parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i]
}

const schema = z.object({
  avatar: z
    .instanceof(File, {
      message: 'Please select an image file.'
    })
    .refine(file => file.size <= MAX_FILE_SIZE, {
      message: `The image is too large. Please choose an image smaller than ${formatBytes(MAX_FILE_SIZE)}.`
    })
    .refine(file => ACCEPTED_IMAGE_TYPES.includes(file.type), {
      message: 'Please upload a valid image file (JPEG, PNG, or WebP).'
    })
    .refine(
      file =>
        new Promise((resolve) => {
          const reader = new FileReader()
          reader.onload = (e) => {
            const img = new Image()
            img.onload = () => {
              const meetsDimensions
                = img.width >= MIN_DIMENSIONS.width
                  && img.height >= MIN_DIMENSIONS.height
                  && img.width <= MAX_DIMENSIONS.width
                  && img.height <= MAX_DIMENSIONS.height
              resolve(meetsDimensions)
            }
            img.src = e.target?.result as string
          }
          reader.readAsDataURL(file)
        }),
      {
        message: `The image dimensions are invalid. Please upload an image between ${MIN_DIMENSIONS.width}x${MIN_DIMENSIONS.height} and ${MAX_DIMENSIONS.width}x${MAX_DIMENSIONS.height} pixels.`
      }
    )
})

type Schema = z.output<typeof schema>

const state = reactive<Partial<Schema>>({
  avatar: undefined
})

function createObjectUrl(file: File): string {
  return URL.createObjectURL(file)
}

async function onSubmit(event: FormSubmitEvent<Schema>) {
  console.log(event.data)
}
</script>

<template>
  <UForm :schema="schema" :state="state" class="space-y-4 w-64" @submit="onSubmit">
    <UFormField name="avatar" label="Avatar" description="JPG, GIF or PNG. 1MB Max.">
      <UFileUpload v-slot="{ open, removeFile }" v-model="state.avatar" accept="image/*">
        <div class="flex flex-wrap items-center gap-3">
          <UAvatar size="lg" :src="state.avatar ? createObjectUrl(state.avatar) : undefined" icon="i-lucide-image" />

          <UButton :label="state.avatar ? 'Change image' : 'Upload image'" color="neutral" variant="outline" @click="open()" />
        </div>

        <p v-if="state.avatar" class="text-xs text-muted mt-1.5">
          {{ state.avatar.name }}

          <UButton
            label="Remove"
            color="error"
            variant="link"
            size="xs"
            class="p-0"
            @click="removeFile()"
          />
        </p>
      </UFileUpload>
    </UFormField>

    <UButton type="submit" label="Submit" color="neutral" />
  </UForm>
</template>
```

### With files-bottom slot

You can use the `files-bottom` slot to add a [Button](/docs/components/button) under the files list to remove all files for example.

```vue [FileUploadFilesBottomSlotExample.vue]
<script setup lang="ts">
const value = ref<File[]>([])
</script>

<template>
  <UFileUpload
    v-model="value"
    icon="i-lucide-image"
    label="Drop your images here"
    description="SVG, PNG, JPG or GIF (max. 2MB)"
    layout="list"
    multiple
    :interactive="false"
    class="w-96 min-h-48"
  >
    <template #actions="{ open }">
      <UButton
        label="Select images"
        icon="i-lucide-upload"
        color="neutral"
        variant="outline"
        @click="open()"
      />
    </template>

    <template #files-bottom="{ removeFile, files }">
      <UButton
        v-if="files?.length"
        label="Remove all files"
        color="neutral"
        @click="removeFile()"
      />
    </template>
  </UFileUpload>
</template>
```

> [!NOTE]
> See: #interactive
> 
> The `interactive` prop is set to `false` in this example to prevent the default clickable area.

### With files-top slot

You can use the `files-top` slot to add a [Button](/docs/components/button) above the files list to add new files for example.

```vue [FileUploadFilesTopSlotExample.vue]
<script setup lang="ts">
const value = ref<File[]>([])
</script>

<template>
  <UFileUpload
    v-model="value"
    icon="i-lucide-image"
    label="Drop your images here"
    description="SVG, PNG, JPG or GIF (max. 2MB)"
    layout="grid"
    multiple
    :interactive="false"
    class="w-96 min-h-48"
  >
    <template #actions="{ open }">
      <UButton
        label="Select images"
        icon="i-lucide-upload"
        color="neutral"
        variant="outline"
        @click="open()"
      />
    </template>

    <template #files-top="{ open, files }">
      <div v-if="files?.length" class="mb-2 flex items-center justify-between">
        <p class="font-bold">
          Files ({{ files?.length }})
        </p>

        <UButton
          icon="i-lucide-plus"
          label="Add more"
          color="neutral"
          variant="outline"
          class="-my-2"
          @click="open()"
        />
      </div>
    </template>
  </UFileUpload>
</template>
```

## API

### Props

```ts
/**
 * Props for the FileUpload component
 */
interface FileUploadProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  id?: string | undefined;
  name?: string | undefined;
  /**
   * The icon to display. Set to `false` to hide the icon.
   */
  icon?: any;
  label?: string | undefined;
  description?: string | undefined;
  color?: "primary" | "secondary" | "success" | "info" | "warning" | "error" | "neutral" | undefined;
  /**
   * The `button` variant is only available when `multiple` is `false`.
   */
  variant?: "button" | "area" | undefined;
  size?: "xs" | "sm" | "md" | "lg" | "xl" | undefined;
  /**
   * The layout of how files are displayed.
   * Only works when `variant` is `area`.
   * @default "\"grid\""
   */
  layout?: "list" | "grid" | undefined;
  /**
   * The position of the files.
   * Only works when `variant` is `area` and when `layout` is `list`.
   * @default "\"outside\""
   */
  position?: "inside" | "outside" | undefined;
  /**
   * Highlight the ring color like a focus state.
   */
  highlight?: boolean | undefined;
  /**
   * Specifies the allowed file types for the input. Provide a comma-separated list of MIME types or file extensions (e.g., "image/png,application/pdf,.jpg").
   * @default "\"*\""
   */
  accept?: string | undefined;
  /**
   * @default "false as never"
   */
  multiple?: M | undefined;
  /**
   * Reset the file input when the dialog is opened.
   * @default "false"
   */
  reset?: boolean | undefined;
  /**
   * Create a zone that allows the user to drop files onto it.
   * @default "true"
   */
  dropzone?: boolean | undefined;
  /**
   * Make the dropzone interactive when the user is clicking on it.
   * @default "true"
   */
  interactive?: boolean | undefined;
  required?: boolean | undefined;
  disabled?: boolean | undefined;
  /**
   * The icon to display for the file.
   */
  fileIcon?: any;
  /**
   * Preview the file (currently only `<img>` is rendered)
   * When set false, only `fileIcon` is displayed
   * @default "true"
   */
  fileImage?: boolean | undefined;
  /**
   * Configure the delete button for the file.
   * When `layout` is `grid`, the default is `{ color: 'neutral', variant: 'solid', size: 'xs' }`{lang="ts-type"}
   * When `layout` is `list`, the default is `{ color: 'neutral', variant: 'link' }`{lang="ts-type"}
   * @default "true"
   */
  fileDelete?: boolean | Omit<ButtonProps, LinkPropsKeys> | undefined;
  /**
   * The icon displayed to delete a file.
   */
  fileDeleteIcon?: any;
  /**
   * Show the file preview/list after upload.
   * @default "true"
   */
  preview?: boolean | undefined;
  ui?: { root?: SlotClass; base?: SlotClass; wrapper?: SlotClass; icon?: SlotClass; avatar?: SlotClass; label?: SlotClass; description?: SlotClass; actions?: SlotClass; files?: SlotClass; file?: SlotClass; fileLeadingAvatar?: SlotClass; fileWrapper?: SlotClass; fileName?: SlotClass; fileSize?: SlotClass; fileTrailingButton?: SlotClass; } | undefined;
  form?: string | undefined;
  formaction?: string | undefined;
  formenctype?: string | undefined;
  formmethod?: string | undefined;
  formnovalidate?: Booleanish | undefined;
  formtarget?: string | undefined;
  modelValue?: (M extends true ? File[] : File) | null | undefined;
}
```

> [!NOTE]
> See: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#attributes
> 
> This component also supports all native `<input>` HTML attributes.

### Slots

```ts
/**
 * Slots for the FileUpload component
 */
interface FileUploadSlots {
  default(): any;
  leading(): any;
  label(): any;
  description(): any;
  actions(): any;
  files(): any;
  files-top(): any;
  files-bottom(): any;
  file(): any;
  file-leading(): any;
  file-name(): any;
  file-size(): any;
  file-trailing(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the FileUpload component
 */
interface FileUploadEmits {
  change: (payload: [event: Event]) => void;
  update:modelValue: (payload: [value: (M extends true ? File[] : File) | null | undefined]) => void;
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
          inputRef
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
          HTMLInputElement
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
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          dropzoneRef
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
          HTMLDivElement
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
    fileUpload: {
      slots: {
        root: 'relative flex flex-col',
        base: [
          'w-full flex-1 bg-default border border-default flex flex-col gap-2 items-stretch justify-center rounded-lg focus-visible:outline-3',
          'transition-[background]'
        ],
        wrapper: 'flex flex-col items-center justify-center text-center',
        icon: 'shrink-0',
        avatar: 'shrink-0',
        label: 'font-medium text-default mt-2',
        description: 'text-muted mt-1',
        actions: 'flex flex-wrap gap-1.5 shrink-0 mt-4',
        files: '',
        file: 'relative',
        fileLeadingAvatar: 'shrink-0',
        fileWrapper: 'flex flex-col min-w-0',
        fileName: 'text-default truncate',
        fileSize: 'text-muted truncate',
        fileTrailingButton: ''
      },
      variants: {
        color: {
          primary: '',
          secondary: '',
          success: '',
          info: '',
          warning: '',
          error: '',
          neutral: ''
        },
        variant: {
          area: {
            wrapper: 'px-4 py-3',
            base: 'p-4'
          },
          button: {}
        },
        size: {
          xs: {
            base: 'text-xs',
            icon: 'size-4',
            file: 'text-xs px-2 py-1 gap-1',
            fileWrapper: 'flex-row gap-1'
          },
          sm: {
            base: 'text-xs',
            icon: 'size-4',
            file: 'text-xs px-2.5 py-1.5 gap-1.5',
            fileWrapper: 'flex-row gap-1'
          },
          md: {
            base: 'text-sm',
            icon: 'size-5',
            file: 'text-xs px-2.5 py-1.5 gap-1.5'
          },
          lg: {
            base: 'text-sm',
            icon: 'size-5',
            file: 'text-sm px-3 py-2 gap-2',
            fileSize: 'text-xs'
          },
          xl: {
            base: 'text-base',
            icon: 'size-6',
            file: 'text-sm px-3 py-2 gap-2'
          }
        },
        layout: {
          list: {
            root: 'gap-2 items-start',
            files: 'flex flex-col w-full gap-2',
            file: 'min-w-0 flex items-center border border-default rounded-md w-full',
            fileTrailingButton: 'ms-auto'
          },
          grid: {
            fileWrapper: 'hidden',
            fileLeadingAvatar: 'size-full rounded-lg',
            fileTrailingButton: 'absolute -top-1.5 -end-1.5 p-0 rounded-full border-2 border-bg'
          }
        },
        position: {
          inside: '',
          outside: ''
        },
        dropzone: {
          true: 'border-dashed data-[dragging=true]:bg-elevated/25'
        },
        interactive: {
          true: ''
        },
        highlight: {
          true: ''
        },
        multiple: {
          true: ''
        },
        disabled: {
          true: 'cursor-not-allowed opacity-75'
        }
      },
      compoundVariants: [
        {
          color: 'primary',
          class: 'outline-primary/25 focus-visible:outline-3 focus-visible:border-primary'
        },
        {
          color: 'secondary',
          class: 'outline-secondary/25 focus-visible:outline-3 focus-visible:border-secondary'
        },
        {
          color: 'success',
          class: 'outline-success/25 focus-visible:outline-3 focus-visible:border-success'
        },
        {
          color: 'info',
          class: 'outline-info/25 focus-visible:outline-3 focus-visible:border-info'
        },
        {
          color: 'warning',
          class: 'outline-warning/25 focus-visible:outline-3 focus-visible:border-warning'
        },
        {
          color: 'error',
          class: 'outline-error/25 focus-visible:outline-3 focus-visible:border-error'
        },
        {
          color: 'primary',
          highlight: true,
          class: 'border-primary'
        },
        {
          color: 'secondary',
          highlight: true,
          class: 'border-secondary'
        },
        {
          color: 'success',
          highlight: true,
          class: 'border-success'
        },
        {
          color: 'info',
          highlight: true,
          class: 'border-info'
        },
        {
          color: 'warning',
          highlight: true,
          class: 'border-warning'
        },
        {
          color: 'error',
          highlight: true,
          class: 'border-error'
        },
        {
          color: 'neutral',
          class: 'outline-inverted/25 focus-visible:outline-3 focus-visible:border-inverted'
        },
        {
          color: 'neutral',
          highlight: true,
          class: 'border-inverted'
        },
        {
          size: 'xs',
          layout: 'list',
          class: {
            fileTrailingButton: '-me-1'
          }
        },
        {
          size: 'sm',
          layout: 'list',
          class: {
            fileTrailingButton: '-me-1.5'
          }
        },
        {
          size: 'md',
          layout: 'list',
          class: {
            fileTrailingButton: '-me-1.5'
          }
        },
        {
          size: 'lg',
          layout: 'list',
          class: {
            fileTrailingButton: '-me-2'
          }
        },
        {
          size: 'xl',
          layout: 'list',
          class: {
            fileTrailingButton: '-me-2'
          }
        },
        {
          variant: 'button',
          size: 'xs',
          class: {
            base: 'p-1'
          }
        },
        {
          variant: 'button',
          size: 'sm',
          class: {
            base: 'p-1.5'
          }
        },
        {
          variant: 'button',
          size: 'md',
          class: {
            base: 'p-1.5'
          }
        },
        {
          variant: 'button',
          size: 'lg',
          class: {
            base: 'p-2'
          }
        },
        {
          variant: 'button',
          size: 'xl',
          class: {
            base: 'p-2'
          }
        },
        {
          layout: 'grid',
          multiple: true,
          class: {
            files: 'grid grid-cols-2 md:grid-cols-3 gap-4 w-full',
            file: 'p-0 aspect-square'
          }
        },
        {
          layout: 'grid',
          multiple: false,
          class: {
            file: 'absolute inset-0 p-0'
          }
        },
        {
          interactive: true,
          disabled: false,
          class: 'hover:bg-elevated/25'
        }
      ],
      defaultVariants: {
        color: 'primary',
        variant: 'area',
        size: 'md'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/FileUpload.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/file-upload.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

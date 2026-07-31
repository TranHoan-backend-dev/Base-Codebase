---
title: "Form"
description: "A form component with built-in validation and submission handling."
canonical_url: "https://ui.nuxt.com/docs/components/form"
---
# Form

> A form component with built-in validation and submission handling.

## Usage

Use the Form component to validate form data using any validation library supporting [Standard Schema](https://github.com/standard-schema/standard-schema) such as [Valibot](https://github.com/fabian-hiller/valibot), [Zod](https://github.com/colinhacks/zod), [Regle](https://github.com/victorgarciaesgi/regle), [Yup](https://github.com/jquense/yup), [Joi](https://github.com/hapijs/joi) or [Superstruct](https://github.com/ianstormtaylor/superstruct) or your own validation logic.

It works with the [FormField](/docs/components/form-field) component to display error messages around form elements automatically.

### Schema validation

It requires two props:

- `state` - a reactive object holding the form's state.
- `schema` - any [Standard Schema](https://github.com/standard-schema/standard-schema) or [Superstruct](https://github.com/ianstormtaylor/superstruct).

> [!WARNING]
> 
> **No validation library is included** by default, ensure you **install the one you need**.

```vue [FormExampleValibot.vue]
<script setup lang="ts">
import * as v from 'valibot'
import type { FormSubmitEvent } from '@nuxt/ui'

const schema = v.object({
  email: v.pipe(v.string(), v.email('Invalid email')),
  password: v.pipe(v.string(), v.minLength(8, 'Must be at least 8 characters'))
})

type Schema = v.InferOutput<typeof schema>

const state = reactive({
  email: '',
  password: ''
})

const toast = useToast()
async function onSubmit(event: FormSubmitEvent<Schema>) {
  toast.add({ title: 'Success', description: 'The form has been submitted.', color: 'success' })
  console.log(event.data)
}
</script>

<template>
  <UForm :schema="schema" :state="state" class="space-y-4" @submit="onSubmit">
    <UFormField label="Email" name="email">
      <UInput v-model="state.email" />
    </UFormField>

    <UFormField label="Password" name="password">
      <UInput v-model="state.password" type="password" />
    </UFormField>

    <UButton type="submit">
      Submit
    </UButton>
  </UForm>
</template>
```

```vue [FormExampleZod.vue]
<script setup lang="ts">
import * as z from 'zod'
import type { FormSubmitEvent } from '@nuxt/ui'

const schema = z.object({
  email: z.email('Invalid email'),
  password: z.string('Password is required').min(8, 'Must be at least 8 characters')
})

type Schema = z.output<typeof schema>

const state = reactive<Partial<Schema>>({
  email: undefined,
  password: undefined
})

const toast = useToast()
async function onSubmit(event: FormSubmitEvent<Schema>) {
  toast.add({ title: 'Success', description: 'The form has been submitted.', color: 'success' })
  console.log(event.data)
}
</script>

<template>
  <UForm :schema="schema" :state="state" class="space-y-4" @submit="onSubmit">
    <UFormField label="Email" name="email">
      <UInput v-model="state.email" />
    </UFormField>

    <UFormField label="Password" name="password">
      <UInput v-model="state.password" type="password" />
    </UFormField>

    <UButton type="submit">
      Submit
    </UButton>
  </UForm>
</template>
```

```vue [FormExampleRegle.vue]
<script setup lang="ts">
import { useRegle, type InferInput } from '@regle/core'
import { required, email, minLength, withMessage } from '@regle/rules'
import type { FormSubmitEvent } from '@nuxt/ui'

const { r$ } = useRegle({ email: '', password: '' }, {
  email: { required, email: withMessage(email, 'Invalid email') },
  password: { required, minLength: withMessage(minLength(8), 'Must be at least 8 characters') }
})

type Schema = InferInput<typeof r$>

const toast = useToast()
async function onSubmit(event: FormSubmitEvent<Schema>) {
  toast.add({ title: 'Success', description: 'The form has been submitted.', color: 'success' })
  console.log(event.data)
}
</script>

<template>
  <UForm :schema="r$" :state="r$.$value" class="space-y-4" @submit="onSubmit">
    <UFormField label="Email" name="email">
      <UInput v-model="r$.$value.email" />
    </UFormField>

    <UFormField label="Password" name="password">
      <UInput v-model="r$.$value.password" type="password" />
    </UFormField>

    <UButton type="submit">
      Submit
    </UButton>
  </UForm>
</template>
```

```vue [FormExampleYup.vue]
<script setup lang="ts">
import { object, string } from 'yup'
import type { InferType } from 'yup'
import type { FormSubmitEvent } from '@nuxt/ui'

const schema = object({
  email: string().email('Invalid email').required('Required'),
  password: string()
    .min(8, 'Must be at least 8 characters')
    .required('Required')
})

type Schema = InferType<typeof schema>

const state = reactive({
  email: undefined,
  password: undefined
})

const toast = useToast()
async function onSubmit(event: FormSubmitEvent<Schema>) {
  toast.add({ title: 'Success', description: 'The form has been submitted.', color: 'success' })
  console.log(event.data)
}
</script>

<template>
  <UForm :schema="schema" :state="state" class="space-y-4" @submit="onSubmit">
    <UFormField label="Email" name="email">
      <UInput v-model="state.email" />
    </UFormField>

    <UFormField label="Password" name="password">
      <UInput v-model="state.password" type="password" />
    </UFormField>

    <UButton type="submit">
      Submit
    </UButton>
  </UForm>
</template>
```

```vue [FormExampleJoi.vue]
<script setup lang="ts">
import Joi from 'joi'
import type { FormSubmitEvent } from '@nuxt/ui'

const schema = Joi.object({
  email: Joi.string().required(),
  password: Joi.string()
    .min(8)
    .required()
})

const state = reactive({
  email: undefined,
  password: undefined
})

const toast = useToast()
async function onSubmit(event: FormSubmitEvent<typeof state>) {
  toast.add({ title: 'Success', description: 'The form has been submitted.', color: 'success' })
  console.log(event.data)
}
</script>

<template>
  <UForm :schema="schema" :state="state" class="space-y-4" @submit="onSubmit">
    <UFormField label="Email" name="email">
      <UInput v-model="state.email" />
    </UFormField>

    <UFormField label="Password" name="password">
      <UInput v-model="state.password" type="password" />
    </UFormField>

    <UButton type="submit">
      Submit
    </UButton>
  </UForm>
</template>
```

```vue [FormExampleSuperstruct.vue]
<script setup lang="ts">
import { object, string, nonempty, refine } from 'superstruct'
import type { Infer } from 'superstruct'
import type { FormSubmitEvent } from '@nuxt/ui'

const schema = object({
  email: nonempty(string()),
  password: refine(string(), 'Password', (value) => {
    if (value.length >= 8) return true
    return 'Must be at least 8 characters'
  })
})

const state = reactive({
  email: '',
  password: ''
})

type Schema = Infer<typeof schema>

async function onSubmit(event: FormSubmitEvent<Schema>) {
  console.log(event.data)
}
</script>

<template>
  <UForm :schema="schema" :state="state" class="space-y-4" @submit="onSubmit">
    <UFormField label="Email" name="email">
      <UInput v-model="state.email" />
    </UFormField>

    <UFormField label="Password" name="password">
      <UInput v-model="state.password" type="password" />
    </UFormField>

    <UButton type="submit">
      Submit
    </UButton>
  </UForm>
</template>
```

### Custom validation

Use the `validate` prop to apply your own validation logic.

The validation function must return a list of errors with the following attributes:

- `message` - the error message to display.
- `name` - the `name` of the `FormField` to send the error to.

> [!TIP]
> 
> It can be used alongside the `schema` prop to handle complex use cases.

```vue [FormExampleBasic.vue]
<script setup lang="ts">
import type { FormError, FormSubmitEvent } from '@nuxt/ui'

const state = reactive({
  email: undefined,
  password: undefined
})

type Schema = typeof state

function validate(state: Partial<Schema>): FormError[] {
  const errors = []
  if (!state.email) errors.push({ name: 'email', message: 'Required' })
  if (!state.password) errors.push({ name: 'password', message: 'Required' })
  return errors
}

const toast = useToast()
async function onSubmit(event: FormSubmitEvent<Schema>) {
  toast.add({ title: 'Success', description: 'The form has been submitted.', color: 'success' })
  console.log(event.data)
}
</script>

<template>
  <UForm :validate="validate" :state="state" class="space-y-4" @submit="onSubmit">
    <UFormField label="Email" name="email">
      <UInput v-model="state.email" />
    </UFormField>

    <UFormField label="Password" name="password">
      <UInput v-model="state.password" type="password" />
    </UFormField>

    <UButton type="submit">
      Submit
    </UButton>
  </UForm>
</template>
```

### Error reporting

Errors are matched to the corresponding [FormField](/docs/components/form-field) using its `name` prop. An error on the `email` field is shown by `<FormField name="email">`.

Nested fields are matched using dot notation. A schema like `{ user: z.object({ email: z.string() }) }` will be applied to `<FormField name="user.email">`.

> [!WARNING]
> 
> Errors on array items include the index in their name (e.g. `tags.0`, `tags.1`) and won't match `<FormField name="tags">` by `name` alone. Use the `error-pattern` prop with a regular expression like `/^tags\..+/` to capture them. This is especially useful for components like [InputTags](/docs/components/input-tags).

```vue [FormExampleErrorPattern.vue]
<script setup lang="ts">
import * as z from 'zod'
import type { FormSubmitEvent } from '@nuxt/ui'

const schema = z.object({
  email: z.email('Invalid email'),
  tags: z.array(z.string().regex(/^[a-z-]+$/, 'Lowercase letters and dashes only')).min(1, 'Please add at least one tag')
})

type Schema = z.output<typeof schema>

const state = reactive<Partial<Schema>>({
  email: undefined,
  tags: []
})

const toast = useToast()
async function onSubmit(event: FormSubmitEvent<Schema>) {
  toast.add({ title: 'Success', description: 'The form has been submitted.', color: 'success' })
  console.log(event.data)
}
</script>

<template>
  <UForm :schema="schema" :state="state" class="space-y-4" @submit="onSubmit">
    <UFormField label="Email" name="email">
      <UInput v-model="state.email" />
    </UFormField>

    <UFormField label="Tags" name="tags" :error-pattern="/^tags\..+/">
      <UInputTags v-model="state.tags" />
    </UFormField>

    <UButton type="submit">
      Submit
    </UButton>
  </UForm>
</template>
```

### Input events

The Form component automatically triggers validation when an input emits an `input`, `change`, or `blur` event.

- Validation on `input` occurs **as you type**.
- Validation on `change` occurs when you **commit to a value**.
- Validation on `blur` happens when an input **loses focus**.

You can control when validation happens this using the `validate-on` prop.

> [!TIP]
> 
> The form always validates on submit.

```vue [FormExampleElements.vue]
<script setup lang="ts">
import * as z from 'zod'
import type { FormSubmitEvent } from '@nuxt/ui'

const schema = z.object({
  input: z.string({ message: 'Please enter your email' }).min(10, 'Must be at least 10 characters'),
  inputNumber: z.number({ message: 'Please enter a number' }).min(10, 'Must be at least 10'),
  inputMenu: z.any().refine(option => option?.value === 'option-2', {
    message: 'Please select Option 2'
  }),
  inputMenuMultiple: z.any().refine(values => !!values?.find((option: any) => option.value === 'option-2'), {
    message: 'Option 2 must be included'
  }),
  textarea: z.string({ message: 'Please enter a message' }).min(10, 'Must be at least 10 characters'),
  select: z.string({ message: 'Please select an option' }).refine(value => value === 'option-2', {
    message: 'Please select Option 2'
  }),
  selectMultiple: z.array(z.string(), { message: 'Please select at least one option' }).refine(values => values.includes('option-2'), {
    message: 'Option 2 must be included'
  }),
  selectMenu: z.any().refine(option => option?.value === 'option-2', {
    message: 'Please select Option 2'
  }),
  selectMenuMultiple: z.any().refine(values => !!values?.find((option: any) => option.value === 'option-2'), {
    message: 'Option 2 must be included'
  }),
  switch: z.boolean().refine(value => value === true, {
    message: 'Must be enabled'
  }),
  checkbox: z.boolean().refine(value => value === true, {
    message: 'Must be checked'
  }),
  radioGroup: z.string({ message: 'Please select an option' }).refine(value => value === 'option-2', {
    message: 'Please select Option 2'
  }),
  checkboxGroup: z.any().refine(values => !!values?.find((option: any) => option === 'option-2'), {
    message: 'Option 2 must be included'
  }),
  listbox: z.any().refine(option => option?.value === 'option-2', {
    message: 'Please select Option 2'
  }),
  listboxMultiple: z.any().refine(values => !!values?.find((option: any) => option.value === 'option-2'), {
    message: 'Option 2 must be included'
  }),
  inputTags: z.array(z.string(), { message: 'Please add at least one tag' }).min(1, 'Please add at least one tag'),
  inputDate: z.any().refine(value => !!value, {
    message: 'Please select a date'
  }),
  inputTime: z.any().refine(value => !!value, {
    message: 'Please select a time'
  }),
  slider: z.number().min(1, 'Must be greater than 0').max(20, 'Must be less than 20'),
  pin: z.string().regex(/^\d$/, 'Must be a digit').array().length(5, 'All 5 digits are required'),
  file: z.file({ message: 'Please upload a file' }).min(1, 'File is required').max(1024 * 1024, 'File must be less than 1MB').mime('image/png', 'Only PNG images are allowed')
})

type Schema = z.input<typeof schema>

const state = reactive<Partial<Schema>>({
  switch: false,
  checkbox: false,
  slider: 0,
  pin: []
})

const form = useTemplateRef('form')

const items = [
  { label: 'Option 1', value: 'option-1' },
  { label: 'Option 2', value: 'option-2' },
  { label: 'Option 3', value: 'option-3' }
]

const toast = useToast()
async function onSubmit(event: FormSubmitEvent<Schema>) {
  toast.add({ title: 'Success', description: 'The form has been submitted.', color: 'success' })
  console.log(event.data)
}
</script>

<template>
  <UForm ref="form" :state="state" :schema="schema" class="w-full" @submit="onSubmit">
    <div class="grid sm:grid-cols-2 md:grid-cols-3 gap-4">
      <UFormField label="Input" name="input">
        <UInput v-model="state.input" placeholder="you@example.com" class="w-full" />
      </UFormField>

      <UFormField name="inputNumber" label="InputNumber">
        <UInputNumber v-model="state.inputNumber" placeholder="Enter a number" class="w-full" />
      </UFormField>

      <UFormField name="pin" label="PinInput" :error-pattern="/(pin)\..*/">
        <UPinInput v-model="state.pin" placeholder="○" />
      </UFormField>

      <UFormField name="inputDate" label="InputDate">
        <UInputDate v-model="state.inputDate" class="w-full" />
      </UFormField>

      <UFormField name="inputTime" label="InputTime">
        <UInputTime v-model="state.inputTime" class="w-full" />
      </UFormField>

      <UFormField name="inputTags" label="InputTags">
        <UInputTags v-model="state.inputTags" placeholder="Add a tag..." class="w-full" />
      </UFormField>

      <UFormField name="inputMenu" label="InputMenu">
        <UInputMenu v-model="state.inputMenu" :items="items" placeholder="Search an option..." class="w-full" />
      </UFormField>

      <UFormField name="inputMenuMultiple" label="InputMenu (Multiple)">
        <UInputMenu v-model="state.inputMenuMultiple" multiple :items="items" placeholder="Search options..." class="w-full" />
      </UFormField>

      <UFormField label="Textarea" name="textarea">
        <UTextarea v-model="state.textarea" placeholder="Write your message..." class="w-full" :rows="1" />
      </UFormField>

      <UFormField name="select" label="Select">
        <USelect v-model="state.select" :items="items" placeholder="Choose an option" class="w-full" />
      </UFormField>

      <UFormField name="selectMultiple" label="Select (Multiple)">
        <USelect v-model="state.selectMultiple" multiple :items="items" placeholder="Choose options" class="w-full" />
      </UFormField>

      <div class="hidden md:block" />

      <UFormField name="selectMenu" label="SelectMenu">
        <USelectMenu v-model="state.selectMenu" :items="items" placeholder="Search an option..." class="w-full" />
      </UFormField>

      <UFormField name="selectMenuMultiple" label="SelectMenu (Multiple)">
        <USelectMenu v-model="state.selectMenuMultiple" multiple :items="items" placeholder="Search options..." class="w-full" />
      </UFormField>

      <div class="hidden md:block" />

      <UFormField name="listbox" label="Listbox">
        <UListbox v-model="state.listbox" :items="items" class="w-full" />
      </UFormField>

      <UFormField name="listboxMultiple" label="Listbox (Multiple)">
        <UListbox v-model="state.listboxMultiple" :items="items" multiple class="w-full" />
      </UFormField>

      <UFormField name="file" label="FileUpload">
        <UFileUpload
          v-model="state.file"
          label="Drop your image here"
          description="PNG (max. 1MB)"
          class="w-full"
        />
      </UFormField>

      <UFormField name="checkbox" label="Checkbox">
        <UCheckbox v-model="state.checkbox" label="Check me" />
      </UFormField>

      <UFormField name="switch" label="Switch">
        <USwitch v-model="state.switch" label="Switch me" />
      </UFormField>

      <UFormField name="slider" label="Slider">
        <USlider v-model="state.slider" class="mt-2.5" />
      </UFormField>

      <UFormField name="checkboxGroup">
        <UCheckboxGroup v-model="state.checkboxGroup" legend="CheckboxGroup" :items="items" />
      </UFormField>

      <UFormField name="radioGroup">
        <URadioGroup v-model="state.radioGroup" legend="RadioGroup" :items="items" />
      </UFormField>
    </div>

    <div class="flex gap-2 mt-8">
      <UButton type="submit">
        Submit
      </UButton>

      <UButton variant="outline" @click="form?.clear()">
        Clear
      </UButton>
    </div>
  </UForm>
</template>
```

> [!TIP]
> 
> You can use the `useFormField` composable to implement this inside your own components.

### Error event

You can listen to the `@error` event to handle errors. This event is triggered when the form is submitted and contains an array of `FormError` objects with the following fields:

- `id` - the input's `id`.
- `name` - the `name` of the `FormField`
- `message` - the error message to display.

Here's an example that focuses the first input element with an error after the form is submitted:

```vue [FormExampleOnError.vue]
<script setup lang="ts">
import type { FormError, FormErrorEvent, FormSubmitEvent } from '@nuxt/ui'

const state = reactive({
  email: undefined,
  password: undefined
})

type Schema = typeof state

function validate(state: Partial<Schema>): FormError[] {
  const errors = []
  if (!state.email) errors.push({ name: 'email', message: 'Required' })
  if (!state.password) errors.push({ name: 'password', message: 'Required' })
  return errors
}

const toast = useToast()
async function onSubmit(event: FormSubmitEvent<Schema>) {
  toast.add({ title: 'Success', description: 'The form has been submitted.', color: 'success' })
  console.log(event.data)
}

async function onError(event: FormErrorEvent) {
  if (event?.errors?.[0]?.id) {
    const element = document.getElementById(event.errors[0].id)
    element?.focus()
    element?.scrollIntoView({ behavior: 'smooth', block: 'center' })
  }
}
</script>

<template>
  <UForm :validate="validate" :state="state" class="space-y-4" @submit="onSubmit" @error="onError">
    <UFormField label="Email" name="email">
      <UInput v-model="state.email" />
    </UFormField>

    <UFormField label="Password" name="password">
      <UInput v-model="state.password" type="password" />
    </UFormField>

    <UButton type="submit">
      Submit
    </UButton>
  </UForm>
</template>
```

### HTML5 validation `4.5+`

When calling `form.submit()` programmatically, the Form component automatically triggers native HTML5 validation before submission.

> [!NOTE]
> 
> This is particularly useful when the submit button is outside the form element, such as in a modal footer.

```vue [FormExampleHtml5Validation.vue]
<script setup lang="ts">
import type { FormSubmitEvent } from '@nuxt/ui'

const state = reactive({
  email: undefined,
  age: undefined
})

type Schema = typeof state

const form = useTemplateRef('form')

const toast = useToast()
async function onSubmit(event: FormSubmitEvent<Schema>) {
  toast.add({ title: 'Success', description: 'The form has been submitted.', color: 'success' })
  console.log(event.data)
}
</script>

<template>
  <div class="space-y-4">
    <UForm ref="form" :state="state" class="space-y-4" @submit="onSubmit">
      <UFormField label="Email" name="email">
        <UInput v-model="state.email" type="email" required />
      </UFormField>

      <UFormField label="Age" name="age">
        <UInput v-model="state.age" type="number" min="18" max="100" required />
      </UFormField>
    </UForm>

    <UButton @click="form?.submit()">
      Submit
    </UButton>
  </div>
</template>
```

### Nesting forms

Use the `nested` prop to nest multiple Form components and link their validation functions. In this case, validating the parent form will automatically validate all the other forms inside it.

Nested forms directly inherit their parent's state, so you don't need to define a separate state for them. You can use the `name` prop to target a nested attribute within the parent's state.

It can be used to dynamically add fields based on user's input:

```vue [FormExampleNested.vue]
<script setup lang="ts">
import * as z from 'zod'
import type { FormSubmitEvent } from '@nuxt/ui'

const schema = z.object({
  name: z.string().min(2),
  news: z.boolean().default(false)
})

type Schema = z.output<typeof schema>

const nestedSchema = z.object({
  email: z.email()
})

type NestedSchema = z.output<typeof nestedSchema>

const state = reactive<Partial<Schema & NestedSchema>>({ })

const toast = useToast()
async function onSubmit(event: FormSubmitEvent<Schema>) {
  toast.add({ title: 'Success', description: 'The form has been submitted.', color: 'success' })
  console.log(event.data)
}
</script>

<template>
  <UForm
    ref="form"
    :state="state"
    :schema="schema"
    class="gap-4 flex flex-col w-60"
    @submit="onSubmit"
  >
    <UFormField label="Name" name="name">
      <UInput v-model="state.name" placeholder="John Lennon" />
    </UFormField>

    <div>
      <UCheckbox v-model="state.news" name="news" label="Register to our newsletter" @update:model-value="state.email = undefined" />
    </div>

    <UForm v-if="state.news" :schema="nestedSchema" nested>
      <UFormField label="Email" name="email">
        <UInput v-model="state.email" placeholder="john@lennon.com" />
      </UFormField>
    </UForm>

    <div>
      <UButton type="submit">
        Submit
      </UButton>
    </div>
  </UForm>
</template>
```

Or to validate list inputs:

```vue [FormExampleNestedList.vue]
<script setup lang="ts">
import * as z from 'zod'
import type { FormSubmitEvent } from '@nuxt/ui'

const schema = z.object({
  customer: z.string().min(2)
})

type Schema = z.output<typeof schema>

const itemSchema = z.object({
  description: z.string().min(1),
  price: z.number().min(0)
})

type ItemSchema = z.output<typeof itemSchema>

const state = reactive<Partial<Schema & { items: Partial<ItemSchema>[] }>>({ })

function addItem() {
  if (!state.items) {
    state.items = []
  }
  state.items.push({})
}

function removeItem() {
  if (state.items) {
    state.items.pop()
  }
}

const toast = useToast()

async function onSubmit(event: FormSubmitEvent<Schema>) {
  toast.add({ title: 'Success', description: 'The form has been submitted.', color: 'success' })
  console.log(event.data)
}
</script>

<template>
  <UForm
    :state="state"
    :schema="schema"
    class="gap-4 flex flex-col w-60"
    @submit="onSubmit"
  >
    <UFormField label="Customer" name="customer">
      <UInput v-model="state.customer" placeholder="Wonka Industries" />
    </UFormField>

    <UForm
      v-for="item, count in state.items"
      :key="count"
      :name="`items.${count}`"
      :schema="itemSchema"
      class="flex gap-2"
      nested
    >
      <UFormField :label="!count ? 'Description' : undefined" name="description">
        <UInput v-model="item.description" />
      </UFormField>
      <UFormField :label="!count ? 'Price' : undefined" name="price" class="w-20">
        <UInput v-model="item.price" type="number" />
      </UFormField>
    </UForm>

    <div class="flex gap-2">
      <UButton color="neutral" variant="subtle" size="sm" @click="addItem()">
        Add Item
      </UButton>

      <UButton color="neutral" variant="ghost" size="sm" @click="removeItem()">
        Remove Item
      </UButton>
    </div>
    <div>
      <UButton type="submit">
        Submit
      </UButton>
    </div>
  </UForm>
</template>
```

## API

### Props

```ts
/**
 * Props for the Form component
 */
interface FormProps {
  id?: string | number | undefined;
  /**
   * Schema to validate the form state. Supports Standard Schema objects, Yup, Joi, and Superstructs.
   */
  schema?: S | undefined;
  /**
   * An object representing the current state of the form.
   */
  state?: (N extends false ? Partial<InferInput<S>> : never) | undefined;
  /**
   * Custom validation function to validate the form state.
   */
  validate?: ((state: Partial<InferInput<S>>) => FormError<string>[] | Promise<FormError<string>[]>) | undefined;
  /**
   * The list of input events that trigger the form validation.
   */
  validateOn?: FormInputEvents[] | undefined;
  /**
   * Disable all inputs inside the form.
   */
  disabled?: boolean | undefined;
  /**
   * The `name` attribute of the form element.
   * For nested forms (`nested` is true), this is also used as the path of the form's state within its parent form.
   */
  name?: string | undefined;
  /**
   * Delay in milliseconds before validating the form on input events.
   * @default "300"
   */
  validateOnInputDelay?: number | undefined;
  /**
   * If true, applies schema transformations on submit.
   * @default "true as T"
   */
  transform?: T | undefined;
  /**
   * If true, this form will attach to its parent Form and validate at the same time.
   */
  nested?: N | undefined;
  /**
   * When `true`, all form elements will be disabled on `@submit` event.
   * This will cause any focused input elements to lose their focus state.
   * @default "true"
   */
  loadingAuto?: boolean | undefined;
  ui?: { base?: any; } | undefined;
  acceptcharset?: string | undefined;
  action?: string | undefined;
  autocomplete?: string | undefined;
  enctype?: string | undefined;
  method?: string | undefined;
  novalidate?: Booleanish | undefined;
  target?: string | undefined;
}
```

> [!NOTE]
> See: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/form#attributes
> 
> This component also supports all native `<form>` HTML attributes.

### Slots

```ts
/**
 * Slots for the Form component
 */
interface FormSlots {
  default(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the Form component
 */
interface FormEmits {
  submit: (payload: [event: FormSubmitEvent<FormData<S, T>>]) => void;
  error: (payload: [event: FormErrorEvent]) => void;
}
```

### Expose

You can access the typed component instance using [`useTemplateRef`](https://vuejs.org/api/composition-api-helpers.html#usetemplateref).

```vue
<script setup lang="ts">
const form = useTemplateRef('form')
</script>

<template>
  <UForm ref="form" />
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
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          submit
        </span>
        
        <span class="sMK4o">
          ()
        </span>
      </code>
    </td>
    
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          Promise
        </span>
        
        <span class="sMK4o">
          <
        </span>
        
        <span class="sBMFI">
          void
        </span>
        
        <span class="sMK4o">
          >
        </span>
      </code>
      
       <br />
      
       <p>
        Triggers form submission with HTML5 validation.
      </p>
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          validate
        </span>
        
        <span class="sMK4o">
          (
        </span>
        
        <span class="sHdIc">
          opts
        </span>
        
        <span class="sMK4o">
          :
        </span>
        
        <span class="sMK4o">
          {
        </span>
        
        <span class="swJcz">
          name
        </span>
        
        <span class="sMK4o">
          ?:
        </span>
        
        <span class="sMK4o">
          keyof
        </span>
        
        <span class="sBMFI">
          T
        </span>
        
        <span class="sMK4o">
          |
        </span>
        
        <span class="sTEyZ">
          (
        </span>
        
        <span class="sMK4o">
          keyof
        </span>
        
        <span class="sBMFI">
          T
        </span>
        
        <span class="sTEyZ">
          )[]
        </span>
        
        <span class="sMK4o">
          ,
        </span>
        
        <span class="swJcz">
          silent
        </span>
        
        <span class="sMK4o">
          ?:
        </span>
        
        <span class="sBMFI">
          boolean
        </span>
        
        <span class="sMK4o">
          ,
        </span>
        
        <span class="swJcz">
          nested
        </span>
        
        <span class="sMK4o">
          ?:
        </span>
        
        <span class="sBMFI">
          boolean
        </span>
        
        <span class="sMK4o">
          ,
        </span>
        
        <span class="swJcz">
          transform
        </span>
        
        <span class="sMK4o">
          ?:
        </span>
        
        <span class="sBMFI">
          boolean
        </span>
        
        <span class="sMK4o">
          })
        </span>
      </code>
    </td>
    
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          Promise
        </span>
        
        <span class="sMK4o">
          <
        </span>
        
        <span class="sBMFI">
          T
        </span>
        
        <span class="sMK4o">
          >
        </span>
      </code>
      
       <br />
      
       <p>
        Triggers form validation. Will raise any errors unless <code>opts.silent</code> is set to true.
      </p>
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          clear
        </span>
        
        <span class="sMK4o">
          (
        </span>
        
        <span class="sHdIc">
          path
        </span>
        
        <span class="sMK4o">
          ?:
        </span>
        
        <span class="sMK4o">
          keyof
        </span>
        
        <span class="sBMFI">
          T
        </span>
        
        <span class="sMK4o">
          |
        </span>
        
        <span class="sBMFI">
          RegExp
        </span>
        
        <span class="sMK4o">
          )
        </span>
      </code>
    </td>
    
    <td>
      <code>
        void
      </code>
      
       <br />
      
       <p>
        Clears form errors associated with a specific path. If no path is provided, clears all form errors.
      </p>
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          getErrors
        </span>
        
        <span class="sMK4o">
          (
        </span>
        
        <span class="sHdIc">
          path
        </span>
        
        <span class="sMK4o">
          ?:
        </span>
        
        <span class="sMK4o">
          keyof
        </span>
        
        <span class="sBMFI">
          T
        </span>
        
        <span class="sMK4o">
          |
        </span>
        
        <span class="sBMFI">
          RegExp
        </span>
        
        <span class="sMK4o">
          )
        </span>
      </code>
    </td>
    
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          FormErrorWithId
        </span>
        
        <span class="sTEyZ">
          []
        </span>
      </code>
      
       <br />
      
       <p>
        Retrieves form errors associated with a specific path. If no path is provided, returns all form errors.
      </p>
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          setErrors
        </span>
        
        <span class="sMK4o">
          (
        </span>
        
        <span class="sHdIc">
          errors
        </span>
        
        <span class="sMK4o">
          :
        </span>
        
        <span class="sBMFI">
          FormError
        </span>
        
        <span class="sTEyZ">
          []
        </span>
        
        <span class="sMK4o">
          ,
        </span>
        
        <span class="sHdIc">
          name
        </span>
        
        <span class="sMK4o">
          ?:
        </span>
        
        <span class="sMK4o">
          keyof
        </span>
        
        <span class="sBMFI">
          T
        </span>
        
        <span class="sMK4o">
          |
        </span>
        
        <span class="sBMFI">
          RegExp
        </span>
        
        <span class="sMK4o">
          )
        </span>
      </code>
    </td>
    
    <td>
      <code>
        void
      </code>
      
       <br />
      
       <p>
        Sets form errors for a given path. If no path is provided, overrides all errors.
      </p>
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          errors
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
          FormErrorWithId
        </span>
        
        <span class="sTEyZ">
          []
        </span>
        
        <span class="sMK4o">
          >
        </span>
      </code>
      
       <br />
      
       <p>
        A reference to the array containing validation errors. Use this to access or manipulate the error information.
      </p>
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          disabled
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
          boolean
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
          dirty
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
          boolean
        </span>
        
        <span class="sMK4o">
          >
        </span>
      </code>
      
       <code>
        true
      </code>
      
       if at least one form field has been updated by the user.
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          dirtyFields
        </span>
      </code>
    </td>
    
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          ReadonlySet
        </span>
        
        <span class="sMK4o">
          <
        </span>
        
        <span class="sBMFI">
          DeepReadonly
        </span>
        
        <span class="sMK4o">
          <keyof
        </span>
        
        <span class="sBMFI">
          T
        </span>
        
        <span class="sMK4o">
          >>
        </span>
      </code>
      
       Tracks fields that have been modified by the user.
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          touchedFields
        </span>
      </code>
    </td>
    
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          ReadonlySet
        </span>
        
        <span class="sMK4o">
          <
        </span>
        
        <span class="sBMFI">
          DeepReadonly
        </span>
        
        <span class="sMK4o">
          <keyof
        </span>
        
        <span class="sBMFI">
          T
        </span>
        
        <span class="sMK4o">
          >>
        </span>
      </code>
      
       Tracks fields that the user interacted with.
    </td>
  </tr>
  
  <tr>
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          blurredFields
        </span>
      </code>
    </td>
    
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          ReadonlySet
        </span>
        
        <span class="sMK4o">
          <
        </span>
        
        <span class="sBMFI">
          DeepReadonly
        </span>
        
        <span class="sMK4o">
          <keyof
        </span>
        
        <span class="sBMFI">
          T
        </span>
        
        <span class="sMK4o">
          >>
        </span>
      </code>
      
       Tracks fields blurred by the user.
    </td>
  </tr>
</tbody>
</table>

## Theme

```ts [app.config.ts]
export default defineAppConfig({
  ui: {
    form: {
      base: ''
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/Form.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/form.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

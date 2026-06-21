---
title: "AuthForm"
description: "A customizable Form to create login, register or password reset forms."
canonical_url: "https://ui.nuxt.com/docs/components/auth-form"
---
# AuthForm

> A customizable Form to create login, register or password reset forms.

## Usage

Built on top of the [Form](/docs/components/form) component, the `AuthForm` component can be used in your pages or wrapped in a [PageCard](/docs/components/page-card).

```vue [AuthFormExample.vue]
<script setup lang="ts">
import * as z from 'zod'
import type { FormSubmitEvent, AuthFormField } from '@nuxt/ui'

const toast = useToast()

const fields: AuthFormField[] = [{
  name: 'email',
  type: 'email',
  label: 'Email',
  placeholder: 'Enter your email',
  required: true
}, {
  name: 'password',
  label: 'Password',
  type: 'password',
  placeholder: 'Enter your password',
  required: true
}, {
  name: 'remember',
  label: 'Remember me',
  type: 'checkbox'
}]

const providers = [{
  label: 'Google',
  icon: 'i-simple-icons-google',
  onClick: () => {
    toast.add({ title: 'Google', description: 'Login with Google' })
  }
}, {
  label: 'GitHub',
  icon: 'i-simple-icons-github',
  onClick: () => {
    toast.add({ title: 'GitHub', description: 'Login with GitHub' })
  }
}]

const schema = z.object({
  email: z.email('Invalid email'),
  password: z.string('Password is required').min(8, 'Must be at least 8 characters')
})

type Schema = z.output<typeof schema>

function onSubmit(payload: FormSubmitEvent<Schema>) {
  console.log('Submitted', payload)
}
</script>

<template>
  <div class="flex flex-col items-center justify-center gap-4 p-4">
    <UPageCard class="w-full max-w-md">
      <UAuthForm
        :schema="schema"
        title="Login"
        description="Enter your credentials to access your account."
        icon="i-lucide-user"
        :fields="fields"
        :providers="providers"
        @submit="onSubmit"
      />
    </UPageCard>
  </div>
</template>
```

### Fields

The Form will construct itself based on the `fields` prop and the state will be handled internally.

Use the `fields` prop as an array of objects with the following properties:

- `name: string`
- `type: 'checkbox' | 'select' | 'otp' | 'InputHTMLAttributes['type']'`

Each field must include a `type` property, which determines the input component and any additional props applied: `checkbox` fields use [Checkbox](/docs/components/checkbox#props) props, `select` fields use [SelectMenu](/docs/components/select-menu#props) props, `otp` fields use [PinInput](/docs/components/pin-input#props) props, and all other types use [Input](/docs/components/input#props) props.

You can also pass any property from the [FormField](/docs/components/form-field#props) component to each field.

```vue
<script setup lang="ts">
import type { AuthFormField } from '@nuxt/ui'

const fields = ref<AuthFormField[]>([
  {
    name: "email",
    type: "email",
    label: "Email",
    placeholder: "Enter your email",
    required: true
  },
  {
    name: "password",
    type: "password",
    label: "Password",
    placeholder: "Enter your password",
    required: true
  },
  {
    name: "country",
    type: "select",
    label: "Country",
    placeholder: "Select country",
    items: [
      {
        label: "United States",
        value: "us"
      },
      {
        label: "France",
        value: "fr"
      },
      {
        label: "United Kingdom",
        value: "uk"
      },
      {
        label: "Australia",
        value: "au"
      }
    ]
  },
  {
    name: "otp",
    type: "otp",
    label: "OTP",
    length: 6,
    placeholder: "○"
  },
  {
    name: "remember",
    type: "checkbox",
    label: "Remember me",
    description: "You will be logged in for 30 days."
  }
])
</script>

<template>
  <UAuthForm :fields="fields" class="max-w-sm" />
</template>
```

### Title

Use the `title` prop to set the title of the Form.

```vue
<script setup lang="ts">
import type { AuthFormField } from '@nuxt/ui'

const fields = ref<AuthFormField[]>([
  {
    name: "email",
    type: "text",
    label: "Email"
  },
  {
    name: "password",
    type: "password",
    label: "Password"
  }
])
</script>

<template>
  <UAuthForm title="Login" :fields="fields" class="max-w-md" />
</template>
```

### Description

Use the `description` prop to set the description of the Form.

```vue
<script setup lang="ts">
import type { AuthFormField } from '@nuxt/ui'

const fields = ref<AuthFormField[]>([
  {
    name: "email",
    type: "text",
    label: "Email"
  },
  {
    name: "password",
    type: "password",
    label: "Password"
  }
])
</script>

<template>
  <UAuthForm title="Login" description="Enter your credentials to access your account." :fields="fields" class="max-w-md" />
</template>
```

### Icon

Use the `icon` prop to set the icon of the Form.

```vue
<script setup lang="ts">
import type { AuthFormField } from '@nuxt/ui'

const fields = ref<AuthFormField[]>([
  {
    name: "email",
    type: "text",
    label: "Email"
  },
  {
    name: "password",
    type: "password",
    label: "Password"
  }
])
</script>

<template>
  <UAuthForm title="Login" description="Enter your credentials to access your account." icon="i-lucide-user" :fields="fields" class="max-w-md" />
</template>
```

### Providers

Use the `providers` prop to add providers to the form.

You can pass any property from the [Button](/docs/components/button) component such as `variant`, `color`, `to`, etc.

```vue
<script setup lang="ts">
import type { ButtonProps, AuthFormField } from '@nuxt/ui'

const providers = ref<ButtonProps[]>([
  {
    label: "Google",
    icon: "i-simple-icons-google",
    color: "neutral",
    variant: "subtle"
  },
  {
    label: "GitHub",
    icon: "i-simple-icons-github",
    color: "neutral",
    variant: "subtle"
  }
])
const fields = ref<AuthFormField[]>([
  {
    name: "email",
    type: "text",
    label: "Email"
  },
  {
    name: "password",
    type: "password",
    label: "Password"
  }
])
</script>

<template>
  <UAuthForm title="Login" description="Enter your credentials to access your account." icon="i-lucide-user" :providers="providers" :fields="fields" class="max-w-md" />
</template>
```

### Separator

Use the `separator` prop to customize the [Separator](/docs/components/separator) between the providers and the fields. Defaults to `or`.

```vue
<script setup lang="ts">
import type { ButtonProps, AuthFormField } from '@nuxt/ui'

const providers = ref<ButtonProps[]>([
  {
    label: "Google",
    icon: "i-simple-icons-google",
    color: "neutral",
    variant: "subtle"
  },
  {
    label: "GitHub",
    icon: "i-simple-icons-github",
    color: "neutral",
    variant: "subtle"
  }
])
const fields = ref<AuthFormField[]>([
  {
    name: "email",
    type: "text",
    label: "Email"
  },
  {
    name: "password",
    type: "password",
    label: "Password"
  }
])
</script>

<template>
  <UAuthForm title="Login" description="Enter your credentials to access your account." icon="i-lucide-user" :providers="providers" :fields="fields" separator="Providers" class="max-w-md" />
</template>
```

You can pass any property from the [Separator](/docs/components/separator#props) component to customize it.

```vue
<script setup lang="ts">
import type { ButtonProps, AuthFormField } from '@nuxt/ui'

const providers = ref<ButtonProps[]>([
  {
    label: "Google",
    icon: "i-simple-icons-google",
    color: "neutral",
    variant: "subtle"
  },
  {
    label: "GitHub",
    icon: "i-simple-icons-github",
    color: "neutral",
    variant: "subtle"
  }
])
const fields = ref<AuthFormField[]>([
  {
    name: "email",
    type: "text",
    label: "Email"
  },
  {
    name: "password",
    type: "password",
    label: "Password"
  }
])
</script>

<template>
  <UAuthForm title="Login" description="Enter your credentials to access your account." icon="i-lucide-user" :providers="providers" :fields="fields" :separator="{
  icon: 'i-lucide-user'
}" class="max-w-md" />
</template>
```

### Submit

Use the `submit` prop to change the submit button of the Form.

You can pass any property from the [Button](/docs/components/button) component such as `variant`, `color`, `to`, etc.

```vue
<script setup lang="ts">
import type { AuthFormField } from '@nuxt/ui'

const fields = ref<AuthFormField[]>([
  {
    name: "email",
    type: "text",
    label: "Email"
  },
  {
    name: "password",
    type: "password",
    label: "Password"
  }
])
</script>

<template>
  <UAuthForm title="Login" description="Enter your credentials to access your account." icon="i-lucide-user" :fields="fields" :submit="{
  label: 'Submit',
  color: 'error',
  variant: 'subtle'
}" class="max-w-md" />
</template>
```

## Examples

### Within a page

You can wrap the `AuthForm` component with the [PageCard](/docs/components/page-card) component to display it within a `login.vue` page for example.

```vue [AuthFormPageExample.vue]
<script setup lang="ts">
import * as z from 'zod'
import type { FormSubmitEvent, AuthFormField } from '@nuxt/ui'

const toast = useToast()

const fields: AuthFormField[] = [{
  name: 'email',
  type: 'email',
  label: 'Email',
  placeholder: 'Enter your email',
  required: true
}, {
  name: 'password',
  label: 'Password',
  type: 'password',
  placeholder: 'Enter your password',
  required: true
}, {
  name: 'remember',
  label: 'Remember me',
  type: 'checkbox'
}]

const providers = [{
  label: 'Google',
  icon: 'i-simple-icons-google',
  onClick: () => {
    toast.add({ title: 'Google', description: 'Login with Google' })
  }
}, {
  label: 'GitHub',
  icon: 'i-simple-icons-github',
  onClick: () => {
    toast.add({ title: 'GitHub', description: 'Login with GitHub' })
  }
}]

const schema = z.object({
  email: z.email('Invalid email'),
  password: z.string('Password is required').min(8, 'Must be at least 8 characters')
})

type Schema = z.output<typeof schema>

function onSubmit(payload: FormSubmitEvent<Schema>) {
  console.log('Submitted', payload)
}
</script>

<template>
  <div class="flex flex-col items-center justify-center gap-4 p-4">
    <UPageCard class="w-full max-w-md">
      <UAuthForm
        :schema="schema"
        :fields="fields"
        :providers="providers"
        title="Welcome back!"
        icon="i-lucide-lock"
        @submit="onSubmit"
      >
        <template #description>
          Don't have an account? <ULink to="#" class="text-primary font-medium">Sign up</ULink>.
        </template>
        <template #password-hint>
          <ULink to="#" class="text-primary font-medium" tabindex="-1">Forgot password?</ULink>
        </template>
        <template #validation>
          <UAlert color="error" icon="i-lucide-info" title="Error signing in" />
        </template>
        <template #footer>
          By signing in, you agree to our <ULink to="#" class="text-primary font-medium">Terms of Service</ULink>.
        </template>
      </UAuthForm>
    </UPageCard>
  </div>
</template>
```

## API

### Props

```ts
/**
 * Props for the AuthForm component
 */
interface AuthFormProps {
  /**
   * The element or component this component should render as.
   */
  as?: any;
  /**
   * The icon displayed above the title.
   */
  icon?: any;
  title?: string | undefined;
  description?: string | undefined;
  fields?: F[] | undefined;
  /**
   * Display a list of Button under the description.
   * `{ color: 'neutral', variant: 'subtle', block: true }`{lang="ts-type"}
   */
  providers?: ButtonProps[] | undefined;
  /**
   * The text displayed in the separator.
   * @default "\"or\""
   */
  separator?: string | SeparatorProps | undefined;
  /**
   * Display a submit button at the bottom of the form.
   * `{ label: 'Continue', block: true }`{lang="ts-type"}
   */
  submit?: Omit<ButtonProps, LinkPropsKeys> | undefined;
  schema?: T | undefined;
  validate?: ((state: Partial<InferInput<T>>) => FormError<string>[] | Promise<FormError<string>[]>) | undefined;
  validateOn?: FormInputEvents[] | undefined;
  validateOnInputDelay?: number | undefined;
  disabled?: boolean | undefined;
  loading?: boolean | undefined;
  loadingAuto?: boolean | undefined;
  ui?: { root?: SlotClass; header?: SlotClass; leading?: SlotClass; leadingIcon?: SlotClass; title?: SlotClass; description?: SlotClass; body?: SlotClass; providers?: SlotClass; checkbox?: SlotClass; select?: SlotClass; password?: SlotClass; otp?: SlotClass; input?: SlotClass; separator?: SlotClass; form?: SlotClass; footer?: SlotClass; } | undefined;
  name?: string | undefined;
  autocomplete?: string | undefined;
  acceptcharset?: string | undefined;
  action?: string | undefined;
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
 * Slots for the AuthForm component
 */
interface AuthFormSlots {
  header(): any;
  leading(): any;
  title(): any;
  description(): any;
  providers(): any;
  separator(): any;
  validation(): any;
  submit(): any;
  footer(): any;
}
```

### Emits

```ts
/**
 * Emitted events for the AuthForm component
 */
interface AuthFormEmits {
  submit: (payload: [payload: FormSubmitEvent<Reactive<InferInput<T>>>]) => void;
}
```

### Expose

You can access the typed component instance (exposing formRef and state) using [`useTemplateRef`](https://vuejs.org/api/composition-api-helpers.html#usetemplateref). For example, in a separate form (e.g. a "reset" form) you can do:

```vue
<script setup lang="ts">
const authForm = useTemplateRef('authForm')
</script>

<template>
  <UAuthForm ref="authForm" />
</template>
```

This gives you access to the following (exposed) properties:

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
          formRef
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
          HTMLFormElement
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
          state
        </span>
      </code>
    </td>
    
    <td>
      <code className="language-ts-type shiki shiki-themes material-theme-lighter material-theme material-theme-palenight" language="ts-type" style="">
        <span class="sBMFI">
          Reactive
        </span>
        
        <span class="sMK4o">
          <
        </span>
        
        <span class="sBMFI">
          FormStateType
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
    authForm: {
      slots: {
        root: 'w-full space-y-6',
        header: 'flex flex-col text-center',
        leading: 'mb-2',
        leadingIcon: 'size-8 shrink-0 inline-block',
        title: 'text-xl text-pretty font-semibold text-highlighted',
        description: 'mt-1 text-base text-pretty text-muted',
        body: 'gap-y-6 flex flex-col',
        providers: 'space-y-3',
        checkbox: '',
        select: 'w-full',
        password: 'w-full',
        otp: 'w-full',
        input: 'w-full',
        separator: '',
        form: 'space-y-5',
        footer: 'text-sm text-center text-muted mt-2'
      }
    }
  }
})
```

## Changelog

See commit history for [component](https://github.com/nuxt/ui/commits/v4/src/runtime/components/AuthForm.vue) and [theme](https://github.com/nuxt/ui/commits/v4/src/theme/auth-form.ts).


## Sitemap

See the full [sitemap](/sitemap.md) for all pages.

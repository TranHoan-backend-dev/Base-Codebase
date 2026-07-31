<!--
  BasePopup Component cho Nuxt 4 sử dụng Nuxt UI v4 (UModal).
  Cung cấp Header chuẩn (padding 12px 16px), Body (padding 0 16px 16px 16px),
  Footer chuẩn (padding 12px 16px) với nút Hủy / Lưu bên phải (nút Lưu có thể ẩn).
  Hỗ trợ nút Resize toàn màn hình và guard onBeforeClose.

  @created_at 09/07/2026
  @author txhoan
-->
<script setup lang="ts">
import { ref, computed } from 'vue'
import type { PopupSize } from '@/types/popup'

const props = withDefaults(
  defineProps<{
    modelValue?: boolean
    open?: boolean
    title?: string
    description?: string
    size?: PopupSize
    showFullscreenButton?: boolean
    showSaveButton?: boolean
    showCancelButton?: boolean
    saveLabel?: string
    cancelLabel?: string
    saveLoading?: boolean
    saveDisabled?: boolean
    dismissible?: boolean
    onBeforeClose?: () => boolean | Promise<boolean>
  }>(),
  {
    modelValue: undefined,
    open: undefined,
    title: '',
    description: '',
    size: 'md',
    showFullscreenButton: true,
    showSaveButton: true,
    showCancelButton: true,
    saveLoading: false,
    saveDisabled: false,
    dismissible: true
  }
)

const emit = defineEmits<{
  (e: 'update:modelValue' | 'update:open', value: boolean): void
  (e: 'close:prevent' | 'save' | 'cancel'): void
}>()

const isOpen = computed(() => props.open ?? props.modelValue ?? false)
const isExpanded = ref(false)

const contentSizeClass = computed(() => {
  if (isExpanded.value) return 'max-w-full h-full sm:max-w-full rounded-none'

  switch (props.size) {
    case 'xs':
      return 'sm:max-w-xs'
    case 'sm':
      return 'sm:max-w-sm'
    case 'lg':
      return 'sm:max-w-2xl'
    case 'xl':
      return 'sm:max-w-4xl'
    case 'full':
      return 'max-w-full h-full sm:max-w-full rounded-none'
    case 'md':
    default:
      return 'sm:max-w-lg'
  }
})

const contentStyle = computed(() => ({
  padding: 0,
  borderRadius: isExpanded.value || props.size === 'full' ? 0 : '8px'
}))

const handleRequestClose = async () => {
  if (props.onBeforeClose) {
    const canClose = await props.onBeforeClose()
    if (!canClose) return
  }
  emit('update:modelValue', false)
  emit('update:open', false)
  emit('cancel')
}

const handleOpenChange = async (newVal: boolean) => {
  if (!newVal) {
    if (!props.dismissible) {
      emit('close:prevent')
      return
    }
    if (props.onBeforeClose) {
      const canClose = await props.onBeforeClose()
      if (!canClose) return
    }
  }
  emit('update:modelValue', newVal)
  emit('update:open', newVal)
}

const toggleFullscreen = () => {
  isExpanded.value = !isExpanded.value
}
</script>

<template>
  <UModal
    :open="isOpen"
    :title="title"
    :description="description"
    :dismissible="dismissible"
    :fullscreen="isExpanded || size === 'full'"
    :close="false"
    :style="contentStyle"
    :ui="{
      content: contentSizeClass,
      header: 'py-3 px-4 border-b border-gray-200 dark:border-gray-800',
      body: 'p-0',
      footer: 'p-0 sm:p-0'
    }"
    @update:open="handleOpenChange"
  >
    <template #header>
      <div class="flex items-center justify-between w-full gap-4">
        <div class="flex flex-col gap-0.5 min-w-0">
          <h3 class="text-base font-semibold leading-6 text-gray-900 dark:text-white truncate">
            {{ title }}
          </h3>
          <p
            v-if="description"
            class="text-xs text-gray-500 dark:text-gray-400 truncate"
          >
            {{ description }}
          </p>
        </div>
        <div class="flex items-center gap-1.5 shrink-0">
          <UButton
            v-if="showFullscreenButton && size !== 'full'"
            :icon="isExpanded ? 'i-heroicons-arrows-pointing-in' : 'i-heroicons-arrows-pointing-out'"
            color="neutral"
            variant="ghost"
            size="sm"
            :aria-label="isExpanded ? $t('popup.collapse') : $t('popup.expand')"
            @click="toggleFullscreen"
          />
          <UButton
            color="neutral"
            variant="ghost"
            icon="i-lucide-x"
            size="sm"
            :aria-label="$t('popup.close')"
            @click="handleRequestClose"
          />
        </div>
      </div>
    </template>

    <template #body>
      <div class="swe-popup__body">
        <slot />
      </div>
    </template>

    <template #footer>
      <div class="swe-popup__footer w-full">
        <slot name="footer">
          <UButton
            v-if="showCancelButton"
            color="neutral"
            variant="outline"
            @click="handleRequestClose"
          >
            {{ cancelLabel || $t('popup.cancel') }}
          </UButton>
          <UButton
            v-if="showSaveButton"
            color="primary"
            :loading="saveLoading"
            :disabled="saveDisabled"
            @click="emit('save')"
          >
            {{ saveLabel || $t('popup.save') }}
          </UButton>
        </slot>
      </div>
    </template>
  </UModal>
</template>

<style scoped lang="scss">
@use "./popup" as *;

.swe-popup__body {
  @include popup-body;
}

.swe-popup__footer {
  @include popup-footer;
}
</style>

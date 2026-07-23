<script setup lang="ts">
/**
 * Component nút bấm tái sử dụng (SweButton) dựa trên Nuxt UI (UButton, UTooltip, UBadge).
 * Hỗ trợ đầy đủ các tính năng: Tooltip (gồm cả HTML sanitized), Badge, Icon position, Loading state.
 *
 * @created_at 23/07/2026
 * @author txhoan
 */
import { computed, ref, onUnmounted } from 'vue'
import { sanitizeHtml } from '~/utils/security'

export interface SweButtonProps {
  /** Tiêu đề hiển thị trên nút */
  btnTitle?: string
  /** Kích thước nút bấm */
  btnSize?: 'xs' | 'sm' | 'md' | 'lg' | 'xl'
  /** Biến thể giao diện của nút */
  variant?: 'solid' | 'outline' | 'soft' | 'subtle' | 'ghost' | 'link' | 'danger' | 'danger-soft'
  /** Tên icon Iconify hoặc Vue component */
  icon?: string
  /** Vị trí hiển thị của Icon: 'left' | 'right' (Mặc định: 'left') */
  iconPosition?: 'left' | 'right'
  /** Văn bản hiển thị thay thế cho btnTitle khi nút đang ở trạng thái loading (isPending=true) */
  loadingText?: string
  /** Nội dung Tooltip hiển thị khi di chuột hoặc focus vào nút */
  tooltip?: string
  /** Chuỗi HTML hiển thị trong Tooltip (được làm sạch an toàn XSS) */
  tooltipHtml?: string
  /** Số lượng nhãn thông báo (Badge) hiển thị ở góc nút */
  badgeCount?: number
  /** Trạng thái loading của nút */
  isPending?: boolean
  /** Cho phép giữ Tooltip mở khi rhover vào vùng nội dung Tooltip */
  keepTooltipOpenOnHover?: boolean
  /** Thời gian trễ đóng Tooltip (ms) */
  tooltipCloseDelay?: number
  /** Vô hiệu hóa nút bấm */
  isDisabled?: boolean
}

const props = withDefaults(defineProps<SweButtonProps>(), {
  btnTitle: '',
  btnSize: 'md',
  variant: 'outline',
  iconPosition: 'left',
  keepTooltipOpenOnHover: false,
  tooltipCloseDelay: 300,
  isPending: false,
  isDisabled: false
})

defineEmits<{
  (e: 'click', event: MouseEvent): void
}>()

const isTooltipOpen = ref(false)
let timeoutId: ReturnType<typeof setTimeout> | null = null

const clearCloseTimeout = () => {
  if (timeoutId) {
    clearTimeout(timeoutId)
    timeoutId = null
  }
}

onUnmounted(() => {
  clearCloseTimeout()
})

const hasTooltip = computed(() => Boolean(props.tooltip || props.tooltipHtml))
const sanitizedTooltipHtml = computed(() => sanitizeHtml(props.tooltipHtml))

const displayText = computed(() => {
  return props.isPending && props.loadingText ? props.loadingText : props.btnTitle
})

const mappedVariant = computed(() => {
  if (props.variant === 'danger') return 'solid'
  if (props.variant === 'danger-soft') return 'soft'
  return props.variant
})

const mappedColor = computed(() => {
  if (props.variant === 'danger' || props.variant === 'danger-soft') return 'error'
  return undefined
})

const handleMouseEnter = () => {
  if (props.keepTooltipOpenOnHover && hasTooltip.value) {
    clearCloseTimeout()
    isTooltipOpen.value = true
  }
}

const handleMouseLeave = () => {
  if (props.keepTooltipOpenOnHover && hasTooltip.value) {
    clearCloseTimeout()
    timeoutId = setTimeout(() => {
      isTooltipOpen.value = false
    }, props.tooltipCloseDelay)
  }
}
</script>

<template>
  <div
    class="inline-flex relative items-center"
    @mouseenter="handleMouseEnter"
    @mouseleave="handleMouseLeave"
  >
    <UTooltip
      :prevent="!hasTooltip"
      :open="keepTooltipOpenOnHover ? isTooltipOpen : undefined"
      :delay="{ close: tooltipCloseDelay }"
    >
      <div class="relative inline-flex">
        <UButton
          :size="btnSize"
          :variant="mappedVariant"
          :color="mappedColor"
          :loading="isPending"
          :disabled="isDisabled"
          :icon="iconPosition === 'left' ? icon : undefined"
          :trailing-icon="iconPosition === 'right' ? icon : undefined"
          v-bind="$attrs"
          @click="$emit('click', $event)"
        >
          <slot>{{ displayText }}</slot>
        </UButton>

        <!-- Badge đính kèm -->
        <UBadge
          v-if="typeof badgeCount === 'number'"
          color="error"
          variant="solid"
          size="xs"
          class="absolute -top-2 -right-2 rounded-full z-10 pointer-events-none"
        >
          {{ badgeCount }}
        </UBadge>
      </div>

      <template #content>
        <slot name="tooltip">
          <!-- eslint-disable-next-line vue/no-v-html -->
          <div
            v-if="tooltipHtml"
            v-html="sanitizedTooltipHtml"
          />
          <span v-else>{{ tooltip }}</span>
        </slot>
      </template>
    </UTooltip>
  </div>
</template>

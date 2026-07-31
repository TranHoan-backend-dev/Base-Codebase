<script setup lang="ts">
import { computed } from 'vue'
import {
  useSweCombobox,
  type ComboboxMode,
  type ComboboxDataType,
  type ComboboxOption
} from '~/composables/useSweCombobox'

/**
 * Props định nghĩa cho SweCombobox Component
 *
 * @created_at 28/07/2026
 * @author txhoan
 */
const props = withDefaults(
  defineProps<{
    /** Chế độ hiển thị: 'view' (gạch dưới style Android) hoặc 'edit' (ô chọn tiêu chuẩn) */
    mode?: ComboboxMode
    /** Kiểu dữ liệu chứa bên trong: 'text' | 'date' | 'number' | 'tag' | 'human' */
    dataType?: ComboboxDataType
    /** Giá trị hiện tại (v-model) */
    modelValue?: string | number | null
    /** Danh sách tùy chọn cho ô chọn */
    options?: ComboboxOption[]
    /** Văn bản gợi ý khi chưa chọn giá trị */
    placeholder?: string
    /** Tiêu đề (Label) của ô chọn */
    label?: string
    /** Trạng thái vô hiệu hóa */
    disabled?: boolean
    /** Class CSS bổ sung */
    className?: string
    /** Chiều rộng tùy chỉnh (ví dụ: '300px', '100%', 250) */
    width?: string | number
    /** Chiều cao tùy chỉnh (ví dụ: '40px', 38) */
    height?: string | number
  }>(),
  {
    mode: 'edit',
    dataType: 'text',
    modelValue: null,
    options: () => [],
    placeholder: undefined,
    label: undefined,
    disabled: false,
    className: '',
    width: undefined,
    height: undefined
  }
)

/**
 * Emits định nghĩa các sự kiện phát ra từ SweCombobox
 */
const emit = defineEmits<{
  (e: 'update:modelValue', value: string | number | null): void
  (e: 'change', value: string | number | null, selectedOption?: ComboboxOption): void
}>()

const { t } = useI18n()
const { formatDate, formatNumber, findSelectedOption, getInitialLetter } = useSweCombobox()

/**
 * Placeholder hiển thị đa ngôn ngữ
 */
const displayPlaceholder = computed(() => {
  return props.placeholder || t('combobox.select_option')
})

/**
 * Option được chọn hiện tại dựa trên modelValue
 */
const selectedOption = computed(() => {
  return findSelectedOption(props.modelValue, props.options)
})

/**
 * Style tùy chỉnh cho chiều rộng và chiều cao container
 */
const containerStyle = computed(() => {
  const style: Record<string, string> = {}
  if (props.width !== undefined) {
    style.width = typeof props.width === 'number' ? `${props.width}px` : props.width
  }
  if (props.height !== undefined) {
    style.height = typeof props.height === 'number' ? `${props.height}px` : props.height
  }
  return style
})

/**
 * Xử lý khi người dùng chọn tùy chọn trong Edit mode
 */
const handleSelectChange = (opt: ComboboxOption | null) => {
  const newValue = opt ? opt.value : null
  emit('update:modelValue', newValue)
  emit('change', newValue, opt || undefined)
}
</script>

<template>
  <!-- === CHẾ ĐỘ VIEW (Android Underline Style) === -->
  <div
    v-if="mode === 'view'"
    class="flex flex-col gap-1"
    :class="className"
    :style="containerStyle"
  >
    <span
      v-if="label"
      class="text-xs font-semibold text-gray-500 uppercase tracking-wider"
    >
      {{ label }}
    </span>
    <div
      class="w-full min-h-[38px] border-b-2 border-gray-300 py-1 flex items-center transition-colors"
      :class="disabled ? 'opacity-50 cursor-not-allowed' : 'hover:border-primary-500'"
    >
      <!-- Chưa chọn giá trị -->
      <span
        v-if="!selectedOption && (modelValue === null || modelValue === undefined)"
        class="text-gray-400 italic text-sm"
      >
        {{ displayPlaceholder }}
      </span>

      <!-- Render Kiểu Human -->
      <template v-else-if="dataType === 'human'">
        <div class="flex items-center gap-3 py-0.5">
          <UAvatar
            :src="selectedOption?.avatarUrl"
            :alt="selectedOption?.label || String(modelValue)"
            :text="getInitialLetter(selectedOption?.label || String(modelValue))"
            size="sm"
          />
          <div class="flex flex-col text-left">
            <span class="text-sm font-medium text-gray-900 leading-tight">
              {{ selectedOption?.label || modelValue }}
            </span>
            <span
              v-if="selectedOption?.description"
              class="text-xs text-gray-500 leading-tight"
            >
              {{ selectedOption.description }}
            </span>
          </div>
        </div>
      </template>

      <!-- Render Kiểu Tag -->
      <template v-else-if="dataType === 'tag'">
        <UBadge
          :color="selectedOption?.tagColor || 'primary'"
          variant="soft"
          size="sm"
          class="font-medium"
        >
          {{ selectedOption?.label || modelValue }}
        </UBadge>
      </template>

      <!-- Render Kiểu Date -->
      <template v-else-if="dataType === 'date'">
        <span class="text-sm font-mono text-gray-800 bg-gray-100 px-2 py-0.5 rounded">
          {{ formatDate(selectedOption?.label || String(modelValue)) }}
        </span>
      </template>

      <!-- Render Kiểu Number -->
      <template v-else-if="dataType === 'number'">
        <span class="text-sm font-semibold text-primary-600 font-mono">
          {{ formatNumber(selectedOption?.label || String(modelValue)) }}
        </span>
      </template>

      <!-- Render Kiểu Text -->
      <template v-else>
        <span class="text-sm text-gray-900 font-normal">
          {{ selectedOption?.label || modelValue }}
        </span>
      </template>
    </div>
  </div>

  <!-- === CHẾ ĐỘ EDIT (Standard Combobox / USelectMenu) === -->
  <div
    v-else
    class="flex flex-col gap-1"
    :class="className"
    :style="containerStyle"
  >
    <label
      v-if="label"
      class="text-xs font-semibold text-gray-700 uppercase tracking-wider"
    >
      {{ label }}
    </label>

    <USelectMenu
      :model-value="selectedOption"
      :items="options"
      :disabled="disabled"
      :placeholder="displayPlaceholder"
      option-attribute="label"
      value-attribute="value"
      class="w-full"
      @update:model-value="handleSelectChange"
    >
      <!-- Custom Display Slot khi được chọn -->
      <template #default="{ modelValue: current }">
        <span
          v-if="!current"
          class="text-gray-400 italic text-sm"
        >
          {{ displayPlaceholder }}
        </span>
        <template v-else-if="dataType === 'human'">
          <div class="flex items-center gap-2">
            <UAvatar
              :src="current.avatarUrl"
              :alt="current.label"
              :text="getInitialLetter(current.label)"
              size="2xs"
            />
            <span class="text-sm font-medium text-gray-900">{{ current.label }}</span>
          </div>
        </template>
        <template v-else-if="dataType === 'tag'">
          <UBadge
            :color="current.tagColor || 'primary'"
            variant="soft"
            size="xs"
          >
            {{ current.label }}
          </UBadge>
        </template>
        <template v-else-if="dataType === 'date'">
          <span class="text-sm font-mono text-gray-800">
            {{ formatDate(current.label) }}
          </span>
        </template>
        <template v-else-if="dataType === 'number'">
          <span class="text-sm font-semibold text-primary-600 font-mono">
            {{ formatNumber(current.label) }}
          </span>
        </template>
        <template v-else>
          <span class="text-sm text-gray-900">{{ current.label }}</span>
        </template>
      </template>

      <!-- Custom Item Slot danh sách các tùy chọn trong Dropdown -->
      <template #item="{ item }">
        <template v-if="dataType === 'human'">
          <div class="flex items-center gap-3 py-1">
            <UAvatar
              :src="item.avatarUrl"
              :alt="item.label"
              :text="getInitialLetter(item.label)"
              size="xs"
            />
            <div class="flex flex-col text-left">
              <span class="text-sm font-medium text-gray-900 leading-tight">
                {{ item.label }}
              </span>
              <span
                v-if="item.description"
                class="text-xs text-gray-500 leading-tight"
              >
                {{ item.description }}
              </span>
            </div>
          </div>
        </template>
        <template v-else-if="dataType === 'tag'">
          <UBadge
            :color="item.tagColor || 'primary'"
            variant="soft"
            size="sm"
          >
            {{ item.label }}
          </UBadge>
        </template>
        <template v-else-if="dataType === 'date'">
          <span class="text-sm font-mono">
            {{ formatDate(item.label) }}
          </span>
        </template>
        <template v-else-if="dataType === 'number'">
          <span class="text-sm font-semibold text-primary-600 font-mono">
            {{ formatNumber(item.label) }}
          </span>
        </template>
        <template v-else>
          <div class="flex flex-col">
            <span class="text-sm text-gray-900">{{ item.label }}</span>
            <span
              v-if="item.description"
              class="text-xs text-gray-500"
            >
              {{ item.description }}
            </span>
          </div>
        </template>
      </template>
    </USelectMenu>
  </div>
</template>

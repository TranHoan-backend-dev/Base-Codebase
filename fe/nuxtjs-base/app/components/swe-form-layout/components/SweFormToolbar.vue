<!--
  SweFormToolbar — Thanh công cụ cố định phía trên trong SweFormLayout.
  Hiển thị icon back + title ở chế độ thường,
  hoặc số bản ghi đã chọn + nút Bỏ chọn/Xóa ở chế độ pickMany.

  @created_at 19/07/2026
-->
<script setup lang="ts">
const props = withDefaults(
  defineProps<{
    /** Tiêu đề thanh công cụ */
    title: string
    /** Bật chế độ chọn nhiều bản ghi */
    pickManyMode?: boolean
    /** Số lượng bản ghi đang được chọn */
    numberOfPickedRecords?: number
  }>(),
  {
    pickManyMode: false,
    numberOfPickedRecords: 0
  }
)

const emit = defineEmits<{
  /** Kích hoạt khi nhấn nút Bỏ chọn hoặc Xóa */
  deselect: []
  delete: []
}>()

const { t } = useI18n()

/**
 * Điều hướng quay lại trang trước.
 * Sử dụng `useRouter` thay vì component icon có sẵn.
 */
const router = useRouter()
const handleGoBack = (): void => {
  router.back()
}
</script>

<template>
  <div
    data-testid="swe-form-toolbar"
    class="swe-form-toolbar"
  >
    <!-- Phía trái: Back icon + Title hoặc Pick-many controls -->
    <div
      data-testid="swe-form-toolbar-left"
      class="swe-form-toolbar__left"
    >
      <template v-if="props.pickManyMode && props.numberOfPickedRecords">
        <span>{{ t('toolbar.selected', { count: props.numberOfPickedRecords }) }}</span>
        <UButton
          data-testid="swe-form-toolbar-deselect-btn"
          class="swe-form-toolbar__deselect-btn"
          color="error"
          variant="ghost"
          @click="emit('deselect')"
        >
          {{ t('toolbar.deselect') }}
        </UButton>
        <UButton
          data-testid="swe-form-toolbar-delete-btn"
          class="swe-form-toolbar__delete-btn"
          color="error"
          variant="solid"
          icon="i-lucide-trash-2"
          @click="emit('delete')"
        >
          {{ t('toolbar.delete') }}
        </UButton>
      </template>
      <template v-else>
        <UIcon
          data-testid="swe-form-toolbar-back-icon"
          name="i-lucide-arrow-left"
          class="swe-form-toolbar__back-icon"
          @click="handleGoBack"
        />
        <span
          data-testid="swe-form-toolbar-title"
          class="swe-form-toolbar__title"
        >{{ props.title }}</span>
      </template>
    </div>

    <!-- Phía phải: Slot cho các nút hành động bổ sung -->
    <div
      data-testid="swe-form-toolbar-right"
      class="swe-form-toolbar__right"
    >
      <slot name="right-actions" />
    </div>
  </div>
</template>

<style scoped lang="scss">
@use "../swe-form-layout" as *;
</style>

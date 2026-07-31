<!--
  SweFormFooter — Footer cố định phía dưới trong SweFormLayout.
  Chứa nút Hủy (outline) và Lưu (primary, hỗ trợ loading state).

  @created_at 19/07/2026
-->
<script setup lang="ts">
withDefaults(
  defineProps<{
    /** Trạng thái loading của nút Lưu */
    isPending?: boolean
  }>(),
  {
    isPending: false
  }
)

const emit = defineEmits<{
  /** Kích hoạt khi nhấn nút Lưu hoặc Hủy */
  save: []
  cancel: []
}>()

const { t } = useI18n()
</script>

<template>
  <div
    data-testid="swe-form-footer"
    class="swe-form-footer"
  >
    <UButton
      data-testid="swe-form-footer-cancel-btn"
      class="swe-form-footer__cancel-btn"
      color="neutral"
      variant="outline"
      @click="emit('cancel')"
    >
      {{ t('popup.cancel') }}
    </UButton>
    <UButton
      data-testid="swe-form-footer-save-btn"
      class="swe-form-footer__save-btn"
      color="primary"
      :loading="isPending"
      @click="emit('save')"
    >
      {{ t('popup.save') }}
    </UButton>
  </div>
</template>

<style scoped lang="scss">
@use "../swe-form-layout" as *;
</style>

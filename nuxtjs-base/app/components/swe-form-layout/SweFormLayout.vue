<!--
  SweFormLayout — Layout chuẩn cho trang biểu mẫu (Form/Detail page).
  Chiếm toàn bộ viewport (100vh), cố định Toolbar trên + Footer dưới,
  chỉ cuộn nội dung ở phần giữa (swe-form-content).

  @created_at 19/07/2026
-->
<script setup lang="ts">
import { onMounted } from 'vue'
import SweFormToolbar from './components/SweFormToolbar.vue'
import SweFormFooter from './components/SweFormFooter.vue'

const props = withDefaults(
  defineProps<{
    /** Tiêu đề hiển thị trên Toolbar */
    title: string
    /** Lớp CSS tùy biến cho wrapper ngoài */
    class?: string
    /** Bật chế độ chọn nhiều bản ghi */
    pickManyMode?: boolean
    /** Số lượng bản ghi đang được chọn */
    numberOfPickedRecords?: number
    /** Trạng thái loading của nút Lưu */
    isPending?: boolean
  }>(),
  {
    class: '',
    pickManyMode: false,
    numberOfPickedRecords: 0,
    isPending: false
  }
)

const emit = defineEmits<{
  /** Kích hoạt khi nhấn các nút hành động: Bỏ chọn, Xóa, Lưu, Hủy */
  deselect: []
  delete: []
  save: []
  cancel: []
}>()

onMounted(() => {
  if (import.meta.client) {
    document.body.setAttribute('data-hydrated', 'true')
  }
})
</script>

<template>
  <div
    data-testid="swe-form-layout"
    :class="['swe-form-layout', props.class]"
  >
    <SweFormToolbar
      :title="props.title"
      :pick-many-mode="props.pickManyMode"
      :number-of-picked-records="props.numberOfPickedRecords"
      @deselect="emit('deselect')"
      @delete="emit('delete')"
    >
      <template #right-actions>
        <slot name="right-actions" />
      </template>
    </SweFormToolbar>

    <div
      data-testid="swe-form-content"
      class="swe-form-content"
    >
      <slot name="layoutBody" />
    </div>

    <SweFormFooter
      :is-pending="props.isPending"
      @save="emit('save')"
      @cancel="emit('cancel')"
    />
  </div>
</template>

<style scoped lang="scss">
@use "./swe-form-layout" as *;
</style>

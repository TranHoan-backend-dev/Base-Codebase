<!--
  Trang Demo kiểm thử BasePopup và BasePopupSidebar trong Nuxt 4.
  Minh họa Center Popup, Sidebar Left, Sidebar Right, Resize Fullscreen,
  và Guard Form có thay đổi (dirty check).

  @created_at 09/07/2026
  @author txhoan
-->
<script setup lang="ts">
import { ref } from 'vue'
import { usePopup } from '@/composables/usePopup'
import BasePopup from '@/components/swe-popup/BasePopup.vue'
import BasePopupSidebar from '@/components/swe-popup/BasePopupSidebar.vue'

const centerPopup = usePopup()
const sidebarRight = usePopup()
const sidebarLeft = usePopup()

const dirtyValue = ref('')

const handleBeforeClose = () => {
  if (dirtyValue.value.trim() !== '') {
    return window.confirm(
      'Bạn có thay đổi chưa lưu, bạn có chắc muốn đóng popup không?'
    )
  }
  return true
}

const handleSaveCenter = () => {
  alert('Đã lưu thành công!')
  dirtyValue.value = ''
  centerPopup.close()
}

const handleSaveRight = () => {
  alert('Đã lưu cấu hình!')
  sidebarRight.close()
}
</script>

<template>
  <div class="flex flex-col items-center justify-center gap-6 py-10 px-4">
    <div class="text-center max-w-xl">
      <h1 class="text-2xl font-bold mb-2">
        {{ $t('popup_demo.title') }}
      </h1>
      <p class="text-sm opacity-70">
        {{ $t('popup_demo.desc') }}
      </p>
    </div>

    <div class="flex flex-wrap gap-4 justify-center">
      <UButton
        color="primary"
        @click="centerPopup.open"
      >
        {{ $t('popup_demo.open_center') }}
      </UButton>
      <UButton
        color="neutral"
        variant="solid"
        @click="sidebarRight.open"
      >
        {{ $t('popup_demo.open_right') }}
      </UButton>
      <UButton
        color="neutral"
        variant="outline"
        @click="sidebarLeft.open"
      >
        {{ $t('popup_demo.open_left') }}
      </UButton>
    </div>

    <!-- 1. BasePopup Center -->
    <BasePopup
      v-model="centerPopup.isOpen.value"
      :title="$t('popup_demo.center_title')"
      :description="$t('popup_demo.center_desc')"
      size="md"
      :show-fullscreen-button="true"
      :show-save-button="true"
      :on-before-close="handleBeforeClose"
      @save="handleSaveCenter"
    >
      <div class="flex flex-col gap-4 py-2">
        <div>
          <label class="block text-sm font-medium mb-1">
            {{ $t('popup_demo.fullname_label') }}
          </label>
          <input
            v-model="dirtyValue"
            class="w-full px-3 py-2 border rounded-md dark:bg-gray-900 dark:border-gray-700"
            :placeholder="$t('popup_demo.fullname_placeholder')"
            type="text"
          >
          <p class="text-xs opacity-60 mt-1">
            {{ $t('popup_demo.dirty_hint') }}
          </p>
        </div>
      </div>
    </BasePopup>

    <!-- 2. BasePopupSidebar Right -->
    <BasePopupSidebar
      v-model="sidebarRight.isOpen.value"
      side="right"
      :title="$t('popup_demo.right_title')"
      :description="$t('popup_demo.right_desc')"
      size="md"
      :show-fullscreen-button="true"
      @save="handleSaveRight"
    >
      <div class="flex flex-col gap-4 py-2">
        <p class="text-sm">
          {{ $t('popup_demo.right_content') }}
        </p>
      </div>
    </BasePopupSidebar>

    <!-- 3. BasePopupSidebar Left -->
    <BasePopupSidebar
      v-model="sidebarLeft.isOpen.value"
      side="left"
      :title="$t('popup_demo.left_title')"
      :description="$t('popup_demo.left_desc')"
      size="sm"
      :show-save-button="false"
      :cancel-label="$t('popup.close')"
    >
      <div class="flex flex-col gap-2 py-2">
        <p class="text-sm">
          {{ $t('popup_demo.home') }}
        </p>
        <p class="text-sm">
          {{ $t('popup_demo.products') }}
        </p>
        <p class="text-sm">
          {{ $t('popup_demo.settings') }}
        </p>
      </div>
    </BasePopupSidebar>
  </div>
</template>

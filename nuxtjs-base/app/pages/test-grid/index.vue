<!--
  Trang kiểm thử hệ thống Dynamic Grid (Nuxt 3 - Nuxt UI).
  Sử dụng cấu hình USERS từ server/api/v1/grid-config/USERS.

  @created_at 08/07/2026
  @author txhoan
-->
<script setup lang="ts">
import { ref } from 'vue'

defineOptions({
  name: 'TestGridPage'
})

const activeGridCode = ref('USERS')

const setActiveGridCode = (code: string) => {
  activeGridCode.value = code
}
</script>

<template>
  <div class="min-h-screen bg-gray-50 dark:bg-gray-950 p-6">
    <div class="max-w-7xl mx-auto space-y-6">
      <div class="flex items-center justify-between">
        <div>
          <h1 class="text-2xl font-bold text-gray-900 dark:text-white">
            Kiểm thử Hệ thống Dynamic Grid (Nuxt 3)
          </h1>
          <p class="text-sm text-gray-500 dark:text-gray-400">
            Minh họa trọn gói SSR Hydration, Phân trang, Lọc và Thao tác hàng loạt.
          </p>
        </div>

        <div class="flex items-center gap-2">
          <UButton
            :color="activeGridCode === 'USERS' ? 'primary' : 'neutral'"
            variant="soft"
            @click="setActiveGridCode('USERS')"
          >
            Người dùng (USERS)
          </UButton>
          <UButton
            :color="activeGridCode === 'ORDERS' ? 'primary' : 'neutral'"
            variant="soft"
            @click="setActiveGridCode('ORDERS')"
          >
            Đơn hàng (ORDERS)
          </UButton>
        </div>
      </div>

      <DynamicGrid
        :grid-code="activeGridCode"
        show-index
        show-selection-checkbox
      />
    </div>
  </div>
</template>

<!--
  Component phân trang riêng biệt cho Dynamic Grid (Nuxt 3 - Nuxt UI).
  Tách biệt khỏi GridTable theo yêu cầu đặc tả để dễ dàng tái sử dụng/tuỳ biến.

  @created_at 08/07/2026
  @author txhoan
-->
<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps<{
  total: number
  currentCount: number
  pageSize?: number
}>()

const page = defineModel<number>('page', { default: 1 })

const computedPageSize = computed(() => props.pageSize || 10)
const startRecord = computed(() => (page.value - 1) * computedPageSize.value + 1)
const endRecord = computed(() => Math.min(page.value * computedPageSize.value, props.total))
</script>

<template>
  <div class="flex flex-wrap items-center justify-between gap-4 px-4 py-3 bg-white dark:bg-gray-900 border-t border-gray-200 dark:border-gray-800 rounded-b-xl">
    <div class="text-sm text-gray-500 dark:text-gray-400">
      {{ $t('grid.showing') }}
      <span class="font-medium text-gray-900 dark:text-white">{{ startRecord }}</span>
      -
      <span class="font-medium text-gray-900 dark:text-white">{{ endRecord }}</span>
      {{ $t('grid.in_total') }}
      <span class="font-medium text-gray-900 dark:text-white">{{ total }}</span>
      {{ $t('grid.records') }}
    </div>

    <UPagination
      v-model:page="page"
      :items-per-page="computedPageSize"
      :total="total"
    />
  </div>
</template>

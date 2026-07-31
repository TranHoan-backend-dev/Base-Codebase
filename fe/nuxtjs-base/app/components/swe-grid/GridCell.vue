<!--
  Component cell hiển thị dữ liệu theo kiểu trong Dynamic Grid (Nuxt 3 - Nuxt UI).
  Sử dụng cơ chế an toàn URL (sanitizeUrl) cho LINK.

  @created_at 08/07/2026
  @author txhoan
-->
<script setup lang="ts">
import { computed } from 'vue'
import type { GridColumnConfig, GridDataItem } from '@/types/grid'
import { sanitizeGridUrl, isInternalUrl } from '@/utils/security'

const props = defineProps<{
  column: GridColumnConfig
  value: unknown
  rowData: GridDataItem
}>()

const safeLinkUrl = computed(() => {
  if (props.column.type !== 'LINK' || !props.column.actionConfig?.target) return null
  let url = props.column.actionConfig.target
  for (const key in props.rowData) {
    url = url.replace(`{${key}}`, String(props.rowData[key] ?? ''))
  }
  return sanitizeGridUrl(url)
})

const isInternal = computed(() => {
  if (!safeLinkUrl.value) return false
  return isInternalUrl(safeLinkUrl.value)
})

const formattedNumber = computed(() => {
  if (props.value === null || props.value === undefined) return ''
  return Number(props.value).toLocaleString('vi-VN')
})

const formattedCurrency = computed(() => {
  if (props.value === null || props.value === undefined) return ''
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(Number(props.value))
})
</script>

<template>
  <div class="truncate">
    <template v-if="column.type === 'LINK' && safeLinkUrl">
      <NuxtLink
        v-if="isInternal"
        :to="safeLinkUrl"
        class="text-primary-600 dark:text-primary-400 font-medium hover:underline"
      >
        {{ value }}
      </NuxtLink>
      <a
        v-else
        :href="safeLinkUrl"
        target="_blank"
        rel="noopener noreferrer"
        class="text-primary-600 dark:text-primary-400 font-medium hover:underline"
      >
        {{ value }}
      </a>
    </template>

    <template v-else-if="column.type === 'BADGE'">
      <UBadge
        :color="(column.valueMap?.[String(value)]?.color || 'neutral') as any"
        variant="subtle"
        size="sm"
      >
        {{ column.valueMap?.[String(value)]?.label || value }}
      </UBadge>
    </template>

    <template v-else-if="column.type === 'NUMBER'">
      <span class="font-mono text-gray-900 dark:text-gray-100">{{ formattedNumber }}</span>
    </template>

    <template v-else-if="column.type === 'CURRENCY'">
      <span class="font-mono font-semibold text-gray-900 dark:text-gray-100">{{ formattedCurrency }}</span>
    </template>

    <template v-else>
      <span class="text-gray-900 dark:text-gray-100">{{ value ?? '' }}</span>
    </template>
  </div>
</template>

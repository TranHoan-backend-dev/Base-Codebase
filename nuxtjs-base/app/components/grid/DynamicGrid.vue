<!--
  Smart Container cho toàn bộ hệ thống Dynamic Grid (Nuxt 3 - Nuxt UI).
  Quản lý trạng thái thông qua useDynamicGrid, tích hợp Toolbar, Filter, Table.
  Hỗ trợ SSR fallback config/data tránh waterfall loading.

  @created_at 08/07/2026
  @author txhoan
-->
<script setup lang="ts">
import { useDynamicGrid } from '@/composables/useDynamicGrid'
import type {
  GridConfigResponse,
  GridDataResponse,
  GridDataItem,
  GridFilterValue
} from '@/types/grid'

const props = defineProps<{
  gridCode: string
  fallbackConfig?: GridConfigResponse
  fallbackData?: GridDataResponse
  showIndex?: boolean
  showSelectionCheckbox?: boolean
  hideHorizontalScroll?: boolean
  hideVerticalScroll?: boolean
  ui?: { wrapper?: string, toolbar?: string, filter?: string, table?: string }
}>()

const emit = defineEmits<{
  (e: 'actionSuccess', actionCode: string, result: unknown): void
  (e: 'rowClick', rowData: GridDataItem): void
  (e: 'selectionChange', selectedKeys: Set<string>): void
}>()

const {
  config,
  data,
  page,
  filters,
  selectedKeys,
  isLoading,
  error,
  refreshData
} = useDynamicGrid(props.gridCode, props.fallbackConfig, props.fallbackData)

const handleSelectionChange = (keys: Set<string>) => {
  selectedKeys.value = keys
  emit('selectionChange', keys)
}

const handleSearch = (newFilters: Record<string, GridFilterValue>) => {
  filters.value = { ...newFilters }
  page.value = 1
}
</script>

<template>
  <div
    class="flex flex-col gap-4 w-full"
    :class="ui?.wrapper"
  >
    <div
      v-if="isLoading && !config"
      class="flex items-center justify-center py-12"
    >
      <UIcon
        name="i-heroicons-arrow-path"
        class="w-8 h-8 animate-spin text-primary-500"
      />
    </div>

    <div
      v-else-if="error"
      class="flex flex-col items-center gap-3 py-10 bg-red-50/50 dark:bg-red-950/20 rounded-xl border border-red-200 dark:border-red-900"
    >
      <UIcon
        name="i-heroicons-exclamation-triangle"
        class="w-8 h-8 text-red-500"
      />
      <p class="text-sm font-medium text-red-600 dark:text-red-400">
        {{ $t('grid.grid_config_error') }} {{ gridCode }}
      </p>
      <UButton
        color="primary"
        variant="soft"
        @click="refreshData"
      >
        {{ $t('grid.reset') }}
      </UButton>
    </div>

    <template v-else-if="config">
      <GridToolbar
        :title="config.title"
        :actions="config.actions.filter((a: any) => a.position === 'TOOLBAR')"
        :bulk-actions="config.actions.filter((a: any) => a.position === 'BULK')"
        :selected-keys="selectedKeys"
        :class="ui?.toolbar"
        @action-success="
          (code: any, res: any) => {
            refreshData()
            emit('actionSuccess', code, res)
          }
        "
      />

      <GridFilter
        v-if="config.filters && config.filters.length > 0"
        :filters="config.filters"
        :class="ui?.filter"
        @search="handleSearch"
      />

      <GridTable
        v-model:page="page"
        :columns="config.columns"
        :data="data?.items || []"
        :layout="config.layoutOptions"
        :show-index="showIndex"
        :show-selection-checkbox="showSelectionCheckbox"
        :selected-keys="selectedKeys"
        :total="data?.total || 0"
        :hide-horizontal-scroll="hideHorizontalScroll"
        :hide-vertical-scroll="hideVerticalScroll"
        :class="ui?.table"
        @selection-change="handleSelectionChange"
        @row-click="(row: any) => emit('rowClick', row)"
      />
    </template>
  </div>
</template>

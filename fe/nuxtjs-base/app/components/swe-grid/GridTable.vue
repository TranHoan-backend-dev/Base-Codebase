<!--
  Component bảng dữ liệu chính (GridTable) trong hệ thống Dynamic Grid (Nuxt 3 - Nuxt UI).
  Sử dụng UTable, hỗ trợ cột số thứ tự (STT) và phân trang độc lập qua GridPagination.

  @created_at 08/07/2026
  @author txhoan
-->
<script setup lang="ts">
import { computed } from 'vue'
import { useI18n } from 'vue-i18n'
import type { GridColumnConfig, LayoutOptions, GridDataItem } from '@/types/grid'

const { t } = useI18n()

const props = defineProps<{
  columns: GridColumnConfig[]
  data: GridDataItem[]
  layout: LayoutOptions
  showIndex?: boolean
  showSelectionCheckbox?: boolean
  selectedKeys: Set<string>
  total: number
  hideHorizontalScroll?: boolean
  hideVerticalScroll?: boolean
}>()

const emit = defineEmits<{
  (e: 'selectionChange', keys: Set<string>): void
  (e: 'rowClick', row: GridDataItem): void
}>()

const page = defineModel<number>('page', { default: 1 })

const showIndexCol = computed(() => props.showIndex ?? props.layout.showIndex ?? false)

const nuxtColumns = computed(() => {
  const cols: Array<Record<string, unknown>> = props.columns
    .filter(c => !c.hidden)
    .map(c => ({
      accessorKey: c.field,
      header: c.header
    }))

  if (showIndexCol.value) {
    cols.unshift({ accessorKey: '_stt', header: t('grid.index') })
  }
  return cols as unknown as undefined
})

const selectedRows = computed({
  get: () => props.data.filter(item => props.selectedKeys.has(String(item.id))),
  set: (rows: GridDataItem[]) => {
    emit('selectionChange', new Set(rows.map(r => String(r.id))))
  }
})
</script>

<template>
  <div class="flex flex-col gap-3 w-full">
    <div
      class="rounded-xl border border-gray-200 dark:border-gray-800 bg-white dark:bg-gray-900 overflow-hidden"
      :class="[
        hideHorizontalScroll ? 'overflow-x-hidden' : '',
        hideVerticalScroll ? 'overflow-y-hidden' : ''
      ]"
    >
      <UTable
        v-model="selectedRows"
        :data="data"
        :columns="nuxtColumns"
        @select="(row: unknown) => emit('rowClick', row as GridDataItem)"
      >
        <template #_stt-cell="{ row }">
          <span class="text-xs font-semibold text-gray-500">
            {{ (page - 1) * (layout.defaultPageSize || 10) + data.indexOf(row as unknown as GridDataItem) + 1 }}
          </span>
        </template>

        <template
          v-for="col in columns"
          :key="col.field"
          #[`${col.field}-cell`]="{ row }"
        >
          <GridCell
            :column="col"
            :value="((row as unknown as Record<string, unknown>)[col.field])"
            :row-data="row as unknown as GridDataItem"
          />
        </template>
      </UTable>
    </div>

    <GridPagination
      v-if="layout.pagination && total > 0"
      v-model:page="page"
      :total="total"
      :current-count="data.length"
      :page-size="layout.defaultPageSize || 10"
    />
  </div>
</template>

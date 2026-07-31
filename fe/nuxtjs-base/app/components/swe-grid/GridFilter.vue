<!--
  Component bộ lọc tìm kiếm cho Dynamic Grid (Nuxt 3 - Nuxt UI).
  Hỗ trợ TEXT_INPUT, SELECT và component bộ lọc tùy chỉnh.

  @created_at 08/07/2026
  @author txhoan
-->
<script setup lang="ts">
import { reactive } from 'vue'
import type { GridFilterConfig, GridFilterValue } from '@/types/grid'

const props = defineProps<{
  filters: GridFilterConfig[]
}>()

const emit = defineEmits<{
  (e: 'search', values: Record<string, GridFilterValue>): void
}>()

const filterValues = reactive<Record<string, GridFilterValue>>({})

const handleSubmit = () => {
  emit('search', { ...filterValues })
}

const handleReset = () => {
  props.filters.forEach((f) => {
    filterValues[f.field] = undefined
  })
  emit('search', {})
}
</script>

<template>
  <div class="flex flex-wrap items-end gap-3 p-4 bg-gray-50/70 dark:bg-gray-800/40 rounded-xl border border-gray-200/80 dark:border-gray-800">
    <div
      v-for="filter in filters"
      :key="filter.field"
      class="min-w-50 flex-1"
    >
      <component
        :is="filter.component"
        v-if="filter.component"
        v-model="filterValues[filter.field]"
        :filter="filter"
      />
      <UFormField
        v-else
        :label="filter.label"
      >
        <USelectMenu
          v-if="filter.type === 'SELECT'"
          v-model="filterValues[filter.field]"
          :options="filter.options || []"
          value-attribute="value"
          option-attribute="label"
          :placeholder="filter.placeholder || $t('grid.select_placeholder')"
        />
        <UInput
          v-else
          v-model="filterValues[filter.field]"
          :placeholder="filter.placeholder"
        />
      </UFormField>
    </div>

    <div class="flex items-center gap-2">
      <UButton
        color="primary"
        icon="i-lucide-search"
        @click="handleSubmit"
      >
        {{ $t('grid.search') }}
      </UButton>
      <UButton
        color="neutral"
        variant="ghost"
        icon="i-lucide-rotate-cw"
        @click="handleReset"
      >
        {{ $t('grid.reset') }}
      </UButton>
    </div>
  </div>
</template>

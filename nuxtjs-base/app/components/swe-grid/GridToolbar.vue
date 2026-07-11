<!--
  Component thanh công cụ (Toolbar) cho Dynamic Grid (Nuxt 3 - Nuxt UI).
  Hiển thị tiêu đề, các nút hành động (Toolbar Actions) và thao tác hàng loạt (Bulk Actions).

  @created_at 08/07/2026
  @author txhoan
-->
<script setup lang="ts">
import { ref } from 'vue'
import type { GridActionConfig } from '@/types/grid'

const props = defineProps<{
  title: string
  actions?: GridActionConfig[]
  bulkActions?: GridActionConfig[]
  selectedKeys: Set<string>
}>()

const emit = defineEmits<{
  (e: 'actionSuccess', code: string, result: unknown): void
}>()

const pendingAction = ref<GridActionConfig | null>(null)
const isModalOpen = ref(false)
const isExecuting = ref(false)

const handleActionClick = (action: GridActionConfig) => {
  if (action.actionConfig?.confirmMessage) {
    pendingAction.value = action
    isModalOpen.value = true
    return
  }
  executeAction(action)
}

const executeAction = async (action: GridActionConfig) => {
  if (!action.actionConfig?.endpoint) return
  isExecuting.value = true
  try {
    const res = await $fetch(action.actionConfig.endpoint, {
      method: action.actionConfig.method || 'POST',
      body: action.position === 'BULK' ? { ids: Array.from(props.selectedKeys) } : undefined
    })
    emit('actionSuccess', action.code, res)
  } finally {
    isExecuting.value = false
    isModalOpen.value = false
  }
}

const handleCancelModal = () => {
  isModalOpen.value = false
}

const handleConfirmModal = () => {
  if (pendingAction.value) {
    executeAction(pendingAction.value)
  }
}
</script>

<template>
  <div class="flex flex-wrap items-center justify-between gap-3 px-4 py-3 bg-white dark:bg-gray-900 rounded-xl border border-gray-200 dark:border-gray-800 shadow-xs">
    <div class="flex items-center gap-2">
      <h2 class="text-base font-semibold text-gray-900 dark:text-white">
        {{ title }}
      </h2>
      <UBadge
        v-if="selectedKeys.size > 0"
        color="primary"
        variant="subtle"
        size="xs"
      >
        {{ $t('grid.selected_count') }} {{ selectedKeys.size }}
      </UBadge>
    </div>

    <div class="flex items-center gap-2">
      <template v-if="selectedKeys.size > 0 && bulkActions?.length">
        <UButton
          v-for="action in bulkActions"
          :key="action.code"
          :color="action.buttonType || 'primary'"
          variant="soft"
          size="sm"
          :icon="action.icon"
          @click="handleActionClick(action)"
        >
          {{ action.label }}
        </UButton>
      </template>

      <UButton
        v-for="action in actions"
        :key="action.code"
        :color="action.buttonType || 'primary'"
        size="sm"
        :icon="action.icon"
        @click="handleActionClick(action)"
      >
        {{ action.label }}
      </UButton>
    </div>

    <UModal v-model="isModalOpen">
      <UCard>
        <template #header>
          <div class="font-semibold text-gray-900 dark:text-white">
            {{ pendingAction?.label }}
          </div>
        </template>
        <p class="text-sm text-gray-600 dark:text-gray-300">
          {{ pendingAction?.actionConfig?.confirmMessage }}
        </p>
        <template #footer>
          <div class="flex justify-end gap-2">
            <UButton
              color="neutral"
              variant="ghost"
              @click="handleCancelModal"
            >
              {{ $t('grid.cancel') }}
            </UButton>
            <UButton
              color="primary"
              :loading="isExecuting"
              @click="handleConfirmModal"
            >
              {{ $t('grid.agree') }}
            </UButton>
          </div>
        </template>
      </UCard>
    </UModal>
  </div>
</template>

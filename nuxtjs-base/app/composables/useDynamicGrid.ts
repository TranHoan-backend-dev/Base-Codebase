/**
 * Composable quản lý trạng thái và dữ liệu cho Dynamic Grid (Nuxt 3).
 * Sử dụng useAsyncData hỗ trợ SSR Hydration, request deduplication và tự động fetch lại khi đổi trang/bộ lọc.
 *
 * @created_at 08/07/2026
 * @author txhoan
 */

import { ref, computed } from 'vue'
import { useAsyncData } from '#app'
import type { GridConfigResponse, GridDataResponse, GridFilterValue } from '@/types/grid'
import { isValidGridConfig, isValidGridData } from '@/utils/security'

export function useDynamicGrid(
  gridCode: string,
  fallbackConfig?: GridConfigResponse,
  fallbackData?: GridDataResponse
) {
  const page = ref<number>(1)
  const filters = ref<Record<string, GridFilterValue>>({})
  const selectedKeys = ref<Set<string>>(new Set())

  const {
    data: config,
    status: configStatus,
    error: configError,
    refresh: refreshConfig
  } = useAsyncData<GridConfigResponse | null>(
    `grid-config-${gridCode}`,
    async () => {
      if (fallbackConfig && fallbackConfig.gridCode === gridCode) {
        return fallbackConfig
      }
      const res = await $fetch<{ data?: GridConfigResponse } & GridConfigResponse>(
        `/api/v1/grid-config/${gridCode}`
      )
      const resConfig = res.data || res
      if (!isValidGridConfig(resConfig)) {
        throw new Error('Cấu hình Grid không hợp lệ')
      }
      return resConfig
    },
    { default: () => fallbackConfig || null }
  )

  const {
    data: gridData,
    status: dataStatus,
    error: dataError,
    refresh: refreshData
  } = useAsyncData<GridDataResponse | null>(
    `grid-data-${gridCode}`,
    async () => {
      if (!config.value) return null
      const cleanFilters: Record<string, string> = {}
      Object.entries(filters.value).forEach(([k, v]) => {
        if (v !== undefined && v !== null && v !== '') {
          cleanFilters[k] = String(v)
        }
      })
      const query = { page: page.value, ...cleanFilters }
      const res = await $fetch<{ data?: GridDataResponse } & GridDataResponse>(
        `/api/v1/grid-data/${gridCode}`,
        { query }
      )
      const resData = res.data || res
      if (!isValidGridData(resData)) {
        throw new Error('Dữ liệu Grid không hợp lệ')
      }
      return resData
    },
    {
      watch: [page, filters, config],
      default: () => fallbackData || null
    }
  )

  const isLoading = computed(() => configStatus.value === 'pending' || dataStatus.value === 'pending')
  const error = computed(() => configError.value || dataError.value)

  const reloadAll = async () => {
    selectedKeys.value.clear()
    await Promise.all([refreshConfig(), refreshData()])
  }

  return {
    config,
    data: gridData,
    page,
    filters,
    selectedKeys,
    isLoading,
    error,
    refreshData: reloadAll
  }
}

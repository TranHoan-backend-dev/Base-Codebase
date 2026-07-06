/**
 * Hook quản lý trạng thái và lấy dữ liệu cho Dynamic Grid.
 * Hỗ trợ SSR fallbackData để tránh waterfall loading, tối ưu hiệu năng Next.js 16.
 *
 * @created_at 05/07/2026
 * @author txhoan
 */

import { useState, useEffect, useCallback, useRef } from "react";

import { baseService } from "@/services/core/BaseService";
import {
  GridConfigResponse,
  GridDataResponse,
  GridFilterValue,
} from "@/types/grid";

export function useDynamicGrid(
  gridCode: string,
  fallbackConfig?: GridConfigResponse,
  fallbackData?: GridDataResponse,
) {
  const [page, setPage] = useState<number>(1);
  const [filters, setFilters] = useState<Record<string, GridFilterValue>>({});
  const [selectedKeys, setSelectedKeys] = useState<Set<string>>(new Set());
  const isInitialMount = useRef<boolean>(true);

  const [config, setConfig] = useState<GridConfigResponse | undefined>(
    fallbackConfig,
  );
  const [data, setData] = useState<GridDataResponse | undefined>(fallbackData);
  const [isConfigLoading, setIsConfigLoading] =
    useState<boolean>(!fallbackConfig);
  const [isDataLoading, setIsDataLoading] = useState<boolean>(!fallbackData);
  const [error, setError] = useState<Error | null>(null);

  // Tải cấu hình Grid nếu chưa có fallbackConfig từ SSR
  useEffect(() => {
    if (config) return;
    let isMounted = true;

    Promise.resolve().then(() => {
      if (isMounted) setIsConfigLoading(true);
    });

    baseService
      .get<{ data?: GridConfigResponse } & GridConfigResponse>(
        `/api/v1/grid-config/${gridCode}`,
      )
      .then((res) => {
        if (!isMounted) return;
        const configData = res.data || res;

        if (configData && configData.gridCode) {
          setConfig(configData as GridConfigResponse);
        }
      })
      .catch((err) => {
        if (isMounted)
          setError(err instanceof Error ? err : new Error(String(err)));
      })
      .finally(() => {
        if (isMounted) setIsConfigLoading(false);
      });

    return () => {
      isMounted = false;
    };
  }, [gridCode, config]);

  // Tải dữ liệu Grid theo trang và bộ lọc
  const fetchData = useCallback(async () => {
    if (!config) return;
    await Promise.resolve(); // Async boundary ngăn gọi setState đồng bộ trong effect
    setIsDataLoading(true);
    setError(null);

    try {
      const cleanFilters: Record<string, string> = {};

      Object.entries(filters).forEach(([k, v]) => {
        if (v !== undefined && v !== null && v !== "") {
          cleanFilters[k] = String(v);
        }
      });

      const queryParams = new URLSearchParams({
        page: page.toString(),
        ...cleanFilters,
      });

      const res = await baseService.get<
        { data?: GridDataResponse } & GridDataResponse
      >(`/api/v1/grid-data/${gridCode}?${queryParams.toString()}`);
      const gridData = res.data || res;

      setData(gridData as GridDataResponse);
    } catch (err) {
      setError(err instanceof Error ? err : new Error(String(err)));
    } finally {
      setIsDataLoading(false);
    }
  }, [config, gridCode, page, filters]);

  useEffect(() => {
    if (isInitialMount.current && fallbackData) {
      isInitialMount.current = false;

      return;
    }
    isInitialMount.current = false;
    let ignore = false;

    Promise.resolve().then(() => {
      if (!ignore) fetchData();
    });

    return () => {
      ignore = true;
    };
  }, [fetchData, fallbackData]);

  return {
    config,
    data,
    page,
    setPage,
    filters,
    setFilters,
    selectedKeys,
    setSelectedKeys,
    isLoading: (isConfigLoading && !config) || isDataLoading,
    error,
    refreshData: fetchData,
  };
}

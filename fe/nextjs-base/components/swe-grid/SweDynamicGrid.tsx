/**
 * Component DynamicGrid (Smart Container) sử dụng HeroUI v3.2.1.
 * Kết nối tự động giữa cấu hình từ API/SSR, Toolbar, Filter và Table.
 *
 * @created_at 06/07/2026
 * @author txhoan
 */

"use client";

import { Spinner, Button } from "@heroui/react";

import { useDynamicGrid } from "@/hooks/useDynamicGrid";
import { useGridTranslations } from "@/hooks/useGridTranslations";
import {
  GridConfigResponse,
  GridDataResponse,
  GridDataItem,
} from "@/types/grid";

import { SweGridFilter } from "./components/SweGridFilter";
import { SweGridTable } from "./components/SweGridTable";
import { SweGridToolbar } from "./components/SweGridToolbar";

export interface DynamicGridProps {
  gridCode: string;
  fallbackConfig?: GridConfigResponse;
  fallbackData?: GridDataResponse;
  hideHorizontalScroll?: boolean;
  hideVerticalScroll?: boolean;
  /** Cho phép bật/tắt hiển thị cột checkbox chọn dòng */
  showSelectionCheckbox?: boolean;
  /** Cho phép bật/tắt hiển thị cột số thứ tự (STT) */
  showIndex?: boolean;
  className?: string;
  classNames?: {
    wrapper?: string;
    toolbar?: string;
    filter?: string;
    table?: string;
  };
  onActionSuccess?: (actionCode: string, result: unknown) => void;
  onRowClick?: (rowData: GridDataItem) => void;
  onSelectionChange?: (selectedKeys: Set<string>) => void;
}

export const SweDynamicGrid = ({
  gridCode,
  fallbackConfig,
  fallbackData,
  hideHorizontalScroll = false,
  hideVerticalScroll = false,
  showSelectionCheckbox,
  showIndex,
  className = "",
  classNames = {},
  onActionSuccess,
  onRowClick,
  onSelectionChange,
}: DynamicGridProps) => {
  const { t } = useGridTranslations();
  const {
    config,
    data,
    isLoading,
    error,
    setFilters,
    selectedKeys,
    setSelectedKeys,
    page,
    setPage,
    refreshData,
  } = useDynamicGrid(gridCode, fallbackConfig, fallbackData);

  /**
   * Xử lý sự kiện khi thay đổi danh sách các dòng được chọn trên bảng.
   * Cập nhật state nội bộ và kích hoạt callback ra bên ngoài (nếu có).
   *
   * @param keys - Tập hợp các ID bản ghi đang được chọn.
   */
  const handleSelectionChange = (keys: Set<string>) => {
    setSelectedKeys(keys);
    onSelectionChange?.(keys);
  };

  /**
   * Xử lý khi một hành động (Action / Bulk Action) thực thi API thành công.
   * Tự động làm mới dữ liệu Grid và kích hoạt callback thông báo ra ngoài.
   *
   * @param actionCode - Mã hành động vừa thực hiện thành công.
   * @param result - Kết quả trả về từ API.
   */
  const handleActionSuccess = (actionCode: string, result: unknown) => {
    refreshData();
    onActionSuccess?.(actionCode, result);
  };

  if (isLoading && !config) {
    return (
      <div className="flex w-full items-center justify-center py-12">
        <Spinner color="current" size="lg" />
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex flex-col items-center justify-center gap-3 py-12 text-center">
        <p className="text-danger font-medium">
          {t("grid_config_error")} {gridCode}
        </p>
        <p className="text-sm text-default-500">{error.message}</p>
        <Button variant="primary" onPress={() => refreshData()}>
          {t("reset")}
        </Button>
      </div>
    );
  }

  if (!config) {
    return (
      <div className="py-8 text-center text-default-500">
        {t("grid_config_error")} {gridCode}
      </div>
    );
  }

  return (
    <div
      className={`flex flex-col gap-4 w-full ${className} ${classNames.wrapper || ""}`}
    >
      <SweGridToolbar
        actions={config.actions.filter((a) => a.position === "TOOLBAR")}
        bulkActions={config.actions.filter((a) => a.position === "BULK")}
        className={classNames.toolbar}
        selectedKeys={selectedKeys}
        title={config.title}
        onActionSuccess={handleActionSuccess}
      />

      {config.filters && config.filters.length > 0 && (
        <SweGridFilter
          className={classNames.filter}
          filters={config.filters}
          onSearch={setFilters}
        />
      )}

      <SweGridTable
        className={classNames.table}
        columns={config.columns}
        data={data?.items || []}
        hideHorizontalScroll={hideHorizontalScroll}
        hideVerticalScroll={hideVerticalScroll}
        layout={config.layoutOptions}
        page={page}
        selectedKeys={selectedKeys}
        showIndex={showIndex}
        showSelectionCheckbox={showSelectionCheckbox}
        total={data?.total || 0}
        onPageChange={setPage}
        onRowClick={onRowClick}
        onSelectionChange={handleSelectionChange}
      />
    </div>
  );
}

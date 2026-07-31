/**
 * Component GridToolbar cho hệ thống Dynamic Grid (HeroUI v3.2.1).
 * Hiển thị tiêu đề, các nút thao tác chung (TOOLBAR) và thao tác hàng loạt (BULK).
 *
 * @created_at 06/07/2026
 * @author txhoan
 */

"use client";

import { useGridTranslations } from "@/hooks/useGridTranslations";
import { GridActionConfig } from "@/types/grid";

import { SweGridAction } from "./SweGridAction";

export interface GridToolbarProps {
  title?: string;
  actions?: GridActionConfig[];
  bulkActions?: GridActionConfig[];
  selectedKeys?: Set<string>;
  onActionSuccess?: (actionCode: string, result: unknown) => void;
  className?: string;
}

export const SweGridToolbar = ({
  title = "",
  actions = [],
  bulkActions = [],
  selectedKeys = new Set(),
  onActionSuccess,
  className = "",
}: GridToolbarProps) => {
  const { t } = useGridTranslations();
  const showBulkActions = selectedKeys.size > 0 && bulkActions.length > 0;

  return (
    <div
      className={`flex flex-wrap items-center justify-between gap-4 py-2 ${className}`}
    >
      {title ? (
        <h2 className="text-xl font-bold tracking-tight text-foreground">
          {title}
        </h2>
      ) : (
        <div />
      )}

      <div className="flex flex-wrap items-center gap-2">
        {showBulkActions && (
          <div className="flex items-center gap-2 pr-4 border-r border-divider mr-2">
            <span className="text-sm font-medium text-default-500">
              {t("selected_count")} {selectedKeys.size}
            </span>
            {bulkActions.map((action) => (
              <SweGridAction
                key={action.code}
                action={action}
                selectedKeys={selectedKeys}
                onSuccess={onActionSuccess}
              />
            ))}
          </div>
        )}

        {actions.map((action) => (
          <SweGridAction
            key={action.code}
            action={action}
            selectedKeys={selectedKeys}
            onSuccess={onActionSuccess}
          />
        ))}
      </div>
    </div>
  );
}

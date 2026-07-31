/**
 * Component GridPagination cho hệ thống Dynamic Grid (HeroUI v3.2.1).
 * Hiển thị thông tin tổng số bản ghi và bộ điều khiển phân trang.
 *
 * @created_at 08/07/2026
 * @author txhoan
 */

"use client";

import { Pagination } from "@heroui/react";

import { useGridTranslations } from "@/hooks/useGridTranslations";

export interface GridPaginationProps {
  /** Trang hiện tại (1-indexed) */
  page: number;
  /** Tổng số bản ghi */
  total: number;
  /** Số lượng bản ghi hiển thị trên trang hiện tại */
  currentCount: number;
  /** Tổng số trang */
  totalPages?: number;
  /** Số lượng bản ghi trên một trang (mặc định 10, dùng tính totalPages nếu không truyền totalPages) */
  pageSize?: number;
  /** Sự kiện khi thay đổi trang */
  onPageChange: (page: number) => void;
  /** Class CSS tùy chỉnh */
  className?: string;
}

export const SweGridPagination = ({
  page,
  total,
  currentCount,
  totalPages: propTotalPages,
  pageSize = 10,
  onPageChange,
  className = "",
}: GridPaginationProps) => {
  const { t } = useGridTranslations();
  const totalPages = propTotalPages ?? Math.max(1, Math.ceil(total / pageSize));

  return (
    <div
      className={`flex w-full items-center justify-between px-2 py-2 ${className}`.trim()}
      data-testid="swe-grid-pagination"
    >
      <div className="text-sm text-default-500" data-testid="swe-pagination-info">
        {t("showing")} {currentCount} {t("in_total")} {total} {t("records")}
      </div>
      {totalPages > 1 && (
        <Pagination>
          <Pagination.Content>
            <Pagination.Item>
              <Pagination.Previous
                isDisabled={page <= 1}
                onPress={() => onPageChange(page - 1)}
                data-testid="swe-pagination-prev-btn"
              >
                <span>{t("previous_page")}</span>
              </Pagination.Previous>
            </Pagination.Item>
            {Array.from({ length: totalPages }, (_, i) => i + 1).map((p) => (
              <Pagination.Item key={p}>
                <Pagination.Link
                  isActive={p === page}
                  onPress={() => onPageChange(p)}
                  data-testid={`swe-pagination-page-${p}`}
                >
                  {p}
                </Pagination.Link>
              </Pagination.Item>
            ))}
            <Pagination.Item>
              <Pagination.Next
                isDisabled={page >= totalPages}
                onPress={() => onPageChange(page + 1)}
                data-testid="swe-pagination-next-btn"
              >
                <span>{t("next_page")}</span>
              </Pagination.Next>
            </Pagination.Item>
          </Pagination.Content>
        </Pagination>
      )}
    </div>
  );
}

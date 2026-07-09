/**
 * Nitro API handler mô phỏng endpoint lấy cấu hình Grid theo mã gridCode.
 *
 * @created_at 08/07/2026
 * @author txhoan
 */

import { defineEventHandler, getRouterParam, createError } from 'h3'
import type { GridConfigResponse } from '../../../../../app/types/grid'

export default defineEventHandler((event): { data: GridConfigResponse } => {
  const gridCode = getRouterParam(event, 'gridCode')?.toUpperCase() || 'USERS'

  if (gridCode !== 'USERS' && gridCode !== 'ORDERS') {
    throw createError({
      statusCode: 404,
      statusMessage: `Không tìm thấy cấu hình Grid cho mã ${gridCode}`
    })
  }

  const config: GridConfigResponse = {
    gridCode,
    title: gridCode === 'USERS' ? 'Quản lý Người dùng' : 'Danh sách Đơn hàng',
    layoutOptions: {
      rowSelection: true,
      pagination: true,
      defaultPageSize: 10,
      pageSizeOptions: [10, 20, 50],
      stickyHeader: true,
      showIndex: true
    },
    columns: [
      {
        field: 'code',
        header: 'Mã số',
        type: 'LINK',
        sortable: true,
        hidden: false,
        actionConfig: {
          type: 'ROUTE',
          target: '/test-grid/{id}'
        }
      },
      {
        field: 'name',
        header: 'Họ và tên',
        type: 'TEXT',
        sortable: true,
        hidden: false
      },
      {
        field: 'status',
        header: 'Trạng thái',
        type: 'BADGE',
        sortable: true,
        hidden: false,
        valueMap: {
          ACTIVE: { label: 'Hoạt động', color: 'green' },
          INACTIVE: { label: 'Tạm khóa', color: 'gray' },
          PENDING: { label: 'Chờ duyệt', color: 'yellow' }
        }
      },
      {
        field: 'createdAt',
        header: 'Ngày tạo',
        type: 'DATE',
        sortable: true,
        hidden: false
      }
    ],
    filters: [
      {
        field: 'name',
        label: 'Tên người dùng',
        type: 'TEXT_INPUT',
        placeholder: 'Nhập tên cần tìm...'
      },
      {
        field: 'status',
        label: 'Trạng thái',
        type: 'SELECT',
        placeholder: 'Tất cả trạng thái',
        options: [
          { label: 'Hoạt động', value: 'ACTIVE' },
          { label: 'Tạm khóa', value: 'INACTIVE' },
          { label: 'Chờ duyệt', value: 'PENDING' }
        ]
      }
    ],
    actions: [
      {
        code: 'ADD_NEW',
        label: 'Thêm mới',
        icon: 'i-heroicons-plus',
        position: 'TOOLBAR',
        buttonType: 'primary',
        actionConfig: {
          type: 'ROUTE',
          target: '/test-grid/new'
        }
      },
      {
        code: 'DELETE_BULK',
        label: 'Xóa đã chọn',
        icon: 'i-heroicons-trash',
        position: 'BULK',
        buttonType: 'error',
        actionConfig: {
          type: 'API',
          method: 'POST',
          endpoint: `/api/v1/grid-data/${gridCode}/delete-bulk`,
          confirmMessage: 'Bạn có chắc chắn muốn xóa các bản ghi đang được chọn không?'
        }
      }
    ]
  }

  return { data: config }
})

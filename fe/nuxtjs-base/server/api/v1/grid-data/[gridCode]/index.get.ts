/**
 * Nitro API handler mô phỏng endpoint lấy danh sách dữ liệu Grid kèm tìm kiếm và phân trang.
 *
 * @created_at 08/07/2026
 * @author txhoan
 */

import { defineEventHandler, getQuery } from 'h3'
import type { GridDataResponse, GridDataItem } from '../../../../../app/types/grid'

const mockUsers: GridDataItem[] = [
  { id: 1, code: 'USR-001', name: 'Nguyễn Văn An', status: 'ACTIVE', createdAt: '2026-07-01' },
  { id: 2, code: 'USR-002', name: 'Trần Thị Bình', status: 'ACTIVE', createdAt: '2026-07-02' },
  { id: 3, code: 'USR-003', name: 'Lê Hoàng Cường', status: 'INACTIVE', createdAt: '2026-07-03' },
  { id: 4, code: 'USR-004', name: 'Phạm Đức Duy', status: 'PENDING', createdAt: '2026-07-04' },
  { id: 5, code: 'USR-005', name: 'Hoàng Mai Phương', status: 'ACTIVE', createdAt: '2026-07-05' },
  { id: 6, code: 'USR-006', name: 'Vũ Quốc Khánh', status: 'ACTIVE', createdAt: '2026-07-06' },
  { id: 7, code: 'USR-007', name: 'Đặng Thùy Linh', status: 'PENDING', createdAt: '2026-07-07' },
  { id: 8, code: 'USR-008', name: 'Bùi Tuấn Kiệt', status: 'INACTIVE', createdAt: '2026-07-08' }
]

export default defineEventHandler((event): { data: GridDataResponse } => {
  const query = getQuery(event)
  const nameFilter = String(query.name || '').trim().toLowerCase()
  const statusFilter = String(query.status || '').trim()

  let items = [...mockUsers]

  if (nameFilter) {
    items = items.filter(item => String(item.name).toLowerCase().includes(nameFilter))
  }

  if (statusFilter) {
    items = items.filter(item => String(item.status) === statusFilter)
  }

  const page = Math.max(1, Number(query.page || 1))
  const pageSize = 10
  const startIndex = (page - 1) * pageSize
  const paginatedItems = items.slice(startIndex, startIndex + pageSize)

  return {
    data: {
      items: paginatedItems,
      total: items.length
    }
  }
})

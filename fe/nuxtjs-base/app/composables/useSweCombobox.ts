/**
 * Chế độ hiển thị của Combobox
 */
export type ComboboxMode = 'view' | 'edit'

/**
 * Các loại kiểu dữ liệu hỗ trợ bởi Combobox
 */
export type ComboboxDataType = 'text' | 'date' | 'number' | 'tag' | 'human'

/**
 * Cấu trúc đối tượng cho kiểu dữ liệu human
 */
export interface HumanItem {
  id: string | number
  name: string
  avatarUrl?: string
  description?: string
}

/**
 * Cấu trúc một Option trong Combobox
 */
export interface ComboboxOption {
  value: string | number
  label: string
  description?: string
  avatarUrl?: string
  tagColor?: 'neutral' | 'primary' | 'secondary' | 'success' | 'warning' | 'error'
  raw?: any
}

/**
 * Composable `useSweCombobox` cung cấp các hàm hỗ trợ định dạng và xử lý logic cho SweCombobox.
 *
 * @created_at 28/07/2026
 * @author txhoan
 */
export function useSweCombobox() {
  /**
   * Định dạng chuỗi ngày tháng sang dạng vi-VN (DD/MM/YYYY)
   */
  const formatDate = (val: string | number | Date | null | undefined): string => {
    if (!val) return ''
    try {
      const d = new Date(val)
      if (!isNaN(d.getTime())) {
        return d.toLocaleDateString('vi-VN', {
          year: 'numeric',
          month: '2-digit',
          day: '2-digit'
        })
      }
    } catch {
      // Fallback giữ nguyên
    }
    return String(val)
  }

  /**
   * Định dạng số sang dạng hiển thị có dấu phân cách hàng nghìn vi-VN
   */
  const formatNumber = (val: string | number | null | undefined): string => {
    if (val === null || val === undefined || val === '') return ''
    const num = Number(val)
    return !isNaN(num) ? num.toLocaleString('vi-VN') : String(val)
  }

  /**
   * Tìm option tương ứng với giá trị truyền vào
   */
  const findSelectedOption = (
    val: string | number | null | undefined,
    options: ComboboxOption[] = []
  ): ComboboxOption | undefined => {
    if (val === null || val === undefined) return undefined
    return options.find(opt => String(opt.value) === String(val))
  }

  /**
   * Lấy chữ cái đầu đại diện cho Avatar khi không có ảnh
   */
  const getInitialLetter = (name?: string): string => {
    if (!name) return '?'
    return name.trim().charAt(0).toUpperCase()
  }

  return {
    formatDate,
    formatNumber,
    findSelectedOption,
    getInitialLetter
  }
}

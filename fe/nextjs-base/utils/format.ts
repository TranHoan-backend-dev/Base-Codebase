/**
 * Tiện ích format hiển thị dữ liệu tiền tệ, ngày tháng thân thiện với người dùng Việt Nam.
 *
 * @created_at 01/08/2026
 * @author txhoan
 */

/**
 * Định dạng tiền tệ VND (Ví dụ: 1000000 -> "1,000,000 VND")
 */
export const formatVND = (value: number | string): string => {
  const number = typeof value === "string" ? Number(value.replace(/[^\d]/g, "")) : value;
  if (isNaN(number)) return "0 VND";
  return `${number.toLocaleString("en-US")} VND`;
};

/**
 * Định dạng ngày giờ chuẩn ISO sang định dạng DD/MM/YYYY
 */
export const formatToDDMMYYYY = (dateString: string): string => {
  if (!dateString) return "";
  try {
    const date = new Date(dateString);
    if (isNaN(date.getTime())) return dateString;
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
  } catch {
    return dateString;
  }
};

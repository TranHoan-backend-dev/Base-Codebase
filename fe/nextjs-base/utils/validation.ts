import { useTranslations } from "next-intl";

/**
 * Hệ thống validation an toàn thông tin đầu vào (Defense in Depth).
 * Hỗ trợ đa ngôn ngữ bằng cách trả về một hàm dịch (t) hoặc sử dụng default key/params.
 *
 * @created_at 01/08/2026
 * @author txhoan
 */

export const validateRequired = (
  value: string,
  fieldName: string,
  t?: (key: string, values?: any) => string
): string | null => {
  if (!value || !value.trim()) {
    return t 
      ? t("validation.required", { fieldName })
      : `${fieldName} không được để trống`;
  }
  return null;
};

export const validateMaxLength = (
  value: string,
  max: number,
  fieldName: string,
  t?: (key: string, values?: any) => string
): string | null => {
  if (value && value.trim().length > max) {
    return t
      ? t("validation.maxLength", { fieldName, max })
      : `${fieldName} không được vượt quá ${max} ký tự`;
  }
  return null;
};

export const validatePhone = (
  phone: string,
  t?: (key: string) => string
): string | null => {
  const phoneRegex = /^0[0-9]{9}$/;
  if (!phoneRegex.test(phone)) {
    return t
      ? t("validation.phone")
      : "Số điện thoại phải bắt đầu bằng 0 và gồm đúng 10 chữ số";
  }
  return null;
};

export const validateDigitsOnly = (
  value: string | number,
  fieldName: string,
  t?: (key: string, values?: any) => string
): string | null => {
  const normalized = String(value ?? "").trim();
  if (!normalized) {
    return validateRequired(normalized, fieldName, t);
  }
  if (!/^\d+$/.test(normalized)) {
    return t
      ? t("validation.digitsOnly", { fieldName })
      : `${fieldName} chỉ được chứa ký tự số`;
  }
  return null;
};

export const normalizeAddress = (value: string): string => {
  return value.replace(/[^a-zA-ZÀ-ỹ0-9\s,.\-/()]/g, "");
};

export const toAccountName = (name: string): string => {
  return name
    .normalize("NFD")
    .replace(/[\u0300-\u036f]/g, "")
    .replace(/đ/g, "d")
    .replace(/Đ/g, "D")
    .toUpperCase();
};

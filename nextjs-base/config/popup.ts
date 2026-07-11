/**
 * Cấu hình token dùng chung cho hệ thống Popup (BasePopup, BasePopupSidebar, v.v.)
 *
 * @created_at 10/07/2026
 * @author txhoan
 */

import type { CSSProperties } from "react";

import {
  gap_0,
  gap_1,
  gap_2,
  larger_space,
  medium_space_1,
  normal_space_1,
} from "@/types/space";
import {
  base_font_size,
  base_font_title,
  base_font_weight,
} from "@/types/font";
import { base_border, border_radius_8 } from "@/types/style";

/** Các token layout (khoảng cách và padding giữa các thành phần trong Popup) */
export const POPUP_LAYOUT_TOKENS = {
  headerGap: gap_2,
  actionGap: gap_0,
  footerGap: gap_1,
  headerPadding: `${larger_space} ${larger_space} 0 ${larger_space}`,
  bodyPadding: `0 ${larger_space} ${medium_space_1} ${larger_space}`,
  footerPadding: ` ${normal_space_1} ${larger_space} ${normal_space_1} ${medium_space_1}`,
  footerMarginTop: normal_space_1,
} as const;

/** Các token font & màu cho text trong Popup */
export const POPUP_TYPOGRAPHY_TOKENS = {
  title: {
    fontSize: base_font_title,
    fontWeight: base_font_weight,
    margin: 0,
  } satisfies CSSProperties,
  body: {
    fontSize: base_font_size,
    fontWeight: base_font_weight,
  } satisfies CSSProperties,
  description: {
    fontSize: base_font_size,
    fontWeight: base_font_weight,
    opacity: 0.7,
    marginTop: "2px",
  } satisfies CSSProperties,
};

/** Các inline style dùng chung cho BasePopup và BasePopupSidebar để tái sử dụng */
export const POPUP_SHARED_STYLES = {
  dialogReset: {
    padding: 0,
  } satisfies CSSProperties,
  popupDialogReset: {
    padding: 0,
    borderRadius: border_radius_8,
  } satisfies CSSProperties,
  sidebarDialogReset: {
    padding: 0,
    borderRadius: 0,
  } satisfies CSSProperties,
  headerCompound: {
    padding: POPUP_LAYOUT_TOKENS.headerPadding,
  } satisfies CSSProperties,
  headerFlat: {
    padding: POPUP_LAYOUT_TOKENS.headerPadding,
    display: "flex",
    flexDirection: "row",
    flexWrap: "nowrap",
    alignItems: "center",
    justifyContent: "space-between",
    gap: POPUP_LAYOUT_TOKENS.headerGap,
    width: "100%",
  } satisfies CSSProperties,
  headerContent: {
    flex: "1 1 auto",
    minWidth: 0,
  } satisfies CSSProperties,
  headerActions: {
    display: "flex",
    alignItems: "center",
    gap: POPUP_LAYOUT_TOKENS.actionGap,
  } satisfies CSSProperties,
  closeTrigger: {
    position: "static",
    margin: 0,
    display: "inline-flex",
    alignItems: "center",
    justifyContent: "center",
    flexShrink: 0,
  } satisfies CSSProperties,
  bodyCompound: {
    padding: POPUP_LAYOUT_TOKENS.bodyPadding,
  } satisfies CSSProperties,
  bodyFlat: {
    padding: POPUP_LAYOUT_TOKENS.bodyPadding,
    overflowY: "auto",
    flex: "1 1 auto",
  } satisfies CSSProperties,
  footer: {
    padding: POPUP_LAYOUT_TOKENS.footerPadding,
    marginTop: POPUP_LAYOUT_TOKENS.footerMarginTop,
    display: "flex",
    justifyContent: "flex-end",
    gap: POPUP_LAYOUT_TOKENS.footerGap,
    borderTop: base_border,
  } satisfies CSSProperties,
};

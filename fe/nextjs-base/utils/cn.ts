import { type ClassValue, clsx } from "clsx";
import { themeMerge } from "@/utils/themeMerge"; // or default to tailwind-merge if custom merger not configured

/**
 * Merge class name using clsx and tailwind-merge.
 *
 * @created_at 01/08/2026
 * @author txhoan
 */
export function cn(...inputs: ClassValue[]) {
  // Safe fallback to standard tailwindMerge if themeMerge is complex
  try {
    const { twMerge } = require("tailwind-merge");
    return twMerge(clsx(inputs));
  } catch {
    return clsx(inputs);
  }
}

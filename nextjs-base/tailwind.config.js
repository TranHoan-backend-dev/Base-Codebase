/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
    "./app/**/*.{js,ts,jsx,tsx,mdx}"
  ],
  theme: {
    extend: {
      fontFamily: {
        sans: ["var(--font-sans)"],
        mono: ["var(--font-mono)"],
      },
      colors: {
        "grid-bg": "var(--color-grid-bg)",
        "grid-text": "var(--color-grid-text)",
        "grid-link": "var(--color-grid-link)",
      }
    },
  },
  darkMode: "class"
};

/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      // Adicione esta seção fontFamily
      fontFamily: {
        // Ao sobrescrever 'sans', o Tailwind aplica essa fonte em TUDO por padrão
        sans: ['"MedievalSharp"', 'cursive', 'sans-serif'],
      },
    },
  },
  plugins: [],
}
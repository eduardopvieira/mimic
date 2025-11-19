/** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/**/*.{html,js}"],
  theme: {
    extend: {
      colors: {
        'fundo-principal': '#1A1A1A',
        'header-cinza': '#444444',
        'card-form': '#2D2D2D',
      },
      fontFamily: {
        sans: ['MedievalSharp', 'serif'],
      }
    },
  },
  plugins: [],
}
import { Routes, Route, Navigate } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import CreateCharacter from './pages/CreateCharacter';
import ManageSpells from './pages/ManageSpells';

function App() {
  return (
    <Routes>
      <Route path="/home-page" element={<Dashboard />} />

      <Route path="/formulario-personagem" element={<CreateCharacter />} />

      <Route path="/" element={<Navigate to="/login" replace />} />

      <Route path="/gerenciar-magias" element={<ManageSpells />} />
    </Routes>
  );
}

export default App;
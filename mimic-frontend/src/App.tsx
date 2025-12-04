import { Routes, Route, Navigate } from 'react-router-dom';
import CreateCharacter from './pages/CreateCharacter';
import ManageSpells from './pages/ManageSpells';
import CreateSpell from './pages/CreateSpell';
import ManageOrigins from './pages/ManageOrigins';
import CreateOrigin from './pages/CreateOrigin';
import ManageCharacters from './pages/ManageCharacters';
import ManageCreatures from './pages/ManageCreatures';
import CreateCreature from './pages/CreateCreature';
import Login from './pages/Login';
import Register from './pages/Register';

function App() {
  return (
    <Routes>

      <Route path="/cadastrar" element={<Register />} />

      <Route path="/login" element={<Login />} />

      <Route path="/" element={<Navigate to="/login" replace />} />

      
      <Route path="/gerenciar-personagens" element={<ManageCharacters />} />

      <Route path="/criar-personagem" element={<CreateCharacter />} />

      <Route path="/editar-personagem/:id" element={<CreateCharacter />} />


      <Route path="/gerenciar-criaturas" element={<ManageCreatures />} />

      <Route path="/criar-criatura" element={<CreateCreature />} />

      <Route path="/editar-criatura/:id" element={<CreateCreature />} />
      
      
      <Route path="/gerenciar-magias" element={<ManageSpells />} />

      <Route path="/criar-magia" element={<CreateSpell />} />

      <Route path="/editar-magia/:id" element={<CreateSpell />} />


      <Route path="/gerenciar-origens" element={<ManageOrigins />} />

      <Route path="/criar-origem" element={<CreateOrigin />} />

      <Route path="/editar-origem/:id" element={<CreateOrigin />} />




    </Routes>
  );
}

export default App;
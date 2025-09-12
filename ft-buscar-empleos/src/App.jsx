import React from 'react';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from 'react-router-dom';

import './App.css';

import LoginForm from './pages/LoginForm';
import RegistrarUsuariosForm from './pages/RegistrarUsuariosForm';
import BuscarOfertasForm from './pages/BuscarOfertasForm';

import LayoutConNav from './components/LayoutConNav';
import RutaProtegida from './components/RutaProtegida';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />

        <Route path="/login" element={<LoginForm />} />
        <Route path="/registrar" element={<RegistrarUsuariosForm />} />

        <Route element={<RutaProtegida />}>
          <Route element={<LayoutConNav />}>
            <Route path="/buscar" element={<BuscarOfertasForm />} />
          </Route>
        </Route>
      </Routes>
    </Router>
  );
}

export default App;

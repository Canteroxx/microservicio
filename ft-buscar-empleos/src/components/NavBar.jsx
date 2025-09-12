import React from 'react';
import { useNavigate } from 'react-router-dom';

const NavBar = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('jwt');
    navigate('/login');
  };

  return (
    <nav
      style={{
        padding: '10px',
        background: '#f4f4f4',
        display: 'flex',
        justifyContent: 'flex-end',
      }}
    >
      <button onClick={handleLogout}>Cerrar Sesión</button>
    </nav>
  );
};

export default NavBar;

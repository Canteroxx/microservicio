import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';

const RutaProtegida = () => {
  const token = localStorage.getItem('jwt');

  if (!token) {
    return <Navigate to="/login" />;
  }

  try {
    const decoded = jwtDecode(token);
    const now = Math.floor(Date.now() / 1000);
    if (decoded.exp && decoded.exp < now) {
      localStorage.removeItem('jwt');
      return <Navigate to="/login" />;
    }
  } catch (err) {
    localStorage.removeItem('jwt');
    return <Navigate to="/login" />;
  }

  return <Outlet />;
};

export default RutaProtegida;

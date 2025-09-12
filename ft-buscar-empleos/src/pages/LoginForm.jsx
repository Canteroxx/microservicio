import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { loginPostulante } from '../services/PostulantesService';

export default function LoginForm() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({ rut: '', password: '' });
  const [mensaje, setMensaje] = useState('');
  const [cargando, setCargando] = useState(false);

  const handleChange = (e) =>
    setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMensaje('');
    try {
      setCargando(true);
      const token = await loginPostulante(formData);
      localStorage.setItem('jwt', token.token);
      setMensaje('Login exitoso');
      navigate('/buscar');
    } catch (err) {
      setMensaje(`Error: ${err.message}`);
    } finally {
      setCargando(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 px-4">
      <div className="w-full max-w-md bg-white rounded-2xl shadow p-6 border border-gray-100">
        <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">
          Iniciar Sesión
        </h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <input
              type="text"
              name="rut"
              placeholder="RUT"
              value={formData.rut}
              onChange={handleChange}
              required
              className="w-full h-11 rounded-lg border border-gray-300 px-3 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>
          <div>
            <input
              type="password"
              name="password"
              placeholder="Contraseña"
              value={formData.password}
              onChange={handleChange}
              required
              className="w-full h-11 rounded-lg border border-gray-300 px-3 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>
          <button
            type="submit"
            disabled={cargando}
            className="w-full h-11 rounded-lg bg-blue-600 text-white font-semibold hover:bg-blue-700 transition disabled:opacity-70"
          >
            {cargando ? 'Ingresando...' : 'Ingresar'}
          </button>
        </form>
        <p className="mt-4 text-center text-sm text-gray-600">
          ¿No tienes cuenta?{' '}
          <Link
            to="/registrar"
            className="text-blue-600 font-medium hover:underline"
          >
            Regístrate aquí
          </Link>
        </p>
        {mensaje && (
          <p
            className={`mt-4 text-center text-sm font-medium ${
              mensaje.startsWith('Error')
                ? 'text-red-600'
                : 'text-green-600'
            }`}
          >
            {mensaje}
          </p>
        )}
      </div>
    </div>
  );
}

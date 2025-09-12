import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { registrarPostulante } from "../services/PostulantesService";

const RegistrarUsuariosForm = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    nombres: "",
    apellidos: "",
    rut: "",
    email: "",
    password: "",
  });

  const [mensaje, setMensaje] = useState("");
  const [cargando, setCargando] = useState(false);

  const handleChange = (e) =>
    setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMensaje("");
    try {
      setCargando(true);
      const data = await registrarPostulante(formData);
      setMensaje(`Usuario registrado con el RUT: ${data.rut}`);
      setFormData({
        nombres: "",
        apellidos: "",
        rut: "",
        email: "",
        password: "",
      });
      setTimeout(() => navigate("/login"), 1500); // redirige después de 1.5s
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
          Registro de Usuario
        </h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="text"
            name="nombres"
            placeholder="Nombres"
            value={formData.nombres}
            onChange={handleChange}
            required
            className="w-full h-11 rounded-lg border border-gray-300 px-3 focus:outline-none focus:ring-2 focus:ring-blue-600"
          />
          <input
            type="text"
            name="apellidos"
            placeholder="Apellidos"
            value={formData.apellidos}
            onChange={handleChange}
            required
            className="w-full h-11 rounded-lg border border-gray-300 px-3 focus:outline-none focus:ring-2 focus:ring-blue-600"
          />
          <input
            type="text"
            name="rut"
            placeholder="RUT"
            value={formData.rut}
            onChange={handleChange}
            required
            className="w-full h-11 rounded-lg border border-gray-300 px-3 focus:outline-none focus:ring-2 focus:ring-blue-600"
          />
          <input
            type="email"
            name="email"
            placeholder="Correo electrónico"
            value={formData.email}
            onChange={handleChange}
            required
            className="w-full h-11 rounded-lg border border-gray-300 px-3 focus:outline-none focus:ring-2 focus:ring-blue-600"
          />
          <input
            type="password"
            name="password"
            placeholder="Contraseña"
            value={formData.password}
            onChange={handleChange}
            required
            className="w-full h-11 rounded-lg border border-gray-300 px-3 focus:outline-none focus:ring-2 focus:ring-blue-600"
          />
          <button
            type="submit"
            disabled={cargando}
            className="w-full h-11 rounded-lg bg-blue-600 text-white font-semibold hover:bg-blue-700 transition disabled:opacity-70"
          >
            {cargando ? "Registrando..." : "Registrar"}
          </button>
        </form>
        <p className="mt-4 text-center text-sm text-gray-600">
          ¿Ya estás registrado?{" "}
          <Link
            to="/login"
            className="text-blue-600 font-medium hover:underline"
          >
            Inicia sesión aquí
          </Link>
        </p>
        {mensaje && (
          <p
            className={`mt-4 text-center text-sm font-medium ${
              mensaje.startsWith("Error") ? "text-red-600" : "text-green-600"
            }`}
          >
            {mensaje}
          </p>
        )}
      </div>
    </div>
  );
};

export default RegistrarUsuariosForm;

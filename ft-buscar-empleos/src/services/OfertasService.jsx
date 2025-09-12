import axios from 'axios';

const API_URL = 'http://localhost:8080/ofertas';

export const buscarOfertas = async (filtros) => {
  const filtrosNormalizados = {
    palabraClave: filtros.palabraClave || null,
    fechaPublicacion: filtros.fechaPublicacion || null,
    regiones: filtros.region.length > 0 ? filtros.region : null,
    comunas: filtros.comuna.length > 0 ? filtros.comuna : null,
    ocupaciones: filtros.ocupacion.length > 0 ? filtros.ocupacion : null,
    gruposEmpleo: filtros.grupoEmpleo.length > 0 ? filtros.grupoEmpleo : null,
    nivelesEducativos:
      filtros.nivelEducativo.length > 0 ? filtros.nivelEducativo : null,
    jornadasLaborales:
      filtros.jornadaLaboral.length > 0 ? filtros.jornadaLaboral : null,
    tiposContrato:
      filtros.tipoContrato.length > 0 ? filtros.tipoContrato : null,
    origenesOferta:
      filtros.origenOferta.length > 0 ? filtros.origenOferta : null,
    discapacidad: filtros.discapacidad ? true : null,
  };

  const token = localStorage.getItem('jwt');
  const headers = token
    ? { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }
    : { 'Content-Type': 'application/json' };

  const response = await axios.post(`${API_URL}/buscar`, filtrosNormalizados, {
    headers,
  });
  return response.data;
};

export const obtenerCatalogos = async () => {
  const token = localStorage.getItem('jwt');
  const headers = token ? { Authorization: `Bearer ${token}` } : {};
  const response = await axios.get(`${API_URL}/catalogos`, { headers });
  return response.data;
};

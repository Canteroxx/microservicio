import axios from "axios";

const BASE_URL = "http://localhost:8080/postulantes";

export const registrarPostulante = async (postulante) => {
  try {
    const response = await axios.post(`${BASE_URL}/registrar`, postulante);
    console.log(response);
    return response.data;
  } catch (error) {
    // Axios envía la respuesta del servidor en error.response
    if (error.response) {
      throw new Error(error.response.data || "Error en la solicitud");
    } else {
      throw new Error(error.message);
    }
  }
};

export const loginPostulante = async ({ rut, password }) => {
  try {
    // Como el backend usa @RequestParam, enviamos la info en la URL
    const params = { rut, password };
    const response = await axios.post(`${BASE_URL}/login`, params);
    console.log(response)
    return response.data; // JWT
  } catch (error) {
    if (error.response) {
      throw new Error(error.response.data || "Credenciales incorrectas");
    } else {
      throw new Error(error.message);
    }
  }
};
import axios from 'axios';
import { MagiaDTO } from '../types/Magia';

const API_URL = 'http://localhost:8080/api/magias';

export const MagiaService = {
  // O retorno é uma Promise que contém o MagiaDTO criado
  create: async (magia: MagiaDTO): Promise<MagiaDTO> => {
    const response = await axios.post<MagiaDTO>(API_URL, magia);
    return response.data;
  },

  getAll: async (): Promise<MagiaDTO[]> => {
    const response = await axios.get<MagiaDTO[]>(API_URL);
    return response.data;
  },

  getById: async (id: number): Promise<MagiaDTO> => {
    const response = await axios.get<MagiaDTO>(`${API_URL}/${id}`);
    return response.data;
  },
  
  delete: async (id: number): Promise<void> => {
    await axios.delete(`${API_URL}/${id}`);
  }
};
import { API_URL } from '@env';
import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: API_URL
});

export default axiosInstance;
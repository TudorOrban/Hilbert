const API_URL = 'http://localhost:8080/api/v1/auth';

const UserService = {
    login: async (username: string, password: string) => {
        try {
            const response = await axios.post(`${API_URL}/login`, {
                username, password
            });
        return response.data;
        } catch (error) {
            throw error;
        }
    }


    






}
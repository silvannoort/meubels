export default class LoginService {
    constructor() {
        console.log("LoginService instantiated");
    }

    isLoggedIn() {
        const token = localStorage.getItem('token');
        console.log("Token in localStorage:", token);
        return !!token;
    }

    async login(user, password) {
        console.log("Attempting login with user:", user);
        const response = await fetch('/restservices/authentication', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username: user, password: password })
        });

        if (response.ok) {
            const data = await response.json();
            console.log("Received token:", data.JWT);
            localStorage.setItem('token', data.JWT);
        } else {
            throw new Error('Login failed');
        }
    }

    async getUser() {
        const token = localStorage.getItem('token');
        if (!token) {
            console.log("No token found in localStorage");
            return null;
        }

        const response = await fetch('/restservices/authentication/validate', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            console.log("User validated successfully");
            const data = await response.json();
            console.log("User data from server:", data);
            return data;
        } else {
            console.log("User validation failed");
            this.logout();
            return null;
        }
    }

    logout() {
        localStorage.removeItem('token');
        console.log("Logged out, token removed from localStorage");
    }
}

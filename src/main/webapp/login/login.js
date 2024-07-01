import LoginService from './login-service.js';

const loginService = new LoginService();

document.forms.login.addEventListener('submit', async (e) => {
    e.preventDefault();
    const username = e.target.username.value;
    const password = e.target.password.value;
    console.log("Attempting login with username:", username, "and password:", password);
    try {
        await loginService.login(username, password);
        console.log("Login successful");
        alert('Login successful!');
        window.location.href = 'index.html';
    } catch (error) {
        console.error("Login failed:", error);
        alert('Login failed: ' + error.message);
    }
});

document.addEventListener('DOMContentLoaded', async () => {
    console.log("DOMContentLoaded event fired");
    const userSpan = document.getElementById('user');
    const loggedIn = loginService.isLoggedIn();
    console.log("Is user logged in?", loggedIn);
    if (loggedIn) {
        const user = await loginService.getUser();
        if (user) {
            console.log("User info:", user);
            userSpan.textContent = user.username;
            document.forms.login.style.display = 'none';
            document.forms.logout.style.display = 'block';
        } else {
            console.log("No valid user found");
            document.forms.login.style.display = 'block';
            document.forms.logout.style.display = 'none';
        }
    } else {
        console.log("User is not logged in");
        document.forms.login.style.display = 'block';
        document.forms.logout.style.display = 'none';
    }
});

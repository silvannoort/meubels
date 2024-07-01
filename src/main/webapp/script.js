import LoginService from './login/login-service.js';

const loginService = new LoginService();

document.addEventListener('DOMContentLoaded', async () => {
    document.forms.loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const username = e.target.username.value;
        const password = e.target.password.value;
        console.log("Attempting login with username:", username, "and password:", password);
        try {
            await loginService.login(username, password);
            console.log("Login successful");
            alert('Login successful!');
            window.location.reload();
        } catch (error) {
            console.error("Login failed:", error);
            alert('Login failed: ' + error.message);
        }
    });

    document.forms.logoutForm.addEventListener('submit', (e) => {
        e.preventDefault();
        loginService.logout();
        console.log("Logout successful");
        alert('Logged out!');
        window.location.reload();
    });

    console.log("DOMContentLoaded event fired");
    const userSpan = document.getElementById('user');
    const profileLink = document.getElementById('profileLink');
    const registerLink = document.getElementById('registerLink');
    const reactieLink = document.getElementById('reactieLink');
    const reviewLink = document.getElementById('reviewLink');
    const adminLink = document.getElementById('adminLink'); // Get the admin link
    const searchSection = document.getElementById('searchSection');
    const loginHeader = document.getElementById('loginHeader');

    const loggedIn = loginService.isLoggedIn();
    console.log("Is user logged in?", loggedIn);
    if (loggedIn) {
        const user = await loginService.getUser();
        if (user) {
            console.log("User info:", user);
            userSpan.textContent = `Ingelogd als: ${user.username}`;
            document.forms.loginForm.style.display = 'none';
            document.forms.logoutForm.style.display = 'block';
            loginHeader.textContent = 'Uitloggen';
            profileLink.style.display = 'inline';
            registerLink.style.display = 'none';
            reactieLink.style.display = 'inline';
            reviewLink.style.display = 'inline';
            searchSection.style.display = 'block';

            if (user.role === 'admin') {
                adminLink.style.display = 'inline';
            } else {
                adminLink.style.display = 'none';
            }
        } else {
            console.log("No valid user found");
            document.forms.loginForm.style.display = 'block';
            document.forms.logoutForm.style.display = 'none';
            loginHeader.textContent = 'Inloggen';
            profileLink.style.display = 'none';
            registerLink.style.display = 'inline';
            reactieLink.style.display = 'none';
            reviewLink.style.display = 'none';
            searchSection.style.display = 'none';
            adminLink.style.display = 'none'; // Hide the admin link
        }
    } else {
        console.log("User is not logged in");
        document.forms.loginForm.style.display = 'block';
        document.forms.logoutForm.style.display = 'none';
        loginHeader.textContent = 'Inloggen';
        profileLink.style.display = 'none';
        registerLink.style.display = 'inline';
        reactieLink.style.display = 'none';
        reviewLink.style.display = 'none';
        searchSection.style.display = 'none';
        adminLink.style.display = 'none'; // Hide the admin link
    }

    document.forms.searchForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const searchTerm = e.target.searchTerm.value;
        try {
            const response = await fetch(`/restservices/reviews/search?productName=${searchTerm}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            if (response.ok) {
                const reviews = await response.json();
                displaySearchResults(reviews);
            } else {
                console.error('Search failed:', response.status);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    });

    function displaySearchResults(reviews) {
        const resultsDiv = document.getElementById('searchResults');
        resultsDiv.innerHTML = '';
        if (reviews.length === 0) {
            resultsDiv.textContent = 'Geen reviews gevonden.';
        } else {
            reviews.forEach(review => {
                const reviewDiv = document.createElement('div');
                reviewDiv.classList.add('review');
                reviewDiv.innerHTML = `
                    <h3>${review.productName}</h3>
                    <p>Rating: ${review.rating}</p>
                    <p>${review.reviewText}</p>
                    <p><small>Geplaatst op: ${new Date(review.createdDate).toLocaleDateString()}</small></p>
                    <button onclick="window.location.href='reactie/reactie.html?reviewId=${review.id}'">Bekijk en Plaats Reactie</button>
                `;
                resultsDiv.appendChild(reviewDiv);
            });
        }
    }
});

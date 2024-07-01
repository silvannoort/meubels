import LoginService from '../login/login-service.js';

const loginService = new LoginService();

document.addEventListener('DOMContentLoaded', async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const reviewId = urlParams.get('reviewId');

    if (!reviewId) {
        alert('Geen review ID gevonden.');
        return;
    }

    const reviewContainer = document.getElementById('reviewDetails');
    const reactieForm = document.getElementById('reactieForm');


    const arrayToDateString = (dateArray) => {
        const [year, month, day, hour, minute, second, nanosecond] = dateArray;
        return new Date(Date.UTC(year, month - 1, day, hour, minute, second, nanosecond / 1000000)).toISOString();
    };


    const fetchReview = async () => {
        try {
            const response = await fetch(`/restservices/reviews/${reviewId}`);
            if (response.ok) {
                const review = await response.json();
                displayReview(review);
            } else {
                console.error('Fout bij ophalen review:', response.status);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };


    const fetchUsers = async () => {
        try {
            const response = await fetch('/restservices/users');
            if (response.ok) {
                const users = await response.json();
                const userMap = new Map();
                users.forEach(user => {
                    userMap.set(user.id, user.username);
                });
                return userMap;
            } else {
                console.error('Fout bij ophalen gebruikers:', response.status);
                return new Map();
            }
        } catch (error) {
            console.error('Error:', error);
            return new Map();
        }
    };

    const displayReview = (review) => {
        reviewContainer.innerHTML = `
            <h2>Review Details</h2>
            <p><strong>Productnaam:</strong> ${review.productName}</p>
            <p><strong>Beoordeling:</strong> ${review.rating}</p>
            <p><strong>Review:</strong> ${review.reviewText}</p>
            <p><strong>Geplaatst op:</strong> ${new Date(arrayToDateString(review.createdDate)).toLocaleString()}</p>
        `;
        displayReacties(review.reacties);
    };

    const displayReacties = async (reacties) => {
        const reactiesContainer = document.getElementById('reactiesList');
        reactiesContainer.innerHTML = '<h3>Reacties:</h3>';
        const userMap = await fetchUsers();

        reacties.forEach(reactie => {
            const reactieDiv = document.createElement('div');
            reactieDiv.classList.add('reactie');
            const username = userMap.get(reactie.userId) || 'Onbekende gebruiker';
            reactieDiv.innerHTML = `
                <p><strong>${username}</strong>: ${reactie.reactieText}</p>
                <p><small>Geplaatst op: ${new Date(arrayToDateString(reactie.createdDate)).toLocaleString()}</small></p>
            `;
            reactiesContainer.appendChild(reactieDiv);
        });
    };


    const user = await loginService.getUser();
    if (!user) {
        alert('Je moet ingelogd zijn om een reactie te plaatsen.');
        window.location.href = '../login/login.html';
        return;
    }


    reactieForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const reactieText = e.target.reactieText.value;
        const createdDate = new Date().toISOString();
        const userId = user.id;

        const reactieData = {
            reactieText,
            createdDate,
            userId,
            reviewId
        };

        const token = localStorage.getItem('token');

        try {
            const response = await fetch(`/restservices/reviews/${reviewId}/reacties`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(reactieData)
            });

            if (response.ok) {
                alert('Reactie geplaatst!');
                window.location.reload();
            } else {
                console.error('Reactie plaatsen mislukt:', response.status);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    });


    fetchReview();
});

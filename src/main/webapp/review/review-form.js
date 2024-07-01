import LoginService from '../login/login-service.js';

const loginService = new LoginService();

document.addEventListener('DOMContentLoaded', async () => {
    const user = await loginService.getUser();
    if (!user) {
        alert('Je moet ingelogd zijn om een review te plaatsen.');

        return;
    }
    console.log("Ingelogde gebruiker:", user);

    document.forms.reviewForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const productName = e.target.productName.value;
        const rating = e.target.rating.value;
        const reviewText = e.target.reviewText.value;
        const createdDate = new Date().toISOString();
        const userId = user.id;

        console.log("Gebruiker ID:", userId);

        const reviewData = {
            productName,
            rating,
            reviewText,
            createdDate,
            userId
        };

        const token = localStorage.getItem('token');

        try {
            const response = await fetch('/restservices/reviews', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(reviewData)
            });

            if (response.ok) {
                alert('Review geplaatst!');
                window.location.reload();
            } else {
                console.error('Review plaatsen mislukt:', response.status);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    });
});

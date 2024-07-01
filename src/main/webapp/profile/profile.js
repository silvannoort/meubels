import LoginService from '../login/login-service.js';

const loginService = new LoginService();

document.addEventListener('DOMContentLoaded', async () => {
    console.log("Profile page loaded");
    const user = await loginService.getUser();
    console.log("User data:", user);
    if (user && user.id) {
        document.getElementById('username').textContent = user.username;
        document.getElementById('email').textContent = user.email;
        const profilePicture = document.getElementById('profilePicture');
        console.log("Profile picture path:", user.profilePicture);
        if (user.profilePicture) {
            profilePicture.src = `../${user.profilePicture}`;
        } else {
            profilePicture.src = '../image/default-profile.png';
        }


        const reviewsList = document.getElementById('reviewsList');
        const reviewsResponse = await fetch(`/restservices/reviews/user/${user.id}`);
        console.log("Fetching reviews for user ID:", user.id);
        const reviews = await reviewsResponse.json();
        reviews.forEach(review => {
            const li = document.createElement('li');
            li.innerHTML = `
                ${review.productName}: ${review.reviewText} (${review.rating}/10)
                <button onclick="location.href='/reactie/reactie.html?reviewId=${review.id}'">Bekijk Review</button>
            `;
            reviewsList.appendChild(li);


            review.reacties.forEach(reactie => {
                if (reactie.userId === user.id) {
                    const reactieLi = document.createElement('li');
                    reactieLi.textContent = reactie.reactieText;
                    li.appendChild(reactieLi);
                }
            });
        });


        const reactionsList = document.getElementById('reactionsList');
        const reactiesResponse = await fetch(`/restservices/reacties/user/${user.id}`);
        console.log("Fetching reactions for user ID:", user.id);
        const reacties = await reactiesResponse.json();
        reacties.forEach(reactie => {
            const li = document.createElement('li');
            li.textContent = reactie.reactieText;
            reactionsList.appendChild(li);
        });
    } else {
        console.error("No user logged in or user ID not defined");
        window.location.href = '../index.html';
    }
});

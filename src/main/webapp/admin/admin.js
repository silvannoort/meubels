document.addEventListener('DOMContentLoaded', async () => {
    const reviewsList = document.getElementById('reviewsList');

    const fetchReviews = async () => {
        try {
            const response = await fetch('/restservices/reviews');
            if (response.ok) {
                const reviews = await response.json();
                displayReviews(reviews);
            } else {
                console.error('Error fetching reviews:', response.status);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const displayReviews = (reviews) => {
        reviewsList.innerHTML = '';
        reviews.forEach(review => {
            const reviewItem = document.createElement('li');
            reviewItem.innerHTML = `
                <p><strong>Product:</strong> ${review.productName}</p>
                <p><strong>Beoordeling:</strong> ${review.rating}</p>
                <p><strong>Review:</strong> ${review.reviewText}</p>
                <button onclick="deleteReview(${review.id})">Verwijder</button>
            `;
            reviewsList.appendChild(reviewItem);
        });
    };


    window.deleteReview = async (id) => {
        try {
            const response = await fetch(`/restservices/reviews/${id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                alert('Review verwijderd!');
                fetchReviews();
            } else {
                console.error('Error deleting review:', response.status);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    fetchReviews();
});

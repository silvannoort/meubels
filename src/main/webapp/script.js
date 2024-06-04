document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const loginData = {
        username: username,
        password: password
    };

    fetch('http://localhost:8080/api/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
        .then(response => response.json())
        .then(data => {
            if(data.success) {
                alert('Login successful!');
            } else {
                alert('Login failed!');
            }
        })
        .catch(error => console.error('Error:', error));
});

document.getElementById('reviewForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const productName = document.getElementById('productName').value;
    const rating = document.getElementById('rating').value;
    const reviewText = document.getElementById('reviewText').value;

    const review = {
        productName: productName,
        rating: rating,
        reviewText: reviewText
    };

    fetch('http://localhost:8080/api/reviews', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(review)
    })
        .then(response => response.json())
        .then(data => {
            alert('Review submitted successfully!');
            document.getElementById('reviewForm').reset();
        })
        .catch(error => console.error('Error:', error));
});

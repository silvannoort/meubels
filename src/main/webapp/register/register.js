document.getElementById('registerForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const userData = {
        username: username,
        password: password,
        email: null,
        profilePicture: null,
        role: "user",
        reviews: [],
        reacties: []
    };

    fetch('http://localhost:8080/restservices/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else if (response.status === 409) {
                throw new Error('User already exists');
            } else {
                throw new Error('Registration failed');
            }
        })
        .then(data => {
            alert('Registration successful!');
            window.location.href = '../index.html';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error: ' + error.message);
        });
});

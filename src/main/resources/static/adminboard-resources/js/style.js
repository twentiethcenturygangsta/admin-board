document.getElementById("login-form").addEventListener("submit", function(event) {
    event.preventDefault();
    // Perform login validation and submission logic here
    // You can use AJAX or other methods to send the login data to the server
    // Example:
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    console.log("Username: " + username);
    console.log("Password: " + password);
});

const API = "http://localhost:8080";

// LOGIN
function login() {
    fetch(API + "/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            username: document.getElementById("username").value,
            password: document.getElementById("password").value
        })
    })
    .then(res => res.text())
    .then(token => {
        localStorage.setItem("token", token);
        window.location.href = "/dashboard";
    });
}

// REGISTER
function register() {
    fetch(API + "/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            username: document.getElementById("username").value,
            password: document.getElementById("password").value
        })
    })
    .then(res => res.text())
    .then(msg => alert(msg));
}

// CREATE CLASS
function createClass() {
    fetch(API + "/class/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify({
            classType: document.getElementById("classType").value,
            meetingLink: document.getElementById("meetingLink").value,
            time: document.getElementById("time").value
        })
    })
    .then(res => alert("Class Created"));
}

// LOGOUT
function logout() {
    localStorage.removeItem("token");
    window.location.href = "/";
}

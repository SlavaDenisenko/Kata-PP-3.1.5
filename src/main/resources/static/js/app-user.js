$(document).ready(function () {
    fetch('/admin/users/par').then(currentUser => {
        currentUser.json().then(data => {
            document.getElementById('current-user').innerText = data.username;
            document.getElementById('role-user').innerText = print(data.roles);
            $(data).each(function (key, value) {
                $('#about-user').append($("<tr>")
                    .append($("<td>").append(value.id))
                    .append($("<td>").append(value.name))
                    .append($("<td>").append(value.lastName))
                    .append($("<td>").append(value.age))
                    .append($("<td>").append(value.username))
                    .append($("<td>").append(print(value.roles))));
            })
        })
    })
})

function print(data) {
    const text = data.map(role => role.name).toString();
    if (text === "ROLE_ADMIN") {
        return "ADMIN";
    } else if (text === "ROLE_USER") {
        return "USER";
    } else {
        return "ADMIN, USER";
    }
}
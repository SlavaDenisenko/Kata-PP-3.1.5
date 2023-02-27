let roleList = [];

$(document).ready(function () {
    $.getJSON("/admin/users", function (data) {
        $(data).each(function (key, value) {
            // language=HTML
            let rowEdit = `
                <td>
                    <button class="btn-edit" id="btn-edit" onclick="editOpen()" data-id="${value.id}">Edit</button>
                </td>
            `;

            // language=HTML
            let rowDelete = `
                <td>
                    <button class="btn-cancel" id="btn-delete" onclick="deleteOpen()" data-id="${value.id}">Delete
                    </button>
                </td>
            `;

            $('#userTable').append($("<tr>")
                .append($("<td>").append(value.id))
                .append($("<td>").append(value.name))
                .append($("<td>").append(value.lastName))
                .append($("<td>").append(value.age))
                .append($("<td>").append(value.username))
                .append($("<td>").append(print(value.roles)))
                .append(rowEdit)
                .append(rowDelete));
        })
    });

    fetch('/admin/users/authorities').then(roles => {
        roles.json().then(data => {
            roleList = data;
        })
    });

    fetch('/admin/users/par').then(currentUser => {
        currentUser.json().then(data => {
            document.getElementById('current-username').innerText = data.username;
            document.getElementById('user-role').innerText = print(data.roles);
        })
    })
})

$(document).ready(function () {
    $('#btn').click(function () {
        for (let i = document.getElementById('userTable').getElementsByTagName('tr').length - 1; i; i--) {
            document.getElementById('userTable').deleteRow(i);
        }
        let getNum = document.getElementById('id').value;
        let url = 'admin/users/' + getNum;
        console.log(url);
        $.getJSON(url, function (data) {
            $(data).each(function (key, value) {
                let rowEdit = `
                    <td>
                        <button class="btn-edit" id="btn-edit" onclick="editOpen()" data-id="${value.id}">Edit</button>
                    </td>
                `;

                let rowDelete = `
                    <td>
                        <button class="btn-cancel" id="btn-delete" onclick="deleteOpen()" data-id="${value.id}">Delete</button>
                    </td>
                `;
                $('#userTable').append($("<tr>")
                    .append($("<td>").append(value.id))
                    .append($("<td>").append(value.name))
                    .append($("<td>").append(value.lastName))
                    .append($("<td>").append(value.age))
                    .append($("<td>").append(value.username))
                    .append($("<td>").append(print(value.roles)))
                    .append(rowEdit)
                    .append(rowDelete));
            })
        })
    })
})

function changeToCreate() {
    document.getElementById('div-create').style.display = "block";
    document.getElementById('div-table').style.display = "none";
    document.getElementById('h5-add').style.display = "block";
    document.getElementById('h5-all').style.display = "none";
    document.getElementById('btn-new-user').className = 'btn-new';
    document.getElementById('btn-users-table').className = 'btn-new-non-active';
    document.getElementById('div-search').style.display = "none";

    cleanFormAdding();
}

function changeToTable() {
    document.getElementById('div-create').style.display = "none";
    document.getElementById('div-table').style.display = "block";
    document.getElementById('h5-add').style.display = "none";
    document.getElementById('h5-all').style.display = "block";
    document.getElementById('btn-new-user').className = 'btn-new-non-active';
    document.getElementById('btn-users-table').className = 'btn-new';
    document.getElementById('div-search').style.display = "block";
}

$(document).on('click', '.btn-edit', function () {
    const userId = $(this).attr('data-id');
    getEditUser(userId);
})

$(document).on('click', '.btn-cancel', function () {
    const userId = $(this).attr('data-id');
    getDeleteUser(userId);
})

function getEditUser(userId) {
    const url = '/admin/users/' + userId;

    fetch(url).then(response => {
        response.json().then(userEdit => {
            $('#id_update').val(userEdit.id);
            $('#name_update').val(userEdit.name);
            $('#lastName_update').val(userEdit.lastName);
            $('#age_update').val(userEdit.age);
            $('#username_update').val(userEdit.username);
            $('#password_update').val(userEdit.password);
            $('#role_edit').empty().val('');
            for (let allRoles of roleList) {
                // language=HTML
                $('#role_edit').append(`
                    <option id="${allRoles.id}" name="${allRoles.name}">${printRole(allRoles.name)}</option>`)
            }
        })
    })
}

function getDeleteUser(userId) {
    const url = '/admin/users/' + userId;

    fetch(url).then(response => {
        response.json().then(deleteUser => {
            $('#id_delete').val(deleteUser.id);
            $('#name_delete').val(deleteUser.name);
            $('#lastName_delete').val(deleteUser.lastName);
            $('#age_delete').val(deleteUser.age);
            $('#username_delete').val(deleteUser.username);
            $('#password_delete').val(deleteUser.password);
            $('#role_delete').val(printRole(deleteUser.roles));
        })
    })
}

function printRole(text) {
    if (text === "ROLE_ADMIN") {
        return "ADMIN";
    } else {
        return "USER";
    }
}

function editOpen() {
    document.getElementById('pop-up').style.display = "block";
}

function deleteOpen() {
    document.getElementById('pop-up-delete').style.display = "block";
}

function editClose() {
    document.getElementById('pop-up').style.display = "none";
}

function deleteClose() {
    document.getElementById('pop-up-delete').style.display = "none";
}

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

async function addNewUser() {
    let user = {
        name: document.getElementById('name').value,
        lastName: document.getElementById('lastName').value,
        age: document.getElementById('age').value,
        username: document.getElementById('username').value,
        password: document.getElementById('password').value,
        roles: getRoles()
    };

    await fetch('http://localhost:8080/admin/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    });
    alert('User added successfully');
    cleanFormAdding();
}

async function editUser() {
    let user = {
        id: document.getElementById('id_update').value,
        name: document.getElementById('name_update').value,
        lastName: document.getElementById('lastName_update').value,
        age: document.getElementById('age_update').value,
        username: document.getElementById('username_update').value,
        password: document.getElementById('password_update').value,
        roles: getRolesUpdate()
    };

    await fetch('http://localhost:8080/admin/users', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    });
    document.getElementById('pop-up').style.display = "none";
}

async function deleteUser() {
    const userId = document.getElementById('id_delete').value;
    const url = '/admin/users/' + userId;

    await fetch(url, {
        method: 'DELETE'
    });
    document.getElementById('pop-up-delete').style.display = "none";
}

function getRoles() {
    let allRoles = [];
    $.each($("select[name='selectRole'] option:selected"), function () {
        let role = {};
        role.id = $(this).attr('id');
        role.name = $(this).attr('name');
        role.authority = $(this).attr('name');
        allRoles.push(role);
    });
    return allRoles;
}

function getRolesUpdate() {
    let allRoles = [];
    $.each($("select[name='role-edit'] option:selected"), function () {
        let role = {};
        role.id = $(this).attr('id');
        role.name = $(this).attr('name');
        role.authority = $(this).attr('name');
        allRoles.push(role);
    });
    return allRoles;
}

function cleanFormAdding() {
    $('#name').empty().val('');
    $('#lastName').empty().val('');
    $('#age').empty().val('');
    $('#username').empty().val('');
    $('#password').empty().val('');
    $('#selectRole').empty().val('');
    for (let allRoles of roleList) {
        // language=HTML
        $('#selectRole').append(`
            <option id="${allRoles.id}" name="${allRoles.name}">${printRole(allRoles.name)}</option>`)
    }
}
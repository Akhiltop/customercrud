document.addEventListener("DOMContentLoaded", function() {
    fetchCustomers();
});

function fetchCustomers() {
    fetch('http://localhost:8080/api/customers', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        
    }).then(response => response.json()).then(data => {
            let tableBody = document.getElementById("customerTableBody");
            tableBody.innerHTML = '';
            data.forEach(customer => {
                let row = `
                    <tr>
                        <td>${customer.id}</td>
                        <td>${customer.uuid}</td>
                        <td>${customer.firstName}</td>
                        <td>${customer.lastName}</td>
                        <td>${customer.street}</td>
                        <td>${customer.address}</td>
                        <td>${customer.city}</td>
                        <td>${customer.state}</td>
                        <td>${customer.email}</td>
                        <td>${customer.phone}</td>
                        <td>
                            <button onclick="editCustomer(${customer.id})">Edit</button>
                            <button onclick="deleteCustomer(${customer.id})">Delete</button>
                        </td>
                    </tr>
                `;
                tableBody.innerHTML += row;
            });
        });
}

function showAddCustomerForm() {
    document.getElementById("customerForm").style.display = "block";
    document.getElementById("formTitle").textContent = "Add Customer";
    document.getElementById("customerFormDetails").reset();
    document.getElementById("customerId").value = '';
}

function closeForm() {
    document.getElementById("customerForm").style.display = "none";
}

function editCustomer(id) {
    fetch(`http://localhost:8080/api/customers/${id}`)
        .then(response => response.json())
        .then(customer => {
            document.getElementById("customerForm").style.display = "block";
            document.getElementById("formTitle").textContent = "Edit Customer";
            document.getElementById("customerId").value = customer.id;
            document.getElementById("uuid").value = customer.uuid;
            document.getElementById("firstName").value = customer.firstName;
            document.getElementById("lastName").value = customer.lastName;
            document.getElementById("street").value = customer.street;
            document.getElementById("address").value = customer.address;
            document.getElementById("city").value = customer.city;
            document.getElementById("state").value = customer.state;
            document.getElementById("email").value = customer.email;
            document.getElementById("phone").value = customer.phone;
        });
}

function deleteCustomer(id) {
    fetch(`http://localhost:8080/api/customers/${id}`, { method: 'DELETE' })
        .then(() => fetchCustomers());
}

function syncAndFetchCustomers() {
    // Step 1: Fetch data from the external API via your backend proxy
    fetch('http://localhost:8080/api/proxy/customers', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(externalCustomers => {
        console.log('Customers fetched from external API via backend:', externalCustomers);

        // Step 2: Send the data to your backend for synchronization
        return fetch('http://localhost:8080/api/customers/sync', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(externalCustomers)
        });
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.text();
    })
    .then(data => {
        console.log('Synchronization complete:', data);

        // Step 3: Fetch the updated customer list from your backend
        return fetchCustomers();
    })
    .catch(error => {
        console.error('Error during synchronization:', error);
    });
}


document.getElementById("customerFormDetails").addEventListener("submit", function(event) {
    event.preventDefault();

    let id = document.getElementById("customerId").value;
    let customer = {
        uuid: document.getElementById("uuid").value,
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        street: document.getElementById("street").value,
        address: document.getElementById("address").value,
        city: document.getElementById("city").value,
        state: document.getElementById("state").value,
        email: document.getElementById("email").value,
        phone: document.getElementById("phone").value,
    };

    let method = id ? 'PUT' : 'POST';
    console.log(method,customer);
    let url = id ? `http://localhost:8080/api/customers/${id}` : 'http://localhost:8080/api/customers';

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            
        },
        body: JSON.stringify(customer)
    })
    .then(() => {
        closeForm();
        fetchCustomers();
    });
});

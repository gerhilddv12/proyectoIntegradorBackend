document.addEventListener('DOMContentLoaded', function () {
    const formulario = document.querySelector('#update_dentist_form');

    // Event listener for the form submission
    formulario.addEventListener('submit', function (event) {
        //event.preventDefault(); // Prevent page reload on form submission

        // Create a JSON object with the dentist data from the form
        const formData = {
            id: parseInt(document.querySelector('#put_dentist_id').value),
            nombre: document.querySelector('#put_nombre').value,
            apellido: document.querySelector('#put_apellido').value,
            matricula: document.querySelector('#put_matricula').value
        };



        // Call the PUT endpoint to update the dentist data
        const url = `/odontologos`;
        const settingsPut = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        };
        console.log(settingsPut.body)

        fetch(url, settingsPut)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error Actualizando datos del odontologo');
                }
                return response.json();
            })
           /* .then(data => {
                // If successful, show a success message
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>Success!</strong> Dentist data updated successfully.</div>';

                document.querySelector('#put_add_response').innerHTML = successAlert;
                document.querySelector('#put_add_response').style.display = "block";
                resetForm();
                window.location.reload();
            })
            */
            .catch(error => {
                // If an error occurs, show an error message
                let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>Error:</strong> ' + error.message + '</div>';

                document.querySelector('#put_add_response').innerHTML = errorAlert;
                document.querySelector('#put_add_response').style.display = "block";
            });
    });

    // Function to reset the form fields
    function resetForm() {
        document.querySelector('#put_dentist_id').value = "";
        document.querySelector('#put_nombre').value = "";
        document.querySelector('#put_apellido').value = "";
        document.querySelector('#put_matricula').value = "";
    }
    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").classList.add("active");
        } else if (pathname == "/odontologos.html") {
            document.querySelector(".nav .nav-item a:last").classList.add("active");
        }
    })();
});

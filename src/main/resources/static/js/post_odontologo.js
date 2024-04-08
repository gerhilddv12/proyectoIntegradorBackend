/*window.addEventListener('load', function () {

    //Al cargar la pagina buscamos y obtenemos el formulario donde estarán
    //los datos que el usuario cargará del nuevo odontólogo
    const formulario = document.querySelector('#add_new_dentist');

    //Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {

       //creamos un JSON que tendrá los datos del nuevo odontólogo
        const formData = {
            nombre: document.querySelector('#add_nombre').value,
            apellido: document.querySelector('#add_apellido').value,
            matricula: document.querySelector('#add_matricula').value
        };

        //invocamos utilizando la función fetch la API odontólogos con el método POST que guardará
        //el odontólogo que enviaremos en formato JSON
        const url = '/odontologos';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        };

        fetch(url, settings)
        .then(response => {
            if (!response.ok) {
                // Si la respuesta indica un error, lanzar una excepción
                throw new Error(`Error: Hubo un problema en el servidor. Código de estado: ${response.status}`);
            }
            // Si la respuesta es exitosa, convertir la respuesta a JSON
            return response.json();
        })
        .then(data => {
                    // Manejar los datos obtenidos si la respuesta fue exitosa
                    resetUploadForm(); // Oculta el formulario después de que se haya guardado correctamente el odontólogo
                    getDentists(); // Si es necesario refrescar la lista de odontólogos
                })

        })
        .catch(error => {
            let errorAlert;
            if (error.response && error.response.status === 400) {
                console.log(error.response)
                errorAlert = `<div class="alert alert-danger alert-dismissible">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Error:</strong> ${error.response.data.message}
                              </div>`;
            } else {
                console.log(error.response)
                errorAlert = `<div class="alert alert-danger alert-dismissible">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Error:</strong> ${error.message}</div>`;
            }
        
            document.querySelector('#add_response').innerHTML = errorAlert;
            document.querySelector('#add_response').style.display = "block";
            resetUploadForm();
            getDentists();
        });
    

        // Prevenir el comportamiento por defecto del formulario
        event.preventDefault();
        
    });


    function resetUploadForm(){
        document.querySelector('#add_nombre').value = "";
        document.querySelector('#add_apellido').value = "";
        document.querySelector('#add_matricula').value = "";

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
*/
window.addEventListener('load', function () {

    //Al cargar la pagina buscamos y obtenemos el formulario donde estarán
    //los datos que el usuario cargará del nuevo odontólogo
    const formulario = document.querySelector('#add_new_dentist');

    //Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {

        //creamos un JSON que tendrá los datos del nuevo odontólogo
        const formData = {
            nombre: document.querySelector('#add_nombre').value,
            apellido: document.querySelector('#add_apellido').value,
            matricula: document.querySelector('#add_matricula').value
        };

        //invocamos utilizando la función fetch la API odontólogos con el método POST que guardará
        //el odontólogo que enviaremos en formato JSON
        const url = '/odontologos';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        };

        fetch(url, settings)
            .then(response => {
                if (!response.ok) {
                    // Si la respuesta indica un error, lanzar una excepción
                    throw new Error(`Error: Hubo un problema en el servidor. Código de estado: ${response.status}`);
                }
                // Si la respuesta es exitosa, convertir la respuesta a JSON
                return response.json();
            })
            .then(data => {
                // Manejar los datos obtenidos si la respuesta fue exitos

                resetUploadForm(); // Oculta el formulario después de que se haya guardado correctamente el odontólogo
                resetUploadForm()
            })
            .catch(error => {
                let errorAlert;
                if (error.response && error.response.status === 400) {
                    console.log(error.response)
                    errorAlert = `<div class="alert alert-danger alert-dismissible">
                                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                                    <strong>Error:</strong> ${error.response.data.message}
                                </div>`;
                } else {
                    console.log(error.response)
                    errorAlert = `<div class="alert alert-danger alert-dismissible">
                                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                                    <strong>Error:</strong> ${error.message}</div>`;
                }
                document.querySelector('#add_response').innerHTML = errorAlert;
                document.querySelector('#add_response').style.display = "block";
                resetUploadForm();
                getDentists();
            });


        // Prevenir el comportamiento por defecto del formulario
        event.preventDefault();

    });


    function resetUploadForm() {
        document.querySelector('#add_nombre').value = "";
        document.querySelector('#add_apellido').value = "";
        document.querySelector('#add_matricula').value = "";

    }

    (function () {
        let pathname = window.location.pathname;
        if (pathname === "/") {
            document.querySelector(".nav .nav-item a:first").classList.add("active");
        } else if (pathname == "/odontologos.html") {
            document.querySelector(".nav .nav-item a:last").classList.add("active");
        }
    })();
});

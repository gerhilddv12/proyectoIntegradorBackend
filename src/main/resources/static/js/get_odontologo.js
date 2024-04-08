window.addEventListener("load", function () {
  // Obtener todos los elementos con la clase "close"
  const closeButtonElements = document.querySelectorAll(".cerrarActualizar");

  // Agregar un event listener a cada elemento
  closeButtonElements.forEach((element) => {
    element.addEventListener("click", function () {
      // Recargar la página
      console.log("click en close");
      window.location.reload();
    });
  });

  //funcion para llamar a los odontologos desde el servidor
  function getDentists() {
    fetch("/odontologos")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Error al obtener la lista de odontólogos");
        }
        return response.json();
      })
      .then((dentists) => {
        //una vez que se obtienen los datos del servidor se carga la tabla de dentistTableBody
        populateDentistTable(dentists);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }

  // Function to populate the dentist table with the received data
  function populateDentistTable(dentists) {
    const tableBody = document.getElementById("dentistTableBody");
    tableBody.innerHTML = ""; // Clearing previous content of the table
    dentists.forEach((dentist) => {
      const row = document.createElement("tr");
      row.id = "tr_" + dentist.id;

      const idCell = document.createElement("td");
      idCell.textContent = dentist.id;
      row.appendChild(idCell);

      const nombreCell = document.createElement("td");
      nombreCell.textContent = dentist.nombre;
      row.appendChild(nombreCell);

      const apellidoCell = document.createElement("td");
      apellidoCell.textContent = dentist.apellido;
      row.appendChild(apellidoCell);

      const matriculaCell = document.createElement("td");
      matriculaCell.textContent = dentist.matricula;
      row.appendChild(matriculaCell);

      // Crear celda para el botón "Eliminar"
      const deleteCell = document.createElement("td");
      const deleteButton = document.createElement("button");
      deleteButton.type = "button";
      deleteButton.className = "btn btn-danger delete-btn";
      deleteButton.textContent = "Eliminar";
      deleteButton.addEventListener("click", function () {
        const dentistId =
          this.closest("tr").querySelector("td:first-child").textContent;
        if (
          confirm("¿Seguro que quieres eliminar este odontólogo?")
        ) {
          deleteDentist(parseInt(dentistId));
        }
      });
      deleteCell.appendChild(deleteButton);
      row.appendChild(deleteCell);

      // Crear celda para el botón "Actualizar"
      const updateCell = document.createElement("td");
      const updateButton = document.createElement("button");
      updateButton.type = "button";
      updateButton.className = "btn btn-primary update-btn";
      updateButton.textContent = "Actualizar";
      updateButton.dataset.id = dentist.id; // Guardar el ID del odontólogo en el botón
      updateCell.appendChild(updateButton);
      row.appendChild(updateCell);

      tableBody.appendChild(row);
    });

    // Event listener para manejar los clicks en los botones de actualización
    document.querySelectorAll(".update-btn").forEach((btn) => {
      btn.addEventListener("click", function () {
        const dentistId = this.dataset.id;
        // Llamar a la función para abrir el modal de actualización con los datos del odontólogo seleccionado
        openUpdateModal(dentistId);
      });
    });
  }

  // Función para abrir el modal de actualización con los datos del odontólogo seleccionado
  function openUpdateModal(dentistId) {
    // Llamar a la API para obtener los datos del odontólogo por su ID
    fetch(`/odontologos/${dentistId}`)
      .then((response) => response.json())
      .then((data) => {
        // Poblar el formulario de actualización con los datos del odontólogo
        document.getElementById("put_dentist_id").value = data.id;
        document.getElementById("put_nombre").value = data.nombre;
        document.getElementById("put_apellido").value = data.apellido;
        document.getElementById("put_matricula").value = data.matricula;
        // Mostrar el modal de actualización
        $("#updateModal").modal("show");
      })
      .catch((error) => console.error("Error:", error));
  }

  // Llamar a la función para obtener la lista de odontólogos cuando la página cargue
  getDentists();
});

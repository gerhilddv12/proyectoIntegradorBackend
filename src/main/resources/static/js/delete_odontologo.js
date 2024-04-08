    // Function to delete dentist
    function deleteDentist(id) {
        const url = '/odontologos/' + id;
        const settings = {
            method: 'DELETE'
        };

        fetch(url, settings)
        .then(response => {
          if (response.ok) {
            // If the delete operation was successful, remove the row from the table
            document.getElementById('tr_' + id).remove();
          } else {
            throw new Error('Error al eliminar el odontólogo');
          }
        })
        .catch(error => {
          console.error('Error:', error);
        });
    }


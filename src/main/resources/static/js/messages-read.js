window.addEventListener('load', (event) => {
    var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        let loadPageDiv = document.getElementById('messageDiv')
        loadPageDiv.innerHTML = ''
        let id = document.getElementById('id').value
        fetch("http://localhost:8080/messages-read?id=" + id, requestOptions)
        .then(response => response.json())
        .then(e => {
            let senderMessage = document.createElement('h3')

            senderMessage.textContent = "От " + e.sender.username + "("
            + e.sender.firstName + " " + e.sender.lastName + ") - " + e.dateTime

            let receiverMessage = document.createElement('h3')

            receiverMessage.textContent = "До " + e.receiver.username + "("
            + e.receiver.firstName + " " + e.receiver.lastName + ") - " + e.dateTime

            loadPageDiv.appendChild(senderMessage);
            loadPageDiv.appendChild(receiverMessage);

            let paragraphMessage = document.createElement('p')
            paragraphMessage.textContent = e.content

            loadPageDiv.appendChild(paragraphMessage)


        }).catch(error => console.log('error', error));
});


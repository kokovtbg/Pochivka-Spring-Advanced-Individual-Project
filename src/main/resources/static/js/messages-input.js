window.addEventListener('load', event => {
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    let messagesInput = document.getElementById('messages-input')
    messagesInput.innerHTML = ''

    fetch("http://localhost:8080/messages-input-list", requestOptions)
    .then(response => response.json())
    .then(json => json.forEach(m => {
        let row = document.createElement('tr')

        let senderCol = document.createElement('td')
        let messageCol = document.createElement('td')
        let dateCol = document.createElement('td')

        let linkToMess1 = document.createElement('a')
        let id = m.id
        linkToMess1.setAttribute('href', '/messages-read/' + id)
        let linkToMess2 = document.createElement('a')
        linkToMess2.setAttribute('href', '/messages-read/' + id)

        linkToMess1.textContent = m.sender.username
        linkToMess2.textContent = m.content.substring(0, 30)
        dateCol.textContent = m.dateTime

        row.appendChild(senderCol)
        senderCol.appendChild(linkToMess1)
        row.appendChild(messageCol)
        messageCol.appendChild(linkToMess2)
        row.appendChild(dateCol)

        messagesInput.appendChild(row);
    })).catch(error => console.log('error', error));
});
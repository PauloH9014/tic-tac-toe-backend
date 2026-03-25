let jogoAtivo = true;

document.getElementById('startButton').addEventListener('click', function() {
    const player1 = document.getElementById('player1').value.trim();
    const player2 = document.getElementById('player2').value.trim();

    if (player1 === '' || player2 === '') {
        alert('Digite o nome dos dois jogadores!');
        return;
    }

    document.getElementById('Home').style.display = 'none';
    document.getElementById('Game').style.display = 'block';
});

function resetGame() {
    fetch('http://localhost:8080/reiniciar', { method: 'POST' })
        .then(function(response) { return response.json(); })
        .then(function(data) {
            if (data.status === 'ok') {
                document.querySelectorAll('.celula').forEach(function(celula) {
                    celula.textContent = '';
                });
                jogoAtivo = true;
            }
        });
}

document.getElementById('reset').addEventListener('click', resetGame);

document.querySelectorAll('.celula').forEach(function(celula) {
    celula.addEventListener('click', function() {
        if (!jogoAtivo) return;
        if (celula.textContent !== '') return;

        const pos = celula.getAttribute('data-pos');
        const coluna = pos[0];
        const linha = pos[1];

        fetch('http://localhost:8080/jogada', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ coluna: coluna, linha: linha })
        })
        .then(function(response) { return response.json(); })
        .then(function(data) {
            if (data.status === 'ok') {
                celula.textContent = data.simbolo;
                fetch('http://localhost:8080/status')
                .then(function(r) { return r.json(); })
                .then(function(status) {
                    if (status.vencedor !== "") {
                        alert('Temos um vencedor: ' + status.vencedor + ' !');
                        jogoAtivo = false;
                    } else if (status.empate) {
                        alert('Empate!');
                        jogoAtivo = false;
                    }
                });
            } else if (data.erro) {
                alert(data.erro);
            }
        });
    });
});

document.getElementById('verHistorico').addEventListener('click', function() {
    fetch('http://localhost:8080/historico')
    .then(function(r) { return r.json(); })
    .then(function(partidas) {
        document.getElementById('bodyTable').innerHTML = '';
        partidas.forEach(function(partida, index) {
            const row = document.createElement('tr');
            row.innerHTML = '<td>' + (index + 1) + '</td>' +
                           '<td>' + partida.vencedor + '</td>' +
                           '<td>' + partida.duracao + 's</td>';
            document.getElementById('bodyTable').appendChild(row);
        });
        document.getElementById('Game').style.display = 'none';
        document.getElementById('Storage').style.display = 'block';
    });
});

document.getElementById('backToGame').addEventListener('click', function() {
    document.getElementById('Storage').style.display = 'none';
    document.getElementById('Game').style.display = 'block';
});
const fRead = require('fs/promises')

// fRead.readFile('./ficheros/prueba.txt', 'utf8')
//     .then((data) => {
//         console.log(data)
//     })
//     .catch((err) => {
//         console.error(err)
//     })

async function lecturaFichero() {
    try {
        await fRead.appendFile('./ficheros/prueba.txt', '\n\nAutor: Adrian')
        const data = await fRead.readFile('./ficheros/prueba.txt', 'utf8')
        console.log(data)
    } catch (err) {
        console.error(err)
    }
}

lecturaFichero()
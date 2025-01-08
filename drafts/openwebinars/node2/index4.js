const fs = require('fs')

const streamer = fs.createReadStream('./ficheros/prueba.txt', 'utf8')
let body = ''

streamer.once('data', (data) => {
    console.log("lectura")
    body += data
})

streamer.on('data', (data) => {
   body += data
})

streamer.on('end', () => {
    console.log(body)
    console.log(body.length)
})
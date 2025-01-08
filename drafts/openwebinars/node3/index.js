const http = require('http')

const server = http.createServer((req, res) => {
    console.log('Metodo',req.method)
    console.log('URL',req.url)
    console.log('Headers',req.headers)
    res.end('Hello World!')
})

server.listen(8080, () => {
    console.log('Server running on port 8080')
})
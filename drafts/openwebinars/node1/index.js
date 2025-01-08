//librerias
const colors = require('colors')
console.log('Hola mundo'.red)

const axios = require('axios').default

axios.get('https://rickandmortyapi.com/api/character')
    .then(response => {
        console.log(response.data)
    })
    .catch(error => {
        console.log(error)
    })
    





const operaciones = require('./operaciones')
const { sumar } = require('./operaciones')
const Persona = require('./persona')

const persona = new Persona('Jorge', 'Carreras', 23)
console.log(persona.toString())

console.log(operaciones.sumar(1,0))
console.log(sumar(4,2))

console.log(__dirname)
console.log(__filename)
console.log(process.argv)

function getParam(param){
    const index = process.argv.indexOf(param)
    return index === -1 ? -1 : process.argv[index+1]
}

const param = getParam('--nombre')
if (param){
    console.log(`Hola ${param}`)
} else {
    console.log('Mal')
}

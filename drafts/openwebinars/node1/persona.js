class Persona {
    constructor(name, surname, age) {
        this.name = name
        this.surname = surname
        this.age = age
    }
    toString() {
        return `Nombre: ${this.name} 
Apellido: ${this.surname} 
Edad: ${this.age}`
    }
}

module.exports = Persona
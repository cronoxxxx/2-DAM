
lista = [["Mujer", "Antonella", "Rodriguez"],
         ["Mujer", "Isabel", "Gutierrez"],
         ["Mujer", "Luna", "Garcia"],
         ["Mujer", "Luciana", "Gonzalez"],
         ["Mujer", "Ana", "Martinez"],
         ["Mujer", "Sofía", "Medina"],
         ["Hombre", "Mario", "Herrera"],
         ["Mujer", "Francesca", "Sanchez"],
         ["Hombre", "Alejandro", "Sanchez"],
         ["Mujer", "Alejandra", "Romero"],
         ["Mujer", "Allison", "Castro"],
         ["Hombre", "Isaac", "Vazquez"],
         ["Hombre", "Carlos", "Gonzalez"],
         ["Mujer", "Renata", "Morrell"],
         ["Mujer", "Laura", "Sanchez"],
         ["Hombre", "Sebastián", "Molina"],
         ["Mujer", "Abigail", "Lopez"],
         ["Mujer", "Andrea", "Ramos"],
         ["Mujer", "María", "Delgado"],
         ["Mujer", "Ivanna", "Serrano"],
         ["Mujer", "Alejandra", "Medina"],
         ["Mujer", "Violeta", "Ortega"],
         ["Mujer", "Ashley", "Rodriguez"],
         ["Mujer", "Julia", "Alvarez"],
         ["Mujer", "Miranda", "Cruz"],
         ["Mujer", "Nicole", "Gil"],
         ["Mujer", "Rafaela", "Herrera"],
         ["Hombre", "Samuel", "Mendez"],
         ["Hombre", "Kevin", "Castro"],
         ["Mujer", "Lucía", "Santos"],
         ["Hombre", "LiaHombre", "Gil"],
         ["Mujer", "Allison", "Jimenez"]]


class Persona:
    def __init__(self, sexo, nombre, apellido, edad=2000):
        self.sexo = sexo
        self.nombre = nombre
        self.apellido = apellido
        self.edad = edad

    def set_sexo(self, other):
        self.sexo = other


# Teniendo encuenta que todos han nacido en el 2000 crea una lista de Personas con la lista de pasajeros llamala:
pasajero = []
"""Esribe tu código aquí"""
for i in lista:
    pasajero.append(Persona(i[0], i[1], i[2]))

# Cambia el nombre del pasajero del día de tu cumpleaños por tu nombre y pon cara="DE CUMPLEAÑOS", si quieres cambiale tambien el sexo
"""Esribe tu código aquí"""
for i in range(len(pasajero)):
    if i == 20:
        pasajero[i].set_sexo("Hombre")
        print(f"El pasajero {pasajero[i].nombre} tiene cara DE CUMPLEAÑOS")
        print(f"Ahora es {pasajero[i].sexo}")
    else:
        print(f"El pasajero {pasajero[i].nombre} tiene cara Alegre")

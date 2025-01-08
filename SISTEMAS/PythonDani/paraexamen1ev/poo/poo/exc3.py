"""Crea una Clase Teléfono, si empieza por 9 u 8 haz que lo guarde como un fijo, 
en el caso de que empiece por 6 o 7 lo guarde como móvil.
Haz un método que cambie el número de télefono, en el caso de que reciva mas de 9 dígitos haz que devuelva "Número no cambiado" 
y no guarde el número, en el caso de que tenga nueve digitos, 
lo guarda como móvil o fijo. Crea una clase persona con télefono, de tal manera que:"""""


class Telefono:
    def __init__(self, numero):
        self.numero = None
        self.tipo = None
        self.cambiar_numero(numero)

    def cambiar_numero(self, numero):
        if len(numero) != 9:
            return "Número no cambiado"
        if numero.startswith('9') or numero.startswith('8'):
            self.tipo = 'fijo'
        elif numero.startswith('6') or numero.startswith('7'):
            self.tipo = 'movil'
        else:
            return "Numero no cambiado"
        self.numero = numero
        return "Numero cambiado"


class PersonaTlf:
    def __init__(self, fecha_nacimiento, genero, nombre, apellido, numero):
        self.fecha_nacimiento = fecha_nacimiento
        self.genero = genero
        self.nombre = nombre
        self.apellido = apellido
        self.numero = Telefono(numero)

    def get_tlf(self):
        print(self.numero.numero)

    def tlf_info(self):
        print(f"Número: {self.numero.numero}, Tipo: {self.numero.tipo}")

    def cambiar_tlf(self, other):
        print(self.numero.cambiar_numero(other))


persona_tlf01 = PersonaTlf(1979, "Hombre", "Manuel", "López", "987654321")
persona_tlf01.get_tlf()
persona_tlf01.tlf_info()
persona_tlf01.cambiar_tlf("12345")
# salida:
# Número no cambiado, necesitamos un número de 9 cifras


persona_tlf01.cambiar_tlf("654987321")
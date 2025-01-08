"""""Crea una clase llamada Cuenta que tendrá los siguientes atributos: titular (que es una persona) 
y cantidad (puede tener decimales). 
El titular será obligatorio y la cantidad es opcional. Construye los siguientes métodos para la clase:

Los setters y getters para cada uno de los atributos. El atributo no se puede modificar directamente, 
sólo ingresando o retirando dinero.

mostrar(): Muestra los datos de la cuenta.
ingresar(cantidad): se ingresa una cantidad a la cuenta, si la cantidad introducida es negativa, no se hará nada.
retirar(cantidad): se retira una cantidad a la cuenta. La cuenta puede estar en números rojos."""""


class Cuenta:
    def __init__(self, titular, cantidad=0):
        self.titular = titular
        self.cantidad = cantidad

    def get_titular(self):
        return self.titular

    def get_cantidad(self):
        return self.cantidad

    def set_cantidad(self, other):
        self.cantidad = other

    def set_titular(self, other):
        self.titular = other

    def mostrar(self):
        return f"El titular {self.titular} tiene {self.cantidad} euros"

    def ingresar(self, cantidad):
        if cantidad >= 0:
            self.cantidad += cantidad
        else:
            print("Error")

    def retirar(self, cantidad):
        if self.cantidad - cantidad >= 0:
            self.cantidad -= cantidad
        else:
            print("Error")


cuenta = Cuenta("Juanca", 2000)
print(cuenta.get_cantidad())
print(cuenta.get_titular())
print(cuenta.mostrar())
cuenta.set_cantidad(3000)
cuenta.set_titular("Emilio")
print(cuenta.get_cantidad())
print(cuenta.get_titular())
print(cuenta.mostrar())
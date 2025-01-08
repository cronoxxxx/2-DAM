"""Crea una clase llamada int8 con una método llamado getint8() que obtenga 
el número rellenando con "8" ceros a la izquierda. Crea otro método sum(int8) que devuelva la suma 
rellenando con "8" ceros a la izquierda En el caso de que el número sea mayor que 9999999 o
 menor que -9999999, devuelve "NAN"""""




class int8:
    def __init__(self, numero):
        self.numero = numero

    def getint8(self):
        if -9999999 <= self.numero <= 9999999:
            return f"{self.numero:08d}"
        else:
            return "NaN"

    def sum(self, other):
        resultado = self.numero + other.numero
        if -9999999 <= resultado <= 9999999:
            return int8(resultado)
        else:
            return "NaN"

    def __str__(self):
        return self.getint8()


a = int8(501)
b = int8(501)
print(a.getint8())
print(a.sum(b))

nmax = int8(9999999)
print(a.sum(nmax))
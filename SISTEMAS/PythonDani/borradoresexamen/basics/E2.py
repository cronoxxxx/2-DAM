#Solicitar el valor del radio del circulo para calcular su area

import math

r = float(input("Introduce el valor del radio: "))


def area(r):
    return math.pi * math.pow (r, 2)

result = area(r)

print ("El area del circulo es {}".format(result.__format__('.2f')))

print (f"El area del circulo es {result.__format__('.2f')}")
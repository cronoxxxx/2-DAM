import math

#Solicitar al usuario un numero n y calcular n + nn + nnn

n = int(input("Introduce un numero: "))

result = n + (math.pow(n, 2)) + (math.pow(n, 3))
parse = int (result)

print(f"{n} + {int(math.pow(n, 2))} + {int(math.pow(n, 3))} = {result}")
print(f"{n} + {int(math.pow(n, 2))} + {int(math.pow(n, 3))} = {parse}")



m = input ("Introduce un numero: ")

mm=int("{}{}".format(m,m))


mmm=int("%s%s%s" % (m,m,m))


print(f"Suma: {int(m)+int(mm)+int(mmm)}")
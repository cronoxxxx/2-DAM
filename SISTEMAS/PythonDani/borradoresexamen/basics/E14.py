
comprobar_si_es_cercano = lambda num1: 1 if abs(1000 - num1) < 100 else (2 if abs(2000 - num1) < 100 else -1)

num = int(input("Introduce un número: "))

if comprobar_si_es_cercano(num) == 1:
    print("Cercano a 1000")
elif comprobar_si_es_cercano(num) == 2:
    print("Cercano a 2000")
else:
    print("No cercano")

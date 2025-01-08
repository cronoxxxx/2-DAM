def max_comun_divisor (a, b):
    while b != 0:
        a, b = b, a % b
    return a

a = int(input("Introduce el primer numero: "))
b = int(input("Introduce el segundo numero: "))

def min_comun_multiplo (a, b):
    return (a * b) // max_comun_divisor(a, b)

print(max_comun_divisor(a, b))
#Implementa la función factorial de forma iterativa
cantidad = int(input("Introduce hasta que numeros desea multiplicar: "))
def factorial(n):
    result = 1
    for i in range(1, n+1):
        result *= i
    return result

print (f"La multiplicación total es {factorial(cantidad)}")

#Implementa la función XOR para dos valores
bin1 = int(input("Introduce el primer valor: "))
bin2 = int(input("Introduce el segundo valor: "))

def xor(num1, num2):
    return num1 ^ num2

print (f"La operación XOR es {xor(bin1, bin2)}")

#Implementa la función lambda que devuelva una lista con el cociente y el resto de una división
diviendo = float(input("Introduce el primer valor: "))
divisor = float(input("Introduce el segundo valor: "))

division = lambda x, y: [x / y, x % y]

result = division(diviendo, divisor)
print(f"Resultado de la división: {result}")


#Una función que devuelva el número de repeticiones de un carácter dentro de un vector

lista = ["a", "b", "c", "d", "a", "b", "c", "d", "a", "b", "c", "d"]

def ocurrencias(vector, caracter):
    return vector.count(caracter)

print(ocurrencias(lista, "a"))

#Una función que, recibiendo un parámetro, nos devuelva su tabla de multiplicar en una lista 

proof = int(input("Introduce el valor: "))

def multiplicar(num):
    return [num * i for i in range(1, 11)]

result = multiplicar(proof)
print ("La tabla de multiplicar es: ", *result, sep=" ")

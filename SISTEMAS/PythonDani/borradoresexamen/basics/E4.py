#Obtener un conjunto de numeros separados por coma y crear una lista

lista = []
cantidad = int(input("Introduce la cantidad de elementos: "))

for i in range(cantidad):
    num = int(input("Introduce un numero: "))
    lista.append(num) 
    
   

print(f"La lista es {lista}")


# Obtener el conjunto de números separados por coma
entrada = input("Ingrese un conjunto de números separados por coma: ")

# Dividir la entrada en una lista de cadenas
numeros_como_cadenas = entrada.split(',')

# Convertir las cadenas en números enteros
numeros = [int(numero.strip()) for numero in numeros_como_cadenas]

print("Lista de números:", numeros)
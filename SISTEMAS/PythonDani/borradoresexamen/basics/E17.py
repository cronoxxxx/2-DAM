def copiarSubcadena(cadena,inicio,final,cantidad):

    subcadena = cadena[inicio-1:final]
    copia_subcadena = subcadena * cantidad
    return copia_subcadena


cadena_inicial = input("Introduce una cadena de caracteres: ")
inicio = int(input("Introduce el primer indice: "))
final = int(input("Introduce el segundo indice: "))
if inicio < 1 or final > len(cadena_inicial) or inicio > final:
    print("Error")
else:
    cantidad = int(input("Introduce la cantidad: "))   
    print(copiarSubcadena(cadena_inicial, inicio, final, cantidad)) 


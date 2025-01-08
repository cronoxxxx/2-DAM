def comprobarSiEsVocal(letra):
    vocales = "aeiouAEIOU"
    return letra in vocales


letra = input("Introduce una letra: ")

if comprobarSiEsVocal(letra):
    print("Es una vocal")
else:
    print("No es una vocal")
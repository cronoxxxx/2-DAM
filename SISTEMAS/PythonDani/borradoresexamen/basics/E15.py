cadena =  str(input("Introduce una cadena de caracteres: "))
cadena = cadena.split(".")

if (cadena[-1] == "py"):
    print("El archivo termina en .py")
else:
   nueva_extension = str(input("Introduce la nueva extension: "))
   cadena[-1] = nueva_extension
   cadena = ".".join(cadena)
   print(cadena)

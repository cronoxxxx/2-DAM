#Obtener la extension de un archivo especificado por el usuario
archivo = input("Introduce el nombre del archivo: ")

extension = archivo.split(".")[-1] #Lee el primer contenido desde derecha a izquierda

print (f"La extension del archivo es {extension}")
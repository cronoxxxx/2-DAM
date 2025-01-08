num = float(input("Introduce un numero: "))
index = int(input(""""suma = 1
multiplicacion = 2
division = 3
resta = 4"""))



def operacion(index, num):
    match index:
        case 1:
            num2=int(input("Introduce otro numero: "))
            print("Suma: ", num + num2)
        case 2:
            num2=int(input("Introduce otro numero: "))
            print("Multiplicacion: ", num * num2)
        case 3:
            num2=int(input("Introduce otro numero: "))
            print("Division: ", num / num2)
        case 4:
            num2=int(input("Introduce otro numero: "))
            print("Resta: ", num - num2)
        case _:
            print("Opcion no valida")

operacion(index, num)

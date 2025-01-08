#Operaciones aritméticas entre dos variables tipo int
n1 = int (input("Introduce el primer valor: "))
n2 = int (input("Introduce el segundo valor: "))

op = int (input ("Ingresa la opcion\n1.Suma\n2.Resta\n3.Multiplicacion\n4.Division\n"))

match op:
    case 1:
        print (f"Suma: {n1+n2}")
    case 2:
        print (f"Resta: {n1-n2}")
    case 3:
        print (f"Multiplicacion: {n1*n2}")
    case 4:
        print (f"Division: {n1/n2}")
    case _:
        print ("Opcion no valida")        


#Operaciones aritméticas entre dos variables tipo float

n1 = float (input("Introduce el primer valor: "))
n2 = float (input("Introduce el segundo valor: "))

op = float (input ("Ingresa la opcion\n1.Suma\n2.Resta\n3.Multiplicacion\n4.Division\n"))

match op:
    case 1:
        print (f"Suma: {n1+n2}")
    case 2:
        print (f"Resta: {n1-n2}")
    case 3:
        print (f"Multiplicacion: {n1*n2}")
    case 4:
        print (f"Division: {n1/n2}")
    case _:
        print ("Opcion no valida")     

#Operaciones de concatenación con dos variables tipo String

string = input("Introduce el primer valor: ")
string2 = input("Introduce el segundo valor: ")

formatter = f"{string} {string2}"

print (formatter)


#Operaciones entre variables tipo Complex y float/int
complex_num = complex (input("Introduce el primer valor: "))
float_num = float (input("Introduce el segundo valor: "))
int_num = int (input("Introduce el tercer valor: "))

sum_result = complex_num + float_num
sub_result = complex_num - int_num
mul_result = complex_num * float_num
div_result = complex_num / int_num

print(f"Suma: {sum_result}")
print(f"Resta: {sub_result}")
print(f"Multiplicacion: {mul_result}")
print(f"Division: {div_result}")

#Operaciones lógicas entre variables tipo booleanas

print (True and True)
print (True and False)
print (False and False)
print (5>=2)
print (5<2)
print (5+2 == 7)
print (5+2 != 7)
if (5+2 == 7):
    print ("5+2 es igual a 7")
else:
    print ("5+2 no es igual a 7")


#Operaciones de casting a tipo int de float, complex y string
entero = int (input("Introduce el primer valor: "))

format_float = float (entero)
print (f"{entero} es {format_float} tipo {type(format_float)}")


format_str = str (entero)
print (f"{entero} es {format_str} tipo {type(format_str)}")

format_complex = complex (entero)
print (f"{entero} es {format_complex} tipo {type(format_complex)}")



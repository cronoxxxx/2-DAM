#Imprimir el calendario para un año y mes especificos

import calendar

year = int(input("Introduce el año: "))
month = int(input("Introduce el mes: "))
if 1<=month<=12:
    print (calendar.month(year, month))
else:
    print ("El mes no existe")

x = 9 * 1.00

print (x)
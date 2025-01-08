import datetime

#Mostrar la fecha de un evento almacenada en una tupla
fecha_evento = (2024,5,5)


print (datetime.date(*fecha_evento))
print (datetime.date(*fecha_evento).strftime("%d/%m/%Y"))

print ("%i/%i/%i" % (fecha_evento[1], fecha_evento[2], fecha_evento[0])) # % para mapear

year, month, day = fecha_evento #desempaquetar

print ("%i/%i/%i" % (day, month, year))
print (f"{day}/{month}/{year}")
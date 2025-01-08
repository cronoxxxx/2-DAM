"""""
Empezamos con el problema del viajante, (Travelling Salesman Problem) responde a la siguiente pregunta: dada una lista de ciudades 
y las distancias entre cada par de ellas, ¿cuál es la ruta más corta posible que visita cada ciudad exactamente una vez 
y al finalizar regresa a la ciudad origen? Este es un problema NP-Hard dentro en la optimización combinatoria, 
muy importante en investigación operativa y en ciencias de la computación. En el siguiente problema partiendo de Madrid 
tenemos que pasar por las siguientes ciudades

"""""

from ipyleaflet import Map, Circle, Polyline
import random
import itertools

ciudad = [[40.4950873744, -3.71704619215],
          [41.6207742504, -2.58874304739],
          [38.4786378049, -0.568699068376],
          [42.0439686698, 1.04798206104],
          [36.5538729195, -5.7604183752],
          [40.8049892162, -6.06541224773],
          [42.435764706, -8.46106294738],
          [38.0165122783, -3.44169215171],
          [41.6203648019, -1.06449678144],
          [42.1280117119, 2.6735559327],
          [43.292357861, -5.99350932547]]

line = Polyline(
    locations=ciudad,
    color="blue",
    weight=2,
    fill=False
)
c = ciudad[0]
m = Map(center=ciudad[0], zoom=5.4)
for i in ciudad:
    circle = Circle()
    circle.location = i
    circle.radius = 5000
    circle.color = "blue"
    circle.fill_color = "blue"
    m.add(circle)
m.add(line)
print(m)

#Crea una función que obtenga el recorrido realizado, testeala con el orden actual de ciudades
#sol : d = 435.6697061255633

def calcular_d(recorrido):
    d=0
    for i in range(len(recorrido)-1):
        d=d+((recorrido[i][0]-recorrido[i+1][0])**2+(recorrido[i][0]-recorrido[i+1][1])**2)**0.5
    return d


#print("Empezamos con ",calcular_d(ciudad))

def cambio(lista):
    a=random.randint(1,len(lista)-1)
    b=random.randint(1,len(lista)-1)
    while a == b :
        b=random.randint(1,len(lista))
    if a > (len(lista)-1):
        a = len(lista)-1
    if b > (len(lista)-1):
        b = len(lista)-1
    aux=lista[a]
    lista[a]=lista[b]
    lista[b]=aux
    return lista

d=calcular_d(ciudad)
recorrido=ciudad
print(d)

"""Haz un algoritmo que utilice el método montecarlo, para este problema haz que desde desde la ciudad[0] se minimice la distancia 
recorrida, utilizalo con almenos 1000000 permutaciones aleatorias para mejorar el recorrido"""

N = 10000
for i in range(N):
    aux = list(recorrido)
    cambio(aux)
    if (calcular_d(aux) < d or random.random() < 0.0000001):
        d = calcular_d(aux)
        recorrido = aux
        print(d)

print("hemos minimizado", calcular_d(recorrido))

c = ciudad[0]
m = Map(center=ciudad[0], zoom=5)

line2 = Polyline(
    locations=recorrido,
    color="blue",
    weight=2,
    fill=False
)

for i in ciudad:
    circle = Circle()
    circle.location = i
    circle.radius = 5000
    circle.color = "blue"
    circle.fill_color = "blue"
    m.add(circle)

m.add(line2)
print(m)


#Utiliza itertools para generar todas las permutaciones y obtener la solución



def calcular_d(recorrido):
    d=0
    for i in range(len(recorrido)-1):
        d=d+((recorrido[i][0]-recorrido[i+1][0])**2+(recorrido[i][0]-recorrido[i+1][1])**2)**0.5
    return d

mejor_ruta=ciudad
mejor_d=calcular_d(ciudad)

ciudad_fija =[ciudad[0]]
otras_ciudades = ciudad[1:]
permutaciones = itertools.permutations(otras_ciudades)
rutas=[]
for i in permutaciones:
    aux= [ciudad_fija[0]]
    for j in i:
        aux.append(j)
    rutas.append(aux)


for i in rutas:
    d=calcular_d(i)
    if d < mejor_d:
        mejor_d=d
        mejor_ruta=i

#sol d = 434.64381367718534
print("Mejor orden encontrado:", mejor_ruta)
print("Distancia mínima encontrada:", mejor_d)




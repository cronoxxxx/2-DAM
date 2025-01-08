""""" Crea una clase llamada Repartidor.
* El constructor inicializará los atributos **(nombre, zona_entrega, pedidos_entregados)** cuando se cree una instancia de la clase.
* Crea un método **entregar_pedido** que registra la entrega de un pedido y muestra un mensaje.
* Crea un método **informe_entregas** que imprime un informe con el número total de pedidos entregados por el repartidor.

Para probar la clase utiliza el sigiente código:

```
repartidor1 = Repartidor("Carlos", "Centro")

repartidor1.entregar_pedido()
repartidor1.entregar_pedido()
repartidor1.entregar_pedido()

repartidor1.informe_entregas()
```

Deberías obtener:
```
Carlos ha entregado un pedido en la zona Centro.
Carlos ha entregado un pedido en la zona Centro.
Carlos ha entregado un pedido en la zona Centro.
Carlos ha entregado 3 pedidos en total."""""


class Repartidor:
    def __init__(self, nombre, zona_entrega, pedidos_entregados=0):
        self.nombre = nombre
        self.zona_entrega = zona_entrega
        self.pedidos_entregados = pedidos_entregados

    def entregar_pedido(self):
        self.pedidos_entregados = self.pedidos_entregados + 1
        print(f"{self.nombre} ha entregado un pedido en la zona {self.zona_entrega}")

    def informe_entregas(self):
        print(f"{self.nombre} ha entregado {self.pedidos_entregados} pedidos en total")

persona = Repartidor("Carlos", "Centro")
persona.entregar_pedido()
persona.entregar_pedido()
persona.entregar_pedido()
persona.informe_entregas()

"""Utiliza estos repartidores y zonas diferentes para hacer cien repartos aleatorios

```
zonas = ["Norte", "Sur", "Este", "Oeste"]
repartidor = ["Juan", "Celia", "Lucas", "Celeste","Dani","Gema","Ramon","Elvira","Pablo","Clara"]
```

La salida tiene que ser:

```
# Obtener informes de entregas
for repartidor in repartidores:
    repartidor.informe_entregas()
    
    
Celeste ha entregado un pedido en la zona Oeste.
Elvira ha entregado un pedido en la zona Norte.
Celeste ha entregado un pedido en la zona Norte.
Celeste ha entregado un pedido en la zona Oeste.
Elvira ha entregado un pedido en la zona Norte.
Dani ha entregado un pedido en la zona Este.
Elvira ha entregado un pedido en la zona Norte.
Celeste ha entregado un pedido en la zona Oeste.
Celeste ha entregado un pedido en la zona Este..
....."""
import random
zonas = ["Norte", "Sur", "Este", "Oeste"]
repartidor = ["Juan", "Celia", "Lucas", "Celeste","Dani","Gema","Ramon","Elvira","Pablo","Clara"]
repartidores = []
for i in range(10):
    persona = Repartidor(random.choice(repartidor), random.choice(zonas),random.randint(1,10))
    repartidores.append(persona)
print(repartidores.__len__())
for repartidor in repartidores:
    repartidor.entregar_pedido()
    repartidor.informe_entregas()


"""""Fíajte en el siguiente informe de entregas:
```
for repartidor in repartidores:
    repartidor.informe_entregas()
    
Lucas ha entregado 6 pedidos en total.
Juan ha entregado 8 pedidos en total.
Celia ha entregado 11 pedidos en total.
Gema ha entregado 15 pedidos en total.
Dani ha entregado 6 pedidos en total.
Elvira ha entregado 10 pedidos en total.
Celia ha entregado 13 pedidos en total.
Pablo ha entregado 10 pedidos en total.
Ramon ha entregado 7 pedidos en total.
Celia ha entregado 14 pedidos en total.
```
Crea un informe de entragas ordenado, para este caso particular quedaría como:

```
# Imprimir informe ordenado
for repartidor in informe_ordenado:
    repartidor.informe_entregas()
    
Gema ha entregado 15 pedidos en total.
Celia ha entregado 14 pedidos en total.
Celia ha entregado 13 pedidos en total.
Celia ha entregado 11 pedidos en total.
Elvira ha entregado 10 pedidos en total.
Pablo ha entregado 10 pedidos en total.
Juan ha entregado 8 pedidos en total.
Ramon ha entregado 7 pedidos en total.
Lucas ha entregado 6 pedidos en total.
Dani ha entregado 6 pedidos en total."""

sorted_repartidores = []

for i in range(len(repartidores)):
    insert_index = len(sorted_repartidores)
    for i2 in range(len(sorted_repartidores)):
        if repartidores[i].pedidos_entregados > sorted_repartidores[i2].pedidos_entregados:
            insert_index = i2

    sorted_repartidores.insert(insert_index, repartidores[i])


print("Número de repartidores en la lista ordenada:", len(sorted_repartidores))
for repartidor in sorted_repartidores:
    repartidor.informe_entregas()





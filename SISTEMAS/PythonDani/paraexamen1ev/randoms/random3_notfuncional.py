"""""Al implementar MD5, obtendremos como resultado, una cadena alfanumérica 
de 32 caracteres hexadecimales. Ocupando siempre 16 Bytes de memoria.

El siguiente pin = '4aca32b706287f13903f08b7aab38f79', ha sido codificado con md5 
"hashlib.md5(b'****')", para ello han utilizado dos carácteres en mayúsculas 
('ABCDEF') y dos números (0-9) , haz una función que obtenga el pin descodificado 
utilizando random.

¿Cuántos intentos ha relizado hasta que la ha descubierto? Realiza el 
ejercicio varias veces y haz un histograma."""""

import hashlib
import random
import string
import matplotlib.pyplot as plt

letras = 'ABCDEF'
def descodificar_pin(hash_md5):
    intentos = 0
    while True :
        intentos += 1
        pin = ''.join(random.choices(letras + string.digits, k=4))
        pin_hash = hashlib.md5(pin.encode()).hexdigest()
        if pin_hash == hash_md5:

            return pin,intentos

intentos_lista = []
num_ejecuciones = 1000
for _ in range(num_ejecuciones):
    hash_md5 = '4aca32b706287f13903f08b7aab38f79'
    _,intentos = descodificar_pin(hash_md5)
    intentos_lista.append(intentos)

plt.hist(intentos_lista, bins=30,alpha=0.5)
plt.title("Histograma de intentos")
plt.xlabel("Intentos")
plt.ylabel("Frecuencia")
plt.show()
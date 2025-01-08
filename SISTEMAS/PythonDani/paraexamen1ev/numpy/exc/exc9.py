""""
En 1674 G. Leibnitz da la serie:
pi/4 = 1 - 1/3 + 1/5 - 1/7 + 1/9 - ...
es decir que
pi = 4 - 4/3 + 4/5 - 4/7 + 4/9 - ...

Haz la serie hasta el termino 4/10000, resultado = 3.1413926...

"""""


def pi(a, n):
    res = 0
    suma = False
    for cont in range(1, n+1, 2):
        if suma:
            res -= a / cont
            suma = False
        else:
            res += a / cont
            suma = True
    return res

a = 4
n = 10000
result = pi(a, n)
print(result)
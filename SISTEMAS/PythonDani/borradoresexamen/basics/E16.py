def generarParesPositivos(cantidad):
    n_pos = []
    for i in range(cantidad):
        if i % 2 == 0:
            n_pos.append(i)
    return n_pos        


print (generarParesPositivos(10))            
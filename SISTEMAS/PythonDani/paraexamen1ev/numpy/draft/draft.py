# Fin
import numpy as np
from scipy import stats
arr = np.array([1, 2, 3])
print(arr)
print("Append 4 y 5 al array")
arr = np.append(arr, [4, 5])
print(arr)
print('-'*50)
# np.apply_along_axis: Aplica una función a lo largo de un eje de un array
# Esta función toma un array 'a' y devuelve la suma del primer y último elemento de 'a'
def my_func(a):
    return a[0] + a[-1]

def sumar(a):
    suma = 0
    for x in a:
        suma += x
    return suma

# Creamos un array 2D
arr = np.array([[1, 2, 3],
                [4, 5, 6],
                [7, 8, 9]])
print("--APPLY ALONG AXIS--")
print(arr)
# Aplicamos la función a lo largo del eje 1 (filas)
# El segundo argumento 1 indica que la función se aplicará a lo largo de las filas (eje 1). Si fuera 0, se aplicaría a lo largo de las columnas.
print("Resultado apply_along_axis que suma el primer y ultimo elemento de cada array de la matriz en filas")
result = np.apply_along_axis(my_func, 1, arr)
print(result)
print("Resultado apply_along_axis que suma el primer y ultimo elemento de cada array de la matriz en columnas")
result = np.apply_along_axis(my_func, 0, arr)
print(result)

print("Resultado apply_along_axis que suma total de cada array de la matriz respecto a filas")
result = np.apply_along_axis(sumar, 1, arr)
print(result)
print('-'*50)
print ("--DOT--")

# Definir dos matrices 2D
matrix_a = np.array([[1, 2],
                     [3, 4]])  # Matriz A de 2x2

matrix_b = np.array([[5, 6],
                     [7, 8]])  # Matriz B de 2x2

matrix_c = np.array([5, 10])  # Matriz c de 1x2
print("Arrays dot")
print("Matriz A")
print(matrix_a)
print("Matriz B")
print(matrix_b)
print("Matriz C")
print(matrix_c)
print("Matriz de transicion simple : Matriz A x Matriz C")
dot_simple = matrix_a.dot(matrix_c)
print("- El primer elemento (fila 1) es (1*5) + (2*10) = 25")
print("- El tercer elemento (fila 2) es (3*5) + (4*10) = 55")
print(dot_simple)


print("Producto punto de las dos matrices: Matriz A x Matriz B")
dot_product = np.dot(matrix_a, matrix_b)
print("El resultado es una nueva matriz donde:")
print("- El primer elemento (fila 1, columna 1) es (1*5) + (2*7) = 19")
print("- El segundo elemento (fila 1, columna 2) es (1*6) + (2*8) = 22")
print("- El tercer elemento (fila 2, columna 1) es (3*5) + (4*7) = 43")
print("- El cuarto elemento (fila 2, columna 2) es (3*6) + (4*8) = 50")
print(dot_product)

print("-"*50)
print("# --NP.EYE--: Crea una matriz identidad")
identity_matrix = np.eye(3)
print("Matriz identidad de 3x3")
print(identity_matrix)
# identity_matrix == array([[1., 0., 0.],
#                           [0., 1., 0.],
#                           [0., 0., 1.]])

identity_matrix = np.eye(3, k=-1)
print("Matriz identidad inversa de 3x3")
print(identity_matrix)
print("-"*50)
print("# --NP.HSTACK--: Apila arrays horizontalmente")

arr1 = np.array([1, 2, 3])
arr2 = np.array([4, 5, 6])
print(arr1)
print(arr2)
hstacked = np.hstack((arr1, arr2))
print("# Concatenar dos arrays en uno unidimensional")
print(hstacked)
# hstacked == array([1, 2, 3, 4, 5, 6])
print("-"*50)
print("# --NP.LINESPACE--: Genera números equiespaciados en un intervalo")
print("primer argumento(0)== inicio, segundo argumento(10)== fin tercer argumento(5)==longitud del array")
linspace_arr = np.linspace(0, 10, 5)
print(linspace_arr)
# linspace_arr == array([ 0.,  2.5,  5.,  7.5, 10.])
print("-"*50)

print("# --NP.MEDIAN--: Calcula la mediana de los elementos de un array con numeros impares")
arr_impar = np.array([1, 2, 3, 4, 5])
print(arr_impar)
median_value = np.median(arr_impar)
print(median_value)
# median_value == 3
print ("--NP.MEDIAN--: Tambien calcula la mediana de los elementos de un array con numeros pares")
arr_par = np.array([1, 2, 3, 4])
print(arr_par)
median_value = np.median(arr_par)
print(median_value)
# median_value == 2.5
print("-"*50)
print("#--STATS.MODE--: Calcula la moda de los elementos de un array (requiere scipy)")
arr_moda_success = np.array([1, 2, 2, 3, 3, 3,3])
print(arr_moda_success)
mode_value = stats.mode(arr_moda_success)
print(mode_value)
print("Resultado:",mode_value[0])
print("Conteo:",mode_value[1])

print("..ARRAY SIN MODA..: Saldra el numero menor y el conteo 1")
arr_moda_fail = np.array([3,2,4,5,6])
print(arr_moda_fail)
mode_value = stats.mode(arr_moda_fail)
print(mode_value)
print("Resultado:",mode_value[0])
print("Conteo:",mode_value[1])
print("-"*50)
print("--NP.MEAN--: Calcula la media de los elementos de un array")
arr_media = np.array([1, 2, 3, 4])
print(arr_media)
mean_value = np.mean(arr_media)
print("Media:",mean_value)
# mean_value == 2.5
print("-"*50)
print("# --NP.LINALG.NORM--: Calcula la norma de un array de x cantidad, tambien la distancia entre dos arrays")
arr_norm = [3, 4]
print(arr_norm)
norm = np.linalg.norm(arr_norm)
print("Norma:",norm,)
# norm == 3^2 + 4^2 = 25raiz = 5
print("En caso de distancia: [1, -1] y [0, 1]")
v = np.array([1, -1])
u = np.array([0, 1])
print(np.linalg.norm(v - u))
#Resultado = 2.23606797749979
print("-"*50)
print("# --NP.ONES--: Crea un array lleno de unos")
ones_arr = np.ones((2, 3))
print("Array de unos de 2x3")
print(ones_arr)
# ones_arr == array([[1., 1., 1.],
#                    [1., 1., 1.]])
print("-"*50)
print("# --NP.PROD--: Calcula el producto o multiplicacion de los elementos de un array")
arr_prod = np.array([1, 2, 3, 4])
print(arr_prod)
prod = np.prod(arr_prod)
print("Producto:",prod)
# prod == 24
print("-"*50)
print("# --NP.PERCENTILE--: Calcula el percentil de los datos a lo largo de un eje")
data = [10, 20, 15, 25, 30, 5, 40, 35, 50, 45, 60, 55, 70, 65, 75, 80]
print("Calcular percentiles clave por posición relativa ((q/100)*(len(data)-1))")
print("Luego interpolacion lineal: index1 + (q/100)*(index2-index1) ---- y ese resultado sumado con la posicion relativa")
print("Datos:", data)
print("Percentil 0 (Mínimo):", np.percentile(data, 0))    # Mínimo = 5
print("Percentil 25 (Primer cuartil):", np.percentile(data, 25))  # Primer cuartil = 23.75
print("Percentil 50 (Mediana):", np.percentile(data, 50))  # Mediana 42.5
print("Percentil 75 (Tercer cuartil):", np.percentile(data, 75))  # Tercer cuartil = 61.25
print("Percentil 100 (Máximo):", np.percentile(data, 100))  # Máximo = 80

print("-"*50)
print("# --NP.RESHAPE--: Cambia la forma de un array sin cambiar sus datos")
arr_reshape = np.array([1, 2, 3, 4, 5, 6])
print(arr_reshape)
reshaped = np.reshape(arr_reshape, (2, 3))
print(reshaped)
print("Sin array existente de 6 elementos de 0 a 5")
reshaped = np.reshape(np.arange(6), (2, 3))
print(reshaped)
# reshaped == array([[0, 1, 2],
#                    [3, 4, 5]])
print("-"*50)
print("# --NP.RESIZE--: Cambia el tamaño de un array")
arr_resize = np.array([1, 2, 3])
print(arr_resize)
resized = np.resize(arr_resize, (2, 3))
print("Resize de 1 a 2x3")
print(resized)
# resized == array([[1, 2, 3],
#                   [1, 2, 3]])
print("-"*50)
print("# --NP.RAVEL--: Aplana un array")
arr_ravel = np.array([[1, 2, 3], [4, 5, 6]])
print(arr_ravel)
raveled = np.ravel(arr_ravel)
print("Array aplanado")
print(raveled)
# raveled == array([1, 2, 3, 4, 5, 6])
print("-"*50)
print("# --NP.SUM--: Calcula la suma de los elementos de un array")
arr_sum = np.array([1, 2,3, 4])
print(arr_sum)
sum_arr = np.sum(arr_sum)
print("Suma:",sum_arr)
# sum_arr == 10
print("-"*50)
print("# --NP.SAVE--: Guarda un array en un archivo binario")
save_arr = np.array([1, 2, 3])
np.save('save_array.npy', save_arr)
load_arr = np.load('save_array.npy')
print(load_arr)
print("-"*50)
print("# --NP.STD--: Calcula la desviación estándar de los elementos de un array")
arr_std = [1, 2, 3, 4]
std_dev = np.std(arr_std)
"""Media: μ = (1 + 2 + 3 + 4) / 4 = 2,5
Desviaciones: (1 - 2,5)² = 2,25, (2 - 2,5)² = 0,25, (3 - 2,5)² = 0,25, (4 - 2,5)² = 2,25
Suma de las varianzas: 2,25 + 0,25 + 0,25 + 2,25 = 5
Varianza muestral: 5 / (4 - 1) = 1,67
Desviación estándar: √1,67 ≈ 1,11"""
print("Desviación estándar:",std_dev)

# std_dev == 1.118033988749895
print("-"*50)
print("# --NP.SAVEZ--: Guarda varios arrays en un archivo comprimido")
print("Array 1")
print(arr1)
print("Array 2")
print(arr2)
np.savez('arrays.npz', arr1=arr1, arr2=arr2)
load_arrays = np.load('arrays.npz')
print("Cargando array 1")
print(load_arrays['arr1'])
print("Cargando array 2")
print(load_arrays['arr2'])
print("-"*50)
print("# --NP.VSTACK--: Apila arrays verticalmente")
arr1 = np.array([1, 2, 3])
print(arr1)
arr2 = np.array([4, 5, 6])
print(arr2)
print("Concatenacion vertical")
vstacked = np.vstack((arr1, arr2))
print(vstacked)
# vstacked == array([[1, 2, 3],
#                    [4, 5, 6]])
print("-"*50)
print("# --NP.ZEROS--: Crea un array lleno de ceros")
zeros_arr = np.zeros((2, 3))
print("Array de ceros de 2x3")
print(zeros_arr)
# zeros_arr == array([[0., 0., 0.],
#                     [0., 0., 0.]])
import pandas as pd
#import openpyxl

#DATASET
print('*'*50)
print("DATASET")

df = pd.read_csv('../files/ModalidadVirtual.csv')

print(df)

print(df['carrera'][1])
print(df['edad'] > 23)
print("Booleans")
filtrar = df['edad']>23
##Con todos lso datos
print("Con todos los datos")
df_filtrar = df[filtrar]
print(df_filtrar)
print("Primeras 10 columnas")
print(df.head(10))

#df_excel = pd.read_excel('estudiantes.xlsx')
#df_excel.to_csv('estudiantes.csv',index=None,header=True)

df_conv = pd.read_csv('estudiantes.csv')
print(df_conv)


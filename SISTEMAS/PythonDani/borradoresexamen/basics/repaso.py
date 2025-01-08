import math

print("Hello World")
print (type("Hello World"))

# Variables
a = 10
b = 20

#Funciones
def suma(a, b):
    return a + b

#Match case

match a:
    case 10:    
        print("a es 10")
        
    case 20:
        print("a es 20")
        
    case _:
        print("a no es 10 ni 20")



#Arrays
arr = [1, 2, 3, 4, 5, True, 'Hello world', 3.1415]
print (arr[:2])


a = "Ahora esto es un texto"
print (type(a))
a = 3.1415
print (type(a))

a+=14
print (a)

b = math.pow(a,2)
print (b)

print(f"El cuadrado de {a.__format__('.2f')} es {b.__format__('.2f')}")


#Lista

myList = [a,True,3.1415,"Adrian",[1,2,3]]


myDict = {
    "name": "Adrian",
    "age":a,
    "isDeveloper": True,
    "country": "Spain"
}

print (myDict["name"])
print (type(myDict))
for i in myDict:
    print (f"{i}: {myDict[i]}")


tupla = (1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    



if (1 in tupla):
    print ("1 esta en la tupla")
else:
    print ("1 no esta en la tupla")
    


for i in range (1, 11,2):
    print (i)  
    
    
    
def fizzbuzz(n):
    for i in range(1, n+1):
        if i % 3 == 0 and i % 5 == 0:
            print("FizzBuzz")
        elif i % 3 == 0:
            print("Fizz")
        elif i % 5 == 0:
            print("Buzz")
        else:
            print(i)   
            
fizzbuzz(15)  


class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age   
        
    def getName (self):
        return self.name    
    
    def setName (self, name):
        self.name = name      
    def toString (self):
        return f"Name: {self.name}, Age: {self.age}"      


person = Person("Adrian", 20)
print (type(person))
print (person.getName())
person.setName("Adrian2")
print (person.getName())
print (person.toString())
print (type(3+1j))
    








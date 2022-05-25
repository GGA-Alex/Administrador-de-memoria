# Administrador de memoria

Desarrollar un programa que simule los algoritmos de ajuste de memoria:

* Primer ajuste.
* Mejor ajuste.
* Peor ajuste.

## Funcionamiento e instrucciones

Crea un array con 12 registros.

[1700, 1500, 900, 5000, 700, 2000, 1800, 3000, 1000, 1200, 2400, 4000]

**Instrucciones:**

1. Crear 32 archivos con tamaño aleatorio desde 300kb hasta 2100 kb.
2. Solicitar al usuario el algoritmo que se va a utilizar: mejor ajuste, primer ajuste o peor ajuste.

**Funcionamiento:**

3. Tomar los 32 archivos (sólo nombre y tamaño), recorrer el array mediante el algoritmo seleccionado por el usuario para almacenar el archivo (nombre y tamaño del archivo).
4. Al terminar de recorrer el array, realizar un segundo recorrido utilizando el mismo algoritmo para intentar almacenar el mayor número de archivos (nombres) posible.

**Entradas y salidas:**

Entradas:
- Nombre de archivo.
- Tamaño de archivo.

Salidas:
- Los nombres de los archivos que fueron guardados exitosamente, su ubicación en el array y su tamaño.
- La ubicación y el espacio no utilizado de cada registro del array.


Ejemplo:
Un archivo de 700kb y uno de 900kb caben en un mismo registro (1600kb) y sobran 500kb posiblemente para otro archivo de 500kb.

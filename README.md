# Trabajo Práctico 1 – U1 (POO y TAD en Java)

### **Identificación del Grupo:** g_t[q]  "Grupo 24" ## 

### **Integrantes:**
* Lopez Guerreros, Sebastian Alejandro, 6153672, TQ
* Oviedo Fernandez, Blas Oviedo, 7037075, TQ

## Declaración de Honor
• Nostros Sebastian Lopez y Blas Oviedo:
• No hemos discutido el código fuente de nuestra tarea con ningún otro
grupo, solo con el Profesor o el AER.
• No hemos usado código obtenido de otro estudiante o de cualquier otra
fuente no autorizada, modificada o no modificada.
• Cualquier código o documentación utilizada en nuestro programa
obtenido de fuentes, tales como libros o notas de curso, han sido claramente
indicada en nuestra tarea.



## Decisiones de Diseño
### **1. Clase BufferHistoryManager**
Decidimos agregar esta clase a modo de simplificar la interacción de la lista con historial para que maneje las llamadas a la clase `HistorialEdicion` creando las instancias necesarias de las clases heredadas de `Comando` para realizar cierta operación sin necesidad de realizar todo el proceso de forma manual, simplemente cree una instancia de `BufferHistoryManager` y mediante esa instancia realice las operaciones.

```diff
< BufferGap<Character> bf = new BufferGap<>();
< HistorialEdicion history = new HistorialEdicion();
< history.ejecutar( new ComandoInsertar( bf, 'A') );

> BufferHistoryManager history = new BufferHistoryManager( new BufferGap<Character>() );
> history.insertar('A');
```

### **2. Ubicación del hueco al duplicar capacidad**
Al agotarse la capacidad y duplicar el tamaño del arreglo interno, decidimos ubicar el nuevo hueco **exactamente en la posición actual del cursor**. Esta decisión responde al patrón de uso natural de un editor: si el usuario agota el espacio al escribir texto de corrido, continuará insertando caracteres inmediatamente en ese mismo lugar. Al mantener el hueco ahí, las operaciones subsecuentes de `insertar()` pueden aprovechar este espacio al instante sin necesidad de trasladar elementos de un lado a otro.

### **3. Estado guardado por cada Comando (Deshacer/Rehacer)**
Para cumplir con el requerimiento de guardar el estado mínimo necesario para revertirse:
* **ComandoInsertar:** Guarda únicamente el `Character` insertado. Para deshacer, basta con eliminar el elemento en la posición actual del cursor.
* **ComandoBorrar:** Guarda el `Character` que fue borrado en la ejecución. Esto es indispensable ya que, si no se almacena, la información se perdería permanentemente y sería imposible restaurar el estado previo al deshacer.
* **ComandoMoverCursor:** Guarda el `delta` (el desplazamiento relativo en cantidad de posiciones). Para deshacer el movimiento, simplemente se mueve el cursor de forma inversa (`-delta`).
* **buffer y state** (en los 3 comandos) **:** Guarda en `buffer` la lista de la clase BufferGap en el cual se realiza la operación y en `state` el estado de la operación ya sea `true` si se realizó la operación o `false` si no, a modo de controlar la posibilidad de revertir o rehacer la operación.

### **4. Invalidez de la Pila de Rehacer**
Cuando el usuario ejecuta un comando nuevo (por ejemplo, insertar un nuevo carácter) después de haber deshecho acciones previas, **la pila de rehacer se limpia por completo**. Esto ocurre porque la historia del documento se bifurca; los estados futuros guardados en la pila de rehacer quedan invalidados, ya que sus índices y offsets ya no concuerdan con el estado del buffer actual. 

### **5. Justificación de Excepciones**
* **`BufferVacioException` (Chequeada):** Borrar sobre un documento vacío es una situación predecible del flujo de uso normal (como mantener presionada la tecla *backspace*). Al ser chequeada, obligamos al código a preverla y capturarla adecuadamente sin detener el programa.
* **`PosicionInvalidaException` (No Chequeada):** Intentar acceder o mover el cursor a una posición fuera de los límites lógicos del buffer representa un error de lógica de programación (un bug). Al derivar de `RuntimeException`, indicamos que es responsabilidad del programador prevenir esta llamada ilegítima.



## Análisis de Desplazamientos

Tabla generada al insertar 10.000 caracteres aleatorios en la posición `N/2` del buffer:

| N (Caracteres iniciales) | Desplazamientos BufferGap | Desplazamientos Arreglo Simple |
|--------------------------|---------------------------|--------------------------------|
| **100.000**              | 0                         | 500.000.000                    |
| **200.000**              | 0                         | 1.000.000.000                  |
| **300.000**              | 0                         | 1.500.000.000                  |
| **400.000**              | 0                         | 2.000.000.000                  |
| **500.000**              | 0                         | 2.500.000.000                  |
| **600.000**              | 0                         | 3.000.000.000                  |
| **700.000**              | 0                         | 3.500.000.000                  |
| **800.000**              | 0                         | 4.000.000.000                  |
| **900.000**              | 0                         | 4.500.000.000                  |
| **1.000.000**            | 0                         | 5.000.000.000                  |

### **Explicación de Resultados:**
La estructura `BufferGap` registra **0 desplazamientos** porque aprovecha el espacio libre ya posicionado en `N/2` tras el llamado a `moverCursor`. Al insertar 10.000 veces, solo ocupa secuencialmente las celdas del hueco sin arrastrar elementos. 
En contraparte, la implementación de un arreglo simple, al carecer de hueco, está forzada a empujar los `N/2` elementos posteriores una posición hacia la derecha por cada nueva inserción.

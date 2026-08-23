/* maneja la logica para el historial de cambios (Ctrl-y/Ctrl-z) */

public class HistorialEdicion {
    private BufferGap<Character> buffer;    // instancia de BufferGap sobre el cual realizar el historial

    /* las pilas guardan objetos del tipo comando que permiten interactuar con la lista del buffer */
    private PilaES<Comando> pilaDeshacer;      // pila para deshacer (Ctrl-z)
    private PilaES<Comando> pilaRehacer;      // pila para rehacer (Ctrl-y)


    public HistorialEdicion (BufferGap<Character> bf) {
        buffer = bf;
        pilaDeshacer = new PilaES<Comando>();
        pilaRehacer = new PilaES<Comando>();
    } // <-> end HistorialEdicion constructor

    public void ejecutar(Comando c){
        /*
            Ejecuta el comando recibido y lo apila en Deshacer, luego vacia la pila Rehacer ya que al ejecutar un nuevo comando el futuro que estaba
            en deshacer ya no es valido.
        */
        c.ejecutar();

        pilaDeshacer.apilar(c);

        pilaRehacer = new PilaES<>();
    }

    public boolean deshacer(Comando c){
        /*
            Deshace el comando ingresado (solamente si hay algo por deshacer en la pila deshacer), esto se apila en rehacer para luego poder usarlos con ctrl + z.
            Retorna falso si no hay nada por deshacer.
        */
        if(sizeDeshacer() != 0){ 
            return false;
        }

        

        
        
    }

    public boolean rehacer () {
        /*
        *Vuelve a ejecutar el último comando deshecho y lo devuelve a la pila de deshacer.
        * Retorna false si no hay nada que rehacer.
        */
        if (pilaRehacer.size() > 0) {
            pilaRehacer.desapilar().ejecutar();
            return true;
        }

        return false;
    } // <-> end rehacer method

    public int sizeDeshacer (){
        // retornaa cantidad de elementos que contiene la pila deshace
        return pilaDeshacer.size();
    }
    
    public int sizeRehacer () {
        /* retorna la cantidad de elementos que contiene pilaCtrlY */
        return pilaRehacer.size();
    } // <-> end sizeRehacer method

} // <> end HistorialEdicion class

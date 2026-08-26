public class TestHistorial {
    public static void main(String[] args) {
        /*
        * Configuracion inicial del buffer.
        */
        BufferGap<Character> bf = new BufferGap<Character>();
        
        // Insertar "HoXla"
        bf.insertar('H');
        bf.insertar('o');
        bf.insertar('X');
        bf.insertar('l');
        bf.insertar('a');
        
        bf.moverCursor(-2);
        
       
        BufferHistoryManager history = new BufferHistoryManager(bf);
        
        System.out.println("TestHistorial\n");
        System.out.println("Estado inicial: " + bf);
        System.out.println("Deshacer: " + history.sizeDeshacer() + " | Rehacer: " + history.sizeRehacer() + "\n");
        
      
        // PASO 1: Insertar '!'
        history.insertar('!');
        System.out.println("1. Insertar('!')");
        System.out.println("   Contenido: " + bf);
        System.out.println("   Deshacer: " + history.sizeDeshacer() + " | Rehacer: " + history.sizeRehacer());
        System.out.println();
        
        // PASO 2: Insertar '?'
        history.insertar('?');
        System.out.println("2. Insertar('?')");
        System.out.println("   Contenido: " + bf);
        System.out.println("   Deshacer: " + history.sizeDeshacer() + " | Rehacer: " + history.sizeRehacer());
        System.out.println();
        
        // PASO 3: deshacer() → true
        boolean r3 = history.deshacer();
        System.out.println("3. deshacer() → " + r3);
        System.out.println("   Contenido: " + bf);
        System.out.println("   Deshacer: " + history.sizeDeshacer() + " | Rehacer: " + history.sizeRehacer());
        System.out.println();
        
        // PASO 4: deshacer() → true
        boolean r4 = history.deshacer();
        System.out.println("4. deshacer() → " + r4);
        System.out.println("   Contenido: " + bf);
        System.out.println("   Deshacer: " + history.sizeDeshacer() + " | Rehacer: " + history.sizeRehacer());
        System.out.println();
        
        // PASO 5: rehacer() → true
        boolean r5 = history.rehacer();
        System.out.println("5. rehacer() → " + r5);
        System.out.println("   Contenido: " + bf);
        System.out.println("   Deshacer: " + history.sizeDeshacer() + " | Rehacer: " + history.sizeRehacer());
        System.out.println();
        
        // PASO 6: MoverCursor(-4)
        history.moverCursor(-4);
        System.out.println("6. MoverCursor(-4)");
        System.out.println("   Contenido: " + bf);
        System.out.println("   Deshacer: " + history.sizeDeshacer() + " | Rehacer: " + history.sizeRehacer());
        System.out.println();
        
        // PASO 7: rehacer() → false
        boolean r7 = history.rehacer();
        System.out.println("7. rehacer() → " + r7);
        System.out.println("   Contenido: " + bf);
        System.out.println("   Deshacer: " + history.sizeDeshacer() + " | Rehacer: " + history.sizeRehacer());
        System.out.println();
        
        // PASO 8: deshacer() → true
        boolean r8 = history.deshacer();
        System.out.println("8. deshacer() → " + r8);
        System.out.println("   Contenido: " + bf);
        System.out.println("   Deshacer: " + history.sizeDeshacer() + " | Rehacer: " + history.sizeRehacer());
        System.out.println();
        
        // PASO 9: Borrar()
        history.borrar();
        System.out.println("9. Borrar()");
        System.out.println("   Contenido: " + bf);
        System.out.println("   Deshacer: " + history.sizeDeshacer() + " | Rehacer: " + history.sizeRehacer());
        System.out.println();
        
        // PASO 10: deshacer() → true
        boolean r10 = history.deshacer();
        System.out.println("10. deshacer() → " + r10);
        System.out.println("    Contenido: " + bf);
        System.out.println("    Deshacer: " + history.sizeDeshacer() + " | Rehacer: " + history.sizeRehacer());
        System.out.println();
        
        // PASO 11: deshacer() → true
        boolean r11 = history.deshacer();
        System.out.println("11. deshacer() → " + r11);
        System.out.println("    Contenido: " + bf);
        System.out.println("    Deshacer: " + history.sizeDeshacer() + " | Rehacer: " + history.sizeRehacer());
        System.out.println();
        
        // PASO 12: deshacer() → false
        boolean r12 = history.deshacer();
        System.out.println("12. deshacer() → " + r12);
        System.out.println("    Contenido: " + bf);
        System.out.println("    Deshacer: " + history.sizeDeshacer() + " | Rehacer: " + history.sizeRehacer());
        System.out.println();
        
    }
}

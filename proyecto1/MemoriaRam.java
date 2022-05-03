import java.util.*;
import java.util.stream.Collectors;

public class MemoriaRam {
 
    int ram[];
    LinkedList<Proceso> procesos;
    boolean memoriaLlena;

    MemoriaRam(int tamRam){
        ram = new int[tamRam];
        procesos = new LinkedList<Proceso>();
        memoriaLlena = false;
    }

    /**----------------------------------------------------------------*/

    /**EN CASO DE QUE LA MEMORIA ESTE LLENA DE LLAMA A ESTA FUNCION
     * filtra los procesos en la memoria ram que se puedan pasar a la memoria virtual para
     * pasar el de mayor tamanio a memoria virtual si es que tiene espacio
    */
    public void liberarEspacio(int tamano, MemoriaVirtual mv) throws CustomException{

        Comparator<Proceso> porTamano = (Proceso ps1, Proceso ps2) -> 
        Integer.valueOf(ps2.tamano).compareTo(Integer.valueOf(ps1.tamano));

        List<Proceso> moverProcesos = procesos.stream() 
                                            .filter(proceso -> proceso.estado == 'w')
                                            .sorted(porTamano)
                                            .collect(Collectors.toList());


        List<Proceso> procesosr = procesos.stream() 
                                            .filter(proceso -> proceso.estado == 'r')
                                            .sorted(porTamano)
                                            .collect(Collectors.toList());


        moverProcesos.addAll(procesosr);


        /**si la lista esta vacia es por que todos los procesos en ram tiene el estado de ejecucion (X) */
        
        if (moverProcesos.isEmpty()) 
            throw new CustomException("Todos los procesos en Ram estan en ejecucion");
        
        
        /** se intenta liberar proceso por proceso hasta que tenga espacio suficiente para insertar el nuevo proceso  */
        int[] espacio;

        do {
            
            if (moverProcesos.isEmpty()) 
            throw new CustomException("No hay espacio suficiente en RAM para agregar el proceso");
            

            if (!(moverProceso(moverProcesos.get(0).pid, mv))) 
                 System.out.println("no hay suficiente espacion en Memoria Virtual, proceso " + moverProcesos.get(0).pid + " eliminado");   

                 
            eliminarProceso(moverProcesos.get(0).pid);
            moverProcesos.remove(0);


            espacio = verificarEspacio(tamano);
            
        } while ( !(espacio[0] >= tamano));   
         
    }
    
    
    public boolean moverProceso(int pid, MemoriaVirtual mv) {
        
        Proceso p;

        List<Proceso> procces= procesos.stream().filter( proceso -> proceso.pid == pid).
                                                                collect(Collectors.toList());

        p = procces.get(0);


        if (!(mv.agregarProceso(p))) 
            return false;
        
        return true;
    }

    /**devuelve true si se logro agregar el proceso a RAM*/
    public boolean agregarProceso(Proceso proceso) {
        
        int[] espacioLibre = verificarEspacio(proceso.tamano); //verifica si cabe el proceso en memoria

        if ( !(espacioLibre[0]>= proceso.tamano)) 
            return false; // retorna false si el proceso no cabe en ram

        
        for (int i = espacioLibre[1]; i < (espacioLibre[1] + proceso.tamano); i++) { // agrega el pid del proceso a ram
            ram[i] = proceso.pid;
        }

        proceso.inicio = espacioLibre[1];
        proceso.fin = (espacioLibre[1] + proceso.tamano);
        
        this.procesos.add(proceso); //agrega el proceso a la lista de procesos

        espacioLibre = verificarEspacio(proceso.tamano);

        if (espacioLibre[0] == 0)
            this.memoriaLlena = true;
        else
            this.memoriaLlena = false;

        return true;
    }


    /** retorna el tamano del espacio mas grande en ram, asi como la posicion de inicio y fin */
    public int[] verificarEspacio( int tam){
                                                        
        int tamPidActual = 0;
        boolean vacio = false;
        int inicio = 0;
        int end = 0;
                                                        
        for (int i = 0; i < this.ram.length; i++) {
            if (this.ram[i] == 0 && !vacio) {
                vacio = true;
                inicio = i;
            }
            if (!(ram[i] == 0) && vacio) {
                end = i;
                vacio = false;
                tamPidActual = end - inicio;
                                                        
                if (tamPidActual >= tam)
                    break;
            }
        }
        if (vacio) {
            end = ram.length;
            tamPidActual = end - inicio;
        }


        int[] datos = {tamPidActual, inicio, end};
        return datos;
    }


    public boolean eliminarProceso(int pid){

        if (procesos.stream().anyMatch(p -> p.pid == pid)) {
            
            procesos.removeIf(p -> p.pid == pid);
    
            for (int i = 0; i < ram.length; i++) { 
                if (ram[i] == pid) {
                    ram[i] = 0;
                }
            }
    
            int[] espacioLibre = verificarEspacio(32);
    
            if (espacioLibre[0] == 0)
                this.memoriaLlena = true;
            else
                this.memoriaLlena = false;

            return true;
        }else{
            return false;
        }

    }
    
    public void imprimirRam() {
        String rojo = "\u001B[31m";
        String amarillo = "\u001B[33m";
        String verde = "\u001B[32m";
        String blanco = "\u001B[37m";

        Map<Integer, Character> map = procesos.stream()
                .collect(Collectors.toMap(p -> p.pid, p -> p.estado));


        System.out.println("\nRAM: " + ram.length);

        System.out.print("\n[");
        
        for (int i = 0; i < ram.length; i++) {
            
            if (ram[i] == 0) {
                System.out.print(ram[i] + ",");
            }else if (map.get(ram[i]) == 'x') {
                System.out.print(rojo + ram[i] + "," + blanco);
            } else if(map.get(ram[i]) == 'r') {
                System.out.print(amarillo + ram[i] + "," + blanco);
            } else if(map.get(ram[i]) == 'w'){
                System.out.print(verde + ram[i] + "," + blanco);
            }
        }
            
        System.out.println("]");
        
    }
    

    public void imprimirBitMap() {
        String b = "\u001B[0m"; // borrar
        String rojo = "\033[31m";

        int contador = 0;
        System.out.println("\n\n -------- Mapa de bits --------\n");
        for (int i = 0; i < ram.length; i++) {
            if (ram[i] != 0 ) {
                System.out.print(rojo + "[1] " + b);
            } else {
                System.out.print("[0] ");
            }
            if (contador < 7) {
                contador++;
            } else {
                System.out.println("\n");
                contador = 0;
            }
        }
    }
    

    public void imprimirListaEnlazada() {
        String b = "\u001B[0m"; // borrar
        String Rojo = "\033[31m";
        String verde = "\033[32m";

        int inicio = 0;
        int end = 0;
        int pidAnt = 0;

        System.out.println("\nLista enlazada\n");
        for (int i = 0; i < ram.length; i++) {
            if (!(ram[i] == pidAnt)) {
                
                if (end != 0 ) {
                    System.out.print("[ " + verde + inicio + b + " ] [ " + Rojo + pidAnt + b + " ] [ " + verde + end + b
                            + " ]--> ");
                }

                pidAnt = ram[i];
                inicio = i;
            }
            end = i;
        }
        System.out.print(
                "[ " + verde + inicio + b + " ] [ " + Rojo + pidAnt + b + " ] [ " + verde + end + b + " ]--> \n");
    }
    

    public boolean finalizarLinea(){
        int pid = 0;

        for (int celda: ram) {
            if (celda != 0) {
                pid = celda;
                break;
            }
        }
        
        if (pid != 0) {
            eliminarProceso(pid);
            return true;
        }else{
            return false;
        }

    }

    public void compactar() {
        int inLib = 0;
        boolean flag = false;

        for (int i = 0; i < ram.length; i++) {
            
            if (ram[i] ==0 && flag == false) {
                inLib = i;
                flag = true;
            }
            if (ram[i] != 0 &&flag == true) {
                ram[inLib] = ram[i];
                ram[i] = 0;
                inLib++;
            }
        }
    }

}

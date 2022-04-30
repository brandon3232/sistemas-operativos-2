import java.util.*;
import java.util.stream.Collectors;

public class MemoriaVirtual extends MemoriaRam{
    
    MemoriaVirtual(int tamMV){
       super(tamMV);
    }
    

    public void liberarEspacio(int tamano) throws CustomException{

        Comparator<Proceso> porTamano = (Proceso ps1, Proceso ps2) -> 
        Integer.valueOf(ps2.tamano).compareTo(Integer.valueOf(ps1.tamano));

        List<Proceso> eliminarProcesos = procesos.stream() 
                                            .filter(proceso -> proceso.estado == 'w')
                                            .sorted(porTamano)
                                            .collect(Collectors.toList());



        /**si la lista esta vacia es por que todos los procesos en Memoria Virtual tiene el estado de listo (r) */
        
        if (eliminarProcesos.isEmpty()) 
            throw new CustomException("Todos los procesos en Ram estan en estado de Listo (r)");
        
        
        /** se intenta liberar proceso por proceso hasta que tenga espacio suficiente para insertar el nuevo proceso  */
        int[] espacio;

        do {
                
            eliminarProceso(eliminarProcesos.get(0).pid);
            eliminarProcesos.remove(0);

            espacio = verificarEspacio(tamano);
            
        } while ( !(espacio[0] >= tamano));   
         
    }

    public void imprimirRam() {
        String rojo = "\u001B[31m";
        String amarillo = "\u001B[33m";
        String verde = "\u001B[32m";
        String blanco = "\u001B[37m";

        Map<Integer, Character> map = procesos.stream()
                .collect(Collectors.toMap(p -> p.pid, p -> p.estado));


        System.out.println("\nMemoria Virtual: " + ram.length);

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

}

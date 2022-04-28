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


}

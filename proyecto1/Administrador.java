public class Administrador {

    
    MemoriaRam ram = new MemoriaRam(32); // define tamano de ram
    MemoriaVirtual mv = new MemoriaVirtual(32);// define tamano de memoria virtual

    public static void main(String[] args) {

        Administrador admin = new Administrador();
        int opcion = 0; 
        
        while (opcion != 5) {
            opcion = UI.menu();

            switch (opcion) {
                case 1:
                    
                    admin.nuevoProceso();

                    break;
                
                case 2:
    
                    break;
                                    
                case 3:
            
                    break;
                default:
                    break;
            }
            





        }

    }
    
    

    public void nuevoProceso(){
        
        int[] proceso = UI.menuNuevoProceso();
        Proceso p1 = new Proceso(proceso[0], proceso[1]);
        
        int[] espacioLibre = ram.verificarEspacio(p1.tamano);
        
        if ((ram.memoriaLlena || espacioLibre[0] < p1.tamano) && !(p1.estado == 'x')) {
            
            if (mv.agregarProceso(p1)) {
                System.out.println("El proceso " + p1.pid + "se agrego correctamente a la Memoria Virtual ");
            }else {
                System.out.println("No hay espacio suficiente en Memoria Virtual, el proceso " + p1.pid + "se elimino");
            }
            
        }


        if ((ram.memoriaLlena || espacioLibre[0] < p1.tamano) && p1.estado == 'x') {
            
            try {

                ram.liberarEspacio(proceso[2], mv);
                Proceso p = new Proceso(proceso[0], proceso[1]);
                
                if (ram.agregarProceso(p))
                    System.out.println("El proceso " + p.pid + " se agrego correctamente");    
                else 
                    System.out.println("Error al agregar el proceso en RAM " + p.pid);    
                    

            } catch (CustomException e) {
                
                System.out.println("Error: " + e.getMessage());

                switch (UI.menuAgregarOEliminar()) {
                    case 1:
                        
                            Proceso p = new Proceso(proceso[0], proceso[1]);
                            p.estado = 'r';

                            if (mv.agregarProceso(p)) {
                                System.out.println("Proceso " + p.pid + " se agrego correctamente a la Memoria Virtual");
                            }else{
                                int op = UI.menuAgregarOEliminarMV();

                                switch(op){
                                    case 1:

                                        try {
                                            mv.liberarEspacio(p1.tamano);

                                            Proceso p2 = new Proceso(proceso[0], proceso[1]);
                
                                            if (mv.agregarProceso(p2))
                                                System.out.println("El proceso " + p.pid + " se agrego correctamente");    
                                            else 
                                                System.out.println("Error al agregar el proceso en Memoria virtual " + p.pid);
                                            
                                        } catch (CustomException a) {
                                            System.out.println("Error: " + a.getMessage());
                                            System.out.println("El proceso " + p1.pid + "se a eliminado");
                                        }
                                        
                                        
                                        break;
                                    
                                    case 2:
                                        System.out.println("Proceso eliminado\n");

                                        break;

                                default:
                                System.out.println("Opcion invalida, se cancelo la ultima accion");
                                    break;

                                }
                            }
                            
                        
                        break;
                
                    case 2:
                        System.out.println("Proceso eliminado\n");
    
                        break;
                    default:
                    System.out.println("Opcion invalida, se cancelo la ultima accion");
                        break;
                }
            }
            
        }else{

            Proceso p = new Proceso(proceso[0], proceso[1]);
                
            if (ram.agregarProceso(p))
                System.out.println("El proceso " + p.pid + " se agrego correctamente");    
            else 
                System.out.println("Error al agregar el proceso " + p.pid);

        }


    }
    
}

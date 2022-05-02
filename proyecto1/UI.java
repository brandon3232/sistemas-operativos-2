import java.util.Scanner;

public class UI {

    public static int menu() {

        Scanner leer = new Scanner(System.in);

        System.out.println("\n----------MENU---------");
        System.out.println("[1] Agregar proceso");
        System.out.println("[2] Finalizar");
        System.out.println("[3] Modificar tamanio");
        System.out.println("[4] Imprimir RAM");
        System.out.println("[5] Salir");
        System.out.print("Selecciones una opcion: ");
        
        int opcion = leer.nextInt();

        return opcion;
    }

    public static int[] menuNuevoProceso(){
        
        Scanner leer = new Scanner(System.in);

        System.out.println("\n");
        System.out.println("Ingrese el pid del proceso: ");
        
        int pid = leer.nextInt();

        System.out.println("Ingrese el tamanio del proceso: ");
        
        int tamano = leer.nextInt();
        
        int[] pidTam = {pid, tamano};
        return pidTam;
        
    }
    public static int menuAgregarOEliminar(){
        Scanner leer = new Scanner(System.in);

        System.out.println("\n");
        System.out.println("no se puede agregar el proceso a RAM:");
        System.out.println("[1] Enviar proceso a Memoria Virtual");
        System.out.println("[2] Eliminar proceso");
        System.out.println("elija una opcion: ");
        
        int op = leer.nextInt();

        return op;
    }

    public static int menuAgregarOEliminarMV(){
        Scanner leer = new Scanner(System.in);

        System.out.println("\n");
        System.out.println("no se puede agregar el proceso a la Memoria Virtual:");
        System.out.println("[1] Liberar espacio en Memoria Virtual");
        System.out.println("[2] Eliminar proceso");
        System.out.println("elija una opcion: ");
        
        int op = leer.nextInt();

        return op;
    }

    public static int menuFinalizar(){
        Scanner leer = new Scanner(System.in);

        System.out.println("\n");
        System.out.println("Finalizar:");
        System.out.println("[1] Finalizar por linea (RAM)");
        System.out.println("[2] Finalizar por pid");
        System.out.println("elija una opcion: ");
        
        int op = leer.nextInt();
        return op;
    }

    public static int eliminarPid(){
        Scanner leer = new Scanner(System.in);

        System.out.println("\n");
        System.out.println("Ingrese el pid del proceso a eliminar:");

        int pid = leer.nextInt();

        return pid;
        
    }

}
import java.util.Scanner;

public class RamConAbstraccion {

    public static Proceso verificarMemoria(String ram[]){ //TODO: arreglar bug: al eliminar una proceso y agregar uno nuevo se agrega al final no importa el tamanio
        int tamPidActual = 0;
        int tamPidAnterior = 0;
        boolean vacio = false;
        int inicio = 0;
        int end = 0;

        for (int i = 0; i < ram.length; i++) {
            if (ram[i] == "0" && vacio == false) {
                vacio = true;
                inicio = i;
            }
            if (ram[i] != "0" && vacio == true) {
                end = i;
                vacio = false;
                tamPidActual = end - inicio;
                if (tamPidActual > tamPidAnterior)
                    tamPidAnterior = tamPidActual;
            }
        }
        if (vacio == true) {
            end = ram.length;
            tamPidActual = (end + 1) - inicio;
            if (tamPidActual > tamPidAnterior)
                tamPidAnterior = tamPidActual;
        }
        Proceso process = new Proceso(0,tamPidAnterior,inicio,end);
        return process;
    }

    public static int crecimientoDeEspacio(int tam) {
        
        double porcentaje = 0.20;
        double tamBase =  tam;
        double tamExtended = tam * porcentaje;
        return (int) Math.ceil(tamExtended);

    }
    
    public static int addProcess(String ram[],int tam){
        int pidint = (int) (Math.random() * 100+1);
        String pid = Integer.toString(pidint);
        int contador = 0;
        Proceso proceso = verificarMemoria(ram);

        if (tam <= proceso.tamano) {

            int tamExpandido = tam + crecimientoDeEspacio(tam);

            for (int i = proceso.inicio; i < proceso.end; i++) { 
                if (contador < tamExpandido) {
                    if (contador < tam) {
                        ram[i] = pid;    
                    }else{
                        ram[i] = pid + "E";
                    }
                    contador++;
                }
            }
            return Integer.parseInt(pid);
        } else
            return -1;
        
    }

    public static boolean ejecutarProcess(String ram[], String pid){
        boolean eliminado = false;
        for (int i = 0; i < ram.length; i++) {
            if (ram[i].equals(pid) || ram[i].equals(pid + "E")){ 
                ram[i] = "0";
                eliminado = true;
            }
        }  
        return eliminado;
    }

    public static void imprimirRam(String ram[],int tamRam){

        System.out.printf("\n\nRam:%dB\n",tamRam);
        System.out.print("\n[");

        for (int i = 0; i < ram.length; i++) {
            if (i == ram.length - 1) {
                System.out.print(ram[i]);
            } else {
                System.out.printf("%s,",ram[i]);
            }
        }

        System.out.println("]\n");
    }

    public static void menu(){
        System.out.println("\n----------MENU---------");
        System.out.println("[1] Agregar proceso");
        System.out.println("[2] Ejecutar proceso");
        System.out.println("[3] Imprimir RAM");
        System.out.println("[4] Salir");
        System.out.print("Selecciones una opcion: ");
    }

    public static void imprimirBitMap(String ram[]) {
        String b = "\u001B[0m"; //borrar 
        String rojo = "\033[31m";
        String azul = "\033[34m";

        int contador = 0;
        System.out.println("\n\n -------- Mapa de bits --------\n");
        for (int i = 0; i < ram.length; i++) {
            if (ram[i] != "0") {
                System.out.print(rojo + "[1] "+ b);
            }else {
                System.out.print("[0] ");
            }
            if (contador < 7) {
                contador++;
            }else {
                System.out.println("\n");
                contador = 0;
            }
        }
    }

    public static void imprimirListaEnlazada(String ram[]) {
        String b = "\u001B[0m"; //borrar
        String Rojo = "\033[31m";
        String verde = "\033[32m";

        boolean enProceso = false;
        int inicio = 0;
        int end = 0;
        String pidAct = "0";
        String pidAnt = "0";

        System.out.println("\nLista enlazada\n");
        for (int i = 0; i < ram.length; i++) {
            if (ram[i] != pidAnt) {
                enProceso = true;
                if (end != 0) {
                    System.out.print("[ " + verde + inicio + b + " ] [ " + Rojo + pidAnt + b + " ] [ " + verde + end + b +" ]--> ");
                }
                
                pidAnt = ram[i];
                inicio = i;
            }
            end = i;
        }
        System.out.print("[ " + verde + inicio + b + " ] [ " + Rojo + pidAnt + b + " ] [ " + verde + end + b +" ]--> \n");
    }

    public static void llenarRam(String ram[]) {
        for (int i = 0; i < ram.length; i++) {
            ram[i] = "0";
        }
    }


    public static void main(String[] args) {
        
        int tamRam = 32;
        String[] ram = new String[tamRam];
        llenarRam(ram);
        Scanner leer = new Scanner(System.in);
        int op = 0;
        boolean ejecutar = true;

        while (ejecutar) {
        
            menu();

            op = leer.nextInt();

            switch (op) {
                case 1:
                    System.out.println("Introdusca el tamanio del proceso");
                    int tam = leer.nextInt();
                     int pid;
                    if ((pid = addProcess(ram,tam)) > 0)
                        System.out.printf("Proceso agregado correctamente: %d\n", pid);
                    else
                        System.out.println("Error al agregar el proceso");

                    break;

                case 2:
                    System.out.println("Escribe el PID del proceso a ejecutar");
                    String npid = Integer.toString( leer.nextInt());

                    if (ejecutarProcess(ram,npid)) {
                        System.out.println("Proceso ejecutado correctamente: " + npid);
                    } else {
                        System.out.println("Error: Proceso no encontrado");
                    }

                    break;

                case 3:
                    System.out.println("\n[1] Imprimir RAM");
                    System.out.println("[2] Imprimir mapa de bits");
                    System.out.println("[3] Imprimir lista enlazada");
                    System.out.println("[4] imprimir todo");
                    System.out.print("Selecciones una opcion: ");
                    int op2 = leer.nextInt();

                    switch (op2) {
                        case 1:
                            imprimirRam(ram,tamRam);
                            break;
                        case 2:
                            imprimirBitMap(ram);
                            break;
                        case 3:
                            imprimirListaEnlazada(ram);
                            break;
                        case 4:
                            imprimirRam(ram,tamRam);
                            imprimirBitMap(ram);
                            imprimirListaEnlazada(ram);

                            break;
                    
                        default:
                            break;
                    }
                    break;
                case 4:
                    ejecutar = false;
                    break;

                default:
                    System.out.println("Opcion incorrecta");
                    break;
            }
        }
    }
}


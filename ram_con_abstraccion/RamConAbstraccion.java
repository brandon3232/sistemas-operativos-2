import java.util.Scanner;

public class RamConAbstraccion {

    public static Proceso verificarMemoria(String[] ram, int tam) { 
                                                           
        int tamPidActual = 0;
        boolean vacio = false;
        int inicio = 0;
        int end = 0;

        for (int i = 0; i < ram.length; i++) {
            if (ram[i].equals("0") && !vacio) {
                vacio = true;
                inicio = i;
            }
            if (!(ram[i].equals("0")) && vacio) {
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
        return new Proceso(0, tamPidActual, inicio, end);
        
    }

    public static int crecimientoDeEspacio(int tam) {

        double porcentaje = 0.20;
        double tamExtended = tam * porcentaje;
        return (int) Math.ceil(tamExtended);

    }

    public static int addProcess(String[] ram, int tam) {
        int pidint = (int) (Math.random() * 100 + 1);
        String pid = Integer.toString(pidint);
        int contador = 0;
        Proceso proceso;


        int tamExpandido = tam + crecimientoDeEspacio(tam);

        proceso = verificarMemoria(ram, tamExpandido);

        if (tamExpandido <= proceso.tamano) {

            for (int i = proceso.inicio; i < proceso.end; i++) {
                if (contador < tamExpandido) {
                    if (contador < tam) {
                        ram[i] = pid;
                    } else {
                        ram[i] = pid + "E";
                    }
                    contador++;
                }
            }
            return Integer.parseInt(pid);
        } else
            return -1;

    }

    public static boolean ejecutarProcess(String[] ram, String pid) {
        boolean eliminado = false;
        for (int i = 0; i < ram.length; i++) {
            if (ram[i].equals(pid) || ram[i].equals(pid + "E")) {
                ram[i] = "0";
                eliminado = true;
            }
        }
        return eliminado;
    }

    public static void imprimirRam(String[] ram, int tamRam) {

        System.out.printf("\n\nRam:%dB\n", tamRam);
        System.out.print("\n[");

        for (int i = 0; i < ram.length; i++) {
            if (i == ram.length - 1) {
                System.out.print(ram[i]);
            } else {
                System.out.printf("%s,", ram[i]);
            }
        }

        System.out.println("]\n");
    }

    public static void menu() {
        System.out.println("\n----------MENU---------");
        System.out.println("[1] Agregar proceso");
        System.out.println("[2] Ejecutar proceso");
        System.out.println("[3] Modificar tamanio");
        System.out.println("[4] Imprimir RAM");
        System.out.println("[5] Salir");
        System.out.print("Selecciones una opcion: ");
    }

    public static void imprimirBitMap(String[] ram) {
        String b = "\u001B[0m"; // borrar
        String rojo = "\033[31m";
        String azul = "\033[34m";

        int contador = 0;
        System.out.println("\n\n -------- Mapa de bits --------\n");
        for (int i = 0; i < ram.length; i++) {
            if (ram[i].equals("0") ) {
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

    public static void imprimirListaEnlazada(String[] ram) {
        String b = "\u001B[0m"; // borrar
        String Rojo = "\033[31m";
        String verde = "\033[32m";

        int inicio = 0;
        int end = 0;
        String pidAct = "0";
        String pidAnt = "0";

        System.out.println("\nLista enlazada\n");
        for (int i = 0; i < ram.length; i++) {
            if (!ram[i].equals(pidAnt)  && !ram[i].equals(pidAnt + "E")) {
                
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

    public static void llenarRam(String[] ram) {
        for (int i = 0; i < ram.length; i++) {
            ram[i] = "0";
        }
    }

    public static Proceso buscarPid(String[] ram, int pid) {

        int tam = 0;
        int expansions = 0;
        Proceso process;

        for (int i = 0; i < ram.length; i++) {
            if (ram[i].equals(Integer.toString(pid)))
                tam++;
            if (ram[i].equals(Integer.toString(pid) + "E"))
                expansions++;
        }
        if (tam == 0)
            process = new Proceso(-1);
        else
            process = new Proceso(pid, tam, expansions);

        return process;
    }

    public static void incrementarTamanio(String[] ram, Proceso process, int incremento) {
        int contador = 0;        

        for (int i = 0; i < ram.length; i++) {
            if (ram[i].equals(Integer.toString(process.pid) + "E") && contador < incremento) {
                
                ram[i] = Integer.toString(process.pid);
                contador++;
            }
        }
    }

    public static void decrementarTamanio(String[] ram, Proceso process, int decremento) {
        int contador = 0;        

        for (int i = ram.length - 1; i > 0; i--) {
            if (ram[i].equals(Integer.toString(process.pid)) && contador < decremento) {
                ram[i] = Integer.toString(process.pid) + "E";
                contador++;
            }
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
                    if ((pid = addProcess(ram, tam)) > 0)
                        System.out.printf("Proceso agregado correctamente: %d\n", pid);
                    else
                        System.out.println("Error al agregar el proceso, espacio en RAM insuficiente");
                    break;

                case 2:
                    System.out.println("Escribe el PID del proceso a ejecutar");
                    String npid = Integer.toString(leer.nextInt());

                    if (ejecutarProcess(ram, npid)) 
                        System.out.println("Proceso ejecutado correctamente: " + npid);
                    else 
                        System.out.println("Error: Proceso no encontrado");

                    break;

                case 3:
                    System.out.println("\n[1] Incrementar tamanio de proceso");
                    System.out.println("[2] Decrementar tamanio de proceso");
                    int op2 = leer.nextInt();

                    switch (op2) {
                        case 1:
                            System.out.println("Indique el PID del proceso a incrementar");
                            int pidinc = leer.nextInt();

                            Proceso process = buscarPid(ram, pidinc);

                            if (process.pid > 0) {

                                System.out.printf(
                                        "Indique la cantidad de bit a incrementar (solo puede incrementar %d bits\n)",
                                        process.tamanoExpancion);
                                int incremento = leer.nextInt();

                                if (incremento <= process.tamanoExpancion)
                                    incrementarTamanio(ram, process, incremento);
                                else
                                    System.out.printf("Error: no se puede incrementar mas de %d BIT \n",process.tamanoExpancion);

                            } else 
                                System.out.println("Error: Proceso no encontrado ");

                            break;

                        case 2:
                            System.out.println("Indique el PID del proceso a decrementar");
                            int piddec = leer.nextInt();

                            Proceso proces = buscarPid(ram, piddec);

                            if (proces.pid > 0) {

                                if (proces.tamano > 1) {
                                    
                                    System.out.printf(
                                            "Indique la cantidad de bit a decrementar (solo puede decrementar %d BITs\n)",
                                            proces.tamano - 1);
                                    int decremento = leer.nextInt();

                                    if (decremento < proces.tamano)
                                        decrementarTamanio(ram, proces, decremento);
                                    else
                                        System.out.printf("Error: no se puede decrementar mas de %d BIT \n", proces.tamano - 1);

                                }else
                                    System.out.println("ERROR: no se puede decrementar ya que el tamanio del proceso es de 1 BIT");


                            } else 
                                System.out.println("Error: Proceso no encontrado ");

                            break;

                        default:
                            break;
                    }

                    break;

                case 4:
                    System.out.println("\n[1] Imprimir RAM");
                    System.out.println("[2] Imprimir mapa de bits");
                    System.out.println("[3] Imprimir lista enlazada");
                    System.out.println("[4] imprimir todo");
                    System.out.print("Selecciones una opcion: ");
                    int op3 = leer.nextInt();

                    switch (op3) {
                        case 1:
                            imprimirRam(ram, tamRam);
                            break;
                        case 2:
                            imprimirBitMap(ram);
                            break;
                        case 3:
                            imprimirListaEnlazada(ram);
                            break;
                        case 4:
                            imprimirRam(ram, tamRam);
                            imprimirBitMap(ram);
                            imprimirListaEnlazada(ram);

                            break;

                        default:
                            break;
                    }
                    break;
                case 5:
                    ejecutar = false;
                    break;

                default:
                    System.out.println("Opcion incorrecta");
                    break;
            }
        }
    }
}

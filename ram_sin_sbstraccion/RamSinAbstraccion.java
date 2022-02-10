package ram_sin_sbstraccion;
import java.util.Scanner;

public class RamSinAbstraccion {

    public static int espaciosLibres(int ram[]){
        int contador = 0;
        for (int i : ram) {
            if (i == 0) 
                contador++;
        }
        return contador;
    }

    public static int addProcess(int ram[],int tam){
        int pid = (int) (Math.random() * 100+1);
        int contador = 0;
        if (tam <= espaciosLibres(ram)) {
            for (int i = 0; i < ram.length; i++) {
                if (ram[i] == 0 && contador < tam) {
                    ram[i] = pid;
                    contador++;
                }
            }
            return pid;
        } else
            return -1;
        
    }

    public static int ejecutarProcess(int ram[]){
        
        int tpid = 0;

        for (int i = (ram.length - 1); i >= 0; i--) {
            if (ram[i] != 0) {
                tpid = ram[i];
                for (int j = 0; j < ram.length; j++) {
                    if (ram[j] == tpid) {
                        ram[j] = 0;
                    }
                }
                break;
            }
        }
        return tpid;
    }

    public static void imprimirRam(int ram[]){

        System.out.print("\n[ ");

        for (int i = 0; i < ram.length; i++) {
            if (i == ram.length - 1) {
                System.out.print(ram[i]);
            } else {
                System.out.printf("%d, ",ram[i]);
            }
        }

        System.out.println("]\n");
    }

    public static void menu(){
        System.out.println("----------MENU---------");
        System.out.println("[1] Agregar proceso");
        System.out.println("[2] Ejecutar proceso");
        System.out.println("[3] Salir");
        System.out.print("Selecciones una opcion: ");
    }


    public static void main(String[] args) {
        
        int ram[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        Scanner leer = new Scanner(System.in);
        int op = 0;
        boolean ejecutar = true;

        while (ejecutar) {
            imprimirRam(ram);

            menu();

            op = leer.nextInt();

            switch (op) {
                case 1:
                    System.out.println("Introdusca el tamanio del proceso");
                    int tam = leer.nextInt();
                     int pid;
                    if ((pid = addProcess(ram,tam)) > 0)
                        System.out.printf("el proceso %d se agrego correctamente\n", pid);
                    else
                        System.out.println("Error al agregar el proceso");

                    break;

                case 2:
                    int tpid;
                    if ((tpid = ejecutarProcess(ram)) != 0) {
                        System.out.printf("El proceso %d se ejecuto correctamente\n", tpid);
                    } else {
                        System.out.println("memoria vacia, no hay procesos que ejecutar");
                    }
                    
                    break;

                case 3:
                    ejecutar = false;
                    break;
            
                default:
                    System.out.println("Opcion incorrecta");
                    break;
            }
        }
    }
}


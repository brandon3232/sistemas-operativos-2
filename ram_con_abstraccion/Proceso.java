public class Proceso {
    int pid;
    int tamano;
    int tamanoExpancion;
    int inicio;
    int end;

    Proceso(int pid, int tamano, int inicio, int end){
        this.pid = pid;
        this. tamano = tamano;
        this.inicio = inicio;
        this.end = end; 
        this.tamanoExpancion = 0;
    }
    Proceso(int pid, int tamano, int tamanoExpancion){
        this.pid = pid;
        this. tamano = tamano;
        this.inicio = 0;
        this.end = 0; 
        this.tamanoExpancion = tamanoExpancion;
    }

    Proceso(int pid){
        this.pid = pid;
        this. tamano = 0;
        this.inicio = 0;
        this.end = 0; 
        this.tamanoExpancion = 0;
    }

}

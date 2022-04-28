public class Proceso {
    
    int pid;
    int tamano;
    int inicio;
    int fin;
    char estado;

    Proceso(int pid, int tamano){
        this.pid = pid;
        this. tamano = tamano;
        inicio = 0;
        fin = 0;

        /** asignacion aleatoria de estado de proceso */
        int varEstado = (int) (Math.random() * 100 + 1);
        
        if (varEstado <= 40) {
            this.estado = 'x';
        }else{
            if (varEstado > 40 && varEstado <= 70) {
                this.estado = 'w';
            }else{
                if (varEstado > 70) {
                this.estado = 'r';
                }
            }
        }
    }

    


}

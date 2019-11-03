import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class UF2ACT3 extends RecursiveTask<Long> {
    long numero;
    
	public UF2ACT3(long numero){
        this.numero=numero;
    } 
    @Override
    protected Long compute() {
        // ATENCIO **1** double calcul = java.lang.Math.cos(54879854);
        if(numero <= 1) return numero;
        UF2ACT3 fib1 = new UF2ACT3(numero-1);
        //fib1.fork();
        UF2ACT3 fib2 = new UF2ACT3(numero-2);
        fib2.fork();
	 return fib1.compute()+ fib2.join();
	 }
    
    public static void main(String[] args){
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println("Calculat:  " + pool.invoke(new UF2ACT3(40)));    
    }
}

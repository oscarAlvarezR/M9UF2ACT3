import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class MaximTask extends RecursiveTask<Short> {
	//private static final int LLINDAR=10000000;
	private static final int LLINDAR=10000000;
	private short[] arr ;
	private int inici, fi;
	// Variable contador
	public static int contador = 0;

	public MaximTask(short[] arr, int inici, int fi) {
		this.arr = arr;
		this.inici = inici;
		this.fi = fi;
		// Mostrem el comptador amb tot el que demana l'activitat
		System.out.println("Comptador " + (MaximTask.contador += 1) + " Inici " + inici + " Fi " + fi);
		
	}

	private short getMaxSeq(){
		short max = arr[inici];
		for (int i = inici+1; i < fi; i++) {
			if (arr[i] > max) {
				max = arr[i];
			}
		}
		return max;
	}

	private short getMaxReq(){
		MaximTask task1;
		MaximTask task2;
		int mig = (inici+fi)/2+1;
		task1 = new MaximTask(arr, inici, mig);
		task1.fork();
		task2 = new MaximTask(arr, mig, fi);
		task2.fork();
		return (short) Math.max(task1.join(), task2.join());
	}

	@Override
	protected Short compute() {
		if(fi - inici <= LLINDAR){
			return getMaxSeq();
		}else{
			return getMaxReq();
		}
	}

	public static void main(String[] args) {

		short[] data = createArray(100000000);

		// Mira el n�mero de processadors
		System.out.println("Inici c�lcul");
		ForkJoinPool pool = new ForkJoinPool();

		int inici=0;
		int fi= data.length;
		MaximTask tasca = new MaximTask(data, inici, fi);

		long time = System.currentTimeMillis();
		// crida la tasca i espera que es completin
		int result1 = pool.invoke(tasca);
		// m�xim
		int result= tasca.join();
		System.out.println("Temps utilitzat:" +(System.
				currentTimeMillis()-time));
		System.out.println ("M�xim es " + result);
	}


	private static short [] createArray(int size){
		short[] ret = new short[size];
		for(int i=0; i<size; i++){
			ret[i] = (short) (1000 * Math.random());
			if(i==((short)(size*0.9))){
				ret[i]=1005;
			}
		}
		return ret;
	}
}
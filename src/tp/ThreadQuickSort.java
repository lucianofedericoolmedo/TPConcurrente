package tp;

import java.util.List;


public class ThreadQuickSort extends Thread {
	
	private List<Integer> values;

	public ThreadQuickSort(List<Integer> values){
		this.setValues(values);
	}
	
	public List<Integer> getValues() {
		return values;
	}

	public void setValues(List<Integer> values) {
		this.values = values;
	}
	
	@Override
	public void run() {
	
	}

	
}

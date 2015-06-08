package tp;

public class Range {
	int start;
	int end;

	public Range(int new_start, int new_end) {
		this.start = new_start;
		this.end = new_end;
	}
	
	public  synchronized boolean isValid() {
		return end - start  >= 0 ;
	}
	
	public synchronized boolean isEmpty(){
		return this.end < this.start;
	}
	
	public synchronized int size() {
		return this.end - this.start;
	}

}
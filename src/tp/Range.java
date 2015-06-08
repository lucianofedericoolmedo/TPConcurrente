package prueba;

public class Range {
	int start;
	int end;

	boolean isValid() {
		return start <= end;
	}
		
	int size() {
		return this.end - this.start;
	}

	Range(int new_start, int new_end) {
		this.start = new_start;
		this.end = new_end;
	}
}
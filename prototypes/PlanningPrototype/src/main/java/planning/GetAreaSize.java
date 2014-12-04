package planning;

public class GetAreaSize {
	private int width;
	private int height;

	public GetAreaSize() {

	}

	public GetAreaSize(int a, int b) {
		width = a;
		height = b;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	public void print(){
		System.out.println(width + ", " + height);
	}
}

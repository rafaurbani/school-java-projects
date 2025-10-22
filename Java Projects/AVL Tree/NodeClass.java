
public class NodeClass {
	public int element;
	public int height;
	public NodeClass leftNode;
	public NodeClass rightNode;

	public NodeClass(int element) {
		this.element = element;
		this.height = 1;
		this.leftNode = null;
		this.rightNode = null;
	}

	public void updateHeight() {
		int leftNodeHeight, rightNodeHeight;

		if (this.leftNode == null)
			leftNodeHeight = 0;
		else
			leftNodeHeight = this.leftNode.height;

		if (this.rightNode == null)
			rightNodeHeight = 0;
		else
			rightNodeHeight = this.rightNode.height;

		this.height = Math.max(leftNodeHeight, rightNodeHeight) + 1;
	}

	@Override
	public String toString() {
		return String.format("NODO:\n  Elemento: %d\n  Altura: %d", this.element, this.height);
	}
}

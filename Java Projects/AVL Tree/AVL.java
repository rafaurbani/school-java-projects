
public class AVL {
	public NodeClass root;

	public AVL() {
		this.root = null;
	}

	// retorna o fator de balanceamento do nodo do parâmetro
	public int getBF(NodeClass n) {
		int leftNodeHeight, rightNodeHeight;

		if (n.leftNode == null)
			leftNodeHeight = 0;
		else
			leftNodeHeight = n.leftNode.height;

		if (n.rightNode == null)
			rightNodeHeight = 0;
		else
			rightNodeHeight = n.rightNode.height;

		return (leftNodeHeight - rightNodeHeight);
	}

	// balanceia a sub-árvore
	public NodeClass rebalance(NodeClass n) {
		if (getBF(n) > 1) {
			System.out.println("Árvore balanceada");
			if (getBF(n.leftNode) >= 0) {
				return rightRotation(n);
			} else {
				leftRotation(n.leftNode);
				return rightRotation(n);
			}
		} else if (getBF(n) < -1) {
			System.out.println("Árvore balanceada");
			if (getBF(n.rightNode) <= 0) {
				return leftRotation(n);
			} else {
				rightRotation(n.rightNode);
				return leftRotation(n);
			}
		} else
			return null;
	}

	public void rebalanceTree() {
		NodeClass newRoot = rebalanceTree(root);
		if (newRoot != null) {
			root = newRoot;
		}
	}

	private NodeClass rebalanceTree(NodeClass n) {
		if (n == null)
			return null;
		
		n.updateHeight();

		NodeClass rebalancedLeftTree = rebalanceTree(n.leftNode);
		NodeClass rebalancedRightTree = rebalanceTree(n.rightNode);

		if (rebalancedLeftTree != null)
			n.leftNode = rebalancedLeftTree;
		if (rebalancedRightTree != null)
			n.rightNode = rebalancedRightTree;

		return rebalance(n);
	}

	public void search(int e) {
		if (root == null) {
			System.out.println("A árvore está vazia!");
			return;
		}

		System.out.print("Nodos procurados: " + root.element + ", ");
		NodeClass n = search(root, e, true);
		if (n == null)
			System.out.println("\nO elemento não existe!");
		else {
			System.out.println("\n" + n);
		}
	}

	public NodeClass search(NodeClass n, int e, boolean searching) {
		if (n == null)
			return null;

		if (e == n.element)
			return n;
		else if (e < n.element) {
			if (searching && n.leftNode != null)
				System.out.print(n.leftNode.element + ", ");
			return search(n.leftNode, e, searching);
		} else {
			if (searching && n.rightNode != null)
				System.out.print(n.rightNode.element + ", ");
			return search(n.rightNode, e, searching);
		}
	}

	public void insert(int e) {
		if (root == null) {
			this.root = new NodeClass(e);
			System.out.println("Nodo " + e + " inserido com sucesso!");
			return;
		}

		if (search(root, e, false) != null) {
			System.out.println("Um nodo com esse elemento já existe!");
			return;
		}

		NodeClass fatherNode = root;

		while (true) {
			if (fatherNode.element > e) {
				if (fatherNode.leftNode == null) {
					fatherNode.leftNode = new NodeClass(e);
					break;
				} else
					fatherNode = fatherNode.leftNode;
			} else {
				if (fatherNode.rightNode == null) {
					fatherNode.rightNode = new NodeClass(e);
					break;
				} else
					fatherNode = fatherNode.rightNode;
			}
		}

		System.out.println("Nodo " + e + " inserido com sucesso!");
		rebalanceTree();
	}

	public void remove(int e) {
		if (search(root, e, false) == null) {
			System.out.println("Um nodo com esse elemento não existe!");
			return;
		}

		System.out.println("Elemento removido!");
		root = remove(root, e);
		rebalanceTree();
	}

	public NodeClass remove(NodeClass n, int e) {
		if (e < n.element) {
			n.leftNode = remove(n.leftNode, e);
		} else if (e > n.element) {
			n.rightNode = remove(n.rightNode, e);
		} else {
			if (n.leftNode == null && n.rightNode == null) {
				// remoção de nodo folha
				n = null;
			} else if (n.leftNode != null && n.rightNode != null) {
				// remoção de nodo com dois filhos
				NodeClass copyNode = findSmallestInTree(n);
				n.rightNode = remove(copyNode, copyNode.element);
				n.element = copyNode.element;
			} else {
				// remoção de nodo com um filho
				if (n.leftNode != null) {
					n = n.leftNode;
				} else {
					n = n.rightNode;
				}
			}
		}

		if (n != null)
			n.updateHeight();
		return n;
	}

	public NodeClass findSmallestInTree(NodeClass n) {
		NodeClass smallest = n.rightNode;

		while (true) {
			if (smallest.leftNode != null)
				smallest = smallest.rightNode;
			else
				break;
		}

		return smallest;
	}

	// ------ROTAÇÕES------

	public NodeClass rightRotation(NodeClass n) {
		// n = k2
		NodeClass k1 = n.leftNode;
		NodeClass y = k1.rightNode;

		k1.rightNode = n;
		n.leftNode = y;

		k1.updateHeight();
		n.updateHeight();

		return k1;
	}

	public NodeClass leftRotation(NodeClass n) {
		// n = k1
		NodeClass k2 = n.rightNode;
		NodeClass y = k2.leftNode;

		k2.leftNode = n;
		n.rightNode = y;

		k2.updateHeight();
		n.updateHeight();

		return k2;
	}

	// ------ENCAMINHAMENTOS------

	public void order(int type) {
		if (root == null)
			System.out.print("A árvore está vazia!");
			
            switch (type) {
                case 1 -> preOrder(root);
                case 2 -> postOrder(root);
                case 3 -> inOrder(root);
                default -> {
					System.out.println("ERRO: Opção inválida");
                }
            }
		System.out.println();
	}

	private void preOrder(NodeClass n) {
		if (n != null) {
			System.out.print(n.element + ", ");
			preOrder(n.leftNode);
			preOrder(n.rightNode);
		}
	}

	private void postOrder(NodeClass n) {
		if (n != null) {
			postOrder(n.leftNode);
			postOrder(n.rightNode);
			System.out.print(n.element + ", ");
		}
	}

	private void inOrder(NodeClass n) {
		if (n != null) {
			inOrder(n.leftNode);
			System.out.print(n.element + ", ");
			inOrder(n.rightNode);
		}
	}
}
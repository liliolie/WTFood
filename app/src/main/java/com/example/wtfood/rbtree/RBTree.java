package com.example.wtfood.rbtree;


import java.util.ArrayList;
import java.util.List;

/**
 * Skeleton code for Red Black Tree
 * 
 * @author dongwookim
 * @author Bernardo Pereira Nunes fixed small bug
 */
public class RBTree {
	
	Node root; // The root node of the tree
	String comparingAttribute;

	/**
	 * Initialize empty RBTree
	 * @param comparingAttribute the attribute to comparing.
	 */
	public RBTree(String comparingAttribute) {
		root = null;
		this.comparingAttribute = comparingAttribute;
	}

	/**
	 * Add a new node into the tree with {@code root} node.
	 * 
	 * @param root Node<T> The root node of the tree where x is being inserted.
	 * @param x    Node<T> New node being inserted.
	 */
	public void insertRecurse(Node root, Node x) {
		int cmp = root.compareTo(x);
		
		if (cmp >= 0) {
			if (root.left.address == null) {
				root.left = x;
				x.parent = root;
			} else {
				insertRecurse(root.left, x);
			}
		} else {
			if (root.right.address == null) {
				root.right = x;
				x.parent = root;
			} else {
				insertRecurse(root.right, x);
			}
		}
		// Do nothing if the tree already has a node with the same value.
	}

	/**
	 * Insert node into RBTree.
	 * 
	 * @param x Node<T> The new node being inserted into the tree.
	 */
	private void insert(Node x) {
		// TODO: Complete this function
		// You need to finish cases 1, 2 and 3; the rest has been done for you

		// Insert node into tree
		if (root == null) {
			root = x;
		} else {
			if(search(x) != null) return;
			insertRecurse(root, x);
		}

		// Fix tree
		while (!x.equals(root) && x.parent.colour == Colour.RED) {
			boolean left  = x.parent == x.parent.parent.left; // Is parent a left node
			Node uncle = left ? x.parent.parent.right : x.parent.parent.left; // Get opposite "uncle" node to parent

			if (uncle.colour == Colour.RED) {
				// Case 1: Recolour
				// TODO: Implement this part
				// ########## YOUR CODE STARTS HERE ##########
				x.parent.colour = Colour.BLACK;
				x.parent.parent.colour = Colour.RED;
				uncle.colour = Colour.BLACK;
				// ########## YOUR CODE ENDS HERE ##########
				// Check if violated further up the tree
				x = x.parent.parent;
			} else {
				if (x.equals(left ? x.parent.right : x.parent.left)) {
					// Case 2: Left Rotation, uncle is right node, x is on the right / Right Rotation, uncle is left node, x is on the left
					x = x.parent;
					if (left) {
						// Perform left rotation
						if (x.equals(root))
							root = x.right; // Update root
						rotateLeft(x);
					} else {
						// This is part of the "then" clause where left and right are swapped
						// Perform right rotation
						// TODO: Implement this part
						// ########## YOUR CODE STARTS HERE ##########
						if (x.equals(root)) {
							root = x.left;
						}
						rotateRight(x);
						
						// ########## YOUR CODE ENDS HERE ##########
					}
				}
				// Adjust colours to ensure correctness after rotation
				x.parent.colour = Colour.BLACK;
				x.parent.parent.colour = Colour.RED;

				// Case 3 : Right Rotation, uncle is right node, x is on the left / Left Rotation, uncle is left node, x is on the right
				// TODO: Complete this part
				if (left) {
					// Perform right rotation
					// ########## YOUR CODE STARTS HERE ##########
					rotateRight(x.parent.parent);

					// ########## YOUR CODE ENDS HERE ##########
				} else {
					// This is part of the "then" clause where left and right are swapped
					// Perform left rotation
					// ########## YOUR CODE STARTS HERE ##########
					rotateLeft(x.parent.parent);

					// ########## YOUR CODE ENDS HERE ##########
				}
				if (x.parent.parent == null) {
					root = x.parent;
				}
			}
		}

		// Ensure property 2 (root and leaves are black) holds
		root.colour = Colour.BLACK;
	}

    /** Rotate the node so it becomes the child of its right branch
    /*
        e.g.
              [x]                    b
             /   \                 /   \
           a       b     == >   [x]     f
          / \     / \           /  \
         c  d    e   f         a    e
                              / \
                             c   d
    */
	public void rotateLeft(Node x) {
		// Make parent (if it exists) and right branch point to each other
		if (x.parent != null) {
			// Determine whether this node is the left or right child of its parent
			if (x.parent.left == x) {
				x.parent.left = x.right;
			} else {
				x.parent.right = x.right;
			}
		}
		x.right.parent = x.parent;

		x.parent = x.right;
		// Take right node's left branch
		x.right = x.parent.left;
		x.right.parent = x;
		// Take the place of the right node's left branch
		x.parent.left = x;
	}

    /** Rotate the node so it becomes the child of its left branch
    /*
        e.g.
              [x]                    a
             /   \                 /   \
           a       b     == >     c     [x]
          / \     / \                   /  \
         c  d    e   f                 d    b
                                           / \
                                          e   f
    */
	public void rotateRight(Node x) {
		// TODO: Implement this function
		// HINT: It is the mirrored version of rotateLeft()
		// ########## YOUR CODE STARTS HERE ##########
		// Make parent (if it exists) and left branch point to each other
		if (x.parent != null) {
			// Determine whether this node is the left or right child of its parent
			if (x.parent.left == x) {
				x.parent.left = x.left;
			} else {
				x.parent.right = x.left;
			}
		}
		x.left.parent = x.parent;

		x.parent = x.left;
		// Take right node's right branch
		x.left = x.parent.right;
		x.left.parent = x;
		// Take the place of the right node's right branch
		x.parent.right = x;

		// ########## YOUR CODE ENDS HERE ##########
	}

	/**
	 * Demo functions (Safely) insert a value into the tree
	 *
	 */
	public void insert(int price, int rating, String address) {
		Node node = new Node(address, rating, price, comparingAttribute);
		insert(node);
	}

	/**
	 * Return the result of a pre-order traversal of the tree
	 * 
	 * @param tree Tree we want to pre-order traverse
	 * @return pre-order traversed tree
	 */
	private String preOrder(Node tree) {
		if (tree != null && tree.address != null) {
			String leftStr = preOrder(tree.left);
			String rightStr = preOrder(tree.right);
			return tree.toString() + (leftStr.isEmpty() ? leftStr : " " + leftStr)
					+ (rightStr.isEmpty() ? rightStr : " " + rightStr);
		}

		return "";
	}

	public String preOrder() {
		return preOrder(root);
	}

	/**
	 * Return the corresponding node of a value, if it exists in the tree
	 * 
	 * @param x Node<T> The root node of the tree we search for the value {@code v}
	 * @param node The node to find.
	 * @return the node equals node in the tree.
	 */
	private Node find(Node x, Node node) {
		if (x.address == null)
			return null;

		int cmp = x.compareTo(node);
		if (cmp < 0)
			return find(x.left, node);
		else if (cmp > 0)
			return find(x.right, node);
		else if (x.equals(node)) {
			return x;
		} else {
			Node left = find(x.left, node);
			Node right = find(x.right, node);

			if (left == null) {
				return right;
			} else {
				return left;
			}
		}
	}

	/**
	 * Returns a node if the value of the node is {@code key}.
	 * 
	 * @param node The node to find.
	 * @return the node fount in the tree;
	 */
	public Node search(Node node) {
		return find(root, node);
	}

	private List<Node> result;
	/**
	 * Return the List of Node satisfy the requirement in the comparing attribute.
	 *
	 * @param sign the sign of equal, less, greater, less or equal, or greater or equal
	 * @param requirement the number of the requirement
	 */
	public List<Node> searchForNodes(String sign, int requirement) {
		result = new ArrayList<>();
		dfs(root, sign, requirement);
		return result;
	}

	private void dfs(Node node, String sign, int requirement) {
		if (node.address == null) {
			return;
		}

		if (node.satisfy(sign, requirement)) {
			result.add(node);
		}
		//TODO: cutting branch
		dfs(node.left, sign, requirement);
		dfs(node.right, sign, requirement);
	}

}
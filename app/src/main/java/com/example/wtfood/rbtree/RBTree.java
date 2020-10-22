package com.example.wtfood.rbtree;


import com.example.wtfood.model.Restaurant;

import java.util.HashSet;
import java.util.Set;

/**
 * Skeleton code for Red Black Tree
 * 
 * @author dongwookim
 * @author Bernardo Pereira Nunes fixed small bug
 * @author Junliang Liu modify the method to suit the APP
 */
public class RBTree {
	
	Node root; // The root node of the tree
	String comparingAttribute;
	int size;

	/**
	 * Initialize empty RBTree
	 * @param comparingAttribute the attribute to comparing.
	 */
	public RBTree(String comparingAttribute) {
		root = null;
		this.comparingAttribute = comparingAttribute;
		size = 0;
	}

	/**
	 * Add a new node into the tree with {@code root} node.
	 * 
	 * @param root Node<T> The root node of the tree where x is being inserted.
	 * @param x    Node<T> New node being inserted.
	 */
	public void insertRecurse(Node root, Node x) {
		int cmp = root.compareTo(x);
		
		if (cmp > 0) {
			if (root.left.restaurant == null) {
				root.left = x;
				x.parent = root;
			} else {
				insertRecurse(root.left, x);
			}
		} else {
			if (root.right.restaurant == null) {
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
		// You need to finish cases 1, 2 and 3; the rest has been done for you

		// Insert node into tree
		if (root == null) {
			root = x;
		} else {
			if(search(x) != null) {
				return;
			}
			insertRecurse(root, x);
		}

		// Fix tree
		while (!x.equals(root) && x.parent.colour == Colour.RED) {
			boolean left  = x.parent == x.parent.parent.left; // Is parent a left node
			Node uncle = left ? x.parent.parent.right : x.parent.parent.left; // Get opposite "uncle" node to parent

			if (uncle.colour == Colour.RED) {
				// Case 1: Recolour
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

		size++;
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
	public void insert(Restaurant restaurant) {
		Node node = new Node(restaurant, comparingAttribute);
		insert(node);
	}

	/**
	 * Return the result of a pre-order traversal of the tree
	 * 
	 * @param tree Tree we want to pre-order traverse
	 * @return pre-order traversed tree
	 */
	private String preOrder(Node tree) {
		if (tree != null && tree.restaurant != null) {
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
		if (x.restaurant == null)
			return null;

		if (x.restaurant.equals(node.restaurant)) {
			return x;
		}
		int cmp = x.compareTo(node);
		if (cmp < 0) {
			return find(x.right, node);
		} else if (cmp > 0) {
			return find(x.left, node);
		} else {
			Node t = find(x.left, node);
			if (t == null) {
				return find(x.right, node);
			} else {
				return t;
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

	private Set<Restaurant> result;

	/**
	 * Return the List of Restaurants satisfy the requirement in the comparing attribute.
	 *
	 * @param sign the sign of equal, less, greater, less or equal, or greater or equal
	 * @param requirement the number of the requirement
	 */
	public Set<Restaurant> searchForNodes(String sign, int requirement) {
		result = new HashSet<>();
		dfs(root, sign, requirement);
		return result;
	}

	/**
	 * get all the Restaurants stored in the tree.
	 * @return List of Restaurants stored in the tree.
	 */
	public Set<Restaurant> getAllNodes() {
		result = new HashSet<>();
		dfs(root);
		return result;
	}

	/**
	 * Depth First Search of Nodes.
	 * @param node the Node to search.
	 */
	private void dfs(Node node) {
		if (node.restaurant == null) {
			return;
		}

		result.add(node.restaurant);

		dfs(node.left);
		dfs(node.right);
	}

	/**
	 * Depth First Search of Nodes, and add the Node satisfy the requirement to the List.
	 * @param node the Node to search.
	 */
	private void dfs(Node node, String sign, int requirement) {
		if (node.restaurant == null) {
			return;
		}

		if (node.satisfy(sign, requirement)) {
			result.add(node.restaurant);
		}
		// cutting branch
		int cmp = 0;
		switch (comparingAttribute) {
			case "price":
				cmp = node.restaurant.getPrice() - requirement;
				break;
			case "rating":
				cmp = node.restaurant.getRating() - requirement;
		}

		if ((sign.equals("<") || sign.equals("<=")) && cmp > 0) {
			dfs(node.left, sign, requirement);
		} else if ((sign.equals(">") || sign.equals(">=") || sign.equals("=")) && cmp < 0) {
			dfs(node.right, sign, requirement);
		} else {
			dfs(node.left, sign, requirement);
			dfs(node.right, sign, requirement);
		}
	}

	/**
	 * @return the size of Nodes in the tree.
	 */
	public int size() {
		return size;
	}

	/**
	 * @return the pre-order result of tree in price.
	 */
	public String pricePreOrder() {
		return pricePreOrder(root);
	}

	/**
	 * @return the pre-order result of tree in price of the Node.
	 */
	private String pricePreOrder(Node root) {
		if (root != null && root.restaurant != null) {
			String leftStr = pricePreOrder(root.left);
			String rightStr = pricePreOrder(root.right);
			return  root.restaurant.getPrice() + (leftStr.isEmpty() ? leftStr : " " + leftStr)
					+ (rightStr.isEmpty() ? rightStr : " " + rightStr);
		}

		return "";
	}

	/**
	 * @return the in-order result of tree in price.
	 */
	public String priceInOrder() {
		return priceInOrder(root);
	}

	/**
	 * the in order result of tree.
	 * @return the in order result of tree.
	 */
	private String priceInOrder(Node root) {
		if (root != null && root.restaurant != null) {
			String leftStr = priceInOrder(root.left);
			String rightStr = priceInOrder(root.right);
			return  (leftStr.isEmpty() ? leftStr : leftStr + " ") + root.restaurant.getPrice()
					+ (rightStr.isEmpty() ? rightStr : " " + rightStr);
		}

		return "";
	}

	/**
	 * @return the in-order result of tree in rating.
	 */
	public String ratingInOrder() {
		return ratingInOrder(root);
	}

	/**
	 * @return the in-order result of tree in rating of the Node.
	 */
	private String ratingInOrder(Node root) {
		if (root != null && root.restaurant != null) {
			String leftStr = ratingInOrder(root.left);
			String rightStr = ratingInOrder(root.right);
			return  (leftStr.isEmpty() ? leftStr : leftStr + " ") + root.restaurant.getRating()
					+ (rightStr.isEmpty() ? rightStr : " " + rightStr);
		}

		return "";
	}

	/**
	 * @return the pre-order result of tree in rating.
	 */
	public String ratingPreOrder() {
		return ratingPreOrder(root);
	}

	/**
	 * @return the pre-order result of tree in rating of the Node.
	 */
	private String ratingPreOrder(Node root) {
		if (root != null && root.restaurant != null) {
			String leftStr = ratingPreOrder(root.left);
			String rightStr = ratingPreOrder(root.right);
			return  root.restaurant.getRating() + (leftStr.isEmpty() ? leftStr : " " + leftStr)
					+ (rightStr.isEmpty() ? rightStr : " " + rightStr);
		}

		return "";
	}
}
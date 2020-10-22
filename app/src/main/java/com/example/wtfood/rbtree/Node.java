package com.example.wtfood.rbtree;


import com.example.wtfood.model.Restaurant;

/**
 * Base class for node
 *
 */
public class Node implements Comparable<Node> {

	Colour colour;			// Node colour
	Restaurant restaurant;
	String comparingAttribute;
	Node parent; 		// Parent node
	Node left, right; 	// Children nodes


	// Leaf node
	public Node() {
		this.restaurant = null;
		this.colour = Colour.BLACK; //leaf nodes are always black
	}

	public Node(Restaurant restaurant, String comparingAttribute) {
		this.restaurant = restaurant;
		this.comparingAttribute = comparingAttribute;
		this.colour = Colour.RED; //property 3 (if a node is red, both children are black) may be violated if parent is red

		this.parent = null;

		// Initialise children leaf nodes
		this.left 			= new Node();  //leaf node
		this.right 			= new Node();  //leaf node
		this.left.parent 	= this; //reference to parent
		this.right.parent 	= this; //reference to parent

	}

	@Override
	public String toString() {
		return "name: " + restaurant.getName() + " price: " + restaurant.getPrice() + " rating: " +
				restaurant.getRating() + " address: " + restaurant.getAddress();
	}

	@Override
	public int compareTo(Node o) {
		switch (comparingAttribute) {
			case "price":
				return this.restaurant.getPrice() - o.restaurant.getPrice();
			case "rating":
				return this.restaurant.getRating() - o.restaurant.getRating();
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == getClass()) {
			Node node = (Node) obj;
			if (node.restaurant == null) {
				return this.restaurant == null;
			}
			return this.restaurant.equals(node.restaurant);
		}

		return false;
	}

	/**
	 * return the current node satisfy the requirement of sign and number.
	 * @param sign the sign of >, <, <=, >=, =
	 * @param requirement the number of the requirement.
	 * @return true if the requirement is satisfied, else return false.
	 */
	public boolean satisfy(String sign, int requirement) {
		switch (comparingAttribute) {
			case "price":
				return satisfyPrice(sign, requirement);
			case "rating":
				return satisfyRating(sign, requirement);
		}

		return false;
	}

	/**
	 * return the current node satisfy the requirement of sign and number in price comparing.
	 * @param sign the sign of >, <, <=, >=, =
	 * @param requirement the number of the requirement.
	 * @return true if the requirement is satisfied, else return false.
	 */
	public boolean satisfyPrice(String sign, int requirement) {
		switch (sign) {
			case "=":
				return this.restaurant.getPrice() == requirement;
			case "<":
				return this.restaurant.getPrice() < requirement;
			case ">":
				return this.restaurant.getPrice() > requirement;
			case "<=":
				return this.restaurant.getPrice() <= requirement;
			case ">=":
				return this.restaurant.getPrice() >= requirement;
		}

		return false;
	}

	/**
	 * return the current node satisfy the requirement of sign and number in rating comparing.
	 * @param sign the sign of >, <, <=, >=, =
	 * @param requirement the number of the requirement.
	 * @return true if the requirement is satisfied, else return false.
	 */
	public boolean satisfyRating(String sign, int requirement) {
		switch (sign) {
			case "=":
				return this.restaurant.getRating() == requirement;
			case "<":
				return this.restaurant.getRating() < requirement;
			case ">":
				return this.restaurant.getRating() > requirement;
			case "<=":
				return this.restaurant.getRating() <= requirement;
			case ">=":
				return this.restaurant.getRating() >= requirement;
		}

		return false;
	}
}
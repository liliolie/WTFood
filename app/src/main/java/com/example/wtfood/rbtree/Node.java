package com.example.wtfood.rbtree;


import com.example.wtfood.fileprocess.Location;
import com.example.wtfood.fileprocess.Restaurant;
import com.example.wtfood.fileprocess.Type;

/**
 * Base class for node
 *
 */
public class Node implements Comparable<Node> {

	Colour colour;			// Node colour
	int rating;
	String name;
	boolean deliveryService;
	Location location;
	Type type;
	int price;
	String address;
	String phone;
	String comparingAttribute;
	Node parent; 		// Parent node
	Node left, right; 	// Children nodes


	public Node(String name, boolean deliveryService, Location location, Type type, String address,
				int rating, int price, String phone, String comparingAttribute) {
		this.name = name;
		this.deliveryService = deliveryService;
		this.address = address;
		this.location = location;
		this.type = type;
		this.rating = rating;
		this.price = price;
		this.phone = phone;
		this.comparingAttribute = comparingAttribute;
		this.colour = Colour.RED; //property 3 (if a node is red, both children are black) may be violated if parent is red

		this.parent = null;

		// Initialise children leaf nodes
		this.left 			= new Node();  //leaf node
		this.right 			= new Node();  //leaf node
		this.left.parent 	= this; //reference to parent
		this.right.parent 	= this; //reference to parent
	}

	// Leaf node
	public Node() {
		this.name = null;
		this.address = null;
		this.rating = 0;
		this.price = 0;
		this.colour = Colour.BLACK; //leaf nodes are always black
	}

	public Node(Restaurant restaurant, String comparingAttribute) {
		this.name = restaurant.getName();
		this.deliveryService = restaurant.isDeliveryService();
		this.address = restaurant.getAddress();
		this.location = restaurant.getLocation();
		this.type = restaurant.getType();
		this.rating = restaurant.getRating();
		this.price = restaurant.getPrice();
		this.phone = restaurant.getPhone();
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
		return "price: " + price + " rating: " + rating + " address: " + address;
	}

	@Override
	public int compareTo(Node o) {
		switch (comparingAttribute) {
			case "price":
				return this.price - o.price;
			case "rating":
				return this.rating - o.rating;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == Node.class) {
			Node node = (Node) obj;
			return node.rating == this.rating && node.price == this.price && this.phone.equals(node.phone)
					&& node.address.equals(this.address) && this.type.equals(node.type) && this.name.equals(node.name)
					&& this.location.equals(node.location) && this.deliveryService == node.deliveryService;
		}

		return false;
	}

	public boolean satisfy(String sign, int requirement) {
		switch (comparingAttribute) {
			case "price":
				return satisfyPrice(sign, requirement);
			case "rating":
				return satisfyRating(sign, requirement);
		}

		return false;
	}

	public boolean satisfyPrice(String sign, int requirement) {
		switch (sign) {
			case "=":
				return this.price == requirement;
			case "<":
				return this.price < requirement;
			case ">":
				return this.price > requirement;
			case "<=":
				return this.price <= requirement;
			case ">=":
				return this.price >= requirement;
		}

		return false;
	}

	public boolean satisfyRating(String sign, int requirement) {
		switch (sign) {
			case "=":
				return this.rating == requirement;
			case "<":
				return this.rating < requirement;
			case ">":
				return this.rating > requirement;
			case "<=":
				return this.rating <= requirement;
			case ">=":
				return this.rating >= requirement;
		}

		return false;
	}
}
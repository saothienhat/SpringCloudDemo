package com.saothienhat.cartservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saothienhat.cartservice.model.Cart;
import com.saothienhat.cartservice.model.Product;

@RestController
@RequestMapping("/cart")
public class CartController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

	@GetMapping("/{userId}")
	public Cart getCart(@Valid @PathVariable long userId) {
		LOGGER.info("Getting cart with userID = " + userId);

		// For simplicity we are returning a hard coded value
		List<Product> products = new ArrayList<>();
		// p1
		Product p1 = new Product(1, "keyboard", 250, 2);
		p1.setTotlalPrice(p1.getBasePrice() * p1.getQuantity());
		products.add(p1);

		// p2
		Product p2 = new Product(2, "mouse", 150, 2);
		p2.setTotlalPrice(p2.getBasePrice() * p2.getQuantity());
		products.add(p2);

		// calculating total price
		double totalPrice = products.stream().mapToDouble(p -> p.getTotlalPrice()).sum();

		Cart cart = new Cart(products.size(), totalPrice, products);
		return cart;
	}
	
	@GetMapping("/products") 
//	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public List<Product> getAllProducts() {
		LOGGER.info("Getting AllProducts...");
		List<Product> products = new ArrayList<Product>();

		// p1
		Product p1 = new Product(1, "keyboard", 250, 2);
		p1.setTotlalPrice(p1.getBasePrice() * p1.getQuantity());
		products.add(p1);

		// p2
		Product p2 = new Product(2, "mouse", 150, 2);
		p2.setTotlalPrice(p2.getBasePrice() * p2.getQuantity());
		products.add(p2);

		return products;
	}

}

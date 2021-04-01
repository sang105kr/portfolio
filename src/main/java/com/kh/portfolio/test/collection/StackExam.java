package com.kh.portfolio.test.collection;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class StackExam {

	public static void main(String[] args) {
		
		Stack<Coin> stack = new Stack<>();
		Queue<Coin> queue = new LinkedList<>();
		
		stack.push(new Coin(100));
		stack.push(new Coin(50));
		stack.push(new Coin(10));
		stack.push(new Coin(500));

		System.out.println(stack);

		while(!stack.isEmpty()) {
			System.out.println(stack.pop().getValue());
		}
		
		System.out.println("----------------");
		
		queue.offer(new Coin(100));
		queue.offer(new Coin(50));
		queue.offer(new Coin(10));
		queue.offer(new Coin(500));
		System.out.println(queue);
		
		while(!queue.isEmpty()) {
			System.out.println(queue.poll().getValue());
		}		
	}
}

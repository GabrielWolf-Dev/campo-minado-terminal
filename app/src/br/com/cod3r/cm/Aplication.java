package br.com.cod3r.cm;

import br.com.cod3r.cm.model.Board;

public class Aplication {
	public static void main(String[] args) {
		Board board = new Board(6, 6, 6);
		
		board.open(3, 3);
		board.handleMarked(4, 4);
		board.handleMarked(4, 5);
		
		System.out.println(board);
	}
}

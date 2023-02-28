package br.com.cod3r.cm;

import br.com.cod3r.cm.model.Board;
import br.com.cod3r.cm.view.BoardConsole;

public class Aplication {
	public static void main(String[] args) {
		Board board = new Board(6, 6, 6);
		
		new BoardConsole(board);
	}
}

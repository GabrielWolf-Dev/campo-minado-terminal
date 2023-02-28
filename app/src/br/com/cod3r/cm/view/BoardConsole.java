package br.com.cod3r.cm.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.cod3r.cm.exception.ExitException;
import br.com.cod3r.cm.exception.ExplosionException;
import br.com.cod3r.cm.model.Board;

public class BoardConsole {
	private Board board;
	private Scanner scan = new Scanner(System.in);
	
	public BoardConsole(Board board) {
		this.board = board;
		
		execGame();
	}
	
	private void execGame() {
		try {
			boolean toContinue = true;
			
			while(toContinue) {
				cicleGame();
				System.out.println("Outra partida? (S/n)");
				String userResponse = scan.nextLine();
				
				if("n".equalsIgnoreCase(userResponse)) {
					toContinue = false;
				} else {
					board.restart();
				}
			}
		} catch(ExitException e) {
			System.out.println("Tchau !!!");
		} finally {
			scan.close();
		}
	}
	


	private void cicleGame() {
		try {
			while(!board.goalReached()) {
				System.out.println(board);
				
				String userDigit = captureTypedValue("Digite (x, y): ");
				Iterator<Integer> xy = Arrays.stream(userDigit.split(",")).map(e -> Integer.parseInt(e.trim())).iterator();
				
				userDigit = captureTypedValue("1 - Abrir ou 2 - (Des)Marcar: ");
				
				if("1".equals(userDigit)) {
					board.open(xy.next(), xy.next());
				} else if("2".equals(userDigit)) {
					board.handleMarked(xy.next(), xy.next());
				}
			}
			
			System.out.println("Você ganhou!!!");
		} catch(ExplosionException e) {
			System.out.println(board);
			System.out.println("Você perdeu!");
		}
	}
	
	private String captureTypedValue(String text) {
		System.out.print(text);
		String digit = scan.nextLine();
		
		if("sair".equalsIgnoreCase(digit)) {
			throw new ExitException();
		}
		
		return digit;
	}
}

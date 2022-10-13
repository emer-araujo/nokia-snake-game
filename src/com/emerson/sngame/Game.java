package com.emerson.sngame;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Game extends JFrame {
	
	private static final long serialVersionUID = -3089061113807962117L;

	public Game() {
		render();
	}
	
	public void render() {
		setTitle("Naja");
		Board tabuleiro = new Board();
		add(tabuleiro); 
		pack(); 
		setLocationRelativeTo(null); 
		setResizable(false); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game frame = new Game();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

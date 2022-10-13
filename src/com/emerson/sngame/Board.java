package com.emerson.sngame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = -2257740304101745345L;
	
	private final int SQUARE_SIZE = 10;
	private final int RAT_POS_LIMIT = 49;
	private int DELAY = 150;
	private final int BOARD_SQUARES = 2500;
	private final int BOARD_END = 500;
	
	private final int snake_pos_x[] = new int[BOARD_SQUARES];
	private final int snake_pos_y[] = new int[BOARD_SQUARES];
	
	private int eaten;
	private int rat_pos_x;
	private int rat_pos_y;
	
    private Direction direction;

	private final Image HEAD = new ImageIcon(getClass().getClassLoader().getResource("head.png")).getImage();
	private final Image BODY = new ImageIcon(getClass().getClassLoader().getResource("body.png")).getImage();
	private final Image RAT = new ImageIcon(getClass().getClassLoader().getResource("rat.png")).getImage();
	
	private Timer timer;
	
	boolean isPaused;
	boolean status = true;
	
	public Board() {
		setPreferredSize(new Dimension(BOARD_END,BOARD_END));
		setBackground(new Color(155, 186, 90));
        setFocusable(true);
        addKeyListener(new Constrols());
        newGame();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(status) {
			move();
			checkRat();
			checkCollision();
		}
        repaint();
	}
	
	public void newGame() {
		eaten = 2;
		timer = new Timer(DELAY,this);
		timer.start();
		generateRat();
	}
	
	private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (BOARD_END - metr.stringWidth(msg)) / 2, BOARD_END / 2);
    }
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

	public void render(Graphics g) {
		super.paintComponent(g);
		if(status) {
			g.drawString("Pontuacao: "+(eaten), 20, 20);
			g.drawImage(RAT, rat_pos_x, rat_pos_y, this);
		
		for(int pos = 0; pos<eaten; pos++) {
			if(pos == 0) {
				g.drawImage(HEAD,snake_pos_x[pos], snake_pos_y[pos], this);
			}else {
				g.drawImage(BODY,snake_pos_x[pos], snake_pos_y[pos],this);
			}
		}
		
		}else {
			gameOver(g);
		}
	}
	
	 private void move() {
	    		    	
		for (int z = eaten; z > 0; z--) {
			snake_pos_x[z] = snake_pos_x[(z - 1)];
			snake_pos_y[z] = snake_pos_y[(z - 1)];
		}
	        		
		if (direction == Direction.Left) {
			snake_pos_x[0] -= SQUARE_SIZE;
		}

		if (direction == Direction.Right) {
			snake_pos_x[0] += SQUARE_SIZE;
		}

		if (direction == Direction.Up) {
			snake_pos_y[0] -= SQUARE_SIZE;
		}

		if (direction == Direction.Down) {
			snake_pos_y[0] += SQUARE_SIZE;
		}
    }
	 
	public void checkRat() {
		if(snake_pos_x[0] == rat_pos_x && snake_pos_y[0] == rat_pos_y) {
			eaten++;
			java.awt.Toolkit.getDefaultToolkit().beep();
			generateRat();
		}
	}
	
    private void checkCollision() {

        for (int z = eaten; z > 0; z--) {
            if ((z > 4) && (snake_pos_x[0] == snake_pos_x[z]) && (snake_pos_y[0] == snake_pos_y[z])) {
                status = false;
            }
        }
        
        if (snake_pos_y[0] >= BOARD_END) {
            status = false;
        }

        if (snake_pos_y[0] < 0) {
            status = false;
        }

        if (snake_pos_x[0] >= BOARD_END) {
            status = false;
        }

        if (snake_pos_x[0] < 0) {
            status = false;
        }

		if (!status) {
            timer.stop();
        }
    }
	
	public void generateRat() {

		int random_x = (int) (Math.random() * RAT_POS_LIMIT);
		rat_pos_x = ((random_x * 10));
		
		int random_y = (int) (Math.random() * RAT_POS_LIMIT);
		rat_pos_y = ((random_y * 10));
	}
	
    private class Constrols extends KeyAdapter {
		@Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && direction != Direction.Right) {
               direction = Direction.Left;
            }

            if (key == KeyEvent.VK_RIGHT && direction != Direction.Left) {
                direction = Direction.Right;
            }

            if (key == KeyEvent.VK_UP && direction != Direction.Down) {
                direction = Direction.Up;
            }

            if (key == KeyEvent.VK_DOWN && direction != Direction.Up) {
                direction = Direction.Down;
            }
            
            if(key == KeyEvent.VK_P) {
            	System.out.println(isPaused);
            	if(!isPaused) {
                	timer.stop();
                	isPaused = true;
            	}else {
            		timer.restart();
            		isPaused = false;
            	}
            }
            
            if(key == KeyEvent.VK_ENTER) 
            {
            	eaten = 2;
            	snake_pos_x[0] = 50;
            	snake_pos_y[0] = 50;
            	direction = Direction.Down;
            	timer.start();
            	status = true;
            }
        }
	}
}

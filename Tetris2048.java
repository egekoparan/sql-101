import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Point;
import java.awt.Color; 
public class Tetris2048 {
	

	
	public static Tetrominoes createIncomingTetromino(int gridHeight, int gridWidth) {
		char[] tetrominoNames = {'I', 'S', 'Z', 'O', 'T', 'L', 'J'};
		Random random = new Random();
		int randomIndex = random.nextInt(7);		
		char randomName = tetrominoNames[randomIndex];
		Tetrominoes tet = new Tetrominoes(randomName, gridHeight, gridWidth);
		return tet;
	}
	public static void drawNextTetromino(Tetrominoes tet) {
		for (Tile tile: tet.getTileList()) {
			Point p = tile.getPosition();
			p.translate(8, -20); 
			StdDraw.setPenColor(tile.getBackgroundColor());
			StdDraw.filledSquare(p.x, p.y, 0.5);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(p.x, p.y, Integer.toString(tile.getNumber())); 
		}
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(14, 5 , "NEXT");
		StdDraw.text(14, 18 , "SCORE");
	}

	public static void main(String[] args) throws InterruptedException {
		
		int gridWidth = 12, gridHeight = 20;       
		StdDraw.setCanvasSize(660, 800);  		 
		StdDraw.setXscale(-1.5, gridWidth + 4.5);  
		StdDraw.setYscale(-1.5, gridHeight - 0.5); 
		StdDraw.enableDoubleBuffering();
		Grid gameGrid = new Grid(gridHeight, gridWidth);  
		Tetrominoes tet = createIncomingTetromino(gridHeight, gridWidth);
		Tetrominoes nextTetromino = createIncomingTetromino(gridHeight, gridWidth);
		boolean createANewTetromino = false; 


		Color backgroundColor = new Color(42, 69, 99);
		Color buttonColor = new Color(25, 255, 228);
		Color textColor = new Color(31, 160, 239);
		StdDraw.clear(backgroundColor);
		String imgFile = "C:\\Users\\tuana\\OneDrive\\Masaüstü\\menu_image.png";
		double imgCenterX = (gridWidth - 1) / 2.0, imgCenterY = gridHeight - 7;
		StdDraw.picture(imgCenterX, imgCenterY, imgFile);
		double buttonW = gridWidth - 1.5, buttonH = 2;
		double buttonX = imgCenterX, buttonY = 5;
		StdDraw.setPenColor(buttonColor);
		StdDraw.filledRectangle(buttonX, buttonY, buttonW / 2, buttonH / 2);
		Font font = new Font("Arial", Font.PLAIN, 25);
		StdDraw.setFont(font);
		StdDraw.setPenColor(textColor);
		String textToDisplay = "Click Here to Start the Game";
		StdDraw.text(buttonX, buttonY, textToDisplay);
		while (true) {
			 StdDraw.show();
			 StdDraw.pause(7000);
			 if (StdDraw.isMousePressed()) {
					double mouseX = StdDraw.mouseX(), mouseY = StdDraw.mouseY();
					if (mouseX > buttonX - buttonW / 2 && mouseX < buttonX + buttonW / 2)
						 if (mouseY > buttonY - buttonH / 2 && mouseY < buttonY + buttonH / 2)
								break; 

			 }
		
		label1:   
			while (true)  {
				if (StdDraw.isMousePressed()) {
					if (Grid.getAction() == true)
					{Grid.setAction(false);}
					else {
						Grid.setAction(true);
						TimeUnit.SECONDS.sleep(1); 
					}
				}
				if (Grid.getAction() == true){
					continue label1;
				}
				boolean success = false;
				if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
					success = tet.goLeft(gameGrid);
				}
				else if(StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
					success = tet.goRight(gameGrid);
				}
				else if(StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
					success = tet.Rotate(gameGrid);
				}
	
				if (!success)
					success = tet.goDown(gameGrid);
				    if(!success && tet.GameOver) {
				    	String gameOverMsg = "Game Over! :(";
				    	StdDraw.setPenColor(StdDraw.WHITE);
				    	Font gameOverFont = new Font("Arial", Font.BOLD, 70);
				    	StdDraw.setFont(gameOverFont);
				    	StdDraw.clear(StdDraw.PINK);
				        StdDraw.text((gridWidth + 3)/2, gridHeight/2, gameOverMsg);
				        StdDraw.show();
				        break;
				    }
				    
				createANewTetromino = !success;  
				if (createANewTetromino) {
					gameGrid.updateGrid(tet);  
					gameGrid.do2048(tet);       
					gameGrid.checkFullLines();  
					tet = nextTetromino;       
					nextTetromino = createIncomingTetromino(gridHeight, gridWidth);
				}
	
			
				StdDraw.clear(StdDraw.BOOK_LIGHT_BLUE);        
				drawNextTetromino(nextTetromino);   	
				gameGrid.display();                
				tet.display();                   
				StdDraw.setPenColor(StdDraw.RED);  
				StdDraw.text(14, 16, gameGrid.getScoreAsString());
				StdDraw.show();  
				StdDraw.pause(150);  
			} 
		}
}
	
	}
// © 2018 Luke Jagg
// MIT License

package RecursiveTicTacToe;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.stage.Stage;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

// Includes Drawing
public class Main extends Application {

	final int cellSize = 80;
	final int boardWidth = 3;
	final int boardHeight = 3;
	final int inARow = 3;
	
	//Check which moves possible then place stuff
	//final boolean computer = true;
	
	final int width = boardWidth * boardWidth * cellSize;
	final int height = boardHeight * boardHeight * cellSize;
	
	final int boardSizeX = boardWidth * cellSize;
	final int boardSizeY = boardHeight * cellSize;
	
	final Image X = new Image("file:res/X.png");
	final Image O = new Image("file:res/O.png");
	
	int selectedCellX = 0;
	int selectedCellY = 0;
	int selectedBoardX = 0;
	int selectedBoardY = 0;
	
	int lastX = -1;
	int lastY = -1;
	
	boolean changingBoards = true;
	boolean freeChangeBoard = false;
	boolean oMove = false;
	boolean started = false;
	
	Board[][] boards = new Board[boardWidth][boardHeight];
	Board mainBoard = new Board(boardWidth, boardHeight);
	
	Media click = new Media(new File("res/Click.mp3").toURI().toString());
	MediaPlayer clickPlayer = new MediaPlayer(click);
	
	Media theme = new Media(new File("res/Theme.mp3").toURI().toString());
	MediaPlayer themePlayer = new MediaPlayer(theme);
	
	GraphicsContext gc;
	
	@Override
    public void start(Stage primaryStage) 
    {
	    Group root = new Group();
	    	Scene s = new Scene(root, width, height + cellSize, Color.BLACK);

	    	final Canvas canvas = new Canvas(width, height + cellSize);
	    	gc = canvas.getGraphicsContext2D();
	    	 
	    	root.getChildren().add(canvas);
	    
	    	primaryStage.setTitle("Recursive Toes");
	    	primaryStage.setScene(s);
	    	primaryStage.show();
	    	
	    	canvas.setFocusTraversable(true);
	    	
	    	canvas.setOnMouseClicked(e -> {
	    		
	    		if (!started) {
	    			Start();
	    			return;
	    		}
	    		
	    		if (changingBoards) {
	    			
	    			if (boards[selectedBoardX][selectedBoardY].winner == 0) {
	    				
	    				changingBoards = false;
	    				Draw();
	    				
	    			}
	    			
	    		}
	    		else {
	    			if (freeChangeBoard) {
	    				int selectedBoardX = (int) e.getX() / boardSizeX;
			    		int selectedBoardY = (int) e.getY() / boardSizeY;
			    		if ((selectedBoardX != this.selectedBoardX || selectedBoardY != this.selectedBoardY) && selectedBoardX < boardWidth && selectedBoardY < boardHeight && selectedBoardX >= 0 && selectedBoardY >= 0) {
			    			this.selectedBoardX = selectedBoardX;
			    			this.selectedBoardY = selectedBoardY;
			    			selectedCellX = -1;
			    			selectedCellY = -1;
			    			changingBoards = true;
			    			Draw();
			    		}
	    			}
	    			if (!changingBoards && selectedCellX >= 0 && selectedCellY >= 0) {
	    				if (boards[selectedBoardX][selectedBoardY].board[selectedCellX][selectedCellY] == 0) {
	    					boards[selectedBoardX][selectedBoardY].PlacePiece(selectedCellX, selectedCellY, (oMove == false) ? 1 : 2);
	    					oMove = !oMove;
	    					lastX = selectedBoardX * boardWidth + selectedCellX;
	    					lastY = selectedBoardY * boardHeight + selectedCellY;
	    					if (boards[selectedBoardX][selectedBoardY].winner >= 1) {
	    						mainBoard.PlacePiece(selectedBoardX, selectedBoardY, boards[selectedBoardX][selectedBoardY].winner);
	    						if (mainBoard.winner != 0) {
	    							started = false;
	    						}
	    					}
	    					if (boards[selectedCellX][selectedCellY].winner == 0) {
	    						freeChangeBoard = false;
	    						changingBoards = false;
	    						selectedBoardX = selectedCellX;
	    						selectedBoardY = selectedCellY;
	    					}
	    					else {
	    						freeChangeBoard = true;
	    						changingBoards = true;
	    						selectedBoardX = -1;
	    						selectedBoardY = -1;
	    						selectedCellX = -1;
		    					selectedCellY = -1;
		    					Draw();
	    					}
	    					selectedCellX = -1;
	    					selectedCellY = -1;
	    					Draw();
	    					
	    					clickPlayer.stop();
		   			    	clickPlayer = new MediaPlayer(click);
		   	    		 	clickPlayer.play();
	    				}
	    			}
	    		}
	    		
	    	});
	    	
	    	canvas.setOnMouseMoved(e -> {
	    		
	    		if (changingBoards) {
		    		int selectedBoardX = (int) e.getX() / boardSizeX;
		    		int selectedBoardY = (int) e.getY() / boardSizeY;
		    		if ((selectedBoardX != this.selectedBoardX || selectedBoardY != this.selectedBoardY) && selectedBoardX < boardWidth && selectedBoardY < boardHeight && selectedBoardX >= 0 && selectedBoardY >= 0) {
		    			this.selectedBoardX = selectedBoardX;
		    			this.selectedBoardY = selectedBoardY;
		    			selectedCellX = -1;
		    			selectedCellY = -1;
		    			Draw();
		    		}
	    		} else if ((int) e.getX() / boardSizeX == selectedBoardX && (int) e.getY() / boardSizeY == selectedBoardY) {
	    			int selectedCellX = ((int) e.getX() / cellSize) % boardWidth;
		    		int selectedCellY = ((int) e.getY() / cellSize) % boardHeight;
		    		if ((selectedCellX != this.selectedCellX || selectedCellY != this.selectedCellY) && selectedCellX < boardWidth && selectedCellY < boardHeight && selectedCellX >= 0 && selectedCellY >= 0) {
		    			this.selectedCellX = selectedCellX;
		    			this.selectedCellY = selectedCellY;
		    			Draw();
		    		}
	    		}
	    		
	    	});
				    	
	    	primaryStage.setOnCloseRequest(e -> {
		    	
		    	System.exit(0);
		    	
	    	});
	    	
	    	Start();
    }
	
	void Draw() {

		gc.setEffect(new ColorAdjust());
		gc.setTransform(new Affine());
		
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, width, height + cellSize);
		
		
		gc.setFill(Color.WHITE);
		
		if (mainBoard.winner == 0) {
			gc.fillText(((oMove) ? "O" : "X") + "'s Turn", width / 2, height + cellSize - 10);
		}
		else if (mainBoard.winner == 1) {
			gc.fillText("X WON! O SUX!!!", width / 2, height + cellSize - 10);
		}
		else if (mainBoard.winner == 2) {
			gc.fillText("O WON! X SUX!!!", width / 2, height + cellSize - 10);
		}
		else if (mainBoard.winner == -1) {
			gc.fillText("DOGE'S GAME", width / 2, height + cellSize - 10);
		}
		
		int x = width / boardWidth;
		int y = height / boardHeight;
		int lineWidth = cellSize / 20;
		if (selectedBoardX >= 0 && selectedBoardY >= 0) {
			if (changingBoards) {
				if (boards[selectedBoardX][selectedBoardY].winner == 0) {
					gc.setFill(Color.rgb(0, 255, 0, 0.6));
				}
				else {
					gc.setFill(Color.rgb(255, 0, 0, 0.4));
				}
			}
			else gc.setFill(Color.rgb(0, 255, 0, 0.3));
			
			gc.fillRect(x * selectedBoardX, y * selectedBoardY, x, y);
			
			gc.setFill(Color.rgb(0, 255, 0, 0.6));
			if (selectedCellX >= 0 && selectedCellY >= 0) {
				gc.fillRect(selectedBoardX * x + selectedCellX * cellSize, selectedBoardY * y + selectedCellY * cellSize, cellSize, cellSize);
			}
		}
		
		gc.setFill(Color.WHITE);
		for (int i = 1; i < boardWidth; i++) {
			gc.fillRect(i * x, lineWidth / 2, lineWidth, height - lineWidth);
		}
		for (int j = 1; j < boardHeight; j++) {
			gc.fillRect(lineWidth / 2, j * y, width - lineWidth, lineWidth);
		}
		x = cellSize * boardWidth;
		y = cellSize * boardHeight;
		lineWidth = cellSize / 40;
		
		for (int a = 0; a < boardWidth; a++) {
			for (int b = 0; b < boardHeight; b++) {
				gc.setTransform(new Affine());
				if (boards[a][b].winner == 0) {
					for (int i = 1; i < boardWidth; i++) {
						gc.fillRect(a * x + i * cellSize, b * y + lineWidth / 2 + 2, lineWidth, y - lineWidth - 4);
					}
					for (int j = 1; j < boardHeight; j++) {
						gc.fillRect(a * x + lineWidth / 2 + 2, b * y + j * cellSize, x - lineWidth - 4, lineWidth);
					}
					
					for (int i = 0; i < boardWidth; i++) {
						for (int j = 0; j < boardHeight; j++) {
							gc.setEffect(new ColorAdjust());
							if (a * boardWidth + i == lastX && b * boardHeight + j == lastY) {
								ColorAdjust effect = new ColorAdjust();
								effect.setSaturation(1);
								effect.setHue(0.2);
								gc.setEffect(effect);
							}
							if (boards[a][b].board[i][j] == 1) {
								gc.drawImage(X, a * x + i * cellSize + cellSize * .1, b * y + j * cellSize + cellSize * .1, cellSize * .8, cellSize * .8);
							}
							else if (boards[a][b].board[i][j] == 2) {
								gc.drawImage(O, a * x + i * cellSize + cellSize * .1, b * y + j * cellSize + cellSize * .1, cellSize * .8, cellSize * .8);
							}
							gc.setEffect(new ColorAdjust());
						}
					}
				}
				else {
					if (boards[a][b].winner == 1) {
						gc.drawImage(X, a * x + x * .1, b * y + y * .1, x * .8, y * .8);
					}
					else if (boards[a][b].winner == 2) {
						gc.drawImage(O, a * x + x * .1, b * y + y * .1, x * .8, y * .8);
					}
				}
				
			}
		}
		
		gc.restore();
		
	}
	
	void CreateTheme() {
		themePlayer.stop();
	    	themePlayer = new MediaPlayer(theme);
	    	themePlayer.setVolume(0.4);
	 	themePlayer.play();
	 	themePlayer.setOnEndOfMedia(new Runnable() {
	   	    public void run() {
	   	    		CreateTheme();
	   	    }
	   	});
	}
	
	void Start() {
		started = true;
		oMove = false;
		changingBoards = true;
		freeChangeBoard = true;
		boards = new Board[boardWidth][boardHeight];
		for (int i = 0; i < boardWidth; i++) {
			for (int j = 0; j < boardHeight; j++) {
				boards[i][j] = new Board(boardWidth, boardHeight);
			}
		}
		
//		boards[0][0].PlacePiece(0, 0, 1);
//		boards[0][0].PlacePiece(1, 1, 1);
//		boards[0][0].PlacePiece(2, 2, 1);
//		System.out.println(boards[0][0].winner);
		
		mainBoard = new Board(boardWidth, boardHeight);
		Board.winLength = inARow;
		
		selectedCellX = -1;
		selectedCellY = -1;
		selectedBoardX = -1;
		selectedBoardY = -1;
		
		gc.setFont(new Font(cellSize - 20));
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.BOTTOM);
		
		CreateTheme();
	
		Draw();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}

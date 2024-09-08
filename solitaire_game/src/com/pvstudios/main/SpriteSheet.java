package com.pvstudios.main;



import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class SpriteSheet {

    private BufferedImage spriteSheet;
    
    private int spriteWidth, spriteHeight;
    private int spriteGutterX, spriteGutterY;
    private int spritesheetRows, spritesheetCols;
    
    private int x, y;
    
    /**
     * 
     * @param x positionX
     * @param y positionY
     * @param spriteWidth the width of each sprite
     * @param spriteHeight the height of each sprite
     * @param spritesheetRows how many rows has the spritesheet
     * @param spritesheetCols how many cols has the spritesheet
     * @param path sprite sheet path
     */
    public SpriteSheet(int x, int y, int spriteWidth, int spriteHeight, int spritesheetRows, int spritesheetCols, String path) {
        this.setX(x);
        this.setY(y);
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.spritesheetRows = spritesheetRows;
        this.spritesheetCols = spritesheetCols;
        
        try {
			this.spriteSheet = loadImage(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 
     * @param x positionX
     * @param y positionY
     * @param spriteWidth the width of each sprite
     * @param spriteHeight the height of each sprite
     * @param spritesheetRows how many rows has the spritesheet
     * @param spritesheetCols how many cols has the spritesheet
     * @param spriteGutterX spaceX between sprites
     * @param spriteGutterY spaceY between sprites
     * @param path sprite sheet path
     */
    public SpriteSheet(int x, int y, int spriteWidth, int spriteHeight, int spritesheetRows, int spritesheetCols, int spriteGutterX, int spriteGutterY, String path) {
        this.setX(x);
        this.setY(y);
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.spritesheetRows = spritesheetRows;
        this.spritesheetCols = spritesheetCols;
        this.spriteGutterX = spriteGutterX;
        this.setSpriteGutterY(spriteGutterY);
        
        try {
			this.spriteSheet = loadImage(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private BufferedImage loadImage(String path) throws IOException {
        URL url = getClass().getResource(path);
        return ImageIO.read(url);
    }
    
	
	  public BufferedImage getSprite(int row, int col) {
		  int x = spriteGutterX + col * (spriteWidth + spriteGutterX);
		  int y = (((row*spriteHeight)+(row+spriteGutterX))+(spriteGutterX*row));
		  BufferedImage sprite = spriteSheet.getSubimage(
				  x,
				  y,
                  spriteWidth,
                  spriteHeight
                  );

		  return sprite;
	  }
	  
	  public BufferedImage getSprite(int row, int col, int offsetPixelX, int offsetPixelY) {
		  int x = spriteGutterX + col * (spriteWidth + spriteGutterX);
		  int y = (((row*spriteHeight)+(row+spriteGutterX))+(spriteGutterX*row));
		  BufferedImage sprite = spriteSheet.getSubimage(
				  x,
				  y,
                  spriteWidth+offsetPixelX,
                  spriteHeight+offsetPixelY
                  );

		  return sprite;
	  }
	 

	public int getSpritesheetRows() {
		return spritesheetRows;
	}

	public int getSpritesheetCols() {
		return spritesheetCols;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpriteGutterY() {
		return spriteGutterY;
	}

	public void setSpriteGutterY(int spriteGutterY) {
		this.spriteGutterY = spriteGutterY;
	}
    
   
}

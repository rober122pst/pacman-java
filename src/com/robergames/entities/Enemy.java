package com.robergames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.robergames.main.Game;
import com.robergames.world.AStar;
import com.robergames.world.Camera;
import com.robergames.world.Vector2i;



public class Enemy extends Entity{
	
	public int ghostFrames = 0;
	public static boolean ghostMode = false;

	public Enemy(int x, int y, int width, int height,int speed, BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
	}

	public void tick(){
		depth = 0;
		if(ghostMode == false) {
			if(path == null || path.size() == 0) {
					Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
					Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
					path = AStar.findPath(Game.world, start, end);
				}
			
				followPath(path);
				
				if(x % 16 == 0 && y % 16 == 0) {
					if(new Random().nextInt(100) < 10) {
						Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
						Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
						path = AStar.findPath(Game.world, start, end);
					}
				}
		}
		if(ghostMode == true) {
			ghostFrames++;
			if(ghostFrames >= 180) {
				ghostFrames = 0;
				ghostMode = false;
			}
		}else {
			ghostFrames = 0;
		}
	}
	

	
	public void render(Graphics g) {
		if(ghostMode == false) {
			super.render(g);
		}else {
			g.drawImage(Entity.ENEMY_GHOST,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
	}
	
	
}

package com.robergames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.robergames.main.Game;
import com.robergames.world.Camera;
import com.robergames.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 3;
	public int right_dir = 0,left_dir = 1,up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	
	private BufferedImage[] rightPlayer,leftPlayer,upPlayer,downPlayer;

	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		rightPlayer[0] = Game.spritesheet.getSprite(32,0,16,16);
		rightPlayer[1] = Game.spritesheet.getSprite(48,0,16,16);
		rightPlayer[2] = Game.spritesheet.getSprite(64,0,16,16);
		rightPlayer[3] = Game.spritesheet.getSprite(48,0,16,16);
		
		leftPlayer[0] = Game.spritesheet.getSprite(32,0,16,16);
		leftPlayer[1] = Game.spritesheet.getSprite(48,16,16,16);
		leftPlayer[2] = Game.spritesheet.getSprite(32,16,16,16);
		leftPlayer[3] = Game.spritesheet.getSprite(48,16,16,16);
		
		upPlayer[0] = Game.spritesheet.getSprite(32,0,16,16);
		upPlayer[1] = Game.spritesheet.getSprite(64+16,16,16,16);
		upPlayer[2] = Game.spritesheet.getSprite(64,16,16,16);
		upPlayer[3] = Game.spritesheet.getSprite(64+16,16,16,16);
		
		downPlayer[0] = Game.spritesheet.getSprite(32,0,16,16);
		downPlayer[1] = Game.spritesheet.getSprite(64+16,0,16,16);
		downPlayer[2] = Game.spritesheet.getSprite(64+32,0,16,16);
		downPlayer[3] = Game.spritesheet.getSprite(64+16,0,16,16);
	}
	
	public void tick(){
		if(x < 0-8) {
			x=Game.WIDTH+8;
		}else if(x > Game.WIDTH+8) {
			x=0-8;
		}
		depth = 1;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			x+=speed;
			dir = right_dir;
		}
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			x-=speed;
			dir = left_dir;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed))){
			y-=speed;
			dir = up_dir;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed))){
			y+=speed;
			dir = down_dir;
		}
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
		
		catchCoin();
		this.enemyCollision();
		
		if(Game.moedasContagem == Game.moedasAtual) {
			//Ganhamos o jogo
			World.restartGame("/level1.png");
		}
	}
	
	public void catchCoin() {
		for (int i = 0;i < Game.entities.size();i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Coin) {
				if(isColidding(this, current)) {
					Game.moedasAtual++;
					Game.entities.remove(i);
					return;
				}
			}
			if(current instanceof CoinMaster) {
				if(isColidding(this, current)) {
					Game.moedasAtual++;
					Game.entities.remove(i);
					if(Enemy.ghostMode == false) {
						Enemy.ghostMode = true;
					}
					return;
				}
			}
		}
	}
	
	public void enemyCollision() {
		for (int i = 0;i < Game.entities.size();i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Enemy) {
				if(isColidding(this, current)) {
					if(Enemy.ghostMode == false)
						World.restartGame("/level1.png");
					else {
						Game.entities.remove(current);
					}
					return;
				}
			}
		}
	}

	public void render(Graphics g) {
		if(dir == right_dir) {
			g.drawImage(rightPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if(dir == left_dir) {
			g.drawImage(leftPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if(dir == up_dir) {
			g.drawImage(upPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if(dir == down_dir) {
			g.drawImage(downPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
	}
	


}

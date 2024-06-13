package com.robergames.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.robergames.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,18));
		g.drawString("Moedas: "+Game.moedasAtual+"/"+Game.moedasContagem, 30, 30);
	}
	
}

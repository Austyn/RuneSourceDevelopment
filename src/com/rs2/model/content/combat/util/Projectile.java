package com.rs2.model.content.combat.util;

import com.rs2.net.StreamBuffer;
import com.rs2.model.Entity;
import com.rs2.model.players.Player;

public class Projectile {
	
	public static void createProjectile(Player player, Entity attacker, Entity victim,
	int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time) {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(19);
		out.writeHeader(player.getEncryptor(), 85);
		out.writeByte/*C*/((victim.getPosition().getY() - (attacker.getPosition().getRegionY() * 8)) - 2);
		out.writeByte/*C*/((victim.getPosition().getY() - (attacker.getPosition().getRegionX() * 8)) - 3);
		out.writeHeader(player.getEncryptor(), 117);
		out.writeByte(angle);
		out.writeByte(offY);
		out.writeByte(offX);
		out.writeShort(lockon);
		out.writeShort(gfxMoving);
		out.writeByte(startHeight);
		out.writeByte(endHeight);
		out.writeShort(time);
		out.writeShort(speed);
		out.writeByte(16);
		out.writeByte(64);
	}


}

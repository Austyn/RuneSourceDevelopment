package com.rs2.model.content.combat;

import com.rs2.model.players.Player;
import com.rs2.model.players.ItemManager;
import com.rs2.model.content.combat.magic.SpellDefinition;
import com.rs2.model.npcs.Npc;
import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.tick.Tick;
import com.rs2.util.Misc;

/**
  * By Mikey` of Rune-Server (MSN: metallic_mike@yahoo.com)
  * - Credit to Xynth of Rune-Server for the projectiles.
  */
public class Combat {

	private Player player;
	private Npc npc;
	
	public Combat(Player player) {
		this.player = player;
	}
	
	public Combat(Npc npc) {
		this.npc = npc;
	}
	
	/**
	  * The combat tick for entities.
	  * Ticks for all players and npcs (entitys).
	  */
	public void combatTick(Entity entity) {
		if (entity.getCombatingEntity() instanceof Npc && entity.getCombatingEntity().isDead())
			entity.setCombatTimer(0);
		if (entity.getAttackTimer() > 0)
			entity.setAttackTimer(entity.getAttackTimer() - 1);
		if (entity.isInstigatingAttack() && !inCombat(entity, entity.getTarget()))
			if (entity.getAttackTimer() == 0)
				attackEntity(entity, entity.getTarget());
		if (entity.getCombatTimer() > 0)
			entity.setCombatTimer(entity.getCombatTimer() - 1);
		if (entity.getCombatTimer() == 0)
			resetCombat(entity);
		if (entity.isFrozen() && entity.getFrozenTimer() > 0)
			entity.setFrozenTimer(entity.getFrozenTimer() - 1);
		if (entity.isFrozen() && entity.getFrozenTimer() == 0)
			entity.setFrozen(false, 0);
		sendDelayedHit(entity);
	}
	
	/**
	  * Handles the entity attacking another entity.
	  */
	public void attackEntity(Entity attacker, Entity victim) {
		if (victim instanceof Player)
			attacker.getUpdateFlags().faceEntity(victim.getIndex() + 32768);
		else
			attacker.getUpdateFlags().faceEntity(victim.getIndex());
		if (!meetsAttackRequirements(attacker, victim)) {
			return;
		}
		if (attacker.getAttackType() == Entity.AttackTypes.MAGIC || attacker.getAttackType() == Entity.AttackTypes.RANGE)
			sendProjectile(attacker, victim);
		else
			attacker.setHitDelayTimer(1);
		attacker.getUpdateFlags().sendAnimation(attacker.grabAttackAnimation(), 0);
		appendCombatTimers(attacker, victim);
		attacker.setCombatingEntity(victim);
		victim.setCombatingEntity(attacker);
	}
	
	public void sendDelayedHit(Entity attacker) {
		if (attacker.getHitDelayTimer() == 0) {
			attacker.getCombatingEntity().hit(1, 1);
			if (attacker.getCombatingEntity().getAttackTimer() == 0 && !attacker.getCombatingEntity().isInstigatingAttack() 
					|| attacker.getCombatingEntity().getAttackTimer() > 1 && attacker.getCombatingEntity().isInstigatingAttack())
				attacker.getCombatingEntity().getUpdateFlags().sendAnimation(attacker.getCombatingEntity().grabDefenceAnimation(), 0);
			autoRetaliate(attacker.getCombatingEntity());
			if (attacker.getAttackType() == Entity.AttackTypes.MAGIC) {
				if (player.getMagic().getSpellDefinitions()[player.getMagic().getMagicIndex()].getMagicType() == 
						SpellDefinition.MagicTypes.ANCIENT)
					attacker.getCombatingEntity().getUpdateFlags().sendGraphic(player.getMagic().getSpellDefinitions()
					[player.getMagic().getMagicIndex()].getEndGraphicId(), 0);
				else
					attacker.getCombatingEntity().getUpdateFlags().sendHighGraphic(player.getMagic().getSpellDefinitions()
					[player.getMagic().getMagicIndex()].getEndGraphicId(), 0);
			}
			resetAfterAttack(attacker);
		}
		if (attacker.getHitDelayTimer() != -1)
			attacker.setHitDelayTimer(attacker.getHitDelayTimer() - 1);
	}
	
	/**
	  * Resets anything needed after the end of combat.
	  */
	public void resetCombat(Entity entity) {
		entity.setCombatingEntity(null);
	}
	
	/**
	  * Resets anything needed after the end of the attack.
	  */
	public void resetAfterAttack(Entity attacker) {
		if (attacker instanceof Player) {
			player.getMagic().applyMagicEffects(attacker, attacker.getCombatingEntity());
			player.getMagic().resetMagic(attacker);
		}
	}
	
	/**
	  * Resets anything needed after the end of combat.
	  * - Still needs to check for weapon types.
	  */
	public void resetCombatType(Entity attacker) {
		attacker.setAttackType(Entity.AttackTypes.MELEE);
	}
	
	/**
	  * Updates the Entity's combat timers
	  */
	public void appendCombatTimers(Entity attacker, Entity victim) {
		attacker.setAttackTimer(attacker.grabHitTimer());
		attacker.setCombatTimer(15);
		victim.setCombatTimer(15);
	}
	
	/**
	 * Checking if the attacker meets the requirements to attack the victim.
	 */
	public boolean meetsAttackRequirements(Entity attacker, Entity victim) {
		if (victim.isDead() || attacker.isDead() || victim == null) {
			resetCombat(attacker);
			attacker.setInstigatingAttack(false);
			attacker.setFollowingEntity(null);
			return false;
		}
		if (attacker instanceof Player) {
			switch (attacker.getAttackType()) {
				case MELEE:
					break;
				case RANGE:
					break;
				case MAGIC:
					player.getMagic().removeRunes(attacker);
					break;
				default:
					break;
			}
		}
		if (!withinRange(attacker, victim))
			return false;
		return true;
	}
	
	/** 
	  * Checks to see if both entitys can start fighting eachother.
	  */
	public boolean inCombat(Entity attacker, Entity victim) {
		if (attacker == null || victim == null)
			return false;
		if (attacker.getCombatTimer() != 0 && attacker.getCombatingEntity() != victim &&
		!victim.isInMultiZone() && !attacker.isInMultiZone()) {
			if (attacker instanceof Player) {
				player.getActionSender().sendMessage("You are already in combat.");
				attacker.setFollowingEntity(null);
				player.getMovementHandler().reset();
			}
			attacker.setInstigatingAttack(false);
			return true;
		}
		if (victim.getCombatingEntity() != null && attacker.getCombatingEntity() != victim &&
		!victim.isInMultiZone() && !attacker.isInMultiZone()) {
			if (attacker instanceof Player) {
				attacker.setFollowingEntity(null);
				player.getMovementHandler().reset();
				attacker.setInstigatingAttack(false);
				attacker.setCombatingEntity(null);
				if (victim instanceof Player)
					player.getActionSender().sendMessage("That player is already in combat.");
				else if (victim instanceof Npc)
					player.getActionSender().sendMessage("That npc is already in combat.");
				return true;
			}
			else {
				attacker.setInstigatingAttack(false);
				attacker.setCombatingEntity(null);
				return true;
			}
		}
		return false;
	}
	
	/**
	  * Determines whether the Entity should retaliate automatically.
	  */
	public void autoRetaliate(Entity victim) {
		if (victim.shouldAutoRetaliate()) {
			victim.setTarget(victim.getCombatingEntity());
			victim.setFollowingEntity(victim.getCombatingEntity());
			victim.setInstigatingAttack(true);
		}
	}
		
	public void sendProjectile(Entity attacker, Entity victim) {
		int attackerX = attacker.getPosition().getX(), attackerY = attacker.getPosition().getY();
		int victimX = victim.getPosition().getX(), victimY = victim.getPosition().getY();
		int offsetX = (attackerY - victimY) * -1;
		int offsetY = (attackerX - victimX) * -1;
		int projectileId = 10;
		if (attacker.getAttackType() == Entity.AttackTypes.MAGIC) {
			int startGraphicId = player.getMagic().getSpellDefinitions()[player.getMagic().getMagicIndex()].getGraphicId();
			projectileId = player.getMagic().getSpellDefinitions()[player.getMagic().getMagicIndex()].getProjectileId();
			if (startGraphicId != 0)
				attacker.getUpdateFlags().sendHighGraphic(startGraphicId, 0);
		}
		attacker.setHitDelayTimer((((calculateProjectileSpeed(attacker, victim)) / 10) / 2) - 1);
		World.sendProjectile(attacker.getPosition(), offsetX, offsetY, projectileId, 43, 31, 
		calculateProjectileSpeed(attacker, victim), victim.getIndex() - 1);
	}
	
	public int calculateProjectileSpeed(Entity attacker, Entity victim) {
		int[][] distanceCalc =
		{{3, 60}, {6, 70}, {9, 80}, {12, 90}/*, {5, 100},
		{6, 110}, {7, 120}*/};
		for (int i = 0; i < distanceCalc.length; i++)
			if (Misc.getDistance(attacker.getPosition(), victim.getPosition()) <= distanceCalc[i][0])
				return distanceCalc[i][1];
		return 60;
	}
	
	/** 
	  * Checks if the attacker is within distance required for attacking.
	  */
	public boolean withinRange(Entity attacker, Entity victim) {
		int combatDistance = getDistanceForCombatType(attacker);
		if (attacker instanceof Player) {
			if (player.getPrimaryDirection() != -1 && player.getSecondaryDirection() == -1)
				combatDistance += 1;
			if (player.getPrimaryDirection() != -1 && player.getSecondaryDirection() != -1)
				combatDistance += 2;
		}
		if (attacker instanceof Npc && npc.getPrimaryDirection() != 1)
			combatDistance += 1;
		return Misc.getDistance(attacker.getPosition(), victim.getPosition()) <= combatDistance;
	}
	
	/** 
	  * Getting the distance needed for combat.
	  */
	public int getDistanceForCombatType(Entity entity) {
		int combatDistance = 0;
		switch (entity.getAttackType()) {
			case MELEE:
				combatDistance += 1;
				return combatDistance;
			case RANGE:
				combatDistance += 8;
				return combatDistance;
			case MAGIC:
				combatDistance += 9;
				return combatDistance;
			default:
				return combatDistance;
		}
	}
	
}
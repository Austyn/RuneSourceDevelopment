package com.rs2.model.content.combat.magic;

import com.rs2.model.players.Player;
import com.rs2.model.players.Item;
import com.rs2.model.players.ItemManager;
import com.rs2.model.npcs.Npc;
import com.rs2.model.Entity;

/**
  * By Mikey` of Rune-Server (MSN: metallic_mike@yahoo.com)
  */
public class Magic {

	private Player player;
	private Npc npc;
	
	public Magic(Player player) {
		this.player = player;
	}
	
	public Magic(Npc npc) {
		this.npc = npc;
	}
	
	/**
	  * All the spell definitions.
	  */
	private static SpellDefinition[] spellDefinitions = new SpellDefinition[50];
	
	/**
	  * I put this in place for loops. It corresponds to the number of indexes in the SpellDefinition[] array.
	  */
	public static int spellCount = 0;
	
	/**
	  * Staff ids, and the runes they can substitute for.
	  */
	public static final int[][] STAFFS =
	{//runeId, staffId
	{556, 1381}, {0, 4675}
	};
	
	private int magicId, magicMaxHit, magicIndex, graphicId, animationId;
	private boolean autoCasting;
	private String spellName;
	
	/**
	  * The type of magic you are casting.
	  */
	public enum MagicTypes {
		AUTO_CAST, SINGLE_ATTACK
	}
	
	/**
	  * This instance is used to declare single magic attacks, while the main class is used for autocasting.
	  */
	public Magic singleMagicAttack;
	
	/**
	  * Declaring a new instance of the magic class.
	  */
	public Magic(int magicId, int magicMaxHit, int magicIndex, int graphicId, int animationId, String spellName) {
		this.magicId = magicId;
		this.magicMaxHit = magicMaxHit;
		this.magicIndex = magicIndex;
		this.graphicId = graphicId;
		this.animationId = animationId;
		this.spellName = spellName;
	}
	
	/**
	  * Declaring a single magic attack, or setting your autocast settings.
	  */
	public void calculateAttackWithMagic(Entity attacker, Entity victim, int magicId, MagicTypes magicType) {
		if (attacker instanceof Player) {
			int magicIndex = -1;
			for (int i = 0; i < spellCount; i++) {
				if (magicId == spellDefinitions[i].getSpellId())
					magicIndex = i;
			}
			if (magicIndex == -1)
				return;
			int magicLevel = player.getSkill().getLevelForXP((int) player.getSkill().getExp()[player.getSkill().MAGIC]);
			String spellName = spellDefinitions[magicIndex].getSpellName();
			int magicLevelRequired = spellDefinitions[magicIndex].getRequiredLevel();
			int magicMaxHit = spellDefinitions[magicIndex].getMaxHit();
			int graphicId = spellDefinitions[magicIndex].getGraphicId();
			int[] runesRequired = spellDefinitions[magicIndex].getRunesRequired();
			if (magicLevel >= magicLevelRequired) {
				for (int i = 0; i < runesRequired.length; i += 2) {
					if (runesRequired[i] != 0) {
						if (player.getInventory().getItemContainer().getCount(runesRequired[i]) < runesRequired[i + 1]) {
							if (!usingCorrespondingStaff(runesRequired[i])) {
								String runeName = ItemManager.getInstance().getItemName(runesRequired[i]);
								player.getActionSender().sendMessage("You don't have enough " + runeName + "s to cast " + spellName + ".");
								attacker.setInstigatingAttack(false);
								attacker.setFollowingEntity(null);
								return;
							}
						}
					}
				}
				if (magicType == MagicTypes.SINGLE_ATTACK) {
					singleMagicAttack = new Magic(magicId, magicMaxHit, magicIndex, graphicId, animationId, spellName);
					attacker.setAttackType(Entity.AttackTypes.MAGIC);
					attacker.setInstigatingAttack(true);
					attacker.setTarget(victim);
				}
				else if (magicType == MagicTypes.AUTO_CAST) {
					this.magicId = magicId;
					this.magicMaxHit = magicMaxHit;
					this.spellName = spellName;
					this.magicIndex = magicIndex;
					this.autoCasting = true;
					attacker.setAttackType(Entity.AttackTypes.MAGIC);
				}
			}
			else {
				player.getActionSender().sendMessage("You need a magic level of " + magicLevelRequired + " to cast " + spellName + ".");
			}
		}
	}
	
	/**
	  * Reseting magic where needed.
	  */
	public void resetMagic(Entity attacker) {
		if (attacker instanceof Entity) {
			if (!isAutoCasting() && attacker.getAttackType() == Entity.AttackTypes.MAGIC
			|| isAutoCasting() && !hasStaff()) {
				magicId = -1;
				magicMaxHit = -1;
				spellName = "";
				player.getCombat().resetCombatType(attacker);
			}
			if (singleMagicAttack != null) {
				singleMagicAttack = null;
				attacker.setInstigatingAttack(false);
				attacker.setFollowingEntity(null);
			}
		}
	}
	
	public void resetAutoCast() {
		magicId = -1;
		magicMaxHit = -1;
		spellName = "";
	}
	
	/**
	  * Checking for and removing runes before casting the spell.
	  */
	public void removeRunes(Entity attacker) {
		int magicIndex = this.magicIndex;
		if (singleMagicAttack != null) {
			magicIndex = singleMagicAttack.magicIndex;
		}
		int[] runesRequired = spellDefinitions[magicIndex].getRunesRequired();
		int[] runesToRemove = new int[runesRequired.length];
		for (int i = 0; i < runesRequired.length; i += 2) {
			if (!usingCorrespondingStaff(runesRequired[i])) {
				if (player.getInventory().getItemContainer().getCount(runesRequired[i]) < runesRequired[i + 1]) {
					String runeName = ItemManager.getInstance().getItemName(runesRequired[i]);
					String spellName = getSpellName();
					player.getActionSender().sendMessage("You don't have enough " + runeName + "s to cast " + spellName + ".");
					player.getCombat().resetCombatType(attacker);
					return;
				}
			}
			if (!usingCorrespondingStaff(runesRequired[i])) {
					runesToRemove[i] = runesRequired[i];
					runesToRemove[i + 1] = runesRequired[i + 1];
			}
		}
		for (int i = 0; i < runesToRemove.length; i += 2)
			if (runesToRemove[i] != 0)
				player.getInventory().removeItem(new Item(runesToRemove[i], runesToRemove[i + 1]));
	}
	
	/**
	  * Applying any extra effects (poisoning, freezing, etc) after the attack.
	  */
	public void applyMagicEffects(Entity attacker, Entity victim) {
		int magicIndex = this.magicIndex;
		if (singleMagicAttack != null)
			magicIndex = singleMagicAttack.magicIndex;
		switch (magicIndex) {
			case 0:
				break;
		}
	}
	
	/**
	  * Check for a staff that corresponds to the rune.
	  */
	public boolean usingCorrespondingStaff(int runeId) {
		Item weapon = player.getEquipment().getItemContainer().get(3);
		if (weapon == null)
			return false;
		for (int i = 0; i < STAFFS.length; i++) {
			if (STAFFS[i][0] == runeId) {
				if (weapon.getId() == STAFFS[i][1]) {
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	/**
	  * Checking if the player has a staff equipped.
	  */
	public boolean hasStaff() {
		Item weapon = player.getEquipment().getItemContainer().get(3);
		if (weapon == null)
			return false;
		for (int i = 0; i < STAFFS.length; i++) {
			if (weapon.getId() == STAFFS[i][1]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	  * The buttons associated with anything relating to this class.
	  */
	public void clickingToAutoCast(int buttonId) {
		int magicIndex = this.magicIndex;
		if (singleMagicAttack != null)
			magicIndex = singleMagicAttack.magicIndex;
		for (int i = 0; i < spellCount; i++) {
			if (buttonId == spellDefinitions[i].getAutoCastButton()) {
				calculateAttackWithMagic(player, null, spellDefinitions[i].getSpellId(), MagicTypes.AUTO_CAST);
				player.getActionSender().sendConfig(108, autoCasting ? 1 : 0);
				if (autoCasting)
					player.getActionSender().sendString((String) "@yel@" + spellDefinitions[i].getSpellName(), 18585);
				else
					player.getActionSender().sendString("Choose spell", 18585);
				player.getActionSender().sendSidebarInterface(0, 328);
			}
		}
		switch (buttonId) {
			case 1093:
				if (magicId > 0) {
					autoCasting = !autoCasting;
					player.getActionSender().sendConfig(108, autoCasting ? 1 : 0);
				}
				else {
					player.getActionSender().sendMessage("You haven't selected a spell to autocast!");
				}
				break;
			case 1097:
				Item weapon = player.getEquipment().getItemContainer().get(3);
				if (weapon.getId() == 4675) {
					if (player.getMagicBookType() == Player.MagicBookTypes.ANCIENT)
						player.getActionSender().sendSidebarInterface(0, 1689);
					else
						player.getActionSender().sendMessage("You can't autocast ancient magic while using the modern spell book!");
				}
				else {
					if (player.getMagicBookType() == Player.MagicBookTypes.MODERN)
						player.getActionSender().sendSidebarInterface(0, 1829);
					else
						player.getActionSender().sendMessage("You can't autocast modern magic while using the ancient spell book!");
				}
				break;
			case 7212:
				player.getActionSender().sendSidebarInterface(0, 328);
				break;
		}
	}
	
	public int getMagicId() {
		return magicId;
	}
	
	public int getMagicMaxHit() {
		return magicMaxHit;
	}
	
	public int getMagicIndex() {
		if (singleMagicAttack != null)
			return singleMagicAttack.magicIndex;
		return this.magicIndex;
	}
	
	public String getSpellName() {
		return spellName;
	}
	
	public boolean isAutoCasting() {
		return autoCasting;
	}
	
	public void setAutoCasting(boolean autoCasting) {
		this.autoCasting = autoCasting;
		player.getActionSender().sendConfig(108, autoCasting ? 1 : 0);
		if (!autoCasting) {
			player.getActionSender().sendString("Choose spell", 18585);
			resetAutoCast();
		}
	}
	
	public static SpellDefinition[] getSpellDefinitions() {
		return spellDefinitions;
	}
	
}
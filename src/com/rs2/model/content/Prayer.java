package com.rs2.model.content;

import com.rs2.model.players.Player;

public class Prayer {

	private Player player;

	public Prayer(Player player) {
		this.player = player;
	}

	public static final int THICK_SKIN = 0, BURST_OF_STRENGTH = 1, CLARITY_OF_THOUGHT = 2, SHARP_EYE = 3, MYSTIC_WILL = 4,
	ROCK_SKIN = 5, SUPERHUMAN_STRENGTH = 6, IMPROVED_REFLEXES = 7, RAPID_RESTORE = 8, RAPID_HEAL = 9,
	PROTECT_ITEM = 10, HAWK_EYE = 11, MYSTIC_LORE = 12, STEEL_SKIN = 13, ULTIMATE_STRENGTH = 14,
	INCREDIBLE_REFLEXES = 15, PROTECT_FROM_MAGIC = 16, PROTECT_FROM_RANGE = 17, PROTECT_FROM_MELEE = 18,
	EAGLE_EYE = 19, MYSTIC_MIGHT = 20, RETRIBUTION = 21, REDEMPTION = 22, SMITE = 23, CHIVALRY = 24, PIETY = 25;

	private final Object[][] PRAYER_DATA = {
			{THICK_SKIN, 83, "Thick Skin", 1, 12}, 

			{BURST_OF_STRENGTH, 84, "Burst of Strength", 4, 12}, 

			{CLARITY_OF_THOUGHT, 85, "Clarity of Thought", 7, 12},

			{SHARP_EYE, 862, "Sharp Eye", 8, 12},

			{MYSTIC_WILL, 863, "Mystic Will", 0, 12},

			{ROCK_SKIN, 86, "Rock Skin", 10, 6},

			{SUPERHUMAN_STRENGTH, 87, "Superhuman Strength", 13, 6},

			{IMPROVED_REFLEXES, 88, "Improved Reflexes", 16, 6},

			{RAPID_RESTORE, 89, "Rapid Restore", 19, 26},

			{RAPID_HEAL, 90, "Rapid Heal", 22, 18},

			{PROTECT_ITEM, 91, "Protect Item", 25, 18},

			{HAWK_EYE, 864, "Hawk Eye", 26, 6},

			{MYSTIC_LORE, 865, "Mystic Lore", 27, 6},

			{STEEL_SKIN, 92, "Steel Skin", 28, 3},

			{ULTIMATE_STRENGTH, 93, "Ultimate Strength", 31, 3},

			{INCREDIBLE_REFLEXES, 94, "Incredible Reflexes", 34, 3},

			{PROTECT_FROM_MAGIC, 95, "Protect from Magic", 37, 3},

			{PROTECT_FROM_RANGE, 96, "Protect from Range", 40, 3},

			{PROTECT_FROM_MELEE, 97, "Protect from Melee", 43, 3},

			{EAGLE_EYE, 866, "Eagle Eye", 44, 3},

			{MYSTIC_MIGHT, 867, "Mystic Might", 45, 3},

			{RETRIBUTION, 98, "Retribution", 46, 12},

			{REDEMPTION, 99, "Redemtpion", 49, 6},

			{SMITE, 100, "Smite", 52, 2}
	};

	public int getPrayerData(int index, int dataSlot) {
		int data = 0;
		for (Object[] i : PRAYER_DATA) {
			if (i[0] == (Integer) index) {
				data = (Integer) i[dataSlot];
			}
		}
		return data;
	}

	public void activatePrayers(int id) {
		if (player.isDead()) {
			return;
		}
		int config = 0;
		String name = null;
		int level = 0;
		for (Object[] data : PRAYER_DATA) {
			if (data[0] == (Integer) id) {
				config = (Integer) data[1];
				name = (String) data[2];
				level = (Integer) data[3];
			}
		}
		if (player.getSkill().getLevel()[5] < level) {
			player.getActionSender().sendMessage("You need a prayer level of at least " + level + " to use " + name + ".");
			player.getActionSender().sendConfig(config, 0);
			return;
		}
		if (player.getSkill().getLevel()[5] <= 0) {
			resetAll();
			return;
		}
		int headIcon = -1;
		boolean hasHeadIcon = false;
		switch (id) {
		case PROTECT_FROM_MAGIC:
			headIcon = 2;
			hasHeadIcon = true;
			break;
		case PROTECT_FROM_RANGE:
			headIcon = 1;
			hasHeadIcon = true;
			break;
		case PROTECT_FROM_MELEE:
			headIcon = 0;
			hasHeadIcon = true;
			break;
		case RETRIBUTION:
			headIcon = 3;
			hasHeadIcon = true;
			break;
		case REDEMPTION:
			headIcon = 5;
			hasHeadIcon = true;
			break;
		case SMITE:
			headIcon = 4;
			hasHeadIcon = true;
			break;
		}
		if (hasHeadIcon) {
			player.setPrayerIcon(!player.getIsUsingPrayer()[id] ? headIcon : -1);
		}
		player.getIsUsingPrayer()[id] = !player.getIsUsingPrayer()[id];
		player.getActionSender().sendConfig(config, player.getIsUsingPrayer()[id] ? 1 : 0);
		switchPrayers(id);
		player.setPrayerDrainTimer(player.getIsUsingPrayer()[id] ? 0 : 1);
		player.setAppearanceUpdateRequired(true);
	}

	public void setPrayers(int buttonId) {
		switch(buttonId) {
		case 21233:
			activatePrayers(THICK_SKIN);
			break;
		case 21234:
			activatePrayers(BURST_OF_STRENGTH);
			break;
		case 21235:
			activatePrayers(CLARITY_OF_THOUGHT);
			break;
		case 77100:
			activatePrayers(SHARP_EYE);
			break;
		case 77102:
			activatePrayers(MYSTIC_WILL);
			break;
		case 21236:
			activatePrayers(ROCK_SKIN);
			break;
		case 21237:
			activatePrayers(SUPERHUMAN_STRENGTH);
			break;
		case 21238:
			activatePrayers(IMPROVED_REFLEXES);
			break;
		case 21239:
			activatePrayers(RAPID_RESTORE);
			break;
		case 21240:
			activatePrayers(RAPID_HEAL);
			break;
		case 21241:
			activatePrayers(PROTECT_ITEM);
			break;
		case 77104:
			activatePrayers(HAWK_EYE);
			break;
		case 77106:
			activatePrayers(MYSTIC_LORE);
			break;
		case 21242:
			activatePrayers(STEEL_SKIN);
			break;
		case 21243:
			activatePrayers(ULTIMATE_STRENGTH);
			break;
		case 21244:
			activatePrayers(INCREDIBLE_REFLEXES);
			break;
		case 21245:
			activatePrayers(PROTECT_FROM_MAGIC);
			break;
		case 21246:
			activatePrayers(PROTECT_FROM_RANGE);
			break;
		case 21247:
			activatePrayers(PROTECT_FROM_MELEE);
			break;
		case 77109:
			activatePrayers(EAGLE_EYE);
			break;
		case 77111:
			activatePrayers(MYSTIC_MIGHT);
			break;
		case 2171:
			activatePrayers(RETRIBUTION);
			break;
		case 2172:
			activatePrayers(REDEMPTION);
			break;
		case 2173:
			activatePrayers(SMITE);
			break;
		case 77113:
			activatePrayers(CHIVALRY);
			break;
		case 77115:
			activatePrayers(PIETY);
			break;
		}
	}

	private void switchPrayers(int id) {
		int[] turnOff = new int[0];
		switch (id) {
		case THICK_SKIN:
			turnOff = new int[] {ROCK_SKIN, STEEL_SKIN};
			break;
		case ROCK_SKIN:
			turnOff = new int[] {THICK_SKIN, STEEL_SKIN};
			break;
		case STEEL_SKIN:
			turnOff = new int[] {THICK_SKIN, ROCK_SKIN};
			break;
		case CLARITY_OF_THOUGHT:
			turnOff = new int[] {IMPROVED_REFLEXES, INCREDIBLE_REFLEXES};
			break;
		case IMPROVED_REFLEXES:
			turnOff = new int[] {CLARITY_OF_THOUGHT, INCREDIBLE_REFLEXES};
			break;
		case INCREDIBLE_REFLEXES:
			turnOff = new int[] {IMPROVED_REFLEXES, CLARITY_OF_THOUGHT};
			break;
		case BURST_OF_STRENGTH:
			turnOff = new int[] {SUPERHUMAN_STRENGTH, ULTIMATE_STRENGTH, SHARP_EYE, MYSTIC_WILL, HAWK_EYE, 
					MYSTIC_LORE, EAGLE_EYE, MYSTIC_MIGHT};
			break;
		case SUPERHUMAN_STRENGTH:
			turnOff = new int[] {BURST_OF_STRENGTH, ULTIMATE_STRENGTH, SHARP_EYE, MYSTIC_WILL, HAWK_EYE, 
					MYSTIC_LORE, EAGLE_EYE, MYSTIC_MIGHT};
			break;
		case ULTIMATE_STRENGTH:
			turnOff = new int[] {SUPERHUMAN_STRENGTH, BURST_OF_STRENGTH, SHARP_EYE, MYSTIC_WILL, HAWK_EYE, 
					MYSTIC_LORE, EAGLE_EYE, MYSTIC_MIGHT};
			break;
		case SHARP_EYE:
			turnOff = new int[] {MYSTIC_WILL, HAWK_EYE, MYSTIC_LORE, EAGLE_EYE, MYSTIC_MIGHT,
					BURST_OF_STRENGTH, SUPERHUMAN_STRENGTH, ULTIMATE_STRENGTH,
					CLARITY_OF_THOUGHT, IMPROVED_REFLEXES, INCREDIBLE_REFLEXES};
			break;
		case HAWK_EYE:
			turnOff = new int[] {MYSTIC_WILL, SHARP_EYE, MYSTIC_LORE, EAGLE_EYE, MYSTIC_MIGHT, 
					BURST_OF_STRENGTH, SUPERHUMAN_STRENGTH, ULTIMATE_STRENGTH,
					CLARITY_OF_THOUGHT, IMPROVED_REFLEXES, INCREDIBLE_REFLEXES};
			break;
		case EAGLE_EYE:
			turnOff = new int[] {MYSTIC_WILL, HAWK_EYE, MYSTIC_LORE, SHARP_EYE, MYSTIC_MIGHT,
					BURST_OF_STRENGTH, SUPERHUMAN_STRENGTH, ULTIMATE_STRENGTH,
					CLARITY_OF_THOUGHT, IMPROVED_REFLEXES, INCREDIBLE_REFLEXES};
			break;
		case MYSTIC_WILL:
			turnOff = new int[] {SHARP_EYE, HAWK_EYE, MYSTIC_LORE, EAGLE_EYE, MYSTIC_MIGHT,
					BURST_OF_STRENGTH, SUPERHUMAN_STRENGTH, ULTIMATE_STRENGTH,
					CLARITY_OF_THOUGHT, IMPROVED_REFLEXES, INCREDIBLE_REFLEXES};
			break;
		case MYSTIC_LORE:
			turnOff = new int[] {MYSTIC_WILL, HAWK_EYE, SHARP_EYE, EAGLE_EYE, MYSTIC_MIGHT,
					BURST_OF_STRENGTH, SUPERHUMAN_STRENGTH, ULTIMATE_STRENGTH,
					CLARITY_OF_THOUGHT, IMPROVED_REFLEXES, INCREDIBLE_REFLEXES};
			break;
		case MYSTIC_MIGHT:
			turnOff = new int[] {MYSTIC_WILL, HAWK_EYE, MYSTIC_LORE, EAGLE_EYE, SHARP_EYE,
					BURST_OF_STRENGTH, SUPERHUMAN_STRENGTH, ULTIMATE_STRENGTH,
					CLARITY_OF_THOUGHT, IMPROVED_REFLEXES, INCREDIBLE_REFLEXES};
			break;
		case PROTECT_FROM_MAGIC:
			turnOff = new int[] {REDEMPTION, SMITE, RETRIBUTION, PROTECT_FROM_RANGE, PROTECT_FROM_MELEE};
			break;
		case PROTECT_FROM_RANGE:
			turnOff = new int[] {REDEMPTION, SMITE, RETRIBUTION, PROTECT_FROM_MAGIC, PROTECT_FROM_MELEE};
			break;
		case PROTECT_FROM_MELEE:
			turnOff = new int[] {REDEMPTION, SMITE, RETRIBUTION, PROTECT_FROM_RANGE, PROTECT_FROM_MAGIC};
			break;
		case RETRIBUTION:
			turnOff = new int[] {REDEMPTION, SMITE, PROTECT_FROM_MELEE, PROTECT_FROM_RANGE, PROTECT_FROM_MAGIC};
			break;
		case REDEMPTION:
			turnOff = new int[] {RETRIBUTION, SMITE, PROTECT_FROM_MELEE, PROTECT_FROM_RANGE, PROTECT_FROM_MAGIC};
			break;
		case SMITE:
			turnOff = new int[] {REDEMPTION, RETRIBUTION, PROTECT_FROM_MELEE, PROTECT_FROM_RANGE, PROTECT_FROM_MAGIC};
			break;
		}
		for (int i : turnOff) {
			if (i != id) {
				player.getIsUsingPrayer()[i] = false;
				player.getActionSender().sendConfig(getPrayerData(i, 1), 0);
			}
		}
	}

	public void drainPrayer() {
		double drainAmount = 0;
		for (int i = 0; i < 25; i ++) {
			if (player.getIsUsingPrayer()[i]) {
				double drain = (double) getPrayerData(i, 4);
				double bonus = 0.035 * player.getBonuses().get(11);
				drain = drain * (1 + bonus);
				drain = 0.6 / drain;
				drainAmount += drain;
				player.setPrayerPoints(player.getPrayerPoints() - drainAmount);
				player.getSkill().getLevel()[5] = (int) player.getPrayerPoints();
				player.getSkill().refresh(5);
				if (player.getPrayerPoints() <= 0) {
					resetAll();
					player.getActionSender().sendMessage("You have ran out of prayer points; you must recharge at an altar.");
				}
			}
		}
	}

	public void resetAll() {
		for (int i = 0; i < 25; i ++) {
			player.getIsUsingPrayer()[i] = false;
			player.getActionSender().sendConfig(getPrayerData(i, 1), 0);
		}
		player.setPrayerIcon(-1);
		player.setAppearanceUpdateRequired(true);
	}

	public void resetPrayer(int id) {
		player.getIsUsingPrayer()[id] = false;
		player.getActionSender().sendConfig(getPrayerData(id, 1), 0);
	}

}

package com.rs2.model.players;

import java.util.Arrays;

import com.rs2.Constants;
import com.rs2.model.players.container.Container;
import com.rs2.model.players.container.Container.Type;

public class Equipment {
	
	public static final int EQUIPMENT_INTERFACE = 1688;
	public static final int SIZE = 14;
	public static final int ACCURATE = 0;
	public static final int AGGRESSIVE = 1;
	public static final int CONTROLLED = 2;
	public static final int DEFENSIVE = 3;
	
	private Player player;
	private Container itemContainer = new Container(Type.STANDARD, 14);
	
	public Equipment(Player player) {
		this.player = player;
	}
	
	public void sendEquipmentOnLogin() {
		refresh();
	}

	public static final int[] CAPES = {1007, 1019, 1021, 1023, 1027, 1029, 1031, 1052, 2412, 
		2413, 2414, 4304, 4315, 4317, 4319, 4321, 4323, 4325, 4327, 4329, 4331, 4333, 
		4335, 4337, 4339, 4341, 4343, 4345, 4347, 4349, 4351, 4353, 4355, 4357, 4359, 
		4361, 4363, 4365, 4367, 4369, 4371, 4373, 4375, 4377, 4379, 4381, 4383, 4385, 
		4387, 4389, 4391, 4393, 4395, 4397, 4399, 4401, 4403, 4405, 4407, 4409, 4411, 
		4413, 4514, 4516, 6070, 6570 
	};
	
	public static final int[] BOOTS = {88, 89, 626, 628, 630, 632, 634, 1061, 1837, 
		1846, 2577, 2579, 2894, 2904, 2914, 2924, 2934, 3061, 3105, 3107, 3791, 4097, 
		4107, 4117, 4119, 4121, 4123, 4125, 4127, 4129, 4131, 4310, 5062, 5063, 5064, 
		5345, 5557, 6069, 6106, 6143, 6145, 6147, 6328 
	};
	
	public static final int[] GLOVES = {1059, 1063, 1065, 1580, 2487, 2489, 2491, 
		2902, 2912, 2922, 2932, 2942, 3060, 3799, 4095, 4105, 4115, 4308, 5556, 6068, 
		6110, 6149, 6151, 6153, 7462 
	};
	
	public static final int[] SHIELDS = {1171, 1173, 1175, 1177, 1179, 1181, 1183, 
		1185, 1187, 1189, 1191, 1193, 1195, 1197, 1199, 1201, 1540, 2589, 2597, 2603, 
		2611, 2621, 2629, 2659, 2667, 2675, 2890, 3122, 3488, 3758, 3839, 3840, 3841, 
		3842, 3843, 3844, 4072, 4156, 4224, 4225, 4226, 4227, 4228, 4229, 4230, 4231, 
		4232, 4233, 4234, 4302, 4507, 4512, 6215, 6217, 6219, 6221, 6223, 6225, 6227, 
		6229, 6231, 6233, 6235, 6237, 6239, 6241, 6243, 6245, 6247, 6249, 6251, 6253, 
		6255, 6257, 6259, 6261, 6263, 6265, 6267, 6269, 6271, 6273, 6275, 6277, 6279, 
		6524, 8850
	};
	
	public static final int[] HATS = {5525, 5527, 5529, 5531, 5533, 5535, 5537, 5539, 5541, 5543, 5545, 5547, 5549, 5551, 74, 579, 656, 658, 660, 662, 664, 740, 1017, 1037, 1038, 1040, 1042, 1044, 1046, 1048, 1050, 1053, 1055, 1057, 1137, 1139, 1141, 1143, 1145, 1147, 1149, 1151, 1153, 1155, 1157, 1159, 1161, 1163, 1165, 1506, 1949, 2422, 2581, 2587, 2595, 2605, 2613, 2619, 2627, 2631, 2651, 2657, 2673, 2900, 2910, 2920, 2930, 2940, 2978, 2979, 2980, 2981, 2982, 2983, 2984, 2985, 2986, 2987, 2988, 2989, 2990, 2991, 2992, 2993, 2994, 2995, 3057, 3385, 3486, 3748, 3749, 3751, 3753, 3755, 3797, 4041, 4042, 4071, 4089, 4099, 4109, 4164, 4302, 4506, 4511, 4513, 4515, 4551, 4567, 4708, 4716, 4724, 4745, 4753, 4856, 4857, 4858, 4859, 4880, 4881, 4882, 4883, 4904, 4905, 4906, 4907, 4952, 4953, 4954,
			4955, 4976, 4977, 4978, 4979, 5013, 5014, 5554, 5574, 6109, 6128, 6131, 
			6137, 6182, 6188, 6335, 6337, 6339, 6345, 6355, 6365, 6375, 10828
	};
	
	public static final int[] AMULETS = {86, 87, 295, 421, 552, 589, 1478, 1692, 1694, 
		1696, 1698, 1700, 1702, 1704, 1706, 1708, 1710, 1712, 1725, 1727, 1729, 1731, 
		4021, 4081, 4250, 4677, 6040, 6041, 6208, 6585 
	};
	
	public static final int[] ARROWS = {78, 598, 877, 878, 879, 880, 881, 882, 883, 
		884, 885, 886, 887, 888, 889, 890, 891, 892, 893, 942, 2532, 2533, 2534, 2535, 
		2536, 2537, 2538, 2539, 2540, 2541, 2866, 4160, 4172, 4173, 4174, 4175, 4740, 
		5616, 5617, 5618, 5619, 5620, 5621, 5622, 5623, 5624, 5625, 5626, 5627, 6061, 
		6062 
	};
	
	public static final int[] RINGS = {773, 1635, 1637, 1639, 1641, 1643, 1645, 2550,
		2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566, 2568, 2570, 2572, 4202, 4657, 
		6465 
	};
	
	public static final int[] BODY = {3140, 1101, 1103, 1105, 1107, 1109, 1111, 1113, 
		1115, 1117, 1119, 1121, 1123, 1125, 1127, 1129, 1131, 1133, 1135, 2499, 2501, 
		2503, 2583, 2591, 2599, 2607, 2615, 2623, 2653, 2669, 3387, 3481, 4712, 4720, 
		4728, 4749, 4892, 4893, 4894, 4895, 4916, 4917, 4918, 4919, 4964, 4965, 4966, 
		4967, 6107, 6133, 6322, 10551
	};
	
	public static final int[] LEGS = {538, 542, 548, 1011, 1013, 1015, 1067, 1069, 1071, 
		1073, 1075, 1077, 1079, 1081, 1083, 1085, 1087, 1089, 1091, 1093, 2585, 2593, 
		2601, 2609, 2617, 2625, 2655, 2663, 2671, 2497, 3059, 3389, 3472, 3473, 3474, 3475, 
		3476, 3477, 3478, 3479, 3480, 3483, 3485, 3795, 4087, 4585, 4712, 4714, 4722, 
		4730, 4738, 4751, 4759, 4874, 4875, 4876, 4877, 4898, 4899, 4900, 4901, 4922, 
		4923, 4924, 4925, 4946, 4947, 4948, 4949, 4970, 4971, 4972, 4973, 4994, 4995, 
		4996, 4997, 5048, 5050, 5052, 5576, 6107, 6130, 6187, 6390
	};
	
	public static final int[] PLATEBODY = {3140, 1115, 1117, 1119, 1121, 1123, 1125, 
		1127, 2583, 2591, 2599, 2607, 2615, 2623, 2653, 2669, 3481, 4720, 4728, 4749, 10551 
	};
	
	public static final int[] FULL_HELM = {1153, 1155, 1157, 1159, 1161, 1163, 1165, 
		2587, 2595, 2605, 2613, 2619, 2627, 2657, 2673, 3486 
	};
	
	public static final int[] FULL_MASK = {1053, 1055, 1057 
	};
	
	public void refresh() {
		Item[] items = itemContainer.toArray();
		player.getActionSender().sendUpdateItems(EQUIPMENT_INTERFACE, items);
		sendBonus(player);
		sendWeaponInterface();
	}
	
	public void refresh(int slot, Item item) {
		player.getActionSender().sendUpdateItem(slot, EQUIPMENT_INTERFACE, item);
		sendBonus(player);
		sendWeaponInterface();
	}
	
	public void equip(int slot) {
		Item item = player.getInventory().getItemContainer().get(slot);
		if (!(Boolean) player.getAttributes().get("canTeleport")) {
			return;
		}
		if (item == null) {
			return;
		}
		if (item.getDefinition().isStackable()) {
			int slotType = getEquipmentSlot(item.getId());
			Item equipItem = itemContainer.get(slotType);
			if (itemContainer.get(slotType) != null) {
				if (item.getId() == equipItem.getId()) {
					itemContainer.set(slotType, new Item(item.getId(), 
						item.getCount() + equipItem.getCount()));
				} else {
					player.getInventory().addItemToSlot(equipItem, slot);
					itemContainer.set(slotType, item);
				}
			} else {
				itemContainer.set(slotType, item);
			}
			player.getInventory().removeItemSlot(item, slot);
		} else {
			int slotType = getEquipmentSlot(item.getId());
			if (itemContainer.get(slotType) != null) {
				Item equipItem = itemContainer.get(slotType);
				player.getInventory().addItemToSlot(equipItem, slot);
			} else {
				player.getInventory().removeItemSlot(item, slot);
			}
			itemContainer.set(slotType, new Item(item.getId(), item.getCount()));
		}
		refresh();
		player.getMagic().setAutoCasting(false);
		player.getInventory().refresh();
		player.setAppearanceUpdateRequired(true);
		sendBonus(player);
		player.getAttributes().put("usedGlory", Boolean.FALSE);
	}
	
	public void unequip(int slot) {
		Item item = itemContainer.get(slot);
		if (player.getInventory().getItemContainer().freeSlot() == -1) {
			player.getActionSender().sendMessage(
					"Not enough space in your inventory.");
			return;
		}
		if (item == null)
			return;
		itemContainer.remove(item, slot);
		player.getInventory().addItem(new Item(item.getId(), item.getCount()));
		refresh(slot, new Item(-1, 0));
		player.setAppearanceUpdateRequired(true);
		sendBonus(player);
	}
	
	public void setBonus(Player player) {
		for (int i = 0; i < 14; i ++) {
			if (player.getEquipment().getItemContainer().get(i) == null) {
				continue;
			}
			Item item = player.getEquipment().getItemContainer().get(i);
			for (int bonus = 0; bonus < 12; bonus ++) {
				player.setBonuses(bonus, player.getBonuses().get(bonus) + item.getEquipmentDefintion().getBonuses()[bonus]);
			}
		}
	}
	
	public void sendBonus(Player player) {
		int offset = 0;
		String send = "";
		for (int i = 0; i < 12; i++) {
			player.setBonuses(i, 0);
		}
		setBonus(player);
		for (int i = 0; i < 12; i++) {
			if (player.getBonuses().get(i) >= 0) {
				send = Constants.BONUS_NAME[i] + ": +" + player.getBonuses().get(i);
			} else {
				send = Constants.BONUS_NAME[i] + ": -" + Math.abs(player.getBonuses().get(i));
			}
			if (i == 10) {
				offset = 1;
			}
			player.getActionSender().sendString(send, (1675 + i + offset));
		}
	}
	
	public void sendWeaponInterface() {
		Item weapon = itemContainer.get(Constants.EQUIPMENT_SLOT_WEAPON);
		if (weapon == null) {
			player.getActionSender().sendSidebarInterface(0, 5855);
			player.getActionSender().sendString("Unarmed", 5857);
			return;
		}
		int interfaceId = weapon.getEquipmentDefintion().getInterfaceId();
		player.getActionSender().sendSidebarInterface(0, interfaceId);
		player.getActionSender().sendString(weapon.getDefinition().getName(), 
				interfaceId + 3);
		player.getActionSender().sendItemOnInterface(interfaceId + 1, 200, weapon.getId());
	}
	
	/**
	 * Gets the slot that the item belongs to in the player equipment. If it has
	 * no slot definition, the default slot is the weapon slot. This method is a
	 * lot faster than the traditional winterlove server approach, because it
	 * uses binary searching of the sorted equipment slot arrays instead of
	 * straight looping.
	 * 
	 * @param itemID
	 *            the item ID
	 * @return the slot
	 */
	public static int getEquipmentSlot(int itemID) {
		if (Arrays.binarySearch(PLATEBODY, itemID) > 0) {
			return Constants.EQUIPMENT_SLOT_CHEST;
		}
		if (Arrays.binarySearch(FULL_HELM, itemID) > 0) {
			return Constants.EQUIPMENT_SLOT_HEAD;
		}
		if (Arrays.binarySearch(FULL_MASK, itemID) > 0) {
			return Constants.EQUIPMENT_SLOT_HEAD;
		}
		if (Arrays.binarySearch(BODY, itemID) > 0) {
			return Constants.EQUIPMENT_SLOT_CHEST;
		}
		if (Arrays.binarySearch(LEGS, itemID) > 0) {
			return Constants.EQUIPMENT_SLOT_LEGS;
		}
		if (Arrays.binarySearch(CAPES, itemID) > 0) {
			return Constants.EQUIPMENT_SLOT_CAPE;
		}
		if (Arrays.binarySearch(BOOTS, itemID) > 0) {
			return Constants.EQUIPMENT_SLOT_FEET;
		}
		if (Arrays.binarySearch(GLOVES, itemID) > 0) {
			return Constants.EQUIPMENT_SLOT_HANDS;
		}
		if (Arrays.binarySearch(SHIELDS, itemID) > 0) {
			return Constants.EQUIPMENT_SLOT_SHIELD;
		}
		if (Arrays.binarySearch(HATS, itemID) > 0) {
			return Constants.EQUIPMENT_SLOT_HEAD;
		}
		if (Arrays.binarySearch(AMULETS, itemID) > 0) {
			return Constants.EQUIPMENT_SLOT_AMULET;
		}
		if (Arrays.binarySearch(ARROWS, itemID) > 0) {
			return Constants.EQUIPMENT_SLOT_ARROWS;
		}
		if (Arrays.binarySearch(RINGS, itemID) > 0) {
			return Constants.EQUIPMENT_SLOT_RING;
		}
		return Constants.EQUIPMENT_SLOT_WEAPON;
	}
	
	public static boolean isFullHelm(int id) {
		return Arrays.binarySearch(FULL_HELM, id) > 0;
	}
	
	public static boolean isFullMask(int id) {
		return Arrays.binarySearch(FULL_MASK, id) > 0;
	}
	
	public static boolean isPlatebody(int id) {
		return Arrays.binarySearch(PLATEBODY, id) > 0;
	}
	
	public int getStandAnim(Item item) {
		if (item == null) {
			return 0x328;
		}
		return ItemManager.getInstance().getEquipmentDefinitions()[item.getId()].
			getStandAnim();
	}
	
	public int getWalkAnim(Item item) {
		if (item == null) {
			return 0x333;
		}
		return ItemManager.getInstance().getEquipmentDefinitions()[item.getId()].
			getWalkAnim();
	}
	
	public int getRunAnim(Item item) {
		if (item == null) {
			return 0x338;
		}
		return ItemManager.getInstance().getEquipmentDefinitions()[item.getId()].
			getRunAnim();
	}

	public static void sortEquipmentSlotDefinitions() {
		Arrays.sort(CAPES);
		Arrays.sort(BOOTS);
		Arrays.sort(GLOVES);
		Arrays.sort(HATS);
		Arrays.sort(AMULETS);
		Arrays.sort(ARROWS);
		Arrays.sort(RINGS);
		Arrays.sort(BODY);
		Arrays.sort(LEGS);
		Arrays.sort(PLATEBODY);
		Arrays.sort(FULL_HELM);
		Arrays.sort(FULL_MASK);
	}
	
	public Container getItemContainer() {
		return itemContainer;
	}
	
	public class AttackStyles {

		private int accurate = 422;
		private int aggressive = 423;
		private int controlled = 422;
		private int defensive = 422;

		public int get(int style) {
			switch(style) {
			case 0:
				return accurate;
			case 1:
				return aggressive;
			case 2:
				return controlled;
			case 3:
				return defensive;
			}
			return accurate;
		}
	}
	
	public class EquipmentDefinition {
		
		private int id;
		private int[] bonuses;
		private int standAnim;
		private int walkAnim;
		private int runAnim;
		private AttackStyles attackStyles;
		private int defenseAnim;
		private int weaponSpeed;
		private int interfaceId;
		private int specialAttackInterface;
		private int soundId;
		private int hitTimer;

		public void setId(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public void setBonuses(int[] bonuses) {
			this.bonuses = bonuses;
		}

		public int[] getBonuses() {
			return bonuses;
		}

		public void setStandAnim(int standAnim) {
			this.standAnim = standAnim;
		}

		public int getStandAnim() {
			return standAnim;
		}

		public void setWalkAnim(int walkAnim) {
			this.walkAnim = walkAnim;
		}

		public int getWalkAnim() {
			return walkAnim;
		}

		public void setRunAnim(int runAnim) {
			this.runAnim = runAnim;
		}

		public int getRunAnim() {
			return runAnim;
		}

		public void setWeaponSpeed(int weaponSpeed) {
			this.weaponSpeed = weaponSpeed;
		}

		public int getWeaponSpeed() {
			return weaponSpeed;
		}

		public void setInterfaceId(int interfaceId) {
			this.interfaceId = interfaceId;
		}

		public int getInterfaceId() {
			return interfaceId;
		}

		public void setDefenseAnim(int defenseAnim) {
			this.defenseAnim = defenseAnim;
		}

		public int getDefenseAnim() {
			return defenseAnim;
		}

		public void setAttackStyles(AttackStyles attackStyles) {
			this.attackStyles = attackStyles;
		}

		public AttackStyles getAttackStyles() {
			return attackStyles;
		}

		public void setSpecialAttackInterface(int specialAttackInterface) {
			this.specialAttackInterface = specialAttackInterface;
		}

		public int getSpecialAttackInterface() {
			return specialAttackInterface;
		}

		public void setSoundId(int soundId) {
			this.soundId = soundId;
		}

		public int getSoundId() {
			return soundId;
		}

		public void setHitTimer(int hitTimer) {
			this.hitTimer = hitTimer;
		}

		public int getHitTimer() {
			return hitTimer;
		}
	}

}

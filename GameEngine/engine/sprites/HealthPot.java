package engine.sprites;

import engine.map.Tile;

public class HealthPot extends Item implements ItemAction{

	public HealthPot(String name, Tile tile) {
		super(name, tile);
		setImage("HPpot.png");
		classType = HealthPot.class;
	}

	public HealthPot(String name) {
		this.name = name;
		setImage("HPpot.png");
		classType = HealthPot.class;
	}
	
	public HealthPot() {
		name = "Health Potion";
		setImage("HPpot.png");
		classType = HealthPot.class;
	}

	@Override
	public void action() {
		if(owner.health+20>=owner.max_health)
			owner.health = owner.max_health;
		else
			owner.health+=20;
		owner.inventory.removeItem(this);
	}
}

package test.sprite;

import java.util.ArrayList;

import test.gameStates.PlayState;

public class CollisionManager {
	public ArrayList<Sprite> collidables;
	public PlayState playState;

	public ArrayList<Sprite> removeQueue;
	
	public CollisionManager(PlayState playState) {
		this.playState = playState;
		removeQueue = new ArrayList<Sprite>();
	}

	public void playerCollision(Sprite spr1, Sprite spr2) {
		Player player;
		if (spr1 instanceof Player)
			player = (Player) spr1;
		else
			player = (Player) spr2;
		player.lives--;
		player.reset();
	}

	public void bulletCollision(Sprite spr1, Sprite spr2) {
		Bullet bullet;
		Asteriod ast;
		if (spr2 instanceof Bullet && spr1 instanceof Asteriod) {
			bullet = (Bullet) spr2;
			ast = (Asteriod) spr1;
		} else {
			bullet = (Bullet) spr1;
			ast = (Asteriod) spr2;
		}
		
		addToRemoveQueue(bullet);
		
		ast.health--;

		if (ast.health == 0) {
			addToRemoveQueue(ast);
		}
	}
	
	public void addToRemoveQueue(Sprite sprite){
		removeQueue.add(sprite);
	}
	public void remove() {
		for(int i = 0; i < removeQueue.size(); i ++) {
			if(removeQueue.get(i) instanceof Bullet) {
				playState.player.bullets.remove(removeQueue.get(i));
				playState.sprites.remove(removeQueue.get(i));
				collidables.remove(removeQueue.get(i));
			}
			if(removeQueue.get(i) instanceof Asteriod) {
				playState.sprites.remove(removeQueue.get(i));
				playState.asteriods.remove(removeQueue.get(i));
			}
		}
	}

	public void updateSprites() {

		if (playState.sprites != null) {
			collidables = playState.sprites;
			collidables.addAll(playState.player.bullets);

			for (Sprite spr1 : collidables) {
				for (Sprite spr2 : collidables) {
					if (spr1 != null && spr2 != null
							&& spr1.rec.intersects(spr2.rec) && spr1 != spr2) {
						if (spr1 instanceof Player || spr2 instanceof Player) {
							playerCollision(spr1, spr2);
							System.out.println("Player hit");
						}
						if ((spr2 instanceof Bullet && spr1 instanceof Asteriod)
								|| (spr1 instanceof Bullet && spr2 instanceof Asteriod)) {
							System.out.println("Bullet hit asteriod");
							bulletCollision(spr1, spr2);
						}
					}
				}

			}
			remove();
		}
	}
}

package engine.asteriods;

import java.awt.Graphics2D;

import engine.core.Game;
import engine.core.Sandbox;
import engine.objects.GameObject;


public class AsteriodCreator extends GameObject {

	public Asteriod[] asteriods;

	public final int startNum = 20;

	public final int gameWidth = Game.WIDTH,
			gameHeight = Game.HEIGHT;

	public AsteriodCreator(Sandbox sandbox, int vpx, int vpy) {
		super(sandbox);
		asteriods = new Asteriod[startNum];
		for (int i = 0; i < asteriods.length; i++) {
			asteriods[i] = new Asteriod(sandbox,
					(int) (Math.random() * 300),
					(int) (Math.random() * 300),
					(int) (Math.random() * 8), (int) (Math.random() * 8), 40,
					40);
		}
	}

	@Override
	public void update() {

	}

	public void paint(Graphics2D g, int vpx, int vpy) {
		for (Asteriod ast : asteriods)
			if(ast!=null)
				ast.paint(g);
	}

	public void update(int vpx, int vpy) {
		for (Asteriod ast : asteriods) {
			if(ast!=null) {
			if (ast.xpos > 300|| ast.xpos < 0
					|| ast.ypos > 300
					|| ast.ypos < 0) {
				System.out.println("Asteriod out of window at"+ast.xpos+","+ast.ypos);
				ast = null;
				/*ast = new Asteriod(getSandbox(),
						//(int) (vpx-gameWidth/2 + (Math.random() * (vpx+gameWidth/2))),
						//(int) (vpy-gameHeight/2 + (Math.random() * (vpy+gameHeight/2))),
						(int)(Math.random()*300),
						(int)(Math.random()*300),
						(int) -(Math.random() * 8), (int) (Math.random() * 8),
						40, 40);*/
				//ast.update();
				//System.out.println("ast xpos:"+ast.xpos+" ypos:"+ast.ypos);
			}
			else
				ast.update();
		}
		}
	}
}

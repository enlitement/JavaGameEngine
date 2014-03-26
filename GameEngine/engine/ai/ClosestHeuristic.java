package engine.ai;

/**
 * A heuristic that uses the tile that is closest to the target as the next best
 * tile.
 * 
 * @author Kevin Glass
 */
public class ClosestHeuristic implements AStarHeuristic {

	@Override
	public float getCost(TileBasedMap map, Mover mover, int sx, int sy, int fx,
			int fy) {
		float dx = sx - fx;
		float dy = sy - fy;

		float result = (float) (Math.sqrt((dx * dx) + (dy * dy)));

		return result;
	}

}

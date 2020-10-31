package io.cubyz.world.cubyzgenerators.biomes;

import java.util.Random;

import io.cubyz.blocks.Block;
import io.cubyz.world.Chunk;
import io.cubyz.world.MetaChunk;
import io.cubyz.world.World;

/**
 * A small oval of different ground terrain.
 */

public class GroundPatch extends StructureModel {
	Block newGround;
	float width, variation, depth, smoothness;
	
	public GroundPatch(Block newGround, float chance, float width, float variation, float depth, float smoothness) {
		super(chance);
		this.newGround = newGround;
		this.width = width;
		this.variation = variation;
		this.depth = depth;
		this.smoothness = smoothness;
	}

	@Override
	public void generate(int x, int z, int height, Chunk chunk, MetaChunk metaChunk, Random rand) {
		float width = this.width + (rand.nextFloat() - 0.5f)*this.variation;
		float orientation = 2*(float)Math.PI*rand.nextFloat();
		float ellipseParam = 1 + rand.nextFloat(); 

		// Orientation of the major and minor half axis of the ellipse.
		// For now simply use a minor axis 1/ellipseParam as big as the major.
		float xMain = (float)Math.sin(orientation)/width;
		float zMain = (float)Math.cos(orientation)/width;
		float xSecn = ellipseParam*(float)Math.cos(orientation)/width;
		float zSecn = -ellipseParam*(float)Math.sin(orientation)/width;
		int xMin = (int)(x - width);
		if(xMin < 0) xMin = 0;
		int xMax = (int)(x + width);
		if(xMax >= 16) xMax = 15;
		int zMin = (int)(z - width);
		if(zMin < 0) zMin = 0;
		int zMax = (int)(z + width);
		if(zMax >= 16) zMax = 15;
		for(int px = chunk.startIndex(xMin); px <= xMax; px++) {
			for(int pz = chunk.startIndex(zMin); pz <= zMax; pz++) {
				float main = xMain*(x - px) + zMain*(z - pz);
				float secn = xSecn*(x - px) + zSecn*(z - pz);
				float dist = main*main + secn*secn;
				if(dist <= 1) {
					int startHeight = (int)(metaChunk.heightMap[px + chunk.getWorldX() & 255][pz + chunk.getWorldZ() & 255]*World.WORLD_HEIGHT);
					for(int py = chunk.startIndex((int)(startHeight - depth + 1)); py <= startHeight; py += chunk.getVoxelSize()) {
						if(dist <= smoothness || (dist - smoothness)/(1 - smoothness) < rand.nextFloat())
							chunk.updateBlock(px, py, pz, newGround);
					}
				}
			}
		}
	}
}

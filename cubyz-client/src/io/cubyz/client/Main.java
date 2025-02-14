package io.cubyz.client;

import io.cubyz.gui.Init;
import io.cubyz.gui.SceneManager;
import io.cubyz.gui.rendering.Input;
import io.cubyz.gui.rendering.Window;
import io.cubyz.renderUniverse.RenderUniverse;
import io.cubyz.server.BaseMod;
import io.cubyz.server.modding.ModLoader;
import io.cubyz.utils.gui.StatusInfo;
import io.cubyz.utils.log.Log;

/**
 *	Starting point of the client.
 *
 */
public class Main {
	public static void main(String[] args) {
		
		ModLoader loader = new ModLoader(null, new StatusInfo(), new BaseMod(), new ClientBaseMod());
		try {
			Init.init();
			
			Window.createWindow(1280, 720);
			Window.setBackgroundColor(0.5f, 1f, 0.5f, 1);
			
			SceneManager.init();
			Game.init();
			
			while(!Window.shouldClose()) {
				try{
					Thread.sleep(10);
				} catch(Exception e) {}
				Input.update();
				SceneManager.update();
				SceneManager.draw();
				RenderUniverse.draw();
				Window.render();
			}
			Log.info("Execution stopped.");
			
			System.exit(1);
		} catch(Exception e) {
			Log.severe(e);
		}
	}

}

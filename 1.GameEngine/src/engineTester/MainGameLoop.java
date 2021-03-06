package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.ObjLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop 
{
	
	
	public static void main(String[] args)
	{
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader= new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		
			
		
		RawModel model = ObjLoader.loadObjModel("dragon", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
		Entity stall = new Entity(texturedModel, new Vector3f(0,0,-20),0,0,0,1);
		
		Light light = new Light(new Vector3f(0,10,-15), new Vector3f(1,1,1));
		
		Camera camera = new Camera();
		
		while(!Display.isCloseRequested())
		{
			renderer.prepare();
			
			//game logic
			camera.move();
			stall.increaseRotation(0, 0.1f, 0);
			shader.start();
			shader.loadLight(light);
			shader.loadViewMatrix(camera);
			renderer.render(stall, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}

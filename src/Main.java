import MAndEngine.Engine;

/**
 * creates a new instance of mandengine that takes what class to use to start up
 * is in the form of a string as one can load multiple classes, say from a config file
 * 
 * @author Marcus
 *
 */
public class Main {
	public static void main(String[] args) {
		Engine engine = new Engine(new String[] {"ImgurDownloader"}, false, false);
		engine.run();
	}
}

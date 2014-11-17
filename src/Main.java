import MAndEngine.Engine;

/**
 * 
 * @author Marcus
 *
 */
public class Main {
	public static void main(String[] args) {
		
		Engine engine = new Engine(new String[] {"ImgurDownloader"}, false);
		engine.run();
		
	}
}

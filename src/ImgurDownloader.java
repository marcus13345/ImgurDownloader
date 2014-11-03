import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import MAndEngine.BasicApp;
import MAndEngine.Engine;

public class ImgurDownloader implements BasicApp {

	private ArrayList<GraphicalImgurRequest> requests;
	private String textBox = "";

	@Override
	public Dimension getResolution() {
		return new Dimension(800, 600);
	}

	@Override
	public void initialize() {

		requests = new ArrayList<GraphicalImgurRequest>();

	}

	@Override
	public void resumeApp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pauseApp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void tick() {
		int GAP = 70;
		int MARGIN_TOP = 30;

		for (int i = 0; i < requests.size(); i++) {
			requests.get(i).setDestination(450, MARGIN_TOP + (GAP * i));
			requests.get(i).tick();
		}
		
		Iterator<GraphicalImgurRequest> iterator = requests.iterator();
		while(iterator.hasNext()) {
			GraphicalImgurRequest req = iterator.next();
			if(!req.isBusy()) {
				iterator.remove();
			}
		}
		
		if (Engine.keys[KeyEvent.VK_ENTER] && textBox != "") {
			GraphicalImgurRequest request = new GraphicalImgurRequest(800, MARGIN_TOP + (GAP * (requests.size())), 450, MARGIN_TOP + (GAP * (requests.size())));
			request.saveSubreddit(textBox, 10, "time");
			requests.add(request);
			textBox = "";
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 800, 600);
		for (int i = 0; i < requests.size(); i++) {
			requests.get(i).draw(g);
		}
		g.drawString(textBox, 100, 100);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char c = ("" + e.getKeyChar()).toLowerCase().charAt(0);
		if (c >= 97 && c < 123) {

			textBox += c;

		}

		else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			textBox = textBox.substring(0, textBox.length() - 1);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTitle() {
		return "Imgur Downloader";
	}

	@Override
	public Color getColor() {
		return null;
	}

	@Override
	public int getFramerate() {
		return 30;
	}

	@Override
	public boolean getResizable() {
		return true;
	}

	@Override
	public boolean visibleInMenu() {
		return true;
	}

	@Override
	public void resized(int width, int height) {
		// not resizable
	}

	@Override
	public void click() {
		// TODO
	}

}

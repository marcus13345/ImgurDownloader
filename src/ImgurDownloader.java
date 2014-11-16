import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.ImageIO;

import MAndEngine.BasicApp;
import MAndEngine.Engine;

public class ImgurDownloader implements BasicApp, ImageListener{

	private ArrayList<GraphicalImgurRequest> requests;
	private String textBox = "";
	private BufferedImage image;

	@Override
	public Dimension getResolution() {
		return new Dimension(800, 600);
	}

	@Override
	public void initialize() {

		requests = new ArrayList<GraphicalImgurRequest>();
		try {
			image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("start.png"));
		} catch (Exception e) {
			image = null;
			System.out.println("eakljrfgskldhfg");
		}
		
	}

	@Override
	public void resumeApp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pauseApp() {
		// TODO Auto-generated method stub

	}

	final int LEFT_MARGIN = 20;
	
	@Override
	public void tick() {
		int GAP = GraphicalImgurRequest.WIDTH + LEFT_MARGIN;
		
		for (int i = 0; i < requests.size(); i++) {
			requests.get(i).setDestination(LEFT_MARGIN + (GAP * i), INNER_FRAME_HEIGHT + 25);
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
			execute(textBox);
			textBox = "";
		}
	}
	
	private void execute(String str) {
		String[] parts = str.split(" ");
		if(parts[0].equalsIgnoreCase("load")) {
			loadScript(parts[1]);
		}else{
			//assume subreddit
			GraphicalImgurRequest request = new GraphicalImgurRequest(0, 0, 0, 0, this);
			int pages = 0;
			try{
				pages = Integer.parseInt(parts[1]);
			}catch (Exception e) {}
			request.saveSubreddit(parts[0], pages == 0 ? 10 : pages, "time");
			requests.add(request);
		}
	}
	
	private void loadScript(String script) {
		File file = new File(System.getenv("APPDATA") + "\\MAndWorks\\ImgurDownloader\\scripts\\" + script + ".lst");
		try {
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) {
				String line = scan.nextLine();

				execute(line);
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//TODO NAME THESE RIGHT
	final int INNER_FRAME_WIDTH = 800;
	final int INNER_FRAME_HEIGHT = 520;
	
	//yeah, image scaling!
	final int IMAGE_WIDTH = 800;
	final int IMAGE_HEIGHT = 520 - 35;
	
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 800, 600);

		
		g.setColor(new Color(230, 230, 230));
		g.fillRect(0, 0, 800, 600);

		
		g.setColor(new Color(210, 210, 210));
		g.fillRect(0, 0, INNER_FRAME_WIDTH, INNER_FRAME_HEIGHT);

		g.setColor(new Color(230, 230, 230));
		g.fillRect(0, 0, 800, 35);
		
		for (int i = 0; i < requests.size(); i++) {
			requests.get(i).draw(g);
		}
		g.setColor(new Color(70, 70, 70));
		g.drawString("save: " + textBox, 100, 20);

		g.drawImage(image, 0, 35, null);
		g.setColor(rgb(255,154,0));
		g.drawLine(0, INNER_FRAME_HEIGHT, 800, INNER_FRAME_HEIGHT);
		g.setColor(rgb(255,154,0));
		g.drawLine(0, 35, 800, 35);
		
		g.setColor(new Color(50, 50, 50));
		g.drawString("Requests: " + requests.size(), 0, 600);
	}
	
	private Color rgb (int r, int g, int b) {
		return new Color(r, g, b);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char c = ("" + e.getKeyChar()).toLowerCase().charAt(0);
		if ((c >= 97 && c < 123) || c == ' ') {

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

	@Override
	public void newImage(BufferedImage image) {
		
		try {
			image = fitImageScale(image, IMAGE_WIDTH, IMAGE_HEIGHT);
			this.image = image;
		} catch (IOException e) {
			//eh, whatevs
		}
		
	}

	private static BufferedImage fitImageScale(BufferedImage image, int width, int height) throws IOException {

		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();

		double scaleY = (double) height / imageHeight;
		double scaleX = (double) width / imageWidth;

		// fill or fit bit
		// heh, hutch
		if (scaleX > scaleY)
			scaleX = scaleY;
		else
			scaleY = scaleX;

		// give us the transform object thing
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);

		// then make the scaling algorithm thing.
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

		// out new image that we need to crop onto the buffer with the right
		// dimensions.
		BufferedImage newImage = bilinearScaleOp.filter(image, new BufferedImage((int) (imageWidth * scaleX), (int) (imageHeight * scaleY), image.getType()));

		// make the buffer
		BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();

		int newImageWidth = newImage.getWidth(null);
		int newImageHeight = newImage.getHeight(null);

		// do math, shove it on.
		g.drawImage(newImage, (width - newImageWidth) / 2, (height - newImageHeight) / 2, null);

		// return dat
		return buffer;
	}

}

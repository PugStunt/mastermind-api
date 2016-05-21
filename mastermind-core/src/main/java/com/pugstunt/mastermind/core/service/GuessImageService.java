package com.pugstunt.mastermind.core.service;

import static java.util.Objects.isNull;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.common.collect.Lists;

public class GuessImageService {

	private static final String IMG_PATH = "C:\\Users\\evandro\\workspace\\mastermind-be\\image.png";

	private static final int IMG_HEIGHT = 50;
	private static final int IMG_WIDTH = 50;
	private static final int OFFSET = 5;

	private static final int[] RED = { 231, 76, 60 };
	private static final int[] BLUE = { 46, 204, 113 };
	private static final int[] GREEN = { 52, 152, 219 };
	private static final int[] YELLOW = { 241, 196, 15 };
	private static final int[] ORANGE = { 230, 126, 34 };
	private static final int[] PURPLE = { 155, 89, 182 };
	private static final int[] CYAN = { 26, 188, 156 };
	private static final int[] MAGENTA = { 222, 107, 174 };
	private static final Map<String, int[]> colorMap = new HashMap<>();
	static {
		colorMap.put("R", RED);
		colorMap.put("B", BLUE);
		colorMap.put("G", GREEN);
		colorMap.put("Y", YELLOW);
		colorMap.put("O", ORANGE);
		colorMap.put("P", PURPLE);
		colorMap.put("C", CYAN);
		colorMap.put("M", MAGENTA);
	}

	public static void main(String args[]) throws IOException {

		BufferedImage image = assembleResponseImage("RPYGOGOP");
		// return image;
		saveImage(image);
	}

	public static BufferedImage assembleResponseImage(String guessCode) throws IOException {

		List<String> colors = new ArrayList<>();

		for (int i = 0; i < guessCode.length(); i++) {
			Character colorCode = guessCode.charAt(i);
			if (isNull(Lists.newArrayList(com.pugstunt.mastermind.core.domain.enums.Color.values())
					.contains(colorCode.toString()))) {
				throw new IllegalArgumentException("Invalid color code: " + colorCode);
			}
			colors.add(colorCode.toString());
		}

		BufferedImage img = null;

		for (String color : colors) {
			if (isNull(img)) {
				img = newColor(colorMap.get(color));
			} else {
				img = joinBufferedImages(img, newColor(colorMap.get(color)));
			}
		}
		return img;
	}

	private static BufferedImage newColor(int[] rgb) throws IOException {

		BufferedImage img = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = img.createGraphics();

		graphics.setPaint(new Color(rgb[0], rgb[1], rgb[2]));
		graphics.fillRect(0, 0, img.getWidth(), img.getHeight());

		return img;
	}

	/**
	 * join two BufferedImage you can add a orientation parameter to control
	 * direction you can use a array to join more BufferedImage
	 */
	public static BufferedImage joinBufferedImages(BufferedImage baseImage, BufferedImage joinedImage) {

		int totalWidth = baseImage.getWidth() + joinedImage.getWidth() + OFFSET;

		BufferedImage newImage = new BufferedImage(totalWidth, IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = newImage.createGraphics();
		Color oldColor = graphics.getColor();

		graphics.setColor(oldColor);
		graphics.drawImage(baseImage, null, 0, 0);
		graphics.drawImage(joinedImage, null, baseImage.getWidth() + OFFSET, 0);
		graphics.dispose();
		return newImage;
	}

	private static File saveImage(BufferedImage img) throws IOException {

		// file object
		File f = null;
		// write image
		try {
			f = new File(IMG_PATH);
			ImageIO.write(img, "png", f);
			return f;
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		return null;
	}
}

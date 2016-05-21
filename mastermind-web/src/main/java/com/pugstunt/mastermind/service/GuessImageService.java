package com.pugstunt.mastermind.service;

import static java.util.Objects.isNull;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.pugstunt.mastermind.transformers.ColorTransformer;

public class GuessImageService {

	private static final int IMG_HEIGHT = 50;
	private static final int IMG_WIDTH = 50;
	private static final int OFFSET = 5;

	/**
	 * Receives a simple string with color values and returns an image according
	 * to the given colors
	 * 
	 * @param guessCode
	 * @return the image that corresponds to the given color parameters
	 * @throws IOException
	 */
	public byte[] assembleResponseImage(String guessCode) throws IOException {

		List<com.pugstunt.mastermind.core.domain.enums.Color> colors = guessCode.chars()
				.mapToObj(new ColorTransformer()).collect(Collectors.toList());

		BufferedImage img = null;

		for (com.pugstunt.mastermind.core.domain.enums.Color color : colors) {
			if (isNull(img)) {
				img = newColor(color.getRgb());
			} else {
				img = joinBufferedImages(img, newColor(color.getRgb()));
			}
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}

	/**
	 * Builds a new image that represents a single guess for the given color
	 * 
	 * @param rgb
	 *            an array with the rgb values
	 * @return a {@link BufferedImage}
	 * @throws IOException
	 */
	private static BufferedImage newColor(int[] rgb) throws IOException {

		BufferedImage img = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = img.createGraphics();

		graphics.setPaint(new Color(rgb[0], rgb[1], rgb[2]));
		graphics.fillRect(0, 0, img.getWidth(), img.getHeight());

		return img;
	}

	/**
	 * Joins two BufferedImages in one single image
	 * 
	 * @param baseImage
	 * @param joinedImage
	 * @return a single {@link BufferedImage} upon the merged given images
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

}

package com.pugstunt.mastermind.service;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.pugstunt.mastermind.core.domain.enums.Color;

public class ImageService {

	private static final int IMG_HEIGHT = 50;
	private static final int IMG_WIDTH = 50;
	private static final int OFFSET = 5;

	private static final Font RESPONSE_IMAGE_FONT = new Font("Consolas", Font.BOLD, 20);
	private static final String INVALID_CODE_IMAGE = "image/invalidImageCode.png";
	
	/**
	 * Receives a simple string with color values and returns an image according
	 * to the given colors
	 * 
	 * @param guessCode
	 * @return the image that corresponds to the given color parameters
	 * @throws IOException
	 */
	public byte[] assembleResponseImage(String guessCode) throws IOException {

		List<Color> colors = Color.from(guessCode);

		BufferedImage img = new BufferedImage((IMG_WIDTH + OFFSET) * colors.size(), IMG_HEIGHT,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = img.createGraphics();
		graphics.setFont(RESPONSE_IMAGE_FONT);

		for (int i = 0; i < colors.size(); i++) {
			Color color = colors.get(i);
			graphics.setPaint(color.getRgb());
			graphics.fillRect((IMG_HEIGHT + OFFSET) * i, 0, IMG_WIDTH, IMG_HEIGHT);
			graphics.setPaint(java.awt.Color.WHITE);
			graphics.drawString(Character.toString(color.getValue()), (IMG_WIDTH + OFFSET) * i + IMG_WIDTH / 2 - 5,
					IMG_HEIGHT / 2 + 8);
		}
		graphics.dispose();

		return imageToBytes(img);
	}

	public byte[] fallbackImage(String code) throws IOException {

		BufferedImage img = ImageIO
				.read(new File(ImageService.class.getResource(INVALID_CODE_IMAGE).getPath()));
		return imageToBytes(img);
	}

	private byte[] imageToBytes(BufferedImage img) throws IOException {

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(img, "png", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			return imageInByte;
		}
		
	}

}

package com.pugstunt.mastermind.api;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.inject.Inject;
import com.pugstunt.mastermind.service.ImageService;

@Path("v1/image")
public class ImageRS {

	private ImageService imageService;

	@Inject
	public ImageRS(ImageService imageService) {
		this.imageService = imageService;
	}

	/**
	 * 
	 * @param code
	 *            A text string representing the chain of colors of a guess, to
	 *            be transformed in an image representation
	 * @return The image related to the given color parameter
	 * @throws IOException
	 */
	@GET
	@Path("/{code}.png")
	@Produces("image/png")
	public byte[] guessImage(@PathParam("code") String code) throws IOException {

		return imageService.assembleResponseImage(code);
	}

}

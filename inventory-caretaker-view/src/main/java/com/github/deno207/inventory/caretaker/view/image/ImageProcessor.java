package com.github.deno207.inventory.caretaker.view.image;

import javafx.scene.image.Image;
import com.github.deno207.inventory.caretaker.model.ui.adaptor.DisplayItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The ImageProcessor is responsible for loading both external images and cached images for the UI, as well as saving
 * images to the application cache when a display item selects or changes it's image.
 *
 * @author Damion Wilson
 * @version 1.3
 */
public class ImageProcessor {
    private static final String STOCK_IMAGE_FOLDER = "images/stock/";
    private static final String DEFAULT_STOCK_IMAGE = "/" + STOCK_IMAGE_FOLDER + "Default-stock-image.png";
    private static final String CATEGORY_IMAGE_FOLDER = "images/category/";
    private static final String DEFAULT_CATEGORY_IMAGE = "/" + CATEGORY_IMAGE_FOLDER + "Default-category-image.png";
    private static final String SUPPLIER_IMAGE_FOLDER = "images/supplier/";
    private static final String DEFAULT_SUPPLIER_IMAGE = "/" + SUPPLIER_IMAGE_FOLDER + "Default-supplier-image.png";

    /**
     * Default constructor which checks that all of the image cache folders exist, and creates them if they don't
     */
    public ImageProcessor() {
        File file = new File(STOCK_IMAGE_FOLDER);
        if (!file.exists()) {
            if (!file.mkdirs()){
                System.err.println("Unable to make stock image directory");
            }
        }

        file = new File(CATEGORY_IMAGE_FOLDER);
        if (!file.exists()) {
            if (!file.mkdirs()){
                System.err.println("Unable to make category image directory");
            }
        }

        file = new File(SUPPLIER_IMAGE_FOLDER);
        if (!file.exists()) {
            if (!file.mkdirs()){
                System.err.println("Unable to make stock image directory");
            }
        }
    }

    /**
     * returns the provided display item's image.
     * If the image can't be loaded, then the default image for this item's type is returned instead
     * @param displayItem the DisplayItem who's image will be loaded
     * @return The DisplayItem's Image or the item's default image if the image couldn't be loaded
     */
    public Image getItemImage(DisplayItem displayItem) {
        String defaultImagePath = switch (displayItem.getItemType()) {
            case STOCK -> DEFAULT_STOCK_IMAGE;
            case SUPPLIER -> DEFAULT_SUPPLIER_IMAGE;
            case CATEGORY -> DEFAULT_CATEGORY_IMAGE;
        };

        InputStream imageStream = getImageInputStream(displayItem.getImagePath(), defaultImagePath);
        return new Image(imageStream);
    }

    /**
     * Attempts to create an inputStream from the specified file path so that the image can be loaded.
     * If this attempt fails, it will create an inputStream to the default image at defaultImagePath instead
     * @param imagePath The file path of the image that is being loaded
     * @param defaultImagePath The resource path to the default image
     * @return An inputStream from imagePath if it can be loaded, otherwise the inputStream from defaultImagePath
     */
    private InputStream getImageInputStream(String imagePath, String defaultImagePath) {
        if (imagePath == null || imagePath.isBlank()) {
            return getClass().getResourceAsStream(defaultImagePath);
        }

        InputStream imageStream = null;
        try {
            imageStream = new FileInputStream(imagePath);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to load file stream for item image");
            e.printStackTrace();
        }
        if (imageStream == null || imageStream.equals(InputStream.nullInputStream())) {
            return getClass().getResourceAsStream(defaultImagePath);
        }
        return imageStream;
    }

    /**
     * loads an image from the computer's file system, specified by the provided file object
     * @param file The file object representing the image file to be loaded
     * @return The image for file if it can be loaded, null otherwise
     */
    public Image loadExternalImage(File file) {
        if (file.exists() && file.isFile()) {
            try {
                return new Image(new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Saves an external image file to the applications image cache, so that it can be easily accessed later
     * @param file The file object representing the image to be saved
     * @param item The display item that this image will belong to.
     * @param id The database id of item
     * @return The name of the saved image file
     * @throws IOException if there is an error opening the provided file or saving it to the image cache
     */
    public String saveExternalImage(File file, DisplayItem item, int id) throws IOException {
        String imageFolder = switch (item.getItemType()) {
            case STOCK -> STOCK_IMAGE_FOLDER;
            case CATEGORY -> CATEGORY_IMAGE_FOLDER;
            case SUPPLIER -> SUPPLIER_IMAGE_FOLDER;
        };

        return saveExternalImage(file, imageFolder, item, id);
    }

    /**
     * Saves the provided external image to the image cache
     *
     * The image is saved under the file {item name}-{id}.{file-extension}.
     * @param file The image file to be saved
     * @param imageFolder The image cache folder the image will be saved in. This normally refers to the type of item
     *                    that the image is associated with
     * @param displayItem The item that this image belongs to
     * @param id the database id of displayItem
     * @return The name of the saved image file or blank if file is null
     * @throws IOException If there is an error saving the image file to the image cache
     */
    private String saveExternalImage(File file, String imageFolder, DisplayItem displayItem, int id) throws IOException {
        //if provided file is null, then there is no file to save and return an empty string
        if (file == null) {
            return "";
        }

        String fileName = constructImageFileName(displayItem, id, file);
        File internalStockImageFile = new File(imageFolder, fileName);

        try (FileInputStream inputStream = new FileInputStream(file); FileOutputStream outputStream = new FileOutputStream(internalStockImageFile)) {
            byte[] fileBytes = new byte[100];
            while (inputStream.read(fileBytes) > 0) {
                outputStream.write(fileBytes);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while saving Image file/n" + e.getMessage(), e);
        }
        return internalStockImageFile.getName();
    }

    /**
     * replaces an image currently in the image cache with a new image file
     * @param selectedImage The newly selected image file
     * @param displayItem The display item the image belongs to
     * @param id the database id of displayItem
     * @return The name of the saved image file
     * @throws IOException If there is an error deleting the old image or saving the new image
     */
    public String replaceCurrentImage(File selectedImage, DisplayItem displayItem, int id) throws IOException {
        String filePath = displayItem.getImagePath();
        File previousImageFile = null;
        if (filePath != null && !filePath.isBlank()) {
            previousImageFile = new File(filePath);
        }

        String imageFolder = switch (displayItem.getItemType()) {
            case SUPPLIER -> SUPPLIER_IMAGE_FOLDER;
            case CATEGORY -> CATEGORY_IMAGE_FOLDER;
            case STOCK -> STOCK_IMAGE_FOLDER;
        };

        if (previousImageFile == null && selectedImage == null) {
            //there is not previous image and no selected image, return a blank string
            return "";
        } else if (previousImageFile == null) {
            //if there is no previous image but there is a selected image, save the image
            return saveExternalImage(selectedImage, imageFolder, displayItem, id);
        } else if (selectedImage == null) {
            //if there is a previous image but no selected image, deleted the image
            if (previousImageFile.delete()) {
                return "";
            } else {
                throw new IOException("Unable to delete old image file");
            }
        } else {
            // if there is a previous image and a selected image, check if they are the same image
            if (checkIfImagesAreTheSame(previousImageFile, selectedImage)) {
                //if there are the same, don't need to do anything
                return previousImageFile.getName();
            } else {
                //if they are different, overwrite the old image with the new one
                return saveExternalImage(selectedImage, imageFolder, displayItem, id);
            }
        }
    }

    private String constructImageFileName(DisplayItem displayItem, int id, File selectedFile) {
        String name = displayItem.getName();
        String fileName = selectedFile.getName();
        int fileExtensionStartingPoint = fileName.lastIndexOf('.');
        String fileExtension = fileName.substring(fileExtensionStartingPoint);

        return name + "-" + id + fileExtension;
    }

    /**
     * checks to see if the provided image files contain the same images
     * @param oldImageFile The first image being checked
     * @param newImageFile The second image being checked
     * @return True if the files contain the same image, false otherwise
     * @throws IOException If there is an error opening the image files
     */
    private boolean checkIfImagesAreTheSame(File oldImageFile, File newImageFile) throws IOException {
        Image oldImage = new Image(new FileInputStream(oldImageFile));
        Image newImage = new Image(new FileInputStream(newImageFile));

        if (oldImage.getHeight() != newImage.getHeight()) {
            return false;
        }
        if (oldImage.getWidth() != newImage.getWidth()) {
            return false;
        }
        int oldPixelColour;
        int newPixelColour;

        for (int x = 0; x < oldImage.getWidth(); x++) {
            for (int y = 0; y < oldImage.getHeight(); y++) {
                oldPixelColour = oldImage.getPixelReader().getArgb(x, y);
                newPixelColour = newImage.getPixelReader().getArgb(x, y);
                if (oldPixelColour != newPixelColour) {
                    return false;
                }
            }
        }
        return true;
    }

}

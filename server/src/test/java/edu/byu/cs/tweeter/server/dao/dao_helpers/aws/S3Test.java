package edu.byu.cs.tweeter.server.dao.dao_helpers.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

import javax.imageio.ImageIO;

public class S3Test {

    public static final String BUCKET_NAME = "340tweeter";
    public static final String ALIAS = "@doNotDeleteTest";
    public static final String filePath = "C:\\Users\\jacob\\Documents\\Fall2020\\340\\Milestones\\M4\\Project\\server\\catch.png";

    @Test
    public void testUploadImage() {
        byte[] imageBytes = null;
        try {
            // file to byte[], File -> Path
            File file = new File(filePath);
            imageBytes = Files.readAllBytes(file.toPath());
            String result = S3.uploadImage(imageBytes, ALIAS);
            Assertions.assertTrue(!result.toUpperCase().contains("ERROR"));
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.assertFalse(true);
        }
    }

    @Test
    public void testRetrieveImage() {
        uploadImage(ALIAS); // Make sure we are retrieving an image that exists.
        String key = ALIAS + "Image";

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();

        try {
            // Retrieve image
            GetObjectRequest request = new GetObjectRequest(BUCKET_NAME, key);
            S3Object s3Object = s3Client.getObject(request);
            byte[] imageBytes = IOUtils.toByteArray(s3Object.getObjectContent());

            InputStream is = new ByteArrayInputStream(imageBytes);
            BufferedImage bi = ImageIO.read(is);

            File outputfile = new File("s3Result.png");
            ImageIO.write(bi, "png", outputfile);

            String filePath = "C:\\Users\\jacob\\Documents\\Fall2020\\340\\Milestones\\M4\\Project\\Server\\s3Result.png";
            File file = new File(filePath);
            Assertions.assertNotNull(file);

        } catch (IOException e) {
            e.getMessage();
            Assertions.assertFalse(true);
        }
    }

    public void uploadImage(String alias) {
        byte[] imageBytes = null;
        try {
            // file to byte[], File -> Path
            File file = new File(filePath);
            imageBytes = Files.readAllBytes(file.toPath());
            String result = S3.uploadImage(imageBytes, alias);
            Assertions.assertTrue(!result.toUpperCase().contains("ERROR"));
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.assertFalse(true);
        }
    }

    public byte[] byteArrayFromUrl(String urlText) throws MalformedURLException {
        URL url = new URL(urlText);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (InputStream inputStream = url.openStream()) {
            int n = 0;
            byte [] buffer = new byte[ 1024 ];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toByteArray();
    }
}

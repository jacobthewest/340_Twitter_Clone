package edu.byu.cs.tweeter.server.dao.dao_helpers.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class S3 {

    public static final String BUCKET_NAME = "340tweeter";
    public static final String S3_URL = "https://340tweeter.s3-us-west-2.amazonaws.com/";

    public static String uploadImage(byte[] imageBytes, String alias) {
        try {
            // Create AmazonS3 object for doing S3 operations
            AmazonS3 s3Client = AmazonS3ClientBuilder
                    .standard()
                    .withRegion("us-west-2")
                    .build();

            // Put the image bytes in an InputStreawm
            InputStream inputStream = new ByteArrayInputStream(imageBytes);

            // Add meta data to the image
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(imageBytes.length);
            meta.setContentType("image/png");

            // Name the image file
            String fileName = alias + "Image";

            // Put the image in the s3 buket.
            PutObjectResult putObjectResult = s3Client.putObject(new PutObjectRequest(
                    BUCKET_NAME, fileName, inputStream, meta)
                    .withCannedAcl(CannedAccessControlList.Private));

            // Close the InputStream
            inputStream.close();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
            return e.getMessage();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
            // There was an inputStream error
            e.printStackTrace();
            return e.getMessage();
        }
        String urlAppendage = "%40" + alias.substring(1, alias.length()) + "Image";
        String fullUrl = S3_URL + urlAppendage;
        return fullUrl;
    }

    public static byte[] getImage(String alias) {
        String key = alias + "Image";

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();

        try {
            // retrieve image
            GetObjectRequest request = new GetObjectRequest(BUCKET_NAME, key);
            S3Object s3Object = s3Client.getObject(request);
            byte[] imageBytes = IOUtils.toByteArray(s3Object.getObjectContent());
            return imageBytes;
        } catch (IOException e) {
            e.getMessage();
            return null;
        }
    }

    public static void deleteImage(String alias) {
        String key = alias + "Image";

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();

        // delete image
        DeleteObjectRequest request = new DeleteObjectRequest(BUCKET_NAME, key);
        s3Client.deleteObject(request);
    }
}

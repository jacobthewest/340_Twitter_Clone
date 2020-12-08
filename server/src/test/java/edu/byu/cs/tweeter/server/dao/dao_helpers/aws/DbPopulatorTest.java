package edu.byu.cs.tweeter.server.dao.dao_helpers.aws;

import org.junit.jupiter.api.Test;

import java.util.List;

import edu.byu.cs.tweeter.shared.domain.User;

public class DbPopulatorTest {

    @Test
    public void putUserFamilyDataIncludingImages() {
        DbPopulator.putUserDataForTest();
        S3Test s3Test = new S3Test();
        List<User> family = DbPopulator.getFamilyForPutUsersData();

        for(User member: family) {
            s3Test.uploadImage(member.getAlias());
        }
    }
}

package edu.byu.cs.tweeter.server.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.shared.domain.Status;
import edu.byu.cs.tweeter.shared.domain.User;

public class ListOfStatuses {

    private User definedUser;

    public static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    public static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
    public static final String MIKE = "https://i.imgur.com/VZQQiQ1.jpg";

    public final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL, "password");
    public final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL, "password");
    public final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL, "password");
    public final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL, "password");
    public final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL, "password");
    public final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL, "password");
    public final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL, "password");
    public final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL, "password");
    public final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL, "password");
    public final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL, "password");
    public final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL, "password");
    public final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL, "password");
    public final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL, "password");
    public final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL, "password");
    public final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL, "password");
    public final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL, "password");
    public final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL, "password");
    public final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL, "password");
    public final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL, "password");
    public final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL, "password");
    public final User JacobWest = new User("Jacob", "West", "@JacobWest", MIKE, "password");
    public final User RickyMartin = new User("Ricky", "Martin", "@RickyMartin", MIKE, "password");
    public final User RobertGardner = new User("Robert", "Gardner", "@RobertGardner", MIKE, "password");
    public final User Snowden = new User("The", "Snowden", "@Snowden", MIKE, "password");
    public final User TristanThompson = new User("Tristan", "Thompson", "@TristanThompson", MIKE, "password");
    public final User KCP = new User("Kontavius", "Caldwell Pope", "@KCP", MIKE, "password");
    public final User theMedia = new User("the", "Media", "@theMedia", MIKE, "password");
    public final User Rudy = new User("Rudy", "Gobert", "@Rudy", MIKE, "password");
    public final User BillBelichick = new User("Bill", "Belichick", "@BillBelichick", MIKE, "password");
    public final User TestUser = new User("Test", "User", "@TestUser", MALE_IMAGE_URL, "password");
    public final User userBarney = new User("Barney", "Rubble", "", "password");
    public final User DaffyDuck = new User("Daffy", "Duck", "", "password");
    public final User Zoe = new User("Zoe", "Zabriski", "", "password");


    public ListOfStatuses() {}

    public List<Status> get21Statuses(User definedUser) {
        List<Status> feed = new ArrayList<>();

        if(definedUser == null) {
            // --------------------- 1--------------------- //
            List<String> uOne = new ArrayList<>();
            uOne.add("multiply.com");
            List<String> mOne = new ArrayList<>();
            mOne.add("@JacobWest");
            mOne.add("@RickyMartin");
            Date d = createDate(2020, 0, 11, 0, 13);
            Calendar a = Calendar.getInstance();
            a.setTime(d);
            Status s = new Status(BillBelichick, "This is a text @JacobWest @RickyMartin multiply.com", uOne, a, mOne);
            feed.add(s); // # 1

            // --------------------- 2 --------------------- //
            List<String> uTwo = new ArrayList<>();
            uTwo.add("tinyurl.com");
            d = createDate(2020, 0, 11, 0, 14);
            Calendar b = Calendar.getInstance();
            b.setTime(d);
            s = new Status(Rudy, "You should visit tinyurl.com", uTwo, b, null);
            feed.add(s);

            // --------------------- 3 --------------------- //
            List<String> mThree = new ArrayList<>();
            mThree.add("@JacobWest");
            d = createDate(2019, 3, 16, 3, 34);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            s = new Status(theMedia, "Dolphins @JacobWest have Tua", null, c, mThree);
            feed.add(s);

            // --------------------- 4 --------------------- //
            d = createDate(2014, 7, 30, 17, 01);
            Calendar de = Calendar.getInstance();
            de.setTime(d);
            s = new Status(JacobWest, "Jacksonville will draft third", null, de, null);
            feed.add(s);

            // --------------------- 5 --------------------- //
            List<String> uFive = new ArrayList<>();
            uFive.add("dell.com");
            d = createDate(2012, 3, 3, 18, 21);
            Calendar e = Calendar.getInstance();
            e.setTime(d);
            s = new Status(RickyMartin, "I endorse dell.com", uFive, e, null);
            feed.add(s);

            // --------------------- 6 --------------------- //
            List<String> mSix = new ArrayList<>();
            mSix.add("@RobertGardner");
            mSix.add("@Snowden");
            mSix.add("@TristanThompson");
            d = createDate(2002, 10, 19, 14, 59);
            Calendar f = Calendar.getInstance();
            f.setTime(d);
            s = new Status(theMedia, "@RobertGardner @Snowden @TristanThompson", null, f, mSix);
            feed.add(s);

            // --------------------- 7 --------------------- //
            d = createDate(2000, 10, 19, 14, 59);
            Calendar g = Calendar.getInstance();
            g.setTime(d);
            s = new Status(KCP, ";)", null, g, null);
            feed.add(s);

            // --------------------- 8 --------------------- //
            d = createDate(2003, 5, 30, 16, 11);
            Calendar h = Calendar.getInstance();
            h.setTime(d);
            s = new Status(TristanThompson, "One, two, pick and roll", null, h, null);
            feed.add(s);

            // --------------------- 9 --------------------- //
            d = createDate(2001, 9, 4, 18, 29);
            Calendar i = Calendar.getInstance();
            i.setTime(d);
            s = new Status(Snowden, "A lot of old guys past their prime.", null, i, null);
            feed.add(s);

            // --------------------- 10 --------------------- //
            d = createDate(2019, 8, 12, 19, 1);
            Calendar j = Calendar.getInstance();
            j.setTime(d);
            s = new Status(TristanThompson, "I remember being a role player.", null, j, null);
            feed.add(s);

            // --------------------- 11 --------------------- //
            List<String> uEleven = new ArrayList<>();
            List<String> mEleven = new ArrayList<>();
            uEleven.add("salon.com");
            mEleven.add("@KCP");
            d = createDate(2007, 4, 15, 4, 43);
            Calendar k = Calendar.getInstance();
            k.setTime(d);
            s = new Status(theMedia, "Why did we sign him? @KCP. salon.com", uEleven, k, mEleven);
            feed.add(s);

            // --------------------- 12 --------------------- //
            List<String> mTwelve = new ArrayList<>();
            mTwelve.add("@theMedia");
            mTwelve.add("@Rudy");
            d = createDate(2016, 8, 9, 8, 5);
            Calendar l = Calendar.getInstance();
            l.setTime(d);
            s = new Status(BillBelichick, "Rudy and I are chill @theMedia @Rudy", null, l, mTwelve);
            feed.add(s);

            // --------------------- 13 --------------------- //
            d = createDate(2013, 3, 13, 9, 56);
            Calendar m = Calendar.getInstance();
            m.setTime(d);
            s = new Status(JacobWest, "I am the tinker man!", null, m, null);
            feed.add(s);

            // --------------------- 14 --------------------- //
            List<String> uFourteen = new ArrayList<>();
            List<String> mFourteen = new ArrayList<>();
            uFourteen.add("https://www.bostonherald.com/wp-content/uploads/2019/09/patsnl037.jpg");
            mFourteen.add("@BillBelichick");
            d = createDate(2013, 3, 13, 9, 55);
            Calendar n = Calendar.getInstance();
            n.setTime(d);
            s = new Status(JacobWest, "We are the new power couple @BillBelichick https://www.bostonherald.com/wp-content/uploads/2019/09/patsnl037.jpg", uFourteen, n, mFourteen);
            feed.add(s);

            // --------------------- 15 --------------------- //
            d = createDate(2012, 3, 13, 9, 55);
            Calendar o = Calendar.getInstance();
            o.setTime(d);
            s = new Status(Snowden, "That takes a lot of ownership!", null, o, null);
            feed.add(s);

            // --------------------- 16 --------------------- //
            d = createDate(2012, 3, 12, 9, 55);
            Calendar p = Calendar.getInstance();
            p.setTime(d);
            s = new Status(TristanThompson, "We beat the clippers!", null, p, null);
            feed.add(s);

            // --------------------- 17 --------------------- //
            d = createDate(2012, 3, 12, 9, 45);
            Calendar q = Calendar.getInstance();
            q.setTime(d);
            s = new Status(BillBelichick, "I lift bro!", null, q, null);
            feed.add(s);

            // --------------------- 18 --------------------- //
            d = createDate(2010, 8, 17, 9, 55);
            Calendar r = Calendar.getInstance();
            r.setTime(d);
            s = new Status(Rudy, "The truth is an acquired taste.", null, r, null);
            feed.add(s);

            // --------------------- 19 --------------------- //
            d = createDate(2020, 8, 17, 9, 55);
            Calendar sa = Calendar.getInstance();
            sa.setTime(d);
            s = new Status(RickyMartin, "Encuentra la buena vida baby! #CoronaLite", null, sa, null);
            feed.add(s);

            // --------------------- 20 --------------------- //
            d = createDate(2020, 0, 27, 23, 55);
            Calendar t = Calendar.getInstance();
            t.setTime(d);
            s = new Status(KCP, "Me calle bien el Snoop Dogg", null, t, null);
            feed.add(s);

            // --------------------- 21 --------------------- //
            d = createDate(2020, 3, 7, 9, 4);
            Calendar u = Calendar.getInstance();
            u.setTime(d);
            s = new Status(RobertGardner, "Hago buena musica", null, u, null);
            feed.add(s);
        } else {
            // --------------------- 1--------------------- //
            List<String> uOne = new ArrayList<>();
            uOne.add("multiply.com");
            List<String> mOne = new ArrayList<>();
            mOne.add("@JacobWest");
            mOne.add("@RickyMartin");
            Date d = createDate(2020, 0, 11, 0, 13);
            Calendar a = Calendar.getInstance();
            a.setTime(d);
            Status s = new Status(definedUser, "This is a text @JacobWest @RickyMartin multiply.com", uOne, a, mOne);
            feed.add(s); // # 1

            // --------------------- 2 --------------------- //
            List<String> uTwo = new ArrayList<>();
            uTwo.add("tinyurl.com");
            d = createDate(2020, 0, 11, 0, 14);
            Calendar b = Calendar.getInstance();
            b.setTime(d);
            s = new Status(definedUser, "You should visit tinyurl.com", uTwo, b, null);
            feed.add(s);

            // --------------------- 3 --------------------- //
            List<String> mThree = new ArrayList<>();
            mThree.add("@JacobWest");
            d = createDate(2019, 3, 16, 3, 34);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            s = new Status(definedUser, "Dolphins @JacobWest have Tua", null, c, mThree);
            feed.add(s);

            // --------------------- 4 --------------------- //
            d = createDate(2014, 7, 30, 17, 01);
            Calendar de = Calendar.getInstance();
            de.setTime(d);
            s = new Status(definedUser, "Jacksonville will draft third", null, de, null);
            feed.add(s);

            // --------------------- 5 --------------------- //
            List<String> uFive = new ArrayList<>();
            uFive.add("dell.com");
            d = createDate(2012, 3, 3, 18, 21);
            Calendar e = Calendar.getInstance();
            e.setTime(d);
            s = new Status(definedUser, "I endorse dell.com", uFive, e, null);
            feed.add(s);

            // --------------------- 6 --------------------- //
            List<String> mSix = new ArrayList<>();
            mSix.add("@RobertGardner");
            mSix.add("@Snowden");
            mSix.add("@TristanThompson");
            d = createDate(2002, 10, 19, 14, 59);
            Calendar f = Calendar.getInstance();
            f.setTime(d);
            s = new Status(definedUser, "@RobertGardner @Snowden @TristanThompson", null, f, mSix);
            feed.add(s);

            // --------------------- 7 --------------------- //
            d = createDate(2000, 10, 19, 14, 59);
            Calendar g = Calendar.getInstance();
            g.setTime(d);
            s = new Status(definedUser, ";)", null, g, null);
            feed.add(s);

            // --------------------- 8 --------------------- //
            d = createDate(2003, 5, 30, 16, 11);
            Calendar h = Calendar.getInstance();
            h.setTime(d);
            s = new Status(definedUser, "One, two, pick and roll", null, h, null);
            feed.add(s);

            // --------------------- 9 --------------------- //
            d = createDate(2001, 9, 4, 18, 29);
            Calendar i = Calendar.getInstance();
            i.setTime(d);
            s = new Status(definedUser, "A lot of old guys past their prime.", null, i, null);
            feed.add(s);

            // --------------------- 10 --------------------- //
            d = createDate(2019, 8, 12, 19, 1);
            Calendar j = Calendar.getInstance();
            j.setTime(d);
            s = new Status(definedUser, "I remember being a role player.", null, j, null);
            feed.add(s);

            // --------------------- 11 --------------------- //
            List<String> uEleven = new ArrayList<>();
            List<String> mEleven = new ArrayList<>();
            uEleven.add("salon.com");
            mEleven.add("@KCP");
            d = createDate(2007, 4, 15, 4, 43);
            Calendar k = Calendar.getInstance();
            k.setTime(d);
            s = new Status(definedUser, "Why did we sign him? @KCP. salon.com", uEleven, k, mEleven);
            feed.add(s);

            // --------------------- 12 --------------------- //
            List<String> mTwelve = new ArrayList<>();
            mTwelve.add("@theMedia");
            mTwelve.add("@Rudy");
            d = createDate(2016, 8, 9, 8, 5);
            Calendar l = Calendar.getInstance();
            l.setTime(d);
            s = new Status(definedUser, "Rudy and I are chill @theMedia @Rudy", null, l, mTwelve);
            feed.add(s);

            // --------------------- 13 --------------------- //
            d = createDate(2013, 3, 13, 9, 56);
            Calendar m = Calendar.getInstance();
            m.setTime(d);
            s = new Status(definedUser, "I am the tinker man!", null, m, null);
            feed.add(s);

            // --------------------- 14 --------------------- //
            List<String> uFourteen = new ArrayList<>();
            List<String> mFourteen = new ArrayList<>();
            uFourteen.add("https://www.bostonherald.com/wp-content/uploads/2019/09/patsnl037.jpg");
            mFourteen.add("@BillBelichick");
            d = createDate(2013, 3, 13, 9, 55);
            Calendar n = Calendar.getInstance();
            n.setTime(d);
            s = new Status(definedUser, "We are the new power couple @BillBelichick https://www.bostonherald.com/wp-content/uploads/2019/09/patsnl037.jpg", uFourteen, n, mFourteen);
            feed.add(s);

            // --------------------- 15 --------------------- //
            d = createDate(2012, 3, 13, 9, 55);
            Calendar o = Calendar.getInstance();
            o.setTime(d);
            s = new Status(definedUser, "That takes a lot of ownership!", null, o, null);
            feed.add(s);

            // --------------------- 16 --------------------- //
            d = createDate(2012, 3, 12, 9, 55);
            Calendar p = Calendar.getInstance();
            p.setTime(d);
            s = new Status(definedUser, "We beat the clippers!", null, p, null);
            feed.add(s);

            // --------------------- 17 --------------------- //
            d = createDate(2012, 3, 12, 9, 45);
            Calendar q = Calendar.getInstance();
            q.setTime(d);
            s = new Status(definedUser, "I lift bro!", null, q, null);
            feed.add(s);

            // --------------------- 18 --------------------- //
            d = createDate(2010, 8, 17, 9, 55);
            Calendar r = Calendar.getInstance();
            r.setTime(d);
            s = new Status(definedUser, "The truth is an acquired taste.", null, r, null);
            feed.add(s);

            // --------------------- 19 --------------------- //
            d = createDate(2020, 8, 17, 9, 55);
            Calendar sa = Calendar.getInstance();
            sa.setTime(d);
            s = new Status(definedUser, "Encuentra la buena vida baby! #CoronaLite", null, sa, null);
            feed.add(s);

            // --------------------- 20 --------------------- //
            d = createDate(2020, 0, 27, 23, 55);
            Calendar t = Calendar.getInstance();
            t.setTime(d);
            s = new Status(definedUser, "Me calle bien el Snoop Dogg", null, t, null);
            feed.add(s);

            // --------------------- 21 --------------------- //
            d = createDate(2020, 3, 7, 9, 4);
            Calendar u = Calendar.getInstance();
            u.setTime(d);
            s = new Status(definedUser, "Hago buena musica", null, u, null);
            feed.add(s);
        }
        return feed;
    }

    public Map<String, List<Status>> initializeFeed() {
        Map<String, List<Status>> returnMe = new HashMap<String, List<Status>>();
        List<Status> statuses = get21Statuses(null);
        returnMe.put(user1.getAlias(), statuses);
        returnMe.put(user2.getAlias(), statuses);
        returnMe.put(user3.getAlias(), statuses);
        returnMe.put(user4.getAlias(), statuses);
        returnMe.put(user5.getAlias(), statuses);
        returnMe.put(user6.getAlias(), statuses);
        returnMe.put(user7.getAlias(), statuses);
        returnMe.put(user8.getAlias(), statuses);
        returnMe.put(user9.getAlias(), statuses);
        returnMe.put(user10.getAlias(), statuses);
        returnMe.put(user11.getAlias(), statuses);
        returnMe.put(user12.getAlias(), statuses);
        returnMe.put(user13.getAlias(), statuses);
        returnMe.put(user14.getAlias(), statuses);
        returnMe.put(user15.getAlias(), statuses);
        returnMe.put(user16.getAlias(), statuses);
        returnMe.put(user17.getAlias(), statuses);
        returnMe.put(user18.getAlias(), statuses);
        returnMe.put(user19.getAlias(), statuses);
        returnMe.put(user20.getAlias(), statuses);
        returnMe.put(JacobWest.getAlias(), statuses);
        returnMe.put(RickyMartin.getAlias(), statuses);
        returnMe.put(RobertGardner.getAlias(), statuses);
        returnMe.put(Snowden.getAlias(), statuses);
        returnMe.put(TristanThompson.getAlias(), statuses);
        returnMe.put(KCP.getAlias(), statuses);
        returnMe.put(theMedia.getAlias(), statuses);
        returnMe.put(Rudy.getAlias(), statuses);
        returnMe.put(BillBelichick.getAlias(), statuses);
        returnMe.put(TestUser.getAlias(), statuses);
        return returnMe;
    }

    public Map<String, List<Status>> initializeStory() {
        Map<String, List<Status>> returnMe = new HashMap<String, List<Status>>();
        List<Status> jacobStatusList = get21Statuses(JacobWest);
        returnMe.put(JacobWest.getAlias(), jacobStatusList);
        List<Status> rickyStatusList = get21Statuses(RickyMartin);
        returnMe.put(RickyMartin.getAlias(), rickyStatusList);
        List<Status> robertStatusList = get21Statuses(RobertGardner);
        returnMe.put(RobertGardner.getAlias(), robertStatusList);
        List<Status> snowdenStatusList = get21Statuses(Snowden);
        returnMe.put(Snowden.getAlias(), snowdenStatusList);
        List<Status> tristanStatusList = get21Statuses(TristanThompson);
        returnMe.put(TristanThompson.getAlias(), tristanStatusList);
        List<Status> kcpStatusList = get21Statuses(KCP);
        returnMe.put(KCP.getAlias(), kcpStatusList);
        List<Status> mediaStatusList = get21Statuses(theMedia);
        returnMe.put(theMedia.getAlias(), mediaStatusList);
        List<Status> rudyStatusList = get21Statuses(Rudy);
        returnMe.put(Rudy.getAlias(), rudyStatusList);
        List<Status> billStatusList = get21Statuses(BillBelichick);
        returnMe.put(BillBelichick.getAlias(), billStatusList);
        List<Status> testStatusList = get21Statuses(TestUser);
        returnMe.put(TestUser.getAlias(), testStatusList);
        List<Status> a = get21Statuses(user1);
        returnMe.put(user1.getAlias(), a);
        List<Status> b = get21Statuses(user2);
        returnMe.put(user2.getAlias(), b);
        List<Status> c = get21Statuses(user3);
        returnMe.put(user3.getAlias(), c);
        List<Status> d = get21Statuses(user4);
        returnMe.put(user4.getAlias(), d);
        List<Status> e = get21Statuses(user5);
        returnMe.put(user5.getAlias(), e);
        List<Status> f = get21Statuses(user6);
        returnMe.put(user6.getAlias(), f);
        List<Status> g = get21Statuses(user7);
        returnMe.put(user7.getAlias(), g);
        List<Status> h = get21Statuses(user8);
        returnMe.put(user8.getAlias(), h);
        List<Status> i = get21Statuses(user9);
        returnMe.put(user9.getAlias(), i);
        List<Status> j = get21Statuses(user10);
        returnMe.put(user10.getAlias(), j);
        List<Status> k = get21Statuses(user11);
        returnMe.put(user11.getAlias(), k);
        List<Status> l = get21Statuses(user12);
        returnMe.put(user12.getAlias(), l);
        List<Status> m = get21Statuses(user13);
        returnMe.put(user13.getAlias(), m);
        List<Status> n = get21Statuses(user14);
        returnMe.put(user14.getAlias(), n);
        List<Status> o = get21Statuses(user15);
        returnMe.put(user15.getAlias(), o);
        List<Status> p = get21Statuses(user16);
        returnMe.put(user16.getAlias(), p);
        List<Status> q = get21Statuses(user17);
        returnMe.put(user17.getAlias(), q);
        List<Status> r = get21Statuses(user18);
        returnMe.put(user18.getAlias(), r);
        List<Status> s = get21Statuses(user19);
        returnMe.put(user19.getAlias(), s);
        List<Status> t = get21Statuses(user20);
        returnMe.put(user20.getAlias(), t);
        return returnMe;
    }

    private Date createDate(int year, int month, int day, int hour, int minute) {
        Date d = new Date(year - 1900, month, day);
        d.setHours(hour);
        d.setMinutes(minute);
        return d;
    }
}

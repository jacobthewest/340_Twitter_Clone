package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.CountDAO;
import edu.byu.cs.tweeter.shared.service.CountService;
import edu.byu.cs.tweeter.shared.service.request.CountRequest;
import edu.byu.cs.tweeter.shared.service.response.CountResponse;

public class CountServiceImpl implements CountService {

    @Override
    public CountResponse getCount(CountRequest request) {

        // TODO: Generates dummy data. Replace with a real implementation.
        return getCountDAO().getCount(request);
    }

    CountDAO getCountDAO() {return new CountDAO();}
}

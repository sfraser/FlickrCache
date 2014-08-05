package org.frasers.flickr.services;

/**
 * Created by sfraser on 8/4/14.
 */
public class ActivityResponse {

    public String getCounter() {
        return _counter;
    }

    final String _counter;

    public ActivityResponse(String counter) {
        _counter = counter;
    }
}

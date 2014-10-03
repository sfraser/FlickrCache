package org.frasers.flickr.services;

/**
 * Created by sfraser on 10/2/14.
 */
public class PhotosResponse {
    public String getCounter() {
        return _counter;
    }

    final String _counter;

    public PhotosResponse(String counter) {
        _counter = counter;
    }
}

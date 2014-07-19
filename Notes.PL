### Following is an example of hacking Plugin.pm for the Flickr screensaver to work with FlickrCache:

### Screensavers

# Each call to a screensaver returns a new image + metadata to display
# {
#   image   => 'http://...',
#   caption => 'text',
# }

use LWP::UserAgent;

sub _screensaver_request {
        my $ignored_url = shift;
        my $request = shift;
        my $client  = $request->client;

        # tracing -SEF
        $log->error( 'In _screensaver_request' );

        my $url = 'http://127.0.0.1:8081/mine';

        my $ua = LWP::UserAgent->new;

        # set custom HTTP request header fields
        my $req = HTTP::Request->new(GET => $url);
        $req->header('content-type' => 'application/json');

        $request->setStatusProcessing();

        my $resp = $ua->request($req);

        if ($resp->is_success) {
                my $message = $resp->decoded_content;
                $log->error( "Received reply:", $message);

                my $data = eval { from_json( $resp->content ) };
                if ( $@ || $data->{error} || !$data->{image} ) {
                        $log->error( "Error parsing JSON");

                        # Not sure what status to use here
                        $request->setStatusBadParams();
                        return;
                }

                $log->error( 'Parsed JSON looks like this: ', to_json($data) );
                $request->addResult( data => [ $data ] );

                $request->setStatusDone();
        }
        else {
                $log->error( "HTTP GET error code: ", $resp->code);
                $log->error( "HTTP GET error message: ", $resp->message);

                # Not sure what status to use here
                $request->setStatusBadParams();

       }
}

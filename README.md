# Hacker News Client

The app provides command-line interface for interaction with Hacker News API.

### Available commands

`help` prints help with available commands and their description

`top-stories [--limit=<int> --offset=<int>]` fetches top stories using paging arguments `limit` (default is 10) and `offset` (default is 0) to determine which and how many top stories to fetch

`best-stories [--limit=<int> --offset=<int>]` fetches best stories using paging arguments `limit` (default is 10) and `offset` (default is 0) to determine which and how many top stories to fetch

`new-stories [--limit=<int>. --offset=<int>]` fetches new stories using paging arguments `limit` (default is 10) and `offset` (default is 0) to determine which and how many top stories to fetch

`user-info --name=<string> [--show-submitted=<bool>]` fetches information about user specified by the `name` argument (providing this argument is mandatory), additionaly items submitted by the user will be displayed if the argument `show-submitted` is set to `true`

`get-comments --item=<int> [--limit=<int> --offset=<int>]` fetches comments of an item specified by the `item` argument (mandatory, should by a numeric ID), paging parameters `limit` and `offset` work as described above

There are also two application-wide argument that can be provided with any command:

`--ttl=<int>` specifies time-to-live (in seconds) - all cached items that were added more than `ttl`-seconds ago will be removed from cache

`--clear-cache` clears the cache for storing API responses

### Examples (using SBT)

`sbt "run top-stories --limit=20"` fetches 20 top stories

`sbt "run --clear-cache user-info --name=abc --show-submitted=true"` firstly, the cache is cleared, then info about user `abc` is displayed together with his submitted items

`sbt "run --ttl=3600 get-comments --item=123"` firstly, all items cached for more than 1 hour are removed, then item with ID `123` is displayed together with its 10 comments
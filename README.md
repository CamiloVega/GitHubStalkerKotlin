# GitHubStalkerKotlin
This is an exercise that I had to do for my current employer. The idea is to create a GitHub stalker.
Showing the users that a particular user follows. 
Given that it covers several aspects of App development I decided to do it again using the latest technologies I learned:
* RxJava2
* Retrofit2
* Dagger2
* Butterknife
* Picasso
* MVP Pattern

The full description of the excercise is the Following

## Application Specification

  Display a list of user names and profile images that belong to the bypass organization.
  Clicking on a member will display a list of user names and profile images of who they follow alphabetically.
  This process repeats itself for the newly displayed user.
  Searching should show a filtered list of users.
  The cache library provided needs expiration and invalidation.
  Feel free to improve on any code provided.

Note: If you start receiving 403 errors while developing it's becuase Github has a request limit of 60 queries per ip address per hour.

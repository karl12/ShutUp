app.config(['$routeProvider',
  function ($routeProvider) {

    $routeProvider.
    when('/home', {
      templateUrl: 'app/meeting/meeting.html',
      controller: 'MeetingCtrl'
    }).
    otherwise({
      redirectTo: '/home'
    });
  }]);